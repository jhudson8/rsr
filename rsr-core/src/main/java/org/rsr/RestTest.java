package org.rsr;

import java.io.Serializable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/person/")
@Produces(MediaType.APPLICATION_JSON)
public class RestTest implements Serializable {

	@Path(":id/:value")
	@GET
	public Serializable getPerson(Context context, String id, String value) {
		return "JOE - " + id + " - " + value;
	}
}
