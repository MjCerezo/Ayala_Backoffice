package com.etel.creditfacility.forms;

import java.math.BigDecimal;

public class ExistingFacilityForm {
	
	private String appno;
	private String cifno;
	private String cfrefnoconcat;
	private String facilitytype;
	private BigDecimal cfapprovedamt;
	private BigDecimal cfproposedamt;
	private String cfstatus;
	private String cfappno;
	
	public String getCfappno() {
		return cfappno;
	}
	public void setCfappno(String cfappno) {
		this.cfappno = cfappno;
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
	public String getCfrefnoconcat() {
		return cfrefnoconcat;
	}
	public void setCfrefnoconcat(String cfrefnoconcat) {
		this.cfrefnoconcat = cfrefnoconcat;
	}
	public String getFacilitytype() {
		return facilitytype;
	}
	public void setFacilitytype(String facilitytype) {
		this.facilitytype = facilitytype;
	}
	public BigDecimal getCfapprovedamt() {
		return cfapprovedamt;
	}
	public void setCfapprovedamt(BigDecimal cfapprovedamt) {
		this.cfapprovedamt = cfapprovedamt;
	}
	public BigDecimal getCfproposedamt() {
		return cfproposedamt;
	}
	public void setCfproposedamt(BigDecimal cfproposedamt) {
		this.cfproposedamt = cfproposedamt;
	}
	public String getCfstatus() {
		return cfstatus;
	}
	public void setCfstatus(String cfstatus) {
		this.cfstatus = cfstatus;
	}
	
	
}
