package com.etel.instruction.forms;

public class RequestValidationForm {
	//BI
	private Boolean bap;
	private Boolean cmap;
	private Boolean cic;
	private Boolean blacklist;
	private Boolean amla;
	
	//CI
	private Boolean pdrn;
	private Boolean evr;
	private Boolean bvr;
	private Boolean bankcheck;
	private Boolean creditcheck;
	private Boolean tradecheck;
	
	private String appno;
	private String cifno;
	private String investigationtype;
	
	public Boolean getBap() {
		return bap;
	}
	public void setBap(Boolean bap) {
		this.bap = bap;
	}
	public Boolean getCmap() {
		return cmap;
	}
	public void setCmap(Boolean cmap) {
		this.cmap = cmap;
	}
	public Boolean getCic() {
		return cic;
	}
	public void setCic(Boolean cic) {
		this.cic = cic;
	}
	public Boolean getBlacklist() {
		return blacklist;
	}
	public void setBlacklist(Boolean blacklist) {
		this.blacklist = blacklist;
	}
	public Boolean getAmla() {
		return amla;
	}
	public void setAmla(Boolean amla) {
		this.amla = amla;
	}
	public Boolean getPdrn() {
		return pdrn;
	}
	public void setPdrn(Boolean pdrn) {
		this.pdrn = pdrn;
	}
	public Boolean getEvr() {
		return evr;
	}
	public void setEvr(Boolean evr) {
		this.evr = evr;
	}
	public Boolean getBvr() {
		return bvr;
	}
	public void setBvr(Boolean bvr) {
		this.bvr = bvr;
	}
	public Boolean getBankcheck() {
		return bankcheck;
	}
	public void setBankcheck(Boolean bankcheck) {
		this.bankcheck = bankcheck;
	}
	public Boolean getCreditcheck() {
		return creditcheck;
	}
	public void setCreditcheck(Boolean creditcheck) {
		this.creditcheck = creditcheck;
	}
	public Boolean getTradecheck() {
		return tradecheck;
	}
	public void setTradecheck(Boolean tradecheck) {
		this.tradecheck = tradecheck;
	}
	public String getAppno() {
		return appno;
	}
	public void setAppno(String appno) {
		this.appno = appno;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getInvestigationtype() {
		return investigationtype;
	}
	public void setInvestigationtype(String investigationtype) {
		this.investigationtype = investigationtype;
	}
	
}
