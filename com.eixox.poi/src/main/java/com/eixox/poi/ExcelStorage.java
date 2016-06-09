package com.eixox.poi;

import com.eixox.data.DataDelete;
import com.eixox.data.DataInsert;
import com.eixox.data.DataSelect;
import com.eixox.data.DataUpdate;
import com.eixox.data.Storage;

public class ExcelStorage implements Storage {

	public DataSelect select(String name) {
		return new ExcelSelect(name);
	}

	public DataUpdate update(String name) {
		throw new RuntimeException("NOT IMPLEMENTED");
	}

	public DataDelete delete(String name) {
		throw new RuntimeException("NOT IMPLEMENTED");
	}

	public DataInsert insert(String name) {
		throw new RuntimeException("NOT IMPLEMENTED");
	}

}
