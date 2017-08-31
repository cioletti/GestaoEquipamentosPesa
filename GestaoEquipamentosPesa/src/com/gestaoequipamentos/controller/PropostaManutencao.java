package com.gestaoequipamentos.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import com.gestaoequipamentos.bean.ChekinBean;
import com.gestaoequipamentos.bean.ClienteBean;
import com.gestaoequipamentos.bean.FotosBean;
import com.gestaoequipamentos.bean.RelatorioFotosBean;
import com.gestaoequipamentos.bean.SegmentoBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.business.ChekinBusiness;
import com.gestaoequipamentos.business.ClienteBusiness;
import com.gestaoequipamentos.entity.GeCheckIn;
import com.gestaoequipamentos.entity.GeDocSegOper;
import com.gestaoequipamentos.entity.GeSegmento;
import com.gestaoequipamentos.entity.TwFilial;
import com.gestaoequipamentos.util.ConectionDbs;
import com.gestaoequipamentos.util.Connection;
import com.gestaoequipamentos.util.DataUtil;
import com.gestaoequipamentos.util.IConstantAccess;
import com.gestaoequipamentos.util.JpaUtil;

import flex.messaging.io.ArrayList;

/**
 * Servlet implementation class PropostaManutencao
 */
public class PropostaManutencao extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PropostaManutencao() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idChechIn  = request.getParameter("idChechIn");
		String codCliente  = request.getParameter("codCliente");
		String imprimirComPecas  = request.getParameter("imprimirComPecas");
		UsuarioBean usuarioBean = (UsuarioBean) request.getSession().getAttribute("usuario");
		ClienteBusiness business = new ClienteBusiness();
		ClienteBean bean = business.findDataCliente(codCliente);
		String orcamentista = request.getParameter("orcamentista");
		//JasperReport jasperReport = null; 
		JasperReport jasperReport = null; 
		//byte[] pdfInspecao = null;
		

		//Obtem o caminho do .jasper  
		ServletContext servletContext = super.getServletContext();  
		String caminhoJasper = servletContext.getRealPath("WEB-INF/proposta/proposta.jasper"); 
		String pathSubreport = servletContext.getRealPath("WEB-INF/proposta/")+"/"; 

		//Carrega o arquivo com o jasperReport  
		EntityManager manager = null;
		java.sql.Connection con = null;
		java.sql.Connection conDbs = null;
		ResultSet rs = null;
		Statement stmt = null;
		try{
			manager = JpaUtil.getInstance();
			GeCheckIn checkIn = manager.find(GeCheckIn.class, Long.valueOf(idChechIn));
			
			if((checkIn.getTipoCliente() != null && checkIn.getTipoCliente().equals("INT")) || checkIn.getSiglaIndicadorGarantia() != null ){
				caminhoJasper = servletContext.getRealPath("WEB-INF/proposta_inter/proposta.jasper"); 
				pathSubreport = servletContext.getRealPath("WEB-INF/proposta_inter/")+"/"; 
			}
			
			try {  
				jasperReport = (JasperReport) JRLoader.loadObject( caminhoJasper );  
			} catch (Exception jre) {  
				jre.printStackTrace();  
			}
			
			conDbs = ConectionDbs.getConnecton();
			stmt = conDbs.createStatement();
			rs = stmt.executeQuery("select CUNO, CUNM from "+IConstantAccess.LIB_DBS+".WOPHDRS0 where wono = '"+checkIn.getNumeroOs()+"'");
			if(rs.next()){
				manager.getTransaction().begin();
				checkIn.setCodCliente(rs.getString("CUNO").trim());
				checkIn.getIdOsPalm().setCliente(rs.getString("CUNM").trim());
				manager.getTransaction().commit();
				
				//con = ConectionDbs.getConnecton();
				String SQL = "select f.IDNO1 from "+IConstantAccess.LIB_DBS+".empeqpd0 f where f.EQMFS2 = '"+checkIn.getIdOsPalm().getSerie()+"'";
				rs = stmt.executeQuery(SQL);
				if(rs.next()){
					String IDNO1 = rs.getString("IDNO1");
					if(IDNO1 != null){
						manager.getTransaction().begin();
						checkIn.setIdEquipamento(IDNO1.trim());
						manager.getTransaction().commit();
					}
				}
				SQL = "select LBCC from "+IConstantAccess.LIB_DBS+".CIPNAME0 where cuno = '"+checkIn.getCodCliente()+"'";
				rs = stmt.executeQuery(SQL);
				if(rs.next()){
					String LBCC = rs.getString("LBCC");
					if(LBCC != null){
						manager.getTransaction().begin();
						if(LBCC.equals("10")){
							checkIn.setTipoCliente("EXT");
						}else if(DataUtil.verificarClienteGarantia(checkIn.getCodCliente())){
							checkIn.setTipoCliente("INT");
						}
						manager.getTransaction().commit();
					}
				}
				
			}

			
			stmt = sendNotesObsOsDbs(usuarioBean, conDbs, stmt, checkIn);
			
			
			//recupera o último número do documento para recuperar as peças com os preços
			Query query = manager.createNativeQuery("select oper.num_doc, oper.id from ge_segmento seg, ge_doc_seg_oper oper"+
					"	where seg.id_checkin =:id_checkin"+
					"	and seg.id = oper.id_segmento");
			query.setParameter("id_checkin", Long.valueOf(idChechIn));
			List<Object[]> pecasList = query.getResultList();
			for (Object[] objects : pecasList) {
				//manager.getTransaction().begin();
				GeDocSegOper docSegOper = manager.find(GeDocSegOper.class, ((BigDecimal)objects[1]).longValue());
				synchronized (docSegOper) {
					//				GeDocSegOper segOper = new GeDocSegOper();
					//				segOper.setNumDoc(docSegOper.getNumDoc());
					//				segOper.setDataCriacao(new Date());
					//				segOper.setIdOperacao(docSegOper.getIdOperacao());
					//				segOper.setIdSegmento(docSegOper.getIdSegmento());
					//				manager.persist(segOper);
					//aqui tem o numero do documento e o numero do id da ge_doc_seg_oper
					new ChekinBusiness(usuarioBean).sendContratoDbs(docSegOper.getNumDoc(),
							docSegOper, checkIn.getTipoCliente(),
							checkIn.getPossuiSubTributaria());
					//manager.getTransaction().commit();
				}
				
			}			
			Map parametros = new HashMap();  
			if(checkIn.getPossuiSubTributaria() == null || checkIn.getPossuiSubTributaria().equals("N")){
				query = manager.createNativeQuery("select sum(CASE WHEN p.valor_total is null then 0 else p.valor_total end) from ge_pecas p, (select oper.num_doc, max(oper.id) id_oper from ge_doc_seg_oper oper, ge_segmento seg"+
						" where seg.id_checkin ="+Long.valueOf(idChechIn)+
						" and seg.id = oper.id_segmento"+
						" group by oper.num_doc)  aux"+
				" where p.id_doc_seg_oper = aux.id_oper"); 
				parametros.put("IS_SUB_TRIBUTARIA", "N");
			}else if(checkIn.getPossuiSubTributaria().equals("S")){
				query = manager.createNativeQuery("select sum(CASE WHEN p.TOTALTRIBUTOS is null then 0 else p.TOTALTRIBUTOS end) from ge_pecas p, (select oper.num_doc, max(oper.id) id_oper from ge_doc_seg_oper oper, ge_segmento seg"+
						" where seg.id_checkin ="+Long.valueOf(idChechIn)+
						" and seg.id = oper.id_segmento"+
						" group by oper.num_doc)  aux"+
				" where p.id_doc_seg_oper = aux.id_oper"); 
				parametros.put("IS_SUB_TRIBUTARIA", "S");
			}
			
			TwFilial filial = manager.find(TwFilial.class, Long.valueOf(usuarioBean.getFilial())); // Busca a aprovação da proposta do usuário logado.	
			parametros.put("ORDENACAO_PECAS", "N");
			if( checkIn.getCodCliente() != null && (Integer.valueOf(checkIn.getCodCliente()).equals(47414) || Integer.valueOf(checkIn.getCodCliente()).equals(833924)) ){
				parametros.put("ORDENACAO_PECAS", "S");
			}
			parametros.put("APROVACAO_PROPOSTA_SERVICO", filial.getAprovacaoPropostaServico());
			parametros.put("SUBREPORT_DIR", pathSubreport);
			parametros.put("OBS", checkIn.getObservacao());
			BigDecimal totalPecas = BigDecimal.ZERO;
			if(query.getResultList().size() > 0){
				totalPecas = (BigDecimal)query.getSingleResult();
			}
			parametros.put("ID_CHECHIN", BigDecimal.valueOf(Double.valueOf(idChechIn))); 
			parametros.put("TOTAL_PECAS", totalPecas); 
			parametros.put("ENDERECO", bean.getEND());
			if(Integer.valueOf(checkIn.getIdOsPalm().getFilial()).intValue() == 0){
				parametros.put("ENDERECO_MARCOSA", "PESA PARANÁ EQUIPAMENTOS - BR 116, N.° 11.807, KM 100 - HAUER - PARANÁ.<br>"
						+"CEP: 81690-200. Fone: 55 (41) 2103-2211");
			}else if(Integer.valueOf(checkIn.getIdOsPalm().getFilial()).intValue() == 10){
				parametros.put("ENDERECO_MARCOSA","AVENIDA TIRADENTES, Nº 2900 - JARDIM JOCKEY CLUB - PARANÁ.<br>"
						+"CEP: 86072-360. Fone: 55 (43) 2101-6000");	
			}else if(Integer.valueOf(checkIn.getIdOsPalm().getFilial()).intValue() == 11){
				parametros.put("ENDERECO_MARCOSA","RODOVIA PR 317, 7073, LOTE 01/02 - PQ. INDUSTRIAL MARIO BULHÕES - PARANÁ.<br>"
						+"CEP: 87065-005. Fone: 55 (44) 3366-3000");
			}else if(Integer.valueOf(checkIn.getIdOsPalm().getFilial()).intValue() == 15){
				parametros.put("ENDERECO_MARCOSA","BR 277 KM 590 S/N - PARANÁ.<br>"
						+"CEP: 85818-560. Fone: 55 (45) 2101-2500");
			}else if(Integer.valueOf(checkIn.getIdOsPalm().getFilial()).intValue() == 18){
				parametros.put("ENDERECO_MARCOSA","HELENA GRODZKI, 340 - UMBARA - PARANÁ.<br>"
						+"CEP: 81930-085. Fone: 55 (41) 3535-6300");
			}else if(Integer.valueOf(checkIn.getIdOsPalm().getFilial()).intValue() == 21){
				parametros.put("ENDERECO_MARCOSA","BR 101 KM 33 - DISTRITO INDUSTRIAL - SANTA CATARINA.<br>"
						+"CEP: 89219-505. Fone: 55 (47) 2101-0777");
			}else if(Integer.valueOf(checkIn.getIdOsPalm().getFilial()).intValue() == 22){
				parametros.put("ENDERECO_MARCOSA","R. PLINIO ARLINDO DE NEVES, 2133 D - ACESSO BR 282 - BELVEDERE - SANTA CATARINA.<br>"
						+"CEP: 89810-300. Fone: 55 (49) 3313-1400");
			}else if(Integer.valueOf(checkIn.getIdOsPalm().getFilial()).intValue() == 25){
				parametros.put("ENDERECO_MARCOSA","PAULINO PEDRO HERMES, 2909, MARGINAL BR 101 - NSA SRA DO ROSÁRIO - SANTA CATARINA.<br>"
						+"CEP: 88110-693. Fone: 55 (48) 2107-8755");
			}else if(Integer.valueOf(checkIn.getIdOsPalm().getFilial()).intValue() == 30){
				parametros.put("ENDERECO_MARCOSA","AV. INDUSTRIAS, 325 - BAIRRO ANCHIETA - RIO GRANDE DO SUL.<br>"
						+"CEP: 90200-290. Fone: 55 (51) 2125-5355");
			}else if(Integer.valueOf(checkIn.getIdOsPalm().getFilial()).intValue() == 32){
				parametros.put("ENDERECO_MARCOSA","RSC 453, NR 16685 - DESVIO RIZZO - RIO GRANDE DO SUL.<br>"
						+"CEP: 95112-360. Fone: 55 (54) 3535-5500");
			}else if(Integer.valueOf(checkIn.getIdOsPalm().getFilial()).intValue() == 33){
				parametros.put("ENDERECO_MARCOSA","ROD RS 153 KM 01 Nº 965 - JERÔNIMO COELHO - RIO GRANDE DO SUL.<br>"
						+"CEP: 99034-600. Fone: 55 (54) 3327-4611");
			}
			
//			if(checkIn.getIdTipoFrete() != null){
//				parametros.put("PORC_FRETE", checkIn.getIdTipoFrete().getTaxa());
//				parametros.put("FRETE_MIN", checkIn.getIdTipoFrete().getFreteMinimo());
//			}else{
//				parametros.put("PORC_FRETE", BigDecimal.ZERO);
//				parametros.put("FRETE_MIN", BigDecimal.ZERO);
//				
//			}
//			if(checkIn.getIdTipoFrete() != null){
//				query = manager.createNativeQuery("select sum(((p.qtd_nao_atendido * p.valor) * "+ checkIn.getIdTipoFrete().getTaxa()+") / 100) from ge_pecas p, (select oper.num_doc, max(oper.id) id_oper from ge_doc_seg_oper oper, ge_segmento seg"+
//						" where seg.id_checkin ="+Long.valueOf(idChechIn)+
//						" and seg.id = oper.id_segmento"+
//						" group by oper.num_doc)  aux"+
//						" where p.id_doc_seg_oper = aux.id_oper"); 
//
//
//				BigDecimal totalFrete = (BigDecimal)query.getSingleResult();
//				if(totalFrete != null && checkIn.getIdTipoFrete() != null && totalFrete.longValue() < checkIn.getIdTipoFrete().getFreteMinimo().longValue() && totalFrete.longValue() > 0){
//					parametros.put("TOTAL_FRETE", checkIn.getIdTipoFrete().getFreteMinimo());
//				}else{
//					parametros.put("TOTAL_FRETE", (totalFrete == null || totalFrete.longValue() == 0)?BigDecimal.ZERO:totalFrete);
//				}
//			}else{
//				parametros.put("TOTAL_FRETE", BigDecimal.ZERO);
//			}
			
			//TwFuncionario funcionario = manager.find(TwFuncionario.class, checkIn.getIdFuncionarioDataOrcamento());
			
			parametros.put("IMPRIMIR_PECAS", imprimirComPecas); 
			parametros.put("NOME_CLIENTE", checkIn.getIdOsPalm().getCliente()); 
			parametros.put("ID_EQUIPAMENTO", checkIn.getIdEquipamento()); 
			parametros.put("USUARIO", orcamentista); 
			if(checkIn.getIdOsPalm().getSerie().length() >= 4){
				parametros.put("PREFIXO", checkIn.getIdOsPalm().getSerie().subSequence(0, 4));
			}else{
				parametros.put("PREFIXO", checkIn.getIdOsPalm().getSerie());
			}
			parametros.put("MODELO", checkIn.getIdOsPalm().getEquipamento()); 
			
			if(checkIn.getTipoCliente() != null && checkIn.getTipoCliente().equals("INT")){
				parametros.put("PORC_SERV_TERC", "1.00"); 
			}else{
				parametros.put("PORC_SERV_TERC", "1.50"); 
			}
			if(checkIn.getValidadeProposta() != null){
				parametros.put("EXEC_SERVICO", String.valueOf(checkIn.getValidadeProposta())+" dia(s).");				
			}
			String SQL = "";
			
			if(checkIn.getTipoCliente() != null && !checkIn.getTipoCliente().equals("INT")){
				SQL = "select distinct ((pre.valor_de_venda * ch.fator_cliente)* "+
						"	(select c.fator from ge_complexidade c "+
						"	where c.id =  "+
						"	(select p.id_complexidade from ge_preco p where p.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+checkIn.getIdOsPalm().getIdFamilia().getId()+")"+ 
						"	 and p.id_prefixo = (select pre.id from ge_prefixo pre where pre.descricao = substring(palm.serie,0,5) and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+checkIn.getIdOsPalm().getIdFamilia().getId()+") )"+
						"	 and p.comp_code = seg.com_code"+
						"	 and p.job_code = seg.job_code )"+
						"	) * CASE WHEN ch.fator_urgencia = 'S'"+
						" THEN (select fator_urgencia from ge_fator) ELSE 1 END) * seg.qtd_comp valor_total_hora, seg.horas_prevista,"+

						"	(select c.fator from ge_complexidade c"+ 
						"	where c.id =  "+
						"	(select p.id_complexidade from ge_preco p where p.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+checkIn.getIdOsPalm().getIdFamilia().getId()+")"+ 
						"	 and p.id_prefixo = (select pre.id from ge_prefixo pre where pre.descricao = substring(palm.serie,0,5) and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+checkIn.getIdOsPalm().getIdFamilia().getId()+") )"+
						"	 and p.comp_code = seg.com_code"+
						"	 and p.job_code = seg.job_code )"+
						"	) complexidade,ch.fator_urgencia , ch.fator_cliente, pre.valor_de_venda, ch.valor_servicos_terceiros, seg.numero_segmento"+

						"	from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre"+ 
						"	where ch.id = "+idChechIn+
						"	and ch.id = seg.id_checkin"+
						"	and ch.id_os_palm = palm.idos_palm"+
						"	and pre.descricao = substring(palm.serie,0,5)" +
						"   and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = palm.id_familia)  ";
			}else{
				SQL = " select ((select f.valor_inter from ge_fator f) * seg.qtd_comp), seg.horas_prevista"+
						"	from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre"+ 
						"	where ch.id = "+idChechIn+
						"	and ch.id = seg.id_checkin"+
						"	and ch.id_os_palm = palm.idos_palm"+
						"	and pre.descricao = substring(palm.serie,0,5)" +
						"   and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = palm.id_familia)  ";
			}
			query = manager.createNativeQuery(SQL);
			List<Object[]> list = query.getResultList();
			BigDecimal valorMaoDeObra = BigDecimal.ZERO;
			BigDecimal total = BigDecimal.ZERO;
			List<RelatorioFotosBean> fileListApagar = new java.util.ArrayList<RelatorioFotosBean>();
			for (Object[] objects : list) {
				valorMaoDeObra = (BigDecimal)objects[0];
				if(valorMaoDeObra == null){
					valorMaoDeObra = BigDecimal.ZERO;
				}
				String horas = (String)objects[1];
				//horas = "5.00"; //<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//				String [] aux = horas.split(":");
//				Double h = Double.valueOf(aux[0]);
//				Double m = Double.valueOf(aux[1]) / 60;
//				h = h+m;
				total = total.add(valorMaoDeObra.multiply(BigDecimal.valueOf(Double.valueOf(horas))));
				//servico_terceiros = (BigDecimal)objects[6];
			}
			BigDecimal servico_terceiros = BigDecimal.ZERO;
			SQL = "select sum(CASE WHEN st.valor is null then 0 else st.valor end)  from ge_segmento seg, ge_segmento_serv_terceiros st"+
				  "	where seg.id = st.id_segmento"+
				  "	and seg.id_checkin = "+Long.valueOf(idChechIn); 
			query = manager.createNativeQuery(SQL);
			BigDecimal st = (BigDecimal)query.getSingleResult();
			if(st != null  && st.longValue() > 0){
				servico_terceiros = st;
			}else{
				servico_terceiros = BigDecimal.ZERO;
			}
			
			parametros.put("TOTAL_MAO_DE_OBRA", total);
			if(totalPecas != null && servico_terceiros != null){
				if(checkIn.getTipoCliente() != null && checkIn.getTipoCliente().equals("INT")){
					parametros.put("TOTAL_ORCAMENTO", totalPecas.add(total).add(servico_terceiros));
				}else{
					parametros.put("TOTAL_ORCAMENTO", totalPecas.add(total).add(servico_terceiros.multiply(BigDecimal.valueOf(1.50))));
				}
			}else if(servico_terceiros != null){
				if(checkIn.getTipoCliente() != null && checkIn.getTipoCliente().equals("INT")){
					parametros.put("TOTAL_ORCAMENTO", total.add(servico_terceiros));
				}else{
					parametros.put("TOTAL_ORCAMENTO", total.add(servico_terceiros.multiply(BigDecimal.valueOf(1.50))));
				}

			}else{
				if(checkIn.getTipoCliente() != null && checkIn.getTipoCliente().equals("INT")){
					parametros.put("TOTAL_ORCAMENTO", total);
				}else{
					parametros.put("TOTAL_ORCAMENTO", total);
				}
				
			}
//			if(checkIn.getTipoCliente() != null && checkIn.getTipoCliente().equals("INT")){
//				parametros.put("TOTAL_MAT_USU_TEC", BigDecimal.ZERO);
//			}else{
//				parametros.put("TOTAL_MAT_USU_TEC", total.multiply(BigDecimal.valueOf(0.05)));
//			}
			if(checkIn.getTipoCliente() != null && checkIn.getTipoCliente().equals("INT")){
				parametros.put("TOTAL_SERVICO_TERCEIROS", servico_terceiros);
			}else{
				parametros.put("TOTAL_SERVICO_TERCEIROS", servico_terceiros.multiply(BigDecimal.valueOf(1.50)));
			}
//			InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("marcosaLogo.gif");
//			File img = File.createTempFile("img", "gif",new File("."));
//			OutputStream out=new FileOutputStream(img);
//			byte buf[]=new byte[1024];
//			int len;
//			while((len=inputStream.read(buf))>0)
//				out.write(buf,0,len);
//			out.close();
//			inputStream.close();
			
			//parametros.put("logo", img);
			
			
			SQL = "select distinct S.JOB_CONTROL, J.NOME_SECAO from GE_SEGMENTO S, JOBCONTROL J "+
				"	where ID_CHECKIN ="+idChechIn+
				"		and J.RESPAR = s.JOB_CONTROL";
			//manager = JpaUtil.getInstance();
			
			query = manager.createNativeQuery(SQL);
			List<Object[]> pair = query.getResultList();
			List<SegmentoBean> segmentoList = new java.util.ArrayList<SegmentoBean>();
			con = com.gestaoequipamentos.util.Connection.getConnection();
			for (Object[] objects : pair) {
				SegmentoBean segmento = new SegmentoBean();
				segmento.setJbct((String)objects[0]);
				segmento.setJbctStr((String)objects[1]);



				List<FotosBean> fileList = new java.util.ArrayList<FotosBean>();
				
				File imagem = null;
				InputStream input;
				OutputStream stream;
				Integer length;
				byte bufeer[];
				RelatorioFotosBean relatorioFotosBean;
				String sql = "select FOTO, NOME_ARQUIVO, convert(varchar(4000), OBS) OBS, ID_CHECK_IN from GE_FOTO_ORCAMENTISTA where NUMERO_OS = '"+checkIn.getNumeroOs()+"' and JOB_CONTROL = '"+segmento.getJbct()+"'";
				stmt= con.createStatement();
				rs = stmt.executeQuery(sql);
				Long idCheckin = 0L;
				while(rs.next()){
					FotosBean fotosBean = new FotosBean();
					byte[] fileBytes = rs.getBytes(1);
					String nomeArquivo = rs.getString("NOME_ARQUIVO");
					imagem = File.createTempFile(nomeArquivo, "jpg");

					FileOutputStream fs = new FileOutputStream(imagem);
					BufferedOutputStream bs = new BufferedOutputStream(fs);
					bs.write(fileBytes);
					bs.close();
					bs = null;
					fotosBean.setImagem(imagem);
					fotosBean.setNomeArquivo(nomeArquivo);
					fotosBean.setObservacao(rs.getString("OBS"));
					fileList.add(fotosBean);
					//imagem.delete();
					idCheckin = rs.getLong("ID_CHECK_IN");

				}
				
				if(fileList.size() == 0){
					continue;
				}


				// c:\teste\fotos\2\ALIENWARE.jpg\boxshot_us_large__16844_zoom.jpg



				List<RelatorioFotosBean> files = new ArrayList();					
				// Insere o Logo do relatório.
				InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("logo.jpg");					

				File img=File.createTempFile("img", "jpg",new File("."));

				OutputStream out=new FileOutputStream(img);
				byte buf[]=new byte[1024];
				int len;
				while((len=inputStream.read(buf))>0)
					out.write(buf,0,len);
				out.close();
				inputStream.close();	
				parametros.put("logo", img);

				//Insere duas por vez
				int mod = fileList.size() % 2;

				for(int i = 0; i < fileList.size(); i++){
					relatorioFotosBean = new RelatorioFotosBean();
					FotosBean fotosBean = fileList.get(i);
					int aux = 2;
					int count = 0;
					if(fileList.size()-i < 2){
						aux = mod;
					}

					for(int j = i; j< aux+i; j++){
						switch (count) {
						case 0:
							input = new FileInputStream(fotosBean.getImagem());
							imagem=File.createTempFile("img"+String.valueOf(j), "jpg",new File("."));
							stream = new FileOutputStream(imagem);

							bufeer=new byte[1024];
							length = new Integer(0);

							while((length=input.read(bufeer))>0){

								stream.write(bufeer,0,length);						
							}
							stream.close();
							input.close();

							relatorioFotosBean.setImg1(imagem);		
							relatorioFotosBean.setDescricao1(fotosBean.getObservacao());	
							fotosBean.getImagem().delete();
							break;
						case 1:
							i++;
							fotosBean = fileList.get(i);
							input = new FileInputStream(fotosBean.getImagem());
							imagem=File.createTempFile("img"+String.valueOf(j), "jpg",new File("."));
							stream = new FileOutputStream(imagem);

							bufeer=new byte[1024];
							length = new Integer(0);

							while((length=input.read(bufeer))>0){
								//System.out.println("true");
								stream.write(bufeer,0,length);						
							}
							stream.close();
							input.close();

							relatorioFotosBean.setImg2(imagem);		
							relatorioFotosBean.setDescricao2(fotosBean.getObservacao());
							fotosBean.getImagem().delete();
							break;						
						default:
							break;
						}
						count++;
					}

					files.add(relatorioFotosBean);
					fileListApagar.add(relatorioFotosBean);
				}
				segmento.setFotosList(new JRBeanCollectionDataSource (files));
				segmentoList.add(segmento);
			}
			
			JRBeanCollectionDataSource segmentosFotos = new JRBeanCollectionDataSource(segmentoList);	
			parametros.put("SEGMENTO_FOTOS", segmentosFotos);
			byte[] pdfInspecao = null;  
			//Gera o pdf para exibicao..  
			try { 
				//con = Connection.getConnection();
				//while (con == null ){
					//con = Connection.getConnection();
				//}
				pdfInspecao = JasperRunManager.runReportToPdf(jasperReport, parametros, con);  
			} catch (Exception jre) {  
				jre.printStackTrace();  
			}

			//Parametros para nao fazer cache e o que será exibido..  
			response.setContentType( "application/pdf" );  
			response.setHeader("Content-disposition",
			"attachment; filename=proposta.pdf" ); 

			//Envia para o navegador o pdf..  
			ServletOutputStream servletOutputStream = response.getOutputStream();  
			servletOutputStream.write( pdfInspecao );  
			servletOutputStream.flush();  
			servletOutputStream.close();
			
			for (RelatorioFotosBean fotosBean : fileListApagar){
				if(fotosBean.getImg1() != null){
					fotosBean.getImg1().delete();
				}
				if(fotosBean.getImg2() != null){
					fotosBean.getImg2().delete();
				}
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
			if(conDbs != null){
				try {
					conDbs.close();
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public Statement sendNotesObsOsDbs(UsuarioBean usuarioBean,
			java.sql.Connection conDbs, Statement stmt, GeCheckIn checkIn)
			throws SQLException, Exception {
		String obsOS = checkIn.getObsProposta();
		if(obsOS != null && obsOS.length() > 0){
			int initIndex = 0;
			int lengthIndex = 48;
			String SQL = "";
			Integer linha = 24;
			if(checkIn.getTipoCliente().equals("INT")){
				SQL = "delete from "+IConstantAccess.LIB_DBS+".WOPNOTE0 n where TRIM(n.NTLNO1) > '011' and wono = '"+checkIn.getNumeroOs()+"'";
				linha = 12;
			}else{
				SQL = "delete from "+IConstantAccess.LIB_DBS+".WOPNOTE0 n where TRIM(n.NTLNO1) > '023' and wono = '"+checkIn.getNumeroOs()+"'";
				linha = 24;
			}
			stmt = conDbs.createStatement();
			stmt.executeUpdate(SQL);
			String aux = "";
			for (initIndex = 0; initIndex < obsOS.length() ; initIndex++) {
				aux += obsOS.charAt(initIndex);
				if(initIndex -1 == lengthIndex){
					stmt = new ChekinBusiness(usuarioBean).setNotesFluxoOSDBS(aux.toUpperCase(), conDbs, checkIn.getNumeroOs(), (linha < 100)?"0"+linha:linha+"");
					lengthIndex += 50;
					linha += 1;
					aux = "";
				}
			}
			if(aux.length() > 0){
				stmt = new ChekinBusiness(usuarioBean).setNotesFluxoOSDBS(aux.toUpperCase(), conDbs, checkIn.getNumeroOs(), (linha < 100)?"0"+linha:linha+"");
			}
		}
		return stmt;
	}

}
