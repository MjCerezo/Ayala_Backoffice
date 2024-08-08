package com.casa.fintx.forms;

import com.coopdb.data.Tbcashpositiondenom;

public class CashPositionConfirmForm {

	private String status;
	private Tbcashpositiondenom denom;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Tbcashpositiondenom getDenom() {
		return denom;
	}
	public void setDenom(Tbcashpositiondenom denom) {
		this.denom = denom;
	}
	
}
