package com.eixox.database.schema;

public class SchemaDb extends SchemaDbObject {

	public final SchemaDbObjectList<SchemaDbTable> tables = new SchemaDbObjectList<SchemaDbTable>();
	public final SchemaDbObjectList<SchemaDbView> views = new SchemaDbObjectList<SchemaDbView>();

	@Override
	public final SchemaDbObjectType getDbObjectType() {
		return SchemaDbObjectType.DATABASE;
	}

}
