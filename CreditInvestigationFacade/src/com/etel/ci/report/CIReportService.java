package com.etel.ci.report;


import java.util.List;

import com.coopdb.data.Tbcibankcheck;
import com.coopdb.data.Tbcibvr;
import com.coopdb.data.Tbcicreditcheck;
import com.coopdb.data.Tbcidependents;
import com.coopdb.data.Tbcievr;
import com.coopdb.data.Tbcipdrn;
import com.coopdb.data.Tbcipdrnresidence;
import com.coopdb.data.Tbcipdrnverhighlights;
import com.coopdb.data.Tbcireportmain;
import com.coopdb.data.Tbcitradecheck;
import com.coopdb.data.Tbcitraderefcorp;
import com.coopdb.data.Tbdeskciactivity;
import com.coopdb.data.Tbdeskcidetails;
import com.etel.ci.report.desk.EvrBvrActivities;
import com.etel.ci.report.field.PDRNField;

public interface CIReportService {
	public String createReport(Tbcireportmain rptmain);

	public Tbcireportmain getCIReport(String rptID);

	public String submitCIReport(String rptstatus, String rptid, String reasonforreturn);

	public String saveOrUpdateDeskCIDetails(Tbdeskcidetails d);

	public String saveOrUpdateDeskCIActivity(Tbdeskciactivity ciactivity);

	public List<Tbdeskcidetails> getDeskCIDetails(String rptid, String activity, Integer emporbusid);

	public Tbdeskciactivity getDeskCIActivity(String rptid, String activity, Integer activityID);
	
	public List<EvrBvrActivities> listEvrBvrActivities(String rptid, String activitytype);
	
	public String saveOrUpdateBankCheck(Tbcibankcheck bank, Tbdeskciactivity act);
	
	public String saveOrUpdateCreditCheck(Tbcicreditcheck credit, Tbdeskciactivity act);
	
	public String saveOrUpdateTradeCheck(Tbcitradecheck trade, Tbdeskciactivity act);
	
	public List<Tbcibankcheck> listBankCheck(String rptid);
	
	public List<Tbcicreditcheck> listCreditCheck(String rptid);
	
	public List<Tbcitradecheck> listTradeCheck(String rptid);
	
	public String saveOrUpdatePDRNField(Tbcipdrn pdrn, Tbcipdrnresidence res, Tbcipdrnverhighlights h, String rptid);
	
	public PDRNField getPDRNField(String rptid);
	
	public List<Tbcidependents> listDependents(String rptid);
	
	public String saveOrUpdateDependents(Tbcidependents dep);
	
	public String saveOrUpdateEVRField(Tbcievr evr);
	
	public String saveOrUpdateBVRField(Tbcibvr bvr);
	
	public List<Tbcievr> listEVRField(String rptid);
	
	public List<Tbcibvr> listBVRField(String rptid);
	
	public String deleteItem(Integer dependentsID, Integer employmentID, Integer businessID, Integer traderefID);
	
	public Tbcievr getEVRField(Integer eid);
	
	public Tbcibvr getBVRField(Integer bid, String rptid);
	
	public List<Tbcitraderefcorp> listTradeReference(String rptid);
	
	public String saveOrUpdateTradeReference(Tbcitraderefcorp t);
	
	public List<Tbcireportmain> getCiReportListByAppno(String appno);
	
	public List<Tbcireportmain> getCiReportListByRequestId(String rqstid);

	public String deleteCIDeskItem(Integer bankchkID, Integer creditchkID, Integer tradechkID);

	public List<Tbdeskcidetails> getDeskCIDetailsPDRN(String rptid, Integer emporbusid);

	public List<Tbdeskcidetails> getDeskCIDetailsEVR(String rptid, Integer emporbusid);

	public List<Tbdeskcidetails> getDeskCIDetailsBVR(String rptid, Integer emporbusid);

	//MAR
	public String setEvalIDForCIEvalFromEval(String appno);

}
