package com.etel.systemparameter;

import java.util.List;

import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbreferrors;
import com.coopdb.data.Tbaccessrights;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbcasafeesandcharges;
import com.coopdb.data.Tbcoa;
import com.coopdb.data.Tbcoamaintenance;
import com.coopdb.data.Tbcolareamaintenance;
import com.coopdb.data.Tbcollectionarea;
import com.coopdb.data.Tbcollector;
import com.coopdb.data.Tbcollectormaintenance;
import com.coopdb.data.Tbcollectorpersubarea;
import com.coopdb.data.Tbcolsubareamaintenance;
import com.coopdb.data.Tbgovernmentcontribution;
import com.coopdb.data.Tbholiday;
import com.coopdb.data.Tbproperties;
import com.etel.branch.forms.BranchForm;
import com.etel.systemparameter.forms.AccessRightsForm;
import com.etel.systemparameter.forms.BranchSysParamsForm;
import com.etel.systemparameter.forms.CollectionAreaForm;
import com.etel.systemparameter.forms.CoopForm;
import com.etel.systemparameter.forms.UserSysParamsForm;
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
public class SystemParamsFacade extends JavaServiceSuperClass {
	SystemParamsService srvc = new SystemParamsServiceImpl();

	public SystemParamsFacade() {
		// super(INFO);
	}

	/** MODULENAME CODEVALUES - Display Codevalue of Modulename*/
	public List<Tbaccessrights> getAccessRightListperModuleName(String modulename) {
		return srvc.getAccessRightListperModuleName(modulename);
	}

	/** ADD ACCESS RIGHTS PER MODULE NAME*/
	public String addAccessRights(AccessRightsForm form) {
		return srvc.addAccessRights(form);
	}

	/** DELETE ACCESS RIGHTS PER MODULE NAME*/
	public String deleteAccessRights(String modulename) {
		return srvc.deleteAccessRights(modulename);
	}

	/**
	 * Save System Properties
	 * @author Kevin
	 * @param config - TBPROPERTIES TABLE
	 */
	public String savePropertiesConfig(Tbproperties config) {
		return srvc.savePropertiesConfig(config);
	}
	/**
	 * Get System Properties
	 * @author Kevin
	 */
	public Tbproperties getProperties() {
		return srvc.getProperties();
	}
	
	//added by fedric
	public List<Tbcasafeesandcharges> getRecordsCasaFeesAndCharges() {
		return srvc.getRecordsCasaFeesAndCharges();
	}
	
	public List<CoopForm> getListOfCoopcode() {
		return srvc.getListofCoopcode();
	}

	public String addRecordsCasaFeesAndCharges(Tbcasafeesandcharges casaFees,String beingUpdated) {
		return srvc.addRecordsCasaFeesAndCharges(casaFees,beingUpdated);
	}
	
    public List<Tbreferrors> getRefNameByReferrorType(String reftype) {
		return srvc.getRefNameByReferrorType(reftype);
    }
    
	 public String viewImage(int id, String imgtype){
		 return srvc.viewImage(id, imgtype);
	 }
	 
	 public String checkPicOrPDF(int id, String imgtype) {
		return srvc.checkPicOrPDF(id, imgtype);
	}
	 
	/** 10-24-2022 **/
	 
	// Collection Area Maintenance
	public String saveOrUpdateCollectionArea(Tbcollectionarea d, Boolean isChangeOrUpdated) {
		return srvc.saveOrUpdateCollectionArea(d, isChangeOrUpdated);
	}
    public List<Tbcollectionarea> listTbcollectionarea(String status, String areacode) {
		return srvc.listTbcollectionarea(status, areacode);
    }
	public String activateOrDeactivateCollectionArea(Tbcollectionarea d) {
		return srvc.activateOrDeactivateCollectionArea(d);
	}
	
	// Collectors Maintenance
	public String saveOrUpdateCollector(Tbcollector d, Boolean isChangeOrUpdated) {
		return srvc.saveOrUpdateCollector(d, isChangeOrUpdated);
	}
    public List<Tbcollector> listTbcollector(String status, String areacode, String subareacode) {
		return srvc.listTbcollector(status, areacode, subareacode);
    }
	public String activateOrDeactivateCollector(Tbcollector d) {
		return srvc.activateOrDeactivateCollector(d);
	}
	
	// Area Code / Sub-Area Code
    public List<Tbcollectionarea> listAreacode() {
		return srvc.listAreacode();
    }
    public List<Tbcollectionarea> listSubAreacode(String areacode) {
		return srvc.listSubAreacode(areacode);
    }
    
    /**New Design 11.03.2022**/
    
    // List
    public List<BranchSysParamsForm> listBranch() {
		return srvc.listBranch();
    }
    public List<CollectionAreaForm> listArea(String branchcode) {
		return srvc.listArea(branchcode);
    }
    public List<CollectionAreaForm> listSubArea(String branchcode, String areacode) {
		return srvc.listSubArea(branchcode, areacode);
    }
    public List<CollectionAreaForm> listCollector() {
		return srvc.listCollector();
    }
    public List<CollectionAreaForm> listCollectorPerSubArea(String collectorid) {
		return srvc.listCollectorPerSubArea(collectorid);
    }
    public List<UserSysParamsForm> listUser(String branchcode) {
		return srvc.listUser(branchcode);
    }
    
    // Delete
	public String deleteItem(Integer id, String type) {
		return srvc.deleteItem(id, type);
	}
	
    // Save Or Update
	public String saveOrUpdateArea(Tbcolareamaintenance d, Boolean isChangeOrUpdated) {
		return srvc.saveOrUpdateArea(d, isChangeOrUpdated);
	}
	public String saveOrUpdateSubArea(Tbcolsubareamaintenance d, Boolean isChangeOrUpdated) {
		return srvc.saveOrUpdateSubArea(d, isChangeOrUpdated);
	}
	public String saveOrUpdateCollector2(Tbcollectormaintenance d, Boolean isChangeOrUpdated) {
		return srvc.saveOrUpdateCollector2(d, isChangeOrUpdated);
	}
	public String saveOrUpdateCollectorPerSubArea(Tbcollectorpersubarea d, Boolean isChangeOrUpdated) {
		return srvc.saveOrUpdateCollectorPerSubArea(d, isChangeOrUpdated);
	}
    public CollectionAreaForm getCollectorName(String branchcode, String areacode, String subareacode) {
		return srvc.getCollectorName(branchcode, areacode, subareacode);
    }
   
    // CIF Change Profile Update
    public String saveOrUpdateAreaSubArea(Tbcifmain d, String changetype, String remarks) {
    	return srvc.saveOrUpdateAreaSubArea(d, changetype, remarks);
    }
    public List<CollectionAreaForm> listCollectorPerBranchcode(String branchcode) {
		return srvc.listCollectorPerBranchcode(branchcode);
    }
    public List<CollectionAreaForm> listCollectorForReports(String branchcode, String areacode, String subareacode) {
		return srvc.listCollectorForReports(branchcode, areacode, subareacode);
    }

    public List<Tbcoamaintenance> getAndListTbcoa () {
		return srvc.getAndListTbcoa ();
	}
    public String saveOrDeleteGlCode(String accountno, String desc, String type) {
    	return srvc.saveOrDeleteGlCode(accountno, desc, type);
    }
    public List<Tbcoa> getAndListTbcoaByAcctnoAndDesc (String acctno, String desc) {
		return srvc.getAndListTbcoaByAcctnoAndDesc (acctno, desc);
	}
    
    // Holiday Maintenance
    public String saveOrUpdateHoliday(Tbholiday d, String type) {
    	return srvc.saveOrUpdateHoliday (d, type);
    }
    public List<Tbholiday> listHoliday (String nationalorlocal, String holidayname, String branchcode) {
		return srvc.listHoliday (nationalorlocal, holidayname, branchcode);
	}
    
    public String saveOrUpdateDeleteGovernmentContribution(Tbgovernmentcontribution d, String saveOrDel) {
    	return srvc.saveOrUpdateDeleteGovernmentContribution(d, saveOrDel);
    }
    public List<Tbgovernmentcontribution> getTbgovernmentcontribution(String contributionType) {
    	return srvc.getTbgovernmentcontribution(contributionType);
    }
}

