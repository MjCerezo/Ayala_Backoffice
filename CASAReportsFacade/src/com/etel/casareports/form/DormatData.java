/**
 * 
 */
package com.etel.casareports.form;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class DormatData {
	
	String accountNumber;
	String accountName;
	Date lastTransactionDate;
	BigDecimal accountBalance;
	BigDecimal outstandingBalance;
	Integer daysDormat;
	Integer monthsDormat;
	Integer yearsDormat;
	
	String transactionCode;
	BigDecimal previousBalance;
	BigDecimal transactionAmount;
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Date getLastTransactionDate() {
		return lastTransactionDate;
	}
	public void setLastTransactionDate(Date lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	public BigDecimal getOutstandingBalance() {
		return outstandingBalance;
	}
	public void setOutstandingBalance(BigDecimal outstandingBalance) {
		this.outstandingBalance = outstandingBalance;
	}
	public Integer getDaysDormat() {
		return daysDormat;
	}
	public void setDaysDormat(Integer daysDormat) {
		this.daysDormat = daysDormat;
	}
	public Integer getMonthsDormat() {
		return monthsDormat;
	}
	public void setMonthsDormat(Integer monthsDormat) {
		this.monthsDormat = monthsDormat;
	}
	public Integer getYearsDormat() {
		return yearsDormat;
	}
	public void setYearsDormat(Integer yearsDormat) {
		this.yearsDormat = yearsDormat;
	}
	public String getTransactionCode() {
		return transactionCode;
	}
	public void setTransactionCode(String transactionCode) {
		this.transactionCode = transactionCode;
	}
	public BigDecimal getPreviousBalance() {
		return previousBalance;
	}
	public void setPreviousBalance(BigDecimal previousBalance) {
		this.previousBalance = previousBalance;
	}
	public BigDecimal getTransactionAmount() {
		return transactionAmount;
	}
	public void setTransactionAmount(BigDecimal transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	
}
