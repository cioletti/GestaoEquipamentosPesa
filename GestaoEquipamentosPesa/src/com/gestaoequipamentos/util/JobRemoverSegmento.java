package com.gestaoequipamentos.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.poi.hslf.blip.WMF;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.entity.GeSegmento;
import com.gestaoequipamentos.entity.GeSegmentoAssociado;

public class JobRemoverSegmento implements Job{
	

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement prstmt = null;
		Statement prstmtSubTributaria = null;
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
			prstmtSubTributaria = con.createStatement();
			con.setAutoCommit(false);
			prstmt = con.prepareStatement("select trim(d.wonosm) wonosm, trim(d.wono) wono, trim(d.wosgno) wosgno, trim(d.coderr) coderr, trim(d.descerr) descerr from "+IConstantAccess.AMBIENTE_DBS+".USPDSSM0 d where d.coderr is not null and d.coderr <> '' and d.wonosm like '%O%'");
			rs = prstmt.executeQuery();
			Statement prstmtDelete = con.createStatement();
			Query query = null;
			while(rs.next()){
				String wonosm = rs.getString("wonosm").split("-")[0];
				GeSegmento geSegmento = manager.find(GeSegmento.class, Long.valueOf(wonosm));
				if(geSegmento == null){														
					prstmtDelete.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 where wonosm = '"+rs.getString("wonosm")+"'");
//					query = manager.createQuery("delete from GeSegmentoAssociado where idSegPai.id = "+geSegmento.getId());
//					query.executeUpdate();
					//manager.remove(geSegmento);
					//prstmtDelete.executeUpdate("delete from LIBU15FTP.USPIFSM0 where wonosm = '"+rs.getString("wonosm")+"' and wosgno = '"+geSegmento.getNumeroSegmento()+"'");
					continue;
				}
				if(rs.getString("coderr") != null && rs.getString("coderr").trim().equals("99")){
					geSegmento.setMsgDbs(rs.getString("descerr").trim());
					geSegmento.setCodErroDbs("00");
					//remove o segmento pois o mesmo não existe no BDS
					if(rs.getString("descerr").contains("Nao Existe no DBS")){
						query = manager.createQuery("delete from GeSegmentoAssociado where idSegPai.id = "+geSegmento.getId());
						query.executeUpdate();
						
						query = manager.createQuery("delete from GeSegmentoAssociado where idSegmento.id = "+geSegmento.getId());
						query.executeUpdate();
						manager.remove(geSegmento);
						try {
							query = manager.createNativeQuery("delete from OF_AGENDAMENTO where ID in(" +
									" select ag.ID from OF_AGENDAMENTO ag, OF_SEGMENTO seg"+
									"	where seg.ID_AGENDAMENTO = ag.ID"+
									"	and ag.NUM_OS = '"+geSegmento.getIdCheckin().getNumeroOs()+"'"+
									"	and seg.NUM_SEGMENTO = '"+geSegmento.getNumeroSegmento()+"')");
							query.executeUpdate();
						} catch (Exception e) {
							new EmailHelper().sendSimpleMail("delete from OF_AGENDAMENTO where ID in(" +
									" select ag.ID from OF_AGENDAMENTO ag, OF_SEGMENTO seg"+
									"	where seg.ID_AGENDAMENTO = ag.ID"+
									"	and ag.NUM_OS = '"+geSegmento.getIdCheckin().getNumeroOs()+"'"+
									"	and seg.NUM_SEGMENTO = '"+geSegmento.getNumeroSegmento()+"')", "ERRO AO REMOVER AGENDAMENTO SEG", "rodrigo@rdrsistemas.com.br");
						}
					}
					
				}else if(("").equals(rs.getString("coderr"))){
					geSegmento.setCodErroDbs("00");
					geSegmento.setMsgDbs("Não foi possível remover o segmento tente novamente!");
				}else{
					prstmtDelete.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 where wono = '"+geSegmento.getIdCheckin().getNumeroOs()+"' and wosgno = '"+geSegmento.getNumeroSegmento()+"'");
//					prstmtDelete.executeUpdate("delete from LIBU15FTP.USPIFSM0 where wonosm = '"+geSegmento.getIdCheckin().getId()+"-OF' and wosgno = '"+geSegmento.getNumeroSegmento()+"'");
					try {												   	
						prstmtDelete.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPOPSM0 where wonosm = '"+geSegmento.getIdCheckin().getId()+"-O' and wosgno = '"+geSegmento.getNumeroSegmento()+"'");
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(geSegmento.getIdCheckin().getPossuiSubTributaria() != null && geSegmento.getIdCheckin().getPossuiSubTributaria().equals("S")){
						geSegmento.getIdCheckin().setIsFindSubTributaria("P");
						//verifica se possui substituição tributária e reenvia a mesma para ser refeita
						
						//Inclui a OS na tabela do DBS 				
						String SQL = "insert into "+IConstantAccess.PESARDRTRIBUTACAO+".RDRWONO (WONO) values('"+geSegmento.getIdCheckin().getNumeroOs()+"')";
						prstmtSubTributaria.executeUpdate(SQL);
					}
					query = manager.createQuery("delete from GeSegmentoAssociado where idSegPai.id = "+geSegmento.getId());
					query.executeUpdate();
					
					query = manager.createQuery("delete from GeSegmentoAssociado where idSegmento.id = "+geSegmento.getId());
					query.executeUpdate();
					manager.remove(geSegmento);
					try {
						query = manager.createNativeQuery("delete from OF_AGENDAMENTO where ID in(" +
								" select ag.ID from OF_AGENDAMENTO ag, OF_SEGMENTO seg"+
								"	where seg.ID_AGENDAMENTO = ag.ID"+
								"	and ag.NUM_OS = '"+geSegmento.getIdCheckin().getNumeroOs()+"'"+
								"	and seg.NUM_SEGMENTO = '"+geSegmento.getNumeroSegmento()+"')");
						query.executeUpdate();
					} catch (Exception e) {
						new EmailHelper().sendSimpleMail("delete from OF_AGENDAMENTO where ID in(" +
								" select ag.ID from OF_AGENDAMENTO ag, OF_SEGMENTO seg"+
								"	where seg.ID_AGENDAMENTO = ag.ID"+
								"	and ag.NUM_OS = '"+geSegmento.getIdCheckin().getNumeroOs()+"'"+
								"	and seg.NUM_SEGMENTO = '"+geSegmento.getNumeroSegmento()+"')", "ERRO AO REMOVER AGENDAMENTO SEG", "rodrigo@rdrsistemas.com.br");
					}
				}
				prstmtDelete.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPDSSM0 d where TRIM(d.WONOSM) = '"+geSegmento.getId()+"-O' and d.coderr is not null and d.coderr <> ''");
			}
			manager.getTransaction().commit();
			con.commit();
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			if(con != null){
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			try {
				if(manager != null && manager.isOpen()){
					manager.close();
				}
				if(con != null){
					con.close();
					prstmtSubTributaria.close();
					prstmt.close();
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		System.out.println("WO/Seg:P003360-05 Nao Existe no DBS.".contains("Nao Existe no DBS"));
	}

}
