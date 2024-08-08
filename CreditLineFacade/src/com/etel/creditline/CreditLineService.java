package com.etel.creditline;

import java.util.List;

import com.coopdb.data.Tbapprovedcf;
import com.coopdb.data.Tbcfmaintenance;
import com.etel.creditfacility.forms.ExistingFacilityForm;
import com.etel.forms.CreditLineForm;
import com.etel.forms.ExistFacilityForm;
import com.etel.forms.FormValidation;
import com.etel.inquiry.forms.InquiryForm;

public interface CreditLineService {
	List<CreditLineForm> getListOfCreditFacilityType(String type);
	FormValidation saveCreditLineSetupIndiv (CreditLineForm form);
	FormValidation saveCreditLineSetupCorp (CreditLineForm form);
	List<CreditLineForm> creditLineInquiry(String cif);
	String checkIfExistingInCreditLine(String cifno);
	Tbapprovedcf getCreditLineDetails(String cifno);
	//MAR 01-18-21
	List<ExistFacilityForm> searchExistingFacilityByCifNo(String cifno);
	//MAR 11-09-2020
	List<ExistFacilityForm> searchFacility(String cfappno, String cifno, String fullname, Integer page, Integer maxresult);
	int searchFacilityCount (String cfappno, String cifno, String fullname);
	Tbapprovedcf checkStatusActive(String cifno);
	int totalActiveCreditLine(String cifno);
}
