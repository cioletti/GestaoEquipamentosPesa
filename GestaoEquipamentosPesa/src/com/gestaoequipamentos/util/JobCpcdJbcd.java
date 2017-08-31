package com.gestaoequipamentos.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.entity.Jbcd;

public class JobCpcdJbcd implements Job{

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ResultSet rs = null;
		PreparedStatement prstmt = null;
		String SQL_JBCDDS_GE = "select jbcd.JBCD, jbcd.JBCDDS from "+IConstantAccess.LIB_DBS+".SHLJOBC0 jbcd";
		String SQL_CPTCDD_GE = "select cpcd.CPTCD, cpcd.CPTCDD from "+IConstantAccess.LIB_DBS+".SHLCMPC0 cpcd";
		EntityManager manager = null;
		Connection con = null;
			try {
//				InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//				Properties prop = new Properties();
//				prop.load(in);
//				String url = prop.getProperty("dbs.url");
//				String user = prop.getProperty("dbs.user");
//				String password =prop.getProperty("dbs.password");
//				Class.forName(prop.getProperty("dbs.driver")).newInstance();

				con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();  

				prstmt = con.prepareStatement(SQL_JBCDDS_GE);
				rs = prstmt.executeQuery();
				//boolean isConnection = true;
				//while(isConnection){
					try {
						manager = JpaUtil.getInstance();
						manager.getTransaction().begin();
						Query query = manager.createNativeQuery("delete from jbcd t"+
										" where t.id not in (select tr.id_jbcd from tr_desc_treinamento_jbcd tr)");
						query.executeUpdate();
						while(rs.next()){
							try {
								query  = manager.createQuery("from jbcd j where j.descricao ='"+rs.getString("JBCD").trim()+"'");
								Jbcd jbcd = (Jbcd)query.getSingleResult();
								jbcd.setJbcd(rs.getString("JBCD").trim());
								jbcd.setJbcdds(rs.getString("JBCDDS").trim());
								manager.merge(jbcd);
							} catch (Exception e) {
								query =  manager.createNativeQuery("INSERT INTO jbcd (id, jbcd, jbcdds) VALUES (JBCD_ID_SEQ.Nextval, '"+rs.getString("JBCD").trim()+"','"+rs.getString("JBCDDS").trim()+"')");
								query.executeUpdate();
							}
							
						}
						
						prstmt = con.prepareStatement(SQL_CPTCDD_GE);
						rs = prstmt.executeQuery();
						query = manager.createNativeQuery("delete from cptcd");
						query.executeUpdate();
						while(rs.next()){
							try {
								query =  manager.createNativeQuery("INSERT INTO cptcd (id, cptcd, cptcdd ) VALUES (cptcd_id_seq.nextval,'"+rs.getString("CPTCD").trim()+"','"+rs.getString("CPTCDD").trim().replace("'", "")+"')");
								query.executeUpdate();
							} catch (Exception e) {
								e.printStackTrace();
							}
							
						}
						
						
						manager.getTransaction().commit();
						
						//isLoadService = false;
						EmailHelper emailHelper = new EmailHelper();
						emailHelper.sendSimpleMail("Serviço de importação de CPCD e JBCD executado com sucesso", "Business group", "cioletti.rodrigo@gmail.com");
						//isConnection = false;
					} catch (Exception e1) {
						e1.printStackTrace();
						EmailHelper emailHelper = new EmailHelper();
						emailHelper.sendSimpleMail("Erro ao executar a busca do CPCD e JBCD no DBS", "ERRO Business group", "cioletti.rodrigo@gmail.com");
					}
				//}
			}catch (Exception e) {
				e.printStackTrace();
				EmailHelper emailHelper = new EmailHelper();
				emailHelper.sendSimpleMail("Erro ao executar a recuperação do Business group no DBS", "ERRO Business group", "cioletti.rodrigo@gmail.com");
			}finally{
				try {
					con.close();
					rs.close();
					prstmt.close();
					if(manager != null && manager.isOpen()){
						manager.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
	}

}
