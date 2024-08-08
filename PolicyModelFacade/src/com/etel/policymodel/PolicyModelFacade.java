package com.etel.policymodel;

import java.util.ArrayList;
import java.util.List;

import com.coopdb.data.Tbpolicygroup;
import com.coopdb.data.Tbpolicyitems;
import com.coopdb.data.Tbpolicymodel;
import com.coopdb.data.Tbpolicyoperandsperitem;
import com.etel.scoremodel.forms.PolicyItemsForm;
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
public class PolicyModelFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	private PolicyModelService pmSrvc = new PolicyModelServiceImpl();

	public String addModel(String modelname, String modeldesc){
    	return pmSrvc.addModel(modelname, modeldesc);
    }
    
	public String addPolicyCharacteristic(String modelno, String fielddesc, String tbcode, String fieldname,
			String datatype, String multi, String groupname, String codename, String dbcode, String apprefno) {
		return pmSrvc.addPolicyCharacteristic(modelno, fielddesc, tbcode, fieldname, datatype, multi, groupname,
				codename, dbcode, apprefno);
	}

	public String addPolicyCharacteristicDetail(String policykey, String oper1, String value1, String oper2, String value2, String remarks, String approvallevel, String field){
    	return pmSrvc.addPolicyCharacteristicDetail(policykey, oper1, value1, oper2, value2, remarks, approvallevel, field);
    
    }
    
    public List<Tbpolicymodel> getModelList(){
    	return pmSrvc.getModelList();
    }
    
    public List<PolicyItemsForm> getPolicyCharacteristic(String modelno){
    	List<PolicyItemsForm> list = new ArrayList<PolicyItemsForm>();
    	list = pmSrvc.getPolicyCharacteristic(modelno);
    	return list;
    }
    
    public List<Tbpolicyoperandsperitem> getPolicyCharacteristicDetail(String scKey){
    	return pmSrvc.getPolicyCharacteristicDetail(scKey);
    }
    
    public String updatePolicyCharacteristic(Tbpolicyitems pol){
    	return pmSrvc.updatePolicyCharacteristic(pol);
    }
    
    public String updatePolicyCharacteristicDetail(Tbpolicyoperandsperitem pol){
    	return pmSrvc.updatePolicyCharacteristicDetail(pol);
    }
    
    public String deleteModel(String modelno){
    	return pmSrvc.deleteModel(modelno);
    }
    
    public String deleteCharac(String policykey){
    	return pmSrvc.deleteCharac(policykey);
    }
    
    public String deleteCharacDet(int id){
    	return pmSrvc.deleteCharacDet(id);
    }
    
    public String duplicateModel(String modelname, String modeldesc, String dupmodelno){
    	return pmSrvc.duplicateModel(modelname, modeldesc, dupmodelno);
    }
    
    public String updatePolicyModel(Tbpolicymodel model){
    	return pmSrvc.updatePolicyModel(model);
    }
    
    public List<Tbpolicygroup> getGroupList(String modelno){
    	return pmSrvc.getGroupList(modelno);
    }
    
    public List<String> getGroupListByName(String modelno){
    	return pmSrvc.getGroupListByName(modelno);
    }
    
    public List<Tbpolicyoperandsperitem> getBINByName(String groupname){
    	return pmSrvc.getBINByName(groupname);
    }
    
    public String saveCombinationKey(Tbpolicygroup group){
    	return pmSrvc.saveCombinationKey(group);
    }
    
    public String updateGroup(Tbpolicygroup group){
    	return pmSrvc.updateGroup(group);
    }
    
    public String deleteGroupKey(Tbpolicygroup group){
    	return pmSrvc.deleteGroupKey(group);
    }
}
