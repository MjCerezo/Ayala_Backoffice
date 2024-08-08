package com.etel.documentation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbdocdetails;
//import com.loansdb.data.Tbcfinsurancedetails;
import com.coopdb.data.Tbdocsperapplication;
//import com.coopdb.data.Tbdocsperbusinesstype;
import com.coopdb.data.Tbdocsperproduct;
import com.coopdb.data.Tblstapp;
//import com.cifsdb.data.Tbdocdetails;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.documentation.forms.DocAccessRightsForm;
import com.etel.lms.forms.LoanAccountForm;
import com.etel.lmseod.LMSEODService;
import com.etel.lmseod.LMSEODServiceImpl;
import com.etel.utils.ApplicationNoGenerator;
import com.etel.utils.HQLUtil;
import com.etel.utils.LoggerUtil;
import com.etel.utils.UserUtil;

public class DocumentationServiceImpl implements DocumentationService {

	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	
	/**
	 * -- Save or Update CF Insurance Details--
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 * */
//	@Override
//	public String saveOrUpdateCFInsurance(Tbcfinsurancedetails insurance) {
//		String flag = "failed";
//		try {
//			if(dbService.saveOrUpdate(insurance)){
//				flag = "success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}

	/**
	 * -- Delete CF Insurance Details--
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 * */
//	@Override
//	public String deleteCFInsurance(String appno, Integer id) {
//		String flag = "failed";
//		try {
//			if(appno != null && id != null){
//				params.put("appno", appno);
//				params.put("id", id);
//				int res = dbService.executeUpdate("DELETE FROM Tbcfinsurancedetails WHERE appno=:appno AND id=:id", params);
//				if(res > 0){
//					flag = "success";
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}
	
	/**
	 * --Get List of CF Insurance--
	 * @author Kevin (08.25.2018)
	 * @return form  = {@link Tbcfinsurancedetails}
	 * */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Tbcfinsurancedetails> getListOfCFInsurance(String appno) {
//		List<Tbcfinsurancedetails> list = new ArrayList<Tbcfinsurancedetails>();
//		try {
//			if(appno != null){
//				params.put("appno", appno);
//				list  = (List<Tbcfinsurancedetails>) dbService.executeListHQLQuery("FROM Tbcfinsurancedetails WHERE appno=:appno", params);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}

	/**
	 * --Refresh Document Checklist Per Application--
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 * */
//	@SuppressWarnings("unchecked")
//	@Override
//	public String refreshDocChecklistPerApp(String appno, String cifno) {
//		String flag = "failed";
//		String username = UserUtil.securityService.getUserName();
//		try {
//			if(appno != null && cifno != null){
//				params.put("cifno", cifno);
//				String businesstype = (String) dbService.execSQLQueryTransformer("SELECT businesstype FROM Tbcifcorporate WHERE cifno=:cifno", params, null, 0);
//				if(businesstype != null){
//					params.put("businesstype", businesstype);
//					List<Tbdocsperbusinesstype> list  = (List<Tbdocsperbusinesstype>) dbService.executeListHQLQuery("FROM Tbdocsperbusinesstype WHERE businesstype=:businesstype", params);
//					if(list != null && !list.isEmpty()){
//						for(Tbdocsperbusinesstype d : list){
//							params.put("id", d.getId());
//							Tbdocsperapplication docSubmitted = (Tbdocsperapplication) dbService
//									.executeUniqueHQLQueryMaxResultOne(
//											"FROM Tbdocsperapplication WHERE appno=:appno AND docchecklistid=:id",
//											params);
//							if (docSubmitted != null) {
//								System.out.println("1");
//								// Update existing record
//								docSubmitted.setDocumentcode(d.getDocumentcode());
//								docSubmitted.setDocumentname(d.getDocumentname());
//								docSubmitted.setUploadedby(null);
//								docSubmitted.setDateuploaded(new Date());
//								dbService.saveOrUpdate(docSubmitted);
//							}else{
//								System.out.println("2");
//								// Create new record
//								Tbdocsperapplication docS = new Tbdocsperapplication();
//								docS.setAppno(appno);
//								docS.setCifno(cifno);
//								docS.setDocchecklistid(d.getId());
//								docS.setDocumentcode(d.getDocumentcode());
//								docS.setDocumentname(d.getDocumentname());
//								docS.setUploadedby(username);
//								dbService.save(docS);
//							}
//						}
//					}
//					//Delete from Tbdocsperapplication if not in default docchecklist
//					dbService.executeUpdate("DELETE FROM Tbdocsperapplication WHERE docchecklistid NOT IN(SELECT id FROM Tbdocsperbusinesstype WHERE businesstype='"+businesstype+"')", params);
//				}else{
//					System.out.println("3");
//				}
//				flag = "success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}

	/**
	 * --Get List of Document Checklist per Application--
	 * @author Kevin (08.25.2018)
	 * @return form  = {@link Tbdocsperapplication}
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocsperapplication> getListOfDocsPerApp(String appno) {
		List<Tbdocsperapplication> list = new ArrayList<Tbdocsperapplication>();
		try {
			if(appno != null){
				System.out.println("appno: " +appno);
				params.put("appno", appno);
				list  = (List<Tbdocsperapplication>) dbService.executeListHQLQuery("FROM Tbdocsperapplication WHERE appno=:appno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * -- Save or Update Doc Checklist per Application--
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public String saveOrUpdateDocsPerApp(Tbdocsperapplication docsperapp) {
		String flag = "failed";
		try {
			if (dbService.saveOrUpdate(docsperapp)) {
				if(docsperapp.getCifno() != null){
					params.put("cifno", docsperapp.getCifno());
					List<Tbdocdetails> docs = (List<Tbdocdetails>) dbService.executeListHQLQuery("FROM Tbdocdetails WHERE cifno=:cifno", params);
					if(docs != null){
						for (Tbdocdetails d : docs) {
							if (d.getDoctype().equals(docsperapp.getDocumentcode())
									&& d.getDoccategory().equals(docsperapp.getDoccategory())) {
								if (docsperapp.getDocappstatus().equals("1")) // "On-Hand"-in-LOS
									d.setDocstatus("0");// "Complete"-in-CIF
								if (docsperapp.getDocappstatus().equals("2")) // "To-Follow"-in-LOS
									d.setDocstatus("1");// "With-Deficiency"-in-CIF
								d.setRemarks(docsperapp.getRemarks());
								d.setDmsid(docsperapp.getDmsid());
								if (dbService.saveOrUpdate(d)) {
									String q = "UPDATE Tbdocdetails SET docstatus='" + docsperapp.getDocappstatus() + "', remarks='"
											+ d.getRemarks() + "', dmsid='" + d.getDmsid() + "' WHERE cifno='"
											+ docsperapp.getCifno() + "' AND loanappno='" + docsperapp.getAppno()
											+ "' AND doctype='" + docsperapp.getDocumentcode() + "' AND doccategory='"
											+ docsperapp.getDoccategory() + "'";
									Integer res = dbService.executeUpdate(q, null);
									if (res != null && res > 0) {
										System.out.println(">>>>>DOCUMENT UPDATED TO LOS' TBDOCDETAILS<<<<<");
									}
								}
							}
						}
					}
				}
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * Generate documents for every loan application based on loan product
	 * See QDEServiceImpl Line 109 for method execution.
	 * @author DANIEL (09.01.2018)
	 * */
	public static void createDocumentsPerApplication(String appno) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("appno", appno);
		try {
			Tblstapp a = (Tblstapp)dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
			if(a!=null){
				params.put("loanproduct", a.getLoanproduct());
				@SuppressWarnings("unchecked")
				List<Tbdocsperproduct> p = (List<Tbdocsperproduct>)dbService.executeListHQLQuery("FROM Tbdocsperproduct WHERE productcode=:loanproduct", params);
				if (p != null && p.size() != 0) {
					for(Tbdocsperproduct e : p){
						Tbdocsperapplication docs = new Tbdocsperapplication();
						docs.setDoccategory(e.getDoccategory());
						docs.setCifno(a.getCifno());
						docs.setAppno(a.getAppno());
						docs.setDocumentcode(e.getDocumentcode());
						docs.setDocumentname(e.getDocumentname());
						docs.setApplicationstatus(e.getApplicationstatus());
						if(dbService.save(docs)){
							System.out.println(">>>"+e.getDocumentname()+" saved as documents for "+a.getAppno()+" with loan product"+a.getLoanproduct()+"<<<<<");
							String q = "INSERT INTO Tbdocdetails (loanappno, cifno, doctype, doccategory) VALUES ('"+a.getAppno()+"', '"+a.getCifno()+"', '"+e.getDocumentcode()+"', '"+e.getDoccategory()+"')";	
							Integer res = dbService.executeUpdate(q, null);
							if (res != null && res > 0) {
								System.out.println("DOCUMENT ALSO SAVED TO TBDOCDETAILS!");
							}
						}
					}
				} else if (p == null || p.size() == 0) {
					System.out.println(">>>>>PLEASE CHECK TBDOCSPERPRODUCT OR THE APPLICATION'S LOANPRODUCT<<<<<");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get details of existing documents from CIF
	 * @author DANIEL (09.01.2018)
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public String getExistingDocumentsDetailsfromCIF(String cifno, String appno) {
		params.put("cifno", cifno);
		params.put("appno", appno);
		try {
			List<Tbdocdetails> docs = (List<Tbdocdetails>)dbService.executeListHQLQuery("FROM Tbdocdetails WHERE cifno=:cifno", params);
			List<Tbdocsperapplication> app = (List<Tbdocsperapplication>)dbService.executeListHQLQuery("FROM Tbdocsperapplication WHERE appno=:appno", params);
			if (docs != null && docs.size() != 0) {
				for (Tbdocdetails d : docs) {
					for (Tbdocsperapplication a : app) {
						if (a.getDoccategory().equals(d.getDoccategory())
								&& a.getDocumentcode().equals(d.getDoctype())) {
							if (d.getDocstatus().equals("0")) {// "Complete"-in-CIF
								a.setDocstatus("1");// "On-Hand"-in-LOS
							}
							if (d.getDocstatus().equals("1")) {// "With-Deficiency"-in-CIF
								a.setDocstatus("2");// "To-Follow"-in-LOS
							}
							a.setRemarks(d.getRemarks());
							a.setDmsid(d.getDmsid());
							if (dbService.saveOrUpdate(a)) {
								String q = "UPDATE Tbdocdetails SET docstatus='" + a.getDocstatus() + "', remarks='"
										+ a.getRemarks() + "', dmsid='" + a.getDmsid() + "' WHERE cifno='"
										+ a.getCifno() + "' AND loanappno='" + a.getAppno() + "' AND doctype='"
										+ a.getDocumentcode() + "' AND doccategory='" + a.getDoccategory() + "'";
								Integer res = dbService.executeUpdate(q, null);
								if (res != null && res > 0) {
									System.out.println(">>>>>DOCUMENT UPDATED TO LOS' TBDOCDETAILS<<<<<");
								}
							}
						}
					}
				}
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * --Get Doc Access Rights--
	 * @author Kevin (09.12.2018)
	 * @return form = {@link DocAccessRightsForm}
	 * */
	@Override
	public DocAccessRightsForm getDocAccessRights(String appno) {
		DocAccessRightsForm form = new DocAccessRightsForm();
		String username = UserUtil.securityService.getUserName();
		params.put("appno", appno);
		boolean isDocHead = false;
		try {
			Tblstapp lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
			if(lstapp != null){
				//Doc Head Default User
				if(lstapp.getAssigneddochead() != null && lstapp.getAssigneddochead().equalsIgnoreCase(username)){
					isDocHead = true;
				}
				if(lstapp.getDocumentationstatus() != null){
					//New or Returned
					if(lstapp.getDocumentationstatus()== 0 || lstapp.getDocumentationstatus()== 3){
						//Doc Analyst
						if(lstapp.getAssigneddocanalyst() != null && lstapp.getAssigneddocanalyst().equalsIgnoreCase(username)){
							form.setBtnReturn(true);
							form.setBtnSaveOrDelete(true);
							form.setBtnSubmitToDocHead(true);
							form.setReadOnly(false);
						}
						//Doc Head
						if(isDocHead){
							form.setBtnSubmitToDocAnalyst(true);
						}
					}
					
					//For Review
					if(lstapp.getDocumentationstatus() == 1){
						//Doc Head
						if(isDocHead){
							form.setBtnReturn(true);
							form.setBtnReturnToDocAnalyst(true);
							form.setBtnSubmitApplication(true);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/**
	 * --Update Assigned Doc Analyst--
	 * @author Kevin (09.12.2018)
	 * @return String = success, otherwise failed
	 * */
	@Override
	public String updateAssignedDocAnalyst(String appno, String assigneddocanalyst) {
		String flag = "failed";
		try {
			if(appno != null && assigneddocanalyst != null){
				params.put("appno", appno);
				Tblstapp lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
				if(lstapp != null){
					boolean chk = false;
					if(lstapp.getAssigneddocanalyst() == null){
						chk = true;
						lstapp.setAssigneddocanalyst(assigneddocanalyst);
						lstapp.setDocassigndate(new Date());
					}else{
						//if re-assigned
						chk = true;
						if(!assigneddocanalyst.equalsIgnoreCase(lstapp.getAssigneddocanalyst())){
							lstapp.setAssigneddocanalyst(assigneddocanalyst);
							lstapp.setDocassigndate(new Date());
						}
					}
					if(chk){
						if(dbService.saveOrUpdate(lstapp)){
							flag = "success";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * --Update Documentation Status--
	 * @author Kevin (09.12.2018)
	 * @return String = success, otherwise failed
	 * */
	@Override
	public String updateDocumentationStatus(String appno, Integer status) {
		String flag = "failed";
		try {
			if(appno != null){
				params.put("appno", appno);
				Tblstapp lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
				if(lstapp != null){
					if (status == null) {
						System.out.println(">>>>>>>>Documentation Status is null !");
						return flag;
					} else {
						lstapp.setDocumentationstatus(status);
						lstapp.setDocumentationstatusdate(new Date());
						if (dbService.saveOrUpdate(lstapp)) {
							flag = "success";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocsperapplication> getDocsperApp(String appno) {
		// TODO Auto-generated method stub
		List<Tbdocsperapplication> list = new ArrayList<Tbdocsperapplication>();
		
		try {
			if(appno!=null){
				params.put("appno", appno);
				list = (List<Tbdocsperapplication>) dbService
						.executeListHQLQuery("FROM Tbdocsperapplication WHERE appno=:appno", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	/**
	 * --Generate PN Number--
	 * @author Kevin (01.05.2019)
	 * @return String = success, otherwise failed
	 * */
	@Override
	public String generateAndSavePNNo(String appno) {
		String flag = "failed";
		try {
			if(appno != null){
				params.put("appno", appno);
				Tblstapp lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
				if(lstapp != null){
					String pnno = ApplicationNoGenerator.generatePNNo(lstapp.getCompanycode(), lstapp.getLoanproduct());
					if(pnno != null){
						lstapp.setPnno(pnno);
						lstapp.setLastupdatedby(UserUtil.securityService.getUserName());
						lstapp.setDatelastupdated(new Date());
						if(dbService.saveOrUpdate(lstapp)){
							flag = "success";
							dbService.executeUpdate("UPDATE Tbaccountinfo SET pnno='"+pnno+"',loanno='"+pnno+"' WHERE applno=:appno", params);
							dbService.executeUpdate("UPDATE Tbloanfeesperapp SET acctno='"+pnno+"' WHERE appno=:appno", params);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Update Account Info Status--
	 * @author Kevin (01.05.2019)
	 * @return String = success, otherwise failed
	 * */
	@Override
	public String updateAccountInfoStatus(String appno, String status) {
		String flag = "failed";
		try {
//			System.out.println("FEED"+appno+status);// Commented by Ced 6-21-2021
			if(appno != null && status != null){
				params.put("appno", appno);
				Tbaccountinfo info = (Tbaccountinfo) dbService.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno=:appno", params);
				if(info != null){
					info.setTxstat(status);
					info.setStsdate(new Date());
					if(dbService.saveOrUpdate(info)){
						System.out.println("success");
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String bookToLMS(String appno, String txstat) {
		// TODO Auto-generated method stub
		String flag = "failed";
		List<LoanAccountForm> listOfLoans = new ArrayList<LoanAccountForm>();
		try {
			
			String strQuery = "SELECT applno, pnno, dtbook as txdate, clientid, name as fullname"
					+ ", (SELECT productname FROM Tbloanproduct WHERE productcode = product) as product, pnamt as loanamount"
					+ ", fduedt as fduedate, matdt as matdate, nominal as interestrate, intcycdesc as intperiod, ppynum as ppynum, term, termcycdesc as termperiod, amortfee as amortization, loanno as accountno"
					+ "  FROM Tbaccountinfo WHERE pnno IS NOT NULL";
			
			if(appno != null){
				params.put("appno", appno);
				strQuery += " AND applno =:appno";
				
				
				//Update company code (Kev 12.16.2019)
				dbService.executeUpdate("UPDATE b SET b.legveh=IIF(b.legveh=NULL,a.companycode,b.legveh) FROM TBLSTAPP a INNER JOIN TBACCOUNTINFO b ON a.appno=b.applno AND a.appno=:appno", params);
				
				
				//UPDATE pnno - 04.02.2021
				dbService.executeUpdate("UPDATE b SET b.accountno=a.pnno, b.loanno=a.pnno FROM TBACCOUNTINFO a INNER JOIN TBPAYSCHED b ON a.applno=b.applno AND a.applno=:appno", params);
				dbService.executeUpdate("UPDATE b SET b.acctno=a.pnno FROM TBACCOUNTINFO a INNER JOIN TBLOANFEESPERAPP b ON a.applno=b.appno AND a.applno=:appno", params);
				
			}else{
				LoggerUtil.error(">>>>bookToLMS - appno parameter cannot be empty !", this.getClass());
				return "failed";
			}
			if(txstat != null){
				params.put("txstat", txstat);
				strQuery += " AND txstat =:txstat";
			}
			listOfLoans = (List<LoanAccountForm>) dbService.execSQLQueryTransformer(strQuery, params, LoanAccountForm.class, 1);
			if(listOfLoans != null && !listOfLoans.isEmpty()){
				LMSEODService eodSrvc = new LMSEODServiceImpl(); 
				if(eodSrvc.loanBooking(listOfLoans).equals("Success")){
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@Override
	public String checkLoanRelease(String appno) {
		String flag = "failed";
		try {
			if(appno!=null){
				params.put("appno", appno);
				Tbaccountinfo info = (Tbaccountinfo) dbService.executeUniqueHQLQueryMaxResultOne
						("FROM Tbaccountinfo WHERE applno=:appno", params);
				String query = "SELECT ISNULL(SUM(ISNULL(amount,0)),0) FROM TBLOANRELEASEINST WHERE status = '0' AND applno=:appno";
				BigDecimal total = (BigDecimal) dbService.executeUniqueSQLQuery
					(query, params);
				if(info!=null && info.getNetprcds()!=null){
					BigDecimal b = new BigDecimal(String.valueOf(total));
					int d = b.compareTo(info.getNetprcds());
					if(d==0){ // equal
						return "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return flag;
	}
	
}
