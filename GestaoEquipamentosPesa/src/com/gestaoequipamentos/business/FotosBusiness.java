package com.gestaoequipamentos.business;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.ChekinBean;
import com.gestaoequipamentos.bean.FotosBean;
import com.gestaoequipamentos.bean.SegmentoBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.GeFotoOrcamentista;
import com.gestaoequipamentos.util.IConstantAccess;
import com.gestaoequipamentos.util.JpaUtil;
import com.gestaoequipamentos.util.UtilGestaoEquipamentosPesa;


public class FotosBusiness {
	private UsuarioBean usuarioBean;
	public FotosBusiness(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	
	public Collection<String> findAllArquivos(Long idFoto) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();		

		Collection<String> listForm = new ArrayList<String>();

		try {
			prop.load(in);
			String dirFotos = prop.getProperty("dir.gestao");		
			File dir = new File(dirFotos + idFoto);

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
	
	
	public Boolean fazerUploadEmDiretorio(FotosBean fotos, byte[] bytes, String nomeArquivo) throws Exception {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();

		try {
			prop.load(in);
			String diretorioFotos = prop.getProperty("dir.gestao") + fotos.getId();

			if(!diretorioExiste(diretorioFotos)) {
				criarDiretorioDasFotos(fotos.getId());
			}

			String caminhoCompletoArquivo = prop.getProperty("dir.gestao") + fotos.getId()+ "/" + UtilGestaoEquipamentosPesa.replaceCaracterEspecial(nomeArquivo);

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
	public Boolean fazerUploadFotoOrcamentista(FotosBean fotos, byte[] bytes, String nomeArquivo) throws Exception {
		int len;
		String query;
		PreparedStatement pstmt = null;
		Connection conn = null;
		try
		{
			conn = com.gestaoequipamentos.util.Connection.getConnection();
			File file = File.createTempFile(nomeArquivo, ".jpg");
			FileOutputStream fos = new FileOutputStream(file);

			fos.write(bytes);
			fos.flush();
			fos.close();
			FileInputStream fis = new FileInputStream(file);
			len = (int)file.length();

			query = ("insert into GE_FOTO_ORCAMENTISTA (NUMERO_OS, OBS, DATA, ID_FUNCIONARIO,ID_CHECK_IN,JOB_CONTROL, NOME_ARQUIVO, FOTO) VALUES(?,?,?,?,?,?,?,?)");
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1,fotos.getNumeroOs());
			pstmt.setString(2, fotos.getObservacao());
			pstmt.setDate(3, new java.sql.Date(new Date().getTime()));
			pstmt.setString(4, this.usuarioBean.getMatricula());
			pstmt.setLong(5, fotos.getIdCheckin());
			pstmt.setString(6,fotos.getJobControl());
			pstmt.setString(7,file.getName());

			// Method used to insert a stream of bytes
			pstmt.setBinaryStream(8, fis, len); 
			pstmt.executeUpdate();
			file.delete();
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}finally{
			try {
				conn.close();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			String dirFotos = prop.getProperty("dir.gestao");
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
	
	public boolean removerArquivoDaBibliografia(Long idfoto, String nomeArquivo) {
		InputStream in = getClass().getClassLoader().getResourceAsStream(IConstantAccess.CONF_FILE);
		Properties prop = new Properties();

		try {
			prop.load(in);
			String dirFotos = prop.getProperty("dir.gestao");

			File dir = new File(dirFotos + idfoto);

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
			String caminho = prop.getProperty("dir.gestao");
			return caminho+=idFoto;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public List<FotosBean> findAllArquivosOcamentista(String numeroOs, String jobControl) {
		EntityManager manager = null;
		List<FotosBean> fotoList = new ArrayList<FotosBean>();
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("from GeFotoOrcamentista where numeroOs =:numeroOs and jobControl =:jobControl");
			query.setParameter("jobControl", jobControl);
			query.setParameter("numeroOs", numeroOs);
			List<GeFotoOrcamentista> fotos = (List<GeFotoOrcamentista>)query.getResultList();
			for(GeFotoOrcamentista orc : fotos){
				FotosBean bean = new FotosBean();
				bean.setId(orc.getId());
				bean.setIdCheckin(orc.getIdCheckIn());
				bean.setJobControl(orc.getJobControl());
				bean.setNumeroOs(orc.getNumeroOs());
				bean.setObservacao(orc.getObs());
				bean.setNomeArquivo(orc.getNomeArquivo());
				fotoList.add(bean);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}

		return fotoList;
	}
	
	public boolean removerFotoOrcamentista(Long idfoto) {
		EntityManager manager = null;
		
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeFotoOrcamentista fotoOrcamentista = manager.find(GeFotoOrcamentista.class, idfoto);
			manager.remove(fotoOrcamentista);
			manager.getTransaction().commit();
			return true;
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}	
	public boolean editarFotoOrcamentista(FotosBean bean) {
		EntityManager manager = null;
		
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeFotoOrcamentista fotoOrcamentista = manager.find(GeFotoOrcamentista.class, bean.getId());
			fotoOrcamentista.setObs(bean.getObservacao());
			manager.merge(fotoOrcamentista);
			manager.getTransaction().commit();
			return true;
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}	
	
	public boolean salvarDetalhesFotos(ChekinBean checkinBean, String jobControl, String tituloFotos, String descricaoFalhaFotos, String conclusaoFotos) {
		EntityManager manager = null;
		
		try {
			String SQL = "update GE_SEGMENTO set TITULO_FOTOS = '"+tituloFotos+"', DESCRICAO_FALHA_FOTOS = '"+descricaoFalhaFotos+"', CONCLUSAO_FOTOS = '"+conclusaoFotos+"', FUNCIONARIO_FOTOS = '"+this.usuarioBean.getMatricula()+"'"+
						"	where ID_CHECKIN = "+checkinBean.getId()+
						"	and JOB_CONTROL = '"+jobControl+"'";
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery(SQL);
			query.executeUpdate();
			manager.getTransaction().commit();
			return true;
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}	
	
	public SegmentoBean findDetalhesFotos(ChekinBean checkinBean, String jobControl){
		EntityManager manager = null;

		SegmentoBean bean = new SegmentoBean();
		try {
			String SQL = "select distinct convert(varchar(4000), TITULO_FOTOS) TITULO_FOTOS, convert(varchar(4000),DESCRICAO_FALHA_FOTOS) DESCRICAO_FALHA_FOTOS, convert(varchar(4000),CONCLUSAO_FOTOS) CONCLUSAO_FOTOS from GE_SEGMENTO where ID_CHECKIN = "+checkinBean.getId()+" and JOB_CONTROL = '"+jobControl+"'";
			manager = JpaUtil.getInstance();

			Query query = manager.createNativeQuery(SQL);
			Object[] pair = (Object[])query.getSingleResult();
			bean.setTituloFotos((String)pair[0]);
			bean.setDescricaoFalhaFotos((String)pair[1]);
			bean.setConclusaoFotos((String)pair[2]);
			return bean;

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		}finally{ 
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return bean;
	}

}
