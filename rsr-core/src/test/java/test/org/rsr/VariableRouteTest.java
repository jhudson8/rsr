package test.org.rsr;

import javax.ws.rs.core.MediaType;

import junit.framework.TestCase;

import org.rsr.Executable;
import org.rsr.RestHandler;
import org.rsr.RestResponse;

public class VariableRouteTest extends TestCase {

	private RestHandler handler = null;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		handler = new RestHandler();
	}

	/**
	 * Test a route with no parameters.  Although routes can be added as {@link Executable}, they will
	 * most likely be created as annotated methods.
	 */
	public void testStaticRoute() {
		handler.addRoute("/foo/bar", new TestExecutable(), "get").setMediaType(MediaType.APPLICATION_JSON);
		RestResponse response = handler.onRoute("/foo/bar", new TestContext(), "get");
		assertNotNull(response);
		assertEquals("/foo/bar", response.getResponse().toString());
		
		response = handler.onRoute("/foo/bar", new TestContext(), "post");
		assertNull(response);
		
		response = handler.onRoute("foo/bar", new TestContext(), "get");
		assertNull(response);
	}

	/**
	 * Test a route with a single parameter
	 */
	public void testDynamicRoute() {
		handler.addRoute("/foo/:bar", new TestExecutable(), "get").setMediaType(MediaType.APPLICATION_JSON);
		RestResponse response = handler.onRoute("/foo/whatever", new TestContext(), "get");
		assertNotNull(response);
		assertEquals("/foo/whatever", response.getResponse().toString());
	}

	/**
	 * Test a complex route with multiple variable names
	 */
	public void testVarNames() {
		handler.addRoute("/foo/:bar/blabla/:another", new TestExecutable(), "get").setMediaType(MediaType.APPLICATION_JSON);
		TestContext context = new TestContext();
		RestResponse response = handler.onRoute("/foo/whatever", context, "get");
		assertNull(response);
		
		// route parameters will also be set as named values in the context
		response = handler.onRoute("/foo/whatever/blabla/123", context, "get");
		assertEquals("whatever", context.getParameter("bar"));
		assertEquals("123", context.getParameter("another"));
	}

	/**
	 * Test a wildcard parameter
	 */
	public void testWildcard() {
		TestExecutable executable = new TestExecutable();
		handler.addRoute("/foo/:bar/*splat", executable, "get").setMediaType(MediaType.APPLICATION_JSON);
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
