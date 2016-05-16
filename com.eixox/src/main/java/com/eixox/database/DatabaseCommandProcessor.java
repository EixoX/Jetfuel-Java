package com.eixox.database;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DatabaseCommandProcessor<T> {

	public T process(ResultSet rs) throws SQLException;
}
