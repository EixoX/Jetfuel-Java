package com.eixox.data.database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;

import com.eixox.data.Adapter;

public class SqlDatabase {

	private final Driver driver;
	private final String url;
	private final Properties properties;

	public SqlDatabase(String url, Properties properties) throws SQLException {
		this.driver = DriverManager.getDriver(url);
		this.url = url;
		this.properties = properties;
	}

	public SqlDatabase(String url, String username, String password) throws SQLException {
		this.driver = DriverManager.getDriver(url);
		this.url = url;
		this.properties = new Properties();
		this.properties.setProperty("user", username);
		if (password != null)
			this.properties.setProperty("password", password);
	}

	// Description Here:
	// _____________________________________________________
	public synchronized final int executeNonQuery(String command) throws SQLException {
		final Connection conn = this.driver.connect(this.url, this.properties);
		try {
			final Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			try {
				return stmt.executeUpdate(command);
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}
	}

	// Description Here:
	// _____________________________________________________
	public synchronized final int executeNonQuery(String command, Object... parameters) throws SQLException {
		final Connection conn = this.driver.connect(this.url, this.properties);
		try {
			final CallableStatement call = conn.prepareCall(command, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			try {
				for (int i = 0; i < parameters.length; i++)
					call.setObject(i + 1, parameters[i]);
				return call.executeUpdate();
			} finally {
				call.close();
			}
		} finally {
			conn.close();
		}
	}

	// Description Here:
	// _____________________________________________________
	public synchronized final int executeNonQuery(String command, List<Object> paramList) throws SQLException {
		final Connection conn = this.driver.connect(this.url, this.properties);
		try {
			final CallableStatement call = conn.prepareCall(command, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			try {
				final int pCount = paramList.size();
				for (int i = 0; i < pCount; i++)
					call.setObject(i + 1, paramList.get(i));
				return call.executeUpdate();
			} finally {
				call.close();
			}
		} finally {
			conn.close();
		}
	}

	// Description Here:
	// _____________________________________________________
	public synchronized final Object executeScalar(String command) throws SQLException {
		final Connection conn = this.driver.connect(this.url, this.properties);
		try {
			final Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			try {
				final ResultSet rs = stmt.executeQuery(command);
				try {
					if (rs.next())
						return rs.getObject(1);
					else
						return null;
				} finally {
					rs.close();
				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}
	}

	// Description Here:
	// _____________________________________________________
	public synchronized final Object executeScalar(String command, Object... parameters) throws SQLException {
		final Connection conn = this.driver.connect(this.url, this.properties);
		try {
			final CallableStatement call = conn.prepareCall(command, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			try {
				for (int i = 0; i < parameters.length; i++)
					call.setObject(i + 1, parameters[i]);

				final ResultSet rs = call.executeQuery();
				try {
					if (rs.next())
						return rs.getObject(1);
					else
						return null;
				} finally {
					rs.close();
				}

			} finally {
				call.close();
			}
		} finally {
			conn.close();
		}
	}

	// Description Here:
	// _____________________________________________________
	public synchronized final Object executeScalar(String command, List<Object> paramList) throws SQLException {
		final Connection conn = this.driver.connect(this.url, this.properties);
		try {
			final CallableStatement call = conn.prepareCall(command, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			try {
				final int pCount = paramList.size();
				for (int i = 0; i < pCount; i++)
					call.setObject(i + 1, paramList.get(i));

				final ResultSet rs = call.executeQuery();
				try {
					if (rs.next())
						return rs.getObject(1);
					else
						return null;
				} finally {
					rs.close();
				}

			} finally {
				call.close();
			}
		} finally {
			conn.close();
		}
	}

	// Description Here:
	// _____________________________________________________
	public synchronized final Object executeNonQueryAndScopeIdentity(String command) throws SQLException {
		final Connection conn = this.driver.connect(this.url, this.properties);
		try {
			final Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			try {
				stmt.executeUpdate(command, Statement.RETURN_GENERATED_KEYS);
				final ResultSet generatedKeys = stmt.getGeneratedKeys();
				try {
					return generatedKeys.next() ? generatedKeys.getObject(1) : null;
				} finally {
					generatedKeys.close();
				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}
	}

	// Description Here:
	// _____________________________________________________
	public synchronized final Object executeNonQueryAndScopeIdentity(String command, Object... parameters) throws SQLException {
		final Connection conn = this.driver.connect(this.url, this.properties);
		try {
			final CallableStatement call = conn.prepareCall(command, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			try {
				for (int i = 0; i < parameters.length; i++)
					call.setObject(i + 1, parameters[i]);
				call.executeUpdate();
				final ResultSet generatedKeys = call.getGeneratedKeys();
				try {
					return generatedKeys.next() ? generatedKeys.getObject(1) : null;
				} finally {
					generatedKeys.close();
				}
			} finally {
				call.close();
			}
		} finally {
			conn.close();
		}
	}

	// Description Here:
	// _____________________________________________________
	public synchronized final Object executeNonQueryAndScopeIdentity(String command, List<Object> paramList) throws SQLException {
		final Connection conn = this.driver.connect(this.url, this.properties);
		try {
			final CallableStatement call = conn.prepareCall(command, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			try {
				final int pCount = paramList.size();
				for (int i = 0; i < pCount; i++)
					call.setObject(i + 1, paramList.get(i));
				call.executeUpdate();
				final ResultSet generatedKeys = call.getGeneratedKeys();
				try {
					return generatedKeys.next() ? generatedKeys.getObject(1) : null;
				} finally {
					generatedKeys.close();
				}
			} finally {
				call.close();
			}
		} finally {
			conn.close();
		}
	}

	// Description Here:
	// _____________________________________________________
	public synchronized final <T> T executeQuery(String command, Adapter<ResultSet, T> adapter) throws SQLException {
		final Connection conn = this.driver.connect(this.url, this.properties);
		try {
			final Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			try {
				final ResultSet executeQuery = stmt.executeQuery(command);
				try {
					return adapter.adapt(executeQuery);
				} finally {
					executeQuery.close();
				}
			} finally {
				stmt.close();
			}
		} finally {
			conn.close();
		}
	}

	// Description Here:
	// _____________________________________________________
	public synchronized final <T> T executeQuery(String command, Adapter<ResultSet, T> adapter, Object... parameters) throws SQLException {
		final Connection conn = this.driver.connect(this.url, this.properties);
		try {
			final CallableStatement call = conn.prepareCall(command, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			try {
				for (int i = 0; i < parameters.length; i++)
					call.setObject(i + 1, parameters[i]);
				final ResultSet executeQuery = call.executeQuery();
				try {
					return adapter.adapt(executeQuery);
				} finally {
					executeQuery.close();
				}
			} finally {
				call.close();
			}
		} finally {
			conn.close();
		}
	}

	// Description Here:
	// _____________________________________________________
	public synchronized final <T> T executeQuery(String command, Adapter<ResultSet, T> adapter, List<Object> paramList) throws SQLException {
		final Connection conn = this.driver.connect(this.url, this.properties);
		try {
			final CallableStatement call = conn.prepareCall(command, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			try {
				final int pCount = paramList.size();
				for (int i = 0; i < pCount; i++)
					call.setObject(i + 1, paramList.get(i));
				final ResultSet executeQuery = call.executeQuery(command);
				try {
					return adapter.adapt(executeQuery);
				} finally {
					executeQuery.close();
				}
			} finally {
				call.close();
			}
		} finally {
			conn.close();
		}
	}
}
