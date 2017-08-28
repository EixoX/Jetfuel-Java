package com.eixox.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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

	public final boolean executeSql(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(this.text.toString());
		putParameters(ps);
		try {
			return ps.execute();
		} finally {
			ps.close();
		}
	}

	public final Object executeScopeIdentity(Connection conn, String identityName) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(this.text.toString(), Statement.RETURN_GENERATED_KEYS);
		putParameters(ps);
		try {
			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			try {
				if (!rs.next())
					return null;
				
				Object generatedKeys = rs.getObject("GENERATED_KEYS");
				return generatedKeys;
			}
			catch (Exception ex) {
				Object generatedKeys = rs.getObject(identityName);
				
				return generatedKeys;
			}
			finally {
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

	public final <T> int executeQuery(Connection conn, EntityAspect aspect, List<T> list, int skip, int take)
			throws SQLException {
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

	public final void putParameters(PreparedStatement ps) throws SQLException {
		int s = this.parameters.size();
		for (int i = 0; i < s; i++) {
			Object pvalue = this.parameters.get(i);
			if (pvalue instanceof Character)
				ps.setObject(i + 1, pvalue.toString());
			else
				ps.setObject(i + 1, pvalue);
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
