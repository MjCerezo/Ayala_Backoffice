/**
 * 
 */
package com.etel.docmaintenance;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.docmaintenanceform.DocChecklistForm;
import com.etel.docmaintenanceform.ListDocCheckListForm;
//import com.etel.creditfacility.CreditFacilityServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.LoggerUtil;
import com.etel.utils.UserUtil;
import com.etel.workflow.forms.WorkflowDashboardForm;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbdocchecklist;
import com.coopdb.data.Tbdocsperapplication;
import com.coopdb.data.Tbdocspercf;
import com.coopdb.data.Tbdocspercfapp;
import com.coopdb.data.Tbdocsperproduct;
import com.coopdb.data.Tbdocspertrans;
import com.coopdb.data.Tbgeneraldocs;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbtransaction;

/**
 * @author MMM
 *
 */
public class DocMaintenanceServiceImpl implements DocMaintenanceService {

	/* (non-Javadoc)
	 * @see com.etel.docmaintenance.DocMaintenanceService#getDocumentListPerDocCategory(java.lang.String)
	 */
	
	private DBService dbService = new DBServiceImpl();
	
	private Map<String, Object> params = HQLUtil.getMap();
	String username = UserUtil.securityService.getUserName();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbgeneraldocs> getDocumentListPerDocCategory(String category) {
		List<Tbgeneraldocs> doclist = new ArrayList<Tbgeneraldocs>();
		
		try {
			if(category != null){
				params.put("category", category);
				doclist = (List<Tbgeneraldocs>) dbService.executeListHQLQuery("FROM Tbgeneraldocs where id.doccategory=:category", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doclist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocChecklistForm> getDocumentListPerDocCategoryNew(String category, String cifno, String custType) {
		List<DocChecklistForm> doclist = new ArrayList<DocChecklistForm>();
		try {
			if(category != null){
				params.put("category", category);
				params.put("cifno", cifno);
				params.put("custType", custType);
				doclist = (List<DocChecklistForm>) dbService.execSQLQueryTransformer(
						"EXEC sp_GetListofDocument @doccategory=:category, @cifno=:cifno,  @customerType=:custType", params,
						DocChecklistForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doclist;
	}	
	
	@Override
	public String saveDocType(Tbgeneraldocs doc) {
		String flag = "failed";
		
		try {
			if(doc.getId().getDoccategory() != null && doc.getId().getDoctype() != null && doc.getDocname() != null) {
				
				params.put("code", doc.getId().getDoctype());
				params.put("category", doc.getId().getDoccategory() );
				Tbgeneraldocs checkdoc = (Tbgeneraldocs) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbgeneraldocs WHERE id.doccategory=:category AND id.doctype=:code", params);
				if(checkdoc == null) {
					doc.setCreatedby(UserUtil.securityService.getUserName());
					doc.setDatecreated(new Date());
				} else {
					doc.setDateupdated(new Date());
					doc.setUpdatedby(UserUtil.securityService.getUserName());
				}
				dbService.saveOrUpdate(doc);
				flag="success";
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean checkDocTypeAvailability(String doctype, String doccategory) {
		// TODO Auto-generated method stub
		boolean result = false;
		try {
			params.put("code", doctype);
			params.put("category", doccategory);
			Tbgeneraldocs checkdoc = (Tbgeneraldocs) dbService.executeUniqueHQLQueryMaxResultOne(
					"FROM Tbgeneraldocs WHERE id.doccategory=:category AND id.doctype=:code", params);
			if (checkdoc == null) {
				result = false;
			} else {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String deleteDocCategory(Tbcodetable doccat) {
		String flag = "failed";
		//check if there are existing doc type under the doc category
		List<Tbgeneraldocs> doclist = new ArrayList<Tbgeneraldocs>();
		try {
			params.put("category", doccat.getId().getCodevalue());
			doclist = (List<Tbgeneraldocs>) dbService.executeListHQLQuery("FROM Tbgeneraldocs where id.doccategory=:category", params);
			if (doclist.size() == 0) {
				dbService.delete(doccat);
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String deleteDocType(Tbgeneraldocs doc) {
		String flag = "failed";
		
		//check if there are existing doc type under the doc category
		
		List<Tbdocsperproduct> doclistperprod = new ArrayList<Tbdocsperproduct>();
		List<Tbdocspercf> doclistpercf = new ArrayList<Tbdocspercf>();
		List<Tbdocspercfapp> doclistperapp = new ArrayList<Tbdocspercfapp>();
		try {
			params.put("category", doc.getId().getDoccategory());
			params.put("type", doc.getId().getDoctype());
			doclistperprod = (List<Tbdocsperproduct>) dbService.executeListHQLQuery("FROM Tbdocsperproduct where doccategory=:category AND documentcode=:type", params);
			doclistpercf = (List<Tbdocspercf>) dbService.executeListHQLQuery("FROM Tbdocspercf where documentcode=:type", params);
			doclistperapp = (List<Tbdocspercfapp>) dbService.executeListHQLQuery("FROM Tbdocspercfapp where doccategory=:category AND documentcode=:type", params);
					
			if (doclistperprod.size() == 0 && doclistpercf.size() == 0 && doclistperapp.size() == 0) {
				dbService.delete(doc);
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocspercfapp> getDocsPerApplicationPerCF(String loanappno, String cfcode) {
		
		List<Tbdocspercfapp> doclistperapp = new ArrayList<Tbdocspercfapp>();
		try {
			if(loanappno != null && cfcode != null){
				params.put("loanappno", loanappno);
				params.put("cfcode", cfcode);
				doclistperapp = (List<Tbdocspercfapp>) dbService.executeListHQLQuery("FROM Tbdocspercfapp WHERE appno=:loanappno AND cfcode=:cfcode", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return doclistperapp;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getGenerateDocsPerAppPerCF(String loanappno, String cfcode, String cifno, String cfrefnoconcat) {
		String flag = "failed";
		List<Tbdocspercf> doclistpercf = new ArrayList<Tbdocspercf>();
		Tbdocspercfapp docperapp = new Tbdocspercfapp();
		Tbgeneraldocs gendoc = new Tbgeneraldocs();
		try {
			params.put("cifno", cifno);
			
			// get business type
			String businesstype = (String) dbService.executeUniqueSQLQuery("SELECT b.desc2 FROM TBCIFCORPORATE a "
					+ "INNER JOIN TBCODETABLE b ON a.businesstype=b.codevalue AND b.codename = 'BUSINESSTYPE' WHERE a.cifno=:cifno", params);
			
			if(businesstype != null){
				
				params.put("cfcode", cfcode);
				doclistpercf = (List<Tbdocspercf>) dbService.executeListHQLQuery("FROM Tbdocspercf WHERE facilitycode=:cfcode", params);
				
				for (Tbdocspercf doccf	: doclistpercf) {
					
					params.put("loanappno", loanappno);
					params.put("doccat", doccf.getId().getDoccategory());
					params.put("doctype", doccf.getId().getDocumentcode());
					params.put("cfrefnoconcat", cfrefnoconcat);
					docperapp = (Tbdocspercfapp) dbService.executeUniqueHQLQuery("FROM Tbdocspercfapp WHERE appno=:loanappno AND cfrefno=:cfrefnoconcat AND cfcode=:cfcode AND doccategory=:doccat AND documentcode=:doctype", params);
					
					if(businesstype.equals("Corporation")) {
						gendoc = (Tbgeneraldocs) dbService.executeUniqueHQLQuery("From Tbgeneraldocs where doccategory=:doccat AND doctype=:doctype AND applytocorp='true'", params);
					}
					else if(businesstype.equals("Partnership")) {
						gendoc = (Tbgeneraldocs) dbService.executeUniqueHQLQuery("From Tbgeneraldocs where doccategory=:doccat AND doctype=:doctype AND applytopart='true'", params);
					}
					else if(businesstype.equals("Sole Proprietorship")) {
						gendoc = (Tbgeneraldocs) dbService.executeUniqueHQLQuery("From Tbgeneraldocs where doccategory=:doccat AND doctype=:doctype AND applytosole='true'", params);
					}
					else {
						gendoc = (Tbgeneraldocs) dbService.executeUniqueHQLQuery("From Tbgeneraldocs where doccategory=:doccat AND doctype=:doctype AND appytoindiv='true'", params);
					}
	
					if (gendoc != null) {
						if (docperapp == null) {
							// new
	
							Tbdocspercfapp newdoc = new Tbdocspercfapp();
	
							newdoc.setAppno(loanappno);
							newdoc.setCfrefno(cfrefnoconcat);
							newdoc.setCfcode(cfcode);
							newdoc.setCifno(cifno);
							newdoc.setDoccategory(doccf.getId().getDoccategory());
							newdoc.setDocumentcode(doccf.getId().getDocumentcode());
							newdoc.setDocumentname(doccf.getDocumentname());
							newdoc.setIssubmitted(false);
							newdoc.setIsuploaded(false);
							newdoc.setEnablenotarialfee(doccf.getEnablenotarialfee());
							newdoc.setEnablerequired(doccf.getEnablerequired());
							newdoc.setIsforpreloanrelease(false);
							newdoc.setIsforavailment(false);
							if(dbService.save(newdoc)){
								
								//Replicate CF Details to LAM - Kevin 01-16-2019
								//CreditFacilityServiceImpl.replicateCFDetailsToLAMLoanDetails(newdoc.getAppno());
							}
	
						} else {
							docperapp.setDocumentname(doccf.getDocumentname());
							docperapp.setIssubmitted(false);
							docperapp.setIsuploaded(false);
							docperapp.setEnablenotarialfee(doccf.getEnablenotarialfee());
							docperapp.setEnablerequired(doccf.getEnablerequired());
							if(dbService.saveOrUpdate(docperapp)){
								
								//Replicate CF Details to LAM - Kevin 01-16-2019
								//CreditFacilityServiceImpl.replicateCFDetailsToLAMLoanDetails(docperapp.getAppno());
							}
						}
					}
				}
				flag = "success";
			}else{
				flag = "failed";
				LoggerUtil.error(">>>>>>>>CIF Businesstype is null", this.getClass());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateDocumentperCF(Tbdocspercfapp doc) {
		String result = "failed";
		try {
			if (doc != null) {
				if(dbService.saveOrUpdate(doc)){
					result = "success";
					
					//Replicate CF Details to LAM - Kevin 01-16-2019
					//CreditFacilityServiceImpl.replicateCFDetailsToLAMLoanDetails(doc.getAppno());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocspertrans> getListDocsPerTrans(String txcode) {
		List<Tbdocspertrans> docs = new ArrayList<Tbdocspertrans>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (txcode != null) {
				params.put("txcode", txcode);
				docs = (List<Tbdocspertrans>) dbService
						.executeListHQLQuery("FROM Tbdocspertrans WHERE txcode=:txcode", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docs;
	}

	@Override
	public String saveOrDeleteDocumentPerTrans(Tbdocspertrans docpertrans, String ident) {
		String flag = "failed";
		try {
			if (docpertrans != null && ident != null) {
				
				if(ident.equals("delete")){
					if(dbService.delete(docpertrans)){
						flag = "success";
					}
				}else{
					if (docpertrans.getId() != null && docpertrans.getTxcode() != null) {
						params.put("id", docpertrans.getId());
						params.put("txcode", docpertrans.getTxcode());
						Tbdocspertrans docspertx = (Tbdocspertrans) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbdocspertrans WHERE id=:id AND txcode=:txcode", params);
						if (docspertx != null) {
							docpertrans.setCreatedby(docspertx.getCreatedby());
							docpertrans.setDatecreated(docspertx.getDatecreated());
							docpertrans.setUpdatedby(UserUtil.securityService.getUserName());
							docpertrans.setDateupdated(new Date());
							if (dbService.saveOrUpdate(docpertrans)) {
								flag = "success";
							}
						} else {
							docpertrans.setCreatedby(UserUtil.securityService.getUserName());
							docpertrans.setDatecreated(new Date());
							if (dbService.save(docpertrans)) {
								flag = "success";
							}
						}
					} else{
						params.put("txcode", docpertrans.getTxcode());
						params.put("doccategory", docpertrans.getDoccategory());
						params.put("doccode", docpertrans.getDocumentcode());
						Integer cnt = (Integer) dbService.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbdocspertrans WHERE txcode=:txcode AND doccategory=:doccategory AND documentcode=:doccode", params);
						if(cnt > 0){
							return "existing";
						}
						
						docpertrans.setCreatedby(UserUtil.securityService.getUserName());
						docpertrans.setDatecreated(new Date());
						if (dbService.save(docpertrans)) {
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

	//added by fed
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbtransaction> getListTransaction() {
		List<Tbtransaction> docs = new ArrayList<Tbtransaction>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			
				//params.put("txcode", txcode);
				docs = (List<Tbtransaction>) dbService
						.executeListHQLQuery("FROM Tbtransaction order by txcode", null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docs;
	}

	@Override
	public String saveUpdateTransaction(Tbtransaction form,String beingUpdated) {
		String flag = "failed";
	
		if(beingUpdated.equals("false")) {
			if(form != null)
			{
				params.put("txcode", form.getTxcode());
				Tbtransaction docs = (Tbtransaction) dbService
						.executeUniqueHQLQuery("FROM Tbtransaction WHERE txcode=:txcode", params);
				if(docs != null) {
					flag = "existing";
					return flag;
				}
				else {
					dbService.saveOrUpdate(form);
					flag = "success";
					return flag;
				}
			}
		}
		else {
		

			if(form != null) {
				params.put("id", form.getId());
				Tbtransaction trans = (Tbtransaction) dbService
						.executeUniqueHQLQuery("FROM Tbtransaction WHERE id=:id", params);
				trans.setTxcode(form.getTxcode());
				trans.setTxname(form.getTxname());
				
				dbService.saveOrUpdate(trans);
				flag = "success";
				return flag;
				
			}
			
		}
		return flag;
		
			
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocChecklistForm> getDocChecklist(String appno, String memberid) {
		List<DocChecklistForm> list = new ArrayList<DocChecklistForm>();
		try {
			if(appno!=null && memberid!=null){
				params.put("memberid", memberid);
				params.put("appno", appno);
				// member
//				List<Tbdocchecklist> a = (List<Tbdocchecklist>) dbService.executeListHQLQuery
//						("FROM Tbdocchecklist WHERE membershipid=:memberid", params);
//				if(a!=null){
//					for(Tbdocchecklist b : a){
//						DocChecklistForm c = new DocChecklistForm();
//						c.setDocumentname(b.getDocumentname());
//						c.setDocstatus(b.getDocstatus());
//						list.add(c);
//					}
//				}
				// loans
				List<Tbdocsperapplication> a2 = (List<Tbdocsperapplication>) dbService.executeListHQLQuery
						("FROM Tbdocsperapplication WHERE appno=:appno", params);
				if(a2!=null){
					for(Tbdocsperapplication b : a2){
						DocChecklistForm c = new DocChecklistForm();
						c.setDocumentname(b.getDocumentname());
						c.setDocstatus(b.getDocstatus());
						c.setDocanalystremarks(b.getDocanalystremarks());
						c.setRemarks(b.getRemarks());
						list.add(c);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String refreshDocChecklist(String appno) {
		String flag = "success";
		try {
			if (appno != null) {
				params.put("appno", appno);
				Tblstapp app = (Tblstapp) dbService
						.executeUniqueHQLQueryMaxResultOne("FROM Tblstapp WHERE appno=:appno", params);
				if (app != null && app.getLoanproduct() != null) {
					params.put("lp", app.getLoanproduct());
					List<Tbdocsperproduct> p = (List<Tbdocsperproduct>) dbService
							.executeListHQLQuery("FROM Tbdocsperproduct WHERE productcode=:lp", params);
					if (p != null) {
						for (Tbdocsperproduct a : p) {
							params.put("docappno", app.getAppno());
							params.put("doccode", a.getDocumentcode());
							Tbdocsperapplication e = (Tbdocsperapplication) dbService.executeUniqueHQLQuery(
									"FROM Tbdocsperapplication WHERE appno=:docappno AND documentcode=:doccode",
									params);
							if (e == null) {
								e = new Tbdocsperapplication();
								e.setDocumentcode(a.getDocumentcode());
								e.setDoccategory(a.getDoccategory());
								e.setDocumentname(a.getDocumentname());
								e.setAppno(app.getAppno());
								dbService.save(e);
							}
//							Tbdocsperapplication n = new Tbdocsperapplication();
//							n.setDocumentcode(a.getDocumentcode());
//							n.setDoccategory(a.getDoccategory());
//							n.setDocumentname(a.getDocumentname());
//							n.setAppno(app.getAppno());
//							dbService.save(n);
						}
					}
					params.put("productcode", app.getLoanproduct());
					dbService.executeUpdate(
							"DELETE FROM Tbdocsperapplication WHERE appno=:appno AND documentcode NOT IN(SELECT documentcode FROM Tbdocsperproduct WHERE productcode=:productcode)",
							params);
//					params.put("memberid", app.getCifno());
//					List<Tbdocchecklist> c = (List<Tbdocchecklist>) dbService
//							.executeListHQLQuery("FROM Tbdocchecklist WHERE cifno=:memberid", params);
//					if (c != null) {
//						for (Tbdocchecklist a : c) {
//							params.put("cifno", a.getMembershipid());
//							params.put("docappno", app.getAppno());
//							params.put("doccode", a.getDocumentcode());
//							params.put("doccategory", a.getDoccategory());
//							params.put("txcode", a.getTxcode());
//
//							Tbdocsperapplication e = (Tbdocsperapplication) dbService.executeListHQLQuery(
//									"FROM Tbdocsperapplication WHERE cifno=:cifno AND appno=:docappno AND documentcode=:doccode AND doccategory=:doccategory AND txcode=:txcode",
//									params);
//							if (e == null) {
//								e = new Tbdocsperapplication();
//								e.setDocumentcode(a.getDocumentcode());
//								e.setDoccategory(a.getDoccategory());
//								e.setDocumentname(a.getDocumentname());
//								e.setAppno(app.getAppno());
//								e.setCifno(a.getMembershipid());
//								e.setTxcode(a.getTxcode());
//								e.setIssubmitted(a.getIssubmitted());
//								e.setRemarks(a.getRemarks());
//								e.setDatereqsubmission(a.getDatereqsubmission());
//								dbService.save(e);
//							}
////							Tbdocsperapplication n = new Tbdocsperapplication();
////							n.setDocumentcode(a.getDocumentcode());
////							n.setDoccategory(a.getDoccategory());
////							n.setDocumentname(a.getDocumentname());
////							n.setAppno(app.getAppno());
////							n.setCifno(a.getMembershipid());
////							n.setTxcode(a.getTxcode());
////							n.setIssubmitted(a.getIssubmitted());
////							n.setRemarks(a.getRemarks());
////							n.setDatereqsubmission(a.getDatereqsubmission());
////							dbService.save(n);
//						}
//					}
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


}
