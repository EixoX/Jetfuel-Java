package com.eixox.data.text;

import com.eixox.data.Column;
import com.eixox.data.adapters.ValueAdapter;

public interface TextColumn extends Column {

	public ValueAdapter<?> getAdapter();
}
