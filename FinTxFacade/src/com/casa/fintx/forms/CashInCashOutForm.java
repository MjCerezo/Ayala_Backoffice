package com.casa.fintx.forms;

import java.util.List;

import com.coopdb.data.Tbcashposition;

public class CashInCashOutForm {

	private List<Tbcashposition> cashin;
	private List<Tbcashposition> cashout;
	
	public List<Tbcashposition> getCashin() {
		return cashin;
	}
	public void setCashin(List<Tbcashposition> cashin) {
		this.cashin = cashin;
	}
	public List<Tbcashposition> getCashout() {
		return cashout;
	}
	public void setCashout(List<Tbcashposition> cashout) {
		this.cashout = cashout;
	}

	
}
