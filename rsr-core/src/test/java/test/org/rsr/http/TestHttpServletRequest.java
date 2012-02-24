package test.org.rsr.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

public class TestHttpServletRequest implements HttpServletRequest {

	private String method;
	private String pathInfo;
	private Map<String, String[]> parameters = new HashMap<String, String[]>();

	public TestHttpServletRequest(String route, String method) {
		this.pathInfo = route;
		this.method = method;
	}
	
	public Object getAttribute(String name) {
		return null;
	}

	public Enumeration<String> getAttributeNames() {
		return null;
	}

	public String getCharacterEncoding() {
		return null;
	}

	public void setCharacterEncoding(String env)
			throws UnsupportedEncodingException {
	}

	public int getContentLength() {
		return 0;
	}

	public String getContentType() {
		return null;
	}

	public ServletInputStream getInputStream() throws IOException {
		return null;
	}

	public String getParameter(String name) {
		String[] rtn = parameters.get(name);
		if (null != rtn)
			return rtn[0];
		else
			return null;
	}

	public Enumeration<String> getParameterNames() {
		return null;
	}

	public String[] getParameterValues(String name) {
		return parameters.get(name);
	}

	public Map<String, String[]> getParameterMap() {
		return parameters;
	}

	public String getProtocol() {
		return null;
	}

	public String getScheme() {
		return null;
	}

	public String getServerName() {
		return null;
	}

	public int getServerPort() {
		return 0;
	}

	public BufferedReader getReader() throws IOException {
		return null;
	}

	public String getRemoteAddr() {
		return null;
	}

	public String getRemoteHost() {
		return null;
	}

	public void setAttribute(String name, Object o) {
	}

	public void removeAttribute(String name) {
	}

	public Locale getLocale() {
		return null;
	}

	public Enumeration<Locale> getLocales() {
		return null;
	}

	public boolean isSecure() {
		return false;
	}

	public RequestDispatcher getRequestDispatcher(String path) {
		return null;
	}

	public String getRealPath(String path) {
		return null;
	}

	public int getRemotePort() {
		return 0;
	}

	public String getLocalName() {
		return null;
	}

	public String getLocalAddr() {
		return null;
	}

	public int getLocalPort() {
		return 0;
	}

	public ServletContext getServletContext() {
		return null;
	}

	public AsyncContext startAsync() throws IllegalStateException {
		return null;
	}

	public AsyncContext startAsync(ServletRequest servletRequest,
			ServletResponse servletResponse) throws IllegalStateException {
		return null;
	}

	public boolean isAsyncStarted() {
		return false;
	}

	public boolean isAsyncSupported() {

		return false;
	}

	public AsyncContext getAsyncContext() {

		return null;
	}

	public DispatcherType getDispatcherType() {

		return null;
	}

	public String getAuthType() {

		return null;
	}

	public Cookie[] getCookies() {

		return null;
	}

	public long getDateHeader(String name) {

		return 0;
	}

	public String getHeader(String name) {

		return null;
	}

	public Enumeration<String> getHeaders(String name) {

		return null;
	}

	public Enumeration<String> getHeaderNames() {

		return null;
	}

	public int getIntHeader(String name) {

		return 0;
	}

	public String getMethod() {
		return method;
	}

	public String getPathInfo() {
		return pathInfo;
	}

	public String getPathTranslated() {

		return null;
	}

	public String getContextPath() {

		return null;
	}

	public String getQueryString() {

		return null;
	}

	public String getRemoteUser() {

		return null;
	}

	public boolean isUserInRole(String role) {

		return false;
	}

	public Principal getUserPrincipal() {

		return null;
	}

	public String getRequestedSessionId() {

		return null;
	}

	public String getRequestURI() {

		return null;
	}

	public StringBuffer getRequestURL() {

		return null;
	}

	public String getServletPath() {

		return null;
	}

	public HttpSession getSession(boolean create) {

		return null;
	}

	public HttpSession getSession() {

		return null;
	}

	public boolean isRequestedSessionIdValid() {

		return false;
	}

	public boolean isRequestedSessionIdFromCookie() {

		return false;
	}

	public boolean isRequestedSessionIdFromURL() {

		return false;
	}

	public boolean isRequestedSessionIdFromUrl() {

		return false;
	}

	public boolean authenticate(HttpServletResponse response)
			throws IOException, ServletException {

		return false;
	}

	public void login(String username, String password) throws ServletException {

	}

	public void logout() throws ServletException {

	}

	public Collection<Part> getParts() throws IOException, ServletException {

		return null;
	}

	public Part getPart(String name) throws IOException, ServletException {

		return null;
	}

}
