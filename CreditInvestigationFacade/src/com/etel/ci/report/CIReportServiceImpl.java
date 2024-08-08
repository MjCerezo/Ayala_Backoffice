package com.etel.ci.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbmemberbusiness;
import com.coopdb.data.Tbmembercreditcardinfo;
import com.coopdb.data.Tbmemberemployment;
import com.coopdb.data.Tbmemberfinancialinfo;
import com.etel.ci.CreditInvestigationServiceImpl;
import com.etel.ci.report.desk.BVRDesk;
import com.etel.ci.report.desk.EVRDesk;
import com.etel.ci.report.desk.EvrBvrActivities;
import com.etel.ci.report.desk.PDRNDesk;
import com.etel.ci.report.field.PDRNField;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImplLOS;
import com.etel.dataentry.FullDataEntryService;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.utils.ApplicationNoGenerator;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

import member.MemberService;
import member.MemberServiceImpl;

import com.coopdb.data.Tbcibankcheck;
import com.coopdb.data.Tbcibvr;
import com.coopdb.data.Tbcicreditcheck;
import com.coopdb.data.Tbcidependents;
import com.coopdb.data.Tbcievr;
import com.coopdb.data.Tbcipdrn;
import com.coopdb.data.Tbcipdrnresidence;
import com.coopdb.data.Tbcipdrnverhighlights;
import com.coopdb.data.Tbcireportmain;
import com.coopdb.data.Tbcirequest;
import com.coopdb.data.Tbcitradecheck;
import com.coopdb.data.Tbcitraderefcorp;
import com.coopdb.data.Tbdeskciactivity;
import com.coopdb.data.Tbdeskcidetails;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tblstexistingloansother;
import com.coopdb.data.Tbmember;

public class CIReportServiceImpl extends CreditInvestigationServiceImpl implements CIReportService{
	
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private String username = secservice.getUserName();
	public DBService dbserviceLOS = new DBServiceImplLOS();

	@Override
	public String createReport(Tbcireportmain r) {
		Tbcireportmain mr = new Tbcireportmain();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			Tbcirequest i = getCIRequest(r.getCirequestid());
//			if (!i.getAssignedci().equals(securityService.getUserName())) {
//				// Assigned CI is not equal to user currently login
//				return "Problem creating report.";
//			}
			mr.setCireportid(ApplicationNoGenerator.generateReportID("CI"));
			mr.setCirequestid(r.getCirequestid());
			mr.setCifno(r.getCifno());
			mr.setCustomername(getCIFRecord(r.getCifno()).getCustomername());
			mr.setCompanycode(i.getCompanycode());
			mr.setCustomertype(i.getCustomertype());
			mr.setReasonforci(i.getPurposeforci());
			String appno = null;
			if (i.getAppno() != null) {
				// inside application
				mr.setParticipationcode(i.getParticipationcode());
				mr.setAppno(i.getAppno());
				appno = i.getAppno();
			}
//			mr.setReportdate(new Date());
			mr.setDaterequested(i.getDaterequested());
			mr.setRequestedby(i.getRequestedby());
			mr.setReportedby(i.getAssignedci());
			// REPORTSTATUS = New
			mr.setStatus("0");
			mr.setStatusdatetime(new Date());
			if (dbservice.save(mr)) {
				if(mr.getAppno() != null){
					// Set status to On-Process(Tbinvestigationinst) Inside application
					params.put("appno", mr.getAppno());
					params.put("cifno", mr.getCifno());
					params.put("invsttype", "CI");
					//On-Process
					dbservice.executeUpdate("UPDATE Tbinvestigationinst SET status='2' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype",
							params);
				}
				
				//create report
				if(i.getCitype().equals("1")){
					//Desk CI
					
					//save the following:
					//a.	Personal Verification (PDRN) 
					//b.	Employment Verification (EVR)
					//c.	Business Verification (BVR)
					//d.	Bank Check
					//e.	Credit Check
					//f.	Trade Check 
					
					// if individual or corporate
					if(i.getCustomertype().equals("1")){
						//individual
						if(i.getIspdrn()){
							PDRNDesk.savePDRNDesk(mr.getCireportid(), mr.getCifno());
						}
						if(i.getIsevr()){
							EVRDesk.saveEVRDesk(mr.getCireportid(), mr.getCifno());
						}
						if(i.getIsbvr()){
							BVRDesk.saveBVRDesk(mr.getCireportid(), mr.getCifno(), "1");
						}
					}else{
						//corporate
						if(i.getIsbvr()){
							BVRDesk.saveBVRDesk(mr.getCireportid(), mr.getCifno(), i.getCustomertype());
						}
					}
					//
					String cifno = mr.getCifno();
					String rptid = mr.getCireportid();
					
					params.put("cifno", mr.getCifno());
					
					if(i.getIsbankcheck()){
						/** Save initial bank check from CIF **/
						@SuppressWarnings("unchecked")
						List<Tbmemberfinancialinfo> bact = (ArrayList<Tbmemberfinancialinfo>) dbservice
								.executeListHQLQuery("FROM Tbmemberfinancialinfo WHERE membershipid=:cifno and financialtype = '1'", params);
						if (bact != null) {
							for (Tbmemberfinancialinfo a : bact) {
//								if (a.getAccounttype().equals("Deposit")) {
									Tbcibankcheck ck = new Tbcibankcheck();
									ck.setCireportid(rptid);
									ck.setCifno(cifno);
									ck.setAppno(appno);
									ck.setBankaccttype(a.getAccounttype());
									ck.setBank(a.getBankname());
									ck.setBranch(a.getBranch());
//									ck.setAccountname(a.getAccountname());
//									ck.setAccountnumber(a.get);
//									ck.setDateopened(a.getDateopened());
//									ck.setAdb(a.getAdb());
									ck.setOutstandingbal(a.getOutstandingbalance());
									if(dbservice.save(ck)){
										Tbdeskciactivity activity = new Tbdeskciactivity();
										activity.setBankid(ck.getId());
										activity.setCireportid(rptid);
										activity.setCiactivity("BANK");
										activity.setCifno(cifno);
										dbservice.saveOrUpdate(activity);
									}
//								}
							}
						}
					}
					if(i.getIscreditcheck()){
						/** Save initial credit check from CIF **/
						@SuppressWarnings("unchecked")
						List<Tbmembercreditcardinfo> cact = (ArrayList<Tbmembercreditcardinfo>) dbservice
								.executeListHQLQuery("FROM Tbmembercreditcardinfo WHERE memberid=:cifno", params);
						if (cact != null) {
							for (Tbmembercreditcardinfo a : cact) {
//								if (a.getAccounttype().equals("Loan")) {
									Tbcicreditcheck cc = new Tbcicreditcheck();
									cc.setCireportid(rptid);
									cc.setCifno(cifno);
									cc.setAppno(appno);
//									cc.setLoantype(a.getAccounttype());
									cc.setBank(a.getBank());
//									cc.setBranch(a.getBranch());
//									cc.setPncnno(a.getPncnno());
//									cc.setAccountname(a.getAccountname());
//									cc.setValuedate(a.getValuedate());
//									cc.setMaturitydate(a.getMaturitydate());
									cc.setOutstandingbal(a.getOutstandingbalance());
//									cc.setCurrency(a.getCurrency());
									if(dbservice.save(cc)){
										Tbdeskciactivity activity = new Tbdeskciactivity();
										activity.setCreditid(cc.getId());
										activity.setCireportid(rptid);
										activity.setCiactivity("CREDIT");
										activity.setCifno(cifno);
										dbservice.saveOrUpdate(activity);
									}
//								}
							}
						}
					}
					
//					if(i.getIstradecheck()){
//						/** Save initial trade check from CIF **/
//						@SuppressWarnings("unchecked")
//						List<Tbtradereference> tref = (ArrayList<Tbtradereference>)dbservice
//								.executeListHQLQuery("FROM Tbtradereference WHERE cifno=:cifno", params);
//						if(tref != null){
//							for(Tbtradereference t : tref){
//								Tbcitradecheck tc = new Tbcitradecheck();
//								tc.setCireportid(rptid);
//								tc.setCifno(cifno);
//								tc.setAppno(appno);
//								tc.setTradetype(t.getTradetype());
//								// Noreen 05-17-2018
//								tc.setCompanyname(t.getClientsuppliername());
//								tc.setCompanyaddress(t.getClientsupplieraddress());
//								tc.setCompanyphoneno(t.getLandline1());
//								tc.setCountrycodephone(t.getMobile1());
//								tc.setAreacodephone(t.getMobile2());
//								
////								tc.setCompanyname(t.getCompanyname());
////								tc.setCompanyaddress(t.getCompanyaddress());
////								tc.setCountrycodephone(t.getCountrycodephone());
////								tc.setAreacodephone(t.getAreacodephone());
////								tc.setCompanyphoneno(t.getCompanyphoneno());
//								if(dbservice.save(tc)){
//									Tbdeskciactivity activity = new Tbdeskciactivity();
//									activity.setTradeid(tc.getId());
//									activity.setCireportid(rptid);
//									activity.setCiactivity("TRADE");
//									activity.setCifno(cifno);
//									dbservice.saveOrUpdate(activity);
//								}
//							}
//						}
//					}
				}else{
					//Field CI
					// if individual or corporate
					if(i.getCustomertype().equals("1")){
						//individual
						//PDRN
						//a)	Personal Verification
						//b)	Residence Verification
						//c)	Verification Highlights
						// save initial PDRN
						if(i.getIspdrn()){
							Tbcipdrn pdrn = new Tbcipdrn();
							pdrn.setCireportid(mr.getCireportid());
							pdrn.setAppno(i.getAppno());
							pdrn.setCifno(i.getCifno());
							dbservice.save(pdrn);	
							
							Tbcipdrnresidence res = new Tbcipdrnresidence();
							res.setCireportid(mr.getCireportid());
							res.setAppno(i.getAppno());
							res.setCifno(i.getCifno());
							dbservice.save(res);
							
							Tbcipdrnverhighlights h = new Tbcipdrnverhighlights();
							h.setCireportid(mr.getCireportid());
							dbservice.save(h);
						}
						
					}else{
						//corporate
						if(i.getIsbvr()){
							Tbcibvr bvr = new Tbcibvr();
							bvr.setCireportid(mr.getCireportid());
							bvr.setAppno(i.getAppno());
							bvr.setCifno(i.getCifno());
							bvr.setCustomertype(i.getCustomertype());
							dbservice.save(bvr);
						}
					}
				}
				return mr.getCireportid();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public Tbcireportmain getCIReport(String rptID) {
		Tbcireportmain m = new Tbcireportmain();
		try {
			if(rptID != null){
				Map<String, Object> params = HQLUtil.getMap();
				params.put("cireportid", rptID);
				m = (Tbcireportmain)dbservice.executeUniqueHQLQuery("FROM Tbcireportmain WHERE cireportid=:cireportid", params);
				// updates all desk details from CIF record
				if (m != null) {
					Tbcirequest i = getCIRequest(m.getCirequestid());
					// if individual or corporate
					if (i != null && i.getCustomertype().equals("1") && i.getCitype().equals("1")) {
						// individual
						if (i.getIspdrn()) {
							PDRNDesk.savePDRNDesk(m.getCireportid(), m.getCifno());
						}
						if (i.getIsevr()) {
							EVRDesk.saveEVRDesk(m.getCireportid(), m.getCifno());
						}
						if (i.getIsbvr()) {
							BVRDesk.saveBVRDesk(m.getCireportid(), m.getCifno(), "1");
						}
					} else if(i != null && (i.getCustomertype().equals("2")||i.getCustomertype().equals("3")) && i.getCitype().equals("2")){
						// corporate
						if (i.getIsbvr()) {
							BVRDesk.saveBVRDesk(m.getCireportid(), m.getCifno(), i.getCustomertype());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	@Override
	public String submitCIReport(String rptstatus, String rptid, String reasonforreturn) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("rptid", rptid);
			Tbcireportmain m = (Tbcireportmain)dbservice.executeUniqueHQLQuery("FROM Tbcireportmain WHERE cireportid=:rptid", params);
//			Tbcirequest i = getCIRequest(m.getCirequestid());

			if(m != null && rptstatus != null){
				
				m.setDateupdated(new Date());
				m.setStatus(rptstatus);
				m.setStatusdatetime(new Date());
				
//				if(rptstatus.equals("1")){
//					//to For Review
//					m.setDatesubmitted(new Date());
//					m.setReviewedby(new DefaultUsers(m.getCompanycode()).getCisupervisor());
//					if(dbservice.saveOrUpdate(m)){
//						return "For Review";
//					}
//				}
//				if(rptstatus.equals("2")){
//					//to Reviewed
//					m.setReviewedby(securityService.getUserName());
//					m.setDatereviewed(new Date());
//					if(dbservice.saveOrUpdate(m)){
//						// Completed
//						i.setStatus("3");
//						i.setStatusdate(new Date());
//						if(dbservice.saveOrUpdate(i)){
//							//Update status (Tbinvestigationinst) Inside application
//							if(m.getAppno() != null){
//								params.put("appno", m.getAppno());
//								params.put("cifno", m.getCifno());
//								params.put("invsttype", "CI");
//								Integer a = (Integer) dbservice.executeUniqueSQLQuery("SELECT COUNT(*) FROM TBCIREQUEST WHERE appno=:appno AND cifno=:cifno AND status IN ('0','1','2')", params);
//								if(a == null || (a != null && a == 0)){
//									dbservice.executeUpdate("UPDATE Tbinvestigationinst SET status='Completed' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype", params);
//								}else{
//									dbservice.executeUpdate("UPDATE Tbinvestigationinst SET status='On-Process' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype", params);
//								}
//							}							
//							return "Reviewed";
//						}
//					}
//				}
				if(rptstatus.equals("4")){
					//to Returned
					m.setReasonforreturn(reasonforreturn);
					if(dbservice.saveOrUpdate(m)){
						AuditLog.addAuditLog(
								AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("RETURN TO CI",
										AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
								"User " + username + " Returned to Credit Investigator.", username, new Date(),
								AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
						// Set status to Report Returned (Tbcirequest)
						params.put("cirequestid", m.getCirequestid());
						int res = dbservice.executeUpdate("UPDATE Tbcirequest SET status='6' WHERE cirequestid=:cirequestid", params);
						if(res > 0){
							return "Returned";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String saveOrUpdateDeskCIDetails(Tbdeskcidetails d) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(d.getId() != null){
				params.put("id", d.getId());
				int res = dbservice.executeUpdate("UPDATE Tbdeskcidetails "
						+ "SET findings='"+d.getFindings()+"', remarks='"+d.getRemarks()+"' WHERE id=:id", params);
				if(res > 0){
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdeskcidetails> getDeskCIDetails(String rptid, String activity, Integer emporbusid) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("rptid", rptid);
			params.put("activity", activity);
			params.put("emporbusid", emporbusid);
			
			if (activity.equals("PDRN")) {
				List<Tbdeskcidetails> pdrnlist = (List<Tbdeskcidetails>) dbservice.executeListHQLQuery(
						"FROM Tbdeskcidetails WHERE cireportid=:rptid AND ciactivity=:activity", params);
				PDRNDesk p = new PDRNDesk();
				return p.getPDRNDesk(pdrnlist);
			}
			if (activity.equals("EVR")) {
				List<Tbdeskcidetails> evrlist = (List<Tbdeskcidetails>) dbservice.executeListHQLQuery(
						"FROM Tbdeskcidetails WHERE cireportid=:rptid AND ciactivity=:activity "
					   +"AND employmentid=:emporbusid",params);
				EVRDesk ev = new EVRDesk();
				return ev.getEVRDesk(evrlist);
			}
			if (activity.equals("BVR")){
				if(emporbusid == null){
					List<Tbdeskcidetails> bvrlist = (List<Tbdeskcidetails>) dbservice.executeListHQLQuery(
							"FROM Tbdeskcidetails WHERE cireportid=:rptid AND ciactivity=:activity "
						   +"AND businessid IS NULL",params);
					BVRDesk bv = new BVRDesk();
					return bv.getBVRDesk(bvrlist, null);
				}else{
					List<Tbdeskcidetails> bvrlist = (List<Tbdeskcidetails>) dbservice.executeListHQLQuery(
							"FROM Tbdeskcidetails WHERE cireportid=:rptid AND ciactivity=:activity "
						   +"AND businessid=:emporbusid",params);
					BVRDesk bv = new BVRDesk();
					return bv.getBVRDesk(bvrlist, emporbusid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String saveOrUpdateDeskCIActivity(Tbdeskciactivity ciactivity) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(ciactivity.getId() != null){
				params.put("id", ciactivity.getId());
				Tbdeskciactivity actv = (Tbdeskciactivity) dbservice.executeUniqueHQLQuery(
						"FROM Tbdeskciactivity WHERE id=:id", params);
				actv.setInformantcontactno(ciactivity.getInformantcontactno());
				actv.setInformantname(ciactivity.getInformantname());
				actv.setInformantposition(ciactivity.getInformantposition());
				actv.setOverallfindings(ciactivity.getOverallfindings());
				actv.setOverallremarks(ciactivity.getOverallremarks());
				if(dbservice.saveOrUpdate(actv)){
					return "success";
				}
			}else{
				dbservice.save(ciactivity);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}


	@Override
	public Tbdeskciactivity getDeskCIActivity(String rptid, String activity, Integer activityID) {
		Map<String, Object> params = HQLUtil.getMap();
		Tbdeskciactivity act = null;
		try {
			params.put("rptid", rptid);
			params.put("actid", activityID);
			
			if (activity.equals("PDRN")) {
				act = (Tbdeskciactivity) dbservice.executeUniqueHQLQuery(""
						+ "FROM Tbdeskciactivity WHERE ciactivity='PDRN' AND cireportid=:rptid", params);
				if(act != null){
					return act;
				}
			}
			else if (activity.equals("EVR")) {
				act = (Tbdeskciactivity) dbservice.executeUniqueHQLQuery(""
						+ "FROM Tbdeskciactivity WHERE ciactivity='EVR' "
						+ "AND cireportid=:rptid AND employmentid=:actid", params);
				if(act != null){
					return act;
				}
			}
			else if (activity.equals("BVR") && activityID != null) {
				act = (Tbdeskciactivity) dbservice.executeUniqueHQLQuery(""
						+ "FROM Tbdeskciactivity WHERE ciactivity='BVR' "
						+ "AND cireportid=:rptid AND businessid=:actid", params);
				if(act != null){
					return act;
				}
			}
			else if (activity.equals("BVR") && activityID == null) {
				act = (Tbdeskciactivity) dbservice.executeUniqueHQLQuery(""
						+ "FROM Tbdeskciactivity WHERE ciactivity='BVR' "
						+ "AND cireportid=:rptid AND businessid IS NULL", params);
				if(act != null){
					return act;
				}
			}
			if (activity.equals("BANK")) {
				act = (Tbdeskciactivity) dbservice.executeUniqueHQLQuery(""
						+ "FROM Tbdeskciactivity WHERE ciactivity='BANK' "
						+ "AND cireportid=:rptid AND bankid=:actid", params);
			}
			if (activity.equals("CREDIT")) {
				act = (Tbdeskciactivity) dbservice.executeUniqueHQLQuery(""
						+ "FROM Tbdeskciactivity WHERE ciactivity='CREDIT' "
						+ "AND cireportid=:rptid AND creditid=:actid", params);
			}
			if (activity.equals("TRADE")) {
				act = (Tbdeskciactivity) dbservice.executeUniqueHQLQuery(""
						+ "FROM Tbdeskciactivity WHERE ciactivity='TRADE' "
						+ "AND cireportid=:rptid AND tradeid=:actid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return act;
	}

	@Override
	public List<EvrBvrActivities> listEvrBvrActivities(String rptid, String activitytype) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(rptid != null){
				params.put("rptid", rptid);
				params.put("activity", activitytype);
				@SuppressWarnings("unchecked")
				List<Tbdeskciactivity> list = (List<Tbdeskciactivity>)dbservice.executeListHQLQuery(
						"FROM Tbdeskciactivity WHERE cireportid=:rptid AND ciactivity=:activity", params);
				List<EvrBvrActivities> evractivtylist = new ArrayList<EvrBvrActivities>();
				if(list != null){
					for(Tbdeskciactivity a : list){
						if(activitytype.equals("EVR")){
							params.put("id", a.getEmploymentid());
							Tbmemberemployment emp = (Tbmemberemployment) dbservice
									.executeUniqueHQLQuery("FROM Tbmemberemployment WHERE id=:id", params);
							EvrBvrActivities e = new EvrBvrActivities();
							
							emp.setStreetnoname(emp.getStreetnoname() == null ? "" : emp.getStreetnoname());
							emp.setSubdivision(emp.getSubdivision() == null ? "" : emp.getSubdivision());
							emp.setBarangay(emp.getBarangay() == null ? "" : emp.getBarangay());
							emp.setCity(emp.getCity() == null ? "" : emp.getCity());
							emp.setRegion(emp.getRegion() == null ? "" : emp.getRegion());
							emp.setPostalcode(emp.getPostalcode() == null ? "" : emp.getPostalcode());
							emp.setCountry(emp.getCountry() == null ? "" : emp.getCountry());
							
							e.setFulladdres(emp.getStreetnoname() + " " + emp.getSubdivision() + " " + emp.getBarangay() + " " + emp.getCity()
							+ " " + emp.getRegion() + " " + emp.getPostalcode() + " " + emp.getCountry());
							e.setEmployerorbusinessname(emp.getCompanyname());
							e.setInformantname(a.getInformantname());
							e.setOverallremarks(a.getOverallremarks());
							e.setActiviy("EVR");
							e.setCireportid(a.getCireportid());
							e.setEmploymentid(emp.getId());
							evractivtylist.add(e);
						}else{
							params.put("id", a.getBusinessid());
							Tbmemberbusiness bus = (Tbmemberbusiness) dbservice
									.executeUniqueHQLQuery("FROM Tbmemberbusiness WHERE id=:id", params);
							EvrBvrActivities e = new EvrBvrActivities();
							
							bus.setStreetnoname(bus.getStreetnoname() == null ? "" : bus.getStreetnoname());
							bus.setSubdivison(bus.getSubdivison() == null ? "" : bus.getSubdivison());
							bus.setBarangay(bus.getBarangay() == null ? "" : bus.getBarangay());
							bus.setCity(bus.getCity() == null ? "" : bus.getCity());
							bus.setRegion(bus.getRegion() == null ? "" : bus.getRegion());
							bus.setPostalcode(bus.getPostalcode() == null ? "" : bus.getPostalcode());
							bus.setCountry(bus.getCountry() == null ? "" : bus.getCountry());
							
							e.setFulladdres(bus.getStreetnoname() + " " + bus.getSubdivison() + " " + bus.getBarangay() + " " + bus.getCity()
							+ bus.getRegion() + " " + bus.getPostalcode() + " " + bus.getCountry());
							e.setEmployerorbusinessname(bus.getBusinessname());
							e.setInformantname(a.getInformantname());
							e.setOverallremarks(a.getOverallremarks());
							e.setActiviy("BVR");
							e.setCireportid(a.getCireportid());
							e.setBusinessid(bus.getId());
							evractivtylist.add(e);
						}
					}
					return evractivtylist;
				}
			}
		} catch (Exception er) {
			er.printStackTrace();
		}
		return null;
	}

	@Override
	public String saveOrUpdateBankCheck(Tbcibankcheck bank, Tbdeskciactivity act) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (bank.getId() != null) {
				params.put("id", bank.getId());
				Tbcibankcheck b = (Tbcibankcheck) dbservice.executeUniqueHQLQuery("FROM Tbcibankcheck WHERE id=:id",
						params);
				if (b != null) {
					b.setBankaccttype(bank.getBankaccttype());
					b.setBank(bank.getBank());
					b.setBranch(bank.getBranch());
					b.setAccountname(bank.getAccountname());
					b.setAccountnumber(bank.getAccountnumber());
					b.setDateopened(bank.getDateopened());
					b.setAdb(bank.getAdb());
					b.setOutstandingbal(bank.getOutstandingbal());
					b.setAmc(bank.getAmc());
					b.setAccountstatus(bank.getAccountstatus());
					if (dbservice.saveOrUpdate(b)) {
						saveOrUpdateDeskCIActivity(act);
						return "success";
					}
				}
			} else {
				if (dbservice.save(bank)) {
					Tbdeskciactivity activity = new Tbdeskciactivity();
					activity.setBankid(bank.getId());
					activity.setCireportid(bank.getCireportid());
					activity.setCiactivity("BANK");
					activity.setCifno(bank.getCifno());
					activity.setInformantname(act.getInformantname());
					activity.setInformantcontactno(act.getInformantcontactno());
					activity.setInformantposition(act.getInformantposition());
					activity.setOverallfindings(act.getOverallfindings());
					activity.setOverallremarks(act.getOverallremarks());
					if (dbservice.save(activity)) {
						return "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String saveOrUpdateCreditCheck(Tbcicreditcheck credit, Tbdeskciactivity act) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (credit.getId() != null) {
				params.put("id", credit.getId());
				Tbcicreditcheck c = (Tbcicreditcheck) dbservice.executeUniqueHQLQuery("FROM Tbcicreditcheck WHERE id=:id",
						params);
				if (c != null) {
					c.setLoantype(credit.getLoantype());
					c.setBank(credit.getBank());
					c.setBranch(credit.getBranch());
					c.setPncnno(credit.getPncnno());
					c.setAccountname(credit.getAccountname());
					c.setValuedate(credit.getValuedate());
					c.setMaturitydate(credit.getMaturitydate());
					c.setOutstandingbal(credit.getOutstandingbal());
					c.setCurrency(credit.getCurrency());
					c.setPnloanamount(credit.getPnloanamount());
					c.setExperiencehandling(credit.getExperiencehandling());
					if(dbservice.saveOrUpdate(c)){
						saveOrUpdateDeskCIActivity(act);
						return "success";
					}
				}
			} else {
				if (dbservice.save(credit)) {
					Tbdeskciactivity activity = new Tbdeskciactivity();
					activity.setCreditid(credit.getId());
					activity.setCireportid(credit.getCireportid());
					activity.setCiactivity("CREDIT");
					activity.setCifno(credit.getCifno());
					activity.setInformantname(act.getInformantname());
					activity.setInformantcontactno(act.getInformantcontactno());
					activity.setInformantposition(act.getInformantposition());
					activity.setOverallfindings(act.getOverallfindings());
					activity.setOverallremarks(act.getOverallremarks());
					if(dbservice.save(activity)){
						return "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String saveOrUpdateTradeCheck(Tbcitradecheck trade, Tbdeskciactivity act) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (trade.getId() != null) {
				params.put("id", trade.getId());
				Tbcitradecheck t = (Tbcitradecheck) dbservice.executeUniqueHQLQuery("FROM Tbcitradecheck WHERE id=:id",
						params);
				if (t != null) {
					t.setTradetype(trade.getTradetype());
					t.setCompanyname(trade.getCompanyname());
					t.setCompanyaddress(trade.getCompanyaddress());
					t.setCountrycodephone(trade.getCountrycodephone());
					t.setAreacodephone(trade.getAreacodephone());
					t.setCompanyphoneno(trade.getCompanyphoneno());
					t.setNaturebusiness(trade.getNaturebusiness());
					t.setAveragetransaction(trade.getAveragetransaction());
					t.setCreditlimit(trade.getCreditlimit());
					t.setLengthofdealing(trade.getLengthofdealing());
					t.setProductservicesoffered(trade.getProductservicesoffered());
					if(dbservice.saveOrUpdate(t)){
						saveOrUpdateDeskCIActivity(act);
						return "success";
					}
				}
			} else {
				if (dbservice.save(trade)) {
					Tbdeskciactivity activity = new Tbdeskciactivity();
					activity.setTradeid(trade.getId());
					activity.setCireportid(trade.getCireportid());
					activity.setCiactivity("TRADE");
					activity.setCifno(trade.getCifno());
					activity.setInformantname(act.getInformantname());
					activity.setInformantcontactno(act.getInformantcontactno());
					activity.setInformantposition(act.getInformantposition());
					activity.setOverallfindings(act.getOverallfindings());
					activity.setOverallremarks(act.getOverallremarks());
					if(dbservice.save(activity)){
						return "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcibankcheck> listBankCheck(String rptid) {
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbcibankcheck> list = null;
		try {
			Tbcireportmain rpt = getCIReport(rptid);
			params.put("rptid", rptid);
			params.put("cifno", rpt.getCifno());
			params.put("appno", rpt.getAppno());
			
			if((Integer)dbservice.execStoredProc("SELECT COUNT(*) FROM Tbcibankcheck WHERE cireportid=:rptid", params, null, 0, null)==0) {
				System.out.println(dbservice.execStoredProc("insert into TBCIBANKCHECK select :rptid,membershipid,:appno,accounttype,bankname,branch,null,null,null,null,null,outstandingbalance,accountstatus " + 
						"from TBMEMBERFINANCIALINFO where membershipid =:cifno and financialtype = '1'", params, null, 2, null) + " : MEMBERFINANCIALINFO");
				System.out.println(dbservice.execStoredProc("insert into TBCIBANKCHECK select :rptid,:cifno,:appno,bankaccttype,bank,null,null,null,null,null,null,outstandingbal,accountstatus " + 
						"from TBLSTBANKACCOUNTS where appno =:appno", params, null, 2, null) + "  : LSTBANKACCOUNTS");
			}
			list = (ArrayList<Tbcibankcheck>)dbservice
					.executeListHQLQuery("FROM Tbcibankcheck WHERE cireportid=:rptid", params);
			if(list != null){
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcicreditcheck> listCreditCheck(String rptid) {
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbcicreditcheck> list = null;
		try {
			System.out.println("CREDIT CHECK !");
			Tbcireportmain rpt = getCIReport(rptid);
			params.put("rptid", rptid);
			params.put("cifno", rpt.getCifno());
			params.put("appno", rpt.getAppno());
			if((Integer)dbservice.execStoredProc("SELECT COUNT(*) FROM Tbcicreditcheck WHERE cireportid=:rptid", params, null, 0, null)==0) {
				System.out.println(dbservice.execStoredProc("insert into TBCICREDITCHECK select :rptid,memberid,:appno,cardtype,bank,null,null,null,null,dateexpiry,outstandingbalance,null,creditlimit,null " + 
						"from TBMEMBERCREDITCARDINFO where memberid =:cifno", params, null, 2, null) + " : TBMEMBERCREDIT");
				System.out.println(dbservice.execStoredProc("insert into TBCICREDITCHECK select :rptid,:cifno,:appno,cardtype,bank,null,null,null,null,dateexpiry,limitbalance,null,creditlimit,null " + 
						"from TBLSTCREDITCARDINFO where appno =:appno", params, null, 2, null) + " : TBLSTCREDIT");
			}
//			params.put("credited", true);
			List<Tblstexistingloansother> other = (List<Tblstexistingloansother>)dbservice.executeListSQLQueryWithClass
					("SELECT * FROM Tblstexistingloansother WHERE appno=:appno AND hascreditcheck != 1 OR hascreditcheck IS NULL", params, Tblstexistingloansother.class);
//			List<Tblstexistingloansother> others = (List<Tblstexistingloansother>)dbservice.executeListHQLQuery("FROM Tblstexistingloansother WHERE hascreditcheck != 1 AND appno=:appno", params);
			System.out.println(other.size());
			if(other.size() > 0){
				for(Tblstexistingloansother o : other){
					Tbcicreditcheck c = new Tbcicreditcheck();
					c.setAppno(o.getAppno());
					c.setBank(o.getBank());
					c.setLoantype(o.getLoantype());
					c.setMaturitydate(o.getMaturitydate());
					c.setOutstandingbal(o.getOutstandingbal());
					c.setCireportid(rpt.getCireportid());
					if(dbservice.save(c)){
						o.setHascreditcheck(true);
						dbservice.saveOrUpdate(o);
					}
				}
			}
			list = (ArrayList<Tbcicreditcheck>)dbservice
					.executeListHQLQuery("FROM Tbcicreditcheck WHERE cireportid=:rptid", params);
			if(list != null){
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcitradecheck> listTradeCheck(String rptid) {
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbcitradecheck> list = null;
		try {
			params.put("rptid", rptid);
			list = (ArrayList<Tbcitradecheck>)dbservice
					.executeListHQLQuery("FROM Tbcitradecheck WHERE cireportid=:rptid", params);
			if(list != null){
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveOrUpdatePDRNField(Tbcipdrn pdrn, Tbcipdrnresidence res, Tbcipdrnverhighlights h, String rptid) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(rptid != null){
				params.put("rptid", rptid);
				boolean b = false;
				//PDRN
				if (pdrn.getDateofbirth() != null) {
					pdrn.setAge(DateTimeUtil.getAge(pdrn.getDateofbirth()));
				}
				if (pdrn.getSpousedateofbirth() != null) {
					pdrn.setSpouseage(DateTimeUtil.getAge(pdrn.getSpousedateofbirth()));
				}
				if (dbservice.saveOrUpdate(pdrn)) {
					b = true;
				}else{
					return "Problem updating <b>Personal Verification";
				}
				// RESIDENCE
				if (dbservice.saveOrUpdate(res)) {
					b = true;
				}else{
					return "Problem updating <b>Residence Verification";
				}
				//VERIFICATION
				if (dbservice.saveOrUpdate(h)) {
					b = true;
				}else{
					return "Problem updating <b>Verification Highlights";
				}			
				if(b){
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public PDRNField getPDRNField(String rptid) {
		PDRNField field = new PDRNField();
		FullDataEntryService fde = new FullDataEntryServiceImpl();
		MemberService member = new MemberServiceImpl();
		Tbmember mem = new Tbmember();
		try {
			Map<String, Object> params = HQLUtil.getMap();
			if (rptid != null) {
				params.put("rptid", rptid);
				field.setPdrn(
						(Tbcipdrn) dbservice.executeUniqueHQLQuery("FROM Tbcipdrn WHERE cireportid =:rptid", params));
				Tblstapp app = fde.getLstapp(field.getPdrn().getAppno());
				if (app.getCifno() != null) {
					mem = member.getMember(app.getCifno()).getMemberpersonalinformation();
				}
				field.setResidence((Tbcipdrnresidence) dbservice
						.executeUniqueHQLQuery("FROM Tbcipdrnresidence WHERE cireportid =:rptid", params));
				field.setHighlights((Tbcipdrnverhighlights) dbservice
						.executeUniqueHQLQuery("FROM Tbcipdrnverhighlights WHERE cireportid =:rptid", params));
				if (field.getPdrn().getLastname() == null) {
					field.getPdrn().setLastname(mem.getLastname());
				}
				if (field.getPdrn().getFirstname() == null) {
					field.getPdrn().setFirstname(mem.getFirstname());
				}
				if (field.getPdrn().getMiddlename() == null) {
					field.getPdrn().setMiddlename(mem.getMiddlename());
				}
				if (field.getPdrn().getDateofbirth() == null) {
					field.getPdrn().setDateofbirth(mem.getDateofbirth());
				}
				if (field.getPdrn().getTitle() == null) {
					field.getPdrn().setTitle(mem.getTitle());
				}
				if (field.getPdrn().getCivilstatus() == null) {
					field.getPdrn().setCivilstatus(mem.getCivilstatus());
				}
				if(field.getPdrn().getSuffix() == null){
					field.getPdrn().setSuffix(mem.getSuffix());
				}
				return field;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcidependents> listDependents(String rptid) {
		List<Tbcidependents> list = null;
		try {
			Map<String, Object> params = HQLUtil.getMap();
			if(rptid != null){
				params.put("rptid", rptid);
				list = (ArrayList<Tbcidependents>)dbservice.executeListHQLQuery(""
						+ "FROM Tbcidependents WHERE cireportid=:rptid", params);
				if(list != null){
					return list;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveOrUpdateDependents(Tbcidependents dep) {
		try {
			if (dep.getDependentid() != null) {
				if (dbservice.saveOrUpdate(dep)) {
					return "success";
				}
			} else {
				if (dbservice.save(dep)) {
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String saveOrUpdateEVRField(Tbcievr evr) {
		try {
			if (dbservice.saveOrUpdate(evr)) {
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String saveOrUpdateBVRField(Tbcibvr bvr) {
		try {
			if (dbservice.saveOrUpdate(bvr)) {
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcievr> listEVRField(String rptid) {
		List<Tbcievr> list = null;
		try {
			Map<String, Object> params = HQLUtil.getMap();
			if(rptid != null){
				params.put("rptid", rptid);
				list = (ArrayList<Tbcievr>)dbservice.executeListHQLQuery(""
						+ "FROM Tbcievr WHERE cireportid=:rptid", params);
				if(list != null){
					return list;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcibvr> listBVRField(String rptid) {
		List<Tbcibvr> list = null;
		try {
			Map<String, Object> params = HQLUtil.getMap();
			if(rptid != null){
				params.put("rptid", rptid);
				list = (ArrayList<Tbcibvr>)dbservice.executeListHQLQuery(""
						+ "FROM Tbcibvr WHERE cireportid=:rptid", params);
				if(list != null){
					return list;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteItem(Integer dependentsID, Integer employmentID, Integer businessID, Integer traderefID) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(dependentsID != null){
				params.put("id", dependentsID);
				Tbcidependents dep = (Tbcidependents) dbservice.executeUniqueHQLQuery("FROM Tbcidependents WHERE dependentid=:id", params);
				if(dep != null){
					if(dbservice.delete(dep)){
						return "success";
					}				
				}	
			}
			if(employmentID != null){
				params.put("id", employmentID);
				Tbcievr evr = (Tbcievr) dbservice.executeUniqueHQLQuery("FROM Tbcievr WHERE id=:id", params);
				if(evr != null){
					if(dbservice.delete(evr)){
						return "success";
					}				
				}	
			}
			if(businessID != null){
				params.put("id", businessID);
				Tbcibvr bvr = (Tbcibvr) dbservice.executeUniqueHQLQuery("FROM Tbcibvr WHERE id=:id", params);
				if(bvr != null){
					if(dbservice.delete(bvr)){
						return "success";
					}				
				}
			}
			if(traderefID != null){
				params.put("id", traderefID);
				Tbcitraderefcorp tr = (Tbcitraderefcorp) dbservice.executeUniqueHQLQuery("FROM Tbcitraderefcorp WHERE traderefid=:id", params);
				if(tr != null){
					if(dbservice.delete(tr)){
						return "success";
					}				
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public Tbcievr getEVRField(Integer eid) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("id", eid);
			Tbcievr evr = (Tbcievr) dbservice.executeUniqueHQLQuery("FROM Tbcievr WHERE id=:id", params);
			if(evr != null){
				return evr;			
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tbcibvr getBVRField(Integer bid, String rptid) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(bid != null){
				params.put("id", bid);
				Tbcibvr bvr = (Tbcibvr) dbservice.executeUniqueHQLQuery("FROM Tbcibvr WHERE id=:id", params);
				if(bvr != null){
					return bvr;			
				}				
			}else{
				//this return is for corporate
				params.put("rptid", rptid);
				Tbcibvr bvr = (Tbcibvr) dbservice.executeUniqueHQLQuery("FROM Tbcibvr WHERE cireportid=:rptid", params);
				if(bvr != null){
					return bvr;			
				}					
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcitraderefcorp> listTradeReference(String rptid) {
		List<Tbcitraderefcorp> list = null;
		try {
			Map<String, Object> params = HQLUtil.getMap();
			if(rptid != null){
				params.put("rptid", rptid);
				list = (ArrayList<Tbcitraderefcorp>)dbservice.executeListHQLQuery(""
						+ "FROM Tbcitraderefcorp WHERE cireportid=:rptid", params);
				if(list != null){
					return list;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String saveOrUpdateTradeReference(Tbcitraderefcorp t) {
		try {
			if (dbservice.saveOrUpdate(t)) {
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcireportmain> getCiReportListByAppno(String appno) {
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbcireportmain> report = new ArrayList<Tbcireportmain>();
		StringBuilder hql = new StringBuilder();
		params.put("appno", appno);
		try {
			hql.append("SELECT a.cireportid, a.cirequestid, a.appno, a.cifno, a.customername, a.customertype, (SELECT fullname FROM TBUSER WHERE username=a.reportedby) as reportedby, ");
			hql.append("(SELECT fullname FROM TBUSER WHERE username=a.requestedby) as requestedby, (SELECT fullname FROM TBUSER WHERE username=a.reviewedby) as reviewedby, ");
			hql.append("(SELECT desc1 FROM TBCODETABLE WHERE codename='REPORTSTATUS' AND codevalue=a.status) as status, (SELECT coopname FROM TBCOOPERATIVE WHERE coopcode = a.companycode) as companycode ");
			hql.append("FROM Tbcireportmain a WHERE a.appno=:appno");
			
			report = (List<Tbcireportmain>) dbservice.execSQLQueryTransformer(hql.toString(), params, Tbcireportmain.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return report;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcireportmain> getCiReportListByRequestId(String rqstid) {
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbcireportmain> report = new ArrayList<Tbcireportmain>();
		StringBuilder hql = new StringBuilder();
		params.put("cirequestid", rqstid);
		try {
			hql.append("SELECT a.cireportid, a.cirequestid, a.appno, a.cifno, a.customername, a.customertype, (SELECT fullname FROM TBUSER WHERE username=a.reportedby) as reportedby, ");
			hql.append("(SELECT fullname FROM TBUSER WHERE username=a.requestedby) as requestedby, (SELECT fullname FROM TBUSER WHERE username=a.reviewedby) as reviewedby, ");
			hql.append("(SELECT desc1 FROM TBCODETABLE WHERE codename='REPORTSTATUS' AND codevalue=a.status) as status, a.companycode ");
			hql.append("FROM Tbcireportmain a WHERE a.cirequestid=:cirequestid");
			
			report = (List<Tbcireportmain>) dbservice.execSQLQueryTransformer(hql.toString(), params, Tbcireportmain.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return report;
	}

	@Override
	public String deleteCIDeskItem(Integer bankchkID, Integer creditchkID, Integer tradechkID) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			int res = 0;
			if(bankchkID != null){
				params.put("id", bankchkID);
				res =  dbservice.executeUpdate("DELETE FROM Tbcibankcheck WHERE id=:id", params);
				if(res > 0){
					return "success";
				}	
			}
			if(creditchkID != null){
				params.put("id", creditchkID);
				res =  dbservice.executeUpdate("DELETE FROM Tbcicreditcheck WHERE id=:id", params);
				if(res > 0){
					return "success";
				}		
			}
			if(tradechkID != null){
				params.put("id", tradechkID);
				res =  dbservice.executeUpdate("DELETE FROM Tbcitradecheck WHERE id=:id", params);
				if(res > 0){
					return "success";
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdeskcidetails> getDeskCIDetailsPDRN(String rptid, Integer emporbusid) {
		Map<String, Object> params = HQLUtil.getMap();
		PDRNDesk p = new PDRNDesk();
		try {
			params.put("rptid", rptid);
			params.put("activity", "PDRN");
			params.put("emporbusid", emporbusid);
			
			List<Tbdeskcidetails> pdrnlist = (List<Tbdeskcidetails>) dbservice.executeListHQLQuery(
					"FROM Tbdeskcidetails WHERE cireportid=:rptid AND ciactivity=:activity", params);
			if(pdrnlist != null){
				return p.getPDRNDesk(pdrnlist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdeskcidetails> getDeskCIDetailsEVR(String rptid, Integer emporbusid) {
		Map<String, Object> params = HQLUtil.getMap();
		EVRDesk ev = new EVRDesk();
		try {
			params.put("rptid", rptid);
			params.put("activity", "EVR");
			params.put("emporbusid", emporbusid);
			List<Tbdeskcidetails> evrlist = (List<Tbdeskcidetails>) dbservice.executeListHQLQuery(
					"FROM Tbdeskcidetails WHERE cireportid=:rptid AND ciactivity=:activity "
				   +"AND employmentid=:emporbusid",params);
			if(evrlist != null){
				return ev.getEVRDesk(evrlist);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdeskcidetails> getDeskCIDetailsBVR(String rptid, Integer emporbusid) {
		Map<String, Object> params = HQLUtil.getMap();
		BVRDesk bv = new BVRDesk();
		try {
			params.put("rptid", rptid);
			params.put("activity", "BVR");
			params.put("emporbusid", emporbusid);
			if(emporbusid == null){
				List<Tbdeskcidetails> bvrlist = (List<Tbdeskcidetails>) dbservice.executeListHQLQuery(
						"FROM Tbdeskcidetails WHERE cireportid=:rptid AND ciactivity=:activity "
					   +"AND businessid IS NULL", params);
				if(bvrlist != null){
					return bv.getBVRDesk(bvrlist, null);
				}
				
			}else{
				List<Tbdeskcidetails> bvrlist = (List<Tbdeskcidetails>) dbservice.executeListHQLQuery(
						"FROM Tbdeskcidetails WHERE cireportid=:rptid AND ciactivity=:activity "
					   +"AND businessid=:emporbusid",params);
				if(bvrlist != null){
					return bv.getBVRDesk(bvrlist, emporbusid);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//MAR
	@Override
	public String setEvalIDForCIEvalFromEval(String appno) {
		System.out.println("--------- Running setEvalIDForCIEvalFromEval ! ...");
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(appno!=null){
				params.put("appno", appno);
				String username = secservice.getUserName();
				Integer maxId = (Integer) dbserviceLOS.executeUniqueSQLQuery("SELECT MAX(evalreportid) FROM Tbevalreport WHERE appno=:appno", params);
				Integer res = dbserviceLOS.executeUpdate
						("UPDATE Tbevalci SET evalreportid ='"+maxId+"', datecreated = GETDATE(), createdby='"+username+"' WHERE appno ='"+appno+"' AND evalreportid = '0'", null);
				if(res!=null && res > 0){
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
