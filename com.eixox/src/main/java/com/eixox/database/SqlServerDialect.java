package com.eixox.database;

public class SqlServerDialect extends DatabaseDialect {

	public SqlServerDialect() {
		super('"', '"');
	}

	@Override
	protected void prependPage(DatabaseCommand command, int pageSize, int pageOrdinal) {
		command.text.append(" TOP ");
		command.text.append(pageSize * (pageOrdinal + 1));
		command.text.append(" ");
	}

}
