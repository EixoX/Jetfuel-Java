package com.eixox.database;

import java.sql.Connection;

import com.eixox.data.Delete;
import com.eixox.data.Insert;
import com.eixox.data.Select;
import com.eixox.data.Storage;
import com.eixox.data.Update;

public class TableStorage implements Storage {

	public final Database database;
	public final String tableName;

	public TableStorage(Database database, String tableName) {
		this.database = database;
		this.tableName = tableName;
	}

	public final Select select() {
		return new DatabaseSelect(database, tableName);
	}

	public final Update update() {
		return new DatabaseUpdate(database, tableName);
	}

	public final Delete delete() {
		return new DatabaseDelete(database, tableName);
	}

	public final Insert insert() {
		return new DatabaseInsert(database, tableName);
	}

	public long bulkUpdate(Iterable<Update> updates) {
		try {
			Connection conn = this.database.createConnection();
			try {
				long l = 0L;
				for (Update u : updates) {
					l += ((DatabaseUpdate) u).execute(conn);
				}
				return l;
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public long bulkDelete(Iterable<Delete> deletes) {
		try {
			Connection conn = this.database.createConnection();
			try {
				long l = 0L;
				for (Delete d : deletes) {
					l += ((DatabaseDelete) d).execute(conn);
				}
				return l;
			} finally {
				conn.close();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
