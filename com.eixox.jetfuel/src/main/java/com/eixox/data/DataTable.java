package com.eixox.data;

import java.util.ArrayList;

public class DataTable {

	private final ArrayList<DataColumn> columns = new ArrayList<DataColumn>();
	private final ArrayList<DataRow> rows = new ArrayList<DataRow>();
	private String caption;
	private String name;

	/**
	 * @return the caption
	 */
	public final String getCaption() {
		return caption;
	}

	/**
	 * @param caption
	 *            the caption to set
	 */
	public final void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the columns
	 */
	public final ArrayList<DataColumn> getColumns() {
		return columns;
	}

	/**
	 * @return the rows
	 */
	public final ArrayList<DataRow> getRows() {
		return rows;
	}

	public final DataRow add(Object... values) {
		DataRow row = new DataRow(this, this.columns.size());
		if (values != null && values.length > 0)
			for (int i = 0; i < values.length; i++)
				row.setValue(i, values[i]);
		this.rows.add(row);
		return row;
	}

	public String toHtmlString(String tableClass, String tableStyle) {

		StringBuilder builder = new StringBuilder(1024);
		builder.append("<table");
		builder.append(" class=\"");
		builder.append(tableClass);
		builder.append("\" style=\"");
		builder.append(tableStyle);
		builder.append("\">");

		builder.append("<thead>");
		builder.append("<tr>");
		for (DataColumn col : columns) {
			builder.append("<th>");
			builder.append(col.getCaption());
			builder.append("</th>");
		}
		builder.append("</tr>");
		builder.append("</thead>");
		builder.append("<tbody>");
		for (DataRow row : rows) {
			builder.append("<tr>");
			for (Object td : row.getData()) {
				builder.append("<td>");
				builder.append(td);
				builder.append("</td>");
			}
			builder.append("</tr>");
		}
		builder.append("</tbody>");
		builder.append("</table>");

		return builder.toString();
	}

}
