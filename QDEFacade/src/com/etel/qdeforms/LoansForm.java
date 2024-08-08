package com.etel.qdeforms;

import java.util.List;

import com.coopdb.data.Tbloans;

public class LoansForm {
	
	private List<Tbloans> borrower;
	private List<Tbloans> comaker;
	
	
	public List<Tbloans> getBorrower() {
		return borrower;
	}
	public List<Tbloans> getComaker() {
		return comaker;
	}
	public void setBorrower(List<Tbloans> borrower) {
		this.borrower = borrower;
	}
	public void setComaker(List<Tbloans> comaker) {
		this.comaker = comaker;
	}
	
	
	
	

}
