package com.gestaoequipamentos.controller;

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import com.gestaoequipamentos.bean.ClienteBean;
import com.gestaoequipamentos.bean.FilialBean;
import com.gestaoequipamentos.bean.FotosBean;
import com.gestaoequipamentos.bean.RelatorioFotosBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.business.ClienteBusiness;
import com.gestaoequipamentos.business.ClienteInterBusiness;
import com.gestaoequipamentos.business.FotosBusiness;

import flex.messaging.io.ArrayList;


/**
 * Servlet implementation class FotosCheckin
 */
public class FotosCheckin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FotosCheckin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pastaFotos = request.getParameter("pastaFotos");
		String numOS = "";
		if(request.getParameter("numOS") != null){
			numOS = (request.getParameter("numOS").equals("null")) ? "" : request.getParameter("numOS");
		}
		//String cliente = (request.getParameter("cliente").equals("null") ? "" : request.getParameter("cliente"));
		String codCliente = "";
		if(request.getParameter("codCliente") != null){
			codCliente = (request.getParameter("codCliente").equals("null") ? "" : request.getParameter("codCliente"));
		}
		String modelo = "";
		if(request.getParameter("modelo") != null){
			modelo = (request.getParameter("modelo").equals("null") ? "" : request.getParameter("modelo"));
		}
		String serie = "";
		if(request.getParameter("serie") != null){
			serie = (request.getParameter("serie").equals("null") ? "" : request.getParameter("serie"));
		}
		String descricaoArquivo = request.getParameter("descricaoArquivo");

		if(pastaFotos != null){
			
			try {
		    UsuarioBean bean = (UsuarioBean)request.getSession().getAttribute("usuario");
			List<File> fileList = new ArrayList();	
			Long idFoto = Long.valueOf(pastaFotos);
			FotosBusiness business = new FotosBusiness(bean);
			RelatorioFotosBean relatorioFotosBean;
			OutputStream stream;
			byte bufeer[];
			String caminhoFotos = business.findCaminhoArquivos(idFoto);
			String pathFoto;
			Integer length;
			InputStream input;
			File imagem = null;
			List<RelatorioFotosBean> files = new ArrayList();
			
			if(descricaoArquivo != null){
				pathFoto = caminhoFotos+"/"+descricaoArquivo;
				fileList.add(new File(pathFoto));
			}else{
				Collection<String> fotosList = business.findAllArquivos(idFoto);
				Iterator<String> it = fotosList.iterator();

				while (it.hasNext()) {  
					pathFoto = caminhoFotos +"/"+ it.next();
					fileList.add(new File(pathFoto));
				}
			}
			// c:\teste\fotos\2\ALIENWARE.jpg\boxshot_us_large__16844_zoom.jpg
			 
			 JasperReport jasperReport = null;  
				byte[] pdfFotos = null;  
				
				ServletContext servletContext = super.getServletContext();  
				String caminhoJasper = servletContext.getRealPath("WEB-INF/fotos_os/FotosOS.jasper"); 
				
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
						
						int aux = 2;
						int count = 0;
						if(fileList.size()-i < 2){
							aux = mod;
						}
						
						for(int j = i; j< aux+i; j++){
							switch (count) {
							case 0:
								input = new FileInputStream(fileList.get(j));
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
								relatorioFotosBean.setDescricao1(this.obterNomeArquivoSemExtensao(fileList.get(j)));							
								break;
							case 1:
								input = new FileInputStream(fileList.get(j));
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
								relatorioFotosBean.setDescricao2(this.obterNomeArquivoSemExtensao(fileList.get(j)));
								break;						
							default:
								break;
							}
							count++;
						}
						i += aux - 1;
						files.add(relatorioFotosBean);
					}
					ClienteBusiness clienteBusiness = new ClienteBusiness();
					ClienteBean clienteBean = clienteBusiness.findDataCliente(codCliente);
					parametros.put("NUM_OS", numOS);
					parametros.put("CLIENTE", clienteBean.getRAZSOC());
					parametros.put("MODELO", modelo);
					parametros.put("SERIE", serie);
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
	
//	public static void main(String[] args) throws IOException {
//		File file = new File("c:/teste/fotos/2/boxshot_us_large__16844_zoom.jpg");
//		
//		
//		String name = file.getName();
//		
//		
//		String []strings = name.split("\\.");
//		if(strings.length > 1 && strings[1].equals("jpg")){
//			System.out.println(strings[0]);
//		}else{
//			System.out.println(file.getName());
//		}
////		String regex = "[\\w\\-]{4}$";
////		System.out.println(Pattern.matches(regex, file.getName()));
////		
////		for (String string : strings){
////			System.out.println(string);
////		}
//		
//		
//		
//		//System.out.println(name);
////
////		 int tam = 10;
////	        int retorno = 0;
////	        int inicio = 0;
////	        int fim = tam;
////	        int length = (int)file.length();
////		try {
////			InputStream stream = new FileInputStream(file);
////			byte bytes[] = new byte[(int)file.length()];
////			//System.out.println("true");
////			byte buf[]=new byte[1024];
////			 while (  (retorno = stream.read(buf)) > 0 ) {
////	                System.out.println("de " + inicio + " ate " +  fim);
////	                
////	                // aqui o inicio é aumentado pra onde parou da ultima vez...
////	                // o fim é aumentado para mais "10" depois so inicio
////	                inicio += retorno;
////	                fim = inicio + tam;
////	               
////	            }
////	            
////	            // mostra como ficou a ultima iteracao
////	        
////		} catch (FileNotFoundException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//		
//		int i = 0;
//		String a = String.valueOf(i);
//		System.out.println(a);
//		
//		
//	}

}
