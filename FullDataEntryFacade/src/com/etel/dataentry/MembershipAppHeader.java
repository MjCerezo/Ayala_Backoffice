package com.etel.dataentry;

import java.util.Date;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

public class MembershipAppHeader {

	private String membershipappid;
	private String applicantname;
	private Date applicationdate;
	private String applicanttype;
	private String applicantsource;
	private String employeeid;
	private String membershipid;
	private String stage;
	private Date encodeddate;
	private String encodedby;
	private String branch;
	//private String coopcode;
	
	// ***** Added by Kyle ***** \\
	
	private String employeeidtbmember;
	private String employmentstatus;
	private String coopcode;
	private String branchcode;
	private String branchname;
	private String chaptername;
	private String membershipclass;
	private String membershipstatus;
	private String coopname;
	
	public static MembershipAppHeader getAppHeader(String appid){
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			param.put("appid", appid);
			MembershipAppHeader h = (MembershipAppHeader)dbService.execSQLQueryTransformer(""
					+ "SELECT "
					+ "membershipappid, "
					+ "CONCAT (lastname, ', ', firstname, ' ', middlename) as applicantname, "
					+ "applicationdate, "
					+ "encodedby, "
					+ "encodeddate, "
					+ "(SELECT desc1 FROM TBCODETABLE WHERE codename = 'APPLICANTTYPE' AND codevalue = applicanttype) as applicanttype, "
					+ "applicantsource, "
					+ "employeeid, "
					+ "membershipid, "
					+ "branch, coopcode, "
					+ "(SELECT desc1 FROM TBCODETABLE WHERE codename = 'MEMBERSHIPAPPSTATUS' AND codevalue = membershipappstatus) as stage "
					+ "FROM TBMEMBERSHIPAPP WHERE membershipappid=:appid", param, MembershipAppHeader.class, 0);
			if(h != null){
				h.setEncodedby(UserUtil.getUserFullname(h.getEncodedby()));
				if(h.getApplicantsource()==null){
					h.setApplicantsource("1");
					dbService.saveOrUpdate(h);
				}
				return h;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/***** Application Header For Loan Application (Revised) *****/
	
	public static MembershipAppHeader getAppHeaderTbmember(String memid) {
		DBService srvc = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("memid", memid);
			MembershipAppHeader m = (MembershipAppHeader)srvc.execSQLQueryTransformer("SELECT employeeid, employmentstatus, "
					+ "employeeid as employeeidtbmember, "
					+ "employmentstatus as employmentstatus, "
					+ "a.coopcode, "
					+ "a.branch as branchcode, "
					+ "(SELECT coopname FROM TBCOOPERATIVE WHERE coopcode = a.coopcode) as coopname, "
					+ "(SELECT branchname FROM TBBRANCH WHERE branchcode = a.branch) as branchname, "
					+ "(SELECT chaptername FROM TBCHAPTER WHERE chaptercode = chapter) as chaptername, "
					+ "(SELECT desc1 FROM TBCODETABLE WHERE codevalue = membershipclass and codename = 'MEMBERSHIPCLASS') as membershipclass, "
					+ "(SELECT desc1 FROM TBCODETABLE WHERE codevalue = membershipstatus and codename = 'MEMBERSHIPAPPSTATUS') as membershipstatus "
					+ "FROM TBMEMBER a WHERE a.membershipid =:memid",
					params, MembershipAppHeader.class, 0);
			if (m!=null) {
				m.setEncodedby(UserUtil.getUserFullname(m.getEncodedby()));
				if (m.getApplicantsource()==null) {
					m.setApplicantsource("2");
					srvc.saveOrUpdate(m);
				}
				return m;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Date getEncodeddate() {
		return encodeddate;
	}

	public String getEncodedby() {
		return encodedby;
	}

	public String getBranch() {
		return branch;
	}

	public void setEncodeddate(Date encodeddate) {
		this.encodeddate = encodeddate;
	}

	public void setEncodedby(String encodedby) {
		this.encodedby = encodedby;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}	

	public String getMembershipappid() {
		return membershipappid;
	}

	public String getApplicantname() {
		return applicantname;
	}

	public Date getApplicationdate() {
		return applicationdate;
	}

	public String getApplicanttype() {
		return applicanttype;
	}

	public String getEmployeeid() {
		return employeeid;
	}

	public String getMembershipid() {
		return membershipid;
	}

	public String getStage() {
		return stage;
	}

	public void setMembershipappid(String membershipappid) {
		this.membershipappid = membershipappid;
	}

	public void setApplicantname(String applicantname) {
		this.applicantname = applicantname;
	}

	public void setApplicationdate(Date applicationdate) {
		this.applicationdate = applicationdate;
	}

	public void setApplicanttype(String applicanttype) {
		this.applicanttype = applicanttype;
	}

	public void setEmployeeid(String employeeid) {
		this.employeeid = employeeid;
	}

	public void setMembershipid(String membershipid) {
		this.membershipid = membershipid;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getApplicantsource() {
		return applicantsource;
	}

	public void setApplicantsource(String applicantsource) {
		this.applicantsource = applicantsource;
	}

	public String getCoopcode() {
		return coopcode;
	}

	public void setCoopcode(String coopcode) {
		this.coopcode = coopcode;
	}



	public String getEmployeeidtbmember() {
		return employeeidtbmember;
	}



	public void setEmployeeidtbmember(String employeeidtbmember) {
		this.employeeidtbmember = employeeidtbmember;
	}



	public String getEmploymentstatus() {
		return employmentstatus;
	}



	public void setEmploymentstatus(String employmentstatus) {
		this.employmentstatus = employmentstatus;
	}



	public String getBranchname() {
		return branchname;
	}



	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}



	public String getChaptername() {
		return chaptername;
	}



	public void setChaptername(String chaptername) {
		this.chaptername = chaptername;
	}



	public String getMembershipclass() {
		return membershipclass;
	}



	public void setMembershipclass(String membershipclass) {
		this.membershipclass = membershipclass;
	}



	public String getMembershipstatus() {
		return membershipstatus;
	}



	public void setMembershipstatus(String membershipstatus) {
		this.membershipstatus = membershipstatus;
	}

	public String getCoopname() {
		return coopname;
	}

	public void setCoopname(String coopname) {
		this.coopname = coopname;
	}

	public String getBranchcode() {
		return branchcode;
	}

	public void setBranchcode(String branchcode) {
		this.branchcode = branchcode;
	}
	
}
