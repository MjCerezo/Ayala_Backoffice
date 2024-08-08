package com.etel.instruction.forms;

import java.util.List;

public class InvestigationFormList {
	private List<InvestigationForm> individual;
	private List<InvestigationForm> corporate;
	private List<InvestigationForm> single;
	private List<InvestigationForm> group;
	
	public List<InvestigationForm> getSingle() {
		return single;
	}
	public List<InvestigationForm> getGroup() {
		return group;
	}
	public void setSingle(List<InvestigationForm> single) {
		this.single = single;
	}
	public void setGroup(List<InvestigationForm> group) {
		this.group = group;
	}
	public List<InvestigationForm> getIndividual() {
		return individual;
	}
	public void setIndividual(List<InvestigationForm> individual) {
		this.individual = individual;
	}
	public List<InvestigationForm> getCorporate() {
		return corporate;
	}
	public void setCorporate(List<InvestigationForm> corporate) {
		this.corporate = corporate;
	}

}
