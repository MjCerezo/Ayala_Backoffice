package com.etel.qib;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbcodetable;
import com.cifsdb.data.Tbqibhistory;
import com.cifsdb.data.Tbqibinfo;
import com.etel.codetable.forms.CodetableForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.forms.FormValidation;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

public class QIBServiceImpl implements QIBService {

	@Override
	public FormValidation saveOrUpdateQIB(Tbqibinfo info, String cifno) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		FormValidation form = new FormValidation();
		form.setFlag("failed");
		try {
			if (cifno != null) {
				params.put("cifno", cifno);
		
//				// Save qib checkbox first				
//				if(cifno.substring(0, 1).equals("1")){
//					// Indiv
//					Tbcifindividual indiv = (Tbcifindividual) dbService.executeUniqueHQLQuery("FROM Tbcifindividual WHERE cifno=:cifno", params);
//					if(indiv!=null){	
//						if(indiv.getIsqualifiedinvestor()==null || indiv.getIsqualifiedinvestor().equals(false)){
//							System.out.println(">>>>>> QIB INDIV BEFORE <<<<<<< " + indiv.getIsqualifiedinvestor());		
//							indiv.setIsqualifiedinvestor(true);
//							dbService.saveOrUpdate(indiv);				
//							System.out.println(">>>>>> QIB INDIV AFTER <<<<<<< " + indiv.getIsqualifiedinvestor());								
//						}
//					}	
//				}else{
//					// Corp
//					Tbcifcorporate corp = (Tbcifcorporate) dbService.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno=:cifno", params);
//					if(corp!=null){	
//						if(corp.getIsqualifiedinvestor()==null || corp.getIsqualifiedinvestor().equals(false)){
//							System.out.println(">>>>>> QIB CORP BEFORE <<<<<<< " + corp.getIsqualifiedinvestor());		
//							corp.setIsqualifiedinvestor(true);
//							dbService.saveOrUpdate(corp);				
//							System.out.println(">>>>>> QIB CORP AFTER <<<<<<< " + corp.getIsqualifiedinvestor());								
//						}
//					}						
//				}
				
					// Save qib info
					Tbqibinfo i = getQIBInfo(cifno);
					if (i != null) {
						i.setRegistrationdate(info.getRegistrationdate());
						i.setRegistrationnumber(info.getRegistrationnumber());
						i.setValidityyears(info.getValidityyears());
						i.setValiditydate(info.getValiditydate());
						i.setEvaluatormarketing(info.getEvaluatormarketing());
						i.setPositionmarketing(info.getPositionmarketing());
						i.setEvaluationdatemarketing(info.getEvaluationdatemarketing());
						i.setEvaluatorcompliance(info.getEvaluatorcompliance());
						i.setPositioncompliance(info.getPositioncompliance());
						i.setEvaluationdatecompliance(info.getEvaluationdatecompliance());
						i.setCurrentnetworth(info.getCurrentnetworth());
						i.setKeyobjective1(info.getKeyobjective1());
						i.setKeyobjective2(info.getKeyobjective2());
						i.setKeyobjective3(info.getKeyobjective3());
						i.setKeyobjective4(info.getKeyobjective4());
						i.setKeyobjective5(info.getKeyobjective5());
						i.setKeyobjectiveothers(info.getKeyobjectiveothers());
						i.setPortfolioinv1(info.getPortfolioinv1());
						i.setPortfolioinv2(info.getPortfolioinv2());
						i.setPortfolioinv3(info.getPortfolioinv3());
						i.setPortfolioinv4(info.getPortfolioinv4());
						i.setPortfolioinv5(info.getPortfolioinv5());
						i.setPortfolioinv6(info.getPortfolioinv6());
						i.setPortfolioinv7(info.getPortfolioinv7());
						i.setPortfolioinvothers(info.getPortfolioinvothers());
						i.setAmountofinvestment(info.getAmountofinvestment());
						i.setCurrentportfoliosecurities(info.getCurrentportfoliosecurities());
						i.setCurrentportfoliosecuritiesothers(info.getCurrentportfoliosecuritiesothers());
						i.setAmountofsecurities(info.getAmountofsecurities());
						i.setIntendedinv(info.getIntendedinv());
						i.setIntendedinvothers(info.getIntendedinvothers());
						i.setAppetiterisk(info.getAppetiterisk());
						i.setAppetiteriskothers(info.getAppetiteriskothers());
						i.setYeartrading(info.getYeartrading());
						i.setYeartradingothers(info.getYeartradingothers());
						i.setQibposition1(info.getQibposition1());
						i.setQibposition2(info.getQibposition2());
						i.setQibposition3(info.getQibposition3());
						i.setQibposition4(info.getQibposition4());
						i.setQibpositionothers(info.getQibpositionothers());
						i.setRegistrationstatus(info.getRegistrationstatus()); // status
						i.setTerminationreason(info.getTerminationreason());
						i.setDateupdated(new Date());
						i.setUpdatedby(UserUtil.securityService.getUserName());
						System.out.println(">>>>>>>>>> UPDATE");
						if (dbService.saveOrUpdate(i)) {
							form.setFlag("success");
							System.out.println(">>>>>>>>>> UPDATE 2");
						}
					} else {
						if (dbService.save(info)) {
							System.out.println(">>>>>>>>>> NEW");
							form.setFlag("success");
						}
					}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public Tbqibinfo getQIBInfo(String cifno) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		Tbqibinfo i = new Tbqibinfo();
		if (cifno != null) {
			params.put("cifno", cifno);
			i = (Tbqibinfo) dbService.executeUniqueHQLQuery("FROM Tbqibinfo WHERE cifno=:cifno", params);
			if (i != null) {
				return i;
			} 
		}
		return i;
	}

	@Override
	public String saveQIBHistory(String cifno, String status) {
		// TODO Auto-generated method stub
		
		String flag = "failed";
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		
		try {
			param.put("cifno", cifno);
			Tbcifmain listmain = (Tbcifmain) dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", param);
			if(listmain!=null){
				Tbqibhistory qib = new Tbqibhistory();
				qib.setCifno(cifno);
				qib.setActivitydate(new Date());
				qib.setActivitydesc(status);
				qib.setUsername(UserUtil.securityService.getUserName());
				if(dbService.saveOrUpdate(qib)){
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
	public List<Tbqibhistory> qibHistory(String cifno) {
		// TODO Auto-generated method stub
		
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		List<Tbqibhistory> list = new ArrayList<Tbqibhistory>();
		
		try {
			param.put("cifno", cifno);
			list = (List<Tbqibhistory>) dbService.executeListHQLQuery("FROM Tbqibhistory WHERE cifno=:cifno", param);
			
			// Formatter - Activity Desc
			if(list!=null){
				for(Tbqibhistory qib : list){
					param.put("actDesc", qib.getActivitydesc());
					Tbcodetable regStatus = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='QIBREGISTRATIONSTATUS' AND a.id.codevalue =:actDesc", param);
					if(regStatus!=null){
						qib.setActivitydesc(regStatus.getDesc1());
					}									
				}
			}			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;			
	}

	@Override
	public FormValidation validateQIBInfo(String cifno) {
		// TODO Auto-generated method stub
		
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = new HashMap<String, Object>();		
		FormValidation form = new FormValidation(); 
		form.setFlag("failed");		
		StringBuilder errorMessage = new StringBuilder();
		boolean flag = false;
		
		try {
			param.put("cifno", cifno);
			Tbqibinfo qib = (Tbqibinfo) dbService.executeUniqueHQLQuery("FROM Tbqibinfo WHERE cifno=:cifno", param);
			if(qib==null){
				System.out.println(">>>>>>>>>>>>>> Tbqibinfo is null <<<<<<<<<<<<");
				errorMessage.append("<br/>");
				errorMessage.append("<b>Missing required field(s):</b> QIB Information");
				flag = true;
			}else{
				if(qib.getRegistrationdate()==null
						|| qib.getRegistrationnumber()==null 
						|| qib.getValidityyears()==null 
						|| qib.getValiditydate()==null 
						|| qib.getEvaluatormarketing()==null
						|| qib.getPositionmarketing()==null
						|| qib.getEvaluationdatemarketing()==null
						|| qib.getEvaluatorcompliance()==null
						|| qib.getPositioncompliance()==null
						|| qib.getEvaluationdatecompliance()==null
						|| qib.getAmountofinvestment()==null
						|| qib.getAmountofsecurities()==null
						|| qib.getIntendedinv()==null
						|| qib.getAppetiterisk()==null
						|| qib.getYeartrading()==null
						|| qib.getRegistrationstatus()==null
					){
					errorMessage.append("<br/>");
					errorMessage.append("<b>Missing required field(s):</b> QIB Information");
					flag = true;
				} 
				
				/** Added Checker for Corp **/
				if(cifno.substring(0, 1).equals("2")){
					if(qib.getCurrentnetworth()==null){
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Current Networth");
						flag = true;								
					}
				}
				
				// keyobjectiveothers - Mandatory only if Others is Checked "keyobjective5"
				if(qib.getKeyobjective5()!=null && qib.getKeyobjective5().equals(true)){
					if(qib.getKeyobjectiveothers()==null){
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Other Key Objectives");
						flag = true;						
					}
				}
				// portfolioinvothers - Mandatory only if Others is Checked "portfolioinv7"
				if(qib.getPortfolioinv7()!=null && qib.getPortfolioinv7().equals(true)){
					if(qib.getPortfolioinvothers()==null){
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Other Portfolio Investment");
						flag = true;						
					}
				}				
				// portfoliosecothers - Mandatory only if Others is Selected in Current Portfolio of Securities "currentportfoliosecurities"
				if(qib.getCurrentportfoliosecurities()!=null && qib.getCurrentportfoliosecurities().equals("0")){ 
					if(qib.getCurrentportfoliosecuritiesothers()==null){
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Current Portfolio of Securities (Others)");
						flag = true;						
					}
				}	
				// intendedinvothers - Mandatory only if Others is Selected in Intended Investment Horizon, "intendedinv"
				if(qib.getIntendedinv()!=null && qib.getIntendedinv().equals("0")){ 
					if(qib.getIntendedinvothers()==null){
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Intended Investment Horizon (Others)");
						flag = true;						
					}
				}
				// appetiteriskothers - Mandatory only if Others is Selected in Appetite for Risk, "appetiterisk"
				if(qib.getAppetiterisk()!=null && qib.getAppetiterisk().equalsIgnoreCase("O")){ // letter O
					if(qib.getAppetiteriskothers()==null){
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Appetite for Risk (Others)");
						flag = true;						
					}
				}
				// yeartradingothers - Mandatory only if Others is Selected in Years of Experience in Trading, "yeartrading"
				if(qib.getYeartrading()!=null && qib.getYeartrading().equals("0")){
					if(qib.getYeartradingothers()==null){
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Others");
						flag = true;						
					}
				}
				// qibpositionothers - Mandatory only if Executive Position is Checked, "qibposition4"
				if(qib.getQibposition4()!=null && qib.getQibposition4().equals(true)){
					if(qib.getQibpositionothers()==null){
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Executive Position Value");
						flag = true;						
					}
				}				
			}
			if(flag==false){
				form.setFlag("success");
			}else{
				form.setErrorMessage(errorMessage.toString());
			}				
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

//	Loop delete unused qib	
	@SuppressWarnings("unchecked")
	@Override
	public String deleteQIB(String cifno) {
		// TODO Auto-generated method stub
		
		String flag = "";
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();		
		
		try {
			
			param.put("cifno", cifno);
			Tbcifindividual indiv = (Tbcifindividual) dbService.executeUniqueHQLQuery("FROM Tbcifindividual WHERE cifno=:cifno", param);
			Boolean qib = indiv.getIsqualifiedinvestor();
			
			if(qib==null || qib.equals(false)){
				List<Tbqibinfo> list = (List<Tbqibinfo>) dbService.executeListHQLQuery("FROM Tbqibinfo WHERE cifno=:cifno", param);
				if(list!=null){
					for(Tbqibinfo data : list){
						param.put("cifno", data.getCifno());
						Tbqibinfo d = (Tbqibinfo) dbService.executeUniqueHQLQuery("FROM Tbqibinfo WHERE cifno=:cifno", param);
							if(dbService.delete(d)){
								return flag = "success";
							}else{
								return flag = "failed"; 
							}
					}
					return flag = "success";					
				}else{
					return flag = "success";
				}
			}else{
				return flag = "success";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodetableForm> getListofCodesPerCodename(String codename) {
		// TODO Auto-generated method stub
		
		List<CodetableForm> codelist = new ArrayList<CodetableForm>();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("codename", codename);
		try {
			// Method 1
			// List<Object> obj = (List<Object>)
			// dbService.executeListSQLQuery("SELECT codename, codevalue, desc1,
			// desc2, remarks FROM Tbcodetable WHERE codename=:codename",
			// params);
			// System.out.println(">>>>>>>>>>>>>>>>>codetable size:
			// "+obj.size());
			// if (obj != null) {
			// for (Object obj1 : obj) {
			// Object obj2[] = (Object[]) obj1;
			// ArrayList<String> data = new ArrayList<String>();
			// for (Object obj3 : obj2) {
			// data.add(String.valueOf(obj3));
			// }
			// CodetableForm form = new CodetableForm();
			// form.setCodename(data.get(0));
			// form.setCodevalue(data.get(1));
			// form.setDesc1(data.get(2));
			// form.setDesc2(data.get(3));
			// form.setRemarks(data.get(4));
			// codelist.add(form);
			// }
			// }

			// Method 2
			List<Tbcodetable> codetable = (List<Tbcodetable>) dbService.executeListHQLQuery("FROM Tbcodetable a WHERE a.id.codename=:codename ORDER BY codevalue ASC", params);
			if (codetable != null) {
				for (Tbcodetable ct : codetable) {
					CodetableForm form = new CodetableForm();
					form.setCodename(ct.getId().getCodename());
					form.setCodevalue(ct.getId().getCodevalue());
					form.setDesc1(ct.getDesc1());
					form.setDesc2(ct.getDesc2());
					form.setRemarks(ct.getRemarks());
					codelist.add(form);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codelist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodetableForm> getListofCodesPerCodename2(String codename) {
		// TODO Auto-generated method stub
		
		List<CodetableForm> codelist = new ArrayList<CodetableForm>();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("codename", codename);
		try {
			// Method 2
			List<Tbcodetable> codetable = (List<Tbcodetable>) dbService.executeListHQLQuery("FROM Tbcodetable a WHERE a.id.codename=:codename ORDER BY desc2 ASC", params);
			if (codetable != null) {
				for (Tbcodetable ct : codetable) {
					CodetableForm form = new CodetableForm();
					form.setCodename(ct.getId().getCodename());
					form.setCodevalue(ct.getId().getCodevalue());
					form.setDesc1(ct.getDesc1());
					form.setDesc2(ct.getDesc2());
					form.setRemarks(ct.getRemarks());
					codelist.add(form);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codelist;			
		}

}
