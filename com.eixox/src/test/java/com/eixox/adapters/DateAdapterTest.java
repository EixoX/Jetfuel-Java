package com.eixox.adapters;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class DateAdapterTest {

	@Test
	public void testSqlFormat() {
		Date date = new Date();

		@SuppressWarnings("unchecked")
		ValueAdapter<Date> adapter = (ValueAdapter<Date>) ValueAdapters.getAdapter(Date.class);

		String sqlFormatted = adapter.formatSql(date, false);

		System.out.println(sqlFormatted);

		Assert.assertNotNull(sqlFormatted);

	}

}
