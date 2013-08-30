package com.eixox.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.eixox.data.SelectResult;

public class DbStorageProcessorForColumn implements ResultsetProcessor<SelectResult<Object>> {

	private final int columnOrdinal;
	private final int pageSize;
	private final int pageOrdinal;

	public DbStorageProcessorForColumn(int ordinal, int pageSize, int pageOrdinal) {
		this.columnOrdinal = ordinal;
		this.pageSize = pageSize;
		this.pageOrdinal = pageOrdinal;
	}

	@Override
	public final SelectResult<Object> process(ResultSet resultSet) {

		SelectResult<Object> result = new SelectResult<Object>(pageSize, pageOrdinal);

		try {
			while (resultSet.next())
				result.add(resultSet.getObject(this.columnOrdinal));
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return result;
	}

}
