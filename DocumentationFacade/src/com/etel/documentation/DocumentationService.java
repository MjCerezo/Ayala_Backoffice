package com.etel.documentation;

import java.util.List;

import com.etel.documentation.forms.DocAccessRightsForm;
//import com.loansdb.data.Tbcfinsurancedetails;
import com.coopdb.data.Tbdocsperapplication;

public interface DocumentationService {

	/**
	 * -- Save or Update CF Insurance Details--
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 * */
//	String saveOrUpdateCFInsurance(Tbcfinsurancedetails insurance);

	/**
	 * --Save or Update CF Insurance Details--
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 * */
//	String deleteCFInsurance(String appno, Integer id);

	/**
	 * --Get List of CF Insurance--
	 * @author Kevin (08.25.2018)
	 * @return form  = {@link Tbcfinsurancedetails}
	 * */
//	List<Tbcfinsurancedetails> getListOfCFInsurance(String appno);

	/**
	 * --Refresh Document Checklist Per Application--
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 * */
//	String refreshDocChecklistPerApp(String appno, String cifno);
	/**
	 * --Get List of Document Checklist per Application--
	 * @author Kevin (08.25.2018)
	 * @return form  = {@link Tbdocsperapplication}
	 * */
	List<Tbdocsperapplication> getListOfDocsPerApp(String appno);
	/**
	 * -- Save or Update Doc Checklist per Application--
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 * */
	String saveOrUpdateDocsPerApp(Tbdocsperapplication docsperapp);

	/**
	 * Get details of existing documents from CIF
	 * @author DANIEL (09.01.2018)
	 * */
	String getExistingDocumentsDetailsfromCIF(String cifno, String appno);

	/**
	 * --Get Doc Access Rights--
	 * @author Kevin (09.12.2018)
	 * @return form = {@link DocAccessRightsForm}
	 * */
	DocAccessRightsForm getDocAccessRights(String appno);
	/**
	 * --Update Assigned Doc Analyst--
	 * @author Kevin (09.12.2018)
	 * @return String = success, otherwise failed
	 * */
	String updateAssignedDocAnalyst(String appno, String assigneddocanalyst);

	/**
	 * --Update Documentation Status--
	 * @author Kevin (09.12.2018)
	 * @return String = success, otherwise failed
	 * */
	String updateDocumentationStatus(String appno, Integer status);
	
	List<Tbdocsperapplication> getDocsperApp(String appno);
	String generateAndSavePNNo(String appno);
	String bookToLMS(String appno, String status);
	String updateAccountInfoStatus(String appno, String status);
	String checkLoanRelease(String appno);

}
