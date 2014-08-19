package com.eixox.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.eixox.NameValueCollection;
import com.eixox.Pair;
import com.eixox.Streams;
import com.eixox.UsecaseResult;
import com.eixox.Viewee;

public class ViewHandler implements Viewee {

	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private NameValueCollection<Object> parameters;
	private static final Charset UTF8 = Charset.forName("UTF-8");

	protected ViewHandler(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	public final boolean isMultipart() {
		return (request != null && request.getContentType() != null && request.getContentType().toLowerCase().contains("multipart/form-data"));
	}

	public final synchronized NameValueCollection<Object> getParameters() {
		try {
			if (this.parameters == null) {
				this.parameters = new NameValueCollection<Object>();
				if (this.isMultipart()) {
					for (Part part : request.getParts()) {
						String contentType = part.getContentType();
						if (contentType == null || contentType.isEmpty()) {
							String paramText = part.getSize() > 0 ? Streams.readText(part.getInputStream(), UTF8) : null;
							this.parameters.add(part.getName(), paramText);
						} else {
							this.parameters.add(part.getName(), part);
						}
					}
				} else {
					Enumeration<String> parameterNames = request.getParameterNames();
					while (parameterNames.hasMoreElements()) {
						String nextElement = parameterNames.nextElement();
						this.parameters.add(nextElement, request.getParameter(nextElement));
					}
				}
			}
			return this.parameters;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public final Object getParameter(String name) throws IllegalStateException, IOException, ServletException {
		for (Pair<String, Object> pair : this.getParameters())
			if (name.equalsIgnoreCase(pair.key))
				return pair.value;

		return null;
	}

	public final Object getAttribute(String name) {
		if (name == null || name.isEmpty())
			return null;
		else
			return this.request.getAttribute(name);
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

	public final boolean isPost() {
		return "post".equalsIgnoreCase(this.request.getMethod());
	}

	public final String getReferer() {
		return this.request == null ? null : this.request.getHeader("referer");
	}

	public final String getUserAgent() {
		return this.request.getHeader("User-Agent");
	}

	public final String getUserAddress() {
		return this.request.getRemoteAddr();
	}

	public final String getErrorMessage() {
		return (String) request.getAttribute("ErrorMessage");
	}

	public final void setErrorMessage(String errorMessage) {
		request.setAttribute("ErrorMessage", errorMessage);
	}

	public final String getSuccessMessage() {
		return (String) request.getAttribute("SuccessMessage");
	}

	public final void setSuccessMessage(String successMessage) {
		request.setAttribute("SuccessMessage", successMessage);
	}

	public final String getWarningMessage() {
		return (String) request.getAttribute("WarningMessage");
	}

	public final void setWarningMessage(String warningMessage) {
		request.setAttribute("WarningMessage", warningMessage);
	}

	public final void setResult(UsecaseResult result) {
		if (result != null) {
			request.setAttribute("UsecaseResult", result);
			switch (result.resultType) {
				case EXCEPTION:
					request.setAttribute("LastException", result.exception);
					setErrorMessage(result.message);
					break;
				case HAS_WARNINGS:
					setWarningMessage(result.message);
					break;
				case SUCESS:
					setSuccessMessage(result.message);
					break;
				default:
					if (result.message == null || result.message.isEmpty())
						result.message = "Ops. Não foi possível executar a operação";
					setErrorMessage(result.message);
					break;
			}
		}
	}

	public final Exception getLastException() {
		return (Exception) request.getAttribute("LastException");
	}

	public final void setLastException(Exception lastException) {
		request.setAttribute("LastException", lastException);
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
			throw new RuntimeException(e);
		}

	}

	public void onException(Exception e) {
		setErrorMessage(e.getLocalizedMessage());
		setLastException(e);

	}

	public final boolean hasErrorMessage() {
		String errorMessage = this.getErrorMessage();
		return errorMessage != null && !errorMessage.isEmpty();
	}

	public final boolean hasSuccessMessage() {
		String successMessage = this.getSuccessMessage();
		return successMessage != null && !successMessage.isEmpty();
	}

	public final boolean hasWarningMessage() {
		String warningMessage = this.getWarningMessage();
		return warningMessage != null && !warningMessage.isEmpty();
	}

	public final ViewHandlerState getState() {
		final ViewHandlerState vhs = new ViewHandlerState();
		vhs.setErrorMessage(getErrorMessage());
		vhs.setSuccessMessage(getSuccessMessage());
		vhs.setWarningMessage(getWarningMessage());
		return vhs;
	}
}
