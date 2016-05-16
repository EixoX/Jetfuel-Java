package com.eixox.data.entities;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.eixox.data.Aggregate;
import com.eixox.data.AggregateFilterExpression;
import com.eixox.data.Filter;
import com.eixox.data.FilterExpression;
import com.eixox.data.FilterNode;
import com.eixox.data.FilterOperation;
import com.eixox.data.FilterTerm;

public class EntityAspect<T> extends ArrayList<EntityAspectMember<T>> {

	private static final long serialVersionUID = -8834993804389021748L;
	public final String tableName;
	public final Class<T> entityClass;
	public final EntityAspectMember<T> identity;
	public final EntityAspectMember<T>[] compositeKeys;
	public final EntityAspectMember<T>[] uniques;
	public final boolean hasAggregates;

	private final String findTableName() {
		Persistent ps = this.entityClass.getAnnotation(Persistent.class);
		return ps == null || ps.name().isEmpty() ? this.entityClass.getSimpleName() : ps.name();
	}

	public EntityAspect(Class<T> claz) {
		this(claz, claz.getDeclaredFields());
	}

	protected EntityAspectMember<T> decorate(Field field) {
		Persistent persistent = field.getAnnotation(Persistent.class);
		return persistent == null ? null : new EntityAspectMember<T>(this, field, persistent);

	}

	@SuppressWarnings("unchecked")
	public EntityAspect(Class<T> claz, Field[] fields) {
		super(fields.length);
		this.entityClass = claz;

		boolean hasaggr = false;
		EntityAspectMember<T> id = null;
		ArrayList<EntityAspectMember<T>> uniquesList = new ArrayList<EntityAspectMember<T>>();
		ArrayList<EntityAspectMember<T>> compositeList = new ArrayList<EntityAspectMember<T>>();

		for (int i = 0; i < fields.length; i++) {
			EntityAspectMember<T> member = decorate(fields[i]);
			if (member != null) {
				this.add(member);
				if (member.aggregate != Aggregate.NONE)
					hasaggr = true;

				switch (member.columnType) {
				case COMPOSITE_KEY:
					compositeList.add(member);
					break;
				case IDENTITY:
					if (id != null)
						throw new RuntimeException(
								"You can't have more than one identity for a given entity: " + member.field);
					else
						id = member;
					break;
				case UNIQUE:
					uniquesList.add(member);
					break;
				default:
					break;
				}
			}
		}
		this.tableName = findTableName();
		this.identity = id;
		this.compositeKeys = compositeList.toArray(new EntityAspectMember[0]);
		this.uniques = uniquesList.toArray(new EntityAspectMember[0]);
		this.hasAggregates = hasaggr;
	}

	public final String getName() {
		return this.tableName;
	}

	public final EntityAspectMember<T> get(String name) {
		int s = this.size();
		for (int i = 0; i < s; i++) {
			EntityAspectMember<T> eam = this.get(i);
			if (name.equalsIgnoreCase(eam.field.getName()))
				return eam;
		}
		throw new RuntimeException(name + " was not found on " + this.entityClass);
	}

	public final EntityAspectMember<T> getByColumnName(String columnName) {
		int s = this.size();
		for (int i = 0; i < s; i++) {
			EntityAspectMember<T> eam = this.get(i);
			if (columnName.equalsIgnoreCase(eam.columName))
				return eam;
		}
		return null;
	}

	public final T newInstance() {
		try {
			return this.entityClass.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isIdentity(Object value) {
		if (value == null)
			return false;
		else if (Number.class.isAssignableFrom(value.getClass())) {
			return ((Number) value).longValue() > 0L;
		} else if (value instanceof String) {
			return !((String) value).isEmpty();
		} else
			throw new RuntimeException("IMPLEMENT the emptiness of " + value.getClass());
	}

	public final int getOrdinal(String name) {
		int s = this.size();
		for (int i = 0; i < s; i++)
			if (name.equalsIgnoreCase(get(i).getName()))
				return i;
		return -1;
	}

	public final void transformFilter(Filter source, FilterExpression where,
			AggregateFilterExpression having, FilterOperation op) {

		switch (source.getFilterType()) {
		case EXPRESSION:
			FilterExpression exp = new FilterExpression();
			AggregateFilterExpression aggr = new AggregateFilterExpression();
			transformFilter(((FilterExpression) source).first, exp, aggr, FilterOperation.AND);
			switch (op) {
			case AND:
				if (!exp.isEmpty())
					where.andWhere(exp);
				if (aggr.first != null)
					having.andHaving(aggr);
				break;
			case OR:
				if (!exp.isEmpty())
					where.orWhere(exp);
				if (aggr.first != null)
					having.orHaving(aggr);
				break;
			default:
				throw new RuntimeException("Can't handle filter operation " + op);
			}
			break;
		case NODE:
			FilterNode node = (FilterNode) source;
			transformFilter(node.filter, where, having, op);
			if (node.next != null) {
				transformFilter(node.next, where, having, node.operation);
			}
			break;
		case TERM:
			FilterTerm term = (FilterTerm) source;
			EntityAspectMember<T> member = get(term.name);
			switch (op) {
			case AND:
				if (member.aggregate != Aggregate.NONE)
					having.andHaving(member.aggregate, member.columName, term.comparison, term.value);
				else
					where.andWhere(member.columName, term.comparison, term.value);
				break;
			case OR:
				if (member.aggregate != Aggregate.NONE)
					having.orHaving(member.aggregate, member.columName, term.comparison, term.value);
				else
					where.orWhere(member.columName, term.comparison, term.value);
				break;
			default:
				throw new RuntimeException("Can't handle filter operation " + op);
			}
			break;
		default:
			throw new RuntimeException("Can't handle filter type " + source.getFilterType());

		}
	}
}
