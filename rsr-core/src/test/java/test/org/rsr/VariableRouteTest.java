package test.org.rsr;

import javax.ws.rs.core.MediaType;

import junit.framework.TestCase;

import org.rsr.RestHandler;
import org.rsr.RestResponse;

public class VariableRouteTest extends TestCase {

	private RestHandler handler = null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		handler = new RestHandler();
	}
	
	public void testStaticRoute() {
		handler.addRoute("/foo/bar", new TestExecutable(), "get", MediaType.APPLICATION_JSON, null);
		RestResponse response = handler.onRoute("/foo/bar", new TestContext(), "get");
		assertNotNull(response);
		assertEquals("/foo/bar", response.getResponse().toString());
		response = handler.onRoute("/foo/bar", new TestContext(), "post");
		assertNull(response);
		response = handler.onRoute("foo/bar", new TestContext(), "get");
		assertNull(response);
	}

	public void testDynamicRoute() {
		handler.addRoute("/foo/:bar", new TestExecutable(), "get", MediaType.APPLICATION_JSON, null);
		RestResponse response = handler.onRoute("/foo/whatever", new TestContext(), "get");
		assertNotNull(response);
		assertEquals("/foo/whatever", response.getResponse().toString());
	}

	public void testVarNames() {
		handler.addRoute("/foo/:bar/blabla/:another", new TestExecutable(), "get", MediaType.APPLICATION_JSON, null);
		TestContext context = new TestContext();
		RestResponse response = handler.onRoute("/foo/whatever", context, "get");
		assertNull(response);
		response = handler.onRoute("/foo/whatever/blabla/123", context, "get");
		assertEquals("whatever", context.getParameter("bar"));
		assertEquals("123", context.getParameter("another"));
	}

	public void testWildcard() {
		TestExecutable executable = new TestExecutable();
		handler.addRoute("/foo/:bar/*splat", executable, "get", MediaType.APPLICATION_JSON, null);
		RestResponse response = handler.onRoute("/foo/whatever", new TestContext(), "get");
		assertNull(response);
		TestContext context = new TestContext();
		response = handler.onRoute("/foo/whatever/baz/biz", context, "get");
		assertEquals(2, executable.getParameters().length);
		assertEquals("whatever", executable.getParameters()[0]);
		assertEquals("baz/biz", executable.getParameters()[1]);
		assertEquals("whatever", context.getParameter("bar"));
		assertEquals("baz/biz", context.getParameter("splat"));
	}
}
