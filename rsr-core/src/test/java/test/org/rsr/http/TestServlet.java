package test.org.rsr.http;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

import junit.framework.TestCase;

import org.rsr.http.HttpContext;
import org.rsr.http.RsrServlet;

public class TestServlet extends TestCase {

	@Path("/person")
	public class FooServlet extends RsrServlet {
		private static final long serialVersionUID = 1L;

		@GET
		@Path("/search/:term")
		public String searchPeopleWithContext(String term) {
			return term;
		}

		@POST
		@Path("/search2/:term")
		public String searchPeopleWithoutContext(String term, HttpContext context) {
			return term;
		}
	}

	public void testTheServlet() throws Exception {
		TestHttpServletRequest req = new TestHttpServletRequest("/person/search/foo", "get");
		TestHttpServletResponse res = new TestHttpServletResponse();
		
		FooServlet servlet = new FooServlet();
		servlet.init(null);
		servlet.service(req, res);
		String response = res.getTestResponse();
		assertEquals("foo", response);

		req = new TestHttpServletRequest("/person/search2/blab", "post");
		res = new TestHttpServletResponse();
		
		servlet = new FooServlet();
		servlet.init(null);
		servlet.service(req, res);
		response = res.getTestResponse();
		assertEquals("blab", response);
	}
}
