package com.eixox.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.eixox.database.schema.SchemaDb;
import com.eixox.database.schema.SchemaDbColumn;
import com.eixox.database.schema.SchemaDbObject;
import com.eixox.database.schema.SchemaDbObjectList;
import com.eixox.database.schema.SchemaDbTable;
import com.eixox.database.schema.SchemaDbView;

public class SqlServerDatabase extends Database {

	public final SqlServerDialect dialect = new SqlServerDialect();

	public SqlServerDatabase(String url) {
		super(url);
	}

	public SqlServerDatabase(String url, Properties properties) {
		super(url, properties);
	}

	@Override
	public final String getDriverClassName() {
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		//return "net.sourceforge.jtds.jdbc.Driver";
	}

	@Override
	protected DatabaseDialect createDialect() {
		return new SqlServerDialect();
	}

	private void readSchemaTablesAndViews(final SchemaDb db) {
		DatabaseCommand cmd = new DatabaseCommand();
		cmd.text.append("SELECT");
		cmd.text.append("	so.object_id as object_id,");
		cmd.text.append("	so.name as object_name,");
		cmd.text.append("	so.create_date as date_created,");
		cmd.text.append("	so.modify_date as date_updated,");
		cmd.text.append("	sc.column_id as column_id,");
		cmd.text.append("	sc.name as column_name,");
		cmd.text.append("	st.user_type_id as type_id,");
		cmd.text.append("	st.name as type_name,");
		cmd.text.append("	sc.max_length,");
		cmd.text.append("	sc.precision,");
		cmd.text.append("	sc.scale,");
		cmd.text.append("	sc.collation_name,");
		cmd.text.append("	sc.is_nullable,");
		cmd.text.append("	sc.is_rowguidcol,");
		cmd.text.append("	sc.is_identity,");
		cmd.text.append("	sc.is_computed,");
		cmd.text.append("	so.type");
		cmd.text.append(" FROM sys.columns sc");
		cmd.text.append(" INNER JOIN sys.objects so ON so.object_id = sc.object_id");
		cmd.text.append(" INNER JOIN sys.types st ON st.user_type_id = sc.user_type_id");
		cmd.text.append(" WHERE so.type IN ('U', 'V')");
		cmd.text.append(" ORDER BY so.object_id");
		try {
			Connection conn = getConnection();
			try {
				db.name = conn.getCatalog();
				cmd.executeQuery(conn, new ResultSetProcessor<Integer>() {

					public Integer process(ResultSet rs) throws SQLException {
						int counter = 0;
						SchemaDbObject dbo = null;
						SchemaDbObjectList<SchemaDbColumn> colList = null;
						while (rs.next()) {
							Object object_id = rs.getObject(1);
							char type = rs.getString(17).charAt(0);

							if (dbo == null || !object_id.equals(dbo.id)) {
								switch (type) {
								case 'U':
									SchemaDbTable tbl = new SchemaDbTable();
									db.tables.add(tbl);
									dbo = tbl;
									colList = tbl.columns;
									break;
								case 'V':
									SchemaDbView vw = new SchemaDbView();
									db.views.add(vw);
									dbo = vw;
									colList = vw.columns;
									break;
								default:
									throw new RuntimeException("CANT READ TYPE " + type);
								}

								dbo.id = object_id;
								dbo.name = rs.getString(2);
								dbo.date_created = rs.getTimestamp(3);
								dbo.date_updated = rs.getTimestamp(4);
							}

							SchemaDbColumn column = new SchemaDbColumn();
							colList.add(column);

							column.id = rs.getObject(5);
							column.name = rs.getString(6);
							column.type_id = rs.getObject(7);
							column.type_name = rs.getString(8);
							column.max_length = rs.getInt(9);
							column.precision = rs.getInt(10);
							column.scale = rs.getInt(11);
							column.collation_name = rs.getString(12);
							column.is_nullable = rs.getBoolean(13);
							column.is_rowguidcol = rs.getBoolean(14);
							column.is_identity = rs.getBoolean(15);
							column.is_computed = rs.getBoolean(16);
							column.date_created = dbo.date_created;
							column.date_updated = dbo.date_updated;

						}
						return counter;
					}
				});
			} finally {
				conn.close();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected SchemaDb readSchema() {
		final SchemaDb db = new SchemaDb();
		readSchemaTablesAndViews(db);
		return db;

	}

}
