package com.eixox.database;

public class PostgresDialect extends DatabaseDialect {

	public PostgresDialect() {
		super('"', '"');
	}

	@Override
	protected void appendPage(DatabaseCommand command, int pageSize, int pageOrdinal) {
		if (pageOrdinal > 0) {
			command.text.append(" LIMIT ");
			command.text.append(pageSize);
			command.text.append(" OFFSET ");
			command.text.append(pageSize * pageOrdinal);
		} else if (pageSize > 0) {
			command.text.append(" LIMIT ");
			command.text.append(pageSize);
		}
	}
	
	@Override
	public final boolean supportsPaging() {
		return true;
	}
}
