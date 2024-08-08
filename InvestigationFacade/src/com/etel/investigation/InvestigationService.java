package com.etel.investigation;

import java.util.List;

import com.coopdb.data.Tbinvestigationresults;

public interface InvestigationService {

	String saveOrUpdateInvestigation(Tbinvestigationresults inv, String invtype);

	List<Tbinvestigationresults> getInvestigationResList(String appno, String cifno, String invtype);

	String generateInvestigationReport(String appno);

	Tbinvestigationresults getInvestigationResPerType(String appno, String cifno, String invtype,
			String participationcode);

}
