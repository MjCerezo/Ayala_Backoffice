package com.etel.qde;
import java.util.List;

import com.coopdb.data.Tbemployee;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmembershipapp;

public class DedupeResult {
	private List<Tbmembershipapp> listmembershipapp;
	private List<Tbemployee> listemployee;
	private List<Tbmember> listmember;
	
	public List<Tbmember> getListmember() {
		return listmember;
	}
	public void setListmember(List<Tbmember> listmember) {
		this.listmember = listmember;
	}
	public List<Tbmembershipapp> getListmembershipapp() {
		return listmembershipapp;
	}
	public void setListmembershipapp(List<Tbmembershipapp> listmembershipapp) {
		this.listmembershipapp = listmembershipapp;
	}
	public List<Tbemployee> getListemployee() {
		return listemployee;
	}
	public void setListemployee(List<Tbemployee> listemployee) {
		this.listemployee = listemployee;
	}
	
}
