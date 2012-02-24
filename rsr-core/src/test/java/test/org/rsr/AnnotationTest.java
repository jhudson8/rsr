package test.org.rsr;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import junit.framework.TestCase;

import org.rsr.RestHandler;
import org.rsr.RestResponse;

public class AnnotationTest extends TestCase {

	private RestHandler handler = null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		handler = new RestHandler();
	}

	@Path("/root")
	public class Controller1 implements Serializable {
		
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

	public void testParameterTypes() throws Exception {
		Controller1 controller = new Controller1();
		handler.addController(controller);
		RestResponse response = handler.onRoute("/root/search/foo/bar", new TestContext(), "get");
		assertEquals(response.getResponse(), "foo-bar");
		response = handler.onRoute("/root/search/foo/bar", new TestContext(), "post");
		assertNotNull(response);
		
		response = handler.onRoute("/root/12343", new TestContext(), "get");
		assertEquals(response.getResponse(), "12343");
		
		response = handler.onRoute("/root", new TestContext(), "get");
		assertEquals(response.getResponse(), "get");
		
		response = handler.onRoute("/root", new TestContext(), "post");
		assertEquals(response.getResponse(), "post");
	}
}
