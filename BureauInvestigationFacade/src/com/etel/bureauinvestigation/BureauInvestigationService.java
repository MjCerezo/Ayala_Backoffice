package com.etel.bureauinvestigation;

import java.util.List;

import com.coopdb.data.Tbbapnfis;
import com.coopdb.data.Tbbiactivity;
import com.coopdb.data.Tbbicic;
import com.coopdb.data.Tbbicmap;
import com.coopdb.data.Tbbireportmain;
import com.coopdb.data.Tbbirequest;
import com.etel.bureauinvestigation.forms.AMLAWatchlistForm;
import com.etel.bureauinvestigation.forms.BAPListForm;
import com.etel.bureauinvestigation.forms.BIAccessRightsForm;
import com.etel.bureauinvestigation.forms.BIEvalForm;
import com.etel.bureauinvestigation.forms.BiActivityForm;
import com.etel.bureauinvestigation.forms.BlacklistForm;
import com.etel.bureauinvestigation.forms.CIFRecordForm;
import com.etel.forms.ReturnForm;
import com.coopdb.data.Tbevalbi;

public interface BureauInvestigationService {

	List<Tbbirequest> getListofBiRequest(String appno, String lastname, String firstname, String middlename,
			String corporatename, String customertype, String birequestid, String cifno, String requeststatus,
			Integer page, Integer maxResult, String assigneduser, Boolean viewflag);

	int getBiRequestTotal(String appno, String lastname, String firstname, String middlename, String corporatename,
			String customertype, String birequestid, String cifno, String requeststatus, String assigneduser,
			Boolean viewflag);

	String saveOrUpdateBIRequest(Tbbirequest request, String status);

	Tbbirequest getBIRequest(String requestid);

	CIFRecordForm getCIFRecord(String cifno);

	String submitBIRequest(String requestid, String status);

	boolean isBISupervisorByCompanycode(String companycode);

	BIAccessRightsForm getBiAccessRights(String requestid, String dlgType, Boolean viewflag);

	String saveOrUpdateBiReport(Tbbireportmain bireportmain, Tbbiactivity bapactivity, Tbbicmap cmapdetails,
			Tbbiactivity cmapactivity, Tbbicic cicdetails, Tbbiactivity cicactivity,
			Tbbiactivity externalblacklistactivity, Tbbiactivity internalblacklistactivity,
			Tbbiactivity externalamlawatchlistactivity, Tbbiactivity internalamlawatchlistactivity);

	ReturnForm createBiReport(String birequestid, String bireportid);

	BAPListForm getBapList(String bireportid, Boolean isFromFileUpload);

	Tbbapnfis getBapNfisRecord(Integer bapid);

	String saveOrUpdateBapNfis(Tbbapnfis bapnfis, String baptype);

	BiActivityForm getBiActivity(String bireportid);

	Tbbireportmain getBIReport(String bireportid);

	Tbbicmap getCMAP(String bireportid);

	Tbbicic getCIC(String bireportid);

	String readBapXML(String filename, String bireportid);

	String saveMatch(Integer bapid);

	String deleteMatch(Integer bapid);

	String changeBapType(String bireportid, String baptype);

	String deleteAllBAPRecord(String bireportid, String baptype);

	BIAccessRightsForm getBIReportAccessRight(String bireportid);

	String submitBiReport(String bireportid, String status, String reasonforreturn);

	List<BlacklistForm> getBlacklistRecordByCifno(String cifno);

	List<Tbbireportmain> getBiReportListByAppno(String appno);

	List<Tbbirequest> getBiRequestListByCifno(String cifno);

	List<Tbbireportmain> getBiReportListByRequestId(String birequestid);

	String generateBAPXML(String bireportid, String cifno);

	List<AMLAWatchlistForm> getAMLARecordByCifno(String cifno);

	List<BIEvalForm> getAllBIReportperAppNo(String appno, Integer evalreportid);

	BiActivityForm getBiReportResultPerReportID(String cifno, String bapbireportid, String cmapbireportid, String cicbireportid,
			String blacklistbireportid, String amlabireportid);

	String saveOrUpdateCMAP(Tbbicmap data);

	String deleteCMAP(String biID);

	List<Tbbicmap> getCMAPList(String appno, String type);

}
