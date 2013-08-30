package com.eixox.database;

import java.sql.ResultSet;

public interface ResultsetProcessor<T> {

	public T process(ResultSet resultSet);
}
