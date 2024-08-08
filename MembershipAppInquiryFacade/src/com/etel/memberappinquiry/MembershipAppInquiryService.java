package com.etel.memberappinquiry;

import java.util.List;

import com.coopdb.data.Tbmembershipapp;

public interface MembershipAppInquiryService {

	List<Tbmembershipapp> searchMembershipApp(String membershipappid, String membershipappstatus, String lname,
			String fname, String mname, String encodedby, String membershipclass, String companycode, Integer page,
			Integer maxResult);

	Integer getMembershippAppTotalResult(String membershipappid, String membershipappstatus, String lname, String fname,
			String mname, String encodedby, String membershipclass, String companycode);

}
