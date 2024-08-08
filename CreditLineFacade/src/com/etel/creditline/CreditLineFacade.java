package com.etel.creditline;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbapprovedcf;
import com.etel.forms.CreditLineForm;
import com.etel.forms.ExistFacilityForm;
import com.etel.forms.FormValidation;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class.  All
 * public methods will be exposed to the client.  Their return
 * values and parameters will be passed to the client or taken
 * from the client, respectively.  This will be a singleton
 * instance, shared between all requests. 
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL, String, Exception).
 * LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level.
 * For info on these levels, look for tomcat/log4j documentation
 */
@ExposeToClient
public class CreditLineFacade extends JavaServiceSuperClass {
	/* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
	 *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
	 */
	CreditLineService service = new CreditLineServiceImpl();


	public List<CreditLineForm> getListOfCreditFacilityType(String type) {
		return service.getListOfCreditFacilityType(type);
	}

	public FormValidation saveCreditLineSetupIndiv (CreditLineForm form)
	{
		return service.saveCreditLineSetupIndiv(form);
	}
	public FormValidation saveCreditLineSetupCorp (CreditLineForm form)
	{
		return service.saveCreditLineSetupCorp(form);
	}

	//MAR 10-30-2020
	public List<CreditLineForm> creditLineInquiry(String cif) {
		return service.creditLineInquiry(cif);
	} 

	public String checkIfExistingInCreditLine(String cifno) {
		return service.checkIfExistingInCreditLine(cifno);
	}

	public Tbapprovedcf getCreditLineDetails(String cifno) {
		return service.getCreditLineDetails(cifno);
	}

	public Date addDays(Date date, int days)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); //minus number would decrement the days
		return cal.getTime();
	}
	//MAR 01-18-21
	public List<ExistFacilityForm> searchExistingFacilityByCifNo(String cifno) {
		return service.searchExistingFacilityByCifNo(cifno);
	}
	//MAR  01-18-21
	public List<ExistFacilityForm> searchFacility (String cfappno, String cifno, String fullname, Integer page, Integer maxresult){
		return service.searchFacility(cfappno, cifno, fullname, page, maxresult);
	}
	public int searchFacilityCount (String cfappno, String cifno, String fullname){
		return service.searchFacilityCount(cfappno, cifno, fullname);
	}
	//MAR 01-20-21
	public Tbapprovedcf checkStatusActive(String cifno) {
		return service.checkStatusActive(cifno);
	}
	public int totalActiveCreditLine(String cifno) {
		return service.totalActiveCreditLine(cifno);
	}
}
