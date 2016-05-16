package com.eixox.data;

import java.sql.Timestamp;

import com.eixox.data.entities.Persistent;

@Persistent(name = "entity_with_identity")
public class EntityWithIdentity {

	@Persistent(ColumnType.IDENTITY)
	public long id;

	@Persistent
	public String col1;

	@Persistent
	public Timestamp created_at;

}
