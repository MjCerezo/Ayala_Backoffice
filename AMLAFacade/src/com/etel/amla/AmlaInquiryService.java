package com.etel.amla;

import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbamlaindividual;
import com.coopdb.data.Tbamlalistmain;
import com.coopdb.data.Tbamlanoncountries;
import com.coopdb.data.Tbamlarequest;
import com.coopdb.data.Tbcountry;
import com.etel.amla.forms.AMLANonCountriesForm;
import com.etel.amla.forms.AMLApprovalForm;
import com.etel.amla.forms.AmlaForm;
import com.etel.amla.forms.AmlaInquiryForm;

public interface AmlaInquiryService {

	List<Tbamlalistmain> searchAmla(AmlaInquiryForm form);

	String addAmlaRequest(AmlaForm form);

	String addAmlaIndiv(AmlaForm form);

	String addAmlaCorp(AmlaForm form);

	AmlaForm searchCIF(String cifno);

	List<Tbcountry> getAllCountry();

	List<Tbamlarequest> searchRequestByStatus(AMLApprovalForm form, String requestid);

	Tbamlarequest getRequestRecord(Integer requestid);

	String saveNewAmlaMain(AmlaForm form);

	String updateAmlaRequestStatus(Integer requestid, String requeststatus);

	Tbamlaindividual getIndividualRecord(String amlalistid);

//	Tbamlacorporate getCorporateRecord(String amlalistid);

	String updateNewAmlaMain(AmlaForm form);

	String updateAmlaIndiv(AmlaForm form);

	String updateAmlaCorp(AmlaForm form);

	List<Tbamlanoncountries> getAllNonCoopCountries();

	String updateAmalNonCoopCountries(AMLANonCountriesForm form);

	String addAmlaNonCoopCountries(AMLANonCountriesForm form);

	Tbamlanoncountries getAmlaNonCoopRecord(AMLANonCountriesForm form);

	String deleteAmlaNonCoop(AMLANonCountriesForm form);

	String updateDraftRequest(String middlename, String suffix, String remarks, Boolean openended, Date enddate,
			Date startdate, String country, String reference, String source, Integer requestid, String requeststatus);

}
