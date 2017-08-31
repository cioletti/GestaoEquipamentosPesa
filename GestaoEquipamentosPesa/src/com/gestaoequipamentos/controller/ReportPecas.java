package com.gestaoequipamentos.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
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
import com.gestaoequipamentos.bean.PecasBean;
import com.gestaoequipamentos.bean.SituacaoOSBean;
import com.gestaoequipamentos.business.GestorRentalBusiness;
import com.gestaoequipamentos.business.ImportXmlBusiness;
import com.gestaoequipamentos.business.PecasBusiness;
import com.gestaoequipamentos.util.JpaUtil;

/**
 * Servlet implementation class ReportPecas
 */
public class ReportPecas extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReportPecas() {
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
		String idSegmento = request.getParameter("idSegmento");
		String semDoc = request.getParameter("semDoc");
		List<PecasBean> listaPecas = new ArrayList<PecasBean>();
		JasperReport jasperReport = null;  
		JasperPrint pdfInspecao = null;


		//Obtem o caminho do .jasper  
		ServletContext servletContext = super.getServletContext();  
		String caminhoJasper = servletContext.getRealPath("WEB-INF/relatorio_pecas/relatorio_pecas.jasper"); 

		//Carrega o arquivo com o jasperReport  
		try {  
			jasperReport = (JasperReport) JRLoader.loadObject( caminhoJasper );  
		} catch (Exception jre) {  
			jre.printStackTrace();  
		}  
		try {
			ImportXmlBusiness business = new ImportXmlBusiness();
			listaPecas = business.findAllPecas(Long.valueOf(idSegmento),null, semDoc);

			Map parametros = new HashMap();

			JRBeanCollectionDataSource result =  new JRBeanCollectionDataSource(listaPecas);
			
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
			response.setHeader("content-disposition", "inline; filename=RelatorioPecas.xls");   
			
			//Envia para o navegador o pdf..  
			ServletOutputStream servletOutputStream = response.getOutputStream();  
			servletOutputStream.write( bytes );  
			servletOutputStream.flush();  
			servletOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
