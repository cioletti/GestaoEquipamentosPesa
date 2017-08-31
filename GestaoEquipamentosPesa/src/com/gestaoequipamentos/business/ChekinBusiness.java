package com.gestaoequipamentos.business;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.ChekinBean;
import com.gestaoequipamentos.bean.ContatoBean;
import com.gestaoequipamentos.bean.DataHeaderBean;
import com.gestaoequipamentos.bean.DetalhesPropostaBean;
import com.gestaoequipamentos.bean.GeFormularioAprovacaoOsBean;
import com.gestaoequipamentos.bean.GestaoEquipamentosBean;
import com.gestaoequipamentos.bean.PecasAprovacaoOSBean;
import com.gestaoequipamentos.bean.SegmentoBean;
import com.gestaoequipamentos.bean.SistemaBean;
import com.gestaoequipamentos.bean.SituacaoOSBean;
import com.gestaoequipamentos.bean.TipoFreteBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.GeCabecalhoPecas;
import com.gestaoequipamentos.entity.GeCheckIn;
import com.gestaoequipamentos.entity.GeConsultorProposta;
import com.gestaoequipamentos.entity.GeDetalhesProposta;
import com.gestaoequipamentos.entity.GeDocSegOper;
import com.gestaoequipamentos.entity.GeFormularioAprovacaoOs;
import com.gestaoequipamentos.entity.GeOperacao;
import com.gestaoequipamentos.entity.GeOsPalm;
import com.gestaoequipamentos.entity.GePecas;
import com.gestaoequipamentos.entity.GePecasAprovacao;
import com.gestaoequipamentos.entity.GePecasTemp;
import com.gestaoequipamentos.entity.GeSegmento;
import com.gestaoequipamentos.entity.GeSituacaoOs;
import com.gestaoequipamentos.entity.GeTipoFrete;
import com.gestaoequipamentos.entity.TwFilial;
import com.gestaoequipamentos.entity.TwFuncionario;
import com.gestaoequipamentos.util.ConectionDbs;
import com.gestaoequipamentos.util.DateHelper;
import com.gestaoequipamentos.util.EmailHelper;
import com.gestaoequipamentos.util.IConstantAccess;
import com.gestaoequipamentos.util.JpaUtil;
import com.gestaoequipamentos.util.ValorMonetarioHelper;


public class ChekinBusiness {
	private UsuarioBean bean;
	private SimpleDateFormat fmt = new SimpleDateFormat("dMMyyyy");
	private SimpleDateFormat fmtPtBr = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private SimpleDateFormat dateFormatHorimetroDbs = new SimpleDateFormat("ddMMyy");
	String semana[] = {"Domingo","Segunda-feira","Terça-feira","Quarta-feira","Quinta-feira","Sexta-feira","Sábado","Domingo"}; 
	public ChekinBusiness(UsuarioBean bean) {
		this.bean = bean;
	}
	//private String AMBIENTE_DBS = "LIBU15FTP";
	
	
	public Boolean editarJobControl(SegmentoBean seg, ChekinBean ch, String observacaojobControl){	
		ResultSet rs = null;
		PreparedStatement prstmt_ = null;
		EntityManager manager = null;

		Connection con = null;
		try {
			
			
			
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
			String SQL = "select t1.RESPAR, t2.CSCC, t2.WOSGNO  from "+IConstantAccess.LIB_DBS+".WOPHDRS0 t1, "+IConstantAccess.LIB_DBS+".WOPSEGS0 t2" +
					" where t1.wono = t2.wono " +
					" and t1.wono = '"+ch.getNumeroOs()+"' " +
					" and t2.wosgno = '"+seg.getNumeroSegmento()+"'";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				manager = JpaUtil.getInstance();
				new SegmentoBusiness(this.bean).logEditarSegmento(manager, seg.getId());
				manager.getTransaction().begin();
				SQL = "update GE_CHECK_IN set JOB_CONTROL = '"+rs.getString("RESPAR")+"' where ID = "+ch.getId(); 
				Query query = manager.createNativeQuery(SQL);
				query.executeUpdate();
				SQL = "update GE_SEGMENTO set JOB_CONTROL = '"+rs.getString("CSCC")+"', DESCRICAO_JOB_CONTROL = '"+observacaojobControl+"' where ID ="+seg.getId() ; 
				query = manager.createNativeQuery(SQL);
				query.executeUpdate();
				manager.getTransaction().commit();
			}
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();					
			}
			
			try {
				if(con != null){
					con.close();
					prstmt_.close();
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	
	}

	public List<ChekinBean> findAllChekin(String campoPesquisa, String jobControl){
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		String campo = campoPesquisa;
		try {
			manager = JpaUtil.getInstance(); //Pega conexÃ£o com o banco

			//Query query = manager.createQuery("Select ch.*, s.dataPrevisaoEntrega From GeCheckIn ch, GeSituacaoOS s,GeOsPalm os where s.idCheckin = ch.id and s.dataConclusaoServico is null and os.filial =:filial"); // where idOsPalm.filial =:filial order by dataEmissao"); //Retorna uma consulta do banco= select * from
			//Query query = manager.createQuery("From GeCheckin where idOsPalm.filial =: filial");
			String sqlCheckIn = "From " +
			" GeCheckIn ch , GeOsPalm os ";					

				
			if(campo != null && !campo.equals("TODAS")){
				sqlCheckIn +="  where ch.id not in (select s.idCheckin.id from GeSituacaoOs s where s.dataFaturamento is not null)  ";
				sqlCheckIn +="  and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)";
				//sqlCheckIn +=" and (ch.statusNegociacaoConsultor <> 'R' or ch.statusNegociacaoConsultor is null)";
			}else{
				sqlCheckIn +="  where 1=1";
				campoPesquisa = "";
			}
			//sqlCheckIn += " and seg.idCheckin.id = ch.id ";
			sqlCheckIn +=" and os.idosPalm = ch.idOsPalm ";
//			if(jobControl != null){
//				sqlCheckIn += " and ch.jobControl =:jobControl";
//			} //" where ch.isCheckIn = " +colfindSituacaoOS+					
			if(isPerfilDigitador()){ // Verifica se tem o perfil de digitador.
				sqlCheckIn +=" and ch.isLiberadoPDigitador = 'S'";
			}
			
			sqlCheckIn += " and CONVERT(INT,os.filial) =:filial AND (" ;

			sqlCheckIn += " ch.numeroOs LIKE '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR ch.idEquipamento LIKE '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.cliente like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.serie like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.modelo like '%"+campoPesquisa.toUpperCase()+"%' )";
			//sqlCheckIn += " ORDER BY os.emissao desc)";
			/*if(numeroOSText != null && !numeroOSText.equals("")){
				SQL_CHECKIN += " and ch.numeroOs ='"+numeroOSText.toUpperCase()+"'";
			}
			if((clienteText != null && !clienteText.equals("")) && (numeroOSText == null || numeroOSText.equals(""))){
				SQL_CHECKIN += " and os.cliente like '"+clienteText.toUpperCase()+"%'";
			}*/		
			
			sqlCheckIn += " order by os.emissao desc";
			Query query = manager.createQuery(sqlCheckIn );
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			if(jobControl != null){
				query.setParameter("jobControl", jobControl);
			}
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				
				
				
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
				if(situacaoOsList.size() > 0){
					geSituacaoOs = situacaoOsList.get(0);
				}
				if(geSituacaoOs.getDataFaturamento() != null && !campo.equals("TODAS")){
					continue;
				}
				String dataLiberacaoPecas = "N".equals(checkIn.getIsLiberadoPDigitador())?null:checkIn.getIsLiberadoPDigitador();
				String dataTerminoServico = (geSituacaoOs.getDataTerminoServico() != null)?dateFormat.format(geSituacaoOs.getDataTerminoServico()):null;
				if(jobControl != null){
					dataLiberacaoPecas = findDataLiberacaoPecasSegmento(manager, checkIn, jobControl);
					dataTerminoServico = findDataTerminoServico(manager, checkIn, jobControl);
				}
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				bean.setIsRemoveSegmento("N");
				if(geSituacaoOs.getDataPrevisaoEntrega() == null){
					//if(geSituacaoOs.getDataPrevisaoEntrega() != null){//To Do REMOVER ESTA CONDIÃ‡ÃƒO QUANDO O SISTEMA ESTIVER INTEGRADO COM O CRM
						bean.setIsRemoveSegmento("S");
					//}
				}
				resulList.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}	
	public List<ChekinBean> findAllChekinSuper(String campoPesquisa, String jobControl){
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		String campo = campoPesquisa;
		try {
			manager = JpaUtil.getInstance(); //Pega conexÃ£o com o banco
			
			//Query query = manager.createQuery("Select ch.*, s.dataPrevisaoEntrega From GeCheckIn ch, GeSituacaoOS s,GeOsPalm os where s.idCheckin = ch.id and s.dataConclusaoServico is null and os.filial =:filial"); // where idOsPalm.filial =:filial order by dataEmissao"); //Retorna uma consulta do banco= select * from
			//Query query = manager.createQuery("From GeCheckin where idOsPalm.filial =: filial");
			String sqlCheckIn = "select distinct ch, os, so From " +
			" GeCheckIn ch , GeOsPalm os, GeSegmento seg, GeSituacaoOs so ";					
			
			
			if(campo != null && !campo.equals("TODAS")){
				//sqlCheckIn +="  where ch.id not in (select s.idCheckin.id from GeSituacaoOs s where s.dataFaturamento is not null)  ";
				sqlCheckIn +="  where ch.isEncerrada is null  ";
				sqlCheckIn +="  and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)";
				//sqlCheckIn +=" and (ch.statusNegociacaoConsultor <> 'R' or ch.statusNegociacaoConsultor is null)";
			}else{
				sqlCheckIn +="  where 1=1";
				campoPesquisa = "";
			}
			sqlCheckIn += " and seg.idCheckin.id = ch.id ";
			sqlCheckIn +=" and os.idosPalm = ch.idOsPalm ";
			sqlCheckIn +=" and so.idCheckin.id = ch.id ";
			if(jobControl != null){
				sqlCheckIn += " and seg.jobControl =:jobControl";
			} //" where ch.isCheckIn = " +colfindSituacaoOS+					
			if(isPerfilDigitador()){ // Verifica se tem o perfil de digitador.
				sqlCheckIn +=" and ch.isLiberadoPDigitador = 'S'";
			}
			
			sqlCheckIn += " and os.filial =:filial AND (" ;
			
			sqlCheckIn += " ch.numeroOs LIKE '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR ch.idEquipamento LIKE '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.cliente like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.serie like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.modelo like '%"+campoPesquisa.toUpperCase()+"%' )";
			sqlCheckIn += " ORDER BY ch.id )";
			/*if(numeroOSText != null && !numeroOSText.equals("")){
				SQL_CHECKIN += " and ch.numeroOs ='"+numeroOSText.toUpperCase()+"'";
			}
			if((clienteText != null && !clienteText.equals("")) && (numeroOSText == null || numeroOSText.equals(""))){
				SQL_CHECKIN += " and os.cliente like '"+clienteText.toUpperCase()+"%'";
			}*/		
			
			sqlCheckIn += " order by os.emissao desc";
			Query query = manager.createQuery(sqlCheckIn );
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			if(jobControl != null){
				query.setParameter("jobControl", jobControl);
			}
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				GeSituacaoOs geSituacaoOs = (GeSituacaoOs)pair[2];
				//GeSegmento geSegmento = (GeSegmento)pair[3];
				
//				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
//				query.setParameter("idCheckin", checkIn.getId());
//				List<GeSituacaoOs> situacaoOsList = query.getResultList();
//				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
//				if(situacaoOsList.size() > 0){
//					geSituacaoOs = situacaoOsList.get(0);
//				}
//				if(!campo.equals("TODAS")){
//					continue;
//				}
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				String dataLiberacaoPecas = findDataLiberacaoPecasSegmento(manager, checkIn, jobControl);
				String dataTerminoServico = findDataTerminoServico(manager, checkIn, jobControl);
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				bean.setIsRemoveSegmento("N");
				//bean.setIdSegmento(geSegmento.getId());
				if(geSituacaoOs.getDataPrevisaoEntrega() == null){
					//if(geSituacaoOs.getDataPrevisaoEntrega() != null){//To Do REMOVER ESTA CONDIÃ‡ÃƒO QUANDO O SISTEMA ESTIVER INTEGRADO COM O CRM
					bean.setIsRemoveSegmento("S");
					//}
				}
				if(bean.getDataTerminoServico() != null &&  bean.getDataFaturamento() == null){
					query = manager.createNativeQuery("select distinct lbcc from pmp_cliente_pl where cod_cliente =:codClienet and lbcc is not null");
					query.setParameter("codClienet", checkIn.getCodCliente());
					//Condição para retirar as OS's de rental e rental usadas da tela do supervisor
					if(query.getResultList().size() > 0){
						String lbcc = (String)query.getResultList().get(0);
						if((lbcc.equals("2U") || lbcc.equals("3W") || lbcc.equals("2Y") || lbcc.equals("2E") || lbcc.equals("2Z") || lbcc.equals("2R") || lbcc.equals("2V") ) && bean.getDataFaturamento() == null && bean.getDataTerminoServico() != null){
							bean.setDataFaturamento(" ");
						}
					}
				}
				resulList.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}	
	public List<ChekinBean> findAllChekin(String campoPesquisa, String jobControl, String tipoCliente){
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		String campo = campoPesquisa;
		try {
			manager = JpaUtil.getInstance(); //Pega conexÃ£o com o banco
			
			//Query query = manager.createQuery("Select ch.*, s.dataPrevisaoEntrega From GeCheckIn ch, GeSituacaoOS s,GeOsPalm os where s.idCheckin = ch.id and s.dataConclusaoServico is null and os.filial =:filial"); // where idOsPalm.filial =:filial order by dataEmissao"); //Retorna uma consulta do banco= select * from
			//Query query = manager.createQuery("From GeCheckin where idOsPalm.filial =: filial");
			String sqlCheckIn = "From " +
			" GeCheckIn ch , GeOsPalm os ";					
			
			
			if(campo != null && !campo.equals("TODAS")){
				sqlCheckIn +="  where ch.id not in (select s.idCheckin.id from GeSituacaoOs s where s.dataFaturamento is not null)  ";
				sqlCheckIn +="  and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)";
				//sqlCheckIn +=" and (ch.statusNegociacaoConsultor <> 'R' or ch.statusNegociacaoConsultor is null)";
			}else{
				sqlCheckIn +="  where 1=1";
				campoPesquisa = "";
			}
			sqlCheckIn +=" and os.idosPalm = ch.idOsPalm ";
			sqlCheckIn +=" and ch.tipoCliente = '"+tipoCliente+"' ";
			if(jobControl != null){
				sqlCheckIn += " and ch.jobControl =:jobControl";
			} //" where ch.isCheckIn = " +colfindSituacaoOS+					
			if(isPerfilDigitador()){ // Verifica se tem o perfil de digitador.
				sqlCheckIn +=" and ch.isLiberadoPDigitador = 'S'";
			}
			
			sqlCheckIn += " and os.filial =:filial AND (" ;
			
			sqlCheckIn += " ch.numeroOs LIKE '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.cliente like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.serie like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.modelo like '%"+campoPesquisa.toUpperCase()+"%' )";
			sqlCheckIn += " ORDER BY ch.id )";
			/*if(numeroOSText != null && !numeroOSText.equals("")){
				SQL_CHECKIN += " and ch.numeroOs ='"+numeroOSText.toUpperCase()+"'";
			}
			if((clienteText != null && !clienteText.equals("")) && (numeroOSText == null || numeroOSText.equals(""))){
				SQL_CHECKIN += " and os.cliente like '"+clienteText.toUpperCase()+"%'";
			}*/		
			
			sqlCheckIn += " order by os.emissao desc";
			Query query = manager.createQuery(sqlCheckIn );
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			if(jobControl != null){
				query.setParameter("jobControl", jobControl);
			}
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
				if(situacaoOsList.size() > 0){
					geSituacaoOs = situacaoOsList.get(0);
				}
				if(geSituacaoOs.getDataFaturamento() != null && !campo.equals("TODAS")){
					continue;
				}
				String dataLiberacaoPecas = null;
				String dataTerminoServico = (geSituacaoOs.getDataTerminoServico() != null)?dateFormat.format(geSituacaoOs.getDataTerminoServico()):null;
				if(jobControl != null){
					dataLiberacaoPecas = findDataLiberacaoPecasSegmento(manager, checkIn, jobControl);
					dataTerminoServico = findDataTerminoServico(manager, checkIn, jobControl);
				}
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				bean.setIsRemoveSegmento("N");
				if(geSituacaoOs.getDataPrevisaoEntrega() == null){
					//if(geSituacaoOs.getDataPrevisaoEntrega() != null){//To Do REMOVER ESTA CONDIÃ‡ÃƒO QUANDO O SISTEMA ESTIVER INTEGRADO COM O CRM
					bean.setIsRemoveSegmento("S");
					//}
				}
				resulList.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}	
	public List<ChekinBean> findAllConsultorGaosChekin(String campoPesquisa, String tipoCliente){
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		String campo = campoPesquisa;
		try {
			manager = JpaUtil.getInstance(); //Pega conexÃ£o com o banco
			
			//Query query = manager.createQuery("Select ch.*, s.dataPrevisaoEntrega From GeCheckIn ch, GeSituacaoOS s,GeOsPalm os where s.idCheckin = ch.id and s.dataConclusaoServico is null and os.filial =:filial"); // where idOsPalm.filial =:filial order by dataEmissao"); //Retorna uma consulta do banco= select * from
			//Query query = manager.createQuery("From GeCheckin where idOsPalm.filial =: filial");
			String sqlCheckIn = "From " +
			" GeCheckIn ch , GeOsPalm os, GeConsultorProposta cp ";					


			sqlCheckIn +="  where ch.id not in (select s.idCheckin.id from GeSituacaoOs s where s.dataFaturamento is not null)  ";
			sqlCheckIn +="  and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)";
			//sqlCheckIn +=" and (ch.statusNegociacaoConsultor <> 'R' or ch.statusNegociacaoConsultor is null)";

			sqlCheckIn +=" and os.idosPalm = ch.idOsPalm ";
			sqlCheckIn +=" and ch.tipoCliente = '"+tipoCliente+"' ";
			sqlCheckIn +=" and cp.idCheckIn.id = ch.id";
			sqlCheckIn +=" and cp.idEpidno.epidno = '"+bean.getMatricula()+"'";
							
			
			//sqlCheckIn += " and os.filial =:filial AND (" ;
//			sqlCheckIn += " AND (" ;
			
//			sqlCheckIn += " ch.numeroOs LIKE '%"+campoPesquisa.toUpperCase()+"%'";
//			sqlCheckIn += " OR os.cliente like '%"+campoPesquisa.toUpperCase()+"%'";
//			sqlCheckIn += " OR os.serie like '%"+campoPesquisa.toUpperCase()+"%'";
//			sqlCheckIn += " OR os.modelo like '%"+campoPesquisa.toUpperCase()+"%' )";
			sqlCheckIn += " ORDER BY ch.id )";
			/*if(numeroOSText != null && !numeroOSText.equals("")){
				SQL_CHECKIN += " and ch.numeroOs ='"+numeroOSText.toUpperCase()+"'";
			}
			if((clienteText != null && !clienteText.equals("")) && (numeroOSText == null || numeroOSText.equals(""))){
				SQL_CHECKIN += " and os.cliente like '"+clienteText.toUpperCase()+"%'";
			}*/		
			
			sqlCheckIn += " order by os.emissao desc";
			Query query = manager.createQuery(sqlCheckIn );
			//query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
				if(situacaoOsList.size() > 0){
					geSituacaoOs = situacaoOsList.get(0);
				}
				if(geSituacaoOs.getDataFaturamento() != null && !campo.equals("TODAS")){
					continue;
				}
				String dataLiberacaoPecas = "N".equals(checkIn.getIsLiberadoPDigitador())?null:checkIn.getIsLiberadoPDigitador();
				String dataTerminoServico = (geSituacaoOs.getDataTerminoServico() != null)?dateFormat.format(geSituacaoOs.getDataTerminoServico()):null;
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				bean.setIsRemoveSegmento("N");
				if(geSituacaoOs.getDataPrevisaoEntrega() == null){
					//if(geSituacaoOs.getDataPrevisaoEntrega() != null){//To Do REMOVER ESTA CONDIÃ‡ÃƒO QUANDO O SISTEMA ESTIVER INTEGRADO COM O CRM
					bean.setIsRemoveSegmento("S");
					//}
				}
				resulList.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}	
	
	public List<ChekinBean> findAllChekinOrcamentista(String campoPesquisa){
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		
		try {
						
			manager = JpaUtil.getInstance(); //Pega conexÃ£o com o banco

			//Query query = manager.createQuery("Select ch.*, s.dataPrevisaoEntrega From GeCheckIn ch, GeSituacaoOS s,GeOsPalm os where s.idCheckin = ch.id and s.dataConclusaoServico is null and os.filial =:filial"); // where idOsPalm.filial =:filial order by dataEmissao"); //Retorna uma consulta do banco= select * from
			//Query query = manager.createQuery("From GeCheckin where idOsPalm.filial =: filial");
			String sqlCheckIn = "From " +
					" GeCheckIn ch , GeOsPalm os, GeSituacaoOs so";					
					
					sqlCheckIn +=" where os.idosPalm = ch.idOsPalm ";
					//sqlCheckIn +=" and (ch.statusNegociacaoConsultor <> 'R' or ch.statusNegociacaoConsultor is null)";
					sqlCheckIn +=" and so.idCheckin.id = ch.id";
					if(campoPesquisa == null || "".equals(campoPesquisa)){
						sqlCheckIn +=" and ch.id not in (select s.idCheckin.id from GeSituacaoOs s where s.dataFaturamento is not null)  ";	
						sqlCheckIn +=" and ch.isLiberadoPOrcamentista = 'S'";
						sqlCheckIn +=" and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)";
						sqlCheckIn +=" and so.encerrarAutomatica is null";
					}

			sqlCheckIn += " and os.filial =:filial AND (" ;
			
			sqlCheckIn += " ch.numeroOs LIKE '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR ch.idEquipamento LIKE '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.cliente like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.serie like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.modelo like '%"+campoPesquisa.toUpperCase()+"%' )";
			//sqlCheckIn += " ORDER BY ch.id )";
			/*if(numeroOSText != null && !numeroOSText.equals("")){
				SQL_CHECKIN += " and ch.numeroOs ='"+numeroOSText.toUpperCase()+"'";
			}
			if((clienteText != null && !clienteText.equals("")) && (numeroOSText == null || numeroOSText.equals(""))){
				SQL_CHECKIN += " and os.cliente like '"+clienteText.toUpperCase()+"%'";
			}*/		
			
			if(!bean.getIsAdm() && !bean.getSiglaPerfil().equals("GAOS")){
				if(campoPesquisa == null || "".equals(campoPesquisa)){
					sqlCheckIn += " and (SUBSTRING (ch.codCliente, 5, 3)+ch.tipoCliente  NOT IN ('243INT','247INT','023INT','241INT','463INT','468INT','998INT','249INT','267INT','268INT') OR ch.codCliente IS NULL)";
				}
			}
			
			sqlCheckIn += " order by os.emissao desc";
			Query query = manager.createQuery(sqlCheckIn );
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				//query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				//query.setParameter("idCheckin", checkIn.getId());
				//List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = (GeSituacaoOs)pair[2];
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				//String dataLiberacaoPecas = findDataLiberacaoPecasSegmento(manager, checkIn, this.bean.getJobControl());
				//String dataTerminoServicos = findDataTerminoServico(manager, checkIn, this.bean.getJobControl());
				String dataTerminoServicos = null;
				//String dataLiberacaoPecas = null;
				Integer totalDadaLiberacao = this.getTotalDataliberacao(checkIn.getId());
				String dataLiberacaoPecas = (totalDadaLiberacao == 0)?"S":null;
				if(geSituacaoOs.getDataTerminoServico() != null){
					dataTerminoServicos = dateFormat.format(geSituacaoOs.getDataTerminoServico());
				}
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServicos, this.bean);
				bean.setSiglaPerfil(this.bean.getSiglaPerfil());
				resulList.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	
	private Integer getTotalDataliberacao(Long idCheckin){
		EntityManager manager = null;
		Integer totalDadaLiberacao = 0;
		try {

			manager = JpaUtil.getInstance(); //Pega conexÃ£o com o banco
			String SQL = "select COUNT(*) from GE_SEGMENTO where ID_CHECKIN = "+idCheckin+
			" and DATA_LIBERACAO_PECAS is null";
			Query query = manager.createNativeQuery(SQL);
			totalDadaLiberacao = (Integer)query.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return totalDadaLiberacao;
	}

	/**
	 * Verifica se o usuário logado tem o perfil de digitador
	 * @return true se tem o perfil e false se não tem.
	 */
	private boolean isPerfilDigitador() {
		for(SistemaBean sisBean : this.bean.getSistemaList()){
			if(sisBean.getPerfilBean().getSigla().equals("DIG")){
				return true;
			}
		}
		
		return false;
	}
	
	public List <ChekinBean> findSituacaoOS (String col1findSituacaoOS, String col2findSituacaoOS){
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String SQL  = "From " +
					" GeCheckIn ch , GeOsPalm os" +
					" where ch.id in (select s.idCheckin from GeSituacaoOs s where "+col1findSituacaoOS+" is not null and "+col2findSituacaoOS+ " is null)  " +
					//" and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)" +
					" and ch.numeroOs is not null" +
					" and os.idosPalm = ch.idOsPalm "+
					" and os.filial =:filial ";

			
			Query query = manager.createQuery(SQL);
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
				if(situacaoOsList.size() > 0){
					geSituacaoOs = situacaoOsList.get(0);
				}
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				
				String dataLiberacaoPecas = "N".equals(checkIn.getIsLiberadoPDigitador())?null:checkIn.getIsLiberadoPDigitador();
				String dataTerminoServico = (geSituacaoOs.getDataTerminoServico() != null)?dateFormat.format(geSituacaoOs.getDataTerminoServico()):null;
				
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				resulList.add(bean);
			}
  
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	public List <ChekinBean> findSituacaoOSSupervisor (String col1findSituacaoOS, String col2findSituacaoOS){
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String SQL  = "From " +
			" GeCheckIn ch , GeOsPalm os" +
			" where ch.id in (select s.idCheckin from GeSituacaoOs s where "+col1findSituacaoOS+" is not null and "+col2findSituacaoOS+ " is null)  " +
			//" and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)" +
			" and ch.numeroOs is not null" +
			" and os.idosPalm = ch.idOsPalm "+
			" and os.filial =:filial ";
			
			
			Query query = manager.createQuery(SQL);
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
				if(situacaoOsList.size() > 0){
					geSituacaoOs = situacaoOsList.get(0);
				}
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				
				String dataLiberacaoPecas = "N".equals(checkIn.getIsLiberadoPDigitador())?null:checkIn.getIsLiberadoPDigitador();
				String dataTerminoServico = geSituacaoOs.getDataTerminoServico() != null ? dateFormat.format(geSituacaoOs.getDataTerminoServico()): null;
				
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				resulList.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	public List <ChekinBean> findSituacaoOS (String col1findSituacaoOS, String col2findSituacaoOS, String tipoCliente){
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String SQL  = "From " +
			" GeCheckIn ch , GeOsPalm os" +
			" where ch.id in (select s.idCheckin from GeSituacaoOs s where "+col1findSituacaoOS+" is not null and "+col2findSituacaoOS+ " is null)  " +
			//" and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)" +
			" and ch.numeroOs is not null" +
			" and ch.tipoCliente = '"+tipoCliente+"' "+
			" and os.idosPalm = ch.idOsPalm "+
			" and os.filial =:filial ";
			
			
			Query query = manager.createQuery(SQL);
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
				if(situacaoOsList.size() > 0){
					geSituacaoOs = situacaoOsList.get(0);
				}
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				String dataLiberacaoPecas = "N".equals(checkIn.getIsLiberadoPDigitador())?null:checkIn.getIsLiberadoPDigitador();
				String dataTerminoServico = (geSituacaoOs.getDataTerminoServico() != null)?dateFormat.format(geSituacaoOs.getDataTerminoServico()):null;
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				resulList.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	public String findDataLiberacaoPecasSegmento(EntityManager manager,
			GeCheckIn checkIn, String jobControl) {
		Query query;
		String dataLiberacaoPecas = null;
		query = manager.createNativeQuery("select convert(datetime, DATA_LIBERACAO_PECAS, 103) From Ge_Segmento where  ID_CHECKIN =:idCheckin and job_Control =:jobControl");
		query.setParameter("idCheckin", checkIn.getId());
		query.setParameter("jobControl", jobControl);
		List<Date> segmentoList = (List<Date>)query.getResultList();
		
		for (Date DATA_LIBERACAO_PECAS : segmentoList) {
			if(DATA_LIBERACAO_PECAS == null){
				return null;
			}			
			dataLiberacaoPecas = dateFormat.format(DATA_LIBERACAO_PECAS);
		}
		if(query.getResultList().size() > 0){
		}
		return dataLiberacaoPecas;
	}
	public String findDataTerminoServico(EntityManager manager,
			GeCheckIn checkIn, String jobControl) {
		Query query;
		String dataTerminoServico = null;
		//query = manager.createQuery("From GeSegmento where  idCheckin.id =:idCheckin and jobControl =:jobControl and data_termino_servico is not null");
		query = manager.createNativeQuery("select convert(datetime, data_termino_servico, 103) From Ge_Segmento where  ID_CHECKIN =:idCheckin and job_Control =:jobControl and data_termino_servico is not null");
		query.setParameter("idCheckin", checkIn.getId());
		query.setParameter("jobControl", jobControl);
		if(query.getResultList().size() > 0){
			Date data_termino_servico = (Date)query.getResultList().get(0);
			dataTerminoServico = dateFormat.format(data_termino_servico);
		}
		return dataTerminoServico;
	}
				  
	public String findSituacaoOSBO(String numeroOs){
		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;
		try {
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();	
			statement = con.createStatement();
			String SQL = "select distinct CSCC from "+IConstantAccess.LIB_DBS+".PCPCOHD0 p, "+IConstantAccess.LIB_DBS+".WOPHDRS0 w"+
					" WHERE p.WONO = w.WONO"+
					" AND trim(p.WONO) = '"+numeroOs+"'"+
					" AND p.DOCSU = 'B'";
			rs = statement.executeQuery(SQL);
			String segmentos = "";
			while(rs.next()){
				segmentos += rs.getString("CSCC").trim()+"-";
			}
			if(segmentos.length() > 0){
				segmentos = segmentos.substring(0, segmentos.length() - 1);
				return "APROVADA BO "+segmentos;	
			}
//			if(rs.next()){
//				return "APROVADA BO";	
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(con != null){
					con.close();
					statement.close();
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		return "APROVADA";
	}


	public List <ChekinBean> findSituacaoOS (String colfindSituacaoOS){
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String SQL = "From " +
					" GeCheckIn ch , GeOsPalm os	";
					if(!colfindSituacaoOS.equals("R")){
						if(!colfindSituacaoOS.equals("'N'")){
							SQL += " where (ch.isCheckIn = " +colfindSituacaoOS+" or ch.isCheckIn is null)";
						}else{
							SQL += " where ch.isCheckIn = " +colfindSituacaoOS;
						}
					}else{
						SQL += " where ch.statusNegociacaoConsultor = '" +colfindSituacaoOS+"'";
					}
					//" and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)" +
					SQL +=" and os.idosPalm = ch.idOsPalm " +
					" and os.filial =:filial ";
			Query query = manager.createQuery(SQL);
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
				if(situacaoOsList.size() > 0){
					geSituacaoOs = situacaoOsList.get(0);
				}
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				String dataLiberacaoPecas = "N".equals(checkIn.getIsLiberadoPDigitador())?null:checkIn.getIsLiberadoPDigitador();
				String dataTerminoServico = (geSituacaoOs.getDataTerminoServico() != null)?dateFormat.format(geSituacaoOs.getDataTerminoServico()):null;
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				resulList.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	public List <ChekinBean> findSituacaoOSSupervisor (String colfindSituacaoOS){
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String SQL = "From " +
			" GeCheckIn ch , GeOsPalm os	";
			if(!colfindSituacaoOS.equals("R")){
				if(!colfindSituacaoOS.equals("'N'")){
					SQL += " where (ch.isCheckIn = " +colfindSituacaoOS+" or ch.isCheckIn is null)";
				}else{
					SQL += " where ch.isCheckIn = " +colfindSituacaoOS;
				}
			}else{
				SQL += " where ch.statusNegociacaoConsultor = '" +colfindSituacaoOS+"'";
			}
			//" and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)" +
			SQL +=" and os.idosPalm = ch.idOsPalm " +
			" and os.filial =:filial ";
			Query query = manager.createQuery(SQL);
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
				if(situacaoOsList.size() > 0){
					geSituacaoOs = situacaoOsList.get(0);
				}
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				String dataLiberacaoPecas = "N".equals(checkIn.getIsLiberadoPDigitador())?null:checkIn.getIsLiberadoPDigitador();
				String dataTerminoServico = geSituacaoOs.getDataTerminoServico() != null ? dateFormat.format(geSituacaoOs.getDataTerminoServico()): null;
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				resulList.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	public List <ChekinBean> findSituacaoOSObj (String colfindSituacaoOS, ChekinBean beanCh){
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String SQL = "From " +
			" GeCheckIn ch , GeOsPalm os	";
			if(!colfindSituacaoOS.equals("R")){
				if(!colfindSituacaoOS.equals("'N'")){
					SQL += " where (ch.isCheckIn = " +colfindSituacaoOS+" or ch.isCheckIn is null)";
				}else{
					SQL += " where ch.isCheckIn = " +colfindSituacaoOS;
				}
			}else{
				SQL += " where ch.statusNegociacaoConsultor = '" +colfindSituacaoOS+"'";
			}
			//" and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)" +
			SQL +=" and os.idosPalm = ch.idOsPalm " +
			" and ch.tipoCliente = '"+beanCh.getTipoCliente()+"' "+
			" and os.filial =:filial ";
			Query query = manager.createQuery(SQL);
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
				if(situacaoOsList.size() > 0){
					geSituacaoOs = situacaoOsList.get(0);
				}
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				String dataLiberacaoPecas = "N".equals(checkIn.getIsLiberadoPDigitador())?null:checkIn.getIsLiberadoPDigitador();
				String dataTerminoServico = (geSituacaoOs.getDataTerminoServico() != null)?dateFormat.format(geSituacaoOs.getDataTerminoServico()):null;
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				resulList.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	public List <ChekinBean> findSituacaoOSFaturamento (String col1findSituacaoOS)throws Exception{
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From " +
					" GeCheckIn ch , GeOsPalm os " +
					" where ch.id in (select s.idCheckin from GeSituacaoOs s where "+col1findSituacaoOS+" is not null )  " +
					//" and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)" +
					" and ch.numeroOs is not null" +
					" and os.idosPalm = ch.idOsPalm " +
			" and os.filial =:filial ");
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
				if(situacaoOsList.size() > 0){
					geSituacaoOs = situacaoOsList.get(0);
				}
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				String dataLiberacaoPecas = "N".equals(checkIn.getIsLiberadoPDigitador())?null:checkIn.getIsLiberadoPDigitador();
				String dataTerminoServico = (geSituacaoOs.getDataTerminoServico() != null)?dateFormat.format(geSituacaoOs.getDataTerminoServico()):null;
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				resulList.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	public List <ChekinBean> findSituacaoOSFaturamentoSupervisor (String col1findSituacaoOS)throws Exception{
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From " +
					" GeCheckIn ch , GeOsPalm os " +
					" where ch.id in (select s.idCheckin from GeSituacaoOs s where "+col1findSituacaoOS+" is not null )  " +
					//" and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)" +
					" and ch.numeroOs is not null" +
					" and os.idosPalm = ch.idOsPalm " +
			" and os.filial =:filial ");
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
				if(situacaoOsList.size() > 0){
					geSituacaoOs = situacaoOsList.get(0);
				}
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				String dataLiberacaoPecas = "N".equals(checkIn.getIsLiberadoPDigitador())?null:checkIn.getIsLiberadoPDigitador();
				String dataTerminoServico = geSituacaoOs.getDataTerminoServico() != null ? dateFormat.format(geSituacaoOs.getDataTerminoServico()): null;
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				resulList.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	public List <ChekinBean> findSituacaoOSFaturamento (String col1findSituacaoOS, String tipoCliente)throws Exception{
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From " +
					" GeCheckIn ch , GeOsPalm os " +
					" where ch.id in (select s.idCheckin from GeSituacaoOs s where "+col1findSituacaoOS+" is not null )  " +
					//" and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)" +
					" and ch.numeroOs is not null" +
					" and os.idosPalm = ch.idOsPalm " +
					" and ch.tipoCliente = '"+tipoCliente+"' "+
			" and os.filial =:filial ");
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = new GeSituacaoOs();
				if(situacaoOsList.size() > 0){
					geSituacaoOs = situacaoOsList.get(0);
				}
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				String dataLiberacaoPecas = "N".equals(checkIn.getIsLiberadoPDigitador())?null:checkIn.getIsLiberadoPDigitador();
				String dataTerminoServico = (geSituacaoOs.getDataTerminoServico() != null)?dateFormat.format(geSituacaoOs.getDataTerminoServico()):null;
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServico, this.bean);
				resulList.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}


//	private final Timer timer = new Timer();

//	public void startNewOsEstimada(final GestaoEquipamentosBean bean, final GeCheckIn checkIn, final EntityManager manager) {
//		timer.schedule(new TimerTask() {
//			public void run() {
//				if(createOsEstimadaThread(bean, checkIn)){
//					sendEmailSupervisor(manager, bean.getJobControl(), "Sr. supervisor, foi gerada uma nova OS para o seu Job Control.");					
//					timer.cancel();
//				}
//			}
//		},  30 * 1000);
//	}
	
	/**
	 * Envia e-mail para supervisor do JobContol selecionado ao ser criado uma nova OS.
	 * @param manager
	 */	
	public void sendEmailSupervisor(EntityManager manager, String jobcontrol, String msg){
		try {
			Query query = manager.createNativeQuery("SELECT email, EPLSNM FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+
					" WHERE tw.epidno = perfil.id_tw_usuario" +
					" AND tw.STN1 = '"+Integer.valueOf(this.bean.getFilial()).toString()+"'"+
					" AND perfil.job_control = '"+jobcontrol+"'" +
					" AND perfil.id_perfil = (select p.id from adm_perfil p where p.sigla = 'SUPER' and tipo_sistema = 'GE')");
			
			if(query.getResultList().size() > 0){
				List<Object[]> list = (List<Object[]>)query.getResultList();
				for(Object[] email : list){
					EmailHelper helper = new EmailHelper();
					helper.sendSimpleMail(email[1]+" "+msg, "Ordem de Serviço Sistema Oficina", (String)email[0]);
				}				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public GestaoEquipamentosBean newOsEstimada(GestaoEquipamentosBean bean){ //, SegmentoBean bean2
		bean.setMsg("");
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("From GeCheckIn where id = :id");
			query.setParameter("id", bean.getIdCheckIn());
			GeCheckIn checkIn = (GeCheckIn)query.getSingleResult();
			checkIn.setCodCliente(bean.getCodigoCliente()); 
			checkIn.setIdSupervisor(this.bean.getMatricula());
			//checkIn.setFatorUrgencia(bean.getVcc().getUrgencia());
			checkIn.setFatorUrgencia("S");
			checkIn.getIdOsPalm().setCliente(bean.getNomeCliente());
			checkIn.setTipoCliente(bean.getVcc().getTipoCliente());
			checkIn.setClienteInter(bean.getVcc().getClienteInter());
			checkIn.setContaContabilSigla(bean.getVcc().getContaContabilSigla());
			checkIn.setCentroCustoSigla(bean.getVcc().getCentroDeCustoSigla());
			checkIn.setSiglaIndicadorGarantia(bean.getVcc().getIndGarantia());
			checkIn.setJobControl(bean.getJobControl());
			checkIn.setCodErroDbs("100");
			checkIn.setNumeroOs("");
			String SQL = "";
			//if(checkIn.getTipoCliente() != null && checkIn.getTipoCliente().equals("INT")){
				Connection con = null;
				try {
					SQL = "select ic.IDCD01 from "+IConstantAccess.LIB_DBS+".SCPDIVF0 ic	where ic.CUNO = '"+bean.getCodigoCliente()+"' and ic.IDCD01 is not null and trim(ic.IDCD01) <> ''";
					con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
					Statement statement = con.createStatement();
					ResultSet rs = statement.executeQuery(SQL);
					
					if(rs.next()){
						String codigoSegmento = rs.getString("IDCD01");
							query = manager.createNativeQuery("select cli.fator  from ge_segmento_cliente cli where cli.codigo =:codigo order by cli.id desc");
							query.setParameter("codigo", codigoSegmento.trim());
							if(query.getResultList().size() > 0){
								checkIn.setFatorCliente((BigDecimal)query.getResultList().get(0));
							}			  
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally{
					try {
						if(con != null){
							con.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
//		}else{				
//			SQL = "select crm.cust_codigosegmento from MME.vw_crmsegmentoscontas_ds crm where  crm.cuno = '"+bean.getCodigoCliente()+"'";
//			query = manager.createNativeQuery(SQL);
//			if(query.getResultList().size() > 0){
//				String codigoSegmento = (String)query.getResultList().get(0);
//				query = manager.createNativeQuery("select cli.fator  from ge_segmento_cliente cli where cli.codigo =:codigo order by cli.id desc");
//				query.setParameter("codigo", codigoSegmento.trim());
//				if(query.getResultList().size() > 0){
//					checkIn.setFatorCliente((BigDecimal)query.getResultList().get(0));
//				}
//			}  
//		}
			
//		checkIn.setFatorCliente(BigDecimal.valueOf(1.25));
			
			//Connection con = null;
			try {
				con = ConectionDbs.getConnecton();
				SQL = "select f.IDNO1 from "+IConstantAccess.LIB_DBS+".empeqpd0 f where f.EQMFS2 = '"+bean.getNumeroSerie()+"'";
				Statement statement = con.createStatement();
				ResultSet rs = statement.executeQuery(SQL);
				if(rs.next()){
					String IDNO1 = rs.getString("IDNO1");
					if(IDNO1 != null){
						checkIn.setIdEquipamento(IDNO1.trim());
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} finally {
				if(con != null){
					try {
						con.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			
			if(checkIn.getCodErroDbs() != null && checkIn.getCodErroDbs().equals("99")){ 
				this.removerDadosInterfaceOs(checkIn.getId().toString(), bean.getBgrp());
			}
			
			if(this.verifyCreateOs(checkIn.getId().toString(), bean.getBgrp())){
			//	this.startNewOsEstimada(bean, checkIn, manager);
				return bean;
			}
			checkIn.getIdOsPalm().setHorimetro(Long.valueOf(bean.getHorimetro()));
			checkIn.getIdOsPalm().setSmu(bean.getHorimetro());
			manager.merge(checkIn);
			
			/*Formata Horimetro e data atual para enviar para o DBS*/
			String horimetro = bean.getHorimetro();			
			horimetro += "0";		
			
			while (horimetro.length() < 8){
				horimetro = "0"+ horimetro;
			}		
			String dataHorimetroDbs = dateFormatHorimetroDbs.format(new Date());
			
			manager.getTransaction().commit();
			//Cria uma os estimada no dbs
			if(this.createOsEstimada(checkIn.getId().toString(), bean.getSegmento(), bean.getJobControl(), 
					"E", bean.getJobControl(), (Integer.valueOf(this.bean.getFilial()) < 10)?"0"+Integer.valueOf(this.bean.getFilial()):this.bean.getFilial(), bean.getCodigoCliente(),
					 bean.getEstimateBy(), bean.getMake(), bean.getNumeroSerie(), "S", bean.getBgrp(), bean, checkIn.getTipoCliente() == null ? "" : checkIn.getTipoCliente(), checkIn.getIdOsPalm().getModelo(), checkIn.getSiglaIndicadorGarantia(), horimetro, dataHorimetroDbs)){
				//this.startNewOsEstimada(bean, checkIn, manager);
				Statement statement = null;
				ResultSet rs = null;
				try {
					con = ConectionDbs.getConnecton();
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyMMdd");
					
					SQL = "SELECT  WCLPIPS0.PIPNO  FROM "+IConstantAccess.LIB_DBS+".WCLPIPS0 WCLPIPS0"+
		                    "  where WCLPIPS0.EQMFSN = '"+bean.getNumeroSerie()+"'"+
		                    " and WCLPIPS0.wono = ''"+
		                    " and WCLPIPS0.endd8 > "+dateFormat.format(new Date());
					statement = con.createStatement();
					rs = statement.executeQuery(SQL); 
					String PIPNO = "";
					while(rs.next()){
						PIPNO += rs.getString("PIPNO")+",";
						
					}
					if(PIPNO.length() > 0){
						PIPNO += PIPNO.substring(0, PIPNO.length());
						bean.setMsg("OS enviada com sucesso!\nPOSSIBILIDADE DE PIP/PSP! Carta de Crédito "+PIPNO);
						manager.getTransaction().begin();
						checkIn.setPipno(PIPNO);
						manager.merge(checkIn);
						manager.getTransaction().commit();
					}
					if(bean.getVcc().getCodigoLiberacao() != null && !"".equals(bean.getVcc().getCodigoLiberacao())){
						query = manager.createNativeQuery("update GE_FORMULARIO_APROVACAO_OS set IS_USADO = 'S', ID_CHECK_IN = "+checkIn.getId()+" where id = "+bean.getVcc().getCodigoLiberacao());
						manager.getTransaction().begin();
						query.executeUpdate();
						checkIn.setIdOsAprovada(bean.getVcc().getCodigoLiberacao());
						manager.getTransaction().commit();
					}
//				manager.getTransaction().begin();
//				//checkIn.setIsCreateOS("S");
//				manager.merge(checkIn);
//				manager.getTransaction().commit();
					
				} catch (Exception e) {
					if(manager != null && manager.getTransaction().isActive()){
						manager.getTransaction().rollback();
					}
					e.printStackTrace();
				}finally{
					if(manager != null && manager.isOpen()){
						manager.close();					
					}				
					if(con != null){
						try {
							con.close();
							rs.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				}			
				return bean;
			}
			bean.setMsg("Não foi possível criar OS estimada!");
			return bean;
		} catch (NumberFormatException e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();					
			}	
		}
		bean.setMsg("Não foi possível criar OS estimada!");
		return bean;
	}
	
	
//	public static void main(String[] args) throws ParseException {
//		
//		String dateStr = "25/08/2012";
//		
//		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//		
//		Date date = format.parse(dateStr);
//		
//		System.out.println(date);
//		
//		SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
//		
//		System.out.println(format2.format(date));
//		
//		
//	}
	
//	public static void main(String[] args) {
//		 SimpleDateFormat dateFormatHorimetroDbs = new SimpleDateFormat("ddMMyy");
//		String horimetro = "1234567";
//		Date d = new Date(); 
//		
//		horimetro += "0";
//		
//		
//		while (horimetro.length() < 8){
//			horimetro = "0"+ horimetro;
//		}
//		
//		dateFormatHorimetroDbs.format(d);
//		
//		System.out.println(dateFormatHorimetroDbs.format(d));
//	}
	


	public Boolean createOsEstimada(String wonosm, String wosgno, String cscc, String ind,
			String respar, String stn1, String cuno, String estby, String eqmfcd, String eqmfsn, String shpfld, String bgrp, GestaoEquipamentosBean bean,String tipoCliente, String modelo, String indGarantia, String horimetro, String dataHorimetroDbs){


		Connection con = null;
		Statement statement = null;
		//String lbrate = "F"; // Caractere que serÃ¡ enviado junto com o total para o DBS para enviar o total para o DBS

		try {

			String pair = "'"+wonosm+"-O','"+cuno+"','"+respar+"','"+stn1+"','"+estby+"','"+eqmfcd+"','"+eqmfsn+"','"+bgrp+"','"+horimetro+"','"+dataHorimetroDbs+"'";
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			con.setAutoCommit(true);
			statement = con.createStatement();
			
			String SQL = "delete from "+IConstantAccess.AMBIENTE_DBS+".USPWOSM0 where wonosm = '"+wonosm+"-O'";// (wonosm, cuno, respar, stn1, estby, eqmfcd, eqmfsn, bgrp) values("+pair+")";
			statement.executeUpdate(SQL);
			
			statement = con.createStatement();
			SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPWOSM0 (wonosm, cuno, respar, stn1, estby, eqmfcd, eqmfsn, bgrp, smu, datal) values("+pair+")"; //
			statement.executeUpdate(SQL);
			
			SegmentoBusiness business = new SegmentoBusiness(this.bean);
			business.inserirSegmento(bean.getSegmentoList(), Long.valueOf(wonosm));
//			for (SegmentoBean segmentoBean : bean.getSegmentoList()) {
//				try {
//					String horas = segmentoBean.getHoras().replace(".", "");
//					horas = new Integer(Integer.valueOf(horas) * segmentoBean.getQtdComp()).toString();
//					if(horas.length() == 3){
//						horas = "00"+horas;
//					}else if(horas.length() == 4){
//						horas = "0"+horas;
//					}
//					if(Integer.valueOf(horas) == 0){
//						horas = "";
//					}
//					String qty = segmentoBean.getQtdComp().toString();
//					for(int i = segmentoBean.getQtdComp().toString().length(); i < 5; i++){
//						qty = "0"+qty;
//					}
//					String watpcd = "    ";
//					if(indGarantia != null && !("").equals(indGarantia)){
//						watpcd = indGarantia;
//					}
//					Map<String, String> map = this.findValorMaoDeObra(bean.getIdCheckIn(), modelo, segmentoBean.getNumeroSegmento(),tipoCliente);					
//					if(map != null && map.size() > 0 && !"0".equals(map.get(segmentoBean.getNumeroSegmento()).equals("0"))){
//						pair = "'"+wonosm+"-O','"+segmentoBean.getNumeroSegmento()+"','"+segmentoBean.getJbctStr()+"','"+segmentoBean.getJbcd()+"','"+segmentoBean.getCptcd()+"','"+ind+"','"+horas+"','"+lbrate+"','"+map.get(segmentoBean.getNumeroSegmento())+"', '"+qty+"', '"+watpcd+"','       '";
//						SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 (wonosm, wosgno, cscc, jbcd, cptcd, ind, frsthr, lbrate, lbamt, qty, watpcd, pipno) values("+pair+")";	
//					}else{
//						pair = "'"+wonosm+"-O','"+segmentoBean.getNumeroSegmento()+"','"+segmentoBean.getJbctStr()+"','"+segmentoBean.getJbcd()+"','"+segmentoBean.getCptcd()+"','"+ind+"','"+horas+"', '"+qty+"', '"+watpcd+"','       '";
//						SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 (wonosm, wosgno, cscc, jbcd, cptcd, ind, frsthr, qty, watpcd, pipno) values("+pair+")";
//					}
//					statement.executeUpdate(SQL);
//				} catch (Exception e) {
//					e.printStackTrace();
//					EntityManager manager = null;
//					
//					try {
//						manager = JpaUtil.getInstance();
//						manager.getTransaction().begin();
//						Query query = manager.createQuery("From GeSegmento where idCheckin.id =:id and numeroSegmento =:numeroSegmento");
//						query.setParameter("id", bean.getIdCheckIn());
//						query.setParameter("numeroSegmento", segmentoBean.getNumeroSegmento());
//						GeSegmento seg = (GeSegmento)query.getSingleResult();
//						seg.setMsgDbs("Erro ao enviar segmento para o DBS!");
//						seg.setCodErroDbs("99");
//						manager.merge(seg);
//						manager.getTransaction().commit();
//					} catch (Exception e1) {
//						if(manager != null && manager.getTransaction().isActive()){
//							manager.getTransaction().rollback();
//						}
//						e1.printStackTrace();
//					} finally {
//						if(manager != null){
//							manager.close();
//						}
//					}
//				}
//			} 
			return true;

		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				if(con != null){
					con.close();
					if(statement != null){
						statement.close();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}	
	
	public Map<String, String> findValorMaoDeObra(Long idChekin, String modelo, String numSegmento, String tipoCliente, Long id_familia){
		EntityManager manager = null;
		Query query  = null;
		try {
			manager = JpaUtil.getInstance();
			if(tipoCliente != null && tipoCliente.equals("EXT")){		
			
			String SQL = "select "+
				" 	 (select (((((pre.valor_de_venda * ch.fator_cliente)*"+
		        " (select c.fator from ge_complexidade c where c.id ="+
		        " (select p.id_complexidade from ge_preco p where p.id_modelo = pre.id_modelo"+
		        " and p.id_prefixo = pre.id "+
		        " and p.comp_code = seg.com_code"+
		        " and p.job_code = seg.job_code ))"+ 
		         
		        " * CASE WHEN ch.fator_urgencia = 'S'"+
		        " THEN (select fator_urgencia from ge_fator) ELSE 1 END)))"+  
		         
		        " * convert(decimal(10,2),seg.horas_prevista))"+
		         
		        " from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv"+
		        " where ch.id = seg1.id_checkin"+
		        " and ch.id = seg.id_checkin"+
		        " and ch.id_os_palm = palm.idos_palm"+
		        " and pre.descricao = substring(palm.serie,0,5)"+
		        " and arv.descricao = '"+modelo+"'"+
		        " and pre.id_modelo = arv.id_arv" +
		        " and arv.ID_FAMILIA = "+id_familia+ 
		        " and seg.id = seg1.id)  total, seg1.numero_segmento"+
		        " from ge_segmento seg1"+
		        " where seg1.id_checkin = "+idChekin+
		        " and seg1.NUMERO_SEGMENTO = '"+numSegmento+"'";
				
				
			query = manager.createNativeQuery (SQL);	
//			query = manager.createNativeQuery ("select x.total, x.numero_segmento from(select seg1.id,seg1.numero_segmento, seg1.descricao_job_code||' / '||seg1.descricao_comp_code descricao, 0 id_operacao, null id_doc_seg_oper, seg1.qtd_comp, seg1.horas_prevista, "+
//					" (select (((((pre.valor_de_venda * ch.fator_cliente)*"+
//			        " (select c.fator from ge_complexidade c where c.id ="+
//			        " (select p.id_complexidade from ge_preco p where p.id_modelo = pre.id_modelo"+
//			        " and p.id_prefixo = pre.id "+
//			        " and p.comp_code = seg.com_code"+
//			        " and p.job_code = seg.job_code )) * decode(ch.fator_urgencia,'S',"+
//			        " (select fator_urgencia from ge_fator), 1))))  * TO_NUMBER(replace(seg.horas_prevista, '.',',')))"+
//			        " from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv"+
//			        " where ch.id = seg1.id_checkin"+
//			        " and ch.id = seg.id_checkin"+
//			        " and ch.id_os_palm = palm.idos_palm"+
//			        " and pre.descricao = substr(palm.serie,0,4)" +
//			        " and arv.descricao = '"+modelo+"'"+
//			        " and pre.id_modelo = arv.id_arv "+
//			        " and seg.id = seg1.id)  total"+
//			        " from ge_segmento seg1"+
//			        " where seg1.id not in (select id_segmento from ge_doc_seg_oper)"+
//			        " and seg1.id_checkin = "+idChekin+
//			        " union"+
//			        " select seg2.id,seg2.numero_segmento, seg2.descricao_job_code||' / '||seg2.descricao_comp_code descricao, decode(oper.id_operacao, null,0,oper.id_operacao), oper.id_doc_seg_oper id_doc_seg_oper, seg2.qtd_comp, seg2.horas_prevista,"+
//			        " (select (((((pre.valor_de_venda * ch.fator_cliente)*"+
//			        " (select c.fator from ge_complexidade c"+
//			        " where c.id = (select p.id_complexidade from ge_preco p where p.id_modelo = pre.id_modelo"+
//			        " and p.id_prefixo = pre.id"+
//			        " and p.comp_code = seg.com_code"+
//			        " and p.job_code = seg.job_code )) * decode(ch.fator_urgencia,'S', (select fator_urgencia from ge_fator), 1))))  * TO_NUMBER(replace(seg.horas_prevista, '.',',')))"+
//			        " from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv"+
//			        " where ch.id = seg2.id_checkin"+
//			        " and ch.id = seg.id_checkin"+
//			        " and ch.id_os_palm = palm.idos_palm"+
//			        " and pre.descricao = substr(palm.serie,0,4)"+
//			        " and arv.descricao = '"+modelo+"'"+
//			        " and pre.id_modelo = arv.id_arv "+
//			        " and seg.id = seg2.id) total"+
//					" from ge_segmento seg2,(select distinct s.id, op.id_operacao, max(op.id) id_doc_seg_oper"+
//					" from ge_segmento s left join ge_doc_seg_oper op on op.id_segmento = s.id"+
//					" where s.id_checkin = "+idChekin+
//					" group by s.id , op.id_operacao) oper"+
//					" where oper.id = seg2.id"+
//					" order by 2) x" +
//					" where x.numero_segmento = '"+numSegmento+"'" );
			
			
//			select x.total, x.numero_segmento from(select seg1.id,seg1.numero_segmento, seg1.descricao_job_code+' / '+seg1.descricao_comp_code as descricao, 0 id_operacao, null id_doc_seg_oper, seg1.qtd_comp, seg1.horas_prevista, 
//				 	 (select (((((pre.valor_de_venda * ch.fator_cliente)*
//		         (select c.fator from ge_complexidade c where c.id =
//		         (select p.id_complexidade from ge_preco p where p.id_modelo = pre.id_modelo
//		         and p.id_prefixo = pre.id 
//		         and p.comp_code = seg.com_code
//		         and p.job_code = seg.job_code )) 
//		         
//		         * CASE WHEN ch.fator_urgencia = 'S'
//		         THEN (select fator_urgencia from ge_fator) ELSE 1 END)))  
//		         
//		         * convert(decimal(10,2),seg.horas_prevista))
//		         
//		         from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv
//		         where ch.id = seg1.id_checkin
//		         and ch.id = seg.id_checkin
//		         and ch.id_os_palm = palm.idos_palm
//		         and pre.descricao = substring(palm.serie,0,5)
//		         and arv.descricao = '120KQ'
//		         and pre.id_modelo = arv.id_arv 
//		         and seg.id = seg1.id)  total
//		         from ge_segmento seg1
//		         where seg1.id not in (select id_segmento from ge_doc_seg_oper)
//		         and seg1.id_checkin = 5
//		         union
//		         select seg2.id,seg2.numero_segmento, seg2.descricao_job_code+' / '+seg2.descricao_comp_code as descricao, (CASE WHEN oper.id_operacao = null THEN 0 ELSE oper.id_operacao END) as operacao, oper.id_doc_seg_oper as id_doc_seg_oper, seg2.qtd_comp, seg2.horas_prevista,
//		         (select (((((pre.valor_de_venda * "+fatorCliente+")*
//		         (select c.fator from ge_complexidade c
//		         where c.id = (select p.id_complexidade from ge_preco p where p.id_modelo = pre.id_modelo
//		         and p.id_prefixo = pre.id
//		         and p.comp_code = seg.com_code
//		         and p.job_code = seg.job_code )) * 
//		         
//		         
//		         CASE WHEN ch.fator_urgencia = 'S' THEN (select fator_urgencia from ge_fator) ELSE 1 END)))  
//		         
//		         
//		         * convert(decimal(10,2),seg.horas_prevista))
//		         from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv
//		         where ch.id = seg2.id_checkin
//		         and ch.id = seg.id_checkin
//		         and ch.id_os_palm = palm.idos_palm
//		         and pre.descricao = substring(palm.serie,0,5)
//		         and arv.descricao = '120KQ'
//		         and pre.id_modelo = arv.id_arv 
//		         and seg.id = seg2.id) total
//				 from ge_segmento seg2,(select distinct s.id, op.id_operacao, max(op.id) id_doc_seg_oper
//				 from ge_segmento s left join ge_doc_seg_oper op on op.id_segmento = s.id
//				 where s.id_checkin = 5
//				 and op.ID_OPERACAO is not null
//				 group by s.id , op.id_operacao) oper
//				 where oper.id = seg2.id
//				 ) x
//				 where x.numero_segmento = '01'
//				 and x.total is not null
//				 Order By x.numero_segmento
//			
			}else{
				String SQL = "select "+
			          " (select ((select f.valor_inter from ge_fator f) * (convert(decimal(10,2),seg.horas_prevista)))"+
					" from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv"+
					"  where ch.id = seg1.id_checkin"+
					"  and ch.id = seg.id_checkin"+
					" and ch.id_os_palm = palm.idos_palm"+
					" and pre.descricao = substring(palm.serie,0,5)"+
					" and arv.descricao = '"+modelo+"'"+
					" and pre.id_modelo = arv.id_arv"+ 
					" and arv.ID_FAMILIA = "+id_familia+ 
					" and seg.id = seg1.id) as  total, seg1.numero_segmento"+
					" from ge_segmento seg1"+
					" where seg1.id_checkin = "+idChekin+
					" and seg1.numero_segmento = '"+numSegmento+"'";
				query = manager.createNativeQuery (SQL);
//				query = manager.createNativeQuery ("select x.total, x.numero_segmento from(select seg1.id,seg1.numero_segmento, seg1.descricao_job_code||' / '||seg1.descricao_comp_code descricao, 0 id_operacao, null id_doc_seg_oper, seg1.qtd_comp, seg1.horas_prevista,"+ 
//					 " (select "+
//			         " ((select f.valor_inter from ge_fator f) * (TO_NUMBER(replace(seg.horas_prevista, '.',','))))"+
//			         " from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv"+
//			         " where ch.id = seg1.id_checkin"+
//			         " and ch.id = seg.id_checkin"+
//			         " and ch.id_os_palm = palm.idos_palm"+
//			         " and pre.descricao = substr(palm.serie,0,4)"+
//			         " and arv.descricao = '"+modelo+"'"+
//			         " and pre.id_modelo = arv.id_arv"+ 
//			         " and seg.id = seg1.id)  total"+
//			         " from ge_segmento seg1"+
//			         //" where seg1.id not in (select id_segmento from ge_doc_seg_oper)"+
//			         " where seg1.id_checkin = "+idChekin+
//			         " " +
//			         " union"+
//			         " select seg2.id,seg2.numero_segmento, seg2.descricao_job_code||' / '||seg2.descricao_comp_code descricao, decode(oper.id_operacao, null,0,oper.id_operacao), oper.id_doc_seg_oper id_doc_seg_oper, seg2.qtd_comp, seg2.horas_prevista,"+
//			         " (select (pre.valor_de_custo *"+
//			         " (TO_NUMBER(replace(seg.horas_prevista, '.',','))))"+
//			         " from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv"+
//			         " where ch.id = seg2.id_checkin"+
//			         " and ch.id = seg.id_checkin"+
//			         " and ch.id_os_palm = palm.idos_palm"+
//			         " and pre.descricao = substr(palm.serie,0,4)"+
//			         " and arv.descricao = '"+modelo+"'"+
//			         " and pre.id_modelo = arv.id_arv"+ 
//			         " and seg.id = seg2.id) total"+
//					 " from ge_segmento seg2,(select distinct s.id, op.id_operacao, max(op.id) id_doc_seg_oper"+
//					 " from ge_segmento s left join ge_doc_seg_oper op on op.id_segmento = s.id"+
//					 " where s.id_checkin = "+idChekin+
//					 " group by s.id , op.id_operacao) oper"+
//					 " where oper.id = seg2.id"+
//					 " order by 2) x"+
//					 " where x.numero_segmento = '"+numSegmento+"'" +
//					 " and x.total is not null" );						 
			}
			List<Object[]> list = query.getResultList();
			Map<String, String> map = new HashMap<String, String>();
//			BigDecimal b = BigDecimal.valueOf(365,57);
//			String numSeg = "0";
//			int j = 1;
			for (Object[] object : list){
				if(object[0] == null){
					return null;
					
				} 				
				//String totalMaoObra = b.toString();
				//String totalMaoObraDBS = totalMaoObra.toString().replace(",", "");
				//String totalMaoObra = ((BigDecimal)object[0]).toString();//
				String totalMaoObra = "";
				//String totalMaoObraDBS = totalMaoObra.toString().replace(".", "");	
				BigDecimal bd = ((BigDecimal)object[0]);
				int decimalPlace = 2;
				bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
				totalMaoObra = bd.toString().replace(".", "");
				for (int i = totalMaoObra.length();  i < 13; i++) {
					totalMaoObra = "0"+totalMaoObra;
				}
				//map.put((String)b, totalMaoObraDBS);
				map.put((String) object[1], totalMaoObra);
//				j++;
			}
		
			return map;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}
	public Map<String, String> findValorMaoDeObraSubst(Long idChekin, String modelo, String numSegmento, String tipoCliente, Long id_familia){
		EntityManager manager = null;
		Query query  = null;
		try {
			manager = JpaUtil.getInstance();
			if(tipoCliente != null && tipoCliente.equals("EXT")){		
				
				String SQL = "select "+
				" 	 (select (((((pre.valor_de_venda * ch.fator_cliente)*"+
				" (select c.fator from ge_complexidade c where c.id ="+
				" (select p.id_complexidade from ge_preco p where p.id_modelo = pre.id_modelo"+
				" and p.id_prefixo = pre.id "+
				" and p.comp_code = seg.com_code"+
				" and p.job_code = seg.job_code ))"+ 
				
				" * CASE WHEN ch.fator_urgencia = 'S'"+
				" THEN (select fator_urgencia from ge_fator) ELSE 1 END)))"+  
				
				" * convert(decimal(10,2),seg.HORAS_PREVISTA_SUBST))"+
				
				" from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv"+
				" where ch.id = seg1.id_checkin"+
				" and ch.id = seg.id_checkin"+
				" and ch.id_os_palm = palm.idos_palm"+
				" and pre.descricao = substring(palm.serie,0,5)"+
				" and arv.descricao = '"+modelo+"'"+
				" and pre.id_modelo = arv.id_arv" +
				" and arv.ID_FAMILIA = "+id_familia+ 
				" and seg.id = seg1.id)  total, seg1.numero_segmento"+
				" from ge_segmento seg1"+
				" where seg1.id_checkin = "+idChekin+
				" and seg1.NUMERO_SEGMENTO = '"+numSegmento+"'";
				
				
				query = manager.createNativeQuery (SQL);	
//			query = manager.createNativeQuery ("select x.total, x.numero_segmento from(select seg1.id,seg1.numero_segmento, seg1.descricao_job_code||' / '||seg1.descricao_comp_code descricao, 0 id_operacao, null id_doc_seg_oper, seg1.qtd_comp, seg1.horas_prevista, "+
//					" (select (((((pre.valor_de_venda * ch.fator_cliente)*"+
//			        " (select c.fator from ge_complexidade c where c.id ="+
//			        " (select p.id_complexidade from ge_preco p where p.id_modelo = pre.id_modelo"+
//			        " and p.id_prefixo = pre.id "+
//			        " and p.comp_code = seg.com_code"+
//			        " and p.job_code = seg.job_code )) * decode(ch.fator_urgencia,'S',"+
//			        " (select fator_urgencia from ge_fator), 1))))  * TO_NUMBER(replace(seg.horas_prevista, '.',',')))"+
//			        " from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv"+
//			        " where ch.id = seg1.id_checkin"+
//			        " and ch.id = seg.id_checkin"+
//			        " and ch.id_os_palm = palm.idos_palm"+
//			        " and pre.descricao = substr(palm.serie,0,4)" +
//			        " and arv.descricao = '"+modelo+"'"+
//			        " and pre.id_modelo = arv.id_arv "+
//			        " and seg.id = seg1.id)  total"+
//			        " from ge_segmento seg1"+
//			        " where seg1.id not in (select id_segmento from ge_doc_seg_oper)"+
//			        " and seg1.id_checkin = "+idChekin+
//			        " union"+
//			        " select seg2.id,seg2.numero_segmento, seg2.descricao_job_code||' / '||seg2.descricao_comp_code descricao, decode(oper.id_operacao, null,0,oper.id_operacao), oper.id_doc_seg_oper id_doc_seg_oper, seg2.qtd_comp, seg2.horas_prevista,"+
//			        " (select (((((pre.valor_de_venda * ch.fator_cliente)*"+
//			        " (select c.fator from ge_complexidade c"+
//			        " where c.id = (select p.id_complexidade from ge_preco p where p.id_modelo = pre.id_modelo"+
//			        " and p.id_prefixo = pre.id"+
//			        " and p.comp_code = seg.com_code"+
//			        " and p.job_code = seg.job_code )) * decode(ch.fator_urgencia,'S', (select fator_urgencia from ge_fator), 1))))  * TO_NUMBER(replace(seg.horas_prevista, '.',',')))"+
//			        " from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv"+
//			        " where ch.id = seg2.id_checkin"+
//			        " and ch.id = seg.id_checkin"+
//			        " and ch.id_os_palm = palm.idos_palm"+
//			        " and pre.descricao = substr(palm.serie,0,4)"+
//			        " and arv.descricao = '"+modelo+"'"+
//			        " and pre.id_modelo = arv.id_arv "+
//			        " and seg.id = seg2.id) total"+
//					" from ge_segmento seg2,(select distinct s.id, op.id_operacao, max(op.id) id_doc_seg_oper"+
//					" from ge_segmento s left join ge_doc_seg_oper op on op.id_segmento = s.id"+
//					" where s.id_checkin = "+idChekin+
//					" group by s.id , op.id_operacao) oper"+
//					" where oper.id = seg2.id"+
//					" order by 2) x" +
//					" where x.numero_segmento = '"+numSegmento+"'" );
				
				
//			select x.total, x.numero_segmento from(select seg1.id,seg1.numero_segmento, seg1.descricao_job_code+' / '+seg1.descricao_comp_code as descricao, 0 id_operacao, null id_doc_seg_oper, seg1.qtd_comp, seg1.horas_prevista, 
//				 	 (select (((((pre.valor_de_venda * ch.fator_cliente)*
//		         (select c.fator from ge_complexidade c where c.id =
//		         (select p.id_complexidade from ge_preco p where p.id_modelo = pre.id_modelo
//		         and p.id_prefixo = pre.id 
//		         and p.comp_code = seg.com_code
//		         and p.job_code = seg.job_code )) 
//		         
//		         * CASE WHEN ch.fator_urgencia = 'S'
//		         THEN (select fator_urgencia from ge_fator) ELSE 1 END)))  
//		         
//		         * convert(decimal(10,2),seg.horas_prevista))
//		         
//		         from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv
//		         where ch.id = seg1.id_checkin
//		         and ch.id = seg.id_checkin
//		         and ch.id_os_palm = palm.idos_palm
//		         and pre.descricao = substring(palm.serie,0,5)
//		         and arv.descricao = '120KQ'
//		         and pre.id_modelo = arv.id_arv 
//		         and seg.id = seg1.id)  total
//		         from ge_segmento seg1
//		         where seg1.id not in (select id_segmento from ge_doc_seg_oper)
//		         and seg1.id_checkin = 5
//		         union
//		         select seg2.id,seg2.numero_segmento, seg2.descricao_job_code+' / '+seg2.descricao_comp_code as descricao, (CASE WHEN oper.id_operacao = null THEN 0 ELSE oper.id_operacao END) as operacao, oper.id_doc_seg_oper as id_doc_seg_oper, seg2.qtd_comp, seg2.horas_prevista,
//		         (select (((((pre.valor_de_venda * "+fatorCliente+")*
//		         (select c.fator from ge_complexidade c
//		         where c.id = (select p.id_complexidade from ge_preco p where p.id_modelo = pre.id_modelo
//		         and p.id_prefixo = pre.id
//		         and p.comp_code = seg.com_code
//		         and p.job_code = seg.job_code )) * 
//		         
//		         
//		         CASE WHEN ch.fator_urgencia = 'S' THEN (select fator_urgencia from ge_fator) ELSE 1 END)))  
//		         
//		         
//		         * convert(decimal(10,2),seg.horas_prevista))
//		         from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv
//		         where ch.id = seg2.id_checkin
//		         and ch.id = seg.id_checkin
//		         and ch.id_os_palm = palm.idos_palm
//		         and pre.descricao = substring(palm.serie,0,5)
//		         and arv.descricao = '120KQ'
//		         and pre.id_modelo = arv.id_arv 
//		         and seg.id = seg2.id) total
//				 from ge_segmento seg2,(select distinct s.id, op.id_operacao, max(op.id) id_doc_seg_oper
//				 from ge_segmento s left join ge_doc_seg_oper op on op.id_segmento = s.id
//				 where s.id_checkin = 5
//				 and op.ID_OPERACAO is not null
//				 group by s.id , op.id_operacao) oper
//				 where oper.id = seg2.id
//				 ) x
//				 where x.numero_segmento = '01'
//				 and x.total is not null
//				 Order By x.numero_segmento
//			
			}else{
				String SQL = "select "+
				" (select ((select f.valor_inter from ge_fator f) * (convert(decimal(10,2),seg.HORAS_PREVISTA_SUBST)))"+
				" from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv"+
				"  where ch.id = seg1.id_checkin"+
				"  and ch.id = seg.id_checkin"+
				" and ch.id_os_palm = palm.idos_palm"+
				" and pre.descricao = substring(palm.serie,0,5)"+
				" and arv.descricao = '"+modelo+"'"+
				" and pre.id_modelo = arv.id_arv"+ 
				" and arv.ID_FAMILIA = "+id_familia+ 
				" and seg.id = seg1.id) as  total, seg1.numero_segmento"+
				" from ge_segmento seg1"+
				" where seg1.id_checkin = "+idChekin+
				" and seg1.numero_segmento = '"+numSegmento+"'";
				query = manager.createNativeQuery (SQL);
//				query = manager.createNativeQuery ("select x.total, x.numero_segmento from(select seg1.id,seg1.numero_segmento, seg1.descricao_job_code||' / '||seg1.descricao_comp_code descricao, 0 id_operacao, null id_doc_seg_oper, seg1.qtd_comp, seg1.horas_prevista,"+ 
//					 " (select "+
//			         " ((select f.valor_inter from ge_fator f) * (TO_NUMBER(replace(seg.horas_prevista, '.',','))))"+
//			         " from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv"+
//			         " where ch.id = seg1.id_checkin"+
//			         " and ch.id = seg.id_checkin"+
//			         " and ch.id_os_palm = palm.idos_palm"+
//			         " and pre.descricao = substr(palm.serie,0,4)"+
//			         " and arv.descricao = '"+modelo+"'"+
//			         " and pre.id_modelo = arv.id_arv"+ 
//			         " and seg.id = seg1.id)  total"+
//			         " from ge_segmento seg1"+
//			         //" where seg1.id not in (select id_segmento from ge_doc_seg_oper)"+
//			         " where seg1.id_checkin = "+idChekin+
//			         " " +
//			         " union"+
//			         " select seg2.id,seg2.numero_segmento, seg2.descricao_job_code||' / '||seg2.descricao_comp_code descricao, decode(oper.id_operacao, null,0,oper.id_operacao), oper.id_doc_seg_oper id_doc_seg_oper, seg2.qtd_comp, seg2.horas_prevista,"+
//			         " (select (pre.valor_de_custo *"+
//			         " (TO_NUMBER(replace(seg.horas_prevista, '.',','))))"+
//			         " from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre, ge_arv_inspecao arv"+
//			         " where ch.id = seg2.id_checkin"+
//			         " and ch.id = seg.id_checkin"+
//			         " and ch.id_os_palm = palm.idos_palm"+
//			         " and pre.descricao = substr(palm.serie,0,4)"+
//			         " and arv.descricao = '"+modelo+"'"+
//			         " and pre.id_modelo = arv.id_arv"+ 
//			         " and seg.id = seg2.id) total"+
//					 " from ge_segmento seg2,(select distinct s.id, op.id_operacao, max(op.id) id_doc_seg_oper"+
//					 " from ge_segmento s left join ge_doc_seg_oper op on op.id_segmento = s.id"+
//					 " where s.id_checkin = "+idChekin+
//					 " group by s.id , op.id_operacao) oper"+
//					 " where oper.id = seg2.id"+
//					 " order by 2) x"+
//					 " where x.numero_segmento = '"+numSegmento+"'" +
//					 " and x.total is not null" );						 
			}
			List<Object[]> list = query.getResultList();
			Map<String, String> map = new HashMap<String, String>();
//			BigDecimal b = BigDecimal.valueOf(365,57);
//			String numSeg = "0";
//			int j = 1;
			for (Object[] object : list){
				if(object[0] == null){
					return null;
					
				} 				
				//String totalMaoObra = b.toString();
				//String totalMaoObraDBS = totalMaoObra.toString().replace(",", "");
				//String totalMaoObra = ((BigDecimal)object[0]).toString();//
				String totalMaoObra = "";
				//String totalMaoObraDBS = totalMaoObra.toString().replace(".", "");	
				BigDecimal bd = ((BigDecimal)object[0]);
				int decimalPlace = 2;
				bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
				totalMaoObra = bd.toString().replace(".", "");
				for (int i = totalMaoObra.length();  i < 13; i++) {
					totalMaoObra = "0"+totalMaoObra;
				}
				//map.put((String)b, totalMaoObraDBS);
				map.put((String) object[1], totalMaoObra);
//				j++;
			}
			
			return map;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw e;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}
//	public static void main( String[] args ) {
//	      Object r = 3.1567;
//	       int decimalPlace = 2;
//	       String rs = (r).toString();
//	       rs = rs.toString().replace(".", "");	       
//	       BigDecimal bd = new BigDecimal(rs);
//	       bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
//	       r = bd.doubleValue();
//	       System.out.println(r); // output is 3.15
//	     
//	
//	}


	private Boolean createOsEstimadaThread(GestaoEquipamentosBean bean, GeCheckIn checkIn) {

		EntityManager manager = null;
		List<GeSegmento> segmentoList = new ArrayList<GeSegmento>();
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();			 
			String wono = this.findOsEstimada(checkIn.getId().toString(), bean);
			GeCheckIn checkInObj = manager.find(GeCheckIn.class, bean.getIdCheckIn());
			GeSituacaoOs situacaoObj = new GeSituacaoOs();			

			if(wono == null){
				try {
					if(checkInObj.getNumeroOs() == null || "".equals(checkInObj.getNumeroOs())){ 
						return true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return true;
			}
			if("".equals(wono)){
				return false;
			}else{
				checkInObj.setNumeroOs(wono.trim());
				manager.merge(checkInObj);//
				situacaoObj.setDataChegada(new Date());
				situacaoObj.setNumeroOs(wono.trim());
				situacaoObj.setFilial(Long.valueOf(bean.getFilial()));
				situacaoObj.setIdCheckin(checkInObj);
				situacaoObj.setIsCheckOut("N");
				manager.merge(situacaoObj);//
				//INCLUIR A DATA DE INICIO NA TABELA GE_DATA SETAR DATA INICIO (ATUAL)
				Connection con = null;

//				InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//				Properties prop = new Properties();
				try {
//					prop.load(in);
//					String url = prop.getProperty("dbs.url");
//					String user = prop.getProperty("dbs.user");
//					String password =prop.getProperty("dbs.password");
//					Class.forName(prop.getProperty("dbs.driver")).newInstance();
					con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
					Statement statement = con.createStatement();


					//manager.getTransaction().begin();
					Query query = manager.createQuery("from GeSegmento where idCheckin.id =:id");
					query.setParameter("id", checkInObj.getId());
					segmentoList = query.getResultList();
					for (GeSegmento segmentoBean : segmentoList) {
						for (GeOperacao operacaoBean : segmentoBean.getGeOperacaoCollection()) {
							String pair = "'"+operacaoBean.getId()+"','"+checkInObj.getNumeroOs()+"','"+segmentoBean.getNumeroSegmento()+"','"+operacaoBean.getNumOperacao()+"','"+operacaoBean.getJbcd()+"','"+operacaoBean.getCptcd()+"'";
							String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPOPSM0 (wonosm, wono, wosgno, woopno, jbcd, cptcd) values("+pair+")"; //
							statement.executeUpdate(SQL);
						}
						segmentoBean.setHasSendDbs("S");
					}
					manager.getTransaction().commit();
				}catch (Exception e) {
					if(manager != null && manager.getTransaction().isActive()){
						manager.getTransaction().rollback();
					}
					e.printStackTrace();
					return false;
				}finally{					
					try {
						con.close();
						if(manager != null & manager.isOpen()){
							manager.close();
						}
					} catch (SQLException e) {
						if(manager != null && manager.getTransaction().isActive()){
							manager.getTransaction().rollback();
						}
						e.printStackTrace();
					}
				}
			}

			if(bean.getVcc().getTipoCliente() != null && !bean.getVcc().getTipoCliente().equals("")){
				if(bean.getVcc().getTipoCliente().equals("INT")){
					if(!this.updateIntoClienteInterOrExcecaoGarantiaDbs(wono,bean.getVcc().getClienteInter(), bean.getVcc().getContaContabilSigla(), bean.getVcc().getCentroDeCustoSigla(),segmentoList,checkInObj.getId().toString(),null)){
						bean.setMsg("A Ordem de serviço extimada foi criada, mas não foi possível cadastrar centro de custo e conta contábil!");
					}
				}else if(bean.getVcc().getTipoCliente().equals("EXT")){
					if(!this.updateGarantiaClienteExternoDbs(wono, bean.getVcc().getContaContabilSigla(), bean.getVcc().getCentroDeCustoSigla(),segmentoList.get(0).getNumeroSegmento(),checkInObj.getId().toString())){
						bean.setMsg("A Ordem de serviço extimada foi criada, mas não foi possível cadastrar centro de custo e conta contábil!");
					}
				}
			}else{
				//significa que Ã© garantica com a regra de excessÃ£o
				if(!("").equals(bean.getVcc().getClienteInter())){
					if(!this.updateIntoClienteInterOrExcecaoGarantiaDbs(wono,bean.getVcc().getClienteInter(), bean.getVcc().getContaContabilSigla(), bean.getVcc().getCentroDeCustoSigla(),segmentoList,checkInObj.getId().toString(), null)){
						bean.setMsg("A Ordem de serviço extimada foi criada, mas não foi possível cadastrar centro de custo e conta contábil!");
					}
				}else{//grantia sem regra de excessÃ£o
					if(!this.updateGarantiaClienteExternoDbs(wono, bean.getVcc().getContaContabilSigla(), bean.getVcc().getCentroDeCustoSigla(),segmentoList.get(0).getNumeroSegmento(),checkInObj.getId().toString())){
						bean.setMsg("A Ordem de serviço extimada foi criada, mas não foi possível cadastrar centro de custo e conta contábil!");
					}
				}
			}

			//manager.getTransaction().commit();	
		}catch (Exception e) {
			manager.getTransaction().rollback();
			e.printStackTrace();
		}
		return true;
	}
	public boolean updateIntoClienteInterOrExcecaoGarantiaDbs(String wono, String clienteInter, String contaContabilSigla, String centroDeCustoSigla, List<GeSegmento> segmentoList, String wonosm, List<SegmentoBean> segmentoBeanList){
		Connection con = null;

//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();
		try {
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();

			//			String url = "jdbc:as400://192.168.128.146";
			//			String user = "XUPU15PSS";
			//			String password = "marcosa";
			//			Class.forName("com.ibm.as400.access.AS400JDBCDriver").newInstance();
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			if(segmentoList != null){
				for (GeSegmento geSegmento : segmentoList) {
					Statement statement = con.createStatement();
					String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 (LBCUNO, MSCUNO, PTCUNO, PTDOLP, LBDOLP, MSDOLP, WONO, WOSGNO, WONOSM) values('"+clienteInter+"', '"+clienteInter+"', '"+clienteInter+"','100', '100', '100','"+wono+"','"+geSegmento.getNumeroSegmento()+"','"+wonosm+"-OF')";
					//" where GS.WONO = '"+wono+"'";
					statement.executeUpdate(SQL);
//					statement = con.createStatement();
//					statement.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 where WONOSM = '"+wonosm+"-OF'");
					statement = con.createStatement();
					SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 (CONTA, WONO, WOSGNO, WONOSM) values( '"+contaContabilSigla+"          "+centroDeCustoSigla+"', '"+wono+"','"+geSegmento.getNumeroSegmento()+"','"+wonosm+"-OFCC')";
					//" where WONO = '"+wono+"'";
					statement.executeUpdate(SQL);	
//					statement = con.createStatement();
//					statement.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 where WONOSM = '"+wonosm+"-OF'");	
				}
			}else{
				for (SegmentoBean geSegmento : segmentoBeanList) {
					Statement statement = con.createStatement();
					String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 (LBCUNO, MSCUNO, PTCUNO, PTDOLP, LBDOLP, MSDOLP, WONO, WOSGNO, WONOSM) values('"+clienteInter+"', '"+clienteInter+"', '"+clienteInter+"','100', '100', '100','"+wono+"','"+geSegmento.getNumeroSegmento()+"','"+wonosm+"-OF')";
					//" where GS.WONO = '"+wono+"'";
					statement.executeUpdate(SQL);
//					statement = con.createStatement();
//					statement.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 where WONOSM = '"+wonosm+"-OF'");
					statement = con.createStatement();
					SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 (CONTA, WONO, WOSGNO, WONOSM) values( '"+contaContabilSigla+"          "+centroDeCustoSigla+"', '"+wono+"','"+geSegmento.getNumeroSegmento()+"','"+wonosm+"-OFCC')";
					//" where WONO = '"+wono+"'";
					statement.executeUpdate(SQL);	
//					statement = con.createStatement();
//					statement.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 where WONOSM = '"+wonosm+"-OF'");	
				}
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public boolean updateIntoClienteInterOrExcecaoGarantiaDbsAvulso(String wono, String clienteInter, String contaContabilSigla, String centroDeCustoSigla, List<GeSegmento> segmentoList, String wonosm, List<SegmentoBean> segmentoBeanList){
		Connection con = null;
		
//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();
		try {
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();
			
			//			String url = "jdbc:as400://192.168.128.146";
			//			String user = "XUPU15PSS";
			//			String password = "marcosa";
			//			Class.forName("com.ibm.as400.access.AS400JDBCDriver").newInstance();
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			if(segmentoList != null){
				for (GeSegmento geSegmento : segmentoList) {
					Statement statement = con.createStatement();
					String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 (LBCUNO, MSCUNO, PTCUNO, PTDOLP, LBDOLP, MSDOLP, WONO, WOSGNO, WONOSM) values('"+clienteInter+"', '"+clienteInter+"', '"+clienteInter+"','100', '100', '100','"+wono+"','"+geSegmento.getNumeroSegmento()+"','"+wonosm+"-OF')";
					//" where GS.WONO = '"+wono+"'";
					statement.executeUpdate(SQL);
//					statement = con.createStatement();
//					statement.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 where WONOSM = '"+wonosm+"-OF'");
					statement = con.createStatement();
					SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 (CONTA, WONO, WOSGNO, WONOSM) values( '"+contaContabilSigla+"          "+centroDeCustoSigla+"', '"+wono+"','"+geSegmento.getNumeroSegmento()+"','"+wonosm+"-OFCC')";
					//" where WONO = '"+wono+"'";
					statement.executeUpdate(SQL);	
//					statement = con.createStatement();
//					statement.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 where WONOSM = '"+wonosm+"-OF'");	
				}
			}else{
				for (SegmentoBean geSegmento : segmentoBeanList) {
					Statement statement = con.createStatement();
					String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 (LBCUNO, MSCUNO, PTCUNO, PTDOLP, LBDOLP, MSDOLP, WONO, WOSGNO, WONOSM) values('"+clienteInter+"', '"+clienteInter+"', '"+clienteInter+"','100', '100', '100','"+wono+"','"+geSegmento.getNumeroSegmento()+"','"+wonosm+"-OF')";
					//" where GS.WONO = '"+wono+"'";
					statement.executeUpdate(SQL);
//					statement = con.createStatement();
//					statement.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 where WONOSM = '"+wonosm+"-OF'");
					statement = con.createStatement();
					SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 (CONTA, WONO, WOSGNO, WONOSM) values( '"+contaContabilSigla+"          "+centroDeCustoSigla+"', '"+wono+"','"+geSegmento.getNumeroSegmento()+"','"+wonosm+"-OFCC')";
					//" where WONO = '"+wono+"'";
					statement.executeUpdate(SQL);	
//					statement = con.createStatement();
//					statement.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 where WONOSM = '"+wonosm+"-OF'");	
				}
			}
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	

	public boolean updateGarantiaClienteExternoDbs(String wono, String contaContabilSigla, String centroDeCustoSigla, String segmento, String wonosm){
		Connection con = null;

//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();
		try {
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();

			//			String url = "jdbc:as400://192.168.128.146";
			//			String user = "XUPU15PSS";
			//			String password = "marcosa";
			//			Class.forName("com.ibm.as400.access.AS400JDBCDriver").newInstance();
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			Statement statement = con.createStatement();

			String SQL = "insert into "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 (CONTA, WONO, WOSGNO, WONOSM) values( '"+contaContabilSigla+"          "+centroDeCustoSigla+"', '"+wono+"','"+segmento+"','"+wonosm+"-OF')";;
			//" where WONO like '"+wono+"%'";
			statement.executeUpdate(SQL);
//			statement = con.createStatement();
//			statement.executeUpdate("delete from "+IConstantAccess.AMBIENTE_DBS+".USPIFSM0 where WONOSM = '"+wonosm+"-OF");
			con.commit();
			return true;
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean verifyCreateOs(String id, String bgrp){
		ResultSet rs = null;
		PreparedStatement prstmt_ = null;
//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();
		Connection con = null;
		try {
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();

			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			//Class.forName("com.ibm.as400.access.AS400JDBCDriver").newInstance();
			//con = DriverManager.getConnection("jdbc:as400://192.168.128.146", "XUPU15PSS", "marcosa"); 
			String SQL = "select w.wono, w.wonosm, w.descerr, w.coderr from "+IConstantAccess.AMBIENTE_DBS+".USPWOSM0 w where w.wonosm = '"+id+"-O' and w.bgrp is not null and w.bgrp = '"+bgrp+"' and w.wono <> ''";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();

			if(rs.next()){
				String wono = rs.getString("wono");
				String coderr = rs.getString("coderr");
				if(wono == null || "".equals(wono.trim())){
					if(coderr != null && !"".equals(coderr)){
						return false;
					}
					return true;
				}else{
					return true;
				}
			}
		}catch (Exception e) { 
			e.printStackTrace();
		}finally{
			try {
				if(con != null){
					con.close();
					prstmt_.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	private boolean removerDadosInterfaceOs(String id, String bgrp){
		PreparedStatement prstmt_ = null;
		Connection con = null;
		try {
			
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			String SQL = "delete from "+IConstantAccess.AMBIENTE_DBS+".USPWOSM0 w where w.wonosm = '"+id+"-O' and w.bgrp is not null and w.bgrp = '"+bgrp+"' and w.wono <> ''";
			prstmt_ = con.prepareStatement(SQL);
			prstmt_.executeUpdate();
			
			return true;
		}catch (Exception e) { 
			e.printStackTrace();
		}finally{
			try {
				if(con != null){
					con.close();
					prstmt_.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public String findOsEstimada(String idCheckIn, GestaoEquipamentosBean bean) {
		ResultSet rs = null;
		PreparedStatement prstmt_ = null;


		Connection con = null;
//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();

		String wono = "";
		try {

//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();

			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
			//Class.forName("com.ibm.as400.access.AS400JDBCDriver").newInstance();
			//con = DriverManager.getConnection("jdbc:as400://192.168.128.146", "XUPU15PSS", "marcosa"); 
			String SQL = "select w.wono, w.wonosm, descerr from "+IConstantAccess.AMBIENTE_DBS+".USPWOSM0 w where w.wonosm = '"+idCheckIn+"-OF'";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();

			if(rs.next()){
				wono = rs.getString("wono");
				if(wono == null || "".equals(wono.trim())){
					if(!"".equals(rs.getString("descerr"))){
//						prstmt_ = con.prepareStatement("delete from "+IConstantAccess.AMBIENTE_DBS+".USPWOSM0 where wonosm = '"+idCheckIn+"-OF'");
//						prstmt_.executeUpdate();
//						prstmt_ = con.prepareStatement("delete from "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 where wonosm = '"+idCheckIn+"-OF'");
//						prstmt_.executeUpdate();
						return null;
					}
				}
//				prstmt_ = con.prepareStatement("delete from "+IConstantAccess.AMBIENTE_DBS+".USPWOSM0 where wonosm = '"+idCheckIn+"-OF'");
//				prstmt_.executeUpdate();
//				prstmt_ = con.prepareStatement("delete from "+IConstantAccess.AMBIENTE_DBS+".USPSGSM0 where wonosm = '"+idCheckIn+"-OF'");
//				prstmt_.executeUpdate();
			}else{
				return "";
			}

		}catch (Exception e) { 
			e.printStackTrace();
		}finally{
			try {
				if(con != null){
					con.close();
					prstmt_.close();
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return wono.trim();
	}

	/**
	 * Salva a data de término de serviços
	 * @param bean
	 * @param dataTerminoServico
	 * @return
	 */
	public Boolean saveDataTerminoServicos(ChekinBean bean, String dataTerminoServico, String jobControl){
		//PreparedStatement prstmt_ = null;
		//Connection con = null;
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			
			manager.getTransaction().begin();
			
			Query query = manager.createQuery("From GeSegmento where  idCheckin.id =:idCheckin and jobControl =:jobControl");
			query.setParameter("idCheckin", bean.getId());
			query.setParameter("jobControl", jobControl);
			
			List<GeSegmento> geSegmentos = (List<GeSegmento>)query.getResultList();
			
			query = manager.createQuery("From GeSituacaoOs where  idCheckin.id =:idCheckin");
			query.setParameter("idCheckin", bean.getId());
			GeSituacaoOs geSituacaoOs = (GeSituacaoOs)query.getSingleResult();
			//significa que a OS é da sessão do encarregado de serviços
			if(jobControl.equals(geSituacaoOs.getIdCheckin().getJobControl())){
				query = manager.createNativeQuery("select * From ge_segmento where ID_CHECKIN =:idCheckin and JOB_CONTROL <> '"+jobControl+"' and data_Termino_Servico is null");
				query.setParameter("idCheckin", bean.getId());
				if(query.getResultList().size() > 0){
					return false;
				}
				//con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
				//prstmt_ = setDateFluxoOSDBS(dataTerminoServico, con, bean.getNumeroOs(), "006");
				geSituacaoOs.setDataTerminoServico(dateFormat.parse(dataTerminoServico));
				geSituacaoOs.setIdFuncDataTerminoServico(this.bean.getMatricula());
				manager.merge(geSituacaoOs);
			}
			SegmentoBusiness business = new SegmentoBusiness(this.bean);
			for (GeSegmento geSegmento : geSegmentos) {
				geSegmento.setDataTerminoServico(dateFormat.parse(dataTerminoServico));
				geSegmento.setIdFuncionarioDataTerminoServico(this.bean.getMatricula());
				business.alterarFluxoProcessoOficinaJobControl(geSegmento.getIdCheckin().getId(), "OK", null, jobControl, null);
			}
			//receber o consultor e o recepcionista quando cscc do segmento igual jobcontrol da os
			if(jobControl.equals(geSituacaoOs.getIdCheckin().getJobControl())){
				this.sendEmailConsultor(manager, geSituacaoOs.getIdCheckin().getTipoCliente(), "gostaríamos de informar que o(s) segmento(s) da OS "+geSituacaoOs.getIdCheckin().getNumeroOs()+" da sessão "+this.bean.getJobControl()+" está com a data de término de serviços!", bean.getId(), geSituacaoOs.getIdCheckin());
				this.sendEmailRecepcionaista(manager, "gostaríamos de informar que o(s) segmento(s) da OS "+geSituacaoOs.getIdCheckin().getNumeroOs()+" da sessão "+this.bean.getJobControl()+" está com a data de término de serviços!", geSituacaoOs.getIdCheckin().getIdOsPalm().getFilial());
			}
			
			manager.getTransaction().commit();
			return true;
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
//			try {
//				if(con != null){
//					prstmt_.close();
//					con.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		return false;
	}
	/**
	 * Salva a data de faturamento
	 * @param bean
	 * @param dataTerminoServico
	 * @return
	 */
	public Boolean saveDataFaturamento(ChekinBean bean, String dataFaturamento, String encerrarAutomatica){
		//PreparedStatement prstmt_ = null;
		//Connection con = null;
		EntityManager manager = null;
		try{
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			//con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			//prstmt_ = setDateFluxoOSDBS(dataFaturamento, con, bean.getNumeroOs(), "007");
			Query query = manager.createQuery("From GeSituacaoOs where  idCheckin.id =:idCheckin");
			query.setParameter("idCheckin", bean.getId());
			GeSituacaoOs geSituacaoOs = (GeSituacaoOs)query.getSingleResult();
			if(geSituacaoOs.getDataTerminoServico() == null){
				geSituacaoOs.setDataTerminoServico(dateFormat.parse(dataFaturamento));
				geSituacaoOs.setIdFuncDataTerminoServico(this.bean.getMatricula());
			}
			geSituacaoOs.setDataFaturamento(dateFormat.parse(dataFaturamento));
			geSituacaoOs.setIdFuncDataFaturamento(this.bean.getMatricula());
			geSituacaoOs.setEncerrarAutomatica(encerrarAutomatica);
			manager.merge(geSituacaoOs);
			manager.getTransaction().commit();
			this.sendEmailConsultor(manager, geSituacaoOs.getIdCheckin().getTipoCliente(), "gostaríamos de informar que a OS "+geSituacaoOs.getIdCheckin().getNumeroOs()+" foi faturada!", bean.getId(), geSituacaoOs.getIdCheckin());
			return true;
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
//			if(con != null){
//				try {
//					prstmt_.close();
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
		}
		return false;
	}
	
	/**
	 * Libera o orçamento para agendamento
	 * @param bean
	 * @return
	 */
	public String saveDataOrcamento (ChekinBean bean, boolean setIsDateOrcamento, String dataOrcamento, List<UsuarioBean> usuarioList){
		EntityManager manager = null;
		//ResultSet rs = null;
		//PreparedStatement prstmt_ = null;
		//Connection con = null;
		try {
			manager = JpaUtil.getInstance();
			//Verifica se todas os segmentos possuem número de documento
			Query query = manager.createNativeQuery("select op.* from ge_segmento seg, ge_doc_seg_oper op, ge_check_in ch" +
					" where ch.id =:idCheckIn" +
					" and ch.id = seg.id_checkin" +
					" and seg.id = op.id_segmento" +
			" and op.msg_dbs is not null");
			query.setParameter("idCheckIn", bean.getId());
			if(query.getResultList().size() > 0){
				return "Existem erros ao enviar peças, favor verificar!-true";
			}
//			query = manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
//			query.setParameter("idCheckin", bean.getId());
//			GeSituacaoOs geSituacaoOs = (GeSituacaoOs)query.getSingleResult();
//			if(geSituacaoOs.getDataEntregaPedidos() == null){
//				return "Não é permitido criar data de orçamento sem a data de entrega de pedidos, favor verificar!";
//			}
			//con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();


//			if(!this.openOs(bean.getId(), bean.getNumeroOs())){
//				return "Não foi possível abrir OS!-true";
//			}
			
//			//Verifica se a OS está OPEN
//			String SQL = "select TRIM(w.ACTI) ACTI from "+IConstantAccess.LIB_DBS+".WOPHDRS0 w  where TRIM(w.WONO) = '"+bean.getNumeroOs()+"'"; 
//			prstmt_ = con.prepareStatement(SQL);
//			rs = prstmt_.executeQuery();
//			if(rs.next()){
//				String ACTI = rs.getString("ACTI");
//				
//				if(ACTI.equals("E")){
//					if(!this.openOs(bean.getId(), bean.getNumeroOs())){
//						//return "Não foi possível abrir OS!-true";
//					}
//				}
//			}
			
			manager.getTransaction().begin();
			
			//recupera o fator do segmento o cliente no CRM
			GeCheckIn checkIn = (GeCheckIn)manager.find(GeCheckIn.class, bean.getId());
			checkIn.setIsLiberadoPConsultor("S");
			checkIn.setIdFuncionarioDataOrcamento(this.bean.getMatricula());
			
//			SQL = "select crm.cust_codigosegmento from MME.vw_crmsegmentoscontas_ds crm where  crm.cuno = '"+bean.getCodCliente()+"'";
//			query = manager.createNativeQuery(SQL);
//			if(query.getResultList().size() > 0){
//				String codigoSegmento = (String)query.getResultList().get(0);
//				query = manager.createNativeQuery("select cli.fator  from ge_segmento_cliente cli where cli.codigo =:codigo order by cli.id desc");
//				query.setParameter("codigo", codigoSegmento.trim());
//				if(query.getResultList().size() > 0){
//					checkIn.setFatorCliente((BigDecimal)query.getResultList().get(0));
//				}else{
//					return "Não existe fator cadastrado para o cliente, favor verificar!-true";
//				}
//			}else{
//				return "Não existe fator cadastrado para o cliente no DBS, favor verificar!-true";
//			}
			//recupera o último número do documento para recuperar as peças com os preços
			/*query = manager.createNativeQuery("select oper.num_doc, max(oper.id) from ge_segmento seg, ge_doc_seg_oper oper"+
					"	where seg.id_checkin =:id_checkin"+
					"	and seg.id = oper.id_segmento"+
			"	group by oper.num_doc");
			query.setParameter("id_checkin", bean.getId());
			List<Object[]> list = query.getResultList();
			for (Object[] objects : list) {
				GeDocSegOper docSegOper = manager.find(GeDocSegOper.class, ((BigDecimal)objects[1]).longValue());
				//aqui tem o numero do documento e o numero do id da ge_doc_seg_oper
				if(docSegOper.getNumDoc() != null && !this.sendContratoDbs(docSegOper.getNumDoc(), docSegOper, checkIn.getTipoCliente(), checkIn.getPossuiSubTributaria())){
					return "Não foi possível recuperar o orçamento do DBS!-true";
				}
			}*/
			if(setIsDateOrcamento){
				query = manager.createQuery("From GeSituacaoOs where idCheckin.id =:id");
				query.setParameter("id", bean.getId());
				GeSituacaoOs situacaoOs = (GeSituacaoOs)query.getSingleResult();
				//if(situacaoOs.getDataOrcamento() == null){
				Locale locale = new Locale("pt","BR");
				Calendar calendar = Calendar.getInstance(locale);
					situacaoOs.setDataOrcamento(calendar.getTime());
					situacaoOs.setIdFuncDataOrcamento(this.bean.getMatricula());
					manager.merge(situacaoOs);
					// Inserindo a data de orçamento no DBS.
					//prstmt_ = setDateFluxoOSDBS(dataOrcamento, con, bean.getNumeroOs(), "003");
//					SQL = "select TRIM(W.NTDA) NTDA, substring(W.NTDA, 0, 11) as campoReplace from "+IConstantAccess.LIB_DBS+".WOPNOTE0 W where TRIM(W.WONO) = '"+bean.getNumeroOs()+"' AND TRIM(W.NTLNO1) = '003' AND TRIM(W.WOSGNO) = ''";
//					prstmt_ = con.prepareStatement(SQL);
//					rs = prstmt_.executeQuery();
//					if(rs.next()){
//						String NTDA = rs.getString("NTDA");
//						String campoReplace = rs.getString("campoReplace");
//						String dataAux = NTDA.replace(campoReplace, dataOrcamento);
//						SQL = "update "+IConstantAccess.LIB_DBS+".WOPNOTE0 W set W.NTDA = '"+dataAux+"' where TRIM(W.WONO) = '"+bean.getNumeroOs()+"' AND TRIM(W.NTLNO1) = '003' AND TRIM(W.WOSGNO) = ''";
//						prstmt_ = con.prepareStatement(SQL);
//						prstmt_.executeUpdate();
//					}
				//}
			}
			//manager.getTransaction().commit();
					
			checkIn.setHasSendDataOrcamento(bean.getHasSendDataOrcamento());
			checkIn.setStatusNegociacaoConsultor("N");
			manager.merge(checkIn);
			for (int i = 0; i < usuarioList.size(); i++) {
				UsuarioBean usuarioBean = usuarioList.get(i);
				query = manager.createNativeQuery("delete from Ge_Consultor_Proposta where ID_CHECK_IN =:ID_CHECK_IN and ID_EPIDNO =:ID_EPIDNO");
				query.setParameter("ID_CHECK_IN", checkIn.getId());
				query.setParameter("ID_EPIDNO", usuarioBean.getMatricula());
				query.executeUpdate();
				if(usuarioBean.getIsSelected()){
					GeConsultorProposta consultorProposta = new GeConsultorProposta();
					consultorProposta.setIdCheckIn(checkIn);
					consultorProposta.setIdEpidno(manager.find(TwFuncionario.class, usuarioBean.getMatricula()));
					manager.merge(consultorProposta);
				}
			}
			
			manager.getTransaction().commit();
			sendEmailConsultor(manager, checkIn.getTipoCliente(), "gostaríamos de informar que a OS "+checkIn.getNumeroOs()+" foi liberada para o negociação com o cliente!", checkIn.getId(), checkIn);
			return "Transação realizada com sucesso!";

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
//				if(con != null){
//					//rs.close();
//					prstmt_.close();
//					con.close();
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "Erro o tentar enviar a data de orçamento!-true";
	}
	
	/**
	 * Envia e-mail para o digitador quando supervisor liberar a OS.
	 * @param manager
	 */	
//	private void sendEmailConsultor(EntityManager manager, String tipoCliente, String msg){
//		try {
//			Query query = null;
//			if("EXT".equals(tipoCliente)){
//			  query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+
//					" WHERE tw.epidno = perfil.id_tw_usuario"+
//					" AND tw.STN1 = '"+Integer.valueOf(this.bean.getFilial()).toString()+"'"+
//					" AND perfil.ID_PERFIL in (select id from adm_perfil where sigla in('CONS','ORC') and tipo_sistema = 'GE')"); 
//			} else{
//				 query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+
//							" WHERE tw.epidno = perfil.id_tw_usuario"+
//							" AND tw.STN1 = '"+Integer.valueOf(this.bean.getFilial()).toString()+"'"+
//							" AND perfil.ID_PERFIL in (select id from adm_perfil where sigla in('GAOS','ORC') and tipo_sistema = 'GE')"); 
//			}
//			if(query.getResultList().size() > 0){
//				List<Object []> list = query.getResultList();
//				for(Object [] pair : list){
//					EmailHelper helper = new EmailHelper();
//					helper.sendSimpleMail((String)pair[1]+" "+msg, "Ordem de Serviço", (String)pair[0]);				
//				}				
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	/**
	 * Envia e-mail para o digitador quando supervisor liberar a OS.
	 * @param manager
	 */	
	private void sendEmailConsultor(EntityManager manager, String tipoCliente, String msg, Long idCheckin, GeCheckIn checkin){
		try {
			Query query = null;
			query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+
			" WHERE tw.epidno in (select cp.id_epidno from GE_CONSULTOR_PROPOSTA cp where cp.ID_CHECK_IN =:ID_CHECK_IN)" +
			" UNION " +
			" SELECT tw.email, tw.EPLSNM FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+
					" WHERE tw.epidno = perfil.id_tw_usuario"+
					" AND tw.STN1 = '"+Integer.valueOf(checkin.getIdOsPalm().getFilial()).toString()+"'"+
					" AND perfil.ID_PERFIL in (select id from adm_perfil where sigla in('ORC') and tipo_sistema = 'GE')"); 
			query.setParameter("ID_CHECK_IN", idCheckin);

			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				for(Object [] pair : list){
					EmailHelper helper = new EmailHelper();
					helper.sendSimpleMail((String)pair[1]+" "+msg, " Sistema Oficina Ordem de Serviço - Proposta "+checkin.getId(), (String)pair[0]);				
				}				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void sendEmailRecepcionaista(EntityManager manager, String msg, String filial){
		try {
			//Envia e-mail para todos os envolvidos no processo de OS.
			Query query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM, (select p.SIGLA from ADM_PERFIL p where p.ID = perfil.ID_PERFIL) SIGLA FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+ 
					  " WHERE tw.epidno = perfil.id_tw_usuario"+
					  " AND perfil.ID_PERFIL in (select id from ADM_PERFIL"+
					  //" WHERE SIGLA in ('RECP','DIG','ORC','CONS')"+				 
					  //" WHERE SIGLA in ('DIG','ORC','CONS')"+	
					  " WHERE SIGLA in ('RECP')"+
					  " AND TIPO_SISTEMA = 'GE'"+
					  " AND tw.STN1 =:filial)"); 
			
			query.setParameter("filial", Integer.valueOf(filial).toString());
			
			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				EmailHelper helper = new EmailHelper();
				for(Object [] pair : list){
					helper.sendSimpleMail((String)pair[1]+" "+msg+": ","Ordem de Serviço", (String)pair[0]);
				}				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void sendEmailOrcamentista(EntityManager manager, String msg, String filial){
		try {
			//Envia e-mail para todos os envolvidos no processo de OS.
			Query query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM, (select p.SIGLA from ADM_PERFIL p where p.ID = perfil.ID_PERFIL) SIGLA FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+ 
					" WHERE tw.epidno = perfil.id_tw_usuario"+
					" AND perfil.ID_PERFIL in (select id from ADM_PERFIL"+
					//" WHERE SIGLA in ('RECP','DIG','ORC','CONS')"+				 
					//" WHERE SIGLA in ('DIG','ORC','CONS')"+	
					" WHERE SIGLA in ('ORC')"+
					" AND TIPO_SISTEMA = 'GE'"+
			" AND tw.STN1 =:filial)"); 
			
			query.setParameter("filial", Integer.valueOf(filial).toString());
			
			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				EmailHelper helper = new EmailHelper();
				for(Object [] pair : list){
					helper.sendSimpleMail((String)pair[1]+" "+msg+": ","Ordem de Serviço", (String)pair[0]);
				}				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	private void sendEmailDigitador(EntityManager manager, String msg, String filial){
		try {
			//Envia e-mail para todos os envolvidos no processo de OS.
			Query query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM, (select p.SIGLA from ADM_PERFIL p where p.ID = perfil.ID_PERFIL) SIGLA FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+ 
					" WHERE tw.epidno = perfil.id_tw_usuario"+
					" AND perfil.ID_PERFIL in (select id from ADM_PERFIL"+
					//" WHERE SIGLA in ('RECP','DIG','ORC','CONS')"+				 
					//" WHERE SIGLA in ('DIG','ORC','CONS')"+	
					" WHERE SIGLA in ('DIG')"+
					" AND TIPO_SISTEMA = 'GE'"+
			" AND tw.STN1 =:filial)"); 
			
			query.setParameter("filial", Integer.valueOf(filial).toString());
			
			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				EmailHelper helper = new EmailHelper();
				for(Object [] pair : list){
					helper.sendSimpleMail((String)pair[1]+" "+msg+": ","Ordem de Serviço  Sistema Oficina", (String)pair[0]);
				}				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public Boolean openOs(Long idCheckIn, String numOs){
		Connection con = null;
		Statement statement = null;
		try {
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			String SQL = "insert into  "+IConstantAccess.AMBIENTE_DBS+".USPPPSM0 (WONOSM, WONO) values('"+idCheckIn+"-O','"+numOs+"')";
			statement = con.createStatement();
			statement.executeUpdate(SQL);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(con != null){
					con.close();
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	public String verificarAprovacaoProposta(Long idCheckin){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();	
			Query query = manager.createQuery("From GeSituacaoOs where idCheckin.id =:id");
			query.setParameter("id", idCheckin);
			GeSituacaoOs situacaoOs = (GeSituacaoOs)query.getSingleResult();			
			if(situacaoOs.getDataAprovacao() != null){
				return "Não é permitido enviar proposta para o CRM pois a mesma já foi aprovada";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}
	
	public String saveDataNegociacao (ChekinBean bean, String codCliente){
		PreparedStatement prstmt_ = null;
		Connection con = null;
		EntityManager manager = null;
		ResultSet rs = null;
		String SQL;
		try {
			//insere a data de negociação e aprovação no DBS.
			manager = JpaUtil.getInstance();	
			manager.getTransaction().begin();
//			InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//			Properties prop = new Properties();
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			//Query para buscar cod.Cliente e verificar se é igual a enviada no codCliente
			SQL = "SELECT USPWOHD0.CUSTNO FROM  B108F034."+IConstantAccess.LIB_DBS+".WOPSEGS0 WOPSEGS0 LEFT OUTER JOIN B108F034."+IConstantAccess.LIB_DBS+".USPWOHD0 USPWOHD0 ON WOPSEGS0.WONO = USPWOHD0.WONO"+  
			" WHERE WOPSEGS0.SHPFLD='S' AND WOPSEGS0.ACTI='O' AND WOPSEGS0.WOSGNO='01'"+
			" AND WOPSEGS0.BGRP not in ('CONS', 'PM')"+
			" AND USPWOHD0.WONO = '"+bean.getNumeroOs()+"'"; 
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				String custno = rs.getString("CUSTNO");
				if(!codCliente.equals(custno)){					
					return "Codigo cliente diferente do DBS. Confirme!";
				}else{
					GeCheckIn checkIn = new GeCheckIn();
					checkIn = manager.find(GeCheckIn.class, bean.getId());
					checkIn.setCodCliente(codCliente);
					manager.merge(checkIn);
				}
			}
			Query query = manager.createQuery("From GeSituacaoOs where idCheckin.id =:id");
			query.setParameter("id", bean.getId());
			GeSituacaoOs situacaoOs = (GeSituacaoOs)query.getSingleResult();			
			if(situacaoOs.getDataAprovacao() != null){
				return "Não é permitido enviar proposta para o CRM pois a mesma já foi aprovada";
			}
			if(situacaoOs.getDataOrcamento() != null){
				situacaoOs.setDataEntregaPedidos(new Date());
				if(bean.getTipoCliente() == null || bean.getTipoCliente().equals("INT")  ){
					query = manager.createNativeQuery("select count(seg.id) from ge_segmento seg"+ 
							"	where seg.id_checkin =:id_checkin"+
							"	and (seg.job_code = '051' or seg.job_code = '050')");
					query.setParameter("id_checkin", bean.getId());
					BigDecimal total = (BigDecimal)query.getSingleResult();
					if(total.intValue() > 0){
						situacaoOs.setDataAprovacao(new Date());
						situacaoOs.getIdCheckin().setHasSendDataAprovacao("S");
					}else{
						SQL = "  select f.eplsnm, f.email from adm_perfil_sistema_usuario adm,"+ 
									 "  tw_funcionario f"+
									 "	where adm.id_tw_usuario = f.epidno"+
									 "  and adm.id_centro_custo = (select id from pmp_centro_de_custo where sigla = '"+situacaoOs.getIdCheckin().getCentroCustoSigla()+"')" +
									 "  and f.stn1 = "+Integer.valueOf(bean.getFilial());
						query = manager.createNativeQuery(SQL);
						List<Object[]> pair = query.getResultList();
						for (Object[] objects : pair) {
							new EmailHelper().sendSimpleMail("A Os "+situacaoOs.getNumeroOs()+" para ser aprovada foi enviada para o seu centro de custo.", "Aprovar OS  Sistema Oficina", ((String)objects[1]));
							new EmailHelper().sendSimpleMail("A Os "+situacaoOs.getNumeroOs()+" para ser aprovada foi enviada para o seu centro de custo.", "Aprovar OS  Sistema Oficina", "cioletti.rodrigo@gmail.com");
						}
					}
				}else{
					situacaoOs.getIdCheckin().setHasSendDataAprovacao("S");
					situacaoOs.setDataAprovacao(new Date());
				}
				
//				query = manager.createNativeQuery("select count(seg.id) from ge_segmento seg"+ 
//						"	where seg.id_checkin =:id_checkin"+
//						"	and (seg.job_code = '051' or seg.job_code = '050')");
//				query.setParameter("id_checkin", bean.getId());
//				BigDecimal total = (BigDecimal)query.getSingleResult();
//				if(total.intValue() > 0){
//					situacaoOs.setDataAprovacao(new Date());
//					situacaoOs.getIdCheckin().setHasSendDataAprovacao("S");
//				}
//				//insere a data de negociação e aprovação no DBS.
//				InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//				Properties prop = new Properties();
//				prop.load(in);
//				String url = prop.getProperty("dbs.url");
//				String user = prop.getProperty("dbs.user");
//				String password =prop.getProperty("dbs.password");
//				Class.forName(prop.getProperty("dbs.driver")).newInstance();
//				con = DriverManager.getConnection(url, user, password); 
				//recupera o fator do segmento o cliente do DBS
				//fazer um if aqui para cliente interno
				//if((bean.getTipoCliente() == null) ||!bean.getTipoCliente().equals("INT")){
				if(bean.getSiglaIndicadorGarantia() != null || situacaoOs.getDataAprovacao() == null){
					SQL = "update B108F034."+IConstantAccess.LIB_DBS+".USPWOHD0 set DTNG = '"+fmt.format(situacaoOs.getDataEntregaPedidos())+"' where WONO ='"+situacaoOs.getNumeroOs()+"'";
				}else{
					SQL = "update B108F034."+IConstantAccess.LIB_DBS+".USPWOHD0 set DTNG = '"+fmt.format(situacaoOs.getDataEntregaPedidos())+"', DTAP='"+fmt.format(situacaoOs.getDataAprovacao())+"' where WONO ='"+situacaoOs.getNumeroOs()+"'";
					
				}
//				}else{
//				SQL = "update B108F034."+IConstantAccess.LIB_DBS+".USPWOHD0 set DTNG = '"+fmt.format(situacaoOs.getDataNegociacao())+"' where WONO ='"+situacaoOs.getNumeroOs()+"'";
//				}
				prstmt_ = con.prepareStatement(SQL);
				prstmt_.execute();
			}	
			manager.merge(situacaoOs);						
			manager.getTransaction().commit();
			//this.saveDataOrcamento (bean, false);//manda false para não alterar a data de orçamento e recupera as peças do dbs para enviar para o crm
			return "Transação realizada com sucesso!";

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			try {
				if(con != null){
					con.close();
					rs.close();
					prstmt_.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Falha ao enviar data de negociação!-true";
	}
	public String saveDataNegociacaoClienteInter (ChekinBean bean){
		PreparedStatement prstmt_ = null;
		Connection con = null;
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();	
			manager.getTransaction().begin();
			Query query = manager.createQuery("From GeSituacaoOs where numeroOs =:numOS");
			query.setParameter("numOS", bean.getNumeroOs());
			GeSituacaoOs situacaoOs = (GeSituacaoOs)query.getSingleResult();
			situacaoOs.setDataEntregaPedidos(new Date());
			//Seta o hasdatadeorçamento para C para não deixar enviar para o CRM novamente 
			query = manager.createQuery("From GeCheckIn where id =:id");
			query.setParameter("id", bean.getId());
			GeCheckIn checkIn = (GeCheckIn) query.getSingleResult();
			checkIn.setHasSendDataOrcamento("C");
			manager.merge(checkIn);
			//Envia data de negociação para o DBS
//			InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//			Properties prop = new Properties();
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			//recupera o fator do segmento o cliente do DBS
			String SQL = "update B108F034."+IConstantAccess.LIB_DBS+".USPWOHD0 set DTNG = '"+fmt.format(situacaoOs.getDataEntregaPedidos())+"' where WONO ='"+bean.getNumeroOs()+"'";
			prstmt_ = con.prepareStatement(SQL);
			prstmt_.execute();			
			manager.merge(situacaoOs);						
			manager.getTransaction().commit();
			//this.saveDataOrcamento (bean, false);//manda false para não alterar a data de orçamento e recupera as peças do dbs para enviar para o crm
			return "Data de negociação salva com sucesso, aguarde a aprovação da OS!";

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			try {
				if(con != null){
					con.close();
					prstmt_.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return "Ocorreu um erro a salvar a data de negociação!";
		
	}

	public synchronized Boolean sendContratoDbs(String numDoc, GeDocSegOper segOper, String tipoCliente, String possuiSubTributaria) throws Exception{
		ResultSet rs = null;
		PreparedStatement prstmt_ = null;
		Connection con = null;
 
//		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//		Properties prop = new Properties();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			
			//			manager = JpaUtil.getInstance();
			//			manager.getTransaction().begin();


//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();

			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();  

			String SQL = "SELECT PCPCOHD0.RFDCNO, PCPCOHD0.CUNO, PCPCOHD0.CUNM, PCPCOHD0.CUNM2, PCPCOHD0.CUADD1, PCPCOHD0.CUADD2, PCPCOHD0.CUADD3, PCPCOHD0.CUCYST, PCPCOHD0.ZIPCD9" +
			" FROM "+ IConstantAccess.LIB_DBS+".PCPCOHD0 PCPCOHD0"+
			" WHERE PCPCOHD0.RFDCNO='"+numDoc+"'";

			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
//			rs.last();   
//			int totalOfRecords = rs.getRow();  
//			if(totalOfRecords == 0){
//				return true;
//			}
//			rs.beforeFirst(); 
			GeCabecalhoPecas cabecalhoPecas = new GeCabecalhoPecas();
			if(rs.next()){
				manager.getTransaction().begin();
				Query query = manager.createNativeQuery("delete from ge_cabecalho_pecas where id_doc_seg_oper = "+segOper.getId());
				query.executeUpdate();
				cabecalhoPecas.setRfdcno(rs.getString("RFDCNO").trim());
				cabecalhoPecas.setCuno(rs.getString("CUNO").trim());
				cabecalhoPecas.setCunm(rs.getString("CUNM").trim());
				cabecalhoPecas.setCunm2(rs.getString("CUNM2").trim());
				cabecalhoPecas.setCuadd1(rs.getString("CUADD1").trim());
				cabecalhoPecas.setCuadd2(rs.getString("CUADD2").trim());
				cabecalhoPecas.setCuadd3(rs.getString("CUADD3").trim());
				cabecalhoPecas.setCucyst(rs.getString("CUCYST").trim());
				cabecalhoPecas.setZipcd9(rs.getString("ZIPCD9").trim());
				cabecalhoPecas.setIdDocSegOper(segOper);
				manager.persist(cabecalhoPecas);
				manager.getTransaction().commit();
			}
			
			Boolean pedidoAplicado = false;
			prstmt_ = con.prepareStatement("select distinct(RFDCNO) from "+IConstantAccess.LIB_DBS+".WOPPART0 where wono = '"+segOper.getIdSegmento().getIdCheckin().getNumeroOs()+"' and WOSGNO = '"+segOper.getIdSegmento().getNumeroSegmento()+"'");
			rs = prstmt_.executeQuery();
			//bean.setPedido("Não");
			if(rs.next()){
				pedidoAplicado = true;
			}
			
			//UNCS valor de custo para cliente interno
			SQL = " SELECT PCPCOPD0.ORQY, PCPCOPD0.BOIMCT, PCPCOPD0.SOS1, PCPCOPD0.DSFCCD, PCPCOPD0.PANO20, PCPCOPD0.DS18, PCPCOPD0.UNSEL, PCPCOPD0.unsel*PCPCOPD0.orqy UNSEL_ORQY, PCPCOPD0.SKNSKI, PCPCOPD0.UNCS, PCPCOPD0.UNCS*PCPCOPD0.orqy UNCS_ORQY, PCPCOPD0.NTDA " +
			" FROM "+IConstantAccess.LIB_DBS+".PCPCOPD0 PCPCOPD0 " +
					//" and PCPCOPD0.orqy = parts.orqy"+
			" WHERE trim(PCPCOPD0.RFDCNO)='"+numDoc+"'" +
			" order by PCPCOPD0.SOS1, PCPCOPD0.PANO20";
			
			
			
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			int count = 0;
			Query query = manager.createQuery("from GePecas where idDocSegOper.id = "+segOper.getId());
			List<GePecas> verificaRemovidaList = query.getResultList();
			while(rs.next()){
					//System.out.println(numDoc+" "+count);
				if(count == 0){
					manager.getTransaction().begin();
					query = manager.createNativeQuery("delete from ge_pecas_temp where id_doc_seg_oper = "+segOper.getId());
					query.executeUpdate();

					query = manager.createNativeQuery("insert into ge_pecas_temp select * from ge_pecas where id_doc_seg_oper = "+segOper.getId());
					query.executeUpdate();

					query = manager.createNativeQuery("delete from ge_pecas where id_doc_seg_oper = "+segOper.getId());
					query.executeUpdate();
					manager.getTransaction().commit();
				}
				
				//if(pedidoAplicado == false){
					manager.getTransaction().begin();
					GePecas pecas = new GePecas();
					pecas.setQtd(Long.valueOf(rs.getString("ORQY").trim()));
					pecas.setSos1(rs.getString("SOS1").trim());
					pecas.setPartNo(rs.getString("PANO20").trim());
					pecas.setPartName(rs.getString("DS18").trim().replaceAll("'", ""));
					pecas.setUnsel(BigDecimal.valueOf(rs.getDouble("UNSEL_ORQY")));
					if("EXT".equals(tipoCliente)){
						pecas.setValor(BigDecimal.valueOf(rs.getDouble("UNSEL")));
						pecas.setValorTotal(BigDecimal.valueOf(rs.getDouble("UNSEL_ORQY")));
					}else if("INT".equals(tipoCliente)){
						pecas.setValor(BigDecimal.valueOf(rs.getDouble("UNCS")));
						pecas.setValorTotal(BigDecimal.valueOf(rs.getDouble("UNCS_ORQY")));
					}
					pecas.setQtdNaoAtendido(Long.valueOf(rs.getString("BOIMCT").trim()));
					pecas.setNtda((rs.getString("NTDA") != null)?rs.getString("NTDA").trim():null);
					pecas.setIdDocSegOper(segOper);

					pecas.setIpi(BigDecimal.ZERO);
					pecas.setIcmsub(BigDecimal.ZERO);
					pecas.setTotalTributos(BigDecimal.ZERO);
					//				if("S".equals(possuiSubTributaria)){
					//					pecas.setIpi(BigDecimal.valueOf(rs.getDouble("IPI")));
					//					pecas.setIcmsub(BigDecimal.valueOf(rs.getDouble("ICMSUB")));
					//					pecas.setTotalTributos(BigDecimal.valueOf(rs.getDouble("TOTALTRIBUTOS")));
					//				}
					manager.persist(pecas);
					manager.getTransaction().commit();
					//recupera todas as peças que possuem substituição tributaria da tabela temporária para fazer a atualização
					query = manager.createQuery("from GePecasTemp where idDocSegOper.id = "+segOper.getId()+" and totaltributos <> 0.00" +
							" and partNo = '"+rs.getString("PANO20").trim()+"' and partName = '"+rs.getString("DS18").trim().replaceAll("'", "")+"'" +
										" and qtd = "+Long.valueOf(rs.getString("ORQY").trim())+" " +
												" and sos1 ='"+rs.getString("SOS1").trim()+"'"+
												" and unsel ="+BigDecimal.valueOf(rs.getDouble("UNSEL_ORQY")));
					List<GePecasTemp> pecasTemp = query.getResultList();
					for (GePecasTemp gePecasTemp : pecasTemp) {
						manager.getTransaction().begin();
						query = manager.createNativeQuery("update Ge_Pecas  set ipi ="+gePecasTemp.getIpi()+", icmsub ="+gePecasTemp.getIcmsub()+", totaltributos ="+gePecasTemp.getTotalTributos()+
								" where PART_NO = '"+gePecasTemp.getPartNo()+"' and PART_NAME = '"+gePecasTemp.getPartName().replace("'", "")+"'" +
										" and qtd = "+gePecasTemp.getQtd()+" " +
												" and sos1 ='"+gePecasTemp.getSos1()+"'"+
												" and ID_DOC_SEG_OPER ="+segOper.getId()+
												" and UNSEL ="+gePecasTemp.getUnsel());
//						query.setParameter("ipi", BigDecimal.valueOf(rs.getDouble("IPI")));
//						query.setParameter("icmsub", BigDecimal.valueOf(rs.getDouble("ICMSUB")));
//						query.setParameter("totalTributos", BigDecimal.valueOf(rs.getDouble("TOTALTRIBUTOS")));
						//query.setParameter("partNo", rs.getString("PANO20"));
						//query.setParameter("sos1", rs.getString("SOS1"));
						//query.setParameter("partName", rs.getString("DESCRICAO"));
						//query.setParameter("qtd", Long.valueOf(rs.getString("QTD").trim()));
						query.executeUpdate();
						manager.getTransaction().commit();
					}
				//}else{
					
//					Boolean isRemover = true;
//					for (GePecas gePecas : verificaRemovidaList) {
//						if(gePecas.getPartNo().trim().equals(rs.getString("PANO20").trim())){
//							isRemover = false;
//							break;
//						}
//						
//					}
//					if(isRemover){
//						manager.getTransaction().begin();
//						query = manager.createNativeQuery("delete from ge_pecas where id_doc_seg_oper = "+segOper.getId()+" and part_No = '"+rs.getString("PANO20").trim()+"'");
//						query.executeUpdate();
//						manager.getTransaction().commit();
//					}
					
//					if(count == 0 && pedidoAplicado == true){
//						manager.getTransaction().begin();
//						query = manager.createNativeQuery("delete from ge_pecas where id_doc_seg_oper = "+segOper.getId()+" and (valor is null or totaltributos = 0.00)");
//						query.executeUpdate();
//						manager.getTransaction().commit();
//					}
//					String SQLPECAS = "from GePecas where partNo = '"+rs.getString("PANO20").trim()+"' and partName = '"+rs.getString("DS18").replace("'", "")+"'" +
//					" and qtd = "+Long.valueOf(rs.getString("ORQY").trim())+" " +
//					" and sos1 ='"+rs.getString("SOS1")+"'"+
//					" and idDocSegOper.id ="+segOper.getId();
//					if("EXT".equals(tipoCliente)){
//						SQLPECAS +=" and valor ="+BigDecimal.valueOf(rs.getDouble("UNSEL"))+
//						" and valorTotal ="+BigDecimal.valueOf(rs.getDouble("UNSEL_ORQY"));
//					}else if("INT".equals(tipoCliente)){
//						SQLPECAS +=" and valor ="+BigDecimal.valueOf(rs.getDouble("UNCS"))+
//						" and valorTotal ="+BigDecimal.valueOf(rs.getDouble("UNCS_ORQY"));
//					}
//					
					
					
//					query = manager.createQuery(SQLPECAS);
//					GePecas pecas = new GePecas();
//					if(query.getResultList().size() >  0){
//						List<GePecas> pecasList = query.getResultList();
//						for (GePecas gePecas : pecasList) {
//							pecas = gePecas;
//							manager.getTransaction().begin();
//							pecas.setQtd(Long.valueOf(rs.getString("ORQY").trim()));
//							pecas.setSos1(rs.getString("SOS1").trim());
//							pecas.setPartNo(rs.getString("PANO20").trim());
//							pecas.setPartName(rs.getString("DS18").trim().replaceAll("'", ""));
//							pecas.setUnsel(BigDecimal.valueOf(rs.getDouble("UNSEL_ORQY")));
//							if("EXT".equals(tipoCliente)){
//								pecas.setValor(BigDecimal.valueOf(rs.getDouble("UNSEL")));
//								pecas.setValorTotal(BigDecimal.valueOf(rs.getDouble("UNSEL_ORQY")));
//							}else if("INT".equals(tipoCliente)){
//								pecas.setValor(BigDecimal.valueOf(rs.getDouble("UNCS")));
//								pecas.setValorTotal(BigDecimal.valueOf(rs.getDouble("UNCS_ORQY")));
//							}
//							pecas.setQtdNaoAtendido(Long.valueOf(rs.getString("BOIMCT").trim()));
//							pecas.setIdDocSegOper(segOper);
//
//							pecas.setIpi(BigDecimal.ZERO);
//							pecas.setIcmsub(BigDecimal.ZERO);
//							pecas.setTotalTributos(BigDecimal.ZERO);
//							//				if("S".equals(possuiSubTributaria)){
//							//					pecas.setIpi(BigDecimal.valueOf(rs.getDouble("IPI")));
//							//					pecas.setIcmsub(BigDecimal.valueOf(rs.getDouble("ICMSUB")));
//							//					pecas.setTotalTributos(BigDecimal.valueOf(rs.getDouble("TOTALTRIBUTOS")));
//							//				}
//							manager.merge(pecas);
//							manager.getTransaction().commit();
//						}
//
//					}else{
//
//						manager.getTransaction().begin();
//						pecas.setQtd(Long.valueOf(rs.getString("ORQY").trim()));
//						pecas.setSos1(rs.getString("SOS1").trim());
//						pecas.setPartNo(rs.getString("PANO20").trim());
//						pecas.setPartName(rs.getString("DS18").trim().replaceAll("'", ""));
//						pecas.setUnsel(BigDecimal.valueOf(rs.getDouble("UNSEL_ORQY")));
//						if("EXT".equals(tipoCliente)){
//							pecas.setValor(BigDecimal.valueOf(rs.getDouble("UNSEL")));
//							pecas.setValorTotal(BigDecimal.valueOf(rs.getDouble("UNSEL_ORQY")));
//						}else if("INT".equals(tipoCliente)){
//							pecas.setValor(BigDecimal.valueOf(rs.getDouble("UNCS")));
//							pecas.setValorTotal(BigDecimal.valueOf(rs.getDouble("UNCS_ORQY")));
//						}
//						pecas.setQtdNaoAtendido(Long.valueOf(rs.getString("BOIMCT").trim()));
//						pecas.setIdDocSegOper(segOper);
//
//						pecas.setIpi(BigDecimal.ZERO);
//						pecas.setIcmsub(BigDecimal.ZERO);
//						pecas.setTotalTributos(BigDecimal.ZERO);
//						//				if("S".equals(possuiSubTributaria)){
//						//					pecas.setIpi(BigDecimal.valueOf(rs.getDouble("IPI")));
//						//					pecas.setIcmsub(BigDecimal.valueOf(rs.getDouble("ICMSUB")));
//						//					pecas.setTotalTributos(BigDecimal.valueOf(rs.getDouble("TOTALTRIBUTOS")));
//						//				}
//						manager.merge(pecas);
//						manager.getTransaction().commit();
//					}
//				}
				count++;

			}
			if("S".equals(possuiSubTributaria)){
				SQL = "select parts.TOTSEL PUNI, parts.ORQY QTD, parts.IPI IPI, parts.ICMSUB ICMSUB, parts.VLRTOT TOTALTRIBUTOS," +
				" trim(parts.PANO20) PANO20, trim(parts.SOS1) SOS1, trim(parts.DESCRICAO) DESCRICAO from "+IConstantAccess.PESARDRTRIBUTACAO+".RDRPARTS parts where parts.RFDCNO = '"+numDoc+"'";
				prstmt_ = con.prepareStatement(SQL);
				rs = prstmt_.executeQuery();
				while(rs.next()){
					manager.getTransaction().begin();
					query = manager.createNativeQuery("update Ge_Pecas  set ipi ="+rs.getBigDecimal("IPI")+", icmsub ="+rs.getBigDecimal("ICMSUB")+", totaltributos ="+rs.getBigDecimal("TOTALTRIBUTOS")+
							" where PART_NO = '"+rs.getString("PANO20")+"' and PART_NAME = '"+rs.getString("DESCRICAO").replace("'", "")+"'" +
									" and qtd = "+Long.valueOf(rs.getString("QTD").trim())+" " +
											" and sos1 ='"+rs.getString("SOS1")+"'"+
											" and ID_DOC_SEG_OPER ="+segOper.getId()+
											" and UNSEL ="+BigDecimal.valueOf(rs.getDouble("PUNI")));
//					query.setParameter("ipi", BigDecimal.valueOf(rs.getDouble("IPI")));
//					query.setParameter("icmsub", BigDecimal.valueOf(rs.getDouble("ICMSUB")));
//					query.setParameter("totalTributos", BigDecimal.valueOf(rs.getDouble("TOTALTRIBUTOS")));
					//query.setParameter("partNo", rs.getString("PANO20"));
					//query.setParameter("sos1", rs.getString("SOS1"));
					//query.setParameter("partName", rs.getString("DESCRICAO"));
					//query.setParameter("qtd", Long.valueOf(rs.getString("QTD").trim()));
					query.executeUpdate();
					manager.getTransaction().commit();
				}
			}
			//veVenda.setHasSendCrm("S");
			//manager.merge(veVenda);

			//manager.getTransaction().commit();
			return true;
		
		} catch (SQLException e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			try {
				if(manager != null && manager.isOpen()){
					manager.close();
				}
				if(con != null){
					con.close();
					prstmt_.close();
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		throw new Exception();

	}
	public Boolean saveDataPrevisao (Long idCheckin, String dataPrevisao, TipoFreteBean freteBean){
		EntityManager manager = null;

		//PreparedStatement prstmt_ = null;
		//Connection con = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("From GeSituacaoOs where  idCheckin.id =:idCheckin");
			query.setParameter("idCheckin", idCheckin);
			GeSituacaoOs geSituacaoOs = (GeSituacaoOs)query.getSingleResult();
			geSituacaoOs.setDataPrevisaoEntrega(dateFormat.parse(dataPrevisao));
			geSituacaoOs.setIdFuncDataPrevisaoEntrega(this.bean.getMatricula());
			geSituacaoOs.setIsCheckOut("S");
			query = manager.createQuery("From GeCheckIn where id =:id");
			query.setParameter("id", idCheckin);
			GeCheckIn checkIn = (GeCheckIn) query.getSingleResult();
			checkIn.setHasSendDataPrevisao("S");
			
			GeTipoFrete tipoFrete = manager.find(GeTipoFrete.class, freteBean.getId());
			checkIn.setIdTipoFrete(tipoFrete);
			manager.merge(geSituacaoOs);
			manager.merge(checkIn);

			//insere a data de previsão de entrega
//			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
//			//recupera o fator do segmento o cliente do DBS
//			String SQL = "update B108F034."+IConstantAccess.LIB_DBS+".USPWOHD0 set DTEX = '"+fmt.format(geSituacaoOs.getDataPrevisaoEntrega())+"' where WONO ='"+geSituacaoOs.getNumeroOs()+"'";
//			prstmt_ = con.prepareStatement(SQL);
//			prstmt_.execute();
			
			
			//con = ConectionDbs.getConnecton(); 
			//prstmt_ = setDateFluxoOSDBS(dataPrevisao, con, checkIn.getNumeroOs(), "005");
			manager.getTransaction().commit();
			
			if(!verificarUsuarioSupervisor()){
				this.sendEmailSupervisor(manager, checkIn.getJobControl(), "a data de previsão de entrega foi adicionada pelo orçamentista na OS "+checkIn.getNumeroOs()+".");
				if(checkIn.getIdTipoFrete() != null){
					//new EmailHelper().sendSimpleMail("A data de previsão de entrega foi adicionada pelo orçamentista na OS "+checkIn.getNumeroOs()+" para o tipo de frete "+checkIn.getIdTipoFrete().getTipoFrete(), "Aplicar Pedido", "Craviski_Ana@pesa.com.br");
					this.sendEmailDigitador(manager, "a data de previsão de entrega foi adicionada pelo orçamentista na OS "+checkIn.getNumeroOs()+" para o tipo de frete "+checkIn.getIdTipoFrete().getTipoFrete(), checkIn.getIdOsPalm().getFilial());
				}				
			}else{
				this.sendEmailDigitador(manager, "a data de previsão de entrega foi adicionada pelo orçamentista na OS "+checkIn.getNumeroOs()+".", checkIn.getIdOsPalm().getFilial());
			}
			// se for supervisor mandar sem tipo de frete
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
//			try {
//				if(con != null){
//					prstmt_.close();
//					con.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
		}
		return true;		
	}
	/**
	 * Verifica se o usuário logado é supervisor.
	 * @return
	 */
	private Boolean verificarUsuarioSupervisor(){
		for(SistemaBean sistemaBean : bean.getSistemaList()){
			if(sistemaBean.getSigla() != null && sistemaBean.getSigla().equals("GE")){
				if(sistemaBean.getPerfilBean() != null && sistemaBean.getPerfilBean().getSigla().equals("SUPER")){
					return true;
				}
			}
		}
		return false;
	}

	public PreparedStatement setDateFluxoOSDBS(String dataPrevisao,
			Connection con, String numeroOs, String coluna) throws SQLException {
		PreparedStatement prstmt_ = null;
		try {
			ResultSet rs;
			String SQL = "select TRIM(W.NTDA) NTDA, substring(W.NTDA, 0, 15) as campoReplace from "+IConstantAccess.LIB_DBS+".WOPNOTE0 W where TRIM(W.WONO) = '"+numeroOs+"' AND TRIM(W.NTLNO1) = '"+coluna+"' AND TRIM(W.WOSGNO) = ''";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				String NTDA = rs.getString("NTDA");
				String campoReplace = rs.getString("campoReplace");
				String dataAux = NTDA.replace(campoReplace, dataPrevisao+" "+((this.bean.getEstimateBy() != null)?this.bean.getEstimateBy():"   "));
				SQL = "update "+IConstantAccess.LIB_DBS+".WOPNOTE0 W set W.NTDA = '"+dataAux+"' where TRIM(W.WONO) = '"+numeroOs+"' AND TRIM(W.NTLNO1) = '"+coluna+"' AND TRIM(W.WOSGNO) = ''";
				prstmt_ = con.prepareStatement(SQL);
				prstmt_.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prstmt_;
	}
	public Boolean setDateFluxoOSDBSServico(String dataPrevisao,
			Connection con, String numeroOs, String coluna) throws Exception {
		PreparedStatement prstmt_ = null;
		try {
			//con.setAutoCommit(false);
			ResultSet rs;
			String SQL = "select TRIM(W.NTDA) NTDA, substring(W.NTDA, 0, 15) as campoReplace from "+IConstantAccess.LIB_DBS+".WOPNOTE0 W where TRIM(W.WONO) = '"+numeroOs+"' AND TRIM(W.NTLNO1) = '"+coluna+"' AND TRIM(W.WOSGNO) = ''";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				String NTDA = rs.getString("NTDA");
				String campoReplace = rs.getString("campoReplace");
				String dataAux = NTDA.replace(campoReplace, dataPrevisao+" "+((this.bean.getEstimateBy() != null)?this.bean.getEstimateBy():"   "));
				SQL = "update "+IConstantAccess.LIB_DBS+".WOPNOTE0 W set W.NTDA = '"+dataAux+"' where TRIM(W.WONO) = '"+numeroOs+"' AND TRIM(W.NTLNO1) = '"+coluna+"' AND TRIM(W.WOSGNO) = ''";
				prstmt_ = con.prepareStatement(SQL);
				prstmt_.executeUpdate();
				return true;
			}
			//con.commit();
		} catch (Exception e) {
			throw e;
		}finally{
			if(prstmt_ != null){
				prstmt_.close();
			}
		}
		return false;
	}
	public Boolean setDateFluxoOSDBSServicoNaoAutomatico(String dataPrevisao,
			Connection con, String numeroOs, String coluna) {
		PreparedStatement prstmt_ = null;
		try {
			//con.setAutoCommit(false);
			ResultSet rs;
			String SQL = "select TRIM(W.NTDA) NTDA, substring(W.NTDA, 0, 15) as campoReplace from "+IConstantAccess.LIB_DBS+".WOPNOTE0 W where TRIM(W.WONO) = '"+numeroOs+"' AND TRIM(W.NTLNO1) = '"+coluna+"' AND TRIM(W.WOSGNO) = ''";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				String NTDA = rs.getString("NTDA");
				String campoReplace = rs.getString("campoReplace");
				String dataAux = NTDA.replace(campoReplace, dataPrevisao+" BLQ");
				dataAux += ((this.bean.getEstimateBy() != null) ? this.bean.getEstimateBy() : "   ");
				//String dataAux = NTDA.replace(campoReplace, dataPrevisao+" "+((this.bean.getEstimateBy() != null)?this.bean.getEstimateBy():"   "));
				SQL = "update "+IConstantAccess.LIB_DBS+".WOPNOTE0 W set W.NTDA = '"+dataAux+"' where TRIM(W.WONO) = '"+numeroOs+"' AND TRIM(W.NTLNO1) = '"+coluna+"' AND TRIM(W.WOSGNO) = ''";
				prstmt_ = con.prepareStatement(SQL);
				prstmt_.executeUpdate();
				return true;
			}
			//con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(prstmt_ != null){
				try {
					prstmt_.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	public Boolean setDateFluxoOSDBSServicoAutomatica(String dataPrevisao,
			Connection con, String numeroOs, String coluna) {
		PreparedStatement prstmt_ = null;
		try {
			//con.setAutoCommit(false);
			ResultSet rs;
			String SQL = "select TRIM(W.NTDA) NTDA, substring(W.NTDA, 0, 15) as campoReplace from "+IConstantAccess.LIB_DBS+".WOPNOTE0 W where TRIM(W.WONO) = '"+numeroOs+"' AND TRIM(W.NTLNO1) = '"+coluna+"' AND TRIM(W.WOSGNO) = ''";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				String NTDA = rs.getString("NTDA");
				String campoReplace = rs.getString("campoReplace");
				String dataAux = NTDA.replace(campoReplace, dataPrevisao+" AUT");
				dataAux += ((this.bean.getEstimateBy() != null)?this.bean.getEstimateBy():"   ");
				SQL = "update "+IConstantAccess.LIB_DBS+".WOPNOTE0 W set W.NTDA = '"+dataAux+"' where TRIM(W.WONO) = '"+numeroOs+"' AND TRIM(W.NTLNO1) = '"+coluna+"' AND TRIM(W.WOSGNO) = ''";
				prstmt_ = con.prepareStatement(SQL);
				prstmt_.executeUpdate();
				return true;
			}
			//con.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(prstmt_ != null){
				try {
					prstmt_.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return true;
	}
	public Boolean setDateFluxoOSDBSDadosCliente(String campo, 
			Connection con, String numeroOs, String coluna) throws Exception {
		PreparedStatement prstmt_ = null;
		try {
			//con.setAutoCommit(false);
			ResultSet rs;
			String SQL = "select TRIM(W.NTDA) NTDA, substring(W.NTDA, 0, 15) as campoReplace from "+IConstantAccess.LIB_DBS+".WOPNOTE0 W where TRIM(W.WONO) = '"+numeroOs+"' AND TRIM(W.NTLNO1) = '"+coluna+"' AND TRIM(W.WOSGNO) = ''";
			prstmt_ = con.prepareStatement(SQL);
			rs = prstmt_.executeQuery();
			if(rs.next()){
//				String NTDA = rs.getString("NTDA");
//				if(NTDA.length() > 25){
//					NTDA = NTDA.substring(0,25);
//				}
				//String campoReplace = rs.getString("campoReplace");
				//String dataAux = NTDA+" "+campo;
				if(campo.length() > 50){
					campo = campo.substring(0,50);
				}
				SQL = "update "+IConstantAccess.LIB_DBS+".WOPNOTE0 W set W.NTDA = '"+campo+"' where TRIM(W.WONO) = '"+numeroOs+"' AND TRIM(W.NTLNO1) = '"+coluna+"' AND TRIM(W.WOSGNO) = ''";
				prstmt_ = con.prepareStatement(SQL);
				prstmt_.executeUpdate();
				return true;
			}
			//con.commit();
		} catch (Exception e) {
			throw e;
		}finally{
			if(prstmt_ != null){
				prstmt_.close();
			}
		}
		return false;
	}
	public PreparedStatement setNotesFluxoOSDBS(String notes,
			Connection con, String numeroOs, String coluna) throws Exception {
		PreparedStatement prstmt_ = null;
		try {
			String SQL = "insert into "+IConstantAccess.LIB_DBS+".WOPNOTE0 (WONO, NTLNO1, NTDA, MASTRI) VALUES ('"+numeroOs+"', '"+coluna+"','"+notes.replaceAll("'", "")+"','I')";
			prstmt_ = con.prepareStatement(SQL);
			prstmt_.executeUpdate();
		} catch (Exception e) {
			throw e;
		}
		return prstmt_;
	}

	public Boolean saveDataConclusao (Long idCheckin, Date dataPrevisao){
		EntityManager manager = null;
		PreparedStatement prstmt_ = null;
		Connection con = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("From GeSituacaoOs where  idCheckin.id =:idCheckin");
			query.setParameter("idCheckin", idCheckin);
			GeSituacaoOs geSituacaoOs = (GeSituacaoOs)query.getSingleResult();
			geSituacaoOs.setDataTerminoServico(dataPrevisao);
			//geSituacaoOs.setIsCheckOut("S");
			//			query = manager.createQuery("From GeCheckin where id =: id");
			//			query.setParameter("id", idCheckin);
			//			ChekinBean bean = new ChekinBean();
			//			GeCheckIn checkIn = (GeCheckIn) query.getSingleResult();
			//			checkIn.setHasSendDataPrevisao("S");
			//			bean.setHasSendDataPrevisao("S");
			manager.merge(geSituacaoOs);
			
			if(geSituacaoOs.getIdCheckin().getIdCrmProposta() != null){
				query = manager.createNativeQuery("select ID_EMS_PROPOSTA from CRM_PROPOSTA where ID =:id_crm_propposta");
				query.setParameter("id_crm_propposta", geSituacaoOs.getIdCheckin().getIdCrmProposta());
				BigDecimal ID_EMS_PROPOSTA = (BigDecimal)query.getSingleResult();
				query = manager.createNativeQuery(" update EMS_PROPOSTA "+ 
										" set ID_STATUS_OPT =  (select id from EMS_STATUS_OPORTUNIDADE where sigla = 'FIN') "+
										" where ID =:id_ems_proposta ");
				query.setParameter("id_ems_proposta", ID_EMS_PROPOSTA);
				query.executeUpdate();
			}
			//			manager.merge(checkIn);
			manager.getTransaction().commit();	

			//insere a data de negociação e aprovação no DBS.
//			InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
//			Properties prop = new Properties();
//			prop.load(in);
//			String url = prop.getProperty("dbs.url");
//			String user = prop.getProperty("dbs.user");
//			String password =prop.getProperty("dbs.password");
//			Class.forName(prop.getProperty("dbs.driver")).newInstance();
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			//recupera o fator do segmento o cliente do DBS
			String SQL = "update B108F034."+IConstantAccess.LIB_DBS+".USPWOHD0 set DTCO = '"+fmt.format(geSituacaoOs.getDataPrevisaoEntrega())+"' where WONO ='"+geSituacaoOs.getNumeroOs()+"'";
			prstmt_ = con.prepareStatement(SQL);
			prstmt_.execute();

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			try {
				if(con != null){
					con.close();
					prstmt_.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;		
	}
	public String findDataPrevisao (Long idCheckin){
		String date = null;
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("Select data_previsao_entrega from ge_situacao_os where id_checkin ="+idCheckin);
			Object object = query.getSingleResult();
			ChekinBean bean = new ChekinBean();
			if(object != null){
				bean.setDataPrevisaoEntrega(object.toString());
				date = bean.getDataPrevisaoEntrega();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return date;
	}
	public String findDataConclusao (Long idCheckin){
		String date = null;
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("Select data_conclusao_servico from ge_situacao_os where id_checkin ="+idCheckin);
			Object object = query.getSingleResult();
			ChekinBean bean = new ChekinBean();
			if(object != null){
				bean.setDataPrevisaoEntrega(object.toString());
				date = bean.getDataPrevisaoEntrega();
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return date;
	}
	public String findServicosTerceiros(Long idCheckin){
		String valorService = null;
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("Select valor_servicos_terceiros from ge_check_in where id ="+idCheckin);
			Object object = query.getSingleResult();
			if (object != null){
				valorService = (String.valueOf(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,###,##0.00", Double.valueOf(String.valueOf(object)))));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return valorService;
	}
	public Boolean saveValorServcoTerceiros(String valorTerceiros, Long idCheckin){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("From GeCheckIn where id =:id");
			query.setParameter("id", idCheckin);
			GeCheckIn checkIn = (GeCheckIn)query.getSingleResult();
			checkIn.setValorServicosTerceiros(BigDecimal.valueOf(Double.valueOf(valorTerceiros.replace(".", "").replace(",", "."))));
			manager.merge(checkIn);
			manager.getTransaction().commit();	
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return true;
	}
	public ChekinBean verificarSePossuiCheckOut(Long idCheCkin){
		EntityManager manager = null;

		ChekinBean bean = new ChekinBean();
		try{
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select cin.numero_os, palm.cliente, palm.contato, palm.telefone, convert(varchar(10), cout.data_emissao, 103)+' '+convert(varchar(10), cout.data_emissao, 114), " +
					"palm.tecnico, palm.modelo, palm.serie, palm.horimetro, palm.idos_palm, palm.equipamento, (select descricao from ge_familia where id =palm.id_familia) " +
					"from ge_check_in cin, ge_check_out cout, ge_os_palm palm " +
					" where cin.id = cout.id_checkin " +
					" and cin.id =:idCheckIn" +
			" and cout.id_os_palm = palm.idos_palm");
			query.setParameter("idCheckIn", idCheCkin);
			List<Object[]> objects = query.getResultList();
			for (Object[] obj : objects) {
				bean.setNumeroOs((String)obj[0]);
				bean.setCliente((String)obj[1]);
				bean.setContato((String)obj[2]);
				bean.setTelefone((String)obj[3]);
				bean.setData((String)obj[4]);
				bean.setTecnico((String)obj[5]);
				bean.setModelo((String)obj[6]);
				bean.setSerie((String)obj[7]);
				bean.setHorimetro(((BigDecimal)obj[8]).toString());
				bean.setIdOsPalm(((BigDecimal)obj[9]).longValue());
				bean.setEquipamento((String)obj[10]);
				bean.setFamilia((String)obj[11]);
				return bean;
			}
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;


	}
	
	public Boolean verificarGerarcaoProposta(Long idChechIn){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String SQL = "select ((pre.valor_de_venda * ch.fator_cliente)* "+
					"	(select c.fator from ge_complexidade c "+
					"	where c.id =  "+
					"	(select p.id_complexidade from ge_preco p where p.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo)"+ 
					"	 and p.id_prefixo = (select pre.id from ge_prefixo pre where pre.descricao = substr(palm.serie,0,5))"+
					"	 and p.comp_code = seg.com_code"+
					"	 and p.job_code = seg.job_code )"+
					"	) * decode(ch.fator_urgencia,'S', (select fator_urgencia from ge_fator), 1)) * seg.qtd_comp valor_total_hora, seg.horas_prevista,"+

					"	(select c.fator from ge_complexidade c"+ 
					"	where c.id =  "+
					"	(select p.id_complexidade from ge_preco p where p.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo)"+ 
					"	 and p.id_prefixo = (select pre.id from ge_prefixo pre where pre.descricao = substr(palm.serie,0,5))"+
					"	 and p.comp_code = seg.com_code"+
					"	 and p.job_code = seg.job_code )"+
					"	) complexidade,ch.fator_urgencia , ch.fator_cliente, pre.valor_de_venda, ch.valor_servicos_terceiros"+

					"	from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre"+ 
					"	where ch.id = "+idChechIn+
					"	and ch.id = seg.id_checkin"+
					"	and ch.id_os_palm = palm.idos_palm"+
					"	and pre.descricao = substr(palm.serie,0,5)";

			Query query = manager.createNativeQuery(SQL);
			List<Object[]> list = query.getResultList();
			BigDecimal valorMaoDeObra = BigDecimal.ZERO;
			for (Object[] objects : list) {
				valorMaoDeObra = (BigDecimal)objects[0];
				if(valorMaoDeObra == null){
					return false;
				}
			}
			return true;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}
	
	public ContatoBean findContato(Long idOsPalm){
		ContatoBean bean = new ContatoBean();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			
			Query query = manager.createNativeQuery("select os.contato, os.telefone from ge_os_palm os where os.idos_palm = "+idOsPalm);
			Object[] list =(Object[]) query.getSingleResult();
			bean.setContato((String)list[0]);
			bean.setTelefone((String)list[1]);						
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return bean;		
	}
	public Boolean saveOrUpdateContato(Long idOsPalm, String contato, String telefone){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("From GeOsPalm where idosPalm =:idosPalm");
			query.setParameter("idosPalm", idOsPalm);
			GeOsPalm osPalm = (GeOsPalm)query.getSingleResult();
			osPalm.setContato(contato);
			osPalm.setTelefone(telefone);
			manager.merge(osPalm);
			manager.getTransaction().commit();	
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return true;
	}
//	public MotivoRejeicaoOSBean findMotivoRejeicao(Long idCheckin){
//		MotivoRejeicaoOSBean bean = new MotivoRejeicaoOSBean();
//		try {
//			EntityManager manager = JpaUtil.getInstance();
//			
//			Query query = manager.createNativeQuery("select os.tipo_rejeicao, os.obs from ge_situacao_os os where os.id_checkin = "+ idCheckin);
//			Object[] result = (Object[])query.getSingleResult();
//			bean.setTipoRejeicao((String)result[0]);
//			bean.setObs((String)result[1]);												
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return bean;		
//	}
	/**
	 * Recupera todos os detalhes da proposta para imprimir
	 */
	public DetalhesPropostaBean findDetalhesProposta(ChekinBean chekinBean){
		EntityManager manager = null;
		DetalhesPropostaBean detalhesProppostaBean = new DetalhesPropostaBean();
		try {
			manager = JpaUtil.getInstance();
			GeCheckIn checkIn = manager.find(GeCheckIn.class, chekinBean.getId());
			
			SegmentoBusiness segmentoBusiness = new SegmentoBusiness(this.bean);
			detalhesProppostaBean.getSegmentoList().addAll(segmentoBusiness.findAllSegmentoDesassociadosProposta(chekinBean.getId()));
			
			
//			Query query = manager.createQuery("From GeSegmento where idCheckin.id =:idCheckin");
//			query.setParameter("idCheckin", checkIn.getId());
//			List<GeSegmento> segmentoList = query.getResultList();
//			
//			for (GeSegmento seg : segmentoList) {
//				SegmentoBean segmentoBean = new SegmentoBean();
//				segmentoBean.setId(seg.getId());
//				segmentoBean.setObservacao(seg.getObservacao());
//				segmentoBean.setDescricao(seg.getNumeroSegmento()+" - "+seg.getDescricaoJobCode()+" - "+seg.getDescricaoCompCode());
//				segmentoBean.setIdCheckin(checkIn.getId());
//				detalhesProppostaBean.getSegmentoList().add(segmentoBean);
//			}
			Query query = manager.createQuery("From GeDetalhesProposta where idCheckin.id =:idCheckin");
			query.setParameter("idCheckin", checkIn.getId());
			GeDetalhesProposta detalhesProposta = new GeDetalhesProposta();
			if(query.getResultList().size() > 0){
				detalhesProposta = (GeDetalhesProposta)query.getSingleResult();
			}
			
			detalhesProppostaBean.setFormaEntregaProposta(detalhesProposta.getFormaEntregaProposta());
			detalhesProppostaBean.setAosCuidados(detalhesProposta.getAosCuidados());
			detalhesProppostaBean.setTelefone(checkIn.getIdOsPalm().getTelefone());
			detalhesProppostaBean.setEmail(detalhesProposta.getEmail());
			detalhesProppostaBean.setObjetoProposta(detalhesProposta.getObjetoProposta());
			detalhesProppostaBean.setMaquina("CATERPILLAR");
			if(detalhesProposta.getMaquina() != null){
				detalhesProppostaBean.setMaquina(detalhesProposta.getMaquina());
			}
			detalhesProppostaBean.setModelo(checkIn.getIdOsPalm().getModelo());
			detalhesProppostaBean.setSerie(checkIn.getIdOsPalm().getSerie());
			detalhesProppostaBean.setObservacao(detalhesProposta.getObservacao());
			detalhesProppostaBean.setCondicaoPagamento(detalhesProposta.getCondicoesPagamento());
			detalhesProppostaBean.setPrazoEntrega(detalhesProposta.getPrazoEntrega());
			if(detalhesProposta.getValidadeProposta() != null){
				detalhesProppostaBean.setValidadeProposta(fmtPtBr.format(detalhesProposta.getValidadeProposta()));
			}
			TwFilial filial = manager.find(TwFilial.class, Long.valueOf(bean.getFilial()));
			
			detalhesProppostaBean.setAprovacaoPropostaServico(filial.getAprovacaoPropostaServico());
			if(checkIn.getIdFuncionarioDataOrcamento() != null){
				TwFuncionario funcionario = manager.find(TwFuncionario.class, checkIn.getIdFuncionarioDataOrcamento());
				detalhesProppostaBean.setOrcamentista(funcionario.getEplsnm());
			}else{
				detalhesProppostaBean.setOrcamentista(this.bean.getNome());
			}
			detalhesProppostaBean.setFatorUrgencia(checkIn.getFatorUrgencia());
			if(detalhesProposta.getIdCheckin() != null){
				detalhesProppostaBean.setImprimirSubTributaria(detalhesProposta.getIdCheckin().getPossuiSubTributaria());
				detalhesProppostaBean.setIsFindSubTributaria(detalhesProposta.getIdCheckin().getIsFindSubTributaria());
			}
			return detalhesProppostaBean;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}

	
	/**
	 * Salva as informações dos detalhes da proposta e envia a os para aplicar os valores de substituição tributária se for o caso
	 * @param idCheckIn
	 * @param detalhesPropostaBean
	 * @return
	 */
	public DetalhesPropostaBean saveOrUpdateDetalhesProposta(Long idCheckIn, DetalhesPropostaBean detalhesPropostaBean){
		EntityManager manager = null;
		Connection con = null;
		Statement prstmt = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("from GeDetalhesProposta where idCheckin.id =:idCheckin");
			query.setParameter("idCheckin", idCheckIn);			
			GeDetalhesProposta geDetalhesProposta = new GeDetalhesProposta();
			if(query.getResultList().size() > 0){
				geDetalhesProposta = (GeDetalhesProposta)query.getSingleResult();
			}
			
			GeCheckIn checkIn = manager.find(GeCheckIn.class, idCheckIn);
			if(detalhesPropostaBean.getId() != null && detalhesPropostaBean.getId() > 0){
				geDetalhesProposta = manager.find(GeDetalhesProposta.class, detalhesPropostaBean.getId());
				setDetalhesPeoposta(detalhesPropostaBean, manager,
						geDetalhesProposta, checkIn);
				manager.merge(geDetalhesProposta);
			}else{
				setDetalhesPeoposta(detalhesPropostaBean, manager,
						geDetalhesProposta, checkIn);
				manager.persist(geDetalhesProposta);
				detalhesPropostaBean.setId(geDetalhesProposta.getId());
			}
			
			for (SegmentoBean segmentoBean : detalhesPropostaBean.getSegmentoList()) {
				GeSegmento geSegmento = manager.find(GeSegmento.class, segmentoBean.getId());
				geSegmento.setObservacao(segmentoBean.getObservacao());
				if(segmentoBean.getNomeSegmento() != null && !"".equals(segmentoBean.getNomeSegmento())){
					geSegmento.setNomeSegmento(segmentoBean.getNomeSegmento());
				}else{
					geSegmento.setNomeSegmento(null);
				}
			}
			geDetalhesProposta.getIdCheckin().setPossuiSubTributaria(detalhesPropostaBean.getImprimirSubTributaria());
			if(detalhesPropostaBean.getImprimirSubTributaria().equals("N")){
				geDetalhesProposta.getIdCheckin().setIsFindSubTributaria("N");
				detalhesPropostaBean.setIsFindSubTributaria("N");
			} else if(detalhesPropostaBean.getImprimirSubTributaria().equals("S") && !"P".equals(geDetalhesProposta.getIdCheckin().getIsFindSubTributaria()) && !"S".equals(geDetalhesProposta.getIdCheckin().getIsFindSubTributaria())){
				geDetalhesProposta.getIdCheckin().setIsFindSubTributaria("P");
				detalhesPropostaBean.setIsFindSubTributaria("P");
				con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
	        	prstmt = con.createStatement();
				//Inclui a OS na tabela do DBS 				
				String SQL = "insert into "+IConstantAccess.PESARDRTRIBUTACAO+".RDRWONO (WONO) values('"+geDetalhesProposta.getIdCheckin().getNumeroOs()+"')";
				prstmt.executeUpdate(SQL);
			}
			geDetalhesProposta.getIdCheckin().setFatorUrgencia(detalhesPropostaBean.getFatorUrgencia());
			manager.getTransaction().commit();	
			return detalhesPropostaBean;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			if(con != null){
				try {
					con.close();
					prstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}


	private void setDetalhesPeoposta(DetalhesPropostaBean detalhesPropostaBean,
			EntityManager manager, GeDetalhesProposta geDetalhesProposta,
			GeCheckIn checkIn) throws ParseException {
		geDetalhesProposta.setFormaEntregaProposta(detalhesPropostaBean.getFormaEntregaProposta());
		geDetalhesProposta.setAosCuidados(detalhesPropostaBean.getAosCuidados());
		checkIn.getIdOsPalm().setTelefone(detalhesPropostaBean.getTelefone());
		checkIn.getIdOsPalm().setModelo(detalhesPropostaBean.getModelo());
		checkIn.getIdOsPalm().setSerie(detalhesPropostaBean.getSerie());
		geDetalhesProposta.setEmail(detalhesPropostaBean.getEmail());
		geDetalhesProposta.setObjetoProposta(detalhesPropostaBean.getObjetoProposta());
		geDetalhesProposta.setMaquina(detalhesPropostaBean.getMaquina());
		geDetalhesProposta.setCondicoesPagamento(detalhesPropostaBean.getCondicaoPagamento());
		geDetalhesProposta.setPrazoEntrega(detalhesPropostaBean.getPrazoEntrega());
		geDetalhesProposta.setValidadeProposta(fmtPtBr.parse(detalhesPropostaBean.getValidadeProposta()));
		TwFilial filial = manager.find(TwFilial.class, Long.valueOf(bean.getFilial()));
		filial.setAprovacaoPropostaServico(detalhesPropostaBean.getAprovacaoPropostaServico());
		geDetalhesProposta.setIdFuncionario(bean.getMatricula());
		geDetalhesProposta.setIdCheckin(checkIn);
		geDetalhesProposta.setObservacao(detalhesPropostaBean.getObservacao());
		geDetalhesProposta.setDataCriacao(new Date());
	}
//	public static void main(String[] args) {
//		String obsOS = "rodrigo";
//		//leva para o notes da OS
//		if(obsOS.length() > 0){
//			int initIndex = 0;
//			int lengthIndex = 3;
//			String aux = "";
//			for (initIndex = 0; initIndex < obsOS.length() ; initIndex++) {
//				aux += obsOS.charAt(initIndex);
//				if(initIndex -1 == lengthIndex){
//					System.out.println(aux);
//					lengthIndex += 23;
//					aux = "";
//				}
//			}
//			if(aux.length() > 0){
//				System.out.println(aux);
//			}
//		}
//	}

	/**
	 * Insere observação da OS no campo obsProposta do banco.
	 * @param bean
	 * @param obsOS
	 * @return
	 */
	public Boolean saveObsOS(ChekinBean bean, String obsOS, String obsJobControl, String jobControl) {
		EntityManager manager = null;
		Connection conn = null;
		Statement prstmt = null;
		//ResultSet rs = null;
		try {
			manager = JpaUtil.getInstance();
					
			conn = ConectionDbs.getConnecton();
			
			//leva para o notes da OS
//			if(obsOS.length() > 0){
//				int initIndex = 0;
//				int lengthIndex = 23;
//				String aux = "";
//				for (initIndex = 0; lengthIndex < obsOS.length(); initIndex++) {
//					aux += obsOS.charAt(initIndex);
//					if(obsOS.length() -1 == lengthIndex){
//						prstmt = new ChekinBusiness(this.bean).setDateFluxoOSDBS(aux, conn, bean.getNumeroOs(), "004");
//						lengthIndex += 23;
//						aux = "";
//					}
//				}
//				if(aux.length() > 0){
//					prstmt = new ChekinBusiness(this.bean).setDateFluxoOSDBS(aux, conn, bean.getNumeroOs(), "004");
//				}
//			}
			if(obsOS.length() > 0){
				int initIndex = 0;
				int lengthIndex = 48;
				String SQL = "";
				Integer linha = 24;
				if(bean.getTipoCliente().equals("INT")){
					SQL = "delete from "+IConstantAccess.LIB_DBS+".WOPNOTE0 n where TRIM(n.NTLNO1) > '011' and wono = '"+bean.getNumeroOs()+"'";
					linha = 12;
				}else{
					SQL = "delete from "+IConstantAccess.LIB_DBS+".WOPNOTE0 n where TRIM(n.NTLNO1) > '023' and wono = '"+bean.getNumeroOs()+"'";
					linha = 24;
				}
				prstmt = conn.createStatement();
				prstmt.executeUpdate(SQL);
				String aux = "";
				for (initIndex = 0; initIndex < obsOS.length() ; initIndex++) {
					aux += obsOS.charAt(initIndex);
					if(initIndex -1 == lengthIndex){
						prstmt = new ChekinBusiness(this.bean).setNotesFluxoOSDBS(aux.toUpperCase(), conn, bean.getNumeroOs(), (linha < 100)?"0"+linha:linha+"");
						lengthIndex += 50;
						linha += 1;
						aux = "";
					}
				}
				if(aux.length() > 0){
					prstmt = new ChekinBusiness(this.bean).setNotesFluxoOSDBS(aux.toUpperCase(), conn, bean.getNumeroOs(), (linha < 100)?"0"+linha:linha+"");
				}
			}
			manager.getTransaction().begin();

			GeCheckIn checkIn = manager.find(GeCheckIn.class, bean.getId());
			checkIn.setObsProposta(obsOS);
			Query query = manager.createQuery("From GeSegmento where idCheckin.id =:idCheckin and jobControl =:jobControl");
			query.setParameter("idCheckin", checkIn.getId());
			query.setParameter("jobControl", jobControl);
			List<GeSegmento> geSegmentos = query.getResultList();
			for (GeSegmento geSegmento : geSegmentos) {
				geSegmento.setDescricaoJobControl(obsJobControl);
			}
			//checkIn.setObsJobControl(obsJobControl);
			manager.merge(checkIn);
			manager.getTransaction().commit();	
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			return false;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			if(conn != null){
				try {
					conn.close();
					if(prstmt != null){
						prstmt.close();
					}
				} catch (SQLException e) {					
					e.printStackTrace();
				}
			}
		}

		return true;
	}
	/**
	 * Busca os dados da tabela SituacaoOS.
	 * @param bean
	 * @return
	 */	
	public SituacaoOSBean findDatasOS(ChekinBean bean) {
		EntityManager manager = null;
		Connection con = null;
        Statement prstmt = null;
        ResultSet rs = null;
		try {
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
        	prstmt = con.createStatement();
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("FROM GeSituacaoOs WHERE idCheckin.id =:idCheckin");
			query.setParameter("idCheckin", bean.getId());
			if(query.getSingleResult() != null){
				GeSituacaoOs situacaoOs = (GeSituacaoOs) query.getSingleResult();
				
				SituacaoOSBean osBean = new SituacaoOSBean();
				osBean.fromBean(situacaoOs, manager);
				String SQL = (" select trim(DESCERR) DESCERR from "+IConstantAccess.AMBIENTE_DBS+".USPPPSM0 where trim(wono) = '"+bean.getNumeroOs()+"'");
	        	rs = prstmt.executeQuery(SQL);
	        	if (rs.next()){	
	        		osBean.setStatusOs(rs.getString("DESCERR"));
	        	}
				
				return osBean;						
			}			

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			if(con != null){
				try {
					con.close();
					prstmt.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
	/**
	 * Remove o checkin sem OS (Recepção). Na tabela Ge_OS_PALM e remove as outras que são cascade.
	 * @param bean
	 * @return
	 */
	public Boolean removerCheckin(ChekinBean bean) {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			
			GeOsPalm osPalm = manager.find(GeOsPalm.class, bean.getIdOsPalm());
			manager.remove(osPalm);
			
			manager.getTransaction().commit();
			
			return true;
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		
		return false;
	}
	
	
	public String removerOS(ChekinBean bean, String osTxt) {		
		
		
		try {
			ChekinBean checkinTransferido =  naoExisteOS(osTxt);
			
			if( checkinTransferido == null ){
				return "Erro: Não existe o numero de OS informado.";
			}
			
			if( existeOSDbs(bean.getNumeroOs()) ){
				return "Erro: Existe registro da OS no DBS.";
			}
			
			return removerOSAtualizarAgendamento(osTxt, bean, checkinTransferido);
			
		} catch (Exception e) {
			e.printStackTrace();
			return "Ocorreu um erro ao realizar a operação";
		}
		
	}
	
	public Boolean removerSemSubstituicao(Long id){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeCheckIn checkIn = manager.find(GeCheckIn.class, id);
			new EmailHelper().sendSimpleMail("A os "+checkIn.getNumeroOs()+" foi removida pelo usuário "+this.bean.getNome(), "OS Removida  Sistema Oficina", "rodrigo@rdrsistemas.com.br");
			manager.remove(checkIn);
			manager.getTransaction().commit();
			return true;
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	
	
	
	/**
	 * Verifica se a OS existe para dar procedimento a exclusão da OS.
	 * @return null se não existe e ChekinBean se existe.
	 */
	private ChekinBean naoExisteOS(String osTxt) throws Exception{
		List<ChekinBean> list = this.findAllChekin("TODAS", null);
		
		for(ChekinBean chekinBean : list){
			if(chekinBean.getNumeroOs() != null && chekinBean.getNumeroOs().toUpperCase().equals(osTxt)){				
				return chekinBean;
			}
		}		
		return null;
	}
	
	/**
	 * Verifica se a OS existe no DBS.
	 * @return true se existir e false se não.
	 */
	private Boolean existeOSDbs(String numOS) throws Exception{
		Connection con = null;// MENS461
		try {
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();	
			Statement statement = con.createStatement();
			String SQL = "select * from "+ IConstantAccess.LIB_DBS+".WOPHDRS0 where TRIM(wono) = '"+numOS+"'";
			ResultSet rs = statement.executeQuery(SQL);
			if(rs.next()){
				return true;	
			}
			statement.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}finally{
			try {
				if(con != null){
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception();
			}	
		}
		return false;
	}
		
	/**
	 * Remove a OS do banco local e atualiza a tabela OF_Agendamento com o novo numero da OS.  
	 * @return Mensagem de sucesso ou mensagem de erro.
	 */
	private String removerOSAtualizarAgendamento(String osTxt, ChekinBean bean, ChekinBean checkinTransferido)throws Exception{
		
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			
			// Verifica se a Os substituta tem checkin, se não tiver o checkin da OS excluída vai para a OS substituta. 
			Query query = manager.createNativeQuery("select COUNT(*) from GE_OS_PALM_DT where OS_PALM_IDOS_PALM =:idOsPalm");
			query.setParameter("idOsPalm", checkinTransferido.getIdOsPalm());		

			if(((Integer)query.getSingleResult()) == 0){  // Se for igual a zero transfere o checkin.
				// Atualiza o checkin da Os excluída com o novo checkin da OS substituta.
				query = manager.createNativeQuery("UPDATE GE_OS_PALM_DT set OS_PALM_IDOS_PALM = "+checkinTransferido.getIdOsPalm()+
						" WHERE OS_PALM_IDOS_PALM = "+bean.getIdOsPalm());
				
				query.executeUpdate();
				
				/// Coloca a referência da OS excluída(ID) para o novo ID da OS Substituta. 
				query = manager.createNativeQuery("UPDATE GE_FOTO_INSPECAO  SET ID_OS_PALM = "+checkinTransferido.getIdOsPalm()+
						" WHERE ID_OS_PALM = "+bean.getIdOsPalm());
				
				query.executeUpdate();
				
				// Como a OS recebeu um checkin, o campo isCheckin no banco passa a ser Sim para ser visualizada no combo (com checkin) 
				query = manager.createNativeQuery("UPDATE GE_CHECK_IN SET IS_CHECK_IN = 'S'" +
						" WHERE ID = "+checkinTransferido.getId());
				
				query.executeUpdate();
				// Como a OS recebeu um checkin, o campo tipo_operacao no banco passa a ser CHECKIN para poder visualizar o CHECKIN da OS.
				query = manager.createNativeQuery("UPDATE GE_OS_PALM SET TIPO_OPERACAO = 'CHECKIN'" +
						" WHERE IDOS_PALM = "+checkinTransferido.getIdOsPalm());
				
				query.executeUpdate();
				
			}
			
			query = manager.createNativeQuery("UPDATE OF_AGENDAMENTO set NUM_OS = '"+osTxt+"'"+
					"WHERE NUM_OS = '"+bean.getNumeroOs()+"'");
			
			query.executeUpdate();
			
			query = manager.createNativeQuery("DELETE FROM GE_OS_PALM WHERE IDOS_PALM = "+bean.getIdOsPalm());
			
			query.executeUpdate();
			
			manager.getTransaction().commit();
			
			
		}catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			throw new Exception();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		
		return "Operação realizada com sucesso.";
	}
		
	
	public String findObsOs(Long idCheckIn)throws Exception{
		
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			
			Query query = manager.createNativeQuery("select convert(varchar(4000), c.OBS_PROPOSTA) from ge_check_in c where c.ID = "+idCheckIn);
			
			if(query.getResultList().size() > 0){
				return (String)query.getSingleResult(); 
			}
			
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		
		return null;
	}
	
	public List<ChekinBean> findAllChekinRental(String campoPesquisa) {
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		
		try {
						
			manager = JpaUtil.getInstance(); //Pega conexÃ£o com o banco

			//Query query = manager.createQuery("Select ch.*, s.dataPrevisaoEntrega From GeCheckIn ch, GeSituacaoOS s,GeOsPalm os where s.idCheckin = ch.id and s.dataConclusaoServico is null and os.filial =:filial"); // where idOsPalm.filial =:filial order by dataEmissao"); //Retorna uma consulta do banco= select * from
			//Query query = manager.createQuery("From GeCheckin where idOsPalm.filial =: filial");
			String sqlCheckIn = "From " +
					" GeCheckIn ch , GeOsPalm os, GeSituacaoOs so";					
					
					sqlCheckIn +=" where os.idosPalm = ch.idOsPalm ";
					//sqlCheckIn +=" and (ch.statusNegociacaoConsultor <> 'R' or ch.statusNegociacaoConsultor is null)";
					sqlCheckIn +=" and so.idCheckin.id = ch.id";
					if(campoPesquisa == null || "".equals(campoPesquisa)){
						sqlCheckIn +=" and ch.id not in (select s.idCheckin.id from GeSituacaoOs s where s.dataFaturamento is not null)  ";	
						sqlCheckIn +=" and ch.isLiberadoPOrcamentista = 'S'";
						sqlCheckIn +=" and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)";
						sqlCheckIn +=" and so.dataFaturamento is null";
					}

			sqlCheckIn += " and os.filial =:filial AND (" ;
			
			sqlCheckIn += " ch.numeroOs LIKE '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.cliente like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.serie like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.modelo like '%"+campoPesquisa.toUpperCase()+"%' )";
			//sqlCheckIn += " ORDER BY ch.id )";
			/*if(numeroOSText != null && !numeroOSText.equals("")){
				SQL_CHECKIN += " and ch.numeroOs ='"+numeroOSText.toUpperCase()+"'";
			}
			if((clienteText != null && !clienteText.equals("")) && (numeroOSText == null || numeroOSText.equals(""))){
				SQL_CHECKIN += " and os.cliente like '"+clienteText.toUpperCase()+"%'";
			}*/		
			
			sqlCheckIn += " and SUBSTRING (ch.codCliente, 5, 3)+ch.tipoCliente IN ('305INT','304INT','292INT','344INT','234INT','629INT','469INT', '209INT', '254INT', '298INT', '297INT')";
			sqlCheckIn += " and ch.tipoCliente = 'INT'";
			sqlCheckIn += " order by os.emissao desc";
			Query query = manager.createQuery(sqlCheckIn );
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = (GeSituacaoOs)pair[2];
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				//String dataLiberacaoPecas = findDataLiberacaoPecasSegmento(manager, checkIn, this.bean.getJobControl());
				//String dataTerminoServicos = findDataTerminoServico(manager, checkIn, this.bean.getJobControl());
				String dataTerminoServicos = "";
				String dataLiberacaoPecas = null;
				if(geSituacaoOs.getDataTerminoServico() != null){
					dataTerminoServicos = dateFormat.format(geSituacaoOs.getDataTerminoServico());
				}
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServicos, this.bean);
				resulList.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	
	
	public List<ChekinBean> findAllChekinRentalGarantia(String campoPesquisa) {
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		
		try {
						
			manager = JpaUtil.getInstance(); //Pega conexÃ£o com o banco

			//Query query = manager.createQuery("Select ch.*, s.dataPrevisaoEntrega From GeCheckIn ch, GeSituacaoOS s,GeOsPalm os where s.idCheckin = ch.id and s.dataConclusaoServico is null and os.filial =:filial"); // where idOsPalm.filial =:filial order by dataEmissao"); //Retorna uma consulta do banco= select * from
			//Query query = manager.createQuery("From GeCheckin where idOsPalm.filial =: filial");
			String sqlCheckIn = "select distinct ch, os, so, f From " +
					" GeCheckIn ch , GeOsPalm os, GeSituacaoOs so, PmpClientePl pl, TwFilial f";					
					
					sqlCheckIn +=" where os.idosPalm = ch.idOsPalm ";
					//sqlCheckIn +=" and (ch.statusNegociacaoConsultor <> 'R' or ch.statusNegociacaoConsultor is null)";
					sqlCheckIn +=" and so.idCheckin.id = ch.id";
					//if(campoPesquisa == null || "".equals(campoPesquisa)){
						sqlCheckIn +=" and ch.id not in (select s.idCheckin.id from GeSituacaoOs s where s.dataFaturamento is not null)  ";	
						//sqlCheckIn +=" and ch.isLiberadoPOrcamentista = 'S'";
						sqlCheckIn += " and so.dataTerminoServico is not null ";
						sqlCheckIn +=" and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)";
						sqlCheckIn +=" and so.dataFaturamento is null";
						sqlCheckIn +=" and f.stno = so.filial";
					//}
			//sqlCheckIn += " and so.dataTerminoServico is not null ";		
			sqlCheckIn += " and pl.codCliente = ch.codCliente ";
			sqlCheckIn += " and pl.lbcc in ('2Y','2E','2Z') ";
			
			
			sqlCheckIn += " and os.filial = f.stno " ;
			sqlCheckIn += " AND (ch.numeroOs LIKE '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.cliente like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.serie like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.modelo like '%"+campoPesquisa.toUpperCase()+"%' )";
			//sqlCheckIn += " ORDER BY ch.id )";
			/*if(numeroOSText != null && !numeroOSText.equals("")){
				SQL_CHECKIN += " and ch.numeroOs ='"+numeroOSText.toUpperCase()+"'";
			}
			if((clienteText != null && !clienteText.equals("")) && (numeroOSText == null || numeroOSText.equals(""))){
				SQL_CHECKIN += " and os.cliente like '"+clienteText.toUpperCase()+"%'";
			}*/		
			
			//sqlCheckIn += " and SUBSTRING (ch.codCliente, 5, 3)+ch.tipoCliente IN ('305INT','304INT','292INT','344INT','234INT','629INT','469INT', '209INT', '254INT', '298INT', '297INT')";
			//sqlCheckIn += " and ch.tipoCliente = 'INT'";
			sqlCheckIn += " order by os.emissao desc";
			Query query = manager.createQuery(sqlCheckIn );
			//query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = (GeSituacaoOs)pair[2];
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				//String dataLiberacaoPecas = findDataLiberacaoPecasSegmento(manager, checkIn, this.bean.getJobControl());
				//String dataTerminoServicos = findDataTerminoServico(manager, checkIn, this.bean.getJobControl());
				String dataTerminoServicos = "";
				String dataLiberacaoPecas = null;
				if(geSituacaoOs.getDataTerminoServico() != null){
					dataTerminoServicos = dateFormat.format(geSituacaoOs.getDataTerminoServico());
				}
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServicos, this.bean);
				TwFilial filial = (TwFilial)pair[3];
				bean.setFilialStr(filial.getStnm());
				resulList.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	
	public List<ChekinBean> findAllChekinRentalGarantiaUsadas(String campoPesquisa) {
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		
		try {
			
			
			manager = JpaUtil.getInstance(); //Pega conexÃ£o com o banco

			//Query query = manager.createQuery("Select ch.*, s.dataPrevisaoEntrega From GeCheckIn ch, GeSituacaoOS s,GeOsPalm os where s.idCheckin = ch.id and s.dataConclusaoServico is null and os.filial =:filial"); // where idOsPalm.filial =:filial order by dataEmissao"); //Retorna uma consulta do banco= select * from
			//Query query = manager.createQuery("From GeCheckin where idOsPalm.filial =: filial");
			String sqlCheckIn = "select distinct ch, os, so, f From " +
					" GeCheckIn ch , GeOsPalm os, GeSituacaoOs so, PmpClientePl pl, TwFilial f";					
					
					sqlCheckIn +=" where os.idosPalm = ch.idOsPalm ";
					//sqlCheckIn +=" and (ch.statusNegociacaoConsultor <> 'R' or ch.statusNegociacaoConsultor is null)";
					sqlCheckIn +=" and so.idCheckin.id = ch.id";
					if(campoPesquisa == null || "".equals(campoPesquisa)){
						sqlCheckIn +=" and ch.id not in (select s.idCheckin.id from GeSituacaoOs s where s.dataFaturamento is not null)  ";	
						//sqlCheckIn +=" and ch.isLiberadoPOrcamentista = 'S'";
						sqlCheckIn +=" and (so.dataTerminoServico is not null or ch.isLiberadoPOrcamentista = 'S')";
						sqlCheckIn +=" and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)";
						sqlCheckIn +=" and so.dataFaturamento is null";
						sqlCheckIn +=" and f.stno = so.filial";
					}
			//sqlCheckIn += " and so.dataTerminoServico is not null ";		
			sqlCheckIn += " and pl.codCliente = ch.codCliente ";
			sqlCheckIn += " and pl.lbcc in ('2U','3W','2R','2V') ";
			
			
			sqlCheckIn += " and os.filial = f.stno " +
					" AND (" ;
			
			sqlCheckIn += " ch.numeroOs LIKE '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.cliente like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.serie like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.modelo like '%"+campoPesquisa.toUpperCase()+"%' )";
			//sqlCheckIn += " ORDER BY ch.id )";
			/*if(numeroOSText != null && !numeroOSText.equals("")){
				SQL_CHECKIN += " and ch.numeroOs ='"+numeroOSText.toUpperCase()+"'";
			}
			if((clienteText != null && !clienteText.equals("")) && (numeroOSText == null || numeroOSText.equals(""))){
				SQL_CHECKIN += " and os.cliente like '"+clienteText.toUpperCase()+"%'";
			}*/		
			
			//sqlCheckIn += " and SUBSTRING (ch.codCliente, 5, 3)+ch.tipoCliente IN ('305INT','304INT','292INT','344INT','234INT','629INT','469INT', '209INT', '254INT', '298INT', '297INT')";
			//sqlCheckIn += " and ch.tipoCliente = 'INT'";
			sqlCheckIn += " order by os.emissao desc";
			Query query = manager.createQuery(sqlCheckIn );
			//query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
				query.setParameter("idCheckin", checkIn.getId());
				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = (GeSituacaoOs)pair[2];
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				//String dataLiberacaoPecas = findDataLiberacaoPecasSegmento(manager, checkIn, this.bean.getJobControl());
				//String dataTerminoServicos = findDataTerminoServico(manager, checkIn, this.bean.getJobControl());
				String dataTerminoServicos = "";
				String dataLiberacaoPecas = null;
				if(geSituacaoOs.getDataTerminoServico() != null){
					dataTerminoServicos = dateFormat.format(geSituacaoOs.getDataTerminoServico());
				}
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServicos, this.bean);
				TwFilial filial = (TwFilial)pair[3];
				bean.setFilialStr(filial.getStnm());
				resulList.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	
	
	
	public List<ChekinBean> findAllChekinGarantia(String campoPesquisa) {
		List<ChekinBean> resulList = new ArrayList<ChekinBean>();
		EntityManager manager = null;
		
		try {
			
			manager = JpaUtil.getInstance(); //Pega conexÃ£o com o banco
			
			//Query query = manager.createQuery("Select ch.*, s.dataPrevisaoEntrega From GeCheckIn ch, GeSituacaoOS s,GeOsPalm os where s.idCheckin = ch.id and s.dataConclusaoServico is null and os.filial =:filial"); // where idOsPalm.filial =:filial order by dataEmissao"); //Retorna uma consulta do banco= select * from
			//Query query = manager.createQuery("From GeCheckin where idOsPalm.filial =: filial");
			String sqlCheckIn = "From " +
			" GeCheckIn ch , GeOsPalm os, GeSituacaoOs so";					
			
			sqlCheckIn +=" where os.idosPalm = ch.idOsPalm ";
			//sqlCheckIn +=" and (ch.statusNegociacaoConsultor <> 'R' or ch.statusNegociacaoConsultor is null)";
			sqlCheckIn +=" and so.idCheckin.id = ch.id";
			if(campoPesquisa == null || "".equals(campoPesquisa)){
				sqlCheckIn +=" and ch.id not in (select s.idCheckin.id from GeSituacaoOs s where s.dataFaturamento is not null)  ";	
				//sqlCheckIn +=" and ch.isLiberadoPOrcamentista = 'S'";
				sqlCheckIn +=" and (ch.numeroOs not in (select cout.numeroOs from GeCheckOut cout) or ch.numeroOs is null)";
				sqlCheckIn +=" and so.encerrarAutomatica is null";
			}
			
			sqlCheckIn += " and os.filial =:filial AND (" ; 
			
			sqlCheckIn += " ch.numeroOs LIKE '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.cliente like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.serie like '%"+campoPesquisa.toUpperCase()+"%'";
			sqlCheckIn += " OR os.modelo like '%"+campoPesquisa.toUpperCase()+"%' )";
			//sqlCheckIn += " ORDER BY ch.id )";
			/*if(numeroOSText != null && !numeroOSText.equals("")){
				SQL_CHECKIN += " and ch.numeroOs ='"+numeroOSText.toUpperCase()+"'";
			}
			if((clienteText != null && !clienteText.equals("")) && (numeroOSText == null || numeroOSText.equals(""))){
				SQL_CHECKIN += " and os.cliente like '"+clienteText.toUpperCase()+"%'";
			}*/		
			
			sqlCheckIn += " and SUBSTRING (ch.codCliente, 5, 3)+ch.tipoCliente IN ('243INT','247INT','249INT','023INT','241INT','463INT','468INT','267INT','268INT','288INT','998INT', '025INT','469INT','025INT','294INT','295INT','242INT','234INT','235INT')";
			sqlCheckIn += " and ch.tipoCliente = 'INT'";
			sqlCheckIn += " order by os.emissao desc";
			Query query = manager.createQuery(sqlCheckIn );
			query.setParameter("filial", Integer.valueOf(bean.getFilial()).toString());
			List<Object[]> list = (List<Object[]>) query.getResultList();
			for (Object[] pair : list) {
				ChekinBean bean = new ChekinBean();
				GeCheckIn checkIn = (GeCheckIn)pair[0];
//				query =  manager.createQuery("from GeSituacaoOs where idCheckin.id =:idCheckin");
//				query.setParameter("idCheckin", checkIn.getId());
//				List<GeSituacaoOs> situacaoOsList = query.getResultList();
				GeSituacaoOs geSituacaoOs = (GeSituacaoOs)pair[2];
				GeOsPalm geOsPalm = (GeOsPalm)pair[1];
				//String dataLiberacaoPecas = findDataLiberacaoPecasSegmento(manager, checkIn, this.bean.getJobControl());
				//String dataTerminoServicos = findDataTerminoServico(manager, checkIn, this.bean.getJobControl());
				String dataTerminoServicos = "";
				String dataLiberacaoPecas = null;
				if(geSituacaoOs.getDataTerminoServico() != null){
					dataTerminoServicos = dateFormat.format(geSituacaoOs.getDataTerminoServico());
				}
				bean.fromBean(checkIn, geSituacaoOs,geOsPalm, dataLiberacaoPecas, dataTerminoServicos, this.bean);
				resulList.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return resulList;
	}
	
	public String sincronizarJBCD(String codigo){
		ResultSet rs = null;
		PreparedStatement prstmt_ = null;
		EntityManager manager = null;

		Connection con = null;
		try {
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			String SQL_JBCDDS_GE = "select jbcd.JBCD, jbcd.JBCDDS from "+IConstantAccess.LIB_DBS+".SHLJOBC0 jbcd where jbcd.JBCD = '"+codigo+"'";
			prstmt_ = con.prepareStatement(SQL_JBCDDS_GE);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				manager = JpaUtil.getInstance();
				manager.getTransaction().begin();
				String SQL = " select j.JBCDDS from JBCD j  where JBCD = '"+codigo+"'";
				Query query = manager.createNativeQuery(SQL);
				if(query.getResultList().size()>0){
					SQL = "update JBCD set JBCDDS = '"+rs.getString("JBCDDS").trim()+"' where JBCD = '"+codigo+"'";
					query = manager.createNativeQuery(SQL);
					query.executeUpdate();	
					manager.getTransaction().commit();
					return "Job Code sincronizado com sucesso!";
				}else {
					SQL = "INSERT into JBCD (JBCD, JBCDDS) VALUES ('"+codigo+"','"+rs.getString("JBCDDS").trim()+"')";
					query = manager.createNativeQuery(SQL);
					query.executeUpdate();
					manager.getTransaction().commit();
					return "Job Code sincronizado com sucesso!";
				}
			}else{
				return "O Job Code não está cadastrado no DBS.\nFavor cadastrá-lo!";
			}

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			if(con != null){
				try {
					con.close();
					prstmt_.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return "Erro ao Sincronizar Job Code";

	}
	
	public String sincronizarCPTCD(String codigo){
		ResultSet rs = null;
		PreparedStatement prstmt_ = null;
		EntityManager manager = null;

		Connection con = null;
		try {
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();

			String SQL_CPTCDD_GE = "select cpcd.CPTCD, cpcd.CPTCDD from "+IConstantAccess.LIB_DBS+".SHLCMPC0 cpcd where cpcd.CPTCD = '"+codigo+"'";
			prstmt_ = con.prepareStatement(SQL_CPTCDD_GE);
			rs = prstmt_.executeQuery();
			if(rs.next()){
				manager = JpaUtil.getInstance();
				manager.getTransaction().begin();
				String SQL = " select c.CPTCDD from  CPTCD c  where CPTCD = '"+codigo+"'";
				Query query = manager.createNativeQuery(SQL);
				if(query.getResultList().size()>0){
					SQL = "update CPTCD set CPTCDD = '"+rs.getString("CPTCDD").trim()+"' where CPTCD = '"+codigo+"'";
					query = manager.createNativeQuery(SQL);
					query.executeUpdate();	
					manager.getTransaction().commit();
					return "Comp. Code sincronizado com sucesso!";
				}else {
					SQL = "INSERT into CPTCD (CPTCD, CPTCDD) VALUES ('"+codigo+"','"+rs.getString("CPTCDD").trim()+"')";
					query = manager.createNativeQuery(SQL);
					query.executeUpdate();
					manager.getTransaction().commit();
					return "Comp. Code sincronizado com sucesso!";
				}
				
			}else{
				return "O Comp. Code não está cadastrado no DBS.\nFavor cadastrá-lo!";
			}

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			if(con != null){
				try {
					con.close();
					prstmt_.close();
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return "Erro ao Sincronizar Comp. Code";

	}
	
	public ChekinBean findCheckin(String numeroOs){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeCheckIn where numeroOs =:numeroOs");
			query.setParameter("numeroOs", numeroOs);
			if(query.getResultList().size() > 0){
				GeCheckIn checkIn = (GeCheckIn)query.getSingleResult();
				ChekinBean bean = new ChekinBean();
				bean.setId(checkIn.getId());
				return bean;
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}
	
	public Boolean verificarOSInvoice(String numeroOs){
		Connection con = null;
		Statement prstmt = null;
		ResultSet rs = null;
		try {
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton(); 
			prstmt = con.createStatement();
			//Verifica se a OS está OPEN
			String SQL = "select TRIM(w.ACTI) ACTI, TRIM(w.OPNDT8) OPNDT8 from "+IConstantAccess.LIB_DBS+".WOPHDRS0 w  where TRIM(w.WONO) = '"+numeroOs+"'"; 
			rs = prstmt.executeQuery(SQL);
			if(rs.next()){
				String ACTI = rs.getString("ACTI");
				if(ACTI.equals("I")){
					return true;
				}else{
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(con != null){
					con.close();
					rs.close();
					prstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}     	
		}  
		return false;
	}
	
	
	public synchronized void teste (){
		System.out.println("Teste");
		System.out.println("Teste");
	}
	
	public List<DataHeaderBean> findAllHeaderList(String data){
		List<DataHeaderBean> result = new ArrayList<DataHeaderBean>();
		try {
			Calendar calendar = new GregorianCalendar();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if(data == null){
				calendar.setTime(new Date());
				calendar.add(Calendar.DAY_OF_MONTH, -1);
			}else{
				try {
					calendar.setTime(sdf.parse(data));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			
			for(int i = 0; i < 7; i++){
				DataHeaderBean bean = new DataHeaderBean();
				switch (i) {
				case 0:
					bean.setDescricao("Data Open");
					break;
				case 1:
					bean.setDescricao("Cliente");
					break;
				case 2:
					bean.setDescricao("Número OS");
					break;
				case 3:
					bean.setDescricao("Descrição");
					break;
				case 4:
					bean.setDescricao("Modelo");
					break;
				case 5:
					bean.setDescricao("Prazo PE TS");
					break;
				case 6:
					bean.setDescricao("Filial");
					break;

				default:
					break;
				}
				result.add(bean);
			}
			for(int i = 0; i < 2; i++){
				if(calendar.get(Calendar.DAY_OF_WEEK)  == 1){
					calendar.add(Calendar.DAY_OF_MONTH, 1);
				}
				if(calendar.get(Calendar.DAY_OF_WEEK)  == 7){
					calendar.add(Calendar.DAY_OF_MONTH, 2);
				}
				DataHeaderBean bean = new DataHeaderBean();
				bean.setData(calendar.getTime());
				bean.setDescricao(semana[calendar.get(Calendar.DAY_OF_WEEK) - 1]);
				bean.setDateString(sdf.format(calendar.getTime()));
				result.add(bean);
				calendar.add(Calendar.DAY_OF_MONTH, 1);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<String> findAllJobControlLegenda(String tipoCliente, String jobControl, String campoPesquisa, String beginDate, String endDate, String codigos){
		List<String> jbct = new ArrayList<String>();
		EntityManager manager = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat dateFormatSQLSERVER = new SimpleDateFormat("yyyy-MM-dd");
			manager = JpaUtil.getInstance();

			String SQL = "select distinct  seg.job_control"+
					" from TW_FILIAL f,GE_SITUACAO_OS so,GE_OS_PALM palm,GE_CHECK_IN ch, GE_SEGMENTO seg"+
					" where ch.ID = seg.ID_CHECKIN"+
					//" and ch.TIPO_CLIENTE =:tipoCliente"+
					//" and seg.ID_CHECKIN = 8132"+
					" and palm.IDOS_PALM = ch.ID_OS_PALM"+
					" and so.ID_CHECKIN = ch.ID"+
					" and f.STNO = palm.FILIAL" +
					//" and seg.JOB_CONTROL =:jobControl" +
					//" and ch.IS_ENCERRADA is null" +
					" and palm.FILIAL =:FILIAL" ;
			if(jobControl != null){
				SQL += " and seg.JOB_CONTROL ='"+jobControl+"'";
			}
			if(tipoCliente != null){
				SQL += " and ch.TIPO_CLIENTE ='"+tipoCliente+"'";
			}
			if(beginDate != null && !"".equals(beginDate) && endDate != null && !"".equals(endDate)){		
				SQL +=	" and ch.DATA_EMISSAO between '"+dateFormatSQLSERVER.format(dateFormat.parse(beginDate))+" 00:00' and '"+dateFormatSQLSERVER.format(dateFormat.parse(endDate))+" 23:59'";
			}
			if(campoPesquisa != null && !campoPesquisa.trim().equals("")){
				SQL += " and (ch.NUMERO_OS like '"+campoPesquisa+"%' OR palm.MODELO like '"+campoPesquisa+"%' OR palm.cliente like '"+campoPesquisa+"%')";
			}else{
				SQL += " and so.DATA_TERMINO_SERVICO is null ";
			}
			if(codigos != null){
				if(jobControl.equals("BA-Interno")){
					SQL += " and SUBSTRING (COD_CLIENTE,5,7) not in ("+codigos+")";
				}else{
					SQL += " and SUBSTRING (COD_CLIENTE,5,7) in ("+codigos+")";
				}
				jobControl = "BA";
			}
			Query query = manager.createNativeQuery(SQL);
			query.setParameter("FILIAL", Integer.valueOf(this.bean.getFilial()));
			List<String> result = query.getResultList();
			for (String pair : result) {
				jbct.add(pair);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}	
		}
		return jbct;
	}
	
	public List<SegmentoBean> findAllStatusFluxoSegmentoOs(List<DataHeaderBean> dataHeaderList, String tipoCliente, String jobControl, String campoPesquisa, String beginDate, String endDate, String codigos){
		List<SegmentoBean> resultSegmento = new ArrayList<SegmentoBean>();
		EntityManager manager = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormatSQLSERVER = new SimpleDateFormat("yyyy-MM-dd");
		try {
			manager = JpaUtil.getInstance();

			String SQL = "select distinct convert(varchar(10),ch.DATA_ABERTURA,103) data_abertura, palm.cliente, ch.NUMERO_OS,"+
					"  (select distinct convert(varchar(4000), s.DESCRICAO_JOB_CONTROL)  from GE_SEGMENTO s where s.ID_CHECKIN = ch.id and s.JOB_CONTROL = seg.JOB_CONTROL and s.DESCRICAO_JOB_CONTROL is not null and s.DESCRICAO_JOB_CONTROL <> '' "+
					" and s.ID = ( select MAX(s.id)  from GE_SEGMENTO s where s.ID_CHECKIN = ch.id and s.JOB_CONTROL = seg.JOB_CONTROL and s.DESCRICAO_JOB_CONTROL is not null and s.DESCRICAO_JOB_CONTROL <> ''))  DESCRICAO_JOB_CONTROL, "+
					" palm.MODELO, convert(varchar(10),so.DATA_PREVISAO_ENTREGA,103) DATA_PREVISAO_ENTREGA,"+
					" convert(varchar(10),seg.DATA_TERMINO_SERVICO,103) DATA_TERMINO_SERVICO,"+
					" f.STNM, null id_segmento, seg.job_control, ch.id idCheckIn, convert(varchar(10),so.data_aprovacao,103) data_aprovacao, " +
					" convert(varchar(10),so.data_orcamento, 103) data_orcamento, ch.COD_CLIENTE, palm.SERIE, ch.ID_EQUIPAMENTO, " +
					" (select distinct convert(varchar(4000), s.OBSERVACAO_FLUXO_OS)  from GE_SEGMENTO s where s.ID_CHECKIN = ch.id and s.JOB_CONTROL = seg.JOB_CONTROL and s.OBSERVACAO_FLUXO_OS is not null and s.OBSERVACAO_FLUXO_OS <> '')  OBSERVACAO_FLUXO_OS"+
					" ,ch.id documento from TW_FILIAL f,GE_SITUACAO_OS so,GE_OS_PALM palm,GE_CHECK_IN ch, GE_SEGMENTO seg"+
					" where ch.ID = seg.ID_CHECKIN"+
					//" and ch.TIPO_CLIENTE =:tipoCliente"+
					//" and seg.ID_CHECKIN = 8132"+
					" and palm.IDOS_PALM = ch.ID_OS_PALM"+
					" and so.ID_CHECKIN = ch.ID"+
					" and f.STNO = palm.FILIAL" +
					" and seg.JOB_CONTROL =:jobControl" +
					//" and ch.IS_ENCERRADA is null" +
					" and palm.FILIAL =:FILIAL" ;
			if(beginDate != null && !"".equals(beginDate) && endDate != null && !"".equals(endDate)){		
				SQL +=	" and ch.DATA_EMISSAO between '"+dateFormatSQLSERVER.format(dateFormat.parse(beginDate))+" 00:00' and '"+dateFormatSQLSERVER.format(dateFormat.parse(endDate))+" 23:59'";
			}
			if(campoPesquisa != null && !campoPesquisa.trim().equals("")){
				SQL += " and (ch.NUMERO_OS like '"+campoPesquisa+"%' OR palm.MODELO like '"+campoPesquisa+"%' OR palm.cliente like '"+campoPesquisa+"%')";
			}else{
				SQL += " and so.DATA_TERMINO_SERVICO is null ";
			}
			if(codigos != null){
				if(jobControl.equals("BA-Interno")){
					SQL += " and SUBSTRING (COD_CLIENTE,5,7) not in ("+codigos+")";
				}else{
					SQL += " and SUBSTRING (COD_CLIENTE,5,7) in ("+codigos+")";
				}
				jobControl = "BA";
			}
			if(tipoCliente != null && !"TD".equals(tipoCliente)){
				SQL += " and ch.TIPO_CLIENTE ='"+tipoCliente+"'";
			}
			Query query = manager.createNativeQuery(SQL);
			//query.setParameter("tipoCliente", tipoCliente);
			query.setParameter("jobControl", jobControl);
			query.setParameter("FILIAL", Integer.valueOf(this.bean.getFilial()));
			List<Object[]> result = query.getResultList();
			for (Object [] pair : result) {
				SegmentoBean bean = new SegmentoBean();
				
				if(pair[11] == null && pair[12] != null){
					Date diaAtual = new Date();
					double dias = DateHelper.diferencaEmDia( dateFormat.parse((String)pair[12]) , diaAtual);
					if(dias > 30){
						bean.setIsMoreThirtyDay("S");
					}
				}
				bean.setDateOpen((String)pair[0]);
				bean.setCliente((String)pair[1]);
				bean.setNumOs((String)pair[2]);
				bean.setDescricaojobControl((String)pair[3]);
				bean.setModelo((String)pair[4]);
				bean.setStatusPrazo("NA");
				bean.setPrazoPETS("");
				if(pair[5] != null){
					bean.setPrazoPETS((String)pair[5]);
					if(pair[6] != null){
						Date datePrevisaoEntrega = dateFormat.parse((String)pair[5]);
						Date dateTerminoServico = dateFormat.parse((String)pair[6]);
						if(datePrevisaoEntrega.after(dateTerminoServico)){
							bean.setStatusPrazo("EAP");//Entrega antes do prazo
						}else if(datePrevisaoEntrega.before(dateTerminoServico)){
							bean.setStatusPrazo("PN");//prazo não cumprido
						}else{
							bean.setStatusPrazo("PC");//Prazo cumprido
						}
						bean.setPrazoPETS(bean.getPrazoPETS()+" "+(String)pair[6]);
					}
				}
				bean.setFilial((String)pair[7]);
				if(pair[8] != null){
					bean.setId(((BigDecimal)pair[8]).longValue());
				}
				bean.setJbctStr((String)pair[9]);
				bean.setIdCheckin(((BigDecimal)pair[10]).longValue());
				bean.setNumSerie((String)pair[14]);
				bean.setIdEquipamento((String)pair[15]);
				bean.setDocumento(((BigDecimal)pair[17]).longValue());
				if(pair[15] == null){
					bean.setIdEquipamento("");
				}
				List<SegmentoBean> segmentoList = new ArrayList<SegmentoBean>();
				
				//verificar se o dia da semana é sábado ou domingo
//				if(DateHelper.verificarDiaSemana() == 1 || DateHelper.verificarDiaSemana() == 7){
//					SegmentoBean segmentoBean = new SegmentoBean();
//					segmentoBean.setId(((BigDecimal)pair[10]).longValue());
//					segmentoBean.setSiglaStatusLegenda("CINZA");
//					segmentoList.add(segmentoBean);
//					continue;
//				}
				
				for (DataHeaderBean dataHeaderBean : dataHeaderList) {
					if(dataHeaderBean.getDateString() == null){
						continue;
					}
					SegmentoBean segmentoBean = new SegmentoBean();
					if(pair[8] != null){
						segmentoBean.setId(((BigDecimal)pair[8]).longValue());
					}
					query =  manager.createNativeQuery("select max(df.ID) from ge_segmento seg, GE_DATA_FLUXO_SEGMENTO df " +
							" where seg.id_checkin =:id_checkin " +
							" and seg.job_Control =:jobControl " +
							" and convert(varchar(10),df.DATA,103) = '"+dataHeaderBean.getDateString()+"'" +
							" and df.ID_segmento = seg.ID");
					query.setParameter("id_checkin", ((BigDecimal)pair[10]).longValue());
					query.setParameter("jobControl", jobControl);
					List<BigDecimal> idSegmentos = query.getResultList();
					String fluxoSegmentos = "";
					for (BigDecimal bigDecimal : idSegmentos) {
						fluxoSegmentos += bigDecimal+",";
					}
					if(fluxoSegmentos.length() > 0){
						fluxoSegmentos = fluxoSegmentos.substring(0, fluxoSegmentos.length() - 1);
					}
					
					segmentoBean.setIdCheckin(((BigDecimal)pair[10]).longValue());
					segmentoBean.setSiglaStatusLegenda("BRANCO");
					segmentoBean.setDataHeader(dataHeaderBean.getDateString());
					query = manager.createNativeQuery("select distinct LP.SIGLA, LP.DESCRICAO, df.OBSERVACAO from  GE_DATA_FLUXO_SEGMENTO df, GE_LEGENDA_PROCESSO_OFICINA LP  "+
													  "	where convert(varchar(10),df.DATA,103) = '"+dataHeaderBean.getDateString()+"'"+  
													  "	and df.ID in(" +fluxoSegmentos+")"+
													  " AND LP.ID = df.ID_LEGENDA_PROCESSO_OFICINA");

					//query.setParameter("ID_SEGMENTO", idSegmento.longValue());
					if(query.getResultList().size() > 0){
						Object []parameter = (Object[])query.getSingleResult();
						segmentoBean.setSiglaStatusLegenda((String) parameter[0]);
						segmentoBean.setDescricaoStatusLegenda((String)parameter[1]);
						segmentoBean.setObservacaoStatusLegenda((String)pair[16]);
						if(pair[16] != null){
							segmentoBean.setDescricaoStatusLegenda((String) parameter[1]+"\n"+(String)pair[16]);
						}
						segmentoBean.setJbctStr((String)pair[9]);
					}
					segmentoList.add(segmentoBean);
					
				}
				bean.setSegmentoList(segmentoList);
				resultSegmento.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}	
		}
		return resultSegmento;
	}
	
//	public List<SegmentoBean> findAllStatusFluxoSegmentoOs(List<DataHeaderBean> dataHeaderList, String tipoCliente, String jobControl, String campoPesquisa, String beginDate, String endDate, String codigos){
//		List<SegmentoBean> resultSegmento = new ArrayList<SegmentoBean>();
//		EntityManager manager = null;
//		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//		SimpleDateFormat dateFormatSQLSERVER = new SimpleDateFormat("yyyy-MM-dd");
//		try {
//			manager = JpaUtil.getInstance();
//
//			String SQL = "select distinct convert(varchar(10),ch.DATA_ABERTURA,103) data_abertura, palm.cliente, ch.NUMERO_OS,"+
//					" convert(varchar(4000), seg.DESCRICAO_JOB_CONTROL) DESCRICAO_JOB_CONTROL,"+
//					" palm.MODELO, convert(varchar(10),so.DATA_PREVISAO_ENTREGA,103) DATA_PREVISAO_ENTREGA,"+
//					" convert(varchar(10),seg.DATA_TERMINO_SERVICO,103) DATA_TERMINO_SERVICO,"+
//					" f.STNM, null id_segmento, ch.job_control, ch.id idCheckIn, convert(varchar(10),so.data_aprovacao,103) data_aprovacao, " +
//					" convert(varchar(10),so.data_orcamento, 103) data_orcamento, ch.COD_CLIENTE, palm.SERIE, ch.ID_EQUIPAMENTO, seg.OBSERVACAO_FLUXO_OS"+
//					" from TW_FILIAL f,GE_SITUACAO_OS so,GE_OS_PALM palm,GE_CHECK_IN ch, GE_SEGMENTO seg"+
//					" where ch.ID = seg.ID_CHECKIN"+
//					//" and seg.ID_CHECKIN = 8132"+
//					" and palm.IDOS_PALM = ch.ID_OS_PALM"+
//					" and so.ID_CHECKIN = ch.ID"+
//					" and f.STNO = palm.FILIAL" +
//					
//					//" and ch.IS_ENCERRADA is null" +
//					" and palm.FILIAL =:FILIAL" ;
//			if(jobControl != null){
//				SQL += " and seg.JOB_CONTROL ='"+jobControl+"'";
//			}
//			if(jobControl != null){
//				SQL += " and ch.TIPO_CLIENTE ='"+tipoCliente+"'";
//			}
//			if(beginDate != null && !"".equals(beginDate) && endDate != null && !"".equals(endDate)){		
//				SQL +=	" and ch.DATA_EMISSAO between '"+dateFormatSQLSERVER.format(dateFormat.parse(beginDate))+" 00:00' and '"+dateFormatSQLSERVER.format(dateFormat.parse(endDate))+" 23:59'";
//			}
//			if(campoPesquisa != null && !campoPesquisa.trim().equals("")){
//				SQL += " and (ch.NUMERO_OS like '"+campoPesquisa+"%' OR palm.MODELO like '"+campoPesquisa+"%' OR palm.cliente like '"+campoPesquisa+"%')";
//			}else{
//				SQL += " and so.DATA_TERMINO_SERVICO is null ";
//			}
//			if(codigos != null){
//				if(jobControl.equals("BA-Interno")){
//					SQL += " and SUBSTRING (COD_CLIENTE,5,7) not in ("+codigos+")";
//				}else{
//					SQL += " and SUBSTRING (COD_CLIENTE,5,7) in ("+codigos+")";
//				}
//				jobControl = "BA";
//			}
//			Query query = manager.createNativeQuery(SQL);
//			//query.setParameter("tipoCliente", tipoCliente);
//			//query.setParameter("jobControl", jobControl);
//			query.setParameter("FILIAL", Integer.valueOf(this.bean.getFilial()));
//			List<Object[]> result = query.getResultList();
//			for (Object [] pair : result) {
//				SegmentoBean bean = new SegmentoBean();
//				
//				if(pair[11] == null && pair[12] != null){
//					Date diaAtual = new Date();
//					double dias = DateHelper.diferencaEmDia( dateFormat.parse((String)pair[12]) , diaAtual);
//					if(dias > 30){
//						bean.setIsMoreThirtyDay("S");
//					}
//				}
//				bean.setDateOpen((String)pair[0]);
//				bean.setCliente((String)pair[1]);
//				bean.setNumOs((String)pair[2]);
//				bean.setDescricaojobControl((String)pair[3]);
//				bean.setModelo((String)pair[4]);
//				bean.setStatusPrazo("NA");
//				bean.setPrazoPETS("");
//				if(pair[5] != null){
//					bean.setPrazoPETS((String)pair[5]);
//					if(pair[6] != null){
//						Date datePrevisaoEntrega = dateFormat.parse((String)pair[5]);
//						Date dateTerminoServico = dateFormat.parse((String)pair[6]);
//						if(datePrevisaoEntrega.after(dateTerminoServico)){
//							bean.setStatusPrazo("EAP");//Entrega antes do prazo
//						}else if(datePrevisaoEntrega.before(dateTerminoServico)){
//							bean.setStatusPrazo("PN");//prazo não cumprido
//						}else{
//							bean.setStatusPrazo("PC");//Prazo cumprido
//						}
//						bean.setPrazoPETS(bean.getPrazoPETS()+" "+(String)pair[6]);
//					}
//				}
//				bean.setFilial((String)pair[7]);
//				if(pair[8] != null){
//					bean.setId(((BigDecimal)pair[8]).longValue());
//				}
//				bean.setJbctStr((String)pair[9]);
//				bean.setIdCheckin(((BigDecimal)pair[10]).longValue());
//				bean.setNumSerie((String)pair[14]);
//				bean.setIdEquipamento((String)pair[15]);
//				if(pair[15] == null){
//					bean.setIdEquipamento("");
//				}
//				List<SegmentoBean> segmentoList = new ArrayList<SegmentoBean>();
//				
//				//verificar se o dia da semana é sábado ou domingo
////				if(DateHelper.verificarDiaSemana() == 1 || DateHelper.verificarDiaSemana() == 7){
////					SegmentoBean segmentoBean = new SegmentoBean();
////					segmentoBean.setId(((BigDecimal)pair[10]).longValue());
////					segmentoBean.setSiglaStatusLegenda("CINZA");
////					segmentoList.add(segmentoBean);
////					continue;
////				}
//				
//				for (DataHeaderBean dataHeaderBean : dataHeaderList) {
//					if(dataHeaderBean.getDateString() == null){
//						continue;
//					}
//					SegmentoBean segmentoBean = new SegmentoBean();
//					if(pair[8] != null){
//						segmentoBean.setId(((BigDecimal)pair[8]).longValue());
//					}
//					SQL = "select min(ID) from ge_segmento where id_checkin =:id_checkin  ";
//					if(jobControl != null){
//						SQL += "and job_Control = '"+jobControl+"'";
//					}else{
//						SQL += "and job_Control = '"+(String)pair[9]+"'";
//					}
//					query =  manager.createNativeQuery(SQL);
//					query.setParameter("id_checkin", ((BigDecimal)pair[10]).longValue());
//					//query.setParameter("jobControl", jobControl);
//					List<BigDecimal> idSegmentos = query.getResultList();
//					BigDecimal idSegmento = idSegmentos.get(0); 
//					if(idSegmento == null){
//						continue;
//					}
//					segmentoBean.setIdCheckin(((BigDecimal)pair[10]).longValue());
//					segmentoBean.setSiglaStatusLegenda("BRANCO");
//					segmentoBean.setDataHeader(dataHeaderBean.getDateString());
//					query = manager.createNativeQuery("select LP.SIGLA, LP.DESCRICAO, df.OBSERVACAO from  GE_DATA_FLUXO_SEGMENTO df, GE_LEGENDA_PROCESSO_OFICINA LP  "+
//													  "	where convert(varchar(10),df.DATA,103) = '"+dataHeaderBean.getDateString()+"'"+  
//													  "	and df.ID_SEGMENTO =:ID_SEGMENTO" +
//													  " AND LP.ID = df.ID_LEGENDA_PROCESSO_OFICINA");
//
//					query.setParameter("ID_SEGMENTO", idSegmento.longValue());
//					if(query.getResultList().size() > 0){
//						Object []parameter = (Object[])query.getSingleResult();
//						segmentoBean.setSiglaStatusLegenda((String) parameter[0]);
//						segmentoBean.setDescricaoStatusLegenda((String)parameter[1]);
//						segmentoBean.setObservacaoStatusLegenda((String)pair[16]);
//						if(pair[16] != null){
//							segmentoBean.setDescricaoStatusLegenda((String) parameter[1]+"\n"+(String)pair[16]);
//						}
//						segmentoBean.setJbctStr((String)pair[9]);
//					}
//					segmentoList.add(segmentoBean);
//					
//				}
//				bean.setSegmentoList(segmentoList);
//				resultSegmento.add(bean);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if(manager != null && manager.isOpen()){
//				manager.close();
//			}	
//		}
//		return resultSegmento;
//	}





	/**
	 * Insere observação da OS no campo obsProposta do banco.
	 * @param bean
	 * @param obsOS
	 * @return
	 */
	public Boolean saveObsJobControl(String obsJobControl, Long idCheckIn, String jobControl) {
		EntityManager manager = null;

		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeSegmento where idCheckin.id =:idCheckin and jobControl =:jobControl");
			query.setParameter("idCheckin", idCheckIn);
			query.setParameter("jobControl", jobControl);
			List<GeSegmento> geSegmentos = query.getResultList();
			for (GeSegmento geSegmento : geSegmentos) {
				manager.getTransaction().begin();
				geSegmento.setDescricaoJobControl(obsJobControl);
				manager.merge(geSegmento);
				manager.getTransaction().commit();			
			}

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			return false;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}

		return true;
	}
	public boolean salvarFormularioAprovarOs(GeFormularioAprovacaoOsBean bean) throws Exception{
		GeFormularioAprovacaoOs aprovacaoOs = new GeFormularioAprovacaoOs();
		
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			if(bean.getId() > 0){
				aprovacaoOs = manager.find(GeFormularioAprovacaoOs.class, bean.getId());
				bean.setStatus(null);
				bean.setTipoSolicitacao(aprovacaoOs.getTipoSolicitacao());
			}else{
				aprovacaoOs.setNomeFilial(bean.getFilialCriacaoOS());
			}
			bean.fromBean(aprovacaoOs);
//			GeCheckIn checkIn = manager.find(GeCheckIn.class, bean.getIdCheckIn());
//			checkIn.setIsAprovadoOs("P");
			//TwFilial filial = manager.find(TwFilial.class, Long.valueOf(Long.valueOf(bean.getFilialCriacaoOS())));
			manager.getTransaction().begin();
			aprovacaoOs.setIdFuncionarioResponsavel(this.bean.getMatricula());
			aprovacaoOs.setDataInclusao(new Date());
			aprovacaoOs.setFilial(Long.valueOf(this.bean.getFilial()));
			if(bean.getTipoSolicitacao().equals("E")){
				aprovacaoOs.setStatus("A");
			}
			manager.persist(aprovacaoOs);
			//manager.merge(checkIn);
			manager.getTransaction().commit();
			if(bean.getListaPecasAprovacao().size() > 0){
				for (PecasAprovacaoOSBean aprovacaoOSBean : bean.getListaPecasAprovacao()) { 
					GePecasAprovacao aprovacao = new GePecasAprovacao();
					aprovacao.setQtd(Long.valueOf(aprovacaoOSBean.getQtd()));
					aprovacao.setPartNumber(aprovacaoOSBean.getPartNo());
					aprovacao.setPartName(aprovacaoOSBean.getPartName());
					aprovacao.setUniteRate(BigDecimal.valueOf(  Double.valueOf(aprovacaoOSBean.getUniteRate().replace(".", "").replace(",", "."))    ));
					aprovacao.setDNPrice(BigDecimal.valueOf(  Double.valueOf(aprovacaoOSBean.getDnPrice().replace(".", "").replace(",", "."))    ));
					aprovacao.setUniteCL(BigDecimal.valueOf(  Double.valueOf(aprovacaoOSBean.getUniteCl().replace(".", "").replace(",", "."))    ));
					aprovacao.setCLPrice(BigDecimal.valueOf(  Double.valueOf(aprovacaoOSBean.getClPrice().replace(".", "").replace(",", "."))    ));
					aprovacao.setIdAprovacaoOs(aprovacaoOs);
					manager.getTransaction().begin();
					manager.persist(aprovacao);
					manager.getTransaction().commit();
				}
			}
			if(bean.getTipoSolicitacao().equals("R") || bean.getTipoSolicitacao().equals("C")){
				sendMailPerfilAprovadorInterno(bean, manager);
				
			}
			if(bean.getTipoSolicitacao().equals("G") || bean.getTipoSolicitacao().equals("G")){
				sendMailPerfilGarantia(bean, manager);
				
			}
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	private void sendMailPerfilAprovadorInterno(
			GeFormularioAprovacaoOsBean bean, EntityManager manager) {
		try {
			//Envia e-mail para todos os envolvidos no processo de OS.
			Query query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM, (select p.SIGLA from ADM_PERFIL p where p.ID = perfil.ID_PERFIL) SIGLA FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+ 
					  " WHERE tw.epidno = perfil.id_tw_usuario"+
					  " AND perfil.ID_PERFIL in (select id from ADM_PERFIL"+
					  //" WHERE SIGLA in ('RECP','DIG','ORC','CONS')"+				 
					  //" WHERE SIGLA in ('DIG','ORC','CONS')"+	
					  " WHERE SIGLA in ('APRINT')"+
					  " AND TIPO_SISTEMA = 'GE')"); 
			
			//query.setParameter("filial", Integer.valueOf(this.bean.getFilial()).toString());
			
			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				EmailHelper helper = new EmailHelper();
				for(Object [] pair : list){
					helper.sendSimpleMail("Gostaria de solicitar a aprovação de criação da OS para o equipamento de série "+bean.getSerie()+", modelo "+bean.getModelo(),"Solicitação de Aprovação de OS", (String)pair[0]);
				}				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendMailPerfilGarantia(
			GeFormularioAprovacaoOsBean bean, EntityManager manager) {
		try {
			//Envia e-mail para todos os envolvidos no processo de OS.
			Query query = manager.createNativeQuery("SELECT tw.email, tw.EPLSNM, (select p.SIGLA from ADM_PERFIL p where p.ID = perfil.ID_PERFIL) SIGLA FROM tw_funcionario tw, adm_perfil_sistema_usuario perfil"+ 
					  " WHERE tw.epidno = perfil.id_tw_usuario"+
					  " AND perfil.ID_PERFIL in (select id from ADM_PERFIL"+
					  //" WHERE SIGLA in ('RECP','DIG','ORC','CONS')"+				 
					  //" WHERE SIGLA in ('DIG','ORC','CONS')"+	
					  " WHERE SIGLA in ('GEGAR')"+
					  " AND TIPO_SISTEMA = 'GE')"); 
			
			//query.setParameter("filial", Integer.valueOf(this.bean.getFilial()).toString());
			
			if(query.getResultList().size() > 0){
				List<Object []> list = query.getResultList();
				EmailHelper helper = new EmailHelper();
				for(Object [] pair : list){
					helper.sendSimpleMail("Gostaria de solicitar a aprovação de criação da OS para o equipamento de série "+bean.getSerie()+", modelo "+bean.getModelo(),"Solicitação de Aprovação de OS", (String)pair[0]);
				}				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean aprovarOs(Boolean isAprovarOs, String motivoRejeicao, GeFormularioAprovacaoOsBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			GeFormularioAprovacaoOs aprovacaoOs = manager.find(GeFormularioAprovacaoOs.class, bean.getId());
			bean.fromBean(aprovacaoOs);
			TwFuncionario funcionario = manager.find(TwFuncionario.class, aprovacaoOs.getIdFuncionarioResponsavel());
			if(isAprovarOs){
				if(motivoRejeicao != null){
					aprovacaoOs.setObsRejeicao(motivoRejeicao);
				}
				aprovacaoOs.setStatus("A");
				Query query = manager.createQuery("from TwFilial where stnm = '"+aprovacaoOs.getNomeFilial()+"'");
				TwFilial filial = (TwFilial)query.getSingleResult();
				new EmailHelper().sendSimpleMail("A sua solicitação de código "+aprovacaoOs.getId()+", série "+aprovacaoOs.getSerie()+", modelo "+aprovacaoOs.getModelo()+" foi aprovada.", "Criação OS Aprovada", funcionario.getEmail());
				this.sendEmailRecepcionaista(manager, "A solicitação de código "+aprovacaoOs.getId()+", série "+aprovacaoOs.getSerie()+", modelo "+aprovacaoOs.getModelo()+" foi aprovada.", filial.getStno().toString());
			}else{
				if(aprovacaoOs.getObsRejeicao() == null){
					aprovacaoOs.setObsRejeicao(motivoRejeicao);
				}else{
					aprovacaoOs.setObsRejeicao(aprovacaoOs.getObsRejeicao()+"\n"+motivoRejeicao);
				}
				aprovacaoOs.setStatus("R");
				new EmailHelper().sendSimpleMail("A sua solicitação de código "+aprovacaoOs.getId()+", série "+aprovacaoOs.getSerie()+", modelo "+aprovacaoOs.getModelo()+" foi rejeitada.", "Criação OS Rejeitado", funcionario.getEmail());
			}
			manager.getTransaction().begin();
			aprovacaoOs.setData(new Date());
			manager.merge(aprovacaoOs);
			manager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	
	public List<GeFormularioAprovacaoOsBean> findFormularioAprovarOs(String campo){
		EntityManager manager = null;
		List<GeFormularioAprovacaoOsBean> result = new ArrayList<GeFormularioAprovacaoOsBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = null;
			String SQL = "";
			if(campo != null && !campo.equals("")){
				SQL = "from GeFormularioAprovacaoOs fa, TwFuncionario f where (fa.codigoCliente like '"+campo+"%' or fa.cliente like '"+campo+"%' or fa.serie like '"+campo+"%' or fa.modelo like '"+campo+"%' or f.eplsnm like '"+campo+"%')" +
						" and f.epidno =  fa.idFuncionarioResponsavel ";
				//query = manager.createQuery(SQL);
				//query.setParameter("campo", campo);
				
			}else{
				SQL = "from GeFormularioAprovacaoOs fa, TwFuncionario f where fa.isUsado is null" +
				" and f.epidno =  fa.idFuncionarioResponsavel ";
				//" and (fa.STATUS <> 'A' or fa.status is null)";
				//query = manager.createQuery();
			}
			if(this.bean.getSiglaPerfil().equals("RECP")){
				query = manager.createQuery("from TwFilial where stno = "+Integer.valueOf(this.bean.getFilial()));
				TwFilial filial = (TwFilial)query.getSingleResult();
				SQL += " and fa.tipoSistema = 'OFI' and fa.nomeFilial = '"+filial.getStnm()+"'";
			}
			query = manager.createQuery(SQL);
			if(query.getResultList().size() == 0 && !"".equals(campo)){
				SQL = "from GeFormularioAprovacaoOs fa, GeCheckIn c where c.numeroOs like '"+campo+"%'" +
				" and c.id =  fa.idCheckIn ";
				query = manager.createQuery(SQL);
			}
			
			
			List<Object[]> formList = (List<Object[]>)query.getResultList();
			
			for (Object[] pair : formList) {
				GeFormularioAprovacaoOs geFormularioAprovacaoOs = (GeFormularioAprovacaoOs)pair[0];
				GeFormularioAprovacaoOsBean bean = new GeFormularioAprovacaoOsBean();
				bean.toBean(geFormularioAprovacaoOs);
				if(geFormularioAprovacaoOs.getIdCheckIn() != null && geFormularioAprovacaoOs.getIdCheckIn() > 0){
					GeCheckIn checkIn = manager.find(GeCheckIn.class, geFormularioAprovacaoOs.getIdCheckIn());
					bean.setNumOs(checkIn.getNumeroOs());
				}
				String funcAprovador = "";
				if(geFormularioAprovacaoOs.getTipoSolicitacao().equals("G") || geFormularioAprovacaoOs.getTipoSolicitacao().equals("GG") || geFormularioAprovacaoOs.getTipoSolicitacao().equals("GGG")){
					query = manager.createNativeQuery("select EPLSNM from TW_FUNCIONARIO f, ADM_PERFIL p, ADM_PERFIL_SISTEMA_USUARIO psu"+
													"	where f.EPIDNO = psu.ID_TW_USUARIO"+
													"	and p.SIGLA = 'APRGAR'"+
													"	and p.TIPO_SISTEMA = 'GE'"+
													"	and psu.ID_PERFIL = p.ID");
					List<String> aprovadorList = query.getResultList();
					for (String string : aprovadorList) {
						funcAprovador += string+"\n";
					}
				}
				
				if(geFormularioAprovacaoOs.getTipoSolicitacao().equals("C") || geFormularioAprovacaoOs.getTipoSolicitacao().equals("CC")){
					query = manager.createNativeQuery("select EPLSNM from TW_FUNCIONARIO f, ADM_PERFIL p, ADM_PERFIL_SISTEMA_USUARIO psu"+
													"	where f.EPIDNO = psu.ID_TW_USUARIO"+
													"	and p.SIGLA = 'APRINT'"+
													"	and p.TIPO_SISTEMA = 'GE'"+
													"	and psu.ID_PERFIL = p.ID");
					List<String> aprovadorList = query.getResultList();
					for (String string : aprovadorList) {
						funcAprovador += string+"\n";
					}
				}
				
				
				if(geFormularioAprovacaoOs.getTipoSolicitacao().equals("R")){
					query = manager.createNativeQuery("select EPLSNM from TW_FUNCIONARIO f, ADM_PERFIL p, ADM_PERFIL_SISTEMA_USUARIO psu"+
													"	where f.EPIDNO = psu.ID_TW_USUARIO"+
													"	and p.SIGLA = 'APRRENT'"+
													"	and p.TIPO_SISTEMA = 'GE'"+
													"	and psu.ID_PERFIL = p.ID");
					List<String> aprovadorList = query.getResultList();
					for (String string : aprovadorList) {
						funcAprovador += string+"\n";
					}
				}
				if(geFormularioAprovacaoOs.getTipoSolicitacao().equals("AG")){
					query = manager.createNativeQuery("select EPLSNM from TW_FUNCIONARIO f, ADM_PERFIL p, ADM_PERFIL_SISTEMA_USUARIO psu"+
													"	where f.EPIDNO = psu.ID_TW_USUARIO"+
													"	and p.SIGLA = 'APRGER'"+
													"	and p.TIPO_SISTEMA = 'GE'"+
													"	and psu.ID_PERFIL = p.ID");
					List<String> aprovadorList = query.getResultList();
					for (String string : aprovadorList) {
						funcAprovador += string+"\n";
					}
				}
				if(geFormularioAprovacaoOs.getTipoSolicitacao().equals("AGU")){
					query = manager.createNativeQuery("select EPLSNM from TW_FUNCIONARIO f, ADM_PERFIL p, ADM_PERFIL_SISTEMA_USUARIO psu"+
													"	where f.EPIDNO = psu.ID_TW_USUARIO"+
													"	and p.SIGLA = 'APGARU'"+
													"	and p.TIPO_SISTEMA = 'GE'"+
													"	and psu.ID_PERFIL = p.ID");
					List<String> aprovadorList = query.getResultList();
					for (String string : aprovadorList) {
						funcAprovador += string+"\n";
					}
				}
				bean.setFuncAprovador(funcAprovador);
				TwFuncionario func = manager.find(TwFuncionario.class, geFormularioAprovacaoOs.getIdFuncionarioResponsavel());
				bean.setResponsavel(func.getEplsnm());
				query = manager.createQuery("from GePecasAprovacao where idAprovacaoOs.id = "+geFormularioAprovacaoOs.getId());
				List<GePecasAprovacao> listPecas = query.getResultList();
				for (GePecasAprovacao gePecasAprovacao : listPecas) {
					PecasAprovacaoOSBean osBean = new PecasAprovacaoOSBean();
					osBean.setId(gePecasAprovacao.getId().toString());
					osBean.setPartNo(gePecasAprovacao.getPartNumber());
					osBean.setPartName(gePecasAprovacao.getPartName());
					osBean.setUniteRate(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteRate().doubleValue()));
					osBean.setDnPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getDNPrice().doubleValue()));
					osBean.setUniteCl(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteCL().doubleValue()));
					osBean.setClPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getCLPrice().doubleValue()));
					bean.getListaPecasAprovacao().add(osBean);
				}
				result.add(bean);
			}
			
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	
	public List<GeFormularioAprovacaoOsBean> findFormularioAprovarOsConcessaoRental(String campo){
		EntityManager manager = null;
		List<GeFormularioAprovacaoOsBean> result = new ArrayList<GeFormularioAprovacaoOsBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = null;
			if(campo != null && !campo.equals("")){
				query = manager.createQuery("from GeFormularioAprovacaoOs where (codigoCliente like '"+campo+"%' or cliente like '"+campo+"%' or serie like '"+campo+"%' or modelo like '"+campo+"%') and (tipoSolicitacao = 'C' or tipoSolicitacao = 'CC') and status is null and codigoCliente not like '%246' and codigoCliente not like '%240'" +
						" and codigoCliente not in (select codCliente from PmpClientePl where lbcc in ('2R'))");
				//query.setParameter("campo", campo);
			}else{
				query = manager.createQuery("from GeFormularioAprovacaoOs where (tipoSolicitacao = 'C' or tipoSolicitacao = 'CC') and status is null and codigoCliente not like '%246' and codigoCliente not like '%240'" +
				" and codigoCliente not in (select codCliente from PmpClientePl where lbcc in ('2R'))");
			}
			
			List<GeFormularioAprovacaoOs> formList = query.getResultList();
			
			for (GeFormularioAprovacaoOs geFormularioAprovacaoOs : formList) {
				GeFormularioAprovacaoOsBean bean = new GeFormularioAprovacaoOsBean();
				bean.toBean(geFormularioAprovacaoOs);
				TwFuncionario func = manager.find(TwFuncionario.class, geFormularioAprovacaoOs.getIdFuncionarioResponsavel());
				bean.setResponsavel(func.getEplsnm());
				query = manager.createQuery("from GePecasAprovacao where idAprovacaoOs.id = "+geFormularioAprovacaoOs.getId());
				List<GePecasAprovacao> listPecas = query.getResultList();
				for (GePecasAprovacao gePecasAprovacao : listPecas) {
					PecasAprovacaoOSBean osBean = new PecasAprovacaoOSBean();
					osBean.setId(gePecasAprovacao.getId().toString());
					osBean.setPartNo(gePecasAprovacao.getPartNumber());
					osBean.setPartName(gePecasAprovacao.getPartName());
					osBean.setUniteRate(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteRate().doubleValue()));
					osBean.setDnPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getDNPrice().doubleValue()));
					osBean.setUniteCl(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteCL().doubleValue()));
					osBean.setClPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getCLPrice().doubleValue()));
					bean.getListaPecasAprovacao().add(osBean);
				}
				result.add(bean);
			}
			
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	
	public List<GeFormularioAprovacaoOsBean> findFormularioAprovarOsCustomizacao(String campo){
		EntityManager manager = null;
		List<GeFormularioAprovacaoOsBean> result = new ArrayList<GeFormularioAprovacaoOsBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = null;
			if(campo != null && !campo.equals("")){
				query = manager.createQuery("from GeFormularioAprovacaoOs where (codigoCliente like '"+campo+"%' or cliente like '"+campo+"%' or serie like '"+campo+"%' or modelo like '"+campo+"%') and (tipoSolicitacao = 'C' or tipoSolicitacao = 'CC') and status is null and (codigoCliente like '%246' or codigoCliente like '%240')");
				//query.setParameter("campo", campo);
			}else{
				query = manager.createQuery("from GeFormularioAprovacaoOs where (tipoSolicitacao = 'C' or tipoSolicitacao = 'CC') and status is null and (codigoCliente like '%246' or codigoCliente like '%240')");
			}
			
			List<GeFormularioAprovacaoOs> formList = query.getResultList();
			
			for (GeFormularioAprovacaoOs geFormularioAprovacaoOs : formList) {
				GeFormularioAprovacaoOsBean bean = new GeFormularioAprovacaoOsBean();
				bean.toBean(geFormularioAprovacaoOs);
				TwFuncionario func = manager.find(TwFuncionario.class, geFormularioAprovacaoOs.getIdFuncionarioResponsavel());
				bean.setResponsavel(func.getEplsnm());
				query = manager.createQuery("from GePecasAprovacao where idAprovacaoOs.id = "+geFormularioAprovacaoOs.getId());
				List<GePecasAprovacao> listPecas = query.getResultList();
				for (GePecasAprovacao gePecasAprovacao : listPecas) {
					PecasAprovacaoOSBean osBean = new PecasAprovacaoOSBean();
					osBean.setId(gePecasAprovacao.getId().toString());
					osBean.setPartNo(gePecasAprovacao.getPartNumber());
					osBean.setPartName(gePecasAprovacao.getPartName());
					osBean.setUniteRate(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteRate().doubleValue()));
					osBean.setDnPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getDNPrice().doubleValue()));
					osBean.setUniteCl(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteCL().doubleValue()));
					osBean.setClPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getCLPrice().doubleValue()));
					bean.getListaPecasAprovacao().add(osBean);
				}
				result.add(bean);
			}
			
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	
	
	public List<GeFormularioAprovacaoOsBean> findFormularioAprovarOsGerador(String campo){
		EntityManager manager = null;
		List<GeFormularioAprovacaoOsBean> result = new ArrayList<GeFormularioAprovacaoOsBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = null;
			if(campo != null && !campo.equals("")){
				query = manager.createQuery("from GeFormularioAprovacaoOs where (codigoCliente like '"+campo+"%' or cliente like '"+campo+"%' or serie like '"+campo+"%' or modelo like '"+campo+"%') and tipoSolicitacao = 'AG' and status is null");
				//query.setParameter("campo", campo);
			}else{
				query = manager.createQuery("from GeFormularioAprovacaoOs where tipoSolicitacao = 'AG' and status is null");
			}
			
			List<GeFormularioAprovacaoOs> formList = query.getResultList();
			
			for (GeFormularioAprovacaoOs geFormularioAprovacaoOs : formList) {
				GeFormularioAprovacaoOsBean bean = new GeFormularioAprovacaoOsBean();
				bean.toBean(geFormularioAprovacaoOs);
				TwFuncionario func = manager.find(TwFuncionario.class, geFormularioAprovacaoOs.getIdFuncionarioResponsavel());
				bean.setResponsavel(func.getEplsnm());
				query = manager.createQuery("from GePecasAprovacao where idAprovacaoOs.id = "+geFormularioAprovacaoOs.getId());
				List<GePecasAprovacao> listPecas = query.getResultList();
				for (GePecasAprovacao gePecasAprovacao : listPecas) {
					PecasAprovacaoOSBean osBean = new PecasAprovacaoOSBean();
					osBean.setId(gePecasAprovacao.getId().toString());
					osBean.setPartNo(gePecasAprovacao.getPartNumber());
					osBean.setPartName(gePecasAprovacao.getPartName());
					osBean.setUniteRate(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteRate().doubleValue()));
					osBean.setDnPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getDNPrice().doubleValue()));
					osBean.setUniteCl(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteCL().doubleValue()));
					osBean.setClPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getCLPrice().doubleValue()));
					bean.getListaPecasAprovacao().add(osBean);
				}
				result.add(bean);
			}
			
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	
	public List<GeFormularioAprovacaoOsBean> findFormularioAprovarOsRental(String campo){
		EntityManager manager = null;
		List<GeFormularioAprovacaoOsBean> result = new ArrayList<GeFormularioAprovacaoOsBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = null;
			if(campo != null && !campo.equals("")){
				query = manager.createQuery("from GeFormularioAprovacaoOs where (codigoCliente like '"+campo+"%' or cliente like '"+campo+"%' or serie like '"+campo+"%' or modelo like '"+campo+"%') and tipoSolicitacao = 'R' and status is null" +
				" and codigoCliente not in (select codCliente from PmpClientePl where lbcc in ('2V'))");
				//query.setParameter("campo", campo);
			}else{
				query = manager.createQuery("from GeFormularioAprovacaoOs where tipoSolicitacao = 'R' and status is null" +
				" and codigoCliente not in (select codCliente from PmpClientePl where lbcc in ('2V'))");
			}
			
			List<GeFormularioAprovacaoOs> formList = query.getResultList();
			
			for (GeFormularioAprovacaoOs geFormularioAprovacaoOs : formList) {
				GeFormularioAprovacaoOsBean bean = new GeFormularioAprovacaoOsBean();
				bean.toBean(geFormularioAprovacaoOs);
				TwFuncionario func = manager.find(TwFuncionario.class, geFormularioAprovacaoOs.getIdFuncionarioResponsavel());
				bean.setResponsavel(func.getEplsnm());
				query = manager.createQuery("from GePecasAprovacao where idAprovacaoOs.id = "+geFormularioAprovacaoOs.getId());
				List<GePecasAprovacao> listPecas = query.getResultList();
				for (GePecasAprovacao gePecasAprovacao : listPecas) {
					PecasAprovacaoOSBean osBean = new PecasAprovacaoOSBean();
					osBean.setId(gePecasAprovacao.getId().toString());
					osBean.setPartNo(gePecasAprovacao.getPartNumber());
					osBean.setPartName(gePecasAprovacao.getPartName());
					osBean.setUniteRate(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteRate().doubleValue()));
					osBean.setDnPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getDNPrice().doubleValue()));
					osBean.setUniteCl(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteCL().doubleValue()));
					osBean.setClPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getCLPrice().doubleValue()));
					bean.getListaPecasAprovacao().add(osBean);
				}
				result.add(bean);
			}
			
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	
	
	public List<GeFormularioAprovacaoOsBean> findFormularioAprovarOsGarantia(String campo){
		EntityManager manager = null;
		List<GeFormularioAprovacaoOsBean> result = new ArrayList<GeFormularioAprovacaoOsBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = null;
			if(this.bean.getSiglaPerfil().equals("APGARU")){
				if(campo != null && !campo.equals("")){
					query = manager.createQuery("from GeFormularioAprovacaoOs where (codigoCliente like '"+campo+"%' or cliente like '"+campo+"%' or serie like '"+campo+"%' or modelo like '"+campo+"%') and (tipoSolicitacao = 'G' or tipoSolicitacao = 'GG' or tipoSolicitacao = 'GGG' or tipoSolicitacao = 'AGU') and status is null" +
					" and codigoCliente in (select codCliente from PmpClientePl where lbcc in ('3W','2U','2R','2V'))");
					//query.setParameter("campo", campo);
				}else{
					query = manager.createQuery("from GeFormularioAprovacaoOs where (tipoSolicitacao = 'G' or tipoSolicitacao = 'GG' or tipoSolicitacao = 'GGG' or tipoSolicitacao = 'AGU') and status is null" +
					" and codigoCliente in (select codCliente from PmpClientePl where lbcc in ('3W','2U','2R','2V'))");
				}
			} else{ 			
				if(campo != null && !campo.equals("")){
					query = manager.createQuery("from GeFormularioAprovacaoOs where (codigoCliente like '"+campo+"%' or cliente like '"+campo+"%' or serie like '"+campo+"%' or modelo like '"+campo+"%') and (tipoSolicitacao = 'G' or tipoSolicitacao = 'GG' or tipoSolicitacao = 'GGG') and status is null" +
					" and codigoCliente not in (select codCliente from PmpClientePl where lbcc in ('3W,'2U'))");
					//query.setParameter("campo", campo);
				}else{
					query = manager.createQuery("from GeFormularioAprovacaoOs where (tipoSolicitacao = 'G' or tipoSolicitacao = 'GG' or tipoSolicitacao = 'GGG') and status is null" +
					" and codigoCliente not in (select codCliente from PmpClientePl where lbcc in ('3W','2U'))");
				}
			}
				
			List<GeFormularioAprovacaoOs> formList = query.getResultList();
			
			for (GeFormularioAprovacaoOs geFormularioAprovacaoOs : formList) {
				GeFormularioAprovacaoOsBean bean = new GeFormularioAprovacaoOsBean();
				bean.toBean(geFormularioAprovacaoOs);
				TwFuncionario func = manager.find(TwFuncionario.class, geFormularioAprovacaoOs.getIdFuncionarioResponsavel());
				bean.setResponsavel(func.getEplsnm());
				query = manager.createQuery("from GePecasAprovacao where idAprovacaoOs.id = "+geFormularioAprovacaoOs.getId());
				List<GePecasAprovacao> listPecas = query.getResultList();
				for (GePecasAprovacao gePecasAprovacao : listPecas) {
					PecasAprovacaoOSBean osBean = new PecasAprovacaoOSBean();
					osBean.setId(gePecasAprovacao.getId().toString());
					osBean.setPartNo(gePecasAprovacao.getPartNumber());
					osBean.setPartName(gePecasAprovacao.getPartName());
					osBean.setUniteRate(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteRate().doubleValue()));
					osBean.setDnPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getDNPrice().doubleValue()));
					osBean.setUniteCl(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteCL().doubleValue()));
					osBean.setClPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getCLPrice().doubleValue()));
					bean.getListaPecasAprovacao().add(osBean);
				}
				result.add(bean);
			}
			
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	
	public GeFormularioAprovacaoOsBean findFormularioAprovarOsById(Long id){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeFormularioAprovacaoOs where id =:id");
			query.setParameter("id", id);
			
			GeFormularioAprovacaoOs geFormularioAprovacaoOs = (GeFormularioAprovacaoOs)query.getSingleResult();
			
				GeFormularioAprovacaoOsBean bean = new GeFormularioAprovacaoOsBean();
				bean.toBean(geFormularioAprovacaoOs);
				query = manager.createQuery("from GePecasAprovacao where idAprovacaoOs.id = "+geFormularioAprovacaoOs.getId());
				List<GePecasAprovacao> listPecas = query.getResultList();
				for (GePecasAprovacao gePecasAprovacao : listPecas) {
					PecasAprovacaoOSBean osBean = new PecasAprovacaoOSBean();
					osBean.setId(gePecasAprovacao.getId().toString());
					osBean.setQtd(gePecasAprovacao.getQtd().toString());
					osBean.setPartNo(gePecasAprovacao.getPartNumber());
					osBean.setPartName(gePecasAprovacao.getPartName());
					osBean.setUniteRate(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteRate().doubleValue()));
					osBean.setDnPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getDNPrice().doubleValue()));
					osBean.setUniteCl(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteCL().doubleValue()));
					osBean.setClPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getCLPrice().doubleValue()));
					bean.getListaPecasAprovacao().add(osBean);
				}
				return bean;
			
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}
	
	public Boolean findCodigoLiberacao(Long codigoLiberacao, String serie, String codcliente){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select * from GE_FORMULARIO_APROVACAO_OS where ID = "+codigoLiberacao+" and IS_USADO is null and SERIE = '"+serie+"' and CODIGO_CLIENTE = '"+codcliente+"' and status = 'A' and TIPO_SISTEMA = 'OFI'");
			
			if(query.getResultList().size() > 0){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
	public Boolean verificaSolicitacaoCriacaoOS(String serie, String codcliente){
		EntityManager manager = null;
		try {
			String filialDestino = codcliente.substring(2,4);
			
			manager = JpaUtil.getInstance();
			TwFilial filial = manager.find(TwFilial.class, Long.valueOf(filialDestino));
			Query query = manager.createNativeQuery("select CODIGO_CLIENTE from GE_FORMULARIO_APROVACAO_OS where SERIE = '"+serie+"' and NOME_FILIAL = '"+filial.getStnm()+"' and IS_USADO is null and codigo_cliente <> '' and TIPO_SISTEMA = 'OFI'");
			
			if(query.getResultList().size() > 0){
				List<String> lista = query.getResultList();
				for (int i = 0; i < lista.size(); i++) {
					String codigoCliente = (String)lista.get(i);
					String codigoClienteAux = codigoCliente.substring(0,2)+this.bean.getFilial()+codigoCliente.substring(4,codigoCliente.length());
					if(codigoClienteAux.equals(codcliente)){
						return true;
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	public static void main(String[] args) {
		String filialDestino = "0900472".substring(2,4);
		System.out.println(filialDestino);
	}
	
	public String verificaSolicitacaoCriacaoOSCliente(String serie, String codcliente, Long idCheckIn, String tipoCliente){
		String [] codInter = {"09XX023","09XX463","09XX243","09XX247","09XX288","09XX249","09XX267","09XX268","09XX025","09XX295","09XX468","09XX469",
				"09XX344","09XX472","09XX244","09XX464","09XX024","09XX292","09XX304","09XX305","09XX629","09XX297","09XX300","09XX235","09XX242","09XX294","09XX241","09XX234",
				"09XX209","09XX254","09XX102","09XX280","09XX486","09XX410","09XX021","09XX245","09XX246","09XX291","09XX290","09XX411","09XX030","09XX020","09XX240","09XX250","09XX285","09XX401","09XX460","09XX461",
				"09XX462","09XX465","09XX466","09XX467","09XX470","09XX488","09XX490","09XX930","09XX298","09XX103","09XX104","09XX293","09XX028","09XX026"};
		EntityManager manager = null;
		try {
			//String filialDestino = codcliente.substring(2,4);
			String codCliInter = codcliente.substring(4,codcliente.length());
			
			manager = JpaUtil.getInstance();
			//GeCheckIn checkIn = manager.find(GeCheckIn.class, idCheckIn);
			TwFilial filial = manager.find(TwFilial.class, Long.valueOf(this.bean.getFilial()));
			Query query = manager.createNativeQuery("select CODIGO_CLIENTE, TIPO_SOLICITACAO from GE_FORMULARIO_APROVACAO_OS where (SERIE = '"+serie.trim()+"' OR SERIE = '0"+serie.trim()+"') and NOME_FILIAL = '"+filial.getStnm()+"' and IS_USADO is null and codigo_cliente <> '' and TIPO_SISTEMA = 'OFI'");
			
			if(query.getResultList().size() > 0){
				List<Object[]> lista = query.getResultList();
				for (int i = 0; i < lista.size(); i++) {
					Object pair[] = (Object[])lista.get(i);
					String codigoCliente = (String)pair[0];
					String tipoSolicitacao = (String)pair[1];
					if(tipoSolicitacao.equals("E") && tipoCliente.equals("EXT")){
						return codigoCliente;
					}
					if(!tipoSolicitacao.equals("E") && tipoCliente.equals("INT")){
						for (String codcli : codInter) {
							if(codCliInter.equals(codcli.substring(4,codcli.length()))){
								String codigoClienteAux = codcli.substring(0,2)+this.bean.getFilial()+codcli.substring(4,codcli.length());
								return codigoClienteAux;
							}
						}
					}
//					String codigoClienteAux = codigoCliente.substring(0,2)+this.bean.getFilial()+codigoCliente.substring(4,codigoCliente.length());
//					if(codigoClienteAux.equals(codcliente)){
//						return codigoCliente;
//					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}

	
	public List<GeFormularioAprovacaoOsBean> findFormularioAprovarRecepcaoOs(String serie, String codigoCliente){
		EntityManager manager = null;
		List<GeFormularioAprovacaoOsBean> result = new ArrayList<GeFormularioAprovacaoOsBean>();
		try {
			manager = JpaUtil.getInstance();
			TwFilial filial = manager.find(TwFilial.class, Long.valueOf(this.bean.getFilial()));
			//Query query = manager.createQuery("from GeFormularioAprovacaoOs where codigoCliente = '"+codigoCliente+"' and (serie = '"+serie+"' or serie = '0"+serie+"') and IS_USADO is null");
			Query query = manager.createQuery("from GeFormularioAprovacaoOs where (serie = '"+serie.trim()+"' OR serie = '0"+serie.trim()+"') and nomeFilial = '"+filial.getStnm()+"' and isUsado is null and codigoCliente <> '' and status = 'A' and TIPO_SISTEMA = 'OFI'");
			List<GeFormularioAprovacaoOs> formList = query.getResultList();
			
			for (GeFormularioAprovacaoOs geFormularioAprovacaoOs : formList) {
				GeFormularioAprovacaoOsBean bean = new GeFormularioAprovacaoOsBean();
				bean.toBean(geFormularioAprovacaoOs);
				query = manager.createQuery("from GePecasAprovacao where idAprovacaoOs.id = "+geFormularioAprovacaoOs.getId());
				List<GePecasAprovacao> listPecas = query.getResultList();
				for (GePecasAprovacao gePecasAprovacao : listPecas) {
					PecasAprovacaoOSBean osBean = new PecasAprovacaoOSBean();
					osBean.setId(gePecasAprovacao.getId().toString());
					osBean.setPartNo(gePecasAprovacao.getPartNumber());
					osBean.setPartName(gePecasAprovacao.getPartName());
					osBean.setUniteRate(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteRate().doubleValue()));
					osBean.setDnPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getDNPrice().doubleValue()));
					osBean.setUniteCl(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getUniteCL().doubleValue()));
					osBean.setClPrice(ValorMonetarioHelper.formata("###,##0.00", gePecasAprovacao.getCLPrice().doubleValue()));
					bean.getListaPecasAprovacao().add(osBean);
				}
				result.add(bean);
			}
			
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	public boolean removerFormularioAprovarOsById(Long id){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			GeFormularioAprovacaoOs aprovacaoOs = manager.find(GeFormularioAprovacaoOs.class, id);
			
			manager.getTransaction().begin();
			manager.remove(aprovacaoOs);
			manager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
	
}


class SelfishRunner extends Thread {


	  public void run() {
		  synchronized (this) {
			new ChekinBusiness(null).teste();
		}
	  }
	}