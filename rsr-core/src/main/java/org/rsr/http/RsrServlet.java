package org.rsr.http;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rsr.RestHandler;
import org.rsr.RestResponse;

public class RsrServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RestHandler handler;

	protected Serializable getController() {
		return this;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		handler = new RestHandler();
		registerRoutes(handler);
	}

	protected void registerRoutes(RestHandler handler) {
		Serializable controller = getController();
		if (null != controller) {
			handler.addController(getController());
		}
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String route = req.getPathInfo();
		if (null == route) route = "";
		RestResponse response = handler.onRoute(
				route, new HttpContext(req, resp), req.getMethod());
		if (null == response) {
			throw new ServletException("Invalid path '" + route + "'");
		}
		String mediaType = response.getMediaType();
		if (null != mediaType) {
			resp.setContentType(mediaType);
		}
		response.connect(resp.getOutputStream());
		resp.getOutputStream().close();
	}
}
