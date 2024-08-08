package com.etel.dedupe.forms;

import java.util.List;

import com.coopdb.data.Tbamlalistmain;
import com.coopdb.data.Tbblacklistmain;
import com.coopdb.data.Tbemployee;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmemberrelationship;
import com.coopdb.data.Tbmembershipapp;

public class SearchResult {

	private List<Tbmember> membership;
	private List<Tbmembershipapp> application;
	private List<Tbemployee> employee;
	private List<Tbmemberrelationship> relatives;
	private List<Tbamlalistmain> amla;
	private List<Tbblacklistmain> blacklist;

	public List<Tbmember> getMembership() {
		return membership;
	}

	public void setMembership(List<Tbmember> membership) {
		this.membership = membership;
	}

	public List<Tbmembershipapp> getApplication() {
		return application;
	}

	public void setApplication(List<Tbmembershipapp> application) {
		this.application = application;
	}

	public List<Tbemployee> getEmployee() {
		return employee;
	}

	public void setEmployee(List<Tbemployee> employee) {
		this.employee = employee;
	}

	public List<Tbmemberrelationship> getRelatives() {
		return relatives;
	}

	public void setRelatives(List<Tbmemberrelationship> relatives) {
		this.relatives = relatives;
	}

	public List<Tbamlalistmain> getAmla() {
		return amla;
	}

	public void setAmla(List<Tbamlalistmain> amla) {
		this.amla = amla;
	}

	public List<Tbblacklistmain> getBlacklist() {
		return blacklist;
	}

	public void setBlacklist(List<Tbblacklistmain> blacklist) {
		this.blacklist = blacklist;
	}

}
