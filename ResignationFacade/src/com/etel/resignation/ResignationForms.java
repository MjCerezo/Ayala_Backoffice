package com.etel.resignation;

import java.util.Date;

//DANIEL FESALBON <<<<<<<<<<<<<<<<<<<<<<
public class ResignationForms {
	
	private String documentname;
	private String documentcode;
	private boolean hassubmitted;
	private Date submitdate;
	private String txcode;
	
	public Date getSubmitdate() {
		return submitdate;
	}
	public void setSubmitdate(Date submitdate) {
		this.submitdate = submitdate;
	}
	public boolean isHassubmitted() {
		return hassubmitted;
	}
	public void setHassubmitted(boolean hassubmitted) {
		this.hassubmitted = hassubmitted;
	}
	public String getDocumentname() {
		return documentname;
	}
	public void setDocumentname(String documentname) {
		this.documentname = documentname;
	}
	public String getTxcode() {
		return txcode;
	}
	public void setTxcode(String txcode) {
		this.txcode = txcode;
	}
	public String getDocumentcode() {
		return documentcode;
	}
	public void setDocumentcode(String documentcode) {
		this.documentcode = documentcode;
	}
	
//	private static DBService dbService = new DBServiceImpl();
//	private Map<String, Object> params = HQLUtil.getMap();
//	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
//	
//	public Tbresign saveFiling(Tbresign n, Tbresign resignation){
//		
//		params.put("txname", "Resignation");
//		Tbtransaction t = (Tbtransaction)dbService.executeUniqueHQLQuery("FROM Tbtransaction WHERE txname=:txname", params);
//		if(t!=null){
//				n.setTxcode(t.getTxcode());
//				n.setTxdate(new Date());
//		}
//		int ref = TransactionNoGenerator.generateTransactionNo("TRANSACTION");
//		n.setTxRefNo(ref);
//		n.setMembershipId(resignation.getMembershipId());
//		n.setCapconBankAcctNo(resignation.getCapconBankAcctNo());
//		n.setCapconAcctType(resignation.getCapconAcctType());
//		n.setCapconBankName(resignation.getCapconBankName());
//		n.setCapconInstruction(resignation.getCapconInstruction());
//		n.setDividendAcctType(resignation.getDividendAcctType());
//		n.setDividendBankAcctNo(resignation.getDividendBankAcctNo());
//		n.setDividendBankName(resignation.getDividendBankName());
//		n.setDividendInstruction(resignation.getDividendInstruction());
//		n.setFirstName(resignation.getFirstName());
//		n.setLastName(resignation.getLastName());
//		n.setMiddleName(resignation.getMiddleName());
//		n.setCreatedBy(secservice.getUserName());
//		n.setCreationDate(new Date());
//		return n;
//	}

}
