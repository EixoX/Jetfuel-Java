package com.eixox.data.text;

import com.eixox.adapters.ValueAdapter;
import com.eixox.data.Column;

public interface TextColumn extends Column {

	public ValueAdapter<?> getAdapter();
}
