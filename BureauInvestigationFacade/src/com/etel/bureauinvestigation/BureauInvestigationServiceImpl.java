package com.etel.bureauinvestigation;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//import com.cifsdb.data.Tbcifcorporate;
//import com.cifsdb.data.Tbcifindividual;
import com.coopdb.data.Tbbapnfis;
import com.coopdb.data.Tbbiactivity;
import com.coopdb.data.TbbiactivityId;
import com.coopdb.data.Tbbicic;
import com.coopdb.data.Tbbicmap;
import com.coopdb.data.Tbbireportmain;
import com.coopdb.data.Tbbirequest;
import com.coopdb.data.Tbevalreport;
import com.coopdb.data.Tbinvestigationinst;
import com.coopdb.data.Tbmember;
import com.etel.bureauinvestigation.forms.AMLAWatchlistForm;
import com.etel.bureauinvestigation.forms.BAPListForm;
import com.etel.bureauinvestigation.forms.BIAccessRightsForm;
import com.etel.bureauinvestigation.forms.BIEvalForm;
import com.etel.bureauinvestigation.forms.BiActivityForm;
import com.etel.bureauinvestigation.forms.BlacklistForm;
import com.etel.bureauinvestigation.forms.CIFRecordForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.defaultusers.forms.DefaultUsers;
import com.etel.forms.ReturnForm;
import com.etel.utils.ApplicationNoGenerator;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.etel.utils.LoggerUtil;
import com.etel.utils.UserUtil;
import com.coopdb.data.Tbevalbi;
import com.coopdb.data.TbevalbiId;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * Bureau Investigation Module
 * 
 * @author Kevin Dec.05,2017
 */
public class BureauInvestigationServiceImpl implements BureauInvestigationService {

	private DBService dbService = new DBServiceImpl();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private String username = secservice.getUserName();

	/**
	 * --BI Request Inquiry--
	 * 
	 * @author Kevin
	 * @return List <{@link Tbbirequest}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbbirequest> getListofBiRequest(String appno, String lastname, String firstname, String middlename,
			String corporatename, String customertype, String birequestid, String cifno, String requeststatus,
			Integer page, Integer maxResult, String assigneduser, Boolean viewflag) {
		List<Tbbirequest> req = new ArrayList<Tbbirequest>();
		assigneduser = UserUtil.securityService.getUserName();
		try {
			StringBuilder hql = new StringBuilder();
//			 System.out.println("appno: " +appno + ",\n lastname: " + lastname
//			 + ",\n firstname: " + firstname + ",\n middlename: " + middlename
//			 + ",\n corporatename: " + corporatename + ",\n customertype: " +
//			 customertype + ",\n birequestid: " + birequestid + ",\n cifno: "
//			 + cifno
//			 + ",\n requeststatus: " + requeststatus+ ",\n viewflag: " +
//			 viewflag+ ",\n assigneduser: " + assigneduser);

			if (appno == null && lastname == null && firstname == null && middlename == null && corporatename == null
					&& customertype == null && birequestid == null && cifno == null && requeststatus == null) {
				System.out.println(">>>ALL FIELDS NULL : Search BI Request<<<");
				return req;
			} else {

				hql.append(
						"SELECT (SELECT desc1 FROM Tbcodetable WHERE codename='REQUESTSTATUS' AND codevalue=status) as status, assignedby, * FROM Tbbirequest WHERE ");
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
							hql.append(" AND ((requestedby='" + assigneduser + "' AND status='0') OR ((assignedbi='"
									+ assigneduser + "') AND status IN ('0','1','2','6')) OR (assignedbisupervisor='"
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
				if (birequestid != null) {
					hql.append(" AND birequestid like '%" + birequestid + "%'");
				}
				if (requeststatus != null) {
					hql.append(" AND status = '" + requeststatus + "'");
				}
			}
			req = (List<Tbbirequest>) dbService.execSQLQueryTransformerListPagination(hql.toString(), null,
					Tbbirequest.class, page, maxResult);
			if (req != null && !req.isEmpty()) {
				for (Tbbirequest r : req) {
					if (r.getAssignedbi() != null) {
						r.setAssignedbi(UserUtil.getUserFullname(r.getAssignedbi()));
					}
					if (r.getAssignedbisupervisor() != null) {
						r.setAssignedbisupervisor(UserUtil.getUserFullname(r.getAssignedbisupervisor()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		AuditLog.addAuditLog(
				AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("VIEW ALL BUREAU INVESTIGATION REQUEST",
						AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET)),
				"User " + username + " Viewed All Bureau Investigation Request.", username, new Date(),
				AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET);
		return req;
	}

	/**
	 * --BI Request Inquiry Total Result--
	 * 
	 * @author Kevin
	 * @return count
	 */
	@Override
	public int getBiRequestTotal(String appno, String lastname, String firstname, String middlename,
			String corporatename, String customertype, String birequestid, String cifno, String requeststatus,
			String assigneduser, Boolean viewflag) {
		Integer total = 0;
		assigneduser = UserUtil.securityService.getUserName();
		try {
			StringBuilder hql = new StringBuilder();
			if (appno == null && lastname == null && firstname == null && middlename == null && corporatename == null
					&& customertype == null && birequestid == null && cifno == null && requeststatus == null) {
				return 0;
			} else {
				hql.append("SELECT count(*) FROM Tbbirequest WHERE ");
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
							hql.append(" AND ((requestedby='" + assigneduser + "' AND status='0') OR ((assignedbi='"
									+ assigneduser + "') AND status IN ('0','1','2','6')) OR (assignedbisupervisor='"
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
				if (birequestid != null) {
					hql.append(" AND birequestid like '%" + birequestid + "%'");
				}
				if (requeststatus != null) {
					hql.append(" AND status = '" + requeststatus + "'");
				}
			}
			total = (Integer) dbService.executeUniqueSQLQuery(hql.toString(), null);
			if (total == null) {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	/**
	 * --Save or Update BI Request--
	 * 
	 * @author Kevin
	 * @return String = success, otherwise failed
	 */
	@Override
	public String saveOrUpdateBIRequest(Tbbirequest request, String status) {
		String flagOrRequestId = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		Tbbirequest r = null;
		try {
			if (status == null) {
				System.out.println(">>>>> Missing BI request status...");
				return flagOrRequestId;
			}
			if (request.getBirequestid() != null) {
				params.put("birequestid", request.getBirequestid());
				r = (Tbbirequest) dbService.executeUniqueHQLQuery("FROM Tbbirequest WHERE birequestid=:birequestid",
						params);
				if (r != null) {
					r.setIsbaprequired(request.getIsbaprequired());
					r.setIscmaprequired(request.getIscmaprequired());
					r.setIscicrequired(request.getIscicrequired());
					r.setIsblacklistrequired(request.getIsblacklistrequired());
					r.setIsamlawatchlistrequired(request.getIsamlawatchlistrequired());// daniel
																						// sept.14.2018
					r.setAssignedbi(request.getAssignedbi());
					r.setAssignedby(UserUtil.securityService.getUserName());
					r.setCompanycode(request.getCompanycode());
					r.setRemarks(request.getRemarks());
					r.setPurposeforbi(request.getPurposeforbi());

					// Set Assigned BI
					if (r.getAssignedbi() == null) {
						if (request.getAssignedbi() != null) {
							r.setAssignedbi(request.getAssignedbi());
							r.setAssigneddate(new Date());
							r.setAssignedby(UserUtil.securityService.getUserName());
						}
					} else if (r.getAssignedbi() != null && !r.getAssignedbi().equals(request.getAssignedbi())) {
						r.setAssignedbi(request.getAssignedbi());
						r.setAssigneddate(new Date());
						r.setAssignedby(UserUtil.securityService.getUserName());
					}

					// Set assign supervisor
					if (request.getCompanycode() != null) {
						DefaultUsers d = new DefaultUsers(request.getCompanycode());
						if (r.getAssignedbisupervisor() != null
								&& r.getAssignedbisupervisor().equals(d.getBisupervisor())) {
							r.setAssignedbisupervisor(d.getBisupervisor());
						}
					}

					// CODETABLE 'REQUESTSTATUS' 0 = New
					r.setStatus(status);

					// Retrieve CIF record
					CIFRecordForm c = getCIFRecord(request.getCifno());
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
						System.out.println(">>>>>Problem retrieving CIF Record...");
						return flagOrRequestId;
					}
					if (dbService.saveOrUpdate(r)) {
						AuditLog.addAuditLog(
								AuditLogEvents
										.getAuditLogEvents(AuditLogEvents.getEventID("ASSIGN AND SUBMIT BI REQUEST",
												AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
								"User " + username + " Assigned and Submitted " + r.getAppno() + "'s BI Request.",
								username, new Date(), AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
						flagOrRequestId = r.getBirequestid();
					}
				} else {
					r = new Tbbirequest();
					// Set birequestid, generate request id
					r.setBirequestid(ApplicationNoGenerator.generateRequestID("BI"));

					// Set other details
					r.setAppno(request.getAppno());
					if (request.getAssignedbi() != null) {
						r.setAssignedbi(request.getAssignedbi());
						r.setAssigneddate(new Date());
						r.setAssignedby(UserUtil.securityService.getUserName());
					}
					r.setIsamlawatchlistrequired(request.getIsamlawatchlistrequired());// daniel.sept.14.2018
					r.setIsbaprequired(request.getIsbaprequired());
					r.setIscmaprequired(request.getIscmaprequired());
					r.setIscicrequired(request.getIscicrequired());
					r.setIsblacklistrequired(request.getIsblacklistrequired());
					r.setCompanycode(request.getCompanycode());
					r.setRemarks(request.getRemarks());
					r.setPurposeforbi(request.getPurposeforbi());
					r.setStatusdate(new Date());
					r.setDaterequested(new Date());
					r.setRequestedby(UserUtil.securityService.getUserName());
					r.setParticipationcode(request.getParticipationcode());

					// Set assign supervisor
					if (request.getCompanycode() != null) {
						DefaultUsers d = new DefaultUsers(request.getCompanycode());
						r.setAssignedbisupervisor(d.getBisupervisor());
					}

					// CODETABLE 'REQUESTSTATUS' 0 = New
					r.setStatus(status);

					// Get from CIF records
					CIFRecordForm c = getCIFRecord(request.getCifno());
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
						System.out.println(">>>>>Problem retrieving CIF Record...");
						return flagOrRequestId;
					}
					if (dbService.save(r)) {
						flagOrRequestId = r.getBirequestid();
					}
				}
			} else {
				r = new Tbbirequest();
				// Set birequestid, generate request id
				r.setBirequestid(ApplicationNoGenerator.generateRequestID("BI"));

				// Set other details
				r.setAppno(request.getAppno());
				if (request.getAssignedbi() != null) {
					r.setAssignedbi(request.getAssignedbi());
					r.setAssigneddate(new Date());
					r.setAssignedby(UserUtil.securityService.getUserName());
				}
				r.setIsamlawatchlistrequired(request.getIsamlawatchlistrequired());// daniel.sept.14.2018
				r.setIsbaprequired(request.getIsbaprequired());
				r.setIscmaprequired(request.getIscmaprequired());
				r.setIscicrequired(request.getIscicrequired());
				r.setIsblacklistrequired(request.getIsblacklistrequired());
				r.setCompanycode(request.getCompanycode());
				r.setRemarks(request.getRemarks());
				r.setPurposeforbi(request.getPurposeforbi());
				r.setStatusdate(new Date());
				r.setDaterequested(new Date());
				r.setRequestedby(UserUtil.securityService.getUserName());
				r.setParticipationcode(request.getParticipationcode());

				// Set assign supervisor
				if (request.getCompanycode() != null) {
					DefaultUsers d = new DefaultUsers(request.getCompanycode());
					r.setAssignedbisupervisor(d.getBisupervisor());
				}
				// CODETABLE 'REQUESTSTATUS' 0 = New
				r.setStatus(status);

				// Get from CIF records
				CIFRecordForm c = getCIFRecord(request.getCifno());
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
					System.out.println(">>>>>Problem retrieving CIF Record...");
					return flagOrRequestId;
				}
				if (dbService.save(r)) {
					AuditLog.addAuditLog(
							AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("CREATE BI REQUEST",
									AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
							"User " + username + " Created " + r.getAppno() + "a BI Request.", username, new Date(),
							AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
					flagOrRequestId = r.getBirequestid();
				}

				if (!flagOrRequestId.equals("failed")) {
					// Set status to On-Process(Tbinvestigationinst) Inside
					// application
					if (r.getAppno() != null) {
						params.put("appno", r.getAppno());
						params.put("cifno", r.getCifno());
						params.put("invsttype", "BI");
						// On-Process
						dbService.executeUpdate(
								"UPDATE Tbinvestigationinst SET status='2' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype",
								params);

					}
				}
			}

			if (r != null && r.getAssignedbi() != null) {
				// Update BI Report Assigned BI - Kevin 10.29.2018
				/*
				 * REPORTSTATUS: 0 New 1 On-going 2 For Review 3 Reviewed 4 Returned 5 Cancelled
				 */

				params.put("birequestid", flagOrRequestId);
				dbService.executeUpdate("Update Tbbireportmain SET reportedby='" + r.getAssignedbi()
						+ "' WHERE birequestid=:birequestid AND status IN ('0','1','4')", params);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flagOrRequestId;
	}

	/**
	 * --Get BI Request Record--
	 * 
	 * @author Kevin
	 * @return form = {@link Tbbirequest}
	 */
	@Override
	public Tbbirequest getBIRequest(String requestid) {
		Map<String, Object> params = HQLUtil.getMap();
		Tbbirequest req = new Tbbirequest();
		try {
			if (requestid != null) {
				params.put("birequestid", requestid);
				req = (Tbbirequest) dbService.executeUniqueHQLQuery("FROM Tbbirequest WHERE birequestid=:birequestid",
						params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return req;
	}

	/**
	 * --Get CIF Record (CIFSDB)--
	 * 
	 * @author Kevin
	 * @return form = {@link CIFRecordForm}
	 */
	@Override
	public CIFRecordForm getCIFRecord(String cifno) {
		Map<String, Object> params = HQLUtil.getMap();
		CIFRecordForm record = new CIFRecordForm();
		try {
			if (cifno != null) {
				params.put("cifno", cifno);
				if (cifno.matches("-?\\d+(\\.\\d+)?")) {
					record = (CIFRecordForm) dbService.execSQLQueryTransformer(""
							+ "SELECT CAST(m.id as varchar(25)) as cifno, '2' as customertype, m.businessname as customername, m.incorporationdate as dateofincorporation, "
							+ "m.incorporationdate as dateofbirth, '1' as cifstatus " + "FROM Tbmemberbusiness m "
							+ "WHERE m.id=:cifno", params, CIFRecordForm.class, 0);
				} else {
					record = (CIFRecordForm) dbService.execSQLQueryTransformer(""
							+ "SELECT membershipid as cifno, '1' as customertype, membername as customername, membershipdate as dateofincorporation, "
							+ "dateofbirth, firstname, middlename, lastname, ISNULL(suffix,'') as suffix, membershipstatus as cifstatus "
							+ "FROM Tbmember WHERE membershipid =:cifno", params, CIFRecordForm.class, 0);
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

	/**
	 * --Update BI Request Status--
	 * 
	 * @author Kevin
	 * @return String = success, otherwise failed
	 */
	@Override
	public String submitBIRequest(String requestid, String status) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (requestid != null && status != null) {
				params.put("requestid", requestid);
				int res = dbService.executeUpdate("UPDATE Tbbirequest SET status='" + status
						+ "', statusdate=GETDATE() WHERE birequestid=:requestid", params);
				if (res > 0) {
					flag = "success";

					// Cancelled
					if (status.equals("7")) {
						dbService.executeUpdate(
								"UPDATE Tbbireportmain SET status='5', statusdatetime=GETDATE() WHERE birequestid=:requestid AND status IN('0','1','4')",
								params);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Get default BI Supervisor by company--
	 * 
	 * @author Kevin
	 * @param companycode
	 * @return Boolean = true, otherwise false
	 */
	@Override
	public boolean isBISupervisorByCompanycode(String companycode) {
		try {
			if (companycode != null) {
				String supervisor = new DefaultUsers(companycode).getBisupervisor();
				if (supervisor != null && supervisor.equals(UserUtil.securityService.getUserName())) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * --Get BI Request Access Rights--
	 * 
	 * @author Kevin
	 * @param dlgType  = new or update
	 * @param viewflag = false if from outside application, otherwise true
	 * @return form = {@link BIAccessRightsForm}
	 */
	@Override
	public BIAccessRightsForm getBiAccessRights(String requestid, String dlgType, Boolean viewflag) {
		BIAccessRightsForm form = new BIAccessRightsForm();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (requestid != null) {
				params.put("id", requestid);
				Tbbirequest req = (Tbbirequest) dbService
						.executeUniqueHQLQuery("FROM Tbbirequest WHERE birequestid=:id", params);
				if (req != null) {
					if (req.getStatus() != null) {
						String supervisor = new DefaultUsers(req.getCompanycode()).getBisupervisor();
//						// Draft
//						if (req.getStatus().equals("0")) {
//							if (req.getRequestedby() != null
//									&& req.getRequestedby().equals(UserUtil.securityService.getUserName())) {
//								form.setBtnSave(true);
//								form.setReadOnly(false);
//								form.setBtnSubmit(true);
//
//								// Outside Application
//								if (req.getAppno() == null || (viewflag != null && viewflag == false)) {
//									form.setBtnCancel(true);
//									form.setSlcCompany(false);
//								}
//							}
//
//							// If BI Supervisor
//							if (supervisor != null && supervisor.equals(UserUtil.securityService.getUserName())) {
//								form.setBtnSubmit(true);
//								form.setBtnCancel(true);
//								form.setSlcAssignedBi(false);
//							}
//						}
						// New || Report Opened || Report On-going
						if (req.getStatus().equals("0") || req.getStatus().equals("1") || req.getStatus().equals("2")) {
							if (supervisor != null && supervisor.equals(UserUtil.securityService.getUserName())) {
								form.setBtnSubmit(true);
								form.setBtnCancel(true);
								form.setSlcAssignedBi(false);
							}
//							if (req.getAssignedbi() != null
//									&& req.getAssignedbi().equals(UserUtil.securityService.getUserName())) {
//								form.setBtnSubmit(false);
//								form.setBtnCreateReport(true);
//							}
						}
					}
				}
			} else {
				if (dlgType != null && dlgType.equalsIgnoreCase("new")) {
					if (viewflag != null && viewflag == false) {
						form.setBtnSubmit(true);
						form.setSlcCompany(false);
					} else {
						form.setBtnSubmit(true);
						form.setSlcAssignedBi(false);
					}

					form.setReadOnly(false);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/**
	 * --Save or Update BI Report--
	 * 
	 * @author Kevin
	 * @return String = success, otherwise failed
	 */
	@Override
	public String saveOrUpdateBiReport(Tbbireportmain bireportmain, Tbbiactivity bapactivity, Tbbicmap cmapdetails,
			Tbbiactivity cmapactivity, Tbbicic cicdetails, Tbbiactivity cicactivity,
			Tbbiactivity externalblacklistactivity, Tbbiactivity internalblacklistactivity,
			Tbbiactivity externalamalwatchlistactivity, Tbbiactivity internalamlawatchlistactivity) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		Tbbiactivity biact = new Tbbiactivity();
		params.put("bireportid", bireportmain.getBireportid());
		boolean isbaprequired = false;
		boolean iscmaprequired = false;
		boolean iscicrequired = false;
		boolean isblacklistrequired = false;
		boolean isamlarequired = false;
		try {
			Tbbireportmain bir = (Tbbireportmain) dbService
					.executeUniqueHQLQuery("FROM Tbbireportmain WHERE bireportid=:bireportid", params);
			if (bir != null) {
				params.put("birequestid", bir.getBirequestid());
				Tbbirequest birequest = (Tbbirequest) dbService
						.executeUniqueHQLQuery("FROM Tbbirequest WHERE birequestid=:birequestid", params);
				if (birequest != null) {
					if (birequest.getIsbaprequired() != null && birequest.getIsbaprequired()) {
						isbaprequired = true;
					}
					if (birequest.getIscmaprequired() != null && birequest.getIscmaprequired()) {
						iscmaprequired = true;
					}
					if (birequest.getIscicrequired() != null && birequest.getIscicrequired()) {
						iscicrequired = true;
					}
					if (birequest.getIsblacklistrequired() != null && birequest.getIsblacklistrequired()) {
						isblacklistrequired = true;
					}

					if (birequest.getIsamlawatchlistrequired() != null && birequest.getIsamlawatchlistrequired()) {
						isamlarequired = true;
					}

				}

				/** BAP **/
				if (isbaprequired) {
					// Result
					params.put("actbap", "BAP");
					biact = (Tbbiactivity) dbService.executeUniqueHQLQuery(
							"FROM Tbbiactivity WHERE bireportid=:bireportid AND biactivity=:actbap", params);
					if (biact == null) {
						biact = new Tbbiactivity();
						TbbiactivityId id = new TbbiactivityId();
						id.setBireportid(bireportmain.getBireportid());
						id.setBiactivity("BAP");
						biact.setId(id);
						biact.setCifno(bireportmain.getCifno());
						biact.setOverallresults(bapactivity.getOverallresults());
						biact.setOverallremarks(bapactivity.getOverallremarks());
						dbService.save(biact);
					} else {
						biact.setOverallresults(bapactivity.getOverallresults());
						biact.setOverallremarks(bapactivity.getOverallremarks());
						dbService.saveOrUpdate(biact);
					}
				}

				/** CMAP **/
				if (iscmaprequired) {
					// Details
					Tbbicmap cmap = (Tbbicmap) dbService
							.executeUniqueHQLQuery("FROM Tbbicmap WHERE bireportid=:bireportid", params);
					if (cmap == null) {
						cmap = new Tbbicmap();
						cmap.setAppno(bir.getAppno());
						cmap.setBireportid(bir.getBireportid());
						cmap.setBirequestid(bir.getBirequestid());
						cmap.setCifno(bir.getCifno());
						cmap.setDescription(cmapdetails.getDescription());
						cmap.setDmsid(cmapdetails.getDmsid());
						cmap.setReportdate(cmapdetails.getReportdate());
						dbService.save(cmap);
					} else {
						cmap.setDescription(cmapdetails.getDescription());
						cmap.setDmsid(cmapdetails.getDmsid());
						cmap.setReportdate(cmapdetails.getReportdate());
						dbService.saveOrUpdate(cmap);
					}
					// Result
					params.put("actcmap", "CMAP");
					biact = (Tbbiactivity) dbService.executeUniqueHQLQuery(
							"FROM Tbbiactivity WHERE bireportid=:bireportid AND biactivity=:actcmap", params);
					if (biact == null) {
						biact = new Tbbiactivity();
						TbbiactivityId id = new TbbiactivityId();
						id.setBireportid(bireportmain.getBireportid());
						id.setBiactivity("CMAP");
						biact.setId(id);
						biact.setCifno(bireportmain.getCifno());
						biact.setOverallresults(cmapactivity.getOverallresults());
						biact.setOverallremarks(cmapactivity.getOverallremarks());
						dbService.save(biact);
					} else {
						biact.setOverallresults(cmapactivity.getOverallresults());
						biact.setOverallremarks(cmapactivity.getOverallremarks());
						dbService.saveOrUpdate(biact);
					}
				}

				/** CIC **/
				if (iscicrequired) {
					// Details
					Tbbicic cic = (Tbbicic) dbService.executeUniqueHQLQuery("FROM Tbbicic WHERE bireportid=:bireportid",
							params);
					if (cic == null) {
						cic = new Tbbicic();
						cic.setAppno(bir.getAppno());
						cic.setBireportid(bir.getBireportid());
						cic.setBirequestid(bir.getBirequestid());
						cic.setCifno(bir.getCifno());
						cic.setDescription(cicdetails.getDescription());
						cic.setDmsid(cicdetails.getDmsid());
						cic.setReportdate(cicdetails.getReportdate());
						dbService.save(cic);
					} else {
						cic.setDescription(cicdetails.getDescription());
						cic.setDmsid(cicdetails.getDmsid());
						cic.setReportdate(cicdetails.getReportdate());
						dbService.saveOrUpdate(cic);
					}
					// Result
					params.put("actcic", "CIC");
					biact = (Tbbiactivity) dbService.executeUniqueHQLQuery(
							"FROM Tbbiactivity WHERE bireportid=:bireportid AND biactivity=:actcic", params);
					if (biact == null) {
						biact = new Tbbiactivity();
						TbbiactivityId id = new TbbiactivityId();
						id.setBireportid(bireportmain.getBireportid());
						id.setBiactivity("CIC");
						biact.setId(id);
						biact.setCifno(bireportmain.getCifno());
						biact.setOverallresults(cicactivity.getOverallresults());
						biact.setOverallremarks(cicactivity.getOverallremarks());
						dbService.save(biact);
					} else {
						biact.setOverallresults(cicactivity.getOverallresults());
						biact.setOverallremarks(cicactivity.getOverallremarks());
						dbService.saveOrUpdate(biact);
					}
				}

				/** BLACKLIST **/
				if (isblacklistrequired) {
					params.put("actblklistinternal", "BLACKLISTINTERNAL");
					params.put("actblklistexternal", "BLACKLISTEXTERNAL");
					Tbbiactivity biacti = (Tbbiactivity) dbService.executeUniqueHQLQuery(
							"FROM Tbbiactivity WHERE bireportid=:bireportid AND biactivity=:actblklistinternal",
							params);
					Tbbiactivity biacte = (Tbbiactivity) dbService.executeUniqueHQLQuery(
							"FROM Tbbiactivity WHERE bireportid=:bireportid AND biactivity=:actblklistexternal",
							params);
					if (biacti == null) {
						biacti = new Tbbiactivity();
						TbbiactivityId id = new TbbiactivityId();
						id.setBireportid(bireportmain.getBireportid());
						id.setBiactivity("BLACKLISTINTERNAL");
						biacti.setId(id);
						biacti.setCifno(bireportmain.getCifno());
						biacti.setOverallresults(internalblacklistactivity.getOverallresults());
						biacti.setOverallremarks(internalblacklistactivity.getOverallremarks());
						dbService.save(biacti);
					} else {
						biacti.setOverallresults(internalblacklistactivity.getOverallresults());
						biacti.setOverallremarks(internalblacklistactivity.getOverallremarks());
						dbService.saveOrUpdate(biacti);
					}
					if (biacte == null) {
						biacte = new Tbbiactivity();
						TbbiactivityId id = new TbbiactivityId();
						id.setBireportid(bireportmain.getBireportid());
						id.setBiactivity("BLACKLISTEXTERNAL");
						biacte.setId(id);
						biacte.setCifno(bireportmain.getCifno());
						biacte.setOverallresults(externalblacklistactivity.getOverallresults());
						biacte.setOverallremarks(externalblacklistactivity.getOverallremarks());
						dbService.save(biacte);
					} else {
						biacte.setOverallresults(externalblacklistactivity.getOverallresults());
						biacte.setOverallremarks(externalblacklistactivity.getOverallremarks());
						dbService.saveOrUpdate(biacte);
					}
				}

				/** AMLAWATCHLIST **/
				if (isamlarequired) {
					params.put("interanlactamla", "AMLAINTERNAL");
					params.put("externalactamla", "AMLAEXTERNAL");
					Tbbiactivity biacti = (Tbbiactivity) dbService.executeUniqueHQLQuery(
							"FROM Tbbiactivity WHERE bireportid=:bireportid AND biactivity=:internalactamla", params);
					Tbbiactivity biacte = (Tbbiactivity) dbService.executeUniqueHQLQuery(
							"FROM Tbbiactivity WHERE bireportid=:bireportid AND biactivity=:externalactamla", params);
					if (biacti == null) {
						biacti = new Tbbiactivity();
						TbbiactivityId id = new TbbiactivityId();
						id.setBireportid(bireportmain.getBireportid());
						id.setBiactivity("AMLAINTERNAL");
						biacti.setId(id);
						biacti.setCifno(bireportmain.getCifno());
						biacti.setOverallresults(internalamlawatchlistactivity.getOverallresults());
						biacti.setOverallremarks(internalamlawatchlistactivity.getOverallremarks());
						dbService.save(biacti);
					} else {
						biacti.setOverallresults(internalamlawatchlistactivity.getOverallresults());
						biacti.setOverallremarks(internalamlawatchlistactivity.getOverallremarks());
						dbService.saveOrUpdate(biacti);
					}
					if (biacte == null) {
						biacte = new Tbbiactivity();
						TbbiactivityId id = new TbbiactivityId();
						id.setBireportid(bireportmain.getBireportid());
						id.setBiactivity("AMLAEXTERNAL");
						biacte.setId(id);
						biacte.setCifno(bireportmain.getCifno());
						biacte.setOverallresults(externalamalwatchlistactivity.getOverallresults());
						biacte.setOverallremarks(externalamalwatchlistactivity.getOverallremarks());
						dbService.save(biacte);
					} else {
						biacte.setOverallresults(externalamalwatchlistactivity.getOverallresults());
						biacte.setOverallremarks(externalamalwatchlistactivity.getOverallremarks());
						dbService.saveOrUpdate(biacte);
					}
				}

				// Update TBBIREPORTMAIN
				bir.setIsbapautomated(bireportmain.getIsbapautomated());
				bir.setBapresults(bireportmain.getBapresults());
				bir.setCmapresults(bireportmain.getCmapresults());
				bir.setCicresults(bireportmain.getCicresults());
				bir.setBlacklistresults(bireportmain.getBlacklistresults());
				bir.setAmlawatchlistresults(bireportmain.getAmlawatchlistresults());// DANIEL.SEPT.14.2018
				bir.setExtblacklistresults(bireportmain.getExtblacklistresults());
				bir.setExtamlawatchlistresults(bireportmain.getExtamlawatchlistresults());
				bir.setDateupdated(new Date());
				if (dbService.saveOrUpdate(bir)) {
					AuditLog.addAuditLog(
							AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("SAVE BI REPORT",
									AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
							"User " + username + " Saved " + bir.getAppno() + "'s BI Report.", username, new Date(),
							AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Create BI Report--
	 * 
	 * @author Kevin
	 * @return form = {@link ReturnForm} {flag = success, otherwise failed ; message
	 *         = msg}
	 */
	@Override
	public ReturnForm createBiReport(String birequestid, String bireportid) {
		ReturnForm form = new ReturnForm();
		form.setFlag("failed");
		Map<String, Object> params = HQLUtil.getMap();
		Tbbirequest r = new Tbbirequest();
		Tbbireportmain bimain = new Tbbireportmain();
		params.put("birequestid", birequestid);
		try {
			r = (Tbbirequest) dbService.executeUniqueHQLQuery("FROM Tbbirequest WHERE birequestid=:birequestid",
					params);
			if (r != null) {
				if (bireportid == null) {
					bimain = new Tbbireportmain();
					bimain.setAppno(r.getAppno());
					String genBiReportId = ApplicationNoGenerator.generateReportID("BI");
					bimain.setBireportid(genBiReportId);
					bimain.setBirequestid(birequestid);
					bimain.setCifno(r.getCifno());
					bimain.setCustomername(r.getCustomername());
					bimain.setDatecreated(new Date());
					bimain.setDateofbirth(r.getDateofbirth());
					bimain.setDateofincorporation(r.getDateofincorporation());
					bimain.setDaterequested(r.getDaterequested());
					bimain.setFirstname(r.getFirstname());
					bimain.setLastname(r.getLastname());
					bimain.setMiddlename(r.getMiddlename());
					bimain.setSuffix(r.getSuffix());
//					bimain.setReportdate(new Date());
					bimain.setReportedby(r.getAssignedbi());
					bimain.setRequestedby(r.getRequestedby());
					bimain.setStatus("0");// New
					bimain.setStatusdatetime(new Date());
					bimain.setCompanycode(r.getCompanycode());
					bimain.setCustomertype(r.getCustomertype());
					bimain.setReasonforbi(r.getPurposeforbi());
					bimain.setParticipationcode(r.getParticipationcode());
					if (dbService.save(bimain)) {
						form.setFlag("success");
						form.setMessage(bimain.getBireportid());
					}
				} else {
					params.put("bireportid", bireportid);
					bimain = (Tbbireportmain) dbService
							.executeUniqueHQLQuery("FROM Tbbireportmain WHERE bireportid=:bireportid", params);
					if (bimain != null) {
						bimain.setDateupdated(new Date());
						if (dbService.saveOrUpdate(bimain)) {
							form.setFlag("success");
							form.setMessage(bimain.getBireportid());
						}
					}
				}
				if (form.getFlag().equals("success")) {
					// Set status to On-Process(Tbinvestigationinst) Inside application
					if (r.getAppno() != null) {
						params.put("appno", r.getAppno());
						params.put("cifno", r.getCifno());
						params.put("invsttype", "BI");

						// On-Process
						dbService.executeUpdate(
								"UPDATE Tbinvestigationinst SET status='2' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype",
								params);
					}

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/**
	 * --Get BAP List (Adverse, Card, Case, Mishandled)--
	 * 
	 * @author Kevin
	 * @return form = {@link BAPListForm}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BAPListForm getBapList(String bireportid, Boolean isFromFileUpload) {
		BAPListForm form = new BAPListForm();
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbbapnfis> bap = new ArrayList<Tbbapnfis>();
		try {
			if (bireportid != null) {
				params.put("bireportid", bireportid);
				String hql = "FROM Tbbapnfis WHERE bireportid=:bireportid AND reporttype=:reporttype";

				if (isFromFileUpload == null || isFromFileUpload == false) {
					hql = hql + " AND ismatched = 'true'";
				} else {
					hql = hql + " AND isfromfileupload = 'true'";
				}

				// Adverse Match
				params.put("reporttype", "ADV");
				bap = (List<Tbbapnfis>) dbService.executeListHQLQuery(hql, params);
				form.setAdversematch(bap);

				// Card Match
				params.put("reporttype", "CARD");
				bap = (List<Tbbapnfis>) dbService.executeListHQLQuery(hql, params);
				form.setCardmatch(bap);

				// Case Match
				params.put("reporttype", "CASE");
				bap = (List<Tbbapnfis>) dbService.executeListHQLQuery(hql, params);
				form.setCasematch(bap);

				// Mishandled Match
				params.put("reporttype", "MISHANDLED");
				bap = (List<Tbbapnfis>) dbService.executeListHQLQuery(hql, params);
				form.setMishandledmatch(bap);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/**
	 * --Get BAP Record by bapid--
	 * 
	 * @author Kevin
	 * @return form = {@link Tbbapnfis}
	 */
	@Override
	public Tbbapnfis getBapNfisRecord(Integer bapid) {
		Map<String, Object> params = HQLUtil.getMap();
		Tbbapnfis bap = new Tbbapnfis();
		try {
			if (bapid != null) {
				params.put("bapid", bapid);
				bap = (Tbbapnfis) dbService.executeUniqueHQLQuery("FROM Tbbapnfis WHERE bapid=:bapid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bap;
	}

	/**
	 * --Save or Update Bap Record--
	 * 
	 * @author Kevin
	 * @return String = success, otherwise failed
	 */
	@Override
	public String saveOrUpdateBapNfis(Tbbapnfis bapnfis, String baptype) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		Tbbapnfis bap = null;
		String rptMainAppno = null;
		try {
			if (bapnfis.getBireportid() != null && baptype != null) {
				params.put("bireportid", bapnfis.getBireportid());
				rptMainAppno = (String) dbService
						.executeUniqueSQLQuery("SELECT appno FROM Tbbireportmain WHERE bireportid=:bireportid", params);
				dbService.executeUpdate("UPDATE Tbbireportmain SET isbapautomated='" + baptype
						+ "', dateupdated=GETDATE() WHERE bireportid=:bireportid", params);
			}
			if (bapnfis.getBapid() == null) {
				bap = new Tbbapnfis();
				bap.setBireportid(bapnfis.getBireportid());
				bap.setCifno(bapnfis.getCifno());
				bap.setAppno(rptMainAppno);
				bap.setReporttype(bapnfis.getReporttype());
				if (bapnfis.getRecordname() != null) {
					bap.setRecordname(bapnfis.getRecordname().toUpperCase());
				}
				bap.setDateofbirth(bapnfis.getDateofbirth());
				if (bapnfis.getAddress() != null) {
					bap.setAddress(bapnfis.getAddress());
				}
				if (bapnfis.getSpouse() != null) {
					bap.setSpouse(bapnfis.getSpouse());
				}
				if (bapnfis.getBusinessname() != null) {
					bap.setBusinessname(bapnfis.getBusinessname());
				}
				if (bapnfis.getAdverseentrytype() != null) {
					bap.setAdverseentrytype(bapnfis.getAdverseentrytype());
				}
				if (bapnfis.getLoantype() != null) {
					bap.setLoantype(bapnfis.getLoantype());
				}
				bap.setReporteddate(bapnfis.getReporteddate());
				bap.setSecurityamount(bapnfis.getSecurityamount());
				if (bapnfis.getBank() != null) {
					bap.setBank(bapnfis.getBank().toUpperCase());
				}
				bap.setRemarks(bapnfis.getRemarks());
				if (bapnfis.getCardnumber() != null) {
					bap.setCardnumber(bapnfis.getCardnumber());
				}
				bap.setBalance(bapnfis.getBalance());
				if (bapnfis.getPlaintiff() != null) {
					bap.setPlaintiff(bapnfis.getPlaintiff());
				}
				bap.setCodefendant(bapnfis.getCodefendant());
				if (bapnfis.getCasenumber() != null) {
					bap.setCasenumber(bapnfis.getCasenumber());
				}
				if (bapnfis.getNaturecase() != null) {
					bap.setNaturecase(bapnfis.getNaturecase());
				}
				if (bapnfis.getCourtsalanumber() != null) {
					bap.setCourtsalanumber(bapnfis.getCourtsalanumber());
				}
				if (bapnfis.getJointaccounttype() != null) {
					bap.setJointaccounttype(bapnfis.getJointaccounttype());
				}
				if (bapnfis.getJointaccountname() != null) {
					bap.setJointaccountname(bapnfis.getJointaccountname());
				}
				bap.setDateofincorporation(bapnfis.getDateofincorporation());
				if (bapnfis.getCifno() != null && bapnfis.getCifno().length() > 0) {
					bap.setCustomertype(bapnfis.getCifno().substring(0, 1));
				}
				bap.setParticipationcode(bapnfis.getParticipationcode());
				if (bapnfis.getDef1typ() != null) {
					bap.setDef1typ(bapnfis.getDef1typ());
				}
				if (bapnfis.getDef2typ() != null) {
					bap.setDef2typ(bapnfis.getDef2typ());
				}
				bap.setFiledate(bapnfis.getFiledate());
				bap.setOpened(bapnfis.getOpened());
				bap.setClosed(bapnfis.getClosed());
				bap.setIsmatched(true);
				bap.setIsfromfileupload(false);
				if (bapnfis.getBranch() != null) {
					bap.setBranch(bapnfis.getBranch());
				}
				bap.setCreditlimit(bapnfis.getCreditlimit());
				if (dbService.save(bap)) {
					flag = "success";
				}
			} else {
				params.put("bapid", bapnfis.getBapid());
				bap = (Tbbapnfis) dbService.executeUniqueHQLQuery("FROM Tbbapnfis WHERE bapid=:bapid", params);
				if (bapnfis.getRecordname() != null) {
					bap.setRecordname(bapnfis.getRecordname().toUpperCase());
				}
				bap.setDateofbirth(bapnfis.getDateofbirth());
				if (bapnfis.getAddress() != null) {
					bap.setAddress(bapnfis.getAddress());
				}
				if (bapnfis.getSpouse() != null) {
					bap.setSpouse(bapnfis.getSpouse());
				}
				if (bapnfis.getBusinessname() != null) {
					bap.setBusinessname(bapnfis.getBusinessname());
				}
				if (bapnfis.getAdverseentrytype() != null) {
					bap.setAdverseentrytype(bapnfis.getAdverseentrytype());
				}
				if (bapnfis.getLoantype() != null) {
					bap.setLoantype(bapnfis.getLoantype());
				}
				bap.setReporteddate(bapnfis.getReporteddate());
				bap.setSecurityamount(bapnfis.getSecurityamount());
				if (bapnfis.getBank() != null) {
					bap.setBank(bapnfis.getBank().toUpperCase());
				}
				bap.setRemarks(bapnfis.getRemarks());
				if (bapnfis.getCardnumber() != null) {
					bap.setCardnumber(bapnfis.getCardnumber());
				}
				bap.setBalance(bapnfis.getBalance());
				if (bapnfis.getPlaintiff() != null) {
					bap.setPlaintiff(bapnfis.getPlaintiff());
				}
				bap.setCodefendant(bapnfis.getCodefendant());
				if (bapnfis.getCasenumber() != null) {
					bap.setCasenumber(bapnfis.getCasenumber());
				}
				if (bapnfis.getNaturecase() != null) {
					bap.setNaturecase(bapnfis.getNaturecase());
				}
				if (bapnfis.getCourtsalanumber() != null) {
					bap.setCourtsalanumber(bapnfis.getCourtsalanumber());
				}
				if (bapnfis.getJointaccounttype() != null) {
					bap.setJointaccounttype(bapnfis.getJointaccounttype());
				}
				if (bapnfis.getJointaccountname() != null) {
					bap.setJointaccountname(bapnfis.getJointaccountname());
				}
				bap.setDateofincorporation(bapnfis.getDateofincorporation());
				if (bapnfis.getDef1typ() != null) {
					bap.setDef1typ(bapnfis.getDef1typ());
				}
				if (bapnfis.getDef2typ() != null) {
					bap.setDef2typ(bapnfis.getDef2typ());
				}
				bap.setFiledate(bapnfis.getFiledate());
				bap.setOpened(bapnfis.getOpened());
				bap.setClosed(bapnfis.getClosed());
				if (bapnfis.getBranch() != null) {
					bap.setBranch(bapnfis.getBranch());
				}
				bap.setCreditlimit(bapnfis.getCreditlimit());
				if (dbService.saveOrUpdate(bap)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Get BI Activity--
	 * 
	 * @author Kevin
	 * @return form = {@link BiActivityForm}
	 */
	@Override
	public BiActivityForm getBiActivity(String bireportid) {
		BiActivityForm form = new BiActivityForm();
		Map<String, Object> params = HQLUtil.getMap();
		Tbbiactivity act = new Tbbiactivity();
		boolean isbaprequired = false;
		boolean iscmaprequired = false;
		boolean iscicrequired = false;
		boolean isblacklistrequired = false;
		boolean isamlarequired = false;// DANIEL SEPT.14.2018
		try {
			if (bireportid != null) {
				params.put("bireportid", bireportid);
				Tbbireportmain bireport = (Tbbireportmain) dbService
						.executeUniqueHQLQuery("FROM Tbbireportmain WHERE bireportid=:bireportid", params);
				if (bireport != null) {
					if (bireport.getBirequestid() != null) {
						params.put("birequestid", bireport.getBirequestid());
						Tbbirequest birequest = (Tbbirequest) dbService
								.executeUniqueHQLQuery("FROM Tbbirequest WHERE birequestid=:birequestid", params);
						if (birequest != null) {
							if (birequest.getIsbaprequired() != null && birequest.getIsbaprequired()) {
								isbaprequired = true;
							}
							if (birequest.getIscmaprequired() != null && birequest.getIscmaprequired()) {
								iscmaprequired = true;
							}
							if (birequest.getIscicrequired() != null && birequest.getIscicrequired()) {
								iscicrequired = true;
							}
							if (birequest.getIsblacklistrequired() != null && birequest.getIsblacklistrequired()) {
								isblacklistrequired = true;
							}
							if (birequest.getIsamlawatchlistrequired() != null
									&& birequest.getIsamlawatchlistrequired()) {
								isamlarequired = true;
							}
						}
					}
				}
				// BAP NFIS
				if (isbaprequired) {
					params.put("type", "BAP");
					act = (Tbbiactivity) dbService.executeUniqueHQLQuery(
							"FROM Tbbiactivity WHERE bireportid=:bireportid AND biactivity=:type", params);
					form.setBapnfis(act);
				}

				// CMAP
				if (iscmaprequired) {
					params.put("type", "CMAP");
					act = (Tbbiactivity) dbService.executeUniqueHQLQuery(
							"FROM Tbbiactivity WHERE bireportid=:bireportid AND biactivity=:type", params);
					form.setCmap(act);
				}

				// CIC
				if (iscicrequired) {
					params.put("type", "CIC");
					act = (Tbbiactivity) dbService.executeUniqueHQLQuery(
							"FROM Tbbiactivity WHERE bireportid=:bireportid AND biactivity=:type", params);
					form.setCic(act);
				}
				if (isblacklistrequired) {
					// BLACKLIST
					params.put("typeinternal", "BLACKLISTINTERNAL");
					Tbbiactivity acti = (Tbbiactivity) dbService.executeUniqueHQLQuery(
							"FROM Tbbiactivity WHERE bireportid=:bireportid AND biactivity=:typeinternal", params);
					form.setBlacklistinternal(acti);
					params.put("typeexternal", "BLACKLISTEXTERNAL");
					Tbbiactivity acte = (Tbbiactivity) dbService.executeUniqueHQLQuery(
							"FROM Tbbiactivity WHERE bireportid=:bireportid AND biactivity=:typeexternal", params);
					form.setBlacklistexternal(acte);
				}

				if (isamlarequired) {
					// AMLAWATCHLIST
					params.put("typeinternal", "AMLAINTERNAL");
					Tbbiactivity acti = (Tbbiactivity) dbService.executeUniqueHQLQuery(
							"FROM Tbbiactivity WHERE bireportid=:bireportid AND biactivity=:typeinternal", params);
					form.setAmlawatchlistinternal(acti);
					params.put("typeexternal", "AMLAEXTERNAL");
					Tbbiactivity acte = (Tbbiactivity) dbService.executeUniqueHQLQuery(
							"FROM Tbbiactivity WHERE bireportid=:bireportid AND biactivity=:typeexternal", params);
					form.setAmlawatchlistexternal(acte);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/**
	 * --Get BI Report by bireportid--
	 * 
	 * @author Kevin
	 * @return form = {@link Tbbireportmain}
	 */
	@Override
	public Tbbireportmain getBIReport(String bireportid) {
		Map<String, Object> params = HQLUtil.getMap();
		Tbbireportmain report = new Tbbireportmain();
		try {
			if (bireportid != null) {
				params.put("bireportid", bireportid);
				report = (Tbbireportmain) dbService
						.executeUniqueHQLQuery("FROM Tbbireportmain WHERE bireportid=:bireportid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return report;
	}

	/**
	 * --Get CMAP Record by bireportid--
	 * 
	 * @author Kevin
	 * @return form = {@link Tbbicmap}
	 */
	@Override
	public Tbbicmap getCMAP(String bireportid) {
		Map<String, Object> params = HQLUtil.getMap();
		Tbbicmap cmap = new Tbbicmap();
		try {
			if (bireportid != null) {
				params.put("bireportid", bireportid);
				cmap = (Tbbicmap) dbService.executeUniqueHQLQuery("FROM Tbbicmap WHERE bireportid=:bireportid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cmap;
	}

	/**
	 * --Get CIC Record by bireportid--
	 * 
	 * @author Kevin
	 * @return form = {@link Tbbicic}
	 */
	@Override
	public Tbbicic getCIC(String bireportid) {
		Map<String, Object> params = HQLUtil.getMap();
		Tbbicic cic = new Tbbicic();
		try {
			if (bireportid != null) {
				params.put("bireportid", bireportid);
				cic = (Tbbicic) dbService.executeUniqueHQLQuery("FROM Tbbicic WHERE bireportid=:bireportid", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cic;
	}

	/**
	 * --Read Bap XML (fileupload)--
	 * 
	 * @author Kevin
	 * @return String = success, otherwise failed
	 */
	@Override
	public String readBapXML(String filename, String bireportid) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbbapnfis> advlist = new ArrayList<Tbbapnfis>();
		List<Tbbapnfis> cardlist = new ArrayList<Tbbapnfis>();
		List<Tbbapnfis> caselist = new ArrayList<Tbbapnfis>();
		List<Tbbapnfis> mishandlist = new ArrayList<Tbbapnfis>();
		params.put("bireportid", bireportid);
		try {
			Tbbireportmain bireport = (Tbbireportmain) dbService
					.executeUniqueHQLQuery("FROM Tbbireportmain WHERE bireportid=:bireportid", params);

			String urlDirectory = RuntimeAccess.getInstance().getSession().getServletContext()
					.getRealPath("resources/data/upload/" + filename);
			File fXmlFile = new File(urlDirectory);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();

			System.out.println("Root element : " + doc.getDocumentElement().getNodeName());
			NodeList mainList = doc.getElementsByTagName("request-item");
			System.out.println("----------------------------");

			for (int temp = 0; temp < mainList.getLength(); temp++) {
				Node firstNode = mainList.item(temp);

				if (firstNode.getNodeType() == Node.ELEMENT_NODE) {

					Element firstElement = (Element) firstNode;

					System.out.println("Parameter" + firstElement.getAttribute("param"));
					System.out.println("Name : " + firstElement.getElementsByTagName("name").item(0).getTextContent());
					System.out.println("Bank : " + firstElement.getElementsByTagName("bank").item(0).getTextContent());

					/**
					 * ADVERSE LIST
					 * 
					 */
					if (firstElement.getElementsByTagName("adv").getLength() != 0) {
						System.out.println("------------->>> ADVERSE LIST <<--------------");
						NodeList subListAdverse = firstElement.getElementsByTagName("adv");
						System.out.println("Adverse");
						for (int temp1 = 0; temp1 < subListAdverse.getLength(); temp1++) {
							Node secondNode = subListAdverse.item(temp1);
							if (secondNode.getNodeType() == Node.ELEMENT_NODE) {
								Element secondElement = (Element) secondNode;

								NodeList subListMatch = secondElement.getElementsByTagName("match");
								for (int temp2 = 0; temp2 < subListMatch.getLength(); temp2++) {
									Tbbapnfis adv = new Tbbapnfis();
									Node thirdNode = subListMatch.item(temp2);
									if (thirdNode.getNodeType() == Node.ELEMENT_NODE) {
										Element thirdElement = (Element) thirdNode;
										System.out.println("Match" + thirdElement.getAttribute("match"));
										if (thirdElement.getElementsByTagName("name").getLength() != 0) {
											System.out.println("Name : " + thirdElement.getElementsByTagName("name")
													.item(0).getTextContent());
											adv.setRecordname(
													thirdElement.getElementsByTagName("name").item(0).getTextContent());
										}
										if (thirdElement.getElementsByTagName("bday").getLength() != 0) {
											System.out.println("Bday : " + thirdElement.getElementsByTagName("bday")
													.item(0).getTextContent());
											String tempDate = thirdElement.getElementsByTagName("bday").item(0)
													.getTextContent();
											String dateString = tempDate.substring(0, 4) + "-"
													+ tempDate.substring(4, 6) + "-" + tempDate.substring(6, 8);
											adv.setDateofbirth(new SimpleDateFormat("yyyy-MM-dd").parse(dateString));
										}
										if (thirdElement.getElementsByTagName("address").getLength() != 0) {
											adv.setAddress(thirdElement.getElementsByTagName("address").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("spouse").getLength() != 0) {
											adv.setSpouse(thirdElement.getElementsByTagName("spouse").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("business").getLength() != 0) {
											adv.setBusinessname(thirdElement.getElementsByTagName("business").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("advtype").getLength() != 0) {
											adv.setAdverseentrytype(thirdElement.getElementsByTagName("advtype").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("loantype").getLength() != 0) {
											adv.setLoantype(thirdElement.getElementsByTagName("loantype").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("reported").getLength() != 0) {
											String tempDate = thirdElement.getElementsByTagName("reported").item(0)
													.getTextContent();
											String dateString = tempDate.substring(0, 4) + "-"
													+ tempDate.substring(4, 6) + "-" + tempDate.substring(6, 8);
											adv.setReporteddate(new SimpleDateFormat("yyyy-MM-dd").parse(dateString));
										}
										if (thirdElement.getElementsByTagName("secuamt").getLength() != 0) {
											String output = thirdElement.getElementsByTagName("secuamt").item(0)
													.getTextContent().replaceAll("\\s", "");
											long tempamt = (long) Double.parseDouble(output);
											adv.setSecurityamount(BigDecimal.valueOf(tempamt));
										}
										if (thirdElement.getElementsByTagName("bank").getLength() != 0) {
											adv.setBank(
													thirdElement.getElementsByTagName("bank").item(0).getTextContent());
										}
										if (thirdElement.getElementsByTagName("remarks").getLength() != 0) {
											adv.setRemarks(thirdElement.getElementsByTagName("remarks").item(0)
													.getTextContent());
										}
										advlist.add(adv);
									}
								}
							}
						}
					}

					/**
					 * CARD LIST
					 * 
					 */
					if (firstElement.getElementsByTagName("card").getLength() != 0) {
						System.out.println("------------->>> CARD LIST <<--------------");
						NodeList subListCard = firstElement.getElementsByTagName("card");
						System.out.println("Card");
						for (int temp1 = 0; temp1 < subListCard.getLength(); temp1++) {
							Node secondNode = subListCard.item(temp1);
							if (secondNode.getNodeType() == Node.ELEMENT_NODE) {
								Element secondElement = (Element) secondNode;

								NodeList subListMatch = secondElement.getElementsByTagName("match");
								for (int temp2 = 0; temp2 < subListMatch.getLength(); temp2++) {
									Tbbapnfis card = new Tbbapnfis();
									Node thirdNode = subListMatch.item(temp2);
									if (thirdNode.getNodeType() == Node.ELEMENT_NODE) {
										Element thirdElement = (Element) thirdNode;
										System.out.println("Match" + thirdElement.getAttribute("match"));
										if (thirdElement.getElementsByTagName("name").getLength() != 0) {
											System.out.println("Name : " + thirdElement.getElementsByTagName("name")
													.item(0).getTextContent());
											card.setRecordname(
													thirdElement.getElementsByTagName("name").item(0).getTextContent());
										}
										if (thirdElement.getElementsByTagName("bday").getLength() != 0) {
											System.out.println("Bday : " + thirdElement.getElementsByTagName("bday")
													.item(0).getTextContent());
											String tempDate = thirdElement.getElementsByTagName("bday").item(0)
													.getTextContent();
											String dateString = tempDate.substring(0, 4) + "-"
													+ tempDate.substring(4, 6) + "-" + tempDate.substring(6, 8);
											Date dt = null;
											SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
											try {
												dt = formatter.parse(dateString);
											} catch (Exception e) {
												e.printStackTrace();
											}
											card.setDateofbirth(dt);
										}
										if (thirdElement.getElementsByTagName("address").getLength() != 0) {
											card.setAddress(thirdElement.getElementsByTagName("address").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("spouse").getLength() != 0) {
											card.setSpouse(thirdElement.getElementsByTagName("spouse").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("business").getLength() != 0) {
											card.setBusinessname(thirdElement.getElementsByTagName("business").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("cardno").getLength() != 0) {
											card.setCardnumber(thirdElement.getElementsByTagName("cardno").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("reported").getLength() != 0) {
											String tempDate = thirdElement.getElementsByTagName("reported").item(0)
													.getTextContent();
											String dateString = tempDate.substring(0, 4) + "-"
													+ tempDate.substring(4, 6) + "-" + tempDate.substring(6, 8);
											card.setReporteddate(new SimpleDateFormat("yyyy-MM-dd").parse(dateString));
										}
										if (thirdElement.getElementsByTagName("balance").getLength() != 0) {
											String output = thirdElement.getElementsByTagName("balance").item(0)
													.getTextContent().replaceAll("\\s", "");
											long tempamt = (long) Double.parseDouble(output);
											card.setBalance(BigDecimal.valueOf(tempamt));
										}
										if (thirdElement.getElementsByTagName("bank").getLength() != 0) {
											card.setBank(
													thirdElement.getElementsByTagName("bank").item(0).getTextContent());
										}
										if (thirdElement.getElementsByTagName("remarks").getLength() != 0) {
											card.setRemarks(thirdElement.getElementsByTagName("remarks").item(0)
													.getTextContent());
										}
										cardlist.add(card);
									}
								}
							}
						}
					}

					/**
					 * CASE LIST
					 * 
					 */
					if (firstElement.getElementsByTagName("case").getLength() != 0) {
						System.out.println("------------->>> CASE LIST <<--------------");
						NodeList subListCase = firstElement.getElementsByTagName("case");
						System.out.println("Case");
						for (int temp1 = 0; temp1 < subListCase.getLength(); temp1++) {
							Node secondNode = subListCase.item(temp1);
							if (secondNode.getNodeType() == Node.ELEMENT_NODE) {
								Element secondElement = (Element) secondNode;

								NodeList subListMatch = secondElement.getElementsByTagName("match");
								for (int temp2 = 0; temp2 < subListMatch.getLength(); temp2++) {
									Tbbapnfis cases = new Tbbapnfis();
									Node thirdNode = subListMatch.item(temp2);
									if (thirdNode.getNodeType() == Node.ELEMENT_NODE) {
										Element thirdElement = (Element) thirdNode;
										System.out.println("Match" + thirdElement.getAttribute("match"));
										if (thirdElement.getElementsByTagName("name").getLength() != 0) {
											System.out.println("Name : " + thirdElement.getElementsByTagName("name")
													.item(0).getTextContent());
											cases.setRecordname(
													thirdElement.getElementsByTagName("name").item(0).getTextContent());
										}
										if (thirdElement.getElementsByTagName("reported").getLength() != 0) {
											System.out.println("Reported : " + thirdElement
													.getElementsByTagName("reported").item(0).getTextContent());
											String tempDate = thirdElement.getElementsByTagName("reported").item(0)
													.getTextContent();
											String dateString = tempDate.substring(0, 4) + "-"
													+ tempDate.substring(4, 6) + "-" + tempDate.substring(6, 8);
											cases.setReporteddate(new SimpleDateFormat("yyyy-MM-dd").parse(dateString));
										}
										if (thirdElement.getElementsByTagName("plaintiff").getLength() != 0) {
											cases.setPlaintiff(thirdElement.getElementsByTagName("plaintiff").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("def1typ").getLength() != 0) {
											cases.setDef1typ(thirdElement.getElementsByTagName("def1typ").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("def2typ").getLength() != 0) {
											cases.setDef2typ(thirdElement.getElementsByTagName("def2typ").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("filedate").getLength() != 0) {
											String tempDate = thirdElement.getElementsByTagName("filedate").item(0)
													.getTextContent();
											String dateString = tempDate.substring(0, 4) + "-"
													+ tempDate.substring(4, 6) + "-" + tempDate.substring(6, 8);
											cases.setFiledate(new SimpleDateFormat("yyyy-MM-dd").parse(dateString));
										}
										if (thirdElement.getElementsByTagName("casenum").getLength() != 0) {
											cases.setCasenumber(thirdElement.getElementsByTagName("casenum").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("sala").getLength() != 0) {
											cases.setCourtsalanumber(
													thirdElement.getElementsByTagName("sala").item(0).getTextContent());
										}
										if (thirdElement.getElementsByTagName("nature").getLength() != 0) {
											cases.setNaturecase(thirdElement.getElementsByTagName("nature").item(0)
													.getTextContent());
										}
										caselist.add(cases);
									}
								}
							}
						}
					}

					/**
					 * MISHANDLED
					 * 
					 */
					if (firstElement.getElementsByTagName("bchk").getLength() != 0) {
						System.out.println("------------->>> MISHANDLED LIST <<--------------");
						NodeList subListCase = firstElement.getElementsByTagName("bchk");
						System.out.println("bchk");
						for (int temp1 = 0; temp1 < subListCase.getLength(); temp1++) {
							Node secondNode = subListCase.item(temp1);
							if (secondNode.getNodeType() == Node.ELEMENT_NODE) {
								Element secondElement = (Element) secondNode;

								NodeList subListMatch = secondElement.getElementsByTagName("match");
								for (int temp2 = 0; temp2 < subListMatch.getLength(); temp2++) {
									Tbbapnfis mishand = new Tbbapnfis();
									Node thirdNode = subListMatch.item(temp2);
									if (thirdNode.getNodeType() == Node.ELEMENT_NODE) {
										Element thirdElement = (Element) thirdNode;
										System.out.println("Match" + thirdElement.getAttribute("match"));
										if (thirdElement.getElementsByTagName("name").getLength() != 0) {
											System.out.println("Name : " + thirdElement.getElementsByTagName("name")
													.item(0).getTextContent());
											mishand.setRecordname(
													thirdElement.getElementsByTagName("name").item(0).getTextContent());
										}
										if (thirdElement.getElementsByTagName("bday").getLength() != 0) {
											System.out.println("Bday : " + thirdElement.getElementsByTagName("bday")
													.item(0).getTextContent());
											String tempDate = thirdElement.getElementsByTagName("bday").item(0)
													.getTextContent();
											String dateString = tempDate.substring(0, 4) + "-"
													+ tempDate.substring(4, 6) + "-" + tempDate.substring(6, 8);
											mishand.setDateofbirth(
													new SimpleDateFormat("yyyy-MM-dd").parse(dateString));
										}
										if (thirdElement.getElementsByTagName("address").getLength() != 0) {
											mishand.setAddress(thirdElement.getElementsByTagName("address").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("spouse").getLength() != 0) {
											mishand.setSpouse(thirdElement.getElementsByTagName("spouse").item(0)
													.getTextContent());
										}
										if (thirdElement.getElementsByTagName("reported").getLength() != 0) {
											String tempDate = thirdElement.getElementsByTagName("reported").item(0)
													.getTextContent();
											String dateString = tempDate.substring(0, 4) + "-"
													+ tempDate.substring(4, 6) + "-" + tempDate.substring(6, 8);
											mishand.setReporteddate(
													new SimpleDateFormat("yyyy-MM-dd").parse(dateString));
										}
										if (thirdElement.getElementsByTagName("bank").getLength() != 0) {
											mishand.setBank(
													thirdElement.getElementsByTagName("bank").item(0).getTextContent());
										}
										if (thirdElement.getElementsByTagName("opened").getLength() != 0) {
											String tempDate = thirdElement.getElementsByTagName("opened").item(0)
													.getTextContent();
											String dateString = tempDate.substring(0, 4) + "-"
													+ tempDate.substring(4, 6) + "-" + tempDate.substring(6, 8);
											mishand.setOpened(new SimpleDateFormat("yyyy-MM-dd").parse(dateString));
										}
										if (thirdElement.getElementsByTagName("closed").getLength() != 0) {
											String tempDate = thirdElement.getElementsByTagName("closed").item(0)
													.getTextContent();
											String dateString = tempDate.substring(0, 4) + "-"
													+ tempDate.substring(4, 6) + "-" + tempDate.substring(6, 8);
											mishand.setClosed(new SimpleDateFormat("yyyy-MM-dd").parse(dateString));
										}
										if (thirdElement.getElementsByTagName("jtype").getLength() != 0) {
											mishand.setJointaccounttype(thirdElement.getElementsByTagName("jtype")
													.item(0).getTextContent());
										}
										if (thirdElement.getElementsByTagName("jacct").getLength() != 0) {
											mishand.setJointaccountname(thirdElement.getElementsByTagName("jacct")
													.item(0).getTextContent());
										}
										mishandlist.add(mishand);
									}
								}
							}
						}
					} // --End of Mishandled
				}
			}
			/** Delete from TBBAPNFIS if there's an existing record **/
			dbService.executeUpdate("DELETE FROM Tbbapnfis WHERE bireportid=:bireportid", params);

			/** Insert new record to TBBAPNFIS **/
			// Adverse
			if (advlist != null && !advlist.isEmpty()) {
				for (Tbbapnfis adversematch : advlist) {
					adversematch.setBireportid(bireport.getBireportid());
					adversematch.setCifno(bireport.getCifno());
					adversematch.setAppno(bireport.getAppno());
					adversematch.setReporttype("ADV");
					if (bireport.getCifno() != null && bireport.getCifno().length() > 0) {
						adversematch.setCustomertype(bireport.getCifno().substring(0, 1));
					}
					adversematch.setIsmatched(false);
					adversematch.setIsfromfileupload(true);
					dbService.save(adversematch);
				}
			}

			// Card
			if (cardlist != null && !cardlist.isEmpty()) {
				for (Tbbapnfis cardmatch : cardlist) {
					cardmatch.setBireportid(bireport.getBireportid());
					cardmatch.setCifno(bireport.getCifno());
					cardmatch.setAppno(bireport.getAppno());
					cardmatch.setReporttype("CARD");
					if (bireport.getCifno() != null && bireport.getCifno().length() > 0) {
						cardmatch.setCustomertype(bireport.getCifno().substring(0, 1));
					}
					cardmatch.setIsmatched(false);
					cardmatch.setIsfromfileupload(true);
					dbService.save(cardmatch);
				}
			}

			// Case
			if (caselist != null && !caselist.isEmpty()) {
				for (Tbbapnfis casematch : caselist) {
					casematch.setBireportid(bireport.getBireportid());
					casematch.setCifno(bireport.getCifno());
					casematch.setAppno(bireport.getAppno());
					casematch.setReporttype("CASE");
					if (bireport.getCifno() != null && bireport.getCifno().length() > 0) {
						casematch.setCustomertype(bireport.getCifno().substring(0, 1));
					}
					casematch.setIsmatched(false);
					casematch.setIsfromfileupload(true);
					dbService.save(casematch);
				}
			}

			// Mishandled
			if (mishandlist != null && !mishandlist.isEmpty()) {
				for (Tbbapnfis mishandledmatch : mishandlist) {
					mishandledmatch.setBireportid(bireport.getBireportid());
					mishandledmatch.setCifno(bireport.getCifno());
					mishandledmatch.setAppno(bireport.getAppno());
					mishandledmatch.setReporttype("MISHANDLED");
					if (bireport.getCifno() != null && bireport.getCifno().length() > 0) {
						mishandledmatch.setCustomertype(bireport.getCifno().substring(0, 1));
					}
					mishandledmatch.setIsmatched(false);
					mishandledmatch.setIsfromfileupload(true);
					dbService.save(mishandledmatch);
				}
			}
			// fXmlFile.delete();
			bireport.setIsbapautomated(true);
			bireport.setDateupdated(new Date());
			if (dbService.saveOrUpdate(bireport)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
		return flag;
	}

	/**
	 * --Save Matched from XML--
	 * 
	 * @author Kevin
	 * @return String = success, otherwise failed
	 */
	@Override
	public String saveMatch(Integer bapid) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (bapid != null) {
				params.put("bapid", bapid);
				int res = dbService.executeUpdate("UPDATE Tbbapnfis SET ismatched='true' WHERE bapid=:bapid", params);
				if (res > 0) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Delete record from table (Tbbapnfis) by bapid--
	 * 
	 * @author Kevin
	 * @return String = success, otherwise failed
	 */
	@Override
	public String deleteMatch(Integer bapid) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (bapid != null) {
				params.put("bapid", bapid);
				Tbbapnfis bap = (Tbbapnfis) dbService.executeUniqueHQLQuery("FROM Tbbapnfis WHERE bapid=:bapid",
						params);
				if (bap != null) {
					if (bap.getIsfromfileupload() != null && bap.getIsfromfileupload() == true) {
						bap.setIsmatched(false);
						if (dbService.saveOrUpdate(bap)) {
							flag = "success";
						}
					} else {
						if (dbService.delete(bap)) {
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
	 * --Change BAP Type Validator--
	 * 
	 * @author Kevin
	 * @return String = success if equal, otherwise failed
	 */
	@Override
	public String changeBapType(String bireportid, String baptype) {
		String flag = "success";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (bireportid != null) {
				params.put("bireportid", bireportid);
				Tbbireportmain bireport = (Tbbireportmain) dbService
						.executeUniqueHQLQuery("FROM Tbbireportmain WHERE bireportid=:bireportid", params);
				if (bireport != null) {
					if (bireport.getIsbapautomated() != null) {
						if (baptype != null) {
							boolean isAutomated = baptype.equals("1") ? true : false;
							if (!bireport.getIsbapautomated().equals(isAutomated)) {
								flag = "failed";
							}
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
	 * --Delete All BAP Record by bireportid--
	 * 
	 * @author Kevin
	 * @return String = success, otherwise failed
	 */
	@Override
	public String deleteAllBAPRecord(String bireportid, String baptype) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("bireportid", bireportid);
			int res = dbService.executeUpdate("DELETE FROM Tbbapnfis WHERE bireportid=:bireportid", params);
			if (res >= 0) {
				String isAutomated = "false";
				if (baptype != null && (baptype.equals("1") || baptype.equals(1))) {
					isAutomated = "true";
				}
				res = dbService.executeUpdate("UPDATE Tbbireportmain SET isbapautomated='" + isAutomated
						+ "', dateupdated=GETDATE() WHERE bireportid=:bireportid", params);
				if (res > 0) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Get BI Report Access Rights--
	 * 
	 * @author Kevin
	 * @return form = {@link BIAccessRightsForm}
	 */
	@Override
	public BIAccessRightsForm getBIReportAccessRight(String bireportid) {
		BIAccessRightsForm form = new BIAccessRightsForm();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (bireportid != null) {
				params.put("bireportid", bireportid);
				Tbbireportmain bir = (Tbbireportmain) dbService
						.executeUniqueHQLQuery("FROM Tbbireportmain WHERE bireportid=:bireportid", params);
				if (bir != null) {
					if (bir.getStatus() != null) {
						// New or Returned
						if (bir.getStatus().equals("0")) {
							if (bir.getReportedby() != null
									&& bir.getReportedby().equals(UserUtil.securityService.getUserName())) {
								form.setBtnStartRpt(true);
							}
						}
						// On-going
						if (bir.getStatus().equals("1") || bir.getStatus().equals("4")) {
							if (bir.getReportedby() != null
									&& bir.getReportedby().equals(UserUtil.securityService.getUserName())) {
								form.setBtnSave(true);
								form.setBtnSubmit(true);
								form.setBtnDelete(true);
								form.setReadOnly(false);
							}
						}
						// For Review - BIS
						if (bir.getStatus().equals("2")) {
							String supervisor = new DefaultUsers(bir.getCompanycode()).getBisupervisor();
							if (supervisor != null && supervisor.equals(UserUtil.securityService.getUserName())) {
								form.setBtnReturn(true);
								form.setBtnReview(true);
								form.setBtnCancel(true);
							}
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
	 * --Update BI Report status--
	 * 
	 * @author Kevin
	 * @return String = success, otherwise failed
	 */
	@Override
	public String submitBiReport(String bireportid, String status, String reasonforreturn) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (bireportid != null && status != null) {
				params.put("bireportid", bireportid);
				Tbbireportmain r = (Tbbireportmain) dbService
						.executeUniqueHQLQuery("FROM Tbbireportmain WHERE bireportid=:bireportid", params);
				if (r != null) {
					r.setStatusdatetime(new Date());
					// For Review
					if (status.equals("2")) {
						r.setReviewedby(new DefaultUsers(r.getCompanycode()).getBisupervisor());
					}
					// Reviewed
					if (status.equals("3")) {
						r.setRevieweddatetime(new Date());
						// Set status to Completed (Tbbirequest)
						params.put("birequestid", r.getBirequestid());
						int res = dbService.executeUpdate(
								"UPDATE Tbbirequest SET status='5' WHERE birequestid=:birequestid", params);
						if (res > 0) {
							// Update status (Tbinvestigationinst) Inside
							// application
							if (r.getAppno() != null) {
								params.put("appno", r.getAppno());
								params.put("cifno", r.getCifno());
								params.put("invsttype", "BI");
								Integer a = (Integer) dbService.executeUniqueSQLQuery(
										"SELECT COUNT(*) FROM TBBIREQUEST WHERE appno=:appno AND cifno=:cifno AND status IN ('0','1','2','6	')",
										params);
								if (a == null || (a != null && a == 0)) {
									// Completed
									dbService.executeUpdate(
											"UPDATE Tbinvestigationinst SET status='3' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype",
											params);
								} else {
									// On-Process
									dbService.executeUpdate(
											"UPDATE Tbinvestigationinst SET status='2' WHERE appno=:appno AND cifno=:cifno AND investigationtype=:invsttype",
											params);
								}
							}
						}
					}

					// Return
					if (status.equals("4")) {
						r.setReasonforreturn(reasonforreturn);

						// Set status to Report Returned (Tbbirequest)
						params.put("birequestid", r.getBirequestid());
						dbService.executeUpdate("UPDATE Tbbirequest SET status='6' WHERE birequestid=:birequestid",
								params);
					}

					// Cancelled
					if (status.equals("5")) {
						// Set status to Cancelled (Tbbirequest)
						params.put("birequestid", r.getBirequestid());
						dbService.executeUpdate("UPDATE Tbbirequest SET status='7' WHERE birequestid=:birequestid",
								params);
					}
					r.setDateupdated(new Date());
					r.setStatusdatetime(new Date());
					r.setStatus(status);
					if (dbService.saveOrUpdate(r)) {
						if (r.getStatus().equals("4")) {
							AuditLog.addAuditLog(
									AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("RETURN TO BI",
											AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL)),
									"User " + username + " Returned " + r.getAppno() + "'s Report " + r.getBireportid()
											+ " to Bureau Investigator.",
									username, new Date(), AuditLogEvents.LOAN_APPLICATION_INVESTIGATION_APPRAISAL);
						}
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
	 * --Get Blacklist record by cifno <b>(CIFSDB)</b>--
	 * 
	 * @author Kevin
	 * @return List<{@link BlacklistForm}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BlacklistForm> getBlacklistRecordByCifno(String cifno) {
		List<BlacklistForm> blk = new ArrayList<BlacklistForm>();
		Map<String, Object> params = HQLUtil.getMap();
		String table = "Tbblacklistindividual";
		try {
			if (cifno != null) {
				if (cifno.substring(0, 1).equals("2") || cifno.substring(0, 1).equals("3")) {
					table = "Tbblacklistcorporate";
				}
				params.put("cifno", cifno);
				blk = (List<BlacklistForm>) dbService.execSQLQueryTransformer(
						"SELECT m.blacklistid,m.cifno, m.fullname, s.description, m.startdate, m.enddate,(SELECT desc1 FROM Tbcodetable WHERE codename = 'BLACKLISTSTATUS' AND codevalue=m.status) as status,  "
								+ "(SELECT desc1 FROM Tbcodetable WHERE codename = 'BLACKLISTSOURCE' AND codevalue=m.blacklistsource) as source FROM Tbblacklistmain m ,"
								+ table + " s WHERE m.cifno=s.cifno AND m.cifno=:cifno",
						params, BlacklistForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return blk;
	}

	/**
	 * --Get BI Report list by appno--
	 * 
	 * @author Kevin
	 * @return List<{@link Tbbireportmain}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbbireportmain> getBiReportListByAppno(String appno) {
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbbireportmain> report = new ArrayList<Tbbireportmain>();
		StringBuilder hql = new StringBuilder();
		params.put("appno", appno);
		try {
			hql.append(
					"SELECT a.bireportid, a.birequestid, a.appno, a.cifno, a.customername, a.customertype, (SELECT fullname FROM TBUSER WHERE username=a.reportedby) as reportedby, ");
			hql.append(
					"(SELECT fullname FROM TBUSER WHERE username=a.requestedby) as requestedby, (SELECT fullname FROM TBUSER WHERE username=a.reviewedby) as reviewedby, ");
			hql.append(
					"(SELECT desc1 FROM TBCODETABLE WHERE codename='REPORTSTATUS' AND codevalue=a.status) as status, (SELECT companyname FROM TBCOMPANY WHERE companycode = a.companycode) as companycode ");
			hql.append("FROM Tbbireportmain a WHERE a.appno=:appno");

			report = (List<Tbbireportmain>) dbService.execSQLQueryTransformer(hql.toString(), params,
					Tbbireportmain.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return report;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbbirequest> getBiRequestListByCifno(String cifno) {
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbbirequest> request = new ArrayList<Tbbirequest>();
		StringBuilder hql = new StringBuilder();
		params.put("cifno", cifno);
		try {
			hql.append(
					"SELECT a.birequestid, a.appno, a.cifno, a.customername, a.customertype, (SELECT fullname FROM TBUSER WHERE username=a.assignedbi) as assignedbi, ");
			hql.append(
					"(SELECT fullname FROM TBUSER WHERE username=a.requestedby) as requestedby, (SELECT fullname FROM TBUSER WHERE username=a.assignedbisupervisor) as assignedbisupervisor, ");
			hql.append(
					"(SELECT desc1 FROM TBCODETABLE WHERE codename='REPORTSTATUS' AND codevalue=a.status) as status, (SELECT companyname FROM TBCOMPANY WHERE companycode = a.companycode) as companycode ");
			hql.append("FROM Tbbirequest a WHERE a.cifno=:cifno");

			request = (List<Tbbirequest>) dbService.execSQLQueryTransformer(hql.toString(), params, Tbbirequest.class,
					1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbbireportmain> getBiReportListByRequestId(String birequestid) {
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbbireportmain> report = new ArrayList<Tbbireportmain>();
		StringBuilder hql = new StringBuilder();
		params.put("birequestid", birequestid);
		try {
			hql.append(
					"SELECT a.bireportid, a.birequestid, a.appno, a.cifno, a.customername, a.customertype, (SELECT fullname FROM TBUSER WHERE username=a.reportedby) as reportedby, ");
			hql.append(
					"(SELECT fullname FROM TBUSER WHERE username=a.requestedby) as requestedby, (SELECT fullname FROM TBUSER WHERE username=a.reviewedby) as reviewedby, ");
			hql.append(
					"(SELECT desc1 FROM TBCODETABLE WHERE codename='REPORTSTATUS' AND codevalue=a.status) as status, a.companycode ");
			hql.append("FROM Tbbireportmain a WHERE a.birequestid=:birequestid");

			report = (List<Tbbireportmain>) dbService.execSQLQueryTransformer(hql.toString(), params,
					Tbbireportmain.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return report;
	}

	@Override
	public String generateBAPXML(String bireportid, String cifno) {
		// TODO Auto-generated method stub
		LoggerUtil.info(">>>>>>>>>>>>>>>>> Generating of BAP XML - BI Report ID: " + bireportid + " CIF No.: " + cifno
				+ " <<<<<<<<<<<<<<", BureauInvestigationServiceImpl.class);
		SimpleDateFormat dt = new SimpleDateFormat("MM/dd/yyyy");
		SimpleDateFormat dtt = new SimpleDateFormat("MM-dd-yyyy");
		Date date = new Date();
		String str = dtt.format(date);
		String sfilename = bireportid + "_" + cifno + "_" + str + ".xml";
		String filename = sfilename.replaceAll("-", "");
		String urlDirectory = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("resources/generatedXML/");
		Map<String, Object> param = HQLUtil.getMap();
		param.put("bireportid", bireportid);
		param.put("cifno", cifno);
		PrintWriter out = null;
		try {
			File file = new File(urlDirectory);
			if (!file.exists()) {
				file.mkdirs();
			}
			out = new PrintWriter(new FileWriter(urlDirectory + "/" + filename));
			String customertype = cifno.substring(0, 1);
			// Indiv
			if (customertype.equals("1")) {
				Tbmember cifIndiv = (Tbmember) dbService
						.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:cifno", param);
				out.append("<FILE>\n");
				out.append("\t<REC>\n");
				out.append("\t\t<IB_FNAM>");
				out.append(cifIndiv.getFirstname().toUpperCase());
				out.append("</IB_FNAM>\n");
				if (cifIndiv.getMiddlename() == null || cifIndiv.getMiddlename().equals(null)
						|| cifIndiv.getMiddlename().equals("null")) {
					out.append("\t\t<IB_MNAM/>");
				} else {
					out.append("\t\t<IB_MNAM>");
					out.append(cifIndiv.getMiddlename().toUpperCase());
					out.append("</IB_MNAM>\n");
				}
				out.append("\t\t<IB_LNAM>");
				out.append(cifIndiv.getLastname().toUpperCase());
				out.append("</IB_LNAM>\n");
				out.append("\t\t<IB_BIRTH>");
				String bday = dt.format(cifIndiv.getDateofbirth());
				out.append(bday);
				out.append("</IB_BIRTH>\n");
				out.append("\t\t<IB_TAN/>\n");
				out.append("\t\t<IB_SPOUSE/>\n");
				out.append("\t\t<IB_ADDR1/>\n");
				out.append("\t\t<RECKEY/>\n");
				out.append("\t</REC>\n");
				out.append("</FILE>");
			}
			// Corp
//			if (customertype.equals("2") || customertype.equals("3")) {
//				Tbcifcorporate cifCorp = (Tbcifcorporate) dbServiceCIF
//						.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno=:cifno", param);
//
//				out.append("\t<REC>\n");
//				out.append("\t\t<IB_NAMC>");
//				out.append(cifCorp.getCorporatename().toUpperCase());
//				out.append("</IB_NAMC>\n");
//				out.append("\t\t<IB_ADDR1>");
//				String comFulladdress1 = "";
//				if (cifCorp.getFulladdress1() == null || cifCorp.getFulladdress1().equals(null)) {
//					comFulladdress1 = " ";
//				} else {
//					comFulladdress1 = cifCorp.getFulladdress1().toUpperCase();
//				}
//				out.append(comFulladdress1);
//				out.append("</IB_ADDR1>\n");
//				out.append("\t\t<IB_SECN/>\n");
//				out.append("\t\t<IB_TAN/>\n");
//				out.append("\t\t<RECKEY/>\n");
//				out.append("\t\t<IB_BANKCD/>\n");
//				out.append("\t</REC>\n");
//			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		return filename;
	}

	@Override
	public List<AMLAWatchlistForm> getAMLARecordByCifno(String cifno) {
		// DANIEL.SEPT.09.2018
		List<AMLAWatchlistForm> amla = new ArrayList<AMLAWatchlistForm>();
//		Map<String, Object> params = HQLUtil.getMap();
//		String table = "Tbamlaindividual";
		try {
//			if (cifno != null) {
//				if (cifno.substring(0, 1).equals("2") || cifno.substring(0, 1).equals("3")) {
//					table = "Tbamlacorporate";
//				}
//				params.put("cifno", cifno);
//				amla = (List<AMLAWatchlistForm>) dbService.execSQLQueryTransformer(
//						"SELECT m.amlalistid, m.cifno, m.fullname, s.description, m.startdate, m.enddate,(SELECT desc1 FROM Tbcodetable WHERE codename = 'AMLASTATUS' AND codevalue=m.status) as status,  "
//								+ "(SELECT desc1 FROM Tbcodetable WHERE codename = 'AMLALISTSOURCE' AND codevalue=m.amlalist) as source FROM Tbamlalistmain m ,"
//								+ table + " s WHERE m.cifno=s.cifno AND m.cifno=:cifno",
//						params, AMLAWatchlistForm.class, 1);
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return amla;
	}

	@SuppressWarnings("unchecked")
	public List<BIEvalForm> getAllBIReportperAppNo(String appno, Integer evalreportid) {
		Map<String, Object> params = HQLUtil.getMap();
		List<BIEvalForm> bi = new ArrayList<BIEvalForm>();
		try {
			System.out.println("CHECK METHOD <>><><>");
			params.put("appno", appno);
			params.put("evalreportid", evalreportid);
			List<Tbevalbi> evalbi = (List<Tbevalbi>) dbService
					.executeListHQLQuery("FROM Tbevalbi WHERE appno=:appno AND evalreportid=:evalreportid", params);
			List<Tbinvestigationinst> inv = (List<Tbinvestigationinst>) dbService.executeListHQLQuery(
					"FROM Tbinvestigationinst WHERE appno=:appno AND investigationtype='BI' AND status='3'", params);
			if (evalbi == null || evalbi.size() == 0) {
				System.out.println("METHOD asdasdsa");
				Tbevalreport ereport = (Tbevalreport) dbService.executeUniqueHQLQuery(
						"FROM Tbevalreport WHERE appno=:appno AND evalreportid=:evalreportid", params);
				if (ereport != null) {
//					List<Tbinvestigationinst> inv = (List<Tbinvestigationinst>) dbService.executeListHQLQuery(
//							"FROM Tbinvestigationinst WHERE appno=:appno AND investigationtype='BI' AND status='3'",
//							params);//Completed
					if (inv != null) {
						for (Tbinvestigationinst i : inv) {
							params.put("cifno", i.getId().getCifno());
							BIEvalForm e = BILatestInvestigation(i.getId().getCifno());
							Tbevalbi bid = new Tbevalbi();
							TbevalbiId id = new TbevalbiId();
							id.setCifno(i.getId().getCifno());
							id.setEvalreportid(evalreportid);
							e.setSubjectname(i.getCustomername());
							e.setAppno(appno);
							e.setId(id);
							e.setDatecreated(new Date());
							e.setParticipationcode(i.getParticipationcode());
							e.setInstruction(i.getInstruction());
							bid.setSubjectname(i.getCustomername());
							bid.setAppno(appno);
							bid.setId(id);
							bid.setDatecreated(new Date());
							bid.setCic(e.getCic());
							bid.setCmap(e.getCmap());
							bid.setBapnfis(e.getBapnfis());
							bid.setCmapbireportid(e.getCmapbireportid());
							bid.setBapbireportid(e.getBapbireportid());
							bid.setCicbireportid(e.getCicbireportid());
							if (dbService.save(bid)) {
								bi.add(e);
							}
						}
					}
				}
			} else {
				ObjectMapper mapper = new ObjectMapper();
				for (Tbevalbi evbi : evalbi) {
					String jsonString = mapper.writeValueAsString(evbi);
					BIEvalForm e = mapper.readValue(jsonString, BIEvalForm.class);
					e.setCifno(evbi.getId().getCifno());
					if (inv != null) {
						for (Tbinvestigationinst i : inv) {
							System.out.println(mapper.writeValueAsString(i));
							if (e.getCifno().equals(i.getId().getCifno())) {
								e.setInstruction(i.getInstruction());
								e.setParticipationcode(i.getParticipationcode());
							}
						}
					}
					bi.add(e);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bi;
	}

	/* Daniel Fesalbon */
	public BIEvalForm BILatestInvestigation(String cifno) {
		Map<String, Object> params = HQLUtil.getMap();
		DBService dbService = new DBServiceImpl();
		params.put("cifno", cifno);
		BIEvalForm original = new BIEvalForm();
		try {
			Tbbireportmain bap = (Tbbireportmain) dbService.execSQLQueryTransformer(
					"SELECT TOP 1 * FROM Tbbireportmain WHERE bapresults IS NOT NULL AND cifno=:cifno AND status='3' ORDER BY revieweddatetime DESC",
					params, Tbbireportmain.class, 0);
			original.setBapbireportid(bap.getBireportid());
			original.setBapnfis(bap.getBapresults());
			Tbbireportmain cic = (Tbbireportmain) dbService.execSQLQueryTransformer(
					"SELECT TOP 1 * FROM Tbbireportmain WHERE cicresults IS NOT NULL AND cifno=:cifno AND status='3' ORDER BY revieweddatetime DESC",
					params, Tbbireportmain.class, 0);
			original.setCicbireportid(cic.getBireportid());
			original.setCic(cic.getCicresults());
			Tbbireportmain cmap = (Tbbireportmain) dbService.execSQLQueryTransformer(
					"SELECT TOP 1 * FROM Tbbireportmain WHERE cmapresults IS NOT NULL AND cifno=:cifno AND status='3' ORDER BY revieweddatetime DESC",
					params, Tbbireportmain.class, 0);
			original.setCmapbireportid(cmap.getBireportid());
			original.setCmap(cmap.getCmapresults());
			/* Do not delete these comments! */
//			Tbbireportmain blackint = (Tbbireportmain) dbService.execSQLQueryTransformer(
//					"SELECT TOP 1 * FROM Tbbireportmain WHERE blacklistresults IS NOT NULL AND cifno=:cifno AND status='3' ORDER BY revieweddatetime DESC",
//					params, Tbbireportmain.class, 0);
//			original.setBlacklistreportid(blackint.getBireportid());
//			original.setInternalblacklist(blackint.getBlacklistresults());
//			Tbbireportmain blackext = (Tbbireportmain) dbService.execSQLQueryTransformer(
//					"SELECT TOP 1 * FROM Tbbireportmain WHERE extblacklistresults IS NOT NULL AND cifno=:cifno AND status='3' ORDER BY revieweddatetime DESC",
//					params, Tbbireportmain.class, 0);
//			original.setExternalblacklist(blackext.getExtblacklistresults());
//			Tbbireportmain amlaint = (Tbbireportmain) dbService.execSQLQueryTransformer(
//					"SELECT TOP 1 * FROM Tbbireportmain WHERE amlawatchlistresults IS NOT NULL AND cifno=:cifno AND status='3' ORDER BY revieweddatetime DESC",
//					params, Tbbireportmain.class, 0);
//			original.setAmlareportid(amlaint.getBireportid());
//			original.setAmlawatchlistedinternal(amlaint.getAmlawatchlistresults());
//			Tbbireportmain amlaext = (Tbbireportmain) dbService.execSQLQueryTransformer(
//					"SELECT TOP 1 * FROM Tbbireportmain WHERE extamlawatchlistresults IS NOT NULL AND cifno=:cifno AND status='3' ORDER BY revieweddatetime DESC",
//					params, Tbbireportmain.class, 0);
//			original.setAmlawatchlistedexternal(amlaext.getExtamlawatchlistresults());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return original;
	}

	@Override
	public BiActivityForm getBiReportResultPerReportID(String cifno, String bapbireportid, String cmapbireportid,
			String cicbireportid, String blacklistbireportid, String amlabireportid) {
		Map<String, Object> params = HQLUtil.getMap();
		BiActivityForm form = new BiActivityForm();
		try {
			params.put("bap", bapbireportid);
			params.put("cmap", cmapbireportid);
			params.put("cic", cicbireportid);
			params.put("blacklist", blacklistbireportid);
			params.put("amla", amlabireportid);
			params.put("cifno", cifno);
			Tbbiactivity bap = (Tbbiactivity) dbService.executeUniqueHQLQuery(
					"FROM Tbbiactivity WHERE bireportid=:bap AND biactivity='BAP' AND cifno=:cifno", params);
			Tbbiactivity cmap = (Tbbiactivity) dbService.executeUniqueHQLQuery(
					"FROM Tbbiactivity WHERE bireportid=:bap AND biactivity='CMAP' AND cifno=:cifno", params);
			Tbbiactivity cic = (Tbbiactivity) dbService.executeUniqueHQLQuery(
					"FROM Tbbiactivity WHERE bireportid=:bap AND biactivity='CIC' AND cifno=:cifno", params);
			Tbbiactivity blacklistinternal = (Tbbiactivity) dbService.executeUniqueHQLQuery(
					"FROM Tbbiactivity WHERE bireportid=:bap AND biactivity='BLACKLISTINTERNAL' AND cifno=:cifno",
					params);
			Tbbiactivity blacklistexternal = (Tbbiactivity) dbService.executeUniqueHQLQuery(
					"FROM Tbbiactivity WHERE bireportid=:bap AND biactivity='BLACKLISTEXTERNAL' AND cifno=:cifno",
					params);
			Tbbiactivity amlainternal = (Tbbiactivity) dbService.executeUniqueHQLQuery(
					"FROM Tbbiactivity WHERE bireportid=:bap AND biactivity='AMLAINTERNAL' AND cifno=:cifno", params);
			Tbbiactivity amlaexternal = (Tbbiactivity) dbService.executeUniqueHQLQuery(
					"FROM Tbbiactivity WHERE bireportid=:bap AND biactivity='AMLAEXTERNAL' AND cifno=:cifno", params);
			form.setBapnfis(bap);
			form.setCmap(cmap);
			form.setCic(cic);
			form.setBlacklistinternal(blacklistinternal);
			form.setBlacklistexternal(blacklistexternal);
			form.setAmlawatchlistinternal(amlainternal);
			form.setAmlawatchlistexternal(amlaexternal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String saveOrUpdateCMAP(Tbbicmap data) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		DBService dbService = new DBServiceImpl();
		try {
			if (data.getCmapid() != null) {
				params.put("biID", data.getCmapid());
				Tbbicmap row = (Tbbicmap) dbService.executeUniqueHQLQuery("FROM Tbbicmap WHERE cmapid=:biID", params);
				if (row != null) {
					row.setBireportid(data.getBireportid());
					row.setCifno(data.getCifno());
					row.setAppno(data.getAppno());
					row.setDmsid(data.getDmsid());
					row.setDescription(data.getDescription());
					row.setReportdate(data.getReportdate());
					row.setRepo(data.getRepo());
					row.setReporttype(data.getReporttype());
					row.setRecordname(data.getRecordname());
					row.setAddress(data.getAddress());
					row.setAmount(data.getAmount());
					row.setDraweebank(data.getDraweebank());
					row.setReasonreturned(data.getReasonreturned());
					row.setRemarks(data.getRemarks());
					row.setStatus(data.getStatus());
					row.setDatereporteddt(data.getDatereporteddt());
					row.setDateofbirth(data.getDateofbirth());
					row.setTelephoneno(data.getTelephoneno());
					row.setDatedisconnected(data.getDatedisconnected());
					row.setPastdue(data.getPastdue());
					if (dbService.saveOrUpdate(row)) {
						flag = "success";
					}
				}
			} else {
				Tbbicmap n = new Tbbicmap();
				n.setBireportid(data.getBireportid());
				n.setCifno(data.getCifno());
				n.setAppno(data.getAppno());
				n.setDmsid(data.getDmsid());
				n.setDescription(data.getDescription());
				n.setReportdate(data.getReportdate());
				n.setRepo(data.getRepo());
				n.setReporttype(data.getReporttype());
				n.setRecordname(data.getRecordname());
				n.setAddress(data.getAddress());
				n.setAmount(data.getAmount());
				n.setDraweebank(data.getDraweebank());
				n.setReasonreturned(data.getReasonreturned());
				n.setRemarks(data.getRemarks());
				n.setStatus(data.getStatus());
				n.setDatereporteddt(data.getDatereporteddt());
				n.setDateofbirth(data.getDateofbirth());
				n.setTelephoneno(data.getTelephoneno());
				n.setDatedisconnected(data.getDatedisconnected());
				n.setPastdue(data.getPastdue());
				if (dbService.save(n)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteCMAP(String biID) {
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (biID != null) {
				params.put("biID", biID);
				Integer r = dbService.executeUpdate("DELETE FROM Tbbicmap WHERE cmapid=:biID", params);
				if (r != null && r == 1) {
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
	public List<Tbbicmap> getCMAPList(String appno, String type) {
		List<Tbbicmap> list = new ArrayList<Tbbicmap>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (appno != null && type != null) {
				params.put("appno", appno);
				params.put("type", type);
				list = (List<Tbbicmap>) dbService
						.executeListHQLQuery("FROM Tbbicmap WHERE appno=:appno AND reporttype=:type", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}