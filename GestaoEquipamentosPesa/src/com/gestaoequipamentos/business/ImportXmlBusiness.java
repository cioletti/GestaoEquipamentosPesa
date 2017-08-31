package com.gestaoequipamentos.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.gestaoequipamentos.bean.DocSegOperBean;
import com.gestaoequipamentos.bean.GeDocSegOperBean;
import com.gestaoequipamentos.bean.PecasBean;
import com.gestaoequipamentos.bean.PrecoBean;
import com.gestaoequipamentos.bean.SegmentoBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.GeDocSegOper;
import com.gestaoequipamentos.entity.GeOperacao;
import com.gestaoequipamentos.entity.GePecas;
import com.gestaoequipamentos.entity.GePecasLog;
import com.gestaoequipamentos.entity.GeSegmento;
import com.gestaoequipamentos.entity.TwFuncionario;
import com.gestaoequipamentos.util.JpaUtil;

public class ImportXmlBusiness {
	
	private UsuarioBean usuarioBean;

	public ImportXmlBusiness() {
		// TODO Auto-generated constructor stub
	}
	
	public ImportXmlBusiness(UsuarioBean bean) {
		this.usuarioBean = bean;
	}
	public Long salvarXml(byte[] bytesArquivo, Long idSegmento, Long idOper, Long idDocSegOper, String idFuncionario) {
		try {
			File xml = File.createTempFile(""+new Date().getTime(), ".xml", new File("."));
			FileOutputStream outPut = new FileOutputStream(xml);  
			outPut.write(bytesArquivo);  
			outPut.flush();
			outPut.close();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
			DocumentBuilder db;
			List<PecasBean> messageList = new ArrayList<PecasBean>();
			try{
				db = dbf.newDocumentBuilder();
				Document doc = db.parse( xml );  
				Element elem = doc.getDocumentElement();  
				// pega todos os elementos usuario do XML  

				NodeList nl = elem.getElementsByTagName( "HEADER" ); 
				Element tagMessage = (Element) nl.item( 0 ); 

				String USERID = getChildTagValue( tagMessage, "USERID" ); 
				//headerEl = (Element)messageChidrenList.item(0);
				String SERIALNO = getChildTagValue( tagMessage, "SERIALNO" ); 

				nl = elem.getElementsByTagName( "PARTS" ); 
				// percorre cada elemento usuario encontrado  
				//EntityManager manager = JpaUtil.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				//manager.getTransaction().begin();
				tagMessage = (Element) nl.item( 0 );  
				NodeList messageChidrenList = tagMessage.getChildNodes();
				for( int i=0; i<messageChidrenList.getLength(); i++ ) {  

					PecasBean bean = new PecasBean();
					Element headerEl = (Element)messageChidrenList.item(i);
					bean.setPartNo(getChildTagValue( headerEl, "PARTNO" ));   
					bean.setPartName(getChildTagValue( headerEl, "PARTNAME" )); 
					bean.setQtd(getChildTagValue( headerEl, "QUANTITY" )); 
					bean.setGroupNumber(getChildTagValue( headerEl, "GROUPNO" )); 
					bean.setGroupName(getChildTagValue( headerEl, "GROUPNAME" )); 
					bean.setSmcsCode(getChildTagValue( headerEl, "SMCSCODE" )); 			        
					bean.setReferenceNo(getChildTagValue( headerEl, "REFERENCENO" )); 	
					bean.setUserId(USERID);
					bean.setSos("000");
					bean.setSerialNo(SERIALNO);
					messageList.add(bean);
				}  
				xml.delete();
				Long idDocSegOperPk = this.savePecas(messageList, idSegmento, idOper, idDocSegOper, idFuncionario);
				this.savePecasLog(idDocSegOperPk, messageList);
				return idDocSegOperPk;
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}  
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public Long salvarCsv(byte[] bytesArquivo, Long idModelo, Long idPrefixo) {
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			File csv = File.createTempFile(""+new Date().getTime(), ".csv", new File("."));
			FileOutputStream outPut = new FileOutputStream(csv);  
			outPut.write(bytesArquivo);  
			outPut.flush();
			outPut.close();
			PrecoBusiness business = new PrecoBusiness(null);
			BufferedReader reader = new BufferedReader(new FileReader(csv));
			String thisLine = "";
			int count = 1; 

			String horas = "";
			while ((thisLine = reader.readLine()) != null) { // while loop begins here
				String [] aux = thisLine.split("\",\"");
				PrecoBean bean = new PrecoBean();
				bean.setIdModelo(idModelo);
				bean.setIdPrefixo(idPrefixo);
				if(count == 2){
					bean.setCompCode(aux[4].replace("\"", "")); 
					Query query = manager.createNativeQuery("select cptcdd from cptcd where cptcd =:cptcd");
					query.setParameter("cptcd", bean.getCompCode());
					List<String> list = query.getResultList();
					if(list.size() > 0){
						bean.setDescricaoCompCode(list.get(0));
					}					
					bean.setJobCode(aux[5].replace("\"", ""));
					query = manager.createNativeQuery("select jbcdds from jbcd where jbcd =:jbcd");
					query.setParameter("jbcd", bean.getJobCode());
					list = query.getResultList();
					if(list.size() > 0){
						bean.setDescricaoJobCode(list.get(0));
					}
					bean.setIdComplexidade(Long.valueOf(aux[7].replace("\"", "")));
					horas = aux[12].replace("\"", "");
					String [] horasAux = horas.split(",");
					Integer minutos = Integer.valueOf(horasAux[1]);
					horas = horasAux[0]+"."+((minutos < 10)?"0"+minutos:minutos);
					bean.setQtdHoras(horas);
					business.saveOrUpdate(bean);
				}else if(count > 2){
					bean.setCompCode(aux[27].replace("\"", "")); 
					Query query = manager.createNativeQuery("select cptcdd from cptcd where cptcd =:cptcd");
					query.setParameter("cptcd", bean.getCompCode());
					List<String> list = query.getResultList();
					if(list.size() > 0){
						bean.setDescricaoCompCode(list.get(0));
					}
					bean.setJobCode(aux[28].replace("\"", ""));
					query = manager.createNativeQuery("select jbcdds from jbcd where jbcd =:jbcd");
					query.setParameter("jbcd", bean.getJobCode());
					list = query.getResultList();
					if(list.size() > 0){
						bean.setDescricaoJobCode(list.get(0));
					}
					bean.setIdComplexidade(Long.valueOf(aux[30].replace("\"", "")));
					horas = aux[35].replace("\"", "");
					String [] horasAux = horas.split(",");
					Integer minutos = Integer.valueOf(horasAux[1]);
					bean.setQtdHoras(horasAux[0]+"."+((minutos < 10)?"0"+minutos:minutos));
					business.saveOrUpdate(bean);
				}
				count++;
			} // end
			csv.delete();

			return 1L;	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}
	public Boolean removerPecas(Long idDocSegOper){ // REMOVER PELO ID 
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			
			Query query = manager.createQuery("from GePecas where idDocSegOper.id =:idDocSegOper");
			query.setParameter("idDocSegOper", idDocSegOper);
			List<GePecas> pecasList = query.getResultList();
			String SQL = "";
			for (GePecas gePecas : pecasList) {
				//SQL = "INSERT INTO GE_REMOVER_PECA_LOG  (PART_NUMBER,PART_NAME ,ID_DOC_SEG_OPER ,ID_FUNCIONARIO, DATA) VALUES ('"+gePecas.getPartNo()+"','"+gePecas.getPartName()+"',"+idDocSegOper+",'"+this.usuarioBean.getMatricula()+"', GETDATE())";
				SQL = "INSERT INTO GE_REMOVER_PECA_LOG  (PART_NUMBER,PART_NAME ,ID_DOC_SEG_OPER ,ID_FUNCIONARIO, DATA, ID_SEGMENTO, NUMERO_SEGEMENTO) VALUES ('"+gePecas.getPartNo()+"','"+gePecas.getPartName()+"',"+idDocSegOper+",'"+this.usuarioBean.getMatricula()+"', GETDATE(),"+gePecas.getIdDocSegOper().getIdSegmento().getId()+",'"+gePecas.getIdDocSegOper().getIdSegmento().getNumeroSegmento()+"')";
				query = manager.createNativeQuery(SQL);
				manager.getTransaction().begin();
				query.executeUpdate();
				manager.getTransaction().commit();
			}
			
			manager.getTransaction().begin();
			query = manager.createNativeQuery("delete from ge_doc_seg_oper where id = "+idDocSegOper);
			query.executeUpdate();
			//query = manager.createNativeQuery("delete from GE_PECAS_LOG where ID_DOC_SEG_OPER = "+idDocSegOper);
			//query.executeUpdate();
			manager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}
	public Boolean removerPecasSegmento(Long idSegmento){ // REMOVER PELO ID 
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			
			Query query = manager.createQuery("from GePecas p, GeDocSegOper op where op.idSegmento.id =:idSegmento and p.idDocSegOper.id = op.id");
			query.setParameter("idSegmento", idSegmento);
			List<Object []> pecasList = query.getResultList();
			String SQL = "";
			for (Object [] pair : pecasList) {
				
				GePecas gePecas = (GePecas)pair[0];
				//SQL = "INSERT INTO GE_REMOVER_PECA_LOG  (PART_NUMBER,PART_NAME ,ID_DOC_SEG_OPER ,ID_FUNCIONARIO, DATA) VALUES ('"+gePecas.getPartNo()+"','"+gePecas.getPartName()+"',"+idDocSegOper+",'"+this.usuarioBean.getMatricula()+"', GETDATE())";
				SQL = "INSERT INTO GE_REMOVER_PECA_LOG  (PART_NUMBER,PART_NAME ,ID_DOC_SEG_OPER ,ID_FUNCIONARIO, DATA, ID_SEGMENTO, NUMERO_SEGEMENTO) VALUES ('"+gePecas.getPartNo()+"','"+gePecas.getPartName()+"',"+gePecas.getIdDocSegOper().getId()+",'"+this.usuarioBean.getMatricula()+"', GETDATE(),"+gePecas.getIdDocSegOper().getIdSegmento().getId()+",'"+gePecas.getIdDocSegOper().getIdSegmento().getNumeroSegmento()+"')";
				query = manager.createNativeQuery(SQL);
				manager.getTransaction().begin();
				query.executeUpdate();
				manager.getTransaction().commit();
			}
			
			manager.getTransaction().begin();
			query = manager.createNativeQuery("delete from ge_doc_seg_oper where id_segmento = "+idSegmento);
			query.executeUpdate();
			//query = manager.createNativeQuery("delete from GE_PECAS_LOG where ID_DOC_SEG_OPER = "+idDocSegOper);
			//query.executeUpdate();
			manager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}

	private Long savePecas(List<PecasBean> list, Long idSegmento, Long idOper, Long idDocSegOper, String idFuncionario){//salvar peças
		//this.removerPecas(idDocSegOper);//deletar where idOper
		GeDocSegOper geDocSegOper = null;			
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			geDocSegOper = manager.find(GeDocSegOper.class, idDocSegOper);
			if(geDocSegOper == null){
				geDocSegOper = new GeDocSegOper();
			}
			GeSegmento segmento = manager.find(GeSegmento.class, idSegmento);
			GeOperacao operacao = null;
			if(idOper.intValue() > 0){
				operacao = manager.find(GeOperacao.class, idOper);
			}
			geDocSegOper.setDataCriacao(new Date());
			geDocSegOper.setIdOperacao(operacao);
			geDocSegOper.setIdSegmento(segmento);
			geDocSegOper.setIdFuncionarioPecas(idFuncionario);
			manager.persist(geDocSegOper);			

			for (PecasBean pecasBean : list) {
				GePecas pecas = new GePecas();
				pecas.setIdDocSegOper(geDocSegOper);
				pecasBean.toBean(pecas);
				manager.persist(pecas);			
			}
			manager.getTransaction().commit();	
			return geDocSegOper.getId();
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}
	
	/**
	 * Salva o log das peças importadas para o sistema
	 * @param list
	 * @param idSegmento
	 * @param idOper
	 * @param idDocSegOper
	 */
	private void savePecasLog(Long idDocSegOperPk, List<PecasBean> list){//salvar peças
		//this.removerPecas(idDocSegOper);//deletar where idOper
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("from GePecas where idDocSegOper.id =:idDocSegOper");
			query.setParameter("idDocSegOper", idDocSegOperPk);
			List<GePecas> gePecaslist = query.getResultList();
			GePecas gePecas = (GePecas)gePecaslist.get(0);
			
			for (PecasBean pecasBean : list) {
				GePecasLog pecas = new GePecasLog();
				
				pecas.setPartNo(pecasBean.getPartNo());
				pecas.setPartName(pecasBean.getPartName());
				//pecas.setId(getId());
				pecas.setQtd(Long.valueOf(pecasBean.getQtd()));
				pecas.setGroupNumber(pecasBean.getGroupNumber());
				pecas.setReferenceNo(pecasBean.getReferenceNo());
				pecas.setSmcsCode(pecasBean.getSmcsCode());
				pecas.setGroupName(pecasBean.getGroupName());
				pecas.setUserId(pecasBean.getUserId());
				pecas.setSos1(pecasBean.getSos());
				pecas.setIdDocSegOper(idDocSegOperPk);
				pecas.setIdFuncionario(this.usuarioBean.getMatricula());
				pecas.setDate(new Date());
				pecas.setNumSegmento(gePecas.getIdDocSegOper().getIdSegmento().getNumeroSegmento());
				pecas.setNumeroOs(gePecas.getIdDocSegOper().getIdSegmento().getIdCheckin().getNumeroOs());
				//pecas.setIdPecaOriginal(pecasBean.getId());
				manager.persist(pecas);			
			}
			manager.getTransaction().commit();	
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}
	public Boolean savePecasAvulsoDocSeg (PecasBean bean, Long idSegmento, Long idOper, String idFuncionarioPecas){
		GeDocSegOper geDocSegOper = new GeDocSegOper();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeSegmento segmento = manager.find(GeSegmento.class, idSegmento);
			GeOperacao operacao = null;
			if(idOper != null){
				operacao = manager.find(GeOperacao.class, idOper);
			}
			geDocSegOper.setIdFuncionarioPecas(idFuncionarioPecas);
			geDocSegOper.setDataCriacao(new Date());
			geDocSegOper.setIdOperacao(operacao);
			geDocSegOper.setIdSegmento(segmento);
			manager.persist(geDocSegOper);	
			GePecas pecas = new GePecas();
			pecas.setIdDocSegOper(geDocSegOper);
			bean.toBean(pecas);
			manager.persist(pecas);
			//log do sistema para saber se as que foi o usuário que imporou as pecas
			GePecasLog pecasLog = new GePecasLog();
			pecasLog.setIdDocSegOper(geDocSegOper.getId());
			bean.toBean(pecasLog);
			pecasLog.setIdFuncionario(this.usuarioBean.getMatricula());
			pecasLog.setDate(new Date());
			pecasLog.setNumeroOs(segmento.getIdCheckin().getNumeroOs());
			pecasLog.setNumSegmento(segmento.getNumeroSegmento());
			pecasLog.setIdPecaOriginal(pecas.getId());
			manager.merge(pecasLog);
			manager.getTransaction().commit();
			return true;
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return false;
	}
		public Long savePecasAvulsoIdDoc (PecasBean bean, Long idSegmento, Long idOper, String idFuncionarioPecas){
			GeDocSegOper geDocSegOper = new GeDocSegOper();
			EntityManager manager = null;
			try {
				manager = JpaUtil.getInstance();
				manager.getTransaction().begin();
				GeSegmento segmento = manager.find(GeSegmento.class, idSegmento);
				GeOperacao operacao = null;
				if(idOper != null){
					operacao = manager.find(GeOperacao.class, idOper);
				}
				geDocSegOper.setIdFuncionarioPecas(idFuncionarioPecas);
				geDocSegOper.setDataCriacao(new Date());
				geDocSegOper.setIdOperacao(operacao);
				geDocSegOper.setIdSegmento(segmento);
				manager.persist(geDocSegOper);	
				GePecas pecas = new GePecas();
				pecas.setIdDocSegOper(geDocSegOper);
				bean.toBean(pecas);
				manager.persist(pecas);
				//log do sistema para saber se as que foi o usuário que imporou as pecas
				GePecasLog pecasLog = new GePecasLog();
				pecasLog.setIdDocSegOper(geDocSegOper.getId());
				bean.toBean(pecasLog);
				pecasLog.setIdFuncionario(this.usuarioBean.getMatricula());
				pecasLog.setDate(new Date());
				pecasLog.setNumeroOs(segmento.getIdCheckin().getNumeroOs());
				pecasLog.setNumSegmento(segmento.getNumeroSegmento());
				pecasLog.setIdPecaOriginal(pecas.getId());
				manager.merge(pecasLog);
				manager.getTransaction().commit();
				return geDocSegOper.getId();
			} catch (Exception e) {
				if(manager != null && manager.getTransaction().isActive()){
					manager.getTransaction().rollback();
				}
				e.printStackTrace();
			} finally {
				if(manager != null && manager.isOpen()){
					manager.close();
				}
			}
			return 0L;
		}
	public String savePecasAvulso (PecasBean bean, Long idDocSegOper, String idFuncionarioPecas)throws Exception {
		EntityManager manager = null;
		GeDocSegOper oper = new GeDocSegOper();
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeDocSegOper docSegOper = manager.find(GeDocSegOper.class, idDocSegOper);
			docSegOper.setIdFuncionarioPecas(idFuncionarioPecas);
			if(docSegOper.getNumDoc() != null){
				return "Não é possivel salvar essa peça pois existe numero de documento-false";
			}
			GePecas pecas = new GePecas();			
			oper = manager.find(GeDocSegOper.class, idDocSegOper);
			//pecas.setIdDocSegOper(idDocSegOper);
			pecas.setPartNo(bean.getPartNo());
			pecas.setPartName(bean.getPartName());
			//pecas.setId(Long.valueOf(bean.getId()));
			pecas.setQtd(Long.valueOf(bean.getQtd()));
			pecas.setGroupNumber(bean.getGroupNumber());
			pecas.setReferenceNo(bean.getReferenceNo());
			pecas.setSmcsCode(bean.getSmcsCode());
			pecas.setGroupName(bean.getGroupName());
			pecas.setUserId(bean.getUserId());
			pecas.setSos1(bean.getSos());
			pecas.setIdDocSegOper(oper);
			manager.persist(pecas);
			
			//log do sistema para saber se as que foi o usuário que imporou as pecas
			GePecasLog pecasLog = new GePecasLog();
			pecasLog.setIdDocSegOper(pecas.getIdDocSegOper().getId());
			bean.toBean(pecasLog);
			pecasLog.setIdFuncionario(this.usuarioBean.getMatricula());
			pecasLog.setDate(new Date());
			pecasLog.setNumeroOs(pecas.getIdDocSegOper().getIdSegmento().getIdCheckin().getNumeroOs());
			pecasLog.setNumSegmento(pecas.getIdDocSegOper().getIdSegmento().getNumeroSegmento());
			pecasLog.setIdPecaOriginal(pecas.getId());
			manager.merge(pecasLog);
			
			
			manager.getTransaction().commit();
			return "Peça salva com sucesso";
		} catch (Exception e) {	
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return "Ocorreu um erro ao salvar a peça-false";
	}
	public List<PecasBean> findAllPecas(Long idDocSegOper){
		List<PecasBean> pecas = new ArrayList<PecasBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From GePecas where idDocSegOper.id = "+idDocSegOper);
			//query.setParameter("idSegmento", idSegmento);
			List<GePecas> result = query.getResultList();
			for(GePecas pecasObj : result){
				PecasBean bean = new PecasBean();
				bean.fromBean(pecasObj);
				TwFuncionario funcionario = manager.find(TwFuncionario.class, pecasObj.getIdDocSegOper().getIdFuncionarioPecas());
				bean.setNomeFuncionario(funcionario.getEplsnm());
				pecas.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return pecas;
	}	
	public List<PecasBean> findAllPecas(Long idSegmento, Long idOperacao, String semDoc){
		List<PecasBean> pecas = new ArrayList<PecasBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String complemento = "";
			String SQL = "from GePecas p where p.idDocSegOper.id in(#) order by p.idDocSegOper.id";
			if(idOperacao != null && idOperacao > 0){
				complemento += "	select dso.id from GeDocSegOper dso where dso.idSegmento.id ="+idSegmento+ "and dso.idOperacao.id ="+idOperacao+" and numDoc is not null";
			}else{
				if(semDoc == null){
					complemento += "	select dso.id from GeDocSegOper dso where dso.idSegmento.id ="+idSegmento+" and numDoc is not null";
				}else{
					complemento += "	select dso.id from GeDocSegOper dso where dso.idSegmento.id ="+idSegmento;
				}
			}
			Query query = manager.createQuery(SQL.replace("#", complemento));
			//query.setParameter("idSegmento", idSegmento);
			List<GePecas> result = query.getResultList();
			//int i = 0;
			String nomeFuncionario = "";
			for(GePecas pecasObj : result){
				PecasBean bean = new PecasBean();

				bean.fromBean(pecasObj);
				TwFuncionario funcionario = manager.find(TwFuncionario.class, pecasObj.getIdDocSegOper().getIdFuncionarioPecas());
				nomeFuncionario = funcionario.getEplsnm();
				bean.setNomeFuncionario(funcionario.getEplsnm());
				pecas.add(bean);
			}

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return pecas;
	}	
	public List<PecasBean> findAllPecasSem00E(Long idSegmento, Long idOperacao){
		List<PecasBean> pecas = new ArrayList<PecasBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String complemento = "";
			String SQL = "from GePecas p where p.idDocSegOper.id in(#) order by p.idDocSegOper.id";
			if(idOperacao != null && idOperacao > 0){
				complemento += "	select dso.id from GeDocSegOper dso where dso.idSegmento.id ="+idSegmento+ "and dso.idOperacao.id ="+idOperacao+" and (numDoc is null or codErroDbs = '99')";
			}else{
				complemento += "	select dso.id from GeDocSegOper dso where dso.idSegmento.id ="+idSegmento+" and (numDoc is null or codErroDbs = '99')";
			}
			Query query = manager.createQuery(SQL.replace("#", complemento));
			//query.setParameter("idSegmento", idSegmento);
			List<GePecas> result = query.getResultList();
			int i = 0;
			String nomeFuncionario = "";
			for(GePecas pecasObj : result){
				PecasBean bean = new PecasBean();
			
				bean.fromBean(pecasObj);
				if(i == 0 && pecasObj.getIdDocSegOper().getIdFuncionarioPecas() != null){
					TwFuncionario funcionario = manager.find(TwFuncionario.class, pecasObj.getIdDocSegOper().getIdFuncionarioPecas());
					nomeFuncionario = funcionario.getEplsnm();
					i++;
				}
				bean.setNomeFuncionario(nomeFuncionario);
				pecas.add(bean);
			}
			
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return pecas;
	}	
	public List<GeDocSegOperBean> findAllNumDocSem00E(Long idSegmento){
		SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		List<GeDocSegOperBean> geDocSegOperList = new ArrayList<GeDocSegOperBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String SQL = "from GeDocSegOper where numDoc is null and idSegmento.id = "+idSegmento;
			
			Query query = manager.createQuery(SQL);
			//query.setParameter("idSegmento", idSegmento);
			List<GeDocSegOper> result = query.getResultList();
			int i = 0;
			for(GeDocSegOper operObj : result){
				GeDocSegOperBean bean = new GeDocSegOperBean();
				bean.setId(operObj.getId());
				TwFuncionario funcionario = manager.find(TwFuncionario.class, operObj.getIdFuncionarioPecas());
				bean.setNomeFuncionario(funcionario.getEplsnm());
				bean.setIdFuncionario(funcionario.getEpidno());
				bean.setDescricao(funcionario.getEplsnm()+" "+dataFormatada.format(operObj.getDataCriacao()));
				geDocSegOperList.add(bean);
			}
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return geDocSegOperList;
	}	
	// este geDocSegOperListrna o conteúdo (texto) de uma tag (elemento)  
	// filho da tag informada como parâmetro. A tag filho a ser pesquisada  
	// é a tag informada pelo nome (string)  
	private static String getChildTagValue( Element elem, String tagName ) throws Exception {  
		NodeList children = elem.getElementsByTagName( tagName );  
		if( children == null ) return null;  
		Element child = (Element) children.item(0);  
		if( child == null || child.getFirstChild() == null) return null;  
		return child.getFirstChild().getNodeValue();  
	}
	//Este método remove uma única peça do Grid de Peças 
	public String removerPeca(PecasBean bean, Long idDocSegOper, String ultimoItem){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeDocSegOper docSegOper = manager.find(GeDocSegOper.class, idDocSegOper);
			if(docSegOper.getNumDoc() != null){
				return "Não é possivel exluir essa peça pois existe numero de documento-false";
			}if(ultimoItem.equals("sim")){
				manager.remove(docSegOper);
			}
			GePecas pecas = manager.find(GePecas.class, Long.valueOf(bean.getId()));
			
			String SQL = "INSERT INTO GE_REMOVER_PECA_LOG  (PART_NUMBER,PART_NAME ,ID_DOC_SEG_OPER ,ID_FUNCIONARIO, DATA, ID_SEGMENTO, NUMERO_SEGEMENTO) VALUES ('"+pecas.getPartNo()+"','"+pecas.getPartName()+"',"+idDocSegOper+",'"+this.usuarioBean.getMatricula()+"', GETDATE(),"+pecas.getIdDocSegOper().getIdSegmento().getId()+",'"+pecas.getIdDocSegOper().getIdSegmento().getNumeroSegmento()+"')";
			Query query = manager.createNativeQuery(SQL);
			query.executeUpdate();
//			Query query = manager.createNativeQuery("delete from GE_PECAS_LOG where id_Peca_Original =:idPecaOriginal");
//			query.setParameter("idPecaOriginal", pecas.getId());
//			query.executeUpdate();
			manager.remove(pecas);
			
			manager.getTransaction().commit();

			return "Peça excluída com sucesso!";
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return "Ocorreu um erro ao exluir a peça-false";
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}
	public Boolean saveResponsavelPecas(SegmentoBean segmentoBean) {		
		EntityManager manager = null;
		
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			
			GeSegmento segmento = manager.find(GeSegmento.class, segmentoBean.getId());
			
			if(segmento != null){
				segmento.setIdFuncionarioPecas(segmentoBean.getIdFuncionarioPecas());
				manager.merge(segmento);				
				manager.getTransaction().commit();
				return true;
			}
			manager.getTransaction().commit();
		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		
		return false;
	}
	public Boolean removerFuncionarioPecas(SegmentoBean segmentoBean) {
		EntityManager manager = null;
		
		try {
			manager = JpaUtil.getInstance();
			
			Query query = manager.createNativeQuery("select * from GE_DOC_SEG_OPER where ID_SEGMENTO = "+segmentoBean.getId());
			if(query.getResultList().size() > 0){
				return true;
			}
			
			manager.getTransaction().begin();
			
			query = manager.createNativeQuery("UPDATE GE_SEGMENTO SET ID_FUNCIONARIO_PECAS = null"+ 
						" WHERE ID = "+segmentoBean.getId());

			query.executeUpdate();
			
			manager.getTransaction().commit();
			return true;

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		
		return false;
	}
	public SegmentoBean findSegmentoBy(Long idSegmento) {
		EntityManager manager = null;

		try {
			manager = JpaUtil.getInstance();

			GeSegmento segmento = manager.find(GeSegmento.class, idSegmento);

			SegmentoBean bean = new SegmentoBean();
			bean.fromBean(segmento);
			
			if(bean.getIdFuncionarioPecas() != null){
				Query query = manager.createNativeQuery("SELECT EPLSNM FROM TW_FUNCIONARIO WHERE EPIDNO = '"+bean.getIdFuncionarioPecas()+"'");
				
				String nomeFuncionarioPecas = (String) query.getSingleResult();
				bean.setNomeFuncionarioPecas(nomeFuncionarioPecas);						
			}			

			return bean;

		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}	
		}
		return null;
	}
	public List<UsuarioBean> findAllFuncionariosByCampoPesquisa(String campoPesquisa) {
		EntityManager manager = null;
		List<UsuarioBean> beans = new ArrayList<UsuarioBean>();
		
		try {
			manager = JpaUtil.getInstance();
			
			Query query = manager.createQuery("FROM TwFuncionario WHERE (EPIDNO LIKE '%"+campoPesquisa+"%' or EPLSNM LIKE '%"+campoPesquisa+"%')" +
					" AND STN1 = "+ Long.valueOf(usuarioBean.getFilial())+" and login is not null");
			
			List<TwFuncionario> list = (List<TwFuncionario>)query.getResultList();
			
			for(TwFuncionario bean : list){
				UsuarioBean objUsuario = new UsuarioBean();				
				objUsuario.setNome(bean.getEplsnm());
				objUsuario.setMatricula(bean.getEpidno());
				objUsuario.setFilial(bean.getStn1());
								
				beans.add(objUsuario);				
			}		
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}	
		}
		return beans;

	}

}
