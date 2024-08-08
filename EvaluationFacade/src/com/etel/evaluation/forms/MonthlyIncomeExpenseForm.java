package com.etel.evaluation.forms;

import java.math.BigDecimal;
import java.util.List;

import com.coopdb.data.Tbevalmonthlyexpense;
import com.coopdb.data.Tbevalmonthlyincome;

public class MonthlyIncomeExpenseForm {
	
	//Monthly Income
	private List<Tbevalmonthlyincome> monthlyIncomeList;
	private BigDecimal totalapplicantincome = BigDecimal.ZERO;
	private BigDecimal totalspouseincome = BigDecimal.ZERO;
	private BigDecimal totalcomakerincome = BigDecimal.ZERO;
	private BigDecimal totalcombinedgrossincome = BigDecimal.ZERO;
	
	//Monthly Expense
	private List<Tbevalmonthlyexpense> monthlyExpenseList;
	private BigDecimal totalapplicantexpense = BigDecimal.ZERO;
	private BigDecimal totalspouseexpense = BigDecimal.ZERO;
	private BigDecimal totalcomakerexpense = BigDecimal.ZERO;
	private BigDecimal totalcombinedgrossexpense = BigDecimal.ZERO;
	
	public List<Tbevalmonthlyincome> getMonthlyIncomeList() {
		return monthlyIncomeList;
	}
	public void setMonthlyIncomeList(List<Tbevalmonthlyincome> monthlyIncomeList) {
		this.monthlyIncomeList = monthlyIncomeList;
	}
	public BigDecimal getTotalapplicantincome() {
		return totalapplicantincome;
	}
	public void setTotalapplicantincome(BigDecimal totalapplicantincome) {
		this.totalapplicantincome = totalapplicantincome;
	}
	public BigDecimal getTotalspouseincome() {
		return totalspouseincome;
	}
	public void setTotalspouseincome(BigDecimal totalspouseincome) {
		this.totalspouseincome = totalspouseincome;
	}
	public BigDecimal getTotalcomakerincome() {
		return totalcomakerincome;
	}
	public void setTotalcomakerincome(BigDecimal totalcomakerincome) {
		this.totalcomakerincome = totalcomakerincome;
	}
	public BigDecimal getTotalcombinedgrossincome() {
		return totalcombinedgrossincome;
	}
	public void setTotalcombinedgrossincome(BigDecimal totalcombinedgrossincome) {
		this.totalcombinedgrossincome = totalcombinedgrossincome;
	}
	public List<Tbevalmonthlyexpense> getMonthlyExpenseList() {
		return monthlyExpenseList;
	}
	public void setMonthlyExpenseList(List<Tbevalmonthlyexpense> monthlyExpenseList) {
		this.monthlyExpenseList = monthlyExpenseList;
	}
	public BigDecimal getTotalapplicantexpense() {
		return totalapplicantexpense;
	}
	public void setTotalapplicantexpense(BigDecimal totalapplicantexpense) {
		this.totalapplicantexpense = totalapplicantexpense;
	}
	public BigDecimal getTotalspouseexpense() {
		return totalspouseexpense;
	}
	public void setTotalspouseexpense(BigDecimal totalspouseexpense) {
		this.totalspouseexpense = totalspouseexpense;
	}
	public BigDecimal getTotalcomakerexpense() {
		return totalcomakerexpense;
	}
	public void setTotalcomakerexpense(BigDecimal totalcomakerexpense) {
		this.totalcomakerexpense = totalcomakerexpense;
	}
	public BigDecimal getTotalcombinedgrossexpense() {
		return totalcombinedgrossexpense;
	}
	public void setTotalcombinedgrossexpense(BigDecimal totalcombinedgrossexpense) {
		this.totalcombinedgrossexpense = totalcombinedgrossexpense;
	}

}
