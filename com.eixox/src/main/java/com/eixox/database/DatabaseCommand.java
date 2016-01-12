package com.eixox.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.eixox.Strings;
import com.eixox.data.DataSelectResult;
import com.eixox.data.entities.EntityAspect;

public class DatabaseCommand {

	public final StringBuilder text;
	public final ArrayList<Object> parameters;

	public DatabaseCommand() {
		this(512);
	}

	public DatabaseCommand(int textLength) {
		this.parameters = new ArrayList<Object>();
		this.text = new StringBuilder(textLength);
	}

	public DatabaseCommand(int textLength, int paramCount) {
		this.parameters = new ArrayList<Object>(paramCount);
		this.text = new StringBuilder(textLength);
	}

	public final int executeNonQuery(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(this.text.toString());
		putParameters(ps);
		try {
			return ps.executeUpdate();
		} finally {
			ps.close();
		}
	}

	public final Object executeScalar(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(this.text.toString());
		putParameters(ps);
		try {
			ResultSet rs = ps.executeQuery();
			try {
				return rs.next() ? rs.getObject(1) : null;
			} finally {
				rs.close();
			}
		} finally {
			ps.close();
		}
	}

	public final Object executeScopeIdentity(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(this.text.toString(), Statement.RETURN_GENERATED_KEYS);
		putParameters(ps);
		try {
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			try {
				return rs.next() ? rs.getObject(1) : null;
			} finally {
				rs.close();
			}
		} finally {
			ps.close();
		}
	}

	public final <T> int executeQuery(Connection conn, EntityAspect aspect, List<T> list) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(this.text.toString());
		putParameters(ps);
		int count = list.size();
		try {
			ResultSet rs = ps.executeQuery();
			try {
				ResultsetToClassIterator<T> rtc = new ResultsetToClassIterator<T>(aspect, rs);
				while (rtc.hasNext())
					list.add(rtc.next());
			} finally {
				rs.close();
			}
		} finally {
			ps.close();
		}
		return list.size() - count;
	}

	public final <T> int executeQuery(Connection conn, EntityAspect aspect, List<T> list, int skip, int take) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(this.text.toString());
		putParameters(ps);
		int count = list.size();
		try {
			ResultSet rs = ps.executeQuery();
			try {
				ResultsetToClassIterator<T> rtc = new ResultsetToClassIterator<T>(aspect, rs);
				if (skip > 0)
					rtc.skip(skip);

				if (take > 0)
					return rtc.take(take, list);
				else {
					while (rtc.hasNext())
						list.add(rtc.next());
					return list.size() - count;
				}
			} finally {
				rs.close();
			}
		} finally {
			ps.close();
		}

	}

	public final <T> T executeQuerySingleResult(Connection conn, EntityAspect aspect) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(this.text.toString());
		putParameters(ps);

		try {
			ResultSet rs = ps.executeQuery();
			try {
				ResultsetToClassIterator<T> rtc = new ResultsetToClassIterator<T>(aspect, rs);
				return rtc.hasNext() ? rtc.next() : null;
			} finally {
				rs.close();
			}
		} finally {
			ps.close();
		}

	}

	public final int executeMemberQuery(Connection conn, List<Object> list) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(this.text.toString());
		putParameters(ps);
		int count = list.size();
		try {
			ResultSet rs = ps.executeQuery();
			try {
				while (rs.next())
					list.add(rs.getObject(1));

			} finally {
				rs.close();
			}
		} finally {
			ps.close();
		}
		return list.size() - count;
	}

	public final void putParameters(PreparedStatement ps) throws SQLException {
		int s = this.parameters.size();
		for (int i = 0; i < s; i++) {
			Object pvalue = this.parameters.get(i);
			if (pvalue == null)
				ps.setNull(i + 1, Types.NULL);
			else
				ps.setObject(i + 1, pvalue);
		}

	}

	public final DataSelectResult executeQueryToResult(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(this.text.toString());
		DataSelectResult result = new DataSelectResult();
		putParameters(ps);

		try {
			ResultSet rs = ps.executeQuery();
			try {
				if (rs.next()) {
					ResultSetMetaData metadata = rs.getMetaData();
					int count = metadata.getColumnCount();
					for (int i = 0; i < count; i++) {
						result.cols.add(metadata.getColumnName(i + 1));
					}

					do {
						Object[] arr = new Object[count];
						for (int i = 0; i < arr.length; i++)
							arr[i] = rs.getObject(i + 1);
						result.rows.add(arr);
					} while (rs.next());

				}
			} finally {
				rs.close();
			}
		} finally {
			ps.close();
		}

		return result;

	}

	public final Object[] executeQuerySingleResult(Connection conn, int colCount) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(this.text.toString());
		putParameters(ps);
		try {
			ResultSet rs = ps.executeQuery();
			try {
				if (rs.next()) {
					Object[] row = new Object[colCount];
					for (int i = 0; i < colCount; i++)
						row[i] = rs.getObject(i + 1);
					return row;
				} else
					return null;

			} finally {
				rs.close();
			}
		} finally {
			ps.close();
		}
	}

	public final <T> T executeQuery(Connection conn, ResultSetProcessor<T> processor) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(this.text.toString());
		putParameters(ps);
		try {
			ResultSet rs = ps.executeQuery();
			try {
				return processor.process(rs);
			} finally {
				rs.close();
			}
		} finally {
			ps.close();
		}
	}
}
