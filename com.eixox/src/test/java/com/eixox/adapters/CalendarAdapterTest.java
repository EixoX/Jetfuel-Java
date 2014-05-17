package com.eixox.adapters;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

public class CalendarAdapterTest {

	@Test
	public void testSqlFormat() {
		Calendar calendar = Calendar.getInstance();

		@SuppressWarnings("unchecked")
		ValueAdapter<Calendar> adapter = (ValueAdapter<Calendar>) ValueAdapters.getAdapter(Calendar.class);

		String sqlFormatted = adapter.formatSql(calendar, false);

		System.out.println(sqlFormatted);

		Assert.assertNotNull(sqlFormatted);

	}
}
