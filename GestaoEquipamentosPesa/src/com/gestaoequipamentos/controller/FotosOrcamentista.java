package com.gestaoequipamentos.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
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

import com.gestaoequipamentos.bean.ClienteBean;
import com.gestaoequipamentos.bean.FotosBean;
import com.gestaoequipamentos.bean.RelatorioFotosBean;
import com.gestaoequipamentos.bean.SegmentoBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.business.ClienteBusiness;
import com.gestaoequipamentos.util.JpaUtil;
import com.sun.xml.internal.stream.Entity;

import flex.messaging.io.ArrayList;

/**
 * Servlet implementation class FotosOrcamentista
 */
public class FotosOrcamentista extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FotosOrcamentista() {
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

		String idFotoOrcamentista = request.getParameter("idFotoOrcamentista");
		String jobControl = request.getParameter("jobControl");
		String numeroOS = request.getParameter("numOS");
		String unico = request.getParameter("unico");
		String modelo = request.getParameter("modelo");
		String serie = request.getParameter("serie");
		String codCliente = request.getParameter("codCliente");
		Connection con = null;
		java.sql.PreparedStatement pstmt = null;
		RelatorioFotosBean relatorioFotosBean;
		OutputStream stream;
		byte bufeer[];
		EntityManager manager = null;
		try {
			if(unico.equals("S")){
				con = com.gestaoequipamentos.util.Connection.getConnection();
				String sql = "select FOTO, NOME_ARQUIVO from GE_FOTO_ORCAMENTISTA where ID = "+idFotoOrcamentista;
				pstmt= con.prepareStatement(sql);
				java.sql.ResultSet rs = pstmt.executeQuery();

				if(!rs.next())return ;
				byte[] fileBytes = rs.getBytes(1);
				String nomeArquivo = rs.getString("NOME_ARQUIVO");
				OutputStream os = response.getOutputStream();

				os.write(fileBytes);
				os.flush();
				os.close();

				ServletOutputStream servletOutputStream = response.getOutputStream();

				response.setHeader("Content-disposition", "attachment; filename=" + nomeArquivo);

				servletOutputStream.write(fileBytes);
				servletOutputStream.flush();
				servletOutputStream.close();
			}else{
				try {
				    UsuarioBean bean = (UsuarioBean)request.getSession().getAttribute("usuario");
					List<FotosBean> fileList = new java.util.ArrayList<FotosBean>();	
					Integer length;
					InputStream input;
					File imagem = null;
					List<RelatorioFotosBean> files = new ArrayList();
					con = com.gestaoequipamentos.util.Connection.getConnection();
					String sql = "select FOTO, NOME_ARQUIVO, convert(varchar(4000), OBS) OBS, ID_CHECK_IN from GE_FOTO_ORCAMENTISTA where NUMERO_OS = '"+numeroOS+"' and JOB_CONTROL = '"+jobControl+"'";
					pstmt= con.prepareStatement(sql);
					java.sql.ResultSet rs = pstmt.executeQuery();
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
					
					String SQL = "select distinct convert(varchar(4000), TITULO_FOTOS) TITULO_FOTOS, convert(varchar(4000),DESCRICAO_FALHA_FOTOS) DESCRICAO_FALHA_FOTOS, convert(varchar(4000),CONCLUSAO_FOTOS) CONCLUSAO_FOTOS from GE_SEGMENTO where ID_CHECKIN = "+idCheckin+" and JOB_CONTROL = '"+jobControl+"'";
					manager = JpaUtil.getInstance();
					
					Query query = manager.createNativeQuery(SQL);
					Object[] pair = (Object[])query.getSingleResult();
					SegmentoBean segmento = new SegmentoBean();
					segmento.setTituloFotos((String)pair[0]);
					segmento.setDescricaoFalhaFotos((String)pair[1]);
					segmento.setConclusaoFotos((String)pair[2]);
					// c:\teste\fotos\2\ALIENWARE.jpg\boxshot_us_large__16844_zoom.jpg
					 
					 JasperReport jasperReport = null;  
						byte[] pdfFotos = null;  
						
						ServletContext servletContext = super.getServletContext();  
						String caminhoJasper = servletContext.getRealPath("WEB-INF/fotos_orc/FotosOS.jasper"); 
						
						//Carrega o arquivo com o jasperReport  
						try {  
							jasperReport = (JasperReport) JRLoader.loadObject( caminhoJasper );  
						} catch (Exception jre) {  
							jre.printStackTrace();  
						}	
						
						try {
							Map parametros = new HashMap();  
												
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
							}
							//recuperar o ge_check_in
							
							ClienteBusiness clienteBusiness = new ClienteBusiness();
							ClienteBean clienteBean = clienteBusiness.findDataCliente(codCliente);
							parametros.put("NUM_OS", numeroOS);
							parametros.put("CLIENTE", clienteBean.getRAZSOC());
							parametros.put("MODELO", modelo);
							parametros.put("SERIE", serie);
							parametros.put("TITULO_FOTOS", segmento.getTituloFotos());
							parametros.put("DESCRICAO_FALHA_FOTOS", segmento.getDescricaoFalhaFotos());
							parametros.put("CONCLUSAO_FOTOS", segmento.getConclusaoFotos());
							parametros.put("JOB_CONTROL", jobControl);
							JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(files);					
							
							try {  				
								pdfFotos = JasperRunManager.runReportToPdf( jasperReport, parametros, dataSource);  
							} catch (Exception jre) {  
								jre.printStackTrace();  
							}  
							
							//Parametros para nao fazer cache e o que será exibido..  
							response.setContentType( "application/pdf" );  
							response.setHeader("Content-disposition",
							"attachment; filename=Fotos.pdf" ); 
							
							//Envia para o navegador o pdf..  
							ServletOutputStream servletOutputStream = response.getOutputStream();  
							servletOutputStream.write( pdfFotos);  
							servletOutputStream.flush();  
							servletOutputStream.close();
							img.delete();
							if(imagem != null){
								imagem.delete();						
							}					
							
						} catch (Exception e) {
							e.printStackTrace();
						}			 
					 
					} catch (Exception e) {
						e.printStackTrace();
					}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			try {
				con.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * Obtém nome do arquivo sem sua extensão
	 * @param file
	 * @return
	 */
	private String obterNomeArquivoSemExtensao(File file){
		try {
			String name = file.getName();
			
			String []strings = name.split("\\.");
			if(strings.length > 1 && strings[1].equals("jpg")){
				return strings[0];
			}else{
				return file.getName();
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
		
		return "";
	}

}
