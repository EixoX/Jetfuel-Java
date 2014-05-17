package com.eixox.data;

import java.util.Date;

import com.eixox.database.DatabaseColumn;
import com.eixox.database.DatabaseColumnType;
import com.eixox.database.DatabaseTable;

@DatabaseTable(dataName = "testentity1")
public class TestEntity1 {

	@DatabaseColumn(type = DatabaseColumnType.IDENTITY)
	public int id;
	@DatabaseColumn
	public String name;
	@DatabaseColumn(type = DatabaseColumnType.UNIQUE)
	public String email;
	@DatabaseColumn
	public Date birthDay;
	@DatabaseColumn(type = DatabaseColumnType.UNIQUE)
	public long cpf;
	@DatabaseColumn
	public Date dateCreated;
	@DatabaseColumn
	public Date dateUpdated;
}
