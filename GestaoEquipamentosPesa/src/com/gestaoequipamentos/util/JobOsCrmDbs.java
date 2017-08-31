	package com.gestaoequipamentos.util;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.business.ChekinBusiness;
import com.gestaoequipamentos.entity.CrmSegmento;
import com.gestaoequipamentos.entity.GeCheckIn;
import com.gestaoequipamentos.entity.GeSegmento;
import com.gestaoequipamentos.entity.GeSituacaoOs;
import com.gestaoequipamentos.entity.TwFuncionario;

public class JobOsCrmDbs implements Job {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public synchronized void execute(JobExecutionContext arg0) throws JobExecutionException {
		
        Connection con = null;
        ChekinBusiness business = new ChekinBusiness(null); 
        Statement prstmt = null;
        Statement prstmtOper = null;
        String lbrate = "F"; // Caractere que será enviado junto com o total para o DBS para enviar o total para o DBS
        String ind = "E";
        EntityManager manager = null;
        ResultSet rs = null;
        ResultSet rs_os_open = null;
        Statement prstmtDataUpdate = null;
        PreparedStatement prstmt_open_os = null;
        try {
        	con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	prstmt = con.createStatement();
        	//prstmt_open_os = con.createStatement();
        	prstmtOper = con.createStatement();
       
        	manager = JpaUtil.getInstance();
        	Query query = manager.createNativeQuery("select ID from GE_CHECK_IN ch where ch.ID_CRM_PROPOSTA is not null and (ch.IS_CREATE_OS = 'N' OR ch.IS_CREATE_OS is null)");
        	List<BigDecimal> idList = (List<BigDecimal>)query.getResultList();
        	String pair = "";
        	for (BigDecimal bigDecimal : idList) {
				pair += "'"+bigDecimal+"-O',";
			}
        	pair = (pair.length() > 0)?pair.substring(0, pair.length()-1):"''";
        	String SQL = "select TRIM(DESCERR) DESCERR, TRIM(CODERR) CODERR, TRIM(WONO) WONO, TRIM(WONOSM) WONOSM from "+IConstantAccess.AMBIENTE_DBS+".USPWOSM0 where LENGTH(TRIM(CODERR)) > 0 and TRIM(WONOSM) in("+pair+")";
        	
        	
        	rs = prstmt.executeQuery(SQL);
        	while (rs.next()){		            	 
        		String CODERR = rs.getString("CODERR").trim();				
        		String WONO = rs.getString("WONO").trim();				
        		String DESCERR = rs.getString("DESCERR").trim();
        		String [] aux = rs.getString("WONOSM").trim().split("-");
        		if(aux.length > 1){
        			String PEDSM = aux[0];
        			GeCheckIn checkIn = manager.find(GeCheckIn.class, Long.valueOf(PEDSM));
        			query = manager.createQuery("from CrmSegmento where idProposta.id =:idCrmProposta and jobControl <> 'CA'");
					query.setParameter("idCrmProposta", checkIn.getIdCrmProposta());
					
					List<CrmSegmento> crmSegmentoList = query.getResultList();
					for (CrmSegmento crmSegmento : crmSegmentoList) {
						GeSegmento geSegmento = new GeSegmento();
						geSegmento.setNumeroSegmento(crmSegmento.getNumeroSegmento());
						geSegmento.setJobCode(crmSegmento.getJobCode());
						geSegmento.setDescricaoJobCode(crmSegmento.getDescricaoJobCode());
						geSegmento.setComCode(crmSegmento.getComCode());
						geSegmento.setIdCheckin(checkIn);
						geSegmento.setHorasPrevista(crmSegmento.getHorasPrevista());
						geSegmento.setJobControl(crmSegmento.getJobControl());
						geSegmento.setDescricaoCompCode(crmSegmento.getDescricaoCompCode());
						geSegmento.setQtdComp(crmSegmento.getQtdComp().intValue());
						geSegmento.setIdFuncionarioCriador(manager.find(TwFuncionario.class, crmSegmento.getIdFuncionarioCriador()));
						//geSegmento.setIdFuncionarioPecas(crmSegmento.getIdFuncionarioCriador());
						manager.getTransaction().begin();
						manager.persist(geSegmento);
						manager.getTransaction().commit();
						
//						query = manager.createQuery("from GeStatusServTerceiros where sigla = 'MTR'");
//						GeStatusServTerceiros geStatusServTerceiros = (GeStatusServTerceiros)query.getSingleResult(); 
//						
//						query = manager.createQuery("from CrmSegmentoServTerceiros where crmSegmentoServTerceirosPK.idCrmSegmento =:idCrmSegmento");
//						query.setParameter("idCrmSegmento", geSegmento.getId());
//						List<CrmSegmentoServTerceiros> crmSegmentoServTerceiros = query.getResultList();
//						for (CrmSegmentoServTerceiros segmentoServTerceiros : crmSegmentoServTerceiros) {
//							
//							CrmSegmentoServTerceiros terceiros = new CrmSegmentoServTerceiros(crmSegmento.getId(), segmentoServTerceiros.getCrmSegmentoServTerceirosPK().getIdTipoServicoTerceiros());
//							terceiros.setIdEmsSegmento(geSegmento.getId());
//							terceiros.setData(segmentoServTerceiros.getData());
//							terceiros.setValor(segmentoServTerceiros.getValor());
//							terceiros.setQtd(segmentoServTerceiros.getQtd());
//							terceiros.setIdFornServTerceiros(segmentoServTerceiros.getIdFornServTerceiros());
//							terceiros.setLocalServico(segmentoServTerceiros.getLocalServico());
//							terceiros.setObs(segmentoServTerceiros.getObs());
//							terceiros.setIdStatusServTerceiros(geStatusServTerceiros);
//							manager.getTransaction().begin();
//							manager.persist(terceiros);
//							manager.getTransaction().commit();
//						}

//						query = manager.createQuery("from CrmPecas where idCrmSegmento.id =:idSegmento");
//						query.setParameter("idSegmento", geSegmento.getId());
//						List<CrmPecas> crmPecasList = query.getResultList();
//						manager.getTransaction().begin();
//						for (CrmPecas crmPecas : crmPecasList) {
//							GeDocSegOper segOper = new GeDocSegOper();
//							segOper.setIdSegmento(geSegmento);
//							segOper.setDataCriacao(new Date());
//							manager.persist(segOper);
//
//							GePecas pecas = new GePecas();
//							pecas.setIdDocSegOper(segOper);
//							pecas.setPartNo(crmPecas.getPartNo());
//							pecas.setPartName(crmPecas.getPartName());
//							pecas.setQtd(crmPecas.getQtd());
//							pecas.setGroupNumber(crmPecas.getGroupNumber());
//							pecas.setGroupName(crmPecas.getGroupName());
//							pecas.setReferenceNo(crmPecas.getReferenceNo());
//							pecas.setSmcsCode(crmPecas.getSmcsCode());
//							pecas.setUserId(crmPecas.getUserId());
//							pecas.setValor(crmPecas.getValor());
//							pecas.setValorTotal(crmPecas.getValorTotal());
//							pecas.setSos1(crmPecas.getSos1());
//							pecas.setQtdNaoAtendido(crmPecas.getQtdNaoAtendido());
//							pecas.setIpi(crmPecas.getIpi());
//							pecas.setIcmsub(crmPecas.getIcmsub());
//							pecas.setTotalTributos(crmPecas.getTotaltributos());
//							pecas.setUnsel(crmPecas.getUnsel());
//							manager.persist(pecas);					
//						}
//						manager.getTransaction().commit();	
					}

        		       		        		
        			if(checkIn != null){
        				manager.getTransaction().begin();
        				if(CODERR.equals("00")){
        					checkIn.setMsgDbs("OS Criada com sucesso!");
        					checkIn.setCodErroDbs("00");
        					checkIn.setIsCreateOS("S");
        					checkIn.setHasSendCrmDbs("S");
        					checkIn.setIsLiberadoPDigitador("N");
        					checkIn.setIsLiberadoPConsultor("N");

        					checkIn.setNumeroOs(WONO);
        					//Enviar os segmentos para o DBS
        					
        					String filial = buscarFilialOS(manager, checkIn.getId());
        					query = manager.createNativeQuery("delete from Ge_Situacao_Os where ID_CHECKIN =:ID_CHECKIN");
        					query.setParameter("ID_CHECKIN", checkIn.getId());
        					query.executeUpdate();
        					// Seta os dados na tabela Ge_Situacao_OS
        					GeSituacaoOs situacaoObj = new GeSituacaoOs();
        					query = manager.createQuery("From TwFuncionario where epidno =:epidno");
							query.setParameter("epidno", checkIn.getIdSupervisor());
							TwFuncionario func = (TwFuncionario)query.getSingleResult();
							situacaoObj.setIdFuncDataChegada(func.getEpidno());
        					situacaoObj.setDataChegada(new Date());
        					situacaoObj.setNumeroOs(WONO.trim());
        					situacaoObj.setFilial(Long.valueOf(filial));
        					situacaoObj.setIsCheckOut("N");
        					situacaoObj.setIdCheckin(checkIn);
        					manager.persist(situacaoObj);
        					
        					query = manager.createQuery("from GeSegmento where idCheckin.id =:idCheckin");
        					query.setParameter("idCheckin", checkIn.getId());        					        					
        					
        					List<GeSegmento> geSegmentoList = (List<GeSegmento>)query.getResultList();
        					String jobControl = "";
        					for (GeSegmento segmentoBean : geSegmentoList) {
        						try {
        							jobControl += "'"+segmentoBean.getJobControl()+"',";
        							String horas = segmentoBean.getHorasPrevista().replace(".", "");
        							// horas = new Integer(Integer.valueOf(horas) * segmentoBean.getQtdComp()).toString();
        							if(horas.length() == 3){
        								horas = "00"+horas;
        							}else if(horas.length() == 4){
        								horas = "0"+horas;
	        						}else if(horas.length() == 1){
	        							horas = "0000"+horas;
	        						}else if(horas.length() == 2){
	        							horas = "000"+horas;
	        						}        							
        							
        							if(Integer.valueOf(horas) == 0){
        								horas = "";
        							}
        							String qty = segmentoBean.getQtdComp().toString();
        							for(int i = segmentoBean.getQtdComp().toString().length(); i < 5; i++){
        								qty = "0"+qty;
        							}
        							String watpcd = "    ";
        							//        						if(indGarantia != null && !("").equals(indGarantia)){
        							//        							watpcd = indGarantia;
        							//        						}
        							Map<String, String> map = business.findValorMaoDeObra(checkIn.getId(),checkIn.getIdOsPalm().getModelo(), segmentoBean.getNumeroSegmento(),checkIn.getTipoCliente(), checkIn.getIdOsPalm().getIdFamilia().getId());					
        							if(map != null && map.size() > 0 && !"0".equals(map.get(segmentoBean.getNumeroSegmento()).equals("0"))){
        								pair = "'"+checkIn.getId()+"-O','"+segmentoBean.getNumeroSegmento()+"','"+segmentoBean.getJobControl()+"','"+segmentoBean.getJobCode()+"','"+segmentoBean.getComCode()+"','"+ind+"','"+horas+"','"+lbrate+"','"+map.get(segmentoBean.getNumeroSegmento())+"', '"+qty+"', '"+watpcd+"','       '";
        								SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 (wonosm, wosgno, cscc, jbcd, cptcd, ind, frsthr, lbrate, lbamt, qty, watpcd, pipno) values("+pair+")";	
        							}else{
        								pair = "'"+checkIn.getId()+"-O','"+segmentoBean.getNumeroSegmento()+"','"+segmentoBean.getJobControl()+"','"+segmentoBean.getJobCode()+"','"+segmentoBean.getComCode()+"','"+ind+"','"+horas+"', '"+qty+"', '"+watpcd+"','       '";
        								SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 (wonosm, wosgno, cscc, jbcd, cptcd, ind, frsthr, qty, watpcd, pipno) values("+pair+")";
        							}
        							prstmtOper.executeUpdate(SQL);
        						} catch (Exception e) {
        							e.printStackTrace();
        							try {
        								query = manager.createQuery("From GeSegmento where idCheckin.id =:id and numeroSegmento =:numeroSegmento");
        								query.setParameter("id", checkIn.getId());
        								query.setParameter("numeroSegmento", segmentoBean.getNumeroSegmento());
        								GeSegmento seg = (GeSegmento)query.getSingleResult();
        								seg.setMsgDbs("Erro ao enviar segmento para o DBS!");
        								seg.setCodErroDbs("99");
        								manager.merge(seg);

        							} catch (Exception e1) {
        								e1.printStackTrace();
        							}

        						}
        					} 
        					if(jobControl.length() > 0){
        						jobControl = jobControl.substring(0, jobControl.length() -1);
        					}else{
        						jobControl = "''";
        					}
        					sendEmailSupervisor(manager, jobControl, filial, WONO); // Envia e-mail para o supervisor relatando que a OS foi criada!
        					
        					SendEmailHelper.sendEmailSupervisorJobControlAssociados(manager, jobControl, filial, WONO);
        					
        					//enviar e-mail para o gestor de rental
        					
        					//Envia e-mail para o engenheiro de serviço responsável e para o gestor de rental
        					if(checkIn.getTipoCliente().equals("INT") && checkIn.getCodCliente().length() > 4){
        						if(checkIn.getCodCliente().substring(4, checkIn.getCodCliente().length()).equals("246")){
        							new EmailHelper().sendSimpleMail("Gostaríamos de informar que a OS "+WONO+" foi criada, para o código do cliente "+checkIn.getCodCliente()+" com as seguintes obs:\n"+checkIn.getObsProposta(), "Ordem de Serviço", "laroca_marco@pesa.com.br");
        						}
        						
        						if(checkIn.getCodCliente().substring(4, checkIn.getCodCliente().length()).equals("305")
        								|| checkIn.getCodCliente().substring(4, checkIn.getCodCliente().length()).equals("304")
        								|| checkIn.getCodCliente().substring(4, checkIn.getCodCliente().length()).equals("292")
        								|| checkIn.getCodCliente().substring(4, checkIn.getCodCliente().length()).equals("344")
        								|| checkIn.getCodCliente().substring(4, checkIn.getCodCliente().length()).equals("234")
        								|| checkIn.getCodCliente().substring(4, checkIn.getCodCliente().length()).equals("629")
        								|| checkIn.getCodCliente().substring(4, checkIn.getCodCliente().length()).equals("469")){
        							this.sendEmailGestorRental(manager, filial, checkIn.getNumeroOs());
        						}
        					}
        					
        					// Inserindo a data de chegada no DBS.
//        					SQL = "select W.NTDA from "+IConstantAccess.LIB_DBS+".WOPNOTE0 W where TRIM(W.WONO) = '"+WONO+"' AND TRIM(W.NTLNO1) = '001' AND TRIM(W.WOSGNO) = ''";
//        					rsData = prstmtData.executeQuery(SQL);
//        					if(rsData.next()){
//        						String NTDA = rsData.getString("NTDA");
//        						String dataAux = NTDA.replace("../../....", dateFormat.format(new Date()));
//        						SQL = "update "+IConstantAccess.LIB_DBS+".WOPNOTE0 W set W.NTDA = '"+dataAux+"' where TRIM(W.WONO) = '"+WONO+"' AND TRIM(W.NTLNO1) = '001' AND TRIM(W.WOSGNO) = ''";
//        						prstmtDataUpdate.executeUpdate(SQL);
//        					}
        					UsuarioBean bean = new UsuarioBean();
        					
        					bean.setEstimateBy(func.getEstimateBy());
        					prstmtDataUpdate = new ChekinBusiness(bean).setDateFluxoOSDBS(dateFormat.format(new Date()), con, WONO, "001");
        					
        					//Verifica se a OS está OPEN
        					SQL = "select TRIM(w.ACTI) ACTI from "+IConstantAccess.LIB_DBS+".WOPHDRS0 w  where TRIM(w.WONO) = '"+checkIn.getNumeroOs()+"'"; 
        					prstmt_open_os = con.prepareStatement(SQL);
        					rs_os_open = prstmt_open_os.executeQuery();
        					if(rs_os_open.next()){
        						String ACTI = rs_os_open.getString("ACTI");
        						
        						checkIn.setIsOpenOs("N");
        						if(ACTI.equals("E")){
        							new ChekinBusiness(bean).openOs(checkIn.getId(), checkIn.getNumeroOs());
//        							if(!new ChekinBusiness(bean).openOs(checkIn.getId(), checkIn.getNumeroOs())){
//        								checkIn.setIsOpenOs("N");
//        								//return "Não foi possível abrir OS!-true";
//        							}
        						}
        					}
        					
        					
        				}else{
        					checkIn.setNumeroOs(DESCERR);
        					checkIn.setCodErroDbs("99");
        					checkIn.setIsCreateOS("N");
        					try {
								query = manager.createNativeQuery("delete from GE_SEGMENTO where ID_CHECKIN = "+checkIn.getId());
								//query.setParameter("idCheckin", checkIn.getId());  
								query.executeUpdate();
							} catch (Exception e) {
								new EmailHelper().sendSimpleMail("Erro ao remover segmento da OS JobOsDbs linha 228!", "ERRO OS JobOsDbs", "rodrigo@rdrsistemas.com.br");
								e.printStackTrace();
							}
        					//System.out.println("Executei a remoção do segmento!");
//        					List<GeSegmento> geSegmentoList = (List<GeSegmento>)query.getResultList();
//        					for (GeSegmento segmentoBean : geSegmentoList) {
//        						segmentoBean.setCodErroDbs("99");
//        						segmentoBean.setMsgDbs("Não foi possível criar segmento no DBS, pois, a OS não foi criada!");
//        						for(GeOperacao op : segmentoBean.getGeOperacaoCollection()){
//        							op.setCodErroDbs("99");
//        							op.setMsgDbs("Não foi possível criar operação no DBS, pois, a OS não foi criada!");
//        						}
//        					}
        				}	
        				manager.getTransaction().commit();
        			} else {
        				continue;   				
        			}
        		}else{
        			String PEDSM = aux[0];
        			manager.getTransaction().begin();
        			GeCheckIn checkIn = manager.find(GeCheckIn.class, Long.valueOf(PEDSM));
        			if(checkIn != null){
        				if(CODERR.equals("99")){
        					checkIn.setNumeroOs(DESCERR);
        					checkIn.setCodErroDbs("99");
        					query = manager.createNativeQuery("delete From Ge_Segmento where id_Checkin =:id");
							query.setParameter("id", checkIn.getId());
							query.executeUpdate();
        				}
        			}
        			manager.getTransaction().commit();
        		}
        		
        	}
        } catch (Exception e) {
        	if(manager != null && manager.getTransaction().isActive()){
        		manager.getTransaction().rollback();
        	}
        	e.printStackTrace();
        	
        }finally{
        	try {
        		if(manager != null && manager.isOpen()){
        			manager.close();
        		}
        		if(rs != null){
        			rs.close();
        		}
        		if(prstmt != null){
        			prstmt.close();
        		}
        		if(prstmtOper != null){
        			prstmtOper.close();
        		}	
        		if(prstmtDataUpdate != null){
        			prstmtDataUpdate.close();
        		}
        		if(prstmt_open_os != null){
        			prstmt_open_os.close();
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
	
	/**
	 * Busca a filial da OS
	 */
	private String buscarFilialOS(EntityManager manager, Long idCheckin) {

		try {
			Query query = manager.createNativeQuery("SELECT filial FROM ge_os_palm p, ge_check_in c " +
					"WHERE c.id_os_palm = p.idos_palm AND c.id = "+idCheckin);

			String idFilial = (String) query.getSingleResult();
			return idFilial;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * Envia e-mail para supervisor do JobContol da OS ao ser criado uma nova Ordem de Serviço.
	 * @param manager
	 */	
	private void sendEmailSupervisor(EntityManager manager, String jobcontrol, String filial, String numOS){
		try {
			
			if(jobcontrol == null){
				jobcontrol = "''";
			}
			
			Query query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+
					 " WHERE tw.epidno = perfil.id_tw_usuario"+
					 " AND perfil.ID_PERFIL = (select id from adm_perfil where sigla = 'SUPER' and tipo_sistema = 'GE')"+
					 " AND tw.STN1 = '"+Integer.valueOf(filial).toString()+"'"+
					 " AND perfil.JOB_CONTROL in ("+jobcontrol+")");

			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				for(Object [] pair : list){
					EmailHelper helper = new EmailHelper();
					helper.sendSimpleMail((String)pair[1]+" gostaríamos de informar que a OS "+numOS+" foi liberada!", "Ordem de Serviço", (String)pair[0]);		
				}				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * Envia e-mail para o gestor de rental de filial.
	 * @param manager
	 */	
	private void sendEmailGestorRental(EntityManager manager, String filial, String numOS){
		try {
			Query query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+
					" WHERE tw.epidno = perfil.id_tw_usuario"+
					" AND perfil.ID_PERFIL = (select id from adm_perfil where sigla = 'GERENT' and tipo_sistema = 'GE')"+
					" AND tw.STN1 = '"+Integer.valueOf(filial).toString()+"'");
			
			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				for(Object [] pair : list){
					EmailHelper helper = new EmailHelper();
					helper.sendSimpleMail((String)pair[1]+" gostaríamos de informar que a OS "+numOS+" foi criada!", "Ordem de Serviço", (String)pair[0]);		
				}				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		System.out.println("00XX246".substring(4, "00XX246".length()).equals("246"));
	}
	
//	public static void main(String[] args) {
//		String data = "../../.... ...... (SAIDA MATERIAL E N.FISCAL)     ";
//		  
//
//		
//		String dataAux = data.replace("../../....", dateFormat.format(new Date()));
//		
//		System.out.println(dataAux);
//	}
		
	}


