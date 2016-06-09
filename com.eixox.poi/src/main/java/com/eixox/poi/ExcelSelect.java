package com.eixox.poi;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.eixox.data.DataSelect;
import com.eixox.data.DataSelectResult;
import com.eixox.data.entities.EntityAspect;

public class ExcelSelect extends DataSelect {

	public ExcelSelect(String from) {
		super(from);
	}

	@Override
	public DataSelectResult toResult() {
		

		try {
			return Excel.process(new FileInputStream(new File(this.from)), new ExcelProcessor<DataSelectResult>() {

				private DataSelectResult result;

				public void init(ExcelProcessorState state) {
					this.result = new DataSelectResult(state.cols, state.rowCount);
				}

				public void process(ExcelProcessorState state) {
					if (filter == null || filter.evaluate(result.cols, state.row))
						result.rows.add(state.row);
				}

				public DataSelectResult getOutput() {
					return result;
				}
			});
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	@Override
	public long count() {
		try {
			return Excel.process(new FileInputStream(new File(this.from)), new ExcelProcessor<Long>() {

				private long counter = 0;

				public void init(ExcelProcessorState state) {
					if (filter == null) {
						this.counter = state.rowCount;
						state.cancel = true;
					}
				}

				public void process(ExcelProcessorState state) {
					if (filter == null || filter.evaluate(state.cols, state.row))
						counter++;
				}

				public Long getOutput() {
					return counter;
				}
			});
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public boolean exists() {
		return count() > 0;
	}

	@Override
	public Object getFirstMember(final String name) {
		try {
			return Excel.process(new FileInputStream(new File(this.from)), new ExcelProcessor<Object>() {

				Object first = null;

				public void init(ExcelProcessorState state) {

				}

				public void process(ExcelProcessorState state) {
					if (filter == null || filter.evaluate(state.cols, state.row)) {
						int ordinal = state.cols.indexOf(name);
						first = state.row[ordinal];
						state.cancel = true;
					}
				}

				public Object getOutput() {
					return first;
				}
			});
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<Object> getMember(final String name) {
		try {
			return Excel.process(new FileInputStream(new File(this.from)), new ExcelProcessor<List<Object>>() {

				List<Object> items;
				int ordinal = -1;

				public void init(ExcelProcessorState state) {
					items = new ArrayList<Object>(state.rowCount);
					ordinal = state.cols.indexOf(name);
				}

				public void process(ExcelProcessorState state) {
					if (filter == null || filter.evaluate(state.cols, state.row)) {
						Object val = state.row[ordinal];
						items.add(val);
					}
				}

				public List<Object> getOutput() {
					return items;
				}
			});
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public DataSelectResult getMembers(final String... names) {
		try {
			return Excel.process(new FileInputStream(new File(this.from)), new ExcelProcessor<DataSelectResult>() {

				DataSelectResult items;
				int[] ordinals;

				public void init(ExcelProcessorState state) {
					items = new DataSelectResult(state.rowCount);
					ordinals = new int[names.length];
					for (int i = 0; i < ordinals.length; i++) {
						ordinals[i] = state.cols.indexOf(names[i]);
						items.cols.add(names[i]);
						if (ordinals[i] < 0)
							throw new RuntimeException(names[i] + " was not found on the collection");
					}
				}

				public void process(ExcelProcessorState state) {
					if (filter == null || filter.evaluate(state.cols, state.row)) {
						Object[] newRow = new Object[ordinals.length];
						for (int i = 0; i < newRow.length; i++)
							newRow[i] = state.row[ordinals[i]];
						items.rows.add(newRow);
					}
				}

				public DataSelectResult getOutput() {
					return items;
				}
			});
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Object[] getFirstMembers(final String... names) {
		try {
			return Excel.process(new FileInputStream(new File(this.from)), new ExcelProcessor<Object[]>() {

				int[] ordinals;
				Object[] result;

				public void init(ExcelProcessorState state) {
					ordinals = new int[names.length];
					for (int i = 0; i < names.length; i++)
						ordinals[i] = state.cols.indexOf(names[i]);
				}

				public void process(ExcelProcessorState state) {
					if (filter == null || filter.evaluate(state.cols, state.row)) {
						result = new Object[ordinals.length];
						for (int i = 0; i < ordinals.length; i++)
							result[i] = state.row[ordinals[i]];
						state.cancel = true;
					}
				}

				public Object[] getOutput() {
					return result;
				}
			});
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public <T> long transform(final EntityAspect aspect, final List<T> list) {
		try {
			return Excel.process(new FileInputStream(new File(this.from)), new ExcelProcessor<Long>() {
				int[] ordinals;
				int initialsize;

				public void init(ExcelProcessorState state) {
					ordinals = new int[aspect.getCount()];
					initialsize = list.size();
					for (int i = 0; i < ordinals.length; i++)
						ordinals[i] = state.cols.indexOf(aspect.getColumnName(i));

				}

				@SuppressWarnings("unchecked")
				public void process(ExcelProcessorState state) {
					if (filter == null || filter.evaluate(state.cols, state.row)) {
						T entity = (T) aspect.newInstance();
						for (int i = 0; i < ordinals.length; i++)
							if (ordinals[i] >= 0) {
								Object o = state.row[ordinals[i]];
								aspect.setValue(entity, i, o);
							}
						list.add(entity);
					}
				}

				public Long getOutput() {
					return (long) (list.size() - initialsize);
				}
			});
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getEntity(final EntityAspect aspect) {
		try {
			return Excel.process(new FileInputStream(new File(this.from)), new ExcelProcessor<T>() {

				T entity;
				int[] ordinals;

				public void init(ExcelProcessorState state) {
					ordinals = new int[aspect.getCount()];
					for (int i = 0; i < ordinals.length; i++)
						ordinals[i] = state.cols.indexOf(aspect.getColumnName(i));
				}

				public void process(ExcelProcessorState state) {
					if (filter == null || filter.evaluate(state.cols, state.row)) {
						entity = (T) aspect.newInstance();
						state.cancel = true;
						for (int i = 0; i < ordinals.length; i++)
							if (ordinals[i] >= 0) {
								Object o = state.row[ordinals[i]];
								aspect.setValue(entity, i, o);
							}
					}
				}

				public T getOutput() {
					return entity;
				}
			});
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

}
