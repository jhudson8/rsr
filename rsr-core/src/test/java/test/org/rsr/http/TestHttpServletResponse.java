package test.org.rsr.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class TestHttpServletResponse implements HttpServletResponse {

	private ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	
	public String getTestResponse() {
		return new String(outputStream.toByteArray());
	}
	
	public String getCharacterEncoding() {

		return null;
	}

	public String getContentType() {

		return null;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		return new ServletOutputStream() {
			
			@Override
			public void write(int b) throws IOException {
				outputStream.write(b);
			}
		};
	}

	public PrintWriter getWriter() throws IOException {

		return null;
	}

	public void setCharacterEncoding(String charset) {


	}

	public void setContentLength(int len) {


	}

	public void setContentType(String type) {


	}

	public void setBufferSize(int size) {


	}

	public int getBufferSize() {

		return 0;
	}

	public void flushBuffer() throws IOException {


	}

	public void resetBuffer() {


	}

	public boolean isCommitted() {

		return false;
	}

	public void reset() {


	}

	public void setLocale(Locale loc) {


	}

	public Locale getLocale() {

		return null;
	}

	public void addCookie(Cookie cookie) {


	}

	public boolean containsHeader(String name) {

		return false;
	}

	public String encodeURL(String url) {

		return null;
	}

	public String encodeRedirectURL(String url) {

		return null;
	}

	public String encodeUrl(String url) {

		return null;
	}

	public String encodeRedirectUrl(String url) {

		return null;
	}

	public void sendError(int sc, String msg) throws IOException {


	}

	public void sendError(int sc) throws IOException {


	}

	public void sendRedirect(String location) throws IOException {


	}

	public void setDateHeader(String name, long date) {


	}

	public void addDateHeader(String name, long date) {


	}

	public void setHeader(String name, String value) {


	}

	public void addHeader(String name, String value) {


	}

	public void setIntHeader(String name, int value) {


	}

	public void addIntHeader(String name, int value) {


	}

	public void setStatus(int sc) {


	}

	public void setStatus(int sc, String sm) {


	}

	public int getStatus() {

		return 0;
	}

	public String getHeader(String name) {

		return null;
	}

	public Collection<String> getHeaders(String name) {

		return null;
	}

	public Collection<String> getHeaderNames() {

		return null;
	}

}
