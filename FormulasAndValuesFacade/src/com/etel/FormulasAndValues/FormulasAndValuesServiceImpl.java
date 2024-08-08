package com.etel.FormulasAndValues;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbpersonalreference;
import com.coopdb.data.Tbapa;
import com.coopdb.data.TbapaId;
import com.coopdb.data.Tbsbl;
import com.coopdb.data.TbsblId;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.formulaandvalues.form.FormulaAndValuesForm;
import com.etel.relatedaccount.form.DepositAccountForm;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class FormulasAndValuesServiceImpl implements FormulasAndValuesService {

	private Map<String, Object> params = new HashMap<String, Object>();
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private Map<String, Object> param = HQLUtil.getMap();
	DBService dbService = new DBServiceImpl();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FormulaAndValuesForm> getAPA() {
		List<FormulaAndValuesForm> list = new ArrayList<FormulaAndValuesForm>();
		try {
			String myQuery = "SELECT id, rr4savingsdeposit, rr4termdeposit, rr4checkingdeposit, transferpoolrate FROM TBAPA";
			list = (List<FormulaAndValuesForm>) dbService.execSQLQueryTransformer(myQuery, param,FormulaAndValuesForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String saveOrUpdateAPA(Tbapa apa) {
		Tbapa d = new Tbapa();
		if (apa.getId() != null) {
			params.put("id", apa.getId());
			d = (Tbapa) dbService.executeUniqueHQLQuery("FROM Tbapa WHERE id=:id", params);
			d.setRr4savingsdeposit(apa.getRr4savingsdeposit());
			d.setRr4termdeposit(apa.getRr4termdeposit());
			d.setRr4checkingdeposit(apa.getRr4checkingdeposit());
			d.setTransferpoolrate(apa.getTransferpoolrate());

			if (dbService.saveOrUpdate(d)) {
				System.out.println("UPDATE");
				return "success";
			}
		} else {
			if (dbService.saveOrUpdate(apa)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<FormulaAndValuesForm> getSBL() {
		List<FormulaAndValuesForm> list = new ArrayList<FormulaAndValuesForm>();
		try {
			String myQuery = "SELECT id, currentratesbl, netunimpairedcapital FROM TBSBL";
			list = (List<FormulaAndValuesForm>) dbService.execSQLQueryTransformer(myQuery, param,FormulaAndValuesForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}
	@Override
	public String saveOrUpdateAPA(Tbsbl sbl) {
		
		Tbsbl d = new Tbsbl();
		if (sbl.getId() != null) {
			params.put("id", sbl.getId());
			d = (Tbsbl) dbService.executeUniqueHQLQuery("FROM Tbsbl WHERE id=:id", params);
			d.setCurrentratesbl(sbl.getCurrentratesbl());
			d.setNetunimpairedcapital(sbl.getNetunimpairedcapital());
			if (dbService.saveOrUpdate(d)) {
				System.out.println("UPDATE");
				return "success";
			}
		} else {
			if (dbService.saveOrUpdate(sbl)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}
	
}
