package org.rsr.http;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.rsr.RsrHandler;
import org.rsr.RestResponse;

/**
 * Main class to be used for servlet-based route mappings.  You can extend this servlet and provide your own
 * annotated methods.  Alternatively, if you want IOC behavior, you can override {@link RsrServlet#getController()}
 * to return a value retrieved from an IOC container.
 * 
 * In addition, routes can be added manually by overriding {@link RsrServlet#registerRoutes(RsrHandler)} and calling
 * addRoute on {@link RsrServlet#getRsrHandler()}.
 * 
 * @author Joe Hudson
 *
 */
public class RsrServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private RsrHandler handler;

	/**
	 * @return the RSR handler
	 */
	protected RsrHandler getRsrHandler() {
		return handler;
	}

	/**
	 * @return the controller used to find annotated route handlers
	 */
	protected Serializable getController() {
		return this;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		handler = new RsrHandler();
		super.init(config);
		registerRoutes(handler);
	}

	/**
	 * Register all routes.  By default, add the controller to the {@link RsrHandler}
	 * @param handler the RSR handler
	 */
	protected void registerRoutes(RsrHandler handler) {
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
		try {
			response.connect(resp.getOutputStream());
			resp.getOutputStream().close();
		}
		catch (Exception e) {
			throw new ServletException(e);
		}
	}
}
