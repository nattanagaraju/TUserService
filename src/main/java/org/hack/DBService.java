package org.hack;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.hack.dao.MySQLDAO;
import org.tlog.TLogger;
import org.tlog.TPath;

import com.sun.jersey.api.view.Viewable;

@Path("/db")
public class DBService {
	@Context
	HttpServletRequest request;

	@GET 
	@Produces(MediaType.TEXT_HTML)
	public Viewable getDBPage() {
		TLogger.logInfo("DBService", "getDBPage", "Entry & Exit");
		return new Viewable("/db.jsp");
	}
	
	@GET
	@Path("/submitQuery")
	@Produces(MediaType.TEXT_HTML)
	public Viewable submitQuery(@QueryParam("query") String query) {
		TLogger.logInfo("DBService", "submitQuery", "Entry");
		TPath.startTPath("DBQueryExecutor");
		Map<String, Object> result = null;
		String statusMsg = "SUCCESS";
		String statusCode = "0000";
		try{
			if(query != null && query.trim().length() > 0){
				MySQLDAO dao = new MySQLDAO();
				result = dao.executeQuery(query);
			}else{
				result = new HashMap<String, Object>();
				result.put("message", "Enter valid SQL query to execute.");
				result.put("queryValid", false);
			}
		}catch(Exception e){
			 e.printStackTrace();
	         TLogger.logError("DBService", "submitQuery",  e.getMessage(), e);
	         statusMsg = "FAILURE: "+e.getMessage();
	 		 statusCode = "0006";
		}
		request.setAttribute("query", query);
		request.setAttribute("response", result);
		TPath.endTPath("DBQueryExecutor", "DBService", "submitQuery", statusMsg, statusCode);
		TPath.execTPathReport("DBQueryExecutor", result);
		TLogger.logInfo("DBService", "submitQuery", "Exit");
		return new Viewable("/db.jsp");
	}
}
