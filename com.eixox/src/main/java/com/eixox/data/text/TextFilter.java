package com.eixox.data.text;

import com.eixox.data.Filter;

public interface TextFilter extends Filter {

	public boolean filter(Object[] row);

}
