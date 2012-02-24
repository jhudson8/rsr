// Download JSR 311 API herE: http://jsr311.java.net/
package org.rsr;

import java.io.ByteArrayOutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class RestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RestHandler restHandler = new RestHandler();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void addRoutes() throws Exception {
		restHandler.addController(this);
	}

	protected RestHandler getRestHandler() {
		return restHandler;
	}
	
	public static void main(String[] args) {
		RestHandler r = new RestHandler();
		final RestTest test = new RestTest();
		r.addController(test);
		
		// r.addRoute("/person/:id/:foo", "getPerson", test, RouteType.GET);
		try {
			RestResponse rtn = r.onRoute("/person/12345/45678", null, "get");
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			rtn.writeTo(out);
			System.out.println(new String(out.toByteArray()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
