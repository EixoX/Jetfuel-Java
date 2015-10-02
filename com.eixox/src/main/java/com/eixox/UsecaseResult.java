package com.eixox;

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
	
	public static final UsecaseResult exception(Exception ex){
		UsecaseResult res = new UsecaseResult();
		res.resultType = UsecaseResultType.EXCEPTION;
		res.message = ex.getLocalizedMessage();
		res.exception = ex;
		return res;		
	}
}
