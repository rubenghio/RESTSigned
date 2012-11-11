package org.rest.signed;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.security.doseta.Verify;

@Path("/signed")
public class RestSignedService {
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/resource")
	public String getSigned() {
		return "Hello World";
	}

	@POST
	@Path("/post")
	@Verify
	public void secure(String example) {
		System.out.println("Example: " + example);
	}
}
