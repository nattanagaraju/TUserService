package org.hack;

import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.hack.dao.MySQLDAO;
import org.hack.util.TLogger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.view.Viewable;

@Path("/user")
public class TUserService {
	@Context
	HttpServletRequest request;

	@GET 
	@Path("/getUser")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser(@QueryParam("email") String email) {
		String response = null;
		TLogger.logInfo("Rest Service called...");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		MySQLDAO dao = new MySQLDAO();
		Map<String, Object> resp = dao.executeQuery("select * from users where email='"+email+"'");
		response = gson.toJson(resp); 
		TLogger.reportLog("Completed.."+response);
		return response;
	}
	
}
