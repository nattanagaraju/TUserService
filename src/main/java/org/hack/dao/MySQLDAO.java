package org.hack.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hack.util.EnvProperties;
import org.tlog.TLogger;
import org.tlog.TPath;

public class MySQLDAO {
	public Map<String, Object> executeQuery(String query){
		TLogger.logInfo("MySQLDAO", "executeQuery", "Entry");
		Map<String, Object> result = new HashMap<String, Object>();
		String statusMsg = "SUCCESS";
		String statusCode = "0000";
		TPath.startTPath("MySQLDAO");
		try {
			Connection connection = null;
			if("local".equals(EnvProperties.getProp("env"))){
				connection = getConnection();
			}else{
				connection = getCludConnection();
			}
			Statement st = connection.createStatement();
			TLogger.logInfo("MySQLDAO", "executeQuery", "Query: "+query);
			result.put("query", query);
			result.put("connectionStatus", "Connection Retrieved successfully");
			if(query.startsWith("select") || query.startsWith("show") || query.startsWith("desc")){
				ResultSet rs = st.executeQuery(query);
				result.put("queryExecutionStatus", "success");
				ResultSetMetaData meta = rs.getMetaData();
				int colsCount = meta.getColumnCount();
				ArrayList<String> colList = new ArrayList<String>();
				HashMap<String, Object> row = null;
				ArrayList<HashMap<String, Object>> rowList = new ArrayList<HashMap<String, Object>>();
				for(int i=1; i<=colsCount; i++){
					colList.add(meta.getColumnLabel(i));
				}
				while(rs.next()){
					row = new HashMap<String, Object>();
					rowList.add(row);
					for(String columnName: colList){
						row.put(columnName, rs.getObject(columnName));
					}
				}
				result.put("message", rs + ": Query executed successfully...");
				result.put("result", rowList);
				result.put("columnList", colList);
			}else{
				int rs = st.executeUpdate(query);
				result.put("queryExecutionStatus", "success");
				result.put("message", rs + " Rows affected with this query");
			}
			connection.close();
		} catch (Exception e) {
            e.printStackTrace();
            TLogger.logError("MySQLDAO", "executeQuery",  e.getMessage(), e);
            result.put("queryExecutionStatus", "failue");
            result.put("message", e.getMessage());
            statusMsg = "FAILURE: "+e.getMessage();
	 		statusCode = "0006";
        }
		TPath.endTPath("MySQLDAO", "MySQLDAO", "executeQuery", statusMsg, statusCode);
		TLogger.logInfo("MySQLDAO", "executeQuery", "QueryResult: "+result.toString());
		TLogger.logInfo("MySQLDAO", "executeQuery", "Exit");
		return result;
	}
	
	public Connection getConnection() {
		TLogger.logInfo("MySQLDAO", "getConnection", "Entry");
        String url = "jdbc:mysql://localhost:3306/tpath";
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,"root","root");
            TLogger.logInfo("MySQLDAO", "getConnection", "DB Connection retrieved successfully..");
        } catch (Exception e) {
            e.printStackTrace();
            TLogger.logError("MySQLDAO", "getConnection",  e.getMessage(), e);
        }
        TLogger.logInfo("MySQLDAO", "getConnection", "Exit");
        return connection;
    }
	
    public Connection getCludConnection() {
    	TLogger.logInfo("MySQLDAO", "getCludConnection", "Entry");
        String url = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net:3306/ad_4da09c6f1711355";
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,"b1ca12e92f18fc","c2d65a2b");
            TLogger.logInfo("MySQLDAO", "getCludConnection", "DB Connection retrieved successfully..");
        } catch (Exception e) {
            e.printStackTrace();
            TLogger.logError("MySQLDAO", "getCludConnection",  e.getMessage(), e);
        }
        TLogger.logInfo("MySQLDAO", "getCludConnection", "Exit");
        return connection;
    }
}
