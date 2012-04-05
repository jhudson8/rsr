package test.org.rsr;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import junit.framework.TestCase;

import org.rsr.RsrHandler;
import org.rsr.RestResponse;
import org.rsr.http.RsrServlet;

public class AnnotationTest extends TestCase {

	private RsrHandler handler = null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		handler = new RsrHandler();
		Controller1 controller = new Controller1();
		handler.addController(controller);
	}

	/**
	 * This would be an extension of {@link RsrServlet} with normal usage
	 */
	@Path("/root") // optional prefix for all method paths
	public class Controller1 implements Serializable {
		private static final long serialVersionUID = 1L;

		@GET
		@POST
		@Path("/search/:type/:term")
		@Produces(MediaType.APPLICATION_JSON)
		public String search(String type, String term) {
			return type + "-" + term;
		}

		@GET
		@Path("/:id")
		public String getById(Long id) {
			return id.toString();
		}

		@GET
		public String noMethodPathGet() {
			return "get";
		}
		
		@POST
		public String noMethodPathPost() {
			return "post";
		}
	}

	/**
	 * Ensure that route parameters can be properly set on method executions
	 * and ensure proper method responses
	 */
	public void testParameterTypes() throws Exception {
		
		// test no route parameters with "get" type
		RestResponse response = handler.onRoute("/root", new TestContext(), "get");
		assertEquals(response.getResponse(), "get");

		// test no route parameters with "post" type
		response = handler.onRoute("/root", new TestContext(), "post");
		assertEquals(response.getResponse(), "post");
		
		// test a single route parameter
		response = handler.onRoute("/root/12343", new TestContext(), "get");
		assertEquals(response.getResponse(), "12343");

		// multiple parameters in this route
		response = handler.onRoute("/root/search/foo/bar", new TestContext(), "get");
		assertEquals(response.getResponse(), "foo-bar");

		// test that the that is also a response to a "post" type
		response = handler.onRoute("/root/search/foo/bar", new TestContext(), "post");
		assertNotNull(response);

	}
}
