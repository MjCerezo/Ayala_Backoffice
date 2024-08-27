package com.etel.qde;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudfoundry.org.codehaus.jackson.map.DeserializationConfig;
import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;

import com.cifsdb.data.AuditTrail;
import com.cifsdb.data.Tbcifcorporate;
import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbcustomerrelationship;
import com.cifsdb.data.Tbteams;
import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbappbeneficiary;
import com.coopdb.data.Tbappemployment;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbchapter;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbcountry;
import com.coopdb.data.Tbemployee;
import com.coopdb.data.Tbloanoffset;
import com.coopdb.data.TbloanoffsetId;
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tbloscorporate;
import com.coopdb.data.Tblosindividual;
import com.coopdb.data.Tblosmain;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tblstappindividual;
import com.coopdb.data.Tblstappcorporate;
import com.coopdb.data.Tblstcomakers;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmemberbusiness;
import com.coopdb.data.Tbmembercreditcardinfo;
import com.coopdb.data.Tbmemberdependents;
import com.coopdb.data.Tbmemberdosri;
import com.coopdb.data.Tbmemberemployment;
import com.coopdb.data.Tbmemberfinancialinfo;
import com.coopdb.data.Tbmemberrelationship;
import com.coopdb.data.Tbmembershipapp;
import com.coopdb.data.Tbreferror;
import com.coopdb.data.Tbuser;
import com.coopdb.data.Tbworkflowprocess;
import com.etel.audittrail.AuditTrailFacade;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.company.CompanyService;
import com.etel.company.CompanyServiceImpl;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.documentation.DocumentationServiceImpl;
import com.etel.documents.DocumentServiceImpl;
import com.etel.forms.FormValidation;
import com.etel.forms.TblstappForm;
import com.etel.history.HistoryService;
import com.etel.history.HistoryServiceImpl;
import com.etel.loanproduct.LoanProductService;
import com.etel.loanproduct.LoanProductServiceImpl;
import com.etel.loanproduct.forms.LoanFeeInputForm;
import com.etel.qdeforms.LoansForm;
import com.etel.qdeforms.QDEParameterForm;
import com.etel.utils.ApplicationNoGenerator;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.CIFNoGenerator;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class QDEServiceImpl implements QDEService {
	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> param = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private GetterSetter transfer = new GetterSetter(); /////////////////// DANI
	private FullDataEntryServiceImpl fde = new FullDataEntryServiceImpl();
	private String username = secservice.getUserName();

	public String createApplication(QDEParameters qdeparams) {
		try {
			param.put("companycode", qdeparams.getCompanycode());
			if (qdeparams.getMembershipappid() != null && !qdeparams.getMembershipappid().equals("")) {
//				System.out.println("EXISTING APPLICATION BUT NOT MEMBER");
				param.put("memappid", qdeparams.getMembershipappid());
				// EXISTING
				Tbmembershipapp m = (Tbmembershipapp) dbService
						.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:memappid", param);
				if (m != null) {
					return "<b>" + m.getLastname() + ", " + m.getFirstname() + "" + m.getMiddlename()
							+ "</b> has an existing Application.";
//					return m.getMembershipappid();
				}
			} else if (qdeparams.getMembershipid() != null && !qdeparams.getMembershipid().equals("")
					&& !qdeparams.getMembershiptype().equals("2")) {
				// RETURNEE
				param.put("membershipid", qdeparams.getMembershipid());
				Tbmember mem = (Tbmember) dbService.executeUniqueHQLQuery(
						"FROM Tbmember WHERE membershipid=:membershipid and companycode =:companycode", param);
				if (mem != null && mem.getMembershipstatus().equals("3")) { // ifRESIGN
					Tbmembershipapp m = new Tbmembershipapp();
					param.put("encoder", secservice.getUserName());
					Tbuser encoder = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:encoder",
							param);
					String newappid = ApplicationNoGenerator.generateID("A", encoder.getCoopcode());
					ObjectMapper mapper = new ObjectMapper();
					String value = mapper.writeValueAsString(mem);
					mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
					m = mapper.readValue(value, Tbmembershipapp.class);
					m.setMembershipappstatus("1");
					m.setMembershipstatus("7");
					m.setApplicanttype("1");
					m.setAssignedto(null);
					m.setAssigneddate(null);
					m.setEncodedby(secservice.getUserName());
					m.setEncodeddate(new Date());
					m.setApplicationdate(new Date());
					m.setMembershipappid(newappid);
					m.setCoopcode(encoder.getCoopcode());
					m.setMembershipclass(qdeparams.getMembershiptype());
					m.setOriginatingbranch(qdeparams.getBranchcode());
					m.setServicestatus(qdeparams.getServicestatus());
					if (dbService.save(m)) {
						Map<String, String> mapids = new HashMap<String, String>();
						mapids.put("membershipappid", m.getMembershipappid());
						fde.createDOSRI(m.getMembershipappid());
						DocumentServiceImpl.createInitialDocumentChecklist("010", mapids);
						return m.getMembershipappid();
					}

				} else if (mem != null && !mem.getMembershipstatus().equals("3")
						&& !qdeparams.getMembershiptype().equals("2")) {
					return "Member already registered in the institution.";
				} else {
					return "Failed.";
				}
			} else {
				// NEW
				if (qdeparams.getApplicationtype() != null) {
					if (qdeparams.getApplicationtype().equals("2")) {
						return "<font color=\"red\">Please fill up mandatory fields properly!</font>";
					}
					if (qdeparams.getApplicationtype().equals("1")) {
						if (qdeparams.getMembershipappid() != null && !qdeparams.getMembershipappid().equals("")) {
							System.out.println(qdeparams.getMembershipappid());
							param.put("appid", qdeparams.getMembershipappid());
							Tbmembershipapp vldt = (Tbmembershipapp) dbService
									.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:appid", param);
							if (vldt != null) {
								return "<b>" + vldt.getLastname() + ", " + vldt.getFirstname() + ""
										+ vldt.getMiddlename() + "</b> has an existing Application.";
							}
						}

						Tbmembershipapp m = new Tbmembershipapp();
						if (qdeparams.getFirstname() != null) {
							m.setFirstname(qdeparams.getFirstname());
						} else {
							return "<b>First name</b> cannot be empty<br>";
						}
						if (qdeparams.getLastname() != null) {
							m.setLastname(qdeparams.getLastname());
						} else {
							return "<b>Last name</b> cannot be empty<br>";
						}
						m.setMiddlename(qdeparams.getMiddlename());
						if (qdeparams.getMembershiptype().equals("2")) {
							param.put("employeeid", qdeparams.getEmployeeid());
							Tbmember mem = (Tbmember) dbService.executeUniqueHQLQuery(
									"FROM Tbmember WHERE employeeid=:employeeid and companycode=:companycode", param);
							if (mem != null) {
								param.put("memberid", mem.getMembershipid());
								param.put("firstname", "%" + m.getFirstname() + "%");
								param.put("lastname", "%" + m.getLastname() + "%");
								param.put("middlename",
										m.getMiddlename() != null ? "%" + m.getMiddlename() + "%" : "%%");
								Tbmemberrelationship rel = (Tbmemberrelationship) dbService.executeUniqueHQLQuery(
										"FROM Tbmemberrelationship WHERE " + "lastname like:lastname AND "
												+ "firstname like:firstname AND middlename like:middlename AND "
												+ "mainmemberid =:memberid",
										param);
								if (rel != null) {
									m.setCountry1(rel.getCountry() != null ? rel.getCountry() : null);
									m.setStateprovince1(rel.getStateprovince() != null ? rel.getStateprovince() : null);
									m.setRegion1(rel.getRegion() != null ? rel.getRegion() : null);
									m.setCity1(rel.getCity() != null ? rel.getCity() : null);
									m.setPostalcode1(rel.getPostalcode() != null ? rel.getPostalcode() : null);
									m.setSubdivision1(rel.getSubdivision() != null ? rel.getSubdivision() : null);
									m.setBarangay1(rel.getBarangay() != null ? rel.getBarangay() : null);
									m.setStreetnoname1(rel.getStreetnoname() != null ? rel.getStreetnoname() : null);
									m.setDateofbirth(rel.getDateofbirth() != null ? rel.getDateofbirth() : null);
									m.setAge(rel.getAge() != null ? rel.getAge() : null);
									m.setGender(rel.getGender() != null ? rel.getGender() : null);
									m.setSuffix(rel.getSuffix() != null ? rel.getSuffix() : null);
									m.setPhoneno(rel.getContactno() != null && rel.getContactno().length() > 3
											? rel.getContactno().substring(3)
											: null);
									m.setAreacodephone(rel.getContactno() != null && rel.getContactno().length() > 3
											? rel.getContactno().substring(0, 2)
											: null);
								}
							}
						}
						param.put("user", secservice.getUserName());
						Tbuser user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:user",
								param);

						String newappid = ApplicationNoGenerator.generateID("A", user.getCoopcode());

						m.setMembershipappstatus("1");
						m.setMembershipstatus("7");
						m.setApplicanttype(qdeparams.getApplicationtype());
						m.setMembershipappid(newappid);
						m.setApplicationdate(new Date());
						m.setEncodedby(secservice.getUserName());
						m.setCoopcode(user.getCoopcode());
						m.setDateofbirth(qdeparams.getDateofbirth());
						m.setCountry1("PH");
						m.setCountry2("PH");
						m.setSpousecountry("PH");
						m.setMothercountry("PH");
						m.setFathercountry("PH");
						m.setNationality("PH");
						m.setCountryofbirth("PH");
						m.setMembershipclass(qdeparams.getMembershiptype());
						m.setOriginatingbranch(qdeparams.getBranchcode());
						m.setCompanycode(qdeparams.getCompanycode());
						m.setBranch(qdeparams.getBranchcode());
						m.setServicestatus(qdeparams.getServicestatus());
						if (qdeparams.getEmployeeid() != null && !qdeparams.getMembershiptype().equals("2")) {
							m.setEmployeeid(qdeparams.getEmployeeid());
							m.setWithmoa(true);
							param.put("employeeid", qdeparams.getEmployeeid());
							Tbemployee e = (Tbemployee) dbService.executeUniqueHQLQuery(
									"FROM Tbemployee WHERE employeeid=:employeeid and companycode=:companycode", param);
							// GET FROM TBEMPLOYEE | SAVE TO TBMEMBERSHIPAPP
							m = transfer.getEmployee(m, e);
							// GET FROM TBEMPLOYEE | SAVE TO TBAPPEMPLOYMENT
							Tbappemployment emp = new Tbappemployment();
							emp = transfer.setEmployment(emp, e, newappid);
							// GET FROM TBEMPLOYEE | SAVE TO TBAPPBENEFICIARY
							Tbappbeneficiary bnemp = new Tbappbeneficiary();
							bnemp = transfer.getEmployeeBeneficiary(bnemp, e, newappid);
							m.setCompanycode(e.getCompanycode());
						}
						if (dbService.save(m)) {
							Map<String, String> mapids = new HashMap<String, String>();
							mapids.put("membershipappid", m.getMembershipappid());
							fde.createDOSRI(m.getMembershipappid());
							if (qdeparams.getMembershiptype().equals("2")) {
								Tbreferror r = new Tbreferror();
								r.setEmpid(qdeparams.getEmployeeid());
								param.put("employeeid", qdeparams.getEmployeeid());
								Tbmember mem = (Tbmember) dbService.executeUniqueHQLQuery(
										"FROM Tbmember WHERE employeeid=:employeeid and companycode=:companycode",
										param);
								r.setEmployername(mem.getCompanyname());
								r.setMembershipappid(m.getMembershipappid());
								r.setMembershipid(null);
								r.setPosition((String) dbService.executeUniqueHQLQuery(
										"SELECT ISNULL(position,'') FROM Tbmemberemployment WHERE employeeid=:employeeid and companyid=:companycode",
										param));
								r.setReferrorname(
										mem.getFirstname() + " " + mem.getMiddlename() + " " + mem.getLastname());
								r.setRefmemberid(mem.getMembershipid());
								System.out.println("Creating referror entry : " + fde.addReferrorPerParam(r));
								param.put("relappid", m.getMembershipappid());
								param.put("memberid", mem.getMembershipid());
								String middlename = "%%";
								param.put("firstname", "%" + qdeparams.getFirstname() + "%");
								param.put("lastname", "%" + qdeparams.getLastname() + "%");
								middlename = qdeparams.getMiddlename() != null ? qdeparams.getMiddlename() : "%%";
								param.put("middlename", middlename);

								System.out.println(dbService.executeUpdate(
										"UPDATE TBMEMBERRELATIONSHIP SET relatedappid=:relappid WHERE "
												+ "firstname like:firstname AND " + "lastname like:lastname AND "
												+ "middlename like:middlename AND " + "mainmemberid =:memberid",
										param));

							}
							DocumentServiceImpl.createInitialDocumentChecklist("010", mapids);
							AuditLog.addAuditLog(
									AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_CREATE_MEMBERSHIP_APPLICATION),
									"User " + username + " Created New Membership: " + m.getMembershipappid() + ".",
									username, new Date(),
									AuditLogEvents.getEventModule(AuditLogEvents.M_CREATE_MEMBERSHIP_APPLICATION));
							return m.getMembershipappid();
						}
					}
				} else {
					return "<font color=\"red\">Please fill up mandatory fields!</font>";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public DedupeResult dedupeResults(QDEParameters qdeparams) {
		DedupeResult d = new DedupeResult();
		try {
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(mapper.writeValueAsString(qdeparams));
			param.put("lname", qdeparams.getLastname() == null ? "%%" : "%" + qdeparams.getLastname() + "%");
			param.put("fname", qdeparams.getFirstname() == null ? "%%" : "%" + qdeparams.getFirstname() + "%");
			param.put("mname", qdeparams.getMiddlename() == null ? "%%" : "%" + qdeparams.getMiddlename() + "%");
			param.put("empid", qdeparams.getEmployeeid() == null ? "%%" : "%" + qdeparams.getEmployeeid() + "%");
			param.put("companycode",
					qdeparams.getCompanycode() == null ? "%%" : "%" + qdeparams.getCompanycode() + "%");
			param.put("branch", "%%");
//			param.put("branch", qdeparams.getBranchcode() == null ? "%%" : "%" + qdeparams.getBranchcode() + "%");
			param.put("memberid", qdeparams.getMembershipid() == null ? "%%" : "%" + qdeparams.getMembershipid() + "%");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String date = null;
			System.out.println(param);
			if (qdeparams.getDateofbirth() != null) {
				date = formatter.format(qdeparams.getDateofbirth());
			}
			if (qdeparams.getMembershiptype().equals("2")) {
				if (qdeparams.getDateofbirth() == null) {
					d.setListmembershipapp((List<Tbmembershipapp>) dbService
							.execSQLQueryTransformer("SELECT membershipappid, lastname, firstname, middlename, suffix, "
									+ "applicationdate, membershipappstatus, encodedby, encodeddate, applicanttype, dateofbirth FROM Tbmembershipapp "
									+ "WHERE (employeeid like:empid "
									+ "and ISNULL(branch,'') like:branch and ISNULL(companycode,'') like:companycode)",
//									+ "(firstname like :fname AND (middlename like :mname or lastname like :mname))",
									param, Tbmembershipapp.class, 1));
					d.setListmember((List<Tbmember>) dbService
							.execSQLQueryTransformer("SELECT membershipappid, lastname, firstname, middlename, suffix, "
									+ "membershipdate, membershipid, membershipstatus, dateofbirth, employeeid FROM Tbmember "
									+ "WHERE (employeeid like:empid "
									+ "and ISNULL(branch,'') like:branch and ISNULL(companycode,'') like:companycode and membershipid like:memberid)",
//									+ "(firstname like :fname AND (middlename like :mname or lastname like :mname))",
									param, Tbmember.class, 1));
					d.setListemployee((List<Tbemployee>) dbService.execSQLQueryTransformer(
							"SELECT emp.employeeid, emp.lastname, emp.firstname, emp.middlename, emp.suffix, "
									+ "emp.servicestatus, emp.companyname, emp.employmentstatus, emp.dateofbirth, mem.membershipstatus as gender, mem.membershipid as emailadd "
									+ "FROM Tbemployee emp left join Tbmember mem on emp.employeeid = mem.employeeid and ISNULL(emp.companycode,'') = mem.companycode "
									+ "WHERE (emp.lastname like :lname AND emp.firstname like :fname AND emp.middlename like :mname AND emp.employeeid like:empid "
									+ "and ISNULL(emp.branch,'') like:branch and ISNULL(emp.companycode,'') like:companycode and (mem.membershipid like:memberid "
									+ (qdeparams.getMembershipid() == null ? "or mem.membershipappid is null)" : ")")
									+ ")",
//									+ "(firstname like :fname AND (middlename like :mname or lastname like :mname))",
							param, Tbemployee.class, 1));
				} else {
					d.setListmembershipapp((List<Tbmembershipapp>) dbService
							.execSQLQueryTransformer("SELECT membershipappid, lastname, firstname, middlename, suffix, "
									+ "applicationdate, membershipappstatus, encodedby, encodeddate, applicanttype, dateofbirth FROM Tbmembershipapp "
									+ "WHERE (dateofbirth BETWEEN '" + date + " 00:00:00' AND '" + date + " 23:59:00') "
									+ "AND (lastname like :lname AND firstname like :fname AND middlename like :mname "
									+ "and ISNULL(branch,'') like:branch and ISNULL(companycode,'') like:companycode)",
//									+ "(firstname like :fname AND (middlename like :mname or lastname like :mname))",
									param, Tbmembershipapp.class, 1));

					d.setListmember((List<Tbmember>) dbService
							.execSQLQueryTransformer("SELECT membershipappid, lastname, firstname, middlename, suffix, "
									+ "membershipdate, membershipid, membershipstatus, employeeid FROM Tbmember "
									+ "WHERE (dateofbirth BETWEEN '" + date + " 00:00:00' AND '" + date + " 23:59:00') "
									+ "AND (lastname like :lname AND firstname like :fname AND middlename like :mname "
									+ "and ISNULL(branch,'') like:branch and ISNULL(companycode,'') like:companycode and membershipid like:memberid)",
//									+ "(firstname like :fname AND (middlename like :mname or lastname like :mname))",
									param, Tbmember.class, 1));

					d.setListemployee((List<Tbemployee>) dbService.execSQLQueryTransformer(
							"SELECT emp.employeeid, emp.lastname, emp.firstname, emp.middlename, emp.suffix, "
									+ "emp.servicestatus, emp.companyname, emp.employmentstatus, emp.dateofbirth, mem.membershipstatus as gender, mem.membershipid as emailadd "
									+ "FROM Tbemployee emp left join Tbmember mem on emp.employeeid = mem.employeeid and ISNULL(emp.companycode,'') = mem.companycode "
									+ "WHERE (emp.dateofbirth BETWEEN '" + date + " 00:00:00' AND '" + date
									+ " 23:59:00') "
									+ "AND (emp.lastname like :lname AND emp.firstname like :fname AND emp.middlename like :mname AND emp.employeeid like:empid "
									+ "and ISNULL(emp.branch,'') like:branch and ISNULL(emp.companycode,'') like:companycode and (mem.membershipid like:memberid "
									+ (qdeparams.getMembershipid() == null ? "or mem.membershipappid is null)" : ")")
									+ ")",
//									+ "(firstname like :fname AND (middlename like :mname or lastname like :mname))",
							param, Tbemployee.class, 1));
				}
			} else {
				if (qdeparams.getDateofbirth() == null) {
					d.setListmembershipapp((List<Tbmembershipapp>) dbService
							.execSQLQueryTransformer("SELECT membershipappid, lastname, firstname, middlename, suffix, "
									+ "applicationdate, membershipappstatus, encodedby, encodeddate, applicanttype, dateofbirth FROM Tbmembershipapp "
									+ "WHERE (lastname like :lname AND firstname like :fname AND middlename like :mname AND employeeid like:empid "
									+ "and ISNULL(branch,'') like:branch and ISNULL(companycode,'') like:companycode)",
//								+ "(firstname like :fname AND (middlename like :mname or lastname like :mname))",
									param, Tbmembershipapp.class, 1));
					d.setListmember((List<Tbmember>) dbService
							.execSQLQueryTransformer("SELECT membershipappid, lastname, firstname, middlename, suffix, "
									+ "membershipdate, membershipid, membershipstatus, dateofbirth, employeeid FROM Tbmember "
									+ "WHERE (lastname like :lname AND firstname like :fname AND middlename like :mname AND employeeid like:empid "
									+ "and ISNULL(branch,'') like:branch and ISNULL(companycode,'') like:companycode and membershipid like:memberid)",
//								+ "(firstname like :fname AND (middlename like :mname or lastname like :mname))",
									param, Tbmember.class, 1));
					d.setListemployee((List<Tbemployee>) dbService.execSQLQueryTransformer(
							"SELECT emp.employeeid, emp.lastname, emp.firstname, emp.middlename, emp.suffix, "
									+ "emp.servicestatus, emp.companyname, emp.employmentstatus, emp.dateofbirth, mem.membershipstatus as gender, mem.membershipid as emailadd "
									+ "FROM Tbemployee emp left join Tbmember mem on emp.employeeid = mem.employeeid and ISNULL(emp.companycode,'') = mem.companycode "
									+ "WHERE (emp.lastname like :lname AND emp.firstname like :fname AND emp.middlename like :mname AND emp.employeeid like:empid "
									+ "and ISNULL(emp.branch,'') like:branch and ISNULL(emp.companycode,'') like:companycode and (mem.membershipid like:memberid "
									+ (qdeparams.getMembershipid() == null ? "or mem.membershipappid is null)" : ")")
									+ ")",
//								+ "(firstname like :fname AND (middlename like :mname or lastname like :mname))",
							param, Tbemployee.class, 1));
				} else {
					d.setListmembershipapp((List<Tbmembershipapp>) dbService
							.execSQLQueryTransformer("SELECT membershipappid, lastname, firstname, middlename, suffix, "
									+ "applicationdate, membershipappstatus, encodedby, encodeddate, applicanttype, dateofbirth FROM Tbmembershipapp "
									+ "WHERE (dateofbirth BETWEEN '" + date + " 00:00:00' AND '" + date + " 23:59:00') "
									+ "AND (lastname like :lname AND firstname like :fname AND middlename like :mname "
									+ "and ISNULL(branch,'') like:branch and ISNULL(companycode,'') like:companycode)",
//								+ "(firstname like :fname AND (middlename like :mname or lastname like :mname))",
									param, Tbmembershipapp.class, 1));

					d.setListmember((List<Tbmember>) dbService
							.execSQLQueryTransformer("SELECT membershipappid, lastname, firstname, middlename, suffix, "
									+ "membershipdate, membershipid, membershipstatus, employeeid FROM Tbmember "
									+ "WHERE (dateofbirth BETWEEN '" + date + " 00:00:00' AND '" + date + " 23:59:00') "
									+ "AND (lastname like :lname AND firstname like :fname AND middlename like :mname "
									+ "and ISNULL(branch,'') like:branch and ISNULL(companycode,'') like:companycode and membershipid like:memberid)",
//								+ "(firstname like :fname AND (middlename like :mname or lastname like :mname))",
									param, Tbmember.class, 1));

					d.setListemployee((List<Tbemployee>) dbService.execSQLQueryTransformer(
							"SELECT emp.employeeid, emp.lastname, emp.firstname, emp.middlename, emp.suffix, "
									+ "emp.servicestatus, emp.companyname, emp.employmentstatus, emp.dateofbirth, mem.membershipstatus as gender, mem.membershipid as emailadd "
									+ "FROM Tbemployee emp left join Tbmember mem on emp.employeeid = mem.employeeid and ISNULL(emp.companycode,'') = mem.companycode "
									+ "WHERE (emp.dateofbirth BETWEEN '" + date + " 00:00:00' AND '" + date
									+ " 23:59:00') "
									+ "AND (emp.lastname like :lname AND emp.firstname like :fname AND emp.middlename like :mname AND emp.employeeid like:empid "
									+ "and ISNULL(emp.branch,'') like:branch and ISNULL(emp.companycode,'') like:companycode and (mem.membershipid like:memberid "
									+ (qdeparams.getMembershipid() == null ? "or mem.membershipappid is null)" : ")")
									+ ")",
//								+ "(firstname like :fname AND (middlename like :mname or lastname like :mname))",
							param, Tbemployee.class, 1));
				}
			}
			AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_DEDUPE_MEMBER),
					"User " + username + " Dedupe Membership Record.", username, new Date(),
					AuditLogEvents.getEventModule(AuditLogEvents.M_DEDUPE_MEMBER));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	@SuppressWarnings("unchecked")
	public QDEParameterForm searchMember(String membershipid, String firstname, String lastname, String empid,
			String memberOrEmployee) {
		List<Tbmember> main = new ArrayList<Tbmember>();
		List<Tbemployee> emp = new ArrayList<Tbemployee>();
		QDEParameterForm form = new QDEParameterForm();
//		CodetableService code = new CodetableServiceImpl();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("lastname", lastname == null ? "%%" : "%" + lastname + "%");
		param.put("firstname", firstname == null ? "%%" : "%" + firstname + "%");

		try {
			/*
			 * if (membershipid != null) { main = (List<Tbmember>)
			 * dbService.executeListHQLQuery("FROM Tbmember WHERE membershipid=:member)",
			 * param); } else { main = (List<Tbmember>) dbService.executeListHQLQuery(
			 * "FROM Tbmember WHERE lastname LIKE :lastname AND firstname LIKE :firstname)",
			 * param); form.setMember(main); /** Member Main Bucket
			 **/
			/* } */
			if (membershipid == null && empid == null) {

				main = (List<Tbmember>) dbService.executeListHQLQuery(
						"FROM Tbmember WHERE lastname LIKE :lastname AND firstname LIKE :firstname)", param);
				form.setMember(main); /** Member Main Bucket **/
				emp = (List<Tbemployee>) dbService.executeListHQLQuery(
						"FROM Tbemployee WHERE lastname LIKE :lastname AND firstname LIKE :firstname)", param);
				form.setEmployee(emp); /** Member Main Bucket **/

			} else {
				if (membershipid != null) {
					param.put("member", membershipid);
					main = (List<Tbmember>) dbService.executeListHQLQuery("FROM Tbmember WHERE membershipid=:member)",
							param);
				} else if (empid != null) {
					param.put("empid", empid);
					main = (List<Tbmember>) dbService.executeListHQLQuery("FROM Tbmember WHERE employeeid=:empid)",
							param);
					emp = (List<Tbemployee>) dbService.executeListHQLQuery("FROM Tbemployee WHERE employeeid=:empid)",
							param);
				}
			}
			if (main != null && !main.isEmpty()) {
				for (Tbmember main2 : main) {
					param.put("status", main2.getMembershipstatus());
					Tbcodetable membershipstatus = (Tbcodetable) dbService.executeUniqueHQLQuery(
							"FROM Tbcodetable a WHERE a.id.codename='MEMBERSHIPSTATUS' AND a.id.codevalue=:status",
							param);
					param.put("class", main2.getMembershipclass() == null ? "" : main2.getMembershipclass());
					Tbcodetable membershipclass = (Tbcodetable) dbService.executeUniqueHQLQuery(
							"FROM Tbcodetable a WHERE a.id.codename = 'MEMBERSHIPCLASS' AND a.id.codevalue =:class",
							param);
					if (membershipstatus != null) {
						form.setMembershipstatus(membershipstatus.getDesc1());
					}
					if (membershipclass != null) {
						form.setMembershipclass(membershipclass.getDesc1());
					}
					param.put("branchcode", main2.getBranch());
					param.put("chapter", main2.getChaptercode());
					param.put("status", main2.getMembershipstatus());
					Tbcodetable membershipstatus1 = (Tbcodetable) dbService.executeUniqueHQLQuery(
							"FROM Tbcodetable a WHERE a.id.codename='MEMBERSHIPSTATUS' AND a.id.codevalue=:status",
							param);
					Tbbranch branch = (Tbbranch) dbService
							.executeUniqueHQLQuery("FROM Tbbranch a WHERE a.id.branchcode =:branchcode", param);
//					Tbchapter chapter = (Tbchapter) dbService
//							.executeUniqueHQLQuery("FROM Tbchapter a WHERE a.id.chaptercode =:chapter", param);
					// form.setMember(main); /** Member Main Bucket **/
					form.setMembershipstatus(membershipstatus1.getDesc1());
					form.setEmployeeid(main2.getEmployeeid());
//					form.setChapter(chapter.getChaptername());
					form.setBranch(branch.getBranchname());
					form.setCifno(main2.getMembershipid());
					form.setCount(main.size());
//					form.setMember(main); /** Member Main Bucket **/

				}
				form.setMember(main); /** Member Main Bucket **/
			}
			if (emp != null && !emp.isEmpty()) {
				form.setEmployee(emp);
				for (Tbemployee main2 : emp) {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public Tbmember getMember(String membershipid) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tbmember details = new Tbmember();
		try {
			if (membershipid != null) {
				param.put("membershipid", membershipid);
				details = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:membershipid",
						param);
				if (details != null) {
					param.put("countrycode", details.getNationality());
					// param.put("postalcode", details.getPostalcode1());
					if (details.getNationality() != null) {
						param.put("countrycode", details.getNationality());
						// param.put("postalcode", details.getPostalcode1());
						// Tbcountry nationality =
						// (Tbcountry)dbService.executeUniqueHQLQuery("SELECT
						// DISTINCT country FROM Tbcountry WHERE
						// code=:countrycode", param);
						Tbcountry nationality = (Tbcountry) dbService.execSQLQueryTransformer(
								"SELECT DISTINCT country FROM Tbcountry WHERE code=:countrycode", param,
								Tbcountry.class, 0);
						details.setNationality(nationality.getCountry().toString());
					}
					if (details.getGender() != null) {
						param.put("gender", details.getGender());
						Tbcodetable gender = (Tbcodetable) dbService.executeUniqueHQLQuery(
								"FROM Tbcodetable WHERE codename ='GENDER' AND codevalue=:gender", param);
						details.setGender(gender.getDesc1());
					}
					if (details.getCivilstatus() != null) {
						param.put("civilstatus", details.getCivilstatus());
						Tbcodetable civil = (Tbcodetable) dbService.executeUniqueHQLQuery(
								"FROM Tbcodetable WHERE codename ='CIVILSTATUS' AND codevalue=:civilstatus", param);
						details.setCivilstatus(civil.getDesc1());
					}
					if (details.getOwnershiptype1() != null && !details.getOwnershiptype1().equals("")) {
						param.put("ownership1", details.getOwnershiptype1());
						Tbcodetable ownership1 = (Tbcodetable) dbService.executeUniqueHQLQuery(
								"FROM Tbcodetable WHERE codename ='HOMEOWNERSHIP' AND codevalue=:ownership1", param);
						details.setOwnershiptype1(ownership1.getDesc1());
					}
					if (details.getOwnershiptype2() != null && !details.getOwnershiptype1().equals("")) {
						param.put("ownership2", details.getOwnershiptype2());
						Tbcodetable ownership2 = (Tbcodetable) dbService.executeUniqueHQLQuery(
								"FROM Tbcodetable WHERE codename ='HOMEOWNERSHIP' AND codevalue=:ownership2", param);
						details.setOwnershiptype2(ownership2.getDesc1());
					}
					details.setFirstname(details.getLastname()
							.concat(", ".concat(details.getFirstname().concat(" ".concat(details.getMiddlename())))));
					details.setStreetnoname1(details.getStreetnoname1().concat(details.getSubdivision1()
							+ " ".concat(details.getBarangay1() + " ".concat(details.getStateprovince1()
									+ " ".concat(details.getCity1() + " ".concat(details.getRegion1() + " "
											.concat(details.getCountry1() + " ".concat(details.getPostalcode1()))))))));
					AuditLog.addAuditLog(
							AuditLogEvents.getAuditLogEvents(
									AuditLogEvents.getEventID("DEDUPE", AuditLogEvents.LOAN_APPLICATION_ENCODING)),
							"User " + username + " Dedupe Approved Member.", username, new Date(),
							AuditLogEvents.LOAN_APPLICATION_ENCODING);
					return details;
				}
			}
		} catch (Exception e) {
//			System.out.println("sdsadsa");
			e.printStackTrace();
		}
		return details;
	}

	// LOANS Noreen 08/02/2018
	@Override
	public String setupNewApplication(QDEParameterForm form, List<Tbloans> loans) {
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		LoanProductService prodSrvc = new LoanProductServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tblstapp a = new Tblstapp();

		try {
			if (form.getMembershipid() != null && form.getAppno() != null) {
				a.setCifno(form.getMembershipid());
				a.setAppno(form.getAppno());
				param.put("user", UserUtil.securityService.getUserName());
				param.put("member", form.getMembershipid());
				Tbmember m = (Tbmember) dbService
						.executeUniqueHQLQueryMaxResultOne("FROM Tbmember WHERE membershipid=:member", param);
				// Noreen Additional Field for Loans 08-02-18
				a.setMembershipclass(form.getMembershipclass());
				a.setMembershipstatus(form.getMembershipstatus());
				a.setEmployeeid(form.getEmployeeid());
				a.setBranchcode(form.getBranch());
				// a.setFulladdress(form.getFulladdress1());
				a.setCifname(form.getCifname());
				a.setApplicationtype(form.getApplicationtype()); // Loan
				// Application(LO), Line Application(LI), Line Renewal(RE), Line
				// Amendment(AM), Line Availment(AV), Loan Roll Over(RO)
				a.setApplicationdate(new Date());
				a.setDatecreated(new Date());
				a.setCreatedby(UserUtil.securityService.getUserName());
				a.setApplicationstatus(1); // For Encoding
				a.setCreateevalreportflag(0);// Initial Eval Report Flag
				a.setCustomertype("1");// Ced Default
				a.setCompanycode(m.getCompanycode());// Ced coopcode
				a.setCoopcode(m.getCoopcode());
				a.setLoanpurpose(prodSrvc.getLoanProductByProductcode(form.getLoanproduct()).getLoanpurpose());
				if (m != null) {
					a.setLoanproduct(form.getLoanproduct());
//					a.setChaptercode(form.getChapter());
					a.setStatusdatetime(new Date());
					if (dbService.save(a)) {
						DocumentServiceImpl.createDocumentsPerProductApplication(a.getAppno(), a.getLoanproduct());
						if (a.getApplicationtype() == 6 || a.getApplicationtype() == 7) {
							if (loans != null && loans.size() > 0) {
								for (Tbloans loan : loans) {
									Tbloanoffset offset = new Tbloanoffset();
									TbloanoffsetId id = new TbloanoffsetId();
									id.setAccountno(loan.getAccountno());
									id.setAppno(a.getAppno());
									offset.setId(id);
									offset.setAcctsts(loan.getAcctsts());
									offset.setApplno(loan.getApplno());
									offset.setAppstatus(String.valueOf(a.getApplicationstatus()));
									offset.setCifno(a.getCifno());
									offset.setLoanbal(loan.getLoanbal());
									offset.setLpc(loan.getLpcbal());
									offset.setOthercharges(BigDecimal.ZERO);
									offset.setOutstandingbal(loan.getLoanbal());
									offset.setPnno(loan.getPnno());
									offset.setPrinbal(loan.getPrinbal());
									offset.setProdcode(loan.getProdcode());
									offset.setProductgroup(loan.getProductGroup());
									offset.setRebate(BigDecimal.ZERO);
									offset.setTxdate(a.getDatecreated());
									offset.setUidbal(loan.getUidbal());
									dbService.save(offset);
								}
							}
						}
//						List<CodetableForm> mlaForm = new ArrayList<CodetableForm>();
//						FinancialService financialsrvc = new FinancialServiceImpl();
//						mlaForm =  prodSrvc.getSavedParticulars(a.getLoanproduct(), "", "");
//						for(CodetableForm mla: mlaForm) {
//							Tbmlacperloanapp mlaapp = new Tbmlacperloanapp();
//							TbmlacperloanappId mlaappid = new TbmlacperloanappId();
//							MLACForm mlacform = new MLACForm();
//							mlacform.set
//							mlaappid.setAppno(a.getAppno());
//							mlaappid.setConditioncode(mla.getDesc2());
//							mlaappid.setParticulars(mla.getCodevalue());
//							mlaapp.setId(mlaappid);
//							mlaapp.setAmount(financialsrvc.computeMLA(a.getAppno(), mla.getCodevalue()));
//							dbService.save(mlaapp);
//						}
						AuditLog.addAuditLog(
								AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("CREATE LOAN APPLICATION",
										AuditLogEvents.LOAN_APPLICATION_ENCODING)),
								"User " + username + " Created New Loan Application: " + a.getAppno() + ".", username,
								new Date(), AuditLogEvents.LOAN_APPLICATION_ENCODING);
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
	public List<Tblstapp> getLoanApplications(String empID) {
		List<Tblstapp> list = new ArrayList<Tblstapp>();
		List<Tblstapp> retlist = new ArrayList<Tblstapp>();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("empID", empID);
		try {
			list = (List<Tblstapp>) dbsrvc.executeListHQLQuery("FROM Tblstapp WHERE cifno=:empID", params);
			for (Tblstapp a : list) {
				params.put("appno", a.getAppno());
				params.put("seq", a.getApplicationstatus());
				params.put("productcode", a.getLoanproduct());
				params.put("boscode", a.getCompanycode());
				Tbworkflowprocess proc = (Tbworkflowprocess) dbsrvc.executeUniqueHQLQuery(
						"FROM Tbworkflowprocess WHERE workflowid ='3' AND sequenceno=:seq", params);
				Tbloanproduct loan = (Tbloanproduct) dbsrvc
						.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode=:productcode", params);
				Tbaccountinfo acc = (Tbaccountinfo) dbsrvc
						.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno=:appno", params);
				a.setAccountofficer(proc.getProcessname());
				a.setLoanproduct(loan.getProductname());
				if (acc != null) {
					if (acc.getFaceamt() != null) {
						a.setApprovedloanamount(acc.getFaceamt());
					}
				}
				retlist.add(a);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retlist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmemberdependents> getMemberDependents(String memid) {
		List<Tbmemberdependents> list = new ArrayList<Tbmemberdependents>();
		DBService srvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memid", memid);
		try {
			list = (List<Tbmemberdependents>) srvc
					.executeListHQLQuery("FROM Tbmemberdependents WHERE membershipid=:memid", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmemberemployment> getMemberEmployment(String memid) {
		List<Tbmemberemployment> list = new ArrayList<Tbmemberemployment>();
		DBService srvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memid", memid);
		try {
			list = (List<Tbmemberemployment>) srvc
					.executeListHQLQuery("FROM Tbmemberemployment WHERE membershipid=:memid", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmemberbusiness> getMemberBusiness(String memid) {
		List<Tbmemberbusiness> list = new ArrayList<Tbmemberbusiness>();
		DBService srvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memid", memid);
		try {
			list = (List<Tbmemberbusiness>) srvc.executeListHQLQuery("FROM Tbmemberbusiness WHERE membershipid=:memid",
					params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String getMemStat(String codevalue) {
		String desc = "";
		Tbcodetable table = new Tbcodetable();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("codevalue", codevalue);
		try {
			table = (Tbcodetable) dbsrvc.executeUniqueHQLQuery(
					"FROM Tbcodetable WHERE codename ='MEMBERSHIPSTATUS' AND codevalue=:codevalue", params);
			desc = table.getDesc1();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desc;
	}

	@Override
	public String getChapter(String chaptercode) {
		String chaptername = "";
		Tbchapter chapter = new Tbchapter();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("chaptercode", chaptercode);
		try {
			chapter = (Tbchapter) dbsrvc.executeUniqueHQLQuery("FROM Tbchapter WHERE chaptercode=:chaptercode", param);
			chaptername = chapter.getChaptername();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chaptername;
	}

	// Added ced 12042018
	@SuppressWarnings("unchecked")
	public List<Tblstcomakers> getMemberComakers(String appno) {
		List<Tblstcomakers> comakerList = new ArrayList<Tblstcomakers>();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appno", appno);
		try {
			comakerList = (List<Tblstcomakers>) dbsrvc.executeListHQLQuery("FROM Tblstcomakers WHERE appno=:appno",
					param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return comakerList;
	}

	@Override
	public String deleteComaker(String appno, String memberid) {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("appno", appno);
		param.put("memberid", memberid);
		try {
			if ((Integer) dbsrvc.execStoredProc(
					"DELETE FROM TBLSTCOMAKERS WHERE appno =:appno AND membershipid =:memberid", param, null, 2,
					null) > 0)
				return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public Tbmemberdosri getDosriDetails(String memappid) {
		// TODO Auto-generated method stub
		Tbmemberdosri app = new Tbmemberdosri();
		try {
			param.put("appid", memappid);
			if (memappid != null) {
				app = (Tbmemberdosri) dbService.executeUniqueHQLQuery("FROM Tbmemberdosri WHERE membershipid=:appid",
						param);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return app;
	}

	@Override
	public String returnLoanApplication(String appno) {
		// TODO Auto-generated method stub
		try {
			param.put("appno", appno);
			if (appno != null) {
				if (dbService.executeUpdate("UPDATE Tblstapp SET isdoneencoding = 0 WHERE appno =:appno", param) > 0) {
					AuditLog.addAuditLog(
							AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("RETURN FOR EDITING",
									AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET)),
							"User " + username + " Returned Application For Editing.", username, new Date(),
							AuditLogEvents.LOAN_APPLICATION_GENERATE_INSTRUCTION_SHEET);
					return "success";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public LoansForm getLoansbyMemberID(String memid) {
		// TODO Auto-generated method stub
		LoansForm form = new LoansForm();

		try {
			param.put("memid", memid);
			if (memid != null) {
				List<Tbloans> borrower = (List<Tbloans>) dbService
						.executeListHQLQuery("FROM Tbloans WHERE principalno=:memid", param);
				List<Tbloans> comaker = (List<Tbloans>) dbService
						.executeListHQLQuery("FROM Tbloans WHERE (comaker1=:memid OR comaker2=:memid)", param);

				form.setBorrower(borrower);
				form.setComaker(comaker);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String getBranchName(String branchcode) {
		// TODO Auto-generated method stub
		String branchname = "";
		Tbbranch branch = new Tbbranch();
		try {
			param.put("branchcode", branchcode);
			if (branchcode != null) {
				branch = (Tbbranch) dbService.executeUniqueHQLQuery("FROM Tbbranch a WHERE a.branchcode=:branchcode",
						param);
			}
			branchname = branch.getBranchname();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return branchname;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmemberfinancialinfo> getMemberFinancialInfo(String membershipid, String financialtype) {
		// TODO Auto-generated method stub
		try {
			param.put("membershipid", membershipid);
			param.put("financialtype", financialtype);
			List<Tbmemberfinancialinfo> list = (List<Tbmemberfinancialinfo>) dbService.executeListHQLQuery(
					"FROM Tbmemberfinancialinfo WHERE membershipid=:membershipid AND financialtype=:financialtype",
					param);
			if (list != null) {
				return list;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmembercreditcardinfo> getMemberCreditCardInfo(String membershipid) {
		// TODO Auto-generated method stub
		try {
			param.put("membershipid", membershipid);
			List<Tbmembercreditcardinfo> list = (List<Tbmembercreditcardinfo>) dbService
					.executeListHQLQuery("FROM Tbmembercreditcardinfo WHERE memberid=:membershipid", param);
			if (list != null) {
				return list;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String setupNewCIF(QDEParameterForm form, String ciftype) {
		// TODO Auto-generated method stub
		AuditTrailFacade auditTrailFacade = new AuditTrailFacade();
		AuditTrail auditTrail = new AuditTrail();
		String flag = "failed";
		DBService dbService = new DBServiceImplCIF();
		DBService dbServiceCOOP = new DBServiceImpl();
		Tbcifmain main = new Tbcifmain();
		Tbcifindividual indiv = new Tbcifindividual();
		Tbcifcorporate corp = new Tbcifcorporate();
		List<Tbcifmain> dedupe = new ArrayList<Tbcifmain>();
		Tbuser user = new Tbuser();
		Map<String, Object> params = HQLUtil.getMap();
		try {

			if (form.getCustType().equals("I")) {
				// Individual
				// Full Name
				String lname = form.getLname().toUpperCase();
				String fname = form.getFname().toUpperCase();
				String mname = form.getMname() == null ? "" : form.getMname().toUpperCase();
				String suf = form.getSuffix() == null ? "" : form.getSuffix().toUpperCase();
				String fullName = lname + ", " + fname + " " + suf + " " + mname;
				
				main.setFullname(fullName);
				
				// 02.02.2023
				main.setFullname(main.getFullname().trim().replaceAll(" +", " "));
				
				if (main.getFullname() != null) {
					params.put("fullname", main.getFullname());
					if (form.getDate() != null) {
						params.put("dob", form.getDate());
					} else {
						params.put("dob", new Date());
					}

					dedupe = (List<Tbcifmain>) dbService.executeListHQLQuery(
							"FROM Tbcifmain where fullname=:fullname and dateofbirth=:dob", params);
				}

				if (dedupe != null) {
;
					main.setCifno(form.getCifno());
					main.setEncodedby(secservice.getUserName());
					main.setAssignedto(secservice.getUserName());
					main.setEncodeddate(new Date());
					main.setDateupdated(new Date());
					main.setCustomertype("2");
					main.setCifstatus("1"); // For Encoding
					main.setAccreditationstatus("0");
					main.setIsencoding(false); // renz

					/** 012318 As per PONG **/
					if (ciftype == null) {
						main.setCiftype("3"); // Related Entity
					} else {
						main.setCiftype("0"); // Prospect
					}
					// Added by Pong : Saving of originating team 07-11-17
					String username = UserUtil.securityService.getUserName();
					if (username != null) {
						params.put("username", username);
						user = (Tbuser) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username",
								params);
						if (user != null) {
							main.setOriginatingbranch(user.getBranchcode());
							if (user.getTeamcode() != null) {
								main.setOriginatingteam(user.getTeamcode());
							}
						}
					}

					main.setDateofbirth(form.getDate());
					main.setFulladdress1(FullDataEntryServiceImpl.fullAddress(form.getStreetno(), form.getSubdivision(),
							form.getBarangay(), form.getCity(), form.getProvince(), form.getCountry(),
							form.getPostalcode()));

					indiv.setCifno(form.getCifno());
					indiv.setLastname(form.getLname().toUpperCase());
					indiv.setFirstname(form.getFname().toUpperCase());
					indiv.setMiddlename(form.getMname() == null ? "" : form.getMname().toUpperCase());
					indiv.setSuffix(form.getSuffix() == null ? "" : form.getSuffix().toUpperCase());
					indiv.setDateofbirth(form.getDate());

					// 01-25-18 PONGYU
					// check legal age
					// if (indiv.getDateofbirth() != null) {
					// 	int age = DateTimeUtil.getAge(indiv.getDateofbirth());
					// 	if (age < 18) {
					// 		return "Age should not be less than the legal age.";
					// 	}
					// }

					indiv.setTin(form.getTin());
					indiv.setSss(form.getSss());
					indiv.setBankName(form.getBankName());
					indiv.setBankAccountNumber(form.getBankAccountNumber());
					indiv.setStreetno1(form.getStreetno());
					indiv.setSubdivision1(form.getSubdivision());
					indiv.setProvince1(form.getProvince());
					indiv.setCountry1(form.getCountry());
					indiv.setCity1(form.getCity());
					indiv.setBarangay1(form.getBarangay());
					indiv.setPostalcode1(form.getPostalcode());
					indiv.setFulladdress1(FullDataEntryServiceImpl.fullAddress(indiv.getStreetno1(),
							indiv.getSubdivision1(), indiv.getBarangay1(), indiv.getCity1(), indiv.getProvince1(),
							indiv.getCountry1(), indiv.getPostalcode1()));

					if (dbService.saveOrUpdate(main)) {
						dbService.saveOrUpdate(indiv);
						flag = "success";
						//Audit Trail TBCODETABLE CODE = AuditTrail
						auditTrail.setTransactionNumber(form.getCifno());
						auditTrail.setEventType("3");
						auditTrail.setEventName("1");
						auditTrail.setEventDescription(fullName+" Submitted to Status: For Encoding");
						auditTrail.setIpaddress(UserUtil.getUserIp());
						auditTrailFacade.saveAudit(auditTrail);
					} else {
						flag = "failed";
					}
				} else {
					flag = "Member Record is Existing!";
				}

			} else if (form.getCustType().equals("C")) {
				// Corporate

				main.setCifno(form.getCifno());
				main.setEncodedby(secservice.getUserName());
				main.setAssignedto(secservice.getUserName());
				main.setEncodeddate(new Date());
				main.setDateupdated(new Date());
				main.setCustomertype("1");
				main.setAccreditationstatus("0");
				main.setCifstatus("1"); // For Encoding
				main.setIsencoding(false); // renz
				/** 012318 As per PONG **/
				if (ciftype == null) {
					main.setCiftype("3"); // Related Entity
				} else {
					main.setCiftype("0"); // Prospect
				}
				main.setFullname(form.getBusinessname().toUpperCase());
				main.setDateofincorporation(form.getDate());
				// Added by Pong : Saving of originating team 07-11-17
				String username = UserUtil.securityService.getUserName();
				if (username != null) {
					params.put("username", username);
					user = (Tbuser) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
					if (user != null) {
						main.setOriginatingbranch(user.getBranchcode());
						if (user.getTeamcode() != null) {
							main.setOriginatingteam(user.getTeamcode());
						}
					}
				}
				corp.setCifno(form.getCifno());
				corp.setCorporatename(form.getBusinessname().toUpperCase());
				corp.setDateofincorporation(form.getDate());
				corp.setBusinesstype(form.getBtype()); // modified July 24, 2017 for Employment and Business - Quick
														// Setup

				corp.setTin(form.getTin());
				corp.setSss(form.getSss());
				corp.setStreetno1(form.getStreetno());
				corp.setSubdivision1(form.getSubdivision());
				corp.setCountry1(form.getCountry());
				corp.setCity1(form.getCountry());
				corp.setBarangay1(form.getBarangay());
				corp.setPostalcode1(form.getPostalcode());

				if (dbService.saveOrUpdate(main)) {
					dbService.saveOrUpdate(corp);
					flag = "success";
					//Audit Trail TBCODETABLE CODE = AuditTrail
					auditTrail.setTransactionNumber(form.getCifno());
					auditTrail.setEventType("2");
					auditTrail.setEventName("1");
					auditTrail.setEventDescription(form.getBusinessname().toUpperCase()+" Submitted to Status: For Encoding");
					auditTrail.setIpaddress(UserUtil.getUserIp());
					auditTrailFacade.saveAudit(auditTrail);

				} else {
					flag = "failed";
				}
			} else if (form.getCustType().equals("S")) {
				// Sole Prop

				main.setCifno(form.getCifno());
				main.setEncodedby(secservice.getUserName());
				main.setAssignedto(secservice.getUserName());
				main.setEncodeddate(new Date());
				main.setDateupdated(new Date());
				main.setCustomertype("3");
				main.setAccreditationstatus("0");
				main.setCifstatus("1"); // For Encoding
				/** 012318 As per PONG **/
				if (ciftype == null) {
					main.setCiftype("3"); // Related Entity
				} else {
					main.setCiftype("0"); // Prospect
				}
				main.setFullname(form.getBusinessname().toUpperCase());
				main.setDateofincorporation(form.getDate());
				// Added by Pong : Saving of originating team 07-11-17
				String username = UserUtil.securityService.getUserName();
				if (username != null) {
					params.put("username", username);
					user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
					if (user != null) {
						if (user.getTeamcode() != null) {
							main.setOriginatingteam(user.getTeamcode());
						}
					}
				}
				corp.setCifno(form.getCifno());
				corp.setCorporatename(form.getBusinessname().toUpperCase());
				corp.setDateofincorporation(form.getDate());
				if (form.getBtype() != null) {
					corp.setBusinesstype(form.getBtype()); // modified July 24, 2017 for Employment and Business - Quick
															// Setup
				} else {
					corp.setBusinesstype("09");
				}

				corp.setTin(form.getTin());
				corp.setSss(form.getSss());
				corp.setStreetno1(form.getStreetno());
				corp.setSubdivision1(form.getSubdivision());
				corp.setCountry1(form.getCountry());
				corp.setCity1(form.getCountry());
				corp.setBarangay1(form.getBarangay());
				corp.setPostalcode1(form.getPostalcode());

				if (dbService.saveOrUpdate(main)) {
					dbService.saveOrUpdate(corp);
					flag = "success";
					// 08-08-17 PONGYU
					HistoryService h = new HistoryServiceImpl();
					h.addHistory(indiv.getCifno(), "Encoded new CIF record.", null);

				} else {
					flag = "failed";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}


	// MAR 10-13-2020
	@SuppressWarnings("unchecked")
	@Override
	public List<TblstappForm> listLstapp(String cifno) {
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		List<TblstappForm> list = new ArrayList<TblstappForm>();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				list = (List<TblstappForm>) dbServiceCOOP.execSQLQueryTransformer(
						"SELECT appno, (SELECT productname FROM Tbloanproduct WHERE productcode=loanproduct) as loanproduct, applicationdate, (SELECT teamname FROM Tbteams WHERE teamcode=losoriginatingteam) as losoriginatingteam, (SELECT processname FROM Tbworkflowprocess "
								+ "WHERE workflowid=applicationtype AND sequenceno=applicationstatus) as applicationstatus,(SELECT workflowname FROM Tbworkflow WHERE workflowid=applicationtype)as applicationtype FROM Tblstapp WHERE cifno=:cifno",
						param, TblstappForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<TblstappForm> listSpsLstapp(String cifno) {
		DBService dbServiceCOOP = new DBServiceImpl();
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		List<TblstappForm> list = new ArrayList<TblstappForm>();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				param.put("relation", "SPS");
				Tbcustomerrelationship spouse = (Tbcustomerrelationship) dbServiceCIF.executeUniqueHQLQuery(
						"FROM Tbcustomerrelationship WHERE maincifno=:cifno AND relationshipcode=:relation", param);
				param.put("spouse", spouse.getRelatedcifno());
				list = (List<TblstappForm>) dbServiceCOOP.execSQLQueryTransformer(
						"SELECT appno, (SELECT productname FROM Tbloanproduct WHERE productcode=loanproduct) as loanproduct, applicationdate, (SELECT teamname FROM Tbteams WHERE teamcode=losoriginatingteam) as losoriginatingteam, (SELECT processname FROM Tbworkflowprocess "
								+ "WHERE workflowid=applicationtype AND sequenceno=applicationstatus) as applicationstatus FROM Tblstapp WHERE cifno=:spouse",
						param, TblstappForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Tbcifcorporate getCIFCorp(String cifno) {
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		Tbcifcorporate details = new Tbcifcorporate();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				details = (Tbcifcorporate) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno=:cifno",
						param);
				if (details != null) {
					return details;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return details;
	}

	@Override
	public Tbcifmain getCIFMain(String cifno) {
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		Tbcifmain details = new Tbcifmain();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				details = (Tbcifmain) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", param);
				if (details != null) {
					return details;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return details;
	}

	@Override
	public String getSpsNameforDedupe(String cifno) {
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		Tbcustomerrelationship spouse = new Tbcustomerrelationship();
		try {
			param.put("cifno", cifno);
			param.put("relation", "SPS");
			spouse = (Tbcustomerrelationship) dbServiceCIF.executeUniqueHQLQuery(
					"FROM Tbcustomerrelationship WHERE maincifno=:cifno AND relationshipcode=:relation", param);
			if (spouse != null) {
				return "( " + spouse.getRelatedcifname() + " - " + spouse.getRelatedcifno() + " )";
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tbcifindividual getCIFIndiv(String cifno) {
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		Tbcifindividual details = new Tbcifindividual();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				details = (Tbcifindividual) dbServiceCIF
						.executeUniqueHQLQuery("FROM Tbcifindividual WHERE cifno=:cifno", param);
				if (details != null) {
					return details;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return details;
	}

	@SuppressWarnings("unchecked")
	@Override
	public QDEParameterForm searchCIF(String cifno, String fname, String lname, String corporatename, String custType) {
		List<Tbcifmain> main = new ArrayList<Tbcifmain>();
		List<Tbcifindividual> indiv = new ArrayList<Tbcifindividual>();
		QDEParameterForm form = new QDEParameterForm();
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cif", cifno);
		param.put("lname", lname == null ? "%%" : "%" + lname + "%");
		param.put("fname", fname == null ? "%%" : "%" + fname + "%");
		param.put("corporatename", corporatename == null ? "'%%'" : "%" + corporatename + "%");
		param.put("type", custType);
		try {
			if (custType != null) {
				if (cifno != null) {
					main = (List<Tbcifmain>) dbServiceCIF
							.executeListHQLQuery("FROM Tbcifmain WHERE cifno=:cif AND customertype=:type)", param);
				} else {
					if (corporatename != null) {
						main = (List<Tbcifmain>) dbServiceCIF.executeListHQLQuery(
								"FROM Tbcifmain WHERE fullname LIKE :corporatename AND customertype=:type)", param);
					} else {
						indiv = (List<Tbcifindividual>) dbServiceCIF.executeListHQLQuery(
								"FROM Tbcifindividual WHERE lastname LIKE :lname AND firstname LIKE :fname", param);
					}
				}
			}
			if (main != null && !main.isEmpty()) {
				for (Tbcifmain main2 : main) {
					param.put("status", main2.getCifstatus());
					Tbcodetable cifstatus = (Tbcodetable) dbServiceCIF.executeUniqueHQLQuery(
							"FROM Tbcodetable a WHERE a.id.codename='CIFSTATUS' AND a.id.codevalue=:status", param);
					if (cifstatus != null) {
						main2.setCifstatus(cifstatus.getDesc1());
					}
					param.put("team", main2.getOriginatingteam());
					Tbteams t = (Tbteams) dbServiceCIF.executeUniqueHQLQuery("FROM Tbteams a WHERE a.id.teamcode=:team",
							param);
					if (t != null) {
						main2.setOriginatingteam(t.getTeamname());
					}

				}
				form.setCif(main); /** CIF Main Bucket **/
			}

			if (indiv != null && !indiv.isEmpty()) {
				for (Tbcifindividual indiv2 : indiv) {
					param.put("cifindiv", indiv2.getCifno());
					Tbcifmain c = (Tbcifmain) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifindiv",
							param);
					if (main.add(c)) {
						for (Tbcifmain main2 : main) {
							param.put("status", main2.getCifstatus());
							Tbcodetable cifstatus = (Tbcodetable) dbServiceCIF.executeUniqueHQLQuery(
									"FROM Tbcodetable a WHERE a.id.codename='CIFSTATUS' AND a.id.codevalue=:status",
									param);
							if (cifstatus != null) {
								main2.setCifstatus(cifstatus.getDesc1());
							}
						}
						form.setCif(main); /** CIF Main Bucket **/
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public FormValidation setupNewApplicationLOS(QDEParameterForm form) {
		FormValidation f = new FormValidation();
//		DBService dbServiceLOS = new DBServiceImplLOS();
	DBService dbServiceCIF = new DBServiceImplCIF();
	Map<String, Object> param = HQLUtil.getMap();
	CompanyService cmpySrvc = new CompanyServiceImpl();
	Tblstapp a = new Tblstapp();
	try {
		a.setCoopcode(cmpySrvc.getListOfCompany("TBCOOPERATIVE").get(0).getCoopcode());// Added by Ced 6-21-2021
		// If cif is existing
		if (form.getCifno() != null) {
			a.setIsexisting(true);
			a.setCifno(form.getCifno());

			a.setCustomertype(form.getCustomertype()); // Individual or
														// Corporate
			a.setCifname(form.getCifname());
			a.setApplicationtype(form.getApplicationtype()); // Loan
																// Application(LO),
																// Line
																// Application(LI),
																// Line
																// Renewal(RE),
																// Line
																// Amendment(AM),
																// Line
																// Availment(AV),
																// Loan Roll
																// Over(RO)
			a.setApplicationdate(new Date());
			a.setDatecreated(new Date());
			a.setCreatedby(secservice.getUserName());
			a.setApplicationstatus(1); // For Encoding
			a.setCreateevalreportflag(0);// Initial Eval Report Flag
			a.setStatusdatetime(new Date());
			param.put("user", secservice.getUserName());
			// MAR
			Tbuser user1 = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:user", param);
			param.put("name", user1.getUsername());
			param.put("code", user1.getTeamcode());

			// MAR
			// com.loansdb.data.Tbteams team = (com.loansdb.data.Tbteams) dbServiceLOS
			// .executeUniqueHQLQuery("FROM Tbteams WHERE teamcode=:code", param);

			// MAR
			/*
			 * if(team.getIsofficeravailable()==null){
			 * f.setFlag("Officer Available is NULL"); return f; }else{
			 * if(team.getIsofficeravailable()){ if (team.getIsofficeravailable()) {
			 * a.setAccountofficer(team.getOfficer()); } else {
			 * a.setAccountofficer(team.getBackupofficer()); } }else{
			 * a.setAccountofficer(team.getBackupofficer()); } }
			 */
			param.put("cif", form.getCifno());
			Tbcifmain m = (Tbcifmain) dbServiceCIF
					.executeUniqueHQLQueryMaxResultOne("FROM Tbcifmain WHERE cifno=:cif", param);
			if (m != null) {
				a.setCiforiginatingteam(m.getOriginatingteam());

				// 11.28.18
				a.setReferraltype(m.getReferraltype());
				a.setReferrorname(m.getReferrorname());
			}
			// a.setCompanycode(form.getCompany());
			a.setLoanproduct(form.getLoanproduct());
			a.setTypefacility(form.getTypefacility());

			Tbcifindividual i = (Tbcifindividual) dbServiceCIF
					.executeUniqueHQLQueryMaxResultOne("FROM Tbcifindividual WHERE cifno=:cif", param);
			if (form.getCustomertype().equals("2")) {
				if (i != null) {
					a.setCiftin(i.getTin());
					a.setLastname(i.getLastname());
					a.setFirstname(i.getFirstname());

				}
			} 
			param.put("user", secservice.getUserName());
			Tbuser user = (Tbuser) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbuser WHERE username=:user",
					param);
			a.setLosoriginatingteam(user.getTeamcode());
			a.setIsdoneencoding(false);
			a.setBranchcode(user.getBranchcode());

			a.setCompanycode(user.getCompanycode());
			a.setCompanycodemember(m.getCompanyCode());

			String no = "";
			// Jan 31. 2019
			// System.out.println("--------- form.getApplicationtype() : " +
			// form.getApplicationtype());
			if (form.getApplicationtype() != null) {
				if (form.getApplicationtype().equals("1") || form.getApplicationtype() == (1)) {
					no = ApplicationNoGenerator.generateApplicationNo("LO");
				} else if (form.getApplicationtype().equals("2") || form.getApplicationtype() == (2)) {
					no = ApplicationNoGenerator.generateApplicationNo("LI");
				} else if (form.getApplicationtype().equals("3") || form.getApplicationtype() == (3)) {
					no = ApplicationNoGenerator.generateApplicationNo("RE");
				} else if (form.getApplicationtype().equals("4") || form.getApplicationtype() == (4)) {
					no = ApplicationNoGenerator.generateApplicationNo("AM");
				} else if (form.getApplicationtype().equals("5") || form.getApplicationtype() == (5)) {
					no = ApplicationNoGenerator.generateApplicationNo("AV");
				} else if (form.getApplicationtype().equals("6") || form.getApplicationtype() == (6)) {
					no = ApplicationNoGenerator.generateApplicationNo("RO");
				}
			}
			a.setAppno(no);
			// MAR

			if (dbService.save(a)) {
				f.setFlag("success");
				f.setErrorMessage(no);
				System.out.println(">>>>> SUCCESS CREATING APPLICATION FOR " + a.getAppno() + ", " + a.getCifno()
						+ " - " + a.getCifname());

				// AS-PER-NOREEN...FOR-EVERY-LOANAPP-CREATED,-SHOULD-GENERATE-DOCUMENTS-BASED-ON-LOAN-PRODUCT
				DocumentationServiceImpl.createDocumentsPerApplication(a.getAppno());// DocumentServiceImpl-Line-178-DANIEL-09.01.2018

				// Generate Loan Fees per App - Kevin 09.16.2018
				if (a.getLoanproduct() != null && !a.getLoanproduct().equals("")) {
					LoanFeeInputForm feeform = new LoanFeeInputForm();
					feeform.setAppno(a.getAppno());
					LoanProductService prodsrvc = new LoanProductServiceImpl();
					prodsrvc.generateLoanFeesPerApp(feeform);
				}

				// Email Submit Application (Kevin 10.12.2018)
				param.put("appno", a.getAppno());
				param.put("username", UserUtil.securityService.getUserName());
				// MAR
				dbService.execSQLQueryTransformer(
						"EXEC sp_InsertEmailSMTP @appno=:appno, @username=:username, @emailcode = 'EM6', @body =NULL",
						param, null, 0);

				// HISTORY (Kevin 10.22.2019)
				HistoryService h = new HistoryServiceImpl();
				h.saveHistory(a.getAppno(), AuditLogEvents.CREATE_APPLICATION,
						"Created new application - <font size=\"2\"><b>\"FOR ENCODING\"</b>.");

			}
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return f;
	}

	@Override
	public String setupNewCIFRB(QDEParameterForm form, String ciftype) {
		// TODO Auto-generated method stub
		String flag = "failed";
		DBService dbService = new DBServiceImplCIF();
		DBService dbServiceCOOP = new DBServiceImpl();
		Tblosmain main = new Tblosmain();
		Tblosindividual indiv = new Tblosindividual();
		Tbloscorporate corp = new Tbloscorporate();
		Tbuser user = new Tbuser();
		Map<String, Object> params = HQLUtil.getMap();
		try {

			if (form.getCustType().equals("I")) {
				// Individual

				main.setCifno(form.getCifno());
				main.setEncodedby(secservice.getUserName());
				main.setAssignedto(secservice.getUserName());
				main.setEncodeddate(new Date());
				main.setDateupdated(new Date());
				main.setCustomertype("1");
				main.setCifstatus("1"); // For Encoding
				main.setAccreditationstatus("0");
				/** 012318 As per PONG **/
				if (ciftype == null) {
					main.setCiftype("3"); // Related Entity
				} else {
					main.setCiftype("0"); // Prospect
				}
				// Added by Pong : Saving of originating team 07-11-17
				String username = UserUtil.securityService.getUserName();
				if (username != null) {
					params.put("username", username);
					user = (Tbuser) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
					if (user != null) {
						if (user.getTeamcode() != null) {
							main.setOriginatingteam(user.getTeamcode());
						}
					}
				}
				// Full Name
				String lname = form.getLname().toUpperCase();
				String fname = form.getFname().toUpperCase();
				String mname = form.getMname() == null ? "" : form.getMname().toUpperCase();
				String suf = form.getSuffix() == null ? "" : form.getSuffix().toUpperCase();
				main.setFullname(lname + ", " + fname + " " + suf + " " + mname);
				
				// 02.02.2023
				main.setFullname(main.getFullname().trim().replaceAll(" +", " "));
				main.setDateofbirth(form.getDate());

				indiv.setCifno(form.getCifno());
				indiv.setLastname(form.getLname().toUpperCase());
				indiv.setFirstname(form.getFname().toUpperCase());
				indiv.setMiddlename(form.getMname() == null ? "" : form.getMname().toUpperCase());
				indiv.setSuffix(form.getSuffix() == null ? "" : form.getSuffix().toUpperCase());
				indiv.setDateofbirth(form.getDate());

				// 01-25-18 PONGYU
				// check legal age
//				if (indiv.getDateofbirth() != null) {
//					int age = DateTimeUtil.getAge(indiv.getDateofbirth());
//					if (age < 18) {
//						return "Age should not be less than the legal age.";
//					}
//				}

				indiv.setTin(form.getTin());
				indiv.setSss(form.getSss());
				indiv.setStreetno1(form.getStreetno());
				indiv.setSubdivision1(form.getSubdivision());
				indiv.setCountry1(form.getCountry());
				indiv.setCity1(form.getCountry());
				indiv.setBarangay1(form.getBarangay());
				indiv.setPostalcode1(form.getPostalcode());

				if (dbServiceCOOP.saveOrUpdate(main)) {
					dbServiceCOOP.saveOrUpdate(indiv);
					// 08-08-17 PONGYU
					flag = "success";
					HistoryService h = new HistoryServiceImpl();
					h.addHistory(indiv.getCifno(), "Encoded new CIF record.", null);
				} else {
					flag = "failed";
				}

			} else if (form.getCustType().equals("C")) {
				// Corporate

				main.setCifno(form.getCifno());
				main.setEncodedby(secservice.getUserName());
				main.setAssignedto(secservice.getUserName());
				main.setEncodeddate(new Date());
				main.setDateupdated(new Date());
				main.setCustomertype("2");
				main.setAccreditationstatus("0");
				main.setCifstatus("1"); // For Encoding
				/** 012318 As per PONG **/
				if (ciftype == null) {
					main.setCiftype("3"); // Related Entity
				} else {
					main.setCiftype("0"); // Prospect
				}
				main.setFullname(form.getBusinessname().toUpperCase());
				main.setDateofincorporation(form.getDate());
				// Added by Pong : Saving of originating team 07-11-17
				String username = UserUtil.securityService.getUserName();
				if (username != null) {
					params.put("username", username);
					user = (Tbuser) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
					if (user != null) {
						if (user.getTeamcode() != null) {
							main.setOriginatingteam(user.getTeamcode());
						}
					}
				}
				corp.setCifno(form.getCifno());
				corp.setCorporatename(form.getBusinessname().toUpperCase());
				corp.setDateofincorporation(form.getDate());
				corp.setBusinesstype(form.getBtype()); // modified July 24, 2017 for Employment and Business - Quick
														// Setup

				corp.setTin(form.getTin());
				corp.setSss(form.getSss());
				corp.setStreetno1(form.getStreetno());
				corp.setSubdivision1(form.getSubdivision());
				corp.setCountry1(form.getCountry());
				corp.setCity1(form.getCountry());
				corp.setBarangay1(form.getBarangay());
				corp.setPostalcode1(form.getPostalcode());

				if (dbServiceCOOP.saveOrUpdate(main)) {
					dbServiceCOOP.saveOrUpdate(corp);
					flag = "success";
					// 08-08-17 PONGYU
					HistoryService h = new HistoryServiceImpl();
					h.addHistory(indiv.getCifno(), "Encoded new CIF record.", null);

				} else {
					flag = "failed";
				}
			} else if (form.getCustType().equals("S")) {
				// Sole Prop

				main.setCifno(form.getCifno());
				main.setEncodedby(secservice.getUserName());
				main.setAssignedto(secservice.getUserName());
				main.setEncodeddate(new Date());
				main.setDateupdated(new Date());
				main.setCustomertype("3");
				main.setAccreditationstatus("0");
				main.setCifstatus("1"); // For Encoding
				/** 012318 As per PONG **/
				if (ciftype == null) {
					main.setCiftype("3"); // Related Entity
				} else {
					main.setCiftype("0"); // Prospect
				}
				main.setFullname(form.getBusinessname().toUpperCase());
				main.setDateofincorporation(form.getDate());
				// Added by Pong : Saving of originating team 07-11-17
				String username = UserUtil.securityService.getUserName();
				if (username != null) {
					params.put("username", username);
					user = (Tbuser) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
					if (user != null) {
						if (user.getTeamcode() != null) {
							main.setOriginatingteam(user.getTeamcode());
						}
					}
				}
				corp.setCifno(form.getCifno());
				corp.setCorporatename(form.getBusinessname().toUpperCase());
				corp.setDateofincorporation(form.getDate());
				if (form.getBtype() != null) {
					corp.setBusinesstype(form.getBtype()); // modified July 24, 2017 for Employment and Business - Quick
															// Setup
				} else {
					corp.setBusinesstype("09");
				}

				corp.setTin(form.getTin());
				corp.setSss(form.getSss());
				corp.setStreetno1(form.getStreetno());
				corp.setSubdivision1(form.getSubdivision());
				corp.setCountry1(form.getCountry());
				corp.setCity1(form.getCountry());
				corp.setBarangay1(form.getBarangay());
				corp.setPostalcode1(form.getPostalcode());

				if (dbServiceCOOP.saveOrUpdate(main)) {
					dbServiceCOOP.saveOrUpdate(corp);
					flag = "success";
					// 08-08-17 PONGYU
					HistoryService h = new HistoryServiceImpl();
					h.addHistory(indiv.getCifno(), "Encoded new CIF record.", null);

				} else {
					flag = "failed";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// MAR 10-25-2020, modified wel 04.19.21
	public FormValidation setupNewApplicationLOSNotExisting(QDEParameterForm form) {
		FormValidation f = new FormValidation();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		CompanyService cmpySrvc = new CompanyServiceImpl();
		Tbuser user = new Tbuser();
		Tblstapp a = new Tblstapp();
		try {
			a.setCoopcode(cmpySrvc.getListOfCompany("TBCOOPERATIVE").get(0).getCoopcode());// Added by Ced 6-21-2021
			if (form.getCustomertype().equals("1")) {

				a.setIsexisting(false);
				a.setLastname(form.getLastnameNotExisting().toUpperCase());
				a.setFirstname(form.getFirstnameNotExisting().toUpperCase());
				a.setCustomertype(form.getCustomertype()); // Individual or
															// Corporate

				a.setApplicationtype(form.getApplicationtype()); // Loan
																	// Application(LO),
																	// Line
																	// Application(LI),
																	// Line
																	// Renewal(RE),
																	// Line
																	// Amendment(AM),
																	// Line
																	// Availment(AV),
																	// Loan Roll
																	// Over(RO)
				a.setApplicationdate(new Date());
				a.setDatecreated(new Date());
				a.setCreatedby(secservice.getUserName());
				a.setApplicationstatus(1); // For Encoding
				a.setCreateevalreportflag(0);// Initial Eval Report Flag
				a.setStatusdatetime(new Date());

				param.put("user", secservice.getUserName());
				// MAR
				Tbuser user1 = (Tbuser) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbuser WHERE username=:user", param);
				param.put("name", user1.getUsername());
				param.put("code", user1.getTeamcode());

				param.put("user", secservice.getUserName());
				user = (Tbuser) dbServiceCOOP.executeUniqueHQLQueryMaxResultOne("FROM Tbuser WHERE username=:user",
						param);
				a.setLosoriginatingteam(user.getTeamcode());
				a.setIsdoneencoding(false);
				a.setBranchcode(user.getBranchcode());
				a.setCompanycode(user.getCompanycode());

				String no = "";
				// Jan 31. 2019
				if (form.getApplicationtype() != null) {
					if (form.getApplicationtype().equals("1") || form.getApplicationtype() == (1)) {
						no = ApplicationNoGenerator.generateApplicationNo("LO");
					} else if (form.getApplicationtype().equals("2") || form.getApplicationtype() == (2)) {
						no = ApplicationNoGenerator.generateApplicationNo("LI");
					} else if (form.getApplicationtype().equals("3") || form.getApplicationtype() == (3)) {
						no = ApplicationNoGenerator.generateApplicationNo("RE");
					} else if (form.getApplicationtype().equals("4") || form.getApplicationtype() == (4)) {
						no = ApplicationNoGenerator.generateApplicationNo("AM");
					} else if (form.getApplicationtype().equals("5") || form.getApplicationtype() == (5)) {
						no = ApplicationNoGenerator.generateApplicationNo("AV");
					} else if (form.getApplicationtype().equals("6") || form.getApplicationtype() == (6)) {
						no = ApplicationNoGenerator.generateApplicationNo("RO");
					}
				}
				a.setAppno(no);

				// Full Name
				String lname = form.getLastnameNotExisting().toUpperCase();
				String fname = form.getFirstnameNotExisting().toUpperCase();
				a.setCifname(lname + ", " + fname);

				if (dbServiceCOOP.save(a)) {

					System.out.println(">>>>> SUCCESS CREATING APPLICATION FOR " + a.getAppno() + ", " + a.getLastname()
							+ " - " + a.getFirstname());
					// Generate Loan Fees per App - Kevin 09.16.2018
					if (a.getLoanproduct() != null && !a.getLoanproduct().equals("")) {
						LoanFeeInputForm feeform = new LoanFeeInputForm();
						feeform.setAppno(a.getAppno());
						LoanProductService prodsrvc = new LoanProductServiceImpl();
						prodsrvc.generateLoanFeesPerApp(feeform);
					}

					// Email Submit Application (Kevin 10.12.2018)
					param.put("appno", a.getAppno());
					param.put("username", UserUtil.securityService.getUserName());
					// MAR
					dbServiceCOOP.execSQLQueryTransformer(
							"EXEC sp_InsertEmailSMTP @appno=:appno, @username=:username, @emailcode = 'EM6', @body =NULL",
							param, null, 0);

					// HISTORY (Kevin 10.22.2019)
					HistoryService h = new HistoryServiceImpl();
					h.saveHistory(a.getAppno(), AuditLogEvents.CREATE_APPLICATION,
							"Created new application - <font size=\"2\"><b>\"FOR ENCODING\"</b>.");

					// AS-PER-NOREEN...FOR-EVERY-LOANAPP-CREATED,-SHOULD-GENERATE-DOCUMENTS-BASED-ON-LOAN-PRODUCT
					DocumentationServiceImpl.createDocumentsPerApplication(a.getAppno());// DocumentServiceImpl-Line-178-DANIEL-09.01.2018

					// Saving Lstappindividual
					Tblstappindividual indiv = new Tblstappindividual();
					indiv.setLastname(form.getLastnameNotExisting().toUpperCase());
					indiv.setFirstname(form.getFirstnameNotExisting().toUpperCase());
					indiv.setAppno(a.getAppno());

					if (dbServiceCOOP.save(indiv)) {
						dbService.saveOrUpdate(indiv);
						f.setFlag("success");
						f.setErrorMessage(no);
					} else {
						f.setFlag("failed");
					}
				}
			}

			else if (form.getCustomertype().equals("2")) {
				a.setIsexisting(false);
				a.setBusinessname(form.getBusinessnameNotExisting().toUpperCase());
				a.setCustomertype(form.getCustomertype()); // Individual or // Corporate
				a.setApplicationtype(form.getApplicationtype());
				// Loan // Application(LO),
				// Line // Application(LI),
				// Line // Renewal(RE),
				// Line // Amendment(AM),
				// Line // Availment(AV),
				// Loan Roll // Over(RO)
				a.setApplicationdate(new Date());
				a.setDatecreated(new Date());
				a.setCreatedby(secservice.getUserName());
				a.setApplicationstatus(1); // For Encoding
				a.setCreateevalreportflag(0);// Initial Eval Report Flag
				a.setStatusdatetime(new Date());

				param.put("user", secservice.getUserName());
				// MAR
				Tbuser user1 = (Tbuser) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbuser WHERE username=:user", param);
				param.put("name", user1.getUsername());
				param.put("code", user1.getTeamcode());

				param.put("user", secservice.getUserName());
				user = (Tbuser) dbServiceCOOP.executeUniqueHQLQueryMaxResultOne("FROM Tbuser WHERE username=:user",
						param);
				a.setLosoriginatingteam(user.getTeamcode());
				a.setIsdoneencoding(false);
				a.setBranchcode(user.getBranchcode());
				a.setCompanycode(user.getCompanycode());

				String no = "";

				// Full Name
				String businessFullname = form.getBusinessnameNotExisting().toUpperCase();
				a.setCifname(businessFullname);
				// Jan 31. 2019 //
				System.out.println("--------- form.getApplicationtype() : " + //
						form.getApplicationtype());
				if (form.getApplicationtype() != null) {
					if (form.getApplicationtype().equals("1") || form.getApplicationtype() == (1)) {
						no = ApplicationNoGenerator.generateApplicationNo("LO");
					} else if (form.getApplicationtype().equals("2") || form.getApplicationtype() == (2)) {
						no = ApplicationNoGenerator.generateApplicationNo("LI");
					} else if (form.getApplicationtype().equals("3") || form.getApplicationtype() == (3)) {
						no = ApplicationNoGenerator.generateApplicationNo("RE");
					} else if (form.getApplicationtype().equals("4") || form.getApplicationtype() == (4)) {
						no = ApplicationNoGenerator.generateApplicationNo("AM");
					} else if (form.getApplicationtype().equals("5") || form.getApplicationtype() == (5)) {
						no = ApplicationNoGenerator.generateApplicationNo("AV");
					} else if (form.getApplicationtype().equals("6") || form.getApplicationtype() == (6)) {
						no = ApplicationNoGenerator.generateApplicationNo("RO");
					}
				}
				a.setAppno(no);

				if (dbServiceCOOP.save(a)) {
					System.out.println(">>>>> SUCCESS CREATING APPLICATION FOR " + a.getBusinessname());

					// AS-PER-NOREEN...FOR-EVERY-LOANAPP-CREATED,-SHOULD-GENERATE-DOCUMENTS-BASED-ON
					// -LOAN-PRODUCT
					DocumentationServiceImpl.createDocumentsPerApplication(a.getAppno());
					// DocumentServiceImpl-Line-178-DANIEL-09.01.2018

					// Generate Loan Fees per App - Kevin 09.16.2018
					if (a.getLoanproduct() != null && !a.getLoanproduct().equals("")) {
						LoanFeeInputForm feeform = new LoanFeeInputForm();
						feeform.setAppno(a.getAppno());
						LoanProductService prodsrvc = new LoanProductServiceImpl();
						prodsrvc.generateLoanFeesPerApp(feeform);
					}

					// Email Submit Application (Kevin 10.12.2018)
					param.put("appno", a.getAppno());
					param.put("username", UserUtil.securityService.getUserName());

					dbServiceCOOP.execSQLQueryTransformer(
							"EXEC sp_InsertEmailSMTP @appno=:appno, @username=:username, @emailcode = 'EM6', @body =NULL",
							param, null, 0);
					// HISTORY (Kevin 10.22.2019)
					HistoryService h = new HistoryServiceImpl();
					h.saveHistory(a.getAppno(), AuditLogEvents.CREATE_APPLICATION,
							"Created new application - <font size=\"2\"><b>\"FOR ENCODING\"</b>.");
					// Corporate
					// Saving Lstappcorp/business
					Tblstappcorporate corp = new Tblstappcorporate();
					// corp.setCifno(no);
					corp.setAppno(no);
					corp.setCorporatename(form.getBusinessnameNotExisting().toUpperCase());
					corp.setDateofincorporation(form.getDate()); // Added by Pong : Saving of
					corp.setAssignedto(secservice.getUserName());
					corp.setCiftype("2"); // Corp Business
					corp.setCifstatus("1"); // For Encoding

					// Employment and Business - Quick // Setup
					if (dbServiceCOOP.save(corp)) {
						dbServiceCOOP.saveOrUpdate(corp);
						f.setFlag("success");
						f.setErrorMessage(no);
					} else {
						f.setFlag("failed");
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	// MAR 10-29-2020

	@Override
	public FormValidation generateCreditLine(QDEParameterForm form) {
		FormValidation f = new FormValidation();
		try {
			if (form.getCifno() != null) {
				String appno = "";
				if (form.getApplicationtype() != null) {
					if (form.getApplicationtype().equals("1") || form.getApplicationtype() == (1)) {
						appno = ApplicationNoGenerator.generateApplicationNo("LO");
					} else if (form.getApplicationtype().equals("2") || form.getApplicationtype() == (2)) {
						appno = ApplicationNoGenerator.generateApplicationNo("LI");
					} else if (form.getApplicationtype().equals("3") || form.getApplicationtype() == (3)) {
						appno = ApplicationNoGenerator.generateApplicationNo("RE");
					} else if (form.getApplicationtype().equals("4") || form.getApplicationtype() == (4)) {
						appno = ApplicationNoGenerator.generateApplicationNo("AM");
					} else if (form.getApplicationtype().equals("5") || form.getApplicationtype() == (5)) {
						appno = ApplicationNoGenerator.generateApplicationNo("AV");
					} else if (form.getApplicationtype().equals("6") || form.getApplicationtype() == (6)) {
						appno = ApplicationNoGenerator.generateApplicationNo("RO");
					}
				}
				if (appno != null) {
					System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" + appno);
					f.setFlag("success");
					f.setErrorMessage(appno);
					f.setAppno(appno);

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return f;
	}

	@Override
	public String setupApprovedCIF(QDEParameterForm form, String ciftype, String cifstatus, Boolean isencoding) {
		// TODO Auto-generated method stub

		String flag = "failed";
		DBService dbService = new DBServiceImplCIF();
		DBService dbServiceCOOP = new DBServiceImpl();
		Tbcifmain main = new Tbcifmain();
		Tbcifindividual indiv = new Tbcifindividual();
		Tbcifcorporate corp = new Tbcifcorporate();
		Tbuser user = new Tbuser();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (form.getCustType().equals("I") || form.getCustType().equals("1")) {
				// Individual
				param.put("fname", form.getFname());
				param.put("mname", form.getMname());
				param.put("lname", form.getLname());
				param.put("dob", form.getDate());
				indiv = (Tbcifindividual) dbService.execStoredProc(
						"SELECT * FROM Tbcifindividual WHERE firstname=:fname AND middlename=:mname AND lastname=:lname AND CAST(dateofbirth AS DATE) = CAST(:dob AS DATE)",
						param, Tbcifindividual.class, 0, null);
				if (indiv != null) {
					return "CIF Record already exist.";
				}
				indiv = new Tbcifindividual();
				main.setCifno(form.getCifno() != null && !form.getCifno().isEmpty() ? form.getCifno()
						: CIFNoGenerator.generateCIFNo("INDIVIDUAL"));
				main.setEncodedby(secservice.getUserName());
				main.setAssignedto(secservice.getUserName());
				main.setEncodeddate(new Date());
				main.setDateupdated(new Date());
				main.setCustomertype("1");
				main.setCifstatus(cifstatus); // parameterized
				main.setAccreditationstatus("0");
				main.setIsencoding(isencoding); // parameterized
				// CED 08012022
				main.setOriginatingbranch(UserUtil.getUserByUsername(secservice.getUserName()).getBranchcode());
				/** 012318 As per PONG **/
				if (ciftype == null) {
					main.setCiftype("3"); // Related Entity
				} else {
					main.setCiftype(ciftype); // Prospect
				}
				// Added by Pong : Saving of originating team 07-11-17
				String username = UserUtil.securityService.getUserName();
				if (username != null) {
					params.put("username", username);
					user = (Tbuser) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
					if (user != null) {
						if (user.getTeamcode() != null) {
							main.setOriginatingteam(user.getTeamcode());
						}
					}
				}
				// Full Name
				String lname = form.getLname().toUpperCase();
				String fname = form.getFname().toUpperCase();
				String mname = form.getMname() == null ? "" : form.getMname().toUpperCase();
				String suf = form.getSuffix() == null ? "" : form.getSuffix().toUpperCase();
				main.setFullname(lname + ", " + fname + " " + suf + " " + mname);
				
				// 02.02.2023
				main.setFullname(main.getFullname().trim().replaceAll(" +", " "));
				
				main.setDateofbirth(form.getDate());

				indiv.setCifno(form.getCifno());
				indiv.setLastname(form.getLname().toUpperCase());
				indiv.setFirstname(form.getFname().toUpperCase());
				indiv.setMiddlename(form.getMname() == null ? "" : form.getMname().toUpperCase());
				indiv.setSuffix(form.getSuffix() == null ? "" : form.getSuffix().toUpperCase());
				indiv.setDateofbirth(form.getDate());

				// 01-25-18 PONGYU
				// check legal age
				// if (indiv.getDateofbirth() != null) {
				// 	int age = DateTimeUtil.getAge(indiv.getDateofbirth());
				// 	if (age < 18) {
				// 		return "Age should not be less than the legal age.";
				// 	}
				// }

				indiv.setTin(form.getTin());
				indiv.setSss(form.getSss());
				indiv.setStreetno1(form.getStreetno());
				indiv.setSubdivision1(form.getSubdivision());
				indiv.setCountry1(form.getCountry());
				indiv.setCity1(form.getCountry());
				indiv.setBarangay1(form.getBarangay());
				indiv.setPostalcode1(form.getPostalcode());
				if (dbService.saveOrUpdate(main)) {
					dbService.saveOrUpdate(indiv);
					// 08-08-17 PONGYU
					flag = "success";
					HistoryService h = new HistoryServiceImpl();
					h.addHistory(indiv.getCifno(), "Encoded new CIF record.", null);
				} else {
					flag = "failed";
				}

			} else if (form.getCustType().equals("C") || form.getCustType().equals("2")) {
				// Corporate
				param.put("corpname", form.getBusinessname());
				corp = (Tbcifcorporate) dbService
						.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE corporatename=:corpname", param);
				if (corp != null) {
					return "CIF Record already exist.";
				}
				corp = new Tbcifcorporate();

				main.setCifno(form.getCifno() != null && !form.getCifno().isEmpty() ? form.getCifno()
						: CIFNoGenerator.generateCIFNo("CORPORATE"));
				main.setEncodedby(secservice.getUserName());
				main.setAssignedto(secservice.getUserName());
				main.setEncodeddate(new Date());
				main.setDateupdated(new Date());
				main.setCustomertype("2");
				main.setAccreditationstatus("0");
				main.setCifstatus(cifstatus); // parameterized
				main.setIsencoding(isencoding); // parameterized
				/** 012318 As per PONG **/
				if (ciftype == null) {
					main.setCiftype("3"); // Related Entity
				} else {
					main.setCiftype(ciftype); // Prospect
				}
				main.setFullname(form.getBusinessname().toUpperCase());
				main.setDateofincorporation(form.getDate());
				// Added by Pong : Saving of originating team 07-11-17
				String username = UserUtil.securityService.getUserName();
				if (username != null) {
					params.put("username", username);
					user = (Tbuser) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
					if (user != null) {
						if (user.getTeamcode() != null) {
							main.setOriginatingteam(user.getTeamcode());
						}
					}
				}
				corp.setCifno(form.getCifno());
				corp.setCorporatename(form.getBusinessname().toUpperCase());
				corp.setDateofincorporation(form.getDate());
				corp.setBusinesstype(form.getBtype()); // modified July 24, 2017 for Employment and Business - Quick
														// Setup

				corp.setTin(form.getTin());
				corp.setSss(form.getSss());
				corp.setStreetno1(form.getStreetno());
				corp.setSubdivision1(form.getSubdivision());
				corp.setCountry1(form.getCountry());
				corp.setCity1(form.getCountry());
				corp.setBarangay1(form.getBarangay());
				corp.setPostalcode1(form.getPostalcode());

				if (dbService.saveOrUpdate(main)) {
					dbService.saveOrUpdate(corp);
					flag = "success";
					// 08-08-17 PONGYU
					HistoryService h = new HistoryServiceImpl();
					h.addHistory(indiv.getCifno(), "Encoded new CIF record.", null);

				} else {
					flag = "failed";
				}
			} else if (form.getCustType().equals("S")) {
				// Sole Prop

				main.setCifno(form.getCifno());
				main.setEncodedby(secservice.getUserName());
				main.setAssignedto(secservice.getUserName());
				main.setEncodeddate(new Date());
				main.setDateupdated(new Date());
				main.setCustomertype("3");
				main.setAccreditationstatus("0");
				main.setCifstatus("1"); // For Encoding
				/** 012318 As per PONG **/
				if (ciftype == null) {
					main.setCiftype("3"); // Related Entity
				} else {
					main.setCiftype("0"); // Prospect
				}
				main.setFullname(form.getBusinessname().toUpperCase());
				main.setDateofincorporation(form.getDate());
				// Added by Pong : Saving of originating team 07-11-17
				String username = UserUtil.securityService.getUserName();
				if (username != null) {
					params.put("username", username);
					user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
					if (user != null) {
						if (user.getTeamcode() != null) {
							main.setOriginatingteam(user.getTeamcode());
						}
					}
				}
				corp.setCifno(form.getCifno());
				corp.setCorporatename(form.getBusinessname().toUpperCase());
				corp.setDateofincorporation(form.getDate());
				if (form.getBtype() != null) {
					corp.setBusinesstype(form.getBtype()); // modified July 24, 2017 for Employment and Business -
															// Quick
															// Setup
				} else {
					corp.setBusinesstype("09");
				}

				corp.setTin(form.getTin());
				corp.setSss(form.getSss());
				corp.setStreetno1(form.getStreetno());
				corp.setSubdivision1(form.getSubdivision());
				corp.setCountry1(form.getCountry());
				corp.setCity1(form.getCountry());
				corp.setBarangay1(form.getBarangay());
				corp.setPostalcode1(form.getPostalcode());

				if (dbService.saveOrUpdate(main)) {
					dbService.saveOrUpdate(corp);
					flag = "success";
					// 08-08-17 PONGYU
					HistoryService h = new HistoryServiceImpl();
					h.addHistory(indiv.getCifno(), "Encoded new CIF record.", null);

				} else {
					flag = "failed";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}