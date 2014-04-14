package com.eixox.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.eixox.data.DataAspect;

public final class DbEngine {

	private final String		connectionString;
	private final Properties	properties;

	// ______________________________________________________________________________
	public DbEngine(String connectionString, Properties properties) {
		this.connectionString = connectionString;
		this.properties = properties;
	}

	// ______________________________________________________________________________
	public final Connection getConnection() throws SQLException {
		return DriverManager.getConnection(connectionString, properties);
	}

	// ______________________________________________________________________________
	public final int executeNonQuery(String commandText) {
		try {
			Connection conn = getConnection();
			try {
				Statement stmt = conn.createStatement();
				try {
					return stmt.executeUpdate(commandText);
				} finally {
					stmt.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ______________________________________________________________________________
	public final Object executeNonQueryAndScopeIdentity(String commandText) {
		try {
			Connection conn = getConnection();
			try {
				Statement stmt = conn.createStatement();
				try {
					stmt.executeUpdate(commandText, Statement.RETURN_GENERATED_KEYS);
					ResultSet rs = stmt.getGeneratedKeys();
					try {
						return rs.next() ? rs.getObject(1) : null;
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ______________________________________________________________________________
	public final int executeNonQuery(String commandText, Object... parameters) {
		try {
			Connection conn = getConnection();
			try {
				PreparedStatement prepareStatement = conn.prepareStatement(commandText, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				for (int i = 0; i < parameters.length; i++)
					prepareStatement.setObject(i + 1, parameters[i]);
				try {
					return prepareStatement.executeUpdate();
				} finally {
					prepareStatement.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ______________________________________________________________________________
	public final Object executeNonQueryAndScopeIdentity(String commandText, Object... parameters) {
		try {
			Connection conn = getConnection();
			try {
				PreparedStatement prepareStatement = conn.prepareStatement(commandText, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				for (int i = 0; i < parameters.length; i++)
					prepareStatement.setObject(i + 1, parameters[i]);
				try {
					prepareStatement.executeUpdate();
					ResultSet generated = prepareStatement.getGeneratedKeys();
					try {
						return generated.next() ? generated.getObject(1) : null;
					} finally {
						generated.close();
					}

				} finally {
					prepareStatement.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ______________________________________________________________________________
	public final Object executeScalar(String commandText) {
		try {
			Connection conn = getConnection();
			try {
				Statement stmt = conn.createStatement();
				try {
					ResultSet rs = stmt.executeQuery(commandText);
					try {
						return rs.next() ? rs.getObject(1) : null;
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ______________________________________________________________________________
	public final Object executeScalar(String commandText, Object... parameters) {
		try {
			Connection conn = getConnection();
			try {
				PreparedStatement prepareStatement = conn.prepareStatement(commandText, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				for (int i = 0; i < parameters.length; i++)
					prepareStatement.setObject(i + 1, parameters[i]);
				try {
					ResultSet rs = prepareStatement.executeQuery();
					try {
						return rs.next() ? rs.getObject(1) : null;
					} finally {
						rs.close();
					}
				} finally {
					prepareStatement.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ______________________________________________________________________________
	public final <T> T executeEntity(DataAspect aspect, String commandText) {
		try {
			Connection conn = getConnection();
			try {
				Statement stmt = conn.createStatement();
				try {
					ResultSet rs = stmt.executeQuery(commandText);
					try {
						ResultSetToClassIterator<T> iter = new ResultSetToClassIterator<T>(rs, aspect);
						return iter.hasNext() ? iter.next() : null;
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ______________________________________________________________________________
	public final <T> T executeEntity(Class<T> claz, String commandText) {
		return executeEntity(DataAspect.getInstance(claz), commandText);
	}

	// ______________________________________________________________________________
	public final <T> T executeEntity(DataAspect aspect, String commandText, Object... parameters) {
		try {
			Connection conn = getConnection();
			try {
				PreparedStatement prepareStatement = conn.prepareStatement(commandText, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				for (int i = 0; i < parameters.length; i++)
					prepareStatement.setObject(i + 1, parameters[i]);
				try {
					ResultSet rs = prepareStatement.executeQuery();
					try {
						ResultSetToClassIterator<T> iter = new ResultSetToClassIterator<T>(rs, aspect);
						return iter.hasNext() ? iter.next() : null;
					} finally {
						rs.close();
					}
				} finally {
					prepareStatement.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ______________________________________________________________________________
	public final <T> T executeEntity(Class<T> claz, String commandText, Object... parameters) {
		return executeEntity(DataAspect.getInstance(claz), commandText, parameters);
	}

	// ______________________________________________________________________________
	public final <T> List<T> executeQuery(DataAspect aspect, String commandText) {
		List<T> list = new ArrayList<T>(64);
		executeQuery(list, aspect, commandText);
		return list;
	}

	// ______________________________________________________________________________
	public final <T> List<T> executeQuery(Class<T> claz, String commandText) {
		return executeQuery(DataAspect.getInstance(claz), commandText);
	}

	// ______________________________________________________________________________
	public final <T> int executeQuery(List<T> output, DataAspect aspect, String commandText) {
		try {
			Connection conn = getConnection();
			try {
				Statement stmt = conn.createStatement();
				try {
					ResultSet rs = stmt.executeQuery(commandText);
					try {
						int originalSize = output.size();
						for (T child : new ResultSetToClassIterator<T>(rs, aspect))
							output.add(child);
						return output.size() - originalSize;
					} finally {
						rs.close();
					}
				} finally {
					stmt.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	// ______________________________________________________________________________
	public final <T> List<T> executeQuery(DataAspect aspect, String commandText, Object... parameters) {
		List<T> list = new ArrayList<T>(64);
		executeQuery(list, aspect, commandText, parameters);
		return list;
	}

	// ______________________________________________________________________________
	public final <T> List<T> executeQuery(Class<T> claz, String commandText, Object... parameters) {
		return executeQuery(DataAspect.getInstance(claz), commandText, parameters);
	}

	// ______________________________________________________________________________
	public final <T> int executeQuery(List<T> output, DataAspect aspect, String commandText, Object... parameters) {
		try {
			Connection conn = getConnection();
			try {
				PreparedStatement prepareStatement = conn.prepareStatement(commandText, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				for (int i = 0; i < parameters.length; i++)
					prepareStatement.setObject(i + 1, parameters[i]);
				try {
					ResultSet rs = prepareStatement.executeQuery();
					try {
						int originalSize = output.size();
						for (T child : new ResultSetToClassIterator<T>(rs, aspect))
							output.add(child);
						return output.size() - originalSize;
					} finally {
						rs.close();
					}
				} finally {
					prepareStatement.close();
				}
			} finally {
				conn.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
