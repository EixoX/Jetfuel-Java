package com.eixox.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.eixox.data.DataDelete;
import com.eixox.data.DataInsert;
import com.eixox.data.DataSelect;
import com.eixox.data.DataSelectResult;
import com.eixox.data.DataUpdate;
import com.eixox.data.Storage;
import com.eixox.data.entities.EntityAspect;
import com.eixox.database.schema.SchemaDb;

public abstract class Database implements Storage {

	public final String url;
	public final Properties properties;
	public final Class<?> driverClass;
	public final DatabaseDialect dialect;
	private String user;
	private String password;

	public abstract String getDriverClassName();

	protected abstract DatabaseDialect createDialect();

	public Database(String url) {
		this(url, null);
	}

	public Database(String url, Properties properties) {
		try {
			this.driverClass = Class.forName(getDriverClassName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		this.dialect = createDialect();
		this.url = url;
		this.properties = properties;
	}
	
	public Database(String url, String user, String password) {
		this(url, null);
		this.user = user;
		this.password = password;
	}

	public final Connection getConnection() throws SQLException {
		if (this.user != null && !this.user.isEmpty()) {
			return DriverManager.getConnection(url, this.user, this.password);
		}
		
		return this.properties == null
				? DriverManager.getConnection(this.url)
				: DriverManager.getConnection(this.url, this.properties);
	}

	public final DataSelect select(String name) {
		return new DatabaseSelect(this, name);
	}

	public final DataUpdate update(String name) {
		return new DatabaseUpdate(this, name);
	}

	public final DataDelete delete(String name) {
		return new DatabaseDelete(this, name);
	}

	public final DataInsert insert(String name) {
		return new DatabaseInsert(this, name);
	}

	public synchronized <T> List<T> executeQuery(EntityAspect aspect, String commandText, Object... commandParameters) {
		DatabaseCommand command = new DatabaseCommand();
		command.text.append(commandText);
		for (int i = 0; i < commandParameters.length; i++)
			command.parameters.add(commandParameters[i]);
		List<T> list = new ArrayList<T>();
		try {
			Connection conn = getConnection();
			try {
				command.executeQuery(conn, aspect, list);
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public synchronized DataSelectResult executeQuery(String commandText, Object... commandParameters) {
		DatabaseCommand command = new DatabaseCommand();
		command.text.append(commandText);
		for (int i = 0; i < commandParameters.length; i++)
			command.parameters.add(commandParameters[i]);
		try {
			Connection conn = getConnection();
			try {
				PreparedStatement ps = conn.prepareStatement(command.text.toString());
				DataSelectResult result = new DataSelectResult();
				command.putParameters(ps);

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
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized Object executeScalar(String commandText, Object... commandParameters) {
		DatabaseCommand command = new DatabaseCommand();
		command.text.append(commandText);
		for (int i = 0; i < commandParameters.length; i++)
			command.parameters.add(commandParameters[i]);
		try {
			Connection conn = getConnection();
			try {
				return command.executeScalar(conn);
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized int executeNonQuery(String commandText, Object... commandParameters) {
		DatabaseCommand command = new DatabaseCommand();
		command.text.append(commandText);
		for (int i = 0; i < commandParameters.length; i++)
			command.parameters.add(commandParameters[i]);
		try {
			Connection conn = getConnection();
			try {
				return command.executeNonQuery(conn);
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public synchronized boolean executeSql(String commandText, Object... commandParameters) {
		DatabaseCommand command = new DatabaseCommand();
		command.text.append(commandText);
		for (int i = 0; i < commandParameters.length; i++)
			command.parameters.add(commandParameters[i]);
		try {
			Connection conn = getConnection();
			try {
				return command.executeSql(conn);
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private SchemaDb schema = null;

	protected abstract SchemaDb readSchema();

	public synchronized final SchemaDb refreshSchema() {
		schema = readSchema();
		return schema;
	}

	public synchronized final SchemaDb getSchema() {
		if (schema == null)
			schema = readSchema();
		return schema;
	}
}
