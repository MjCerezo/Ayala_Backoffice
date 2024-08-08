package com.etel.ci.forms;

import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;

/**
 * CI application details. retrieves details of application by appno.
 * <br>edit/add fields if needed.
 * */
public class CIAppDetails {
	
	private String cifno;
	private String companycode;
	private String coopcode;
	
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getCompanycode() {
		return companycode;
	}
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}
	public String getCoopcode() {
		return coopcode;
	}
	public void setCoopcode(String coopcode) {
		this.coopcode = coopcode;
	}
	public CIAppDetails(){
		
	}
	
	public CIAppDetails(String appno) {
		super();
		DBService dbservice = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		if(appno != null){
			params.put("appno", appno);
			CIAppDetails details = (CIAppDetails) dbservice.execSQLQueryTransformer("SELECT cifno, companycode, coopcode FROM Tblstapp WHERE appno=:appno", params, CIAppDetails.class, 0);
			if(details != null){
				this.cifno = details.cifno;
				this.companycode = details.companycode;
				this.coopcode = details.coopcode;
			}else{
				throw new IllegalArgumentException("Problem retrieving details in lstapp.");
			}
		}else{
			throw new IllegalArgumentException("Parameter appno is null.");
		}
	}
	
}
