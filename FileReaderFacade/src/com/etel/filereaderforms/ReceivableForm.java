package com.etel.filereaderforms;

import java.math.BigDecimal;
import java.util.Date;


public class ReceivableForm {

	private String loansappno;
	private String reason;
	private String appno;
	private Integer row;
	private Boolean isExistingInCIF;
	private Boolean isExistingInReceivables; // Main Inventory
	private Boolean isAccredited;
	private Boolean isExistingInApplication; // Loan Application Usage
	private Boolean isExistingInLMS; // Loan Account Usage
    private String referenceno;
    private String receivabletype;
    private String tradecifno;	
	private String cifno;
    private String tradetype;
    private String clientsuppliername;
    private String clientsupplieraddress;
    private String contactperson;
    private String contactposition;
    private String emailaddress;
    private String mobile1;
    private String mobile2;
    private String landline1;
    private String landline2;
    private String accreditationstatus;
    private String bankname;
    private String branch;
    private Date checkdate;
    private BigDecimal amount;
    private String remarks;
    private String vehicletype;
    private String make;
    private String model;
    private String chassisno;
    private String engineno;
    private String color;
    private String fuel;
    private String location;
    private String plateno;
    private String conductionstickerno;
    private String checkstatus;
    private String informant1;
    private String informant1position;
    private String informant1contactno;
    private String informant2;
    private String informant2position;
    private String informant2contactno;
    private String invremarks;
    private String paymentterms;
    private Date paymentdate;
    private String postatus;
    private String deliverysched;
    private String paymentoption;
    private String mileage;
    private BigDecimal retailprice;
    private BigDecimal marketresistance;
    private BigDecimal lessmarketresistance;
    private BigDecimal total;
    private BigDecimal loanablevaluepercent;
    private BigDecimal loanablevaluephp;
    private Boolean validCIF; // Must be Client / Supplier of Main Borrower	
    						  // Accredited Client / Supplier
    						  // If Own Check or Inventory it must be the Main Borrower CIF No.	
    						  // Existing in CIF
    
	public Boolean getValidCIF() {
		return validCIF;
	}
	public void setValidCIF(Boolean validCIF) {
		this.validCIF = validCIF;
	}
	public String getReason() {
		return reason;
	}	
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getAppno() {
		return appno;
	}
	public void setAppno(String appno) {
		this.appno = appno;
	}
	public Integer getRow() {
		return row;
	}
	public void setRow(Integer row) {
		this.row = row;
	}
	public Boolean getIsExistingInCIF() {
		return isExistingInCIF;
	}
	public void setIsExistingInCIF(Boolean isExistingInCIF) {
		this.isExistingInCIF = isExistingInCIF;
	}
	public Boolean getIsExistingInReceivables() {
		return isExistingInReceivables;
	}
	public void setIsExistingInReceivables(Boolean isExistingInReceivables) {
		this.isExistingInReceivables = isExistingInReceivables;
	}
	public Boolean getIsAccredited() {
		return isAccredited;
	}
	public void setIsAccredited(Boolean isAccredited) {
		this.isAccredited = isAccredited;
	}
	public Boolean getIsExistingInApplication() {
		return isExistingInApplication;
	}
	public void setIsExistingInApplication(Boolean isExistingInApplication) {
		this.isExistingInApplication = isExistingInApplication;
	}
	public Boolean getIsExistingInLMS() {
		return isExistingInLMS;
	}
	public void setIsExistingInLMS(Boolean isExistingInLMS) {
		this.isExistingInLMS = isExistingInLMS;
	}
	public String getReferenceno() {
		return referenceno;
	}
	public void setReferenceno(String referenceno) {
		this.referenceno = referenceno;
	}
	public String getReceivabletype() {
		return receivabletype;
	}
	public void setReceivabletype(String receivabletype) {
		this.receivabletype = receivabletype;
	}
	public String getTradecifno() {
		return tradecifno;
	}
	public void setTradecifno(String tradecifno) {
		this.tradecifno = tradecifno;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getTradetype() {
		return tradetype;
	}
	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}
	public String getClientsuppliername() {
		return clientsuppliername;
	}
	public void setClientsuppliername(String clientsuppliername) {
		this.clientsuppliername = clientsuppliername;
	}
	public String getClientsupplieraddress() {
		return clientsupplieraddress;
	}
	public void setClientsupplieraddress(String clientsupplieraddress) {
		this.clientsupplieraddress = clientsupplieraddress;
	}
	public String getContactperson() {
		return contactperson;
	}
	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}
	public String getContactposition() {
		return contactposition;
	}
	public void setContactposition(String contactposition) {
		this.contactposition = contactposition;
	}
	public String getEmailaddress() {
		return emailaddress;
	}
	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getLandline1() {
		return landline1;
	}
	public void setLandline1(String landline1) {
		this.landline1 = landline1;
	}
	public String getLandline2() {
		return landline2;
	}
	public void setLandline2(String landline2) {
		this.landline2 = landline2;
	}
	public String getAccreditationstatus() {
		return accreditationstatus;
	}
	public void setAccreditationstatus(String accreditationstatus) {
		this.accreditationstatus = accreditationstatus;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public Date getCheckdate() {
		return checkdate;
	}
	public void setCheckdate(Date checkdate) {
		this.checkdate = checkdate;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getVehicletype() {
		return vehicletype;
	}
	public void setVehicletype(String vehicletype) {
		this.vehicletype = vehicletype;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getChassisno() {
		return chassisno;
	}
	public void setChassisno(String chassisno) {
		this.chassisno = chassisno;
	}
	public String getEngineno() {
		return engineno;
	}
	public void setEngineno(String engineno) {
		this.engineno = engineno;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getFuel() {
		return fuel;
	}
	public void setFuel(String fuel) {
		this.fuel = fuel;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPlateno() {
		return plateno;
	}
	public void setPlateno(String plateno) {
		this.plateno = plateno;
	}
	public String getConductionstickerno() {
		return conductionstickerno;
	}
	public void setConductionstickerno(String conductionstickerno) {
		this.conductionstickerno = conductionstickerno;
	}
	public String getCheckstatus() {
		return checkstatus;
	}
	public void setCheckstatus(String checkstatus) {
		this.checkstatus = checkstatus;
	}
	public String getInformant1() {
		return informant1;
	}
	public void setInformant1(String informant1) {
		this.informant1 = informant1;
	}
	public String getInformant1position() {
		return informant1position;
	}
	public void setInformant1position(String informant1position) {
		this.informant1position = informant1position;
	}
	public String getInformant1contactno() {
		return informant1contactno;
	}
	public void setInformant1contactno(String informant1contactno) {
		this.informant1contactno = informant1contactno;
	}
	public String getInformant2() {
		return informant2;
	}
	public void setInformant2(String informant2) {
		this.informant2 = informant2;
	}
	public String getInformant2position() {
		return informant2position;
	}
	public void setInformant2position(String informant2position) {
		this.informant2position = informant2position;
	}
	public String getInformant2contactno() {
		return informant2contactno;
	}
	public void setInformant2contactno(String informant2contactno) {
		this.informant2contactno = informant2contactno;
	}
	public String getInvremarks() {
		return invremarks;
	}
	public void setInvremarks(String invremarks) {
		this.invremarks = invremarks;
	}
	public String getPaymentterms() {
		return paymentterms;
	}
	public void setPaymentterms(String paymentterms) {
		this.paymentterms = paymentterms;
	}
	public Date getPaymentdate() {
		return paymentdate;
	}
	public void setPaymentdate(Date paymentdate) {
		this.paymentdate = paymentdate;
	}
	public String getPostatus() {
		return postatus;
	}
	public void setPostatus(String postatus) {
		this.postatus = postatus;
	}
	public String getDeliverysched() {
		return deliverysched;
	}
	public void setDeliverysched(String deliverysched) {
		this.deliverysched = deliverysched;
	}
	public String getPaymentoption() {
		return paymentoption;
	}
	public void setPaymentoption(String paymentoption) {
		this.paymentoption = paymentoption;
	}
	public String getMileage() {
		return mileage;
	}
	public void setMileage(String mileage) {
		this.mileage = mileage;
	}
	public BigDecimal getRetailprice() {
		return retailprice;
	}
	public void setRetailprice(BigDecimal retailprice) {
		this.retailprice = retailprice;
	}
	public BigDecimal getMarketresistance() {
		return marketresistance;
	}
	public void setMarketresistance(BigDecimal marketresistance) {
		this.marketresistance = marketresistance;
	}
	public BigDecimal getLessmarketresistance() {
		return lessmarketresistance;
	}
	public void setLessmarketresistance(BigDecimal lessmarketresistance) {
		this.lessmarketresistance = lessmarketresistance;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public BigDecimal getLoanablevaluepercent() {
		return loanablevaluepercent;
	}
	public void setLoanablevaluepercent(BigDecimal loanablevaluepercent) {
		this.loanablevaluepercent = loanablevaluepercent;
	}
	public BigDecimal getLoanablevaluephp() {
		return loanablevaluephp;
	}
	public void setLoanablevaluephp(BigDecimal loanablevaluephp) {
		this.loanablevaluephp = loanablevaluephp;
	}
    
    
    
}
