package com.casa.fintx.forms;

import com.coopdb.data.Tbcashposition;
import com.coopdb.data.Tbcashpositiondenom;

public class CashPositionForm {

	private Tbcashposition cashpos;
	private Tbcashpositiondenom cashdnm;
	
	public Tbcashposition getCashpos() {
		return cashpos;
	}
	public void setCashpos(Tbcashposition cashpos) {
		this.cashpos = cashpos;
	}
	public Tbcashpositiondenom getCashdnm() {
		return cashdnm;
	}
	public void setCashdnm(Tbcashpositiondenom cashdnm) {
		this.cashdnm = cashdnm;
	}
}
