package com.eixox.data;

public final class ClassSelectProcessor<T> implements TableRowProcessor {

	private final ClassStorage<T> aspect;
	private final SelectResult<T> result;

	public ClassSelectProcessor(ClassStorage<T> aspect, int pageSize, int pageOrdinal) {
		this.aspect = aspect;
		this.result = new SelectResult<T>(pageSize, pageOrdinal);
	}

	public final SelectResult<T> getResult() {
		return this.result;
	}

	@SuppressWarnings("unchecked")
	public final void process(Object[] columnValues) {
		try {
			T instance = (T) aspect.getType().newInstance();

			for (int i = 0; i < columnValues.length; i++) {
				if (columnValues[i] != null)
					aspect.get(i).setValue(instance, columnValues[i]);
			}

			this.result.add(instance);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
