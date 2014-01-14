package com.eixox.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.lang.StringEscapeUtils;

import com.eixox.Convert;
import com.eixox.Pair;
import com.eixox.PairList;
import com.eixox.StreamHelper;
import com.eixox.interceptors.InterceptorAspect;
import com.eixox.reflection.ClassSchema;
import com.eixox.reflection.DecoratedMember;

public class ViewHandler {

	private final HttpServletRequest request;
	private final HttpServletResponse response;
	private final boolean isMultipart;
	private String errorMessage;
	private String infoMessage;
	private String successMessage;
	private PairList<String, Object> parameters;

	public ViewHandler(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.isMultipart = (request != null && request.getContentType() != null && request.getContentType().toLowerCase().contains("multipart/form-data"));
	}

	private static final Charset UTF8 = Charset.forName("UTF-8");

	public final synchronized PairList<String, Object> getParameters() throws IOException, IllegalStateException, ServletException {
		if (this.parameters == null) {
			this.parameters = new PairList<String, Object>();
			if (this.isMultipart) {
				for (Part part : request.getParts()) {
					String contentType = part.getContentType();
					if (contentType == null || contentType.isEmpty()) {
						String paramText = part.getSize() > 0 ? StreamHelper.readText(part.getInputStream(), UTF8) : null;
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
	}

	public final Object getParameter(String name) throws IllegalStateException, IOException, ServletException {
		for (Pair<String, Object> pair : this.getParameters())
			if (name.equalsIgnoreCase(pair.getKey()))
				return pair.getValue();

		return null;
	}

	public final Object getAttribute(String name) {
		if (name == null || name.isEmpty())
			return null;
		else
			return this.request.getAttribute(name);
	}

	public final String getAttributeEncoded(String name) {
		Object value = getAttribute(name);
		if (value == null)
			return "";
		else
			return this.html(value.toString());
	}

	public final Cookie getCookieObject(String name) {
		Cookie[] cookies = request.getCookies();
		for (int i = 0; cookies != null && i < cookies.length; i++)
			if (name.equalsIgnoreCase(cookies[i].getName()))
				return cookies[i];
		return null;
	}

	public final String getCookie(String name) {
		Cookie ck = getCookieObject(name);
		return ck == null ? null : ck.getValue();
	}

	public static final int SECONDS_PER_YEAR = 60 * 60 * 24 * 365;

	public final void setCookie(String name, String value) {
		Cookie ck = getCookieObject(name);
		if (ck == null) {
			ck = new Cookie(name, value);
		} else
			ck.setValue(value);

		ck.setPath("/");
		ck.setMaxAge(SECONDS_PER_YEAR);
		response.addCookie(ck);
	}

	public final String getErrorMessage() {
		return errorMessage;
	}

	public final String getInfoMessage() {
		return infoMessage;
	}

	public final String getReferer() {
		return this.request == null ? null : this.request.getHeader("referer");
	}

	public final HttpServletRequest getRequest() {
		return this.request;
	}

	public final HttpServletResponse getResponse() {
		return this.response;
	}

	public final String getSourceIP() {
		return this.request.getRemoteAddr();
	}

	public final String getSuccessMessage() {
		return successMessage;
	}

	public final Locale getLocale() {
		return this.request.getLocale();
	}

	public final String getUrl(String... queryStringParameters) {
		String scheme = request.getScheme(); // http
		String serverName = request.getServerName(); // hostname.com
		int serverPort = request.getServerPort(); // 80
		String contextPath = request.getContextPath(); // /mywebapp
		String servletPath = request.getServletPath(); // /servlet/MyServlet
		String pathInfo = request.getPathInfo(); // /a/b;c=123

		// Reconstruct original requesting URL
		StringBuffer url = new StringBuffer();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}

		url.append(contextPath).append(servletPath);

		if (pathInfo != null) {
			url.append(pathInfo);
		}

		if (queryStringParameters != null && queryStringParameters.length > 0) {
			url.append("?");
			url.append(queryStringParameters[0]);
			url.append("=");
			url.append(request.getParameter(queryStringParameters[0]));

			for (int i = 1; i < queryStringParameters.length; i++) {
				url.append("&");
				url.append(queryStringParameters[i]);
				url.append("=");
				url.append(request.getParameter(queryStringParameters[i]));
			}
		}

		return url.toString();
	}

	public final String getUserAgent() {
		return this.request.getHeader("User-Agent");
	}

	public final boolean hasErrorMessage() {
		return this.errorMessage != null && !this.errorMessage.isEmpty();
	}

	public final boolean hasInfoMessage() {
		return this.infoMessage != null && !this.infoMessage.isEmpty();
	}

	public final boolean hasSuccessMessage() {
		return this.successMessage != null && !this.successMessage.isEmpty();
	}

	public final String html(String html) {
		return StringEscapeUtils.escapeHtml(html);
	}

	public final boolean isPost() {
		return "post".equalsIgnoreCase(this.request.getMethod());
	}

	public final String getAbsoluteUrl(String relative) {
		String scheme = request.getScheme(); // http
		String serverName = request.getServerName(); // hostname.com
		int serverPort = request.getServerPort(); // 80
		String contextPath = request.getContextPath(); // /mywebapp

		// Reconstruct original requesting URL
		StringBuilder url = new StringBuilder();
		url.append(scheme).append("://").append(serverName);

		if ((serverPort != 80) && (serverPort != 443)) {
			url.append(":").append(serverPort);
		}

		url.append(relative.replace("~", contextPath));
		return url.toString();
	}

	public final String mapUrl(String url) {
		return url.replace("~", request.getContextPath());
	}

	public void onException(Exception e) {
		throw new RuntimeException(e);
	}

	public final int parse(ClassSchema schema, Object instance, Locale locale) throws ParseException, IllegalStateException, IOException, ServletException {
		int counter = 0;
		InterceptorAspect interceptors = InterceptorAspect.getInstance(schema.getType());

		for (Pair<String, Object> item : getParameters()) {
			if (item.getKey() != null && item.getValue() != null) {
				int ordinal = schema.getOrdinal(item.getKey());
				if (ordinal >= 0) {
					Object value = item.getValue();
					int interceptorOrdinal = interceptors.getOrdinal(item.getKey());
					if (interceptorOrdinal > 0) {
						value = interceptors.get(interceptorOrdinal).getInterceptors().intercept(value);
					}
					DecoratedMember decoratedMember = schema.get(ordinal);
					value = Convert.changeType(decoratedMember.getType(), value, locale);
					decoratedMember.setValue(instance, value);
					counter++;
				}
			}
		}
		return counter;
	}

	public final void persistProfilerCookieGuid(String guid) {
		setCookie("Guid", guid);
	}

	public final void redirect(String virtualUrl) throws IOException {
		virtualUrl = virtualUrl.replace("~", request.getContextPath());
		this.response.sendRedirect(virtualUrl);
	}

	public final void setAttribute(String name, Object value) {
		this.request.setAttribute(name, value);
	}

	public final void setErrorMessage(String errorMessage) {
		if (this.errorMessage == null || this.errorMessage.isEmpty())
			this.errorMessage = errorMessage;
		else
			this.errorMessage += "<br />\r\n" + errorMessage;

	}

	public final void setInfoMessage(String infoMessage) {
		if (this.infoMessage == null || this.infoMessage.isEmpty())
			this.infoMessage = infoMessage;
		else
			this.infoMessage += "<br />\r\n" + infoMessage;
	}

	public final void setSuccessMessage(String successMessage) {
		if (this.successMessage == null || this.successMessage.isEmpty())
			this.successMessage = successMessage;
		else
			this.successMessage += "<br />\r\n" + successMessage;
	}

	public final String url(String url) throws UnsupportedEncodingException {
		return URLEncoder.encode(url, "UTF-8");
	}

	public final ViewHandlerState getState() {
		ViewHandlerState state = new ViewHandlerState();
		state.setErrorMessage(this.errorMessage);
		state.setSuccessMessage(this.successMessage);
		state.setWarningMessage(this.infoMessage);
		return state;
	}

}
