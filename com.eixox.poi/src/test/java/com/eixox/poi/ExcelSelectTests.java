package com.eixox.poi;

import org.junit.Assert;
import org.junit.Test;

import com.eixox.data.DataSelect;
import com.eixox.data.DataSelectResult;

public class ExcelSelectTests {

	@Test
	public void testReadLocalFile() {

		ExcelStorage storage = new ExcelStorage();
		DataSelect select = storage.select("C:\\Users\\Rodrigo Portela\\Documents\\Per_2016-06-07_150748.xls");

		DataSelectResult result = select.toResult();

		int count = result.rows.size();

		Assert.assertTrue(count == 1619);
	}

	@Test
	public void testReadLocalFileFiltered() {

		ExcelStorage storage = new ExcelStorage();
		DataSelect select = storage.select("C:\\Users\\Rodrigo Portela\\Documents\\Per_2016-06-07_150748.xls");

		select.where("UF", "SP");
		DataSelectResult result = select.toResult();

		int count = result.rows.size();

		Assert.assertTrue(count == 608);
	}
	
	@Test
	public void testReadLocalFileFiltered2() {

		ExcelStorage storage = new ExcelStorage();
		DataSelect select = storage.select("C:\\Users\\Rodrigo Portela\\Documents\\Per_2016-06-07_150748.xls");

		select.where("UF", "SP").and("TP", "LO");
		DataSelectResult result = select.toResult();

		int count = result.rows.size();

		Assert.assertTrue(count == 607);
	}

	@Test
	public void testReadLocalFileCount() {

		ExcelStorage storage = new ExcelStorage();
		DataSelect select = storage.select("C:\\Users\\Rodrigo Portela\\Documents\\Per_2016-06-07_150748.xls");
		int count = (int) select.count();
		Assert.assertTrue(count == 1619);
	}
	
	@Test
	public void testReadLocalFileCount2() {

		ExcelStorage storage = new ExcelStorage();
		DataSelect select = storage.select("C:\\Users\\Rodrigo Portela\\Documents\\Per_2016-06-07_150748.xls");
		select.where("UF", "SP").and("TP", "LO");
		int count = (int) select.count();
		Assert.assertTrue(count == 607);
	}
}
