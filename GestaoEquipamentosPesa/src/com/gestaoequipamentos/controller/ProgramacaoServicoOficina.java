package com.gestaoequipamentos.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.gestaoequipamentos.bean.DataHeaderBean;
import com.gestaoequipamentos.bean.SegmentoBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.business.ChekinBusiness;

/**
 * Servlet implementation class ProgramacaoServicoOficina
 */
public class ProgramacaoServicoOficina extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProgramacaoServicoOficina() {
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
		String dataExata = request.getParameter("dataExata");
		String jobControl = request.getParameter("jobControl");
		String tipoCliente = request.getParameter("tipoCliente");
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		String codigos = request.getParameter("codigos");
		String campoPesquisa = request.getParameter("campoPesquisa");
		JasperReport jasperReport = null;  
		JasperPrint pdfInspecao = null;
		try {
			if(dataExata == null){
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				dataExata = sdf.format(calendar.getTime());
			}
			//Obtem o caminho do .jasper  
			ServletContext servletContext = super.getServletContext();  
			String caminhoJasper = servletContext.getRealPath("/WEB-INF/programacaoServicoOficina/programacaoServicoOficina.jasper"); 
			
			//Carrega o arquivo com o jasperReport  
			try {  
				jasperReport = (JasperReport) JRLoader.loadObject( caminhoJasper );  
			} catch (Exception jre) {  
				jre.printStackTrace();  
			}  
			UsuarioBean usuarioBean = (UsuarioBean)request.getSession().getAttribute("usuario");
			ChekinBusiness business = new ChekinBusiness(usuarioBean);
			List<SegmentoBean> segmentoList = new ArrayList<SegmentoBean>();
			List<DataHeaderBean> dataHeaderList = business.findAllHeaderList(dataExata);
			if(jobControl == null){
				List<String> jbct = business.findAllJobControlLegenda(tipoCliente, jobControl, campoPesquisa, beginDate, endDate, codigos);
				for (String string : jbct) {
					segmentoList.addAll(business.findAllStatusFluxoSegmentoOs(dataHeaderList, tipoCliente, string, campoPesquisa, beginDate, endDate, codigos));
				}
			}else{
			  segmentoList = business.findAllStatusFluxoSegmentoOs(dataHeaderList, tipoCliente, jobControl, campoPesquisa, beginDate, endDate, codigos);
			}
			for (SegmentoBean segmentoBean : segmentoList) {
				for (int i = 0; i < segmentoBean.getSegmentoList().size(); i++) {
					SegmentoBean segBean = segmentoBean.getSegmentoList().get(i);
					if(segBean.getSiglaStatusLegenda().equals("BRANCO")){
						continue;
					}
					switch (i) {
					case 0:
						segmentoBean.setData1(segBean.getSiglaStatusLegenda());
						break;
					case 1:
						segmentoBean.setData2(segBean.getSiglaStatusLegenda());
						break;
					case 2:
						segmentoBean.setData3(segBean.getSiglaStatusLegenda());
						break;
					case 3:
						segmentoBean.setData4(segBean.getSiglaStatusLegenda());
						break;

					default:
						break;
					}
				}
				
				
			}
			
			
			Map parametros = new HashMap();
			parametros.put("data1", dataHeaderList.get(7).getDateString());
			parametros.put("data2", dataHeaderList.get(8).getDateString());
			//parametros.put("data3", dataHeaderList.get(9).getDateString());
			//parametros.put("data4", dataHeaderList.get(10).getDateString());
			
			
			
			
			JRBeanCollectionDataSource result =  new JRBeanCollectionDataSource(segmentoList);
			
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
			ServletOutputStream servletOutputStream = response.getOutputStream();  
			servletOutputStream.write( bytes );  
			servletOutputStream.flush();  
			servletOutputStream.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
