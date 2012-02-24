package org.rsr.http;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rsr.Context;

public class HttpContext implements Context {

	private String mediaType;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String, String> routeParameters;

	public HttpContext (HttpServletRequest req, HttpServletResponse res) {
		this.request = req;
		this.response = res;
	}
	
	public void setRouteParameters(Map<String, String> parameters) {
		this.routeParameters = parameters;
	}

	public String getParameter(String name) {
		String rtn = request.getParameter(name);
		if (null == rtn) rtn = routeParameters.get(name);
		return rtn;
	}

	public String[] getParameterValues(String name) {
		return request.getParameterValues(name);
	}

	public String getCookieValue(String name) {
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	public void addCookie(Cookie cookie) {
		response.addCookie(cookie);
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
}
