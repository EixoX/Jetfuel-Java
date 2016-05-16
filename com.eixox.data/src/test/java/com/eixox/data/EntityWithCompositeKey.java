package com.eixox.data;

import java.sql.Timestamp;

import com.eixox.data.entities.Persistent;

@Persistent(name = "entity_with_composite_key")
public class EntityWithCompositeKey {

	@Persistent(ColumnType.COMPOSITE_KEY)
	public int key1;

	@Persistent(ColumnType.COMPOSITE_KEY)
	public String key2;

	@Persistent
	public String col3;

	@Persistent
	public Timestamp created_at;
}
