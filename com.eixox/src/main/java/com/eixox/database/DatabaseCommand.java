package com.eixox.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.eixox.data.entities.EntityAspect;

public class DatabaseCommand {

	public final StringBuilder text;
	public final ArrayList<Object> parameters;

	public DatabaseCommand() {
		this.text = new StringBuilder();
		this.parameters = new ArrayList<Object>();
	}

	public DatabaseCommand(int textCapaticy) {
		this.text = new StringBuilder(textCapaticy);
		this.parameters = new ArrayList<Object>();
	}

	public DatabaseCommand(int textCapaticy, int valueCapacity) {
		this.text = new StringBuilder(textCapaticy);
		this.parameters = new ArrayList<Object>(valueCapacity);
	}

	public int executeNonQuery(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(text.toString(), ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
		putParameters(ps);
		try {
			return ps.executeUpdate();
		} finally {
			ps.close();
		}
	}

	public Object executeScalar(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(text.toString(), ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
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

	public Object executeScopeIdentity(Connection conn) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(text.toString(), Statement.RETURN_GENERATED_KEYS);
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

	@SuppressWarnings("unchecked")
	public <T> int executeQuery(Connection conn, EntityAspect aspect, List<T> list) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(text.toString(), ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
		putParameters(ps);
		int count = list.size();
		try {
			ResultSet rs = ps.executeQuery();
			try {
				if (rs.next()) {
					int[] mappings = new int[aspect.getCount()];
					ResultSetMetaData metadata = rs.getMetaData();
					int imax = metadata.getColumnCount() >= mappings.length ?
							mappings.length :
							metadata.getColumnCount();

					for (int i = 0; i < imax; i++)
						mappings[i] = aspect.getColumnOrdinal(metadata.getColumnName(i + 1));

					do {

						T entity = (T) aspect.newInstance();
						for (int i = 0; i < imax; i++)
							if (mappings[i] >= 0)
							{
								Object value = rs.getObject(i + 1);
								if (value != null)
									aspect.setValue(entity, mappings[i], value);
							}

						list.add(entity);

					} while (rs.next());
				}

			} finally {
				rs.close();
			}
		} finally {
			ps.close();
		}
		return list.size() - count;
	}

	public Object executeQuerySingleResult(Connection conn, EntityAspect aspect) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(text.toString(), ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
		putParameters(ps);
		Object entity = null;
		try {
			ResultSet rs = ps.executeQuery();
			try {
				if (rs.next()) {
					entity = aspect.newInstance();
					ResultSetMetaData metadata = rs.getMetaData();
					int count = metadata.getColumnCount();

					for (int i = 0; i < count; i++) {
						int ordinal = aspect.getColumnOrdinal(metadata.getColumnName(i + 1));
						if (ordinal >= 0)
						{
							Object value = rs.getObject(i + 1);
							if (value != null)
								aspect.setValue(entity, ordinal, value);
						}
					}
				}
			} finally {
				rs.close();
			}
		} finally {
			ps.close();
		}
		return entity;
	}

	public int executeMemberQuery(Connection conn, EntityAspect aspect, List<Object> list) throws SQLException {
		PreparedStatement ps = conn.prepareStatement(text.toString(), ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
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

	public void putParameters(PreparedStatement ps) throws SQLException {
		int s = parameters.size();
		for (int i = 0; i < s; i++)
			ps.setObject(i + 1, this.parameters.get(i));
	}

}
