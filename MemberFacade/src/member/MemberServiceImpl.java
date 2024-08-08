package member;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudfoundry.org.codehaus.jackson.map.DeserializationConfig;
import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import com.coopdb.data.Tbchangeprofilehistory;
import com.coopdb.data.Tbcooperative;
import com.coopdb.data.Tbdocchecklist;
import com.coopdb.data.Tbdocpertransactiontype;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbmemberrelationship;
import com.coopdb.data.Tbmembershipapp;
import com.coopdb.data.Tbpledge;
import com.coopdb.data.Tbupdateprofilerequest;
import com.coopdb.data.Tbworkflowprocess;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.documents.DocumentServiceImpl;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.etel.utils.ImageUtils;
import com.etel.utils.TransactionNoGenerator;
import com.wavemaker.common.util.IOUtils;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.server.FileUploadResponse;

import member.forms.CapConDeposits;
import member.forms.ChangeProfileForm;
import member.forms.ReturnForm;
import member.forms.memberProfile;
import member.forms.memberUtilities;

public class MemberServiceImpl implements MemberService {

	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private ObjectMapper mapper = new ObjectMapper();
	private memberUtilities memberUtil = new memberUtilities();
	private String username = secservice.getUserName();

	@SuppressWarnings("static-access")
	@Override
	public ReturnForm updateMemberPersonalProfile(Tbmember details, String category, String source, String remarks) {
		params.put("membershipid", details.getMembershipid());
		ReturnForm values = new ReturnForm();
		values.setFlag("failed");
		values.setMessage("Problem updating profile.");
		try {
			Tbmember m = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:membershipid",
					params);
			if (m != null) {
				// address
				// String p = mapper.writeValueAsString(m);
				mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				if (category.equals("0") || category.equals("2")) {
					if (details.getCountry1() != null) {
						m.setCountry1(details.getCountry1());
					}
					if (details.getCountry2() != null) {
						m.setCountry2(details.getCountry2());
					}
					if (details.getRegion1() != null) {
						m.setRegion1(details.getRegion1());
					}
					if (details.getRegion2() != null) {
						m.setRegion2(details.getRegion2());
					}
					if (details.getStateprovince1() != null) {
						m.setStateprovince1(details.getStateprovince1());
					}
					if (details.getStateprovince2() != null) {
						m.setStateprovince2(details.getStateprovince2());
					}
					if (details.getCity1() != null) {
						m.setCity1(details.getCity1());
					}
					if (details.getCity2() != null) {
						m.setCity2(details.getCity2());
					}
					if (details.getBarangay1() != null) {
						m.setBarangay1(details.getBarangay1());
					}
					if (details.getBarangay2() != null) {
						m.setBarangay2(details.getBarangay2());
					}
					if (details.getStreetnoname1() != null) {
						m.setStreetnoname1(details.getStreetnoname1());
					}
					if (details.getStreetnoname2() != null) {
						m.setStreetnoname2(details.getStreetnoname2());
					}
					if (details.getSubdivision1() != null) {
						m.setSubdivision1(details.getSubdivision1());
					}
					if (details.getSubdivision2() != null) {
						m.setSubdivision2(details.getSubdivision2());
					}
					if (details.getPostalcode1() != null) {
						m.setPostalcode1(details.getPostalcode1());
					}
					if (details.getPostalcode2() != null) {
						m.setPostalcode2(details.getPostalcode2());
					}
					if (details.getOwnershiptype1() != null) {
						m.setOwnershiptype1(details.getOwnershiptype1());
					}
					if (details.getOwnershiptype2() != null) {
						m.setOwnershiptype2(details.getOwnershiptype2());
					}
					if (details.getOccupiedsince1() != null) {
						m.setOccupiedsince1(details.getOccupiedsince1());
					}
					if (details.getOccupiedsince2() != null) {
						m.setOccupiedsince1(details.getOccupiedsince2());
					}

					// fulladdress
					FullDataEntryServiceImpl fde = new FullDataEntryServiceImpl();
					String fulladdress1 = fde.fullAddress(details.getStreetnoname1(), details.getSubdivision1(),
							details.getBarangay1(), details.getCity1(), details.getStateprovince1(),
							details.getCountry1(), details.getRegion1(), details.getPostalcode1());
					String fulladdress2 = fde.fullAddress(details.getStreetnoname2(), details.getSubdivision2(),
							details.getBarangay2(), details.getCity2(), details.getStateprovince2(),
							details.getCountry2(), details.getRegion2(), details.getPostalcode2());

					if (!fulladdress1.isEmpty()) {
						m.setFulladdress1(fulladdress1);
					}
					if (!fulladdress2.isEmpty()) {
						m.setFulladdress2(fulladdress2);
					}

					if (details.getSameaspermanentaddress() != null) {
						m.setSameaspermanentaddress(details.getSameaspermanentaddress());
					}
				}
				// contact
				if (category.equals("1") || category.equals("2")) {
					if (details.getHomephoneareacode() != null) {
						m.setHomephoneareacode(details.getHomephoneareacode());
					}
					if (details.getHomephoneno() != null) {
						m.setHomephoneno(details.getHomephoneno());
					}
					if (details.getOfficephoneareacode() != null) {
						m.setOfficephoneareacode(details.getOfficephoneareacode());
					}
					if (details.getOfficephoneno() != null) {
						m.setOfficephoneno(details.getOfficephoneno());
					}
					if (details.getMobilephoneareacode() != null) {
						m.setMobilephoneareacode(details.getMobilephoneareacode());
					}
					if (details.getMobilephoneno() != null) {
						m.setMobilephoneno(details.getMobilephoneno());
					}
					if (details.getPhoneno() != null) {
						m.setPhoneno(details.getPhoneno());
					}
					if (details.getAreacodephone() != null) {
						m.setAreacodephone(details.getAreacodephone());
					}
				}
				if (dbService.saveOrUpdate(m)) {
					// String checkChangesAddress = "Address not updated",
					// checkChangesContact = "Contact not updated";
					// Tbmember previous = mapper.readValue(p, Tbmember.class);
					// values.setFlag("updatedMemberProfile");
					// if (category.equals("1")) {// contact
					// String checkChanges =
					// memberUtil.savePreviousInfoHistory(previous,
					// m.getMembershipid(),
					// "CONTACT", source, remarks);
					// values.setMessage(checkChanges);
					// values.setFlag("contactChangedTracked");
					// } else if (category.equals("0")) {// address
					// String checkChanges =
					// memberUtil.savePreviousInfoHistory(previous,
					// m.getMembershipid(),
					// "ADDRESS", source, remarks);
					// values.setMessage(checkChanges);
					// values.setFlag("addressChangedTracked");
					// } else if (category.equals("2")) {// both
					//// checkChangesAddress =
					// memberUtil.savePreviousInfoHistory(previous,
					// m.getMembershipid(),
					// "CONTACT", source, remarks);
					//// checkChangesContact =
					// memberUtil.savePreviousInfoHistory(previous,
					// m.getMembershipid(),
					// "ADDRESS", source, remarks);
					// values.setMessage(checkChangesAddress + "<br>" +
					// checkChangesContact);
					// values.setFlag("addressAndContactChangedTracked");
					// } else {
					// values.setMessage("Updates' history not tracked.");
					// values.setFlag("categoryUndefined");
					// }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public memberProfile getMemberProfile(String membershipid) {
		// TODO Auto-generated method stub
		memberProfile profile = new memberProfile();
		List<CapConDeposits> list = new ArrayList<CapConDeposits>();
		try {
			params.put("membershipid", membershipid);
			profile.setLoanaccountsborrower((List<Tbloans>) dbService.executeListHQLQuery("FROM Tbloans", params));
			profile.setMemberpersonalinformation((Tbmember) dbService
					.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:membershipid", params));
			profile.setMemberdeposits((List<CapConDeposits>) dbService.execSQLQueryTransformer(
					"SELECT (SELECT prodname FROM TBPRODMATRIX WHERE a.SubProductCode = prodcode) AS accounttypedeposits, "
							+ "a.AccountNo as accountnodeposits, a.AccountBalance as accountbalancedeposits "
							+ "FROM TBDEPOSIT a, TBDEPOSITCIF b WHERE a.AccountNo = b.accountno AND b.cifno=:membershipid",
					params, CapConDeposits.class, 1));
			profile.setCapitalcontibutions((List<CapConDeposits>) dbService.execSQLQueryTransformer(
					"SELECT a.AccountNo as accountnocapcon, " + "a.AccountBalance as accountbalancecapcpon "
							+ "FROM TBDEPOSIT a, TBDEPOSITCIF b WHERE a.AccountNo = b.accountno AND a.SubProductCode = '46' AND b.cifno=:membershipid",
					params, CapConDeposits.class, 1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return profile;
	}

	@SuppressWarnings("unchecked")
	@Override
	public memberProfile getMember(String membershipid) {
		// TODO Auto-generated method stub
		memberProfile profile = new memberProfile();
		try {
			params.put("membershipid", membershipid);
//			profile.setLoanaccountsborrower((List<Tbloans>) dbService.executeListHQLQuery("FROM Tbloans", params));
//			profile.setLoanaccountscomaker((List<Tbloans>) dbService.execSQLQueryTransformer(
//					"EXEC GetMemberComakerLoans @membershipid=:membershipid", params, Tbloans.class, 1));
//			profile.setLoanaccountscomaker(new ArrayList<Tbloans>());
			profile.setMemberpersonalinformation((Tbmember) dbService
					.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:membershipid", params));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return profile;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CapConDeposits> getCapCon(String membershipid) {
		List<CapConDeposits> list = new ArrayList<CapConDeposits>();
		params.put("membershipid", membershipid);
		try {
			list = (List<CapConDeposits>) dbService.execSQLQueryTransformer(
					"SELECT SUM(a.AccountBalance) as sumcapcon, a.AccountNo as accountnocapcon, "
							+ "a.AccountBalance as accountbalancecapcpon "
							+ "FROM TBDEPOSIT a, TBDEPOSITCIF b WHERE a.AccountNo = b.accountno AND a.SubProductCode = '46' AND b.cifno=:membershipid "
							+ "GROUP BY a.AccountNo, a.AccountBalance, a.SubProductCode",
					params, CapConDeposits.class, 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CapConDeposits> getDeposits(String membershipid) {
		List<CapConDeposits> list = new ArrayList<CapConDeposits>();
		params.put("membershipid", membershipid);
		try {
			list = (List<CapConDeposits>) dbService.execSQLQueryTransformer(
					"SELECT SUM(a.AccountBalance) as sumdeposits, "
							+ "(SELECT prodname FROM TBPRODMATRIX WHERE a.SubProductCode = prodcode) AS accounttypedeposits, "
							+ "a.AccountNo as accountnodeposits, a.AccountBalance as accountbalancedeposits "
							+ "FROM TBDEPOSIT a, TBDEPOSITCIF b WHERE a.AccountNo = b.accountno AND b.cifno=:membershipid "
							+ "GROUP BY a.AccountNo, a.AccountBalance, a.SubProductCode",
					params, CapConDeposits.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	@Override
	public Tbcooperative getCooperativePerID(String id) {
		// TODO Auto-generated method stub
		try {
			if (id != null) {
				params.put("coopcode", id.substring(2, 5));
				Tbcooperative c = (Tbcooperative) dbService
						.executeUniqueHQLQuery("FROM Tbcooperative WHERE coopcode=:coopcode", params);
				if (c != null) {
					return c;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BigDecimal computerCapconPledge(String monthlypayment, Integer numberofmonths, Integer totalshares) {
		// TODO Auto-generated method stub
		try {
			if (monthlypayment != null && numberofmonths != null && totalshares != null) {
				Double payment = 0.000;
				if (monthlypayment.equals("1")) {/* semi-monthly */
					payment = 0.500;
				}
				if (monthlypayment.equals("2")) {/* monthly */
					payment = 1.000;
				}
				if (monthlypayment.equals("3")) {/* quarterly */
					payment = 4.000;
				}
				if (monthlypayment.equals("4")) {/* semi-annually */
					payment = 6.000;
				}
				if (monthlypayment.equals("5")) {/* annually */
					payment = 12.000;
				}
				Double divide = totalshares.doubleValue() / numberofmonths.doubleValue();
				Double comp = divide * payment;
				AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_COMPUTE_CAPCON_PLEDGE_AMOUNT),
						"User " + username + " Computed Capcon Pledge Amount.", username, new Date(),
						AuditLogEvents.getEventModule(AuditLogEvents.M_COMPUTE_CAPCON_PLEDGE_AMOUNT));
				return new BigDecimal(comp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unused")
	@Override
	public ReturnForm updateProfile(Tbmember fields, String changecategory, String remarks, String source,
			Integer refno) {
		// TODO Auto-generated method stub
		params.put("membershipid", fields.getMembershipid());
		ReturnForm form = new ReturnForm();
		form.setFlag("undefined");
		form.setMessage("Something's wrong in the process.");
		/* fieldtype values were default based from the codetable */
		String fieldtype = "", oldvalue = "", newvalue = "";
		StringBuilder str = new StringBuilder("possible errors : ");
		boolean saved = false;
		try {
			if (fields.getMembershipid() != null) {
				Tbmember m = (Tbmember) dbService
						.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:membershipid", params);
				if (m != null) {
					Integer count = memberUtil.updateCount(m.getMembershipid(), changecategory);
					if (changecategory.equals("0")) {
						/* address */
						if (fields.getCountry1() != null) {
							if (!fields.getCountry1().equals(m.getCountry1())) {
								oldvalue = m.getCountry1();
								newvalue = fields.getCountry1();
								m.setCountry1(fields.getCountry1());
								fieldtype = "7";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("present country, ");
								}
							}
						}
						if (fields.getCountry2() != null) {
							if (!fields.getCountry2().equals(m.getCountry2())) {
								oldvalue = m.getCountry2();
								newvalue = fields.getCountry2();
								m.setCountry2(fields.getCountry2());
								fieldtype = "18";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append(" permanent country, ");
								}
							}
						}
						if (fields.getRegion1() != null) {
							if (!fields.getRegion1().equals(m.getRegion1())) {
								oldvalue = m.getRegion1();
								newvalue = fields.getRegion1();
								m.setRegion1(fields.getRegion1());
								fieldtype = "6";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("present region, ");
								}
							}
						}
						if (fields.getRegion2() != null) {
							if (!fields.getRegion2().equals(m.getRegion2())) {
								oldvalue = m.getRegion2();
								newvalue = fields.getRegion2();
								m.setRegion2(fields.getRegion2());
								fieldtype = "17";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("permanent region, ");
								}
							}
						}
						if (fields.getStateprovince1() != null) {
							if (!fields.getStateprovince1().equals(m.getStateprovince1())) {
								oldvalue = m.getStateprovince1();
								newvalue = fields.getStateprovince1();
								m.setStateprovince1(fields.getStateprovince1());
								fieldtype = "4";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("present state province, ");
								}
							}
						}
						if (fields.getStateprovince2() != null) {
							if (!fields.getStateprovince2().equals(m.getStateprovince2())) {
								oldvalue = m.getStateprovince2();
								newvalue = fields.getStateprovince2();
								m.setStateprovince2(fields.getStateprovince2());
								fieldtype = "15";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("permanent state province, ");
								}
							}
						}
						if (fields.getCity1() != null) {
							if (!fields.getCity1().equals(m.getCity1())) {
								oldvalue = m.getCity1();
								newvalue = fields.getCity1();
								m.setCity1(fields.getCity1());
								fieldtype = "5";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("present city, ");
								}
							}
						}
						if (fields.getCity2() != null) {
							if (!fields.getCity2().equals(m.getCity2())) {
								oldvalue = m.getCity2();
								newvalue = fields.getCity2();
								m.setCity2(fields.getCity2());
								fieldtype = "16";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("permanent city, ");
								}
							}
						}
						if (fields.getBarangay1() != null) {
							if (!fields.getBarangay1().equals(m.getBarangay1())) {
								oldvalue = m.getBarangay1();
								newvalue = fields.getBarangay1();
								m.setBarangay1(fields.getBarangay1());
								fieldtype = "3";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("present barangay, ");
								}
							}
						}
						if (fields.getBarangay2() != null) {
							if (!fields.getBarangay2().equals(m.getBarangay2())) {
								oldvalue = m.getBarangay2();
								newvalue = fields.getBarangay2();
								m.setBarangay2(fields.getBarangay2());
								fieldtype = "14";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("permanent barangay, ");
								}
							}
						}
						if (fields.getStreetnoname1() != null) {
							if (!fields.getStreetnoname1().equals(m.getStreetnoname1())) {
								oldvalue = m.getStreetnoname1();
								newvalue = fields.getStreetnoname1();
								m.setStreetnoname1(fields.getStreetnoname1());
								fieldtype = "1";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("present street, ");
								}
							}
						}
						if (fields.getStreetnoname2() != null) {
							if (!fields.getStreetnoname2().equals(m.getStreetnoname2())) {
								oldvalue = m.getStreetnoname2();
								newvalue = fields.getStreetnoname2();
								m.setStreetnoname2(fields.getStreetnoname2());
								fieldtype = "12";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("permanent street, ");
								}
							}
						}
						if (fields.getSubdivision1() != null) {
							if (!fields.getSubdivision1().equals(m.getSubdivision1())) {
								oldvalue = m.getSubdivision1();
								newvalue = fields.getSubdivision1();
								m.setSubdivision1(fields.getSubdivision1());
								fieldtype = "2";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("present subdivision, ");
								}
							}
						}
						if (fields.getSubdivision2() != null) {
							if (!fields.getSubdivision2().equals(m.getSubdivision2())) {
								oldvalue = m.getSubdivision2();
								newvalue = fields.getSubdivision2();
								m.setSubdivision2(fields.getSubdivision2());
								fieldtype = "13";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("permanent subdivision, ");
								}
							}
						}
						if (fields.getPostalcode1() != null) {
							if (!fields.getPostalcode1().equals(m.getPostalcode1())) {
								oldvalue = m.getPostalcode1();
								newvalue = fields.getPostalcode1();
								m.setPostalcode1(fields.getPostalcode1());
								fieldtype = "8";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("present postalcode, ");
								}
							}
						}
						if (fields.getPostalcode2() != null) {
							if (!fields.getPostalcode2().equals(m.getPostalcode2())) {
								oldvalue = m.getPostalcode2();
								newvalue = fields.getPostalcode2();
								m.setPostalcode2(fields.getPostalcode2());
								fieldtype = "19";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("permanent postalcode, ");
								}
							}
						}
						if (fields.getOccupiedsince1() != null) {
							if (!fields.getOccupiedsince1().equals(m.getOccupiedsince1())) {
								oldvalue = m.getOccupiedsince1().toString();
								newvalue = fields.getOccupiedsince1().toString();
								m.setOccupiedsince1(fields.getOccupiedsince1());
								fieldtype = "9";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("present address occupational date, ");

								}
							}
						}
						if (fields.getOccupiedsince2() != null) {
							if (!fields.getOccupiedsince2().equals(m.getOccupiedsince2())) {
								oldvalue = m.getOccupiedsince2().toString();
								newvalue = fields.getOccupiedsince2().toString();
								m.setOccupiedsince2(fields.getOccupiedsince2());
								fieldtype = "20";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("permanent address occupational date, ");
								}
							}
						}
						if (fields.getOwnershiptype1() != null) {
							if (!fields.getOwnershiptype1().equals(m.getOwnershiptype1())) {
								oldvalue = m.getOwnershiptype1();
								newvalue = fields.getOwnershiptype1();
								m.setOwnershiptype1(fields.getOwnershiptype1());
								fieldtype = "10";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("present address ownership, ");
								}
							}
						}
						if (fields.getOwnershiptype2() != null) {
							if (!fields.getOwnershiptype2().equals(m.getOwnershiptype2())) {
								oldvalue = m.getOwnershiptype2();
								newvalue = fields.getOwnershiptype2();
								m.setOwnershiptype2(fields.getOwnershiptype2());
								fieldtype = "21";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Address details updated.");
								} else {
									form.setFlag("failed");
									str.append("permanent address ownership, ");
									form.setOptionalvalue("error in the process");
								}
							}
						}
					}
					if (changecategory.equals("1")) {
						/* contact */
						if (fields.getHomephoneareacode() != null) {
							if (!fields.getHomephoneareacode().equals(m.getHomephoneareacode())) {
								oldvalue = m.getHomephoneareacode();
								newvalue = fields.getHomephoneareacode();
								m.setHomephoneareacode(fields.getHomephoneareacode());
								fieldtype = "22";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Contact details updated.");
								} else {
									form.setFlag("failed");
									str.append("home phone number area code, ");
								}
							}
						}
						if (fields.getHomephoneno() != null) {
							if (!fields.getHomephoneno().equals(m.getHomephoneno())) {
								oldvalue = m.getHomephoneno();
								newvalue = fields.getHomephoneno();
								m.setHomephoneno(fields.getHomephoneno());
								fieldtype = "23";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Contact details updated.");
								} else {
									form.setFlag("failed");
									str.append("home phone number, ");
								}
							}
						}
						if (fields.getOfficephoneareacode() != null) {
							if (!fields.getOfficephoneareacode().equals(m.getOfficephoneareacode())) {
								oldvalue = m.getOfficephoneareacode();
								newvalue = fields.getOfficephoneareacode();
								m.setOfficephoneareacode(fields.getOfficephoneareacode());
								fieldtype = "26";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Contact details updated.");
								} else {
									form.setFlag("failed");
									str.append("office phone number area code, ");
								}
							}
						}
						if (fields.getOfficephoneno() != null) {
							if (!fields.getOfficephoneno().equals(m.getOfficephoneno())) {
								oldvalue = m.getOfficephoneno();
								newvalue = fields.getOfficephoneno();
								m.setOfficephoneno(fields.getOfficephoneno());
								fieldtype = "27";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Contact details updated.");
								} else {
									form.setFlag("failed");
									str.append("office phone number, ");
								}
							}
						}
						if (fields.getMobilephoneareacode() != null) {
							if (!fields.getMobilephoneareacode().equals(m.getMobilephoneareacode())) {
								oldvalue = m.getMobilephoneareacode();
								newvalue = fields.getMobilephoneareacode();
								m.setMobilephoneareacode(fields.getMobilephoneareacode());
								fieldtype = "24";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Contact details updated.");
								} else {
									form.setFlag("failed");
									str.append("mobile phone number area code, ");
								}
							}
						}
						if (fields.getMobilephoneno() != null) {
							if (!fields.getMobilephoneno().equals(m.getMobilephoneno())) {
								oldvalue = m.getMobilephoneno();
								newvalue = fields.getMobilephoneno();
								m.setMobilephoneno(fields.getMobilephoneno());
								fieldtype = "25";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Contact details updated.");
								} else {
									form.setFlag("failed");
									str.append("mobile phone number, ");
								}
							}
						}
						if (fields.getEmailaddress() != null) {
							if (!fields.getEmailaddress().equals(m.getEmailaddress())) {
								oldvalue = m.getEmailaddress();
								newvalue = fields.getEmailaddress();
								m.setEmailaddress(fields.getEmailaddress());
								fieldtype = "28";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Contact details updated.");
								} else {
									form.setFlag("failed");
									str.append("email address, ");
								}
							}
						}
					}
					if (changecategory.equals("2")) {
						/* name */
						if (fields.getFirstname() != null) {
							if (!fields.getFirstname().equals(m.getFirstname())) {
								oldvalue = m.getFirstname();
								newvalue = fields.getFirstname();
								m.setFirstname(fields.getFirstname());
								fieldtype = "30";
								m.setMembername(m.getLastname() + " " + m.getFirstname() + " " + m.getMiddlename());
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Name details updated.");
								} else {
									form.setFlag("failed");
									str.append("first name, ");
								}
							}
						}
						if (fields.getLastname() != null) {
							if (!fields.getLastname().equals(m.getLastname())) {
								oldvalue = m.getLastname();
								newvalue = fields.getLastname();
								m.setLastname(fields.getLastname());
								fieldtype = "29";
								m.setMembername(m.getLastname() + " " + m.getFirstname() + " " + m.getMiddlename());
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Name details updated.");
								} else {
									form.setFlag("failed");
									str.append("last name, ");
								}
							}
						}
						if (fields.getMiddlename() != null) {
							if (!fields.getMiddlename().equals(m.getMiddlename())) {
								oldvalue = m.getMiddlename();
								newvalue = fields.getMiddlename();
								m.setMiddlename(fields.getMiddlename());
								fieldtype = "31";
								m.setMembername(m.getLastname() + " " + m.getFirstname() + " " + m.getMiddlename());
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Name details updated.");
								} else {
									form.setFlag("failed");
									str.append("middle name, ");
								}
							}
						}
					}
					if (changecategory.equals("3")) {
						/* date of birth */
						if (fields.getDateofbirth() != null) {
							if (!fields.getDateofbirth().equals(m.getDateofbirth())) {
								oldvalue = m.getDateofbirth().toString();
								newvalue = fields.getDateofbirth().toString();
								m.setDateofbirth(fields.getDateofbirth());
								fieldtype = "32";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Date of birth updated.");
								} else {
									form.setFlag("failed");
									str.append("date of birth, ");
								}
							}
						}
					}
					if (changecategory.equals("4")) {
						/* civil status */
						if (fields.getCivilstatus() != null) {
							if (!fields.getCivilstatus().equals(m.getCivilstatus())) {
								oldvalue = m.getCivilstatus();
								newvalue = fields.getCivilstatus();
								m.setCivilstatus(fields.getCivilstatus());
								fieldtype = "33";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Civil status updated.");
								} else {
									form.setFlag("failed");
									str.append("civil status, ");
								}
							}
						}
					}
					if (changecategory.equals("5")) {
						/* membership status */
						if (fields.getMembershipstatus() != null) {
							if (!fields.getMembershipstatus().equals(m.getMembershipstatus())) {
								oldvalue = m.getMembershipstatus();
								newvalue = fields.getMembershipstatus();
								m.setMembershipstatus(fields.getMembershipstatus());
								fieldtype = "34";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Membership status updated.");
								} else {
									form.setFlag("failed");
									str.append("membership status, ");
								}
							}
						}
					}
					if (changecategory.equals("6")) {
						/* membership type */
						if (fields.getMembershipclass() != null) {
							if (!fields.getMembershipclass().equals(m.getMembershipclass())) {
								oldvalue = m.getMembershipclass();
								newvalue = fields.getMembershipclass();
								m.setMembershipclass(fields.getMembershipclass());
								fieldtype = "35";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("Membership class updated.");
								} else {
									form.setFlag("failed");
									str.append("membership class, ");
								}
							}
						}
					}
					if (changecategory.equals("7")) {
						/* branch */
						if (fields.getBranch() != null) {
							if (!fields.getBranch().equals(m.getBranch())) {
								oldvalue = m.getBranch();
								newvalue = fields.getBranch();
								m.setBranch(fields.getBranch());
								fieldtype = "36";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("branch updated.");
								} else {
									form.setFlag("failed");
									str.append("branch, ");
								}
							}
						}
					}
					if (changecategory.equals("8")) {
						/* company code / bos */
						if (fields.getCompanycode() != null) {
							if (!fields.getCompanycode().equals(m.getCompanycode())) {
								oldvalue = m.getCompanycode();
								newvalue = fields.getCompanycode();
								m.setCompanycode(fields.getCompanycode());
								fieldtype = "37";
								if (dbService.saveOrUpdate(m) == memberUtil.profileChanged(fieldtype,
										m.getMembershipid(), oldvalue, newvalue, source, remarks, changecategory, refno,
										count)) {
									form.setFlag("success");
									form.setMessage("company code updated.");
								} else {
									form.setFlag("failed");
									str.append("company code, ");
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		if (str.toString().substring(str.length() - 2, str.length()).equals(",")) {
			String s = str.toString().replace(str.toString().substring(str.length() - 2, str.length()), "");
			form.setOptionalvalue(s);
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ReturnForm updateProfileRequest(Tbupdateprofilerequest details, String membershipid, String changecategory,
			String status) {
		// TODO Auto-generated method stub
		params.put("id", 4);
		String statusdesc = "UNDEFINED";
		ReturnForm form = new ReturnForm();
		form.setFlag("failed");
		form.setMessage("Problem updating request");
		try {
			List<Tbworkflowprocess> list = (List<Tbworkflowprocess>) dbService
					.executeListHQLQuery("FROM Tbworkflowprocess WHERE workflowid=:id", params);
			if (list != null) {
				for (Tbworkflowprocess w : list) {
					if (Integer.parseInt(status) == w.getId().getSequenceno()) {
						statusdesc = w.getProcessname();
					}
				}
			}
			if (status != null) {
				params.put("memberid", membershipid);
				params.put("changecategory", changecategory);

				if (status.equals("1")) {
					// doc submission
					if (membershipid != null) {
						Tbupdateprofilerequest e = (Tbupdateprofilerequest) dbService.executeUniqueHQLQuery(
								"FROM Tbupdateprofilerequest WHERE memberid=:memberid AND changecategorytype=:changecategory AND txstatus!='3'",
								params);
						Tbmember mem = (Tbmember) dbService
								.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:memberid", params);
						if (e != null && mem != null) {
							form.setFlag("existing");
							form.setMessage("<b>" + membershipid + "</b> has an on-going request '<b>" + e.getTxrefno()
									+ "</b>' in this category.");
						} else {
							if (details.getFirstname() != null) {
								String f = details.getFirstname().toUpperCase();
								details.setFirstname(f);
							}
							if (details.getMiddlename() != null) {
								String m = details.getMiddlename().toUpperCase();
								details.setMiddlename(m);
							}
							if (details.getLastname() != null) {
								String l = details.getLastname().toUpperCase();
								details.setLastname(l);
							}
							if (details.getFirstname() != null) {
								if (details.getFirstname().equals(mem.getFirstname())) {
									details.setFirstname(null);
								}
							}
							if (details.getMiddlename() != null) {
								if (details.getMiddlename().equals(mem.getMiddlename())) {
									details.setMiddlename(null);
								}
							}
							if (details.getLastname() != null) {
								if (details.getLastname().equals(mem.getLastname())) {
									details.setLastname(null);
								}
							}
							details.setTxrefno(TransactionNoGenerator.generateTransactionNo("TRANSACTION"));
							details.setTxstatus(status);
							details.setDaterequested(new Date());
							details.setTxdate(new Date());
							details.setRequestedby(secservice.getUserName());
							details.setTxcode("070");
							if (dbService.save(details)) {
								Map<String, String> docparams = new HashMap<String, String>();
								docparams.put("txrefno", details.getTxrefno().toString());
								docparams.put("membershipid", membershipid);
								docparams.put("reqtype", changecategory);
								DocumentServiceImpl.createInitialDocumentChecklist("070", docparams);
								form.setFlag("success");
								form.setOptionalvalue(details.getTxrefno().toString());
								form.setMessage("Request successfully created.");
							}
						}
					}
				}
				if (status.equals("2")) {
					// for approval
					if (details.getTxrefno() != null) {
						params.put("txrefno", details.getTxrefno());
						Tbupdateprofilerequest e = (Tbupdateprofilerequest) dbService
								.executeUniqueHQLQuery("FROM Tbupdateprofilerequest WHERE txrefno=:txrefno", params);
						if (e != null) {
							if (details.getFirstname() != null) {
								e.setFirstname(details.getFirstname());
							}
							if (details.getMiddlename() != null) {
								e.setMiddlename(details.getMiddlename());
							}
							if (details.getLastname() != null) {
								e.setLastname(details.getLastname());
							}
							if (details.getDateofbirth() != null) {
								e.setDateofbirth(details.getDateofbirth());
							}
							if (details.getCivilstatus() != null) {
								e.setCivilstatus(details.getCivilstatus());
							}
							if (details.getMembershipclass() != null) {
								e.setMembershipclass(details.getMembershipclass());
							}
							if (details.getMembershipstatus() != null) {
								e.setMembershipstatus(details.getMembershipstatus());
							}
							if (details.getBranch() != null) {
								e.setBranch(details.getBranch());
							}
							if (details.getCompanycode()!=null){
								e.setCompanycode(details.getCompanycode());
							}
							
							e.setTxstatus(status);
							if (dbService.saveOrUpdate(e)) {
								form.setFlag("success");
								form.setMessage("Update Request is successfully submitted <b>" + statusdesc + "</b>.");
							}
						}
					}
				}
				if (status.equals("3")) {
					// approved
					if (details.getTxrefno() != null) {
						params.put("txrefno", details.getTxrefno());
						Tbupdateprofilerequest e = (Tbupdateprofilerequest) dbService
								.executeUniqueHQLQuery("FROM Tbupdateprofilerequest WHERE txrefno=:txrefno", params);
						Tbmember m = new Tbmember();
						m.setMembershipid(e.getMemberid());
						m.setFirstname(e.getFirstname());
						m.setMiddlename(e.getMiddlename());
						m.setLastname(e.getLastname());
						m.setDateofbirth(e.getDateofbirth());
						m.setCivilstatus(e.getCivilstatus());
						
						m.setMembershipclass(e.getMembershipclass());
						m.setMembershipstatus(e.getMembershipstatus());
						m.setBranch(e.getBranch());
						m.setCompanycode(e.getCompanycode());
						
						if (updateProfile(m, e.getChangecategorytype(), e.getApprovalremarks(), details.getSource(),
								details.getTxrefno()).getFlag().equals("success")) {
							e.setApprovedby(secservice.getUserName());
							e.setApprovedt(new Date());
							e.setTxstatus(status);
							if (dbService.saveOrUpdate(e)) {
								form.setFlag("success");
								form.setMessage("Request <b>" + details.getTxrefno() + "</b> has been <b>" + statusdesc
										+ "</b>.");
							}
						}
					}
				}
				if (status.equals("4")) {
					// declined
					if (details.getTxrefno() != null) {
						params.put("txrefno", details.getTxrefno());
						Integer s = (Integer) dbService.executeUpdate(
								"UPDATE Tbupdateprofilerequest SET txstatus='" + status + "' WHERE txrefno=:txrefno",
								params);
						if (s > 0 && s != null) {
							form.setFlag("success");
							form.setMessage(
									"Request <b>" + details.getTxrefno() + "</b> has been <b>" + statusdesc + "</b>.");
						}
					}
				}
				if (status.equals("5")) {
					// cancelled
					if (details.getTxrefno() != null) {
						params.put("txrefno", details.getTxrefno());
						Integer s = (Integer) dbService.executeUpdate(
								"UPDATE Tbupdateprofilerequest SET txstatus='" + status + "' WHERE txrefno=:txrefno",
								params);
						if (s > 0 && s != null) {
							form.setFlag("success");
							form.setMessage(
									"Request <b>" + details.getTxrefno() + "</b> has been <b>" + statusdesc + "</b>.");
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public Tbupdateprofilerequest getProfileUpdateRequest(Integer txrefno) {
		// TODO Auto-generated method stub
		try {
			params.put("txrefno", txrefno);
			if (txrefno != null) {
				Tbupdateprofilerequest r = (Tbupdateprofilerequest) dbService.execSQLQueryTransformer(
						"SELECT txrefno, txdate, txcode, lastname, firstname, middlename, dateofbirth, memberid, id, "
								+ "(SELECT desc1 FROM Tbcodetable WHERE codename='CHANGECATEGORY' and codevalue=changecategorytype) as changecategorytype, "
								+ "(SELECT processname FROM Tbworkflowprocess WHERE workflowid='4' AND sequenceno=txstatus) as txstatus, "
								+ "source, civilstatus, daterequested, requestedby, requestremarks, approvedby, approvedt, approvalremarks, membershipstatus, membershipclass, branch, companycode FROM Tbupdateprofilerequest WHERE txrefno=:txrefno",
						params, Tbupdateprofilerequest.class, 0);
				if (r != null) {
					return r;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String saveProfileRequestAsDraft(Tbupdateprofilerequest req) {
		// TODO Auto-generated method stub
		try {
			params.put("txrefno", req.getTxrefno());
			if (req.getTxrefno() != null) {
				Tbupdateprofilerequest e = (Tbupdateprofilerequest) dbService
						.executeUniqueHQLQuery("FROM Tbupdateprofilerequest WHERE txrefno=:txrefno", params);
				if (e != null) {
					if (req.getFirstname() != null) {
						e.setFirstname(req.getFirstname());
					}
					if (req.getLastname() != null) {
						e.setLastname(req.getLastname());
					}
					if (req.getMiddlename() != null) {
						e.setMiddlename(req.getMiddlename());
					}
					if (req.getDateofbirth() != null) {
						e.setDateofbirth(req.getDateofbirth());
					}
					if (req.getCivilstatus() != null) {
						e.setCivilstatus(req.getCivilstatus());
					}
					e.setRequestremarks(req.getRequestremarks());
					e.setApprovalremarks(req.getApprovalremarks());
					e.setSource(req.getSource());
					if (dbService.saveOrUpdate(e)) {
						return "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "failed";
	}

	@SuppressWarnings({ "unchecked", "null" })
	@Override
	public List<Tbdocchecklist> getProfileUpdateDocuments(Integer txrefno, String membershipid) {
		// TODO Auto-generated method stub
		List<Tbdocchecklist> doclist = new ArrayList<Tbdocchecklist>();
		try {
			params.put("txrefno", txrefno);
			params.put("txcode", "070");
			if (txrefno != null && membershipid != null) {
				doclist = (List<Tbdocchecklist>) dbService
						.executeListHQLQuery("FROM Tbdocchecklist WHERE txcode=:txcode AND txrefno=:txrefno", params);
				if (doclist != null || doclist.size() < 1) {
					return doclist;
				} else {
					List<Tbdocpertransactiontype> list = (List<Tbdocpertransactiontype>) dbService
							.executeListHQLQuery("FROM Tbdocpertransactiontype WHERE txcode=:txcode", params);
					if (list != null) {
						for (Tbdocpertransactiontype d : list) {
							Tbdocchecklist n = new Tbdocchecklist();
							n.setMembershipid(membershipid);
							n.setTxrefno(txrefno);
							n.setTxcode(d.getTxcode());
							n.setDocumentcode(d.getDocumentcode());
							n.setDocumentname(d.getDocumentname());
							if (dbService.save(n)) {
								doclist.add(n);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return doclist;
	}

	@Override
	public String updateProfileUpdateDocuments(Tbdocchecklist doc) {
		// TODO Auto-generated method stub
		try {
			params.put("txrefno", doc.getTxrefno());
			params.put("txcode", doc.getTxcode());
			params.put("documentcode", doc.getDocumentcode());
			params.put("membershipid", doc.getMembershipid());
			params.put("id", doc.getId());
			StringBuilder q = new StringBuilder("FROM Tbdocchecklist WHERE ");
			if (doc.getTxrefno() != null) {
				q.append("txrefno=:txrefno AND ");
			}
			if (doc.getTxcode() != null) {
				q.append("txcode=:txcode AND ");
			}
			if (doc.getDocumentcode() != null) {
				q.append("documentcode=:documentcode AND ");
			}
			if (doc.getMembershipid() != null) {
				q.append("membershipid=:membershipid AND ");
			}
			if (doc.getId() != null) {
				q.append("id=:id AND ");
			}
			if (doc.getTxrefno() != null) {
				Tbdocchecklist d = (Tbdocchecklist) dbService
						.executeUniqueHQLQuery(q.toString().substring(0, q.length() - 5), params);
				if (d != null) {
					if (doc.getIssubmitted() != null) {
						if (!doc.getIssubmitted().equals(d.getIssubmitted())) {
							if (doc.getIssubmitted()) {
								d.setDatesubmitted(doc.getDatesubmitted());
							}
							if (!doc.getIssubmitted()) {
								d.setDatesubmitted(null);
							}
						}
					}
					if (doc.getIsreviewed() != null) {
						if (!doc.getIsreviewed().equals(d.getIsreviewed())) {
							if (doc.getIsreviewed()) {
								d.setDatereviewed(new Date());
							} else {
								d.setDatereviewed(null);
							}
						}
					}
					d.setIssubmitted(doc.getIssubmitted());
					d.setIsreviewed(doc.getIsreviewed());
					d.setIsrequired(doc.getIsrequired());
					if (dbService.saveOrUpdate(d)) {
						return "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "failed";
	}

	@Override
	public Tbpledge getPledgeDetails(String memberid) {
		// TODO Auto-generated method stub
		try {
			params.put("id", memberid);
			Tbpledge t = (Tbpledge) dbService.executeUniqueHQLQuery("FROM Tbpledge WHERE membershipid=:id", params);
			if (t != null) {
				return t;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public String saveMember2x2Photo(String directory, String appid, String memberid, String tempfile) {
		// TODO Auto-generated method stub
		try {
//			System.out.println(directory);
			params.put("appid", appid);
			params.put("memberid", memberid);
			if (directory != null) {
				String picture = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(directory);
				if (appid != null) {
					Tbmembershipapp app = (Tbmembershipapp) dbService
							.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:appid", params);
					if (app != null) {
						app.setPicture(ImageUtils.imageToBase64(picture));
						if (dbService.saveOrUpdate(app)) {
//							File f = new File(picture);
//							if(f.exists()) {
//								f.delete();
//								return getUploadedPhoto(null, appid);
//							}
							return "success";
						}
					}
				}
				if (memberid != null) {

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
			// TODO: handle exception
		}
		return "failed";
	}

	@Override
	public String getUploadedPhoto(String memberid, String appid) {
		// TODO Auto-generated method stub
		try {
			params.put("appid", appid);
			params.put("memberid", memberid);
			if (appid != null) {
				Tbmembershipapp app = (Tbmembershipapp) dbService
						.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:appid", params);
				if (app.getPicture() != null && !app.getPicture().equals("")) {
					String dir = RuntimeAccess.getInstance().getSession().getServletContext()
							.getRealPath("resources/data/upload/" + app.getMembershipappid() + ".jpg");
					File f = new File(dir);
					if (f.exists()) {
						f.delete();
						String cnvrt = ImageUtils.base64ToImage(app.getPicture(), dir);
						if (cnvrt != null && cnvrt.equals("success")) {
							return "resources/data/upload/" + app.getMembershipappid() + ".jpg";
						}
					} else {
						String cnvrt = ImageUtils.base64ToImage(app.getPicture(), dir);
						if (cnvrt != null && cnvrt.equals("success")) {
							return "resources/data/upload/" + app.getMembershipappid() + ".jpg";
						}
					}
				}
//				System.out.println(m.getPicture());
//				System.out.println(ImageUtils.base64ToImage(app.getPicture(), RuntimeAccess.getInstance().getSession()
//						.getServletContext().getRealPath("resources/tempdir/test.jpg")));
			}
			if (memberid != null) {
				Tbmember app = (Tbmember) dbService
						.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:memberid", params);
				if (app.getPicture() != null && !app.getPicture().equals("")) {
					String dir = RuntimeAccess.getInstance().getSession().getServletContext()
							.getRealPath("resources/data/upload/" + app.getMembershipid() + ".jpg");
					File f = new File(dir);
					if (f.exists()) {
						f.delete();
						String cnvrt = ImageUtils.base64ToImage(app.getPicture(), dir);
						if (cnvrt != null && cnvrt.equals("success")) {
							return "resources/data/upload/" + app.getMembershipid() + ".jpg";
						}
					} else {
						String cnvrt = ImageUtils.base64ToImage(app.getPicture(), dir);
						if (cnvrt != null && cnvrt.equals("success")) {
							return "resources/data/upload/" + app.getMembershipid() + ".jpg";
						}
					}
				}
//				System.out.println(m.getPicture());
//				System.out.println(ImageUtils.base64ToImage(m.getPicture(), RuntimeAccess.getInstance().getSession().getServletContext()
//						.getRealPath("resources/tempdir/test.jpg")));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
			// TODO: handle exception
		}
		return "failed";
	}

	@Override
	public Tbmember getMemberViaID(String membershipid) {
		// TODO Auto-generated method stub
		try {
			params.put("membershipid", membershipid);
			Tbmember m = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:membershipid",
					params);
			if (m != null) {
				return m;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public String deleteMemberRelation(Integer relid) {
		// TODO Auto-generated method stub
		try {
			params.put("relid", relid);
			if (relid != null) {
				Tbmemberrelationship r = (Tbmemberrelationship) dbService
						.executeUniqueHQLQuery("FROM Tbmemberrelationship WHERE relid=:relid", params);
				if (r != null) {
					if (dbService.delete(r))
						return "success";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChangeProfileForm> searchChangeProfileHistory(String search, String changeCategory,
			String changeFieldType) {
	
		Map<String, Object> params = HQLUtil.getMap();
		params.put("search",search + "%%");
		params.put("cat", changeCategory);
		params.put("type", changeFieldType);
	
		System.out.println(search);
		System.out.println(changeCategory);
		System.out.println(changeFieldType);
		List<ChangeProfileForm> prof = new ArrayList<ChangeProfileForm>();
		prof = (List<ChangeProfileForm>) dbService.execSQLQueryTransformer("select A.memberid,B.membername,A.changecategorytype,A.changefieldtype,A.updatedby,A.dateupdated,A.updatecount,A.updateremarks,A.oldvalue,A.newvalue from TBCHANGEPROFILEHISTORY AS A INNER JOIN TBMEMBER AS B ON A.memberid = B.membershipid where A.memberid like:search  or B.membername like:search and A.changecategorytype =:cat and A.changefieldtype=:type",
				params, ChangeProfileForm.class, 1);
		System.out.println(prof.size());
		return prof;
	}

	String uploadDir = "";

	protected File getUploadDir() throws IOException {
		if (uploadDir.length() == 0) {
			uploadDir = RuntimeAccess.getInstance().getSession().getServletContext()
					.getRealPath("resources/data/upload");
		}
		File f = new File(uploadDir);
		f.mkdirs();
		f.createNewFile();
		return f;
	}
	
	@Override
	public FileUploadResponse upload2x2MemberPicture(MultipartFile file) {
		// Create our return object
		FileUploadResponse ret = new FileUploadResponse();
		try {
			/* Find our upload directory, make sure it exists */
			File dir = getUploadDir();
			if (!dir.exists())
				dir.mkdirs();

			/*
			 * Create a file object that does not point to an existing file. Loop through
			 * names until we find a filename not already in use
			 */
			String filename = file.getOriginalFilename(); /*
															 * .replaceAll("[^a-zA-Z0-9 ._-]" ,"");
															 */
			boolean hasExtension = filename.indexOf(".") != -1;
			String name = (hasExtension) ? filename.substring(0, filename.lastIndexOf(".")) : filename;
			String ext = (hasExtension) ? filename.substring(filename.lastIndexOf(".")) : "";
			File outputFile = new File(dir, filename);
			for (int i = 0; i < 10000 && outputFile.exists(); i++) {
				outputFile = new File(dir, name + i + ext);
			}

			/* resize to 2x2 */
//			BufferedImage inputImage = ImageIO.read(outputFile);
//			inputImage = ImageResizer.resize(inputImage, 96, 96);
//			ImageResizer.resize(outputFile.getPath(), outputFile.getPath(), 96, 96);

			/* Write the file to the filesystem */
			FileOutputStream fos = new FileOutputStream(outputFile);
			IOUtils.copy(file.getInputStream(), fos);
			file.getInputStream().close();
			fos.close();

			/* Setup the return object */
			ret.setPath(outputFile.getPath());
			ret.setError("");
			ret.setWidth("");
			ret.setHeight("");
		} catch (Exception e) {
			System.out.println("ERROR:" + e.getMessage() + " | " + e.toString());
			ret.setError(e.getMessage());
		}
		return ret;
	}
	
	@Override
	public Tbmember getMemberProfileInquiry(String membershipid) {
		// TODO Auto-generated method stub
		/*
		 * query member details with descriptions - this is for read only/ viewing
		 * purposes
		 */
		try {
			params.put("membershipid", membershipid);
			Tbmember m = (Tbmember) dbService.execSQLQueryTransformer(memberUtil.getMemberDetailsQueryReadOnly(),
					params, Tbmember.class, 0);
			if (m != null) {
				return m;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
