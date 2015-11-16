package com.eixox.database.schema;

public class SchemaDbTable extends SchemaDbObject {

	public final SchemaDbObjectList<SchemaDbColumn> columns = new SchemaDbObjectList<SchemaDbColumn>();

	@Override
	public final SchemaDbObjectType getDbObjectType() {
		return SchemaDbObjectType.TABLE;
	}
}
