package com.eixox.database;

import java.sql.ResultSet;

import com.eixox.data.SelectResult;

public class DbStorageMemberProcessor implements ResultsetProcessor<SelectResult<Object>> {

	private final int ordinal;
	private final int pageSize;
	private final int pageOrdinal;

	public DbStorageMemberProcessor(int ordinal, int pageSize, int pageOrdinal) {
		this.ordinal = ordinal;
		this.pageSize = pageSize;
		this.pageOrdinal = pageOrdinal;
	}

	@Override
	public SelectResult<Object> process(ResultSet resultSet) {

		SelectResult<Object> result = new SelectResult<Object>(pageSize, pageOrdinal);
		try {

			while (resultSet.next()) {
				result.add(resultSet.getObject(ordinal + 1));
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

		return result;
	}

}
