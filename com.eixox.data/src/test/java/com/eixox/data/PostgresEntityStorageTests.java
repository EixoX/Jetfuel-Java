package com.eixox.data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import com.eixox.database.PostgresDatabase;
import com.eixox.database.TableStorage;

public class PostgresEntityStorageTests {

	private PostgresDatabase createDatabase()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		Properties props = new Properties();
		props.put("user", "pgsa");
		props.put("password", "s3cr3t0");
		return new PostgresDatabase("jdbc:postgresql://localhost/bovespa", props);
	}

	private TableStorage createTableStorage(String tableName)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
		return new TableStorage(createDatabase(), tableName);
	}

	@Test
	public void testSelectSimple()
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, JsonGenerationException, JsonMappingException, IOException {

		TableStorage pgdb = createTableStorage("cotacao_diaria");

		Assert.assertTrue(pgdb != null);

		Select select = pgdb.select();

		SelectResult result = select.page(100, 0).toResult();

		Assert.assertTrue(result.rows.size() == 100);

		select.offset = 0;
		select.limit = 0;

		Object first = select.addAggregate(Aggregate.SUM, "preco_maximo").first();

		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(first));
		Assert.assertNotNull(first);
	}

	@Test
	public void testSelectSimpleSum()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, JsonGenerationException, JsonMappingException, IOException {

		TableStorage pgdb = createTableStorage("cotacao_diaria");

		Select select = pgdb.select();
		Object first = select.addAggregate(Aggregate.SUM, "preco_maximo").where("codigo_negociacao", "BOVAJ58").first();
		
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(first));
		Assert.assertNotNull(first);
	}

	@Test
	public void testSelectGroupBy()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException,
			JsonGenerationException, JsonMappingException, IOException {
		TableStorage pgdb = createTableStorage("cotacao_diaria");

		Select select = pgdb.select();

		SelectResult result = select
				.addAggregate(Aggregate.MAX, "preco_maximo")
				.addAggregate(Aggregate.MIN, "preco_minimo")
				.addAggregate(Aggregate.AVG, "preco_medio")
				.addColumn("codigo_negociacao")
				.where("codigo_negociacao", "BOVAJ58")
				.toResult();

		ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(result));
		Assert.assertNotNull(result);
	}
}
