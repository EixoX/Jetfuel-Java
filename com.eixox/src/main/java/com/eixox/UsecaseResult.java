package com.eixox;

import com.eixox.restrictions.RestrictionProblems;

public class UsecaseResult<T> {

	public String message;
	public UsecaseResultType resultType;
	public T result;
	public Exception exception;
	public RestrictionProblems problems;

	public UsecaseResult(UsecaseResultType resultType, String message, T result, Exception exception, RestrictionProblems problems) {
		this.resultType = resultType;
		this.message = message;
		this.result = result;
		this.exception = exception;
		this.problems = problems;
	}

}
