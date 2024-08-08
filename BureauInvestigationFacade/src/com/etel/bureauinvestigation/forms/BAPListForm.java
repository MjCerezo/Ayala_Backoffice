package com.etel.bureauinvestigation.forms;

import java.util.List;

import com.coopdb.data.Tbbapnfis;

public class BAPListForm {
	private List<Tbbapnfis> adversematch;
	private List<Tbbapnfis> cardmatch;
	private List<Tbbapnfis> casematch;
	private List<Tbbapnfis> mishandledmatch;
	public List<Tbbapnfis> getAdversematch() {
		return adversematch;
	}
	public void setAdversematch(List<Tbbapnfis> adversematch) {
		this.adversematch = adversematch;
	}
	public List<Tbbapnfis> getCardmatch() {
		return cardmatch;
	}
	public void setCardmatch(List<Tbbapnfis> cardmatch) {
		this.cardmatch = cardmatch;
	}
	public List<Tbbapnfis> getCasematch() {
		return casematch;
	}
	public void setCasematch(List<Tbbapnfis> casematch) {
		this.casematch = casematch;
	}
	public List<Tbbapnfis> getMishandledmatch() {
		return mishandledmatch;
	}
	public void setMishandledmatch(List<Tbbapnfis> mishandledmatch) {
		this.mishandledmatch = mishandledmatch;
	}
	
}
