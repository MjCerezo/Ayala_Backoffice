package com.etel.relationship;

import java.util.List;

import com.cifsdb.data.Tbcustomerrelationship;
import com.etel.codetable.forms.CodetableForm;
import com.etel.forms.FormValidation;
import com.etel.relationship.forms.Relationshipform;

public interface RelationshipService {
//	String saveRelationship(String cifno);
	List<Relationshipform> getListOfCustRelationShip(String cifno, Boolean isconcatenated);
	FormValidation addRelationship(Tbcustomerrelationship rel, String addOrUpdateFlag);
	FormValidation deleteRelationship(Integer id);
	List<CodetableForm> getRelationshipCode();
//	String AddCustTraderef(String cifno,String tradecifno, String tradetype);
	//MAR 10-23-2020
	String AddCustTraderefRB(String cifno,String tradecifno, String tradetype);
	List<Relationshipform> getListOfCustRelationShipRB(String cifno, Boolean isconcatenated);

}
