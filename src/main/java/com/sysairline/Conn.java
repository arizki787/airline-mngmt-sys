package com.sysairline;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conn {
	private static String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
	private static String USER = "sysairline";
	private static String PASS = "180204";
	
	public static Connection getConnection() throws SQLException{
		return DriverManager.getConnection(URL, USER, PASS);
	}
}
