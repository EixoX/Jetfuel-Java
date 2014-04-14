package com.eixox.text;

import java.util.List;

import com.eixox.data.Delete;
import com.eixox.data.Filter;
import com.eixox.data.Insert;
import com.eixox.data.SortExpression;
import com.eixox.data.Storage;
import com.eixox.data.Update;

public class DelimitedTextStorage<T> extends Storage<T> {

	public DelimitedTextStorage(Class<T> claz) {
		super(claz);
	}

	@Override
	public void executeSelect(List<T> output, Filter filter, SortExpression sort, int pageSize, int pageOrdinal) {
		// TODO Auto-generated method stub

	}

	@Override
	public void executeSelectMember(int memberOrdinal, List<Object> output, Filter filter, SortExpression sort, int pageSize, int pageOrdinal) {
		// TODO Auto-generated method stub

	}

	@Override
	public long countWhere(Filter filter) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean existsWhere(Filter filter) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object readMemberWhere(int memberOrdinal, Filter filter, SortExpression sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public T readWhere(Filter filter, SortExpression sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long executeDelete(Delete delete) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long executeUpdate(Update update) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long executeInsert(Insert insert) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object executeInsertAndScopeIdentity(Insert insert) {
		// TODO Auto-generated method stub
		return null;
	}

}
