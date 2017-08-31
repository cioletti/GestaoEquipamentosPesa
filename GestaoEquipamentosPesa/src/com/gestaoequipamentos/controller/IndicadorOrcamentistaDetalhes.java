package com.gestaoequipamentos.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

import com.gestaoequipamentos.bean.IndicadorOrcamentistaBean;
import com.gestaoequipamentos.bean.IndicadorOrcamentistaDetalhesBean;
import com.gestaoequipamentos.bean.RelatorioFotosBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.TwFuncionario;
import com.gestaoequipamentos.util.JpaUtil;

/**
 * Servlet implementation class IndicadorOrcamentistaDetalhes
 */
public class IndicadorOrcamentistaDetalhes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndicadorOrcamentistaDetalhes() {
        super();
        // TODO Auto-generated constructor stub
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
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdfQuery = new SimpleDateFormat("yyyyMMdd");
		EntityManager manager = null;
		String dataInicio = request.getParameter("beginDate");
		String dataFim = request.getParameter("endDate");
		String orcamentista = request.getParameter("orcamentista");
		try {
			manager = JpaUtil.getInstance();
			Map parametros = new HashMap();  

//			TwFuncionario funcionario = manager.find(TwFuncionario.class, orcamentista);
			parametros.put("funcionario", "INDICADORES ORÇAMENTISTA");
			UsuarioBean usuarioBean = (UsuarioBean)request.getSession().getAttribute("usuario");

			String SQL = "select f.EPIDNO, f.EPLSNM+' |<-->| '+per.DESCRICAO, f.EPLSNM from TW_FUNCIONARIO f, ADM_PERFIL_SISTEMA_USUARIO adm, ADM_PERFIL per"+
						"	where adm.ID_TW_USUARIO = f.EPIDNO"+
						"	and adm.ID_PERFIL in (select id from adm_perfil where sigla in ('ORC', 'ADM', 'ORCA', 'GAOS', 'GERENT', 'GEGAR')  and TIPO_SISTEMA = 'GE')"+
						"	and f.STN1 = "+Integer.valueOf(usuarioBean.getFilial())+
						"	and per.ID = adm.ID_PERFIL "+
						"	and f.EPIDNO in ("+orcamentista+")"+
						"	order by f.EPLSNM";		
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> orcList = (List<Object[]>)query.getResultList();
			
			
			Calendar calDateIni = Calendar.getInstance();
			
			Calendar calDateFim = Calendar.getInstance();
			
			List<IndicadorOrcamentistaDetalhesBean> indicadorList = new java.util.ArrayList<IndicadorOrcamentistaDetalhesBean>();
			ServletOutputStream servletOutputStream = response.getOutputStream(); 
			for (Object[] objects : orcList) {
				calDateIni.setTime(sdf.parse(dataInicio));
				calDateFim.setTime(sdf.parse(dataFim));
				
				while(calDateIni.getTimeInMillis() <= calDateFim.getTimeInMillis()){
					SQL = "select  CONVERT(varchar(10), os.DATA_ORCAMENTO, 103) data, palm.CLIENTE, os.NUMERO_OS, palm.MODELO, palm.serie," +
							" CONVERT (VARCHAR, os.data_entrega_pedidos, 103) as data_entrega_pedidos ,datediff(dd, os.data_entrega_pedidos, os.data_orcamento) as dias, CONVERT (VARCHAR, os.data_aprovacao, 103) as data_aprovacao," +
							" ch.tipo_cliente, ch.POSSUI_SUB_TRIBUTARIA, ch.id idcheckin, palm.id_familia " +
					" from GE_SITUACAO_OS os, TW_FUNCIONARIO f, GE_CHECK_IN ch, GE_OS_PALM palm"+
					"	where CONVERT(varchar(10), DATA_ORCAMENTO, 112) = '"+sdfQuery.format(calDateIni.getTime())+"'"+
					"	and os.ID_FUNC_DATA_ORCAMENTO = f.EPIDNO"+
					"	and ch.ID = os.ID_CHECKIN"+
					"	and palm.IDOS_PALM = ch.ID_OS_PALM"+
					"	and f.EPIDNO = '"+(String)objects[0]+"'";	

					query = manager.createNativeQuery(SQL);
					List<Object[]> detalhes = (List<Object[]>)query.getResultList();
					for (Object[] objects2 : detalhes) {
						IndicadorOrcamentistaDetalhesBean detalheBean = new IndicadorOrcamentistaDetalhesBean();
						detalheBean.setData((String)objects2[0]);
						detalheBean.setCliente((String)objects2[1]);
						detalheBean.setNumeroOs((String)objects2[2]);
						detalheBean.setModelo((String)objects2[3]);
						detalheBean.setSerie((String)objects2[4]);
						detalheBean.setDataEntregaPedidos((String)objects2[5]);
						detalheBean.setTempoEspera(((Integer)objects2[6]).toString());
						detalheBean.setDataAprovacao((String)objects2[7]);
						detalheBean.setTipoCliente((String)objects2[8]);
						detalheBean.setFuncionario((String)objects[2]);
						
						BigDecimal idCheckIn = (BigDecimal)objects2[10];
						BigDecimal idFamilia = (BigDecimal)objects2[11];
						if(objects2[9] == null || ((String)objects2[9]).equals("N")){
							query = manager.createNativeQuery("select sum(CASE WHEN p.valor_total is null then 0 else p.valor_total end) from ge_pecas p, (select oper.num_doc, max(oper.id) id_oper from ge_doc_seg_oper oper, ge_segmento seg"+
									" where seg.id_checkin ="+idCheckIn.longValue()+
									" and seg.id = oper.id_segmento"+
									" group by oper.num_doc)  aux"+
							" where p.id_doc_seg_oper = aux.id_oper");
						}else{
							query = manager.createNativeQuery("select sum(CASE WHEN p.TOTALTRIBUTOS is null then 0 else p.TOTALTRIBUTOS end) from ge_pecas p, (select oper.num_doc, max(oper.id) id_oper from ge_doc_seg_oper oper, ge_segmento seg"+
									" where seg.id_checkin ="+idCheckIn.longValue()+
									" and seg.id = oper.id_segmento"+
									" group by oper.num_doc)  aux"+
							" where p.id_doc_seg_oper = aux.id_oper"); 
						}
						BigDecimal totalPecas = BigDecimal.ZERO;
						if(query.getResultList().size() > 0){
							totalPecas = (BigDecimal)query.getSingleResult();
						}
						detalheBean.setValorPecas(totalPecas);
						
						BigDecimal servico_terceiros = BigDecimal.ZERO;
						SQL = "select sum(CASE WHEN st.valor is null then 0 else st.valor end)  from ge_segmento seg, ge_segmento_serv_terceiros st"+
							  "	where seg.id = st.id_segmento"+
							  "	and seg.id_checkin = "+idCheckIn.longValue(); 
						query = manager.createNativeQuery(SQL);
						BigDecimal st = (BigDecimal)query.getSingleResult();
						if(st != null  && st.longValue() > 0){
							servico_terceiros = st;
						}
						detalheBean.setValorServTerceiros(servico_terceiros);
						
						if(!((String)objects2[8]).equals("INT")){
							SQL = "select distinct ((pre.valor_de_venda * ch.fator_cliente)* "+
									"	(select c.fator from ge_complexidade c "+
									"	where c.id =  "+
									"	(select p.id_complexidade from ge_preco p where p.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+idFamilia.longValue()+")"+ 
									"	 and p.id_prefixo = (select pre.id from ge_prefixo pre where pre.descricao = substring(palm.serie,0,5) and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+idFamilia.longValue()+") )"+
									"	 and p.comp_code = seg.com_code"+
									"	 and p.job_code = seg.job_code )"+
									"	) * CASE WHEN ch.fator_urgencia = 'S'"+
									" THEN (select fator_urgencia from ge_fator) ELSE 1 END) * seg.qtd_comp valor_total_hora, seg.horas_prevista,"+

									"	(select c.fator from ge_complexidade c"+ 
									"	where c.id =  "+
									"	(select p.id_complexidade from ge_preco p where p.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+idFamilia.longValue()+")"+ 
									"	 and p.id_prefixo = (select pre.id from ge_prefixo pre where pre.descricao = substring(palm.serie,0,5) and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+idFamilia.longValue()+") )"+
									"	 and p.comp_code = seg.com_code"+
									"	 and p.job_code = seg.job_code )"+
									"	) complexidade,ch.fator_urgencia , ch.fator_cliente, pre.valor_de_venda, ch.valor_servicos_terceiros, seg.numero_segmento"+

									"	from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre"+ 
									"	where ch.id = "+idCheckIn.longValue()+
									"	and ch.id = seg.id_checkin"+
									"	and ch.id_os_palm = palm.idos_palm"+
									"	and pre.descricao = substring(palm.serie,0,5)" +
									"   and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = palm.id_familia)  ";
						}else{
							SQL = " select ((select f.valor_inter from ge_fator f) * seg.qtd_comp), seg.horas_prevista"+
									"	from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre"+ 
									"	where ch.id = "+idCheckIn.longValue()+
									"	and ch.id = seg.id_checkin"+
									"	and ch.id_os_palm = palm.idos_palm"+
									"	and pre.descricao = substring(palm.serie,0,5)" +
									"   and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = palm.id_familia)  ";
						}
						query = manager.createNativeQuery(SQL);
						List<Object[]> list = query.getResultList();
						BigDecimal valorMaoDeObra = BigDecimal.ZERO;
						BigDecimal total = BigDecimal.ZERO;
						//List<RelatorioFotosBean> fileListApagar = new java.util.ArrayList<RelatorioFotosBean>();
						for (Object[] objectsMO : list) {
							valorMaoDeObra = (BigDecimal)objectsMO[0];
							if(valorMaoDeObra == null){
								valorMaoDeObra = BigDecimal.ZERO;
							}
							String horas = (String)objectsMO[1];
							//horas = "5.00"; //<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//							String [] aux = horas.split(":");
//							Double h = Double.valueOf(aux[0]);
//							Double m = Double.valueOf(aux[1]) / 60;
//							h = h+m;
							total = total.add(valorMaoDeObra.multiply(BigDecimal.valueOf(Double.valueOf(horas))));
							//servico_terceiros = (BigDecimal)objects[6];
						}
						detalheBean.setValorMO(total);
						indicadorList.add(detalheBean);
//						response.setContentType( "application/vnd.ms-excel" );
//						servletOutputStream.print("");  
//						servletOutputStream.flush(); 
					}
					
					calDateIni.add(Calendar.DAY_OF_MONTH, 1);
				}
			}
			if(indicadorList.size() == 0){
				IndicadorOrcamentistaDetalhesBean detalheBean = new IndicadorOrcamentistaDetalhesBean();
				indicadorList.add(detalheBean);
			}
			JRBeanCollectionDataSource result =  new JRBeanCollectionDataSource(indicadorList);
			//Obtem o caminho do .jasper  
			ServletContext servletContext = super.getServletContext();
			String caminhoJasper = servletContext.getRealPath("WEB-INF/indicadorOrc/indicadorDetalheOrc.jasper"); 
			String pathSubreport = servletContext.getRealPath("WEB-INF/indicadorOrc/")+"/"; 
			
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject( caminhoJasper ); 
			
			//byte[] pdfInspecao = JasperRunManager.runReportToPdf( jasperReport, parametros, result);
			
			byte[] bytes = null;
			try {  
				JasperPrint excel = JasperFillManager.fillReport(jasperReport, parametros, result);  
				JRXlsExporter exporter = new JRXlsExporter();     
				ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();     
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, excel);    
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);     
				exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);     
				exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);     
				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
				exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE); 

				exporter.exportReport();     
				bytes = xlsReport.toByteArray();     
			} catch (Exception jre) {  
				jre.printStackTrace();  
			}  

			//Parametros para nao fazer cache e o que será exibido..  
			response.setContentType( "application/vnd.ms-excel" );  
			response.setHeader("content-disposition", "inline; filename=indicadorOrcDetalhes.xls");   

			//Envia para o navegador o pdf..  
			 
			servletOutputStream.write( bytes );  
			servletOutputStream.flush();  
			servletOutputStream.close(); 
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}				
		}
	}

}
