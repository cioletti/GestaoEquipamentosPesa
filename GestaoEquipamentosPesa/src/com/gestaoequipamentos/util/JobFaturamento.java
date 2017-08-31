package com.gestaoequipamentos.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.NonUniqueObjectException;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.entity.GeSituacaoOs;

public class JobFaturamento implements Job{


	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ResultSet rs = null;
		PreparedStatement prstmt = null;

		Connection con = null;
		EntityManager manager = null;
		try {
//			InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//			Properties prop = new Properties();
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();

			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
			//boolean isConnection = true;
			//while(isConnection){
			String os = "";
			try {
				manager = JpaUtil.getInstance();
				//manager.getTransaction().begin();
				String numOs = "";
				Query query =  manager.createNativeQuery("select s.numero_os from ge_situacao_os s where s.data_faturamento is null");
				List<String> list = query.getResultList();
				for (String string : list) {
					numOs += "'"+string+"',";
				}
				if(numOs.length() > 0){
					String SQL = "SELECT USPWOHD0.WONO,USPWOHD0.DTFC  FROM B108F034."+IConstantAccess.LIB_DBS+".USPWOHD0 USPWOHD0 WHERE TRIM(USPWOHD0.WONO) IN ("+numOs.substring(0,numOs.length() - 1)+") AND USPWOHD0.DTFC is not null AND TRIM(USPWOHD0.DTFC) <> '0'";
					prstmt = con.prepareStatement(SQL);
					rs = prstmt.executeQuery();
					manager.getTransaction().begin();
					while(rs.next()){
						query =  manager.createQuery("from GeSituacaoOs s where s.numeroOs =:numOs order by id");
						//System.out.println(rs.getString("WONO").trim());
						query.setParameter("numOs", rs.getString("WONO").trim());
						os = rs.getString("WONO").trim();
						try {
							if(query.getResultList().size() > 1){
								manager.remove(query.getResultList().get(0));
							}
							GeSituacaoOs situacaoOs = (GeSituacaoOs)query.getResultList().get(query.getResultList().size()-1);
							SimpleDateFormat fmt = new SimpleDateFormat(rs.getString("DTFC").trim().length() == 7?"dMMyyyy":"ddMMyyyy");
							situacaoOs.setDataFaturamento(fmt.parse(rs.getString("DTFC").trim()));
							manager.merge(situacaoOs);
							query = manager.createNativeQuery("update of_agendamento ag set ag.id_status_atividade = (select sa.id from of_status_atividade sa where sa.sigla = 'CON')"+
									" where ag.id_status_atividade in (select sa.id from of_status_atividade sa where sa.sigla <> 'CON')"+
									" and ag.num_os = '"+situacaoOs.getNumeroOs()+"'");
							query.executeUpdate();
						} catch (Exception e) {
							query = manager.createNativeQuery("   delete from  ge_situacao_os where id = (select min(id) from ge_situacao_os where numero_os = '"+os+"') ");
							query.executeUpdate();
							EmailHelper emailHelper = new EmailHelper();
							emailHelper.sendSimpleMail("Erro ao executar ao executar rotina de faturamento "+os, "ERRO Faturamento", "cioletti.rodrigo@gmail.com");
							e.printStackTrace();
						}
					}
					manager.getTransaction().commit();
				}

			} catch (Exception e1) {
				if(manager != null && manager.getTransaction().isActive()){
	        		manager.getTransaction().rollback();
	        	}
				EmailHelper emailHelper = new EmailHelper();
				emailHelper.sendSimpleMail("Erro ao executar ao executar rotina de faturamento "+os, "ERRO Faturamento", "cioletti.rodrigo@gmail.com");
				e1.printStackTrace();
			}
			//}
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
        		manager.getTransaction().rollback();
        	}
			e.printStackTrace();
			EmailHelper emailHelper = new EmailHelper();
			emailHelper.sendSimpleMail("Erro ao executar ao executar rotina de faturamento", "ERRO Faturamento", "cioletti.rodrigo@gmail.com");
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			try {
				con.close();
				rs.close();
				prstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
