package com.eixox.database;

import java.util.Properties;

public class MySqlDatabase extends Database
{
	
	public MySqlDatabase(String url)  {
		super("com.mysql.jdbc.Driver", url);
	}
	
	public MySqlDatabase(String url, Properties properties) {
		super("com.mysql.jdbc.Driver", url, properties);
	}

	@Override
	protected void appendName(StringBuilder builder, String dataName) {
		builder.append('`');
		builder.append(dataName);
		builder.append('`');
	}

	@Override
	protected void appendPage(StringBuilder builder, int pageSize, int pageOrdinal) {
		if (pageOrdinal > 0) {
			builder.append(" LIMIT ");
			builder.append(pageSize * pageOrdinal);
			builder.append(", ");
			builder.append(pageSize);
		}
		else if (pageSize > 0)
		{
			builder.append(" LIMIT ");
			builder.append(pageSize);
		}
	}

}
