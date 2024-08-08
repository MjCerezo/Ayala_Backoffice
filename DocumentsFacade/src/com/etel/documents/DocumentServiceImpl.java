package com.etel.documents;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.cifsdb.data.Tbdocdetails;
import com.coopdb.data.Tbdocchecklist;
import com.coopdb.data.Tbdocpertransactiontype;
import com.coopdb.data.Tbdocsperapplication;
import com.coopdb.data.Tbdocsperproduct;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.history.HistoryService;
import com.etel.history.HistoryServiceImpl;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.etel.utils.ImageUtils;
import com.etel.utils.UserUtil;
import com.isls.document.forms.DocFields;
import com.coopdb.data.Tbdocuments;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbtransaction;
import com.coopdb.data.Tbworkflowprocess;
import com.wavemaker.common.util.IOUtils;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.server.FileUploadResponse;

public class DocumentServiceImpl implements DocumentService {

	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	DBService dbService = new DBServiceImpl();

	Map<String, Object> param = HQLUtil.getMap();
	private String username = secservice.getUserName();

	@SuppressWarnings("unchecked")
	public static void createInitialDocumentChecklist(String txcode, Map<String, String> ids) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			param.put("txcode", txcode);
			StringBuilder query = new StringBuilder("FROM Tbdocpertransactiontype WHERE txcode=:txcode");
			if (ids.containsKey("reqtype")) {
				for (Map.Entry<String, String> e : ids.entrySet()) {
					if (e.getKey().equals("reqtype")) {
						param.put("reqtype", e.getValue());
						query.append(" AND reqtype=:reqtype");
					}
				}
			}
			List<Tbdocpertransactiontype> doclist = (List<Tbdocpertransactiontype>) dbService
					.executeListHQLQuery(query.toString(), param);
			if (doclist != null) {
				for (Tbdocpertransactiontype d : doclist) {
					Tbdocchecklist doc = new Tbdocchecklist();
					doc.setTxcode(d.getTxcode());
					doc.setDocumentcode(d.getDocumentcode());
					doc.setDocumentname(d.getDocumentname());
					doc.setIssubmitted(false);
					for (Map.Entry<String, String> e : ids.entrySet()) {
						if (e.getKey().equals("dmsid")) {
							doc.setDmsid(e.getValue());
						}
						if (e.getKey().equals("membershipappid")) {
							doc.setMembershipappid(e.getValue());
						}
						if (e.getKey().equals("membershipid")) {
							doc.setMembershipid(e.getValue());
						}
						if (e.getKey().equals("appno")) {
							doc.setAppno(e.getValue());
						}
						if (e.getKey().equals("cifno")) {
							doc.setCifno(e.getValue());
						}
						if (e.getKey().equals("txrefno")) {
							doc.setTxrefno(Integer.parseInt(e.getValue()));
						}
						if (e.getKey().equals("reqtype")) {
							doc.setReqtype(e.getValue());
						}
					}
					dbService.saveOrUpdate(doc);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocchecklist> getNewMemberAppDocChecklist(String membershipappid, String membershipid,
			String status) {
		List<Tbdocchecklist> docs = new ArrayList<Tbdocchecklist>();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			param.put("memappid", membershipappid);
			param.put("memid", membershipid);
			param.put("status", status);
			String qry = "";
			if (membershipappid != null && !membershipappid.equals("")) {
				qry = "FROM Tbdocchecklist WHERE membershipappid=:memappid";
				if (status != null) {
					qry += " AND docstatus=:status";
				}
//				docs = (List<Tbdocchecklist>) dbService
//						.executeListHQLQuery("FROM Tbdocchecklist WHERE membershipappid=:memappid", param);
			}
			if (membershipid != null && !membershipid.equals("")) {
				qry = "FROM Tbdocchecklist WHERE membershipid=:memid";
				if (status != null) {
					qry += " AND docstatus=:status";
				}
//				docs = (List<Tbdocchecklist>) dbService
//						.executeListHQLQuery("FROM Tbdocchecklist WHERE membershipid=:memid", param);
			}
			docs = (List<Tbdocchecklist>) dbService.executeListHQLQuery(qry, param);
			if (docs != null)
				return docs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String verifyNewMemberAppDocumentChecklist(String membershipappid) {
		try {
			ArrayList<Boolean> b = new ArrayList<Boolean>();
			for (Tbdocchecklist l : getNewMemberAppDocChecklist(membershipappid, null, null)) {
				b.add(l.getIssubmitted());
			}
			if (b.contains(false) || b.contains(null)) {
				return "Please submit all required documents.";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}

	@Override
	public String updateDocument(String field, Tbdocchecklist doc) {
		try {
			boolean submitted = false;
			boolean reviewed = false;
			if (doc.getIsreviewed() != null) {
				if (doc.getIsreviewed()) {
					doc.setDatereviewed(new Date());
					reviewed = true;
				} else {
					doc.setDatereviewed(null);
				}
			}
			if (doc.getIssubmitted() != null && doc.getIssubmitted()) {
				submitted = true;
			}
			if (dbService.saveOrUpdate(doc)) {
				if (submitted) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_TAGGED_DOCUMENT_SUBMITTED),
							"User " + username + " Tagged " + doc.getMembershipappid()
									+ "'s Document as \"Submitted\".",
							username, new Date(),
							AuditLogEvents.getEventModule(AuditLogEvents.M_TAGGED_DOCUMENT_SUBMITTED));
				}
				if (reviewed) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_TAGGED_DOCUMENT_AS_REVIEWED),
							"User " + username + " Reviewed " + doc.getMembershipappid() + "'s Submitted Document.",
							username, new Date(),
							AuditLogEvents.getEventModule(AuditLogEvents.M_TAGGED_DOCUMENT_AS_REVIEWED));
				}
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	/*********** FROM UNI ***********/

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocuments> getDocuments() {

		List<Tbdocuments> doc = new ArrayList<Tbdocuments>();
		DBService dbsrvc = new DBServiceImpl();
		@SuppressWarnings("static-access")
		Map<String, Object> param = new HQLUtil().getMap();

		try {
			doc = (List<Tbdocuments>) dbsrvc.executeListHQLQuery("FROM Tbdocuments order by documentcode", param);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc;
	}

	@Override
	public String addDocumentLOS(String category, String doccode, String docname, String remarks) {

		String flag = "";
		Tbdocuments doc = new Tbdocuments();
		DBService dbsrvc = new DBServiceImpl();

		try {
			doc.setCreatedby(secservice.getUserName());
			doc.setDoccategory(category);
			doc.setDocumentcode(doccode);
			doc.setDocumentname(docname);
			doc.setRemarks(remarks);
			doc.setDatecreated(new Date());
			doc.setDoctype(doccode);
			if (dbsrvc.save(doc)) {
				flag = "Success";
			} else {
				flag = "Failed";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public String updateDocumentLOS(String category, String doccode, String docname, String remarks,
			boolean iseditable) {

		String flag = "";
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("doccode", doccode);
		try {
			Tbdocuments doc = (Tbdocuments) dbsrvc.executeUniqueHQLQuery("FROM Tbdocuments WHERE documentcode=:doccode",
					param);

			doc.setUpdatedby(secservice.getUserName());
			doc.setDocumentname(docname);
			doc.setRemarks(remarks);
			doc.setIseditable(iseditable);
			doc.setDateupdated(new Date());
			doc.setDoctype(doccode);

			if (dbsrvc.update(doc)) {
				flag = "Success";
			} else {
				flag = "Failed";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocuments> byCat(String category) {
		List<Tbdocuments> doc = new ArrayList<Tbdocuments>();
		DBService srvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			if (category == null || category.equals("")) {
				doc = (List<Tbdocuments>) srvc.executeListHQLQuery("FROM Tbdocuments order by documentcode", params);
			} else if (category != null) {
				params.put("doccategory", category);
				doc = (List<Tbdocuments>) srvc.executeListHQLQuery(
						"FROM Tbdocuments WHERE doccategory=:doccategory order by documentcode", params);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return doc;
	}

	@Override
	public String checkDocCode(String code) {
		String flag = "";
		Tbdocuments doc = new Tbdocuments();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("documentcode", code);
		try {
			doc = (Tbdocuments) dbsrvc
					.executeUniqueHQLQueryMaxResultOne("FROM Tbdocuments WHERE documentcode=:documentcode", param);
			if (doc != null) {
				flag = "Found";
			} else {
				flag = "Failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String deleteDoc(String code) {
		String flag = "";
		Tbdocuments doc = new Tbdocuments();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("doccode", code);
		try {
			doc = (Tbdocuments) dbsrvc.executeUniqueHQLQuery("FROM Tbdocuments WHERE documentcode=:doccode", params);
			if (dbsrvc.delete(doc)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbtransaction> documentPerTrans(String txname) {
		List<Tbtransaction> trans = new ArrayList<Tbtransaction>();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("txname", "%" + txname + "%");
		try {
			if (txname == null || txname.equals("")) {
				trans = (List<Tbtransaction>) dbsrvc.executeListHQLQuery("FROM Tbtransaction", params);
			} else {
				trans = (List<Tbtransaction>) dbsrvc.executeListHQLQuery("FROM Tbtransaction WHERE txname LIKE :txname",
						params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return trans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocpertransactiontype> reqDocuments(String txcode) {

		List<Tbdocpertransactiontype> docpertx = new ArrayList<Tbdocpertransactiontype>();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("txcode", txcode);
		try {
			docpertx = (List<Tbdocpertransactiontype>) dbsrvc
					.executeListHQLQuery("FROM Tbdocpertransactiontype WHERE txcode=:txcode", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return docpertx;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocuments> tbDocuments() {

		List<Tbdocuments> documents = new ArrayList<Tbdocuments>();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			documents = (List<Tbdocuments>) dbsrvc.executeListHQLQuery("FROM Tbdocuments", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return documents;
	}

	// edited added isExpiring
	@Override
	public String addRequiredDocuments(Tbdocpertransactiontype req) {
		String flag = "";
		Tbdocpertransactiontype docpertrans = new Tbdocpertransactiontype();
		DBService dbsrvc = new DBServiceImpl();
		try {
			if (req.getTransid() != null) {
				if (dbsrvc.saveOrUpdate(req)) {
					return "success";
				}
			} else {

				docpertrans.setTxcode(req.getTxcode());
				docpertrans.setDocumentname(req.getDocumentname());
				docpertrans.setDocumentcode(req.getDocumentcode());
				docpertrans.setTransactionstatus(req.getTransactionstatus());
				docpertrans.setRemarks(req.getRemarks());

				docpertrans.setDoccategory(req.getDoccategory());
				docpertrans.setIsexpiring(req.getIsexpiring());
				docpertrans.setIspartnerrequired(req.getIspartnerrequired());
				docpertrans.setIsindivrequired(req.getIsindivrequired());
				docpertrans.setIssoleproprequired(req.getIssoleproprequired());
				docpertrans.setIscorprequired(req.getIscorprequired());
				docpertrans.setEnablenotarialfee(req.getEnablenotarialfee());

				if (dbsrvc.save(docpertrans)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public String deleteRequiredDocuments(String doccode, String txcode, Integer txid) {
		String flag = "";
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("doccode", doccode);
		params.put("txcode", txcode);
		params.put("id", txid);
		try {
			if (txcode != null && txid == null && doccode == null) {
				Tbdocpertransactiontype docpertrans = (Tbdocpertransactiontype) dbsrvc.executeUniqueHQLQuery(
						"FROM Tbdocpertransactiontype WHERE documentcode=:doccode AND txcode=:txcode", params);
				if (docpertrans != null) {
					if (dbsrvc.delete(docpertrans)) {
						flag = "success";
					}
				}
			}
			if (txcode == null && txid == null && doccode != null) {
				Tbdocpertransactiontype docpertrans = (Tbdocpertransactiontype) dbsrvc
						.executeUniqueHQLQuery("FROM Tbdocpertransactiontype WHERE documentcode=:doccode", params);
				if (docpertrans != null) {
					if (dbsrvc.delete(docpertrans)) {
						flag = "success";
					}
				}
			}
			if (txcode != null && txid == null && doccode != null) {
				Tbdocpertransactiontype docpertrans = (Tbdocpertransactiontype) dbsrvc.executeUniqueHQLQuery(
						"FROM Tbdocpertransactiontype WHERE documentcode=:doccode AND txcode=:txcode", params);
				if (docpertrans != null) {
					if (dbsrvc.delete(docpertrans)) {
						flag = "success";
					}
				}
			}
			if (txid != null) {
				Tbdocpertransactiontype docpertrans = (Tbdocpertransactiontype) dbsrvc
						.executeUniqueHQLQuery("FROM Tbdocpertransactiontype WHERE transid=:id", params);
				if (docpertrans != null) {
					if (dbsrvc.delete(docpertrans)) {
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
	public List<Tbdocsperapplication> getDocumentsPerLoanApplication(String appno, String doccategory, String doccode) {
		Map<String, Object> param = HQLUtil.getMap();
		DBService dbService = new DBServiceImpl();
		try {
			if (appno != null && doccategory != null && doccode != null) {

				param.put("appno", appno);
				param.put("doccategory", doccategory);
				param.put("doccode", doccode);
				List<Tbdocsperapplication> list = (List<Tbdocsperapplication>) dbService.executeListHQLQuery(
						"FROM Tbdocsperapplication WHERE appno=:appno AND documentcode=:doccode AND doccategory=:doccategory",
						param);
//					param.put("cifno", a.getCifno());
//					List<Tbdocchecklist> mlist = (List<Tbdocchecklist>) dbService
//							.executeListHQLQuery("FROM Tbdocchecklist WHERE membershipid=:cifno", param);
//					if (mlist != null) {
//						for (Tbdocchecklist m : mlist) {
//							Tbdocsperapplication d = new Tbdocsperapplication();
//							d.setCifno(m.getMembershipid());
//							d.setDmsid(m.getDmsid());
//							d.setDatereqsubmission(m.getDatereqsubmission());
//							d.setDatesubmitted(m.getDatesubmitted());
//							d.setDateuploaded(m.getDateuploaded());
//							d.setDocbasecode(m.getDocbasecode());
//							d.setDoccategory(m.getDoccategory() == null ? "MEMBERSHIP" : m.getDoccategory());
//							d.setDocid(m.getId());
//							d.setDocstatus(m.getDocstatus());
//							d.setDocappstatus(m.getDocstatus());
//							d.setDocumentcode(m.getDocumentcode());
//							d.setDocumentname(m.getDocumentname());
//							d.setAppno(a.getAppno());
//							d.setFilename(m.getFilename());
//							d.setIssubmitted(m.getIssubmitted());
//							d.setIsuploaded(m.getIsuploaded());
//							d.setRemarks(m.getRemarks());
//							d.setTxcode(m.getTxcode());
//							d.setUploadedby(m.getUploadedby());
//							list.add(d);
//						}
//					}
				if (list != null) {
					return list;
				}
//					if (a.getApplicationstatus() == 1) {
//						param.put("status", a.getApplicationstatus());
//						param.put("id", 3);
//						Tbworkflowprocess flow = (Tbworkflowprocess) dbService.executeUniqueHQLQuery(
//								"FROM Tbworkflowprocess WHERE sequenceno=:status AND workflowid=:id", param);
//						if (flow != null) {
//							param.put("appstatus", flow.getId().getProcessid());
//							List<Tbdocsperapplication> list = (List<Tbdocsperapplication>) dbService
//									.executeListHQLQuery(
//											"FROM Tbdocsperapplication WHERE appno=:appno AND applicationstatus=:appstatus",
//											param);
//							if (list != null) {
//								return list;
//							}
//						}
//					} else {
//						List<Tbdocsperapplication> list = (List<Tbdocsperapplication>) dbService
//								.executeListHQLQuery("FROM Tbdocsperapplication WHERE appno=:appno", param);
//						if (list != null) {
//							return list;
//						}
//					}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String refreshLoanApplicationDocumentChecklist(String appno) {
		Map<String, Object> param = HQLUtil.getMap();
		DBService dbService = new DBServiceImpl();
		try {
			if (appno != null) {
				param.put("appno", appno);
				String loanproduct = (String) dbService
						.execSQLQueryTransformer("SELECT loanproduct FROM Tblstapp WHERE appno=:appno", param, null, 0);
				String cifno = (String) dbService
						.execSQLQueryTransformer("SELECT cifno FROM Tblstapp WHERE appno=:appno", param, null, 0);
				if (loanproduct != null) {
					param.put("loanproduct", loanproduct);
					List<Tbdocsperproduct> list = (List<Tbdocsperproduct>) dbService
							.executeListHQLQuery("FROM Tbdocsperproduct WHERE productcode=:loanproduct", param);
					if (list != null && !list.isEmpty()) {
						for (Tbdocsperproduct d : list) {
							param.put("id", d.getId());
							Tbdocsperapplication docSubmitted = (Tbdocsperapplication) dbService
									.executeUniqueHQLQueryMaxResultOne(
											"FROM Tbdocsperapplication WHERE appno=:appno AND docchecklistid=:id",
											param);
							if (docSubmitted != null) {
								// Update existing record
								docSubmitted.setDocumentcode(d.getDocumentcode());
								// docSubmitted.setDocumentname(d.getDocumentname());
								docSubmitted.setUploadedby(null);
								docSubmitted.setDateuploaded(new Date());
								dbService.saveOrUpdate(docSubmitted);
							} else {
								// Create new record
								Tbdocsperapplication docS = new Tbdocsperapplication();
								docS.setAppno(appno);
								docS.setCifno(cifno);
								docS.setDocid(d.getId());
								docS.setDocumentcode(d.getDocumentcode());
								// docS.setDocumentname(d.getDocumentname());
								// docS.setUploadedby(username);
								dbService.save(docS);
							}
						}
					}
					// Delete from Tbdocsperapplication if not in default
					// Tbdocsperproduct
					dbService.executeUpdate(
							"DELETE FROM Tbdocsperapplication WHERE docchecklistid NOT IN(SELECT id FROM Tbdocsperproduct WHERE productcode='"
									+ loanproduct + "')",
							param);
					return "success";
				} else {
					System.out.println("----------- No Loan Product");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	public static void createDocumentsPerProductApplication(String appno, String productcode) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			param.put("appno", appno);
			param.put("productcode", productcode);
			String memberid = (String) dbService.executeUniqueSQLQuery("SELECT cifno FROM Tblstapp WHERE appno=:appno",
					param);
			@SuppressWarnings("unchecked")
			List<Tbdocsperproduct> doculoans = (List<Tbdocsperproduct>) dbService
					.executeListHQLQuery("FROM Tbdocsperproduct WHERE productcode=:productcode", param);
			if (doculoans != null) {
				for (Tbdocsperproduct d : doculoans) {
					param.put("documentcode", d.getDocumentcode());
					Tbdocsperapplication docapp = (Tbdocsperapplication) dbService.executeUniqueHQLQuery(
							"FROM Tbdocsperapplication WHERE documentcode=:documentcode AND appno=:appno", param);
					if (docapp == null) {
						Tbdocsperapplication newdoc = new Tbdocsperapplication();
						newdoc.setAppno(appno);
						newdoc.setCifno(memberid);
						newdoc.setDocumentcode(d.getDocumentcode());
						newdoc.setDocumentname(d.getDocumentname());
						newdoc.setApplicationstatus(d.getApplicationstatus());
						newdoc.setDoccategory(d.getDoccategory());
						dbService.save(newdoc);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String batchUpdateDocsSubmit(List<Tbdocsperapplication> docs) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Map<String, Object> param = HQLUtil.getMap();
		DBService dbService = new DBServiceImpl();
		try {
			System.out.println("ez");
			for (Tbdocsperapplication docsapp : docs) {
				Tbdocsperapplication app = new Tbdocsperapplication();
				param.put("id", docsapp.getDocid());

				app = (Tbdocsperapplication) dbService
						.executeUniqueHQLQuery("FROM Tbdocsperapplication WHERE docid=:id", param);
				app.setIssubmitted(docsapp.getIssubmitted());
				if (dbService.saveOrUpdate(app)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}
	// Modified String Filepath

	@Override
	public String updateDocperApp(Tbdocsperapplication doc, String filepath) {
		// TODO Auto-generated method stub
		// added file for filename
		String flag = "failed";
		Map<String, Object> param = HQLUtil.getMap();
		DBService dbService = new DBServiceImpl();
		File file = null;
		if (filepath != null) {
			file = new File(filepath);
			// System.out.println(filepath);
		}
		Tbdocsperapplication docapp = new Tbdocsperapplication();
		try {
			if (doc.getDocid() != null) {
				param.put("docid", doc.getDocid());
				docapp = (Tbdocsperapplication) dbService
						.executeUniqueHQLQuery("FROM Tbdocsperapplication WHERE docid=:docid", param);
				if (docapp != null) {
					docapp.setDocappstatus(doc.getDocappstatus());
					docapp.setRemarks(doc.getRemarks());
					docapp.setDocanalystremarks(doc.getDocanalystremarks());

					String stat = doc.getDocappstatus() == null ? "" : doc.getDocappstatus();
					// System.out.println("doc app stat: " + stat);
					if (stat.equals("1")) {
						docapp.setIssubmitted(true);
						// MAR
						docapp.setUploadedby(secservice.getUserName());
						docapp.setDatesubmitted(new Date());
						docapp.setDateuploaded(new Date());
					} else {
						docapp.setIssubmitted(false);
					}
					if (doc.getDocappstatus().equals("1")) {
						if (filepath != null) {
							docapp.setDocbasecode(ImageUtils.pdfToBase64(filepath));
							docapp.setFilename(file.getName());
						} else {
							docapp.setDocbasecode(docapp.getDocbasecode());
							docapp.setFilename(docapp.getFilename());
						}
					} else {
						docapp.setDocbasecode("");
						docapp.setFilename("");
					}
					if (dbService.saveOrUpdate(docapp)) {
						// ADDED FOR SAVING FED
						flag = "success";
					}
					// ADDED FOR SAVING FED
					// param.put("appno", docapp.getAppno());
					// Integer status = (Integer) dbService
					// .executeUniqueSQLQuery("SELECT applicationstatus FROM Tblstapp WHERE
					// appno=:appno", param);
					// if (status.equals(1)) {
					// AuditLog.addAuditLog(
					// AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("UPDATE DOCUMENT",
					// AuditLogEvents.LOAN_APPLICATION_ENCODING)),
					// "User " + username + " Updated " + docapp.getAppno() + "'s Document
					// details.", username,
					// new Date(), AuditLogEvents.LOAN_APPLICATION_ENCODING);
					// }
					// if (status.equals(6)) {
					// AuditLog.addAuditLog(
					// AuditLogEvents.getAuditLogEvents(
					// AuditLogEvents.getEventID("UPDATE DOCUMENT CHECKLIST IN DOCS STAGE",
					// AuditLogEvents.LOAN_APPLICATION_DOCUMENTATION)),
					// "User " + username + " Updated " + docapp.getAppno()
					// + "'s Document Checklist in Docs Stage.",
					// username, new Date(), AuditLogEvents.LOAN_APPLICATION_DOCUMENTATION);
					// }

					// System.out.println("doc app stat:
					// "+doc.getDocappstatus());
					// if(docapp.getDocappstatus().equals("1")){
					// docapp.setIssubmitted(true);
					// }else if(doc.getDocappstatus()==null){
					// docapp.setIssubmitted(false);
					// System.out.println("BOOM");
					// }
					// else{
					// System.out.println("ez");
					// docapp.setIssubmitted(false);
					// }
					// dbService.saveOrUpdate(docapp);

				}
			} else {
				doc.setDocappstatus(doc.getDocappstatus());
				doc.setRemarks(doc.getRemarks());
				doc.setDocanalystremarks(doc.getDocanalystremarks());
				String stat = doc.getDocappstatus() == null ? "" : doc.getDocappstatus();
				// System.out.println("doc app stat: " + stat);
				if (stat.equals("1")) {
					doc.setIssubmitted(true);
					doc.setDateuploaded(new Date());
					doc.setDatesubmitted(new Date());
					doc.setUploadedby(secservice.getUserName());
				} else {
					doc.setIssubmitted(false);

				}
				// CED 07272022 On Hand ONLY
				if (filepath != null && doc.getDocappstatus().equals("1")) {
					doc.setDocbasecode(ImageUtils.pdfToBase64(filepath));
					doc.setFilename(file.getName());
				} else {
					doc.setDocbasecode("");
					doc.setFilename("");
				}

				if (dbService.saveOrUpdate(doc)) {
					// ADDED FOR SAVING FED
					flag = "success";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}

	@Override
	public String previewMembershipDocument(int docid) {
		// TODO Auto-generated method stub
		Map<String, Object> param = HQLUtil.getMap();
		DBService dbService = new DBServiceImpl();
		param.put("id", docid);
		Tbdocchecklist doc = (Tbdocchecklist) dbService.executeUniqueHQLQuery("FROM Tbdocchecklist WHERE id=:id",
				param);
		String path = "resources/docdir/";
//		File dir = new File(
//				RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/docdir/"));
		File dir = new File(RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(path));
		if (!dir.exists())
			dir.mkdirs();
		try {
			if (doc.getDocbasecode() == null && doc.getFilename() == null) {
				return "none";
			}
			if (doc.getDocbasecode() != null && doc.getFilename() != null) {
				String filepath = dir.toString() + "\\";
				ImageUtils.base64ToPDF(doc.getDocbasecode().toString(), filepath, doc.getFilename());
				File file = new File(dir.toString() + "\\" + doc.getFilename());
//				if (Desktop.isDesktopSupported()) {
//					Desktop.getDesktop().open(file);
//					return "success";
//				} else {
				return path + doc.getFilename();
//				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failed";
		}
		return "failed";
	}

	// Added by Fedric
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocuments> getDocumentsByDocCat(String doccat) {
		List<Tbdocuments> doc = new ArrayList<Tbdocuments>();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		System.out.println(doccat);
		params.put("doccategory", doccat);

		try {
			doc = (List<Tbdocuments>) dbsrvc.executeListHQLQuery(
					"FROM Tbdocuments where doccategory=:doccategory order by documentcode", params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return doc;
	}

	@Override
	public String previewLoanApplicationDocument(int docid) {
		// TODO Auto-generated method stub
		Map<String, Object> param = HQLUtil.getMap();
		DBService dbService = new DBServiceImpl();
		param.put("id", docid);
		Tbdocsperapplication doc = (Tbdocsperapplication) dbService
				.executeUniqueHQLQuery("FROM Tbdocsperapplication WHERE docid=:id", param);
		String path = "resources/docdir/";
//		File dir = new File(
//				RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/docdir/"));
		File dir = new File(RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(path));
		if (!dir.exists())
			dir.mkdirs();
		try {
			if (doc.getDocbasecode() == null && doc.getFilename() == null) {
				return "none";
			}
			if (doc.getDocbasecode() != null && doc.getFilename() != null) {
				String filepath = dir.toString() + "\\";
				ImageUtils.base64ToPDF(doc.getDocbasecode().toString(), filepath, doc.getFilename());
				File file = new File(dir.toString() + "\\" + doc.getFilename());
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().open(file);
					return "success";
				} else {
					return path + doc.getFilename();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "failed";
		}
		return "failed";
	}

	@Override
	public String refreshMembershipDocumentsChecklist(String membershipid, String membershipappid) {
		// TODO Auto-generated method stub
		try {

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public String getDocIDbyMemberIDandDocID(String memid, String docid) {
		Map<String, Object> param = HQLUtil.getMap();
		DBService dbService = new DBServiceImpl();
		param.put("memid", memid);
		param.put("docid", docid);
		String flag = "";
		try {
			int id = (Integer) dbService.executeUniqueHQLQuery(
					"SELECT id FROM Tbdocchecklist WHERE membershipid =:memid and documentcode=:docid", param);
			flag = previewMembershipDocument(id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String updateDocument(Tbdocdetails doc) {
		// TODO Auto-generated method stub
		// Update details in document

		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		String flag = "failed";

		try {
			params.put("docid", doc.getDocid());
			System.out.println("docid >>>>>> " + doc.getDocid());
			Tbdocdetails doclist = (Tbdocdetails) dbService
					.executeUniqueHQLQuery("FROM Tbdocdetails WHERE docid=:docid", params);
			if (doclist != null) {
				doclist.setPnno(doc.getPnno());
				doclist.setLinerefno(doc.getLinerefno());
				doclist.setCollateralno(doc.getCollateralno());
				doclist.setDoccategory(doc.getDoccategory());
				doclist.setDoctype(doc.getDoctype());
				doclist.setDatereceived(doc.getDatereceived());
				doclist.setDocstatus(doc.getDocstatus());
				doclist.setUpdatecycle(doc.getUpdatecycle());
				doclist.setRemarks(doc.getRemarks());
				doclist.setIdtype(doc.getIdtype());
				doclist.setIdno(doc.getIdno());
				doclist.setIssuedate(doc.getIssuedate());
				doclist.setIssuecountry(doc.getIssuecountry());
				doclist.setIssueplace(doc.getIssueplace());
				doclist.setExpirydate(doc.getExpirydate());
				doclist.setIssuedby(doc.getIssuedby());
				doclist.setCoveredyear(doc.getCoveredyear());
				doclist.setDatefiledbir(doc.getDatefiledbir());
				doclist.setLineeffectivitydate(doc.getLineeffectivitydate());
				doclist.setLineexpirydate(doc.getLineexpirydate());
				doclist.setChassisno(doc.getChassisno());
				doclist.setDescription(doc.getDescription());
				doclist.setTctno(doc.getTctno());
				doclist.setSuretyno(doc.getSuretyno());
				doclist.setFundercifno(doc.getFundercifno());
				doclist.setLoitype(doc.getLoitype());
				doclist.setProofdoctype(doc.getProofdoctype());
				doclist.setDateserved(doc.getDateserved());
				doclist.setSignatoryname(doc.getSignatoryname());
				doclist.setName(doc.getName());
				doclist.setDateadded(doc.getDateadded());
				doclist.setIncorporationdate(doc.getIncorporationdate());
				doclist.setRegistrationno(doc.getRegistrationno());
				doclist.setRegistrationtype(doc.getRegistrationtype());
				doclist.setSecsubmitteddate(doc.getSecsubmitteddate());
				doclist.setStatussecsubmission(doc.getStatussecsubmission());
				doclist.setDateofmeeting(doc.getDateofmeeting());
				doclist.setLoanappno(doc.getLoanappno());
				doclist.setDateofapplication(doc.getDateofapplication());
				doclist.setPqtype(doc.getPqtype());
				doclist.setDateuploaded(doc.getDateuploaded());
				doclist.setUploadedby(doc.getUploadedby());
				doclist.setOtherdoc(doc.getOtherdoc());

			}

			if (dbService.saveOrUpdate(doclist)) {
				flag = "success";
				// 08-08-17 PONGYU
				HistoryService h = new HistoryServiceImpl();
				h.addHistory(doclist.getCifno(), "Update document: " + doc.getDoctype(), null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocdetails> displayDocsDetails(String doccategory, String doctype, String cifno) {
		// TODO Auto-generated method stub
		// Display Doc Details

		List<Tbdocdetails> document = new ArrayList<Tbdocdetails>();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("doccategory", doccategory);
		params.put("doctype", doctype);
		params.put("cifno", cifno);

		try {
			document = (List<Tbdocdetails>) dbService.executeListHQLQuery(
					"FROM Tbdocdetails WHERE doccategory=:doccategory AND cifno=:cifno AND doctype=:doctype", params);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return document;
	}

	@Override
	public String deleteDocument(int docid) {
		// TODO Auto-generated method stub
		String flag = "failed";
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("docid", docid);
		Tbdocdetails doc = new Tbdocdetails();
		try {
			doc = (Tbdocdetails) dbService.executeUniqueHQLQuery("FROM Tbdocdetails WHERE docid=:docid", params);
			// 08-08-17 PONGYU
			HistoryService h = new HistoryServiceImpl();
			h.addHistory(doc.getCifno(), "Deleted document: " + doc.getDoctype(), "");
			if (dbService.delete(doc)) {
				flag = "successful";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;

	}

	@Override
	public String addDocument(Tbdocdetails doc) {
		// TODO Auto-generated method stub

		String result = "failed";
		DBService dbService = new DBServiceImplCIF();
		Tbdocdetails docval = new Tbdocdetails();
		Map<String, Object> params = HQLUtil.getMap();
//		params.put("docid", doc.getDocid());
		try {
			// Daniel 04-13-2019, validations
			if (doc.getDoctype() != null) {
				params.put("cifno", doc.getCifno());
				params.put("doctype", doc.getDoctype());
				params.put("category", doc.getDoccategory());
				if (doc.getDoctype().equals("ID")) {
					params.put("idtype", doc.getIdtype());
					docval = (Tbdocdetails) dbService.executeUniqueHQLQuery(
							"FROM Tbdocdetails WHERE cifno=:cifno AND doccategory=:category AND doctype=:doctype AND idtype=:idtype",
							params);
					if (docval != null) {
						return "existingid";
					}
				}
			}

			if (doc.getDmsid() != null) {
				params.put("dmsid", doc.getDmsid());
				docval = (Tbdocdetails) dbService.executeUniqueHQLQuery("FROM Tbdocdetails WHERE dmsid=:dmsid", params);
				if (docval == null) {
					// NEW
					docval = doc;
					docval.setDateuploaded(new Date());
					docval.setUploadedby(secservice.getUserName());
					if (dbService.save(docval)) {
						result = "success";
						// 08-08-17 PONGYU
						HistoryService h = new HistoryServiceImpl();
						h.addHistory(docval.getCifno(), "Added new document: " + doc.getDoctype(), null);
					} else {
						result = "exist";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocdetails> getDocDetails(String cifno, String doccat, String doccode) {
		List<Tbdocdetails> doc = new ArrayList<Tbdocdetails>();
		Map<String, Object> params = HQLUtil.getMap();
		DBService dbService = new DBServiceImplCIF();
		try {
//				System.out.println(doccode);
//				System.out.println(appno);
//				System.out.println(doccategory);
			if (doccode != null && cifno != null && doccat != null) {
				params.put("doccode", doccode);
				params.put("cifno", cifno);
				params.put("doccategory", doccat);
				doc = (List<Tbdocdetails>) dbService.executeListHQLQuery(
						"FROM Tbdocdetails WHERE doctype=:doccode " + "and cifno=:cifno and doccategory=:doccategory",
						params);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return doc;
	}

	@Override
	public String saveOrUpdateDocDetails(Tbdocdetails details, String filepath) {

		System.out.println();

		File file = null;
		if (filepath != null) {
			file = new File(filepath);
		}
		Tbdocdetails doc = new Tbdocdetails();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();

		if (details.getDocid() != null) {
			params.put("docid", details.getDocid());
			doc = (Tbdocdetails) dbService.executeUniqueHQLQuery("FROM Tbdocdetails WHERE docid=:docid", params);
			doc.setDoccategory(details.getDoccategory());
			doc.setCifno(details.getCifno());
			doc.setDocstatus(details.getDocstatus());
			doc.setName(details.getName());
			doc.setRemarks(details.getRemarks());
			doc.setDatereceived(new Date());
			// added fed 06.30.2020
			doc.setIdtype(details.getIdtype());
			doc.setUpdatecycle(details.getUpdatecycle());
			doc.setSignatoryname(details.getSignatoryname());
			doc.setIdno(details.getIdno());
			doc.setExpirydate(details.getExpirydate());
			doc.setDatereceived(details.getDatereceived());
			doc.setDatefiledbir(details.getDatefiledbir());
			doc.setDmsid(details.getDmsid());
			doc.setIssueplace(details.getIssueplace());
			doc.setIssuecountry(details.getIssuecountry());
			doc.setIssuedby(details.getIssuedby());
			doc.setIssuedate(details.getIssuedate());
			
			//Mar 02-08-23
			doc.setExpirydate(details.getExpirydate());
			if (filepath != null) {
				doc.setDocfilename(file.getName());
				doc.setDocbasecode(ImageUtils.pdfToBase64(filepath));
			} else {
				doc.setDocfilename(details.getDocfilename());
				doc.setDocbasecode(details.getDocbasecode());
			}

			if (dbService.saveOrUpdate(doc)) {
				System.out.println("updated");
				return "updated";
			}
		} else {
//			details.setDocbasecode(ImageUtils.pdfToBase64(filepath));
//			details.setDocfilename(file.getName());
			details.setDateuploaded(new Date());
			details.setUploadedby(UserUtil.securityService.getUserName());
			details.setRemarks(details.getRemarks());

			if (filepath != null) {
				details.setDocfilename(file.getName());
				details.setDocbasecode(ImageUtils.pdfToBase64(filepath));
			}

			if (dbService.saveOrUpdate(details)) {
				System.out.println("success");
				return "success";
			}
		}
		return "failed";

	}

	@Override
	public String checkPicOrPDF(Integer docid) {
		String filepath = null;
		String ext = null;
		Tbdocdetails doc = new Tbdocdetails();
		DBService dbService = new DBServiceImplCIF();
		File dir = new File(
				RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/docdir"));
		Map<String, Object> params = HQLUtil.getMap();
		params.put("docid", docid);

		try {
			doc = (Tbdocdetails) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbdocdetails WHERE docid=:docid",
					params);
			if (doc != null) {
				try {
					if (doc.getDocbasecode() != null) {
						ImageUtils.base64ToPDF(doc.getDocbasecode(), dir.toString() + "\\", doc.getDocfilename());
						filepath = "resources\\docdir\\" + doc.getDocfilename();

						ext = filepath.substring(filepath.lastIndexOf("."));
					} else {
						return "failed";
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				return "failed";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ext;
	}

	@Override
	public String saveOrUpdateDocs(Tbdocuments ref, String meth) {
		Tbdocuments d = new Tbdocuments();

		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("documentcode", ref.getDocumentcode());
		d = (Tbdocuments) dbService.executeUniqueHQLQuery("FROM Tbdocuments WHERE documentcode=:documentcode", params);
		if (meth.equalsIgnoreCase("update")) {
			// update
			if (d != null) {
				d.setDocumentname(ref.getDocumentname());
				d.setDoccategory(ref.getDoccategory());
				d.setRemarks(ref.getRemarks());

				if (dbService.saveOrUpdate(d)) {
					System.out.println("up");
					return "update";
				}
			}
		} else if (meth.equalsIgnoreCase("save")) {
			// save
			if (d != null) {
				// existing
				System.out.println("exist");
				return "existing";
			} else {
				System.out.println("save");
				dbService.saveOrUpdate(ref);
				return "success";
			}
		}

		return "failed";
	}

	@Override
	public String deleteDocs(String documentcode) {
		DBService dbService = new DBServiceImplCIF();
		String flag = "failed";
		try {
			System.out.println(documentcode + " to delete");
			if (documentcode != null) {
				Integer res = dbService
						.executeUpdate("DELETE FROM TBDOCUMENTS WHERE documentcode ='" + documentcode + "'", null);
				if (res != null && res == 1) {
					System.out.println(documentcode + " deleted");
					flag = "success";
				} else {
					System.out.println(documentcode + " not deleted");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public FileUploadResponse uploadFile(MultipartFile file) {
		FileUploadResponse ret = new FileUploadResponse();
//		Integer maxFileSize = documentProperties().getMaxfilesize();
		try {

//			if(file.getSize() >= maxFileSize) {
//				System.out.println("FEEEEEEEEEEEED");
//				ret.setError("Invalid File Size");
//			}
//			else {
			// System.out.println(file.getSize());
			String filename = file.getOriginalFilename(); /* .replaceAll("[^a-zA-Z0-9 ._-]",""); */
			boolean hasExtension = filename.indexOf(".") != -1;
			// String name = (hasExtension) ? filename.substring(0,
			// filename.lastIndexOf(".")) : filename;
			String ext = (hasExtension) ? filename.substring(filename.lastIndexOf(".")) : "";
			if (ext.equalsIgnoreCase(".pdf") || ext.equalsIgnoreCase(".jpg") || ext.equalsIgnoreCase(".png")
					|| ext.equalsIgnoreCase(".jpeg")) {
				File dir = new File(
						RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/docdir/"));
				if (!dir.exists())
					dir.mkdirs();

				/*
				 * Create a file object that does not point to an existing file. Loop through
				 * names until we find a filename not already in use
				 */
				File outputFile = new File(dir, filename);
				// ystem.out.println(outputFile + "FEEEEEEEED");
				deleteFileOlderThanXdays(1, dir.toString());

				/* Write the file to the filesystem */
				FileOutputStream fos = new FileOutputStream(outputFile);
				IOUtils.copy(file.getInputStream(), fos);
				file.getInputStream().close();
				fos.close();

				ret.setPath(outputFile.getPath());
				ret.setError("");
			}

			else {
				ret.setError("Invalid File Format");
			}
			// }
		} catch (Exception e) {
			System.out.println("ERROR11:" + e.getMessage() + " | " + e.toString());

			ret.setError(e.getMessage());
		}
		return ret;
	}

	/** Delete file older than x days */
	public void deleteFileOlderThanXdays(long days, String dirPath) {
		File folder = new File(dirPath);
		if (folder.exists()) {
			File[] listFiles = folder.listFiles();
			long eligibleForDeletion = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000);
			for (File listFile : listFiles) {
				if (listFile.lastModified() < eligibleForDeletion) {
					if (!listFile.delete()) {
						System.out.println("Unable to Delete Files..");
					}
				}
			}
		}
	}

	@Override
	public String viewDocument(Integer docid) {
		String filepath = null;
		String ext = null;
		DBService dbService = new DBServiceImplCIF();
		Tbdocdetails doc = new Tbdocdetails();
		File dir = new File(
				RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/docdir"));
		Map<String, Object> params = HQLUtil.getMap();
		params.put("docid", docid);
		doc = (Tbdocdetails) dbService.executeUniqueHQLQuery("FROM Tbdocdetails WHERE docid=:docid", params);
		if (doc != null) {
			try {

				if (doc.getDocbasecode() != null) {
					ImageUtils.base64ToPDF(doc.getDocbasecode(), dir.toString() + "\\", doc.getDocfilename());
					ext = doc.getDocfilename().substring(doc.getDocfilename().lastIndexOf("."));
					if (ext.equals(".pdf")) {
						filepath = "resources/docdir/" + doc.getDocfilename();
					} else {
						filepath = ImageUtils.ImageToPDF(dir.toString() + "\\", doc.getDocfilename());
					}
				} else {
					return "failed";
				}
				// System.out.println(filepath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return "failed";
		}
		return filepath;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocsperproduct> getDocumentPerProduct(String appno, String doccategory) {
		List<Tbdocsperproduct> document = new ArrayList<Tbdocsperproduct>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();

		try {
			if (appno != null && doccategory != null) {
				params.put("doccategory", doccategory);
				params.put("appno", appno);
				Tblstapp app = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp where appno=:appno", params);
				if (app != null) {
					if (app.getLoanproduct() != null) {
						params.put("loanproduct", app.getLoanproduct());
						document = (List<Tbdocsperproduct>) dbService.executeListHQLQuery(
								"FROM Tbdocsperproduct WHERE doccategory=:doccategory AND productcode=:loanproduct",
								params);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return document;
	}

	@Override
	public String viewDocumentLAS(Integer docid) {
		String filepath = null;
		String ext = null;
		DBService dbService = new DBServiceImpl();
		Tbdocsperapplication doc = new Tbdocsperapplication();
		File dir = new File(
				RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/docdir"));
		Map<String, Object> params = HQLUtil.getMap();
		params.put("docid", docid);
		doc = (Tbdocsperapplication) dbService.executeUniqueHQLQuery("FROM Tbdocsperapplication WHERE docid=:docid",
				params);
		if (doc != null) {
			try {

				if (doc.getDocbasecode() != null) {
					ImageUtils.base64ToPDF(doc.getDocbasecode(), dir.toString() + "\\", doc.getFilename());
					ext = doc.getFilename().substring(doc.getFilename().lastIndexOf("."));
					if (ext.equals(".pdf")) {
						filepath = "resources/docdir/" + doc.getFilename();
					} else {
						filepath = ImageUtils.ImageToPDF(dir.toString() + "\\", doc.getFilename());
					}
				} else {
					return "failed";
				}
				// System.out.println(filepath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return "failed";
		}
		return filepath;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocsperproduct> getDocumentPerProductNoDocCat(String appno) {
		List<Tbdocsperproduct> document = new ArrayList<Tbdocsperproduct>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (appno != null) {
				params.put("appno", appno);
				Tblstapp app = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp where appno=:appno", params);
				if (app != null) {
					if (app.getLoanproduct() != null) {
						params.put("loanproduct", app.getLoanproduct());
						document = (List<Tbdocsperproduct>) dbService
								.executeListHQLQuery("FROM Tbdocsperproduct WHERE productcode=:loanproduct", params);
					}
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return document;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdocsperapplication> getDocumentsPerLoanApplicationNoDocCat(String appno, String doccode) {
		Map<String, Object> param = HQLUtil.getMap();
		DBService dbService = new DBServiceImpl();
		try {
			if (appno != null && doccode != null) {
				param.put("appno", appno);
				param.put("doccode", doccode);
				List<Tbdocsperapplication> list = (List<Tbdocsperapplication>) dbService.executeListHQLQuery(
						"FROM Tbdocsperapplication WHERE appno=:appno AND documentcode=:doccode", param);
				if (list != null) {
					return list;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
