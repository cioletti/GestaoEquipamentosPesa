package com.gestaoequipamentos.business;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.PrecoBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.GeArvInspecao;
import com.gestaoequipamentos.entity.GeComplexidade;
import com.gestaoequipamentos.entity.GeOrcamento;
import com.gestaoequipamentos.entity.GePreco;
import com.gestaoequipamentos.entity.GePrefixo;
import com.gestaoequipamentos.util.IConstantAccess;
import com.gestaoequipamentos.util.JpaUtil;

public class PrecoBusiness {

   private UsuarioBean usuarioBean;

   public PrecoBusiness(UsuarioBean usuarioBean) {
	   this.usuarioBean = usuarioBean;
   }
   public List<PrecoBean> findAllPreco(){
	   List<PrecoBean> preco = new ArrayList<PrecoBean>();
	   EntityManager manager = null;
	   try {
			manager = JpaUtil.getInstance(); 

			Query query = manager.createQuery("From GePreco"); 
			List<GePreco> result = query.getResultList();
			for (GePreco precoObj : result) { 
				PrecoBean bean = new PrecoBean();
				bean.fromBean(precoObj);
				preco.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return preco;
	}
	public List<PrecoBean> findPrecoModPre(Long idPrefixo,Long idModelo){
		List<PrecoBean> preco = new ArrayList<PrecoBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			
			Query query = manager.createQuery("From GePreco where idModelo = '"+idModelo +"'"+ "and idPrefixo = '" + idPrefixo+"'");			
			List<GePreco> result = query.getResultList();
			for (GePreco precoObj : result) { 
				PrecoBean bean = new PrecoBean();
				bean.fromBean(precoObj);
				preco.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return preco;
	}
	public List<PrecoBean> clonarPreco(Long idModeloOrigem,Long idPrefixoOrigem,Long idModeloDestino,Long idPrefixoDestino){
		List<PrecoBean> preco = new ArrayList<PrecoBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createQuery("From GePreco where idModelo = '"+idModeloOrigem +"'"+ "and idPrefixo = '" + idPrefixoOrigem+"'");			
			List<GePreco> result = query.getResultList();
			GeArvInspecao modelo = manager.find(GeArvInspecao.class, idModeloDestino);
			GePrefixo prefixo = manager.find(GePrefixo.class, idPrefixoDestino);
			for (GePreco precoObj : result) { 
				GePreco gePreco = new GePreco();
				gePreco.setIdComplexidade(precoObj.getIdComplexidade());
				gePreco.setIdModelo(modelo);
				gePreco.setIdPrefixo(prefixo);
				gePreco.setJobCode(precoObj.getJobCode());
				gePreco.setCompCode(precoObj.getCompCode());
				gePreco.setQtdHoras(precoObj.getQtdHoras());
				gePreco.setDescricaoCompCode(precoObj.getDescricaoCompCode());
				gePreco.setDescricaoJobCode(precoObj.getDescricaoJobCode());
				gePreco.setDescricaoServico(precoObj.getDescricaoServico());
				
				manager.persist(gePreco);
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
		return preco;
	}
	public PrecoBean clonarPrecoIndividual(PrecoBean precoOrigem, Long idModeloDestino,Long idPrefixoDestino){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			GeArvInspecao modelo = manager.find(GeArvInspecao.class, idModeloDestino);
			GePrefixo prefixo = manager.find(GePrefixo.class, idPrefixoDestino);
			GeComplexidade complexidade = manager.find(GeComplexidade.class, precoOrigem.getIdComplexidade());

				GePreco gePreco = new GePreco();
				gePreco.setIdComplexidade(complexidade);
				gePreco.setIdModelo(modelo);
				gePreco.setIdPrefixo(prefixo);
				gePreco.setJobCode(precoOrigem.getJobCode());
				gePreco.setCompCode(precoOrigem.getDescricaoCompCode().split("-")[0].trim());
				gePreco.setQtdHoras(precoOrigem.getQtdHoras());
				gePreco.setDescricaoCompCode(precoOrigem.getDescricaoCompCode().split("-")[1].trim());
				gePreco.setDescricaoJobCode(precoOrigem.getDescricaoJobCode());
				gePreco.setDescricaoServico(precoOrigem.getDescricaoServico());
				
				manager.persist(gePreco);
			
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
		return precoOrigem;
	}
	
	public Boolean saveOrUpdate(PrecoBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("select count(*) from ge_preco p where p.id_modelo = "+bean.getIdModelo()+" and p.id_prefixo = "+bean.getIdPrefixo()+" and p.job_code = '"+bean.getJobCode()+"' and p.comp_code = '"+bean.getCompCode()+"'");
			Integer total = (Integer)query.getSingleResult();
			
			if(total.intValue() > 0 && (bean.getId() == null || bean.getId() == 0)){
				manager.getTransaction().commit();
				return false;
			}
			
			GePreco preco = new GePreco();
			if(bean.getId() != null && bean.getId() > 0){
				preco = manager.find(GePreco.class, bean.getId());
				bean.toBean(preco);
				preco.setIdModelo(manager.find(GeArvInspecao.class, bean.getIdModelo()));
				preco.setIdPrefixo(manager.find(GePrefixo.class, bean.getIdPrefixo()));
				preco.setIdComplexidade(manager.find(GeComplexidade.class, bean.getIdComplexidade()));
				preco.setDescricaoServico(bean.getDescricaoServico());
			}else{
				bean.toBean(preco);
				preco.setIdModelo(manager.find(GeArvInspecao.class, bean.getIdModelo()));
				preco.setIdPrefixo(manager.find(GePrefixo.class, bean.getIdPrefixo()));
				preco.setIdComplexidade(manager.find(GeComplexidade.class, bean.getIdComplexidade()));
				preco.setDescricaoServico(bean.getDescricaoServico());
			}
			
			manager.merge(preco);
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
	public Boolean removerCadPreco (PrecoBean bean){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("delete from ge_preco where id = "+bean.getId());
			query.executeUpdate();
			manager.getTransaction().commit();				

		} catch (Exception e) {
			if(manager != null && manager.getTransaction().isActive()){
				manager.getTransaction().rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return true;		
	}
	public List<PrecoBean>findAllOrcamento(PrecoBean bean){
		List<PrecoBean> list = new ArrayList<PrecoBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createQuery("From GePreco where idPrefixo = '" + bean.getIdPrefixo() +"'" + "and idModelo = '" + bean.getIdModelo()+"'" );

			List<GePreco> result = query.getResultList();
			for (GePreco precoBean : result){
				PrecoBean preco = new PrecoBean();
				preco.fromBean(precoBean);
				list.add(preco);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return list;
	}

	public BigDecimal findFatorCliente(String codCliente){
		EntityManager manager = null;

//		try {
//			manager = JpaUtil.getInstance();
////
////			//recupera o fator do segmento o cliente do DBS			
//			manager.getTransaction().begin();
//			String SQL = "select crm.cust_codigosegmento from MME.vw_crmsegmentoscontas_ds crm where  crm.cuno = '"+codCliente+"'";
//			Query query = manager.createNativeQuery(SQL);
//			if(query.getResultList().size() > 0){
//				String codigoSegmento = (String)query.getResultList().get(0);
//				query = manager.createNativeQuery("select cli.fator  from ge_segmento_cliente cli where cli.codigo =:codigo order by cli.id desc");
//				query.setParameter("codigo", codigoSegmento.trim());
//				if(query.getResultList().size() > 0){
//					return (BigDecimal)query.getResultList().get(0);
//				}else{
//					return null;
//				}
//			}else{
//				return null;
//			}
////			return BigDecimal.valueOf(1.2);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}finally{
//			if(manager != null && manager.isOpen()){
//				manager.close();				
//			}
//
//		}
		Connection con = null;
		Statement statement = null;
		try {
			manager = JpaUtil.getInstance();
			String SQL = "select ic.IDCD01 from "+IConstantAccess.LIB_DBS+".SCPDIVF0 ic	where ic.CUNO = '"+codCliente+"' and ic.IDCD01 is not null and trim(ic.IDCD01) <> ''";
			con = com.gestaoequipamentos.util.ConectionDbs.getConnecton();
			statement = con.createStatement();
			ResultSet rs = statement.executeQuery(SQL);
			
			if(rs.next()){
				String codigoSegmento = rs.getString("IDCD01");
					Query query = manager.createNativeQuery("select cli.fator  from ge_segmento_cliente cli where cli.codigo =:codigo order by cli.id desc");
					query.setParameter("codigo", codigoSegmento.trim());
					if(query.getResultList().size() > 0){
						return(BigDecimal)query.getResultList().get(0);
					}			  
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
			try {
				if(con != null){
					con.close();
					statement.close();
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public BigDecimal findValorVenda (Long idModelo, String prefixo){
		//BigDecimal valorVenda;
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select valor_de_venda from ge_prefixo where descricao ='"+ prefixo+"'"+"and id_modelo ="+idModelo);
			return (BigDecimal)query.getSingleResult();					

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}
	public BigDecimal findFatorUrgencia(){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select fator_urgencia from ge_fator");
			return (BigDecimal) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
	}
	public BigDecimal findComplexidade(String complexidade){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			Query query = manager.createNativeQuery("select fator from ge_complexidade where descricao ='"+complexidade+"'");
			return (BigDecimal) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}

	}
	public Boolean gerarCalculo(PrecoBean bean){
		
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			Query query = manager.createNativeQuery("delete from ge_orcamento where id_funcionario =:idFuncionario ");
			query.setParameter("idFuncionario", this.usuarioBean.getMatricula());
			query.executeUpdate();
			manager.getTransaction().commit();
			BigDecimal fatorCliente = findFatorCliente(bean.getCodCliente());
			//BigDecimal fatorCliente = BigDecimal.valueOf(5.555); //TODO: Apagar linha (Teste) 
			BigDecimal valorVenda = findValorVenda (bean.getIdModelo(), bean.getPrefixoStr());
			BigDecimal urgencia;
			//BigDecimal resultado = BigDecimal.ZERO;
			if(bean.getIsUrgente().equals("S")){
				urgencia = findFatorUrgencia();
			}else{
				urgencia = BigDecimal.valueOf(1L);
			}
			for (PrecoBean precoBean : bean.getOrcamentoList()){	
				manager.getTransaction().begin();
				GeOrcamento orcamento = new GeOrcamento();
				orcamento.setCompCode(precoBean.getCompCode());
				orcamento.setJobCode(precoBean.getJobCode());
				orcamento.setIdFuncionario(this.usuarioBean.getMatricula());
				orcamento.setQtd(precoBean.getQtdItens().longValue());
				orcamento.setIdPreco(precoBean.getId());
				orcamento.setValorUnitario(valorVenda.multiply(fatorCliente).multiply(urgencia).multiply(findComplexidade(precoBean.getComplexidadeStr())).multiply(BigDecimal.valueOf(Double.valueOf(precoBean.getQtdHoras()))).multiply(BigDecimal.valueOf(Long.valueOf(precoBean.getQtdItens()))));
				//resultado = resultado.add(valorVenda.multiply(fatorCliente).multiply(urgencia).multiply(findComplexidade(precoBean.getComplexidadeStr())).multiply(BigDecimal.valueOf(Double.valueOf(precoBean.getQtdHoras()))).multiply(BigDecimal.valueOf(Long.valueOf(precoBean.getQtdItens()))));
				manager.persist(orcamento);
				manager.getTransaction().commit();
			}
			return true;
			//return idPreco.substring(0, idPreco.length()-1);			
			//return (String.valueOf(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,###,##0.00", Double.valueOf(String.valueOf(resultado)))));
		} catch (NumberFormatException e) {
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
	public String gerarCalculoUnitario(PrecoBean bean){
		//List<PrecoBean> precoBeanList = new ArrayList<PrecoBean>();			
		String valor = "";
		try {
			BigDecimal fatorCliente = findFatorCliente(bean.getCodCliente());
			//BigDecimal fatorCliente = BigDecimal.valueOf(5.555); //TODO: Apagar linha (Teste) 
			BigDecimal valorVenda = findValorVenda (bean.getIdModelo(), bean.getPrefixoStr());
			BigDecimal urgencia;
			BigDecimal resultado = BigDecimal.ZERO;
			if(bean.getIsUrgente().equals("S")){
				urgencia = findFatorUrgencia();
			}else{
				urgencia = BigDecimal.valueOf(1L);
			}
			for (PrecoBean precoBean : bean.getOrcamentoList()){					
				resultado = (valorVenda.multiply(fatorCliente).multiply(urgencia).multiply(findComplexidade(precoBean.getComplexidadeStr())).multiply(BigDecimal.valueOf(Double.valueOf(precoBean.getQtdHoras()))).multiply(BigDecimal.valueOf(Long.valueOf(precoBean.getQtdItens()))));
				valor = valor + (String.valueOf(com.gestaoequipamentos.util.ValorMonetarioHelper.formata("###,###,##0.00", Double.valueOf(String.valueOf(resultado)))))+";";
				
				//precoBeanList.add(precoBean);
			} //.toString()+","
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valor;
		
	}


}
