package com.etel.memberappinquiry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbmembershipapp;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.util.HQLUtil;

public class MembershipAppInquiryServiceImpl implements MembershipAppInquiryService {
	
	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmembershipapp> searchMembershipApp(String membershipappid, String membershipappstatus, String lname,
			String fname, String mname, String encodedby, String membershipclass, String companycode, Integer page,
			Integer maxResult) {
		// TODO Auto-generated method stub
		List<Tbmembershipapp> list = new ArrayList<Tbmembershipapp>();
		StringBuilder query = new StringBuilder();
		try {
			if (membershipappid == null && membershipappstatus == null && lname == null && fname == null && mname == null
					&& encodedby == null && membershipclass == null && companycode == null) {
			} else {
				query.append("SELECT membershipappid, membershipid, membershipappstatus, oldemployeeid, newemployeeid, isreturnee, "
						+ "isregular, applicanttype, applicationdate, lastname, firstname, middlename, suffix, title, shortname, "
						+ "nationality, civilstatus, gender, dateofbirth, age, placeofbirth, countryofbirth, bloodtype, heightfeet, "
						+ "heightinches, weight, religion, socialaffiliation, highesteducation, streetnoname1, subdivision1, barangay1, "
						+ "stateprovince1, city1, region1, country1, postalcode1, occupiedsince1, ownershiptype1, fulladdress1, "
						+ "sameaspermanentaddress, streetnoname2, subdivision2, barangay2, stateprovince2, city2, region2, country2, "
						+ "postalcode2, fulladdress2, occupiedsince2, ownershiptype2, homephoneareacode, homephoneno, mobilephoneareacode, "
						+ "mobilephoneno, officephoneareacode, officephoneno, schoolpostgraduate, coursepostgraduate, postgradyear, schoolcollege, "
						+ "coursecollege, collegegradyear, isstockholder, isdirector, salary, businessincome, otherincome, spousesalary, "
						+ "spousebusinesssalary, spouseotherincome, spouselastname, spousefirstname, spousemiddlename, spousemaidenname, "
						+ "spousesuffix, tin, sss, gsis, areacodephone, phoneno, emailaddress, idtype, idno, issuedate, expirydate, "
						+ "chaptercode, encodedby, encodeddate, edcomapprover, edcomapprovalstatus, edcomappstatusdate, edcomapproverremarks, "
						+ "boardapprover, boardapprovalstatus, boardappstatusdate, boardapproverremarks, assignedto, assigneddate, cashier, "
						+ "paymentapprovaldate, remarks, recommendedby, recommendationdate, reviewedby, revieweddate, issameasapplicant, "
						+ "spousestreetnoname, spousesubdivision, spousebarangay, spousestateprovince, spousecity, spouseregion, spousecountry, "
						+ "spousepostalcode, spousefulladdress, spouseoccupiedsince, spouseownershiptype, issameasapplicantmother, "
						+ "motherstreetnoname, mothersubdivision, motherbarangay, motherstateprovince, mothercity, motherregion, "
						+ "mothercountry, motherpostalcode, motherfulladdress, motheroccupiedsince, motherownershiptype, motherlastname, "
						+ "motherfirstname, mothermiddlename, mothermaidenname, mothersuffix, issameasapplicantfather, fatherstreetnoname, "
						+ "fathersubdivision, fatherbarangay, fatherstateprovince, fathercity, fatherregion, fathercountry, fatherpostalcode, "
						+ "fatherfulladdress, fatheroccupiedsince, fatherownershiptype, fatherlastname, fatherfirstname, fathermiddlename, "
						+ "fathersuffix, referroname, referrorfulladdress, ismemberofothercoop, isspousemember, isfathermember, ismothermember, "
						+ "statusdate, boardresno, membershipclass, withmoa, branch, primaryschoolname, primaryyrgrad, secondschoolname, "
						+ "secondyrgrad, tertiaryschoolname, tertiarycourse, tertiaryyrgrad, orno, capconpledge, savingspledge, applicantsource, "
						+ "employeeid, sharecapital, noofshares, shareparvalue, attendedseminar, seminardate, mothermembershipid, fathermembershipid, "
						+ "spousemembershipid, coopcode, companycode, spousedob, spousetitle, motherdob, mothertitle, fatherdob, fathertitle, capconpledgeamt, "
						+ "noofshare, totalcapcon, capcontermno, capcontermperiod, capconpycycle, savingspledgeamt, savingspycycle, reviewremarks, returnremarks, "
						+ "membershipfee, ispaid, txrefno, groupcode, servicestatus, accountofficer, membershipstatus, originatingbranch, agentcode, employeeavailable "
						+ "FROM Tbmembershipapp WHERE membershipappid IS NOT NULL");
				if (membershipappid != null) {
					params.put("membershipappid", "%" + membershipappid + "%'");
					query.append(" AND membershipappid LIKE :membershipappid");
				}
				if (membershipappstatus != null) {
					params.put("membershipappstatus", membershipappstatus);
					query.append(" AND membershipappstatus=:membershipappstatus");
				}
				if (membershipappid != null) {
					params.put("lastname", "%" + lname + "%'");
					query.append(" AND lastname LIKE :lastname");
				}
				if (fname != null) {
					params.put("firstname", "%" + fname + "%'");
					query.append(" AND firstname LIKE :firstname");
				}
				if (mname != null) {
					params.put("middlename", "%" + mname + "%'");
					query.append(" AND middlename LIKE :middlename");
				}
				if (encodedby != null) {
					params.put("encodedby", encodedby);
					query.append(" AND encodedby=:encodedby");
				}
				if (membershipclass != null) {
					params.put("membershipclass", membershipclass);
					query.append(" AND membershipclass=:membershipclass");
				}
				if (companycode != null) {
					params.put("companycode", companycode);
					query.append(" AND companycode=:companycode");
				}
				list = (List<Tbmembershipapp>) dbService.execSQLQueryTransformerListPagination(query.toString(), params, Tbmembershipapp.class, page, maxResult);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Integer getMembershippAppTotalResult(String membershipappid, String membershipappstatus, String lname,
			String fname, String mname, String encodedby, String membershipclass, String companycode) {
		// TODO Auto-generated method stub
		Integer total = 0;
		StringBuilder query = new StringBuilder();
		try {
			if (membershipappid == null && membershipappstatus == null && lname == null && fname == null && mname == null
					&& encodedby == null && membershipclass == null && companycode == null) {
			} else {
				query.append("SELECT COUNT(*) FROM Tbmembershipapp WHERE membershipappid IS NOT NULL");
				if (membershipappid != null) {
					params.put("membershipappid", "%" + membershipappid + "%'");
					query.append(" AND membershipappid LIKE :membershipappid");
				}
				if (membershipappstatus != null) {
					params.put("membershipappstatus", membershipappstatus);
					query.append(" AND membershipappstatus=:membershipappstatus");
				}
				if (membershipappid != null) {
					params.put("lastname", "%" + lname + "%'");
					query.append(" AND lastname LIKE :lastname");
				}
				if (fname != null) {
					params.put("firstname", "%" + fname + "%'");
					query.append(" AND firstname LIKE :firstname");
				}
				if (mname != null) {
					params.put("middlename", "%" + mname + "%'");
					query.append(" AND middlename LIKE :middlename");
				}
				if (encodedby != null) {
					params.put("encodedby", encodedby);
					query.append(" AND encodedby=:encodedby");
				}
				if (membershipclass != null) {
					params.put("membershipclass", membershipclass);
					query.append(" AND membershipclass=:membershipclass");
				}
				if (companycode != null) {
					params.put("companycode", companycode);
					query.append(" AND companycode=:companycode");
				}
				total = (Integer) dbService.executeUniqueSQLQuery(query.toString(), params);
				if(total != null && total > 0){
					return total;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

}
