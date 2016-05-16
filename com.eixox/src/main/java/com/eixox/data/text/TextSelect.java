package com.eixox.data.text;

import java.io.Reader;
import java.util.List;

import com.eixox.data.Select;
import com.eixox.data.SelectResult;
import com.eixox.data.entities.EntityAspect;
import com.eixox.data.entities.EntityAspectMember;

public class TextSelect extends Select {

	public final TextSchema<?> schema;
	public final Reader input;

	public TextSelect(Reader input, TextSchema<?> schema) {
		this.schema = schema;
		this.input = input;
	}

	@Override
	public SelectResult toResult() {
		try {
			TextDataReader reader = new TextDataReader(input, this.schema);
			try {
				TextFilterExpression txtFilter = (this.filter == null || this.filter.first == null)
						? null
						: new TextFilterExpression(filter, schema);

				SelectResult result = new SelectResult(columns, 10);
				while (reader.read(txtFilter)) {
					Object[] row = new Object[columns.size()];
					for (int i = 0; i < row.length; i++) {
						int ordinal = this.schema.getOrdinal(columns.get(i));
						row[i] = reader.row[ordinal];
					}
					result.rows.add(row);
				}
				return result;
			} finally {
				reader.close();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public long count() {
		try {
			TextDataReader reader = new TextDataReader(input, this.schema);
			try {
				long counter = 0;
				TextFilterExpression txtFilter = (this.filter == null || this.filter.first == null)
						? null
						: new TextFilterExpression(filter, schema);

				while (reader.read(txtFilter))
					counter++;
				return counter;
			} finally {
				reader.close();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean exists() {
		try {
			TextDataReader reader = new TextDataReader(input, this.schema);
			try {
				TextFilterExpression txtFilter = (this.filter == null || this.filter.first == null)
						? null
						: new TextFilterExpression(filter, schema);

				return reader.read(txtFilter);
			} finally {
				reader.close();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Object[] first() {
		try {
			TextDataReader reader = new TextDataReader(input, this.schema);
			try {
				TextFilterExpression txtFilter = (this.filter == null || this.filter.first == null)
						? null
						: new TextFilterExpression(filter, schema);

				if (!reader.read(txtFilter))
					return null;

				Object[] row = new Object[columns.size()];
				for (int i = 0; i < row.length; i++) {
					int ordinal = this.schema.getOrdinal(columns.get(i));
					row[i] = reader.row[ordinal];
				}

				return row;
			} finally {
				reader.close();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Object scalar() {
		try {
			TextDataReader reader = new TextDataReader(input, this.schema);
			try {
				TextFilterExpression txtFilter = (this.filter == null || this.filter.first == null)
						? null
						: new TextFilterExpression(filter, schema);

				if (!reader.read(txtFilter))
					return null;
				else
					return reader.get(columns.get(0));
			} finally {
				reader.close();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public <T> int transform(EntityAspect<T> aspect, List<T> target) {
		try {
			TextDataReader reader = new TextDataReader(input, this.schema);
			try {
				TextFilterExpression txtFilter = (this.filter == null || this.filter.first == null)
						? null
						: new TextFilterExpression(filter, schema);

				if (!reader.read(txtFilter))
					return 0;

				int counter = 0;
				int[] ordinals = new int[aspect.size()];
				for (int i = 0; i < ordinals.length; i++)
					ordinals[i] = schema.getOrdinal(aspect.get(i).columName);

				do {
					T entity = aspect.newInstance();
					for (int i = 0; i < ordinals.length; i++)
						if (ordinals[i] >= 0) {
							Object val = reader.row[ordinals[i]];
							if (val != null) {
								EntityAspectMember<T> member = aspect.get(i);
								member.setValue(entity, val);
							}
						}
					target.add(entity);
					counter++;
				} while (reader.read(txtFilter));
				return counter;

			} finally {
				reader.close();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public <T> T first(EntityAspect<T> aspect) {
		try {
			TextDataReader reader = new TextDataReader(input, this.schema);
			try {
				TextFilterExpression txtFilter = (this.filter == null || this.filter.first == null)
						? null
						: new TextFilterExpression(filter, schema);

				if (!reader.read(txtFilter))
					return null;

				T entity = aspect.newInstance();
				for (EntityAspectMember<T> member : aspect) {
					int ordinal = schema.getOrdinal(member.columName);
					if (ordinal >= 0) {
						Object val = reader.row[ordinal];
						if (val != null)
							member.setValue(entity, val);
					}
				}
				return entity;

			} finally {
				reader.close();
			}
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
