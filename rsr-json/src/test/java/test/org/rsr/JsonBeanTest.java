package test.org.rsr;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import junit.framework.TestCase;

import org.rsr.RestHandler;
import org.rsr.RestResponse;
import org.rsr.Serializer;
import org.rsr.serializer.SimpleJsonBeanSerializer;

public class JsonBeanTest extends TestCase {

	private RestHandler handler = null;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		handler = new RestHandler();
		handler.addController(new Controller());
	}

	/**
	 * Ensure that we can map a route to a controller method which returns a bean.
	 * The SimpleJsonBeanSerializer converts the bean to JSON.
	 */
	public void testSimpleBean() throws Exception {
		RestResponse response = handler.onRoute("/person/1234/Joe/Hudson",
				new TestContext(), "get");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		response.connect(out);
		assertEquals(
				"{\"id\":1234,\"lastName\":\"Hudson\",\"firstName\":\"Joe\"}",
				new String(out.toByteArray()));
	}

	/**
	 * Ensure that we can map a route to a controller method which returns a bean.
	 * The SimpleJsonBeanSerializer converts the bean to JSON.  This additional test
	 * ensures that we can have nested beans (see the "deep" serializer annotation property
	 * in the controller method below)
	 */
	public void testNestedBean() throws Exception {
		RestResponse response = handler.onRoute("/addr/1234/123 Oak St./Seattle/Wa/12345",
				new TestContext(), "get");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		response.connect(out);
		assertEquals(
				"{\"id\":1234,\"address\":{\"zip\":\"12345\",\"street1\":\"123 Oak St.\",\"state\":\"Wa\",\"city\":\"Seattle\"}}",
				new String(out.toByteArray()));
	}

	/**
	 * This would be the servlet in a traditional usage
	 */
	public class Controller implements Serializable {
		private static final long serialVersionUID = 1L;

		/**
		 * Take person attributes identified in the route and return a fully constructed person.
		 * Respond to the provided route and use the SimpleJsonBeanSerializer to convert to JSON
		 */
		@GET
		@Path("/person/:id/:firstName/:lastName")
		@Serializer(SimpleJsonBeanSerializer.class)
		public Person getPerson(Long id, String firstName, String lastName) {
			Person p = new Person();
			p.setId(id);
			p.setFirstName(firstName);
			p.setLastName(lastName);
			return p;
		}

		/**
		 * Take person attributes identified in the route and return a fully constructed person.
		 * Respond to the provided route and use the SimpleJsonBeanSerializer to convert to JSON
		 * Allow for a nested address with the "deep=2" serializer parameter
		 */
		@GET
		@Path("/addr/:id/:street/:city/:state/:zip")
		@Serializer(type=SimpleJsonBeanSerializer.class, properties="deep=2")
		public Person getPerson(Long id, String street, String city, String state, String zip) {
			Person p = new Person();
			p.setId(id);
			Address a = new Address();
			a.setStreet1(street);
			a.setCity(city);
			a.setState(state);
			a.setZip(zip);
			p.setAddress(a);
			return p;
		}
	}

	/**
	 * Simple person model object
	 */
	public static class Person implements Serializable {
		private static final long serialVersionUID = 1L;

		private Long id;
		private String firstName;
		private String lastName;
		private Address address;

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public Address getAddress() {
			return address;
		}

		public void setAddress(Address address) {
			this.address = address;
		}
	}

	/**
	 * Simple address model object
	 */
	public static class Address implements Serializable {
		private static final long serialVersionUID = 1L;

		private String street1;
		private String city;
		private String state;
		private String zip;
		public String getStreet1() {
			return street1;
		}
		public void setStreet1(String street1) {
			this.street1 = street1;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getZip() {
			return zip;
		}
		public void setZip(String zip) {
			this.zip = zip;
		}
	}
}
