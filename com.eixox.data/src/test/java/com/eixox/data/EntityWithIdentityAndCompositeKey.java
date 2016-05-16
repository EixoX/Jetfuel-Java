package com.eixox.data;

import java.sql.Timestamp;

import com.eixox.data.entities.Persistent;

@Persistent(name = "entity_with_identity_and_composite_key")
public class EntityWithIdentityAndCompositeKey {
	@Persistent(ColumnType.IDENTITY)
	public long id;

	@Persistent
	public String col1;

	@Persistent(ColumnType.COMPOSITE_KEY)
	public int key1;

	@Persistent(ColumnType.COMPOSITE_KEY)
	public String key2;

	@Persistent
	public Timestamp created_at;
}
