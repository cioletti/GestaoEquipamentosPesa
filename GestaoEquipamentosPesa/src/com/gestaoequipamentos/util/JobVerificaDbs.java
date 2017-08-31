package com.gestaoequipamentos.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.entity.GeDocSegOper;
import com.gestaoequipamentos.entity.GePecas;

public class JobVerificaDbs implements Job {//Recuperar as peças do DBS
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException { 
		
        Connection con = null;
        Statement prstmt = null;
        Statement prstmtDelete = null;
        Statement prstmtPecas = null;
        EntityManager manager = null;
        ResultSet rs = null;
        ResultSet rsPecas = null;
        try {
        	con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	prstmt = con.createStatement();
        	prstmtDelete = con.createStatement();
        	prstmtPecas = con.createStatement();
        	manager = JpaUtil.getInstance();

        	String SQL = ("select * from "+IConstantAccess.AMBIENTE_DBS+".USPPWSM0 where CODERR is not null and  SUBSTRING (PEDSM ,1,1) = 'O'");
        	rs = prstmt.executeQuery(SQL);
        	while (rs.next()){		            	 
        						
        		String DESCERR = rs.getString("DESCERR").trim();
        		String CODERR = rs.getString("CODERR").trim();
        		String [] aux = rs.getString("PEDSM").trim().split("-");
        		if(aux.length > 1 && CODERR.length() > 0){
        			String PEDSM = aux[1];
        			manager.getTransaction().begin();
        			GeDocSegOper geDocSegOper = manager.find(GeDocSegOper.class, Long.valueOf(PEDSM.trim()));
        			if(geDocSegOper != null){
        				if(rs.getString("CODERR").trim().equals("99") && rs.getString("CODERR").trim().length() > 0){
        					Query query =  manager.createNativeQuery("delete from ge_pecas where ID_DOC_SEG_OPER = "+geDocSegOper.getId());
        					query.executeUpdate();
        					geDocSegOper.setMsgDbs(DESCERR.trim());
        					geDocSegOper.setCodErroDbs(CODERR.trim());
        					SQL = "select sos, pano20, qtde, CODERR, DESCERR from "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 where pedsm = '"+aux[0]+"-"+aux[1]+"'";
        					rsPecas = prstmtPecas.executeQuery(SQL);
        					while(rsPecas.next()){
        						String sos = rsPecas.getString("sos");
        						String pano20 = rsPecas.getString("pano20");
        						Integer qtde = rsPecas.getInt("qtde");
        						String CODERRPECAS = rsPecas.getString("CODERR");
        						String DESCERRPECAS = rsPecas.getString("DESCERR");
        						GePecas gePecas = new GePecas();
        						gePecas.setSos1(sos);
        						gePecas.setIdDocSegOper(geDocSegOper);
        						gePecas.setQtd(qtde.longValue());
        						gePecas.setPartNo(pano20);
        						gePecas.setCoderr(CODERRPECAS);
        						gePecas.setDescerr(DESCERRPECAS);
        						manager.persist(gePecas);
        					}
        					//manager.getTransaction().commit();
        					//geDocSegOper.getIdSegmento().getIdCheckin().setHasSendDbs("N");

        				}else{
        					Query query =  manager.createNativeQuery("delete from ge_pecas where ID_DOC_SEG_OPER = "+geDocSegOper.getId());
        					query.executeUpdate();
        					geDocSegOper.setNumDoc(rs.getString("PLDBS").trim());
        					geDocSegOper.setMsgDbs(null);
        					
//        					SQL = "select sos, pano20, qtde, CODERR, DESCERR from "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 where pedsm = '"+aux[0]+"-"+aux[1]+"'";
//        					rsPecas = prstmtPecas.executeQuery(SQL);
        					
        					//UNCS valor de custo para cliente interno
        					SQL = " SELECT PCPCOPD0.ORQY, PCPCOPD0.BOIMCT, PCPCOPD0.SOS1, PCPCOPD0.DSFCCD, PCPCOPD0.PANO20, PCPCOPD0.DS18, PCPCOPD0.UNSEL, PCPCOPD0.unsel*PCPCOPD0.orqy UNSEL_ORQY, PCPCOPD0.SKNSKI, PCPCOPD0.UNCS, PCPCOPD0.UNCS*PCPCOPD0.orqy UNCS_ORQY " +
        					" FROM "+IConstantAccess.LIB_DBS+".PCPCOPD0 PCPCOPD0 " +
        							//" and PCPCOPD0.orqy = parts.orqy"+
        					" WHERE trim(PCPCOPD0.RFDCNO)='"+rs.getString("PLDBS").trim()+"'";
        					rsPecas = prstmtPecas.executeQuery(SQL);
        					while(rsPecas.next()){
//        						String sos = rsPecas.getString("sos");
//        						String pano20 = rsPecas.getString("pano20");
//        						Integer qtde = rsPecas.getInt("qtde");
//        						GePecas gePecas = new GePecas();
//        						gePecas.setSos1(sos);
//        						gePecas.setIdDocSegOper(geDocSegOper);
//        						gePecas.setQtd(qtde.longValue());
//        						gePecas.setPartNo(pano20);
        						
        						GePecas gePecas = new GePecas();
        						gePecas.setQtd(Long.valueOf(rsPecas.getString("ORQY").trim()));
        						gePecas.setSos1(rsPecas.getString("SOS1").trim());
        						gePecas.setPartNo(rsPecas.getString("PANO20").trim());
        						gePecas.setPartName(rsPecas.getString("DS18").trim().replaceAll("'", ""));
        						gePecas.setUnsel(BigDecimal.valueOf(rsPecas.getDouble("UNSEL_ORQY")));
        						if("EXT".equals(geDocSegOper.getIdSegmento().getIdCheckin().getTipoCliente())){
        							gePecas.setValor(BigDecimal.valueOf(rsPecas.getDouble("UNSEL")));
        							gePecas.setValorTotal(BigDecimal.valueOf(rsPecas.getDouble("UNSEL_ORQY")));
        						}else if("INT".equals(geDocSegOper.getIdSegmento().getIdCheckin().getTipoCliente())){
        							gePecas.setValor(BigDecimal.valueOf(rsPecas.getDouble("UNCS")));
        							gePecas.setValorTotal(BigDecimal.valueOf(rsPecas.getDouble("UNCS_ORQY")));
        						}
        						gePecas.setQtdNaoAtendido(Long.valueOf(rsPecas.getString("BOIMCT").trim()));
        						gePecas.setIdDocSegOper(geDocSegOper);
        						
        						gePecas.setIpi(BigDecimal.ZERO);
        						gePecas.setIcmsub(BigDecimal.ZERO);
        						gePecas.setTotalTributos(BigDecimal.ZERO);
        						
        						
        						manager.persist(gePecas);
        					}
        					
        					
        					//geDocSegOper.setMsgDbs(rs.getString("CODERR".trim()));
        					//manager.getTransaction().commit();
        				}

        			}
        			if(rs.getString("CODERR").trim().length() > 0 && geDocSegOper != null){
        				//manager.getTransaction().begin();
        				manager.merge(geDocSegOper);
        				manager.getTransaction().commit();
        				String sql = ("delete FROM "+IConstantAccess.AMBIENTE_DBS+".USPPWSM0 where TRIM(PEDSM) = '"+rs.getString("PEDSM").trim()+"'");
        				prstmtDelete.executeUpdate(sql);
        				sql = ("delete FROM "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 where TRIM(PEDSM) = '"+rs.getString("PEDSM").trim()+"'");
        				prstmtDelete.executeUpdate(sql);
        			}else{
        				manager.getTransaction().commit();
        			}
        		}
        	}
//        	manager.getTransaction().begin();
//        	Query query = manager.createNativeQuery("update ge_doc_seg_oper oper set oper.msg_dbs = 'ERRO FAVOR TENTAR REENVIAR NOVAMENTE A RELAÇÃO DE PECAS' where oper.num_doc is null and oper.msg_dbs is null");
//        	query.executeUpdate();
//        	manager.getTransaction().commit();
        } catch (Exception e) {
        	if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
        	e.printStackTrace();
        	StringWriter errors = new StringWriter();
        	e.printStackTrace(new PrintWriter(errors));
        	new EmailHelper().sendSimpleMail("Erro ao buscar 00E\n"+errors.toString(), "ERRO AO RECUPERAR 00E", "rodrigo@rdrsistemas.com.br");
        	
        }finally{
        	try {
        		if(manager != null && manager.isOpen()){
        			manager.close();
        		} 
        		if(rsPecas != null){
        			rsPecas.close();
        		}
        		if(prstmtPecas != null){
        			prstmtPecas.close();
        		}
        		if(rs != null){
        			rs.close();
        		}
        		if(prstmt != null){
        			prstmt.close();
        		}
        		if(con != null){
        			con.close();
        		}
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}       	
        }    
		
	}
//		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(null);
//		business.saveErro();
		
	}


