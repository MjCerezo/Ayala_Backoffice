package com.etel.lmsreport.form;

import java.math.BigDecimal;
import java.util.Date;

public class LMSDataForm {

	//List of Loan Applications
	Date dateApplied;
	String applicationNo;
	String fullname;
	String loanProduct;
	String loanPurpose;
	BigDecimal amountApplied;
	String solicitingOfficer;
	String acctsts;
	Date applicationStatusDate;
	String branch;
	
	//List of LMS Transactions
	Date transactionValueDate;
	String transactionRefNo;
	String transactioncode;
	String pnNo;
	String loanAcctNo;
	String acctName;
	BigDecimal transactionAmount;
	BigDecimal txar1;
	BigDecimal txaddtnlint;
	BigDecimal txpenalty;
	BigDecimal txint;
	BigDecimal txprinbal;
	BigDecimal txOthers;
	BigDecimal txexcessbal;
	String transtat;
	String transType;
	
	//LMS_ListofDecidedLoanApplications
	Date dtbook;
	Integer term;
	//Date dateApplied;
	//String applicationNo;
	String accoutName;
	//String loanProduct;
	BigDecimal loanAmount;
	BigDecimal intRate;
	Date firstDueDate;
	Date maturityDate;
	BigDecimal pnterm;
	String solicitingOff;
	String approvaldecision;
	
	//List of Loan Releases
	Date dateAvailed;
	String loanNo;
	String accountName;
	Date fistDueDate;
	BigDecimal eir;
	String processBy;
	
	//Interest Accrual Set-up for the Period
	Date transactionDate;
	String loanAccountNo;
	BigDecimal intSetUpAmount;
	//Journal Entries for the Day
	String accountCode;
	String accountTitle;
	Date txvaldt;
	BigDecimal debit;
	BigDecimal credit;
	String journal_Details;
	//Schedule of Received Documents & Securities
	Date datesubmitted;
	String documentname;
	String pnno;
	String cifname;
	String uploadedby;
	
	//Schedule of Schedule of Held Securities
	String secNo;
	String secName;
	
	//Loan Releases per Range
	Integer upTo100kCount;
	BigDecimal upTo100kTotal;
	Integer upTo500kCount;
	BigDecimal upTo500kTotal;
	Integer upTo1mCount;
	BigDecimal upTo1mTotal;
	Integer upTo2mCount;
	BigDecimal upTo2mTotal;
	Integer upTo5mCount;
	BigDecimal upTo5mTotal;
	Integer upTo10mCount;
	BigDecimal upTo10mTotal;
	Integer upTo10mOverCount;
	BigDecimal upTo10mOverTotal;
	Integer totalCount;
	BigDecimal totalAmount;
	
	//List of Loan Releases per Firm Size
	Integer microCount;
	BigDecimal microTotal;
	Integer smallCount;
	BigDecimal smallTotal;
	Integer mediumCount;
	BigDecimal mediumTotal;
	Integer largeCount;
	BigDecimal largeTotal;
	
	//LOAN INTEREST & PENALTY COMPUTATION WORKSHEET
	String companyname;
	String cifno;
	String areaname;
	String applno;
	String accountno;
	String productname;
	String subarea;
	String collectorname;
	String status;
	BigDecimal loanamount;
	String prinpaycycdesc;
	BigDecimal interestrate;
	BigDecimal lpcRate;	
	Date bookingdate;	
	String inttypedesc;	
	String intpaycycdesc;	
	BigDecimal lpcRateFrequen;	
	Date matdt;	
	String noofinstallment;
	String transtype;
	String txrefno;
	String docNo;
	BigDecimal prindebit;	
	BigDecimal prnicredit;	
	BigDecimal prinbal;	
	BigDecimal intdays;	
	BigDecimal intdebit;	
	BigDecimal intcredit;	
	BigDecimal intbal;	
	Date duedate;	
	BigDecimal shouldbal;	
	BigDecimal delaybal;	
	BigDecimal pendays;	
	BigDecimal penpaid;	
	BigDecimal penbal;
	
	
	public String getTxrefno() {
		return txrefno;
	}
	public void setTxrefno(String txrefno) {
		this.txrefno = txrefno;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getCompanyname() {
		return companyname;
	}
	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getAreaname() {
		return areaname;
	}
	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}
	public String getApplno() {
		return applno;
	}
	public void setApplno(String applno) {
		this.applno = applno;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getSubarea() {
		return subarea;
	}
	public void setSubarea(String subarea) {
		this.subarea = subarea;
	}
	public String getCollectorname() {
		return collectorname;
	}
	public void setCollectorname(String collectorname) {
		this.collectorname = collectorname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public BigDecimal getLoanamount() {
		return loanamount;
	}
	public void setLoanamount(BigDecimal loanamount) {
		this.loanamount = loanamount;
	}
	public String getPrinpaycycdesc() {
		return prinpaycycdesc;
	}
	public void setPrinpaycycdesc(String prinpaycycdesc) {
		this.prinpaycycdesc = prinpaycycdesc;
	}
	public BigDecimal getInterestrate() {
		return interestrate;
	}
	public void setInterestrate(BigDecimal interestrate) {
		this.interestrate = interestrate;
	}
	public BigDecimal getLpcRate() {
		return lpcRate;
	}
	public void setLpcRate(BigDecimal lpcRate) {
		this.lpcRate = lpcRate;
	}
	public Date getBookingdate() {
		return bookingdate;
	}
	public void setBookingdate(Date bookingdate) {
		this.bookingdate = bookingdate;
	}
	public String getInttypedesc() {
		return inttypedesc;
	}
	public void setInttypedesc(String inttypedesc) {
		this.inttypedesc = inttypedesc;
	}
	public String getIntpaycycdesc() {
		return intpaycycdesc;
	}
	public void setIntpaycycdesc(String intpaycycdesc) {
		this.intpaycycdesc = intpaycycdesc;
	}
	public BigDecimal getLpcRateFrequen() {
		return lpcRateFrequen;
	}
	public void setLpcRateFrequen(BigDecimal lpcRateFrequen) {
		this.lpcRateFrequen = lpcRateFrequen;
	}
	public Date getMatdt() {
		return matdt;
	}
	public void setMatdt(Date matdt) {
		this.matdt = matdt;
	}
	public String getNoofinstallment() {
		return noofinstallment;
	}
	public void setNoofinstallment(String noofinstallment) {
		this.noofinstallment = noofinstallment;
	}
	public String getTranstype() {
		return transtype;
	}
	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}
	public String getDocNo() {
		return docNo;
	}
	public void setDocNo(String docNo) {
		this.docNo = docNo;
	}
	public BigDecimal getPrindebit() {
		return prindebit;
	}
	public void setPrindebit(BigDecimal prindebit) {
		this.prindebit = prindebit;
	}
	public BigDecimal getPrnicredit() {
		return prnicredit;
	}
	public void setPrnicredit(BigDecimal prnicredit) {
		this.prnicredit = prnicredit;
	}
	public BigDecimal getPrinbal() {
		return prinbal;
	}
	public void setPrinbal(BigDecimal prinbal) {
		this.prinbal = prinbal;
	}
	public BigDecimal getIntdays() {
		return intdays;
	}
	public void setIntdays(BigDecimal intdays) {
		this.intdays = intdays;
	}
	public BigDecimal getIntdebit() {
		return intdebit;
	}
	public void setIntdebit(BigDecimal intdebit) {
		this.intdebit = intdebit;
	}
	public BigDecimal getIntcredit() {
		return intcredit;
	}
	public void setIntcredit(BigDecimal intcredit) {
		this.intcredit = intcredit;
	}
	public BigDecimal getIntbal() {
		return intbal;
	}
	public void setIntbal(BigDecimal intbal) {
		this.intbal = intbal;
	}
	public Date getDuedate() {
		return duedate;
	}
	public void setDuedate(Date duedate) {
		this.duedate = duedate;
	}
	public BigDecimal getShouldbal() {
		return shouldbal;
	}
	public void setShouldbal(BigDecimal shouldbal) {
		this.shouldbal = shouldbal;
	}
	public BigDecimal getDelaybal() {
		return delaybal;
	}
	public void setDelaybal(BigDecimal delaybal) {
		this.delaybal = delaybal;
	}
	public BigDecimal getPendays() {
		return pendays;
	}
	public void setPendays(BigDecimal pendays) {
		this.pendays = pendays;
	}
	public BigDecimal getPenpaid() {
		return penpaid;
	}
	public void setPenpaid(BigDecimal penpaid) {
		this.penpaid = penpaid;
	}
	public BigDecimal getPenbal() {
		return penbal;
	}
	public void setPenbal(BigDecimal penbal) {
		this.penbal = penbal;
	}
	public Date getTxvaldt() {
		return txvaldt;
	}
	public void setTxvaldt(Date txvaldt) {
		this.txvaldt = txvaldt;
	}
	public Integer getUpTo100kCount() {
		return upTo100kCount;
	}
	public void setUpTo100kCount(Integer upTo100kCount) {
		this.upTo100kCount = upTo100kCount;
	}
	public BigDecimal getUpTo100kTotal() {
		return upTo100kTotal;
	}
	public void setUpTo100kTotal(BigDecimal upTo100kTotal) {
		this.upTo100kTotal = upTo100kTotal;
	}
	public Integer getUpTo500kCount() {
		return upTo500kCount;
	}
	public void setUpTo500kCount(Integer upTo500kCount) {
		this.upTo500kCount = upTo500kCount;
	}
	public BigDecimal getUpTo500kTotal() {
		return upTo500kTotal;
	}
	public void setUpTo500kTotal(BigDecimal upTo500kTotal) {
		this.upTo500kTotal = upTo500kTotal;
	}
	public Integer getUpTo1mCount() {
		return upTo1mCount;
	}
	public void setUpTo1mCount(Integer upTo1mCount) {
		this.upTo1mCount = upTo1mCount;
	}
	public BigDecimal getUpTo1mTotal() {
		return upTo1mTotal;
	}
	public void setUpTo1mTotal(BigDecimal upTo1mTotal) {
		this.upTo1mTotal = upTo1mTotal;
	}
	public Integer getUpTo2mCount() {
		return upTo2mCount;
	}
	public void setUpTo2mCount(Integer upTo2mCount) {
		this.upTo2mCount = upTo2mCount;
	}
	public BigDecimal getUpTo2mTotal() {
		return upTo2mTotal;
	}
	public void setUpTo2mTotal(BigDecimal upTo2mTotal) {
		this.upTo2mTotal = upTo2mTotal;
	}
	public Integer getUpTo5mCount() {
		return upTo5mCount;
	}
	public void setUpTo5mCount(Integer upTo5mCount) {
		this.upTo5mCount = upTo5mCount;
	}
	public BigDecimal getUpTo5mTotal() {
		return upTo5mTotal;
	}
	public void setUpTo5mTotal(BigDecimal upTo5mTotal) {
		this.upTo5mTotal = upTo5mTotal;
	}
	public Integer getUpTo10mCount() {
		return upTo10mCount;
	}
	public void setUpTo10mCount(Integer upTo10mCount) {
		this.upTo10mCount = upTo10mCount;
	}
	public BigDecimal getUpTo10mTotal() {
		return upTo10mTotal;
	}
	public void setUpTo10mTotal(BigDecimal upTo10mTotal) {
		this.upTo10mTotal = upTo10mTotal;
	}
	public Integer getUpTo10mOverCount() {
		return upTo10mOverCount;
	}
	public void setUpTo10mOverCount(Integer upTo10mOverCount) {
		this.upTo10mOverCount = upTo10mOverCount;
	}
	public BigDecimal getUpTo10mOverTotal() {
		return upTo10mOverTotal;
	}
	public void setUpTo10mOverTotal(BigDecimal upTo10mOverTotal) {
		this.upTo10mOverTotal = upTo10mOverTotal;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Integer getMicroCount() {
		return microCount;
	}
	public void setMicroCount(Integer microCount) {
		this.microCount = microCount;
	}
	public BigDecimal getMicroTotal() {
		return microTotal;
	}
	public void setMicroTotal(BigDecimal microTotal) {
		this.microTotal = microTotal;
	}
	public Integer getSmallCount() {
		return smallCount;
	}
	public void setSmallCount(Integer smallCount) {
		this.smallCount = smallCount;
	}
	public BigDecimal getSmallTotal() {
		return smallTotal;
	}
	public void setSmallTotal(BigDecimal smallTotal) {
		this.smallTotal = smallTotal;
	}
	public Integer getMediumCount() {
		return mediumCount;
	}
	public void setMediumCount(Integer mediumCount) {
		this.mediumCount = mediumCount;
	}
	public BigDecimal getMediumTotal() {
		return mediumTotal;
	}
	public void setMediumTotal(BigDecimal mediumTotal) {
		this.mediumTotal = mediumTotal;
	}
	public Integer getLargeCount() {
		return largeCount;
	}
	public void setLargeCount(Integer largeCount) {
		this.largeCount = largeCount;
	}
	public BigDecimal getLargeTotal() {
		return largeTotal;
	}
	public void setLargeTotal(BigDecimal largeTotal) {
		this.largeTotal = largeTotal;
	}
	public String getSecNo() {
		return secNo;
	}
	public void setSecNo(String secNo) {
		this.secNo = secNo;
	}
	public String getSecName() {
		return secName;
	}
	public void setSecName(String secName) {
		this.secName = secName;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public Date getTransactionDate() {
		return transactionDate;
	}
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public String getLoanAccountNo() {
		return loanAccountNo;
	}
	public void setLoanAccountNo(String loanAccountNo) {
		this.loanAccountNo = loanAccountNo;
	}
	public BigDecimal getIntSetUpAmount() {
		return intSetUpAmount;
	}
	public void setIntSetUpAmount(BigDecimal intSetUpAmount) {
		this.intSetUpAmount = intSetUpAmount;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public String getAccountTitle() {
		return accountTitle;
	}
	public void setAccountTitle(String accountTitle) {
		this.accountTitle = accountTitle;
	}
	public BigDecimal getDebit() {
		return debit;
	}
	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}
	public BigDecimal getCredit() {
		return credit;
	}
	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}
	public String getJournal_Details() {
		return journal_Details;
	}
	public void setJournal_Details(String journal_Details) {
		this.journal_Details = journal_Details;
	}
	public Date getDatesubmitted() {
		return datesubmitted;
	}
	public void setDatesubmitted(Date datesubmitted) {
		this.datesubmitted = datesubmitted;
	}
	public String getDocumentname() {
		return documentname;
	}
	public void setDocumentname(String documentname) {
		this.documentname = documentname;
	}
	public String getPnno() {
		return pnno;
	}
	public void setPnno(String pnno) {
		this.pnno = pnno;
	}
	public String getCifname() {
		return cifname;
	}
	public void setCifname(String cifname) {
		this.cifname = cifname;
	}
	public String getUploadedby() {
		return uploadedby;
	}
	public void setUploadedby(String uploadedby) {
		this.uploadedby = uploadedby;
	}
	public Date getDateAvailed() {
		return dateAvailed;
	}
	public void setDateAvailed(Date dateAvailed) {
		this.dateAvailed = dateAvailed;
	}
	public String getLoanNo() {
		return loanNo;
	}
	public void setLoanNo(String loanNo) {
		this.loanNo = loanNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Date getFistDueDate() {
		return fistDueDate;
	}
	public void setFistDueDate(Date fistDueDate) {
		this.fistDueDate = fistDueDate;
	}
	public BigDecimal getEir() {
		return eir;
	}
	public void setEir(BigDecimal eir) {
		this.eir = eir;
	}
	public String getProcessBy() {
		return processBy;
	}
	public void setProcessBy(String processBy) {
		this.processBy = processBy;
	}
	public Date getDateApplied() {
		return dateApplied;
	}
	public void setDateApplied(Date dateApplied) {
		this.dateApplied = dateApplied;
	}
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getLoanProduct() {
		return loanProduct;
	}
	public void setLoanProduct(String loanProduct) {
		this.loanProduct = loanProduct;
	}
	public String getLoanPurpose() {
		return loanPurpose;
	}
	public void setLoanPurpose(String loanPurpose) {
		this.loanPurpose = loanPurpose;
	}
	public BigDecimal getAmountApplied() {
		return amountApplied;
	}
	public void setAmountApplied(BigDecimal amountApplied) {
		this.amountApplied = amountApplied;
	}
	public String getSolicitingOfficer() {
		return solicitingOfficer;
	}
	public void setSolicitingOfficer(String solicitingOfficer) {
		this.solicitingOfficer = solicitingOfficer;
	}
	public String getAcctsts() {
		return acctsts;
	}
	public void setAcctsts(String acctsts) {
		this.acctsts = acctsts;
	}
	public Date getApplicationStatusDate() {
		return applicationStatusDate;
	}
	public void setApplicationStatusDate(Date applicationStatusDate) {
		this.applicationStatusDate = applicationStatusDate;
	}
	public Date getTransactionValueDate() {
		return transactionValueDate;
	}
	public void setTransactionValueDate(Date transactionValueDate) {
		this.transactionValueDate = transactionValueDate;
	}
	public String getTransactionRefNo() {
		return transactionRefNo;
	}
	public void setTransactionRefNo(String transactionRefNo) {
		this.transactionRefNo = transactionRefNo;
	}
	public String getTransactioncode() {
		return transactioncode;
	}
	public void setTransactioncode(String transactioncode) {
		this.transactioncode = transactioncode;
	}
	public String getPnNo() {
		return pnNo;
	}
	public void setPnNo(String pnNo) {
		this.pnNo = pnNo;
	}
	public String getLoanAcctNo() {
		return loanAcctNo;
	}
	public void setLoanAcctNo(String loanAcctNo) {
		this.loanAcctNo = loanAcctNo;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	public BigDecimal getTxar1() {
		return txar1;
	}
	public void setTxar1(BigDecimal txar1) {
		this.txar1 = txar1;
	}
	public BigDecimal getTxaddtnlint() {
		return txaddtnlint;
	}
	public void setTxaddtnlint(BigDecimal txaddtnlint) {
		this.txaddtnlint = txaddtnlint;
	}
	public BigDecimal getTxpenalty() {
		return txpenalty;
	}
	public void setTxpenalty(BigDecimal txpenalty) {
		this.txpenalty = txpenalty;
	}
	public BigDecimal getTxint() {
		return txint;
	}
	public void setTxint(BigDecimal txint) {
		this.txint = txint;
	}
	public BigDecimal getTxprinbal() {
		return txprinbal;
	}
	public void setTxprinbal(BigDecimal txprinbal) {
		this.txprinbal = txprinbal;
	}
	public BigDecimal getTxOthers() {
		return txOthers;
	}
	public void setTxOthers(BigDecimal txOthers) {
		this.txOthers = txOthers;
	}
	public BigDecimal getTxexcessbal() {
		return txexcessbal;
	}
	public void setTxexcessbal(BigDecimal txexcessbal) {
		this.txexcessbal = txexcessbal;
	}
	public String getTranstat() {
		return transtat;
	}
	public void setTranstat(String transtat) {
		this.transtat = transtat;
	}
	public Date getDtbook() {
		return dtbook;
	}
	public void setDtbook(Date dtbook) {
		this.dtbook = dtbook;
	}
	public String getAccoutName() {
		return accoutName;
	}
	public void setAccoutName(String accoutName) {
		this.accoutName = accoutName;
	}
	public BigDecimal getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}
	public BigDecimal getIntRate() {
		return intRate;
	}
	public void setIntRate(BigDecimal intRate) {
		this.intRate = intRate;
	}
	public Date getFirstDueDate() {
		return firstDueDate;
	}
	public void setFirstDueDate(Date firstDueDate) {
		this.firstDueDate = firstDueDate;
	}
	public Date getMaturityDate() {
		return maturityDate;
	}
	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}
	public BigDecimal getPnterm() {
		return pnterm;
	}
	public void setPnterm(BigDecimal pnterm) {
		this.pnterm = pnterm;
	}
	public String getSolicitingOff() {
		return solicitingOff;
	}
	public void setSolicitingOff(String solicitingOff) {
		this.solicitingOff = solicitingOff;
	}
	public String getApprovaldecision() {
		return approvaldecision;
	}
	public void setApprovaldecision(String approvaldecision) {
		this.approvaldecision = approvaldecision;
	}
	
	
}
