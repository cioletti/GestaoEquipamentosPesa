package com.gestaoequipamentos.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConectionDbs {

	public static Connection getConnecton(){
		InputStream in = new ConectionDbs().getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();
		Connection con = null;
		int i = 0;
		while(i < 5){
			try {
				prop.load(in);
				String url = prop.getProperty("dbs.url");
				String user = prop.getProperty("dbs.user");
				String password =prop.getProperty("dbs.password");
				Class.forName(prop.getProperty("dbs.driver")).newInstance();

				//			String url = "jdbc:as400://192.168.128.146";
				//			String user = "XUPU15PSS";
				//			String password = "marcosa";
				//			Class.forName("com.ibm.as400.access.AS400JDBCDriver").newInstance();
				con = DriverManager.getConnection(url, user, password); 
				return con;
			}catch (Exception e) {
				e.printStackTrace();
			}
			i++;
		}
		return null;
	}
	
	public static void main(String[] args) {
		try {
			String url = "jdbc:as400://u17il01.acnms.com";
						String user = "notes";
						String password = "notesdom55";
						Class.forName("com.ibm.as400.access.AS400JDBCDriver").newInstance();
			Connection con = DriverManager.getConnection(url, user, password);
			con = DriverManager.getConnection(url, user, password);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}
