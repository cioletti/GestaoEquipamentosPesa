package com.gestaoequipamentos.business;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.ClienteBean;
import com.gestaoequipamentos.bean.ClienteInterBean;
import com.gestaoequipamentos.entity.PmpClienteInter;
import com.gestaoequipamentos.util.IConstantAccess;
import com.gestaoequipamentos.util.JpaUtil;

public class ClienteBusiness {

	public ClienteBean findDataCliente(String cuno){
		ResultSet rs = null;
		PreparedStatement prstmt_ = null;

		Connection con = null;

//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();
		ClienteBean bean = new ClienteBean();
		try {
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();

			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 

			String SQL = "select c.FLGDLI,c.CUNM CLCHAVE, " +
			"c.CUNO,c.CUADD2 END2, c.CUADD3 BAIRRO,c.CUCYST CID,c.CUSTE EST, trim(SUBstring(c.TELXNO,0,15)) CPF, c.ZIPCD9 CEP " +
			"FROM "+IConstantAccess.LIB_DBS+".CIPNAME0 c "+
			" where c.CUNO = '"+cuno+"'";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				if( rs.getString("FLGDLI").equals("Y")){
					bean.setMsg("O código do cliente não pode ser usuado!");
					return bean;
				}
				bean.setRAZSOC(rs.getString("CLCHAVE").trim());
				bean.setCLCHAVE(rs.getString("CUNO").trim());
				bean.setEND(rs.getString("END2").trim());
				bean.setBAIRRO(rs.getString("BAIRRO").trim());
				bean.setCID(rs.getString("CID").trim());
				bean.setEST(rs.getString("EST").trim());				
				//bean.setINSCEST(rs.getString("INSCEST").trim());
				//bean.setINSCMUN(rs.getString("INSCMUN").trim());
				//bean.setINDCONT(rs.getString("INDCONT").trim());
				bean.setEST(rs.getString("EST").trim());
				bean.setCEP(rs.getString("CEP").trim());
				bean.setCNPJ(rs.getString("CPF").trim());
				bean.setCNPJ(rs.getString("CPF").trim());
//				if(rs.getString("CONFIS").equals("J")){
//					String primeiraCasa = rs.getString("CGCNUM");
//					if(9 - primeiraCasa.length() > 0){
//						int pc = (9 - primeiraCasa.length());
//						for(int i = 0; i < pc ; i++){
//							primeiraCasa = "0"+primeiraCasa;
//						}
//					}			    	
//					String segundaCasa = rs.getString("CGCFIL");
//					if(4 - segundaCasa.length() > 0){
//						int sc = (4 - segundaCasa.length());
//						for(int i = 0; i < sc ; i++){
//							segundaCasa = "0"+segundaCasa;
//						}
//					}
//
//					String terceiraCasa = rs.getString("CGCDIG");
//					if(2 - terceiraCasa.length() > 0){
//						int tc = (2 - terceiraCasa.length());
//						for(int i = 0; i < tc ; i++){
//							terceiraCasa = "0"+terceiraCasa;
//						}
//					}
//					bean.setCNPJ(primeiraCasa+"/"+segundaCasa+"-"+terceiraCasa);
//				}else{
//					bean.setCNPJ(rs.getString("CPF").trim());
//				}
			}

	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if(con != null){
					con.close();
					prstmt_.close();
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bean;
	}
	public List<ClienteBean> findDataNomeCliente(String nomeCliente){
		ResultSet rs = null;
		PreparedStatement prstmt_ = null;

		Connection con = null;

//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();

		List<ClienteBean> clienteList = new ArrayList<ClienteBean>();
		try {
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();

			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();  

			String SQL = "select c.FLGDLI,c.CUNM CLCHAVE, " +
			"c.CUNO,c.CUADD2 END2, c.CUADD3 BAIRRO,c.CUCYST CID,c.CUSTE EST, trim(SUBstring(c.TELXNO,0,15)) CPF, c.ZIPCD9 CEP " +
			"FROM "+IConstantAccess.LIB_DBS+".CIPNAME0 c "+
			" where c.CUNM like '"+nomeCliente.toUpperCase()+"%'";
			//" and c.FLGDLI <> 'Y'";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			while(rs.next()){
				ClienteBean bean = new ClienteBean();
				bean.setRAZSOC(rs.getString("CLCHAVE").trim());
				bean.setCLCHAVE(rs.getString("CUNO").trim());
				bean.setEND(rs.getString("END2").trim());
				bean.setBAIRRO(rs.getString("BAIRRO").trim());
				bean.setCID(rs.getString("CID").trim());
				bean.setEST(rs.getString("EST").trim());				
				//bean.setINSCEST(rs.getString("INSCEST").trim());
				//bean.setINSCMUN(rs.getString("INSCMUN").trim());
				//bean.setINDCONT(rs.getString("INDCONT").trim());
				bean.setEST(rs.getString("EST").trim());
				bean.setCEP(rs.getString("CEP").trim());
				bean.setCNPJ(rs.getString("CPF").trim());
				bean.setCNPJ(rs.getString("CPF").trim());
				bean.setFLAGDELETE(rs.getString("FLGDLI").trim());
//				if(rs.getString("CONFIS").equals("J")){
//					String primeiraCasa = rs.getString("CGCNUM");
//					if(9 - primeiraCasa.length() > 0){
//						int pc = (9 - primeiraCasa.length());
//						for(int i = 0; i < pc ; i++){
//							primeiraCasa = "0"+primeiraCasa;
//						}
//					}			    	
//					String segundaCasa = rs.getString("CGCFIL");
//					if(4 - segundaCasa.length() > 0){
//						int sc = (4 - segundaCasa.length());
//						for(int i = 0; i < sc ; i++){
//							segundaCasa = "0"+segundaCasa;
//						}
//					}
//
//					String terceiraCasa = rs.getString("CGCDIG");
//					if(2 - terceiraCasa.length() > 0){
//						int tc = (2 - terceiraCasa.length());
//						for(int i = 0; i < tc ; i++){
//							terceiraCasa = "0"+terceiraCasa;
//						}
//					}
//					bean.setCNPJ(primeiraCasa+"/"+segundaCasa+"-"+terceiraCasa);
//				}else{
//					bean.setCNPJ(rs.getString("CPF").trim());
//				}
				clienteList.add(bean);
			}


	
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(con != null){
					con.close();
					prstmt_.close();
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return clienteList;
	}
	
	public Boolean verificarCodigoCliente(String codigoCliente){
		String filial = "";
		if(codigoCliente.length() == 7){
			filial = codigoCliente.substring(2,4);
		}else{
			return false;
		}
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();

			Query query = manager.createQuery("From PmpClienteInter");
			List<PmpClienteInter> clienteInters = query.getResultList();
			for (PmpClienteInter pmpClienteInter : clienteInters) {
				String codigoClienteAux = pmpClienteInter.getCustomerNum().replace("XX", filial);
				if(codigoClienteAux.equals(codigoCliente)){
					return true;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	public static void main(String[] args) {
		System.out.println("09XX465".substring(2,4));
	}


}
