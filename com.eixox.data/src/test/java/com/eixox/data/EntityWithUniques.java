package com.eixox.data;

import java.sql.Timestamp;

import com.eixox.data.entities.Persistent;

@Persistent
public class EntityWithUniques {

	@Persistent(value = ColumnType.UNIQUE, name = "uq_col1")
	public String uqCol1;
	
	@Persistent(value = ColumnType.UNIQUE, name = "uq_col2")
	public String uqCol2;

	@Persistent
	public String col1;

	@Persistent
	public Timestamp created_at;
}
