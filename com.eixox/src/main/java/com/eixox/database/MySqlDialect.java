package com.eixox.database;

public class MySqlDialect extends DatabaseDialect {

	public MySqlDialect() {
		super('`', '`');
	}
	
	@Override
	public final boolean supportsPaging() {
		return true;
	}

	@Override
	protected void appendPage(DatabaseCommand command, int pageSize, int pageOrdinal) {
		if (pageOrdinal > 0) {
			command.text.append(" LIMIT ");
			command.text.append(pageSize * pageOrdinal);
			command.text.append(", ");
			command.text.append(pageSize);
		} else if (pageSize > 0) {
			command.text.append(" LIMIT ");
			command.text.append(pageSize);
		}
	}
}
