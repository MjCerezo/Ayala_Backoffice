package com.etel.resignation;

import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbdocchecklist;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbresign;

public interface ResignationService {

	List<Tbdocchecklist> getResignationDocuments(String txcode, String memberid, String txrefno);

	String fileResignation(String memberid, String cooponly, String bothcoopcomp, Date effectivity, Date applicationdate);

	String saveOrUpdateResignation(Tbresign resign, String memberid);

	Tbresign getMemberResign(String memberid);

	String updateDocuments(String memberid, String doccode, String txcode, int txref, Boolean hassubmit,
			Date datesubmit);

	List<Tbdocchecklist> getDocumentsforReview(String memberid, String txcode, String txrefno);

	String updateDocumentsforReview(String memberid, String doccode, String txcode, String txrefno, Boolean hasreviewed,
			Date reviewdate);

	Tbmember getTbmember(String memberid);

	Boolean validateMember(String membershipid);
}
