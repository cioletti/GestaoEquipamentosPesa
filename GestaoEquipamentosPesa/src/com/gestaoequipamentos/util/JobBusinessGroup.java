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

import com.gestaoequipamentos.entity.GeBgrp;

public class JobBusinessGroup implements Job {
	private String SQL_BGRP_GE = "select bgrp.BGRP from  "+IConstantAccess.LIB_DBS+".SHLBSGP1 bgrp";

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		ResultSet rs = null;
		PreparedStatement prstmt = null;

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

				prstmt = con.prepareStatement(SQL_BGRP_GE);
				rs = prstmt.executeQuery();
				//boolean isConnection = true;
				//while(isConnection){
					try {
						manager = JpaUtil.getInstance();
						manager.getTransaction().begin();
						while(rs.next()){
							try {
								Query query =  manager.createQuery("from GeBgrp where descricao =:descricao");
								query.setParameter("descricao", rs.getString("BGRP").trim());
								query.getSingleResult();
							} catch (Exception e) {
								GeBgrp bgrp = new GeBgrp();
								bgrp.setDescricao(rs.getString("BGRP").trim());
								manager.persist(bgrp);
							}
							
						}
						
						manager.getTransaction().commit();
						
						//isLoadService = false;
						EmailHelper emailHelper = new EmailHelper();
						emailHelper.sendSimpleMail("Serviço de importação de Business group executado com sucesso", "Business group", "cioletti.rodrigo@gmail.com");
						//isConnection = false;
					} catch (Exception e1) {
						EmailHelper emailHelper = new EmailHelper();
						emailHelper.sendSimpleMail("Erro ao executar a busca do Business group no DBS", "ERRO Business group", "cioletti.rodrigo@gmail.com");
					}
				//}
			}catch (Exception e) {
				e.printStackTrace();
				EmailHelper emailHelper = new EmailHelper();
				emailHelper.sendSimpleMail("Erro ao executar a recuperação do Business group no DBS JobBusinessGroup", "ERRO Business group", "cioletti.rodrigo@gmail.com");
			}finally{
				try {
					con.close();
					rs.close();
					prstmt.close();
					manager.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

	}


}
