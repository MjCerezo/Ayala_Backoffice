package com.etel.dedupe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbamlalistmain;
import com.coopdb.data.Tbappemployment;
import com.coopdb.data.Tbblacklistmain;
import com.coopdb.data.Tbemployee;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmemberrelationship;
import com.coopdb.data.Tbmembershipapp;
import com.coopdb.data.Tbreferror;
import com.coopdb.data.Tbuser;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.dataentryutil.MemberRelations;
import com.etel.dedupe.forms.SearchParameters;
import com.etel.dedupe.forms.SearchResult;
import com.etel.dedupe.forms.fromMasterList;
import com.etel.documents.DocumentServiceImpl;
import com.etel.forms.ReturnForm;
import com.etel.qde.GetterSetter;
import com.etel.utils.ApplicationNoGenerator;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class DedupeServiceImpl implements DedupeService {

	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> param = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private GetterSetter transfer = new GetterSetter();
	private FullDataEntryServiceImpl fde = new FullDataEntryServiceImpl();

	@SuppressWarnings("unchecked")
	@Override
	public SearchResult getSearchResult(SearchParameters params) {
		// TODO Auto-generated method stub
		SearchResult search = new SearchResult();
		param.put("employeeid", params.getEmployeeid());
		param.put("companycode", params.getCompanycode());
		param.put("rmemberid", params.getReferror());

		param.put("firstname", params.getFirstname() != null ? "%" + params.getFirstname().trim() + "%" : "%%");
		param.put("lastname", params.getLastname() != null ? "%" + params.getLastname().trim() + "%" : "%%");
		param.put("middlename", params.getMiddlename() != null ? "%" + params.getMiddlename().trim() + "%" : "%%");
		String f = params.getFirstname() != null ? params.getFirstname().trim() : "";
		String m = params.getLastname() != null ? params.getLastname().trim() : "";
		String l = params.getLastname() != null ? params.getLastname().trim() : "";
		param.put("fullname", l + " " + f + " " + m);

		String memQuery = "SELECT m.dateofbirth, m.membershipappid, m.lastname, m.firstname, m.middlename, m.suffix, m.membershipdate, m.membershipid, m.membershipstatus, m.employeeid, m.membershipclass FROM Tbmember m WHERE m.companycode=:companycode";
		String appQuery = "SELECT a.applicationdate, a.membershipappstatus, a.dateofbirth, a.membershipappid, a.lastname, a.firstname, a.middlename, a.suffix, a.membershipstatus, a.employeeid, a.membershipclass FROM Tbmembershipapp a WHERE a.companycode=:companycode";
		String empQuery = "SELECT emp.firstname, emp.lastname, emp.middlename, emp.datehired, emp.dateofbirth, emp.employmentstatus, emp.employeeid, emp.companyname FROM TBEMPLOYEE emp WHERE emp.companycode=:companycode";
//		String relQuery = "SELECT r.relationshipcode, r.relatedappid, r.relatedmemberid, r.lastname, r.firstname, r.middlename, r.dateofbirth, r.relationshipdesc FROM TBMEMBERRELATIONSHIP r LEFT JOIN TBMEMBER m ON m.membershipid=r.mainmemberid WHERE m.membershipid=:rmemberid";
		String relQuery = "SELECT r.relationshipcode, r.relatedappid, r.relatedmemberid, r.lastname, r.firstname, r.middlename, r.dateofbirth, r.relationshipdesc FROM TBMEMBERRELATIONSHIP r WHERE r.mainmemberid=:rmemberid";
		String amlQuery = "SELECT a.cifno, a.amlalistid, a.dateofbirth, a.fullname, a.startdate, a.enddate, a.status FROM TBAMLALISTMAIN a LEFT JOIN Tbamlaindividual i ON i.amlalistid=a.amlalistid WHERE i.firstname like:firstname AND i.lastname like:lastname AND i.middlename like:middlename";
		String blkQuery = "SELECT a.cifno, a.blacklistid, a.dateofbirth, a.fullname, a.startdate, a.enddate, a.status FROM Tbblacklistmain a LEFT JOIN Tbblacklistindividual i ON i.blacklistid=a.blacklistid WHERE i.firstname like:firstname AND i.lastname like:lastname AND i.middlename like:middlename";
		try {
			// employeeid
			if (params.getEmployeeid() != null) {
				memQuery += " AND m.employeeid=:employeeid";
				appQuery += " AND a.employeeid=:employeeid";
				empQuery += " AND emp.employeeid=:employeeid";
			}
			// name fields
			if (params.getFirstname() != null || params.getLastname() != null || params.getMiddlename() != null) {
				memQuery += " AND m.firstname like:firstname AND m.lastname like:lastname AND m.middlename like:middlename";
				appQuery += " AND a.firstname like:firstname AND a.lastname like:lastname AND a.middlename like:middlename";
				empQuery += " AND emp.firstname like:firstname AND emp.lastname like:lastname AND emp.middlename like:middlename";
			}
			// dob
			if (params.getDateofbirth() != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String date = formatter.format(params.getDateofbirth());
				memQuery += " AND m.dateofbirth BETWEEN '" + date + " 00:00:00' AND '" + date + " 23:59:00'";
				appQuery += " AND a.dateofbirth BETWEEN '" + date + " 00:00:00' AND '" + date + " 23:59:00'";
				empQuery += " AND emp.dateofbirth BETWEEN '" + date + " 00:00:00' AND '" + date + " 23:59:00'";
				amlQuery += " AND a.dateofbirth BETWEEN '" + date + " 00:00:00' AND '" + date + " 23:59:00'";
				blkQuery += " AND a.dateofbirth BETWEEN '" + date + " 00:00:00' AND '" + date + " 23:59:00'";
			}
			// etc.
			if (params.getRelationship() != null) {
				param.put("relationship", params.getRelationship());
				relQuery += " AND r.relationshipcode=:relationship";
			}
			// set queries
			search.setMembership((List<Tbmember>) dbService.execSQLQueryTransformer(memQuery, param, Tbmember.class, 1));
			search.setApplication((List<Tbmembershipapp>) dbService.execSQLQueryTransformer(appQuery, param, Tbmembershipapp.class, 1));
			search.setRelatives(params.getReferror()==null?new ArrayList<Tbmemberrelationship>()
					:(List<Tbmemberrelationship>) dbService.execSQLQueryTransformer(relQuery, param, Tbmemberrelationship.class, 1));
			search.setEmployee((List<Tbemployee>) dbService.execSQLQueryTransformer(empQuery, param, Tbemployee.class, 1));
			search.setAmla((List<Tbamlalistmain>) dbService.execSQLQueryTransformer(amlQuery, param, Tbamlalistmain.class, 1));
			search.setBlacklist((List<Tbblacklistmain>) dbService.execSQLQueryTransformer(blkQuery, param, Tbblacklistmain.class, 1));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return search;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbemployee> getList(SearchParameters params) {
		// TODO Auto-generated method stub
		List<Tbemployee> emp = new ArrayList<Tbemployee>();
		String empQuery = "SELECT emp.firstname, emp.lastname, emp.middlename, emp.datehired, emp.dateofbirth, emp.employmentstatus, emp.employeeid, emp.companyname FROM TBEMPLOYEE emp WHERE emp.companycode=:companycode";
		try {
			param.put("employeeid", params.getEmployeeid());
			param.put("companycode", params.getCompanycode());
			if (params.getFirstname() != null) {
				params.setFirstname(params.getFirstname().trim());
			}
			if (params.getLastname() != null) {
				params.setLastname(params.getLastname().trim());
			}
			if (params.getMiddlename() != null) {
				params.setMiddlename(params.getMiddlename().trim());
			}
			param.put("firstname", params.getFirstname() != null ? "%" + params.getFirstname() + "%" : "%%");
			param.put("lastname", params.getLastname() != null ? "%" + params.getLastname() + "%" : "%%");
			param.put("middlename", params.getMiddlename() != null ? "%" + params.getMiddlename() + "%" : "%%");
			if (params.getEmployeeid() != null) {
				empQuery += " AND emp.employeeid=:employeeid";
			}
			if (params.getFirstname() != null || params.getLastname() != null || params.getMiddlename() != null) {
				empQuery += " AND emp.firstname like:firstname AND emp.lastname like:lastname AND emp.middlename like:middlename";
			}
			if (params.getDateofbirth() != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				String date = formatter.format(params.getDateofbirth());
				empQuery += " AND emp.dateofbirth BETWEEN '" + date + " 00:00:00' AND '" + date + " 23:59:00'";
			}
			emp = (List<Tbemployee>) dbService.execSQLQueryTransformer(empQuery, param, Tbemployee.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return emp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmember> getReferror(String name) {
		// TODO Auto-generated method stub
		List<Tbmember> m = new ArrayList<Tbmember>();
		String qry = "SELECT membershipid, firstname, lastname, middlename, employeeid, companycode, companyname, dateofbirth, membershipstatus FROM Tbmember WHERE ";
		try {
			int spacenumber = name.split("\\ ", -1).length - 1;
			int dividedstring = spacenumber + 1;
			if (name.indexOf(" ") != -1) { // if there's a space..multiple parameters<<
				String[] n = name.split(" ");
				for (int i = 0; i < dividedstring; i++) {
					if (n[i].indexOf(",") != -1) { // if there's a comma..delete comma<<
						n[i] = n[i].replace(",", "");
					}
					String nameque = "name" + i;
					param.put(nameque, n[i] == null ? "'%%'" : "%" + n[i] + "%");
					qry += "((lastname like:" + nameque + " or firstname like:" + nameque + " or middlename like:"
							+ nameque + ") and membershipclass='1') ";
					qry += " or ";
				}
			}
			param.put("name", "%" + name + "%");
			qry += " ((lastname like :name or firstname like :name or middlename like :name) and membershipclass='1')";
			m = (List<Tbmember>) dbService.execSQLQueryTransformer(qry, param, Tbmember.class, 1);
//			m = (List<Tbmember>) dbService.execSQLQueryTransformer(
//					"SELECT membershipid, firstname, lastname, middlename, employeeid, companycode, companyname, dateofbirth, membershipstatus FROM Tbmember WHERE membername like:name AND membershipclass='1'",
//					param, Tbmember.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return m;
	}

	@Override
	public ReturnForm createApplication(SearchParameters form, String apptype) {
		// TODO Auto-generated method stub
		ReturnForm ret = new ReturnForm();
		Tbmembershipapp napp = new Tbmembershipapp();
		Tbuser user = new Tbuser();
		Tbmember mem = new Tbmember();
		Tbmembershipapp app = new Tbmembershipapp();
		try {
			param.put("companycode", form.getCompanycode());
			param.put("employeeid", form.getEmployeeid());
			param.put("user", secservice.getUserName());

			app = (Tbmembershipapp) dbService.executeUniqueHQLQuery(
					"FROM Tbmembershipapp WHERE employeeid=:employeeid AND companycode=:companycode", param);

			mem = (Tbmember) dbService.executeUniqueHQLQuery(
					"FROM Tbmember WHERE employeeid=:employeeid AND companycode=:companycode", param);

			user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:user", param);

			napp.setMembershipappstatus("1");
			napp.setMembershipstatus("7");
			napp.setApplicanttype("1");
			napp.setApplicationdate(new Date());
			napp.setEncodedby(secservice.getUserName());
			napp.setCoopcode(user.getCoopcode());
			napp.setDateofbirth(form.getDateofbirth());
			napp.setCountry1("PH");
			napp.setCountry2("PH");
			napp.setCountryofbirth("PH");
			napp.setOriginatingbranch(user.getBranchcode());
			napp.setFirstname(form.getFirstname());
			napp.setLastname(form.getLastname());
			napp.setMiddlename(form.getMiddlename() != null ? form.getMiddlename() : "");
			napp.setCompanycode(form.getCompanycode());
			napp.setEmployeeid(form.getEmployeeid() != null ? form.getEmployeeid() : "");
			napp.setMembershipclass(apptype);

			if (!apptype.equals("2")) {
				if (mem != null) {
					String m = mem.getMiddlename() != null ? mem.getMiddlename() : "";
					ret.setFlag("MEMBER");
					ret.setMessage("<b>" + mem.getEmployeeid() + ", " + mem.getFirstname() + " " + m + " "
							+ mem.getLastname() + "</b> is already a member.");
					return ret;
				} else if (app != null) {
					ret.setFlag("APPLICANT");
					String m = app.getMiddlename() != null ? app.getMiddlename() : "";
					ret.setMessage("<b>" + app.getEmployeeid() + ", " + app.getFirstname() + " " + m + " "
							+ app.getLastname() + "</b> has an on-going application.");
					return ret;
				} else {
					// APPLICATION NUMBER
					napp.setMembershipappid(ApplicationNoGenerator.generateID("A", user.getCoopcode()));

					ret.setFlag(napp.getMembershipappid());
					Tbemployee e = (Tbemployee) dbService.executeUniqueHQLQuery(
							"FROM Tbemployee WHERE employeeid=:employeeid and companycode=:companycode", param);
					// GET FROM TBEMPLOYEE | SAVE TO TBMEMBERSHIPAPP
					napp = transfer.getEmployee(napp, e);
					// GET FROM TBEMPLOYEE | SAVE TO TBAPPEMPLOYMENT
					Tbappemployment emp = new Tbappemployment();
					emp = transfer.setEmployment(emp, e, napp.getMembershipappid());
				}
			} else {
//				napp.setServicestatus("5");
//				napp.setMembershipappid(ApplicationNoGenerator.generateID("A", user.getCoopcode()));

				param.put("firstname", form.getFirstname() != null ? form.getFirstname().trim() : "");
				param.put("lastname", form.getLastname() != null ? form.getLastname().trim() : "");
				param.put("middlename", form.getMiddlename() != null ? form.getMiddlename().trim() : "");
				String qry = "FROM Tbmembershipapp WHERE firstname=:firstname AND lastname=:lastname";
				if (form.getMiddlename() != null) {
					qry += " AND middlename=:middlename";
				}
				if (form.getDateofbirth() != null) {
					param.put("dateofbirth", form.getDateofbirth());
					qry += " AND dateofbirth=:dateofbirth";
				}
				Tbmembershipapp exstng = (Tbmembershipapp) dbService.executeUniqueHQLQueryMaxResultOne(qry, param);
				if (exstng != null) {
					ret.setFlag("APPLICANT");
					String m = exstng.getMiddlename() != null ? exstng.getMiddlename() : "";
					ret.setMessage("<b>" + exstng.getMembershipappid() + ", " + exstng.getFirstname() + " " + m + " "
							+ exstng.getLastname() + "</b> has an on-going application.");
					return ret;
				} else {
					napp.setMembershipappid(ApplicationNoGenerator.generateID("A", user.getCoopcode()));
				}
				param.put("rmemberid", form.getReferror());
				param.put("rcompanycode", form.getReferrorcompany());

				// VALIDATION - CODE CUTTING WITH LACKING PARAMETERS
				Tbmember rmem = (Tbmember) dbService
						.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:rmemberid", param);
				if (rmem != null) {
					napp.setCompanycode(rmem.getCompanycode());
					param.put("memberid", rmem.getMembershipid());
					Tbmemberrelationship r = (Tbmemberrelationship) dbService.executeUniqueHQLQuery(
							"FROM Tbmemberrelationship WHERE firstname =:firstname AND lastname =:lastname AND"
									+ " middlename =:middlename AND mainmemberid =:memberid",
							param);
					if (r == null) {
						if (form.getRelationship() == null) {
							ret.setFlag("REFERROR");
							ret.setMessage("Cannot create application without relationship details.");
						}
					} else if (r.getRelatedappid() != null && !r.getRelatedappid().equals("")) {
						ret.setFlag("APPLICANT");
						param.put("arappid", r.getRelatedappid());
						Tbmembershipapp ar = (Tbmembershipapp) dbService
								.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:arappid", param);
						String m = ar.getMiddlename() != null ? ar.getMiddlename() : "";
						ret.setMessage("<b>" + ar.getMembershipappid() + ", " + ar.getFirstname() + " " + m + " "
								+ ar.getLastname() + "</b> has an on-going application.");
					}
				} else {
					ret.setFlag("REFERROR");
					ret.setMessage("Problem processing referral details. Cannot proceed to application this time.");
				}
			}
			if (dbService.save(napp)) {

				if (apptype.equals("2")) {

					Tbreferror r = new Tbreferror();
					param.put("rmemberid", form.getReferror());
					Tbmember rmem = (Tbmember) dbService
							.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:rmemberid", param);
//					param.put("remployeeid", form.getReferror());
//					param.put("rcompanycode", form.getReferrorcompany());
//
//					Tbmember rmem = (Tbmember) dbService.executeUniqueHQLQuery(
//							"FROM Tbmember WHERE employeeid=:remployeeid and companycode=:rcompanycode", param);

					r.setEmpid(rmem.getEmployeeid());
					r.setEmployername(rmem.getCompanyname());
					r.setMembershipappid(napp.getMembershipappid());
					r.setMembershipid(null);
					r.setPosition((String) dbService.executeUniqueHQLQuery(
							"SELECT ISNULL(position,'') FROM Tbmemberemployment WHERE employeeid=:remployeeid and companyid=:rcompanycode",
							param));
					r.setReferrorname(rmem.getFirstname() + " " + rmem.getMiddlename() + " " + rmem.getLastname());
					r.setRefmemberid(rmem.getMembershipid());
					fde.addReferrorPerParam(r);

					param.put("relappid", napp.getMembershipappid());
					param.put("memberid", rmem.getMembershipid());

					param.put("firstname", form.getFirstname() != null ? form.getFirstname() : "");
					param.put("lastname", form.getLastname() != null ? form.getLastname() : "");
					param.put("middlename", form.getMiddlename() != null ? form.getMiddlename() : "");

					// Creating referral relationship needs the required parameters or membership
					// application will process without relationship details
					if (form.getFirstname() != null && form.getLastname() != null && rmem.getMembershipid() != null) {
						String qry = "UPDATE TBMEMBERRELATIONSHIP SET relatedappid=:relappid WHERE ";
						if (form.getFirstname() != null) {
							qry += "firstname =:firstname AND ";
						}
						if (form.getLastname() != null) {
							qry += "lastname =:lastname AND ";
						}
						if (form.getMiddlename() != null) {
							qry += "middlename =:middlename AND ";
						}
						qry += "mainmemberid =:memberid";
						Integer i = dbService.executeUpdate(qry, param);
						if (i < 1) {
							if (form.getRelationship() != null) {
								MemberRelations mr = new MemberRelations();
								mr.createAssocRegularRelationship(rmem, napp, form.getRelationship());
							}
						}
					}

				}

				fde.createDOSRI(napp.getMembershipappid());

				Map<String, String> mapids = new HashMap<String, String>();
				mapids.put("membershipappid", napp.getMembershipappid());
				DocumentServiceImpl.createInitialDocumentChecklist("010", mapids);

				ret.setFlag(napp.getMembershipappid());
				ret.setMessage(
						"Membership Processing Successful!<br><b>" + napp.getFirstname() + " " + napp.getMiddlename()
								+ " " + napp.getLastname() + "</b> , <b>" + napp.getMembershipappid() + "</b>.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return ret;
	}

	@Override
	public fromMasterList getReadonlyFields(String appid) {
		// TODO Auto-generated method stub
		fromMasterList hr = new fromMasterList();
		try {
			if (appid != null) {
				param.put("appid", appid);
				String empid = (String) dbService.executeUniqueSQLQuery(
						"SELECT employeeid FROM TBMEMBERSHIPAPP WHERE membershipappid=:appid", param);
				String company = (String) dbService.executeUniqueSQLQuery(
						"SELECT companycode FROM TBMEMBERSHIPAPP WHERE membershipappid=:appid", param);
				if (empid != null && company != null) {
					param.put("empid", empid);
					param.put("company", company);
					Tbemployee e = (Tbemployee) dbService.executeUniqueHQLQuery(
							"FROM Tbemployee WHERE employeeid=:empid AND companycode=:company", param);
					if (e != null) {
						hr.setFirstname(e.getFirstname() != null);
						hr.setLastname(e.getLastname() != null);
						hr.setMiddlename(e.getMiddlename() != null);
						hr.setDateofbirth(e.getDateofbirth() != null);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return hr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmember> getSearchPerson(String name) {
		// TODO Auto-generated method stub
		List<Tbmember> m = new ArrayList<Tbmember>();
		String qryMember = "SELECT membershipstatus, membershipclass, membershipid, membershipappid, firstname, lastname, middlename FROM TBMEMBER WHERE";
		String qryApp = "SELECT membershipstatus, membershipclass, membershipid, membershipappid, firstname, lastname, middlename FROM TBMEMBERSHIPAPP WHERE";
		try {
			int spacenumber = name.split("\\ ", -1).length - 1;
			int dividedstring = spacenumber + 1;
			if (name.indexOf(" ") != -1) { // if there's a space..multiple parameters<<
				String[] n = name.split(" ");
				for (int i = 0; i < dividedstring; i++) {
					if (n[i].indexOf(",") != -1) { // if there's a comma..delete comma<<
						n[i] = n[i].replace(",", "");
					}
					String nameque = "name" + i;
					param.put(nameque, n[i] == null ? "'%%'" : "%" + n[i] + "%");
					qryMember += "((lastname like:" + nameque + " or firstname like:" + nameque + " or middlename like:"
							+ nameque + ") and membershipclass='1') ";
					qryApp += "((lastname like:" + nameque + " or firstname like:" + nameque + " or middlename like:"
							+ nameque + ") and membershipclass='1') ";
					qryMember += " or ";
					qryApp += " or ";
				}
			}
			param.put("name", "%" + name + "%");
			qryMember += " (lastname like :name or firstname like :name or middlename like :name)";
			qryApp += " (lastname like :name or firstname like :name or middlename like :name)";
			qryApp += " AND membershipappid NOT IN (SELECT membershipappid FROM TBMEMBER)";
			String qry = qryMember + " UNION ALL " + qryApp;
			m = (List<Tbmember>) dbService.execSQLQueryTransformer(qry, param, Tbmember.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return m;
	}

}
