package com.eixox.database;

import java.util.Properties;

import com.eixox.data.entities.EntityAspect;

public class PostgresDatabase extends Database {

	public PostgresDatabase(String url) {
		super("org.postgresql.Driver", url);
	}

	public PostgresDatabase(String url, Properties properties) {
		super("org.postgresql.Driver", url, properties);
	}

	@Override
	protected void appendName(StringBuilder builder, String dataName) {
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
	
	@Override
	protected void appendScopeIdentity(DatabaseCommand cmd, EntityAspect aspect) {

	}
}
