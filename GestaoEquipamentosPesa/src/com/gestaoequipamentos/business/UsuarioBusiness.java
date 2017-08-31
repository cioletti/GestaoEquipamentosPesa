package com.gestaoequipamentos.business;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.gestaoequipamentos.bean.PerfilBean;
import com.gestaoequipamentos.bean.SistemaBean;
import com.gestaoequipamentos.bean.UsuarioBean;
import com.gestaoequipamentos.entity.AdmPerfilSistemaUsuario;
import com.gestaoequipamentos.entity.TwFilial;
import com.gestaoequipamentos.entity.TwFuncionario;
import com.gestaoequipamentos.util.JpaUtil;

public class UsuarioBusiness {
	private UsuarioBean usuarioBean;
	public UsuarioBusiness(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	
	public UsuarioBusiness() {
	}
	
    private static final String EJBQL_ALTER_PASSWORD = "From TwFuncionario where login =:login and senha =:senha";
	public UsuarioBean loginUsuario(String login, String senha, String url){
		EntityManager manager = null;
		try {
			
				try {
					manager = JpaUtil.getInstance();

					Query query = manager.createQuery("From TwFuncionario u where u.login=:login and u.senha=:senha");
					query.setParameter("login", login);
					query.setParameter("senha", senha);
					TwFuncionario usuario = (TwFuncionario)query.getSingleResult();

					Query query2 = manager.createQuery("From TwFilial f where f.stno=:stno");
					query2.setParameter("stno", Long.valueOf(usuario.getStn1()));
					TwFilial filial = (TwFilial)query2.getSingleResult();

					UsuarioBean usuarioBean = new UsuarioBean();
					usuarioBean.setEmail(usuario.getEmail());
					usuarioBean.setFilial((Integer.valueOf(usuario.getStn1()) < 10)?"0"+usuario.getStn1():usuario.getStn1());
					usuarioBean.setMatricula(usuario.getEpidno());
					usuarioBean.setTelefone(usuario.getTelefone());
					usuarioBean.setLogin(usuario.getLogin());
					usuarioBean.setSenha(usuario.getSenha());
					usuarioBean.setNome(usuario.getEplsnm().toUpperCase());
					usuarioBean.setSiglaFilial(filial.getSigla());
					usuarioBean.setEstimateBy(usuario.getEstimateBy());
					usuarioBean.setTurno(usuario.getTurno());
					usuarioBean.setIdFornecedor(usuario.getIdFornecedor());
					query = manager.createQuery("From AdmPerfilSistemaUsuario where idTwUsuario.epidno = '"+usuario.getEpidno().toUpperCase()+"'");
					List<AdmPerfilSistemaUsuario> sistemaUsuario = query.getResultList();
					usuarioBean.setIsAdm(Boolean.FALSE);
					for (AdmPerfilSistemaUsuario perfilSistemaUsuario : sistemaUsuario) {
						if(perfilSistemaUsuario.getIdSistema().getSigla().equals("GE")){
							usuarioBean.setSiglaPerfil(perfilSistemaUsuario.getIdPerfil().getSigla());
							if(perfilSistemaUsuario.getIdPerfil().getSigla().equals("ADM")){
								usuarioBean.setIsAdm(Boolean.TRUE);
							}else if(perfilSistemaUsuario.getIdPerfil().getSigla().equals("SUPER")){
								usuarioBean.setJobControl(perfilSistemaUsuario.getJobControl());
							}
						}
					}

					query = manager.createQuery("From AdmPerfilSistemaUsuario where idTwUsuario.epidno=:epidno");
					query.setParameter("epidno", usuarioBean.getMatricula());
					List<AdmPerfilSistemaUsuario> admPerfilSistemaUsuarioList = query.getResultList();
					List<SistemaBean> sistemaList = new ArrayList<SistemaBean>();
					for (AdmPerfilSistemaUsuario admPerfilSistemaUsuario : admPerfilSistemaUsuarioList) {
						SistemaBean bean = new SistemaBean();
						bean.setDescricao(admPerfilSistemaUsuario.getIdSistema().getDescricao());
						bean.setId(admPerfilSistemaUsuario.getIdSistema().getId().intValue());
						bean.setSigla(admPerfilSistemaUsuario.getIdSistema().getSigla());
						bean.setDescricaoPerfil(admPerfilSistemaUsuario.getIdPerfil().getDescricao());
						bean.setContext(admPerfilSistemaUsuario.getIdSistema().getContext());
						bean.setImg(admPerfilSistemaUsuario.getIdSistema().getImg());
						bean.setUrl(url+"/"+admPerfilSistemaUsuario.getIdSistema().getContext());
						bean.setJobControl(admPerfilSistemaUsuario.getJobControl());
						PerfilBean perfilBean = new PerfilBean();
						perfilBean.setDescricao(admPerfilSistemaUsuario.getIdPerfil().getDescricao());
						perfilBean.setId(admPerfilSistemaUsuario.getIdPerfil().getId().intValue());
						perfilBean.setSigla(admPerfilSistemaUsuario.getIdPerfil().getSigla());
						bean.setPerfilBean(perfilBean);
						sistemaList.add(bean);				
					}
					usuarioBean.setSistemaList(sistemaList);
					
					return usuarioBean;
					
				}catch (NoResultException e) {
    			
    			}catch (Exception e1) {
    				e1.printStackTrace();
    			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return null;
	}
	
	public String alterPassword(String login, String senhaAntiga, String senhaAtual){
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			manager.getTransaction().begin();
			
			Query query = manager.createQuery(EJBQL_ALTER_PASSWORD);
			query.setParameter("login", login);
			query.setParameter("senha", senhaAntiga);
			
			TwFuncionario usuario = (TwFuncionario)query.getSingleResult();
			usuario.setSenha(senhaAtual);
			manager.getTransaction().commit();
			return "Senha Alterada com sucesso!";
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
	
	public List<UsuarioBean> findAllConsultorUsuario(Long idCheckIn, String tipoCliente){
		List<UsuarioBean> result = new ArrayList<UsuarioBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String SQL = "";
			if("INT".equals(tipoCliente)){
				SQL = "select f.EPIDNO, f.EPLSNM,"+
				"	(select cp.id  from GE_CONSULTOR_PROPOSTA cp where cp.id_epidno = f.EPIDNO and cp.ID_CHECK_IN =:ID_CHECK_IN) id, f.email, fi.STNM"+
				"	from tw_funcionario f, ADM_PERFIL_SISTEMA_USUARIO apsu, TW_FILIAL fi"+
				"	where apsu.ID_PERFIL in (select id from ADM_PERFIL where SIGLA in ('GAOS') and TIPO_SISTEMA = 'GE')"+
				"	and f.EPIDNO = apsu.ID_TW_USUARIO" +
				"  and fi.STNO = f.STN1" +
				"  order by fi.STNM, f.EPLSNM";
			}else{
				SQL = "select f.EPIDNO, f.EPLSNM,"+
				"	(select cp.id  from GE_CONSULTOR_PROPOSTA cp where cp.id_epidno = f.EPIDNO and cp.ID_CHECK_IN =:ID_CHECK_IN) id, f.email, fi.STNM"+
				"	from tw_funcionario f, ADM_PERFIL_SISTEMA_USUARIO apsu, TW_FILIAL fi"+
				"	where apsu.ID_PERFIL in (select id from ADM_PERFIL where SIGLA in ('CONS') and TIPO_SISTEMA = 'GE')"+
				"	and f.EPIDNO = apsu.ID_TW_USUARIO" +
				"  and fi.STNO = f.STN1" +
				"  order by fi.STNM, f.EPLSNM";
			}
			Query query = manager.createNativeQuery(SQL);
			query.setParameter("ID_CHECK_IN", idCheckIn);
			List<Object[]> listObj = (List<Object[]>)query.getResultList();
			for (Object[] objects : listObj) {
				UsuarioBean bean = new UsuarioBean();
				bean.setMatricula((String)objects[0]);
				bean.setNome((String)objects[1]+" - "+(String)objects[4]);
				bean.setIsSelected(((BigDecimal)objects[2] != null)?true:false);
				bean.setEmail((String)objects[3]);
				result.add(bean);
			}
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}
	public List<UsuarioBean> findAllOrcamentistaUsuario(){
		List<UsuarioBean> result = new ArrayList<UsuarioBean>();
		EntityManager manager = null;
		try {
			manager = JpaUtil.getInstance();
			String SQL = "select f.EPIDNO, f.EPLSNM+' |<-->| '+per.DESCRICAO from TW_FUNCIONARIO f, ADM_PERFIL_SISTEMA_USUARIO adm, ADM_PERFIL per"+
			"	where adm.ID_TW_USUARIO = f.EPIDNO"+
			"	and adm.ID_PERFIL in (select id from adm_perfil where sigla in ('ORC', 'ADM', 'ORCA', 'GAOS', 'GERENT', 'GEGAR')  and TIPO_SISTEMA = 'GE')"+
			"	and f.STN1 = "+Integer.valueOf(usuarioBean.getFilial())+
			"	and per.ID = adm.ID_PERFIL "+
			"	order by f.EPLSNM";	
			Query query = manager.createNativeQuery(SQL);
			List<Object[]> listObj = (List<Object[]>)query.getResultList();
			for (Object[] objects : listObj) {
				UsuarioBean bean = new UsuarioBean();
				bean.setMatricula((String)objects[0]);
				bean.setNome((String)objects[1]);
				result.add(bean);
			}
		} catch (Exception e) {	
			e.printStackTrace();
		} finally {
			if(manager != null && manager.isOpen()){
				manager.close();
			}
		}
		return result;
	}


}
