package com.etel.accessrights;

import java.util.List;

import com.etel.accessrights.forms.LAAccessRightsForm;
import com.etel.accessrights.forms.ReadOnlyOrDisable;
import com.etel.accessrights.forms.RequestForm;
import com.etel.accessrights.forms.StatusAndRoles;
import com.etel.role.forms.AccessRightsForm;

public interface AccessRightsService {
	String addAccessRights(AccessRightsForm form);

	String updateAccessRights(AccessRightsForm form);

	List<AccessRightsForm> searchAccess(String parameter);

	List<AccessRightsForm> searchAccessWithModulename(String parameter, String modulename);

	String deleteAccessRights(String accessname);

	String updateProjectSecurityXML();

	String getOfficer();

	LAAccessRightsForm getLoanApplicationAccess(String appno);

	String completeInvestigationAccess(String appno);

	Boolean getDocumentInputLoansAccess(String appno);
	
	StatusAndRoles getStatusAndRoles(String cifno);
	
	
	ReadOnlyOrDisable disabledOrReadOnly(String cifno);
	
	//renz
	RequestForm getRequestAccessRight(String cifno, String requestid, String changetype);

	LAAccessRightsForm getLASAccess(String appno, Integer applicationtype);

	//12-11-2020
	public List<AccessRightsForm> listAccessRightsByAccessType(String module, String accesstype);
	public List<AccessRightsForm> listAccessRights(String module);
}
