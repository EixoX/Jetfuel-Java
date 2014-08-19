package com.eixox;

import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Test;

import com.eixox.data.entities.EntityStorage;

public class TestBuilderHelper {

	@Test
	public void testBuildClass() {

		StringBuilder builder = new StringBuilder();
		Class<?> claz = EntityStorage.class;

		builder.append("package com.eixox; \r\n");
		builder.append("import org.junit.Test; \r\n");
		builder.append("import " + claz.getCanonicalName() + "; \r\n");
		builder.append("public class " + claz.getSimpleName() + "Test {\r\n");
		Method[] methods = claz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			builder.append("\t@Test\r\n");
			builder.append("\tpublic void " + methods[i].getName() + "Test() {\r\n");
			builder.append("\t}\r\n");
		}
		builder.append("}\r\n");

		String file = builder.toString();
		System.out.println(file);
		Assert.assertNotNull(file);
	}
}
