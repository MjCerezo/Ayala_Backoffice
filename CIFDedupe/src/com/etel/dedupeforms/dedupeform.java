package com.etel.dedupeforms;

import java.util.List;

import com.cifsdb.data.Tbamlalistmain;
import com.cifsdb.data.Tbblacklistmain;
import com.cifsdb.data.Tbcifmain;

public class dedupeform {

	private List<Tbcifmain> cif;
	private List<Tbamlalistmain> amla;
	private List<Tbblacklistmain> blacklist;
	private List<cifdedupeform> cifform;
	private List<amladedupeform> amlaform;
	private List<blacklistdedupeform> blacklistform;
	private List<MembershipDedupeForm> membershipDedupe;
	
	public List<Tbcifmain> getCif() {
		return cif;
	}
	public void setCif(List<Tbcifmain> cif) {
		this.cif = cif;
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
	public List<cifdedupeform> getCifform() {
		return cifform;
	}
	public void setCifform(List<cifdedupeform> cifform) {
		this.cifform = cifform;
	}
	public List<amladedupeform> getAmlaform() {
		return amlaform;
	}
	public void setAmlaform(List<amladedupeform> amlaform) {
		this.amlaform = amlaform;
	}
	public List<blacklistdedupeform> getBlacklistform() {
		return blacklistform;
	}
	public void setBlacklistform(List<blacklistdedupeform> blacklistform) {
		this.blacklistform = blacklistform;
	}
	public List<MembershipDedupeForm> getMembershipDedupe() {
		return membershipDedupe;
	}
	public void setMembershipDedupe(List<MembershipDedupeForm> membershipDedupe) {
		this.membershipDedupe = membershipDedupe;
	}
	
	

}
