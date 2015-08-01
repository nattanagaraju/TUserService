package org.hack.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLDAO {
	public void insertdata(){
		try {
		connection = getConnection();
    	if(connection != null){
    		connection.close();
    	}
		} catch (Exception e) {
            System.out.println("Error");
            System.out.println(e);
            e.printStackTrace();

        }
	}
	
	private Connection connection = null;
    public Connection getConnection() {
        String url = "jdbc:mysql://us-cdbr-iron-east-02.cleardb.net:3306/ad_4da09c6f1711355";
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,"b1ca12e92f18fc","c2d65a2b");
            System.out.println("Connect success fully");
            return connection;

        } catch (Exception e) {
            System.out.println("Error");
            System.out.println(e);
            e.printStackTrace();

        }
        return null;

    }
}
