import java.util.HashMap;

import com.eixox.data.database.MySqlDialect;
import com.eixox.data.database.SqlClassStorage;

public class MySqlTestStorage<T> extends SqlClassStorage<T> {

	private MySqlTestStorage(Class<T> claz) {
		super(claz, MySqlDialect.Instance, "jdbc:mysql://localhost:3306/test", "root", null);
	}

	private static final HashMap<Class<?>, MySqlTestStorage<?>> instances = new HashMap<Class<?>, MySqlTestStorage<?>>();

	public static synchronized <T> MySqlTestStorage<T> getInstance(Class<T> claz) {
		@SuppressWarnings("unchecked")
		MySqlTestStorage<T> instance = (MySqlTestStorage<T>) instances.get(claz);
		if (instance == null) {
			instance = new MySqlTestStorage<T>(claz);
			instances.put(claz, instance);
		}
		return instance;
	}
}
