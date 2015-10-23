package com.eixox.database;

import java.sql.Connection;
import java.sql.DriverManager;
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

public abstract class Database implements Storage {

	public final String url;
	public final Properties properties;
	public final Class<?> driverClass;
	public final DatabaseDialect dialect;

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

	public final Connection getConnection() throws SQLException {
		return this.properties == null ? DriverManager.getConnection(this.url)
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
				return command.executeQueryToResult(conn);
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
}
