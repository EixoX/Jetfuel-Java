package com.eixox.data;

import java.util.Date;

import com.eixox.data.entities.Persistent;

@Persistent(name = "testentity1")
public class TestEntity1 {

	@Persistent(type = ColumnType.IDENTITY)
	public int id;
	@Persistent
	public String name;
	@Persistent(type = ColumnType.UNIQUE)
	public String email;
	@Persistent
	public Date birthDay;
	@Persistent(type = ColumnType.UNIQUE)
	public long cpf;
	@Persistent
	public Date dateCreated;
	@Persistent
	public Date dateUpdated;
}
