package com.etel.lmsreport.form;

import java.math.BigDecimal;
import java.util.Date;

public class LMSAnalyticalReportForm {
	//CollectionReport
	Date lastTransaction;
	Date txDate;
	Date nextDueDate;
	String pnno;
	String loanno;
	String fullname;
	String loanProduct;
	BigDecimal originalLoanAmount;
	BigDecimal term;
	BigDecimal monthlyAmozt;
	BigDecimal outsBalance;
	Integer instToGo;
	String acctsts;
	Integer lastInstPaid;
	BigDecimal accountReceivable;
	BigDecimal latePaymentCharge;
	BigDecimal interest;
	BigDecimal principal;
	BigDecimal totalAmountReceived;
	String branch;
	String sourceOfPayment;
	String collector;
	
	
	//ScheduleofAccountswithArrearages
	BigDecimal amountToUpdate;
	
	//DelinquencyBucketListLoanAccount
	String pnNo;
	String loanNo;
	String accountName;
	String loanProd;
	String accountStat;
	Integer days1To30No;
	BigDecimal sum1To30;
	BigDecimal outstandingBalance1To30;
	Integer days31To60No;
	BigDecimal sum31To60;
	BigDecimal outstandingBalance31To60;
	Integer days61To90No;
	BigDecimal sum61To90;
	BigDecimal outstandingBalance61To90;
	Integer days91To120No;
	BigDecimal sum91To120;
	BigDecimal outstandingBalance91To120;
	Integer days121To150No;
	BigDecimal sum121To150;
	BigDecimal outstandingBalance121To150;
	Integer days151To180No;
	BigDecimal sum151To180;
	BigDecimal outstandingBalance151To180;
	Integer days180NoPlus;
	BigDecimal sum180Plus;
	BigDecimal outstandingBalance180Plus;

	//Loan Grant Performance Review (per Branch, Soliciting Officer, Approving Officer)
	Integer totalCount;
	BigDecimal totalAmount;
	Integer totalCurrentCount;
	BigDecimal totalCurrent;
	Integer totalPastDueCount;
	BigDecimal totalPastDue;
	Integer totalPaidOffCount;
	BigDecimal totalPaidOff;
	Integer totalLitigationCount;
	BigDecimal totalLitigation;
	
	//Loan Grant Performance Review per Branch
	BigDecimal percentCurrent;
	BigDecimal percentPastDue;
	BigDecimal percentPaidOff;
	BigDecimal percentLitigation;
	
	//Loan Collections per Branch/Soliciting Officer/Approving Officer
	Integer totalCollectionCount;
	BigDecimal totalCollectionAmount;
	Integer totalCurrentCollectionCount;
	BigDecimal totalCurrentCollection;
	Integer totalPastDueCollectionCount;
	BigDecimal totalPastDueCollection;
	Integer totalPaidOffCollectionCount;
	BigDecimal totalPaidOffCollection;
	Integer totalLitigationCollectionCount;
	BigDecimal totalLitigationCollection;
	
	//List of Loan Accounts Fully Paid Before Maturity Date
	Date matDate;
	Date dateOfPayment;
	BigDecimal amountOfPayment;
	String loanOfficer;
	
	//List of Loan Accounts Fully Paid Before Maturity Date
	BigDecimal totalCollectionAmountPerBranch;
	BigDecimal percentPerBranch;
	BigDecimal totalPercent;
	
	
	//Loans Target vs Performance
	Integer totalActualPerformanceCount;
	BigDecimal totalActualPerformanceAmount;
	Integer totalTargetCount;
	BigDecimal totalTargetAmount;
	Integer totalDifferenceCount;
	BigDecimal totalDifferenceTargetAmount;
	
	
	//
	
	
	public Integer getTotalActualPerformanceCount() {
		return totalActualPerformanceCount;
	}

	public void setTotalActualPerformanceCount(Integer totalActualPerformanceCount) {
		this.totalActualPerformanceCount = totalActualPerformanceCount;
	}

	public BigDecimal getTotalActualPerformanceAmount() {
		return totalActualPerformanceAmount;
	}

	public void setTotalActualPerformanceAmount(BigDecimal totalActualPerformanceAmount) {
		this.totalActualPerformanceAmount = totalActualPerformanceAmount;
	}

	public Integer getTotalTargetCount() {
		return totalTargetCount;
	}

	public void setTotalTargetCount(Integer totalTargetCount) {
		this.totalTargetCount = totalTargetCount;
	}

	public BigDecimal getTotalTargetAmount() {
		return totalTargetAmount;
	}

	public void setTotalTargetAmount(BigDecimal totalTargetAmount) {
		this.totalTargetAmount = totalTargetAmount;
	}

	public Integer getTotalDifferenceCount() {
		return totalDifferenceCount;
	}

	public void setTotalDifferenceCount(Integer totalDifferenceCount) {
		this.totalDifferenceCount = totalDifferenceCount;
	}

	public BigDecimal getTotalDifferenceTargetAmount() {
		return totalDifferenceTargetAmount;
	}

	public void setTotalDifferenceTargetAmount(BigDecimal totalDifferenceTargetAmount) {
		this.totalDifferenceTargetAmount = totalDifferenceTargetAmount;
	}

	public BigDecimal getTotalCollectionAmountPerBranch() {
		return totalCollectionAmountPerBranch;
	}

	public void setTotalCollectionAmountPerBranch(BigDecimal totalCollectionAmountPerBranch) {
		this.totalCollectionAmountPerBranch = totalCollectionAmountPerBranch;
	}

	public BigDecimal getPercentPerBranch() {
		return percentPerBranch;
	}

	public void setPercentPerBranch(BigDecimal percentPerBranch) {
		this.percentPerBranch = percentPerBranch;
	}

	public BigDecimal getTotalPercent() {
		return totalPercent;
	}

	public void setTotalPercent(BigDecimal totalPercent) {
		this.totalPercent = totalPercent;
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

	public Integer getTotalCurrentCount() {
		return totalCurrentCount;
	}

	public void setTotalCurrentCount(Integer totalCurrentCount) {
		this.totalCurrentCount = totalCurrentCount;
	}

	public BigDecimal getTotalCurrent() {
		return totalCurrent;
	}

	public void setTotalCurrent(BigDecimal totalCurrent) {
		this.totalCurrent = totalCurrent;
	}

	public Integer getTotalPastDueCount() {
		return totalPastDueCount;
	}

	public void setTotalPastDueCount(Integer totalPastDueCount) {
		this.totalPastDueCount = totalPastDueCount;
	}

	public BigDecimal getTotalPastDue() {
		return totalPastDue;
	}

	public void setTotalPastDue(BigDecimal totalPastDue) {
		this.totalPastDue = totalPastDue;
	}

	public Integer getTotalPaidOffCount() {
		return totalPaidOffCount;
	}

	public void setTotalPaidOffCount(Integer totalPaidOffCount) {
		this.totalPaidOffCount = totalPaidOffCount;
	}

	public BigDecimal getTotalPaidOff() {
		return totalPaidOff;
	}

	public void setTotalPaidOff(BigDecimal totalPaidOff) {
		this.totalPaidOff = totalPaidOff;
	}

	public Integer getTotalLitigationCount() {
		return totalLitigationCount;
	}

	public void setTotalLitigationCount(Integer totalLitigationCount) {
		this.totalLitigationCount = totalLitigationCount;
	}

	public BigDecimal getTotalLitigation() {
		return totalLitigation;
	}

	public void setTotalLitigation(BigDecimal totalLitigation) {
		this.totalLitigation = totalLitigation;
	}

	public BigDecimal getPercentCurrent() {
		return percentCurrent;
	}

	public void setPercentCurrent(BigDecimal percentCurrent) {
		this.percentCurrent = percentCurrent;
	}

	public BigDecimal getPercentPastDue() {
		return percentPastDue;
	}

	public void setPercentPastDue(BigDecimal percentPastDue) {
		this.percentPastDue = percentPastDue;
	}

	public BigDecimal getPercentPaidOff() {
		return percentPaidOff;
	}

	public void setPercentPaidOff(BigDecimal percentPaidOff) {
		this.percentPaidOff = percentPaidOff;
	}

	public BigDecimal getPercentLitigation() {
		return percentLitigation;
	}

	public void setPercentLitigation(BigDecimal percentLitigation) {
		this.percentLitigation = percentLitigation;
	}

	public Integer getTotalCollectionCount() {
		return totalCollectionCount;
	}

	public void setTotalCollectionCount(Integer totalCollectionCount) {
		this.totalCollectionCount = totalCollectionCount;
	}

	public BigDecimal getTotalCollectionAmount() {
		return totalCollectionAmount;
	}

	public void setTotalCollectionAmount(BigDecimal totalCollectionAmount) {
		this.totalCollectionAmount = totalCollectionAmount;
	}

	public Integer getTotalCurrentCollectionCount() {
		return totalCurrentCollectionCount;
	}

	public void setTotalCurrentCollectionCount(Integer totalCurrentCollectionCount) {
		this.totalCurrentCollectionCount = totalCurrentCollectionCount;
	}

	public BigDecimal getTotalCurrentCollection() {
		return totalCurrentCollection;
	}

	public void setTotalCurrentCollection(BigDecimal totalCurrentCollection) {
		this.totalCurrentCollection = totalCurrentCollection;
	}

	public Integer getTotalPastDueCollectionCount() {
		return totalPastDueCollectionCount;
	}

	public void setTotalPastDueCollectionCount(Integer totalPastDueCollectionCount) {
		this.totalPastDueCollectionCount = totalPastDueCollectionCount;
	}

	public BigDecimal getTotalPastDueCollection() {
		return totalPastDueCollection;
	}

	public void setTotalPastDueCollection(BigDecimal totalPastDueCollection) {
		this.totalPastDueCollection = totalPastDueCollection;
	}

	public Integer getTotalPaidOffCollectionCount() {
		return totalPaidOffCollectionCount;
	}

	public void setTotalPaidOffCollectionCount(Integer totalPaidOffCollectionCount) {
		this.totalPaidOffCollectionCount = totalPaidOffCollectionCount;
	}

	public BigDecimal getTotalPaidOffCollection() {
		return totalPaidOffCollection;
	}

	public void setTotalPaidOffCollection(BigDecimal totalPaidOffCollection) {
		this.totalPaidOffCollection = totalPaidOffCollection;
	}

	public Integer getTotalLitigationCollectionCount() {
		return totalLitigationCollectionCount;
	}

	public void setTotalLitigationCollectionCount(Integer totalLitigationCollectionCount) {
		this.totalLitigationCollectionCount = totalLitigationCollectionCount;
	}

	public BigDecimal getTotalLitigationCollection() {
		return totalLitigationCollection;
	}

	public void setTotalLitigationCollection(BigDecimal totalLitigationCollection) {
		this.totalLitigationCollection = totalLitigationCollection;
	}

	public Date getMatDate() {
		return matDate;
	}

	public void setMatDate(Date matDate) {
		this.matDate = matDate;
	}

	public Date getDateOfPayment() {
		return dateOfPayment;
	}

	public void setDateOfPayment(Date dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
	}

	public BigDecimal getAmountOfPayment() {
		return amountOfPayment;
	}

	public void setAmountOfPayment(BigDecimal amountOfPayment) {
		this.amountOfPayment = amountOfPayment;
	}

	public String getLoanOfficer() {
		return loanOfficer;
	}

	public void setLoanOfficer(String loanOfficer) {
		this.loanOfficer = loanOfficer;
	}

	public Date getTxDate() {
		return txDate;
	}

	public void setTxDate(Date txDate) {
		this.txDate = txDate;
	}

	public String getSourceOfPayment() {
		return sourceOfPayment;
	}

	public void setSourceOfPayment(String sourceOfPayment) {
		this.sourceOfPayment = sourceOfPayment;
	}

	public String getCollector() {
		return collector;
	}

	public void setCollector(String collector) {
		this.collector = collector;
	}

	
	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getPnNo() {
		return pnNo;
	}

	public void setPnNo(String pnNo) {
		this.pnNo = pnNo;
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

	public String getLoanProd() {
		return loanProd;
	}

	public void setLoanProd(String loanProd) {
		this.loanProd = loanProd;
	}

	public String getAccountStat() {
		return accountStat;
	}

	public void setAccountStat(String accountStat) {
		this.accountStat = accountStat;
	}

	public Integer getDays1To30No() {
		return days1To30No;
	}

	public void setDays1To30No(Integer days1To30No) {
		this.days1To30No = days1To30No;
	}

	public BigDecimal getSum1To30() {
		return sum1To30;
	}

	public void setSum1To30(BigDecimal sum1To30) {
		this.sum1To30 = sum1To30;
	}

	public BigDecimal getOutstandingBalance1To30() {
		return outstandingBalance1To30;
	}

	public void setOutstandingBalance1To30(BigDecimal outstandingBalance1To30) {
		this.outstandingBalance1To30 = outstandingBalance1To30;
	}

	public Integer getDays31To60No() {
		return days31To60No;
	}

	public void setDays31To60No(Integer days31To60No) {
		this.days31To60No = days31To60No;
	}

	public BigDecimal getSum31To60() {
		return sum31To60;
	}

	public void setSum31To60(BigDecimal sum31To60) {
		this.sum31To60 = sum31To60;
	}

	public BigDecimal getOutstandingBalance31To60() {
		return outstandingBalance31To60;
	}

	public void setOutstandingBalance31To60(BigDecimal outstandingBalance31To60) {
		this.outstandingBalance31To60 = outstandingBalance31To60;
	}

	public Integer getDays61To90No() {
		return days61To90No;
	}

	public void setDays61To90No(Integer days61To90No) {
		this.days61To90No = days61To90No;
	}

	public BigDecimal getSum61To90() {
		return sum61To90;
	}

	public void setSum61To90(BigDecimal sum61To90) {
		this.sum61To90 = sum61To90;
	}

	public BigDecimal getOutstandingBalance61To90() {
		return outstandingBalance61To90;
	}

	public void setOutstandingBalance61To90(BigDecimal outstandingBalance61To90) {
		this.outstandingBalance61To90 = outstandingBalance61To90;
	}

	public Integer getDays91To120No() {
		return days91To120No;
	}

	public void setDays91To120No(Integer days91To120No) {
		this.days91To120No = days91To120No;
	}

	public BigDecimal getSum91To120() {
		return sum91To120;
	}

	public void setSum91To120(BigDecimal sum91To120) {
		this.sum91To120 = sum91To120;
	}

	public BigDecimal getOutstandingBalance91To120() {
		return outstandingBalance91To120;
	}

	public void setOutstandingBalance91To120(BigDecimal outstandingBalance91To120) {
		this.outstandingBalance91To120 = outstandingBalance91To120;
	}

	public Integer getDays121To150No() {
		return days121To150No;
	}

	public void setDays121To150No(Integer days121To150No) {
		this.days121To150No = days121To150No;
	}

	public BigDecimal getSum121To150() {
		return sum121To150;
	}

	public void setSum121To150(BigDecimal sum121To150) {
		this.sum121To150 = sum121To150;
	}

	public BigDecimal getOutstandingBalance121To150() {
		return outstandingBalance121To150;
	}

	public void setOutstandingBalance121To150(BigDecimal outstandingBalance121To150) {
		this.outstandingBalance121To150 = outstandingBalance121To150;
	}

	public Integer getDays151To180No() {
		return days151To180No;
	}

	public void setDays151To180No(Integer days151To180No) {
		this.days151To180No = days151To180No;
	}

	public BigDecimal getSum151To180() {
		return sum151To180;
	}

	public void setSum151To180(BigDecimal sum151To180) {
		this.sum151To180 = sum151To180;
	}

	public BigDecimal getOutstandingBalance151To180() {
		return outstandingBalance151To180;
	}

	public void setOutstandingBalance151To180(BigDecimal outstandingBalance151To180) {
		this.outstandingBalance151To180 = outstandingBalance151To180;
	}

	public Integer getDays180NoPlus() {
		return days180NoPlus;
	}

	public void setDays180NoPlus(Integer days180NoPlus) {
		this.days180NoPlus = days180NoPlus;
	}

	public BigDecimal getSum180Plus() {
		return sum180Plus;
	}

	public void setSum180Plus(BigDecimal sum180Plus) {
		this.sum180Plus = sum180Plus;
	}

	public BigDecimal getOutstandingBalance180Plus() {
		return outstandingBalance180Plus;
	}

	public void setOutstandingBalance180Plus(BigDecimal outstandingBalance180Plus) {
		this.outstandingBalance180Plus = outstandingBalance180Plus;
	}

	public Date getLastTransaction() {
		return lastTransaction;
	}

	public void setLastTransaction(Date lastTransaction) {
		this.lastTransaction = lastTransaction;
	}

	public Date getNextDueDate() {
		return nextDueDate;
	}

	public void setNextDueDate(Date nextDueDate) {
		this.nextDueDate = nextDueDate;
	}

	public String getPnno() {
		return pnno;
	}

	public void setPnno(String pnno) {
		this.pnno = pnno;
	}

	public String getLoanno() {
		return loanno;
	}

	public void setLoanno(String loanno) {
		this.loanno = loanno;
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

	public BigDecimal getOriginalLoanAmount() {
		return originalLoanAmount;
	}

	public void setOriginalLoanAmount(BigDecimal originalLoanAmount) {
		this.originalLoanAmount = originalLoanAmount;
	}

	public BigDecimal getTerm() {
		return term;
	}

	public void setTerm(BigDecimal term) {
		this.term = term;
	}

	public BigDecimal getMonthlyAmozt() {
		return monthlyAmozt;
	}

	public void setMonthlyAmozt(BigDecimal monthlyAmozt) {
		this.monthlyAmozt = monthlyAmozt;
	}

	public BigDecimal getOutsBalance() {
		return outsBalance;
	}

	public void setOutsBalance(BigDecimal outsBalance) {
		this.outsBalance = outsBalance;
	}

	public Integer getInstToGo() {
		return instToGo;
	}

	public void setInstToGo(Integer instToGo) {
		this.instToGo = instToGo;
	}

	public String getAcctsts() {
		return acctsts;
	}

	public void setAcctsts(String acctsts) {
		this.acctsts = acctsts;
	}

	public Integer getLastInstPaid() {
		return lastInstPaid;
	}

	public void setLastInstPaid(Integer lastInstPaid) {
		this.lastInstPaid = lastInstPaid;
	}

	public BigDecimal getAccountReceivable() {
		return accountReceivable;
	}

	public void setAccountReceivable(BigDecimal accountReceivable) {
		this.accountReceivable = accountReceivable;
	}

	public BigDecimal getLatePaymentCharge() {
		return latePaymentCharge;
	}

	public void setLatePaymentCharge(BigDecimal latePaymentCharge) {
		this.latePaymentCharge = latePaymentCharge;
	}

	public BigDecimal getInterest() {
		return interest;
	}

	public void setInterest(BigDecimal interest) {
		this.interest = interest;
	}

	public BigDecimal getPrincipal() {
		return principal;
	}

	public void setPrincipal(BigDecimal principal) {
		this.principal = principal;
	}

	public BigDecimal getTotalAmountReceived() {
		return totalAmountReceived;
	}

	public void setTotalAmountReceived(BigDecimal totalAmountReceived) {
		this.totalAmountReceived = totalAmountReceived;
	}

	public BigDecimal getAmountToUpdate() {
		return amountToUpdate;
	}

	public void setAmountToUpdate(BigDecimal amountToUpdate) {
		this.amountToUpdate = amountToUpdate;
	}
	
	
	
}
