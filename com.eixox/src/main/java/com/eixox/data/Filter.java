package com.eixox.data;

import java.util.List;

public interface Filter {

	public FilterType getFilterType();
	public boolean evaluate(List<String> cols, Object[] row);
}
