package com.eixox.web;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewHandler {

	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private String errorMessage;
	private String successMessage;
	private String warningMessage;
	private Exception lastException;

	protected ViewHandler(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public static ViewHandler getInstance(HttpServletRequest request, HttpServletResponse response) {
		ViewHandler handler = (ViewHandler) request.getAttribute("ViewHandler");
		if (handler == null) {
			handler = new ViewHandler(request, response);
			request.setAttribute("ViewHandler", handler);
		}
		return handler;
	}

	public final Cookie getCookie(String name) {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++)
			if (name.equalsIgnoreCase(cookies[i].getName()))
				return cookies[i];
		return null;
	}

	public final Cookie setCookie(String name, String value) {
		Cookie cookie = getCookie(name);
		if (cookie == null) {
			cookie = new Cookie(name, value);
			cookie.setPath("/");
			cookie.setMaxAge(946080000);
		} else {
			cookie.setValue(value);
			cookie.setPath("/");
			cookie.setMaxAge(946080000);
		}
		response.addCookie(cookie);
		return cookie;
	}

	public final String getErrorMessage() {
		return errorMessage;
	}

	public final void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public final String getSuccessMessage() {
		return successMessage;
	}

	public final void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	public final String getWarningMessage() {
		return warningMessage;
	}

	public final void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

	public final Exception getLastException() {
		return lastException;
	}

	public final void setLastException(Exception lastException) {
		this.lastException = lastException;
	}

	public final HttpServletRequest getRequest() {
		return request;
	}

	public final HttpServletResponse getResponse() {
		return response;
	}

	public final String encodeUrl(String url) {
		try {
			return URLEncoder.encode(url.replace("~", request.getContextPath()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return url.replace("~", request.getContextPath());
		}

	}

}
