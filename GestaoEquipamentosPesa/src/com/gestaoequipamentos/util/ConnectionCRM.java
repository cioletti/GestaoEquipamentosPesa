package com.gestaoequipamentos.util;

import java.sql.DriverManager;
import java.sql.SQLException;



public class ConnectionCRM {
	//static String URL = "jdbc:oracle:thin:@oracle-rdr.no-ip.org:1522:marcosa";
//	static String URLRM1 = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(LOAD_BALANCE=ON)"
//	          + "(ADDRESS=(PROTOCOL=TCP)"
//	          + "(HOST=10.1.50.23)(PORT=1521))"
//	          + "(ADDRESS=(PROTOCOL=TCP)"
//	          + "(HOST=10.1.50.24)(PORT=1521)))"
//	          + "(CONNECT_DATA=(SERVICE_NAME=marcosa_taf)))";
//	static String URLRM2 = "jdbc:oracle:thin:@10.1.50.23:1521/marcosa_taf";
	static String URLRM1 = "jdbc:oracle:thin:@10.15.2.11:1521/dbx1";
	static String URLRM2 = "jdbc:oracle:thin:@10.15.2.11:1521/dbx1";
	//static String URL = "jdbc:mysql://gordiano.ddns.com.br:3306/marcosa?autoReconnect=true";
//	public static java.sql.Connection getConnection(){
//		java.sql.Connection conexao;
//		try {
//			// Carregando o Driver
//			Class.forName("oracle.jdbc.OracleDriver");
//			// Efetuando a Conexao
//			conexao = DriverManager.getConnection(URLRM1, "DS", "dsp455");
//			if(conexao == null){
//				conexao = DriverManager.getConnection(URLRM2, "DS", "dsp455");
//			}
//			conexao.setAutoCommit(false);
//			return conexao;
//		} catch (ClassNotFoundException objErroDriver) {
//			objErroDriver.printStackTrace();
//		}
//		catch (SQLException objErroConexao) {
//			objErroConexao.printStackTrace();
//		}
//		return null;
//	}
}
