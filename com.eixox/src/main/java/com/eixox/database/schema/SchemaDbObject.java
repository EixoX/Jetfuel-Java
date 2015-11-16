package com.eixox.database.schema;

import java.sql.Timestamp;

public abstract class SchemaDbObject {

	public Object id;
	public String name;
	public Timestamp date_created;
	public Timestamp date_updated;

	public abstract SchemaDbObjectType getDbObjectType();

	@Override
	public String toString() {
		return this.name == null ? super.toString() : this.name;
	}
}
