package com.eixox.data;

import java.sql.Timestamp;

import com.eixox.data.entities.Persistent;

@Persistent(name = "entity_with_identity_and_uniques")
public class EntityWithIdentityAndUniques {

	@Persistent(ColumnType.IDENTITY)
	public long id;

	@Persistent
	public String col1;

	@Persistent(value = ColumnType.UNIQUE, name = "uq_col1")
	public String uq1;

	@Persistent(value = ColumnType.UNIQUE, name = "uq_col2")
	public String uq2;

	@Persistent
	public Timestamp created_at;
}
