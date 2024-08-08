package com.casa.fintx.forms;

public class FinGenericForm {

	private String result;
	private String txrefno;
	private String txrefbr;
	private String posttx;
	private String posttxdesc;
	private String posttxsec;
	private String posttxdescsec;
	private String txstatus;
	private Integer override;
	
	public String getResult() {
		return result;
	}
	public String getTxrefno() {
		return txrefno;
	}
	public String getPosttx() {
		return posttx;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public void setTxrefno(String txrefno) {
		this.txrefno = txrefno;
	}
	public void setPosttx(String posttx) {
		this.posttx = posttx;
	}
	public String getPosttxdesc() {
		return posttxdesc;
	}
	public void setPosttxdesc(String posttxdesc) {
		this.posttxdesc = posttxdesc;
	}
	public String getPosttxsec() {
		return posttxsec;
	}
	public String getPosttxdescsec() {
		return posttxdescsec;
	}
	public void setPosttxsec(String posttxsec) {
		this.posttxsec = posttxsec;
	}
	public void setPosttxdescsec(String posttxdescsec) {
		this.posttxdescsec = posttxdescsec;
	}
	public String getTxrefbr() {
		return txrefbr;
	}
	public void setTxrefbr(String txrefbr) {
		this.txrefbr = txrefbr;
	}
	public String getTxstatus() {
		return txstatus;
	}
	public void setTxstatus(String txstatus) {
		this.txstatus = txstatus;
	}
	public Integer getOverride() {
		return override;
	}
	public void setOverride(Integer override) {
		this.override = override;
	}
	
	

}
