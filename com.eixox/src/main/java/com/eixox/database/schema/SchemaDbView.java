package com.eixox.database.schema;

public class SchemaDbView extends SchemaDbObject {

	public final SchemaDbObjectList<SchemaDbColumn> columns = new SchemaDbObjectList<SchemaDbColumn>();
	public String definition;

	@Override
	public final SchemaDbObjectType getDbObjectType() {
		return SchemaDbObjectType.VIEW;
	}

}
