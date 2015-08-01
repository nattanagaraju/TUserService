package org.hack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.hack.dao.MySQLDAO;
import org.hack.util.TLogger;

import com.sun.jersey.api.view.Viewable;

@Path("/db")
public class DBService {
	@Context
	HttpServletRequest request;

	@GET 
	@Produces(MediaType.TEXT_HTML)
	public Viewable getDBPage() {
		TLogger.logInfo("Rest Service called...");
		TLogger.reportLog("Completed..");
		return new Viewable("/db.jsp");
	}
	
	@GET
	@Path("/submitQuery")
	@Produces(MediaType.TEXT_HTML)
	public Viewable submitQuery(@QueryParam("query") String query) {
		TLogger.logInfo("Rest Service called...");
		Map<String, Object> result = null;
		if(query != null && query.trim().length() > 0){
			MySQLDAO dao = new MySQLDAO();
			result = dao.executeQuery(query);
		}else{
			result = new HashMap<String, Object>();
			result.put("message", "Enter valid SQL query to execute.");
			result.put("queryValid", false);
		}
		request.setAttribute("query", query);
		request.setAttribute("response", result);
		TLogger.reportLog("Completed..");
		return new Viewable("/db.jsp");
	}
}
