package com.etel.creditline;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.Tbapprovedcf;
import com.coopdb.data.TbapprovedcfId;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.creditfacility.forms.ExistingFacilityForm;
import com.etel.forms.CreditLineForm;
import com.etel.forms.ExistFacilityForm;
import com.etel.forms.FormValidation;
import com.etel.history.HistoryService;
import com.etel.history.HistoryServiceImpl;
import com.etel.inquiry.forms.InquiryForm;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class CreditLineServiceImpl implements CreditLineService {
	
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> param = HQLUtil.getMap();
	Map<String, Object> params = new HashMap<String, Object>();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CreditLineForm> getListOfCreditFacilityType(String type) {
		List<CreditLineForm> form = new ArrayList<CreditLineForm>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("type", type);
		try {
			form = (List<CreditLineForm>) dbService
					.execSQLQueryTransformer("SELECT facilitycode, facilityname, type FROM Tbcfmaintenance WHERE type=:type", params, CreditLineForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public FormValidation saveCreditLineSetupIndiv(CreditLineForm form) {
		FormValidation f = new FormValidation();
		Tbapprovedcf a = new Tbapprovedcf();
		TbapprovedcfId aa = new TbapprovedcfId();
		try {
			if (form.getCfappno() != null) {
				a.setCfappno(form.getCfappno());
				aa.setCfrefno(form.getCfappno());
				aa.setCfseqno("0");
				aa.setCfsubseqno("0");
				aa.setCflevel(0);
				a.setCfrefnoconcat(form.getCfrefnoconcat());
				a.setCftype(form.getCftype());
				a.setCfcode(form.getCfcode());
				a.setCfcurrency(form.getCfcurrency());
				a.setCfproposedamt(form.getCfproposedamt());
				a.setCfapprovedamt(form.getCfapprovedamt());
				a.setCfamount(form.getCfamount());
				a.setCfbalance(form.getCfbalance());
				a.setCfexpdt(form.getCfexpdt());
				a.setCfdtopen(form.getCfdtopen());
				a.setCfrevolving(form.getCfrevolving());
				a.setCfstatus(form.getCfstatus());
				a.setCfterm(form.getCfterm());
				a.setCftermperiod(form.getCftermperiod());
				a.setCfminrate(form.getCfminrate());
				a.setCfmaxrate(form.getCfmaxrate());
				a.setCfintrate(form.getCfintrate());
				a.setCfintrateperiod(form.getCfintrateperiod());
				a.setCfmaker(secservice.getUserName());
				a.setCifno(form.getCifno());
				a.setCifname(form.getCifname());
				a.setRemarks(form.getRemarks());
				a.setCfdatecreated(new Date());
				a.setCfcreatedby(secservice.getUserName());
				a.setId(aa);
				if (dbService.save(a)) {
					f.setFlag("success");
					f.setErrorMessage("No Error");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}
	

	@Override
	public FormValidation saveCreditLineSetupCorp(CreditLineForm form) {
		FormValidation f = new FormValidation();
		Tbapprovedcf a = new Tbapprovedcf();
		TbapprovedcfId aa = new TbapprovedcfId();
		try {
			if (form.getCfappno() != null) {
				a.setCfappno(form.getCfappno());
				aa.setCfrefno(form.getCfappno());
				aa.setCfseqno("0");
				aa.setCfsubseqno("0");
				aa.setCflevel(0);
				a.setCfrefnoconcat(form.getCfrefnoconcat());
				a.setCftype(form.getCftype());
				a.setCfcode(form.getCfcode());
				a.setCfcurrency(form.getCfcurrency());
				a.setCfproposedamt(form.getCfproposedamt());
				a.setCfapprovedamt(form.getCfapprovedamt());
				a.setCfamount(form.getCfamount());
				a.setCfbalance(form.getCfbalance());
				a.setCfexpdt(form.getCfexpdt());
				a.setCfdtopen(form.getCfdtopen());
				a.setCfrevolving(form.getCfrevolving());
				a.setCfstatus(form.getCfstatus());
				a.setCfterm(form.getCfterm());
				a.setCftermperiod(form.getCftermperiod());
				a.setCfminrate(form.getCfminrate());
				a.setCfmaxrate(form.getCfmaxrate());
				a.setCfintrate(form.getCfintrate());
				a.setCfintrateperiod(form.getCfintrateperiod());
				a.setCfmaker(secservice.getUserName());
				a.setCifno(form.getCifno());
				a.setCifname(form.getCifname());
				a.setRemarks(form.getRemarks());
				a.setCfdatecreated(new Date());
				a.setCfcreatedby(secservice.getUserName());
				a.setId(aa);
				if (dbService.save(a)) {
					f.setFlag("success");
					f.setErrorMessage("No Error");
				}
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CreditLineForm> creditLineInquiry(String cif) {
		List<CreditLineForm> form = new ArrayList<CreditLineForm>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("cifno", cif);
		try {
			form = (List<CreditLineForm>) dbService
					.execSQLQueryTransformer("SELECT CONVERT(int,rank() OVER (ORDER BY cfappno)) as id, a.cfappno, i.cifno, i.lastname , i.firstname,a.cfappno,a.cfstatus, m.customertype\r\n" + 
							"FROM TBAPPROVEDCF a LEFT JOIN\r\n" + 
							"CIFSDBRB.dbo.TBCIFINDIVIDUAL i ON a.cifno = i.cifno\r\n" + 
							"LEFT JOIN\r\n" + 
							"CIFSDBRB.dbo.TBCIFMAIN m ON i.cifno = m.cifno\r\n" + 
							"Where a.cifno=:cif", params, CreditLineForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String checkIfExistingInCreditLine(String cifno) {	
		String result = null;
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tbapprovedcf details = new Tbapprovedcf();
		try {
			if (cifno != null){
				param.put("cifno", cifno);
				details = (Tbapprovedcf) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbapprovedcf WHERE cifno=:cifno", param);
				if(details!= null){
					result = "1";
				}else{
					result = "0";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public Tbapprovedcf getCreditLineDetails(String cifno) {
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tbapprovedcf details = new Tbapprovedcf();
		try {
			if (cifno != null){
				param.put("cifno", cifno);
				details = (Tbapprovedcf) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbapprovedcf a WHERE cifno=:cifno AND a.cfexpdt > getdate()", param);
				if(details!= null){
				}
				else{
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return details;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExistFacilityForm> searchExistingFacilityByCifNo(String cifno) {
		List<ExistFacilityForm> list = new ArrayList<ExistFacilityForm>();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				list = (List<ExistFacilityForm>) dbService.execSQLQueryTransformer(
						"SELECT a.cfappno,a.cifno,a.cifname,a.cfrefnoconcat,c.facilityname as facilitytype,a.cfapprovedamt,a.cfamount,d.desc1 as cfstatus ,\r\n" + 
						"	a.cfbalance, a.cfterm, a.cftermperiod, a.cfintrate, a.cfmaker, a.cfexpdt, a.cfrevolving\r\n" + 
						"	FROM TBAPPROVEDCF a \r\n" + 
						"	LEFT JOIN Tbcfmaintenance c ON c.facilitycode=a.cftype  \r\n" + 
						"	INNER JOIN TBCODETABLE d ON d.codevalue=a.cfstatus AND d.codename = 'CFSTATUS' AND a.cfstatus = '1' \r\n" + 
						"	WHERE a.cifno=:cifno",
						param, ExistFacilityForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ExistFacilityForm> searchFacility(String cfappno, String cifno, String fullname, Integer page, Integer maxresult) {
		List<ExistFacilityForm> list = new ArrayList<ExistFacilityForm>();
		try {
			params.put("cfappno", cfappno==null?"%":"%"+cfappno+"%");
			params.put("cifno", cifno==null?"%":"%"+cifno+"%");
			params.put("fullname", fullname==null?"%":"%"+fullname+"%");
			
			String q = "EXEC spCreditLineInquiry @page="+page+", @maxresult="+maxresult+", @cfappno="+cfappno+", @cifno="+cifno+", @fullname=:fullname, @ispagingon='true', @codename = 'CFSTATUS', @cfstatus = '1' ";
			list = (List<ExistFacilityForm>) dbService.execSQLQueryTransformer(q, params, ExistFacilityForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public int searchFacilityCount(String cfappno, String cifno, String fullname) {
		Integer count = 0;
		try {
			params.put("cfappno", cfappno==null?"%":"%"+cfappno+"%");
			params.put("cifno", cifno==null?"%":"%"+cifno+"%");
			params.put("fullname", fullname==null?"%":"%"+fullname+"%");
			
			String q = "EXEC spCreditLineInquiry @page=NULL, @maxresult=NULL, @cfappno="+cfappno+", @cifno="+cifno+", @fullname=:fullname, @ispagingon='false', @codename = 'CFSTATUS', @cfstatus = '1' ";
			count = (Integer) dbService.execSQLQueryTransformer(q, params, null, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	//MAR 01-20-21
	@SuppressWarnings("unchecked")
	@Override
	public Tbapprovedcf checkStatusActive(String cifno) {
		Tbapprovedcf details = new Tbapprovedcf();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		List<Tbapprovedcf> list = new ArrayList<Tbapprovedcf>();
		Date dateNow = new Date();
		try {
			if (cifno != null){
				param.put("cifno", cifno);
				
				list = (List<Tbapprovedcf>) dbServiceCOOP.executeListHQLQuery("FROM Tbapprovedcf WHERE cifno=:cifno", param);
				if(list != null) {
					for(int x = 0; x<= list.size(); x++) {
						if(list.get(x).getCfexpdt().before(dateNow)) {
						}else {
							details = (Tbapprovedcf) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbapprovedcf WHERE cifno=:cifno", param);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return details;
	}

	//MAR 01-20-21
	@SuppressWarnings("unchecked")
	@Override
	public int totalActiveCreditLine(String cifno) {
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		List<Tbapprovedcf> list = new ArrayList<Tbapprovedcf>();
		Date dateNow = new Date();
		int totalActive = 0;
		try {
			if (cifno != null){
				param.put("cifno", cifno);
				list = (List<Tbapprovedcf>) dbServiceCOOP.executeListHQLQuery("FROM Tbapprovedcf WHERE cifno=:cifno", param);
				if(list != null) {
					for(int x = 0; x<= list.size(); x++) {
						if(list.get(x).getCfexpdt().before(dateNow)) {
						}else {
							totalActive ++;
						}
					}if(totalActive > 0) {
						totalActive = 1;
					}else {
						totalActive = 0;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalActive;
	}

}
