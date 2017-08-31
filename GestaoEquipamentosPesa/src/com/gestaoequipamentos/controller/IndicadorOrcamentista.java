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
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.util.JpaUtil;

/**
 * Servlet implementation class IndicadorOrcamentista
 */
public class IndicadorOrcamentista extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndicadorOrcamentista() {
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
			Map parametros = new HashMap();  
			
			 
			UsuarioBean usuarioBean = (UsuarioBean)request.getSession().getAttribute("usuario");
			manager = JpaUtil.getInstance();
			
			String SQL = "select f.EPIDNO, f.EPLSNM+' |<-->| '+per.DESCRICAO from TW_FUNCIONARIO f, ADM_PERFIL_SISTEMA_USUARIO adm, ADM_PERFIL per"+
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
			
			List<IndicadorOrcamentistaBean> indicadorList = new java.util.ArrayList<IndicadorOrcamentistaBean>();
			int countData = 1;
			int countSet = 1;
			Class  aClass = IndicadorOrcamentistaBean.class;
			for (Object[] objects : orcList) {
				countData = 1;
				countSet = 1;
				calDateIni.setTime(sdf.parse(dataInicio));
				calDateFim.setTime(sdf.parse(dataFim));
				IndicadorOrcamentistaBean bean = new IndicadorOrcamentistaBean();
				Field nome = aClass.getField("nome");
				nome.set( bean, (String)objects[1]); 
				while(calDateIni.getTimeInMillis() <= calDateFim.getTimeInMillis()){
					parametros.put("DATA"+countData, sdf.format(calDateIni.getTime()));
					countData++;
					SQL = "select  COUNT(*) from GE_SITUACAO_OS os, TW_FUNCIONARIO f"+
					"	where CONVERT(varchar(10), os.DATA_ORCAMENTO, 112) = '"+sdfQuery.format(calDateIni.getTime())+"'"+
					"	and os.ID_FUNC_DATA_ORCAMENTO = f.EPIDNO"+
					"	and f.EPIDNO = '"+(String)objects[0]+"'";	

					query = manager.createNativeQuery(SQL);
					Field total = aClass.getField("total"+countSet);
					total.set(bean, ((Integer)query.getSingleResult()).intValue());
					calDateIni.add(Calendar.DAY_OF_MONTH, 1);
					countSet++;
				}
				indicadorList.add(bean);
			}
			JRBeanCollectionDataSource result =  new JRBeanCollectionDataSource(indicadorList);
			//Obtem o caminho do .jasper  
			ServletContext servletContext = super.getServletContext();
			String caminhoJasper = servletContext.getRealPath("WEB-INF/indicadorOrc/indicadorOrc.jasper"); 
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
			response.setHeader("content-disposition", "inline; filename=indicadorOrc.xls");   

			//Envia para o navegador o pdf..  
			ServletOutputStream servletOutputStream = response.getOutputStream();  
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
