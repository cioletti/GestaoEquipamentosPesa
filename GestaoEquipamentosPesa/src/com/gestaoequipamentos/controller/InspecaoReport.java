package com.gestaoequipamentos.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

import com.gestaoequipamentos.util.Connection;

/**
 * Servlet implementation class InspecaoReport
 */
public class InspecaoReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InspecaoReport() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	this.doGet(request, response);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String osPalmId  = request.getParameter("osPalmId");
		String equipamento = request.getParameter("equipamento");
		String isCheckin = request.getParameter("isCheckin");
		
		JasperReport jasperReport = null;  
		byte[] pdfInspecao = null;    

		//Obtem o caminho do .jasper  
		ServletContext servletContext = super.getServletContext();
		String caminhoJasper = "";
		String pathSubreport = "";
		if(Boolean.valueOf(isCheckin)){
			caminhoJasper = servletContext.getRealPath("WEB-INF/inspecao/inspecao.jasper"); 
			pathSubreport = servletContext.getRealPath("WEB-INF/inspecao/")+"/"; 
		}else{
			caminhoJasper = servletContext.getRealPath("WEB-INF/inspecao_checkout/inspecao.jasper"); 
			pathSubreport = servletContext.getRealPath("WEB-INF/inspecao_checkout/")+"/"; 
		}
		//Carrega o arquivo com o jasperReport  
		try {  
			jasperReport = (JasperReport) JRLoader.loadObject( caminhoJasper );  
		} catch (Exception jre) {  
			jre.printStackTrace();  
		}  
		java.sql.Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try{
			
			String SQL = "select f.stnm from ge_os_palm t, tw_filial f"+
			" where t.filial = f.stno"+
			" and t.idos_palm ="+Long.valueOf(osPalmId);
			conn = Connection.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(SQL);
			rs.next();
			Map parametros = new HashMap();  
			
			parametros.put("SUBREPORT_DIR", pathSubreport);  
			 
			parametros.put("equipamento", equipamento); 
			parametros.put("nome_filial", rs.getString("stnm")); 
			
			parametros.put("idos_palm", BigDecimal.valueOf(Integer.parseInt(osPalmId)));
		 

			InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("logo.jpg");
			File img = File.createTempFile("img", "gif",new File("."));
			OutputStream out=new FileOutputStream(img);
			byte buf[]=new byte[1024];
			int len;
			while((len=inputStream.read(buf))>0)
				out.write(buf,0,len);
			out.close();
			inputStream.close();
			
			parametros.put("logo", img);			
			InputStream inputStreamCamera =  getClass().getClassLoader().getResourceAsStream("camera.GIF");
			File imgCamera = File.createTempFile("imgCamera", "GIF",new File("."));
			OutputStream outCamera = new FileOutputStream(imgCamera);
			byte bufCamera[] = new byte[1024];
			int lenCamera;
			while((lenCamera = inputStreamCamera.read(bufCamera))>0)
				outCamera.write(bufCamera,0,lenCamera);
			outCamera.close();
			inputStreamCamera.close();			
			
			parametros.put("foto_camera", imgCamera);

			//Gera o pdf para exibicao..  
			try {  
				pdfInspecao = JasperRunManager.runReportToPdf( jasperReport, parametros, conn);  
			} catch (Exception jre) {  
				jre.printStackTrace();  
			}  

			//Parametros para nao fazer cache e o que será exibido..  
			response.setContentType( "application/pdf" );  
			response.setHeader("Content-disposition",
			"attachment; filename=inspecao.pdf" ); 

			//Envia para o navegador o pdf..  
			ServletOutputStream servletOutputStream = response.getOutputStream();  
			servletOutputStream.write( pdfInspecao );  
			servletOutputStream.flush();  
			servletOutputStream.close();
			img.delete();
			imgCamera.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				conn.close();
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
