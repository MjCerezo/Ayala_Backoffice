package com.etel.dataentry;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudfoundry.org.codehaus.jackson.map.DeserializationConfig;
import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import com.casa.acct.AccountService;
import com.casa.acct.AccountServiceImpl;
import com.casa.acct.forms.AccountGenericForm;
import com.casa.util.UtilService;
import com.casa.util.UtilServiceImpl;
import com.cifsdb.data.Tbcifbusiness;
import com.cifsdb.data.Tbcifcorporate;
import com.cifsdb.data.Tbcifdependents;
import com.cifsdb.data.Tbcifemployment;
import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbmanagement;
import com.cifsdb.data.Tbothercontacts;
import com.cifsdb.data.Tbtradereference;
import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbaccountofficer;
import com.coopdb.data.TbaccountofficerId;
import com.coopdb.data.Tbamortizedattachment;
import com.coopdb.data.Tbappbeneficiary;
import com.coopdb.data.Tbappbusiness;
import com.coopdb.data.Tbappcreditcardinfo;
import com.coopdb.data.Tbappdependents;
import com.coopdb.data.Tbappdosri;
import com.coopdb.data.Tbappemployment;
import com.coopdb.data.Tbappfinancialinfo;
import com.coopdb.data.Tbapppersonalreference;
import com.coopdb.data.Tbbanks;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdepositcif;
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tbloanreleaseinst;
import com.coopdb.data.Tblosdependents;
import com.coopdb.data.Tblosindividual;
import com.coopdb.data.Tblosmain;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tblstappbusiness;
import com.coopdb.data.Tblstappcorporate;
import com.coopdb.data.Tblstappdependents;
import com.coopdb.data.Tblstappindividual;
import com.coopdb.data.Tblstbankaccounts;
import com.coopdb.data.Tblstcomakers;
import com.coopdb.data.TblstcomakersId;
import com.coopdb.data.Tblstcreditcardinfo;
import com.coopdb.data.Tblstexistingloansother;
import com.coopdb.data.Tblstsourceincome;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmembercreditcardinfo;
import com.coopdb.data.Tbmemberemployment;
import com.coopdb.data.Tbmembernetcapping;
import com.coopdb.data.Tbmemberrelationship;
import com.coopdb.data.Tbmembershipapp;
import com.coopdb.data.Tbothercontactslos;
import com.coopdb.data.Tbpdc;
import com.coopdb.data.Tbpledge;
import com.coopdb.data.Tbprodmatrix;
import com.coopdb.data.Tbreferror;
import com.coopdb.data.Tbusermember;
import com.coopdb.data.Tbworkflowprocess;
import com.etel.CustomerRelationship.CustomerRelationshipService;
import com.etel.CustomerRelationship.CustomerRelationshipServiceImpl;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.company.forms.CompanyForm;
import com.etel.dashboard.forms.TMPFrom;
import com.etel.dataentryforms.AccountInfoForm;
import com.etel.dataentryforms.CollectionInstructionsForm;
import com.etel.dataentryforms.LoanFeesForm;
import com.etel.dataentryforms.LoanPayoutForm;
import com.etel.dataentryforms.LoanReleaseInstForm;
import com.etel.dataentryforms.MembershipHeaderForm;
import com.etel.dataentryforms.MembershipListPerStagesForm;
import com.etel.dataentryforms.PersonalDetails;
import com.etel.dataentryforms.RemarksForm;
import com.etel.dataentryutil.ReturnValues;
import com.etel.dataentryutil.createMember;
import com.etel.dataentryutil.sendEmail;
import com.etel.email.EmailCode;
import com.etel.email.EmailFacade;
import com.etel.facade.SecurityServiceImpl;
import com.etel.forms.FormValidation;
import com.etel.generator.NoGenerator;
import com.etel.history.HistoryService;
import com.etel.history.HistoryServiceImpl;
import com.etel.qib.QIBFacade;
import com.etel.security.adnonad.ADInquiryFacade;
import com.etel.util.DateTimeUtil;
import com.etel.utils.ApplicationNoGenerator;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.etel.utils.ImageUtils;
import com.etel.utils.LoggerUtil;
import com.etel.utils.RegexUtil;
import com.etel.utils.UserUtil;
import com.etel.workflow.forms.WorkflowProcessForm;
import com.wavemaker.common.util.IOUtils;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.server.FileUploadResponse;

public class FullDataEntryServiceImpl implements FullDataEntryService {
	private DBService dbService = new DBServiceImpl();
	private DBService dbServiceCIF = new DBServiceImplCIF();
	private Map<String, Object> param = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private createMember m = new createMember();
	private String username = secservice.getUserName();

	@Override
	public String saveOrUpdateMemberApp(Tbmembershipapp app) {
		try {
			if (app.getMembershipappid() == null) {
				return "Membershipappid is null";
			}
			param.put("memappid", app.getMembershipappid());
			Tbmembershipapp m = (Tbmembershipapp) dbService
					.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid =:memappid", param);
			if (m != null) {
				String image = m.getPicture();
				String membershipstatus = m.getMembershipstatus();
//				String coopcode = m.getCoopcode();
				ObjectMapper mapper = new ObjectMapper();
				mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				m = mapper.readValue(mapper.writeValueAsString(app), Tbmembershipapp.class);
				m.setAge(app.getDateofbirth() != null ? DateTimeUtil.getAge(app.getDateofbirth()) : null);
				m.setPicture(image);
				m.setMembershipstatus(membershipstatus);
				// 2x2 picture upload
//				if(app.getPicture() != null) {
//				String input = RuntimeAccess.getInstance().getSession().getServletContext()
//						.getRealPath(app.getPicture());
//				m.setPicture(ImageUtils.imageToBase64(input));
//				}

//				m.setPicture(ImageUtils.imageToBase64(input));
////				if (app.getSameaspermanentaddress()) {
//					m.setStreetnoname2(app.getStreetnoname1());
//					m.setSubdivision2(app.getSubdivision1());
//					m.setBarangay2(app.getBarangay1());
//					m.setStateprovince2(app.getStateprovince1());
//					m.setCity2(app.getCity1());
//					m.setRegion2(app.getRegion1());
//					m.setCountry2(app.getCountry1());
//					m.setPostalcode2(app.getPostalcode1());
//					m.setOccupiedsince2(app.getOccupiedsince1());
//					m.setOwnershiptype2(app.getOwnershiptype1());
//				} else {
//					m.setStreetnoname2(app.getStreetnoname2());
//					m.setSubdivision2(app.getSubdivision2());
//					m.setBarangay2(app.getBarangay2());
//					m.setStateprovince2(app.getStateprovince2());
//					m.setCity2(app.getCity2());
//					m.setRegion2(app.getRegion2());
//					m.setCountry2(app.getCountry2());
//					m.setPostalcode2(app.getPostalcode2());
//					m.setOccupiedsince2(app.getOccupiedsince2());
//					m.setOwnershiptype2(app.getOwnershiptype2());
//				}
//				if (app.getIssameasapplicant()) {
//					m.setSpousestreetnoname(app.getStreetnoname1());
//					m.setSpousesubdivision(app.getSubdivision1());
//					m.setSpousebarangay(app.getBarangay1());
//					m.setSpousestateprovince(app.getStateprovince1());
//					m.setSpousecity(app.getCity1());
//					m.setSpouseregion(app.getRegion1());
//					m.setSpousecountry(app.getCountry1());
//					m.setSpousepostalcode(app.getPostalcode1());
//					m.setSpousefulladdress(fullAddress(app.getStreetnoname1(), app.getSubdivision1(),
//							app.getBarangay1(), app.getCity1(), app.getStateprovince1(), app.getRegion1(),
//							app.getCountry1(), app.getPostalcode1()));
//				} else {
//					m.setSpousecountry(app.getSpousecountry());
//					m.setSpouseregion(app.getSpouseregion());
//					m.setSpousestateprovince(app.getSpousestateprovince());
//					m.setSpousecity(app.getSpousecity());
//					m.setSpousebarangay(app.getSpousebarangay());
//					m.setSpousesubdivision(app.getSpousesubdivision());
//					m.setSpousestreetnoname(app.getSpousestreetnoname());
//					m.setSpousepostalcode(app.getSpousepostalcode());
//					m.setSpousefulladdress(app.getSpousefulladdress());
//				}
//				if (app.getIssameasapplicantmother()) {
//					m.setMotherstreetnoname(app.getStreetnoname1());
//					m.setMothersubdivision(app.getSubdivision1());
//					m.setMotherbarangay(app.getBarangay1());
//					m.setMotherstateprovince(app.getStateprovince1());
//					m.setMothercity(app.getCity1());
//					m.setMotherregion(app.getRegion1());
//					m.setMothercountry(app.getCountry1());
//					m.setMotherpostalcode(app.getPostalcode1());
//					m.setMotherfulladdress(fullAddress(app.getStreetnoname1(), app.getSubdivision1(),
//							app.getBarangay1(), app.getCity1(), app.getStateprovince1(), app.getCountry1(),
//							app.getRegion1(), app.getPostalcode1()));
//				} else {
//					m.setMothercountry(app.getMothercountry());
//					m.setMotherregion(app.getMotherregion());
//					m.setMotherstateprovince(app.getMotherstateprovince());
//					m.setMothercity(app.getMothercity());
//					m.setMotherbarangay(app.getMotherbarangay());
//					m.setMothersubdivision(app.getMothersubdivision());
//					m.setMotherstreetnoname(app.getMotherstreetnoname());
//					m.setMotherpostalcode(app.getMotherpostalcode());
//					m.setMotherfulladdress(app.getMotherfulladdress());
//				}
//				if (app.getIssameasapplicantfather()) {
//					m.setFatherstreetnoname(app.getStreetnoname1());
//					m.setFathersubdivision(app.getSubdivision1());
//					m.setFatherbarangay(app.getBarangay1());
//					m.setFatherstateprovince(app.getStateprovince1());
//					m.setFathercity(app.getCity1());
//					m.setFatherregion(app.getRegion1());
//					m.setFathercountry(app.getCountry1());
//					m.setFatherpostalcode(app.getPostalcode1());
//					m.setFatherfulladdress(fullAddress(app.getStreetnoname1(), app.getSubdivision1(),
//							app.getBarangay1(), app.getCity1(), app.getStateprovince1(), app.getCountry1(),
//							app.getRegion1(), app.getPostalcode1()));
//				} else {
//					m.setFathercountry(app.getFathercountry());
//					m.setFatherregion(app.getFatherregion());
//					m.setFatherstateprovince(app.getFatherstateprovince());
//					m.setFathercity(app.getFathercity());
//					m.setFatherbarangay(app.getFatherbarangay());
//					m.setFathersubdivision(app.getFathersubdivision());
//					m.setFatherstreetnoname(app.getFatherstreetnoname());
//					m.setFatherpostalcode(app.getFatherpostalcode());
//					m.setFatherfulladdress(app.getFatherfulladdress());
//				}
				m.setFulladdress1(fullAddress(m.getStreetnoname1(), m.getSubdivision1(), m.getBarangay1(), m.getCity1(),
						m.getStateprovince1(), m.getCountry1(), m.getRegion1(), m.getPostalcode1()));
				m.setFulladdress2(fullAddress(m.getStreetnoname2(), m.getSubdivision2(), m.getBarangay2(), m.getCity2(),
						m.getStateprovince2(), m.getCountry2(), m.getRegion2(), m.getPostalcode2()));
				m.setSpousefulladdress(fullAddress(m.getSpousestreetnoname(), m.getSpousesubdivision(),
						m.getSpousebarangay(), m.getSpousecity(), m.getSpousestateprovince(), m.getSpousecountry(),
						m.getSpouseregion(), m.getSpousepostalcode()));
				m.setMotherfulladdress(fullAddress(m.getMotherstreetnoname(), m.getMothersubdivision(),
						m.getMotherbarangay(), m.getMothercity(), m.getMotherstateprovince(), m.getMothercountry(),
						m.getMotherregion(), m.getMotherpostalcode()));
				m.setFatherfulladdress(fullAddress(m.getFatherstreetnoname(), m.getFathersubdivision(),
						m.getFatherbarangay(), m.getFathercity(), m.getFatherstateprovince(), m.getFathercountry(),
						m.getFatherregion(), m.getFatherpostalcode()));
				m.setEncodeddate(new Date());
				if (dbService.saveOrUpdate(m)) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_SAVE_AS_DRAFT_ENCODING),
							"User " + username + " Saved " + m.getMembershipappid()
									+ "'s Membership Application as draft.",
							username, new Date(),
							AuditLogEvents.getEventModule(AuditLogEvents.M_SAVE_AS_DRAFT_ENCODING));
					return m.getMembershipappid();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public Tbmembershipapp getMembershipapp(String memappid, Boolean audit) {
		try {
			param.put("memappid", memappid);
			Tbmembershipapp m = (Tbmembershipapp) dbService
					.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:memappid", param);
//			Tbmembershipapp m = (Tbmembershipapp) dbService.execSQLQueryTransformer(
//					"EXEC getMembershipApplication @membershipapp=:memappid ", param, Tbmembershipapp.class, 0);
			if (m != null) {
				m.setCountry1(m.getCountry1() == null || m.getCountry1().equals("") ? "PH" : m.getCountry1());
				m.setCountry2(m.getCountry2() == null || m.getCountry2().equals("") ? "PH" : m.getCountry2());
				m.setSpousecountry(
						m.getSpousecountry() == null || m.getSpousecountry().equals("") ? "PH" : m.getSpousecountry());
				m.setMothercountry(
						m.getMothercountry() == null || m.getMothercountry().equals("") ? "PH" : m.getMothercountry());
				m.setFathercountry(
						m.getMothercountry() == null || m.getMothercountry().equals("") ? "PH" : m.getFathercountry());
				if (audit != null && audit) {
					int auditId = 0;
					if (m.getMembershipappstatus().equals("2")) {
						// Application Review
						auditId = AuditLogEvents.M_VIEW_APPLICATION_REVIEW;
					}
					if (m.getMembershipappstatus().equals("3")) {
						// Initial Approval
						auditId = AuditLogEvents.M_VIEW_APPLICATION_APPROVAL;
					}
					if (m.getMembershipappstatus().equals("5")) {
						// Recommendation
						auditId = AuditLogEvents.M_VIEW_APPLICATION_RECOMMENDATION;
					}
					if (auditId > 0) {
						AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(auditId),
								"User " + username + " Viewed " + m.getMembershipappid()
										+ "'s Membership Application Profile.",
								username, new Date(), AuditLogEvents.getEventModule(auditId));
					}
				}
				return m;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Tbappdosri createDOSRI(String membershipappid) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("appid", membershipappid);
		Tbmembershipapp app = (Tbmembershipapp) dbService
				.executeUniqueHQLQuery("FROm Tbmembershipapp WHERE membershipappid=:appid", param);
		Tbappdosri newdosri = new Tbappdosri();
		String fullname;
		if (app.getMiddlename() == null) {
			fullname = app.getFirstname() + " " + app.getLastname();
			newdosri.setFullname(fullname);
		}
		if (app.getMiddlename() != null) {
			fullname = app.getFirstname() + " " + app.getMiddlename() + " " + app.getLastname();
			newdosri.setFullname(fullname);
		}
		newdosri.setMembershipappid(membershipappid);
		newdosri.setDosristatus("Non-TOSRI");
		if (dbService.save(newdosri)) {
			return newdosri;
		}
		return null;
	}

	/**
	 * Constructs full address.
	 */
	public static String fullAddress(String streetNumber, String subdivision, String barangay, String city,
			String province, String country, String region, String postalCode) {
		DBService dbService = new DBServiceImpl();
		StringBuilder b = new StringBuilder();
		if (streetNumber != null && !streetNumber.equals("")) {
			b.append(streetNumber + " ");
		}
		if (subdivision != null && !subdivision.equals("")) {
			b.append(subdivision + ", ");
		}
		if (barangay != null && !barangay.equals("")) {
			b.append(barangay + ", ");
		}
		if (city != null && !city.equals("")) {
			b.append(city + ", ");
		}
		if (province != null && !province.equals("")) {
			b.append(province + ", ");
		}
		if (country != null && !country.equals("")) {
			country = (String) dbService
					.executeUniqueSQLQuery("SELECT DISTINCT country FROM Tbcountry WHERE code='" + country + "'", null);
			b.append(country + " ");
		}
		if (region != null && !region.equals("")) {
			b.append(region + ", ");
		}
		if (postalCode != null && !postalCode.equals("")) {
			b.append(postalCode);
		}
		return b.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappemployment> listEmployment(String memappid) {
		try {
			param.put("memid", memappid);
			List<Tbappemployment> e = (List<Tbappemployment>) dbService
					.executeListHQLQuery("FROM Tbappemployment WHERE membershipappid=:memid", param);
			if (e != null)
				return e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappbusiness> listBusiness(String memappid) {
		try {
			param.put("memid", memappid);
			List<Tbappbusiness> b = (List<Tbappbusiness>) dbService
					.executeListHQLQuery("FROM Tbappbusiness WHERE membershipappid=:memid", param);
			if (b != null)
				return b;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappdependents> listDependents(String memappid) {
		try {
			param.put("memid", memappid);
			List<Tbappdependents> d = (List<Tbappdependents>) dbService
					.executeListHQLQuery("FROM Tbappdependents WHERE membershipappid=:memid", param);
			if (d != null)
				return d;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappfinancialinfo> listFinancialInfo(String memappid, String fintype) {
		try {
			param.put("memid", memappid);
			param.put("type", fintype);
			List<Tbappfinancialinfo> f = (List<Tbappfinancialinfo>) dbService.executeListHQLQuery(
					"FROM Tbappfinancialinfo WHERE membershipappid=:memid AND financialtype=:type", param);
			if (f != null)
				return f;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Tbappfinancialinfo>();
	}

	@Override
	public List<Tbappbeneficiary> listBeneficiary(String memappid) {
		try {
			param.put("memid", memappid);
			@SuppressWarnings("unchecked")
			List<Tbappbeneficiary> d = (List<Tbappbeneficiary>) dbService
					.execSQLQueryTransformer("SELECT membershipid, firstname, middlename, lastname, age, gender, "
							+ "fulladdress, relationship, contactno, id, ismember, streetnoname, dateofbirth, "
							+ "subdivision, barangay, stateprovince, city, region, country, postalcode, beneficiarymemberid "
							+ "FROM Tbappbeneficiary WHERE membershipappid=:memid", param, Tbappbeneficiary.class, 1);
			if (d != null)
				return d;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Tbapppersonalreference> listPersonalReference(String memappid) {
		try {
			param.put("id", memappid);
			@SuppressWarnings("unchecked")
			List<Tbapppersonalreference> p = (List<Tbapppersonalreference>) dbService
					.executeListHQLQuery("FROM Tbapppersonalreference WHERE membershipappid=:id", param);
			if (p != null)
				return p;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tbappemployment getEmployment(Integer id) {
		try {
			param.put("id", id);
			Tbappemployment e = (Tbappemployment) dbService.executeUniqueHQLQuery("FROM Tbappemployment WHERE id=:id",
					param);
			if (e != null)
				return e;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Tbappbeneficiary getBeneficiary(Integer id) {
		try {
			param.put("id", id);
			Tbappbeneficiary bn = (Tbappbeneficiary) dbService
					.executeUniqueHQLQuery("FROM Tbappbeneficiary WHERE id=:id", param);
			if (bn != null)
				return bn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tbappbusiness getBusiness(Integer id) {
		try {
			param.put("id", id);
			Tbappbusiness b = (Tbappbusiness) dbService.executeUniqueHQLQuery("FROM Tbappbusiness WHERE id=:id", param);
			if (b != null)
				return b;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tbappdependents getDependents(Integer id) {
		try {
			param.put("id", id);
			Tbappdependents d = (Tbappdependents) dbService.executeUniqueHQLQuery("FROM Tbappdependents WHERE id=:id",
					param);
			if (d != null)
				return d;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tbappfinancialinfo getFinancialInfo(Integer id) {
		try {
			param.put("id", id);
			Tbappfinancialinfo f = (Tbappfinancialinfo) dbService
					.executeUniqueHQLQuery("FROM Tbappfinancialinfo WHERE id=:id", param);
			if (f != null)
				return f;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tbapppersonalreference getPersonalreference(Integer id) {
		// DANI JUNE
		try {
			param.put("id", id);
			Tbapppersonalreference pr = (Tbapppersonalreference) dbService
					.executeUniqueHQLQuery("FROM Tbapppersonalreference WHERE id=:id", param);
			if (pr != null)
				return pr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String saveOrUpdateBeneficiary(Tbappbeneficiary beneficiary) {
		try {
			String p = "";
			if (beneficiary.getId() != null) {
				p = "u";
			} else {
				p = "s";
			}
			if (dbService.saveOrUpdate(beneficiary)) {
				if (p.equals("u")) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_UPDATE_BENEFICIARY),
							"User " + username + " Updated " + beneficiary.getMembershipappid()
									+ "'s Beneficiary Details.",
							username, new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_UPDATE_BENEFICIARY));
				}
				if (p.equals("s")) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_ADD_BENEFICIARY),
							"User " + username + " Added " + beneficiary.getMembershipappid() + "'s Beneficiary.",
							username, new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_ADD_BENEFICIARY));
				}
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String saveOrUpdateReference(Tbapppersonalreference personalreference) {
		try {
			String p = "";
			if (personalreference.getId() != null) {
				p = "u";
			} else {
				p = "s";
			}
			if (dbService.saveOrUpdate(personalreference)) {
				if (p.equals("u")) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_UPDATE_PERSONAL_REFERENCE),
							"User " + username + " Updated " + personalreference.getMembershipappid()
									+ "'s Personal Reference.",
							username, new Date(),
							AuditLogEvents.getEventModule(AuditLogEvents.M_UPDATE_PERSONAL_REFERENCE));
				}
				if (p.equals("s")) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_ADD_PERSONAL_REFERENCE),
							"User " + username + " Added " + personalreference.getMembershipappid()
									+ "'s Personal Reference.",
							username, new Date(),
							AuditLogEvents.getEventModule(AuditLogEvents.M_ADD_PERSONAL_REFERENCE));
				}
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String saveOrUpdateDOSRIStatus(Tbappdosri dosrichecking) {
		try {
			if (dosrichecking.getQ1() || dosrichecking.getQ2() || dosrichecking.getQ3() || dosrichecking.getQ4()
					|| dosrichecking.getQ5()) {
				// System.out.println("DOSRI");
				dosrichecking.setDosristatus("TOSRI");
				if (dbService.saveOrUpdate(dosrichecking)) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_CHANGE_DOSRI_STATUS),
							"User " + username + " Changed " + dosrichecking.getMembershipappid() + "'s DOSRI Status.",
							username, new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_CHANGE_DOSRI_STATUS));
					return "success";
				}
			} else {
				// System.out.println("NON-DOSRI");
				dosrichecking.setDosristatus("Non-TOSRI");
				if (dbService.saveOrUpdate(dosrichecking)) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_CHANGE_DOSRI_STATUS),
							"User " + username + " Changed " + dosrichecking.getMembershipappid() + "'s DOSRI Status.",
							username, new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_CHANGE_DOSRI_STATUS));
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public String saveMembership(Tbmembershipapp appid) {// DANIEL
		// TODO Auto-generated method stub
		AccountService acctSrvc = new AccountServiceImpl();
		UtilService utilSrvc = new UtilServiceImpl();
		try {
			if (appid != null) {
				if (appid != null) {
					param.put("encoder", appid.getEncodedby());
					param.put("coopcode", appid.getCoopcode());
					String membershipid = ApplicationNoGenerator.generateID("M", appid.getCoopcode());
					param.put("membershipappid", appid.getMembershipappid());
					param.put("membershipid", membershipid);
					Tbmember mem = m.saveMember(appid, membershipid);
					mem.setMembershipstatus("0");
					if (dbService.save(mem)) {
						System.out.println(mem.getMembershipstatus() + " " + mem.getMembershipid());
						sendEmail.sendEmailNotification(appid, membershipid, appid.getCoopcode());
						List<ReturnValues> member = (List<ReturnValues>) dbService.execSQLQueryTransformer(
								"EXEC SaveMember @membershipid=:membershipid", param, ReturnValues.class, 1);
						List<Tbprodmatrix> prdmtrxList = new ArrayList<Tbprodmatrix>();
						Tbpledge pledge = new Tbpledge();
						Tbprodmatrix prdmtrxTmp = new Tbprodmatrix();
						Tbusermember umember = new Tbusermember();
						prdmtrxTmp = acctSrvc.getProductDetail("50", "51");// ced 07.11.2019
						pledge.setMembershipappid(mem.getMembershipappid());
						pledge.setMembershipid(mem.getMembershipid());
						pledge.setNoofshare(mem.getNoofshares());
						prdmtrxList.add(prdmtrxTmp);
						prdmtrxTmp = acctSrvc.getProductDetail("20", "21");// ced 07.11.2019
						prdmtrxList.add(prdmtrxTmp);
						for (Tbprodmatrix prdmtrx : prdmtrxList) {
							Tbdeposit dep = new Tbdeposit();
							Tbdepositcif cif = new Tbdepositcif();
							dep.setPlacementAmt(BigDecimal.ZERO);
							dep.setAccountBalance(BigDecimal.ZERO);
							dep.setAddressdispo(mem.getFulladdress1());
							dep.setAccountStatus(1);
							dep.setInstcode(mem.getCoopcode());
							// dep.setCampaign();
							dep.setPosttx(prdmtrx.getPosttx());
							// dep.setSoadispo();
							// dep.setDeliverydispo();
							dep.setSubProductCode(prdmtrx.getProdcode());
							dep.setPlaceholdAmt(BigDecimal.ZERO);
							dep.setAlertflag(0);
							dep.setFloatAmount(BigDecimal.ZERO);
							// dep.setSolicitingofficer();
							dep.setOwnershipType(0);
							dep.setBookDate(utilSrvc.getBusinessdt());
							dep.setJointAcctType("0");
							dep.setProductCode(prdmtrx.getProdgroup());
							dep.setAccountName(mem.getMembername());
							// dep.setReferralofficer();
							dep.setSlaidNo("");
							dep.setUnit(UserUtil.getUserByUsername(secservice.getUserName()).getBranchcode());
							cif.setCifname(mem.getMembername());
							cif.setCifno(mem.getMembershipid());
							List<Tbdepositcif> ciflist = new ArrayList<Tbdepositcif>();
							ciflist.add(cif);
							AccountGenericForm form = acctSrvc.createAccount(dep, ciflist);
							if (prdmtrx.getProdcode().equals("46")) {
								pledge.setCapconacctno(form.getValue());
								pledge.setCapconpledgeamt(appid.getCapconpledgeamt());
								pledge.setCapconpycycle(appid.getCapconpycycle());
								pledge.setCapcontermno(appid.getCapcontermno());
								pledge.setCapcontermperiod(appid.getCapcontermperiod());
								pledge.setTotalcapcon(appid.getTotalcapcon());
								pledge.setShareparvalue(appid.getShareparvalue());
							} else {
								pledge.setSavingsacctno(form.getValue());
								pledge.setSavingspledgeamt(appid.getSavingspledgeamt());
								pledge.setSavingspycycle(appid.getSavingspycycle());
							}
							dbService.saveOrUpdate(pledge);
						}
						// Ced 4-13-2019
						umember.setBranchcode(appid.getBranch());
						umember.setCompanycode(appid.getCompanycode());
						umember.setCoopcode(appid.getCoopcode());
						umember.setCreatedby(secservice.getUserName());
						umember.setDatecreated(new Date());
						umember.setDatetimelockedout(null);
						umember.setDateupdated(null);
						umember.setEmailadd(appid.getEmailaddress());
						umember.setFirstname(appid.getFirstname());
						umember.setFullname(
								appid.getFirstname() + " " + appid.getMiddlename() + " " + appid.getLastname());
						umember.setGroupcode(null);
						umember.setInvalidattempip(null);
						umember.setInvalidattemptscount(0);
						umember.setIsactive(true);
						umember.setIsactivedirectorymember(false);
						umember.setIschangepwrequired(true);
						umember.setIsdisabled(false);
						umember.setIslocked(false);
						umember.setIsloggedon(false);
						umember.setIspwneverexpire(true);
						umember.setIssuspended(false);
						umember.setLastIp(null);
						umember.setLastlogondate(null);
						umember.setLastlogoutdate(null);
						umember.setLastname(appid.getLastname());
						umember.setLastpasswordchange(null);
						umember.setLastsession(null);
						umember.setMiddlename(appid.getMiddlename());
						umember.setMemberid(membershipid);
						umember.setPassword(UserUtil.sha1("P@ssword03"));
						umember.setPosition("");
						umember.setPwexpirydate(null);
						umember.setTeamcode(null);
						umember.setTerminatedsessionip(null);
						umember.setUnitbrid(null);
						umember.setUpdatedby(null);
						umember.setUserid(null);
						umember.setUsername(appid.getFirstname());
						umember.setValiditydatefrom(new Date());
						umember.setValiditydateto(null);
						umember.setValiditytimefrom(null);
						umember.setValiditytimeto(null);
						dbService.save(umember);
						System.out.println(dbService.executeUpdate(
								"UPDATE Tbmemberrelationship SET mainmemberid=:membershipid WHERE applicantid=:membershipappid",
								param));
						System.out.println(dbService.executeUpdate(
								"UPDATE Tbmemberrelationship SET relatedmemberid=:membershipid WHERE relatedappid=:membershipappid",
								param));
						System.out.println(dbService.executeUpdate(
								"UPDATE Tbdocchecklist SET membershipid=:membershipid WHERE membershipappid=:membershipappid",
								param));
						System.out.println(dbService.executeUpdate(
								"UPDATE Tbpepinfo SET cifno=:membershipid WHERE cifno=:membershipappid", param));
						System.out.println(dbService.executeUpdate(
								"UPDATE Tbpepq3 SET cifno=:membershipid WHERE cifno=:membershipappid", param));
						return "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tbappdosri getMemberDosri(String appid) {// CHANGES NEEDED
		// TODO Auto-generated method stub
		try {
			param.put("appid", appid);
			Tbappdosri dosri = (Tbappdosri) dbService
					.executeUniqueHQLQuery("FROM Tbappdosri WHERE membershipappid=:appid", param);
			if (dosri != null) {
				return dosri;
			} else {
				return this.createDOSRI(appid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

//	Commented by Renz because of duplicate method name
//	@Override
//	public String saveOrUpdateEmployment(Tbappemployment emp) {
//		try {
//			param.put("id", emp.getId());
//			if (emp.getId() != null) {
//				Tbappemployment e = (Tbappemployment) dbService
//						.executeUniqueHQLQuery("FROM Tbappemployment WHERE id=:id", param);
//				if (e != null) {
//					e = emp;
//					e.setId(e.getId());
//					e.setMembershipappid(e.getMembershipappid());
//					if (dbService.saveOrUpdate(e)) {
//						AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_UPDATE_EMPLOYMENT),
//								"User " + username + " Updated " + emp.getMembershipappid()
//										+ "'s Employment Information.",
//								username, new Date(),
//								AuditLogEvents.getEventModule(AuditLogEvents.M_UPDATE_EMPLOYMENT));
//						return "success";
//					}
//				}
//			} else {
//				if (dbService.save(emp))
//					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_ADD_EMPLOYMENT),
//							"User " + username + " Added " + emp.getMembershipappid() + "'s Employment Record.",
//							username, new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_ADD_EMPLOYMENT));
//				return "success";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "failed";
//	}
//
//	@SuppressWarnings("static-access")
//	@Override
//	public String saveOrUpdateBusiness(Tbappbusiness bus) {
//		try {
//			bus.setFulladdress(this.fullAddress(bus.getStreetnoname(), bus.getSubdivison(), bus.getBarangay(),
//					bus.getCity(), bus.getStateprovince(), bus.getCountry(), bus.getRegion(), bus.getPostalcode()));
//			if (bus.getId() != null) {
//				param.put("id", bus.getId());
//				Tbappbusiness b = (Tbappbusiness) dbService.executeUniqueHQLQuery("FROM Tbappbusiness WHERE id=:id",
//						param);
//				if (b != null) {
//					b = bus;
//					b.setId(b.getId());
//					b.setMembershipappid(b.getMembershipappid());
//					if (dbService.saveOrUpdate(b)) {
//						AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_UPDATE_BUSINESS),
//								"User " + username + " Updated " + b.getMembershipappid() + "Business Information.",
//								username, new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_UPDATE_BUSINESS));
//						return "success";
//					}
//				}
//			} else {
//				if (dbService.save(bus)) {
//					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_ADD_BUSINESS),
//							"User " + username + " Added " + bus.getMembershipappid() + "'s Business.", username,
//							new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_ADD_BUSINESS));
//					return "success";
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "failed";
//	}

	@Override
	public String saveOrUpdateDependents(Tbappdependents dependents) {
		try {
			String p = "";
			if (dependents.getId() != null) {
				p = "update";
			} else {
				p = "save";
			}
			if (dbService.saveOrUpdate(dependents)) {
				if (p.equals("save")) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_ADD_DEPENDENT),
							"User " + username + " Added " + dependents.getMembershipappid() + "'s Dependent.",
							username, new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_ADD_DEPENDENT));
				}
				if (p.equals("update")) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_UPDATE_DEPENDENT),
							"User " + username + " Updated " + dependents.getMembershipappid()
									+ "'s Dependent Details.",
							username, new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_UPDATE_DEPENDENT));
				}
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String saveOrUpdateFinancial(Tbappfinancialinfo fin) {
		try {
			String p = "";
			if (fin.getId() != null) {
				p = "u";
			} else {
				p = "s";
			}
			if (dbService.saveOrUpdate(fin)) {
				if (p.equals("u")) {
					if (fin.getFinancialtype().equals("1")) {
						AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_UPDATE_BANK_ACCOUNT),
								"User " + username + " Updated " + fin.getMembershipappid()
										+ "'s Bank Account Information.",
								username, new Date(),
								AuditLogEvents.getEventModule(AuditLogEvents.M_UPDATE_BANK_ACCOUNT));
					}
					if (fin.getFinancialtype().equals("2")) {
						AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_UPDATE_ASSET),
								"User " + username + " Updated " + fin.getMembershipappid() + "'s Asset Details.",
								username, new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_UPDATE_ASSET));
					}
				}
				if (p.equals("s")) {
					if (fin.getFinancialtype().equals("1")) {
						AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_ADD_BANK_ACCOUNT),
								"User " + username + " Added " + fin.getMembershipappid() + "'s Bank Account Record.",
								username, new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_ADD_BANK_ACCOUNT));
					}
					if (fin.getFinancialtype().equals("2")) {
						AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_ADD_ASSET),
								"User " + username + " Added " + fin.getMembershipappid() + "'s Asset Details.",
								username, new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_ADD_ASSET));
					}
				}
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

//	Commented by Renz because of duplicate method name	
//	@Override
//	public String deleteItem(Integer employment, Integer business, Integer dependents, Integer financial,
//			Integer beneficiary, Integer personalreference, Integer creditcard, Integer lstsourceincome,
//			Integer existingloans) {
//		try {
//			if (employment != null) {
//				Tbappemployment e = getEmployment(employment);
//				if (e != null) {
//					if (dbService.delete(e))
//						AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_DELETE_EMPLOYMENT),
//								"User " + username + " Deleted " + e.getMembershipappid() + "'s Employment Record.",
//								username, new Date(),
//								AuditLogEvents.getEventModule(AuditLogEvents.M_DELETE_EMPLOYMENT));
//					return "success";
//				}
//			}
//			if (business != null) {
//				Tbappbusiness b = getBusiness(business);
//				if (b != null) {
//					if (dbService.delete(b))
//						AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_DELETE_BUSINESS),
//								"User " + username + " Deleted " + b.getMembershipappid() + "'s Business Record.",
//								username, new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_DELETE_BUSINESS));
//					return "success";
//				}
//			}
//			if (dependents != null) {
//				Tbappdependents d = getDependents(dependents);
//				if (d != null) {
//					if (dbService.delete(d))
//						AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_DELETE_DEPENDENT),
//								"User " + username + " Deleted " + d.getMembershipappid() + "'s Dependent.", username,
//								new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_DELETE_DEPENDENT));
//					return "success";
//				}
//			}
//			if (financial != null) {
//				Tbappfinancialinfo f = getFinancialInfo(financial);
//				if (f != null) {
//					if (dbService.delete(f)) {
//						if (f.getFinancialtype() != null) {
//							if (f.getFinancialtype().equals("1")) {
//								AuditLog.addAuditLog(
//										AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_DELETE_BANK_ACCOUNT),
//										"User " + username + " Deleted " + f.getMembershipappid()
//												+ "'s Bank Account Record.",
//										username, new Date(),
//										AuditLogEvents.getEventModule(AuditLogEvents.M_DELETE_BANK_ACCOUNT));
//							}
//							if (f.getFinancialtype().equals("2")) {
//								AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_DELETE_ASSET),
//										"User " + username + " Deleted " + f.getMembershipappid() + "'s Asset Details.",
//										username, new Date(),
//										AuditLogEvents.getEventModule(AuditLogEvents.M_DELETE_ASSET));
//							}
//						}
//						return "success";
//					}
//				}
//			}
//			if (beneficiary != null) {
//				Tbappbeneficiary bnf = getBeneficiary(beneficiary);
//				if (bnf != null) {
//					if (dbService.delete(bnf))
//						AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_DELETE_BENEFICIARY),
//								"User " + username + " Deleted" + bnf.getMembershipappid() + " Beneficiary.", username,
//								new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_DELETE_BENEFICIARY));
//					return "success";
//				}
//			}
//			if (personalreference != null) {
//				Tbapppersonalreference pref = getPersonalreference(personalreference);
//				if (pref != null) {
//					if (dbService.delete(pref))
//						AuditLog.addAuditLog(
//								AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_DELETE_PERSONAL_REFERENCE),
//								"User " + username + " Deleted " + pref.getMembershipappid() + "'s Personal Reference.",
//								username, new Date(),
//								AuditLogEvents.getEventModule(AuditLogEvents.M_DELETE_PERSONAL_REFERENCE));
//					return "success";
//				}
//			}
//			if (creditcard != null) {
//				Tbappcreditcardinfo crdt = getAppCreditCard(creditcard);
//				if (crdt != null) {
//					if (dbService.delete(crdt))
//						AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_DELETE_CREDIT_CARD_INFO),
//								"User " + username + " Deleted " + crdt.getMembershipappid()
//										+ "'s Credit Card Information.",
//								username, new Date(),
//								AuditLogEvents.getEventModule(AuditLogEvents.M_DELETE_CREDIT_CARD_INFO));
//					return "success";
//				}
//			}
//			if (lstsourceincome != null) {
//				Tblstsourceincome src = getSourceIncome(lstsourceincome);
//				if (src != null) {
//					if (dbService.delete(src)) {
//						AuditLog.addAuditLog(
//								AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("REMOVE INCOME AND EXPENSE",
//										AuditLogEvents.LOAN_APPLICATION_ENCODING)),
//								"User " + username + " Removes Income and Expense details.", username, new Date(),
//								AuditLogEvents.LOAN_APPLICATION_ENCODING);
//						return "success";
//					}
//				}
//			}
//			if (existingloans != null) {
//				Tblstexistingloansother existing = getOtherExistingLoan(existingloans);
//				if (existing != null) {
//					if (dbService.delete(existing)) {
//						return "success";
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "failed";
//	}

	@Override
	public String saveApplicationHeaderdetails(String appid, String branch, String source, String company, String group,
			String memberclass, String ao, String agent) {
		try {
			if (branch != null && source != null && appid != null) {
				param.put("memappid", appid);
				Tbmembershipapp app = (Tbmembershipapp) dbService
						.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:memappid", param);
				if (app != null) {
//					app.setOriginatingbranch(branch);
					app.setBranch(branch);
					app.setApplicantsource(source);
					app.setCompanycode(company);
					app.setGroupcode(group);
					app.setMembershipclass(memberclass);
					app.setAccountofficer(ao);
					app.setAgentcode(agent);
					if (dbService.saveOrUpdate(app))
						return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public Tblstapp getLstapp(String appno) {
		DBService dbServiceLOS = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tblstapp details = new Tblstapp();
		Tbcodetable codetable = new Tbcodetable();
		Tbworkflowprocess work = new Tbworkflowprocess();
		try {
			if (appno != null) {
				param.put("no", appno);
				System.out.println("sdasdas afasdsa " + appno);
				details = (Tblstapp) dbServiceLOS.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:no", param);
				// modified by ced 12042018
				System.out.println("sdasdas" + String.valueOf(details.getApplicationstatus()));
				if (details.getLoanproduct() != null) {
					param.put("productcode", details.getLoanproduct());
					Tbloanproduct descloan = (Tbloanproduct) dbServiceLOS
							.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode=:productcode", param);
					if (descloan != null) {
						details.setLoanproduct(descloan.getProductname());
					}
				}
				// if (details.getLoanpurpose() != null) {
				// param.put("loanpurp", details.getLoanpurpose());
				// descloanpurp = (Tbcodetable)
				// dbServiceLOS.executeUniqueHQLQuery(
				// "FROM Tbcodetable WHERE codevalue=:loanpurp and codename =
				// 'LOANPURPOSE'", param);
				// if (descloanpurp != null) {
				// details.setLoanpurpose(descloanpurp.getDesc1());
				// }
				// }
				// param.put("membershipstatus", details.getMembershipstatus());
				/*
				 * codetable = (Tbcodetable)dbServiceLOS.executeUniqueHQLQuery(
				 * "FROM Tbcodetable a WHERE a.id.codename = 'MEMBERSHIPAPPSTATUS' and a.id.codevalue =:membershipstatus"
				 * , param); param.put("company",
				 * details.getCompanycode()==null?"":details.getCompanycode()); company =
				 * (Tbcompany)dbServiceLOS. executeUniqueHQLQuery(
				 * "FROM Tbcompany WHERE companycode=:company", param); param.put("branchcode",
				 * details.getBranchcode()==null?"":details.getBranchcode()== null); branch =
				 * (Tbbranch)dbServiceLOS. executeUniqueHQLQuery(
				 * "FROM Tbbranch WHERE branchcode=:branchcode", param);
				 */
//				if (codetable != null) {
//					details.setMembershipstatus(codetable.getDesc1());
//				}
				param.put("applicationstat", details.getApplicationstatus());
				work = (Tbworkflowprocess) dbServiceLOS.executeUniqueHQLQuery(
						"FROM Tbworkflowprocess WHERE workflowid = '3' AND sequenceno =:applicationstat ", param);
				details.setCiforiginatingteam(work.getProcessname());
				// commented out by kyle
				// details.setCompanycode(company.getId().getCompanyname());
				// commented out by kyle
				// details.setBranchcode(branch.getId().getBranchname());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return details;

	}

	@Override
	public Tbmemberemployment getComakerInfo(String memid) {
		Tbmemberemployment member = new Tbmemberemployment();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memid", memid);
		try {
			member = (Tbmemberemployment) dbsrvc
					.executeUniqueHQLQuery("FROM Tbmemberemployment WHERE membershipid=:memid", params);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return member;
	}

	@Override
	public String addComaker(String appno, String memid) {

		String flag = "";
		Tblstcomakers comaker = new Tblstcomakers();
		TblstcomakersId comakerid = new TblstcomakersId();
		Tbmember member = new Tbmember();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("memid", memid);

		try {

			member = (Tbmember) dbsrvc.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:memid", param);
			if (member != null) {
				comakerid.setAppno(appno);
				comakerid.setMembershipid(member.getMembershipid());
				comaker.setId(comakerid);
				comaker.setDateadded(new Date());
				comaker.setCifno(member.getMembershipid());
				comaker.setParticipationcode("CMK");
				comaker.setCustomername(member.getLastname().trim().concat(
						", ".concat(member.getFirstname().trim().concat(" ".concat(member.getMiddlename().trim())))));
				comaker.setMonthlyincome(member.getSalary());
				comaker.setBusinessincome(member.getBusinessincome());
				comaker.setOtherincome(member.getOtherincome());
				BigDecimal salary = member.getSalary() == null ? BigDecimal.ZERO : member.getSalary();
				BigDecimal businessincome = member.getBusinessincome() == null ? BigDecimal.ZERO
						: member.getBusinessincome();
				BigDecimal otherincome = member.getOtherincome() == null ? BigDecimal.ZERO : member.getOtherincome();
				BigDecimal total = (salary.add(businessincome)).add(otherincome);
				comaker.setTotalincome(total);
			}
			if (dbsrvc.save(comaker)) {
				AuditLog.addAuditLog(
						AuditLogEvents.getAuditLogEvents(
								AuditLogEvents.getEventID("ADD COMAKER", AuditLogEvents.LOAN_APPLICATION_ENCODING)),
						"User " + username + " Added " + comaker.getId().getAppno() + "'s Co-maker(s).", username,
						new Date(), AuditLogEvents.LOAN_APPLICATION_ENCODING);
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * incomecomaker.getId().setAppno(srcincome.getId().getAppno());
		 * incomecomaker.setMonthlyincome(srcincome.getMonthlyincome());
		 * incomecomaker.setOtherincome(srcincome.getOtherincome());
		 * incomecomaker.setTotalincome(srcincome.getTotalincome());
		 * incomecomaker.setLessrental(srcincome.getLessrental());
		 * incomecomaker.setLessutilities(srcincome.getLessutilities());
		 * incomecomaker.setLessfood(srcincome.getLessfood());
		 * incomecomaker.setLessmedical(srcincome.getLessmedical());
		 * incomecomaker.setLessotherexpense(srcincome.getLessotherexpense());
		 * incomecomaker.setNetincome(srcincome.getNetincome());
		 */
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tblstexistingloansother> getExistingLoansOthers(String memid) {
		List<Tblstexistingloansother> other = new ArrayList<Tblstexistingloansother>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("memid", memid);
		try {
			other = (List<Tblstexistingloansother>) dbService
					.executeListHQLQuery("FROM Tblstexistingloansother WHERE appno=:memid ", param);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return other;
	}

	@Override
	public String addCreditCardInfo(Tblstcreditcardinfo credcard) {
		String flag = "";
		Tblstcreditcardinfo credit = new Tblstcreditcardinfo();
		// Map<String, Object> param = new HashMap<String, Object>();
		try {
			credit.setAppno(credcard.getAppno());
			credit.setBank(credcard.getBank());
			credit.setCardtype(credcard.getCardtype());
			credit.setCreditlimit(credcard.getCreditlimit());
			credit.setDateexpiry(credcard.getDateexpiry());
			credit.setLimitbalance(credcard.getLimitbalance());
			if (dbService.save(credcard)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappcreditcardinfo> getCreditCardInfo(String membershipappid) {
		List<Tbappcreditcardinfo> credit = new ArrayList<Tbappcreditcardinfo>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("membershipappid", membershipappid);
		try {
			credit = (List<Tbappcreditcardinfo>) dbService
					.executeListHQLQuery("FROM Tbappcreditcardinfo WHERE membershipappid=:membershipappid", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return credit;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tblstbankaccounts> getDepositWithOtherBanks(String membershipID) {

		List<Tblstbankaccounts> accounts = new ArrayList<Tblstbankaccounts>();
		param.put("membershipID", membershipID);

		try {
			accounts = (List<Tblstbankaccounts>) dbService
					.executeListHQLQuery("FROM Tblstbankaccounts where appno=:membershipID", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accounts;
	}

	@Override
	public String addDepositInfo(Tblstbankaccounts accounts) {
		String flag = "";
		Tblstbankaccounts acct = new Tblstbankaccounts();

		try {
			acct.setAppno(accounts.getAppno());
			acct.setMembershipid(accounts.getMembershipid());
			acct.setBankaccttype(accounts.getBankaccttype());
			acct.setBank(accounts.getBank());
			acct.setAccountstatus(accounts.getAccountstatus());
			acct.setOutstandingbal(accounts.getOutstandingbal());
			if (dbService.save(acct)) {
				AuditLog.addAuditLog(
						AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID(
								"ADD ENTRY OF DEPOSITS WITH OTHER BANKS", AuditLogEvents.LOAN_APPLICATION_ENCODING)),
						"User " + username + " Added " + acct.getAppno() + "'s Deposits Accounts w/ Other Banks.",
						username, new Date(), AuditLogEvents.LOAN_APPLICATION_ENCODING);
				flag = "success";
//				System.out.println(flag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public PersonalDetails getAndAddMemberAs(String relationship, String membershipid, String applicantid) {
		// TODO Auto-generated method stub
		PersonalDetails d = new PersonalDetails();
		try {
			param.put("membershipid", membershipid);
			Tbmember m = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:membershipid",
					param);
			if (m != null) {
				d.setFirstname(m.getFirstname());
				d.setLastname(m.getLastname());
				d.setMiddlename(m.getMiddlename());
				if (m.getMiddlename() == null) {
					d.setMiddlename(" ");
				}
				d.setFullname(m.getLastname() + " " + m.getFirstname() + " " + m.getMiddlename());
				d.setCountry(m.getCountry1());
				d.setRegion(m.getRegion1());
				d.setStateprovince(m.getStateprovince1());
				d.setCity(m.getCity1());
				d.setBarangay(m.getBarangay1());
				d.setSubdivision(m.getSubdivision1());
				d.setStreetnoname(m.getStreetnoname1());
				d.setPostalcode(m.getPostalcode1());
				d.setContactno(m.getPhoneno());
				d.setAreacodephone(m.getMobilephoneareacode());
				d.setPhoneno(m.getMobilephoneno());
				d.setGender(m.getGender());
				d.setDateofbirth(m.getDateofbirth());
				d.setFulladdress(m.getFulladdress1());
				d.setMembershipid(m.getMembershipid());
				d.setTitle(m.getTitle());
				d.setAge(m.getAge());
				if (relationship.equalsIgnoreCase("BENEFICIARY")) {
					d.setRelationship("Beneficiary");
					d.setFlag("addingMemberAsBeneficiary");
				}
				if (relationship.equalsIgnoreCase("DEPENDENTS")) {
					d.setRelationship("Dependent");
					d.setFlag("addingMemberAsDependents");
				}
				if (relationship.equalsIgnoreCase("PERSONALREFERENCE")) {
					d.setRelationship("Reference");
					d.setFlag("addingMemberAsReference");
				}
				if (relationship.equalsIgnoreCase("MOTHER")) {
					d.setRelationship("Mother");
					d.setFlag("addingMemberAsMother");
				}
				if (relationship.equalsIgnoreCase("FATHER")) {
					d.setRelationship("Father");
					d.setFlag("addingMemberAsFather");
				}
				if (relationship.equalsIgnoreCase("SPOUSE")) {
					d.setAreacodephone("Spouse");
					d.setFlag("addingMemberAsSpouse");
				}
			} else {
				d.setFlag("errorAddingMember");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	@Override
	public String dedupeComaker(String memid) {
		String flag = "";
		Tbmember source = new Tbmember();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("memid", memid);
		try {
			source = (Tbmember) dbsrvc.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:memid", param);
			if (source != null) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String addLoanInfoExistingLoans(Tblstexistingloansother exist) {
		String flag = "";
		Tblstexistingloansother loans = new Tblstexistingloansother();
		// TblstexistingloansotherId id = new TblstexistingloansotherId();
		try {
			// id.setAppno(exist.getId().getAppno());
			loans.setAppno(exist.getAppno());
			loans.setLoantype(exist.getLoantype());
			loans.setOriginalamt(exist.getOriginalamt());
			loans.setIntrate(exist.getIntrate());
			loans.setTerm(exist.getTerm());
			loans.setDaterelease(exist.getDaterelease());
			loans.setMaturitydate(exist.getMaturitydate());
			loans.setPrincipalamt(exist.getPrincipalamt());
			loans.setPrinintrate(exist.getPrinintrate());
			loans.setOutstandingbal(exist.getOutstandingbal());
			loans.setStatus(exist.getStatus());
			loans.setBank(exist.getBank());
			loans.setDatecreated(new Date());
			loans.setCreatedby(secservice.getUserName());
			if (dbService.save(loans)) {
				AuditLog.addAuditLog(
						AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID(
								"ADD ENTRY OF EXISTING LOANS (OTHER)", AuditLogEvents.LOAN_APPLICATION_ENCODING)),
						"User " + username + " Added " + loans.getAppno() + "'s Existing Loans (Other).", username,
						new Date(), AuditLogEvents.LOAN_APPLICATION_ENCODING);
				flag = "success";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String doneEncoding(Tblstapp app) {
		Tblstapp lstapp = new Tblstapp();
		Map<String, Object> param = new HashMap<String, Object>();
		DBService dbsrvc = new DBServiceImpl();
		param.put("appno", app.getAppno());
		String result = "Error During the Process. ";
		try {
			lstapp = (Tblstapp) dbsrvc.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", param);
//			System.out.println(app.getAppno() + " <<<<<< DDDDDDDDDD");
			if (lstapp != null) {
				// lstapp.setIsdoneencoding(lstapp.getIsdoneencoding() ? false :
				// true);
				lstapp.setIsdoneencoding(true);
				lstapp.setApplicationdate(app.getApplicationdate());
				// lstapp.setBranchcode(app.getBranchcode());
				// lstapp.setCompanycode(app.getCompanycode());
				// lstapp.setLoanproduct(app.getLoanproduct());
				lstapp.setLoanpurpose(app.getLoanpurpose());
				if (dbsrvc.update(lstapp)) {
					AuditLog.addAuditLog(
							AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("DONE ENCODING",
									AuditLogEvents.LOAN_APPLICATION_ENCODING)),
							"User " + username + " tagged " + lstapp.getAppno() + " as \"Done Encoding\".", username,
							new Date(), AuditLogEvents.LOAN_APPLICATION_ENCODING);
					result = lstapp.getIsdoneencoding() ? "Done encoding." : "Returned for editing.";
				} else {
					result = "Encountered error during the process.";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "Encountered error during the process.";
		}
		return result;
	}

	@Override
	public String saveOrUpdateCreditCardApp(Tbappcreditcardinfo creditcard) {
		try {
			String p = "";
			if (creditcard.getId() != null) {
				p = "u";
			} else {
				p = "s";
			}
			if (dbService.saveOrUpdate(creditcard)) {
				if (p.equals("u")) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_UPDATE_CREDIT_CARD_INFO),
							"User " + username + " Updated " + creditcard.getMembershipappid()
									+ "'s Credit Card Information.",
							username, new Date(),
							AuditLogEvents.getEventModule(AuditLogEvents.M_UPDATE_CREDIT_CARD_INFO));
				}
				if (p.equals("s")) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_ADD_CREDIT_CARD_INFO),
							"User " + username + " Added " + creditcard.getMembershipappid()
									+ "'s Credit Card Information.",
							username, new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_ADD_CREDIT_CARD_INFO));
				}
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbappcreditcardinfo> getApplicantsCreditCard(String membershipappid) {
		try {
			param.put("membershipappid", membershipappid);
			List<Tbappcreditcardinfo> crdt = (List<Tbappcreditcardinfo>) dbService
					.executeListHQLQuery("FROM Tbappcreditcardinfo WHERE membershipappid=:membershipappid", param);
			if (crdt != null) {
				return crdt;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Tbappcreditcardinfo getAppCreditCard(Integer id) {
		try {
			param.put("id", id);
			Tbappcreditcardinfo cred = (Tbappcreditcardinfo) dbService
					.executeUniqueHQLQuery("FROM Tbappcreditcardinfo WHERE id=:id", param);
			if (cred != null)
				return cred;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbloanproduct> getListLoanProduct() {
		List<Tbloanproduct> list = new ArrayList<Tbloanproduct>();
		SecurityServiceImpl sec = new SecurityServiceImpl();
		try {
			param.put("coopcode", sec.getUserAccount(secservice.getUserName()).getUseraccount().getCoopcode());
			list = (List<Tbloanproduct>) dbService.execSQLQueryTransformer(
					"SELECT c.productcode as productcode, c.productname as productname FROM TBPRODUCTPERCOOP c left join TBLOANPRODUCT p on c.productcode=p.productcode WHERE coopcode=:coopcode and isactive=1",
					param, Tbloanproduct.class, 1);
			// list = (List<Tbloanproduct>) dbService.executeListHQLQuery("FROM
			// Tbloanproduct WHERE status='A'", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveAsDraft(String appno, String loanpurpose, Date appdate) {
		param.put("appno", appno);
		param.put("loanpurpose", loanpurpose);
		param.put("appdate", appdate);
		try {
			if ((Integer) dbService.execStoredProc(
					"UPDATE Tblstapp SET loanpurpose =:loanpurpose, applicationdate=:appdate WHERE appno=:appno", param,
					null, 2, null) > 0) {
				AuditLog.addAuditLog(
						AuditLogEvents.getAuditLogEvents(
								AuditLogEvents.getEventID("SAVE AS DRAFT", AuditLogEvents.LOAN_APPLICATION_ENCODING)),
						"User " + username + " Saved " + appno + "'s Loan Application as draft.", username, new Date(),
						AuditLogEvents.LOAN_APPLICATION_ENCODING);
				return "success";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "success";
	}

	// coop ced 12052018
	@Override
	public String saveOfficer(String appno) {
		Tblstapp lstapp = new Tblstapp();
		param.put("appno", appno);
		try {
			if (UserUtil.hasRole("ACCT_OFFICER")) {
				lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno =:appno", param);
				lstapp.setAccountofficer(secservice.getUserName());
				if (dbService.saveOrUpdate(lstapp))
					;
				return "success";
			} else {
				return "notao";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String saveSourceIncome(Tblstsourceincome source) {
		// TODO Auto-generated method stub
		try {
//			System.out.println("starting..");
			Tblstsourceincome src = getLstSourceIncome(source.getAppno(), source.getParticipationcode());
			if (src.getAppno() != null) {
				src.setLastupdated(new Date());
				src.setUpdatedby(secservice.getUserName());
				src.setLessfood(source.getLessfood());
				src.setLessmedical(source.getLessmedical());
				src.setLessotherexpense(source.getLessotherexpense());
				src.setLessrental(source.getLessrental());
				src.setLessutilities(source.getLessutilities());
				src.setNetincome(source.getNetincome());
				src.setMonthlyincome(source.getMonthlyincome());
				src.setTotalincome(source.getTotalincome());
				src.setBusinessincome(source.getBusinessincome());
				if (dbService.saveOrUpdate(src)) {
					AuditLog.addAuditLog(
							AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("EDIT INCOME AND EXPENSE",
									AuditLogEvents.LOAN_APPLICATION_ENCODING)),
							"User " + username + " Edited " + src.getAppno() + "'s Income and Expense details.",
							username, new Date(), AuditLogEvents.LOAN_APPLICATION_ENCODING);
					return "success";
				}
			} else {
				source.setCreatedby(secservice.getUserName());
				source.setDatecreated(new Date());
				if (dbService.saveOrUpdate(source)) {
					AuditLog.addAuditLog(
							AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("ADD INCOME AND EXPENSE",
									AuditLogEvents.LOAN_APPLICATION_ENCODING)),
							"User " + username + " Added " + source.getAppno() + "'s Income and Expense details.",
							username, new Date(), AuditLogEvents.LOAN_APPLICATION_ENCODING);
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public Tblstsourceincome getSourceIncome(Integer id) {
		// TODO Auto-generated method stub
		try {
			param.put("id", id);
			if (id != null) {
				Tblstsourceincome src = (Tblstsourceincome) dbService
						.executeUniqueHQLQuery("FROM Tblstsourceincome WHERE id=:id", param);
				if (src != null) {
					return src;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Tblstsourceincome();
	}

	@Override
	public Tblstsourceincome getLstSourceIncome(String appno, String participation) {
		// TODO Auto-generated method stub
		try {
			param.put("appno", appno);
			param.put("participationcode", participation);
			Tblstsourceincome src = (Tblstsourceincome) dbService.executeUniqueHQLQuery(
					"FROM Tblstsourceincome a WHERE a.participationcode=:participationcode AND a.appno=:appno", param);
			if (src != null) {
				return src;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Tblstsourceincome();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdeposit> getDeposits(String appno) {
		// TODO Auto-generated method stub
		List<Tbdeposit> list = new ArrayList<Tbdeposit>();
		try {
			param.put("appno", appno);
			Tblstapp app = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", param);
			if (app != null) {
				param.put("cifno", app.getCifno());
				List<Tbdepositcif> cif = (List<Tbdepositcif>) dbService
						.executeListHQLQuery("FROM Tbdepositcif WHERE cifno=:cifno", param);
				if (cif != null) {
					for (Tbdepositcif c : cif) {
						param.put("accountno", c.getAccountno());
						Tbdeposit d = (Tbdeposit) dbService
								.executeUniqueHQLQuery("FROM Tbdeposit WHERE accountNo=:accountno", param);
						if (d != null) {
							list.add(d);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String returnApplication(String membershipappid, String returnremarks) {
		// TODO Auto-generated method stub
		try {
			param.put("membershipappid", membershipappid);
			if (membershipappid != null) {
				Integer i = (Integer) dbService
						.executeUpdate("UPDATE Tbmembershipapp SET membershipappstatus='1', returnremarks='"
								+ returnremarks + "', reviewedby='" + UserUtil.securityService.getUserName()
								+ "' WHERE membershipappid=:membershipappid", param);
				if (i > 0 && i != null) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_RETURN_TO_ENCODING),
							"User " + username + " Returned Membership Application to Encoding.", username, new Date(),
							AuditLogEvents.getEventModule(AuditLogEvents.M_RETURN_TO_ENCODING));
					if (returnremarks != null || returnremarks != "") {
						AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_INPUT_REMARKS_REVIEW),
								"User " + username + " Inputted Remarks during Application Review.", username,
								new Date(), AuditLogEvents.getEventModule(AuditLogEvents.M_INPUT_REMARKS_REVIEW));
					}
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String addLSTCreditCard(Tblstcreditcardinfo card) {
		// TODO Auto-generated method stub
		String flag = "failed";
		Tblstcreditcardinfo lstcard = new Tblstcreditcardinfo();
		try {
			lstcard.setAppno(card.getAppno());
			lstcard.setBank(card.getBank());
			lstcard.setCardtype(card.getCardtype());
			lstcard.setCreditlimit(card.getCreditlimit());
			lstcard.setDateexpiry(card.getDateexpiry());
			lstcard.setLimitbalance(card.getLimitbalance());
			if (dbService.save(lstcard)) {
				flag = "success";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tblstcreditcardinfo> getLSTCredCardInfo(String appno) {
		// TODO Auto-generated method stub
		List<Tblstcreditcardinfo> list = new ArrayList<Tblstcreditcardinfo>();
		try {
			param.put("appno", appno);
			if (appno != null) {
				list = (List<Tblstcreditcardinfo>) dbService
						.executeListHQLQuery("FROM Tblstcreditcardinfo WHERE appno=:appno", param);
				Tblstapp app = getLstapp(appno);
				if (app != null) {
					param.put("id", app.getCifno());
					List<Tbmembercreditcardinfo> m = (List<Tbmembercreditcardinfo>) dbService
							.executeListHQLQuery("FROM Tbmembercreditcardinfo WHERE memberid=:id", param);
					if (m != null) {
						for (Tbmembercreditcardinfo c : m) {
							Tblstcreditcardinfo l = new Tblstcreditcardinfo();
							l.setBank(c.getBank());
							l.setCardtype(c.getCardtype());
							l.setCreditlimit(c.getCreditlimit());
							l.setDateexpiry(c.getDateexpiry());
							l.setLimitbalance(c.getLimitbalance());
							list.add(l);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}

	@Override
	public Tbpledge getCapconSavings(String memid) {
		// TODO Auto-generated method stub
		Tbpledge pledge = new Tbpledge();
		try {
			if (memid != null) {
				param.put("memid", memid);
				pledge = (Tbpledge) dbService.executeUniqueHQLQuery("FROM Tbpledge WHERE membershipid=:memid", param);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return pledge;
	}

	@Override
	public String updateDepositInfo(Tblstbankaccounts acct) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			param.put("id", acct.getId());
			Tblstbankaccounts bank = (Tblstbankaccounts) dbService
					.executeUniqueHQLQuery("FROM Tblstbankaccounts WHERE id=:id", param);
			if (bank != null) {
				bank.setBankaccttype(acct.getBankaccttype());
				bank.setBank(acct.getBank());
				bank.setAccountstatus(acct.getAccountstatus());
				bank.setOutstandingbal(acct.getOutstandingbal());
				bank.setUpdatedby(secservice.getUserName());
				bank.setLastupdated(new Date());
				if (dbService.saveOrUpdate(bank)) {
					flag = "success";
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteDepInfo(Integer id) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			param.put("id", id);
			if (id != null) {
				// System.out.println("id: " + id);
				Tblstbankaccounts dep = (Tblstbankaccounts) dbService
						.executeUniqueHQLQuery("FROM Tblstbankaccounts WHERE id=:id", param);
				if (dep != null) {
//					System.out.println("dep not null");
					if (dbService.delete(dep)) {
						flag = "success";
					}
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String updateExistingLoanOth(Tblstexistingloansother other) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			param.put("id", other.getId());
			Tblstexistingloansother exist = (Tblstexistingloansother) dbService
					.executeUniqueHQLQuery("FROM Tblstexistingloansother WHERE id=:id", param);
			if (exist != null) {
				other.setId(exist.getId());
				other.setUpdatedby(secservice.getUserName());
				other.setLastupdated(new Date());
				if (dbService.saveOrUpdate(other)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tblstexistingloansother getOtherExistingLoan(Integer id) {
		// TODO Auto-generated method stub
		try {
			param.put("id", id);
			Tblstexistingloansother e = (Tblstexistingloansother) dbService
					.executeUniqueHQLQuery("FROM Tblstexistingloansother WHERE id=:id", param);
			if (e != null) {
				return e;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public AccountInfoForm getAccountInfo(String appno) {
		// TODO Auto-generated method stub
		AccountInfoForm form = new AccountInfoForm();
		try {
			param.put("appno", appno);
			if (appno != null) {
				form = (AccountInfoForm) dbService.execSQLQueryTransformer(
						"SELECT a.cifname AS fullname,a.cifno AS cifno,mem.fulladdress1 AS address, '' AS mainloanfacility, '' AS subloanfacility, "
								+ " b.matdt AS linematurity,b.faceamt AS loanamount,a.pnno AS pnno, custtype.desc1 AS accounttype, concat(b.product, '-' ,prod.productname) AS loanproductavailed,"
								+ " b.seccode AS securitycollateral, code.desc1 as loanpurpose from TBLSTAPP a JOIN TBACCOUNTINFO b on a.appno = b.applno JOIN "
								+ "TBMEMBER mem on a.cifno = mem.membershipid JOIN TBLOANPRODUCT prod on prod.productcode = b.product JOIN "
								+ "TBCODETABLE code on code.codevalue = a.loanpurpose and code.codename='LOANPURPOSE' LEFT JOIN TBCODETABLE custtype ON custtype.codevalue =a.customertype "
								+ "AND custtype.codename ='CUSTOMERTYPE' WHERE a.appno=:appno",
						param, AccountInfoForm.class, 0);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbbanks> listBanks() {
		// TODO Auto-generated method stub
		List<Tbbanks> bank = new ArrayList<Tbbanks>();
		try {
			bank = (List<Tbbanks>) dbService.executeListHQLQuery("FROM Tbbanks", null);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return bank;
	}

	@Override
	public String updateAccountInfo(CollectionInstructionsForm collect, String type) {
		// TODO Auto-generated method stub
		Tbaccountinfo info = new Tbaccountinfo();
		String flag = "failed";

		try {
			param.put("appno", collect.getAppno());
			if (collect.getAppno() != null) {
				info = (Tbaccountinfo) dbService.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno=:appno", param);
				Tblstapp app = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", param);
				param.put("prodcode", app.getLoanproduct());
				Tbloanproduct prod = (Tbloanproduct) dbService
						.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode=:prodcode", param);
				if (info != null) {
					if (type.equals("collect")) {
						info.setCollectpdcflag(collect.isPdcflag());
						info.setCollectcheckflag(collect.isCheckflag());
						info.setCollectcashflag(collect.isCashflag());
						info.setCollectdebitacctflag(collect.isDebitacctflag());
						info.setPdcctr(collect.getPdcctr() == null ? 0 : collect.getPdcctr());
						info.setDebitbankbrstn(collect.getBankbrstn());
						info.setDebitbankname(collect.getBankname());
						info.setDebitbankbr(collect.getBankbr());
						info.setDebitbankaccttype(collect.getAccttype());
						info.setDebitbankacctno(collect.getAcctno());
						info.setSeccode(collect.getSeccode());

						// info.setTotalfeescharges(collect.getFeesandcharges());
						// DANIEL 03.11.19
						// defaults
						info.setBkfxrate(BigDecimal.ZERO);
						info.setFireInsurance(BigDecimal.ZERO);
						info.setInsuranceOutright(BigDecimal.ZERO);
						info.setAdjustmentAmortization(BigDecimal.ZERO);
						info.setDesiredMonthlyAmort(BigDecimal.ZERO);
						info.setMonthlyPension(BigDecimal.ZERO);
						info.setOrppd(BigDecimal.ZERO);
						info.setOtherAddtnlToAmortFee(BigDecimal.ZERO);
						info.setOtherNonFinancialCharges(BigDecimal.ZERO);
						info.setAddon(BigDecimal.ZERO);
						info.setNotfee(BigDecimal.ZERO);
						info.setTotalfeesamortized(BigDecimal.ZERO);

						info.setRprceflg("0");

						info.setDocnum(0);
						info.setAdjustedTerm(0);
						info.setNoOfInterestPaymentToAdvance(0);
						info.setAcctsts(1);
						info.setIsAmortFeeRounded(false);

						info.setTxdate(new Date());
						info.setDtbook(new Date());
						info.setTxvaldt(new Date());

						// LSTAPP - DANIEL 03.11.19
						info.setOrigoff(app.getAccountofficer());
						info.setAcctoff(app.getAccountofficer());
						info.setLoanpur(app.getLoanpurpose());
						info.setApprbr(app.getBranchcode());
						info.setLegveh(app.getCompanycode());
						info.setApplicationtype(app.getApplicationtype());
						if (app.getLoanproduct() != null) {
							info.setLoanno(ApplicationNoGenerator.generateLoanNumber(app.getLoanproduct()));
						}
						// INWORDS
						// info.setRepaytypedesc(collect.getRepaytypedesc());
						// info.setInttypedesc(collect.getInttypedesc());
						// info.setIntpaytypedesc(collect.getIntpaytypedesc());
						// info.setIntcycdesc(collect.getIntcycdesc());
						// info.setTermcycdesc(collect.getTermcycdesc());
						// info.setPrinpaycycdesc(collect.getPrinpaycycdesc());
						// info.setIntpaycycdesc(collect.getIntpaytypedesc());

						// info.setTotalfeescharges();

						// noreen's given specs
						info.setNetprcdsorig(info.getNetprcds());
						param.put("codevalue", prod.getInteresttype());
						// System.out.println((String)
						// dbService.execSQLQueryTransformer(
						// "SELECT desc1 FROM TBCODETABLE WHERE
						// codename='INTCOMPTYPE' AND codevalue=:codevalue",
						// param, String.class, 0));
//						System.out.println("prod.getInteresttype" + prod.getInteresttype());
						Tbcodetable code = (Tbcodetable) dbService.execSQLQueryTransformer(
								"SELECT desc1 FROM TBCODETABLE WHERE codename='INTCOMPTYPE' AND codevalue=:codevalue",
								param, Tbcodetable.class, 0);
						info.setIncompmethoddesc(code.getDesc1());

						if (info.getIntpytype().equals("0")) {
							info.setAir(info.getInterestdue());
							// info.setUidBal(BigDecimal.ZERO);
							// info.setOuid(info.getInterestdue());
						} else {
							info.setAir(BigDecimal.ZERO);
							// info.setUidBal(info.getInterestdue().subtract(info.getadv()));
							// info.setOuid(info.getInterestdue().subtract(info.getAdvanceinterest()));
						}
						if (dbService.saveOrUpdate(info)) {
							AuditLog.addAuditLog(
									AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("DEDUPE",
											AuditLogEvents.LOAN_APPLICATION_RELEASING_BOOKING)),
									"User " + username + " Added Collection Instructions.", username, new Date(),
									AuditLogEvents.LOAN_APPLICATION_RELEASING_BOOKING);
							flag = "collect";
						}
					} else {
//						System.out.println("collect" + collect.toString());
						info.setTotalpayabletobank(collect.getPayabletobank());
						info.setTotalfeesandcharges(collect.getFeesandcharges());
						info.setTotalfeesdeducttoloan(collect.getFeesdeduct());
						info.setTotalunpaidfees(collect.getUnpaidfees());
						info.setTotalpaidfees(collect.getPaidfees());
						info.setTotalloanoffsetamt(collect.getLoanoffset());
						if (dbService.saveOrUpdate(info)) {
							flag = "payout";
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanFeesForm> getListLoanFeesperapp(String appno) {
		// TODO Auto-generated method stub
		List<LoanFeesForm> list = new ArrayList<LoanFeesForm>();
		try {
			param.put("appno", appno);
			if (appno != null) {
				list = (List<LoanFeesForm>) dbService.execSQLQueryTransformer(
						"SELECT DISTINCT loan.loanfeecode AS loanfeecode,fees.loanfeename AS particular,loan.payableto AS payableto,loan.feeamount AS amount, "
								+ " IIF(ISNULL(loan.paidflag,0)=0,'No','Yes') AS paid, code.desc1 AS disposition, loan.orno as orno,loan.ordate as ordate "
								+ "FROM TBLOANFEESPERAPP loan LEFT JOIN TBLOANPRODUCTFEES fees "
								+ "ON loan.loanfeecode = fees.loanfeecode LEFT JOIN TBCODETABLE code ON code.codevalue=loan.collectionrule AND code.codename='FEESCOLLECTION' WHERE loan.appno=:appno",
						param, LoanFeesForm.class, 1);

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanPayoutForm> getLoanPayout(String appno) {
		// TODO Auto-generated method stub
		List<LoanPayoutForm> form = new ArrayList<LoanPayoutForm>();
		try {
			param.put("appno", appno);
			if (appno != null) {
				form = (List<LoanPayoutForm>) dbService.execSQLQueryTransformer(
						"SELECT appno, offset.pnno AS pnno,prod.productname AS loanproduct, offset.outstandingbal AS outstandingbal, "
								+ " offset.outstandingbal AS paidoffamount, code.desc1 AS acctstat  FROM TBLOANOFFSET offset LEFT JOIN "
								+ " TBLOANPRODUCT prod ON offset.prodcode = prod.productcode LEFT JOIN TBCODETABLE code ON code.codevalue=offset.acctsts AND code.codename='ACCTSTS' "
								+ "WHERE offset.applno =:appno",
						param, LoanPayoutForm.class, 1);

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}

	// >>CED 3-08-2019 added remarks
	@SuppressWarnings("unchecked")
	@Override
	public List<LoanReleaseInstForm> getLoanReleaseInst(String appno) {
		// TODO Auto-generated method stub
		List<LoanReleaseInstForm> form = new ArrayList<LoanReleaseInstForm>();
		try {
			param.put("appno", appno);
			if (appno != null) {
				form = (List<LoanReleaseInstForm>) dbService.execSQLQueryTransformer(
						"SELECT loan.id as id,loan.applno AS appno,loan.pnno AS pnno,loan.checkno AS checkno,loan.checkacctno AS checkacctno, "
								+ "loan.checkbank AS checkbank,loan.checkbrstn AS checkbrstn,loan.checkbranch AS checkbranch,loan.checkdate AS checkdate, "
								+ "loan.creditbank AS creditbank, loan.creditaccttype AS creditaccttype,loan.creditacctno AS creditacctno,loan.creditbrstn AS creditbrstn, "
								+ "loan.creditbranch AS creditbranch,loan.payeename AS payeename,loan.amount AS amount, code.desc1 AS disposition, loan.remarks as remarks, loan.glcode "
								+ "FROM TBLOANRELEASEINST loan LEFT JOIN TBCODETABLE code ON loan.disposition = code.codevalue AND code.codename='LOANDISPOSITION' "
								+ "WHERE loan.applno=:appno and status ='0'",
						param, LoanReleaseInstForm.class, 1);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}// <<CED 3-08-2019

	@Override
	public CompanyForm getCoopNameByCoopcode(String coopcode) {
		// TODO Auto-generated method stub
		CompanyForm coopname = new CompanyForm();
		try {
			param.put("coopcode", coopcode);
//			System.out.println("coopcode: " + coopcode);
			if (coopcode != null) {
				coopname = (CompanyForm) dbService.execSQLQueryTransformer(
						"SELECT coopname AS coopname FROM TBCOOPERATIVE WHERE coopcode=:coopcode", param,
						CompanyForm.class, 0);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return coopname;
	}

	// >>CED 3-08-2019 new method
	@Override
	public String saveLoanRelInstruction(Tbloanreleaseinst relInst) {
		String result = "failed";
		try {
			if (relInst.getId() == null) {
				relInst.setCreatedby(secservice.getUserName());
				relInst.setDatecreated(new Date());
			} else {
				relInst.setUpdatedby(secservice.getUserName());
				relInst.setDateupdated(new Date());
			}
			dbService.saveOrUpdate(relInst);
//			AuditLog.addAuditLog(
//					AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("ADD LOAN RELEASE INSTRUCTIONS",
//							AuditLogEvents.LOAN_APPLICATION_RELEASING_BOOKING)),
//					"User " + username + " Added Loan Release Instructions.", username, new Date(),
//					AuditLogEvents.LOAN_APPLICATION_RELEASING_BOOKING);
			result = "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	// >>CED 3-08-2019 new method
	@Override
	public String updateRelInst(LoanReleaseInstForm form) {
		String result = "failed";
		param.put("id", form.getId());
		try {
			if (dbService.executeUpdate("UPDATE Tbloanreleaseinst SET status ='2' WHERE id =:id", param) == 1) {
				AuditLog.addAuditLog(
						AuditLogEvents.getAuditLogEvents(AuditLogEvents.getEventID("DELETE LOAN RELEASE INSTRUCTION",
								AuditLogEvents.LOAN_APPLICATION_RELEASING_BOOKING)),
						"User " + username + " Deleted Loan Release Instruction.", username, new Date(),
						AuditLogEvents.LOAN_APPLICATION_RELEASING_BOOKING);
				result = "success";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}// <<CED 3-08-2019
		// >>CED 3-08-2019 new method

	@Override
	public Tbloanreleaseinst getLoanRelease(int id) {
		Tbloanreleaseinst relInst = new Tbloanreleaseinst();
		param.put("id", id);
		try {
			relInst = (Tbloanreleaseinst) dbService.executeUniqueHQLQuery("FROM Tbloanreleaseinst WHERE id = :id",
					param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return relInst;
	}// <<CED 3-08-2019

	@Override
	public String deletePDC(Integer id) {
		// TODO Auto-generated method stub
		try {
			param.put("id", id);
			Tbpdc pdc = (Tbpdc) dbService.executeUniqueHQLQuery("FROM Tbpdc WHERE id=:id", param);
			if (pdc != null) {
				if (dbService.delete(pdc))
					return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "failed";
	}

	@Override
	public RemarksForm getRemarksDetails(String appid) {
		// TODO Auto-generated method stub
		RemarksForm r = new RemarksForm();
		r.setRemarks("-");
		r.setRemarksfrom("-");
		try {
			param.put("id", appid);
			Tbmembershipapp m = getMembershipapp(appid, null);
			if (m != null) {
				if (m.getMembershipappstatus().equals("1") || m.getMembershipappstatus().equals("2")) {
					if (m.getReturnremarks() != null && m.getReviewedby() != null) {
						r.setRemarks(m.getReturnremarks());
						r.setRemarksfrom(UserUtil.getUserFullname(m.getReviewedby()));
					}
				}
				if (m.getMembershipappstatus().equals("3") || m.getMembershipappstatus().equals("4")) {
					r.setRemarks(m.getReviewremarks());
					r.setRemarksfrom(UserUtil.getUserFullname(m.getReviewedby()));
				}
				if (m.getMembershipappstatus().equals("5") || m.getMembershipappstatus().equals("6")) {
					r.setRemarks(m.getEdcomapproverremarks());
					r.setRemarksfrom(UserUtil.getUserFullname(m.getEdcomapprover()));
				}
				if (m.getMembershipappstatus().equals("7") || m.getMembershipappstatus().equals("8")
						|| m.getMembershipappstatus().equals("9")) {
					r.setRemarks(m.getBoardapproverremarks());
					r.setRemarksfrom(UserUtil.getUserFullname(m.getBoardapprover()));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return r;
	}

	@Override
	public String saveAccountSecurity(String appno, String seccode) {
		// TODO Auto-generated method stub
		try {
			param.put("appno", appno);
			if (appno != null) {
				Integer i = (Integer) dbService
						.executeUpdate("UPDATE TBACCOUNTINFO SET seccode=:seccode WHERE applno=:appno", param);
				if (i > 0) {
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "failed";
	}

	@Override
	public String saveOrUpdateAO(Tbaccountofficer d, String saveOrUpdate) {
		String flag = "failed";
		try {
			if (saveOrUpdate != null) {
				if (saveOrUpdate.equalsIgnoreCase("save")) {
					Tbaccountofficer row = new Tbaccountofficer();
					TbaccountofficerId id = new TbaccountofficerId();
					id.setAocode(d.getId().getAocode());
					id.setCompanycode(d.getId().getCompanycode());
					row.setId(id);

					row.setLastname(d.getLastname());
					row.setMiddlename(d.getMiddlename());
					row.setFirstname(d.getFirstname());
					row.setSuffix(d.getSuffix());
					row.setBankacctno(d.getBankacctno());
					row.setStreetnoname(d.getStreetnoname());
					row.setSubdivision(d.getSubdivision());
					row.setBarangay(d.getBarangay());
					row.setStateprovince(d.getStateprovince());
					row.setCity(d.getCity());
					row.setPostalcode(d.getPostalcode());
					row.setRegion(d.getRegion());
					row.setCountry(d.getCountry());

					if (dbService.save(row)) {
						flag = "success";
					}
				} else if (saveOrUpdate.equalsIgnoreCase("update")) {
					if (d.getId().getAocode() != null && d.getId().getCompanycode() != null) {
						param.put("aocode", d.getId().getAocode());
						param.put("companycode", d.getId().getCompanycode());
						Tbaccountofficer row = (Tbaccountofficer) dbService.executeUniqueHQLQueryMaxResultOne(
								"FROM Tbaccountofficer WHERE id.aocode=:aocode AND id.companycode=:companycode", param);
						if (row != null) {

							TbaccountofficerId id = new TbaccountofficerId();
							id.setAocode(d.getId().getAocode());
							id.setCompanycode(d.getId().getCompanycode());
							row.setId(id);

							row.setLastname(d.getLastname());
							row.setMiddlename(d.getMiddlename());
							row.setFirstname(d.getFirstname());
							row.setSuffix(d.getSuffix());
							row.setBankacctno(d.getBankacctno());
							row.setStreetnoname(d.getStreetnoname());
							row.setSubdivision(d.getSubdivision());
							row.setBarangay(d.getBarangay());
							row.setStateprovince(d.getStateprovince());
							row.setCity(d.getCity());
							row.setPostalcode(d.getPostalcode());
							row.setRegion(d.getRegion());
							row.setCountry(d.getCountry());

							if (dbService.saveOrUpdate(row)) {
								flag = "success";
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbaccountofficer> listAO() {
		List<Tbaccountofficer> list = new ArrayList<Tbaccountofficer>();
		try {
			list = (List<Tbaccountofficer>) dbService.executeListHQLQuery("FROM Tbaccountofficer", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmemberrelationship> listMemberRelationshipPerAppID(String appid) {
		// TODO Auto-generated method stub
		try {
			param.put("appid", appid);
			List<Tbmemberrelationship> list = (List<Tbmemberrelationship>) dbService
					.executeListHQLQuery("FROM Tbmemberrelationship WHERE applicantid=:appid", param);
			if (list.size() > 0) {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public String saveOrUpdateMemberRelationship(Tbmemberrelationship relative) {
		// TODO Auto-generated method stub
		try {
			relative.setFirstname(relative.getFirstname().toUpperCase());
			relative.setLastname(relative.getLastname().toUpperCase());
			relative.setMiddlename(relative.getMiddlename().toUpperCase());
			Tbmembershipapp app = getMembershipapp(relative.getApplicantid(), false);
			String firstname = "";
			String lastname = "";
			String middlename = "";
			if (app.getFirstname() != null && !app.getFirstname().equals("")) {
				firstname = app.getFirstname();
			}
			if (app.getLastname() != null && !app.getLastname().equals("")) {
				lastname = app.getLastname();
			}
			if (app.getMiddlename() != null && !app.getMiddlename().equals("")) {
				middlename = app.getMiddlename();
			}
			relative.setMainmembername(lastname + " " + firstname + " " + middlename);
			if (relative.getRelid() != null) {
				if (dbService.saveOrUpdate(relative))
					return "success";
			} else {
				if (dbService.save(relative))
					return "success";
			}
//			MemberRelations rel = new MemberRelations();
//			if (relative.getRelatedappid() != null && !relative.getRelatedappid().equals("")) {
//				if (relative.getRelid() != null) {
//					/* existing record */
//					if (dbService.saveOrUpdate(relative)) {
//						/* just updating */
//						return "success";
//					}
//				} else {
//					/* new record */
//					if (relative.getRelatedmemberid() != null && !relative.getRelatedmemberid().equals("")) {
//						/* if member already */
//						Tbmembershipapp app = getMembershipapp(relative.getApplicantid(), false);
//						Tbmemberrelationship nr = new Tbmemberrelationship();
//						nr.setApplicantid(relative.getRelatedappid());
//						nr.setMainmemberid(relative.getRelatedmemberid());
//						/* personal details */
//						nr.setAge(app.getAge());
//						nr.setDateofbirth(app.getDateofbirth());
//						nr.setGender(app.getGender());
//						nr.setContactno(app.getAreacodephone() + app.getPhoneno());
//						nr.setSuffix(app.getSuffix());
//
//						/* name */
//						nr.setLastname(app.getLastname());
//						nr.setFirstname(app.getFirstname());
//						nr.setMiddlename(app.getMiddlename());
//						nr.setMainmembername(app.getLastname() + " " + app.getFirstname() + " " + app.getMiddlename());
//
//						/* address */
//						nr.setCountry(app.getCountry1());
//						nr.setStateprovince(app.getStateprovince1());
//						nr.setRegion(app.getRegion1());
//						nr.setCity(app.getCity1());
//						nr.setBarangay(app.getBarangay1());
//						nr.setSubdivision(app.getSubdivision1());
//						nr.setStreetnoname(app.getStreetnoname1());
//						nr.setPostalcode(app.getPostalcode1());
//						
//						nr.setIsbeneficiary(false);
//						nr.setIsdependent(false);
//
//						nr.setDateadded(new Date());
//						nr.setAddedby(secservice.getUserName());
//						Tbmemberrelationship r = rel.getCounterPartRelationship(relative.getRelationshipcode(),
//								relative.getGender());
//
//						nr.setRelationshipcode(r.getRelationshipcode() != null ? r.getRelationshipcode() : null);
//						nr.setRelationshipdesc(r.getRelationshipdesc() != null ? r.getRelationshipdesc() : null);
//
//						if (dbService.save(relative) && dbService.save(nr)) {
//							return "success";
//						}
//					}
//				}
//			} else {
////				MemberRelations rel = new MemberRelations();
//				param.put("user", secservice.getUserName());
//				Tbuser user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:user", param);
//				Tbmembershipapp newapp = new Tbmembershipapp();
//
//				/* personal details */
//				newapp.setAge(relative.getAge());
//				newapp.setDateofbirth(relative.getDateofbirth());
//				newapp.setGender(relative.getGender());
//				newapp.setAreacodephone(relative.getContactno().substring(0, 2));
//				newapp.setPhoneno(relative.getContactno().substring(2, relative.getContactno().length()));
//				newapp.setSuffix(relative.getSuffix());
//
//				/* name */
//				newapp.setLastname(relative.getLastname().toUpperCase());
//				newapp.setFirstname(relative.getFirstname().toUpperCase());
//				newapp.setMiddlename(relative.getMiddlename().toUpperCase());
//
//				/* address */
//				newapp.setCountry1(relative.getCountry());
//				newapp.setStateprovince1(relative.getStateprovince());
//				newapp.setRegion1(relative.getRegion());
//				newapp.setCity1(relative.getCity());
//				newapp.setBarangay1(relative.getBarangay());
//				newapp.setSubdivision1(relative.getSubdivision());
//				newapp.setStreetnoname1(relative.getStreetnoname());
//				newapp.setPostalcode1(relative.getPostalcode());
//
//				relative.setRelatedappid(ApplicationNoGenerator.generateID("A", user.getCoopcode()));
//				relative.setDateadded(new Date());
//				relative.setAddedby(secservice.getUserName());
//
//				relative.setLastname(relative.getLastname().toUpperCase());
//				relative.setFirstname(relative.getFirstname().toUpperCase());
//				relative.setMiddlename(relative.getMiddlename().toUpperCase());
//				relative.setMainmembername(
//						relative.getLastname() + " " + relative.getFirstname() + " " + relative.getMiddlename());
//
//				if (dbService.save(relative)) {
//					return rel.createAssociateRegular(relative.getApplicantid(), newapp, relative.getRelatedappid());
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbmemberrelationship> listMemberRelationshipPerMemberID(String memberid) {
		// TODO Auto-generated method stub
		String qry = "FROM Tbmemberrelationship WHERE mainmemberid=:memberid and degreecode IN ('1','2')";
		try {
			param.put("memberid", memberid);
			String civilstatus = (String) dbService
					.executeUniqueSQLQuery("SELECT civilstatus FROM Tbmember where membershipid =:memberid", param);
			if (civilstatus.equals("1")) {
				qry = "FROM Tbmemberrelationship WHERE mainmemberid=:memberid and degreecode = '1'";
			}
			List<Tbmemberrelationship> list = (List<Tbmemberrelationship>) dbService.executeListHQLQuery(qry, param);
			if (list.size() > 0) {
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public String deleteAO(String aocode, String companycode) {
		param.put("aocode", aocode);
		param.put("companycode", companycode);
		Integer res = dbService
				.executeUpdate("DELETE FROM Tbaccountofficer WHERE companycode=:companycode AND aocode=:aocode", param);
		if (res != null && res > 0) {
			return "success";
		}
		return "failed";
	}

	@Override
	public Tbreferror getReferrorPerParam(String appid, String memberid) {
		// TODO Auto-generated method stub
		param.put("appid", appid);
		param.put("memberid", memberid);
		try {
			String ref = "SELECT membershipappid, membershipid, " + "employername, "
					+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='POSITION' and codevalue=position) as position, "
					+ "refmemberid, referrorname, empid FROM Tbreferror";
			if (memberid != null && appid == null) {
				ref = ref + " WHERE membershipid=:memberid";
			}
			if (appid != null && memberid == null) {
				ref = ref + " WHERE membershipappid=:appid";
			}
			Tbreferror r = (Tbreferror) dbService.execSQLQueryTransformer(ref, param, Tbreferror.class, 0);
			if (r != null) {
				return r;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public String addReferrorPerParam(Tbreferror r) {
		// TODO Auto-generated method stub
		try {
			dbService.saveOrUpdate(r);
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public BigDecimal getCapconAccount(String appno, String type) {
		BigDecimal row = BigDecimal.ZERO;
		try {
			if (appno != null && type != null) {
				String q = "SELECT AccountBalance FROM TBDEPOSIT" + " where SubProductCode ='" + type
						+ "' AND AccountNo IN (  select accountno from TBDEPOSITCIF where cifno = (select cifno from TBLSTAPP where appno ='"
						+ appno + "')" + " AND accountno = (SELECT AccountNo FROM TBDEPOSIT"
						+ " where SubProductCode ='" + type
						+ "' AND AccountNo IN (  select accountno from TBDEPOSITCIF where cifno = (select cifno from TBLSTAPP where appno ='"
						+ appno + "'))))";
				row = (BigDecimal) dbService.executeUniqueSQLQuery(q, param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	//

	@Override
	public String deleteItem(Integer dependentsID, Integer employmentID, Integer businessID, Integer otherid,
			Integer bankid) {
		// TODO Auto-generated method stub
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		String flag = null;

		try {
			System.out.println("---- DELETE START -----");
			if (dependentsID == null) {
				// do nothing
			} else {
				param.put("dependentid", dependentsID);
				Tbcifdependents d = (Tbcifdependents) dbService
						.executeUniqueHQLQuery("FROM Tbcifdependents WHERE dependentid=:dependentid", param);

				HistoryService h = new HistoryServiceImpl();
				h.addHistory(d.getCifno(), "Deleted dependents record", null);

				if (dbService.delete(d)) {
					flag = "success";
				} else {
					flag = "failed";
				}
			}
			if (employmentID == null) {
				// do nothing
			} else {
				System.out.println("--- DELETE EMPLOYEMENT START ---");
				param.put("employmentid", employmentID);
				Tbcifemployment e = (Tbcifemployment) dbService
						.executeUniqueHQLQuery("FROM Tbcifemployment WHERE employmentid=:employmentid", param);

				/** Wel.11-14-17 **/
				if (e != null) {
					System.out.println("--- TBCIFEMPLOYMENT 1 NOT NULL ---");
					if (e.getIsautogenerated() != null && e.getIsautogenerated()) {
						return flag = "issystemgenerated";
					} else {
//						if(e.getMngempid()!=null){
//							param.put("genid", e.getMngempid());
//							Tbcifemployment e2 = (Tbcifemployment) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbcifemployment WHERE mngempid=:genid", param);
//							if(e2!=null){
//								System.out.println("--- TBCIFEMPLOYMENT 2 NOT NULL ---");
//								Tbmanagement m = (Tbmanagement) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbmanagement WHERE mngempid=:genid", param);
//								if(m!=null){
//									if(dbService.delete(m)){
//										if(dbService.delete(e2)){
//											HistoryService h = new HistoryServiceImpl();
//											h.addHistory(e.getCifno(), "Deleted employment record", null);
//											flag = "success";
//											/**JAY.05-21-18**/
//											System.out.println("----- Deleted employment -----");
//											CustomerRelationshipService delRel = new CustomerRelationshipServiceImpl();
//											System.out.println("Delete cifno: "+m.getCifno()+" relcode: "+m.getRelationshipcode()+" relcifno: "+m.getRelatedcifno()+" mngempid: "+m.getMngempid());
//											delRel.deleteRelCorp(m.getCifno(), m.getRelationshipcode(), m.getRelatedcifno(), m.getMngempid());
//										}
//										else{
//											flag = "failed";
//										}
//									}else{
//										flag = "failed";
//									}
//								}else{
//									// delete only EMP
//									if(dbService.delete(e2)){
//										HistoryService h = new HistoryServiceImpl();
//										h.addHistory(e.getCifno(), "Deleted employment record", null);
//										flag = "success";
//									}else{
//										flag = "failed";
//									}
//								}
//							}
//						}
//					else{
						if (dbService.delete(e)) {
							HistoryService h = new HistoryServiceImpl();
							h.addHistory(e.getCifno(), "Deleted employment record", null);
							flag = "success";
						} else {
							flag = "failed";
						}
					}
//					}
				}
			}
			if (businessID == null) {
				// do nothing
			} else {
				param.put("businessid", businessID);
				Tbcifbusiness b = (Tbcifbusiness) dbService
						.executeUniqueHQLQuery("FROM Tbcifbusiness WHERE businessid=:businessid", param);
				if (dbService.delete(b)) {
					HistoryService h = new HistoryServiceImpl();
					h.addHistory(b.getCifno(), "Deleted business record", null);
					flag = "success";
				} else {
					flag = "failed";
				}
				/** Wel.11-14-17 **/
//				if(b!=null){
//					if(b.getIsautogenerated()==true){
//						return flag = "issystemgenerated";
//					}else{
//						if(b.getMngbusid()!=null){
//							param.put("genid", b.getMngbusid());
//							Tbcifbusiness b2 = (Tbcifbusiness) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbcifbusiness WHERE mngbusid=:genid", param);
//							if(b2!=null){
//								Tbmanagement m = (Tbmanagement) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbmanagement WHERE mngempid=:genid", param);
//								if(m!=null){
//									if(dbService.delete(m)){
//										if(dbService.delete(b2)){
//											HistoryService h = new HistoryServiceImpl();
//											h.addHistory(b.getCifno(), "Deleted business record", null);
//											flag = "success";
//											System.out.println("----------deleted business--------");
//											/**JAY.05-21-18 **/
//											CustomerRelationshipService delRel = new CustomerRelationshipServiceImpl();
//											delRel.deleteRelCorp(m.getCifno(), m.getRelationshipcode(), m.getRelatedcifno(), m.getMngempid());
//										}
//										
//										else{
//											flag = "failed";
//										}
//									}else{
//										flag = "failed";
//									}
//								}else{
//									if(dbService.delete(b2)){
//										HistoryService h = new HistoryServiceImpl();
//										h.addHistory(b.getCifno(), "Deleted business record", null);
//										flag = "success";
//									}else{
//										flag = "failed";
//									}								
//								}
//							}
//						}else{
//							if (dbService.delete(b)) {
//								HistoryService h = new HistoryServiceImpl();
//								h.addHistory(b.getCifno(), "Deleted business record", null);
//								flag = "success";
//							} else {
//								flag = "failed";
//							}
//						}						
//					}
//				}				
			}

			// other contacts

			if (otherid != null) {
				param.put("id", otherid);
				Tbothercontacts c = (Tbothercontacts) dbService
						.executeUniqueHQLQuery("FROM Tbothercontacts WHERE id=:id", param);

				HistoryService h = new HistoryServiceImpl();
				h.addHistory(c.getCifno(), "Deleted other contact.", null);

				if (dbService.delete(c)) {
					flag = "success";
				} else {
					flag = "failed";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tbcifcorporate getCorporate(String cifno) {
		// TODO Auto-generated method stub
		DBService dbservice = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		Tbcifcorporate corp = new Tbcifcorporate();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				corp = (Tbcifcorporate) dbservice.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno=:cifno",
						param);
				if (corp != null) {
					return corp;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return corp;
	}

	@Override
	public Tbcifmain getDetails(String cifno) {
		// TODO Auto-generated method stub
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		Tbcifmain details = new Tbcifmain();
//		String filepath = null;
		File dir = new File(
				RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("/resources/docdir/"));
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				System.out.println(cifno);
				details = (Tbcifmain) dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", param);
				if (details.getProfpiccode() != null) {
					File directory = new File(dir.getPath());
					File[] files = directory.listFiles();
					for (File f : files) {
						if (f.getName().startsWith(details.getCifno())) {
							f.delete();
						}
					}
					String filename = details.getCifno() + "_"
							+ DateTimeUtil.convertDateToString(new Date(), DateTimeUtil.DATE_FORMAT_YYYYMMDD) + "_ "
							+ DateTimeUtil.convertDateToString(new Date(), DateTimeUtil.DATE_FORMAT_HHMM) + ".png";
//					filepath = dir.toString() + "\\" + filename;
//					System.out.println(filepath + "filepath");
//					System.out.println(dir.toString() + "dir");
					ImageUtils.base64ToPDF(details.getProfpiccode(), dir.toString() + "\\", filename);

					details.setProfpiccode("resources/docdir/" + filename);
//					System.out.println(details.getProfpiccode());
				}

				if (details != null) {
					return details;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return details;
	}

	@Override
	public Tbcifindividual getIndividual(String cifno) {
		// TODO Auto-generated method stub
		DBService dbservice = new DBServiceImplCIF();
		DBService dbserviceLOS = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tbcifindividual indiv = new Tbcifindividual();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				indiv = (Tbcifindividual) dbservice.executeUniqueHQLQuery("FROM Tbcifindividual WHERE cifno=:cifno",
						param);
				// Added by Ced 6-23-2021
				if (indiv == null) {
					indiv = (Tbcifindividual) dbserviceLOS.execStoredProc(
							"SELECT * FROM Tblstappindividual WHERE appno=:cifno", param, Tbcifindividual.class, 0,
							null);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return indiv;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcifbusiness> getListBusiness(String cifno) {
		// TODO Auto-generated method stub
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		List<Tbcifbusiness> list = new ArrayList<Tbcifbusiness>();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				list = (List<Tbcifbusiness>) dbService.executeListHQLQuery("FROM Tbcifbusiness WHERE cifno=:cifno",
						param);
				if (list != null) {
					return list;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcifdependents> getListDependents(String cifno) {
		// TODO Auto-generated method stub
		DBService dbService = new DBServiceImplCIF();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		List<Tbcifdependents> list = new ArrayList<Tbcifdependents>();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				list = (List<Tbcifdependents>) dbService.executeListHQLQuery("FROM Tbcifdependents WHERE cifno=:cifno",
						param);
				if (list != null && list.size() > 0) {
					return list;
				} else {
					list = (List<Tbcifdependents>) dbServiceCOOP.execStoredProc(
							"SELECT * FROM Tblstappdependents WHERE appno=:cifno", param, Tbcifdependents.class, 1,
							null);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcifemployment> getListEmployment(String cifno) {
		// TODO Auto-generated method stub
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		List<Tbcifemployment> list = new ArrayList<Tbcifemployment>();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				list = (List<Tbcifemployment>) dbService.executeListHQLQuery("FROM Tbcifemployment WHERE cifno=:cifno",
						param);
//				if (list != null) {
//					/** Wel.July31,2017 **/
//					List<Tbmanagement> m = (List<Tbmanagement>) dbService.executeListHQLQuery("FROM Tbmanagement WHERE relatedcifno=:cifno AND relationshipcode IN ('SHA','OFF','DR')", param); 
//					if(m != null){
//						for(Tbmanagement man : m){
//							System.out.println(">>> LOOP AT EMPLIST <<<");
//							param.put("relcifno", man.getRelatedcifno());
//							String relcode = man.getRelationshipcode();
//							String position ="";
//							if(relcode.equals("SHA")){
//								position = "'0'";
//							}else if(relcode.equals("DR")){
//								position = "'1'";
//							}else if(relcode.equals("OFF")){
//								position = "'2','3','4','5','6','7','8'";
//							}
//							Tbcifemployment e = (Tbcifemployment) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbcifemployment WHERE cifno=:relcifno AND position IN ("+position+")", param);
//							if(e==null){
//								dbService.delete(man);
//							}
//						}
//					}
//					return list;
//				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public String saveOrUpdateBusiness(Tbcifbusiness bus) {
		// TODO Auto-generated method stub
		DBService dbservice = new DBServiceImplCIF();
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		Tbcifbusiness bu = new Tbcifbusiness();

		try {

			if (bus.getBusinessid() != null) {
				params.put("id", bus.getBusinessid());
				bu = (Tbcifbusiness) dbservice.executeUniqueHQLQuery("FROM Tbcifbusiness WHERE businessid=:id", params);
				if (bu != null) {
					bu.setBuscifno(bus.getBuscifno());
					bu.setNatureofbusiness(bus.getNatureofbusiness());
					bu.setBusinesstype(bus.getBusinesstype());
					bu.setBusinessname(bus.getBusinessname());
					bu.setBusinesstin(bus.getBusinesstin());
					bu.setBusinessclass(bus.getBusinessclass());
					bu.setBusinessphoneno(bus.getBusinessphoneno());
					bu.setBusinessfaxno(bus.getBusinessfaxno());
					bu.setEmailaddress(bus.getEmailaddress());
					bu.setPsic(bus.getPsic());
					bu.setIncoporationdate(bus.getIncoporationdate());
					bu.setYearsofservice(bus.getYearsofservice());
					bu.setGrossincome(bus.getGrossincome());
					bu.setAnnualmonthind(bus.getAnnualmonthind());
					bu.setCurrency(bus.getCurrency());
					bu.setStreetno1(bus.getStreetno1());
					bu.setSubdivision1(bus.getSubdivision1());
					bu.setCountry1(bus.getCountry1());
					bu.setProvince1(bus.getProvince1());
					bu.setCity1(bus.getCity1());
					bu.setBarangay1(bus.getBarangay1());
					bu.setPostalcode1(bus.getPostalcode1());
					bu.setPostalcodename(bus.getPostalcodename());
					bu.setHomeownership1(bus.getHomeownership1());
					bu.setOtherhomeownership1(bus.getOtherhomeownership1());
					bu.setOccupiedsince1(bus.getOccupiedsince1());
					bu.setCountrycodephone(bus.getCountrycodephone());
					bu.setAreacodephone(bus.getAreacodephone());
					bu.setCountrycodefax(bus.getCountrycodefax());
					bu.setAreacodefax(bus.getAreacodefax());
					bu.setPsiccode(bus.getPsiccode());
					bu.setPsiclevel1(bus.getPsiclevel1());
					bu.setPsiclevel2(bus.getPsiclevel2());
					bu.setPsiclevel3(bus.getPsiclevel3());
					bu.setPsiclevel4(bus.getPsiclevel4());
					bu.setFulladdress1(fullAddress(bus.getStreetno1(), bus.getSubdivision1(), bus.getBarangay1(),
							bus.getCity1(), bus.getProvince1(), bus.getCountry1(), bus.getPostalcode1()));
					bu.setGenmanager(bus.getGenmanager());
					bu.setGmcif(bus.getGmcif());

					// no changes if autogenerated

					if (dbservice.saveOrUpdate(bu)) {
						System.out.println(">>>>>>>>>> SUCCESS UPDATING BUSINESS");
						System.out.println(">>>>>>>>>> NEXT IS MANAGEMENT");
						if (bu.getMngbusid() != null) {
							params.put("genid", bu.getMngbusid());
							Tbmanagement m = (Tbmanagement) dbservice.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbmanagement WHERE mngempid=:genid", params);
							if (m != null) {
								params.put("cif", bu.getCifno());
								Tbcifindividual indiv = (Tbcifindividual) dbservice.executeUniqueHQLQueryMaxResultOne(
										"FROM Tbcifindividual WHERE cifno=:cif", params);
								if (indiv != null) {
									m.setLastname(indiv.getLastname());
									m.setFirstname(indiv.getFirstname());
									m.setMiddlename(indiv.getMiddlename());
									m.setSuffix(indiv.getSuffix());
									m.setDateofbirth(indiv.getDateofbirth());
									m.setNationality(indiv.getNationality());
									m.setCifno(bu.getBuscifno());
									m.setDatecreated(new Date());

									System.out.println(">>>>>>>>>>> buscifno - " + bu.getBuscifno());
									params.put("cifcorp", bu.getBuscifno());
									Tbcifcorporate corp = (Tbcifcorporate) dbservice.executeUniqueHQLQueryMaxResultOne(
											"FROM Tbcifcorporate WHERE cifno=:cifcorp", params);
									if (corp != null) {
										System.out
												.println(">>>>>>>>>>>>>> WOHHHH corpbtype - " + corp.getBusinesstype());
										if (corp.getBusinesstype().equals("09")) {
											m.setRelationshipcode("OWN");
										} else if (corp.getBusinesstype().equals("10")
												|| corp.getBusinesstype().equals("11")
												|| corp.getBusinesstype().equals("14")
												|| corp.getBusinesstype().equals("16")) {
											m.setRelationshipcode("P");
										}
										m.setBusinesstype(corp.getBusinesstype());
										// 120417
										if (bu.getIsautogenerated() == true) {
											m.setIsautogenerated(false);
										} else {
											m.setIsautogenerated(true);
										}
									}
									if (dbservice.saveOrUpdate(m)) {
										System.out.println(">>>>>>>>>> SUCCESS UPDATING MANAGEMENT");
										HistoryService h = new HistoryServiceImpl();
										h.addHistory(bu.getCifno(), "Update business information.", null);
										flag = "success";
									}
								}
							}
						}
					}
				}
			} else {
				params.put("cifcorp", bus.getBuscifno());
				Tbcifcorporate corp = (Tbcifcorporate) dbservice
						.executeUniqueHQLQueryMaxResultOne("FROM Tbcifcorporate WHERE cifno=:cifcorp", params);
				if (corp == null || corp.getBusinesstype() == null) {
					return flag = "btypeisnull";
				} else {
					if (corp.getBusinesstype().equals("09") || corp.getBusinesstype().equals("10")
							|| corp.getBusinesstype().equals("11") || corp.getBusinesstype().equals("14")
							|| corp.getBusinesstype().equals("16")) {

						bus.setIsautogenerated(false);
						bus.setFulladdress1(fullAddress(bus.getStreetno1(), bus.getSubdivision1(), bus.getBarangay1(),
								bus.getCity1(), bus.getProvince1(), bus.getCountry1(), bus.getPostalcode1()));
						if (dbservice.save(bus)) {
							// flag = "success";
							HistoryService h = new HistoryServiceImpl();
							h.addHistory(bus.getCifno(), "Added business information.", null);

							System.out.println(">>> 2 WAY SAVING AT MANAGEMENT <<<");
							System.out.println(">>> RELATEDCIFNO - " + bus.getBuscifno());

							params.put("cifindiv", bus.getCifno());

							System.out.println(">>> BUSINESSTYPE - " + corp.getBusinesstype());

							Tbmanagement m = new Tbmanagement();
							if (corp.getBusinesstype().equals("09")) {
								// check if no existing record
								List<Tbmanagement> mn = (List<Tbmanagement>) dbservice.executeListHQLQuery(
										"FROM Tbmanagement WHERE relatedcifno=:cifindiv AND cifno=:cifcorp AND relationshipcode='OWN'",
										params);
								// f(mn==null){
								// continue to create
								m.setRelationshipcode("OWN");
								// }else{
								// System.out.println(">>> Business selected already have an Owner. <<<");
								// return flag = "Business selected already have an Owner.";
								// }
							} else if (corp.getBusinesstype().equals("10") || corp.getBusinesstype().equals("11")
									|| corp.getBusinesstype().equals("14") || corp.getBusinesstype().equals("16")) {
								// continue to create
								m.setRelationshipcode("P");

							} else {
								System.out.println(
										">>> Corporate Business Type is not Sole Proprietorship or Partnership. <<<");
								return flag = "corporation";
							}
							// continue to create
							if (m.getRelationshipcode().equalsIgnoreCase("OWN")
									|| m.getRelationshipcode().equalsIgnoreCase("P")) {
								m.setCifno(bus.getBuscifno());
								m.setRelatedcifno(bus.getCifno());
								Tbcifindividual indiv = (Tbcifindividual) dbservice.executeUniqueHQLQueryMaxResultOne(
										"FROM Tbcifindividual WHERE cifno=:cifindiv", params);
								if (indiv != null) {
									m.setLastname(indiv.getLastname());
									m.setFirstname(indiv.getFirstname());
									m.setMiddlename(indiv.getMiddlename());
									m.setSuffix(indiv.getSuffix());
									m.setDateofbirth(indiv.getDateofbirth());
									m.setNationality(indiv.getNationality());
									m.setIsautogenerated(true);
									m.setDatecreated(new Date());

									NoGenerator no = new NoGenerator();
									String id = no.generateBusID();
									m.setMngempid(id);
								}
								m.setBusinesstype(corp.getBusinesstype());
								if (dbservice.save(m)) {
									// flag = "success";
									System.out.println(">>> SUCCESS SAVING AT MANAGEMENT.");
									bus.setMngbusid(m.getMngempid());
									if (dbservice.saveOrUpdate(bus)) {
										flag = "success";
									}
									/** JAY.05-21-18 **/
									CustomerRelationshipService saveRelation = new CustomerRelationshipServiceImpl();
									saveRelation.saveCustRelationshipCorp(m.getCifno(), m.getRelationshipcode(),
											m.getRelatedcifno());
								} else {
									System.out.println(">>> ERROR!!!!! SAVING AT MANAGEMENT.");
									flag = "success";
								}
							}
						} else {
							System.out.println(">>>>>>>>>>>>>>>>>> ERROR!!!!! SAVING AT BUSINESS.");
						}

					} else {
						return flag = "corporation";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveDependents(Tbcifdependents dependents) {
		// TODO Auto-generated method stub
		DBService dbservice = new DBServiceImplCIF();
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		Tbcifdependents d = new Tbcifdependents();
		try {

			if (dependents.getCifno() != null) {
				params.put("cifno", dependents.getCifno());
				d = (Tbcifdependents) dbservice.executeUniqueHQLQuery("FROM Tbcifdependents WHERE cifno=:cifno",
						params);
			}
			if (d != null) {
				d.setAddress(dependents.getAddress());
				d.setAge(dependents.getAge());
				d.setDateofbirth(dependents.getDateofbirth());
				d.setFullname(dependents.getFullname());
				d.setGender(dependents.getGender());
				d.setGradeyear(dependents.getGradeyear());
				d.setIssameasfulladdress1(dependents.getIssameasfulladdress1());
				d.setIssameasfulladdress2(dependents.getIssameasfulladdress2());
				d.setOtherrelationship(dependents.getOtherrelationship());
				d.setRelationship(dependents.getRelationship());
				d.setContactNumber(dependents.getContactNumber());

				if (dbservice.saveOrUpdate(dependents)) {
					flag = "success";
					// 08-08-17 PONGYU
					HistoryService h = new HistoryServiceImpl();
					h.addHistory(dependents.getCifno(), "Update dependents information", null);
				}
			} else {
				dependents.setAddress(dependents.getAddress());
				dependents.setAge(dependents.getAge());
				dependents.setDateofbirth(dependents.getDateofbirth());
				dependents.setFullname(dependents.getFullname());
				dependents.setGender(dependents.getGender());
				dependents.setGradeyear(dependents.getGradeyear());
				dependents.setIssameasfulladdress1(dependents.getIssameasfulladdress1());
				dependents.setIssameasfulladdress2(dependents.getIssameasfulladdress2());
				dependents.setOtherrelationship(dependents.getOtherrelationship());
				dependents.setRelationship(dependents.getRelationship());
				dependents.setContactNumber(dependents.getContactNumber());
				if (dbservice.saveOrUpdate(dependents)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateEmployment(Tbcifemployment emp) {
		DBService dbservice = new DBServiceImplCIF();
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		Tbcifemployment em = new Tbcifemployment();
		try {
			if (emp.getEmploymentid() != null) {
				params.put("id", emp.getEmploymentid());
				System.out.println("-----EMP ID -------- " + emp.getEmploymentid());
				em = (Tbcifemployment) dbservice.executeUniqueHQLQuery("FROM Tbcifemployment WHERE employmentid=:id",
						params);
				if (em != null) {
					em.setEmployerId(emp.getEmployerId());	
					em.setEmployeraddress(emp.getEmployeraddress());
					em.setBusinesstype(emp.getBusinesstype());
					em.setEmpcifno(emp.getEmpcifno());
					em.setEmprecordtype(emp.getEmprecordtype());
					em.setEmployername(emp.getEmployername());
					em.setEmployertin(emp.getEmployertin());
					em.setEmployerphoneno(emp.getEmployerphoneno());
					em.setPsic(emp.getPsic());
					em.setPosition(emp.getPosition());
					em.setPsoc(emp.getPsoc());
					em.setSeafarerid(emp.getSeafarerid());
					em.setSeafarerduration(emp.getSeafarerduration());
					em.setSeafarervessel(emp.getSeafarervessel());
					em.setSeafarerdisembarkationdate(emp.getSeafarerdisembarkationdate());
					em.setSeafarerembarkationdate(emp.getSeafarerembarkationdate());
					em.setSeafarerpercentageofallotment(emp.getSeafarerpercentageofallotment());
					em.setSeafarerallottee1(emp.getSeafarerallottee1());
					em.setSeafarerallottee2(emp.getSeafarerallottee2());
					em.setDepartment(emp.getDepartment());
					em.setRank(emp.getRank());
					em.setEmpstatus(emp.getEmpstatus());
					em.setDatehiredfrom(emp.getDatehiredfrom());
					if (emp.getDatehiredto() == null) {
						em.setDatehiredto(new Date());
					} else {
						em.setDatehiredto(emp.getDatehiredto());
					}
					em.setYearsofservice(emp.getYearsofservice());
					em.setImmediatehead(emp.getImmediatehead());
					em.setGrossincome(emp.getGrossincome());
					em.setAnnualmonthind(emp.getAnnualmonthind());
					em.setCurrency(emp.getCurrency());
					em.setStreetno1(emp.getStreetno1());
					em.setSubdivision1(emp.getSubdivision1());
					em.setCountry(emp.getCountry());
					em.setProvince1(emp.getProvince1());
					em.setCity1(emp.getCity1());
					em.setBarangay1(emp.getBarangay1());
					em.setPostalcode1(emp.getPostalcode1());
					em.setPostalcodename1(emp.getPostalcodename1());
					em.setCountrycodephone(emp.getCountrycodephone());
					em.setAreacodephone(emp.getAreacodephone());
					em.setPsiccode(emp.getPsiccode());
					em.setPsiclevel1(emp.getPsiclevel1());
					em.setPsiclevel2(emp.getPsiclevel2());
					em.setPsiclevel3(emp.getPsiclevel3());
					em.setPsiclevel4(emp.getPsiclevel4());
					em.setPsoccode(emp.getPsoccode());
					em.setPsoclevel1(emp.getPsoclevel1());
					em.setPsoclevel2(emp.getPsoclevel2());
					em.setPsoclevel3(emp.getPsoclevel3());
					em.setPsoclevel4(emp.getPsoclevel4());
					if (emp.getIsdepedemployee() == null) {
						em.setIsdepedemployee(true);
					} else {
						em.setIsdepedemployee(emp.getIsdepedemployee());
					}
					em.setMonthsofservice(emp.getMonthsofservice());
					em.setRegioncode(emp.getRegioncode());
					em.setDivisioncode(emp.getDivisioncode());
					em.setStationcode(emp.getStationcode());
					em.setPositioncategory(emp.getPositioncategory());
					em.setOtherpositioncategory(emp.getOtherpositioncategory());
					em.setIspresent(emp.getIspresent());
					em.setEmployeeno(emp.getEmployeeno());
					em.setDepedposition(emp.getDepedposition());
//					em.setOccupationstatus(emp.getOccupationstatus());
					em.setFulladdress1(fullAddress(emp.getStreetno1(), emp.getSubdivision1(), emp.getBarangay1(),
							emp.getCity1(), emp.getProvince1(), emp.getCountry(), emp.getPostalcode1()));
					if (dbservice.saveOrUpdate(em)) {
						System.out.println(">>>>>>>>>> SUCCESS UPDATING EMPLOYMENT");
						System.out.println(">>>>>>>>>> NEXT IS MANAGEMENT");
						if (em.getMngempid() != null) {
							System.out.println(">>>>>>>>>> MNGEMP !=NULL - " + em.getMngempid());
							params.put("genid", em.getMngempid());
							Tbmanagement m = (Tbmanagement) dbservice.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbmanagement WHERE mngempid=:genid", params);
							if (m != null) {
								System.out.println(">>>>>>>>>> EMP Position Category - " + em.getPositioncategory());
								if (em.getPositioncategory().equalsIgnoreCase("Director")
										|| em.getPositioncategory().equalsIgnoreCase("Trustee")) {
									m.setRelationshipcode("DR");
								} else if (em.getPositioncategory().equalsIgnoreCase("Junior Officer")
										|| em.getPositioncategory().equalsIgnoreCase("Senior Officer")) {
									m.setRelationshipcode("OFF");
								} else {
									// delete old record
									if (dbservice.delete(m)) {
										System.out.println(
												">>>>>>>>>> DELETED OLD MANAGEMENT, UPDATED EMP IS NOT 2 WAY SAVING");
										HistoryService h = new HistoryServiceImpl();
										h.addHistory(em.getCifno(), "Update employment information.", null);
										return flag = "success";
									}
								}
								if (m.getRelationshipcode().equals("DR") || m.getRelationshipcode().equals("OFF")) {
									params.put("cifcorp", em.getEmpcifno());
									Tbcifcorporate corp = (Tbcifcorporate) dbservice.executeUniqueHQLQueryMaxResultOne(
											"FROM Tbcifcorporate WHERE cifno=:cifcorp", params);
									if (corp != null) {
										m.setBusinesstype(corp.getBusinesstype());
									}
									m.setCifno(em.getEmpcifno());
									params.put("cifindiv", em.getCifno());
									Tbcifindividual indiv = (Tbcifindividual) dbservice
											.executeUniqueHQLQueryMaxResultOne(
													"FROM Tbcifindividual WHERE cifno=:cifindiv", params);
									if (indiv != null) {
										m.setFirstname(indiv.getFirstname());
										m.setMiddlename(indiv.getMiddlename());
										m.setLastname(indiv.getLastname());
										m.setSuffix(indiv.getSuffix());
										m.setDateofbirth(indiv.getDateofbirth());
										m.setNationality(indiv.getNationality());
									}
									// 120417
									if (em.getIsautogenerated() == true) {
										m.setIsautogenerated(false);
									} else {
										m.setIsautogenerated(true);
									}
									m.setPosition(em.getPosition());
									m.setMngempid(em.getMngempid());
									m.setDatecreated(new Date());
									if (dbservice.saveOrUpdate(m)) {
										System.out.println(">>>>>>>>>> SUCCESS UPDATING MANAGEMENT");
										HistoryService h = new HistoryServiceImpl();
										h.addHistory(em.getCifno(), "Update employment information.", null);
										return flag = "success";
									}
								}
							} else {
								Tbmanagement m2 = new Tbmanagement();
								if (em.getPositioncategory().equalsIgnoreCase("Director")
										|| em.getPositioncategory().equalsIgnoreCase("Trustee")) {
									m2.setRelationshipcode("DR");
								} else if (em.getPositioncategory().equalsIgnoreCase("Senior Officer")
										|| em.getPositioncategory().equalsIgnoreCase("Junior Officer")) {
									m2.setRelationshipcode("OFF");
									m2.setPosition(em.getPosition());
								} else {
									System.out.println(">>>>>>>> NO MANAGEMENT");
									HistoryService h = new HistoryServiceImpl();
									h.addHistory(em.getCifno(), "Update employment information.", null);
									return flag = "success";
								}
								// continue to create
								if (m2.getRelationshipcode().equalsIgnoreCase("DR")
										|| m2.getRelationshipcode().equalsIgnoreCase("OFF")) {
									params.put("cifcorp", em.getEmpcifno());
									Tbcifcorporate corp = (Tbcifcorporate) dbservice.executeUniqueHQLQueryMaxResultOne(
											"FROM Tbcifcorporate WHERE cifno=:cifcorp", params);
									m2.setCifno(em.getEmpcifno());
									m2.setRelatedcifno(emp.getCifno());
									params.put("cifindiv", em.getCifno());
									Tbcifindividual indiv = (Tbcifindividual) dbservice
											.executeUniqueHQLQueryMaxResultOne(
													"FROM Tbcifindividual WHERE cifno=:cifindiv", params);
									if (indiv != null) {
										m2.setLastname(indiv.getLastname());
										m2.setFirstname(indiv.getFirstname());
										m2.setMiddlename(indiv.getMiddlename());
										m2.setSuffix(indiv.getSuffix());
										m2.setDateofbirth(indiv.getDateofbirth());
										m2.setNationality(indiv.getNationality());
									}
									if (em.getIsautogenerated() == true) {
										m2.setIsautogenerated(false);
									} else {
										m2.setIsautogenerated(true);
									}
									m2.setBusinesstype(corp.getBusinesstype());
									m2.setMngempid(em.getMngempid());
									m2.setDatecreated(new Date());
									if (dbservice.save(m2)) {
										HistoryService h = new HistoryServiceImpl();
										h.addHistory(em.getCifno(), "Update employment information.", null);
										System.out.println(">>> SUCCESS UPDATING MANAGEMENT.");
										return flag = "success";
									} else {
										System.out.println(">>> ERROR!!!!! UPDATING MANAGEMENT.");
									}
								}
							}
						} else {
							// based on id
//							Tbmanagement  m = new Tbmanagement();
//							if(em.getPositioncategory().equalsIgnoreCase("Director") || em.getPositioncategory().equalsIgnoreCase("Trustee")){ 
//									m.setRelationshipcode("DR");
//							}else if(em.getPositioncategory().equalsIgnoreCase("Senior Officer") || em.getPositioncategory().equalsIgnoreCase("Junior Officer")){ 
//									m.setRelationshipcode("OFF");
//									m.setPosition(em.getPosition());
//							}else{
//								System.out.println(">>>>>>>> NO MANAGEMENT");
//								HistoryService h = new HistoryServiceImpl();
//								h.addHistory(em.getCifno(), "Update employment information.", null);
//								return flag = "success";
//							}
//							// continue to create
//							if(m.getRelationshipcode().equalsIgnoreCase("DR") || m.getRelationshipcode().equalsIgnoreCase("OFF")){
////								NoGenerator no = new NoGenerator();
////								String id = no.generateEmpID();
////								System.out.println(">>>>>>>>>> GENERATED EmpID - " + id );
//								params.put("cifcorp", em.getEmpcifno());
//								Tbcifcorporate  corp = (Tbcifcorporate) dbservice.executeUniqueHQLQueryMaxResultOne("FROM Tbcifcorporate WHERE cifno=:cifcorp", params);
//								m.setCifno(em.getEmpcifno());
//								m.setRelatedcifno(emp.getCifno());
//								params.put("cifindiv", em.getCifno());
//								Tbcifindividual indiv = (Tbcifindividual) dbservice.executeUniqueHQLQueryMaxResultOne("FROM Tbcifindividual WHERE cifno=:cifindiv", params);
//								if(indiv!=null){
//									m.setLastname(indiv.getLastname());
//									m.setFirstname(indiv.getFirstname());
//									m.setMiddlename(indiv.getMiddlename());
//									m.setSuffix(indiv.getSuffix());
//									m.setDateofbirth(indiv.getDateofbirth());
//									m.setNationality(indiv.getNationality());
//								}
//								if(em.getIsautogenerated()==true){
//									m.setIsautogenerated(false);
//								}else{
//									m.setIsautogenerated(true);							
//								}
//								m.setBusinesstype(corp.getBusinesstype());
//								m.setMngempid(em.getMngempid());
//								m.setDatecreated(new Date());
//								if(dbservice.save(m)){
							HistoryService h = new HistoryServiceImpl();
							h.addHistory(em.getCifno(), "Update employment information.", null);
							System.out.println(">>> SUCCESS UPDATING MANAGEMENT.");
							return flag = "success";
//								}else{
//									System.out.println(">>> ERROR!!!!! UPDATING MANAGEMENT.");
//								}
//							}
						}
					}
				}
			} else {
				dbservice.save(emp);
				return flag = "success";
//				params.put("cifcorp", emp.getEmpcifno());
//				Tbcifcorporate  corp = (Tbcifcorporate) dbservice.executeUniqueHQLQueryMaxResultOne("FROM Tbcifcorporate WHERE cifno=:cifcorp", params);
//				
//				if(corp == null || corp.getBusinesstype()==null){
//					return flag = "btypeisnull";
//				}else{
//					if(
//							//corp.getBusinesstype().equals("09") || 
//							corp.getBusinesstype().equals("10") || 
//							   corp.getBusinesstype().equals("11") || corp.getBusinesstype().equals("14") || 
//							   corp.getBusinesstype().equals("16")){
//						
//						return flag = "notcorporation";
//					}else{
//						// creation
//						System.out.println(">>>>>>>>>>>>> POSITION CATEGORY - " + emp.getPositioncategory());
//						if(emp.getPositioncategory().equalsIgnoreCase("Director") 
//								|| emp.getPositioncategory().equalsIgnoreCase("Trustee") 
//								|| emp.getPositioncategory().equalsIgnoreCase("Senior Officer")
//								|| emp.getPositioncategory().equalsIgnoreCase("Junior Officer") 
//								|| emp.getPositioncategory().equalsIgnoreCase("Stockholder/Shareholder")
//								|| emp.getPositioncategory().equalsIgnoreCase("Signatories") 
//								|| emp.getPositioncategory().equalsIgnoreCase("Others")
//								|| emp.getPositioncategory().equalsIgnoreCase("Staff")) { //JAY.05-21-18
//							
//							NoGenerator no = new NoGenerator();
//							String id = no.generateEmpID();
//							emp.setMngempid(id);
//							System.out.println(">>>>>>>>>> ----- GENERATED EmpID - " + id );
//						}
//						emp.setIsautogenerated(false);
//						emp.setFulladdress1(fullAddress(
//								emp.getStreetno1(), 
//								emp.getSubdivision1(), 
//								emp.getBarangay1(), 
//								emp.getCity1(), 
//								emp.getProvince1(), 
//								emp.getCountry(), 
//								emp.getPostalcode1()));
//						if (dbservice.save(emp)) {
//							//flag = "success";
//							HistoryService h = new HistoryServiceImpl();
//							h.addHistory(emp.getCifno(), "Added employment information", null);
//							
//							Tbmanagement  m = new Tbmanagement();
//							if(emp.getPositioncategory().equalsIgnoreCase("Director") || emp.getPositioncategory().equalsIgnoreCase("Trustee")){ 
//								if(corp!=null){
//									m.setRelationshipcode("DR"); // 11-21-17
//								}
//							}else if(emp.getPositioncategory().equalsIgnoreCase("Senior Officer") || emp.getPositioncategory().equalsIgnoreCase("Junior Officer")){ 
//								if(corp!=null){
//									m.setRelationshipcode("OFF"); // 11-21-17
//									m.setPosition(emp.getPosition()); // 11-21-17
//								}
//							}
//							/**JAY.05-21-18**/
//							else if(emp.getPositioncategory().equalsIgnoreCase("Signatories")){
//								if(corp!=null){
//									m.setRelationshipcode("SIG");
//									m.setPosition(emp.getPosition());
//								}
//							}
//							else if(emp.getPositioncategory().equalsIgnoreCase("Stockholder/Shareholder")){
//								if(corp!=null){
//									m.setRelationshipcode("SHA");
//									m.setPosition(emp.getPosition());
//								}
//								
//							}
//							else if(emp.getPositioncategory().equalsIgnoreCase("Others") || emp.getPositioncategory().equalsIgnoreCase("Staff")){
//								if(corp != null){
//									m.setRelationshipcode("OTH");
//									m.setPosition(emp.getPosition());
//								}
//							}
//							else{
//								System.out.println(">>>>>>>> NO MANAGEMENT");
//								return flag = "success";
//							}
//							// continue to create
//							//update- JAY.05-21-18
//							if(m.getRelationshipcode().equalsIgnoreCase("DR") || m.getRelationshipcode().equalsIgnoreCase("OFF") ||
//							  m.getRelationshipcode().equalsIgnoreCase("SIG") || m.getRelationshipcode().equalsIgnoreCase("SHA")
//							  || m.getRelationshipcode().equalsIgnoreCase("OTH")
//							){ // 11-21-17
//								
//								m.setCifno(emp.getEmpcifno());
//								m.setRelatedcifno(emp.getCifno());
//								params.put("cifindiv", emp.getCifno());
//								Tbcifindividual indiv = (Tbcifindividual) dbservice.executeUniqueHQLQueryMaxResultOne("FROM Tbcifindividual WHERE cifno=:cifindiv", params);
//								Tbcifmain main = (Tbcifmain)dbservice.executeUniqueHQLQuery("FROM Tbcifmain where cifno=:cifindiv", params);
//								if(indiv!=null){
//									m.setLastname(indiv.getLastname());
//									m.setFirstname(indiv.getFirstname());
//									m.setMiddlename(indiv.getMiddlename());
//									m.setSuffix(indiv.getSuffix());
//									m.setDateofbirth(indiv.getDateofbirth());
//									m.setNationality(indiv.getNationality());
//								}
//								m.setIsautogenerated(true);
//								m.setBusinesstype(corp.getBusinesstype());
//								m.setMngempid(emp.getMngempid());
//								m.setCustomertype(main.getCustomertype());
//								m.setDatecreated(new Date());
//								if(dbservice.save(m)){
//									flag = "success";
//									System.out.println(">>> SUCCESS SAVING AT MANAGEMENT.");
//									/***JAY.05-21-18*/
//									CustomerRelationshipService saveRelation = new CustomerRelationshipServiceImpl();
//									saveRelation.saveCustRelationshipCorp(m.getCifno(),m.getRelationshipcode(),m.getRelatedcifno());
//								
//								}
////								CustomerRelationshipService saveRelation = new CustomerRelationshipServiceImpl();
////								saveRelation.saveCustRelationshipCorp(m.getCifno(),m.getRelationshipcode(),m.getRelatedcifno());
//								else{
//									System.out.println(">>> ERROR!!!!! SAVING AT MANAGEMENT.");
//								}
//							}
//						}
//					}
//				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String updateIndividualCIF(Tbcifindividual indiv) {
		// TODO Auto-generated method stub
		String flag = null;
		DBService dbservice = new DBServiceImplCIF();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			param.put("cifno", indiv.getCifno());
			Tbcifindividual i = (Tbcifindividual) dbservice
					.executeUniqueHQLQuery("FROM Tbcifindividual WHERE cifno=:cifno", param);
			if (i != null) {
				// Basic Information
				i.setMiddlename(indiv.getMiddlename());
				i.setTitle(indiv.getTitle());
				i.setShortname(indiv.getShortname());
				i.setPreviouslastname(indiv.getPreviouslastname());
				i.setSuffix(indiv.getSuffix());
				i.setGender(indiv.getGender());
				i.setSexualpref(indiv.getSexualpref());
				i.setCivilstatus(indiv.getCivilstatus());
				i.setDateofbirth(indiv.getDateofbirth());
				i.setAge(indiv.getAge());
				i.setPlaceofbirth(indiv.getPlaceofbirth());
				i.setStateofbirth(indiv.getStateofbirth());
				i.setCountryofbirth(indiv.getCountryofbirth());
				i.setCityofbirth(indiv.getCityofbirth());
				i.setAcrnumber(indiv.getAcrnumber());
				i.setNationality(indiv.getNationality());
				i.setDualcitizen(indiv.getDualcitizen());
				i.setOthernationality(indiv.getOthernationality());
				i.setResident(indiv.getResident());
				i.setTin(indiv.getTin());
				// sss - gsis
				i.setSss(indiv.getSss());
				i.setGsis(indiv.getGsis());
				i.setReligion(indiv.getReligion());
				i.setCarsowned(indiv.getCarsowned());
				i.setIsqualifiedinvestor(indiv.getIsqualifiedinvestor());
				i.setSchoolpostgrad(indiv.getSchoolpostgrad());
				i.setCoursepostgrad(indiv.getCoursepostgrad());
				i.setSchoolcollege(indiv.getSchoolcollege());
				i.setCoursecollege(indiv.getCoursecollege());
				i.setSourceoffunds(indiv.getSourceoffunds());
				i.setOthersourceoffunds(indiv.getOthersourceoffunds());
				i.setAnnualincome(indiv.getAnnualincome());
				i.setAsset(indiv.getAsset());
				i.setNetworth(indiv.getNetworth());
//				i.setEmploymentstatus(indiv.getEmploymentstatus()); /** Added by Wel 092617 **/
				i.setUsplaceofbirth(indiv.getUsplaceofbirth());
				i.setUssocialsecurityno(indiv.getUssocialsecurityno());
				i.setUsstateofbirth(indiv.getUsstateofbirth());

				// Address 1
				i.setAddresstype1(indiv.getAddresstype1());
				i.setIssameaddress1(indiv.getIssameaddress1());
				i.setStreetno1(indiv.getStreetno1());
				i.setSubdivision1(indiv.getSubdivision1());
				i.setCountry1(indiv.getCountry1());
				i.setProvince1(indiv.getProvince1());
				i.setCity1(indiv.getCity1());
				i.setBarangay1(indiv.getBarangay1());
				i.setPostalcode1(indiv.getPostalcode1());
				i.setHomeownership1(indiv.getHomeownership1());
				i.setOtherhomeownership1(indiv.getOtherhomeownership1());
				i.setOccupiedsince1(indiv.getOccupiedsince1());
				i.setCommunitiestype1(indiv.getCommunitiestype1());
				i.setFulladdress1(fullAddress(indiv.getStreetno1(), indiv.getSubdivision1(), indiv.getBarangay1(),
						indiv.getCity1(), indiv.getProvince1(), indiv.getCountry1(), indiv.getPostalcode1()));
				// --postalcodename
				i.setPostalcodename2(indiv.getPostalcodename2());
				i.setPostalcodename1(indiv.getPostalcodename1());
				// Address 2
				i.setAddresstype2(indiv.getAddresstype2());
				i.setIssameaddress2(indiv.getIssameaddress2());
				i.setStreetno2(indiv.getStreetno2());
				i.setSubdivision2(indiv.getSubdivision2());
				i.setCountry2(indiv.getCountry2());
				i.setProvince2(indiv.getProvince2());
				i.setCity2(indiv.getCity2());
				i.setBarangay2(indiv.getBarangay2());
				i.setPostalcode2(indiv.getPostalcode2());
				i.setHomeownership2(indiv.getHomeownership2());
				i.setOtherhomeownership2(indiv.getOtherhomeownership2());
				i.setOccupiedsince2(indiv.getOccupiedsince2());
				i.setCommunitiestype2(indiv.getCommunitiestype2());
				i.setFulladdress2(fullAddress(indiv.getStreetno2(), indiv.getSubdivision2(), indiv.getBarangay2(),
						indiv.getCity2(), indiv.getProvince2(), indiv.getCountry2(), indiv.getPostalcode2()));

				// true - use permanent else present
				i.setIsmailingaddress(indiv.getIsmailingaddress());

				// Contact Details
				i.setAreacodemobile(indiv.getAreacodemobile());
				i.setCountrycodemobile(indiv.getCountrycodemobile());
				i.setMobilenumber(indiv.getMobilenumber());
				i.setAreacodephone(indiv.getAreacodephone());
				i.setCountrycodephone(indiv.getCountrycodephone());
				i.setHomephoneno(indiv.getHomephoneno());
				i.setAreacodefax(indiv.getAreacodefax());
				i.setCountrycodefax(indiv.getCountrycodefax());
				i.setFaxnumber(indiv.getFaxnumber());
				i.setEmailaddress(indiv.getEmailaddress());
				i.setContacttype1(indiv.getContacttype1());
				i.setContactvalue1(indiv.getContactvalue1());
				i.setContacttype2(indiv.getContacttype2());
				i.setContactvalue2(indiv.getContactvalue2());

				// Spouse & Parent Details

				i.setSpousetitle(indiv.getSpousetitle());
				i.setSpouselastname(indiv.getSpouselastname());
				i.setSpousefirstname(indiv.getSpousefirstname());
				i.setSpousemiddlename(indiv.getSpousemiddlename());
				i.setSpousesuffix(indiv.getSpousesuffix());
				i.setSpousedateofbirth(indiv.getSpousedateofbirth());
				i.setSpousecifno(indiv.getSpousecifno());

				i.setFathertitle(indiv.getFathertitle());
				i.setFatherlastname(indiv.getFatherlastname());
				i.setFatherfirstname(indiv.getFatherfirstname());
				i.setFathermiddlename(indiv.getFathermiddlename());
				i.setFathersuffix(indiv.getFathersuffix());
				i.setFatherdateofbirth(indiv.getFatherdateofbirth());
				i.setFathercifno(indiv.getFathercifno());

				i.setMothertitle(indiv.getMothertitle());
				i.setMotherlastname(indiv.getMotherlastname());
				i.setMotherfirstname(indiv.getMotherfirstname());
				i.setMothermiddlename(indiv.getMothermiddlename());
				i.setMothersuffix(indiv.getMothersuffix());
				i.setMotherdateofbirth(indiv.getMotherdateofbirth());
				i.setMothercifno(indiv.getMothercifno());
				i.setCifgroupcode(indiv.getCifgroupcode());
				i.setMothermaidenmname(indiv.getMothermaidenmname());

				i.setBeneficiarytitle(indiv.getBeneficiarytitle());
				i.setBeneficiarylastname(indiv.getBeneficiarylastname());
				i.setBeneficiaryfirstname(indiv.getBeneficiaryfirstname());
				i.setBeneficiarymiddlename(indiv.getBeneficiarymiddlename());
				i.setBeneficiarysuffix(indiv.getBeneficiarysuffix());
				i.setBeneficiarydateofbirth(indiv.getBeneficiarydateofbirth());
				i.setBeneficiarycifno(indiv.getBeneficiarycifno());
				i.setRelationtoborrower(indiv.getRelationtoborrower());

				i.setIndivoccupation(indiv.getIndivoccupation());
				i.setResidenceclassification(indiv.getResidenceclassification());

				// Membership Info
				i.setMembershiptype(indiv.getMembershiptype());
				i.setDatemembership(indiv.getDatemembership());
				i.setReferredbycifno(indiv.getReferredbycifno());
				i.setReferredbycifname(indiv.getReferredbycifname());
				i.setRemarks(indiv.getRemarks());

			}
			if (dbservice.saveOrUpdate(i)) {
				Tbcifmain m = (Tbcifmain) dbservice.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", param);
				if (i.getDateofbirth() != null) {
					m.setDateofbirth(i.getDateofbirth());
				}
				if (indiv.getFulladdress1() != null) {
					m.setFulladdress1(i.getFulladdress1());
				}
				if (indiv.getFulladdress2() != null) {
					m.setFulladdress2(i.getFulladdress2());
				}
				Tbcifindividual in = (Tbcifindividual) dbservice
						.executeUniqueHQLQuery("FROM Tbcifindividual WHERE cifno=:cifno", param);
				// suffix
				String suf = "";
				String middlename = "";
				if (indiv.getSuffix() != null && !indiv.getSuffix().equals("")) {
					suf = indiv.getSuffix();
				}
				// middle name
				if (indiv.getMiddlename() != null && !indiv.getMiddlename().equals("")) {
					middlename = indiv.getMiddlename();
				}

				m.setFullname(in.getLastname() + ", " + in.getFirstname() + " " + suf + " " + middlename);

				// 02.02.2023
				m.setFullname(m.getFullname().trim().replaceAll(" +", " "));

				dbservice.saveOrUpdate(m);

//				// 01.30.2023
//				String fullname = m.getFullname();
//				// UPDATE DEPOSIT ACCOUNTS NAME
//				dbServiceCOOP.executeUpdate(
//						"update TBDEPOSITCIF set cifname = " + "'" + fullname + "'" + " where cifno=:cifno", param);
//				dbServiceCOOP.executeUpdate(
//						"update a set a.AccountName =" + "'" + fullname + "'" + " " + " from  TBDEPOSIT a "
//								+ " left join TBDEPOSITCIF b ON a.AccountNo = b.accountno" + " where b.cifno=:cifno",
//						param);
//				// UPDATE LOANS ACCOUNTS NAME
//				dbServiceCOOP.executeUpdate(
//						"update TBLOANS set fullname = " + "'" + fullname + "'" + " where principalno=:cifno", param);
//				dbServiceCOOP.executeUpdate(
//						"update TBACCOUNTINFO set name = " + "'" + fullname + "'" + " where clientid=:cifno", param);
//				dbServiceCOOP.executeUpdate(
//						"update TBLSTAPP set cifname = " + "'" + fullname + "'" + " where cifno=:cifno", param);
//
//				HistoryService h = new HistoryServiceImpl();
//				h.addHistory(i.getCifno(), "Saved as Draft.", null);
				flag = "success";
			} else {
				flag = "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public FormValidation validateDataEntry(String cifno) {
		DBService dbService = new DBServiceImplCIF();
		DBService dbServiceLOS = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		FormValidation form = new FormValidation();
		StringBuilder errorMessage = new StringBuilder();
		String flag = "success";
		boolean isNull = false;
		String custtype = "";
		try {
			param.put("cifno", cifno);

			custtype = (String) dbService.executeUniqueSQLQuery("SELECT customertype FROM Tbcifmain WHERE cifno=:cifno",
					param);
			if (custtype == null) {
				custtype = (String) dbServiceLOS
						.executeUniqueSQLQuery("SELECT customertype FROM Tblstapp WHERE appno=:cifno", param);
			}
			// Individual
			if (custtype.equals("2")) {
				Tbcifindividual info = new Tbcifindividual();
				info = (Tbcifindividual) dbService.executeUniqueHQLQuery("FROM Tbcifindividual WHERE cifno=:cifno",
						param);
				if (info == null) {
					info = (Tbcifindividual) dbServiceLOS.execStoredProc(
							"SELECT * FROM Tblstappindividual WHERE appno=:cifno", param, Tbcifindividual.class, 0,
							null);
				}

				// Checking for all valid field only
				boolean isValid = true;
//					if(info.getShortname() != null){
//						if(!RegexUtil.name(info.getShortname())){
//							isValid = false;
//						}
//					}
//					if(info.getPreviouslastname() != null){
//						if(!RegexUtil.name(info.getPreviouslastname())){
//							isValid = false;
//						}
//					}
//					if(info.getAcrnumber() != null){
//						if(!RegexUtil.numbersWithDash(info.getAcrnumber())){
//							isValid = false;
//						}
//					}
//					if(info.getCountryofbirth() != null && info.getCountryofbirth().equals("US")){
//						if(info.getUssocialsecurityno() != null){
//							if(!RegexUtil.numbersWithDash(info.getUssocialsecurityno())){
//								isValid = false;
//							}
//						}
//					}
//					if(info.getTin() != null){
//						if(!RegexUtil.numbersWithDash(info.getTin())){
//							isValid = false;
//						}
//					}
//					if(info.getMiddlename() != null){
//						if(!RegexUtil.name(info.getMiddlename())){
//							isValid = false;
//						}
//					}
//					if(info.getSuffix() != null && !info.getSuffix().equals("")){
//						if(!RegexUtil.name(info.getSuffix())){
//							isValid = false;
//						}
//					}
//					if(info.getCountrycodemobile() != null){
//						if(!RegexUtil.numbersOnly(info.getCountrycodemobile())){
//							isValid = false;
//						}
//					}
//					if(info.getAreacodemobile() != null){
//						if(!RegexUtil.numbersOnly(info.getAreacodemobile())){
//							isValid = false;
//						}
//					}
				if (info.getMobilenumber() != null) {
					if (!RegexUtil.numbersOnly(info.getMobilenumber())) {
						isValid = false;
					}
				} else {
					isValid = false;
				}
//					if(info.getCountrycodephone() != null){
//						if(!RegexUtil.numbersOnly(info.getCountrycodephone())){
//							isValid = false;
//						}
//					}
//					if(info.getAreacodephone() != null){
//						if(!RegexUtil.numbersOnly(info.getAreacodephone())){
//							isValid = false;
//						}
//					}
//					if(info.getHomephoneno() != null){
//						if(!RegexUtil.numbersOnly(info.getHomephoneno())){
//							isValid = false;
//						}
//					}
//					if(info.getCountrycodefax() != null){
//						if(!RegexUtil.numbersOnly(info.getCountrycodefax())){
//							isValid = false;
//						}
//					}
//					if(info.getAreacodefax() != null){
//						if(!RegexUtil.numbersOnly(info.getAreacodefax())){
//							isValid = false;
//						}
//					}
//					if(info.getFaxnumber() != null){
//						if(!RegexUtil.numbersOnly(info.getFaxnumber())){
//							isValid = false;
//						}
//					}
				if (!isValid) {
//						isNull = true;
//						errorMessage.append("<i>Please make sure to put valid data only.</i>");
					errorMessage.append("<br/>");
					errorMessage.append("<b>Missing required field(s):</b> Contact Details tab");
					isNull = true;
				}
				// End of Checking valid field

				// ----------------- validating required fields -----------------------//
				// basic information tab

				boolean basic = false;
				
				if (info.getGender() == null 
						|| info.getCivilstatus() == null
						|| info.getDateofbirth() == null // CED ADDED REQUIRED FIELD DATE OF BIRTH 08042022
//Renz
//							|| info.getNationality() == null || info.getSourceoffunds() == null
//							|| info.getAnnualincome() == null || info.getAsset() == null
//							|| info.getNetworth() == null
				/* || info.getEmploymentstatus() == null */) 
				{ /** Added by Wel 092617 **/// removed 10-11
					basic = true;
				}

				/*if (info.getDualcitizen() != null) {
					if (info.getDualcitizen()) {
						if (info.getOthernationality() == null) {
							basic = true;
						}
					}
				}*/
				// Renz
//					if (info.getSourceoffunds() != null) {
//						if (info.getSourceoffunds().equals("10")) {
//							if (info.getOthersourceoffunds() == null) {
//								basic = true;
//							}
//						}
//					}
				/*if (info.getCountryofbirth() != null) {
					if (info.getCountryofbirth() == "PH") {
						if (info.getPlaceofbirth() == null || info.getCityofbirth() == null) {
							basic = true;
						}
					} else {

					}
				}*/

				/*
				 * if (info.getTin() == null || info.getTin().equals("") ||
				 * info.getTin().equals("--")) { basic = true; } else { param.put("tin",
				 * info.getTin()); String cifnoTIN = (String) dbService.executeUniqueHQLQuery(
				 * "SELECT cifno FROM Tbcifindividual WHERE tin=:tin and cifno!=:cifno", param);
				 * if (cifnoTIN != null) { errorMessage.append("<br/>");
				 * errorMessage.append("<b>With duplicate TIN record under " + cifnoTIN); isNull
				 * = true; } }
				 */

				// MAR
//				if (info.getTin() == null || info.getTin().equals("") || info.getTin().equals("--")) {
//					basic = true;
//				} else {
//					param.put("tin", info.getTin());
//					String cifnoTINIndiv = (String) dbService.executeUniqueHQLQuery(
//							"SELECT cifno FROM Tbcifindividual WHERE tin=:tin and cifno!=:cifno", param);
//					String cifnoTINCorp = (String) dbService.executeUniqueHQLQuery(
//							"SELECT cifno FROM Tbcifcorporate WHERE tin=:tin and cifno!=:cifno", param);
//					if (cifnoTINIndiv != null) {
//						errorMessage.append("<br/>");
//						errorMessage.append("<b>With duplicate TIN record under " + cifnoTINIndiv);
//						isNull = true;
//					} else if (cifnoTINCorp != null) {
//						errorMessage.append("<br/>");
//						errorMessage.append("<b>With duplicate TIN record under " + cifnoTINCorp);
//						isNull = true;
//					}
//				}

				if (basic) {
					errorMessage.append("<br/>");
					errorMessage.append("<b>Missing required field(s):</b> Basic Details tab");
					isNull = true;
				}

				// Ride QIB - Added by Wel July 18, 2017
				/*if (info.getIsqualifiedinvestor() != null && info.getIsqualifiedinvestor()) {
					QIBFacade qib = new QIBFacade();
					FormValidation f = qib.validateQIBInfo(info.getCifno());
					if (f.getFlag().equals("failed")) {
						errorMessage.append(f.getErrorMessage());
						isNull = true;
					}
				}*/
				// employment details

//					List<Tbcifemployment> em = getListEmployment(cifno);
//					if(em == null || em.isEmpty()){
//						errorMessage.append("<br/>");
//						errorMessage.append("<b>Missing required field(s):</b> No employment information");
//						isNull = true;
//					}
				// Aug 31, 2018
				List<Tbcifbusiness> bus = getListBusiness(cifno);
				if (bus != null && !bus.isEmpty()) {
//					Integer res = (Integer) dbService.executeUniqueSQLQuery(
//							"SELECT COUNT(*) FROM Tbcifbusiness WHERE cifno=:cifno AND (genmanager = '' OR genmanager IS NULL) AND (gmcif = '' OR gmcif IS NULL)",
//							param);
//					if (res != null && res > 0) {
//						errorMessage.append("<br/>");
//						errorMessage.append("<b>Missing General Manager:</b> Business Details tab");
//						isNull = true;
//					}
//						System.out.println("---------- BUSINESS LIST NOT EMPTY !!!");
//						for(Tbcifbusiness bus2 : bus){
//							if(bus2.getBuscifno()!=null){
//								Map<String, Object> params = HQLUtil.getMap();
//								params.put("c", bus2.getBuscifno()); 
//								Integer res = (Integer) dbService.executeUniqueSQLQuery
//										("SELECT COUNT(*) FROM Tbmanagement WHERE cifno=:c AND relationshipcode = 'GM'", params);
//								if(res!=null && res>0){
//									errorMessage.append("<br/>");
//									errorMessage.append("<b>Missing General Manager:</b> Business Details tab");
//									isNull = true;
//								}
//							}
//						}
				}

				// contact details Removed by CED 07212022
//				if (info.getMobilenumber() == null || info.getHomephoneno() == null) {
//					errorMessage.append("<br/>");
//					errorMessage.append("<b>Missing required field(s):</b> Contact Details tab");
//					isNull = true;
//				}
				if (info.getContacttype1() != null) {
					if (info.getContactvalue1() == null) {
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Contact Details tab");
						isNull = true;
					}
				}
				if (info.getContacttype2() != null) {
					if (info.getContactvalue2() == null) {
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Contact Details tab");
						isNull = true;
					}
				}
				boolean address = false;
				// Address1 Address2
				if (info.getCountry1() != null) {
					if (info.getCountry1().equals("PH")) {
						if (info.getProvince1() == null || info.getProvince1().equals("") || info.getCity1() == null
								|| info.getCity1().equals("")) {
							address = true;
						}
					}
				}

				if (info.getCountry2() != null) {
					if (info.getCountry1().equals("PH")) {
						if (info.getProvince2() == null || info.getProvince2().equals("") || info.getCity2() == null
								|| info.getCity2().equals("")) {
							address = true;
						}
					}
				}

				// Address details

				if (/* info.getAddresstype1() == null || */
				info.getStreetno1() == null || info.getStreetno1().equals("") || info.getCountry1() == null
						|| info.getCountry1().equals("") || info.getHomeownership1() == null ||
						/* info.getAddresstype2() == null|| */
						info.getStreetno2() == null || info.getStreetno2().equals("") || info.getCountry2() == null
						|| info.getCountry2().equals("") || info.getHomeownership2() == null || address) {
					errorMessage.append("<br/>");
					errorMessage.append("<b>Missing required field(s):</b> Address Details tab");
					isNull = true;
				}

//					// Spouse & Parent Details
//					boolean spd = false;
//					if (info.getCivilstatus() != null) {
//						if (info.getCivilstatus().equals("2") || info.getCivilstatus().equals("4")) {
//							if (info.getSpousetitle() == null || info.getSpouselastname() == null
//									|| info.getSpousefirstname() == null) {
//								spd = true;
//							}
//						}
//					}
//					// Mother
//					if (info.getMotherlastname() == null || info.getMotherfirstname() == null
//							|| info.getMothermiddlename() == null
//							|| (info.getMotherlastname() != null && info.getMotherlastname().equals("")) 
//							|| (info.getMotherfirstname() != null && info.getMotherfirstname().equals(""))
//							|| (info.getMothermiddlename() != null && info.getMothermiddlename().equals(""))) {
//						spd = true;
//					}
//					if (spd) {
//						isNull = true;
//						errorMessage.append("<br/>");
//						errorMessage.append("<b>Missing required field(s):</b> Parental / Spouse Information tab");
//					}

				if (isNull) {
					flag = "failed";
				}
				form.setFlag(flag);
				form.setErrorMessage(errorMessage.toString());
			}
			// corporate
			else if (custtype.equals("1")) {
				Tbcifcorporate c = new Tbcifcorporate();

				c = (Tbcifcorporate) dbService.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno=:cifno", param);
				if (c == null) {
					c = (Tbcifcorporate) dbServiceLOS.execStoredProc(
							"SELECT * FROM Tblstappcorporate WHERE appno=:cifno", param, Tbcifcorporate.class, 0, null);
				}
				if (c != null) {
					// Basic Info

					if (
							c.getTradename() == null 
							|| c.getDateofincorporation() == null 
							//|| c.getFirmsize() == null
							//|| c.getNationality() == null 
							|| c.getNoofemployees() == null
							//|| c.getMonthlyincomesale() == null 
							|| c.getRegistrationtype() == null
							|| c.getRegistrationno() == null
							// || c.getPsiccode() == null
							//|| c.getBusinesstype() == null 
							//|| c.getTin() == null
							) {
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Basic Information tab");
						isNull = true;

					}

					// MAR
//					if (c.getTin() == null || c.getTin().equals("") || c.getTin().equals("--")) {
//						errorMessage.append("<br/>");
//						errorMessage.append("<b>Missing required field(s):</b> Basic Information tab");
//					} else {
//						param.put("tin", c.getTin());
//						String cifnoTINIndiv = (String) dbService.executeUniqueHQLQuery(
//								"SELECT cifno FROM Tbcifindividual WHERE tin=:tin and cifno!=:cifno", param);
//						String cifnoTINCorp = (String) dbService.executeUniqueHQLQuery(
//								"SELECT cifno FROM Tbcifcorporate WHERE tin=:tin and cifno!=:cifno", param);
//						if (cifnoTINIndiv != null) {
//							errorMessage.append("<br/>");
//							errorMessage.append("<b>With duplicate TIN record under " + cifnoTINIndiv);
//							isNull = true;
//						} else if (cifnoTINCorp != null) {
//							errorMessage.append("<br/>");
//							errorMessage.append("<b>With duplicate TIN record under " + cifnoTINCorp);
//							isNull = true;
//						}
//					}

					// if Registration type = DTI
					if (c.getRegistrationtype() != null && c.getRegistrationtype().equals("1")) {
						if (c.getExpirydate1() == null && isNull == false) {
							errorMessage.append("<br/>");
							errorMessage.append("<b>Missing required field(s):</b> Basic Information tab");
							isNull = true;
						}
					}

					// Contact details
					if (c.getMobilenumber() == null || c.getHomephoneno() == null) {
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Contact Details tab");
						isNull = true;
					}
					if (c.getContacttype1() != null) {
						if (c.getContactvalue1() == null) {
							errorMessage.append("<br/>");
							errorMessage.append("<b>Missing required field(s):</b> Contact Details tab");
							isNull = true;
						}
					}
					if (c.getContacttype2() != null) {
						if (c.getContactvalue2() == null) {
							errorMessage.append("<br/>");
							errorMessage.append("<b>Missing required field(s):</b> Contact Details tab");
							isNull = true;
						}
					}
					// Address details
					if (c.getAddresstype1() == null || c.getStreetno1() == null || c.getStreetno1().equals("")
							|| c.getCountry1() == null || c.getCountry1().equals("") || c.getProvince1() == null
							|| c.getProvince1().equals("") || c.getCity1() == null || c.getCity1().equals("")
							|| c.getHomeownership1() == null)

					// 07-08-2021 MAR
					// || c.getAddresstype2() == null || c.getStreetno2() == null
					// || c.getStreetno2().equals("") || c.getCountry2() == null ||
					// c.getCountry2().equals("")
					// || c.getProvince2() == null || c.getProvince2().equals("") || c.getCity2() ==
					// null
					// || c.getCity2().equals("") || c.getHomeownership2() == null)
					{
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Address Details tab");
						isNull = true;
					}

					// Ride QIB - Added by Wel July 18, 2017
					if (c.getIsqualifiedinvestor() != null && c.getIsqualifiedinvestor()) {
						QIBFacade qib = new QIBFacade();
						FormValidation f = qib.validateQIBInfo(c.getCifno());
						if (f.getFlag().equals("failed")) {
							errorMessage.append(f.getErrorMessage());
							isNull = true;
						}
					}
					// Management Details
					//if (c.getBusinesstype() == null) {
					//	errorMessage.append("<br/>");
					//	errorMessage.append("<b>Missing required field(s):</b> Management Details tab");
					//	isNull = true;
					//} else {
					//
					//}

					if (isNull) {
						flag = "failed";
						form.setFlag(flag);
					}
					form.setFlag(flag);
					form.setErrorMessage(errorMessage.toString());
				}
			}
//			}
			// System.out.println(">>>>>>>>>flag: "+form.getFlag());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return form;
	}

//no dedupe commented by renz
//	@Override
//	public String deleteTradeRef(String cifno, String tradecifno, String tradetype, String cif) {
//		// TODO Auto-generated method stub
//		String flag = "failed";
//		DBService dbService = new DBServiceImplCIF();
//		Map<String, Object> params = HQLUtil.getMap();
//		params.put("cifno", cif);
//		params.put("tradecifno", tradecifno);
//		params.put("tradetype", tradetype);
//		System.out.println("cifno: " +cif+ " tradecifno: " +tradecifno+ " tradetype: " +tradetype);
//		Tbtradereference row = new Tbtradereference();
//		try {
//			row = (Tbtradereference) dbService
//					.executeUniqueHQLQuery("FROM Tbtradereference WHERE id.cifno=:cifno AND id.tradecifno=:tradecifno AND id.tradetype=:tradetype", params);
//			/**JAY.05-23-18**/
//			params.put("cifno", row.getId().getCifno());
//			Tbcustomerrelationship custrel = (Tbcustomerrelationship)dbService.executeUniqueHQLQuery("FROM Tbcustomerrelationship "
//					+ "WHERE maincifno=:cifno AND relatedcifno=:tradecifno AND mngempid=:cifno", params); 
//			
//			HistoryService h = new HistoryServiceImpl();
//			h.addHistory(row.getId().getTradecifno(), "Deleted trade reference.", null);
//			if (dbService.delete(row)) {
//				
//				flag = "success";
//				CustomerRelationshipService delRel = new CustomerRelationshipServiceImpl();
//				delRel.deleteRelCorp(custrel.getMaincifno(), custrel.getRelationshipcode(), custrel.getRelatedcifno(), custrel.getMngempid());
//				System.out.println("Deleted Trade Reference and TradeRef Relationship");
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbtradereference> getTradeRefList(String cifno, String tradetype) {
		// TODO Auto-generated method stub
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		List<Tbtradereference> list = new ArrayList<Tbtradereference>();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				param.put("tradetype", tradetype);
				list = (List<Tbtradereference>) dbService.executeListHQLQuery(
						"FROM Tbtradereference WHERE id.cifno=:cifno AND id.tradetype=:tradetype", param);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

//no dedupe commented by renz	
//	@Override
//	public String saveTradeRef(Tbtradereference data) {
//		// TODO Auto-generated method stub
//		String flag = "failed";
//		DBService dbService = new DBServiceImplCIF();
//		Map<String, Object> params = HQLUtil.getMap();
//		HistoryService h = new HistoryServiceImpl();
//		Tbtradereference tr = new Tbtradereference();
////		TbtradereferenceId id = new TbtradereferenceId();
//		System.out.println("cifno traderef: " +data.getId().getCifno());
//		System.out.println("tradetype: " +data.getId().getTradetype());
//		System.out.println("tradecifno: " +data.getId().getTradecifno());
//		try {
//			//JAY 12-6-18
////			System.out.println("cifno traderef: " +data.getId().getCifno());
//			if(data.getId().getCifno() != null){
//				System.out.println("test");
//				params.put("cifno", data.getId().getCifno());
//				params.put("tradecifno", data.getId().getTradecifno());
//				params.put("tradetype", data.getId().getTradetype());
//				tr = (Tbtradereference) dbService.executeUniqueHQLQuery(
//						"FROM Tbtradereference WHERE id.cifno=:cifno AND id.tradecifno=:tradecifno AND id.tradetype=:tradetype",
//						params);
//
//				 if(tr == null){
//					tr = data;
////					tr.setLandline1(data.getLandline1());
////					tr.setLandline2(data.getLandline2());
////					tr.setClientsupplieraddress(data.getClientsupplieraddress());
////					tr.setClientsuppliername(data.getClientsuppliername());
////					tr.setEmailaddress(data.getEmailaddress());
////					tr.setMobile1(data.getMobile1());
////					tr.setContactperson(data.getContactperson());
////					tr.setContactposition(data.getContactposition());
////					tr.setDepartmentconnected(data.getDepartmentconnected());
////					tr.setProductservicesoffered(data.getProductservicesoffered());
////					tr.setAvebillingamtpertrans(data.getAvebillingamtpertrans());
////					tr.setLengthofbusinessrelations(data.getLengthofbusinessrelations());
////					tr.setMobile2(data.getMobile2());
////					tr.setTradetype(data.getTradetype());
////					tr.setTradecifno(data.getTradecifno());
////					tr.setCustomertype(data.getCustomertype());
////					tr.setAccreditationstatus(data.getAccreditationstatus());
//					if (dbService.save(data)) {
//						flag = "success";
//						params.put("trade", data.getId().getTradetype());
//						Tbcodetable c = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='TRADETYPE' AND a.id.codevalue=:trade", params);
//						if(c != null){
//							h.addHistory(data.getId().getCifno(), "Save trade reference: <b>"+c.getDesc1(), null);
//						}
//					}				
//				}
//				else{
//					flag = "exist";
//				}
////				else{
////					if (dbService.save(data)) {
////						flag = "success";
////						params.put("trade", data.getTradetype());
////						Tbcodetable c = (Tbcodetable) dbService.executeUniqueHQLQuery("FROM Tbcodetable a WHERE a.id.codename ='TRADETYPE' AND a.id.codevalue=:trade", params);
////						if(c != null){
////							h.addHistory(data.getCifno(), "Added trade reference: <b>"+c.getDesc1(), null);
////						}
////					}					
////				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
//	}
//	

	@Override
	public String updateCIFMain(Tbcifmain main, String filepath) {
		String flag = null;
		DBService dbservice = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();

		try {
			param.put("cifno", main.getCifno());
			Tbcifmain m = (Tbcifmain) dbservice.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", param);
			if (m != null) {
				m.setOriginatingbranch(main.getOriginatingbranch());
				m.setBorrowerfundertype(main.getBorrowerfundertype());
				m.setCifpurpose(main.getCifpurpose());
				m.setCifgroupcode(main.getCifgroupcode());
				m.setTaxexempted(main.getTaxexempted());
				m.setReferrorposition(main.getReferrorposition());
				m.setDeclaredmaxvalueoftx(main.getDeclaredmaxvalueoftx());
				m.setClienttype(main.getClienttype());
				m.setReferrorname(main.getReferrorname());
				m.setDeceasedflag(main.getDeceasedflag());
				m.setReferraltype(main.getReferraltype());
				m.setRiskrating(main.getRiskrating());
				m.setCollectorareacode(main.getCollectorareacode());
				m.setCollectorsubareacode(main.getCollectorsubareacode());

				System.out.println(filepath);
				if (filepath != null) {
					m.setProfpiccode(ImageUtils.pdfToBase64(filepath));
					System.out.println("IN");
				}
				if (dbservice.saveOrUpdate(m)) {
					flag = "success";
				} else {
					flag = "failed";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return flag;
	}

	public static String fullAddress(String streetNumber, String subdivision, String barangay, String city,
			String province, String country, String postalCode) {
		DBService dbService = new DBServiceImplCIF();
		StringBuilder b = new StringBuilder();
		if (streetNumber != null && !streetNumber.equals("")) {
			b.append(streetNumber + " ");
		}
		if (subdivision != null && !subdivision.equals("")) {
			b.append(subdivision + ", ");
		}
		if (barangay != null && !barangay.equals("")) {
			b.append(barangay + ", ");
		}
		if (city != null && !city.equals("")) {
			b.append(city + ", ");
		}
		if (province != null && !province.equals("")) {
			b.append(province + ", ");
		}
		if (country != null && !country.equals("")) {
			country = (String) dbService
					.executeUniqueSQLQuery("SELECT DISTINCT country FROM Tbcountry WHERE code='" + country + "'", null);
			b.append(country + " ");
		}
		if (postalCode != null && !postalCode.equals("")) {
			b.append(postalCode);
		}
		return b.toString();
	}
//no dedupe commented by renz
//	@Override
//	public String updateTradeRef(Tbtradereference data) {
//		// TODO Auto-generated method stub
//		DBService dbservice = new DBServiceImplCIF();
//		Map<String,Object> params = HQLUtil.getMap();
//		HistoryService h = new HistoryServiceImpl();
//		String flag ="Failed";
//		try {
//			params.put("cifno", data.getId().getCifno());
//			Tbtradereference traderef = (Tbtradereference) dbservice
//					.executeUniqueHQLQuery("FROM Tbtradereference WHERE id.cifno=:cifno", params);
//			if (traderef != null) {
//				data.getId().setCifno(traderef.getId().getCifno());
//			}
//			if (dbservice.saveOrUpdate(data)) {
//				flag = "Success";
//				params.put("trade", data.getId().getTradetype());
//				Tbcodetable c = (Tbcodetable) dbservice.executeUniqueHQLQuery(
//						"FROM Tbcodetable a WHERE a.id.codename ='TRADETYPE' AND a.id.codevalue=:trade", params);
//				if (c != null) {
//					h.addHistory(data.getId().getCifno(), "Save trade reference: <b>" + c.getDesc1(), null);
//				}
//
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return flag;
//	}

	@Override
	public String changeAccreditationStat(String cifno, String tradecifno, String tradetype) {
		// TODO Auto-generated method stub
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		Tbtradereference trade = new Tbtradereference();
		String flag = "failed";
		try {
			params.put("cifno", cifno);
			params.put("tradecifno", tradecifno);
			params.put("tradetype", tradetype);
			if (cifno != null) {

				trade = (Tbtradereference) dbService.executeUniqueHQLQuery(
						"FROM Tbtradereference WHERE id.cifno=:cifno AND id.tradecifno=:tradecifno AND id.tradetype=:tradetype",
						params);

				if (trade != null) {
					if (trade.getAccreditationstatus().equals("0")) {
						trade.setAccreditationstatus("1");
//						trade.setAccreditationdate(new Date());
						if (dbService.saveOrUpdate(trade)) {
							flag = "success";
						}
					} else {
						trade.setAccreditationstatus("0");
//						trade.setAccreditationdate(new Date());
						if (dbService.saveOrUpdate(trade)) {
							flag = "success";
						}
					}
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tbtradereference getTRDetails(String cifno, String tradecifno, String tradetype) {
		// TODO Auto-generated method stub
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		Tbtradereference details = new Tbtradereference();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				param.put("tradecifno", tradecifno);
				param.put("tradetype", tradetype);
				details = (Tbtradereference) dbService.executeUniqueHQLQuery(
						"FROM Tbtradereference WHERE id.cifno=:cifno AND id.tradecifno=:tradecifno AND id.tradetype=:tradetype",
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
	public String updateCorporateCIF(Tbcifcorporate corp) {
		DBService dbservice = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		String flag = null;
		try {
			System.out.println("start..");
			param.put("cifno", corp.getCifno());
			Tbcifcorporate c = (Tbcifcorporate) dbservice
					.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno=:cifno", param);
			if (c != null) {
				// Basic Information
				c.setDateofincorporation(corp.getDateofincorporation());
				c.setTradename(corp.getTradename());
				c.setFirmsize(corp.getFirmsize());
				c.setNationality(corp.getNationality());
				c.setIsresident(corp.getIsresident());
				c.setNoofemployees(corp.getNoofemployees());
				c.setMonthlyincomesale(corp.getMonthlyincomesale());
				c.setRegistrationtype(corp.getRegistrationtype());
				c.setTin(corp.getTin());
				// sss
				c.setSss(corp.getSss());
				c.setTermofexistence(corp.getTermofexistence());
				c.setLessee(corp.getLessee());
				c.setBusinesstype(corp.getBusinesstype());
				c.setIsqualifiedinvestor(corp.getIsqualifiedinvestor());
				c.setLegalform(corp.getLegalform());
				c.setPsic(corp.getPsic());
				c.setGrossincome(corp.getGrossincome());
				c.setNettaxableincome(corp.getNettaxableincome());
				c.setMonthlyexpenses(corp.getMonthlyexpenses());
				c.setRegistrationno(corp.getRegistrationno());
				// Address 1
				c.setAddresstype1(corp.getAddresstype1());
				c.setStreetno1(corp.getStreetno1());
				c.setSubdivision1(corp.getSubdivision1());
				c.setCountry1(corp.getCountry1());
				c.setProvince1(corp.getProvince1());
				c.setCity1(corp.getCity1());
				c.setBarangay1(corp.getBarangay1());
				c.setPostalcode1(corp.getPostalcode1());
				c.setHomeownership1(corp.getHomeownership1());
				c.setOtherhomeownership1(corp.getOtherhomeownership1());
				c.setOccupiedsince1(corp.getOccupiedsince1());
				c.setFulladdress1(fullAddress(corp.getStreetno1(), corp.getSubdivision1(), corp.getBarangay1(),
						corp.getCity1(), corp.getProvince1(), corp.getCountry1(), corp.getPostalcode1()));
				c.setPostalcodename1(corp.getPostalcodename1());

				// Address 2
				c.setAddresstype2(corp.getAddresstype2());
				c.setStreetno2(corp.getStreetno2());
				c.setSubdivision2(corp.getSubdivision2());
				c.setCountry2(corp.getCountry2());
				c.setProvince2(corp.getProvince2());
				c.setCity2(corp.getCity2());
				c.setBarangay2(corp.getBarangay2());
				c.setPostalcode2(corp.getPostalcode2());
				// --postalcodename2
				c.setPostalcodename2(corp.getPostalcodename2());
				c.setHomeownership2(corp.getHomeownership2());
				c.setOtherhomeownership2(corp.getOtherhomeownership2());
				c.setOccupiedsince2(corp.getOccupiedsince2());
				c.setFulladdress2(fullAddress(corp.getStreetno2(), corp.getSubdivision2(), corp.getBarangay2(),
						corp.getCity2(), corp.getProvince2(), corp.getCountry2(), corp.getPostalcode2()));
				c.setIssameaddress1(corp.getIssameaddress1());

				// true - use permanent else present
				c.setIsmailingaddress(corp.getIsmailingaddress());

				// Contact
				c.setCountrycodemobile(corp.getCountrycodemobile());
				c.setAreacodemobile(corp.getAreacodemobile());
				c.setMobilenumber(corp.getMobilenumber());
				c.setCountrycodephone(corp.getCountrycodephone());
				c.setAreacodephone(corp.getAreacodephone());
				c.setHomephoneno(corp.getHomephoneno());
				c.setCountrycodefax(corp.getCountrycodefax());
				c.setAreacodefax(corp.getAreacodefax());
				c.setFaxnumber(corp.getFaxnumber());
				c.setEmailaddress(corp.getEmailaddress());
				c.setContacttype1(corp.getContacttype1());
				c.setContactvalue1(corp.getContactvalue1());
				c.setContacttype2(corp.getContacttype2());
				c.setContactvalue2(corp.getContactvalue2());
				c.setWebsite(corp.getWebsite());
				c.setMaincontact1(corp.getMaincontact1());

				// PSIC
				c.setPsic(corp.getPsic());
				c.setPsiccode(corp.getPsiccode());
				c.setPsiclevel1(corp.getPsiclevel1());
				c.setPsiclevel2(corp.getPsiclevel2());
				c.setPsiclevel3(corp.getPsiclevel3());
				c.setCifgroupcode(corp.getCifgroupcode());
				c.setResidenceclassification(corp.getResidenceclassification());
				c.setPaidupcapital(corp.getPaidupcapital());

				// Added 12-14-2018 - Kevin
				c.setExpirydate1(corp.getExpirydate1());
				c.setRegistrationtype2(corp.getRegistrationtype2());
				c.setRegistrationno2(corp.getRegistrationno2());
				c.setRegistrationdate2(corp.getRegistrationdate2());
				c.setExpirydate2(corp.getExpirydate2());

			}

			if (dbservice.saveOrUpdate(c)) {
				flag = "success";
				Tbcifmain m = (Tbcifmain) dbservice.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", param);
				if (c.getDateofincorporation() != null) {
					m.setDateofincorporation(c.getDateofincorporation());
				}
				if (c.getFulladdress1() != null) {
					m.setFulladdress1(c.getFulladdress1());
				}
				if (c.getFulladdress2() != null) {
					m.setFulladdress2(c.getFulladdress2());
				}
				dbservice.saveOrUpdate(m);
				HistoryService h = new HistoryServiceImpl();
				h.addHistory(c.getCifno(), "Saved as Draft.", null);
			} else {
				flag = "failed";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String addOtherContact(Tbothercontacts contacts) {
		String flag = "failed";
		DBService dbservice = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();

		try {
			if (contacts.getId() != null) {
				param.put("id", contacts.getId());
				Tbothercontacts c = (Tbothercontacts) dbservice
						.executeUniqueHQLQuery("FROM Tbothercontacts WHERE id=:id", param);
				if (c != null) {
					c.setContactperson(contacts.getContactperson());
					c.setDirectnumber(contacts.getDirectnumber());
					c.setEmailaddress(contacts.getEmailaddress());
					c.setMobilenumber(contacts.getMobilenumber());
					c.setOtherdetails(contacts.getOtherdetails());
				}
				if (dbservice.saveOrUpdate(c)) {
					flag = "success";
					HistoryService h = new HistoryServiceImpl();
					h.addHistory(c.getCifno(), "Update other contact.", null);
				}
			} else {
				if (dbservice.save(contacts)) {
					flag = "success";
					HistoryService h = new HistoryServiceImpl();
					h.addHistory(contacts.getCifno(), "Add other contact.", null);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbothercontacts> listOthercontacts(String cifno) {
		// TODO Auto-generated method stub
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		List<Tbothercontacts> list = new ArrayList<Tbothercontacts>();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				list = (List<Tbothercontacts>) dbService.executeListHQLQuery("FROM Tbothercontacts WHERE cifno=:cifno",
						param);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	// renz
	@Override
	public WorkflowProcessForm getWorkflowDetails(String sequenceno, String cifno) {
		DBService dbService = new DBServiceImpl();
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		WorkflowProcessForm details = new WorkflowProcessForm();
		Tbcifmain main = new Tbcifmain();
		String status = "";

		try {
			if (sequenceno != null) {
				param.put("sequenceno", sequenceno);
				if (cifno != null) {
					param.put("cifno", cifno);
					main = (Tbcifmain) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifmain where cifno =:cifno",
							param);
					
					if(main != null) {
						
						if(main.getCustomertype().equals("1")) {
							details = (WorkflowProcessForm) dbService.execSQLQueryTransformer(
									"select processname,sequenceno FROM Tbworkflowprocess WHERE sequenceno=:sequenceno AND workflowid = '1'",
									param, WorkflowProcessForm.class, 0);
							
						}else if(main.getCustomertype().equals("2")){
							details = (WorkflowProcessForm) dbService.execSQLQueryTransformer(
									"select processname,sequenceno FROM Tbworkflowprocess WHERE sequenceno=:sequenceno AND workflowid = '2'",
									param, WorkflowProcessForm.class, 0);
							
						}
					}
				}
				
				if (details != null) {
					details.setStatus(status);
					return details;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return details;
	}

	// 11-10-2020 MAR
	@Override
	public String saveOrupdateHeader(String appno, String product, String company, String branch, String purpose,
			String reason, String reftype, String refname, String rollovertype) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		// branch = branch != null ? "'" + branch + "'" : branch;
		try {
			if (appno != null) {
				params.put("appno", appno);
				Tblstapp app = (Tblstapp) dbServiceCOOP
						.executeUniqueHQLQueryMaxResultOne("FROM Tblstapp WHERE appno=:appno", params);

				if (app != null) {
					if (reason != null && !reason.equals("")) {
						app.setReasonreturn(reason);
						app.setApplicationstatus(1); // Encoding stage
					} else {
						if (purpose != null && !purpose.equals("")) {
							app.setLoanpurpose(purpose);
						}
						if (product != null && !product.equals("")) {
							app.setLoanproduct(product);
						}
						if (company != null && !company.equals("")) {
							app.setCompanycode(company);
						}
						if (branch != null && !branch.equals("")) {
							app.setBranchcode(branch);
						}
						if (reftype != null && !reftype.equals("")) {
							app.setReferraltype(reftype);
						}
						if (refname != null && !refname.equals("")) {
							app.setReferrorname(refname);
						}
						if (rollovertype != null && !rollovertype.equals("")) {
							app.setRollovertype(rollovertype);
						}
					}
					if (dbServiceCOOP.saveOrUpdate(app)) {
						flag = "success";
					}
				}

				/*
				 * String q = ""; if(reason!=null && !reason.equals("")){ q =
				 * "UPDATE Tblstapp SET reasonreturn ='" + reason + "' WHERE appno='" + appno +
				 * "'"; }else{ q = "UPDATE Tblstapp SET loanpurpose ='" + purpose + "'," +
				 * " loanproduct ='" + product + "'," + " companycode ='" + company + "'," +
				 * " branchcode =" + branch + "," + " referraltype ='" + reftype + "'," +
				 * " referrorname ='" + refname + "'" + " WHERE appno='" + appno + "'"; }
				 * Integer res = dbServiceLOS.executeUpdate(q, null); if (res != null && res >
				 * 0) { flag = "success"; }
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// no dedupe Renz 02172021
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbtradereference> listNewTradeRef(String cifno) {
		DBService dbsrvc = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();

		List<Tbtradereference> list = new ArrayList<Tbtradereference>();

		params.put("cifno", cifno);
		try {
			list = (List<Tbtradereference>) dbsrvc
					.executeListHQLQuery("FROM Tbtradereference WHERE cifno=:cifno and tradetype='0'", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbtradereference> listNewTradeRefSupplier(String cifno) {
		DBService dbsrvc = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();

		List<Tbtradereference> list = new ArrayList<Tbtradereference>();

		params.put("cifno", cifno);
		try {
			list = (List<Tbtradereference>) dbsrvc
					.executeListHQLQuery("FROM Tbtradereference WHERE cifno=:cifno and tradetype='1'", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveOrUpdateNewTradeRef(Tbtradereference ref) {

		DBService dbsrvc = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();

		Tbtradereference d = new Tbtradereference();
		if (ref.getId() != null) {
			params.put("id", ref.getId());
			d = (Tbtradereference) dbsrvc.executeUniqueHQLQuery("FROM Tbtradereference WHERE id=:id", params);
			d.setCifno(ref.getCifno());
			d.setTradetype("0");
			d.setClientsuppliername(ref.getClientsuppliername());
			d.setClientsupplieraddress(ref.getClientsupplieraddress());
			d.setContactperson(ref.getContactperson());
			d.setContactposition(ref.getContactposition());
			d.setEmailaddress(ref.getEmailaddress());

			if (dbsrvc.saveOrUpdate(d)) {
				System.out.println("UPDATE");
				return "update";
			}
		} else {
			ref.setTradetype("0");
			if (dbsrvc.saveOrUpdate(ref)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}

	@Override
	public String saveOrUpdateNewTradeRefSupplier(Tbtradereference ref) {

		DBService dbsrvc = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();

		Tbtradereference d = new Tbtradereference();
		if (ref.getId() != null) {
			params.put("id", ref.getId());
			d = (Tbtradereference) dbsrvc.executeUniqueHQLQuery("FROM Tbtradereference WHERE id=:id", params);
			d.setCifno(ref.getCifno());
			d.setTradetype("1");
			d.setClientsuppliername(ref.getClientsuppliername());
			d.setClientsupplieraddress(ref.getClientsupplieraddress());
			d.setContactperson(ref.getContactperson());
			d.setContactposition(ref.getContactposition());
			d.setEmailaddress(ref.getEmailaddress());

			if (dbsrvc.saveOrUpdate(d)) {
				System.out.println("UPDATE");
				return "update";
			}
		} else {
			ref.setTradetype("1");
			if (dbsrvc.saveOrUpdate(ref)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}

	@Override
	public String deleteNewTradeRef(Integer id) {

		DBService dbsrvc = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();

		String flag = "failed";
		params.put("id", id);
		try {
			if (id != null) {

				Integer res = dbsrvc.executeUpdate("DELETE FROM TBTRADEREFERENCE WHERE id=:id", params);
				if (res != null && res == 1) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// MAR 03-30-2021
	@Override
	public Tblosmain getDetailsRB(String cifno) {
		// TODO Auto-generated method stub
		DBService dbserviceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tblosmain detailsLOS = new Tblosmain();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				System.out.println(cifno);
				detailsLOS = (Tblosmain) dbserviceCOOP.executeUniqueHQLQuery("FROM Tblosmain WHERE cifno=:cifno",
						param);
				if (detailsLOS != null) {
					return detailsLOS;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return detailsLOS;
	}

	public Tblosindividual getIndividualRB(String cifno) {
		// TODO Auto-generated method stub
		DBService dbserviceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tblosindividual indiv = new Tblosindividual();
		try {
			param.put("cifno", cifno);
			indiv = (Tblosindividual) dbserviceCOOP.executeUniqueHQLQuery("FROM Tblosindividual WHERE cifno=:cifno",
					param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indiv;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tblosdependents> getListDependentsRB(String cifno) {
		// TODO Auto-generated method stub
		DBService dbserviceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		List<Tblosdependents> list = new ArrayList<Tblosdependents>();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				list = (List<Tblosdependents>) dbserviceCOOP
						.executeListHQLQuery("FROM Tblosdependents WHERE cifno=:cifno", param);
				if (list != null) {
					return list;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String updateRBMain(Tblosmain main) {
		String flag = null;
		DBService dbserviceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			param.put("cifno", main.getCifno());
			Tblosmain m = (Tblosmain) dbserviceCOOP.executeUniqueHQLQuery("FROM Tblosmain WHERE cifno=:cifno", param);
			if (m != null) {
				m.setOriginatingbranch(main.getOriginatingbranch());
				m.setBorrowerfundertype(main.getBorrowerfundertype());
				m.setCifpurpose(main.getCifpurpose());
				m.setCifgroupcode(main.getCifgroupcode());
				m.setTaxexempted(main.getTaxexempted());
				m.setReferrorposition(main.getReferrorposition());
				m.setDeclaredmaxvalueoftx(main.getDeclaredmaxvalueoftx());
				m.setClienttype(main.getClienttype());
				m.setReferrorname(main.getReferrorname());
				m.setDeceasedflag(main.getDeceasedflag());
				m.setReferraltype(main.getReferraltype());
				if (dbserviceCOOP.saveOrUpdate(m)) {
					flag = "success";
				} else {
					flag = "failed";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return flag;
	}

	@Override
	public String saveDependentsRB(Tblstappdependents dependents) {
		// TODO Auto-generated method stub
		DBService dbserviceCOOP = new DBServiceImpl();
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		Tblstappdependents d = new Tblstappdependents();
		try {

			if (dependents.getCifno() != null) {
				params.put("cifno", dependents.getCifno());
				d = (Tblstappdependents) dbserviceCOOP
						.executeUniqueHQLQuery("FROM Tblstappdependents WHERE cifno=:cifno", params);
			}
			if (d != null) {
				d.setAddress(dependents.getAddress());
				d.setAge(dependents.getAge());
				d.setDateofbirth(dependents.getDateofbirth());
				d.setFullname(dependents.getFullname());
				d.setGender(dependents.getGender());
				d.setGradeyear(dependents.getGradeyear());
				d.setIssameasfulladdress1(dependents.getIssameasfulladdress1());
				d.setIssameasfulladdress2(dependents.getIssameasfulladdress2());
				d.setOtherrelationship(dependents.getOtherrelationship());
				d.setRelationship(dependents.getRelationship());

				if (dbserviceCOOP.saveOrUpdate(dependents)) {
					flag = "success";
					// 08-08-17 PONGYU
					HistoryService h = new HistoryServiceImpl();
					h.addHistory(dependents.getCifno(), "Update dependents information", null);
				}
			} else {
				dbserviceCOOP.save(dependents);
				if (dbserviceCOOP.saveOrUpdate(dependents)) {
					flag = "success";
					// 08-08-17 PONGYU
					HistoryService h = new HistoryServiceImpl();
					h.addHistory(dependents.getCifno(), "Added dependents information", null);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String updateIndividualRB(Tblstappindividual indiv) {
		// TODO Auto-generated method stub
		String flag = null;
		DBService dbserviceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			param.put("appno", indiv.getAppno());
			Tblstappindividual i = (Tblstappindividual) dbserviceCOOP
					.executeUniqueHQLQuery("FROM Tblstappindividual WHERE appno=:appno", param);

			if (i != null) {
				// Basic Information UPDATE
				i.setMiddlename(indiv.getMiddlename());
//				i.setFirstname(indiv.getFirstname());
//				i.setLastname(indiv.getLastname());
				i.setTitle(indiv.getTitle());
				i.setShortname(indiv.getShortname());
				i.setPreviouslastname(indiv.getPreviouslastname());
				i.setSuffix(indiv.getSuffix());
				i.setGender(indiv.getGender());
				i.setCivilstatus(indiv.getCivilstatus());
				i.setDateofbirth(indiv.getDateofbirth());
				i.setAge(indiv.getAge());
				i.setPlaceofbirth(indiv.getPlaceofbirth());
				i.setStateofbirth(indiv.getStateofbirth());
				i.setCountryofbirth(indiv.getCountryofbirth());
				i.setCityofbirth(indiv.getCityofbirth());
				i.setAcrnumber(indiv.getAcrnumber());
				i.setNationality(indiv.getNationality());
				i.setDualcitizen(indiv.getDualcitizen());
				i.setOthernationality(indiv.getOthernationality());
				i.setResident(indiv.getResident());
				i.setTin(indiv.getTin());
				// sss - gsis
				i.setSss(indiv.getSss());
				i.setGsis(indiv.getGsis());
				i.setCarsowned(indiv.getCarsowned());
				i.setIsqualifiedinvestor(indiv.getIsqualifiedinvestor());
				i.setSchoolpostgrad(indiv.getSchoolpostgrad());
				i.setCoursepostgrad(indiv.getCoursepostgrad());
				i.setSchoolcollege(indiv.getSchoolcollege());
				i.setCoursecollege(indiv.getCoursecollege());
				i.setSourceoffunds(indiv.getSourceoffunds());
				i.setOthersourceoffunds(indiv.getOthersourceoffunds());
				i.setAnnualincome(indiv.getAnnualincome());
				i.setAsset(indiv.getAsset());
				i.setNetworth(indiv.getNetworth());
//				i.setEmploymentstatus(indiv.getEmploymentstatus()); /** Added by Wel 092617 **/
				i.setUsplaceofbirth(indiv.getUsplaceofbirth());
				i.setUssocialsecurityno(indiv.getUssocialsecurityno());
				i.setUsstateofbirth(indiv.getUsstateofbirth());

				// Address 1
				i.setAddresstype1(indiv.getAddresstype1());
				i.setIssameaddress1(indiv.getIssameaddress1());
				i.setStreetno1(indiv.getStreetno1());
				i.setSubdivision1(indiv.getSubdivision1());
				i.setCountry1(indiv.getCountry1());
				i.setProvince1(indiv.getProvince1());
				i.setCity1(indiv.getCity1());
				i.setBarangay1(indiv.getBarangay1());
				i.setPostalcode1(indiv.getPostalcode1());
				i.setHomeownership1(indiv.getHomeownership1());
				i.setOtherhomeownership1(indiv.getOtherhomeownership1());
				i.setOccupiedsince1(indiv.getOccupiedsince1());
				i.setFulladdress1(fullAddress(indiv.getStreetno1(), indiv.getSubdivision1(), indiv.getBarangay1(),
						indiv.getCity1(), indiv.getProvince1(), indiv.getCountry1(), indiv.getPostalcode1()));
				// --postalcodename
				i.setPostalcodename2(indiv.getPostalcodename2());
				i.setPostalcodename1(indiv.getPostalcodename1());
				// Address 2
				i.setAddresstype2(indiv.getAddresstype2());
				i.setIssameaddress2(indiv.getIssameaddress2());
				i.setStreetno2(indiv.getStreetno2());
				i.setSubdivision2(indiv.getSubdivision2());
				i.setCountry2(indiv.getCountry2());
				i.setProvince2(indiv.getProvince2());
				i.setCity2(indiv.getCity2());
				i.setBarangay2(indiv.getBarangay2());
				i.setPostalcode2(indiv.getPostalcode2());
				i.setHomeownership2(indiv.getHomeownership2());
				i.setOtherhomeownership2(indiv.getOtherhomeownership2());
				i.setOccupiedsince2(indiv.getOccupiedsince2());
				i.setFulladdress2(fullAddress(indiv.getStreetno2(), indiv.getSubdivision2(), indiv.getBarangay2(),
						indiv.getCity2(), indiv.getProvince2(), indiv.getCountry2(), indiv.getPostalcode2()));

				// true - use permanent else present
				i.setIsmailingaddress(indiv.getIsmailingaddress());

				// Contact Details
				i.setAreacodemobile(indiv.getAreacodemobile());
				i.setCountrycodemobile(indiv.getCountrycodemobile());
				i.setMobilenumber(indiv.getMobilenumber());
				i.setAreacodephone(indiv.getAreacodephone());
				i.setCountrycodephone(indiv.getCountrycodephone());
				i.setHomephoneno(indiv.getHomephoneno());
				i.setAreacodefax(indiv.getAreacodefax());
				i.setCountrycodefax(indiv.getCountrycodefax());
				i.setFaxnumber(indiv.getFaxnumber());
				i.setEmailaddress(indiv.getEmailaddress());
				i.setContacttype1(indiv.getContacttype1());
				i.setContactvalue1(indiv.getContactvalue1());
				i.setContacttype2(indiv.getContacttype2());
				i.setContactvalue2(indiv.getContactvalue2());

				// Spouse & Parent Details

				i.setSpousetitle(indiv.getSpousetitle());
				i.setSpouselastname(indiv.getSpouselastname());
				i.setSpousefirstname(indiv.getSpousefirstname());
				i.setSpousemiddlename(indiv.getSpousemiddlename());
				i.setSpousesuffix(indiv.getSpousesuffix());
				i.setSpousedateofbirth(indiv.getSpousedateofbirth());
				i.setSpousecifno(indiv.getSpousecifno());

				i.setFathertitle(indiv.getFathertitle());
				i.setFatherlastname(indiv.getFatherlastname());
				i.setFatherfirstname(indiv.getFatherfirstname());
				i.setFathermiddlename(indiv.getFathermiddlename());
				i.setFathersuffix(indiv.getFathersuffix());
				i.setFatherdateofbirth(indiv.getFatherdateofbirth());
				i.setFathercifno(indiv.getFathercifno());

				i.setMothertitle(indiv.getMothertitle());
				i.setMotherlastname(indiv.getMotherlastname());
				i.setMotherfirstname(indiv.getMotherfirstname());
				i.setMothermiddlename(indiv.getMothermiddlename());
				i.setMothersuffix(indiv.getMothersuffix());
				i.setMotherdateofbirth(indiv.getMotherdateofbirth());
				i.setMothercifno(indiv.getMothercifno());
				i.setCifgroupcode(indiv.getCifgroupcode());
				i.setMothermaidenmname(indiv.getMothermaidenmname());

				i.setBeneficiarytitle(indiv.getBeneficiarytitle());
				i.setBeneficiarylastname(indiv.getBeneficiarylastname());
				i.setBeneficiaryfirstname(indiv.getBeneficiaryfirstname());
				i.setBeneficiarymiddlename(indiv.getBeneficiarymiddlename());
				i.setBeneficiarysuffix(indiv.getBeneficiarysuffix());
				i.setBeneficiarydateofbirth(indiv.getBeneficiarydateofbirth());
				i.setBeneficiarycifno(indiv.getBeneficiarycifno());
				i.setRelationtoborrower(indiv.getRelationtoborrower());

				i.setIndivoccupation(indiv.getIndivoccupation());
				i.setResidenceclassification(indiv.getResidenceclassification());
			}
			if (dbserviceCOOP.saveOrUpdate(i)) {
//				Tblosmain m = (Tblosmain) dbserviceCOOP.executeUniqueHQLQuery("FROM Tblosmain WHERE cifno=:cifno",
//						param);
//				if (i.getDateofbirth() != null) {
//					m.setDateofbirth(i.getDateofbirth());
//				}
//				if (indiv.getFulladdress1() != null) {
//					m.setFulladdress1(i.getFulladdress1());
//				}
//				if (indiv.getFulladdress2() != null) {
//					m.setFulladdress2(i.getFulladdress2());
//				}
//				Tblosindividual in = (Tblosindividual) dbserviceCOOP
//						.executeUniqueHQLQuery("FROM Tblosindividual WHERE cifno=:cifno", param);
//				// suffix
//				String suf = "";
//				String middlename = "";
//				if (indiv.getSuffix() != null && !indiv.getSuffix().equals("")) {
//					suf = indiv.getSuffix();
//				}
//				// middle name
//				if (indiv.getMiddlename() != null && !indiv.getMiddlename().equals("")) {
//					middlename = indiv.getMiddlename();
//				}
//				m.setFullname(in.getLastname() + ", " + in.getFirstname() + " " + suf + " " + middlename);

//				dbserviceCOOP.saveOrUpdate(m);
				HistoryService h = new HistoryServiceImpl();
				h.addHistory(i.getCifno(), "Saved as Draft.", null);
				flag = "success";
			} else {
				flag = "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public FormValidation validateDataEntryRB(String cifno) {
		// TODO Auto-generated method stub
		DBService dbserviceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tblstapp app = new Tblstapp();
		FormValidation form = new FormValidation();
		StringBuilder errorMessage = new StringBuilder();
		String flag = "success";
		boolean isNull = false;
		try {
//			System.out.println("start validate . . . "+cifno);
			param.put("cifno", cifno);
			app = (Tblstapp) dbserviceCOOP.executeUniqueHQLQuery("FROM Tblstapp WHERE cifno=:cifno", param);
			if (app == null) {
				System.out.println("no record in Tbcifmain");
			} else {

				// Individual
				if (app.getCustomertype().equals("1")) {
					Tblstappindividual info = new Tblstappindividual();
					info = (Tblstappindividual) dbserviceCOOP
							.executeUniqueHQLQuery("FROM Tblstappindividual WHERE cifno=:cifno", param);

					// Checking for all valid field only
//					boolean isValid = true;
//					if(info.getShortname() != null){
//						if(!RegexUtil.name(info.getShortname())){
//							isValid = false;
//						}
//					}
//					if(info.getPreviouslastname() != null){
//						if(!RegexUtil.name(info.getPreviouslastname())){
//							isValid = false;
//						}
//					}
//					if(info.getAcrnumber() != null){
//						if(!RegexUtil.numbersWithDash(info.getAcrnumber())){
//							isValid = false;
//						}
//					}
//					if(info.getCountryofbirth() != null && info.getCountryofbirth().equals("US")){
//						if(info.getUssocialsecurityno() != null){
//							if(!RegexUtil.numbersWithDash(info.getUssocialsecurityno())){
//								isValid = false;
//							}
//						}
//					}
//					if(info.getTin() != null){
//						if(!RegexUtil.numbersWithDash(info.getTin())){
//							isValid = false;
//						}
//					}
//					if(info.getMiddlename() != null){
//						if(!RegexUtil.name(info.getMiddlename())){
//							isValid = false;
//						}
//					}
//					if(info.getSuffix() != null && !info.getSuffix().equals("")){
//						if(!RegexUtil.name(info.getSuffix())){
//							isValid = false;
//						}
//					}
//					if(info.getCountrycodemobile() != null){
//						if(!RegexUtil.numbersOnly(info.getCountrycodemobile())){
//							isValid = false;
//						}
//					}
//					if(info.getAreacodemobile() != null){
//						if(!RegexUtil.numbersOnly(info.getAreacodemobile())){
//							isValid = false;
//						}
//					}
//					if(info.getMobilenumber() != null){
//						if(!RegexUtil.numbersOnly(info.getMobilenumber())){
//							isValid = false;
//						}
//					}
//					if(info.getCountrycodephone() != null){
//						if(!RegexUtil.numbersOnly(info.getCountrycodephone())){
//							isValid = false;
//						}
//					}
//					if(info.getAreacodephone() != null){
//						if(!RegexUtil.numbersOnly(info.getAreacodephone())){
//							isValid = false;
//						}
//					}
//					if(info.getHomephoneno() != null){
//						if(!RegexUtil.numbersOnly(info.getHomephoneno())){
//							isValid = false;
//						}
//					}
//					if(info.getCountrycodefax() != null){
//						if(!RegexUtil.numbersOnly(info.getCountrycodefax())){
//							isValid = false;
//						}
//					}
//					if(info.getAreacodefax() != null){
//						if(!RegexUtil.numbersOnly(info.getAreacodefax())){
//							isValid = false;
//						}
//					}
//					if(info.getFaxnumber() != null){
//						if(!RegexUtil.numbersOnly(info.getFaxnumber())){
//							isValid = false;
//						}
//					}
//					if(!isValid){
//						isNull = true;
//						errorMessage.append("<i>Please make sure to put valid data only.</i>");
//					}
					// End of Checking valid field

					// ----------------- validating required fields -----------------------//
					// basic information tab

					boolean basic = false;

					if (
							info.getTitle() == null
							|| info.getGender() == null 
							|| info.getCivilstatus() == null
							|| info.getCountryofbirth() == null || info.getNationality() == null
							|| info.getSourceoffunds() == null || info.getAnnualincome() == null
							|| info.getAsset() == null || info.getNetworth() == null
					/* || info.getEmploymentstatus() == null */) { /** Added by Wel 092617 **/// removed 10-11
						basic = true;
					}

					if (info.getDualcitizen() != null) {
						if (info.getDualcitizen()) {
							if (info.getOthernationality() == null) {
								basic = true;
							}
						}
					}
					if (info.getSourceoffunds() != null) {
						if (info.getSourceoffunds().equals("10")) {
							if (info.getOthersourceoffunds() == null) {
								basic = true;
							}
						}
					}
					if (info.getCountryofbirth() != null) {
						if (info.getCountryofbirth() == "PH") {
							if (info.getPlaceofbirth() == null || info.getCityofbirth() == null) {
								basic = true;
							}
						}
					}
					if (basic) {
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Basic Details tab");

						if (info.getTitle() == null) {

						}

						isNull = true;
					}

					// Ride QIB - Added by Wel July 18, 2017
					if (info.getIsqualifiedinvestor() != null && info.getIsqualifiedinvestor()) {
						QIBFacade qib = new QIBFacade();
						FormValidation f = qib.validateQIBInfo(info.getCifno());
						if (f.getFlag().equals("failed")) {
							errorMessage.append(f.getErrorMessage());
							isNull = true;
						}
					}
					// employment details

//					List<Tbcifemployment> em = getListEmployment(cifno);
//					if(em == null || em.isEmpty()){
//						errorMessage.append("<br/>");
//						errorMessage.append("<b>Missing required field(s):</b> No employment information");
//						isNull = true;
//					}
					// Aug 31, 2018
					List<Tblstappbusiness> bus = getListBusinessRB(cifno);
					if (bus != null && !bus.isEmpty()) {
						/*
						 * Integer res = (Integer) dbserviceCOOP.executeUniqueSQLQuery(
						 * "SELECT COUNT(*) FROM Tblstappbusiness WHERE cifno=:cifno AND (genmanager = '' OR genmanager IS NULL) AND (gmcif = '' OR gmcif IS NULL)"
						 * , param); if (res != null && res > 0) { errorMessage.append("<br/>");
						 * errorMessage.append("<b>Missing General Manager:</b> Business Details tab");
						 * isNull = true; }
						 */
//						System.out.println("---------- BUSINESS LIST NOT EMPTY !!!");
//						for(Tbcifbusiness bus2 : bus){
//							if(bus2.getBuscifno()!=null){
//								Map<String, Object> params = HQLUtil.getMap();
//								params.put("c", bus2.getBuscifno()); 
//								Integer res = (Integer) dbService.executeUniqueSQLQuery
//										("SELECT COUNT(*) FROM Tbmanagement WHERE cifno=:c AND relationshipcode = 'GM'", params);
//								if(res!=null && res>0){
//									errorMessage.append("<br/>");
//									errorMessage.append("<b>Missing General Manager:</b> Business Details tab");
//									isNull = true;
//								}
//							}
//						}
					}

					// contact details
					if (info.getMobilenumber() == null || info.getHomephoneno() == null) {
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Contact Details tab");
						isNull = true;
					}
					if (info.getContacttype1() != null) {
						if (info.getContactvalue1() == null) {
							errorMessage.append("<br/>");
							errorMessage.append("<b>Missing required field(s):</b> Contact Details tab");
							isNull = true;
						}
					}
					if (info.getContacttype2() != null) {
						if (info.getContactvalue2() == null) {
							errorMessage.append("<br/>");
							errorMessage.append("<b>Missing required field(s):</b> Contact Details tab");
							isNull = true;
						}
					}
					boolean address = false;
					// Address1 Address2
					if (info.getCountry1() != null) {
						if (info.getCountry1().equals("PH")) {
							if (info.getProvince1() == null || info.getProvince1().equals("") || info.getCity1() == null
									|| info.getCity1().equals("")) {
								address = true;
							}
						}
					}

					if (info.getCountry2() != null) {
						if (info.getCountry1().equals("PH")) {
							if (info.getProvince2() == null || info.getProvince2().equals("") || info.getCity2() == null
									|| info.getCity2().equals("")) {
								address = true;
							}
						}
					}

					// Address details
					if (info.getAddresstype1() == null || info.getStreetno1() == null || info.getStreetno1().equals("")
							|| info.getCountry1() == null || info.getCountry1().equals("")
							|| info.getHomeownership1() == null || info.getAddresstype2() == null
							|| info.getStreetno2() == null || info.getStreetno2().equals("")
							|| info.getCountry2() == null || info.getCountry2().equals("")
							|| info.getHomeownership2() == null || address) {
						errorMessage.append("<br/>");
						errorMessage.append("<b>Missing required field(s):</b> Address Details tab");
						isNull = true;
					}

//					// Spouse & Parent Details
//					boolean spd = false;
//					if (info.getCivilstatus() != null) {
//						if (info.getCivilstatus().equals("2") || info.getCivilstatus().equals("4")) {
//							if (info.getSpousetitle() == null || info.getSpouselastname() == null
//									|| info.getSpousefirstname() == null) {
//								spd = true;
//							}
//						}
//					}
//					// Mother
//					if (info.getMotherlastname() == null || info.getMotherfirstname() == null
//							|| info.getMothermiddlename() == null
//							|| (info.getMotherlastname() != null && info.getMotherlastname().equals("")) 
//							|| (info.getMotherfirstname() != null && info.getMotherfirstname().equals(""))
//							|| (info.getMothermiddlename() != null && info.getMothermiddlename().equals(""))) {
//						spd = true;
//					}
//					if (spd) {
//						isNull = true;
//						errorMessage.append("<br/>");
//						errorMessage.append("<b>Missing required field(s):</b> Parental / Spouse Information tab");
//					}

					if (isNull) {
						flag = "failed";
					}
					form.setFlag(flag);
					form.setErrorMessage(errorMessage.toString());
				}
				// corporate
				else if (app.getCustomertype().equals("2") || app.getCustomertype().equals("3")) {
					Tblstappcorporate c = new Tblstappcorporate();
					c = (Tblstappcorporate) dbserviceCOOP
							.executeUniqueHQLQuery("FROM Tblstappcorporate WHERE cifno=:cifno", param);
					if (c != null) {
						// Basic Info
						if (c.getTradename() == null || c.getDateofincorporation() == null || c.getFirmsize() == null
								|| c.getNationality() == null || c.getNoofemployees() == null
								|| c.getMonthlyincomesale() == null || c.getRegistrationtype() == null
								|| c.getRegistrationno() == null || c.getPsiccode() == null
								|| c.getBusinesstype() == null) {
							errorMessage.append("<br/>");
							errorMessage.append("<b>Missing required field(s):</b> Basic Information tab");
							isNull = true;

						}
						// if Registration type = DTI
						if (c.getRegistrationtype() != null && c.getRegistrationtype().equals("1")) {
							if (c.getExpirydate1() == null && isNull == false) {
								errorMessage.append("<br/>");
								errorMessage.append("<b>Missing required field(s):</b> Basic Information tab");
								isNull = true;
							}
						}

						// Contact details
						if (c.getMobilenumber() == null || c.getHomephoneno() == null) {
							errorMessage.append("<br/>");
							errorMessage.append("<b>Missing required field(s):</b> Contact Details tab");
							isNull = true;
						}
						if (c.getContacttype1() != null) {
							if (c.getContactvalue1() == null) {
								errorMessage.append("<br/>");
								errorMessage.append("<b>Missing required field(s):</b> Contact Details tab");
								isNull = true;
							}
						}
						if (c.getContacttype2() != null) {
							if (c.getContactvalue2() == null) {
								errorMessage.append("<br/>");
								errorMessage.append("<b>Missing required field(s):</b> Contact Details tab");
								isNull = true;
							}
						}
						// Address details
						if (c.getAddresstype1() == null || c.getStreetno1() == null || c.getStreetno1().equals("")
								|| c.getCountry1() == null || c.getCountry1().equals("") || c.getProvince1() == null
								|| c.getProvince1().equals("") || c.getCity1() == null || c.getCity1().equals("")
								|| c.getHomeownership1() == null
						/*
						 * || c.getAddresstype2() == null || c.getStreetno2() == null ||
						 * c.getStreetno2().equals("") || c.getCountry2() == null ||
						 * c.getCountry2().equals("") || c.getProvince2() == null ||
						 * c.getProvince2().equals("") || c.getCity2() == null ||
						 * c.getCity2().equals("") || c.getHomeownership2() == null
						 */
						) {
							errorMessage.append("<br/>");
							errorMessage.append("<b>Missing required field(s):</b> Address Details tab");
							isNull = true;
						}

						// Ride QIB - Added by Wel July 18, 2017
						if (c.getIsqualifiedinvestor() != null && c.getIsqualifiedinvestor()) {
							QIBFacade qib = new QIBFacade();
							FormValidation f = qib.validateQIBInfo(c.getCifno());
							if (f.getFlag().equals("failed")) {
								errorMessage.append(f.getErrorMessage());
								isNull = true;
							}
						}
						// Management Details
						if (c.getBusinesstype() == null) {
							errorMessage.append("<br/>");
							errorMessage.append("<b>Missing required field(s):</b> Management Details tab");
							isNull = true;
						} else {

						}

						if (isNull) {
							flag = "failed";
							form.setFlag(flag);
						}
						form.setFlag(flag);
						form.setErrorMessage(errorMessage.toString());
					}
				}
			}
			// System.out.println(">>>>>>>>>flag: "+form.getFlag());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tblstappbusiness> getListBusinessRB(String cifno) {
		// TODO Auto-generated method stub
		DBService dbserviceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		List<Tblstappbusiness> list = new ArrayList<Tblstappbusiness>();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				list = (List<Tblstappbusiness>) dbserviceCOOP
						.executeListHQLQuery("FROM Tblstappbusiness WHERE cifno=:cifno", param);
				if (list != null) {
					return list;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Tbcifcorporate getCorporateRB(String cifno) {
		// TODO Auto-generated method stub
		DBService dbservice = new DBServiceImplCIF();
		DBService dbserviceLOS = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tbcifcorporate corp = new Tbcifcorporate();
		try {
			param.put("cifno", cifno);
			System.out.println("FED" + cifno);
			corp = (Tbcifcorporate) dbservice.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno=:cifno", param);
			if (corp == null) {
				System.out.println("FEDPASSED");
				corp = (Tbcifcorporate) dbserviceLOS.execStoredProc(
						"SELECT * FROM Tblstappcorporate WHERE appno=:cifno", param, Tbcifcorporate.class, 0, null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return corp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbothercontactslos> listOthercontactsRB(String cifno) {
		// TODO Auto-generated method stub
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		List<Tbothercontactslos> list = new ArrayList<Tbothercontactslos>();
		try {
			if (cifno != null) {
				param.put("cifno", cifno);
				list = (List<Tbothercontactslos>) dbServiceCOOP
						.executeListHQLQuery("FROM Tbothercontactslos WHERE cifno=:cifno", param);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String updateCorporateCIFRB(Tblstappcorporate corp) {
		DBService dbserviceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		String flag = null;
		try {
			System.out.println("start..");
			param.put("appno", corp.getAppno());
			System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + corp.getAppno());
			Tblstappcorporate c = (Tblstappcorporate) dbserviceCOOP
					.executeUniqueHQLQuery("FROM Tblstappcorporate WHERE appno=:appno", param);
			if (c != null) {
				// Basic Information
				c.setDateofincorporation(corp.getDateofincorporation());
				c.setTradename(corp.getTradename());
				c.setFirmsize(corp.getFirmsize());
				c.setNationality(corp.getNationality());
				c.setIsresident(corp.getIsresident());
				c.setNoofemployees(corp.getNoofemployees());
				c.setMonthlyincomesale(corp.getMonthlyincomesale());
				c.setRegistrationtype(corp.getRegistrationtype());
				c.setTin(corp.getTin());
				// sss
				c.setSss(corp.getSss());
				c.setTermofexistence(corp.getTermofexistence());
				c.setLessee(corp.getLessee());
				c.setBusinesstype(corp.getBusinesstype());
				c.setIsqualifiedinvestor(corp.getIsqualifiedinvestor());
				c.setLegalform(corp.getLegalform());
				c.setPsic(corp.getPsic());
				c.setGrossincome(corp.getGrossincome());
				c.setNettaxableincome(corp.getNettaxableincome());
				c.setMonthlyexpenses(corp.getMonthlyexpenses());
				c.setRegistrationno(corp.getRegistrationno());
				// Address 1
				c.setAddresstype1(corp.getAddresstype1());
				c.setStreetno1(corp.getStreetno1());
				c.setSubdivision1(corp.getSubdivision1());
				c.setCountry1(corp.getCountry1());
				c.setProvince1(corp.getProvince1());
				c.setCity1(corp.getCity1());
				c.setBarangay1(corp.getBarangay1());
				c.setPostalcode1(corp.getPostalcode1());
				c.setHomeownership1(corp.getHomeownership1());
				c.setOtherhomeownership1(corp.getOtherhomeownership1());
				c.setOccupiedsince1(corp.getOccupiedsince1());
				c.setFulladdress1(fullAddress(corp.getStreetno1(), corp.getSubdivision1(), corp.getBarangay1(),
						corp.getCity1(), corp.getProvince1(), corp.getCountry1(), corp.getPostalcode1()));
				c.setPostalcodename1(corp.getPostalcodename1());

				// Address 2
				c.setAddresstype2(corp.getAddresstype2());
				c.setStreetno2(corp.getStreetno2());
				c.setSubdivision2(corp.getSubdivision2());
				c.setCountry2(corp.getCountry2());
				c.setProvince2(corp.getProvince2());
				c.setCity2(corp.getCity2());
				c.setBarangay2(corp.getBarangay2());
				c.setPostalcode2(corp.getPostalcode2());
				// --postalcodename2
				c.setPostalcodename2(corp.getPostalcodename2());
				c.setHomeownership2(corp.getHomeownership2());
				c.setOtherhomeownership2(corp.getOtherhomeownership2());
				c.setOccupiedsince2(corp.getOccupiedsince2());
				c.setFulladdress2(fullAddress(corp.getStreetno2(), corp.getSubdivision2(), corp.getBarangay2(),
						corp.getCity2(), corp.getProvince2(), corp.getCountry2(), corp.getPostalcode2()));
				c.setIssameaddress1(corp.getIssameaddress1());

				// true - use permanent else present
				c.setIsmailingaddress(corp.getIsmailingaddress());

				// Contact
				c.setCountrycodemobile(corp.getCountrycodemobile());
				c.setAreacodemobile(corp.getAreacodemobile());
				c.setMobilenumber(corp.getMobilenumber());
				c.setCountrycodephone(corp.getCountrycodephone());
				c.setAreacodephone(corp.getAreacodephone());
				c.setHomephoneno(corp.getHomephoneno());
				c.setCountrycodefax(corp.getCountrycodefax());
				c.setAreacodefax(corp.getAreacodefax());
				c.setFaxnumber(corp.getFaxnumber());
				c.setEmailaddress(corp.getEmailaddress());
				c.setContacttype1(corp.getContacttype1());
				c.setContactvalue1(corp.getContactvalue1());
				c.setContacttype2(corp.getContacttype2());
				c.setContactvalue2(corp.getContactvalue2());
				c.setWebsite(corp.getWebsite());
				c.setMaincontact1(corp.getMaincontact1());

				// PSIC
				c.setPsic(corp.getPsic());
				c.setPsiccode(corp.getPsiccode());
				c.setPsiclevel1(corp.getPsiclevel1());
				c.setPsiclevel2(corp.getPsiclevel2());
				c.setPsiclevel3(corp.getPsiclevel3());
				c.setCifgroupcode(corp.getCifgroupcode());
				c.setResidenceclassification(corp.getResidenceclassification());
				c.setPaidupcapital(corp.getPaidupcapital());

				// Added 12-14-2018 - Kevin
				c.setExpirydate1(corp.getExpirydate1());
				c.setRegistrationtype2(corp.getRegistrationtype2());
				c.setRegistrationno2(corp.getRegistrationno2());
				c.setRegistrationdate2(corp.getRegistrationdate2());
				c.setExpirydate2(corp.getExpirydate2());

			}

			if (dbserviceCOOP.saveOrUpdate(c)) {
				/*
				 * Tblosmain m = (Tblosmain)
				 * dbserviceCOOP.executeUniqueHQLQuery("FROM Tblosmain WHERE cifno=:cifno",
				 * param); if (c.getDateofincorporation() != null) {
				 * m.setDateofincorporation(c.getDateofincorporation()); } if
				 * (c.getFulladdress1() != null) { m.setFulladdress1(c.getFulladdress1()); } if
				 * (c.getFulladdress2() != null) { m.setFulladdress2(c.getFulladdress2()); }
				 * dbserviceCOOP.saveOrUpdate(m);
				 */
				HistoryService h = new HistoryServiceImpl();
				h.addHistory(c.getCifno(), "Saved as Draft.", null);
				flag = "success";
			} else {
				flag = "failed";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public BigDecimal totalDebitCredit(String pnno, String type, String txcode) {
		BigDecimal total = BigDecimal.ZERO;
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (pnno != null && type != null) {
				param.put("pnno", pnno);
				param.put("txcode", txcode);
				String query = "";
				if (type.equalsIgnoreCase("debit")) {
					query = "SELECT ISNULL(SUM(ISNULL(debit,0)),0) FROM TBGLENTRIES WHERE accountno=:pnno and txcode=:txcode";
				} else if (type.equalsIgnoreCase("credit")) {
					query = "SELECT ISNULL(SUM(ISNULL(credit,0)),0) FROM TBGLENTRIES WHERE accountno=:pnno and txcode=:txcode";
				}
				total = (BigDecimal) dbService.executeUniqueSQLQuery(query, param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}

	@Override
	public Boolean isPNNoValid(String pnno) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (pnno != null) {
				params.put("pnno", pnno);
				Integer res = (Integer) dbService.executeUniqueSQLQuery(
						"SELECT COUNT(*) FROM TBLOANS WHERE pnno=:pnno AND acctsts IN ('1','2','3','4')", params);
				if (res != null && res > 0) {
					LoggerUtil.info(">>>>>> Invalid PNNo.! : " + pnno
							+ " (TBLOANS) - PNNo. doesn't exist or account status is in (CURRENT, DELINQUENT, NON-EARNING, LITIGATION)",
							this.getClass());
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public String checkNetProceeds(String appno, BigDecimal amount) {
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (appno != null && amount != null) {
				params.put("appno", appno);
				Tbaccountinfo info = (Tbaccountinfo) dbService
						.executeUniqueHQLQueryMaxResultOne("FROM Tbaccountinfo WHERE applno=:appno", params);
				if (info != null && info.getNetprcds() != null) {
					BigDecimal b = new BigDecimal(String.valueOf(amount));
					int d = b.compareTo(info.getNetprcds());
					if (d > 0) { // greater than
						return "failed";
					} else {
						return "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateBus(Tbcifbusiness d) {
		String flag = "failed";
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (d.getCifno() != null) {
				if (d.getBusinessid() != null) {
					// update
					param.put("id", d.getBusinessid());
					param.put("cifno", d.getCifno());
					Tbcifbusiness row = (Tbcifbusiness) dbServiceCIF
							.executeUniqueHQLQuery("FROM Tbcifbusiness WHERE businessid=:id AND cifno=:cifno", param);
					if (row != null) {
						row.setCifno(d.getCifno());
						row.setBusinessname(d.getBusinessname());
						row.setFulladdress1(d.getFulladdress1());
						row.setBusinessclass(d.getBusinessclass());
						row.setNatureofbusiness(d.getNatureofbusiness());
						row.setCountrycodephone(d.getCountrycodephone());
						row.setAreacodephone(d.getAreacodephone());
						row.setBusinessphoneno(d.getBusinessphoneno());
						row.setCountrycodefax(d.getCountrycodefax());
						row.setAreacodefax(d.getAreacodefax());
						// n.setfax
						row.setEmailaddress(d.getEmailaddress());
						row.setGrossincome(d.getGrossincome());
						if (dbServiceCIF.saveOrUpdate(row)) {
							flag = "success";
						}
					}
				} else {
					// add
					Tbcifbusiness n = new Tbcifbusiness();
					n.setCifno(d.getCifno());
					n.setBusinessname(d.getBusinessname());
					n.setFulladdress1(d.getFulladdress1());
					n.setBusinessclass(d.getBusinessclass());
					n.setNatureofbusiness(d.getNatureofbusiness());
					n.setCountrycodephone(d.getCountrycodephone());
					n.setAreacodephone(d.getAreacodephone());
					n.setBusinessphoneno(d.getBusinessphoneno());
					n.setCountrycodefax(d.getCountrycodefax());
					n.setAreacodefax(d.getAreacodefax());
					// n.setfax
					n.setEmailaddress(d.getEmailaddress());
					n.setGrossincome(d.getGrossincome());
					if (dbServiceCIF.save(n)) {
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override // Added by Ced 6-28-2021
	public String deleteDependents(Tblstappdependents d) {
		String result = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("id", d.getDependentid());
		try {
			dbService.executeHQLUpdate("DELETE FROM Tblstappdependents WHERE dependentid =:id", param);
			HistoryService h = new HistoryServiceImpl();
			h.addHistory(d.getAppno(), "Deleted dependents record", null);
			result = "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public FileUploadResponse uploadFile(MultipartFile file) {

		FileUploadResponse ret = new FileUploadResponse();

		try {
			String filename = file.getOriginalFilename(); /* .replaceAll("[^a-zA-Z0-9 ._-]",""); */
			boolean hasExtension = filename.indexOf(".") != -1;
//			String name = (hasExtension) ? filename.substring(0, filename.lastIndexOf(".")) : filename;
			String ext = (hasExtension) ? filename.substring(filename.lastIndexOf(".")) : "";
//			System.out.println("File ext : " + ext);
//			System.out.println("FEEED" + RuntimeAccess.getInstance().getSession().getServletContext());
			if (ext.equalsIgnoreCase(".jpeg") || ext.equalsIgnoreCase(".jpg") || ext.equalsIgnoreCase(".png")) {
				File dir = new File(
						RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/docdir/"));
				if (!dir.exists())
					dir.mkdirs();

				/*
				 * Create a file object that does not point to an existing file. Loop through
				 * names until we find a filename not already in use
				 */
				File outputFile = new File(dir, filename);

				deleteFileOlderThanXdays(1, dir.toString());

				// System.out.println("FEEEEED"+frimage);
				/* Write the file to the filesystem */
				FileOutputStream fos = new FileOutputStream(outputFile);
				IOUtils.copy(file.getInputStream(), fos);
				file.getInputStream().close();
				fos.close();
				// System.out.println(membershipappid);
//				docu_files.setDocubase(ImageUtils.pdfToBase64(outputFile.toString()));
//				docu_files.setDocuname(filename);
//				docu_files.setMembershipappid(membershipappid);
//				dbService.save(docu_files);
				// System.out.println(ImageUtils.pdfToBase64(outputFile.toString()));

				ret.setPath(outputFile.getPath());
				ret.setError("");
			} else {
				ret.setError("Invalid File Format");
			}
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
	public String updateProfilePhoto(String cifno, String filepath) {
		String flag = null;
		DBService dbservice = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();

		try {
			param.put("cifno", cifno);
			Tbcifmain m = (Tbcifmain) dbservice.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", param);
			if (m != null) {
				if (filepath != null) {
					m.setProfpiccode(ImageUtils.pdfToBase64(filepath));
				}
				if (dbservice.saveOrUpdate(m)) {
					flag = "success";
				} else {
					flag = "failed";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String changeCompanyOrMemberStatus(String cifno, String status, String remarks) {
		String flag = null;
		DBService dbservice = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		ADInquiryFacade adInquiry = new ADInquiryFacade();
		NoGenerator noGenerator = new NoGenerator();
		try {
			param.put("cifno", cifno);
			Tbcifmain cifMain = (Tbcifmain) dbservice.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno",
					param);
			
			//Company
			if(cifMain.getCustomertype().equals("1")) {
				
				String cifOldStatus = cifMain.getCifstatus();
				cifMain.setCifoldStatus(cifOldStatus);
				cifMain.setCifoldStatusDate(new Date());
			
				//For Encoding
				if(status.equals("1")) {
					cifMain.setCifstatus("1");
					cifMain.setReturnRemarks(remarks);
					cifMain.setCifreturnedby(username);
					cifMain.setCifreturnedbydate(new Date());
				
				//Approved
				}else if(status.equals("4")) {
					cifMain.setCifstatus("4");
					cifMain.setApprovedRemarks(remarks);
					cifMain.setCifapprovedby(username);
					cifMain.setCifapproveddate(new Date());
					
					//Crete User
					adInquiry.saveMemberCredentials(cifno, "MemberRoles");	
				
				}
				//Cancel
				else if(status.equals("5")) {
					cifMain.setCifstatus("5");
					cifMain.setCancelRemarks(remarks);
					cifMain.setCancelledby(username);
					cifMain.setDatecancelled(new Date());
					
				//Declined
				}else if(status.equals("6")) {
					cifMain.setCifstatus("6");
					cifMain.setDeclinedRemarks(remarks);
					cifMain.setCifdeclinedBy(username);
					cifMain.setCifdeclinedDate(new Date());
					
				}
				
				cifMain.setDateupdated(new Date());
				if (dbservice.saveOrUpdate(cifMain)) {
					flag = "success";
				} else {
					flag = "failed";
				}
				
			}
			//Membership
			if(cifMain.getCustomertype().equals("2")) {
				
				String cifOldStatus = cifMain.getCifstatus();
				cifMain.setCifoldStatus(cifOldStatus);
				cifMain.setCifoldStatusDate(new Date());
			
				//For Encoding
				if(status.equals("1")) {
					cifMain.setCifstatus("1");
					cifMain.setCifreturnedby(username);
					cifMain.setCifreturnedbydate(new Date());
				
				//Approved
				}else if(status.equals("5")) {
					cifMain.setCifstatus("5");
					cifMain.setCifapprovedby(username);
					cifMain.setCifapproveddate(new Date());
					
					//Generate MemberId 
					String memberId = noGenerator.generateMemberId("Membership");
					cifMain.setMemberId(memberId);
					
					//TBCODETABLE MEMBERSTATUS 
					cifMain.setMemberStatus("1"); // Active
					
					//Create Share Capital Account Number
					cifMain.setShareCapitalAccountNumber(memberId+"SHAR");
					
					//Send Email
					adInquiry.saveMemberCredentials(cifno, "MemberRoles");	
				
				}
				//Cancel
				else if(status.equals("6")) {
					cifMain.setCifstatus("6");
					cifMain.setCancelledby(username);
					cifMain.setDatecancelled(new Date());
					
				//Declined
				}else if(status.equals("7")) {
					cifMain.setCifstatus("7");
					cifMain.setCifdeclinedBy(username);
					cifMain.setCifdeclinedDate(new Date());
					
				}
				
				cifMain.setDateupdated(new Date());
				if (dbservice.saveOrUpdate(cifMain)) {
					flag = "success";
				} else {
					flag = "failed";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return flag;
	}

	@Override
	public String saveOrUpdateAmortizedDetails(Tbamortizedattachment d) {
		String flag = "failed";
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (d.getId() != null) {
				// update record

				params.put("id", d.getId());
				Tbamortizedattachment row = (Tbamortizedattachment) dbServiceCoop
						.executeUniqueHQLQuery("FROM Tbamortizedattachment WHERE id=:id", params);
				if (row != null) {
					row.setAttachmenttype(d.getAttachmenttype());
					row.setAttachmentdetail(d.getAttachmentdetail());
					row.setAmount(d.getAmount());
					if (dbServiceCoop.saveOrUpdate(row)) {
						flag = "success";
					}
				}
			} else {
				// new record
				Tbamortizedattachment n = new Tbamortizedattachment();
				n.setAppno(d.getAppno());
				n.setAttachmenttype(d.getAttachmenttype());
				n.setAttachmentdetail(d.getAttachmentdetail());
				n.setAmount(d.getAmount());

				n.setDatecreated(new Date());
				n.setCreatedby(secservice.getUserName());
				if (dbServiceCoop.save(n)) {
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
	public List<Tbamortizedattachment> listAmortizedDetails(String appno) {
		List<Tbamortizedattachment> list = new ArrayList<Tbamortizedattachment>();
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (appno != null) {
				params.put("appno", appno);
				list = (List<Tbamortizedattachment>) dbServiceCoop
						.executeListHQLQuery("FROM Tbamortizedattachment WHERE appno=:appno", params);

				if (list != null) {
					for (Tbamortizedattachment f : list) {
						if (f.getAttachmenttype().equals("0")) {
							// do nothing, get account number
							f.setUsername(f.getAttachmentdetail());
						} else if (f.getAttachmenttype().equals("1")) {
							// get codetable
							params.put("a", f.getAttachmentdetail());
							Tbcodetable row = (Tbcodetable) dbServiceCoop.executeUniqueHQLQuery(
									"FROM Tbcodetable WHERE codename ='LOANFEES' AND codevalue =:a", params);
							if (row != null) {
								f.setUsername(row.getDesc1());
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteAmortizedDetails(Integer id) {
		String flag = "failed";
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (id != null) {
				params.put("id", id);
				Integer res = null;
				res = dbServiceCoop.executeUpdate("DELETE FROM TBAMORTIZEDATTACHMENT WHERE id=:id", params);
				if (res != null && res == 1) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public MembershipHeaderForm getMembershipHeader(String cifno) {
		MembershipHeaderForm membershipHeaderForm = new MembershipHeaderForm();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("cifno", cifno);
			membershipHeaderForm = (MembershipHeaderForm) dbServiceCIF.execSQLQueryTransformer(
					"SELECT"
					+ " (select branchname from AcaciaAyalaCoreDB.dbo.TBBRANCH WHERE branchcode = b.originatingbranch) AS branch,"
					+ " b.cifno AS cifNo, b.fullname AS fullName, b.dateofbirth AS dateOfBirth, a.tin, a.sss, b.encodeddate AS dateEncoded,"
					+ " (SELECT desc1 FROM TBCODETABLE WHERE codename ='MEMBERSHIPTYPE' AND codevalue = a.membershiptype) AS memberType, "
					+ " (SELECT desc1 FROM TBCODETABLE WHERE codename ='MEMBERSHIPAPPLICATIONSTATUS' AND codevalue = b.cifstatus) as applicationStatus,"
					+ " b.encodedby AS encodedBy, a.bankAccountNumber,"
					+ " b.cifapproveddate AS dateApproved,"
					+ " '' AS typeOfAccount, a.referredbycifname AS solicitor"
					+ " FROM TBCIFINDIVIDUAL a"
					+ " LEFT JOIN TBCIFMAIN b ON a.cifno = b.cifno"
					+ " WHERE a.cifno = :cifno",
					params, MembershipHeaderForm.class, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return membershipHeaderForm;
	}
	
	@Override
	public String saveOrUpdateNetCapping(Tbmembernetcapping d) {
		String flag = "failed";
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(d.getAppno()!=null) {
				params.put("appno", d.getAppno());
				Tbmembernetcapping row = (Tbmembernetcapping) dbServiceCoop.executeUniqueHQLQuery
						("FROM Tbmembernetcapping WHERE appno=:appno", params);
				if(row!=null) {
					row.setBasicsalary(d.getBasicsalary());
					row.setWithholdingtax(d.getWithholdingtax());
					row.setExistingamort(d.getExistingamort());
					if(dbServiceCoop.saveOrUpdate(row)) {
						flag = "success";
					}
				}else {
					Tbmembernetcapping n = new Tbmembernetcapping();
					n.setAppno(d.getAppno());
					n.setCifno(d.getCifno());
					n.setBasicsalary(d.getBasicsalary());
					n.setWithholdingtax(d.getWithholdingtax());
					n.setExistingamort(d.getExistingamort());
					if(dbServiceCoop.save(n)) {
						flag = "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tbmembernetcapping getTbmembernetcapping(String appno) {
		Tbmembernetcapping row = new Tbmembernetcapping();
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(appno!=null) {
				params.put("appno", appno);
				row = (Tbmembernetcapping) dbServiceCoop.executeUniqueHQLQuery("FROM Tbmembernetcapping WHERE appno=:appno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}
	
	@Override
	public Tbcifemployment getTbcifemploymentTop1(String cifno) {
		Tbcifemployment row = new Tbcifemployment();
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(cifno!=null) {
				params.put("cifno", cifno);
				row = (Tbcifemployment) dbServiceCIF.execSQLQueryTransformer
						("SELECT TOP 1 * FROM TBCIFEMPLOYMENT WHERE cifno=:cifno ORDER BY employmentid desc", params, Tbcifemployment.class, 0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	@Override
	public String changeCompanyOrMemberBatchUpdateStatus(List<MembershipListPerStagesForm> memberList, String status) {
		DBService dbServiceCIF = new DBServiceImplCIF();
		ADInquiryFacade adInquiry = new ADInquiryFacade();
		NoGenerator noGenerator = new NoGenerator();
		HashMap<String, Object> params = new HashMap<String, Object>();
		
		String flag = "failed";

		try {

			for (MembershipListPerStagesForm form : memberList) {
				Tbcifmain cifMain = new Tbcifmain();
				params.put("cifNo", form.getTrn());
				String cif_no = form.getTrn();

				cifMain = (Tbcifmain) dbServiceCIF
						.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifNo and cifstatus ='4'", params);
				if (cifMain != null) {
					//Company
					if(cifMain.getCustomertype().equals("1")) {
						
						String cifOldStatus = cifMain.getCifstatus();
						cifMain.setCifoldStatus(cifOldStatus);
						cifMain.setCifoldStatusDate(new Date());
					
						//Approved
						if(status.equals("4")) {
							cifMain.setCifstatus("4");
							cifMain.setApprovedRemarks("Batch Approved Remarks");
							cifMain.setCifapprovedby(username);
							cifMain.setCifapproveddate(new Date());
							
							//Create User
							adInquiry.saveMemberCredentials(cif_no, "MemberRoles");	
						
						}
						//Cancel
						else if(status.equals("5")) {
							cifMain.setCifstatus("5");
							cifMain.setCancelRemarks("Batch Cancel Remarks");
							cifMain.setCancelledby(username);
							cifMain.setDatecancelled(new Date());
						}
						
						cifMain.setDateupdated(new Date());
						if (dbServiceCIF.saveOrUpdate(cifMain)) {
							flag = "success";
						} else {
							flag = "failed";
						}
						
					}
					
					//Membership
					if(cifMain.getCustomertype().equals("2")) {
						
						String cifOldStatus = cifMain.getCifstatus();
						cifMain.setCifoldStatus(cifOldStatus);
						cifMain.setCifoldStatusDate(new Date());
					
						//Approved
						if(status.equals("5")) {
							cifMain.setCifstatus("5");
							cifMain.setApprovedRemarks("Batch Approved Remarks");
							cifMain.setCifapprovedby(username);
							cifMain.setCifapproveddate(new Date());
							
							//Generate MemberId 
							String memberId = noGenerator.generateMemberId("Membership");
							cifMain.setMemberId(memberId);
							
							//TBCODETABLE MEMBERSTATUS 
							cifMain.setMemberStatus("1"); // Active
							
							//Create Share Capital Account Number
							cifMain.setShareCapitalAccountNumber(memberId+"SHAR");
							
							//Send Email
							adInquiry.saveMemberCredentials(cif_no, "MemberRoles");		
						
						//Declined	
						}else if(status.equals("7")) {
						cifMain.setCifstatus("7");
						cifMain.setCancelRemarks("Batch Cancel Remarks");
						cifMain.setCifdeclinedBy(username);
						cifMain.setCifdeclinedDate(new Date());
						
					}
						cifMain.setDateupdated(new Date());
						if (dbServiceCIF.saveOrUpdate(cifMain)) {
							flag = "success";
						} else {
							flag = "failed";
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return flag;
	}
}
