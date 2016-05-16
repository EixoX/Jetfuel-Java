package com.eixox.data.text;

import com.eixox.data.Column;
import com.eixox.data.ColumnSchema;

public interface TextSchema<T extends Column> extends ColumnSchema<T> {

	public boolean ignoreBlankLines();

	public boolean ignoreCommentLines();

	public String getCommentQualifier();

	public Object[] parseRow(String line);

	public String formatRow(Object[] row);
	
	public int lineOffset();

}
