package com.gestaoequipamentos.bean;

import com.gestaoequipamentos.entity.GePecas;
import com.gestaoequipamentos.entity.GePecas00E;
import com.gestaoequipamentos.entity.GePecasLog;
import com.gestaoequipamentos.entity.GeRemoverPecaLog;

public class PecasBean {
	
	private String partNo;
	private String partName;
	private String id;
	private String qtd;
	private String groupNumber;
	private String referenceNo;
	private String smcsCode;
	private String groupName;
	private String userId;
	private String serialNo;
	private String sos;
	private Long idDocSegOper;
	private String coderr;
	private String descerr;
	private String nomeFuncionario;
	private String numDoc;
	
	public String getCoderr() {
		return coderr;
	}
	public void setCoderr(String coderr) {
		this.coderr = coderr;
	}
	public String getDescerr() {
		return descerr;
	}
	public void setDescerr(String descerr) {
		this.descerr = descerr;
	}
	public String getPartNo() {
		return partNo;
	}
	public void setPartNo(String partNo) {
		this.partNo = partNo;
	}
	public String getPartName() {
		return partName;
	}
	public void setPartName(String partName) {
		this.partName = partName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQtd() {
		return qtd;
	}
	public void setQtd(String qtd) {
		this.qtd = qtd;
	}
	public String getGroupNumber() {
		return groupNumber;
	}
	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}
	public String getSmcsCode() {
		return smcsCode;
	}
	public void setSmcsCode(String smcsCode) {
		this.smcsCode = smcsCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getSos() {
		return sos;
	}
	public void setSos(String sos) {
		this.sos = sos;
	}
	public Long getIdDocSegOper() {
		return idDocSegOper;
	}
	public void setIdDocSegOper(Long idDocSegOper) {
		this.idDocSegOper = idDocSegOper;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getNomeFuncionario() {
		return nomeFuncionario;
	}
	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}
	public String getNumDoc() {
		return numDoc;
	}
	public void setNumDoc(String numDoc) {
		this.numDoc = numDoc;
	}
	public void toBean(GePecas entity){
		entity.setPartNo(getPartNo());
		entity.setPartName(getPartName());
		entity.setId(entity.getId());
		entity.setQtd(Long.valueOf(getQtd()));
		entity.setGroupNumber(getGroupNumber());
		entity.setReferenceNo(getReferenceNo());
		entity.setSmcsCode(getSmcsCode());
		entity.setGroupName(getGroupName());
		entity.setUserId(getUserId());
		entity.setSos1(getSos());
		entity.setIdDocSegOper(entity.getIdDocSegOper());
	}
	public void toBean(GePecasLog entity){
		entity.setPartNo(getPartNo());
		entity.setPartName(getPartName());
		//entity.setId(getId());
		entity.setQtd(Long.valueOf(getQtd()));
		entity.setGroupNumber(getGroupNumber());
		entity.setReferenceNo(getReferenceNo());
		entity.setSmcsCode(getSmcsCode());
		entity.setGroupName(getGroupName());
		entity.setUserId(getUserId());
		entity.setSos1(getSos());
		//entity.setIdDocSegOper(entity.getIdDocSegOper());
	}
	public void fromBean (GePecas entity){
		setPartNo(entity.getPartNo());
		setPartName(entity.getPartName());
		setId(entity.getId().toString());
		setQtd(entity.getQtd().toString());
		setGroupNumber(entity.getGroupNumber());
		setReferenceNo(entity.getReferenceNo());
		setSmcsCode(entity.getSmcsCode());
		setGroupName(entity.getGroupName());
		setReferenceNo(entity.getReferenceNo());
		setUserId(entity.getUserId());
		setSos(entity.getSos1());
		setIdDocSegOper(entity.getIdDocSegOper().getId());
		setDescerr(entity.getDescerr());
		setNumDoc(entity.getIdDocSegOper().getNumDoc());
	}
	public void fromBean (GePecasLog entity){
		setPartNo(entity.getPartNo());
		setPartName(entity.getPartName());
		setId(entity.getId().toString());
		setQtd(entity.getQtd().toString());
		setGroupNumber(entity.getGroupNumber());
		setReferenceNo(entity.getReferenceNo());
		setSmcsCode(entity.getSmcsCode());
		setGroupName(entity.getGroupName());
		setReferenceNo(entity.getReferenceNo());
		setUserId(entity.getUserId());
		setSos(entity.getSos1());
		//setIdDocSegOper(entity.getIdDocSegOper().getId());
		//setDescerr(entity.getDescerr());
	}
	public void fromBean (GePecas00E entity){
		setPartNo(entity.getPartNo());
		setPartName(entity.getPartName());
		setId(entity.getId().toString());
		setQtd(entity.getQtd().toString());
		setGroupNumber(entity.getGroupNumber());
		setReferenceNo(entity.getReferenceNo());
		setSmcsCode(entity.getSmcsCode());
		setGroupName(entity.getGroupName());
		setReferenceNo(entity.getReferenceNo());
		setUserId(entity.getUserId());
		setSos(entity.getSos1());
		setIdDocSegOper(entity.getIdDocSegOper().getId());
		setDescerr(entity.getDescerr());
		setNumDoc(entity.getIdDocSegOper().getNumDoc());
	}
	public void fromBean (GeRemoverPecaLog entity){
		setPartNo(entity.getPartNumber());
		setPartName(entity.getPartName());
		setId(entity.getId().toString());
		//setQtd(entity.getQtd().toString());
		//setGroupNumber(entity.getGroupNumber());
		//setReferenceNo(entity.getReferenceNo());
		//setSmcsCode(entity.getSmcsCode());
		//setGroupName(entity.getGroupName());
		///setReferenceNo(entity.getReferenceNo());
		//setUserId(entity.getUserId());
		//setSos(entity.getSos1());
		//setIdDocSegOper(entity.getIdDocSegOper().getId());
		//setDescerr(entity.getDescerr());
	}

}
