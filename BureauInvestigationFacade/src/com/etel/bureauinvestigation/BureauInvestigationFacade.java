package com.etel.bureauinvestigation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import com.etel.bureauinvestigation.forms.AMLAWatchlistForm;
import com.etel.bureauinvestigation.forms.BAPListForm;
import com.etel.bureauinvestigation.forms.BIAccessRightsForm;
import com.etel.bureauinvestigation.forms.BIEvalForm;
import com.etel.bureauinvestigation.forms.BiActivityForm;
import com.etel.bureauinvestigation.forms.BlacklistForm;
import com.etel.bureauinvestigation.forms.CIFRecordForm;
import com.etel.forms.ReturnForm;
import com.coopdb.data.Tbbapnfis;
import com.coopdb.data.Tbbiactivity;
import com.coopdb.data.Tbbicic;
import com.coopdb.data.Tbbicmap;
import com.coopdb.data.Tbbireportmain;
import com.coopdb.data.Tbbirequest;
import com.coopdb.data.Tbevalbi;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.server.DownloadResponse;
import com.wavemaker.runtime.server.ParamName;
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
public class BureauInvestigationFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */
	BureauInvestigationService srvc = new BureauInvestigationServiceImpl();

	public BureauInvestigationFacade() {
		// super(INFO);
	}

	public List<Tbbirequest> getListofBiRequest(String appno, String lastname, String firstname, String middlename,
			String corporatename, String customertype, String birequestid, String cifno, String requeststatus,
			Integer page, Integer maxResult, String assigneduser, Boolean viewflag) {
		return srvc.getListofBiRequest(appno, lastname, firstname, middlename, corporatename, customertype, birequestid,
				cifno, requeststatus, page, maxResult, assigneduser, viewflag);
	}

	public int getBiRequestTotal(String appno, String lastname, String firstname, String middlename,
			String corporatename, String customertype, String birequestid, String cifno, String requeststatus,
			String assigneduser, Boolean viewflag) {
		return srvc.getBiRequestTotal(appno, lastname, firstname, middlename, corporatename, customertype, birequestid,
				cifno, requeststatus, assigneduser, viewflag);
	}

	public String saveOrUpdateBIRequest(Tbbirequest request, String status) {
		return srvc.saveOrUpdateBIRequest(request, status);
	}

	public Tbbirequest getBIRequest(String requestid) {
		return srvc.getBIRequest(requestid);
	}

	public CIFRecordForm getCIFRecord(String cifno) {
		return srvc.getCIFRecord(cifno);
	}

	public String submitBIRequest(String requestid, String status) {
		return srvc.submitBIRequest(requestid, status);
	}

	public boolean isBISupervisorByCompanycode(String companycode) {
		return srvc.isBISupervisorByCompanycode(companycode);
	}

	public BIAccessRightsForm getBiAccessRights(String requestid, String dlgType, Boolean viewflag) {
		return srvc.getBiAccessRights(requestid, dlgType, viewflag);
	}

	public String saveOrUpdateBiReport(Tbbireportmain bireportmain, Tbbiactivity bapactivity, Tbbicmap cmapdetails,
			Tbbiactivity cmapactivity, Tbbicic cicdetails, Tbbiactivity cicactivity,
			Tbbiactivity externalblacklistactivity, Tbbiactivity internalblacklistactivity,
			Tbbiactivity externalamlawatchlistactivity, Tbbiactivity internalamlawatchlistactivity) {
		return srvc.saveOrUpdateBiReport(bireportmain, bapactivity, cmapdetails, cmapactivity, cicdetails, cicactivity,
				externalblacklistactivity, internalblacklistactivity, externalamlawatchlistactivity,
				internalamlawatchlistactivity);
	}

	public ReturnForm createBiReport(String birequestid, String bireportid) {
		return srvc.createBiReport(birequestid, bireportid);
	}

	public BAPListForm getBapList(String bireportid, Boolean isFromFileUpload) {
		return srvc.getBapList(bireportid, isFromFileUpload);
	}

	public Tbbapnfis getBapNfisRecord(Integer bapid) {
		return srvc.getBapNfisRecord(bapid);
	}

	public String saveOrUpdateBapNfis(Tbbapnfis bapnfis, String baptype) {
		return srvc.saveOrUpdateBapNfis(bapnfis, baptype);
	}

	public BiActivityForm getBiActivity(String bireportid) {
		return srvc.getBiActivity(bireportid);
	}

	public Tbbireportmain getBIReport(String bireportid) {
		return srvc.getBIReport(bireportid);
	}

	public Tbbicmap getCMAP(String bireportid) {
		return srvc.getCMAP(bireportid);
	}

	public Tbbicic getCIC(String bireportid) {
		return srvc.getCIC(bireportid);
	}

	public String readBapXML(String filename, String bireportid) {
		return srvc.readBapXML(filename, bireportid);
	}

	public String saveMatch(Integer bapid) {
		return srvc.saveMatch(bapid);
	}

	public String deleteMatch(Integer bapid) {
		return srvc.deleteMatch(bapid);
	}

	public String changeBapType(String bireportid, String baptype) {
		return srvc.changeBapType(bireportid, baptype);
	}

	public String deleteAllBAPRecord(String bireportid, String baptype) {
		return srvc.deleteAllBAPRecord(bireportid, baptype);
	}

	public BIAccessRightsForm getBIReportAccessRight(String bireportid) {
		return srvc.getBIReportAccessRight(bireportid);
	}

	public String submitBiReport(String bireportid, String status, String reasonforreturn) {
		return srvc.submitBiReport(bireportid, status, reasonforreturn);
	}

	public List<BlacklistForm> getBlacklistRecordByCifno(String cifno) {
		return srvc.getBlacklistRecordByCifno(cifno);
	}

	public List<Tbbireportmain> getBiReportListByAppno(String appno) {
		return srvc.getBiReportListByAppno(appno);
	}

	public List<Tbbirequest> getBiRequestListByCifno(String cifno) {
		return srvc.getBiRequestListByCifno(cifno);
	}

	public List<Tbbireportmain> getBiReportListByRequestId(String birequestid) {
		return srvc.getBiReportListByRequestId(birequestid);
	}

	public String generateBAPXML(String bireportid, String cifno) {
		return srvc.generateBAPXML(bireportid, cifno);
	}

	public DownloadResponse doDownload(@ParamName(name = "filename") String filename) throws IOException {
		DownloadResponse ret = new DownloadResponse();
		String urlDirectory = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("resources/generatedXML/" + filename);
		File localFile = new File(urlDirectory);
		FileInputStream fis = new FileInputStream(localFile);
		ret.setContents(fis);
		ret.setFileName(filename);
		return ret;
	}

	public List<AMLAWatchlistForm> getAMLARecordByCifno(String cifno) {
		return srvc.getAMLARecordByCifno(cifno);
	}

	public List<BIEvalForm> getAllBIReportperAppNo(String appno, Integer evalreportid) {
		return srvc.getAllBIReportperAppNo(appno, evalreportid);
	}
	
	public BiActivityForm getBiReportResultPerReportID(String cifno, String bapbireportid, String cmapbireportid, String cicbireportid, String blacklistbireportid, String amlabireportid){
		return srvc.getBiReportResultPerReportID(cifno,bapbireportid, cmapbireportid, cicbireportid, blacklistbireportid, amlabireportid);
	}
	
	public String saveOrUpdateCMAP (Tbbicmap data){
		return srvc.saveOrUpdateCMAP(data);
	}
	public String deleteCMAP (String biID){
		return srvc.deleteCMAP(biID);
	}
	public List<Tbbicmap> getCMAPList(String appno, String type) {
		return srvc.getCMAPList(appno, type);
	}	
	
}
