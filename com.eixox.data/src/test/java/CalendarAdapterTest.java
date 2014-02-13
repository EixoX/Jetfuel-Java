import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;

import com.eixox.data.ValueAdapter;
import com.eixox.data.ValueAdapters;

public class CalendarAdapterTest {

	@Test
	public void testSqlFormat() {
		Calendar calendar = Calendar.getInstance();

		@SuppressWarnings("unchecked")
		ValueAdapter<Calendar> adapter = (ValueAdapter<Calendar>) ValueAdapters.getAdapter(Calendar.class);

		String sqlFormatted = adapter.formatSql(calendar);

		System.out.println(sqlFormatted);

		Assert.assertNotNull(sqlFormatted);

	}
}
