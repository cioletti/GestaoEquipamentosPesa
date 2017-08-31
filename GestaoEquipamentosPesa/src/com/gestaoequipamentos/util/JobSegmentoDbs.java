package com.gestaoequipamentos.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.entity.GeDataFluxoSegmento;
import com.gestaoequipamentos.entity.GeLegendaProcessoOficina;
import com.gestaoequipamentos.entity.GeOperacao;
import com.gestaoequipamentos.entity.GeSegmento;

public class JobSegmentoDbs implements Job {
	
	
	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("0.##");
		System.out.println(df.format(10.5).replace(",", "."));
	}
	public synchronized void execute(JobExecutionContext arg0) throws JobExecutionException {
		

        Connection con = null;
        Statement prstmt = null;
        Statement prstmtOper = null;
        EntityManager manager = null;
        ResultSet rs = null;
        try {
        	DecimalFormat df = new DecimalFormat("0.##");
        	con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	prstmt = con.createStatement();
        	prstmtOper = con.createStatement();
        	manager = JpaUtil.getInstance();
        	Query query = manager.createQuery("from GeLegendaProcessoOficina where sigla = 'NV'");
        	GeLegendaProcessoOficina geLegendaProcessoOficina = (GeLegendaProcessoOficina)query.getSingleResult();
        	
        	
        	query = manager.createNativeQuery(" select ID_CHECKIN, NUMERO_SEGMENTO from GE_SEGMENTO s where (s.IS_CREATE_SEGMENTO = 'N' OR s.IS_CREATE_SEGMENTO is null)" +
        			" union "+
					"	select ID_CHECKIN, NUMERO_SEGMENTO from GE_SEGMENTO seg"+
					"	where seg.COD_ERRO_DBS = '99'");
        	List<Object[]> idList = (List<Object[]>)query.getResultList();
        	String pair = "";
        	for (Object[] p : idList) {
				pair += "'"+p[0]+"-O"+p[1]+"',";
			}
        	pair = (pair.length() > 0)?pair.substring(0, pair.length()-1):"''";
        	String SQL = "select TRIM(DESCERR) DESCERR, TRIM(CODERR) CODERR, TRIM(WONOSM) WONOSM, TRIM(WONO) WONO, TRIM(WOSGNO) WOSGNO  from "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 where LENGTH(TRIM(CODERR)) > 0 and TRIM(WONOSM)||TRIM(WOSGNO) in("+pair+")";
        	rs = prstmt.executeQuery(SQL);
        	while (rs.next()){	
        		String CODERR = rs.getString("CODERR").trim();				
        		String DESCERR = rs.getString("DESCERR").trim();
        		String WONO = rs.getString("WONO").trim();	
        		String WOSGNO = rs.getString("WOSGNO").trim();	
        		String [] aux = rs.getString("WONOSM").trim().split("-");
        		if(aux.length > 1){
        			String PEDSM = aux[0];
        			manager.getTransaction().begin();
        			query = manager.createQuery("from GeSegmento where idCheckin.id =:idCheckin and numeroSegmento =:numeroSegmento");
        			query.setParameter("idCheckin", Long.valueOf(PEDSM));
        			query.setParameter("numeroSegmento", WOSGNO);
        			if(query.getResultList().size() > 1){
        				manager.getTransaction().commit();
        				continue;
        			}
        			GeSegmento geSegmento = (GeSegmento)query.getSingleResult();
        			if(CODERR.equals("00")){
        				
        				//Início do fluxo de processo da OS
        				query = manager.createNativeQuery("delete from GE_DATA_FLUXO_SEGMENTO where ID_SEGMENTO = "+geSegmento.getId());
        				query.executeUpdate();
        				
        				GeDataFluxoSegmento dataFluxoSegmento = new GeDataFluxoSegmento();
    					dataFluxoSegmento.setData(new Date());
    					dataFluxoSegmento.setIdFuncionario(geSegmento.getIdCheckin().getIdSupervisor());
    					dataFluxoSegmento.setIdSegmento(geSegmento);
    					dataFluxoSegmento.setIdLegendaProcessoOficina(geLegendaProcessoOficina);
    					manager.persist(dataFluxoSegmento);
    					
    					geSegmento.setIdLegendaProcessoOficina(geLegendaProcessoOficina);
    					
        				geSegmento.setMsgDbs("Segmento Processado com sucesso!");
        				geSegmento.setCodErroDbs("00");
        				geSegmento.setIsCreateSegmento("S");
        				
        				if(geSegmento.getHorasPrevistaSubst() != null && geSegmento.getQtdCompSubst() != null){  
        					//multiplica pela quantidade de componentes
        					Double horasPrevistaSubst = Double.valueOf( geSegmento.getHorasPrevistaSubst()) * geSegmento.getQtdCompSubst();
        					//Atualiza a tabela of_segmento
        					query = manager.createNativeQuery("update OF_SEGMENTO set DURACAO =:duracao" +
        													  " where id in (select seg.id from OF_AGENDAMENTO ag, OF_SEGMENTO seg"+
        													  " where ag.ID_SEGMENTO = seg.id"+
        													  "	and ag.NUM_OS =:numOs"+
        													  "	and seg.NUM_SEGMENTO =:numSegmento)");
        					query.setParameter("duracao", df.format(horasPrevistaSubst).replace(",", "."));
        					query.setParameter("numOs", geSegmento.getIdCheckin().getNumeroOs());
        					query.setParameter("numSegmento", geSegmento.getNumeroSegmento());
        					query.executeUpdate();

        					//Atualiza a tabela of_agendamento
        					query = manager.createNativeQuery("update OF_AGENDAMENTO set DURACAO_PREVISTA =:duracao" +
        							" where id in (select ag.id from OF_AGENDAMENTO ag, OF_SEGMENTO seg"+
        							" where ag.ID_SEGMENTO = seg.id"+
        							" and ag.NUM_OS =:numOs"+
        							" and seg.NUM_SEGMENTO =:numSegmento)");
        					query.setParameter("duracao", df.format(horasPrevistaSubst).replace(",", "."));
        					query.setParameter("numOs", geSegmento.getIdCheckin().getNumeroOs());
        					query.setParameter("numSegmento", geSegmento.getNumeroSegmento());
        					query.executeUpdate();
        					
        					
        					geSegmento.setHorasPrevista(geSegmento.getHorasPrevistaSubst());
        					geSegmento.setQtdComp(geSegmento.getQtdCompSubst());
        				}
        				for (GeOperacao operacaoBean : geSegmento.getGeOperacaoCollection()) {
        					try {
        						pair = "'"+operacaoBean.getId()+"-O','"+WONO+"','"+geSegmento.getNumeroSegmento()+"','"+operacaoBean.getNumOperacao()+"','"+operacaoBean.getJbcd()+"','"+operacaoBean.getCptcd()+"'";
        						SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPOPSM0 (wonosm, wono, wosgno, woopno, jbcd, cptcd) values("+pair+")"; //
        						prstmtOper.executeUpdate(SQL);
        						geSegmento.setHasSendDbs("S");
        					} catch (Exception e) {
        						e.printStackTrace();
        						operacaoBean.setCodErroDbs("99");
        						operacaoBean.setMsgDbs("Não foi possível inserir operação no DBS!");
        					}
        				}
        			}else{
        				geSegmento.setCodErroDbs(CODERR);
        				geSegmento.setMsgDbs(DESCERR);
        				for(GeOperacao op : geSegmento.getGeOperacaoCollection()){
							op.setCodErroDbs("99");
							op.setMsgDbs("Não foi possível criar operação no DBS, pois, o segmento não foi criado!");
						}
        			}
        			
        			manager.getTransaction().commit();	
        		}
        	}  
        } catch (Exception e) {
        	if(manager != null && manager.getTransaction().isActive()){
        		manager.getTransaction().rollback();
        	}
        	EmailHelper emailHelper = new EmailHelper();
			emailHelper.sendSimpleMail("Erro ao executar a busca de segmento na ofcina: "+e.getMessage(), "Erro JobSegmentoDbs Oficina", "rodrigo@rdrsistemas.com.br");
        	e.printStackTrace();
        	
        }finally{
        	try {
        		if(manager != null && manager.isOpen()){
        			manager.close();
        		}
        		if(prstmt != null){
        			prstmt.close();
        		}
        		if(prstmtOper != null){
        			prstmtOper.close();
        		}
        		if(con != null){
        			con.close();
        		}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        }
		
	}
	
//	public static void main(String[] args) {
//		Double aux = Double.valueOf("10.5")*2;
//		System.out.println(aux.toString());
//	}
//		GestaoEquipamentosBusiness business = new GestaoEquipamentosBusiness(null);
//		business.saveErro();
		
	}


