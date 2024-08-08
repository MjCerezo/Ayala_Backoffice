package com.etel.resignation;

import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbdocchecklist;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbresign;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class. All public methods will be exposed to
 * the client. Their return values and parameters will be passed to the client
 * or taken from the client, respectively. This will be a singleton instance,
 * shared between all requests.
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL,
 * String, Exception). LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to
 * modify your log level. For info on these levels, look for tomcat/log4j
 * documentation
 */
@ExposeToClient
public class ResignationFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */
	private ResignationService service = new ResignationServiceImpl();

	
	public List<Tbdocchecklist> getResignationDocuments(String txcode, String memberid, String txrefno) {
		return service.getResignationDocuments(txcode, memberid, txrefno);
	}
	
	public String fileResignation(String memberid, String cooponly, String bothcoopcomp, Date effectivity, Date applicationdate){
		return service.fileResignation(memberid, cooponly, bothcoopcomp, effectivity, applicationdate);
	}
	
	public ResignationHeader getMemberdetails(String memberid, String stage){
		return ResignationHeader.getMemberdetails(memberid, stage);
	}
	
	public memberOtherInfo getBranchandResigningFrom(String memberid){
		return memberOtherInfo.getBranchandResigningFrom(memberid);
	}
	
	public String saveOrUpdateResignation(Tbresign resign, String memberid){
		return service.saveOrUpdateResignation(resign, memberid);
	}
	
	public Tbresign getMemberResign(String memberid){
		return service.getMemberResign(memberid);
	}
	
	public String updateDocuments(String memberid, String doccode, String txcode, int txref, Boolean hassubmit,Date datesubmit){
		return service.updateDocuments(memberid, doccode, txcode, txref, hassubmit, datesubmit);
	}
	
	public List<Tbdocchecklist> getDocumentsforReview(String memberid, String txcode, String txrefno){
		return service.getDocumentsforReview(memberid, txcode, txrefno);
	}
	
	public String updateDocumentsforReview(String memberid, String doccode, String txcode, String txrefno, Boolean hasreviewed, Date reviewdate){
		return service.updateDocumentsforReview(memberid, doccode, txcode, txrefno, hasreviewed, reviewdate);
	}
	
	public Tbmember getTbmember(String memberid){
		return service.getTbmember(memberid);
	}
	
	public Boolean validateMember(String membershipid){
		return service.validateMember(membershipid);
	}
	
}
