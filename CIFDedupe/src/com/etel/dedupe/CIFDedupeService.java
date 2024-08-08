package com.etel.dedupe;

import java.util.Date;
import java.util.List;

import com.etel.dedupeforms.InquiryCount;
import com.etel.dedupeforms.MembershipDedupeForm;
import com.etel.dedupeforms.dedupeform;

public interface CIFDedupeService {

	dedupeform dedupeIndiv(String lname, String fname, String mname, String suf, Date dob);

	dedupeform dedupeCorp(String businessname, Date incorporationdate, String soleProp);

	dedupeform dedupeIndividual(String lname, String fname, Date dob, String tin, String sss, String streetno,
			String subdivision, String country, String province, String city, String barangay, String postalCode, String losLink);

	dedupeform dedupeCorporate(String businessname, Date incorporationdate, String tin, String sss, String streetno,
			String subdivision, String country, String province, String city, String barangay, String postalCode, String corpOrSoleProp, String losLink);

	String checkIfExactMatch(String cifno, String lname, String fname, Date dob, String businessname, Date incorporationdate,
			String tin, String sss, String streetno, String subdivision, String country, String province, String city,
			String barangay, String postalCode);

	dedupeform dedupeIndivFinal(String lname, String fname, String mname, String suf, Date dob, Integer page,
			Integer maxresult, String customertype, String inquirytype, String businessname, Date incorporationdate);

	InquiryCount dedupeIndivFinalCount(String lname, String fname, String mname, String suf, Date dob, Integer page,
			Integer maxresult, String customertype, String inquirytype);
	
}
