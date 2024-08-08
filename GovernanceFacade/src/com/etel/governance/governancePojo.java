package com.etel.governance;

import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbmembereventschecklist;

public class governancePojo {
	private String governancetype;
	private String membername;
	private String membershipid;
	private String membershipstatus;
	private String membershipclass;
	private String chapter;
	private Date membershipdate;
	private List<Tbmembereventschecklist> membergovernancechecklist;
	
	public String getMembername() {
		return membername;
	}
	public void setMembername(String membername) {
		this.membername = membername;
	}
	public String getMembershipid() {
		return membershipid;
	}
	public void setMembershipid(String membershipid) {
		this.membershipid = membershipid;
	}
	public String getMembershipstatus() {
		return membershipstatus;
	}
	public void setMembershipstatus(String membershipstatus) {
		this.membershipstatus = membershipstatus;
	}
	public String getMembershipclass() {
		return membershipclass;
	}
	public void setMembershipclass(String membershipclass) {
		this.membershipclass = membershipclass;
	}
	public String getChapter() {
		return chapter;
	}
	public void setChapter(String chapter) {
		this.chapter = chapter;
	}
	public Date getMembershipdate() {
		return membershipdate;
	}
	public void setMembershipdate(Date membershipdate) {
		this.membershipdate = membershipdate;
	}
	public List<Tbmembereventschecklist> getMembergovernancechecklist() {
		return membergovernancechecklist;
	}
	public void setMembergovernancechecklist(List<Tbmembereventschecklist> membergovernancechecklist) {
		this.membergovernancechecklist = membergovernancechecklist;
	}
	public String getGovernancetype() {
		return governancetype;
	}
	public void setGovernancetype(String governancetype) {
		this.governancetype = governancetype;
	}
}
