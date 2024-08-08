package com.etel.CustomerRelationship;

public interface CustomerRelationshipService {

	String saveCustRelationshipCorp(String cifno,String relCode,String relCifno);

	String deleteRelCorp(String cifno, String relCode, String relCifno,String mngempid);

	String saveCustRelParSps(String cifno);

	String deleteParSps(String cifno, String relCode, String relcifno);
	
	//MAR 10-23-2020
	String saveCustRelParSpsRB(String cifno);
	String deleteRelCorpRB(String cifno, String relCode, String relCifno,String mngempid);
}
