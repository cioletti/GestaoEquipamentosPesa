package com.gestaoequipamentos.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import javassist.compiler.ast.Stmnt;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.business.ChekinBusiness;
import com.gestaoequipamentos.entity.GeSituacaoOs;
import com.gestaoequipamentos.entity.TwFuncionario;

public class JobDataSituacaoOsDbs implements Job {
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public synchronized void execute(JobExecutionContext arg0) throws JobExecutionException {
		
        Connection con = null;
        //Statement prstmt = null;
        EntityManager manager = null;
        Query query = null;
        Statement prstmt = null;
        ResultSet rs = null;
        try {
        	con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	//prstmt = con.createStatement();
        	manager = JpaUtil.getInstance();
        	//Leva a data de entrega de pedidos para o DBS
        	query = manager.createNativeQuery("select ID, convert(varchar(10),DATA_ENTREGA_PEDIDOS,103), ID_FUNC_DATA_ENTREGA_PEDIDOS, NUMERO_OS from GE_SITUACAO_OS where DATA_ENTREGA_PEDIDOS is not null and IS_SEND_DATA_ENTREGA_PEDIDOS is null");
        	List<Object[]> idList = (List<Object[]>)query.getResultList();
        	for (Object []object : idList) {
        		
        		try {
					query = manager.createQuery("From TwFuncionario where epidno =:epidno");
					query.setParameter("epidno", (String)object[2]);
					TwFuncionario func = (TwFuncionario)query.getSingleResult();
					
					UsuarioBean bean = new UsuarioBean();
					
					bean.setEstimateBy(func.getEstimateBy());
					if(new ChekinBusiness(bean).setDateFluxoOSDBSServico((String)object[1], con, (String)object[3], "002")){ 
						manager.getTransaction().begin();
						query = manager.createNativeQuery("update GE_SITUACAO_OS set IS_SEND_DATA_ENTREGA_PEDIDOS = 'S' where ID =:ID");
						query.setParameter("ID", (BigDecimal)object[0]);
						query.executeUpdate();

						manager.getTransaction().commit();
					}
				} catch (Exception e) {
					if(manager != null && manager.getTransaction().isActive()){
		        		manager.getTransaction().rollback();
		        	}
					e.printStackTrace();
				}
			}
        	con.close();
        	con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
            //******************************************************************************************************************************
        	//Leva a data de orçamento para o DBS
        	query = manager.createNativeQuery("select ID, convert(varchar(10),DATA_ORCAMENTO,103), ID_FUNC_DATA_ORCAMENTO, NUMERO_OS from GE_SITUACAO_OS where DATA_ORCAMENTO is not null and IS_SEND_DATA_ORCAMENTO is null");
        	idList = (List<Object[]>)query.getResultList();
        	for (Object []object : idList) {
        		
        		try {
					query = manager.createQuery("From TwFuncionario where epidno =:epidno");
					query.setParameter("epidno", (String)object[2]);
					TwFuncionario func = (TwFuncionario)query.getSingleResult();
					
					UsuarioBean bean = new UsuarioBean();

					bean.setEstimateBy(func.getEstimateBy());
					if(new ChekinBusiness(bean).setDateFluxoOSDBSServico((String)object[1], con, (String)object[3], "003")){
						manager.getTransaction().begin();
						query = manager.createNativeQuery("update GE_SITUACAO_OS set IS_SEND_DATA_ORCAMENTO = 'S' where ID =:ID");
						query.setParameter("ID", (BigDecimal)object[0]);
						query.executeUpdate();

						manager.getTransaction().commit();
					}
        		} catch (Exception e) {
        			if(manager != null && manager.getTransaction().isActive()){
                		manager.getTransaction().rollback();
                	}
        			e.printStackTrace();
				}
			}
        	con.close();
        	con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	//******************************************************************************************************************************
        	//Leva a data de aprovação para o DBS
        	query = manager.createNativeQuery("select ID, convert(varchar(10),DATA_APROVACAO,103), ID_FUNC_DATA_APROVACAO, NUMERO_OS from GE_SITUACAO_OS where DATA_APROVACAO is not null and IS_SEND_DATA_APROVACAO is null");
        	idList = (List<Object[]>)query.getResultList();
        	for (Object []object : idList) {
        		
        		try {
        			query = manager.createQuery("From TwFuncionario where epidno =:epidno");
        			query.setParameter("epidno", (String)object[2]);
        			TwFuncionario func = (TwFuncionario)query.getSingleResult();
        			
        			UsuarioBean bean = new UsuarioBean();
        			
        			bean.setEstimateBy(func.getEstimateBy());
        			if(new ChekinBusiness(bean).setDateFluxoOSDBSServico((String)object[1], con, (String)object[3], "004")){
        				manager.getTransaction().begin();
        				query = manager.createNativeQuery("update GE_SITUACAO_OS set IS_SEND_DATA_APROVACAO = 'S' where ID =:ID");
        				query.setParameter("ID", (BigDecimal)object[0]);
        				query.executeUpdate();

        				manager.getTransaction().commit();
        			}
        		} catch (Exception e) {
        			if(manager != null && manager.getTransaction().isActive()){
                		manager.getTransaction().rollback();
                	}
        			e.printStackTrace();
        		}
        	}
        	con.close();
        	con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	//******************************************************************************************************************************
        	//Leva a data de previsão de entrega para o DBS
        	query = manager.createNativeQuery("select ID, convert(varchar(10),DATA_PREVISAO_ENTREGA,103), ID_FUNC_DATA_PREVISAO_ENTREGA, NUMERO_OS from GE_SITUACAO_OS where DATA_PREVISAO_ENTREGA is not null and IS_SEND_DATA_PREVISAO_ENTREGA is null");
        	idList = (List<Object[]>)query.getResultList();
        	for (Object []object : idList) {
        		
        		try {
        			query = manager.createQuery("From TwFuncionario where epidno =:epidno");
        			query.setParameter("epidno", (String)object[2]);
        			TwFuncionario func = (TwFuncionario)query.getSingleResult();
        			
        			UsuarioBean bean = new UsuarioBean();
        			
        			bean.setEstimateBy(func.getEstimateBy());
        			if(new ChekinBusiness(bean).setDateFluxoOSDBSServico((String)object[1], con, (String)object[3], "005")){ 
        				manager.getTransaction().begin();
        				query = manager.createNativeQuery("update GE_SITUACAO_OS set IS_SEND_DATA_PREVISAO_ENTREGA = 'S' where ID =:ID");
        				query.setParameter("ID", (BigDecimal)object[0]);
        				query.executeUpdate();

        				manager.getTransaction().commit();
        			}
        		} catch (Exception e) {
        			if(manager != null && manager.getTransaction().isActive()){
                		manager.getTransaction().rollback();
                	}
        			e.printStackTrace();
        		}
        	}
        	con.close();
        	con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	//******************************************************************************************************************************
        	//Leva a data de termino do serviço para o DBS
        	query = manager.createNativeQuery("select ID, convert(varchar(10),DATA_TERMINO_SERVICO,103), ID_FUNC_DATA_TERMINO_SERVICO, NUMERO_OS from GE_SITUACAO_OS where DATA_TERMINO_SERVICO is not null and IS_SEND_DATA_TERMINO_SERVICO is null and ID_FUNC_DATA_TERMINO_SERVICO is not null");
        	idList = (List<Object[]>)query.getResultList();
        	for (Object []object : idList) {
        		
        		try {
        			query = manager.createQuery("From TwFuncionario where epidno =:epidno");
        			query.setParameter("epidno", (String)object[2]);
        			TwFuncionario func = (TwFuncionario)query.getSingleResult();
        			
        			UsuarioBean bean = new UsuarioBean();

        			bean.setEstimateBy(func.getEstimateBy());
        			if(new ChekinBusiness(bean).setDateFluxoOSDBSServico((String)object[1], con, (String)object[3], "006")){ 
        				manager.getTransaction().begin();
        				query = manager.createNativeQuery("update GE_SITUACAO_OS set IS_SEND_DATA_TERMINO_SERVICO = 'S' where ID =:ID");
        				query.setParameter("ID", (BigDecimal)object[0]);
        				query.executeUpdate();

        				manager.getTransaction().commit();
        			}
        		} catch (Exception e) {
        			if(manager != null && manager.getTransaction().isActive()){
                		manager.getTransaction().rollback();
                	}
        			e.printStackTrace();
        		}
        	}
        	con.close();
        	con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	//******************************************************************************************************************************
        	//Leva a data de faturamento para o DBS
        	query = manager.createNativeQuery("select ID, convert(varchar(10),DATA_FATURAMENTO,103), ID_FUNC_DATA_FATURAMENTO, NUMERO_OS from GE_SITUACAO_OS where DATA_FATURAMENTO is not null and IS_SEND_DATA_FATURAMENTO is null and (ENCERRAR_AUTOMATICA is null or ENCERRAR_AUTOMATICA = 'N')");
        	idList = (List<Object[]>)query.getResultList();
        	String SQL = null;
        	Double VLROSP = null;
        	Double VLROSS = null;
        	String cuno = null;
        	SimpleDateFormat formatFaturamento = new SimpleDateFormat("dd.MM.yyyy");
        	for (Object []object : idList) {
        		
        		try {
        			query = manager.createQuery("From TwFuncionario where epidno =:epidno");
        			query.setParameter("epidno", (String)object[2]);
        			TwFuncionario func = (TwFuncionario)query.getSingleResult();
        			
        			UsuarioBean bean = new UsuarioBean();
        			
        			bean.setEstimateBy(func.getEstimateBy());
        			if(new ChekinBusiness(bean).setDateFluxoOSDBSServicoNaoAutomatico((String)object[1], con, (String)object[3], "007")){
        				query = manager.createQuery("from GeSituacaoOs where id =:id");
        				query.setParameter("id", ((BigDecimal)object[0]).longValue());
        				GeSituacaoOs geSituacaoOs = (GeSituacaoOs)query.getSingleResult();
        				if(geSituacaoOs.getIdCheckin().getTipoCliente().equals("EXT")){
        					
        					 
        					SQL = "select sum(WIPPAS) WIPPAS from "+IConstantAccess.LIB_DBS+".wopsegs0 where wono = '"+geSituacaoOs.getNumeroOs()+"'";
        					prstmt = con.createStatement();
        					rs = prstmt.executeQuery(SQL);
        					if(rs.next()){
        						VLROSP = rs.getDouble("WIPPAS");
        					}
        					
        					
        			        VLROSS = null; 
        			        
        			        SQL = "select sum(lbamt) lbamt from "+IConstantAccess.LIB_DBS+".wopsegs0 where wono = '"+geSituacaoOs.getNumeroOs()+"'";
        					prstmt = con.createStatement();
        					rs = prstmt.executeQuery(SQL);
        					if(rs.next()){
        						VLROSS = rs.getDouble("lbamt");
        					}
        					
        					SQL = "select cuno from "+IConstantAccess.LIB_DBS+".wophdrs0 where wono = '"+geSituacaoOs.getNumeroOs()+"'";
    						prstmt = con.createStatement();
    						rs = prstmt.executeQuery(SQL);
    						if(rs.next()){
    							cuno = rs.getString("cuno");
    						}
        					
        					SQL = "insert into "+IConstantAccess.PESASAPARQ+".SAPINFWONO (CODEMP, WONO, CUNO, STNO, PROPOSTA, AUTORIZADO, ORDCOMP, ORDCOMS,"+ 
        					" ESTCREDP, ESTCREDS, CPAGP, CPAGS, DSCPCTP, DSCPCTS, DSCVLRP, DSCVLRS, VLRORCP, VLRORCS, VLROSP, VLROSS, DTAFAT,"+
        					" ENCAUT, MANAUT, OBSPEC, OBSSER, CONDESP) values ('PESA', '"+geSituacaoOs.getNumeroOs()+"', '"+cuno+"', '"+geSituacaoOs.getFilial()+"'," +
        					" '"+geSituacaoOs.getPropostaNumero()+"','"+geSituacaoOs.getAutorizadoPor()+"', '"+geSituacaoOs.getOrdemCompraPeca()+"','"+geSituacaoOs.getOrdemCompraServico()+"'," +
        					" '"+geSituacaoOs.getEstabelecimentoCredorPecas()+"','"+geSituacaoOs.getEstabelecimentoCredorServicos()+"', '"+geSituacaoOs.getCondicaoPagamentoPecas()+"'," +
        					" '"+geSituacaoOs.getCondicaoPagamentoServicos()+"', '"+geSituacaoOs.getDescontoPorcPecas()+"', '"+geSituacaoOs.getDescontoPorcServicos()+"'," +
        					" '"+geSituacaoOs.getDescontoValorPecas()+"', '"+geSituacaoOs.getDescontoValorServicos()+"', '"+geSituacaoOs.getValorPecas()+"', '"+geSituacaoOs.getMoMiscDesl()+"'," +
        					" '"+VLROSP+"', '"+VLROSS+"', '"+formatFaturamento.format(geSituacaoOs.getDataFaturamento())+"','"+geSituacaoOs.getEncerrarAutomatica()+"','"+geSituacaoOs.getAutomaticaFaturada()+"'," +
        					" '"+geSituacaoOs.getObsPeca()+"', '"+geSituacaoOs.getObsServico()+"', '"+((geSituacaoOs.getCondicaoEspececial() != null)?geSituacaoOs.getCondicaoEspececial():"")+"')";
        					prstmt = con.createStatement();
        					prstmt.executeUpdate(SQL);
        				}
        				
        				
        				manager.getTransaction().begin();
        				query = manager.createNativeQuery("update GE_SITUACAO_OS set IS_SEND_DATA_FATURAMENTO = 'S' where ID =:ID");
        				query.setParameter("ID", (BigDecimal)object[0]);
        				query.executeUpdate();

        				manager.getTransaction().commit();
        			}
        		} catch (Exception e) {
        			if(manager != null && manager.getTransaction().isActive()){
                		manager.getTransaction().rollback();
                	}
        			e.printStackTrace();
        		}
        	}
        	con.close();
        	con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	//******************************************************************************************************************************
        	//Leva a data de faturamento de encerramento automático para o DBS
        	query = manager.createNativeQuery("select ID, convert(varchar(10),DATA_FATURAMENTO,103), ID_FUNC_DATA_FATURAMENTO, NUMERO_OS from GE_SITUACAO_OS where DATA_FATURAMENTO is not null and IS_SEND_DATA_FATURAMENTO is null and ENCERRAR_AUTOMATICA = 'S'");
        	idList = (List<Object[]>)query.getResultList();
        	for (Object []object : idList) {
        		
        		try {
        			query = manager.createQuery("From TwFuncionario where epidno =:epidno");
        			query.setParameter("epidno", (String)object[2]);
        			TwFuncionario func = (TwFuncionario)query.getSingleResult();
        			
        			UsuarioBean bean = new UsuarioBean();
        			
        			bean.setEstimateBy(func.getEstimateBy());
        			if(new ChekinBusiness(bean).setDateFluxoOSDBSServicoAutomatica((String)object[1], con, (String)object[3], "007")){
        				query = manager.createQuery("from GeSituacaoOs where id =:id");
        				query.setParameter("id", ((BigDecimal)object[0]).longValue());
        				GeSituacaoOs geSituacaoOs = (GeSituacaoOs)query.getSingleResult();
        				if(geSituacaoOs.getIdCheckin().getTipoCliente().equals("EXT")){
        					
        					 
        					SQL = "select sum(WIPPAS) WIPPAS from "+IConstantAccess.LIB_DBS+".wopsegs0 where wono = '"+geSituacaoOs.getNumeroOs()+"'";
        					prstmt = con.createStatement();
        					rs = prstmt.executeQuery(SQL);
        					if(rs.next()){
        						VLROSP = rs.getDouble("WIPPAS");
        					}
        					
        					
        			        VLROSS = null; 
        			        
        			        SQL = "select sum(lbamt) lbamt from "+IConstantAccess.LIB_DBS+".wopsegs0 where wono = '"+geSituacaoOs.getNumeroOs()+"'";
        					prstmt = con.createStatement();
        					rs = prstmt.executeQuery(SQL);
        					if(rs.next()){
        						VLROSS = rs.getDouble("lbamt");
        					}
        					
        					SQL = "select cuno from "+IConstantAccess.LIB_DBS+".wophdrs0 where wono = '"+geSituacaoOs.getNumeroOs()+"'";
    						prstmt = con.createStatement();
    						rs = prstmt.executeQuery(SQL);
    						if(rs.next()){
    							cuno = rs.getString("cuno");
    						}
        					
        					SQL = "insert into "+IConstantAccess.PESASAPARQ+".SAPINFWONO (CODEMP, WONO, CUNO, STNO, PROPOSTA, AUTORIZADO, ORDCOMP, ORDCOMS,"+ 
        					" ESTCREDP, ESTCREDS, CPAGP, CPAGS, DSCPCTP, DSCPCTS, DSCVLRP, DSCVLRS, VLRORCP, VLRORCS, VLROSP, VLROSS, DTAFAT,"+
        					" ENCAUT, MANAUT, OBSPEC, OBSSER, CONDESP) values ('PESA', '"+geSituacaoOs.getNumeroOs()+"', '"+cuno+"', '"+geSituacaoOs.getFilial()+"'," +
        					" '"+geSituacaoOs.getPropostaNumero()+"','"+geSituacaoOs.getAutorizadoPor()+"', '"+geSituacaoOs.getOrdemCompraPeca()+"','"+geSituacaoOs.getOrdemCompraServico()+"'," +
        					" '"+geSituacaoOs.getEstabelecimentoCredorPecas()+"','"+geSituacaoOs.getEstabelecimentoCredorServicos()+"', '"+geSituacaoOs.getCondicaoPagamentoPecas()+"'," +
        					" '"+geSituacaoOs.getCondicaoPagamentoServicos()+"', '"+geSituacaoOs.getDescontoPorcPecas()+"', '"+geSituacaoOs.getDescontoPorcServicos()+"'," +
        					" '"+geSituacaoOs.getDescontoValorPecas()+"', '"+geSituacaoOs.getDescontoValorServicos()+"', '"+geSituacaoOs.getValorPecas()+"', '"+geSituacaoOs.getMoMiscDesl()+"'," +
        					" '"+VLROSP+"', '"+VLROSS+"', '"+formatFaturamento.format(geSituacaoOs.getDataFaturamento())+"','"+geSituacaoOs.getEncerrarAutomatica()+"','"+geSituacaoOs.getAutomaticaFaturada()+"'," +
        					" '"+geSituacaoOs.getObsPeca()+"', '"+geSituacaoOs.getObsServico()+"', '"+((geSituacaoOs.getCondicaoEspececial() != null)?geSituacaoOs.getCondicaoEspececial():"")+"')";
        					prstmt = con.createStatement();
        					StringWriter errors = new StringWriter();
        					try {
								prstmt.executeUpdate(SQL);
							} catch (Exception e) {
								e.printStackTrace(new PrintWriter(errors));
								if(errors.toString().contains("Duplicate key")){
									manager.getTransaction().begin();
			        				
			        				geSituacaoOs.setIsSendDataFaturamento("S");
			        				manager.getTransaction().commit();
								}else{
									e.printStackTrace();
									continue;
								}
							}
        				}
        				manager.getTransaction().begin();
        				
        				geSituacaoOs.setIsSendDataFaturamento("S");
//        				query = manager.createNativeQuery("update GE_SITUACAO_OS set IS_SEND_DATA_FATURAMENTO = 'S' where ID =:ID");
//        				query.setParameter("ID", (BigDecimal)object[0]);
//        				query.executeUpdate();
        				
        				manager.getTransaction().commit();
        			}
        		} catch (Exception e) {
        			if(manager != null && manager.getTransaction().isActive()){
        				manager.getTransaction().rollback();
        			}
        			e.printStackTrace();
        		}
        	}
        	con.close();
        	con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	//******************************************************************************************************************************
        	//Leva os dados do cliente para DBS
        	query = manager.createNativeQuery("select ch.ID, ch.NUMERO_OS, p.CONTATO, p.TELEFONE, p.EMAIL_CONTATO from GE_CHECK_IN ch, GE_OS_PALM p "+
											  "	where ch.SEN_DATA_NOTES is null "+
											  "	and p.IDOS_PALM = ch.ID_OS_PALM " +
											  " and ch.NUMERO_OS is not null"+
											  "	and ch.TIPO_CLIENTE = 'EXT'");
        	idList = (List<Object[]>)query.getResultList();
        	for (Object []object : idList) {
        		
        		try {
        			
        			if(new ChekinBusiness(null).setDateFluxoOSDBSDadosCliente("NOME DO CONTATO ........:"+(String)object[2], con, (String)object[1], "010")){
        				if(new ChekinBusiness(null).setDateFluxoOSDBSDadosCliente("TELEFONE DO CONTATO ....:"+(String)object[3], con, (String)object[1], "011")){
        					if((String)object[4] == null || new ChekinBusiness(null).setDateFluxoOSDBSDadosCliente("EMAIL CONTATO..:"+(String)object[4], con, (String)object[1], "012")){
        						manager.getTransaction().begin();
        						query = manager.createNativeQuery("update GE_CHECK_IN set SEN_DATA_NOTES = 'S' where ID =:ID");
        						query.setParameter("ID", (BigDecimal)object[0]);
        						query.executeUpdate();        				
        						manager.getTransaction().commit();
        					}
        				}
        			}
        		} catch (Exception e) {
        			if(manager != null && manager.getTransaction().isActive()){
        				manager.getTransaction().rollback();
        			}
        			e.printStackTrace();
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
				//prstmt.close();
        		if(con != null){
        			con.close();
        		}
			} catch (SQLException e) {
				e.printStackTrace();
			}
        	
        }
		
	}
	public static void main(String[] args) {
		System.out.println("java.sql.SQLException: [SQL0803] Duplicate key value specified.".contains("Duplicate key"));
	}
		
	}


