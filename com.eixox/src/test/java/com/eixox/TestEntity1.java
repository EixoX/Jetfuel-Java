package com.eixox;

import java.util.Date;

import com.eixox.data.Column;
import com.eixox.data.ColumnType;
import com.eixox.data.Table;

@Table(dataName = "testentity1")
public class TestEntity1 {

	@Column(type = ColumnType.IDENTITY)
	public int		id;
	@Column
	public String	name;
	@Column(type = ColumnType.UNIQUE)
	public String	email;
	@Column
	public Date		birthDay;
	@Column(type = ColumnType.UNIQUE)
	public long		cpf;
	@Column
	public Date		dateCreated;
	@Column
	public Date		dateUpdated;
}
