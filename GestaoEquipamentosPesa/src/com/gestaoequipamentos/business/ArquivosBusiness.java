package com.gestaoequipamentos.business;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import com.gestaoequipamentos.bean.ArquivosBean;
import com.gestaoequipamentos.util.IConstantAccess;
import com.gestaoequipamentos.util.UtilGestaoEquipamentosPesa;


public class ArquivosBusiness {
	
	public Collection<String> findAllArquivos(Long idArquivo) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();		

		Collection<String> listForm = new ArrayList<String>();

		try {
			prop.load(in);
			String dirFotos = prop.getProperty("dir.gestaoArquivos");		
			File dir = new File(dirFotos + idArquivo);

			if (dir.isDirectory()) {
				String[] children = dir.list();

				for (int i = 0; i < children.length; i++) { 
					listForm.add(children[i]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return listForm;
	}
	
	
	public Boolean fazerUploadEmDiretorio(ArquivosBean arquivos, byte[] bytes, String nomeArquivo) throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();

		try {
			prop.load(in);
			String diretorioFotos = prop.getProperty("dir.gestaoArquivos") + arquivos.getId();

			if(!diretorioExiste(diretorioFotos)) {
				criarDiretorioDasFotos(arquivos.getId());
			}

			String caminhoCompletoArquivo = prop.getProperty("dir.gestaoArquivos") + arquivos.getId()+ "/" + UtilGestaoEquipamentosPesa.replaceCaracterEspecial(nomeArquivo);

			File arquivo = new File(caminhoCompletoArquivo);
			FileOutputStream fos = new FileOutputStream(arquivo);

			fos.write(bytes);
			fos.flush();
			fos.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	private Boolean diretorioExiste(String dir) {
		if ((new File(dir)).exists()) {
			return true;
		}
		return false;
	}
	
	private boolean criarDiretorioDasFotos(Long idFoto) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();
		
		try {
			prop.load(in);
			String dirFotos = prop.getProperty("dir.gestaoArquivos");
			File dir = new File(dirFotos + idFoto);
			Boolean criou = dir.mkdirs();
			
			return criou;

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		
		File file = new File("c:/teste/foto/");
		if(file.isDirectory()){
			System.out.println("Diretório existe");
		}else{
			System.out.println(file.mkdirs());
		}		
		
	}
	
	public boolean removerArquivoDaBibliografia(Long idArquivo, String nomeArquivo) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();

		try {
			prop.load(in);
			String dirFotos = prop.getProperty("dir.gestaoArquivos");

			File dir = new File(dirFotos + idArquivo);

			deleteFile(dir, nomeArquivo);
			return true;

		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}	
	
	private void deleteFile(File dir, String nomeArquivo) {
		try {
			File file = new File(dir, nomeArquivo);
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	public String findCaminhoArquivos(Long idFoto){
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();
		try {
			prop.load(in);
			String caminho = prop.getProperty("dir.gestaoArquivos");
			return caminho+=idFoto;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
