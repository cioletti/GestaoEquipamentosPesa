package com.gestaoequipamentos.bean;

import java.util.List;

public class PecasBoTreeBean {
	private List<PecasBoTreeBean> children;

	// Campos 1º nível da árvore
	private String rfdcno;
	private String wono;
	private String wosgno;
	private String woopno;

	// Campos 2º nível da árvore
	private String pano20;
	private String ds18;
	private String qybo;
	private String qyor;
	private String data;
	private String statusReal;

	public List<PecasBoTreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<PecasBoTreeBean> children) {
		this.children = children;
	}

	public String getRfdcno() {
		return rfdcno;
	}

	public void setRfdcno(String rfdcno) {
		this.rfdcno = rfdcno;
	}

	public String getWono() {
		return wono;
	}

	public void setWono(String wono) {
		this.wono = wono;
	}

	public String getWosgno() {
		return wosgno;
	}

	public void setWosgno(String wosgno) {
		this.wosgno = wosgno;
	}

	public String getWoopno() {
		return woopno;
	}

	public void setWoopno(String woopno) {
		this.woopno = woopno;
	}

	public String getPano20() {
		return pano20;
	}

	public void setPano20(String pano20) {
		this.pano20 = pano20;
	}

	public String getDs18() {
		return ds18;
	}

	public void setDs18(String ds18) {
		this.ds18 = ds18;
	}

	public String getQybo() {
		return qybo;
	}

	public void setQybo(String qybo) {
		this.qybo = qybo;
	}

	public String getQyor() {
		return qyor;
	}

	public void setQyor(String qyor) {
		this.qyor = qyor;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getStatusReal() {
		return statusReal;
	}

	public void setStatusReal(String statusReal) {
		this.statusReal = statusReal;
	}
}