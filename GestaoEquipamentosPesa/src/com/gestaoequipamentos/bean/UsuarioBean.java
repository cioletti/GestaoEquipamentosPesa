package com.gestaoequipamentos.bean;

import java.io.Serializable;
import java.util.List;

import com.gestaoequipamentos.entity.TwFuncionario;



public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = -6498621729773118526L;
	private int idUsuario;
	private List<SistemaBean> sistemaList;
	private int perfilIdPerfil;
	private String siglaPerfil;
	private String nome;
    private String login;
    private String senha;
    private String matricula;
    private String msg;
    private String menu;
    private String campo;
    private String filial;
    private String filialStr;
    private String siglaFilial;
    private String email;
    private String telefone;
    private Long idSistema;
    private Long idPerfil;
    private Boolean isAdm;
    private String jobControl;
    private String estimateBy;
    private Boolean isSelected;
    private Long turno;
    private Long idFornecedor;
    //private List<AgendamentoBean> agendamentoList;
    private List<CentroDeCustoBean> centroDeCustoList;
    public String getFilial() {
		return filial;
	}
	public void setFilial(String filial) {
		this.filial = filial;
	}
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getPerfilIdPerfil() {
		return perfilIdPerfil;
	}
	public void setPerfilIdPerfil(int perfilIdPerfil) {
		this.perfilIdPerfil = perfilIdPerfil;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
//	public List<CentroDeCustoBean> getCentroDeCustoList() {
//		return centroDeCustoList;
//	}
//	public void setCentroDeCustoList(List<CentroDeCustoBean> centroDeCustoList) {
//		this.centroDeCustoList = centroDeCustoList;
//	}
	public String getSiglaPerfil() {
		return siglaPerfil;
	}
	public void setSiglaPerfil(String siglaPerfil) {
		this.siglaPerfil = siglaPerfil;
	}
//	public void fromBean(Usuario usuario){
//		setPerfilIdPerfil(usuario.getUsuarioPK().getPerfilIdPerfil());
//		setLogin(usuario.getUsuarioPK().getMatricula());
//		setNome(usuario.getNome());
//		setMatricula(usuario.getUsuarioPK().getMatricula());
//		setCampo(usuario.getCampo());
//		setFilial(usuario.getFilial());
//		//setCentroDeCustoList(CentroDeCustoBean.fromBean(usuario.getCentroDeCustoCollection()));
//		
//		
//	}
//	public Usuario toBean(UsuarioBean usuario){
//		Usuario usu = new Usuario();
//		//usu.setLogin(usuario.getLogin());
//		usu.setNome(usuario.getNome().toUpperCase());
//		usu.getUsuarioPK().setMatricula(usuario.getMatricula().toUpperCase());
//		usu.getUsuarioPK().setPerfilIdPerfil(usuario.getPerfilIdPerfil());
//		usu.setSenha(usuario.getSenha());
//		//usu.setAdm(usuario.getAdm());
//		return usu;
//		
//	}
//	public List<AgendamentoBean> getAgendamentoList() {
//		return agendamentoList;
//	}
//	public void setAgendamentoList(List<AgendamentoBean> agendamentoList) {
//		this.agendamentoList = agendamentoList;
//	}
	public String getFilialStr() {
		return filialStr;
	}
	public void setFilialStr(String filialStr) {
		this.filialStr = filialStr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelefone() {
		return telefone;
	}
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	public Long getIdSistema() {
		return idSistema;
	}
	public void setIdSistema(Long idSistema) {
		this.idSistema = idSistema;
	}
	public String getJobControl() {
		return jobControl;
	}
	public void setJobControl(String jobControl) {
		this.jobControl = jobControl;
	}
	public Long getIdPerfil() {
		return idPerfil;
	}
	public void setIdPerfil(Long idPerfil) {
		this.idPerfil = idPerfil;
	}
	public List<SistemaBean> getSistemaList() {
		return sistemaList;
	}
	public void setSistemaList(List<SistemaBean> sistemaList) {
		this.sistemaList = sistemaList;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
	public Boolean getIsAdm() {
		return isAdm;
	}
	public void setIsAdm(Boolean isAdm) {
		this.isAdm = isAdm;
		
	}	
	public String getSiglaFilial() {
		return siglaFilial;
	}
	public void setSiglaFilial(String siglaFilial) {
		this.siglaFilial = siglaFilial;
	}
	public String getEstimateBy() {
		return estimateBy;
	}
	public void setEstimateBy(String estimateBy) {
		this.estimateBy = estimateBy;
	}
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}
	public Long getTurno() {
		return turno;
	}
	public void setTurno(Long turno) {
		this.turno = turno;
	}

	public Long getIdFornecedor() {
		return idFornecedor;
	}
	public void setIdFornecedor(Long idFornecedor) {
		this.idFornecedor = idFornecedor;
	}
	public void fromBean(TwFuncionario funcionario){
		setLogin(funcionario.getEplsnm());
		setMatricula(funcionario.getEpidno());
	}
	public void toBean(TwFuncionario funcionario){
		funcionario.setLogin(funcionario.getEplsnm());
	}
}
