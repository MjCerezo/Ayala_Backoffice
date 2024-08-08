package com.etel.otherinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbcifpepinfo;
import com.cifsdb.data.Tbdosri;
import com.cifsdb.data.Tbfatca;
import com.cifsdb.data.Tbpepinfo;
import com.cifsdb.data.Tbpepq3;
import com.cifsdb.data.Tbriskprofile;
import com.etel.codetable.forms.CodetableForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.otherinfo.dosri.DosriService;
import com.etel.otherinfo.dosri.DosriServiceImpl;
import com.etel.otherinfo.dosri.forms.AffiliatesOrSubsidiaries;
import com.etel.otherinfo.dosri.forms.CommonDOSRI;
import com.etel.otherinfo.fatca.FatcaService;
import com.etel.otherinfo.fatca.FatcaServiceImpl;
import com.etel.otherinfo.fatca.forms.FATCAViewForm;
import com.etel.otherinfo.pep.PepService;
import com.etel.otherinfo.pep.PepServiceImpl;
import com.etel.otherinfo.pep.forms.PEPViewForm;
import com.etel.otherinfo.pep.forms.PresentPreviousGovEmp;
import com.etel.utils.HQLUtil;
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
public class OtherInformationFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public OtherInformationFacade() {
       super(INFO);
    }
	public String riskStatus(String risktype, String custtype, String desc, Boolean q1, Boolean q2, Boolean q3, Boolean q4, Boolean q5){
    	DBService dbService = new DBServiceImplCIF();
    	Tbriskprofile risk = new Tbriskprofile();
		Map<String, Object> params = HQLUtil.getMap();
		String result = null;
		try {

			params.put("riskprofiletype", risktype);
			params.put("customertype", custtype);
			params.put("description", desc);
			params.put("q1", q1);
			params.put("q2", q2);
			params.put("q3", q3);
			params.put("q4", q4);
			params.put("q5", q5);
			if(q3 != null){//indivDANIEL
			risk = (Tbriskprofile) dbService.executeUniqueHQLQuery("FROM Tbriskprofile WHERE "
					+ "riskprofiletype=:riskprofiletype" + " AND customertype=:customertype"
					+ " AND q1=:q1 AND q2=:q2 AND q3=:q3 AND q4=:q4 AND q5=:q5", params);
			} else {//corpQ3NotIncludedAvoidingNullPointerExceptionDANIEl
				risk = (Tbriskprofile) dbService.executeUniqueHQLQuery("FROM Tbriskprofile WHERE "
						+ "riskprofiletype=:riskprofiletype" + " AND customertype=:customertype"
						+ " AND q1=:q1 AND q2=:q2 AND q4=:q4 AND q5=:q5", params);				
			}
			if (risk != null) {
				result = risk.getResult();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
    }
    
    
    private PepService pepService = new PepServiceImpl();
    private FatcaService fatcaService = new FatcaServiceImpl();
    private DosriService dosriService = new DosriServiceImpl();
    
    public String saveOrUpdatePepInfo(Tbpepinfo info, String cifno,  Tbriskprofile risk, String empstatus) {
		return pepService.saveOrUpdatePepInfo(info, cifno, risk, empstatus);
    }
    public Tbpepinfo getPepInfo(String cifno) {
		return pepService.getPepInfo(cifno);
    }
    public List<PEPViewForm> listPEP(String cifno) {
    	return pepService.listPEP(cifno);
    }
    
    public String saveOrUpdateFatcaInfo(Tbfatca info, String businesstype) {
    	return fatcaService.saveOrUpdateFatcaInfo(info, businesstype);
    }
    
    public Tbfatca getFatcaInfo(String cifno) {
    	return fatcaService.getFatcaInfo(cifno);
    }
    
    public List<FATCAViewForm> listFATCA(String cifno) {
    	return fatcaService.listFATCA(cifno);
    }
    
    public String saveOrUpdateDosri(String cifno, Tbdosri info) {
    	return dosriService.saveOrUpdateDosri(cifno, info);
    }
    
    public Tbdosri getDosriInfo(String cifno) {
    	return dosriService.getDosriInfo(cifno);
    }
    
    public String saveFATCAStatus(String cifno) {
    	return fatcaService.saveFATCAStatus(cifno);
    }
    public String saveDosriStatus(String cifno) {
		return dosriService.saveDosriStatus(cifno);
    }
    
    public List<AffiliatesOrSubsidiaries> listAffiliates(String cifno) {
    	return dosriService.listAffiliates(cifno);
    }
    
    public List<CommonDOSRI> listCommonDOSRI(String cifno, String relationcode) {
    	return dosriService.listCommonDOSRI(cifno, relationcode);
    }
    
    public List<PresentPreviousGovEmp> listQ1(String cifno, String empstatus) {
    	return pepService.listQ1(cifno, empstatus);
    }
    
    public List<PresentPreviousGovEmp> listQ2(String cifno, String empstatus) {
    	return pepService.listQ2(cifno, empstatus);
    }
    
    public List<Tbpepq3> listQ3(String cifno) {
    	return pepService.listQ3(cifno);
    }
    public String saveQ3(Tbpepq3 q3) {
    	return pepService.saveQ3(q3);
    }
    public void deleteQ3(Integer id) {
    	pepService.deleteQ3(id);
    }
    //delete all records in q3 if q3 is unchecked
    @SuppressWarnings("unchecked")
	public void deleteAllQ3(String cifno){
		DBService dbService = new DBServiceImplCIF();
		List<Tbpepq3> list = new ArrayList<Tbpepq3>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(cifno != null){
				params.put("cifno", cifno);
				list = (List<Tbpepq3>) dbService.executeListHQLQuery("FROM Tbpepq3 WHERE cifno=:cifno", params);
				Tbpepinfo info = (Tbpepinfo) dbService.executeUniqueHQLQuery("FROM Tbpepinfo WHERE cifno=:cifno", params);
				if (info != null && !info.getQ3()) {
					if (list != null) {
							dbService.delete(list);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public List<CodetableForm> listGovernmentType() {
		return pepService.listGovernmentType();
    }
    
    //Renz
    public List<Tbcifpepinfo> listPEPQ1(String cifno) {
		return pepService.listPEPQ1(cifno);
    }
    public List<Tbcifpepinfo> listPEPQ2(String cifno) {
		return pepService.listPEPQ2(cifno);
    }
    public String deletePEP(Integer id) {
		return pepService.deletePEP(id);
	}
    public String saveOrUpdateQ1(Tbcifpepinfo ref) {
  		return pepService.saveOrUpdateQ1(ref);
  	}
    public String saveOrUpdateQ2(Tbcifpepinfo ref) {
  		return pepService.saveOrUpdateQ2(ref);
  	}
}
