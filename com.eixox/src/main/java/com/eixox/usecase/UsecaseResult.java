package com.eixox.usecase;

import com.eixox.ui.UIPresentation;

public class UsecaseResult {

	public String message;
	public UsecaseResultType resultType;
	public Object result;
	public Exception exception;
	public UIPresentation presentation;

	public static final UsecaseResult success(Object result) {
		UsecaseResult res = new UsecaseResult();
		res.result = result;
		res.resultType = UsecaseResultType.SUCCESS;
		res.message = "OK";
		return res;
	}

	public static final UsecaseResult error(String msg, Object result) {
		UsecaseResult res = new UsecaseResult();
		res.result = result;
		res.resultType = UsecaseResultType.FAILED;
		res.message = msg;
		return res;
	}

	public static final UsecaseResult exception(Exception ex) {
		UsecaseResult res = new UsecaseResult();
		res.resultType = UsecaseResultType.EXCEPTION;
		res.message = ex.getLocalizedMessage();
		res.exception = ex;
		return res;
	}

	public final int getResultInt() {

		if (resultType == null)
			return -1;
		else
			switch (resultType) {
			case EXCEPTION:
			default:
				return -1;
			case FAILED:
				return 0;
			case SUCCESS:
				return 1;
			case VALIDATION_FAILED:
				return 2;
			case HAS_WARNINGS:
				return 3;
			}
	}
}
