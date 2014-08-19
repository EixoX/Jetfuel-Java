package com.eixox.data;

import java.util.Date;

import com.eixox.database.DatabaseColumn;
import com.eixox.database.DatabaseTable;

@DatabaseTable(tableName = "testentity1")
public class TestEntity1 {

	@DatabaseColumn(type = ColumnType.IDENTITY)
	public int id;
	@DatabaseColumn
	public String name;
	@DatabaseColumn(type = ColumnType.UNIQUE)
	public String email;
	@DatabaseColumn
	public Date birthDay;
	@DatabaseColumn(type = ColumnType.UNIQUE)
	public long cpf;
	@DatabaseColumn
	public Date dateCreated;
	@DatabaseColumn
	public Date dateUpdated;
}
