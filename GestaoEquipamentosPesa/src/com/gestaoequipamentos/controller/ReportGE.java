package com.gestaoequipamentos.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.gestaoequipamentos.bean.FilialBean;
import com.gestaoequipamentos.bean.SituacaoOSBean;
import com.gestaoequipamentos.business.GestorRentalBusiness;
import com.gestaoequipamentos.util.JpaUtil;

/**
 * Servlet implementation class ReportGE
 */
public class ReportGE extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportGE() {
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
		String idFilial = request.getParameter("idFilial");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		JasperReport jasperReport = null;  
		JasperPrint pdfInspecao = null;
		EntityManager manager = null;
		List<BigDecimal> listFilial = new ArrayList<BigDecimal>();
		
		
		//Obtem o caminho do .jasper  
		ServletContext servletContext = super.getServletContext();  
		String caminhoJasper = servletContext.getRealPath("WEB-INF/fluxoOs/FluxoOS.jasper"); 
		
		//Carrega o arquivo com o jasperReport  
		try {  
			jasperReport = (JasperReport) JRLoader.loadObject( caminhoJasper );  
		} catch (Exception jre) {  
			jre.printStackTrace();  
		}  
		try {
			manager = JpaUtil.getInstance();
			
			GestorRentalBusiness bussines = new GestorRentalBusiness();
			if(!idFilial.equals("-1")) { //Se foi selecionada uma filial específica
				listFilial.add(BigDecimal.valueOf(Long.valueOf(idFilial)));
			} else { //Se foi selecionadas todas filiais
				idFilial = "";
				for (FilialBean filial : bussines.findAllFilial()) {
					idFilial+=filial.getStno() + ",";
				}
				idFilial = idFilial.substring(0,idFilial.length()-1);
			}

			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat format2 = new SimpleDateFormat("yyyyMMdd");
			Date date = format.parse(beginDate);
			beginDate = format2.format(date);
			date = format.parse(endDate);
			endDate = format2.format(date);
			String SQL = "select os.NUMERO_OS,"+
					" CONVERT(varchar(10),os.DATA_CHEGADA,103)as DATA_CHEGADA,"+
					" CONVERT(varchar(10),os.DATA_ORCAMENTO,103) as DATA_ORCAMENTO,"+
					" CONVERT(varchar(10),os.DATA_ENTREGA_PEDIDOS,103)as DATA_ENTREGA_PEDIDOS,"+
					" CONVERT(varchar(10),os.DATA_APROVACAO,103)as DATA_APROVACAO,"+
					" CONVERT(varchar(10),os.DATA_TERMINO_SERVICO,103) as DATA_TERMINO_SERVICO,"+
					" CONVERT(varchar(10),os.DATA_PREVISAO_ENTREGA,103)as DATA_PREVISAO_ENTREGA,"+
					" CONVERT(varchar(10),os.DATA_FATURAMENTO,103)as DATA_FATURAMENTO,"+
					" os.ID_CHECKIN, palm.ID_FAMILIA,ch.TIPO_CLIENTE, palm.cliente, ch.JOB_CONTROL, palm.modelo, palm.SERIE" +
					" from GE_SITUACAO_OS os, GE_CHECK_IN ch,GE_OS_PALM palm"+
					" where os.ID_CHECKIN = ch.ID" +
					" and  ch.ID_OS_PALM = palm.IDOS_PALM"+
					" and CONVERT(varchar(10),ch.DATA_EMISSAO,112) between "+beginDate+" and  "+endDate+""+
					" and os.filial in("+idFilial+")";
			
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> list = query.getResultList();
			ServletOutputStream servletOutputStream = response.getOutputStream();  
			List<SituacaoOSBean> situacaoList = new ArrayList<SituacaoOSBean>();
			for (Object[] objects : list) {
				
				SituacaoOSBean bean = new SituacaoOSBean();
				bean.setNumeroOS((String)objects[0]);
				bean.setDataChegada((String)objects[1]);
				bean.setDataOrcamento((String)objects[2]);
				bean.setDataEntregaPedidos((String)objects[3]);
				bean.setDataAprovacao((String)objects[4]);
				bean.setDataTerminoServico((String)objects[5]);
				bean.setDataPrevisaoEntrega((String)objects[6]);
				bean.setDataFaturamento((String)objects[7]);
				bean.setCliente((String)objects[11]);
				bean.setJobControl((String)objects[12]);
				bean.setModelo((String)objects[13]);
				bean.setSerie((String)objects[14]);
				bean.setTipoCliente((String)objects[10]);
				SQL = "select sum(CASE WHEN p.valor_total is null then 0 else p.valor_total end) from ge_pecas p, (select oper.num_doc, max(oper.id) id_oper from ge_doc_seg_oper oper, ge_segmento seg"+
                        " where seg.id_checkin ="+((BigDecimal)objects[8])+
                        " and seg.id = oper.id_segmento"+
                        " group by oper.num_doc)  aux"+
                " where p.id_doc_seg_oper = aux.id_oper";
				query = manager.createNativeQuery(SQL);
				if(query.getResultList().size() > 0){
					Object valor = query.getResultList().get(0);
					if(valor == null){
						valor = BigDecimal.ZERO;
					}
					bean.setValorTotalPecas(((BigDecimal)valor));
				}
				
				if((String)objects[10] != null && !(objects[10].toString().equals("INT"))){
	                SQL =  "select distinct ((pre.valor_de_venda * ch.fator_cliente)* "+
	                        "    (select c.fator from ge_complexidade c "+
	                        "    where c.id =  "+
	                        "    (select p.id_complexidade from ge_preco p where p.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+((BigDecimal)objects[9])+")"+ 
	                        "     and p.id_prefixo = (select pre.id from ge_prefixo pre where pre.descricao = substring(palm.serie,0,5) and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+((BigDecimal)objects[9])+") )"+
	                        "     and p.comp_code = seg.com_code"+
	                        "     and p.job_code = seg.job_code )"+
	                        "    ) * CASE WHEN ch.fator_urgencia = 'S'"+
	                        " THEN (select fator_urgencia from ge_fator) ELSE 1 END) * seg.qtd_comp valor_total_hora, seg.horas_prevista,"+

	                        "    (select c.fator from ge_complexidade c"+ 
	                        "    where c.id =  "+
	                        "    (select p.id_complexidade from ge_preco p where p.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+((BigDecimal)objects[9])+")"+ 
	                        "     and p.id_prefixo = (select pre.id from ge_prefixo pre where pre.descricao = substring(palm.serie,0,5) and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = "+((BigDecimal)objects[9])+") )"+
	                        "     and p.comp_code = seg.com_code"+
	                        "     and p.job_code = seg.job_code )"+
	                        "    ) complexidade,ch.fator_urgencia , ch.fator_cliente, pre.valor_de_venda, ch.valor_servicos_terceiros, seg.numero_segmento"+

	                        "    from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre"+ 
	                        "    where ch.id = "+((BigDecimal)objects[8])+
	                        "    and ch.id = seg.id_checkin"+
	                        "    and ch.id_os_palm = palm.idos_palm"+
	                        "    and pre.descricao = substring(palm.serie,0,5)" +
	                        "   and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = palm.id_familia)  ";
	            }else{
	                SQL = " select ((select f.valor_inter from ge_fator f) * seg.qtd_comp), seg.horas_prevista"+
	                        "    from ge_check_in ch, ge_segmento seg, ge_os_palm palm, ge_prefixo pre"+ 
	                        "    where ch.id = "+((BigDecimal)objects[8])+
	                        "    and ch.id = seg.id_checkin"+
	                        "    and ch.id_os_palm = palm.idos_palm"+
	                        "    and pre.descricao = substring(palm.serie,0,5)" +
	                        "   and pre.id_modelo = (select a.id_arv from ge_arv_inspecao a where a.descricao = palm.modelo and a.id_familia = palm.id_familia)  ";
	            }
				//System.out.println(SQL);
				query = manager.createNativeQuery(SQL);
				List<Object[]> list2 = query.getResultList();


				BigDecimal valorMaoDeObra = BigDecimal.ZERO;
				BigDecimal total = BigDecimal.ZERO;
				for (Object[] objects2 : list2) {
	                valorMaoDeObra = (BigDecimal)objects2[0];
	                if(valorMaoDeObra == null){
	                    valorMaoDeObra = BigDecimal.ZERO;
	                }
	                String horas = (String)objects2[1];
	                //horas = "5.00"; //<<<<<<<<<<<<<<<<<<<<<<<<<<<<
//	                String [] aux = horas.split(":");
//	                Double h = Double.valueOf(aux[0]);
//	                Double m = Double.valueOf(aux[1]) / 60;
//	                h = h+m;
	                total = total.add(valorMaoDeObra.multiply(BigDecimal.valueOf(Double.valueOf(horas))));
	                //servico_terceiros = (BigDecimal)objects[6];
	            }
	            
	            bean.setValorMaoDeObra(total);
				
				situacaoList.add(bean);
				response.setContentType( "application/vnd.ms-excel" );
				servletOutputStream.print("");  
				servletOutputStream.flush(); 
				
			}
					
			
			Map parametros = new HashMap();
			
			
			
			
			JRBeanCollectionDataSource result =  new JRBeanCollectionDataSource(situacaoList);
			
			byte buf[]=new byte[1024];
			int len;
			
			//Gera o excel para exibicao..
			byte[] bytes = null;
			try {  
				pdfInspecao = JasperFillManager.fillReport(jasperReport, parametros, result);  
				JRXlsExporter exporter = new JRXlsExporter();     
				ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();     
				exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, pdfInspecao);    
				exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);     
				exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);     
				exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);     
				exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);    
				exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);  
				//exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, "c:/relatorio.xls");  
				
				
				exporter.exportReport();     
				bytes = xlsReport.toByteArray();     
			} catch (Exception jre) {  
				jre.printStackTrace();  
			}  
			
			//Parametros para nao fazer cache e o que será exibido..  
			response.setContentType( "application/vnd.ms-excel" );  
			//			response.setHeader("Content-disposition",
			//			"attachment; filename=transferencia" ); 
			response.setHeader("content-disposition", "inline; filename=fluxoDatas.xls");   
			
			//Envia para o navegador o pdf..  
			
			servletOutputStream.write( bytes );  
			servletOutputStream.flush();  
			servletOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		
	}
	}


