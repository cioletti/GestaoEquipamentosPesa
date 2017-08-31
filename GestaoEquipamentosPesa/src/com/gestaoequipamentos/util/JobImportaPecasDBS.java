package com.gestaoequipamentos.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.entity.GeMasterPecas;

public class JobImportaPecasDBS implements Job {

	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		PreparedStatement prstmt = null;
		Connection con = null;
		ResultSet rs = null;
		EntityManager manager = null;

		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			
			Query query = manager.createNativeQuery("DELETE FROM ge_master_pecas");
			query.executeUpdate();

			prstmt = con.prepareStatement("SELECT PO.SOS1, PO.PANO20, CF.CLSFCL, "+
										  "	PP.RPPANO, PP.QTY2,PP.RPLSOS, PP.RPPANO, PO.RLPACD"+
										  "	  FROM "+IConstantAccess.LIB_DBS+".pcppipt0 PO"+
										  "	  LEFT JOIN PESA200ARQ.UFPCLSS0 CF"+
										  "	    ON PO.PANO20 = CF.CODITM AND PO.SOS1 = CF.SOS"+
										  "	  LEFT JOIN libCOM230.PCPPRRP0 PP"+
										  "	    ON PP.PANO20 = CF.CODITM"+
										  " 	   AND PP.SOS1 = CF.SOS ");
			rs = prstmt.executeQuery();
			//System.out.println("Comecei importação");

//			EmailHelper emailHelper = new EmailHelper();
//			emailHelper.sendSimpleMail("Comecei a importar Peças para o DBS", "Importação de Peças", "rodrigo@rdrsistemas.com.br");
			while (rs.next()) {

				GeMasterPecas geMasterPecas = new GeMasterPecas();

				if (rs.getString("sos1") != null)
					geMasterPecas.setSos1(rs.getString("sos1").trim());
				if (rs.getString("pano20") != null)
					geMasterPecas.setPano20(rs.getString("pano20").trim());
				if (rs.getString("clsfcl") != null)
					geMasterPecas.setClsfcl(rs.getString("clsfcl").trim());
				if (rs.getString("rppano") != null)
					geMasterPecas.setRppano(rs.getString("rppano").trim());
				if (rs.getString("qty2") != null)
					geMasterPecas.setQty2(rs.getString("qty2").trim());
				if (rs.getString("rplsos") != null)
					geMasterPecas.setRplsos(rs.getString("rplsos").trim());
				if (rs.getString("rlpacd") != null)
					geMasterPecas.setRlpacd(rs.getString("rlpacd").trim());

				manager.persist(geMasterPecas);
			}
			manager.getTransaction().commit();			
			
			//emailHelper.sendSimpleMail("Peças importadas com sucesso do DBS", "Importação de Peças", "rodrigo@rdrsistemas.com.br");
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			EmailHelper emailHelper = new EmailHelper();
			StringWriter writer = new StringWriter();
			e.printStackTrace(new PrintWriter(writer));
			emailHelper.sendSimpleMail("Erro ao executar importação de peças no DBS "+writer.toString(), "ERRO Importação de Peças", "rodrigo@rdrsistemas.com.br");
			e.printStackTrace();
		} finally {
			try {
				if(manager != null && manager.isOpen()){
					manager.close();
				}
				if (con != null) {
					con.close();
					prstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
