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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path("/user")
public class TUserService {
	@Context
	HttpServletRequest request;

	@GET 
	@Path("/getUser")
	@Produces(MediaType.APPLICATION_JSON)
	public String getUser(@QueryParam("email") String email) {
		TLogger.logInfo("TUserService", "getUser", "Entry");
		TPath.startTPath("GetUserDetailsSvc");
		String response = null;
		String statusMsg = "SUCCESS";
		String statusCode = "0000";
		Map<String, Object> result = new HashMap<String, Object>();
		Gson gson = null;
		try{
			gson = new GsonBuilder().setPrettyPrinting().create();
			MySQLDAO dao = new MySQLDAO();
			Map<String, Object> resp = dao.executeQuery("select * from users where email='"+email+"'");
			result.put("responseresult", resp);
		}catch(Exception e){
			e.printStackTrace();
			TLogger.logError("TUserService", "getUser",  e.getMessage(), e);
			statusMsg = "FAILURE: "+e.getMessage();
			statusCode = "0006";
		}
		TPath.endTPath("GetUserDetailsSvc", "TUserService", "getUser", statusMsg, statusCode);
		String tpathlog = TPath.execTPathReport("GetUserDetailsSvc", result);
		result.put("tpath", tpathlog);
		response = gson.toJson(result);
		TLogger.logInfo("TUserService", "getUser", "Response: "+response);
		TLogger.logInfo("TUserService", "getUser", "Exit");
		return response;
	}

}
