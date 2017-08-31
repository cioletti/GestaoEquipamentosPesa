package com.gestaoequipamentos.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.entity.GeOperacao;
import com.gestaoequipamentos.entity.GeSegmento;

public class JobSegmentoCrmDbs implements Job {
	
	SimpleDateFormat dateSerTerc = new SimpleDateFormat("dd/MM/yy");
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
        	//ConsultorCheckinBusiness business = new ConsultorCheckinBusiness(null);
        	DecimalFormat df = new DecimalFormat("0.##");
        	con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	prstmt = con.createStatement();
        	prstmtOper = con.createStatement();
        	manager = JpaUtil.getInstance();
        	Query query = manager.createNativeQuery(" select ID_CHECKIN, NUMERO_SEGMENTO from GE_SEGMENTO s where (s.IS_CREATE_SEGMENTO = 'N' OR s.IS_CREATE_SEGMENTO is null)" +
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
        				continue;
        			}
        			GeSegmento geSegmento = (GeSegmento)query.getSingleResult();
        			if(CODERR.equals("00")){
        				       				
        				geSegmento.setMsgDbs("Segmento Processado com sucesso!");
        				geSegmento.setCodErroDbs("00");
        				geSegmento.setIsCreateSegmento("S");
        				
//        				query = manager.createQuery("from GeSegmentoServTerceiros where geSegmentoServTerceirosPK.idSegmento =:idCrmSegmento");
//						query.setParameter("idCrmSegmento", geSegmento.getId());
//						List<GeSegmentoServTerceiros> geSegmentoServTerceirosList = query.getResultList();
//						int seq = 0;
//						
//						for (GeSegmentoServTerceiros geSegmentoServTerceiros : geSegmentoServTerceirosList) {
//							query = manager.createNativeQuery("select DESCRICAO from GE_TIPO_SERVICO_TERCEIROS where ID = "+geSegmentoServTerceiros.getGeSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
//							String descricaoServTerc = (String)query.getSingleResult();
//													
//							if(descricaoServTerc != null && descricaoServTerc.length() > 0){
//								if(descricaoServTerc.length() > 16){
//									descricaoServTerc = descricaoServTerc.substring(0,16);
//								}
//							}else{
//								descricaoServTerc = "";
//							}
//							String valorSerTerc = "";
//							String valorServTercNota = business.valorServTercFormatada(geSegmentoServTerceiros.getValor());
//							if(geSegmento.getIdCheckin().getTipoCliente().equals("INT")){
//								valorSerTerc = business.valorServTercFormatada(geSegmentoServTerceiros.getValor());
//							}else{
//								BigDecimal valor = geSegmentoServTerceiros.getValor().multiply(BigDecimal.valueOf(1.5));
//								valorSerTerc = business.valorServTercFormatada(valor.setScale(2, RoundingMode.HALF_UP));
//							}
//							pair = "'"+geSegmento.getIdCheckin().getNumeroOs()+"','"+geSegmento.getNumeroSegmento()+"','"+seq+"','"+"MOT"+"','"+dateSerTerc.format(new Date()).replace("/", "")+"','"+business.qtdServTercFormatada(geSegmentoServTerceiros.getQtd())+"','"+valorServTercNota+"','"+valorSerTerc+"','"+descricaoServTerc+"'";
//							//String pair = "'"+bean.getNumeroOs()+"','"+geSegmento.getNumeroSegmento()+"','"+seq+"','"+"MOT"+"','"+dateSerTerc.format(new Date()).replace("/", "")+"','"+qtdServTercFormatada(geSegmentoServTerceiros.getQtd())+"','"+valorServTercNota+"','"+valorSerTerc+"','"+descricaoServTerc+"'";
//							SQL = "INSERT INTO "+IConstantAccess.AMBIENTE_DBS+".USPMSSM0 (WONO, WOSGNO, SEQ, CHGCD, DOCDAT, QTY, UNCS, UNSL, DESC) VALUES ("+pair+")";
//							prstmt.executeUpdate(SQL);
//							seq++;
//							
//							
//						}
//						query = manager.createQuery("from GeDocSegOper where idSegmento.id =:idSegmento");
//						query.setParameter("idSegmento", geSegmento.getId());
//						List<GeDocSegOper> docSegOperList = query.getResultList();
//						manager.getTransaction().begin();
//						for (GeDocSegOper docSegOper : docSegOperList) {
//							GeDocSegOper segOper = new GeDocSegOper();
//							query = manager.createQuery("from GePecas where idDocSegOper.id =:idDocSegOper");
//							query.setParameter("idDocSegOper", segOper.getId());
//							List<GePecas> pecasList = query.getResultList();
//							try {
//								prstmt.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 where PEDSM = 'O-"+segOper.getId()+"'");
//								prstmt.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPPWSM0 where PEDSM = 'O-"+segOper.getId()+"'");
//								for (GePecas gePecas : pecasList) {
//									GePecas pecas = new GePecas();
//									pecas.setIdDocSegOper(segOper);
//									SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 (PEDSM, SOS, PANO20, QTDE) values('O-"+segOper.getId()+"', '"+((gePecas.getSos1() == null)?"000":gePecas.getSos1())+"', '"+gePecas.getPartNo().replace("-", "")+"', '"+gePecas.getQtd()+"')";
//									prstmt.executeUpdate(SQL);
//								}
//								String idFuncionario = docSegOper.getIdSegmento().getIdFuncionarioPecas();
//								String nmec = "";
//								if(idFuncionario != null && idFuncionario.length() > 0){
//									TwFuncionario funcionario = manager.find(TwFuncionario.class, idFuncionario);
//									if(funcionario.getLogin().length() <= 4){
//										nmec = funcionario.getLogin().toUpperCase();
//									}
//								}
//								SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPPWSM0 (PEDSM, WONOSM, SGNOSM, OPERSM, NMEC) values('O-"+segOper.getId()+"', '"+geSegmento.getIdCheckin().getNumeroOs()+"', '"+geSegmento.getNumeroSegmento()+"', '','"+nmec+"')";
//								prstmt.executeUpdate(SQL);
//							} catch (Exception e) {
//								prstmt.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPPLSM0 where PEDSM = 'O-"+segOper.getId()+"'");
//								prstmt.executeUpdate(" delete from "+IConstantAccess.AMBIENTE_DBS+".USPPWSM0 where PEDSM = 'O-"+segOper.getId()+"'");
//								e.printStackTrace();
//							}
//						}
//						manager.getTransaction().commit();	
        			
        				
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
        						pair = "'"+operacaoBean.getId()+"-C','"+WONO+"','"+geSegmento.getNumeroSegmento()+"','"+operacaoBean.getNumOperacao()+"','"+operacaoBean.getJbcd()+"','"+operacaoBean.getCptcd()+"'";
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


