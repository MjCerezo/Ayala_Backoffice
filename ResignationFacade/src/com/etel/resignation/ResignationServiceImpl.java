package com.etel.resignation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbdocchecklist;
import com.coopdb.data.Tbdocpertransactiontype;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbresign;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.documents.DocumentServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.TransactionNoGenerator;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author Daniel
 */

public class ResignationServiceImpl implements ResignationService {
	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	public List<Tbdocchecklist> getResignationDocuments(String txcode, String memberid, String txrefno) {
		try {
			if (txcode != null && memberid != null) {
				params.put("transcode", txcode);
				params.put("txrefno", Integer.parseInt(txrefno));
				params.put("id", memberid);
				@SuppressWarnings("unchecked")
				List<Tbdocchecklist> d = (List<Tbdocchecklist>) dbService.executeListHQLQuery(
						"FROM Tbdocchecklist WHERE txcode=:transcode AND membershipid=:id AND txrefno=:txrefno",
						params);
				if (d.size() != 0) {
					return d;
				} else if (d.size() == 0) {
					return this.createResignationDocuments(memberid, txcode, Integer.parseInt(txrefno));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String fileResignation(String memberid, String cooponly, String bothcoopcomp, Date effectivity, Date applicationdate) {
		try {
			if (memberid != null) {
				params.put("id", memberid);
					Tbmember f = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:id",
							params);
					if (f != null) {
						int transactionno = TransactionNoGenerator.generateTransactionNo("TRANSACTION");
						Tbresign r = new Tbresign();
						r.setTxcode("060"); // RESIGNATIONTRANSACTIONCODE
						r.setCreatedby(secservice.getUserName());
						r.setCreationdate(applicationdate);
						r.setTxdate(new Date());
						r.setMembershipid(f.getMembershipid());
						r.setFirstname(f.getFirstname());
						r.setMiddlename(f.getMiddlename());
						r.setLastname(f.getLastname());
						r.setTxrefno(transactionno);
						r.setEffectivitydate(effectivity);
						r.setResignstatus("1"); // ENCODING STAGE..
						if (cooponly.equals("true")) {
							r.setCooponly(true);
							r.setBothcoopandcompany(false);
						}
						if (bothcoopcomp.equals("true")) {
							r.setBothcoopandcompany(true);
							r.setCooponly(false);
						}
						if (dbService.save(r)) {
							Map<String, String> docparam = new HashMap<String, String>();
							docparam.put("txrefno", r.getTxrefno().toString());
							docparam.put("membershipid", r.getMembershipid());
							DocumentServiceImpl.createInitialDocumentChecklist("060", docparam);
							return r.getMembershipid();
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String saveOrUpdateResignation(Tbresign resign, String memberid) {
		try {
			if (memberid != null) {
				params.put("memid", memberid);
				Tbresign f = (Tbresign) dbService.executeUniqueHQLQuery("FROM Tbresign WHERE membershipid=:memid",
						params);
				if (f != null) {
					System.out.println(f.getMembershipid());
					f.setCapconaccttype(resign.getCapconaccttype());
					f.setCapconbankacctno(resign.getCapconbankacctno());
					f.setCapconbankname(resign.getCapconbankname());
					f.setCapconinstruction(resign.getCapconinstruction());
					f.setDividendaccttype(resign.getDividendaccttype());
					f.setDividendbankacctno(resign.getDividendbankacctno());
					f.setDividendbankname(resign.getDividendbankname());
					f.setDividendinstruction(resign.getDividendinstruction());
					if (dbService.saveOrUpdate(f))
						return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Problem saving.";
	}

	@Override
	public Tbresign getMemberResign(String memberid) {
		try {
			if (memberid != null) {
				params.put("id", memberid);
				Tbresign ret = (Tbresign) dbService.executeUniqueHQLQuery("FROM Tbresign WHERE membershipid=:id",
						params);
				if (ret != null) {
					return ret;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Tbdocchecklist> createResignationDocuments(String memberid, String txcode, Integer refno) {
		try {
			if (memberid != null) {
				params.put("id", memberid);
				params.put("txcode", txcode);
				params.put("refno", refno);
				@SuppressWarnings("unchecked")
				List<Tbdocpertransactiontype> docs = (List<Tbdocpertransactiontype>) dbService
						.executeListHQLQuery("FROM Tbdocpertransactiontype WHERE txcode=:txcode", params);
				if (docs != null) {
					for (Tbdocpertransactiontype o : docs) {
						Tbdocchecklist create = new Tbdocchecklist();
						create.setTxrefno(refno);
						create.setTxcode(o.getTxcode());
						create.setMembershipid(memberid);
						create.setDocumentcode(o.getDocumentcode());
						create.setDocumentname(o.getDocumentname());
						dbService.save(create);
					}
					@SuppressWarnings("unchecked")
					List<Tbdocchecklist> ret = (List<Tbdocchecklist>) dbService.executeListHQLQuery(
							"FROM Tbdocchecklist WHERE txcode=:txcode AND membershipid=:id AND txrefno=:refno", params);
					if (ret != null) {
						return ret;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public String updateDocuments(String memberid, String doccode, String txcode, int txref, Boolean hassubmit,
			Date datesubmit) {
		try {
			if (memberid != null) {
				params.put("code", doccode);
				params.put("tx", txcode);
				params.put("id", memberid);
				params.put("ref", txref);
				Tbdocchecklist e = (Tbdocchecklist) dbService.executeUniqueHQLQuery(
						"FROM Tbdocchecklist WHERE documentcode=:code AND txcode=:tx AND txrefno=:ref AND membershipid=:id",
						params);
				if (e != null) {
					e.setIssubmitted(hassubmit);
					if (hassubmit) {
						e.setDatesubmitted(datesubmit);
					} else if (!hassubmit) {
						e.setDatesubmitted(null);
					}
					if (dbService.saveOrUpdate(e))
						return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Tbdocchecklist> getDocumentsforReview(String memberid, String txcode, String txrefno) {
		try {
			if (memberid != null) {
				params.put("memid", memberid);
				params.put("txcode", txcode);
				params.put("txrefno", Integer.parseInt(txrefno));
				@SuppressWarnings("unchecked")
				List<Tbdocchecklist> doc = (List<Tbdocchecklist>) dbService.executeListHQLQuery(
						"FROM Tbdocchecklist WHERE txcode=:txcode AND txrefno=:txrefno AND membershipid=:memid",
						params);
				if (doc != null) {
					return doc;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String updateDocumentsforReview(String memberid, String doccode, String txcode, String txrefno,
			Boolean hasreviewed, Date reviewdate) {
		try {
			if (memberid != null) {
				params.put("code", doccode);
				params.put("tx", txcode);
				params.put("id", memberid);
				params.put("ref", Integer.parseInt(txrefno));
				Tbdocchecklist e = (Tbdocchecklist) dbService.executeUniqueHQLQuery(
						"FROM Tbdocchecklist WHERE documentcode=:code AND txcode=:tx AND txrefno=:ref AND membershipid=:id",
						params);
				if (e != null) {
					e.setIsreviewed(hasreviewed);
					if (hasreviewed) {
						e.setDatereviewed(reviewdate);
					} else if (!hasreviewed) {
						e.setDatereviewed(null);
					}
					if (dbService.saveOrUpdate(e))
						return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tbmember getTbmember(String memberid) {
		try {
			if (memberid != null) {
				params.put("memberid", memberid);
				Tbmember m = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:memberid",
						params);
				if (m != null) {
					return m;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public Boolean validateMember(String membershipid) {
		// TODO Auto-generated method stub
		try {
			params.put("membershipid", membershipid);
			if (membershipid != null) {
				// check on-going resignation
				Tbresign r = (Tbresign) dbService.execSQLQueryTransformer(
						"SELECT txrefno, membershipid, resignstatus, txcode, txdate, creationdate FROM Tbresign WHERE resignstatus NOT IN ('5', '6', '7', '8') AND membershipid=:membershipid",
						params, Tbresign.class, 0);
				if (r != null) {
					// if there's an on-going resignation
					return true;
				}
				if(r == null){
					return false;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
