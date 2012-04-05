package org.rsr.http;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rsr.Context;

/**
 * Execution context which encapsulates http request and response.
 * 
 * @author Joe Hudson
 */
public class HttpContext implements Context {

	private String mediaType;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private Map<String, String> routeParameters;

	/**
	 * @param req the http servlet request
	 * @param res the http servlet response
	 */
	public HttpContext (HttpServletRequest req, HttpServletResponse res) {
		this.request = req;
		this.response = res;
	}

	/**
	 * Set the route parameters internally
	 */
	public void setRouteParameters(Map<String, String> parameters) {
		this.routeParameters = parameters;
	}

	/**
	 * Return a single paramter value for a route parameter or http query parameter.  Route parameters
	 * will be evaluated first.
	 */
	public String getParameter(String name) {
		String rtn = request.getParameter(name);
		if (null == rtn) rtn = routeParameters.get(name);
		return rtn;
	}

	/**
	 * Return a list of paramter values for a route parameter or http query parameter.  Route parameters
	 * will be evaluated first.
	 */
	public String[] getParameterValues(String name) {
		return request.getParameterValues(name);
	}

	/**
	 * @param name the cookie name
	 * @return a cookie value
	 */
	public String getCookieValue(String name) {
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	/**
	 * Add a cookie
	 * @param cookie the cookie
	 */
	public void addCookie(Cookie cookie) {
		response.addCookie(cookie);
	}

	/**
	 * @return the http servlet request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @return the http servlet response
	 */
	public HttpServletResponse getResponse() {
		return response;
	}

	/**
	 * @return the media type
	 */
	public String getMediaType() {
		return mediaType;
	}

	/**
	 * Set the media type
	 * @param mediaType the media type
	 */
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
}
