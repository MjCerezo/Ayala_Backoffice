package com.etel.creditfacility;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbcifcorporate;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.common.service.DBServiceImplLOS;
import com.etel.company.forms.CompanyForm;
import com.etel.creditfacility.forms.CorpSubsidiaryForm;
import com.etel.creditfacility.forms.ExistingFacilityForm;
import com.etel.creditfacility.forms.LineAvailmentForm;
import com.etel.creditfacility.forms.LoanProdPerCFForm;
import com.etel.forms.ReturnForm;
import com.etel.history.HistoryService;
import com.etel.history.HistoryServiceImpl;
import com.etel.inquiry.forms.DedupeCIFForm;
import com.etel.lam.LAMService;
import com.etel.lam.LAMServiceImpl;
import com.etel.utils.ApplicationNoGenerator;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.loansdb.data.Tbaccountinfo;
import com.loansdb.data.Tbcfcompany;
import com.loansdb.data.Tbcfcoobligor;
import com.loansdb.data.Tbcfcovenants;
import com.loansdb.data.Tbcfdetails;
import com.loansdb.data.TbcfdetailsId;
import com.loansdb.data.Tbcfmaintenance;
import com.loansdb.data.Tbcftermconditions;
import com.loansdb.data.Tbcovenants;
import com.loansdb.data.Tblstapp;

public class CreditFacilityServiceImpl implements CreditFacilityService {

	private DBService dbServiceLOS = new DBServiceImplLOS();
	private DBService dbServiceCIF = new DBServiceImplCIF();
	private Map<String, Object> params = HQLUtil.getMap();
	
	@Override
	public ReturnForm saveOrUpdateCreditFacility(Tbcfdetails cfdetails, List<CompanyForm> company) {
		ReturnForm form = new ReturnForm();
    	form.setFlag("failed");
    	
    	try {
    		System.out.println(cfdetails.getId().getCfrefno() + " - " + cfdetails.getCfrefnoconcat());
			if (cfdetails.getId() != null && cfdetails.getCfrefnoconcat() != null) {
				params.put("cfrefnoconcat", cfdetails.getCfrefnoconcat());
				params.put("appno", cfdetails.getId().getCfappno());
				System.out.println(cfdetails.getId().getCflevel());
				if(cfdetails.getId().getCflevel() == 1){
					params.put("cfcode", cfdetails.getCfcode());
				}else{
					params.put("cfcode", cfdetails.getCftype());
				}
				Tbcfdetails cfd = (Tbcfdetails) dbServiceLOS.executeUniqueHQLQuery(
						"FROM Tbcfdetails WHERE id.cfappno=:appno AND cfrefnoconcat=:cfrefnoconcat",
						params);
				Tbcfmaintenance cfm = (Tbcfmaintenance) dbServiceLOS.executeUniqueHQLQuery("from Tbcfmaintenance where facilitycode=:cfcode",params); 
				if (cfd != null) {
					System.out.println(">>>CF Details not null");
					cfd.setCftype(cfdetails.getCftype());
					cfd.setCfcode(cfdetails.getCfcode());
					cfd.setCfcurrency(cfdetails.getCfcurrency());
					cfd.setCfproposedamt(cfdetails.getCfproposedamt());
					cfd.setCfexpdt(cfdetails.getCfexpdt());
					cfd.setCfrevolving(cfdetails.getCfrevolving());
					if((cfm.getEnablesubfacility() != null && cfm.getEnablesubfacility()) || (cfm.getEnablecoobligor() != null && cfm.getEnablecoobligor())) {
						cfd.setCfshared(true);
						if((cfm.getEnablesubfacility() != null && cfm.getEnablesubfacility())){
							cfd.setCfsharedtype("2");
							if((cfm.getEnablecoobligor() != null && cfm.getEnablecoobligor())){
								cfd.setCfsharedtype("3");
							}
						}else {
							if((cfm.getEnablecoobligor() != null && cfm.getEnablecoobligor())){
								cfd.setCfsharedtype("1");
							}
						}
					}
					else{
						cfd.setCfshared(false);
					}
					
//					cfd.setCfbalance(cfdetails.getCfbalance());
					cfd.setCfterm(cfdetails.getCfterm());
					cfd.setCftermperiod(cfdetails.getCftermperiod());
					cfd.setCfraterule(cfdetails.getCfraterule());
//					cfd.setCfminrate(cfdetails.getCfminrate());
//					cfd.setCfmaxrate(cfdetails.getCfmaxrate());
					cfd.setCfrepaymenttype(cfdetails.getCfrepaymenttype());
					cfd.setRemarks(cfdetails.getRemarks());
//					cfd.setCfmaker(cfdetails.getCfmaker());
					cfd.setCfupdate(new Date());
					cfd.setCflastupdateby(UserUtil.securityService.getUserName());
					//cfd.setCovenants(cfdetails.getCovenants()); 
					cfd.setCfintrate(cfdetails.getCfintrate());
					cfd.setCfintrateperiod(cfdetails.getCfintrateperiod());
					cfd.setCfrequestedvalidity(cfdetails.getCfrequestedvalidity());
					
					//Added (Kevin 01.21.2020)
					cfd.setCflpcrate(cfdetails.getCflpcrate());
					
					//CIFNAME
					if(cfdetails.getCifno() != null){
						params.put("cifno", cfdetails.getCifno());
						String cifname = (String) dbServiceCIF.executeUniqueSQLQuery("Select fullname FROM Tbcifmain WHERE cifno=:cifno", params);
						cfdetails.setCifname(cifname);
					}
					
					if(dbServiceLOS.saveOrUpdate(cfd)){
						form.setFlag("success");
						form.setMessage(cfd.getCfrefnoconcat());
						
						
						params.put("appno", cfd.getId().getCfappno());
						params.put("cifno", cfd.getCifno());
						params.put("cfrefno", cfd.getId().getCfrefno());
						
						//Update Line Validity, Revolving
						if(cfd.getCfexpdt() != null){
							String dtLineValidity = new SimpleDateFormat("yyyy-MM-dd").format(cfd.getCfexpdt());
							dbServiceLOS.executeUpdate("UPDATE Tbcfdetails SET cfexpdt='" +dtLineValidity+"', cfrevolving='"+cfd.getCfrevolving()+"', cflpcrate='"+cfd.getCflpcrate()+"'  WHERE cfappno=:appno AND cifno=:cifno AND cfrefno=:cfrefno", params);
							dbServiceLOS.executeUpdate("UPDATE Tbcfcoobligor SET cfexpdt='" +dtLineValidity+"', cfrevolving='"+cfd.getCfrevolving()+"', cflpcrate='"+cfd.getCflpcrate()+"'  WHERE cfappno=:appno AND cfrefno=:cfrefno", params);
							
							if(cfd.getCfrequestedvalidity() != null){
								String dtReqLineValidity = new SimpleDateFormat("yyyy-MM-dd").format(cfd.getCfrequestedvalidity());
								dbServiceLOS.executeUpdate("UPDATE Tbcfdetails SET cfrequestedvalidity='"+dtReqLineValidity+"'  WHERE cfappno=:appno AND cfrefno=:cfrefno", params);
							}
						}
						
						if (cfd.getId().getCflevel() == 0) {
							// Term Period | Interest Period
							dbServiceLOS.executeUpdate("UPDATE Tbcfdetails SET cftermperiod='"+cfd.getCftermperiod()+"', cfintrateperiod='"+cfd.getCfintrateperiod()+"', "
									+ "cfcurrency='"+cfd.getCfcurrency()+"' WHERE cfappno=:appno AND cfrefno=:cfrefno", params);
							
							dbServiceLOS.executeUpdate("UPDATE Tbcfcoobligor SET cfterm='"+cfd.getCfterm()+"', cftermperiod='"+cfd.getCftermperiod()+"', cfintrate='"+cfd.getCfintrate()+"', cfintrateperiod='"+cfd.getCfintrateperiod()+"', cftype='"+cfd.getCftype()+"', cfcode='"+cfd.getCfcode()+"', "
									+ "cfcurrency='"+cfd.getCfcurrency()+"' WHERE cfappno=:appno AND cfrefno=:cfrefno", params);
							
							params.put("cfrefnoconcat", cfd.getCfrefnoconcat());
							Tbcfcoobligor coobMain  = (Tbcfcoobligor) dbServiceLOS.executeUniqueHQLQuery("FROM Tbcfcoobligor WHERE cfappno=:appno AND cfcifno=:cifno AND cfrefnoconcat=:cfrefnoconcat", params);
							if(coobMain == null){
								coobMain = new Tbcfcoobligor();
								coobMain.setCfterm(cfd.getCfterm());
								coobMain.setCfintrate(cfd.getCfintrate());
								coobMain.setCfproposedamt(cfd.getCfproposedamt());
								coobMain.setCfaddedby(UserUtil.securityService.getUserName());
								coobMain.setCfcreateddate(new Date());
							}
							if((cfm.getEnablecoobligor() == null || cfm.getEnablecoobligor() == false)){
								coobMain.setCfterm(cfd.getCfterm());
								coobMain.setCfintrate(cfd.getCfintrate());
								coobMain.setCfproposedamt(cfd.getCfproposedamt());
							}
							coobMain.setCfappno(cfd.getId().getCfappno());
							coobMain.setCfrefno(cfd.getId().getCfrefno());
							coobMain.setCflevel(cfd.getId().getCflevel());
							coobMain.setCfseqno(cfd.getId().getCfseqno());
							coobMain.setCfsubseqno(cfd.getId().getCfsubseqno());
							coobMain.setCfcifno(cfd.getCifno());
							coobMain.setCfcifname(cfd.getCifname());
							coobMain.setCfamount(cfd.getCfamount());
							coobMain.setRemarks(cfd.getRemarks());
							coobMain.setCfavailed(cfd.getCfavailed());
							coobMain.setCfearmarked(cfd.getCfearmarked());
							coobMain.setCfbalance(cfd.getCfbalance());
							coobMain.setCfstatus(cfd.getCfstatus());
							coobMain.setCfapprovedamt(cfd.getCfapprovedamt());
							coobMain.setCfexpdt(cfd.getCfexpdt());
							coobMain.setCfrevolving(cfd.getCfrevolving());
							coobMain.setCftermperiod(cfd.getCftermperiod());
							coobMain.setCfintrateperiod(cfd.getCfintrateperiod());
							coobMain.setCftype(cfd.getCftype());
							coobMain.setCfcode(cfd.getCfcode());
							coobMain.setCfcurrency(cfd.getCfcurrency());
							coobMain.setCfrefnoconcat(cfd.getId().getCfrefno() + "-" + cfd.getId().getCflevel() + "-" + cfd.getId().getCfseqno() + "-" + cfd.getId().getCfsubseqno());
							coobMain.setCfupdatedby(UserUtil.securityService.getUserName());
							coobMain.setCfupdated(new Date());
							
							//Added (Kevin 01.21.2020)
							coobMain.setCflpcrate(cfd.getCflpcrate());
							
							Tbcifcorporate corp = (Tbcifcorporate) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno=:cifno", params);
							if(corp != null){
								coobMain.setBusinesstype(corp.getBusinesstype());
								coobMain.setDateofincorporation(corp.getDateofincorporation());
								
								String cifstatus = (String) dbServiceCIF.executeUniqueSQLQuery("SELECT cifstatus FROM Tbcifmain WHERE cifno=:cifno", params);
								if(cifstatus != null){
									coobMain.setCfcifstatus(cifstatus);
								}
										
							}
							if (dbServiceLOS.saveOrUpdate(coobMain)) {
								System.out.println(">>>>Coobligor updated ! - "+ coobMain.getCfrefnoconcat());
							}
							// HISTORY (Kevin 10.22.2019)
							HistoryService h = new HistoryServiceImpl();
							h.saveHistory(cfdetails.getId().getCfappno(), AuditLogEvents.EDIT_CREDIT_FACILITY_DETAILS, "Updated main credit line details - CF Reference No.: "+cfdetails.getCfrefnoconcat()+".");
						
						}else{
							// HISTORY (Kevin 10.22.2019)
							HistoryService h = new HistoryServiceImpl();
							h.saveHistory(cfdetails.getId().getCfappno(), AuditLogEvents.EDIT_CREDIT_FACILITY_DETAILS, "Updated sub-facility details - CF Reference No.: "+cfdetails.getCfrefnoconcat()+".");
						
						}
						
						//Shared Type
						if(cfd.getCfsharedtype() != null && !cfd.getCfsharedtype().equals("")){
//							//Shared By Corporations
//							if(cfdetails.getCfsharedtype().equals("1")){
//								if (cfd.getId().getCflevel() == 0) {
//									deleteCreditFacility(cfd.getId().getCfappno(), cfd.getId().getCfrefno(),  (cfd.getId().getCflevel() + 1),  null,  null);
//								}
//								if (cfd.getId().getCflevel() == 1) {
//									deleteCreditFacility(cfd.getId().getCfappno(), cfd.getId().getCfrefno(),  (cfd.getId().getCflevel() + 1) ,  cfd.getId().getCfseqno(), null);
//								}
//								if (cfd.getId().getCflevel() > 1) {
//									deleteCreditFacility(cfd.getId().getCfappno(), cfd.getId().getCfrefno(),  (cfd.getId().getCflevel() + 1) ,  cfd.getId().getCfseqno(),  cfd.getId().getCfsubseqno());
//								}
//							}
							//Shared By Sub Facilities
							if (cfd.getCfsharedtype().equals("2")) {
								deleteCoobligor(null, cfd.getId().getCfappno(), cfd.getId().getCfrefno(),  cfd.getId().getCflevel(),  cfd.getId().getCfseqno(),  cfd.getId().getCfsubseqno(), false);
							}
						}else{
							
							//Delete Subfacility & Coobligor if not omnibus line
							if (cfd.getId().getCflevel() == 0) {
								deleteCreditFacility(cfd.getId().getCfappno(), cfd.getId().getCfrefno(),  (cfd.getId().getCflevel() + 1),  null,  null);
							}
							if (cfd.getId().getCflevel() == 1) {
								deleteCreditFacility(cfd.getId().getCfappno(), cfd.getId().getCfrefno(),  (cfd.getId().getCflevel() + 1) ,  cfd.getId().getCfseqno(), null);
							}
							if (cfd.getId().getCflevel() > 1) {
								deleteCreditFacility(cfd.getId().getCfappno(), cfd.getId().getCfrefno(),  (cfd.getId().getCflevel() + 1) ,  cfd.getId().getCfseqno(),  cfd.getId().getCfsubseqno());
							}
							
							deleteCoobligor(null, cfd.getId().getCfappno(), cfd.getId().getCfrefno(),  cfd.getId().getCflevel(),  cfd.getId().getCfseqno(),  cfd.getId().getCfsubseqno(), false);
						}
						
						//Replicate CF Details to LAM - Kevin 01-16-2019
						replicateCFDetailsToLAMLoanDetails(cfd.getId().getCfappno());
						
						
						//Save Company Funder - Kevin 04.01.2019
						saveCFCompany(company, cfd.getId().getCfappno(), cfd.getId().getCfrefno(),  cfd.getId().getCflevel(),  cfd.getId().getCfseqno(),  cfd.getId().getCfsubseqno(), cfd.getCfrefnoconcat());
						
					}
					
				}else{
					System.out.println(">>>CF Details is null");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}
	
	@Override
	public ReturnForm addCreditFacility(Tbcfdetails cfdetails, String maincfrefno, Integer maincflevel, String maincfseqno,
			String maincfsubseqno, List<CompanyForm> company) {
		ReturnForm form = new ReturnForm();
    	form.setFlag("failed");
    	try {
			TbcfdetailsId id = new TbcfdetailsId();
			if (cfdetails.getId().getCfrefno() == null && cfdetails.getId().getCflevel() == null
					&& cfdetails.getId().getCfseqno() == null && cfdetails.getId().getCfsubseqno() == null) {
				id.setCfappno(cfdetails.getId().getCfappno());
				id.setCfrefno(ApplicationNoGenerator.generateReportID("CF"));
				id.setCflevel(0);
				id.setCfseqno("00");
				id.setCfsubseqno("00");
			}else{
				//App No
				id.setCfappno(cfdetails.getId().getCfappno());
				
				//CF Reference No.
				if (cfdetails.getId().getCfrefno() == null) {
					id.setCfrefno(ApplicationNoGenerator.generateReportID("CF"));
				}else{
					id.setCfrefno(cfdetails.getId().getCfrefno());
				}
				
				//CF Level
				if (cfdetails.getId().getCflevel() == null) {
					id.setCflevel(0);
				}else{
					id.setCflevel(cfdetails.getId().getCflevel());
				}
				//CF Sequence No.
				if (cfdetails.getId().getCfseqno() == null) {
					if(maincfsubseqno == null){
						id.setCfseqno(generateCFSeqNoByCfrefnoAndCflevel(cfdetails.getId().getCfappno(), maincfrefno, maincflevel, maincfseqno, null));
					}else{
						id.setCfseqno(maincfseqno);
					}
				}else{
					id.setCfseqno(cfdetails.getId().getCfseqno());
				}
				
				//CF Sub Sequence No.
				if (cfdetails.getId().getCfsubseqno() == null) {
					if (id.getCflevel() == 0 || id.getCflevel() == 1) {
						id.setCfsubseqno("00");
					}else{
						id.setCfsubseqno(generateCFSeqNoByCfrefnoAndCflevel(cfdetails.getId().getCfappno(), maincfrefno, maincflevel, maincfseqno, maincfsubseqno));
					}
				}else{
					id.setCfsubseqno(cfdetails.getId().getCfsubseqno());
				}
			}	
			
			// 050319
			if(cfdetails.getId().getCflevel()!=null && cfdetails.getId().getCflevel() == 1){
				params.put("cfcode", cfdetails.getCfcode());
			}else{
				params.put("cfcode", cfdetails.getCftype());
			}
			Tbcfmaintenance cfm = (Tbcfmaintenance) dbServiceLOS.executeUniqueHQLQuery("from Tbcfmaintenance where facilitycode=:cfcode",params); 
			if(cfm != null){
				if((cfm.getEnablesubfacility() != null && cfm.getEnablesubfacility()) || (cfm.getEnablecoobligor() != null && cfm.getEnablecoobligor())) {
					cfdetails.setCfshared(true);
					if((cfm.getEnablesubfacility() != null && cfm.getEnablesubfacility())){
						cfdetails.setCfsharedtype("2");
						if((cfm.getEnablecoobligor() != null && cfm.getEnablecoobligor())){
							cfdetails.setCfsharedtype("3");
						}
					}else {
						if((cfm.getEnablecoobligor() != null && cfm.getEnablecoobligor())){
							cfdetails.setCfsharedtype("1");
						}
					}
				}
				else{
					cfdetails.setCfshared(false);
				} // end of 050319
			}
			cfdetails.setId(id);
			cfdetails.setCfrefnoconcat(id.getCfrefno() + "-" + id.getCflevel() + "-" + id.getCfseqno() + "-" + id.getCfsubseqno());
			//Default Value 0 
			cfdetails.setCfamount(BigDecimal.ZERO);
			cfdetails.setCfapprovedamt(BigDecimal.ZERO);
			cfdetails.setCfavailed(BigDecimal.ZERO);
			cfdetails.setCfearmarked(BigDecimal.ZERO);
			cfdetails.setCfbalance(BigDecimal.ZERO);
			cfdetails.setCfupdatecount(0);
			
			cfdetails.setCfdtopen(new Date());
			cfdetails.setCfmaker(UserUtil.securityService.getUserName());
			
			//Initial Status For Approval
			cfdetails.setCfstatus("4");
			
			//CIFNAME
			if(cfdetails.getCifno() != null){
				params.put("cifno", cfdetails.getCifno());
				String cifname = (String) dbServiceCIF.executeUniqueSQLQuery("Select fullname FROM Tbcifmain WHERE cifno=:cifno", params);
				cfdetails.setCifname(cifname);
			}else{
				params.put("cifno", "---");
			}
			if(dbServiceLOS.save(cfdetails)){
				form.setFlag("success");
				form.setMessage(cfdetails.getCfrefnoconcat());
				
				//Coobligor
				if (cfdetails.getId().getCflevel() == 0) {
					Tbcfcoobligor coobMain  = new Tbcfcoobligor();
					coobMain = new Tbcfcoobligor();
					coobMain.setCfterm(cfdetails.getCfterm());
					coobMain.setCfintrate(cfdetails.getCfintrate());
					coobMain.setCfproposedamt(cfdetails.getCfproposedamt());
					coobMain.setCfaddedby(UserUtil.securityService.getUserName());
					coobMain.setCfcreateddate(new Date());
					coobMain.setCfterm(cfdetails.getCfterm());
					coobMain.setCfintrate(cfdetails.getCfintrate());
					coobMain.setCfproposedamt(cfdetails.getCfproposedamt());
					coobMain.setCfappno(cfdetails.getId().getCfappno());
					coobMain.setCfrefno(cfdetails.getId().getCfrefno());
					coobMain.setCflevel(cfdetails.getId().getCflevel());
					coobMain.setCfseqno(cfdetails.getId().getCfseqno());
					coobMain.setCfsubseqno(cfdetails.getId().getCfsubseqno());
					coobMain.setCfcifno(cfdetails.getCifno());
					coobMain.setCfcifname(cfdetails.getCifname());
					coobMain.setCfamount(cfdetails.getCfamount());
					coobMain.setRemarks(cfdetails.getRemarks());
					coobMain.setCfavailed(cfdetails.getCfavailed());
					coobMain.setCfearmarked(cfdetails.getCfearmarked());
					coobMain.setCfbalance(cfdetails.getCfbalance());
					coobMain.setCfstatus(cfdetails.getCfstatus());
					coobMain.setCfapprovedamt(cfdetails.getCfapprovedamt());
					coobMain.setCfexpdt(cfdetails.getCfexpdt());
					coobMain.setCfrevolving(cfdetails.getCfrevolving());
					coobMain.setCftermperiod(cfdetails.getCftermperiod());
					coobMain.setCfintrateperiod(cfdetails.getCfintrateperiod());
					coobMain.setCftype(cfdetails.getCftype());
					coobMain.setCfcode(cfdetails.getCfcode());
					coobMain.setCfcurrency(cfdetails.getCfcurrency());
					coobMain.setCfrefnoconcat(cfdetails.getId().getCfrefno() + "-" + cfdetails.getId().getCflevel() + "-" + cfdetails.getId().getCfseqno() + "-" + cfdetails.getId().getCfsubseqno());
					coobMain.setCfupdatedby(UserUtil.securityService.getUserName());
					coobMain.setCfupdated(new Date());
					
					//Added (Kevin 01.21.2020)
					coobMain.setCflpcrate(cfdetails.getCflpcrate());
					
					Tbcifcorporate corp = (Tbcifcorporate) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno=:cifno", params);
					if(corp != null){
						coobMain.setBusinesstype(corp.getBusinesstype());
						coobMain.setDateofincorporation(corp.getDateofincorporation());
						
						String cifstatus = (String) dbServiceCIF.executeUniqueSQLQuery("SELECT cifstatus FROM Tbcifmain WHERE cifno=:cifno", params);
						if(cifstatus != null){
							coobMain.setCfcifstatus(cifstatus);
						}
								
					}
					if (dbServiceLOS.save(coobMain)) {
						System.out.println(">>>>Coobligor added ! - "+ coobMain.getCfrefnoconcat());
					}
					// HISTORY (Kevin 10.22.2019)
					HistoryService h = new HistoryServiceImpl();
					h.saveHistory(cfdetails.getId().getCfappno(), AuditLogEvents.ADD_CREDIT_FACILITY, "Added main credit line - CF Reference No.: "+cfdetails.getCfrefnoconcat()+".");
				
				}else{
					// HISTORY (Kevin 10.22.2019)
					HistoryService h = new HistoryServiceImpl();
					h.saveHistory(cfdetails.getId().getCfappno(), AuditLogEvents.ADD_CREDIT_FACILITY, "Added sub-facility - CF Reference No.: "+cfdetails.getCfrefnoconcat()+".");
				
				}
				
				//Replicate CF Details to LAM - Kevin 01-16-2019
				replicateCFDetailsToLAMLoanDetails(cfdetails.getId().getCfappno());
				
				//Save Company Funder - Kevin 04.01.2019
				saveCFCompany(company, cfdetails.getId().getCfappno(), cfdetails.getId().getCfrefno(),  cfdetails.getId().getCflevel(),  cfdetails.getId().getCfseqno(),  cfdetails.getId().getCfsubseqno(), cfdetails.getCfrefnoconcat());
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}
	
	public String generateCFSeqNoByCfrefnoAndCflevel(String cfappno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno) {
		String seqNo = null;
		String hql = "FROM Tbcfdetails WHERE id.cfappno=:cfappno AND id.cfrefno=:cfrefno AND id.cflevel=:cflevel AND id.cfseqno=:cfseqno";
		try {
			if(cfrefno != null && cflevel != null && cfseqno != null){
				params.put("cfappno", cfappno);
				params.put("cfrefno", cfrefno);
				params.put("cflevel", cflevel);
				params.put("cfseqno", cfseqno);
				
				if(cfsubseqno != null){
					params.put("cfsubseqno", cfsubseqno);
					hql += " AND id.cfsubseqno=:cfsubseqno";
				}
				
				Tbcfdetails cf = (Tbcfdetails) dbServiceLOS.executeUniqueHQLQuery(hql, params);
				if (cf != null) {
					int one = cf.getSubfacilityseqno() == null ? 0 : cf.getSubfacilityseqno();
					seqNo = String.format("%02d", one + 1); //00
					cf.setSubfacilityseqno(one + 1); 
					dbServiceLOS.saveOrUpdate(cf);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return seqNo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcfdetails> getListOfCreditFacilityByAppNo(String appno) {
		List<Tbcfdetails> list = new ArrayList<Tbcfdetails>();
		try {
			if(appno != null){
				params.put("appno", appno);
				list  = (List<Tbcfdetails>) dbServiceLOS.executeListHQLQuery("FROM Tbcfdetails WHERE id.cfappno=:appno AND id.cflevel='2'", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveOrUpdateCoObligor(Tbcfcoobligor cfcoobligor) {
    	String flag = "failed";
    	try {
    		boolean isNew = false;
    		if (cfcoobligor.getId() == null) {
    			isNew = true;
				cfcoobligor.setCfaddedby(UserUtil.securityService.getUserName());
				cfcoobligor.setCfcreateddate(new Date());
			}else{
				params.put("id", cfcoobligor.getId());
				Tbcfcoobligor cfcoob = (Tbcfcoobligor) dbServiceLOS.executeUniqueHQLQuery("FROM Tbcfcoobligor WHERE id=:id", params);
				if (cfcoob != null) {
					cfcoobligor.setCfaddedby(cfcoob.getCfaddedby());
					cfcoobligor.setCfcreateddate(cfcoob.getCfcreateddate());
				}
				cfcoobligor.setCfrefnoconcat(cfcoob.getCfrefno() + "-" + cfcoob.getCflevel() + "-"
						+ cfcoob.getCfseqno() + "-" + cfcoob.getCfsubseqno());
				cfcoobligor.setCfupdatedby(UserUtil.securityService.getUserName());
				cfcoobligor.setCfupdated(new Date());
			}
			if (dbServiceLOS.saveOrUpdate(cfcoobligor)) {
				flag = "success";
				
				if (isNew) {
					// HISTORY (Kevin 10.28.2019)
					HistoryService h = new HistoryServiceImpl();
					h.saveHistory(cfcoobligor.getCfappno(), AuditLogEvents.ADD_CF_COOBLIGOR,
							"Added Co-obligor - CF Reference No.: " + cfcoobligor.getCfrefnoconcat() + " | CIF : "
									+ cfcoobligor.getCfcifno() + " - " + cfcoobligor.getCfcifname());
				} else {
					// HISTORY (Kevin 10.28.2019)
					HistoryService h = new HistoryServiceImpl();
					h.saveHistory(cfcoobligor.getCfappno(), AuditLogEvents.EDIT_CF_COOBLIGOR_DETAILS,
							"Updated Co-obligor details - CF Reference No.: " + cfcoobligor.getCfrefnoconcat() + " | CIF : "
									+ cfcoobligor.getCfcifno() + " - " + cfcoobligor.getCfcifname());
				}
				

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcfcoobligor> getCoObligorList(String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno, String cfrefnoconcat) {
		List<Tbcfcoobligor> list = new ArrayList<Tbcfcoobligor>();
		StringBuilder hql = new StringBuilder();
		try {
			if(appno != null){
				params.put("appno", appno);
				hql.append("FROM Tbcfcoobligor WHERE cfappno=:appno AND ");
				
				if (cfrefno != null) {
					params.put("cfrefno", cfrefno);
					hql.append("cfrefno=:cfrefno AND ");
				}
				if (cflevel != null) {
					params.put("cflevel", cflevel);
					hql.append("cflevel=:cflevel AND ");
				}
				if (cfseqno != null) {
					params.put("cfseqno", cfseqno);
					hql.append("cfseqno=:cfseqno AND ");
				}
				if (cfsubseqno != null) {
					params.put("cfsubseqno", cfsubseqno);
					hql.append("cfsubseqno=:cfsubseqno AND ");
				}
				if(cfrefnoconcat != null){
					hql = new StringBuilder();
					params.put("cfrefnoconcat", cfrefnoconcat);
					hql.append("FROM Tbcfcoobligor WHERE cfappno=:appno AND cfrefnoconcat=:cfrefnoconcat AND ");
				}
				list  = (List<Tbcfcoobligor>) dbServiceLOS.executeListHQLQuery(hql.toString().substring(0, hql.length() - 5), params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CorpSubsidiaryForm> getSubsidiaries(String maincifno, String searchcifno, String searchcorporatename) {
		List<CorpSubsidiaryForm> list = new ArrayList<CorpSubsidiaryForm>();
		StringBuilder s = new StringBuilder();
		try {
			if(maincifno != null){
				params.put("maincifno", maincifno);
				s.append("SELECT m.cifno, c.corporatename, m.cifstatus, c.dateofincorporation, c.businesstype, ");
				s.append("c.tin, m.encodeddate, m.fulladdress1,(SELECT teamname FROM Tbteams WHERE teamcode=m.originatingteam) as originatingteam FROM Tbcifmain m ");
//				s.append("INNER JOIN Tbcustomerrelationship r ON m.cifno=r.relatedcifno AND r.relationshipcode = 'SUB' ");
				s.append("INNER JOIN Tbcifcorporate c ON m.cifno=c.cifno");
//				
				
				/*
				 * MODIFIED 03.19.2019 - KEVIN, As per Mam Mheanne - Include subsidiaries of subsidiary.
				 * */
				
//				s.append("DECLARE @cifno varchar(20) = '"+maincifno+"' ");
//				s.append("SELECT * FROM ");
//				s.append("( ");
//				
//				// LEVEL 1 QUERY (Mother Company)
//				s.append("SELECT m.cifno, c.corporatename, m.cifstatus, c.dateofincorporation, c.businesstype,  ");
//				s.append("c.tin, m.encodeddate, m.fulladdress1,(SELECT teamname FROM Tbteams WHERE teamcode=m.originatingteam) as originatingteam FROM Tbcifmain m ");
//				s.append("INNER JOIN Tbcustomerrelationship r ON m.cifno=r.relatedcifno AND r.relationshipcode = 'SUB' ");
//				s.append("INNER JOIN Tbcifcorporate c ON m.cifno=c.cifno AND r.maincifno=@cifno ");
//				s.append(" ");
//				
//				// LEVEL 2 QUERY (Subsidiary)
//				s.append("UNION ALL ");
//				s.append(" ");
//				s.append("SELECT m.cifno, c.corporatename, m.cifstatus, c.dateofincorporation, c.businesstype,  ");
//				s.append("c.tin, m.encodeddate, m.fulladdress1,(SELECT teamname FROM Tbteams WHERE teamcode=m.originatingteam) as originatingteam FROM Tbcifmain m ");
//				s.append("INNER JOIN Tbcustomerrelationship r ON m.cifno=r.relatedcifno AND r.relationshipcode = 'SUB' ");
//				s.append("INNER JOIN Tbcifcorporate c ON m.cifno=c.cifno  ");
//				s.append("AND r.maincifno IN  ");
//				s.append("( ");
//				s.append("SELECT DISTINCT maincifno FROM TBCUSTOMERRELATIONSHIP WHERE relationshipcode = 'SUB' AND relatedcifno=@cifno ");
//				s.append("UNION SELECT DISTINCT relatedcifno FROM TBCUSTOMERRELATIONSHIP WHERE relationshipcode = 'SUB' AND maincifno=@cifno ");
//				s.append(") ");
//				s.append("AND r.relatedcifno <> @cifno ");
//				s.append(" ");
//				
//				// LEVEL 3 QUERY (Subsidiaries of subsidiary)
//				s.append("UNION ALL ");
//				s.append("SELECT m.cifno, c.corporatename, m.cifstatus, c.dateofincorporation, c.businesstype,  ");
//				s.append("c.tin, m.encodeddate, m.fulladdress1,(SELECT teamname FROM Tbteams WHERE teamcode=m.originatingteam) as originatingteam FROM Tbcifmain m ");
//				s.append("INNER JOIN Tbcustomerrelationship r ON m.cifno=r.relatedcifno AND r.relationshipcode = 'SUB' ");
//				s.append("INNER JOIN Tbcifcorporate c ON m.cifno=c.cifno  ");
//				s.append("AND r.maincifno IN ");
//				s.append("( ");
//				s.append("SELECT m.cifno FROM Tbcifmain m ");
//				s.append("INNER JOIN Tbcustomerrelationship r ON m.cifno=r.relatedcifno AND r.relationshipcode = 'SUB' ");
//				s.append("INNER JOIN Tbcifcorporate c ON m.cifno=c.cifno ");
//				s.append("AND r.maincifno IN  ");
//				s.append("( ");
//				s.append("SELECT DISTINCT maincifno FROM TBCUSTOMERRELATIONSHIP WHERE relationshipcode = 'SUB' AND relatedcifno=@cifno ");
//				s.append("UNION SELECT DISTINCT relatedcifno FROM TBCUSTOMERRELATIONSHIP WHERE relationshipcode = 'SUB' AND maincifno=@cifno ");
//				s.append(") ");
//				s.append("AND r.relatedcifno <> @cifno ");
//				s.append(") ");
//				
//				s.append(") q WHERE q.cifno IS NOT NULL");
//				if(searchcifno != null){
//					params.put("searchcifno", "%" +searchcifno +"%");
//					s.append(" AND q.cifno LIKE :searchcifno");
////					s.append(" AND q.cifno LIKE %" +searchcifno +"%");
//				}
//				if(searchcorporatename != null){
//					params.put("searchcorporatename", "%" +searchcorporatename +"%");
//					s.append(" AND q.corporatename LIKE :searchcorporatename");
////					s.append(" AND q.corporatename LIKE %" +searchcorporatename +"%");
//				}
				if(searchcifno != null){
					params.put("searchcifno", "%" +searchcifno +"%");
					s.append(" AND c.cifno LIKE :searchcifno");
				}
				if(searchcorporatename != null){
					params.put("searchcorporatename", "%" +searchcorporatename +"%");
					s.append(" AND c.corporatename LIKE :searchcorporatename");
				}
				list = (List<CorpSubsidiaryForm>) dbServiceCIF.execSQLQueryTransformer(s.toString(), params, CorpSubsidiaryForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return list;
	}

	@Override
	public String validateCoObligor(String cfrefnoconcat, String cfcifno, String cfappno) {
		String flag = "failed";
    	try {
    		if(cfcifno != null && cfrefnoconcat != null && cfappno != null){
	    		params.put("cfcifno", cfcifno);
	    		params.put("cfrefnoconcat", cfrefnoconcat);
	    		params.put("cfappno", cfappno);
	    		//Checks if cifno exists.
	    		Integer coobligorChk = (Integer) dbServiceLOS.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbcfcoobligor WHERE cfrefnoconcat=:cfrefnoconcat AND cfcifno=:cfcifno AND cfappno=:cfappno",params);
	    		if(coobligorChk > 0){
	    			return "existing";
	    		}
	    		flag = "success";
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcfdetails> getCFByAppnoCfRefnoCfLevelCfSeqno(String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno) {
		List<Tbcfdetails> list = new ArrayList<Tbcfdetails>();
		StringBuilder hql = new StringBuilder();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(appno != null && cflevel != null){
				params.clear();
				params.put("appno", appno);
				params.put("cflevel", cflevel);
				hql.append("FROM Tbcfdetails WHERE id.cfappno=:appno AND id.cflevel=:cflevel AND ");
				
				if (cfseqno != null) {
					params.put("cfseqno", cfseqno);
					hql.append("id.cfseqno=:cfseqno AND ");
				}
				if (cfrefno != null) {
					params.put("cfrefno", cfrefno);
					hql.append("id.cfrefno=:cfrefno AND ");
				}
				if (cfsubseqno != null) {
					params.put("cfsubseqno", cfsubseqno);
					hql.append("id.cfsubseqno=:cfsubseqno AND ");
				}
				Tblstapp app = (Tblstapp) dbServiceLOS.executeUniqueHQLQueryMaxResultOne("FROM Tblstapp WHERE appno=:appno", params);
					if(app.getApplicationtype()==4 && cflevel==0){
						params.put("cfrefnoconcat", app.getCfrefnoconcat());
						hql.append("cfrefnoconcat=:cfrefnoconcat AND ");
					}
//				System.out.println("QUERY: "+hql.toString().substring(0, hql.length() - 5) + "\nWITH PARAMS: "+params);
				list  = (List<Tbcfdetails>) dbServiceLOS.executeListHQLQuery(hql.toString().substring(0, hql.length() - 5), params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Tbcfdetails getCreditFacilityDetails(String appno, String cfrefno, Integer cflevel, String cfseqno,
			String cfsubseqno, String cfrefnoconcat) {
		Tbcfdetails cf = new Tbcfdetails();
		params.put("appno", appno);
		try {
			if(appno != null && cfrefno != null && cflevel != null && cfseqno != null && cfsubseqno != null){
				params.put("cfrefno", cfrefno);
				params.put("cflevel", cflevel);
				params.put("cfseqno", cfseqno);
				params.put("cfsubseqno", cfsubseqno);
				cf  = (Tbcfdetails) dbServiceLOS.executeUniqueHQLQuery("FROM Tbcfdetails WHERE id.cfappno=:appno AND id.cfrefno=:cfrefno AND id.cflevel=:cflevel AND id.cfseqno=:cfseqno AND id.cfsubseqno=:cfsubseqno", params);
			}else{
				System.out.println(">>> " + cfrefnoconcat);
				if(cfrefnoconcat != null){
					params.put("cfrefnoconcat", cfrefnoconcat);
					cf  = (Tbcfdetails) dbServiceLOS.executeUniqueHQLQuery("FROM Tbcfdetails WHERE id.cfappno=:appno AND cfrefnoconcat=:cfrefnoconcat", params);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cf;
	}

	@Override
	public String deleteCreditFacility(String appno, String cfrefno, Integer cflevel, String cfseqno,
			String cfsubseqno) {
		String flag = "failed";
		StringBuilder hqlmain = new StringBuilder();
		StringBuilder hqlcoobligor = new StringBuilder();
		StringBuilder hqlcovenants = new StringBuilder();
		StringBuilder hqltermscondition = new StringBuilder();
		StringBuilder hqlcfcompany = new StringBuilder();
		StringBuilder strQueryExt = new StringBuilder();
		try {
			if(appno != null){
				params.put("appno", appno);
				hqlmain.append("DELETE FROM Tbcfdetails WHERE cfappno=:appno AND ");
				hqlcoobligor.append("DELETE FROM Tbcfcoobligor WHERE cfappno=:appno AND ");
				hqlcovenants.append("DELETE FROM Tbcfcovenants WHERE cfappno=:appno AND ");
				hqltermscondition.append("DELETE FROM Tbcftermconditions WHERE cfappno=:appno AND ");
				hqlcfcompany.append("DELETE FROM Tbcfcompany WHERE cfappno=:appno AND ");
				if (cfrefno != null) {
					params.put("cfrefno", cfrefno);
					strQueryExt.append("cfrefno=:cfrefno AND ");
				}
				if (cflevel != null) {
					params.put("cflevel", cflevel);
					strQueryExt.append("cflevel=:cflevel AND ");
				}
				if (cfseqno != null) {
					params.put("cfseqno", cfseqno);
					strQueryExt.append("cfseqno=:cfseqno AND ");
				}
				if (cfsubseqno != null) {
					params.put("cfsubseqno", cfsubseqno);
					strQueryExt.append("cfsubseqno=:cfsubseqno AND ");
				}
				hqlmain.append(strQueryExt.toString());
				hqlcoobligor.append(strQueryExt.toString());
				hqlcovenants.append(strQueryExt.toString());
				hqltermscondition.append(strQueryExt.toString());
				hqlcfcompany.append(strQueryExt.toString());
				Integer res = dbServiceLOS.executeUpdate(hqlmain.toString().substring(0, hqlmain.length() - 5), params);
				if(res > 0){
					flag = "success";
					dbServiceLOS.executeUpdate(hqlcoobligor.toString().substring(0, hqlcoobligor.length() - 5), params);
					dbServiceLOS.executeUpdate(hqlcovenants.toString().substring(0, hqlcovenants.length() - 5), params);
					dbServiceLOS.executeUpdate(hqltermscondition.toString().substring(0, hqltermscondition.length() - 5), params);
					dbServiceLOS.executeUpdate(hqlcfcompany.toString().substring(0, hqlcfcompany.length() - 5), params);
					
					String hqlsubfacility = null;
					String hqlsubcoobligor = null;
					String hqlsubcovenants = null;
					String hqlsubtermscondition = null;
					String hqlsubcfcompany = null;
					
					if (appno != null && cfrefno != null && cflevel != null) {
						params.put("appno", appno);
						params.put("cfrefno", cfrefno);
						params.put("cflevel", cflevel);
						
						//Delete Subfacility, Coobligor, Covenants, Termscondition
						if (cflevel == 0) {
							hqlsubfacility = "DELETE FROM Tbcfdetails WHERE cfappno=:appno AND cfrefno=:cfrefno";
							hqlsubcoobligor = "DELETE FROM Tbcfcoobligor WHERE cfappno=:appno AND cfrefno=:cfrefno";
							hqlsubcovenants = "DELETE FROM Tbcfcovenants WHERE cfappno=:appno AND cfrefno=:cfrefno";
							hqlsubtermscondition = "DELETE FROM Tbcftermconditions WHERE cfappno=:appno AND cfrefno=:cfrefno";
							hqlsubcfcompany = "DELETE FROM Tbcfcompany WHERE cfappno=:appno AND cfrefno=:cfrefno";
						
						}
						if(cfseqno != null){
							if(cflevel > 0 ){
								params.put("cflevel", cflevel + 1);
							}
							params.put("cfseqno", cfseqno);
							hqlsubfacility = "DELETE FROM Tbcfdetails WHERE cfappno=:appno AND cfrefno=:cfrefno AND cflevel=:cflevel AND cfseqno=:cfseqno";
							hqlsubcoobligor = "DELETE FROM Tbcfcoobligor WHERE cfappno=:appno AND cfrefno=:cfrefno AND cflevel=:cflevel AND cfseqno=:cfseqno";
							hqlsubcovenants = "DELETE FROM Tbcfcovenants WHERE cfappno=:appno AND cfrefno=:cfrefno AND cflevel=:cflevel AND cfseqno=:cfseqno";
							hqlsubtermscondition = "DELETE FROM Tbcftermconditions WHERE cfappno=:appno AND cfrefno=:cfrefno AND cflevel=:cflevel AND cfseqno=:cfseqno";
							hqlsubcfcompany = "DELETE FROM Tbcfcompany WHERE cfappno=:appno AND cfrefno=:cfrefno AND cflevel=:cflevel AND cfseqno=:cfseqno";
							
						}
						if(hqlsubfacility != null){
							dbServiceLOS.executeUpdate(hqlsubfacility, params);
							dbServiceLOS.executeUpdate(hqlsubcoobligor, params);
							dbServiceLOS.executeUpdate(hqlsubcovenants, params);
							dbServiceLOS.executeUpdate(hqlsubtermscondition, params);
							dbServiceLOS.executeUpdate(hqlsubcfcompany, params);
						}
						
					}
					
					//Replicate CF Details to LAM - Kevin 01-16-2019
					replicateCFDetailsToLAMLoanDetails(appno);
					
					// HISTORY (Kevin 10.25.2019)
					if(flag.equals("success")){
						if(cflevel != null && cflevel > 0){
							String cfrefnoconcat = cfrefno + "-" + cflevel + "-"+ cfseqno + "-" + cfsubseqno;
							HistoryService h = new HistoryServiceImpl();
							h.saveHistory(appno, AuditLogEvents.DELETE_CREDIT_FACILITY, "Deleted sub-facility - CF Reference No.: "+cfrefnoconcat+".");
						}else{
							HistoryService h = new HistoryServiceImpl();
							h.saveHistory(appno, AuditLogEvents.DELETE_CREDIT_FACILITY, "Deleted credit line - CF Reference No.: "+cfrefno+".");
						
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateCovenants(Tbcfcovenants cfcovenants) {
		String flag = "failed";
		try {
			boolean isNew = false;
			if (cfcovenants.getId() == null) {
				isNew = true;
				cfcovenants.setCreatedby(UserUtil.securityService.getUserName());
				cfcovenants.setDatecreated(new Date());
			}else{
				params.put("id", cfcovenants.getId());
				Tbcfcovenants cfcovnts = (Tbcfcovenants) dbServiceLOS.executeUniqueHQLQuery("FROM Tbcfcovenants WHERE id=:id", params);
				if (cfcovnts != null) {
					cfcovenants.setCreatedby(cfcovnts.getCreatedby());
					cfcovenants.setDatecreated(cfcovnts.getDatecreated());
				}
				cfcovenants.setUpdatedby(UserUtil.securityService.getUserName());
				cfcovenants.setLastupdated(new Date());
			}
			params.put("covenant", cfcovenants.getCfcovenants());
			params.put("cfrefnoconcat", cfcovenants.getCfrefnoconcat());
			Tbcfcovenants checkcovenant = (Tbcfcovenants) dbServiceLOS.executeUniqueHQLQuery("FROM Tbcfcovenants WHERE cfcovenants=:covenant AND cfrefnoconcat=:cfrefnoconcat", params);
			if (checkcovenant == null) {
				if(cfcovenants.getCfrefnoconcat()==null){
					cfcovenants.setCfrefnoconcat(cfcovenants.getCfrefno()+"-"+cfcovenants.getCflevel()+"-"+cfcovenants.getCfseqno()+"-"+cfcovenants.getCfsubseqno());
				}
				if (dbServiceLOS.saveOrUpdate(cfcovenants)) {
					flag = "success";
					
					//Replicate CF Details to LAM - Kevin 01-16-2019
					replicateCFDetailsToLAMLoanDetails(cfcovenants.getCfappno());
					
					if(isNew){
						// HISTORY (Kevin 10.28.2019)
						HistoryService h = new HistoryServiceImpl();
						h.saveHistory(cfcovenants.getCfappno(), AuditLogEvents.ADDED_CF_COVENANT, "Added covenant - CF Reference No.: "+cfcovenants.getCfrefnoconcat()+".");
					}else{
						// HISTORY (Kevin 10.28.2019)
						HistoryService h = new HistoryServiceImpl();
						h.saveHistory(cfcovenants.getCfappno(), AuditLogEvents.EDIT_CF_COVENANT_DETAILS, "Updated covenant details - CF Reference No.: "+cfcovenants.getCfrefnoconcat()+".");
					
					}
				}
			} else {
				flag = "existing";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcfcovenants> getListOfCovenants(String appno, String cfrefno, Integer cflevel, String cfseqno,
			String cfsubseqno, String cfrefnoconcat) {
		List<Tbcfcovenants> list = new ArrayList<Tbcfcovenants>();
		StringBuilder hql = new StringBuilder();
		try {
			if(appno != null){
				params.put("appno", appno);
				hql.append("FROM Tbcfcovenants WHERE cfappno=:appno AND ");
				
				if (cfrefno != null) {
					params.put("cfrefno", cfrefno);
					hql.append("cfrefno=:cfrefno AND ");
				}
				if (cflevel != null) {
					params.put("cflevel", cflevel);
					hql.append("cflevel=:cflevel AND ");
				}
				if (cfseqno != null) {
					params.put("cfseqno", cfseqno);
					hql.append("cfseqno=:cfseqno AND ");
				}
				if (cfsubseqno != null) {
					params.put("cfsubseqno", cfsubseqno);
					hql.append("cfsubseqno=:cfsubseqno AND ");
				}
				if(cfrefnoconcat != null){
					hql = new StringBuilder();
					params.put("cfrefnoconcat", cfrefnoconcat);
					hql.append("FROM Tbcfcovenants WHERE cfappno=:appno AND cfrefnoconcat=:cfrefnoconcat AND ");
				}
				list  = (List<Tbcfcovenants>) dbServiceLOS.executeListHQLQuery(hql.toString().substring(0, hql.length() - 5), params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteCoobligor(Integer id, String appno, String cfrefno, Integer cflevel, String cfseqno, String cfsubseqno, Boolean isfromfrontend) {
		String flag = "failed";
		StringBuilder hql = new StringBuilder();
    	try {
    		
    		if(appno != null){
				params.put("appno", appno);
				hql.append("DELETE FROM Tbcfcoobligor WHERE cfappno=:appno AND ");
				
				if(id != null){
		    		params.put("id", id);
		    		hql.append("id=:id AND ");
	    		}
				if (cfrefno != null) {
					params.put("cfrefno", cfrefno);
					hql.append("cfrefno=:cfrefno AND ");
				}
				if (cflevel != null) {
					params.put("cflevel", cflevel);
					hql.append("cflevel=:cflevel AND ");
				}
				if (cfseqno != null) {
					params.put("cfseqno", cfseqno);
					hql.append("cfseqno=:cfseqno AND ");
				}
				if (cfsubseqno != null) {
					params.put("cfsubseqno", cfsubseqno);
					hql.append("cfsubseqno=:cfsubseqno AND ");
				}
				
				//Retain Main Facility - Kevin 02.11.2019
				if(isfromfrontend != null && isfromfrontend == false){
					hql.append("cflevel > 0 AND ");
				}
				int res = dbServiceLOS.executeUpdate(hql.toString().substring(0, hql.length() - 5), params);
	    		if(res > 0){
	    			flag = "success";
	    			
	    			//Replicate CF Details to LAM - Kevin 01-16-2019
					replicateCFDetailsToLAMLoanDetails(appno);
					
					// HISTORY (Kevin 10.28.2019)
					String cfrefnoconcat = cfrefno + "-" + cflevel + "-"+ cfseqno + "-" + cfsubseqno;
					HistoryService h = new HistoryServiceImpl();
					h.saveHistory(appno, AuditLogEvents.DELETE_CF_COOBLIGOR, "Deleted Co-obligor - CF Reference No.: "+cfrefnoconcat+".");
				
	    		}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcovenants> getDefaultCovenants(String str) {
		List<Tbcovenants> list = new ArrayList<Tbcovenants>();
		try {
			str = str == null ? "" : str;
			list  = (List<Tbcovenants>) dbServiceLOS.executeListHQLQuery("FROM Tbcovenants", params);
			if(list != null && !list.isEmpty()){
				for(Tbcovenants c : list){
					c.setCovenants(str.concat(c.getCovenants()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String addDefaultCovenants(List<Tbcovenants> defaultcovenants, String appno, String cfrefno, Integer cflevel,
			String cfseqno, String cfsubseqno, String cfrefnoconcat) {
		String flag = "failed";
		try {
			
			boolean hasexisting = false;
			if (defaultcovenants != null && !defaultcovenants.isEmpty()) {
				for (Tbcovenants c : defaultcovenants) {
						Tbcfcovenants cfcovenants = new Tbcfcovenants();
						cfcovenants.setCfappno(appno);
						cfcovenants.setCfrefno(cfrefno);
						cfcovenants.setCflevel(cflevel);
						cfcovenants.setCfseqno(cfseqno);
						cfcovenants.setCfsubseqno(cfsubseqno);
						cfcovenants.setCfrefnoconcat(cfrefnoconcat);
						cfcovenants.setCreatedby(UserUtil.securityService.getUserName());
						cfcovenants.setDatecreated(new Date());
						cfcovenants.setCfcovenants(c.getCovenants());
						//check if existing covenant
						params.put("covenant", c.getCovenants());
						params.put("cfrefnoconcat", cfrefnoconcat);
						Tbcfcovenants checkcovenant = (Tbcfcovenants) dbServiceLOS.executeUniqueHQLQuery("FROM Tbcfcovenants WHERE cfcovenants=:covenant AND cfrefnoconcat=:cfrefnoconcat", params);
						if (checkcovenant == null) {
							dbServiceLOS.saveOrUpdate(cfcovenants);
						} else {
							hasexisting = true;
						}
				}
				if(hasexisting){
					flag = "existing";
				}else{
					flag = "success";
					//Replicate CF Details to LAM - Kevin 01-16-2019
					replicateCFDetailsToLAMLoanDetails(appno);
					
					// HISTORY (Kevin 10.28.2019)
					HistoryService h = new HistoryServiceImpl();
					h.saveHistory(appno, AuditLogEvents.ADD_CF_DEFAULT_COVENANTS, "Added default covenant(s) - CF Reference No.: "+cfrefnoconcat+".");
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteCovenants(Integer id) {
		String flag = "failed";
    	try {
    		if(id != null){
				params.put("id", id);
				Tbcfcovenants res = (Tbcfcovenants) dbServiceLOS.executeUniqueHQLQuery("FROM Tbcfcovenants WHERE id=:id", params);
	    		if(res != null){
	    			String appno = res.getCfappno();
	    			if(dbServiceLOS.delete(res)){
	    				flag = "success";
	    				
	    				// HISTORY (Kevin 10.28.2019)
						HistoryService h = new HistoryServiceImpl();
						h.saveHistory(appno, AuditLogEvents.DELETE_CF_COVENANT, "Deleted covenant - CF Reference No.: "+res.getCfrefnoconcat()+".");
						
	    			}
	    			
	    			//Replicate CF Details to LAM - Kevin 01-16-2019
					replicateCFDetailsToLAMLoanDetails(appno);
	    		}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateCFTermsCondition(Tbcftermconditions termcondtition) {
		String flag = "failed";
		try {
			boolean isNew = false;
			if (termcondtition.getId() == null) {
				isNew = true;
				termcondtition.setCreatedby(UserUtil.securityService.getUserName());
				termcondtition.setDatecreated(new Date());
			}else{
				params.put("id", termcondtition.getId());
				Tbcftermconditions cftermcond = (Tbcftermconditions) dbServiceLOS.executeUniqueHQLQuery("FROM Tbcftermconditions WHERE id=:id", params);
				if (cftermcond != null) {
					termcondtition.setCreatedby(cftermcond.getCreatedby());
					termcondtition.setDatecreated(cftermcond.getDatecreated());
				}
				termcondtition.setUpdatedby(UserUtil.securityService.getUserName());
				termcondtition.setLastupdated(new Date());
			}
			if (dbServiceLOS.saveOrUpdate(termcondtition)) {
				flag = "success";
				
				//Replicate CF Details to LAM - Kevin 01-16-2019
				replicateCFDetailsToLAMLoanDetails(termcondtition.getCfappno());
				
				if(isNew){
					// HISTORY (Kevin 10.28.2019)
					HistoryService h = new HistoryServiceImpl();
					h.saveHistory(termcondtition.getCfappno(), AuditLogEvents.ADD_CF_TERMS_AND_CONDITIONS, "Added terms and conditions - CF Reference No.: "+termcondtition.getCfrefnoconcat()+".");
					
				}else{
					// HISTORY (Kevin 10.28.2019)
					HistoryService h = new HistoryServiceImpl();
					h.saveHistory(termcondtition.getCfappno(), AuditLogEvents.EDIT_CF_TERMS_AND_CONDITIONS_DETAILS, "Updated terms and conditions details - CF Reference No.: "+termcondtition.getCfrefnoconcat()+".");
					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcftermconditions> getListOfCFTermsCondition(String appno, String cfrefno, Integer cflevel,
			String cfseqno, String cfsubseqno, String cfrefnoconcat) {
		List<Tbcftermconditions> list = new ArrayList<Tbcftermconditions>();
		StringBuilder hql = new StringBuilder();
		try {
			if(appno != null){
				params.put("appno", appno);
				hql.append("FROM Tbcftermconditions WHERE cfappno=:appno AND ");
				
				if (cfrefno != null) {
					params.put("cfrefno", cfrefno);
					hql.append("cfrefno=:cfrefno AND ");
				}
				if (cflevel != null) {
					params.put("cflevel", cflevel);
					hql.append("cflevel=:cflevel AND ");
				}
				if (cfseqno != null) {
					params.put("cfseqno", cfseqno);
					hql.append("cfseqno=:cfseqno AND ");
				}
				if (cfsubseqno != null) {
					params.put("cfsubseqno", cfsubseqno);
					hql.append("cfsubseqno=:cfsubseqno AND ");
				}
				if(cfrefnoconcat != null){
					hql = new StringBuilder();
					params.put("cfrefnoconcat", cfrefnoconcat);
					hql.append("FROM Tbcftermconditions WHERE cfappno=:appno AND cfrefnoconcat=:cfrefnoconcat AND ");
				}
				list  = (List<Tbcftermconditions>) dbServiceLOS.executeListHQLQuery(hql.toString().substring(0, hql.length() - 5), params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteCFTermsCondition(Integer id) {
		String flag = "failed";
    	try {
    		if(id != null){
				params.put("id", id);
				
	    		Tbcftermconditions res = (Tbcftermconditions) dbServiceLOS.executeUniqueHQLQuery("FROM Tbcftermconditions WHERE id=:id", params);
	    		if(res != null){
	    			String appno = res.getCfappno();
	    			if(dbServiceLOS.delete(res)){
	    				flag = "success";
	    				
	    				// HISTORY (Kevin 10.28.2019)
						HistoryService h = new HistoryServiceImpl();
						h.saveHistory(res.getCfappno(), AuditLogEvents.DELETE_CF_TERMS_AND_CONDITIONS, "Deleted terms and conditions - CF Reference No.: "+res.getCfrefnoconcat()+".");
						
	    			}
	    			
	    			//Replicate CF Details to LAM - Kevin 01-16-2019
					replicateCFDetailsToLAMLoanDetails(appno);
	    		}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getDefaultTermsCondition(String c1, String c2, String c3, String facilitycode) {
		List<String> list = new ArrayList<String>();
		try {
			if(c1 != null){
				list.add(c1);
			}
			if(c2 != null){
				list.add(c2);
			}
			if(c3 != null){
				list.add(c3);
			}
			if(facilitycode != null){
				params.put("facilitycode", facilitycode);
				List<String> prodpercf  = (List<String>) dbServiceLOS.execSQLQueryTransformer("SELECT DISTINCT(repaymentscheme) FROM TBLOANSCHEMEPERPROD WHERE prodcode IN(SELECT DISTINCT (prodcode) FROM TBLOANPRODPERCF WHERE facilitycode = '"+facilitycode+"')", params, null, 1);
				if(prodpercf != null && !prodpercf.isEmpty()){
					String ident = prodpercf.size() > 1 ? " are " : " is ";
					String p1 = prodpercf.size() > 1 ? "(" : "";
					String p2 = prodpercf.size() > 1 ? ")" : "";
					String strExt = "";
					String facilityname = (String) dbServiceLOS.execSQLQueryTransformer("SELECT TOP 1 facilityname FROM Tbcfmaintenance WHERE facilitycode = '"+facilitycode+"'", params, null, 0);
					for (String c : prodpercf) {
						strExt += ", " + c;
					}
					list.add("For " + facilityname + " that repayment type" + ident + p1 + strExt.substring(2) + p2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanProdPerCFForm> getListOfLoanProdPerCF(String prodcode, String facilitycode) {
		 List<LoanProdPerCFForm> list = new ArrayList<LoanProdPerCFForm>();
		 try {
			 list  = (List<LoanProdPerCFForm>) dbServiceLOS.execSQLQueryTransformer("SELECT prodcode, facilitycode, productname, facilityname, repaymenttype FROM Tbloanprodpercf", params, LoanProdPerCFForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String addDefaultTermsCondition(List<String> termcondition, String appno, String cfrefno, Integer cflevel,
			String cfseqno, String cfsubseqno, String cfrefnoconcat) {
		String flag = "failed";
		try {
			if(termcondition != null && !termcondition.isEmpty()){
				for(String c : termcondition){
					Tbcftermconditions termcon = new Tbcftermconditions();
					termcon.setCfappno(appno);
					termcon.setCfrefno(cfrefno);
					termcon.setCflevel(cflevel);
					termcon.setCfseqno(cfseqno);
					termcon.setCfsubseqno(cfsubseqno);
					termcon.setCfrefnoconcat(cfrefnoconcat);
					termcon.setCreatedby(UserUtil.securityService.getUserName());
					termcon.setDatecreated(new Date());
					termcon.setCftermconditions(c);
					dbServiceLOS.saveOrUpdate(termcon);
				}
				flag = "success";
				
				//Replicate CF Details to LAM - Kevin 01-16-2019
				replicateCFDetailsToLAMLoanDetails(appno);
				
				// HISTORY (Kevin 10.28.2019)
				HistoryService h = new HistoryServiceImpl();
				h.saveHistory(appno, AuditLogEvents.ADD_DEFAULT_CF_TERMS_AND_CONDITIONS, "Added default terms and conditions - CF Reference No.: "+cfrefnoconcat+".");
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcfdetails> findCFbyCIFNo(String cifno){
		 List<Tbcfdetails> list = new ArrayList<Tbcfdetails>();
		 params.put("cifno", cifno);
		 try {
			 list  = (List<Tbcfdetails>) dbServiceLOS.executeListHQLQuery("FROM Tbcfdetails Where cifno=:cifno", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//AVAILMENT- ABBY
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcfdetails> getCfDetailsByCifNoAndCfLevel(String cifno, Integer cflevel, Boolean isexpired) {
		List<Tbcfdetails> list = new ArrayList<Tbcfdetails>();
		params.put("cifno", cifno);
		params.put("cflevel", cflevel);
		try {
			String hql = "FROM Tbcfdetails Where cifno=:cifno AND cflevel=:cflevel";
			
			//Approved or Expired	
			if(isexpired != null && isexpired){
				hql += " AND cfstatus IN('1','3')";
			}else{
				hql += " AND cfstatus = '1'";
			}
			
			// Check if Coobligagor
			Tbcfcoobligor coob = (Tbcfcoobligor) dbServiceLOS.executeUniqueHQLQueryMaxResultOne("FROM Tbcfcoobligor WHERE cfcifno=:cifno", params);
			if (coob != null) {
				// Get the Main Line
				if (coob.getCfrefno() != null) {
					params.put("cfrefno", coob.getCfrefno());
					list = (List<Tbcfdetails>) dbServiceLOS.executeListHQLQuery("FROM Tbcfdetails a WHERE ((a.id.cfrefno=:cfrefno) OR (a.cifno=:cifno)) AND a.id.cflevel='0' AND a.cfstatus IN('1','3')", params);
				
				}
			}else{
			// If not, the Main Line
				list = (List<Tbcfdetails>) dbServiceLOS.executeListHQLQuery(hql, params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbaccountinfo> getListOfAvailmentsByStatus(String cfrefnoconcat, Integer applicationtype,
			String txstat, String cifno) {
		List<Tbaccountinfo> list = new ArrayList<Tbaccountinfo>();
		if (txstat != null && applicationtype != null && cfrefnoconcat != null) {
			params.put("cfrefnoconcat", cfrefnoconcat);
			params.put("applicationtype", applicationtype);
			params.put("txstat", txstat);
			params.put("cifno", cifno);
			/*
			 *>TXSTAT        
				>3        New
				>4        For Approval
				>5        Approved
				>9        For Posting
				>10        Posted
				>6        Cancelled
				>7        Unposted
			 */
			try {
				if (txstat.equals("A")) { // APPROVED
					list = (List<Tbaccountinfo>) dbServiceLOS.executeListHQLQuery(
							"FROM Tbaccountinfo WHERE clientid=:cifno AND applicationtype=:applicationtype AND txstat IN('5','9','10','7')",
							params);
				} else if (txstat.equals("O")) { // ONGOING
					list = (List<Tbaccountinfo>) dbServiceLOS.executeListHQLQuery(
							"FROM Tbaccountinfo Where clientid=:cifno AND applicationtype=:applicationtype AND txstat IN('3','4')",
							params);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	@Override
	public String createNewLineAvailment(LineAvailmentForm form) {
		// CREATE NEW LINE AVAILMENT RECORD IN LSTAPP
		String result = "failed";
		try{
			Tblstapp app = new Tblstapp();
			if(form.getAppno() == null){
				app.setAppno(ApplicationNoGenerator.generateApplicationNo("AV"));
				app.setCifno(form.getCifno());
				app.setApplicationtype(5);
				app.setCustomertype("2");
				app.setTypefacility(form.getTypefacility());
				app.setDatecreated(new Date());
				app.setCreatedby(UserUtil.securityService.getUserName());
				app.setMainlinerefno(form.getMainlinerefno());
				app.setCfrefno(form.getCfrefno());
				app.setCifname(form.getCifname());
				if(dbServiceLOS.save(app)){
					result = "success";
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExistingFacilityForm> searchExistingFacilityByCifNo(String cifno) {
		DBService dbServiceCOOP = new DBServiceImpl();
		List<ExistingFacilityForm> list = new ArrayList<ExistingFacilityForm>();
		try {
			if (cifno != null) {
				params.put("cifno", cifno);
				//As Per Ate Abs, Display APPROVED FACILITY ONLY  09.17.2018
				// TBCFDETAILS
				//AND a.applicationtype = '2' 
				list = (List<ExistingFacilityForm>) dbServiceCOOP.execSQLQueryTransformer(
						"SELECT a.cfappno,a.cifno,a.cfrefnoconcat,c.facilityname as facilitytype,a.cfapprovedamt,a.cfproposedamt,d.desc1 as cfstatus FROM TBAPPROVEDCF a "
								+ "LEFT JOIN Tbcfmaintenance c ON c.facilitycode=a.cfcode  "
								+ "INNER JOIN TBCODETABLE d ON d.codevalue=a.cfstatus AND d.codename = 'CFSTATUS' AND a.cfstatus = '1' "
								+ "WHERE a.cifno=:cifno",
						params, ExistingFacilityForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String replicateCreditFacility(String appno, String cfrefno, String newappno) {
		String flag = "failed";
		try{
			if(appno != null && cfrefno != null && newappno != null){
				params.put("appno", appno);
				params.put("cfrefno", cfrefno);
				params.put("newcifno", newappno);
				//Credit Facility
				List<Tbcfdetails> cflist = (List<Tbcfdetails>) dbServiceLOS.executeListHQLQuery("FROM Tbcfdetails WHERE id.cfappno=:appno AND id.cfrefno=:cfrefno", params);
				if(cflist != null){
					dbServiceLOS.executeUpdate("DELETE FROM Tbcfdetails WHERE cfappno=:newcifno AND cfrefno=:cfrefno", params);
					for(Tbcfdetails cf: cflist){
						int updtCnt = cf.getCfupdatecount() == null ? 0 : cf.getCfupdatecount();
						cf.setCfupdatecount(updtCnt + 1);
						cf.setCfupdate(new Date());
						cf.setCflastupdateby(UserUtil.securityService.getUserName());
						
						if(dbServiceLOS.saveOrUpdate(cf)){
							Tbcfdetails newCF = cf;
							newCF.getId().setCfappno(newappno);
							newCF.setPrevcfappno(appno);
							
							//For Approval
							newCF.setCfstatus("4");
							
							newCF.setCfrequestedvalidity(null);
							newCF.setCfupdatecount(0);
							newCF.setCfupdate(new Date());
							newCF.setCflastupdateby(UserUtil.securityService.getUserName());
							dbServiceLOS.save(newCF);
						}
					}
				}
				//Co-Obligor
				List<Tbcfcoobligor> coobligor = (List<Tbcfcoobligor>) dbServiceLOS.executeListHQLQuery("FROM Tbcfcoobligor WHERE cfappno=:appno", params);
				if(coobligor != null && !coobligor.isEmpty()){
					dbServiceLOS.executeUpdate("DELETE FROM Tbcfcoobligor WHERE cfappno=:newcifno AND cfrefno=:cfrefno", params);
					for(Tbcfcoobligor cfcoobligor : coobligor) {
						cfcoobligor.setId(null);
						cfcoobligor.setCfappno(newappno);
						cfcoobligor.setCfstatus("4");
						cfcoobligor.setCfaddedby(UserUtil.securityService.getUserName());
						cfcoobligor.setCfcreateddate(new Date());
						dbServiceLOS.save(cfcoobligor);
					}
				}
				//Covenants
				List<Tbcfcovenants> cfcovenants = (List<Tbcfcovenants>) dbServiceLOS.executeListHQLQuery("FROM Tbcfcovenants WHERE cfappno=:appno", params);
				if(cfcovenants != null && !cfcovenants.isEmpty()){
					dbServiceLOS.executeUpdate("DELETE FROM Tbcfcovenants WHERE cfappno=:newcifno AND cfrefno=:cfrefno", params);
					for(Tbcfcovenants cfcovnts : cfcovenants) {
						cfcovnts.setId(null);
						cfcovnts.setCfappno(newappno);
						cfcovnts.setCreatedby(UserUtil.securityService.getUserName());
						cfcovnts.setDatecreated(new Date());
						dbServiceLOS.save(cfcovnts);
					}
				}
				//Terms & Conditions
				List<Tbcftermconditions> termcond = (List<Tbcftermconditions>) dbServiceLOS.executeListHQLQuery("FROM Tbcftermconditions WHERE cfappno=:appno", params);
				if(termcond != null && !termcond.isEmpty()){
					dbServiceLOS.executeUpdate("DELETE FROM Tbcftermconditions WHERE cfappno=:newcifno AND cfrefno=:cfrefno", params);
					for(Tbcftermconditions cftermcond : termcond) {
						cftermcond.setId(null);
						cftermcond.setCfappno(newappno);
						cftermcond.setCreatedby(UserUtil.securityService.getUserName());
						cftermcond.setDatecreated(new Date());
						dbServiceLOS.save(cftermcond);
					}
				}
				//Shared Company
				List<Tbcfcompany> company = (List<Tbcfcompany>) dbServiceLOS.executeListHQLQuery("FROM Tbcfcompany WHERE cfappno=:appno", params);
				if(termcond != null && !termcond.isEmpty()){
					dbServiceLOS.executeUpdate("DELETE FROM Tbcfcompany WHERE cfappno=:newcifno AND cfrefno=:cfrefno", params);
					for(Tbcfcompany cfcompany : company) {
						cfcompany.setId(null);
						cfcompany.setCfappno(newappno);
						dbServiceLOS.save(cfcompany);
					}
				}
				flag = "success";
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * --Replicate CF Details to LAM--
	 * @author Kevin (01-16-2019)
	 * */
	public static void replicateCFDetailsToLAMLoanDetails(String appno) {
		Map<String, Object> params = HQLUtil.getMap();
		DBService dbServiceLOS = new DBServiceImplLOS();
		try {
			String prefix = appno.substring(0, 2);
			params.put("appno", appno);
			Tblstapp app = (Tblstapp) dbServiceLOS.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
			if (app != null && (prefix.equals("LI") || prefix.equals("AM")) && app.getApplicationstatus() == 3) {// Evaluation
				Integer maxId = (Integer) dbServiceLOS
						.executeUniqueSQLQuery("SELECT MAX(evalreportid) FROM Tbevalreport WHERE appno=:appno", params);
				if (maxId != null) {
					LAMService lamsrvc = new LAMServiceImpl();
					lamsrvc.saveCFDetailsToLAM(appno, maxId);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String saveCFCompany(List<CompanyForm> company, String appno, String cfrefno, Integer cflevel,
			String cfseqno, String cfsubseqno, String cfrefnoconcat) {
		String flag = "failed";
		try {
			if(cfrefnoconcat != null){
				params.put("cfrefnoconcat", cfrefnoconcat);
				if (company != null && !company.isEmpty()) {
					String compcode = "''";
					for (CompanyForm c : company) {
						compcode += ", '" + c.getCompanycode() + "'";
						params.put("companycode", c.getCompanycode());
						Tbcfcompany cfcompany = (Tbcfcompany) dbServiceLOS.executeUniqueHQLQuery(
								"FROM Tbcfcompany WHERE companycode=:companycode AND cfrefnoconcat=:cfrefnoconcat", params);
						if (cfcompany == null) {
							cfcompany = new Tbcfcompany();
							cfcompany.setCfappno(appno);
							cfcompany.setCfrefno(cfrefno);
							cfcompany.setCflevel(cflevel);
							cfcompany.setCfseqno(cfseqno);
							cfcompany.setCfsubseqno(cfsubseqno);
							cfcompany.setCfrefnoconcat(cfrefnoconcat);
							cfcompany.setCompanycode(c.getCompanycode());
							cfcompany.setCompanyname(c.getCompanyname());
							cfcompany.setAssignedby(UserUtil.securityService.getUserName());
							cfcompany.setDateassigned(new Date());
							dbServiceLOS.save(cfcompany);
						} else {
							cfcompany.setCompanycode(c.getCompanycode());
							cfcompany.setCompanyname(c.getCompanyname());
							dbServiceLOS.saveOrUpdate(cfcompany);
						}
					}
					
					//Delete if not in selected company funder
					dbServiceLOS.executeUpdate("DELETE FROM Tbcfcompany WHERE companycode NOT IN("+compcode+") AND cfrefnoconcat=:cfrefnoconcat", params);
				}else{
					//Delete if empty selection
					dbServiceLOS.executeUpdate("DELETE FROM Tbcfcompany WHERE cfrefnoconcat=:cfrefnoconcat", params);
					
				}
				
				// Replicate CF Details to LAM - Kevin 01-16-2019
				replicateCFDetailsToLAMLoanDetails(appno);
				
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyForm> getCFCompanyList(String cfrefnoconcat) {
		List<CompanyForm> list = new ArrayList<CompanyForm>();
		try {
			if(cfrefnoconcat != null){
				params.put("cfrefnoconcat", cfrefnoconcat);
				list = (List<CompanyForm>) dbServiceLOS.execSQLQueryTransformer("SELECT companycode, companyname FROM Tbcfcompany WHERE cfrefnoconcat=:cfrefnoconcat", params, CompanyForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//MAR

}
