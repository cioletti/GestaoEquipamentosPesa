package com.gestaoequipamentos.bean;

import java.io.Serializable;

import com.gestaoequipamentos.entity.TwFilial;

public class FilialBean implements Serializable {

	private static final long serialVersionUID = 2155358431269549665L;
	private Long stno;
	private String stnm;
	private String sigla;

	public Long getStno() {
		return stno;
	}

	public void setStno(Long stno) {
		this.stno = stno;
	}

	public String getStnm() {
		return stnm;
	}

	public void setStnm(String stnm) {
		this.stnm = stnm;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public void fromBean(TwFilial entity) {
		setStno(entity.getStno());
		setStnm(entity.getStnm());
		setSigla(entity.getSigla());
	}

	public TwFilial toBean() {
		TwFilial fil = new TwFilial();
		fil.setStnm(getStnm());
		return fil;
	}
}
