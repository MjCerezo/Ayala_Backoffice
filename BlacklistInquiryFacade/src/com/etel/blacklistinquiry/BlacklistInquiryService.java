package com.etel.blacklistinquiry;

import java.util.List;

import com.coopdb.data.Tbblacklistindividual;
import com.coopdb.data.Tbblacklistmain;
import com.coopdb.data.Tbblacklistrequest;
import com.coopdb.data.Tbcountry;
import com.coopdb.data.Tbmember;
import com.etel.blacklist.forms.BlacklistApprovalForm;
import com.etel.blacklist.forms.BlacklistForm;
import com.etel.blacklist.forms.BlacklistInquiryForm;

public interface BlacklistInquiryService {

	List<Tbblacklistmain> searchBlacklist(BlacklistInquiryForm form);

	public String addBlacklistIndiv(BlacklistForm form);

	List<Tbcountry> getAllCountry();

	String addBlacklistRequest(BlacklistForm form);

	List<Tbblacklistrequest> searchRequestByStatus(BlacklistApprovalForm form, String requestid);

	Tbblacklistrequest getRequestRecord(Integer requestid);

	String updateBlacklistRequestStatus(Integer requestid, String requeststatus);

	String saveNewBlacklistMain(BlacklistForm form);

	Tbblacklistindividual getIndividualRecord(String blacklistid);

//	Tbblacklistcorporate getCorporateRecord(String blacklistid);

	String addBlacklistCorp(BlacklistForm form);

	String searchCIF(String cifno);

	String checkCIF(String cifno, String customertype);

	String updateBlacklistIndiv(BlacklistForm form);

	String updateBlacklistCorp(BlacklistForm form);

	String updateNewBlacklistMain(BlacklistForm form);

	Tbmember getCIFIndivRecord(String cifno);

//	Tbcifcorporate getCIFCorpRecord(String cifno);

	String getUser();

	String seachIndivCIF(String cifno);

	String seachCorpCIF(String cifno);

	String updateBlacklistRequestDetails(BlacklistForm form, String requestid);

}
