package com.etel.ci;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;

import com.coopdb.data.Tbbireportmain;
import com.coopdb.data.Tbcireportmain;
import com.coopdb.data.Tbcirequest;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbdeskciactivity;
import com.coopdb.data.Tbevalbi;
import com.coopdb.data.TbevalbiId;
import com.coopdb.data.Tbevalci;
import com.coopdb.data.TbevalciId;
import com.coopdb.data.Tbevalreport;
import com.coopdb.data.Tbinvestigationinst;
import com.etel.bureauinvestigation.forms.BIEvalForm;
import com.etel.ci.forms.CIAppDetails;
import com.etel.ci.forms.CIEvalForm;
import com.etel.ci.forms.CIFRecord;
import com.etel.ci.forms.CreditInvestigator;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.defaultusers.forms.DefaultUsers;
import com.etel.security.forms.TBRoleForm;
import com.etel.utils.ApplicationNoGenerator;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class CreditInvestigationServiceImpl implements CreditInvestigationService {
	public DBService dbservice = new DBServiceImpl();
	protected SecurityService securityService = (SecurityService) RuntimeAccess.getInstance()
			.getServiceBean("securityService");
	private String username = securityService.getUserName();

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcirequest> getListofCiRequest(String appno, String lastname, String firstname, String middlename,
			String corporatename, String customertype, String cirequestid, String cifno, String requeststatus,
			Integer page, Integer maxResult, String assigneduser, Boolean viewflag) {
		List<Tbcirequest> req = new ArrayList<Tbcirequest>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			assigneduser = securityService.getUserName();
			StringBuilder hql = new StringBuilder();
			if (appno == null && lastname == null && firstname == null && middlename == null && corporatename == null
					&& customertype == null && cirequestid == null && cifno == null && requeststatus == null) {
				System.out.println(">>>ALL FIELDS NULL : Search CI Request<<<");
				return req;
			} else {
				hql.append(
						"SELECT (SELECT desc1 FROM Tbcodetable WHERE codename='REQUESTSTATUS' AND codevalue=status) as status, assignedby, * FROM Tbcirequest WHERE ");
				if ((customertype != null
						&& (customertype.equals("1") || customertype.equals("2") || customertype.equals("3")))
						&& (viewflag == null || viewflag == false)) {
					hql.append(" customertype='" + customertype + "'");
				} else {
					hql.append(" customertype IN ('1','2','3')");
				}
				if (viewflag == null || viewflag == false) {
					hql.append(" AND appno IS NULL");
				} else {
					if (cifno == null) {
						if (appno != null) {
							hql.append(" AND appno like '%" + appno + "%'");
						}
						if (assigneduser != null) {
							hql.append(" AND ((requestedby='" + assigneduser + "' AND status='0') OR ((assignedci='"
									+ assigneduser + "') AND status IN ('0','1','2','6')) OR (assignedcisupervisor='"
									+ assigneduser + "' AND status NOT IN ('5')))");// Completed
						}
					}
				}
				if (lastname != null) {
					hql.append(" AND lastname like '%" + lastname + "%'");
				}
				if (firstname != null) {
					hql.append(" AND firstname like '%" + firstname + "%'");
				}
				if (middlename != null) {
					hql.append(" AND middlename like '%" + middlename + "%'");
				}
				if (corporatename != null) {
					hql.append(" AND customername like '%" + corporatename + "%'");
				}
				if (cifno != null) {
					hql.append(" AND cifno like '%" + cifno + "%'");
				}
				if (cirequestid != null) {
					hql.append(" AND cirequestid like '%" + cirequestid + "%'");
				}
				if (requeststatus != null) {
					hql.append(" AND status = '" + requeststatus + "'");
				}
			}
			req = (List<Tbcirequest>) dbservice.execSQLQueryTransformerListPagination(hql.toString(), null,
					Tbcirequest.class, page, maxResult);
			if (req != null && !req.isEmpty()) {
				for (Tbcirequest r : req) {
					if (r.getStatus() != null) {
						params.put("reqStatus", r.getStatus());
						Tbcodetable reqStatus = (Tbcodetable) dbservice.executeUniqueHQLQuery(
								"FROM Tbcodetable a WHERE id.codename ='REQUESTSTATUS' AND id.codevalue =:reqStatus",
								params);
						if (reqStatus != null) {
							r.setStatus(reqStatus.getDesc1());
						}
					}
					if (r.getAssignedci() != null) {
						r.setAssignedci(UserUtil.getUserFullname(r.getAssignedci()));
					}
					if (r.getAssignedcisupervisor() != null) {
						r.setAssignedcisupervisor(UserUtil.getUserFullname(r.getAssignedcisupervisor()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AuditLog.addAuditLog(
				AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("VIEW ALL CREDIT INVESTIGATION REQUEST",
						AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET)),
				"User " + username + " Viewed All Credit Invesitigation Request.", username, new Date(),
				AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET);
		return req;
	}

	@Override
	public int getCiRequestTotal(String appno, String lastname, String firstname, String middlename,
			String corporatename, String customertype, String cirequestid, String cifno, String requeststatus,
			String assigneduser, Boolean viewflag) {
		Integer total = 0;
		try {
			StringBuilder hql = new StringBuilder();
			if (appno == null && lastname == null && firstname == null && middlename == null && corporatename == null
					&& customertype == null && cirequestid == null && cifno == null && requeststatus == null) {
				return 0;
			} else {
				assigneduser = securityService.getUserName();
				hql.append("SELECT count(*) FROM Tbcirequest WHERE ");
				if ((customertype != null
						&& (customertype.equals("1") || customertype.equals("2") || customertype.equals("3")))
						&& (viewflag == null || viewflag == false)) {
					hql.append(" customertype='" + customertype + "'");
				} else {
					hql.append(" customertype IN ('1','2','3')");
				}
				if (viewflag == null || viewflag == false) {
					hql.append(" AND appno IS NULL");
				} else {
					if (cifno == null) {
						if (appno != null) {
							hql.append(" AND appno like '%" + appno + "%'");
						}
						if (assigneduser != null) {
							hql.append(" AND ((requestedby='" + assigneduser + "' AND status='0') OR ((assignedci='"
									+ assigneduser + "') AND status IN ('0','1','2','6')) OR (assignedcisupervisor='"
									+ assigneduser + "' AND status NOT IN ('5')))");// Completed
						}
					}
				}
				if (lastname != null) {
					hql.append(" AND lastname like '%" + lastname + "%'");
				}
				if (firstname != null) {
					hql.append(" AND firstname like '%" + firstname + "%'");
				}
				if (middlename != null) {
					hql.append(" AND middlename like '%" + middlename + "%'");
				}
				if (corporatename != null) {
					hql.append(" AND customername like '%" + corporatename + "%'");
				}
				if (cifno != null) {
					hql.append(" AND cifno like '%" + cifno + "%'");
				}
				if (cirequestid != null) {
					hql.append(" AND cirequestid like '%" + cirequestid + "%'");
				}
				if (requeststatus != null) {
					hql.append(" AND status = '" + requeststatus + "'");
				}
			}
			total = (Integer) dbservice.executeUniqueSQLQuery(hql.toString(), null);
			if (total == null) {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@Override
	public String saveUpdateCIRequest(Tbcirequest request) {
		String flag = null;
		Map<String, Object> params = HQLUtil.getMap();
		Tbcirequest r = new Tbcirequest();
		try {
			if (request.getCirequestid() != null) {
				params.put("cirequestid", request.getCirequestid());
				r = (Tbcirequest) dbservice.executeUniqueHQLQuery("FROM Tbcirequest WHERE cirequestid=:cirequestid",
						params);
				if (r != null) {
					r.setCitype(request.getCitype());
					r.setAssignedci(request.getAssignedci());
					r.setRemarks(request.getRemarks());
					r.setPurposeforci(request.getPurposeforci());

					if (r.getCustomertype().equals("1")) {
						// individual
						if (r.getCitype().equals("1")) {
							r.setIspdrn(request.getIspdrn());
							r.setIsevr(request.getIsevr());
							r.setIsbvr(request.getIsbvr());
							r.setIstradecheck(request.getIstradecheck());
							r.setIsbankcheck(request.getIsbankcheck());
							r.setIscreditcheck(request.getIscreditcheck());
						} else {
							r.setIspdrn(request.getIspdrn());
							r.setIsevr(request.getIsevr());
							r.setIsbvr(request.getIsbvr());
							r.setIstradecheck(request.getIstradecheck());
							r.setIsbankcheck(request.getIsbankcheck());
							r.setIscreditcheck(request.getIscreditcheck());
						}
					} else {
						// corporate
						if (r.getCitype().equals("1")) {
							r.setIsbvr(request.getIsbvr());
//							r.setIstradecheck(request.getIstradecheck());
							r.setIsbankcheck(request.getIsbankcheck());
							r.setIscreditcheck(request.getIscreditcheck());
							r.setIstradecheck(request.getIstradecheck());
							r.setIsbankcheck(request.getIsbankcheck());
							r.setIscreditcheck(request.getIscreditcheck());
						} else {
							r.setIsbvr(request.getIsbvr());
							r.setIstradecheck(request.getIstradecheck());
							r.setIsbankcheck(request.getIsbankcheck());
							r.setIscreditcheck(request.getIscreditcheck());
						}
					}
					// check at least one has true value to conduct
					Boolean[] hasToConduct = { r.getIspdrn(), r.getIsevr(), r.getIsbvr(), r.getIsbankcheck(),
							r.getIstradecheck(), r.getIscreditcheck() };

					if (!Arrays.asList(hasToConduct).contains(true)) {
						return "Please select at least one to conduct for CI";
					}

					// sets the company code, supervisor
					if (r.getAppno() == null) {
						// outside application
						// user will manually select the company
						if (request.getCompanycode() != null) {
							r.setCompanycode(request.getCompanycode());
							params.put("company", request.getCompanycode());
							String supervisor = (String) dbservice.executeUniqueSQLQuery(
									"SELECT cisupervisor FROM Tbdefaultusers WHERE companycode=:company", params);

							if (supervisor != null) {
								r.setAssignedcisupervisor(supervisor);
							} else {
								return "Failed updating request, no default CI supervisor found in company with companycode: "
										+ request.getCompanycode() + " please contact system admin.";
							}
						} else {
							return "Failed updating request. company cannot be empty.";
						}
					}

					if (dbservice.saveOrUpdate(r)) {
						AuditLog.addAuditLog(
								AuditLogEvents
										.getAuditLogEvents(AuditLogEvents.getEventID("ASSIGN AND SUBMIT CI REQUEST",
												AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
								"User " + username + " Assigned and Submitted " + r.getAppno() + "'s CI Request.",
								username, new Date(), AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
						flag = r.getCirequestid();
					} else {
						flag = "failed";
					}
				} else {
					return "Failed retrieving CI Request.";
				}
			} else {
				CIFRecord c = getCIFRecord(request.getCifno());
				if (request.getAppno() != null && !request.getAppno().equals("---")) {
					r.setAppno(request.getAppno());

					// inside application
					// automatically populate by application's company code and it's supervisor in
					// @Tbdefaultuser
					// get from CIF records
					if (c != null) {
						r.setCifno(c.getCifno());
						r.setCustomername(c.getCustomername());
						r.setCustomertype(c.getCustomertype());
						r.setFirstname(c.getFirstname());
						r.setMiddlename(c.getMiddlename());
						r.setLastname(c.getLastname());
						r.setSuffix(c.getSuffix());
						r.setDateofbirth(c.getDateofbirth());
						r.setDateofincorporation(c.getDateofincorporation());
					} else {
						return "Failed creating request. CIF record with CIF number: " + request.getCifno();
					}
					params.put("appno", request.getAppno());
					String company = (String) dbservice
							.executeUniqueSQLQuery("SELECT coopcode FROM Tblstapp WHERE appno=:appno", params);
					if (company != null) {
						r.setCompanycode(company);
						params.put("company", company);
						String cisupervisor = (String) dbservice.executeUniqueSQLQuery(
								"SELECT cisupervisor FROM Tbdefaultusers WHERE companycode=:company", params);

						if (cisupervisor != null) {
							r.setAssignedcisupervisor(cisupervisor);
						} else {
							return "Failed creating request, no default CI supervisor found in company with companycode: "
									+ request.getCompanycode() + " " + "please contact system admin.";
						}
					} else {
						return "Failed creating request, no company found in application with appno: "
								+ request.getAppno() + " " + "please contact system admin.";
					}
					r.setParticipationcode(request.getParticipationcode());
					r.setRequestedby(securityService.getUserName());
					r.setDaterequested(new Date());
				} else {
					// outside application
					// user will manually select the company
					// get CIF records by user's search cif
					if (c != null) {
						// only approved CIF record is valid
						if (!c.getCifstatus().equals("3")) {
							return "Please select <b>APPROVED</b> CIF record only.";
						}
						r.setCifno(c.getCifno());
						r.setCustomername(c.getCustomername());
						r.setCustomertype(c.getCustomertype());
						r.setFirstname(c.getFirstname());
						r.setMiddlename(c.getMiddlename());
						r.setLastname(c.getLastname());
						r.setSuffix(c.getSuffix());
						r.setDateofbirth(c.getDateofbirth());
						r.setDateofincorporation(c.getDateofincorporation());
					} else {
						return "Failed creating request. CIF record with CIF no: " + request.getCifno();
					}
					if (request.getCompanycode() != null) {
						r.setCompanycode(request.getCompanycode());
						params.put("company", request.getCompanycode());
						String supervisor = (String) dbservice.executeUniqueSQLQuery(
								"SELECT cisupervisor FROM Tbdefaultusers WHERE companycode=:company", params);

						if (supervisor != null) {
							r.setAssignedcisupervisor(supervisor);
						} else {
							return "Failed creating request, no default CI supervisor found in company with companycode: "
									+ request.getCompanycode() + " please contact system admin.";
						}
					} else {
						return "Failed creating request. company cannot be empty.";
					}

					r.setRequestedby(securityService.getUserName());
					r.setDaterequested(new Date());
				}
				// set request id, generate request id
				r.setCirequestid(ApplicationNoGenerator.generateRequestID("CI"));

				// set other details
				r.setCitype(request.getCitype());
				r.setAssignedci(request.getAssignedci());
				r.setAssigneddate(new Date());
				r.setRemarks(request.getRemarks());
				r.setStatusdate(new Date());
				r.setPurposeforci(request.getPurposeforci());

				if (r.getCustomertype().equals("1")) {
					// individual
					if (r.getCitype().equals("1")) {
						r.setIspdrn(request.getIspdrn());
						r.setIsevr(request.getIsevr());
						r.setIsbvr(request.getIsbvr());
//						r.setIstradecheck(request.getIstradecheck());
						r.setIsbankcheck(request.getIsbankcheck());
						r.setIscreditcheck(request.getIscreditcheck());
					} else {
						r.setIspdrn(request.getIspdrn());
						r.setIsevr(request.getIsevr());
						r.setIsbvr(request.getIsbvr());
						r.setIsbankcheck(request.getIsbankcheck());
						r.setIscreditcheck(request.getIscreditcheck());
					}
				} else {
					// corporate
					if (r.getCitype().equals("1")) {
						r.setIsbvr(request.getIsbvr());
//						r.setIstradecheck(request.getIstradecheck());
						r.setIsbankcheck(request.getIsbankcheck());
						r.setIscreditcheck(request.getIscreditcheck());
						r.setIsbankcheck(request.getIsbankcheck());
						r.setIscreditcheck(request.getIscreditcheck());
					} else {
						r.setIsbvr(request.getIsbvr());
						r.setIsbankcheck(request.getIsbankcheck());
						r.setIscreditcheck(request.getIscreditcheck());
					}
				}

				// new
				r.setStatus("0");

				// check at least one has true value to conduct CI
				Boolean[] hasToConduct = { request.getIspdrn(), request.getIsevr(), request.getIsbvr(),
						request.getIsbankcheck(), request.getIstradecheck(), request.getIscreditcheck() };

				if (!Arrays.asList(hasToConduct).contains(true)) {
					return "Please select at least one to conduct for CI";
				}

				if (dbservice.save(r)) {
					AuditLog.addAuditLog(
							AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("CREATE CI REQUEST",
									AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
							"User " + username + " Created " + r.getAppno() + "a CI Request.", username, new Date(),
							AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
					flag = r.getCirequestid();
					params.put("appno", r.getAppno());
					params.put("cifno", r.getCifno());

//					params.put("invsttype", "CI");
//					dbservice.executeUpdate("UPDATE Tbinvestigationinst SET status='On-Process' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype", params);
				} else {
					flag = "failed";
				}
			}
			if (r != null && r.getAssignedci() != null) {
				// Update CI Report Assigned CI - Kevin 10.29.2018
				/*
				 * REPORTSTATUS: 0 New 1 On-going 2 For Review 3 Reviewed 4 Returned 5 Cancelled
				 */

				params.put("cirequestid", flag);
				dbservice.executeUpdate("Update Tbcireportmain SET reportedby='" + r.getAssignedci()
						+ "' WHERE cirequestid=:cirequestid AND status IN ('0','1','4')", params);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tbcirequest getCIRequest(String requestID) {
		Map<String, Object> params = HQLUtil.getMap();
		params.put("requestID", requestID);
		Tbcirequest r = new Tbcirequest();
		try {
			if (requestID != null) {
				r = (Tbcirequest) dbservice.executeUniqueHQLQuery("FROM Tbcirequest WHERE cirequestid=:requestID",
						params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}

	@Override
	public CIFRecord getCIFRecord(String cifno) {
		Map<String, Object> params = HQLUtil.getMap();
		CIFRecord record = null;
		try {
			if (cifno != null) {
				params.put("cifno", cifno);
				if (cifno.matches("-?\\d+(\\.\\d+)?")) {
					record = (CIFRecord) dbservice.execSQLQueryTransformer(""
							+ "SELECT CAST(m.id as varchar(25)) as cifno, '2' as customertype, m.businessname as customername, m.incorporationdate as dateofincorporation, "
							+ "m.incorporationdate as dateofbirth, '1' as cifstatus " + "FROM Tbmemberbusiness m "
							+ "WHERE m.id=:cifno", params, CIFRecord.class, 0);
				} else {
					record = (CIFRecord) dbservice.execSQLQueryTransformer(""
							+ "SELECT m.membershipid as cifno, '1' as customertype, m.membername as customername, m.membershipdate as dateofincorporation, "
							+ "m.dateofbirth, m.membershipstatus as cifstatus, m.firstname, m.middlename, m.lastname, m.suffix "
							+ "FROM Tbmember m " + "WHERE m.membershipid=:cifno", params, CIFRecord.class, 0);
				}
				if (record != null) {
					return record;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return record;
	}

	@Override
	public String submitCIRequest(String requestID, String status) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (requestID != null) {
				params.put("id", requestID);
				Tbcirequest r = (Tbcirequest) dbservice.executeUniqueHQLQuery("FROM Tbcirequest WHERE cirequestid=:id",
						params);
				r.setStatusdate(new Date());
				if (status == null) {
					// New || Report Opened || Report On-going
					if (r.getStatus().equals("0") || r.getStatus().equals("1") || r.getStatus().equals("2")) {

						// re-check conduct fields
						Boolean[] hasToConduct = { r.getIspdrn(), r.getIsevr(), r.getIsbvr(), r.getIsbankcheck(),
								r.getIstradecheck(), r.getIscreditcheck() };

						if (!Arrays.asList(hasToConduct).contains(true)) {
							return "Problem submitting request. No item to conduct.";
						}

						if (r.getAssignedci() == null) {
							// check user currently login roles
							List<TBRoleForm> u = UserUtil.getUserRolesByUsername(securityService.getUserName());
							boolean t = false;
							for (TBRoleForm a : u) {
								// if user has supervisor role
								if (a.getRoleid().equals("CI_SUPERVISOR")) {
									t = true;
								}
							}
							// if the user is the default supervisor and currently log in.
							if (t && isCISupervisorByCompanycode(r.getCompanycode())) {
								return "Problem submitting request, no CI is assigned.";
							} else {
								r.setStatus("0");
							}
						} else {
							r.setStatus("0");
						}
						r.setStatusdate(new Date());
					} else {
						r.setStatus(status);
						r.setStatusdate(new Date());
					}
					if (dbservice.saveOrUpdate(r)) {
						return "success";
					}
				} else {
					if (status.equals("7")) {
						// Cancelled
						params.put("id", requestID);
						// if(status.equals("4")){
						dbservice.executeUpdate(
								"UPDATE Tbcireportmain SET status='5', statusdatetime=GETDATE() WHERE cirequestid=:id AND status IN('0','1')",
								params);
						// }
					}
					r.setStatus(status);
					r.setStatusdate(new Date());

					if (dbservice.saveOrUpdate(r)) {
						return "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Problem submitting request.";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CreditInvestigator> listCI() {
		List<CreditInvestigator> ci = new ArrayList<CreditInvestigator>();
		try {
			ci = (List<CreditInvestigator>) dbservice.execSQLQueryTransformer("SELECT u.username, u.fullname "
					+ "FROM Tbuser u LEFT JOIN Tbuserroles r on u.username = r.username "
					+ "WHERE  r.roleid ='CI_USER'", null, CreditInvestigator.class, 1);
			if (ci != null) {
				return ci;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ci;
	}

	@Override
	public boolean isCISupervisorByCompanycode(String c) {
		String supervisor = null;
		try {
			supervisor = new DefaultUsers(c).getCisupervisor();
			if (supervisor != null && supervisor.equals(securityService.getUserName())) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	@Override
	public CIAppDetails getAppdetailByAppno(String appno) {
		CIAppDetails d = new CIAppDetails(appno);
		return d;
	}

	@Override
	public String getCIFNoByReportID(String rptID) {
		String str = null;
		try {
			if (rptID != null) {
				Map<String, Object> params = HQLUtil.getMap();
				params.put("cireportid", rptID);
				str = (String) dbservice
						.executeUniqueSQLQuery("SELECT cifno FROM Tbcireportmain WHERE cireportid=:cireportid", params);

				if (str != null) {
					return str;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CIEvalForm> getAllCIReportperAppNo(String appno, Integer evalreportid) {// CED.JAN.25.2019
		Map<String, Object> params = HQLUtil.getMap();
		List<CIEvalForm> evalForm = new ArrayList<CIEvalForm>();
		try {
			params.put("appno", appno);
			params.put("evalreportid", evalreportid);
			List<Tbevalci> evalciList = (List<Tbevalci>) dbservice
					.executeListHQLQuery("FROM Tbevalci WHERE appno=:appno AND evalreportid=:evalreportid", params);
			if (evalciList == null || evalciList.size() == 0) {
				System.out.println(" - - - - NO EVAL REPORT FORM TBEVALBI - - - ");
				Tbevalreport ereport = (Tbevalreport) dbservice.executeUniqueHQLQuery(
						"FROM Tbevalreport WHERE appno=:appno AND evalreportid=:evalreportid", params);
				if (ereport != null) {
					List<Tbinvestigationinst> invList = new ArrayList<Tbinvestigationinst>();
					invList = (List<Tbinvestigationinst>) dbservice.executeListHQLQuery(
							"FROM Tbinvestigationinst WHERE appno=:appno AND investigationtype='CI' AND status='3'",
							params);// Completed
					for (Tbinvestigationinst inv : invList) {
						params.put("cifno", inv.getId().getCifno());
						Tbevalci evalci = new Tbevalci();
						evalci = (Tbevalci) dbservice.execStoredProc("SELECT " + "customername as subjectname,appno, "
								+ "pdrn.cireportid as pdrncireportid, pdrn.overallfindings as pdrn, "
								+ "bvr.cireportid as bvrcireportid,bvr.overallfindings as bvr, "
								+ "evr.cireportid as evrcireportid,evr.overallfindings as evr, "
								+ "bnk.cireportid as bankcheckcireportid,bnk.overallfindings as bankcheck, "
								+ "credit.cireportid as creditcheckcireportid, credit.overallfindings as creditcheck, "
								+ "customertype,initiatedby as createdby,datecreated " + "FROM TBINVESTIGATIONINST inv "
								+ "LEFT JOIN (SELECT TOP 1 ciactivity,overallfindings,cireportid,cifno FROM TBDESKCIACTIVITY "
								+ "WHERE cifno =:cifno and ciactivity = 'BANK' ORDER BY id DESC) bnk on inv.cifno = bnk.cifno "
								+ "LEFT JOIN (SELECT TOP 1 ciactivity,overallfindings,cireportid,cifno FROM TBDESKCIACTIVITY "
								+ "WHERE cifno =:cifno and ciactivity = 'BVR' ORDER BY id DESC) bvr on inv.cifno = bvr.cifno "
								+ "LEFT JOIN (SELECT TOP 1 ciactivity,overallfindings,cireportid,cifno FROM TBDESKCIACTIVITY "
								+ "WHERE cifno =:cifno and ciactivity = 'EVR' ORDER BY id DESC) evr on inv.cifno = evr.cifno "
								+ "LEFT JOIN (SELECT TOP 1 ciactivity,overallfindings,cireportid,cifno FROM TBDESKCIACTIVITY "
								+ "WHERE cifno =:cifno and ciactivity = 'PDRN' ORDER BY id DESC) pdrn on inv.cifno = pdrn.cifno "
								+ "LEFT JOIN (SELECT TOP 1 ciactivity,overallfindings,cireportid,cifno FROM TBDESKCIACTIVITY "
								+ "WHERE cifno =:cifno and ciactivity = 'CREDIT' ORDER BY id DESC) credit on inv.cifno = credit.cifno "
								+ "WHERE inv.appno=:appno AND inv.investigationtype = 'CI' and inv.status ='3' and inv.cifno =:cifno",
								params, Tbevalci.class, 0, null);
						TbevalciId evalciid = new TbevalciId();
						evalciid.setCifno(inv.getId().getCifno());
						evalciid.setEvalreportid(evalreportid);
						evalci.setId(evalciid);
						dbservice.save(evalci);
					}
				}
			}
			evalForm = (List<CIEvalForm>) dbservice.execStoredProc(
					"SELECT subjectname,participationcode,instruction,'' as overallremarks, "
							+ "rep.appno as appno,rep.cifno as cifno,pdrncireportid,pdrn,bvrcireportid,bvr,evrcireportid,evr,creditcheckcireportid,"
							+ "creditcheck,bankcheckcireportid,bankcheck,inv.customertype as customertype,initiatedby as createdby,rep.datecreated as datecreated "
							+ "FROM TBEVALCI rep "
							+ "LEFT JOIN TBINVESTIGATIONINST inv on rep.appno = inv.appno and rep.cifno = inv.cifno "
							+ "WHERE rep.appno=:appno AND investigationtype='CI' AND status='3' ",
					params, CIEvalForm.class, 1, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return evalForm;
	}
}
