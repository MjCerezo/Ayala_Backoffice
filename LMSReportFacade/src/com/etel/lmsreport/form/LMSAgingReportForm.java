package com.etel.lmsreport.form;

import java.math.BigDecimal;
import java.util.Date;

public class LMSAgingReportForm {
	
	String branch;
	String cifno;
	String pnno;
	String fullname;
	String address;
	String loanprod;
	Date dtbook;
	Date matdate;
	BigDecimal amortamount;
	BigDecimal prinamount;
	BigDecimal prinbal;
	BigDecimal arrears;
	
	BigDecimal day1to30;
	BigDecimal day31to60;
	BigDecimal day61to90;
	BigDecimal day91to180;
	BigDecimal day181to365;
	BigDecimal dayover365;
	
	BigDecimal notyetdue;
	BigDecimal regularsavings;
	BigDecimal sharedcapcommon;
	Date lasttransdate;
	String collectorname;
	String collectiontype;
	Integer noofdelinquent;
	
	BigDecimal day1to30ParAmount;
	BigDecimal day31to60ParAmount;
	BigDecimal day61to90ParAmount;
	BigDecimal day91to180ParAmount;
	BigDecimal day181to365ParAmount;
	BigDecimal dayover365ParAmount;
	
	BigDecimal day1to30ParRate;
	BigDecimal day31to60ParRate;
	BigDecimal day61to90ParRate;
	BigDecimal day91to180ParRate;
	BigDecimal day181to365ParRate;
	BigDecimal dayover365ParRate;
	
	BigDecimal parRateTotal;
	BigDecimal parRateAmount;
	
	
	public BigDecimal getDay1to30ParAmount() {
		return day1to30ParAmount;
	}
	public void setDay1to30ParAmount(BigDecimal day1to30ParAmount) {
		this.day1to30ParAmount = day1to30ParAmount;
	}
	public BigDecimal getDay31to60ParAmount() {
		return day31to60ParAmount;
	}
	public void setDay31to60ParAmount(BigDecimal day31to60ParAmount) {
		this.day31to60ParAmount = day31to60ParAmount;
	}
	public BigDecimal getDay61to90ParAmount() {
		return day61to90ParAmount;
	}
	public void setDay61to90ParAmount(BigDecimal day61to90ParAmount) {
		this.day61to90ParAmount = day61to90ParAmount;
	}
	public BigDecimal getDay91to180ParAmount() {
		return day91to180ParAmount;
	}
	public void setDay91to180ParAmount(BigDecimal day91to180ParAmount) {
		this.day91to180ParAmount = day91to180ParAmount;
	}
	public BigDecimal getDay181to365ParAmount() {
		return day181to365ParAmount;
	}
	public void setDay181to365ParAmount(BigDecimal day181to365ParAmount) {
		this.day181to365ParAmount = day181to365ParAmount;
	}
	public BigDecimal getDayover365ParAmount() {
		return dayover365ParAmount;
	}
	public void setDayover365ParAmount(BigDecimal dayover365ParAmount) {
		this.dayover365ParAmount = dayover365ParAmount;
	}
	public BigDecimal getDay1to30ParRate() {
		return day1to30ParRate;
	}
	public void setDay1to30ParRate(BigDecimal day1to30ParRate) {
		this.day1to30ParRate = day1to30ParRate;
	}
	public BigDecimal getDay31to60ParRate() {
		return day31to60ParRate;
	}
	public void setDay31to60ParRate(BigDecimal day31to60ParRate) {
		this.day31to60ParRate = day31to60ParRate;
	}
	public BigDecimal getDay61to90ParRate() {
		return day61to90ParRate;
	}
	public void setDay61to90ParRate(BigDecimal day61to90ParRate) {
		this.day61to90ParRate = day61to90ParRate;
	}
	public BigDecimal getDay91to180ParRate() {
		return day91to180ParRate;
	}
	public void setDay91to180ParRate(BigDecimal day91to180ParRate) {
		this.day91to180ParRate = day91to180ParRate;
	}
	public BigDecimal getDay181to365ParRate() {
		return day181to365ParRate;
	}
	public void setDay181to365ParRate(BigDecimal day181to365ParRate) {
		this.day181to365ParRate = day181to365ParRate;
	}
	public BigDecimal getDayover365ParRate() {
		return dayover365ParRate;
	}
	public void setDayover365ParRate(BigDecimal dayover365ParRate) {
		this.dayover365ParRate = dayover365ParRate;
	}
	public BigDecimal getParRateTotal() {
		return parRateTotal;
	}
	public void setParRateTotal(BigDecimal parRateTotal) {
		this.parRateTotal = parRateTotal;
	}
	public BigDecimal getParRateAmount() {
		return parRateAmount;
	}
	public void setParRateAmount(BigDecimal parRateAmount) {
		this.parRateAmount = parRateAmount;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getPnno() {
		return pnno;
	}
	public void setPnno(String pnno) {
		this.pnno = pnno;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLoanprod() {
		return loanprod;
	}
	public void setLoanprod(String loanprod) {
		this.loanprod = loanprod;
	}
	public Date getDtbook() {
		return dtbook;
	}
	public void setDtbook(Date dtbook) {
		this.dtbook = dtbook;
	}
	public Date getMatdate() {
		return matdate;
	}
	public void setMatdate(Date matdate) {
		this.matdate = matdate;
	}
	public BigDecimal getAmortamount() {
		return amortamount;
	}
	public void setAmortamount(BigDecimal amortamount) {
		this.amortamount = amortamount;
	}
	public BigDecimal getPrinamount() {
		return prinamount;
	}
	public void setPrinamount(BigDecimal prinamount) {
		this.prinamount = prinamount;
	}
	public BigDecimal getPrinbal() {
		return prinbal;
	}
	public void setPrinbal(BigDecimal prinbal) {
		this.prinbal = prinbal;
	}
	public BigDecimal getArrears() {
		return arrears;
	}
	public void setArrears(BigDecimal arrears) {
		this.arrears = arrears;
	}
	public BigDecimal getDay1to30() {
		return day1to30;
	}
	public void setDay1to30(BigDecimal day1to30) {
		this.day1to30 = day1to30;
	}
	public BigDecimal getDay31to60() {
		return day31to60;
	}
	public void setDay31to60(BigDecimal day31to60) {
		this.day31to60 = day31to60;
	}
	public BigDecimal getDay61to90() {
		return day61to90;
	}
	public void setDay61to90(BigDecimal day61to90) {
		this.day61to90 = day61to90;
	}
	public BigDecimal getDay91to180() {
		return day91to180;
	}
	public void setDay91to180(BigDecimal day91to180) {
		this.day91to180 = day91to180;
	}
	public BigDecimal getDay181to365() {
		return day181to365;
	}
	public void setDay181to365(BigDecimal day181to365) {
		this.day181to365 = day181to365;
	}
	public BigDecimal getDayover365() {
		return dayover365;
	}
	public void setDayover365(BigDecimal dayover365) {
		this.dayover365 = dayover365;
	}
	public BigDecimal getNotyetdue() {
		return notyetdue;
	}
	public void setNotyetdue(BigDecimal notyetdue) {
		this.notyetdue = notyetdue;
	}
	public BigDecimal getRegularsavings() {
		return regularsavings;
	}
	public void setRegularsavings(BigDecimal regularsavings) {
		this.regularsavings = regularsavings;
	}
	public BigDecimal getSharedcapcommon() {
		return sharedcapcommon;
	}
	public void setSharedcapcommon(BigDecimal sharedcapcommon) {
		this.sharedcapcommon = sharedcapcommon;
	}
	public Date getLasttransdate() {
		return lasttransdate;
	}
	public void setLasttransdate(Date lasttransdate) {
		this.lasttransdate = lasttransdate;
	}
	public String getCollectorname() {
		return collectorname;
	}
	public void setCollectorname(String collectorname) {
		this.collectorname = collectorname;
	}
	public String getCollectiontype() {
		return collectiontype;
	}
	public void setCollectiontype(String collectiontype) {
		this.collectiontype = collectiontype;
	}
	public Integer getNoofdelinquent() {
		return noofdelinquent;
	}
	public void setNoofdelinquent(Integer noofdelinquent) {
		this.noofdelinquent = noofdelinquent;
	}
	
	

}
