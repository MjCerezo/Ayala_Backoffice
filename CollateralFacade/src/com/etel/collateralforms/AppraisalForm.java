package com.etel.collateralforms;

import com.coopdb.data.Tbappraisalauto;
import com.coopdb.data.Tbappraisaldeposits;
import com.coopdb.data.Tbappraisallivestock;
import com.coopdb.data.Tbappraisalmachine;
import com.coopdb.data.Tbappraisalrel;

public class AppraisalForm {
	private Tbappraisalauto auto;
	private Tbappraisalmachine machineries;
	private Tbappraisaldeposits holdoutdeposits;
	private Tbappraisalrel realestate;
	private Tbappraisallivestock livestock;
	
	public Tbappraisalauto getAuto() {
		return auto;
	}
	public void setAuto(Tbappraisalauto auto) {
		this.auto = auto;
	}
	public Tbappraisalmachine getMachineries() {
		return machineries;
	}
	public void setMachineries(Tbappraisalmachine machineries) {
		this.machineries = machineries;
	}
	public Tbappraisaldeposits getHoldoutdeposits() {
		return holdoutdeposits;
	}
	public void setHoldoutdeposits(Tbappraisaldeposits holdoutdeposits) {
		this.holdoutdeposits = holdoutdeposits;
	}
	public Tbappraisalrel getRealestate() {
		return realestate;
	}
	public void setRealestate(Tbappraisalrel realestate) {
		this.realestate = realestate;
	}
	public Tbappraisallivestock getLivestock() {
		return livestock;
	}
	public void setLivestock(Tbappraisallivestock livestock) {
		this.livestock = livestock;
	}
}
