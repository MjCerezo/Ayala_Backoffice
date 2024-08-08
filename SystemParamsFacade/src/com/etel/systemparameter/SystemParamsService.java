package com.etel.systemparameter;

import java.util.List;

import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbreferrors;
import com.coopdb.data.Tbaccessrights;
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

public interface SystemParamsService {

	List<Tbaccessrights> getAccessRightListperModuleName(String modulename);

	String addAccessRights(AccessRightsForm accessRights);

	String deleteAccessRights(String modulename);

	String savePropertiesConfig(Tbproperties config);

	Tbproperties getProperties();

	List<Tbcasafeesandcharges> getRecordsCasaFeesAndCharges();

	List<CoopForm> getListofCoopcode();

	String addRecordsCasaFeesAndCharges(Tbcasafeesandcharges casaFees, String beingUpdated);

	List<Tbreferrors> getRefNameByReferrorType(String reftype);
	String viewImage(int id, String imgtype);
	String checkPicOrPDF(int id, String imgtype);

	String saveOrUpdateCollectionArea(Tbcollectionarea d, Boolean isChangeOrUpdated);
	List<Tbcollectionarea> listTbcollectionarea(String status, String areacode);
	String activateOrDeactivateCollectionArea(Tbcollectionarea d);

	String saveOrUpdateCollector(Tbcollector d, Boolean isChangeOrUpdated);
	List<Tbcollector> listTbcollector(String status, String areacode, String subareacode);
	String activateOrDeactivateCollector(Tbcollector d);

	List<Tbcollectionarea> listAreacode();
	List<Tbcollectionarea> listSubAreacode(String areacode);

	List<BranchSysParamsForm> listBranch();
	List<CollectionAreaForm> listArea(String branchcode);
	List<CollectionAreaForm> listSubArea(String branchcode, String areacode);
	List<CollectionAreaForm> listCollector();
	List<CollectionAreaForm> listCollectorPerSubArea(String collectorid);
	List<UserSysParamsForm> listUser(String branchcode);

	String deleteItem(Integer id, String type);

	String saveOrUpdateArea(Tbcolareamaintenance d, Boolean isChangeOrUpdated);
	String saveOrUpdateSubArea(Tbcolsubareamaintenance d, Boolean isChangeOrUpdated);
	String saveOrUpdateCollector2(Tbcollectormaintenance d, Boolean isChangeOrUpdated);
	String saveOrUpdateCollectorPerSubArea(Tbcollectorpersubarea d, Boolean isChangeOrUpdated);

	CollectionAreaForm getCollectorName(String branchcode, String areacode, String subareacode);

	String saveOrUpdateAreaSubArea(Tbcifmain d, String changetype, String remarks);

	List<CollectionAreaForm> listCollectorPerBranchcode(String branchcode);

	List<CollectionAreaForm> listCollectorForReports(String branchcode, String areacode, String subareacode);

	List<Tbcoamaintenance> getAndListTbcoa();

	String saveOrDeleteGlCode(String accountno, String desc, String type);

	List<Tbcoa> getAndListTbcoaByAcctnoAndDesc(String acctno, String desc);

	String saveOrUpdateHoliday(Tbholiday d, String type);

	List<Tbholiday> listHoliday(String nationalorlocal, String holidayname, String branchcode);

	String saveOrUpdateDeleteGovernmentContribution(Tbgovernmentcontribution d, String saveOrDel);

	List<Tbgovernmentcontribution> getTbgovernmentcontribution(String contributionType);



	
}
