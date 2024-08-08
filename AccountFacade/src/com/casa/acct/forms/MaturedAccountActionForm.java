package com.casa.acct.forms;

import java.math.BigDecimal;

import com.coopdb.data.Tbdeposit;

public class MaturedAccountActionForm {
	
	private Tbdeposit deposit;
	private String modeofrelease;
	private String cracctno;
	private BigDecimal payoutamt;
	
	public Tbdeposit getDeposit() {
		return deposit;
	}
	public String getModeofrelease() {
		return modeofrelease;
	}
	public String getCracctno() {
		return cracctno;
	}
	public void setDeposit(Tbdeposit deposit) {
		this.deposit = deposit;
	}
	public void setModeofrelease(String modeofrelease) {
		this.modeofrelease = modeofrelease;
	}
	public void setCracctno(String cracctno) {
		this.cracctno = cracctno;
	}
	public BigDecimal getPayoutamt() {
		return payoutamt;
	}
	public void setPayoutamt(BigDecimal payoutamt) {
		this.payoutamt = payoutamt;
	}
	
}
