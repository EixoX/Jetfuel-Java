package com.eixox.database;

public class PgSqlDialect extends DatabaseDialect {

	@Override
	protected void appendDataName(StringBuilder builder, String dataName) {
		builder.append('"');
		builder.append(dataName);
		builder.append('"');
	}

	@Override
	protected void appendPage(StringBuilder builder, int pageSize, int pageOrdinal) {
		if (pageOrdinal > 0) {
			builder.append(" LIMIT ");
			builder.append(pageSize);
			builder.append(" OFFSET ");
			builder.append(pageSize * pageOrdinal);
		} else if (pageSize > 0) {
			builder.append(" LIMIT ");
			builder.append(pageSize);
		}
	}
}
