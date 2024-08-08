package com.etel.changecifdetails;

import java.util.Date;
import java.util.List;

import com.cifsdb.data.Tbchangecifdetailsrequest;
import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifindividualrequest;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbcorporaterequest;
import com.cifsdb.data.Tbfatca;
import com.cifsdb.data.Tbothercontactrequest;
import com.etel.changecifdetails.form.ChangeCIFDetailsForm;
import com.etel.forms.FormValidation;

public interface ChangeCIFDetailsService {

	List<Tbchangecifdetailsrequest> getListofChangeCIFDetails(String lastname, String firstname,
			String middlename, String corporatename, String customertype, String requestid, String cifno, String status,
			String changetype, Integer page, Integer maxResult);
	
	Integer getChangeCifDetailsTotal(String lastname, String firstname, String middlename, String corporatename,
			String customertype, String requestid, String cifno, String status, String changetype);

	String generateRequestID(String changetype);

	Tbcifindividualrequest getIndivRequest(String cifno, String requestid);

	Tbcorporaterequest getCorpRequest(String cifno, String requestid);

	FormValidation saveCustomerName(String requeststatus, String requestid ,String cifno, String title, String lastname, String firstname, String middlename,
			String suffix, String shortname, String prevlastname, String corporatename, String tradename,
			String customertype);

	Tbchangecifdetailsrequest getChangeCIFMain(String requestid, String cifno);

	FormValidation saveIndivAddress(String requeststatus, String requestid, String cifno, Tbcifindividualrequest indivReq);

	FormValidation saveCorpAddress(String requeststatus, String requestid, String cifno, Tbcorporaterequest corpReq);

	FormValidation saveIndivContactDetails(String requeststatus, String requestid, String cifno, Tbcifindividualrequest indivReq);
	
	FormValidation saveCivilStatusSpouseInfo(String requeststatus, String requestid, String maincifno, String civilstatus, String spsCifno, String spsTitle, String spsLastname, String spsFirstname, String spsMiddlename,
			String spsSuffix, Date spsDateofbirth);

	List<Tbothercontactrequest> listOtherContactsReq(String requestid, String cifno);

	String saveOtherContactReq(Tbothercontactrequest contacts);

	String deleteOtherContactReq(Integer id);

	FormValidation saveCorpContactDetails(String requeststatus, String requestid, String cifno, Tbcorporaterequest corpReq);

	String saveRequestStatus(String requeststatus, String requestid, String cifno, String changetype, String approverremarks);

	String approveRequest(String requestid, String cifno, String changetype, String customertype, String approverremarks);
	
	//Renz
	String saveOrUpdateChangeDetails(Tbchangecifdetailsrequest ref);

	String updateCIFIndividual(Tbcifindividual ref, Tbcifmain refmain, String changetype,String remarks);

	String updateCIFFatca(Tbfatca ref, String changetype,String remarks);

	List<Tbchangecifdetailsrequest> listOfChangeDetailsHistory(String cifno, String cifname);

	List<ChangeCIFDetailsForm> listOfCIFChangeHistory(String cifno, String losLink, String cifLink);

}
