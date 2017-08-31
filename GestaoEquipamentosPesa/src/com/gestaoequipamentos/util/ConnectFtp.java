package com.gestaoequipamentos.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.sql.BLOB;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;



public class ConnectFtp {

	
	private static FTPClient ftp;
	private Properties prop;
	public ConnectFtp() {
		try {   
			ftp = new FTPClient();   
			prop = new Properties();
			//File  file = new File(IConstantAccess.CONF_FILE);
			
			InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
			prop.load(in);
			in.close();
			ftp.connect(prop.getProperty("provedor.ftp"));
			//verifica se conectou com sucesso!   
			if( FTPReply.isPositiveCompletion( ftp.getReplyCode() ) ) {   
				ftp.login( prop.getProperty("usuario.ftp"), prop.getProperty("senha.ftp") );   
			} else {   
				//erro ao se conectar   
				ftp.disconnect();   
				System.out.println("Conex�o recusada");   
				//System.exit(1);  
			}
		} catch( Exception e ) {   
			System.out.println("Ocorreu um erro: "+e);   
			//System.exit(1);   
		}      
	}
	
	public void setWorkDir(String workDir){
		try {
			ftp.changeWorkingDirectory(workDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	static void copiar(File fonte, File destino) throws IOException{
        InputStream in = new FileInputStream(fonte);
        OutputStream out = new FileOutputStream(destino);
    
        byte[] buf = new byte[1024];
        int len;
        while((len = in.read(buf)) > 0){
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
	
	
	
	/**
	 * Envia uma lista de arquivoc para um ftp
	 * @param urlFiles
	 */
	public  void setFilesFtp(File [] urlFiles){
		String nomeArquivo = null;   
        try {   
            //para cada arquivo informado...   
            for( int i=0; i<urlFiles.length; i++ ) {   
                //abre um stream com o arquivo a ser enviado   
                InputStream is = new FileInputStream(urlFiles[i] );   
                //pega apenas o nome do arquivo   
                int idx = urlFiles[i].getName().lastIndexOf(File.separator);   
                if( idx < 0 ) idx = 0;   
                else idx++;   
                nomeArquivo = urlFiles[i].getName().substring( idx, urlFiles[i].getName().length() );   
                   
                //ajusta o tipo do arquivo a ser enviado   
                if( urlFiles[i].getName().endsWith(".txt") ) {   
                    ftp.setFileType( FTPClient.ASCII_FILE_TYPE );   
                }  
                System.out.println("Enviando arquivo "+nomeArquivo+"...");   
                   
                //faz o envio do arquivo   
                ftp.storeFile( nomeArquivo, is );   
                System.out.println("Arquivo "+nomeArquivo+" enviado com sucesso!");   
            }   
               
            ftp.disconnect();   
            System.out.println("Fim. Tchau!");   
        } catch( Exception e ) {   
            System.out.println("Ocorreu um erro: "+e);   
            System.exit(1);   
        }   

	}
	
	/**
	 * 
	 * @return
	 */
	public void sendFileFtp(File fileRemote){
		try {
			FTPClient ftpLocal = new FTPClient();   
			try {   
				prop = new Properties();
				//File  fileConf = new File(IConstantAccess.CONF_FILE);
				
				InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
				prop.load(in);
				in.close();
				ftpLocal.connect(prop.getProperty("provedor.ftp"));
				//verifica se conectou com sucesso!   
				if( FTPReply.isPositiveCompletion( ftp.getReplyCode() ) ) {   
					ftpLocal.login( prop.getProperty("usuario.ftp"), prop.getProperty("senha.ftp") );   
				} else {   
					//erro ao se conectar   
					ftpLocal.disconnect();   
					System.out.println("Conex�o recusada");   
					System.exit(1);  
				}
				ftpLocal.changeWorkingDirectory(prop.getProperty("palm.retorno.ftp"));
			} catch( Exception e ) {   
				System.out.println("Ocorreu um erro: "+e);   
				System.exit(1);   
			}   
			
			ftpLocal.setFileType( FTPClient.BINARY_FILE_TYPE );

			BufferedReader reader = new BufferedReader(new FileReader(fileRemote));
			String contentFile = "";
			String line = "";
			while ((line = reader.readLine()) != null) {
				contentFile+= line+"\n";
			}
			
			byte buf[] = contentFile.getBytes(); 

			//FileInputStream inputStream = new FileInputStream(file);
			
			ByteArrayInputStream inputStream = new ByteArrayInputStream(buf);

			ftpLocal.storeFile(fileRemote.getName(), inputStream );
			inputStream.close();
			ftpLocal.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}  

	}
	public void sendFile(File fileRemote){
		try {
			InputStream in = new ConectionDbs().getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
			Properties prop = new Properties();
			prop.load(in);
			String dirFile = prop.getProperty("file.proposta");
			File file = new File(dirFile+"\\"+fileRemote);
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}  
		
	}
	
//	public void readUpdateSendOS(){
//
//		try {
//			CronOS.scheduler.standby();
//			String filename = "";
//			DateFormat dateArq = new SimpleDateFormat("yyyy-MM-dd");
//			
//			OSBusiness business = new OSBusiness();
//			OSDataBusiness dataBusiness = new OSDataBusiness();
//			setWorkDir(prop.getProperty("palm.retorno.ftp"));
//			FTPFile [] files = ftp.listFiles();
//			for (FTPFile ftpFile : files) {
//
//
//				filename = ftpFile.getName();
//				//	String  tipo = filename.substring(filename.lastIndexOf('.') + 1);
//				if( filename.endsWith(".BMP") ){
//					File file = File.createTempFile(ftpFile.getName().substring(0, ftpFile.getName().length()-4), ".bmp");
//					ftp.retrieveFile(ftpFile.getName(), new FileOutputStream(file));
//
//					Collection<SearchBean> searchList = business.findOS(file.getName().substring(2, filename.lastIndexOf('.')));
//					for (SearchBean searchBean : searchList) {
//						this.inserirAssinatura(searchBean.getNumberOS(), file);
//					}
//					String prefixo = dateArq.format(new Date());
//					File bk = new File(prop.getProperty("registro.bk")+ prefixo+'_'+file.getName());
//					copiar(file, bk);
//				}else
//					if( filename.endsWith(".txt") ) {   
//
//						File file = File.createTempFile(ftpFile.getName().substring(0, ftpFile.getName().length()-4), ".txt");
//						ftp.retrieveFile(ftpFile.getName(), new FileOutputStream(file));
//						String line = "";
//
//						BufferedReader reader = new BufferedReader(new FileReader(file));
//						String numeroOs = null;
//						String tecnicoOs = null;
//						String idOs = null;
//						String nomeExport19 = null;
//						String nomeExport18 = null;
//						
//
//						while ((line = reader.readLine()) != null) {
//							String [] id = line.split("[|]");
//							int registryType = Integer.valueOf(id[0]);
//							DateFormat dateNumber = new SimpleDateFormat("ddMMyyhhmmss");
//							numeroOs = id[id.length - 2];
//							tecnicoOs = id[id.length - 1];
//							nomeExport19 = id[0]+ tecnicoOs +"2"+ dateNumber.format(new Date()) + ".txt";
//							nomeExport18 = id[0]+ tecnicoOs + dateNumber.format(new Date()) + ".txt";
//							
//							if(numeroOs != null && tecnicoOs != null ){
//								SearchBean searchBean = business.findOSTechinical(numeroOs, tecnicoOs);
//								if(searchBean != null){
//									idOs = searchBean.getIdOs().toString();
//								}else{
//									idOs = null;
//								}
//							}
//							if(idOs != null){
//
//								switch (registryType) {
//								case 6:
//									dataBusiness.updateOs(id, 6);
//									break;
//								case 7:
//									AcessoriosBean acessorios = new AcessoriosBean();
//									Acessorios acess = acessorios.toBean(id);
//									acess.setFkDbOsA(Long.valueOf(idOs));
//									dataBusiness.saveAcessorios(acess);
//									break;
//								case 8:
//									PecasBean pecasBean = new PecasBean();
//									Pecas pecas = pecasBean.toBean(id);
//									pecas.setFkDbOsP(Long.valueOf(idOs));
//									dataBusiness.savePecas(pecas);
//									copiaregistroDir(file, prop.getProperty("registro.08")+ nomeExport18);
//									break;
//								case 9:
//									dataBusiness.updateOs(id, 9);
//									copiaregistroDir(file, prop.getProperty("registro.09")+ nomeExport18);
//									break;
//									
//								case 14:
//								//	0   1      2      3       4     5      6  7 8 9   10  11  12     13
//								//	14|301674|PAL|14/10/2010|15:00|CN37444|98||4|MOV|ZAOA|1|CN37444|ZAOA
//									
//									if( business.findOSHoras(id[3], idOs,id[11]) != null){
//										dataBusiness.updateHoras(id,idOs,id[11]);
//									}else{
//										HorasBean horasBean = new HorasBean();
//										Horas horas = horasBean.toBean(id);
//										horas.setFkDbOsH(Long.valueOf(idOs));
//										dataBusiness.saveHoras(horas);
//									}
//									copiaregistroDir(file, prop.getProperty("registro.14")+ nomeExport19);
//									break;
//								case 15:
//									String data = id[3].substring(0,2)+"/"+ id[3].substring(2,4)+"/20" +id[3].substring(4);
//									if( business.findOSHoras(data, idOs,id[11]) != null){
//										dataBusiness.updateHoras(id,idOs,id[11]);
//									}else{
//										HorasBean horasBean = new HorasBean();
//										Horas horas = horasBean.toBean(id);
//										horas.setFkDbOsH(Long.valueOf(idOs));
//										dataBusiness.saveHoras(horas);
//									}
//									copiaregistroDir(file, prop.getProperty("registro.15")+ nomeExport19);
//									
//									
//									
//									break;
//
//								case 16:
//									//String datakm = id[3].substring(0,2)+"/"+ id[3].substring(2,4)+"/20" +id[3].substring(4);
//								 	dataBusiness.updateOs(id, 16);
//								//	if( business.findOSHoras(datakm, idOs,id[10]) != null){
////										dataBusiness.updateHoras(id,idOs,id[10]);
////									}else{
////
////
////										HorasBean horasBean = new HorasBean();
////										Horas horas = horasBean.toBean(id);
////										horas.setFkDbOsH(Long.valueOf(idOs));
////										dataBusiness.saveHoras(horas);
//								 	///renato
//								 	
////									}
//								 	
//									copiaregistroDir(file, prop.getProperty("registro.16")+ nomeExport18);
//									
//									break;
//
//								case 18:
//									dataBusiness.updateOs(id, 18);
//									break;
//								case 19:
//									dataBusiness.updateOs(id, 19);
//									break;
//								case 20:
//									dataBusiness.updateOs(id, 20);
//									break;
//								case 21:
//									dataBusiness.updateOs(id, 21);
//									break;
//
//								}
//							}
//						}
//						String prefixo = dateArq.format(new Date());
//						copiaregistroDir(file,prop.getProperty("registro.bk")+ prefixo+'_'+file.getName());
//						
//						
//					}
//				
//				 ftp.deleteFile(filename);
//			}
//			ftp.disconnect();
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			CronOS.scheduler.start();
//		} catch (SchedulerException e) {
//			e.printStackTrace();
//		}
//	}

	private void copiaregistroDir(File file, String nomeExport) {
		try {
			File Arquivo = new File(nomeExport);
			copiar(file, Arquivo);
		} catch (Exception e) {
		
		}
	}

	public static void main(String[] args) {
		
	//	DateFormat dateNumber = new SimpleDateFormat("ddMMyyyyhhmmss");
	//	String 	linha  = dateNumber.format(new Date());
	//	System.out.print(linha);

		//linha = linha.substring(0,2)+":"+ linha.substring(2);
	//linha = linha.substring(0,2)+"/"+ linha.substring(2,4)+"/20" +linha.substring(4);
//	System.out.println(linha);
		
//		Connection conn = null;
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//
//
//			String url = "jdbc:oracle:thin:@//gordiano.ddns.com.br:1521/XE";
//
//
//			conn = DriverManager.getConnection(url,"sotreq","fuchur");
//
//			Statement stmt = conn.createStatement(); 
//			// Open a stream to read the Blob data
//			 ResultSet rs = stmt.executeQuery("select assinatura from db_os where number_os = 'CN38319'"); 
//			 rs.next();
//			 Blob blob = rs.getBlob("assinatura");
//			InputStream blobStream = blob.getBinaryStream();
//
//			File file = File.createTempFile("assinarura", ".bmp");
//			// Open a file stream to save the Blob data
//			FileOutputStream fileOutStream = new FileOutputStream(file);
//
//			// buffer holding bytes to be transferred
//			byte[] buffer = new byte[10];
//			int nbytes = 0; // Number of bytes read
//
//
//			// Read from the Blob data input stream, and write to the
//			// file output stream
//			while((nbytes = blobStream.read(buffer)) != -1) //Read from Blob stream
//				fileOutStream.write(buffer, 0, nbytes); // Write to file stream
//			fileOutStream.flush();
//			fileOutStream.close();
//		} catch (Exception e)  
//		{  
//			e.printStackTrace();  
//		} finally{
//			try {
//				conn.close();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//
//		}  
	}

	private void inserirAssinatura(String numberOs, File assinatura) {
		Connection conn = null;
		ResultSet rs = null;
		InputStream bin = null;
		OutputStream bout = null;
		Statement stmt = null;
		try { 
			InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
			Properties prop = new Properties();
			prop.load(in);
			Class.forName(prop.getProperty("driver.oracle"));
			String url = prop.getProperty("url.oracle");
			conn = DriverManager.getConnection(url,prop.getProperty("login.oracle"),prop.getProperty("password.oracle"));
			conn.setAutoCommit(true);
			stmt = conn.createStatement();  
		    stmt.execute("update db_os set assinatura = empty_blob() where number_os ='"+numberOs+"'");  
		  
		   
		    rs = stmt.executeQuery("select assinatura from db_os where number_os = '"+numberOs+"' for update");  
			
		    while(rs.next()){ 
		    	// copia arquivo para campo blob  
		    	Blob blob = rs.getBlob("assinatura");  

		    	byte[] bbuf = new byte[1024];  
		    	bin = new FileInputStream(assinatura);  
		    	bout = ((BLOB) blob).getBinaryOutputStream(); // espec�fico driver oracle  
		    	// cout = clob.setCharacterStream(0);  
		    	int bytesRead = 0;  
		    	while ((bytesRead = bin.read(bbuf)) != -1) {  
		    		bout.write(bbuf, 0, bytesRead);  
		    	}  
		    	
		    }
		} catch (Exception e)  
		    {  
		      e.printStackTrace();  
		    } finally{
		    	try {
		    		try {
		    			stmt.close();
						bin.close();
						bout.close();
					} catch (IOException e) {
						e.printStackTrace();
					}  
					rs.close();
		    		conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					
				}
		    	
		    }
	}

}

