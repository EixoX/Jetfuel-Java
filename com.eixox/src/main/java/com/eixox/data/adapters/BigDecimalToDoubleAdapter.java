package com.eixox.data.adapters;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BigDecimalToDoubleAdapter implements ValueAdapter<BigDecimal> {

	public Class<BigDecimal> getDataType() {
		return BigDecimal.class;
	}

	public String format(BigDecimal value) {
		return value.toString();
	}

	public BigDecimal parse(String input) {
		return input == null || input.isEmpty() ? null : BigDecimal.valueOf(Double.parseDouble(input));
	}

	public void parseIntoField(String source, Field field, Object target) {
		try {
			double d = source == null || source.isEmpty() ? 0.0 : Double.parseDouble(source);
			field.setDouble(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void readIntoField(ResultSet source, int position, Field field, Object target) {
		try {
			BigDecimal bd = source.getBigDecimal(position);
			double d = bd == null ? 0.0 : bd.doubleValue();
			field.setDouble(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void readIntoField(ResultSet source, String name, Field field, Object target) {
		try {
			BigDecimal bd = source.getBigDecimal(name);
			double d = bd == null ? 0.0 : bd.doubleValue();
			field.setDouble(target, d);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void readIntoStatement(Object source, Field field, PreparedStatement target, int position) {
		try {
			double d = field.getDouble(source);
			BigDecimal bd = new BigDecimal(d);
			target.setBigDecimal(position, bd);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public void formatSql(BigDecimal source, StringBuilder target) {
		// TODO Auto-generated method stub

	}

}
