package com.eixox.database.schema;

public class SchemaDbColumn extends SchemaDbObject {

	public Object type_id;
	public String type_name;
	public int max_length;
	public int precision;
	public int scale;
	public String collation_name;
	public boolean is_nullable;
	public boolean is_rowguidcol;
	public boolean is_identity;
	public boolean is_computed;
	

	@Override
	public final SchemaDbObjectType getDbObjectType() {
		return SchemaDbObjectType.COLUMN;
	}
}
