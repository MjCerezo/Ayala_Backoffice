package com.etel.codetable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbcodename;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.TbcodetableId;
import com.etel.codetable.forms.AOForm;
import com.etel.codetable.forms.CodetableForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author Kevin
 */
public class CodetableServiceImpl implements CodetableService {

	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	/** Get List of Codes or Values per Codename */
	@SuppressWarnings("unchecked")
	@Override
	public List<CodetableForm> getListofCodesPerCodename(String codename) {
		List<CodetableForm> codelist = new ArrayList<CodetableForm>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("codename", codename);
		try {
			codelist = (List<CodetableForm>) dbService.execSQLQueryTransformer("SELECT codename, codevalue, desc1, desc2, remarks FROM Tbcodetable WHERE codename=:codename ORDER BY desc1 ASC", params, CodetableForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codelist;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CodetableForm> getListofCodesPerCodenameCIF(String codename) {
		List<CodetableForm> codelist = new ArrayList<CodetableForm>();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("codename", codename);
		try {
			codelist = (List<CodetableForm>) dbService.execSQLQueryTransformer("SELECT codename, codevalue, desc1, desc2, remarks FROM Tbcodetable WHERE codename=:codename ORDER BY desc1 ASC", params, CodetableForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codelist;
	}
	/** Get List of Codes or Values per Codename and Desc1 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CodetableForm> getListofCodesPerCodenameAndDesc2(String codename, String desc2) {
		List<CodetableForm> codelist = new ArrayList<CodetableForm>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("codename", codename);
		params.put("desc2", desc2);
		try {
			codelist = (List<CodetableForm>) dbService.execSQLQueryTransformer("SELECT codename, codevalue, desc1, desc2, remarks FROM Tbcodetable WHERE codename=:codename AND desc2=:desc2 ORDER BY desc2 ASC", params, CodetableForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codelist;
	}

	/** Insert new data to TBCODETABLE */
	public String addCodetable(CodetableForm form) {
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		try {
			Tbcodetable codetable = new Tbcodetable();
			TbcodetableId ctId = new TbcodetableId();
			ctId.setCodename(form.getCodename());
			ctId.setCodevalue(form.getCodevalue());
			codetable.setId(ctId);
			codetable.setDesc1(form.getDesc1());
			codetable.setDesc2(form.getDesc2());
			codetable.setRemarks(form.getRemarks());
			codetable.setCreatedby(serviceS.getUserName());
			codetable.setCreateddate(new Date());
			if (dbService.save(codetable)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/** Update data from TBCODETABLE */
	public String updateCodetable(CodetableForm form) {
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("codename", form.getCodename());
		params.put("codevalue", form.getCodevalue());
		try {
			Tbcodetable codetable = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename=:codename AND a.id.codevalue=:codevalue", params);
			if (codetable != null) {
				codetable.getId().setCodename(form.getCodename());
				codetable.getId().setCodevalue(form.getCodevalue());
				codetable.setDesc1(form.getDesc1());
				codetable.setDesc2(form.getDesc2());
				codetable.setRemarks(form.getRemarks());
				if (dbService.saveOrUpdate(codetable)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcodetable> getCodevalueList(String codename) {
		// TODO Auto-generated method stub
		List<Tbcodetable> codevaluelist = new ArrayList<Tbcodetable>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("codename", codename);
		try {
			codevaluelist = (List<Tbcodetable>) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename=:codename", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codevaluelist;
	}

	@SuppressWarnings("unchecked")
	public List<String> getAllCodenameList() {
		// TODO Get All Codenames
		List<String> codenamelist = new ArrayList<String>();
		DBService dbService = new DBServiceImpl();
		try {
			codenamelist = (List<String>) dbService.executeListHQLQuery("SELECT DISTINCT codename FROM Tbcodename WHERE iseditable = 'True' order by codename ASC", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codenamelist;
	}

	@Override
	public String deleteCodename(CodetableForm form) {
		// TODO Auto-generated method stub
		// Delete codename

		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("codename", form.getCodename());
		params.put("codevalue", form.getCodevalue());
		Tbcodetable codetable = new Tbcodetable();
		try {
			codetable = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename=:codename AND a.id.codevalue=:codevalue", params);
			if (dbService.delete(codetable)) {
				flag = "successful";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String addCodeName(String codename, String remarks, Boolean iseditable) {
		// TODO Auto-generated method stub
		// Add Codename to TBCODENAME
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("codename", codename);
		params.put("remarks", remarks);
		params.put("iseditable", iseditable);
		Tbcodename tbcodename = new Tbcodename();
		try {
			tbcodename.setCodename(codename);
			tbcodename.setRemarks(remarks);
			tbcodename.setCreatedby(serviceS.getUserName());
			tbcodename.setCreateddate(new Date());
			tbcodename.setIseditable(iseditable);
			if (dbService.save(tbcodename)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodetableForm> searchCodetable(String search) {
		// TODO Auto-generated method stub
		// Search All to TBCODETABLE
		List<CodetableForm> codelist = new ArrayList<CodetableForm>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("search", "%" + search + "%");
		try {
			codelist = (List<CodetableForm>) dbService.execSQLQueryTransformer("SELECT codename, codevalue, desc1, desc2, remarks FROM Tbcodetable WHERE codename LIKE :search or codevalue LIKE :search or desc1 LIKE :search or desc2 LIKE :search",
					params, CodetableForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codelist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodetableForm> searchCodename(String codename, String search) {
		// TODO Auto-generated method stub
		// Search with codename to TBCODETABLE
		List<CodetableForm> codelist = new ArrayList<CodetableForm>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("parameter", "%" + search + "%");
		params.put("codename", codename);
		try {
			codelist = (List<CodetableForm>) dbService.execSQLQueryTransformer("SELECT codename, codevalue, desc1, desc2, remarks FROM Tbcodetable WHERE codename=:codename AND (codevalue LIKE :search OR desc1 LIKE :search OR desc2 LIKE :search OR remarks LIKE :search)",
					params, CodetableForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codelist;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<AOForm> getListofAO(String aocode) {
		List<AOForm> codelist = new ArrayList<AOForm>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		//params.put("aocode", aocode);
		try {
			codelist = (List<AOForm>) dbService.execSQLQueryTransformer
					("SELECT companycode, aocode, lastname, firstname, middlename, suffix  FROM Tbaccountofficer", params, AOForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codelist;
	}


}
