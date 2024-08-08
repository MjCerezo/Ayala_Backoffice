package com.etel.policymodel;

import java.util.List;

import com.coopdb.data.Tbpolicygroup;
import com.coopdb.data.Tbpolicyitems;
import com.coopdb.data.Tbpolicymodel;
import com.coopdb.data.Tbpolicyoperandsperitem;
import com.etel.scoremodel.forms.PolicyItemsForm;

public interface PolicyModelService {

	String addModel(String modelname, String modeldesc);

	String addPolicyCharacteristic(String modelno, String fielddesc, String tbcode, String fieldname, String datatype,
			String multi, String groupname, String codename, String dbcode, String apprefno);

	String addPolicyCharacteristicDetail(String policykey, String oper1, String value1, String oper2, String value2,
			String remarks, String approvallevel, String field);

	List<Tbpolicymodel> getModelList();

	List<PolicyItemsForm> getPolicyCharacteristic(String modelno);

	List<Tbpolicyoperandsperitem> getPolicyCharacteristicDetail(String scKey);

	String updatePolicyCharacteristic(Tbpolicyitems pol);

	String updatePolicyCharacteristicDetail(Tbpolicyoperandsperitem pol);

	String deleteModel(String modelno);

	String deleteCharac(String policykey);

	String deleteCharacDet(int id);

	String duplicateModel(String modelname, String modeldesc, String dupmodelno);

	String updatePolicyModel(Tbpolicymodel model);

	List<Tbpolicygroup> getGroupList(String modelno);

	List<String> getGroupListByName(String modelno);

	List<Tbpolicyoperandsperitem> getBINByName(String groupname);

	String saveCombinationKey(Tbpolicygroup group);

	String updateGroup(Tbpolicygroup group);

	String deleteGroupKey(Tbpolicygroup group);
	
	

}
