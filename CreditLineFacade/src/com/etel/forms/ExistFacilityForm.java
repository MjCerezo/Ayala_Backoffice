package com.etel.forms;

import java.math.BigDecimal;
import java.util.Date;

public class ExistFacilityForm {
	
	private String cfappno;
	private String cifno;
	private String cfrefnoconcat;
	
	private String cftype;
	
	private BigDecimal cfapprovedamt;
	private BigDecimal cfamount;
	private BigDecimal cfbalance;
	private int cfterm;
	private String cftermperiod;
	private BigDecimal cfintrate;
	private String cfmaker;
	private String cifname;
	private Date cfexpdt;
	private String facilitytype;
	private String cfstatus;
	private boolean cfrevolving;
	private int id;
	private String datestatus;
	
	public String getDatestatus() {
		return datestatus;
	}
	public void setDatestatus(String datestatus) {
		this.datestatus = datestatus;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCfappno() {
		return cfappno;
	}
	public void setCfappno(String cfappno) {
		this.cfappno = cfappno;
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
	public String getCftype() {
		return cftype;
	}
	public void setCftype(String cftype) {
		this.cftype = cftype;
	}
	public BigDecimal getCfapprovedamt() {
		return cfapprovedamt;
	}
	public void setCfapprovedamt(BigDecimal cfapprovedamt) {
		this.cfapprovedamt = cfapprovedamt;
	}
	public BigDecimal getCfamount() {
		return cfamount;
	}
	public void setCfamount(BigDecimal cfamount) {
		this.cfamount = cfamount;
	}
	public BigDecimal getCfbalance() {
		return cfbalance;
	}
	public void setCfbalance(BigDecimal cfbalance) {
		this.cfbalance = cfbalance;
	}
	public int getCfterm() {
		return cfterm;
	}
	public void setCfterm(int cfterm) {
		this.cfterm = cfterm;
	}
	public String getCftermperiod() {
		return cftermperiod;
	}
	public void setCftermperiod(String cftermperiod) {
		this.cftermperiod = cftermperiod;
	}
	public BigDecimal getCfintrate() {
		return cfintrate;
	}
	public void setCfintrate(BigDecimal cfintrate) {
		this.cfintrate = cfintrate;
	}
	public String getCfmaker() {
		return cfmaker;
	}
	public void setCfmaker(String cfmaker) {
		this.cfmaker = cfmaker;
	}
	public String getCifname() {
		return cifname;
	}
	public void setCifname(String cifname) {
		this.cifname = cifname;
	}
	public Date getCfexpdt() {
		return cfexpdt;
	}
	public void setCfexpdt(Date cfexpdt) {
		this.cfexpdt = cfexpdt;
	}
	public String getFacilitytype() {
		return facilitytype;
	}
	public void setFacilitytype(String facilitytype) {
		this.facilitytype = facilitytype;
	}
	public String getCfstatus() {
		return cfstatus;
	}
	public void setCfstatus(String cfstatus) {
		this.cfstatus = cfstatus;
	}
	public boolean isCfrevolving() {
		return cfrevolving;
	}
	public void setCfrevolving(boolean cfrevolving) {
		this.cfrevolving = cfrevolving;
	}
	
	
}
