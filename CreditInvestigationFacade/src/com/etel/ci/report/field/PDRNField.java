package com.etel.ci.report.field;

import com.coopdb.data.Tbcipdrn;
import com.coopdb.data.Tbcipdrnresidence;
import com.coopdb.data.Tbcipdrnverhighlights;

public class PDRNField {
	private Tbcipdrn pdrn;
	private Tbcipdrnresidence residence;
	private Tbcipdrnverhighlights highlights;
	
	public Tbcipdrn getPdrn() {
		return pdrn;
	}
	public void setPdrn(Tbcipdrn pdrn) {
		this.pdrn = pdrn;
	}
	public Tbcipdrnresidence getResidence() {
		return residence;
	}
	public void setResidence(Tbcipdrnresidence residence) {
		this.residence = residence;
	}
	public Tbcipdrnverhighlights getHighlights() {
		return highlights;
	}
	public void setHighlights(Tbcipdrnverhighlights highlights) {
		this.highlights = highlights;
	}
	
	
}
