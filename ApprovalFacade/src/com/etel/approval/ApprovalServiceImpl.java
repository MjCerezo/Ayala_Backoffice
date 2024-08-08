package com.etel.approval;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbapprovalmatrix;
import com.coopdb.data.Tbloanapprovaldetails;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class ApprovalServiceImpl implements ApprovalService {

	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	public static SecurityService securityService = (SecurityService) RuntimeAccess.getInstance()
			.getServiceBean("securityService");
	private String username = securityService.getUserName();

	/**
	 * -- Generate Loan Approval Details--
	 * 
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 */
	@Override
	public String generateLoanApprovalDetails(String appno, Integer evalreportid, BigDecimal loanamount,
			String transactiontype, String loanproduct, Integer approvallevel, String approver) {
		String flag = "failed";
		try {
			if (appno != null && evalreportid != null && loanamount != null && transactiontype != null) {
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				params.put("loanamount", loanamount);
				params.put("transactiontype", transactiontype);
				params.put("loanproduct", loanproduct == null ? "" : loanproduct);
				params.put("approvallevel", approvallevel);
				params.put("approver", approver);
//				String coopcode = (String) dbService
//						.execStoredProc("SELECT coopcode FROM Tblstapp WHERE appno=:appno", params, null, 0, null);
//				params.put("coopcode", coopcode);
//				System.out.println(dbService.execStoredProc("INSERT INTO TBLOANAPPROVALDETAILS "
//						+ "(evalreportid, appno, assigneddate, readstatus, username, approvallevel) "
//						+ "SELECT :evalreportid, :appno, GETDATE(), 0, b.username, :approvallevel "
//						+ "FROM TBUSER a INNER JOIN TBUSERROLES b ON a.username=b.username WHERE b.roleid=:approver "
//						+ "AND a.username NOT IN (SELECT username FROM TBLOANAPPROVALDETAILS "
//						+ "WHERE appno=:appno AND evalreportid=:evalreportid AND approvallevel =:approvallevel) and a.coopcode =:coopcode",
//						params, null, 2, null) + " " + approver + " users assigned for level " + approvallevel);
				
				String res = (String) dbService.execSQLQueryTransformer(
						"EXEC sp_GenerateApprovalDetails @appno=:appno, @evalreportid=:evalreportid, @transtype=:transactiontype, @loanproduct=:loanproduct, @loanamount=:loanamount", params, null, 0);
				if (res != null && res.equals("success")) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
		return flag;
	}

	/**
	 * --Get List of Loan Approval Details--
	 * 
	 * @author Kevin (08.25.2018)
	 * @return List<{@link Tbloanapprovaldetails}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbloanapprovaldetails> getListOfLoanApprDetails(String appno, Integer evalreportid,
			Boolean decisionflag) {
		List<Tbloanapprovaldetails> list = new ArrayList<Tbloanapprovaldetails>();
		try {
			String hql = "FROM Tbloanapprovaldetails WHERE id.appno=:appno AND id.evalreportid=:evalreportid";
			if (appno != null && evalreportid != null) {
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				if (decisionflag != null && decisionflag) {
					hql += " AND decision IS NOT NULL";
				}
				hql += "  ORDER BY decision, decisiondate";
				list = (List<Tbloanapprovaldetails>) dbService.executeListHQLQuery(hql, params);
				if (list != null) {
					for (Tbloanapprovaldetails a : list) {
						a.getId().setUsername(UserUtil.getUserFullname(a.getId().getUsername()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * -- Save or Update Loan Approval Details--
	 * 
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 */
	@Override
	public String saveOrUpdateLoanApprDetails(Tbloanapprovaldetails loanapprddetails) {
		String flag = "failed";
		try {
			if (loanapprddetails.getId().getEvalreportid() != null && loanapprddetails.getId().getAppno() != null
					&& loanapprddetails.getId().getUsername() != null
					&& loanapprddetails.getId().getApprovallevel() != null && loanapprddetails.getDecision() != null) {
				params.put("appno", loanapprddetails.getId().getAppno());
				params.put("evalreportid", loanapprddetails.getId().getEvalreportid());
				params.put("username", loanapprddetails.getId().getUsername());
				params.put("apprlevel", loanapprddetails.getId().getApprovallevel());
				params.put("decision", loanapprddetails.getDecision());

				String query = "SELECT COUNT(*) FROM Tbloanapprovaldetails WHERE appno=:appno AND evalreportid=:evalreportid AND approvallevel=:apprlevel";
				// Approved || Approved with Condition
				if (loanapprddetails.getDecision().equals("1") || loanapprddetails.getDecision().equals("2")) {
					query += " AND decision IN('1','2')";
				} else {
					query += " AND decision=:decision";
				}
				Integer decisioncount = (Integer) dbService.execSQLQueryTransformer(query, params, null, 0);
				Tbloanapprovaldetails lad = (Tbloanapprovaldetails) dbService.executeUniqueHQLQuery(
						"FROM Tbloanapprovaldetails WHERE id.appno=:appno AND id.evalreportid=:evalreportid AND id.username=:username AND id.approvallevel=:apprlevel",
						params);
				if (lad != null) {
					lad.setRemarks(loanapprddetails.getRemarks());
					lad.setDecision(loanapprddetails.getDecision());
					lad.setDecisiondate(new Date());
					if (lad.getApprovalsequence() == null) {
						lad.setApprovalsequence((decisioncount == null ? 0 : decisioncount) + 1);
					}
					if (dbService.saveOrUpdate(lad)) {
						AuditLog.addAuditLog(
								AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("SUBMIT DECISION",
										AuditLogEvents.LOAN_APPLICATION_APPROVAL)),
								"User " + username + " Submitted Approval Decision. (" + lad.getId().getAppno() + ")",
								username, new Date(), AuditLogEvents.LOAN_APPLICATION_APPROVAL);
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Get Loan Approval Details by appno, evalreportid and username--
	 * 
	 * @author Kevin (08.25.2018)
	 * @return form = {@link Tbloanapprovaldetails}
	 */
	@Override
	public Tbloanapprovaldetails getLoanApprovalDetails(String appno, Integer evalreportid, String username) {
		Tbloanapprovaldetails apprdetails = new Tbloanapprovaldetails();
		try {
			if (appno != null && evalreportid != null && username != null) {
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				params.put("username", username);
				int apprlevel = (Integer) dbService.executeUniqueSQLQuery(
						"SELECT ISNULL(MAX(approvallevel),1) FROM Tbloanapprovaldetails WHERE appno=:appno AND evalreportid=:evalreportid AND username=:username",
						params);
				params.put("apprlevel", apprlevel);
				apprdetails = (Tbloanapprovaldetails) dbService.executeUniqueHQLQuery(
						"FROM Tbloanapprovaldetails WHERE id.appno=:appno AND id.evalreportid=:evalreportid AND id.username=:username AND id.approvallevel=:apprlevel",
						params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apprdetails;
	}

	/**
	 * --Get Approval Level (Approval Matrix)--
	 * 
	 * @author Kevin (08.25.2018)
	 * @return Integer = approval level
	 */
	@Override
	public Integer getApprovalLevel(BigDecimal loanamount, String transactiontype) {
		Integer res = 0;
		try {
			if (loanamount != null && transactiontype != null) {
				String hql = "SELECT CASE WHEN '" + loanamount + "' <= a.level1limit THEN 1  " + "WHEN '" + loanamount
						+ "' > a.level1limit AND '" + loanamount + "' <= a.level2limit THEN 2 " + "WHEN '" + loanamount
						+ "' > a.level2limit AND '" + loanamount + "' <= a.level3limit THEN 3 " + "WHEN '" + loanamount
						+ "' > a.level3limit THEN 4 END approvallevel "
						+ "FROM (SELECT * FROM Tbapprovalmatrix WHERE transactiontype = '" + transactiontype + "') a";

				res = (Integer) dbService.execSQLQueryTransformer(hql, params, null, 0);
				if (res == null) {
					res = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * --Get Approval Decision Count--
	 * 
	 * @author Kevin (08.25.2018)
	 * @return Integer = count per decision ('Approved','Approved with
	 *         condition',Rejected)
	 */
	@Override
	public Integer getDecisionCount(String appno, Integer evalreportid, String decision, int approvallevel) {
		Integer res = 0;
		try {
			if (appno != null && appno != null && decision != null) {
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				params.put("decision", decision);
				params.put("approvallevel", approvallevel);
				String query = "SELECT COUNT(*) FROM Tbloanapprovaldetails WHERE appno=:appno AND evalreportid=:evalreportid AND approvallevel=:approvallevel";
				if (decision.equals("1") || decision.equals("2")) {
					query += " AND decision IN('1','2')";
				} else {
					query += " AND decision=:decision";
				}
				res = (Integer) dbService.execSQLQueryTransformer(query, params, null, 0);
				if (res == null) {
					res = 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * --Get Loan Approval Matrix (Parameter)--
	 * 
	 * @author Kevin (08.25.2018)
	 * @return form = {@link Tbapprovalmatrix}
	 */
	@Override
	public Tbapprovalmatrix getApprovalMatrixByTranstype(String transactiontype, String loanproduct) {
		Tbapprovalmatrix apprmatrix = new Tbapprovalmatrix();
		try {
			if (transactiontype != null) {
				params.put("transactiontype", transactiontype);
				params.put("loanproduct", loanproduct);
				// Daniel modified(04.05.2019)
				if (loanproduct != null) {
					apprmatrix = (Tbapprovalmatrix) dbService.executeUniqueHQLQuery(
							"FROM Tbapprovalmatrix WHERE transactiontype=:transactiontype AND loanproduct=:loanproduct",
							params);
				} else {
					apprmatrix = (Tbapprovalmatrix) dbService.executeUniqueHQLQueryMaxResultOne(
							"FROM Tbapprovalmatrix WHERE transactiontype=:transactiontype", params);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return apprmatrix;
	}

	/**
	 * --Get Main CF Total Proposed Amount--
	 * 
	 * @author Kevin (08.25.2018)
	 * @return BigDecimal = Sum of Proposed Credit Limit
	 */
	@Override
	public BigDecimal getLAMMainCFTotalProposedAmt(String appno, Integer evalreportid) {
		BigDecimal total = BigDecimal.ZERO;
		try {
			if (appno != null && evalreportid != null) {
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
//				Object obj = (Object)dbService.execSQLQueryTransformer("SELECT SUM(ISNULL(proposedamount, 0)) FROM Tblamloandetails WHERE appno=:appno AND evalreportid=:evalreportid AND cflevel = '0'", params, null, 0);
//				if(obj != null){
//					total = new BigDecimal(String.valueOf(obj));
//				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	/**
	 * --Validate Line Approval--
	 * 
	 * @author Kevin (08.25.2018)
	 * @return true otherwise false
	 */
	@Override
	public Boolean validateApproval(String appno, Integer evalreportid, String decision, String transactiontype,
			String product) {
		boolean flag = false;
		try {
			if (appno != null && evalreportid != null && decision != null && transactiontype != null) {
				// Get Approval Matrix
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				params.put("decision", decision);
				params.put("txtype", transactiontype);
//				product = (String) dbService.execStoredProc("SELECT loanproduct FROM Tblstapp WHERE appno=:appno",
//						params, null, 0, null);
//				params.put("product", product == null ? "" : product);
//				System.out.println("ez " + params);
				Tbapprovalmatrix apprmatrix = (Tbapprovalmatrix) dbService.executeUniqueHQLQuery(
						"FROM Tbapprovalmatrix WHERE transactiontype=:txtype", params);
				if (apprmatrix != null) {
					// Get total proposed amount
					Tbaccountinfo acctInfo = (Tbaccountinfo) dbService
							.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno=:appno", params);
					BigDecimal loanamount = acctInfo != null
							? acctInfo.getFaceamt() != null ? acctInfo.getFaceamt() : BigDecimal.ZERO
							: BigDecimal.ZERO;
					Tbloanapprovaldetails apprDetails = getLoanApprovalDetails(appno, evalreportid,
							securityService.getUserName());
					// Get Approval Level
					Integer apprlevel = apprDetails.getId().getApprovallevel();
					if (apprlevel != null && apprlevel != 0) {
						// Get Decision Count
						Integer decisioncnt = getDecisionCount(appno, evalreportid, decision, apprlevel);
						System.out
								.println("deccount " + decisioncnt + " deicsion " + decision + " amount " + loanamount);
						if (decisioncnt != null && decisioncnt != 0) {
							// APPROVED || APPROVED w/ CONDITION
							if (decision.equals("1") || decision.equals("2")) {
								if (apprlevel == 1) {
									if (apprmatrix.getLevel1requiredapproval() != null) {
										if (decisioncnt >= apprmatrix.getLevel1requiredapproval()) {
											dbService.executeUpdate("UPDATE TBLSTAPP set approvaldecision='approved' WHERE appno=:appno", params);
											if (loanamount.compareTo(apprmatrix.getLevel1limit()) == 1) {
//												generateLoanApprovalDetails(appno, evalreportid, loanamount,
//														transactiontype, product, 2, apprmatrix.getLevel2approver());
												flag = true;
											} else {
												flag = true;
											}
										}
									}
								}
								if (apprlevel == 2) {
									if (apprmatrix.getLevel2requiredapproval() != null) {
										if (decisioncnt >= apprmatrix.getLevel2requiredapproval()) {
											dbService.executeUpdate("UPDATE TBLSTAPP set approvaldecision='approved' WHERE appno=:appno", params);
											if (loanamount.compareTo(apprmatrix.getLevel2limit()) == 1
													&& apprmatrix.getLevel3approver() != null
													&& !apprmatrix.getLevel3approver().isEmpty()
													&& apprmatrix.getLevel3limit() != null) {
//												generateLoanApprovalDetails(appno, evalreportid, loanamount,
//														transactiontype, product, 3, apprmatrix.getLevel3approver());
												flag = true;
											} else {
												flag = true;
											}
										}
									}
								}
								if (apprlevel == 3) {
									if (apprmatrix.getLevel3requiredapproval() != null) {
										if (decisioncnt >= apprmatrix.getLevel3requiredapproval()) {
											dbService.executeUpdate("UPDATE TBLSTAPP set approvaldecision='approved' WHERE appno=:appno", params);
											if (loanamount.compareTo(apprmatrix.getLevel3limit()) == 1
													&& apprmatrix.getLevel4approver() != null
													&& !apprmatrix.getLevel4approver().isEmpty()
													&& apprmatrix.getLevel4limit() != null) {
//												generateLoanApprovalDetails(appno, evalreportid, loanamount,
//														transactiontype, product, 4, apprmatrix.getLevel4approver());
												flag = true;
											} else {
												flag = true;
											}
										}
									}
								}
								if (apprlevel == 4) {
									if (apprmatrix.getLevel3requiredapproval() != null) {
										if (decisioncnt >= apprmatrix.getLevel4requiredapproval()) {
											dbService.executeUpdate("UPDATE TBLSTAPP set approvaldecision='approved' WHERE appno=:appno", params);
											flag = true;
										}
									}
								}
							}

							// REJECTED
							if (decision.equals("3")) {
								if (apprlevel == 1) {
									if (apprmatrix.getLevel1requiredrejected() != null) {
										if (decisioncnt >= apprmatrix.getLevel1requiredrejected()) {
											dbService.executeUpdate("UPDATE TBLSTAPP set approvaldecision='rejected' WHERE appno=:appno", params);
											flag = true;
										}
									}
								}
								if (apprlevel == 2) {
									if (apprmatrix.getLevel2requiredrejected() != null) {
										if (decisioncnt >= apprmatrix.getLevel2requiredrejected()) {
											dbService.executeUpdate("UPDATE TBLSTAPP set approvaldecision='rejected' WHERE appno=:appno", params);
											flag = true;
										}
									}
								}
								if (apprlevel == 3) {
									if (apprmatrix.getLevel3requiredrejected() != null) {
										if (decisioncnt >= apprmatrix.getLevel3requiredrejected()) {
											dbService.executeUpdate("UPDATE TBLSTAPP set approvaldecision='rejected' WHERE appno=:appno", params);
											flag = true;
										}
									}
								}
								if (apprlevel == 4) {
									if (apprmatrix.getLevel4requiredrejected() != null) {
										if (decisioncnt >= apprmatrix.getLevel4requiredrejected()) {
											flag = true;
										}
									}
								}
							} // end of rejected decision

						} // end of decision count

					} // end of apprlevel

				} // end of appr matrix
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * -- Approved Credit Facility--
	 * 
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public String approvedCFLineApplication(String appno, Integer evalreportid) {
//		String flag = "failed";
//		String prefix = appno.substring(0, 2);
//		try {
//			params.put("appno", appno);
//			params.put("evalreportid", evalreportid);
//			List<Tblamloandetails> lamCF = (List<Tblamloandetails>) dbService.executeListHQLQuery("FROM Tblamloandetails WHERE appno=:appno AND evalreportid=:evalreportid AND cflevel='0'", params);
//			if(lamCF != null){
//				for(Tblamloandetails lam : lamCF){
//					lam.setApproveloanamount(lam.getProposedamount());
//					lam.setUpdatedby(UserUtil.securityService.getUserName());
//					lam.setLastupdated(new Date());
//					if(dbService.saveOrUpdate(lam)){
//						params.put("cfrefno", lam.getCfrefno());
//						
//						/*
//						 * Approved proposed amount TBCFDETAILS
//						 * CFSTATUS = 1 (APPROVED)
//						 * */
//						dbService.executeUpdate("UPDATE Tbcfdetails SET cfapprovedamt = cfproposedamt, cfstatus = '1' WHERE cfappno=:appno AND cfrefno=:cfrefno", params);
//						dbService.executeUpdate("UPDATE Tbcfcoobligor SET cfstatus = '1' WHERE cfappno=:appno AND cfrefno=:cfrefno", params);
//						
//						//Override Tbcfcovenants
//						dbService.executeUpdate("DELETE FROM Tbcfcovenants WHERE cfappno=:appno AND cfrefno=:cfrefno", params);
//						List<Tblamcovenants> lamCovenants = (List<Tblamcovenants>) dbService.executeListHQLQuery("FROM Tblamcovenants WHERE appno=:appno AND cfrefno=:cfrefno AND evalreportid=:evalreportid", params);
//						if(lamCovenants != null && !lamCovenants.isEmpty()){
//							for(Tblamcovenants cfcovnts : lamCovenants) {
//								Tbcfcovenants cv = new Tbcfcovenants();
//								cv.setCfappno(appno);
//								cv.setCfrefno(cfcovnts.getCfrefno());
//								cv.setCflevel(cfcovnts.getCflevel());
//								cv.setCfseqno(cfcovnts.getCfseqno());
//								cv.setCfsubseqno(cfcovnts.getCfsubseqno());
//								cv.setCfrefnoconcat(cfcovnts.getCfrefnoconcat());
//								cv.setCfcovenants(cfcovnts.getCovenants());
//								cv.setCreatedby(UserUtil.securityService.getUserName());
//								cv.setDatecreated(new Date());
//								dbService.save(cv);
//							}
//						}
//						
//						
//						/*
//						 * IF CREDIT LINE RENEWAL
//						 * */
//						if(prefix.equals("RE")){
//							params.put("cfrefno", lam.getCfrefno());
//							Tbcfdetails cf = (Tbcfdetails) dbService.executeUniqueHQLQuery("FROM Tbcfdetails WHERE id.cfappno=:appno AND id.cfrefno=:cfrefno AND id.cflevel='0'", params);
//							if(cf != null){
//								cf.setCfexpdt(cf.getCfrequestedvalidity());
//								if(dbService.saveOrUpdate(cf)){
//									
//									//Set Prev Facility CFSTATUS  to 6 (RENEWED)
//									String prevcfappno = cf.getPrevcfappno();
//									params.put("prevcfappno", prevcfappno);
//									dbService.executeUpdate("UPDATE Tbcfdetails SET cfstatus = '6' WHERE cfappno=:prevcfappno AND cfrefno=:cfrefno", params);
//									dbService.executeUpdate("UPDATE Tbcfcoobligor SET cfstatus = '6' WHERE cfappno=:prevcfappno AND cfrefno=:cfrefno", params);
//								}
//							}
//						}
//						
//						/*
//						 * IF CREDIT LINE AMENDMENT
//						 * */
//						if(prefix.equals("AM")){
//							params.put("cfrefno", lam.getCfrefno());
//							Tbcfdetails cf = (Tbcfdetails) dbService.executeUniqueHQLQuery("FROM Tbcfdetails WHERE id.cfappno=:appno AND id.cfrefno=:cfrefno AND id.cflevel='0'", params);
//							if(cf != null){
//									
//								//Set Prev Facility CFSTATUS  to 7 (AMENDED)
//								String prevcfappno = cf.getPrevcfappno();
//								params.put("prevcfappno", prevcfappno);
//								dbService.executeUpdate("UPDATE Tbcfdetails SET cfstatus = '7' WHERE cfappno=:prevcfappno AND cfrefno=:cfrefno", params);
//								dbService.executeUpdate("UPDATE Tbcfcoobligor SET cfstatus = '7' WHERE cfappno=:prevcfappno AND cfrefno=:cfrefno", params);
//							}
//						}
//						
//						
//					}
//				}
//				flag = "success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}

	/**
	 * --Approval Read Status flag--
	 * 
	 * @author Kevin (08.25.2018)
	 * @return String = success otherwise failed
	 */
	@Override
	public String approvalReadStatus(String appno, Integer evalreportid, String username) {
		String msg = "failed";
		try {
			if (appno != null && evalreportid != null && username != null) {
				params.put("appno", appno);
				params.put("evalreportid", evalreportid);
				params.put("username", username);
				Integer res = dbService.executeUpdate(
						"UPDATE Tbloanapprovaldetails SET readstatus = '1', lastreaddate = GETDATE() WHERE appno=:appno AND evalreportid=:evalreportid AND username=:username",
						params);
				if (res != null) {
					if (res == 0) {
						msg = "Approver username not found ! " + username + " " + params;
						System.out.println(msg);
					}
					if (res > 0) {
						msg = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return msg;
	}
	/**
	 * -- Reject Credit Facility--
	 * 
	 * @author Kevin (09.10.2018)
	 * @return String = success otherwise failed
	 */
//	@SuppressWarnings("unchecked")
//	@Override
//	public String rejectCFLineApplication(String appno, Integer evalreportid) {
//		String flag = "failed";
//		try {
//			params.put("appno", appno);
//			params.put("evalreportid", evalreportid);
//			List<Tblamloandetails> lamCF = (List<Tblamloandetails>) dbService.executeListHQLQuery("FROM Tblamloandetails WHERE appno=:appno AND evalreportid=:evalreportid AND cflevel='0'", params);
//			if(lamCF != null){
//				for(Tblamloandetails lam : lamCF){
//					lam.setUpdatedby(UserUtil.securityService.getUserName());
//					lam.setLastupdated(new Date());
//					if(dbService.saveOrUpdate(lam)){
//						params.put("cfrefno", lam.getCfrefno());
//						/*
//						 * Reject cfrefno TBCFDETAILS
//						 * CFSTATUS = 5 (REJECTED)
//						 * */
//						dbService.executeUpdate("UPDATE Tbcfdetails SET cfstatus = '5' WHERE cfappno=:appno AND cfrefno=:cfrefno", params);
//						dbService.executeUpdate("UPDATE Tbcfcoobligor SET cfstatus = '5' WHERE cfappno=:appno AND cfrefno=:cfrefno", params);
//					}
//				}
//				flag = "success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}

	/**
	 * --Get Total Loan Amount--
	 * @author Kevin (01.03.2019)
	 * @return BigDecimal = total
	 * */
	@Override
	public BigDecimal getTotalLoanAmountByApp(String appno) {
		BigDecimal total = BigDecimal.ZERO;
		try {
			if(appno != null){
				params.put("appno", appno);
				Object obj = (Object) dbService.execSQLQueryTransformer("SELECT ISNULL(SUM(ISNULL(faceamt, 0)), 0) FROM TBACCOUNTINFO WHERE applno=:appno", params, null, 0);
				if(obj != null){
					total = new BigDecimal(String.valueOf(obj));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}
}
