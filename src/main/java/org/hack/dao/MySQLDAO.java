package org.hack.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MySQLDAO {
	public Map<String, Object> executeQuery(String query){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Connection connection = getCludConnection();
			Statement st = connection.createStatement();
			query = query.trim().toLowerCase();
			System.out.println("Query: "+query);
			result.put("query", query);
			result.put("connectionStatus", "Connection Retrieved successfully");
			if(query.startsWith("select") || query.startsWith("show")){
				ResultSet rs = st.executeQuery(query);
				result.put("queryExecutionStatus", "success");
				ResultSetMetaData meta = rs.getMetaData();
				int colsCount = meta.getColumnCount();
				ArrayList<String> colList = new ArrayList<String>();
				HashMap<String, Object> row = null;
				ArrayList<HashMap<String, Object>> rowList = new ArrayList<HashMap<String, Object>>();
				for(int i=1; i<colsCount; i++){
					colList.add(meta.getColumnName(i));
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
            System.out.println("Error");
            System.out.println(e);
            e.printStackTrace();
            result.put("queryExecutionStatus", "failue");
            result.put("message", e.getMessage());
        }
		return result;
	}
	
	public Connection getConnection() {
        String url = "jdbc:mysql://localhost:3306/tpath";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,"root","root");
            System.out.println("DB Connection retrieved successfully..");
            return connection;
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e);
            e.printStackTrace();
        }
        return null;
    }
	
    public Connection getCludConnection() {
        String url = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net:3306/ad_4da09c6f1711355";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,"b1ca12e92f18fc","c2d65a2b");
            System.out.println("DB Connection retrieved successfully..");
            return connection;
        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e);
            e.printStackTrace();
        }
        return null;
    }
}
