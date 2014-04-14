package com.eixox.database;

import com.eixox.Pair;
import com.eixox.PairList;
import com.eixox.data.ClassStorage;
import com.eixox.filters.ClassFilter;
import com.eixox.filters.ClassFilterExpression;
import com.eixox.filters.ClassFilterNode;
import com.eixox.filters.ClassFilterTerm;
import com.eixox.sorters.ClassSorterDirection;
import com.eixox.sorters.ClassSorterNode;

public class DbDialect {
	// __________________________________________________________________________________________________________________
	public String formatName(String name) {
		return "`" + name + "`";
	}

	// __________________________________________________________________________________________________________________
	private final void appendSelect(SqlCommand cmd, ClassStorage<?> aspect, int... ordinals) {
		cmd.append("SELECT ");
		cmd.append(formatName(aspect.getDataName(ordinals[0])));

		for (int i = 1; i < ordinals.length; i++) {
			cmd.append(", ");
			cmd.append(formatName(aspect.getDataName(ordinals[i])));
		}
	}

	// __________________________________________________________________________________________________________________
	private final void appendSelect(SqlCommand cmd, ClassStorage<?> aspect) {
		int count = aspect.getCount();
		cmd.append("SELECT ");
		cmd.append(formatName(aspect.getDataName(0)));

		for (int i = 1; i < count; i++) {
			cmd.append(", ");
			cmd.append(formatName(aspect.getDataName(i)));
		}
	}

	// __________________________________________________________________________________________________________________
	private final void appendNameValue(SqlCommand cmd, ClassStorage<?> aspect, int ordinal, Object value) {
		String name = formatName(aspect.getDataName(ordinal));
		cmd.append(name);
		cmd.append("=?");
		cmd.addValue(value);
	}

	// __________________________________________________________________________________________________________________
	private final void appendNameValue(SqlCommand cmd, ClassStorage<?> aspect, Pair<Integer, Object> pair) {
		String name = formatName(aspect.getDataName(pair.getKey()));
		cmd.append(name);
		cmd.append("=?");
		cmd.addValue(pair.getValue());
	}

	// __________________________________________________________________________________________________________________
	private final void appendFrom(SqlCommand cmd, ClassStorage<?> aspect) {
		cmd.append(" FROM ");
		cmd.append(formatName(aspect.getDataName()));
	}

	// __________________________________________________________________________________________________________________
	private final void appendWhere(SqlCommand cmd, ClassStorage<?> aspect, ClassFilter filter) {
		if (filter != null) {
			cmd.append(" WHERE ");
			appendFilter(cmd, aspect, filter);
		}
	}

	// __________________________________________________________________________________________________________________
	private final void appendWhere(SqlCommand cmd, ClassStorage<?> aspect, int ordinal, Object value) {
		cmd.append(" WHERE ");
		if (value == null) {
			cmd.append(formatName(aspect.getDataName(ordinal)));
			cmd.append(" IS NULL");
		} else {
			appendNameValue(cmd, aspect, ordinal, value);
		}
	}

	// __________________________________________________________________________________________________________________
	private final void appendWhere(SqlCommand cmd, ClassStorage<?> aspect, int[] filterOrdinals, Object[] filterValues) {
		if (filterOrdinals != null && filterOrdinals.length > 0) {
			cmd.append(" WHERE ");
			appendNameValue(cmd, aspect, filterOrdinals[0], filterValues[0]);
			for (int i = 0; i < filterOrdinals.length; i++) {
				cmd.append(" AND ");
				appendNameValue(cmd, aspect, filterOrdinals[i], filterValues[i]);
			}
		}
	}

	// __________________________________________________________________________________________________________________
	private final void appendFilter(SqlCommand cmd, ClassStorage<?> aspect, ClassFilter filter) {
		if (filter instanceof ClassFilterTerm)
			appendFilterTerm(cmd, aspect, (ClassFilterTerm) filter);
		else if (filter instanceof ClassFilterNode)
			appendFilterNode(cmd, aspect, (ClassFilterNode) filter);
		else if (filter instanceof ClassFilterExpression)
			appendFilterExpression(cmd, aspect, (ClassFilterExpression) filter);
		else
			throw new RuntimeException("Unknown filter type " + filter.getClass());
	}

	// __________________________________________________________________________________________________________________
	private final void appendFilterNode(SqlCommand cmd, ClassStorage<?> aspect, ClassFilterNode node) {
		appendFilter(cmd, aspect, node.getFilter());
		ClassFilterNode next = node.getNext();
		if (next != null) {
			switch (node.getOperation()) {
				case And:
					cmd.append(" AND ");
					break;
				case Or:
					cmd.append(" OR ");
					break;
				default:
					throw new RuntimeException("Unknown filter op " + node.getOperation());
			}
			appendFilterNode(cmd, aspect, next);
		}
	}

	// __________________________________________________________________________________________________________________
	private final void appendFilterExpression(SqlCommand cmd, ClassStorage<?> aspect, ClassFilterExpression expression) {
		cmd.append("(");
		appendFilterNode(cmd, aspect, expression.getFirst());
		cmd.append(")");
	}

	// __________________________________________________________________________________________________________________
	protected void appendFilterTerm(SqlCommand cmd, ClassStorage<?> aspect, ClassFilterTerm term) {

		cmd.append(formatName(aspect.getDataName(term.getOrdinal())));
		Object value = term.getValue();
		switch (term.getComparison()) {
			case EqualTo:
				if (value == null)
					cmd.append(" IS NULL");
				else {
					cmd.append(" = ?");
					cmd.addValue(value);
				}
				break;
			case GreaterOrEqual:
				cmd.append(" >= ?");
				cmd.addValue(value);
				break;
			case GreaterThan:
				cmd.append(" > ?");
				cmd.addValue(value);
				break;
			case InCollection:
				cmd.append(" IN (?)");
				cmd.addValue(value);
				break;
			case Like:
				cmd.append(" LIKE ?");
				cmd.addValue(value);
				break;
			case LowerOrEqual:
				cmd.append(" <= ?");
				cmd.addValue(value);
				break;
			case LowerThan:
				cmd.append(" < ?");
				cmd.addValue(value);
				break;
			case NotEqualTo:
				if (value == null) {
					cmd.append(" IS NOT NULL");
				} else {
					cmd.append(" != ?");
					cmd.addValue(value);
				}
				break;
			case NotInCollection:
				cmd.append(" NOT IN (?)");
				cmd.addValue(value);
				break;
			case NotLike:
				cmd.append(" NOT LIKE ?");
				cmd.addValue(value);
				break;
			default:
				throw new RuntimeException("Unknwon filter comparison " + term.getComparison());
		}
	}

	// __________________________________________________________________________________________________________________
	private final void appendSorter(SqlCommand cmd, ClassStorage<?> aspect, ClassSorterNode sorter) {
		if (sorter == null)
			return;
		cmd.append(" ORDER BY ");

		cmd.append(formatName(aspect.getDataName(sorter.getOrdinal())));
		if (sorter.getDirection() == ClassSorterDirection.Descending) {
			cmd.append(" DESC");
		}

		sorter = sorter.getNext();
		while (sorter != null) {
			cmd.append(", ");
			cmd.append(formatName(aspect.getDataName(sorter.getOrdinal())));
			if (sorter.getDirection() == ClassSorterDirection.Descending) {
				cmd.append(" DESC");
			}
			sorter = sorter.getNext();
		}
	}

	// __________________________________________________________________________________________________________________
	protected void appendPager(SqlCommand cmd, int pageSize, int pageOrdinal) {
		cmd.append(" LIMIT ");
		cmd.append(pageSize * pageOrdinal);
		cmd.append(", ");
		cmd.append(pageSize);
	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createSelect(ClassStorage<?> aspect, ClassFilter filter, ClassSorterNode sorter, int pageSize,
			int pageOrdinal) {

		SqlCommand cmd = new SqlCommand();

		appendSelect(cmd, aspect);
		appendFrom(cmd, aspect);
		appendWhere(cmd, aspect, filter);
		appendSorter(cmd, aspect, sorter);
		appendPager(cmd, pageSize, pageOrdinal);

		return cmd;

	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createSelectByMember(ClassStorage<?> aspect, int filterOrdinal, Object filterValue, int pageSize,
			int pageOrdinal) {

		SqlCommand cmd = new SqlCommand();

		appendSelect(cmd, aspect);
		appendFrom(cmd, aspect);
		appendWhere(cmd, aspect, filterOrdinal, filterValue);
		appendPager(cmd, pageSize, pageOrdinal);

		return cmd;
	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createSelectByMembers(ClassStorage<?> aspect, int[] filterOrdinals, Object[] filterValues,
			int pageSize, int pageOrdinal) {

		SqlCommand cmd = new SqlCommand();

		appendSelect(cmd, aspect);
		appendFrom(cmd, aspect);
		appendWhere(cmd, aspect, filterOrdinals, filterValues);
		appendPager(cmd, pageSize, pageOrdinal);

		return cmd;
	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createDelete(ClassStorage<?> aspect, ClassFilter filter) {

		SqlCommand cmd = new SqlCommand();

		cmd.append("DELETE ");
		appendFrom(cmd, aspect);
		appendWhere(cmd, aspect, filter);

		return cmd;
	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createDeleteByMember(ClassStorage<?> aspect, int memberOrdinal, Object memberValue) {

		SqlCommand cmd = new SqlCommand();

		cmd.append("DELETE ");
		appendFrom(cmd, aspect);
		appendWhere(cmd, aspect, memberOrdinal, memberValue);

		return cmd;
	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createDeleteByMembers(ClassStorage<?> aspect, int[] memberOrdinals, Object[] memberValues) {

		SqlCommand cmd = new SqlCommand();

		cmd.append("DELETE ");
		appendFrom(cmd, aspect);
		appendWhere(cmd, aspect, memberOrdinals, memberValues);

		return cmd;

	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createInsert(ClassStorage<?> aspect, PairList<Integer, Object> values) {

		SqlCommand cmd = new SqlCommand();
		cmd.append("INSERT INTO ");
		cmd.append(formatName(aspect.getDataName()));
		cmd.append(" (");

		boolean prependComma = false;
		for (Pair<Integer, Object> pair : values) {
			if (prependComma)
				cmd.append(", ");
			else
				prependComma = true;

			cmd.append(formatName(aspect.getDataName(pair.getKey())));
		}

		prependComma = false;

		cmd.append(") VALUES (");
		for (Pair<Integer, Object> pair : values) {
			if (prependComma)
				cmd.append(", ");
			else
				prependComma = true;

			cmd.append("?");
			cmd.addValue(pair.getValue());
		}
		cmd.append(")");
		return cmd;
	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createInsertAndScopeIdentity(ClassStorage<?> aspect, PairList<Integer, Object> values,
			int identityOrdinal) {

		return createInsert(aspect, values);
	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createUpdate(ClassStorage<?> aspect, PairList<Integer, Object> values, ClassFilter filter) {

		SqlCommand cmd = new SqlCommand();
		cmd.append("UPDATE ");
		cmd.append(formatName(aspect.getDataName()));
		cmd.append(" SET ");
		boolean prependComma = false;

		for (Pair<Integer, Object> pair : values) {
			if (prependComma)
				cmd.append(", ");
			else
				prependComma = true;

			appendNameValue(cmd, aspect, pair);
		}

		if (filter != null) {
			cmd.append(" WHERE ");
			appendFilter(cmd, aspect, filter);
		}

		return cmd;

	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createUpdateByMember(ClassStorage<?> aspect, PairList<Integer, Object> values, int filterOrdinal,
			Object filterValue) {

		SqlCommand cmd = new SqlCommand();
		cmd.append("UPDATE ");
		cmd.append(formatName(aspect.getDataName()));
		cmd.append(" SET ");
		boolean prependComma = false;

		for (Pair<Integer, Object> pair : values) {
			if (prependComma)
				cmd.append(", ");
			else
				prependComma = true;

			appendNameValue(cmd, aspect, pair);
		}

		cmd.append(" WHERE ");

		appendNameValue(cmd, aspect, filterOrdinal, filterValue);

		return cmd;
	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createUpdateByMembers(ClassStorage<?> aspect, PairList<Integer, Object> values,
			int[] filterOrdinals, Object[] filterValues) {

		SqlCommand cmd = new SqlCommand();
		cmd.append("UPDATE ");
		cmd.append(formatName(aspect.getDataName()));
		cmd.append(" SET ");
		boolean prependComma = false;

		for (Pair<Integer, Object> pair : values) {
			if (prependComma)
				cmd.append(", ");
			else
				prependComma = true;

			appendNameValue(cmd, aspect, pair);
		}

		appendWhere(cmd, aspect, filterOrdinals, filterValues);
		return cmd;
	}

	// __________________________________________________________________________________________________________________
	public final SqlCommand createSelectOne(ClassStorage<?> aspect, ClassFilter filter, ClassSorterNode sorter) {

		return createSelect(aspect, filter, sorter, 1, 0);
	}

	// __________________________________________________________________________________________________________________
	public final SqlCommand createSelectOneByMember(ClassStorage<?> aspect, int memberOrdinal, Object memberValue) {
		return createSelectByMember(aspect, memberOrdinal, memberValue, 1, 0);
	}

	// __________________________________________________________________________________________________________________
	public final SqlCommand createSelectOneByMembers(ClassStorage<?> aspect, int[] memberOrdinals, Object[] memberValues) {
		return createSelectByMembers(aspect, memberOrdinals, memberValues, 1, 0);
	}

	// __________________________________________________________________________________________________________________
	public final SqlCommand createSelectMember(ClassStorage<?> aspect, int memberOrdinal, ClassFilter filter,
			ClassSorterNode sorter) {

		return createSelectMembers(aspect, memberOrdinal, filter, sorter, 1, 0);
	}

	// __________________________________________________________________________________________________________________
	public final SqlCommand createSelectMemberByMember(ClassStorage<?> aspect, int memberOrdinal, int filterOrdinal,
			Object filterValue) {

		SqlCommand cmd = new SqlCommand();
		appendSelect(cmd, aspect, memberOrdinal);
		appendFrom(cmd, aspect);
		appendWhere(cmd, aspect, filterOrdinal, filterValue);
		return cmd;
	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createSelectMemberByMembers(ClassStorage<?> aspect, int memberOrdinal, int[] filterOrdinals,
			Object[] filterValues) {

		SqlCommand cmd = new SqlCommand();
		appendSelect(cmd, aspect, memberOrdinal);
		appendFrom(cmd, aspect);
		appendWhere(cmd, aspect, filterOrdinals, filterValues);
		return cmd;
	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createSelectMembers(ClassStorage<?> aspect, int memberOrdinal, ClassFilter filter,
			ClassSorterNode sorter, int pageSize, int pageOrdinal) {

		SqlCommand cmd = new SqlCommand();
		appendSelect(cmd, aspect, memberOrdinal);
		appendWhere(cmd, aspect, filter);
		appendSorter(cmd, aspect, sorter);
		appendPager(cmd, pageSize, pageOrdinal);
		return cmd;
	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createSelectCount(ClassStorage<?> aspect, ClassFilter filter) {

		SqlCommand cmd = new SqlCommand();
		cmd.append("SELECT COUNT(*)");
		appendFrom(cmd, aspect);
		appendWhere(cmd, aspect, filter);
		return cmd;

	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createSelectCountByMember(ClassStorage<?> aspect, int memberOrdinal, Object memberValue) {
		SqlCommand cmd = new SqlCommand();
		cmd.append("SELECT COUNT(*)");
		appendFrom(cmd, aspect);
		appendWhere(cmd, aspect, memberOrdinal, memberValue);
		return cmd;
	}

	// __________________________________________________________________________________________________________________
	public SqlCommand createSelectCountByMembers(ClassStorage<?> aspect, int[] memberOrdinals, Object[] memberValues) {
		SqlCommand cmd = new SqlCommand();
		cmd.append("SELECT COUNT(*)");
		appendFrom(cmd, aspect);
		appendWhere(cmd, aspect, memberOrdinals, memberValues);
		return cmd;
	}
}
