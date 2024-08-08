package com.etel.collateralforms;

import com.loansdb.data.Tbcollateralgroupmain;

public class DedupeCollateralFormGroup {

	private String flag;
	private Tbcollateralgroupmain main;
	
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}	
	public void setMain(Tbcollateralgroupmain main) {
		this.main = main;
	}
}
