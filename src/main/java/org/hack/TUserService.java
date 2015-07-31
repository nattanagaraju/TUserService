package org.hack;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.hack.util.TLogger;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.view.Viewable;

@Path("/user")
public class TUserService {
	@Context
	HttpServletRequest request;

	@GET 
	@Path("/test")
	@Produces(MediaType.TEXT_HTML)
	public Viewable findById() {
		TLogger.logInfo("Rest Service called...");
		request.setAttribute("message", "I am called..");
		TLogger.reportLog("Completed..");
		return new Viewable("/helo.html");
	}
	
}
