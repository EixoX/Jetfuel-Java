import java.util.HashMap;

import com.eixox.data.database.PgSqlDialect;
import com.eixox.data.database.SqlClassStorage;

public class PgSqlTestStorage<T> extends SqlClassStorage<T> {

	private PgSqlTestStorage(Class<T> claz) {
		super(claz, PgSqlDialect.Instance, "jdbc:postgresql://localhost/test", "postgres", "popopo6");
	}

	private static final HashMap<Class<?>, PgSqlTestStorage<?>> instances = new HashMap<Class<?>, PgSqlTestStorage<?>>();

	public static synchronized <T> PgSqlTestStorage<T> getInstance(Class<T> claz) {
		@SuppressWarnings("unchecked")
		PgSqlTestStorage<T> instance = (PgSqlTestStorage<T>) instances.get(claz);
		if (instance == null) {
			instance = new PgSqlTestStorage<T>(claz);
			instances.put(claz, instance);
		}
		return instance;
	}
}
