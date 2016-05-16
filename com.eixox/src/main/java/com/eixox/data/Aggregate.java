package com.eixox.data;

/*
 * Represents common database aggregates to be implemented either by the SGBD or user code.
 */
public enum Aggregate {
	NONE,
	COUNT,
	MAX,
	MIN,
	AVG,
	SUM,
	VAR
}
