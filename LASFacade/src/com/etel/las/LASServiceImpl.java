package com.etel.las;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbbankaccounts;
import com.cifsdb.data.TbbankaccountsId;
import com.cifsdb.data.Tbcifbusiness;
import com.cifsdb.data.Tbcifcorporate;
import com.cifsdb.data.Tbcifdependents;
import com.cifsdb.data.Tbcifemployment;
import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbcreditcardinfo;
import com.cifsdb.data.Tbexistingloansother;
import com.cifsdb.data.Tbpersonalreference;
import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbevaldetails;
import com.coopdb.data.Tbloancollateral;
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tblstappbusiness;
import com.coopdb.data.Tblstappcorporate;
import com.coopdb.data.Tblstappdependents;
import com.coopdb.data.Tblstappemployment;
import com.coopdb.data.Tblstappindividual;
import com.coopdb.data.Tblstapppersonalreference;
import com.coopdb.data.Tblstbankaccounts;
import com.coopdb.data.Tblstcreditcardinfo;
import com.coopdb.data.Tblstexistingloansother;
import com.coopdb.data.Tbuser;
import com.coopdb.data.Tbworkflowprocess;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.dataentry.FullDataEntryService;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.email.EmailCode;
import com.etel.email.EmailService;
import com.etel.email.EmailServiceImpl;
import com.etel.forms.FormValidation;
import com.etel.lasform.SearchCIFForm;
import com.etel.lasform.SearchLOSForm;
import com.etel.utils.CIFNoGenerator;
import com.etel.utils.HQLUtil;
import com.loansdb.data.Tbotheraccounts;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class LASServiceImpl implements LASService {
	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> param = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	Map<String, Object> params = new HashMap<String, Object>();

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

	@Override
	public String saveOrUpdateLstappIndiv(Tblstappindividual d) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (d.getAppno() != null) {
				param.put("appno", d.getAppno());
				Tblstappindividual row = (Tblstappindividual) dbServiceCOOP
						.executeUniqueHQLQuery("FROM Tblstappindividual where appno=:appno", param);
				if (row != null) {
					// Basic Details
					row.setTitle(d.getTitle());
					row.setFirstname(d.getFirstname());
					row.setLastname(d.getLastname());
					row.setMiddlename(d.getMiddlename());
					row.setSuffix(d.getSuffix());
					row.setShortname(d.getShortname());
					row.setPreviouslastname(d.getPreviouslastname());
					row.setGender(d.getGender());
					row.setCivilstatus(d.getCivilstatus());
					row.setDateofbirth(d.getDateofbirth());
					row.setAge(d.getAge());
					row.setCountryofbirth(d.getCountryofbirth());
					row.setStateofbirth(d.getStateofbirth());
					row.setCityofbirth(d.getCityofbirth());
					row.setNationality(d.getNationality());
					row.setDualcitizen(d.getDualcitizen());
					row.setOthernationality(d.getOthernationality());
					row.setResident(d.getResident());
					row.setAcrnumber(d.getAcrnumber());
					row.setTin(d.getTin());
					row.setSss(d.getSss());
					row.setGsis(d.getGsis());

					// Contact Details
					row.setCountrycodemobile(d.getCountrycodemobile());
					row.setAreacodemobile(d.getAreacodemobile());
					row.setMobilenumber(d.getMobilenumber());

					row.setCountrycodephone(d.getCountrycodephone());
					row.setAreacodephone(d.getAreacodephone());
					row.setHomephoneno(d.getHomephoneno());

					row.setCountrycodefax(d.getCountrycodefax());
					row.setAreacodefax(d.getAreacodefax());
					row.setFaxnumber(d.getFaxnumber());

					row.setEmailaddress(d.getEmailaddress());
					row.setContacttype1(d.getContacttype1());
					row.setContactvalue1(d.getContactvalue1());
					row.setContacttype2(d.getContacttype2());
					row.setContactvalue2(d.getContactvalue2());

					// Address Details
					row.setStreetno1(d.getStreetno1());
					row.setSubdivision1(d.getSubdivision1());
					row.setCountry1(d.getCountry1());
					row.setProvince1(d.getProvince1());
					row.setCity1(d.getCity1());
					row.setBarangay1(d.getBarangay1());
					row.setPostalcode1(d.getPostalcode1());
					row.setHomeownership1(d.getHomeownership1());
					row.setOccupiedsince1(d.getOccupiedsince1());
					row.setIssameaddress1(d.getIssameaddress1());

					row.setStreetno2(d.getStreetno2());
					row.setSubdivision2(d.getSubdivision2());
					row.setCountry2(d.getCountry2());
					row.setProvince2(d.getProvince2());
					row.setCity2(d.getCity2());
					row.setBarangay2(d.getBarangay2());
					row.setPostalcode2(d.getPostalcode2());
					row.setHomeownership2(d.getHomeownership2());
					row.setOccupiedsince2(d.getOccupiedsince2());
					row.setIssameaddress2(d.getIssameaddress2());

					row.setMailingaddress(d.getMailingaddress());

					row.setFulladdress1(fullAddress(d.getStreetno1(), d.getSubdivision1(), d.getBarangay1(),
							d.getCity1(), d.getProvince1(), d.getCountry1(), d.getPostalcode1()));

					row.setFulladdress2(fullAddress(d.getStreetno2(), d.getSubdivision2(), d.getBarangay2(),
							d.getCity2(), d.getProvince2(), d.getCountry2(), d.getPostalcode2()));

					// Spouse Parents
					row.setSpousetitle(d.getSpousetitle());
					row.setSpouselastname(d.getSpouselastname());
					row.setSpousefirstname(d.getSpousefirstname());
					row.setSpousemiddlename(d.getSpousemiddlename());
					row.setSpousesuffix(d.getSpousesuffix());
					row.setFathertitle(d.getFathertitle());
					row.setFatherlastname(d.getFatherlastname());
					row.setFathermiddlename(d.getFathermiddlename());
					row.setFathersuffix(d.getFathersuffix());
					row.setMothertitle(d.getMothertitle());
					row.setMotherlastname(d.getMotherlastname());
					row.setMothermiddlename(d.getMothermiddlename());
					row.setMothermaidenmname(d.getMothermaidenmname());
					row.setBeneficiarytitle(d.getBeneficiarytitle());
					row.setBeneficiarylastname(d.getBeneficiarylastname());
					row.setBeneficiaryfirstname(d.getBeneficiaryfirstname());
					row.setBeneficiarysuffix(d.getBeneficiarysuffix());
					row.setBeneficiarydateofbirth(d.getBeneficiarydateofbirth());
					row.setRelationtoborrower(d.getRelationtoborrower());

					if (dbServiceCOOP.saveOrUpdate(row)) {
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
	public Tblstappindividual getTblstappindividual(String appno) {
		Tblstappindividual row = new Tblstappindividual();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (appno != null) {
				param.put("appno", appno);
				row = (Tblstappindividual) dbServiceCOOP
						.executeUniqueHQLQuery("FROM Tblstappindividual WHERE appno=:appno", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	@Override
	public String saveOrUpdateDependents(Tblstappdependents d) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (d.getAppno() != null) {
				if (d.getDependentid() != null) {
					// update
					param.put("id", d.getDependentid());
					param.put("appno", d.getAppno());
					Tblstappdependents row = (Tblstappdependents) dbServiceCOOP.executeUniqueHQLQuery(
							"FROM Tblstappdependents WHERE dependentid=:id AND appno=:appno", param);
					if (row != null) {
						row.setFullname(d.getFullname());
						row.setGender(d.getGender());
						row.setDateofbirth(d.getDateofbirth());
						row.setAge(d.getAge());
						row.setAddress(d.getAddress());
						row.setIssameasfulladdress1(d.getIssameasfulladdress1());
						row.setIssameasfulladdress2(d.getIssameasfulladdress2());
						row.setRelationship(d.getRelationship());
						row.setOtherrelationship(d.getOtherrelationship());
						if (dbServiceCOOP.saveOrUpdate(row)) {
							flag = "success";
						}
					}
				} else {
					// add
					Tblstappdependents n = new Tblstappdependents();
					n.setAppno(d.getAppno());
					n.setFullname(d.getFullname());
					n.setGender(d.getGender());
					n.setDateofbirth(d.getDateofbirth());
					n.setAge(d.getAge());
					n.setAddress(d.getAddress());
					n.setIssameasfulladdress1(d.getIssameasfulladdress1());
					n.setIssameasfulladdress2(d.getIssameasfulladdress2());
					n.setRelationship(d.getRelationship());
					n.setOtherrelationship(d.getOtherrelationship());
					if (dbServiceCOOP.save(n)) {
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
	public List<Tblstappdependents> listTblstappdependents(String appno) {
		List<Tblstappdependents> list = new ArrayList<Tblstappdependents>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (appno != null) {
				param.put("appno", appno);
				list = (List<Tblstappdependents>) dbServiceCOOP
						.executeListHQLQuery("FROM Tblstappdependents WHERE appno=:appno", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteDependent(Integer id) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (id != null) {
				param.put("id", id);
				Tblstappdependents row = (Tblstappdependents) dbServiceCOOP
						.executeUniqueHQLQuery("FROM Tblstappdependents WHERE dependentid=:id", param);
				if (dbServiceCOOP.delete(row)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateEmployment(Tblstappemployment d) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (d.getAppno() != null) {
				if (d.getEmploymentid() != null) {
					// update
					param.put("id", d.getEmploymentid());
					param.put("appno", d.getAppno());
					Tblstappemployment row = (Tblstappemployment) dbServiceCOOP.executeUniqueHQLQuery(
							"FROM Tblstappemployment WHERE employmentid=:id AND appno=:appno", param);
					if (row != null) {
						row.setEmployername(d.getEmployername());
						row.setEmprecordtype(d.getEmprecordtype());
						row.setEmployeraddress(d.getEmployeraddress());
						row.setEmployerphoneno(d.getEmployerphoneno());
						row.setBusinesstype(d.getBusinesstype());
						row.setPsiccode(d.getPsiccode());
						row.setPsoccode(d.getPsoccode());
						row.setPosition(d.getPosition());
						row.setPositioncategory(d.getPositioncategory());
						row.setEmpstatus(d.getEmpstatus());
						row.setDatehiredfrom(d.getDatehiredfrom());
						row.setDatehiredto(d.getDatehiredto());
						row.setIspresent(d.getIspresent());
						row.setYearsofservice(d.getYearsofservice());
						row.setGrossincome(d.getGrossincome());
						if (dbServiceCOOP.saveOrUpdate(row)) {
							flag = "success";
						}
					}
				} else {
					// add
					Tblstappemployment n = new Tblstappemployment();
					n.setAppno(d.getAppno());
					n.setEmprecordtype(d.getEmprecordtype());
					n.setEmployername(d.getEmployername());
					n.setEmployeraddress(d.getEmployeraddress());
					n.setEmployerphoneno(d.getEmployerphoneno());
					n.setBusinesstype(d.getBusinesstype());
					n.setPsiccode(d.getPsiccode());
					n.setPsoccode(d.getPsoccode());
					n.setPosition(d.getPosition());
					n.setPositioncategory(d.getPositioncategory());
					n.setEmpstatus(d.getEmpstatus());
					n.setDatehiredfrom(d.getDatehiredfrom());
					n.setDatehiredto(d.getDatehiredto());
					n.setIspresent(d.getIspresent());
					n.setYearsofservice(d.getYearsofservice());
					n.setGrossincome(d.getGrossincome());
					if (dbServiceCOOP.save(n)) {
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
	public List<Tblstappemployment> listTblstappemployment(String appno) {
		List<Tblstappemployment> list = new ArrayList<Tblstappemployment>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (appno != null) {
				param.put("appno", appno);
				list = (List<Tblstappemployment>) dbServiceCOOP
						.executeListHQLQuery("FROM Tblstappemployment WHERE appno=:appno", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteEmployment(Integer id) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (id != null) {
				param.put("id", id);
				Tblstappemployment row = (Tblstappemployment) dbServiceCOOP
						.executeUniqueHQLQuery("FROM Tblstappemployment WHERE employmentid=:id", param);
				if (dbServiceCOOP.delete(row)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateReference(Tblstapppersonalreference d) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (d.getAppno() != null) {
				if (d.getId() != null) {
					// update
					param.put("id", d.getId());
					param.put("appno", d.getAppno());
					Tblstapppersonalreference row = (Tblstapppersonalreference) dbServiceCOOP.executeUniqueHQLQuery(
							"FROM Tblstapppersonalreference WHERE id=:id AND appno=:appno", param);
					if (row != null) {
						row.setPersonalrefname(d.getPersonalrefname());
						row.setEmployername(d.getEmployername());
						row.setEmployeraddress(d.getEmployeraddress());
						row.setContactno(d.getContactno());
						row.setRelationship(d.getRelationship());
						if (dbServiceCOOP.saveOrUpdate(row)) {
							flag = "success";
						}
					}
				} else {
					// add
					Tblstapppersonalreference n = new Tblstapppersonalreference();
					n.setAppno(d.getAppno());
					n.setPersonalrefname(d.getPersonalrefname());
					n.setEmployername(d.getEmployername());
					n.setEmployeraddress(d.getEmployeraddress());
					n.setContactno(d.getContactno());
					n.setRelationship(d.getRelationship());
					if (dbServiceCOOP.save(n)) {
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
	public List<Tblstapppersonalreference> listTblstappsonalreference(String appno) {
		List<Tblstapppersonalreference> list = new ArrayList<Tblstapppersonalreference>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (appno != null) {
				param.put("appno", appno);
				list = (List<Tblstapppersonalreference>) dbServiceCOOP
						.executeListHQLQuery("FROM Tblstapppersonalreference WHERE appno=:appno", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteReference(Integer id) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (id != null) {
				param.put("id", id);
				Tblstapppersonalreference row = (Tblstapppersonalreference) dbServiceCOOP
						.executeUniqueHQLQuery("FROM Tblstapppersonalreference WHERE id=:id", param);
				if (dbServiceCOOP.delete(row)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateExistingLoan(Tblstexistingloansother d) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (d.getAppno() != null) {
				if (d.getId() != null) {
					// update
					param.put("id", d.getId());
					param.put("appno", d.getAppno());
					Tblstexistingloansother row = (Tblstexistingloansother) dbServiceCOOP
							.executeUniqueHQLQuery("FROM Tblstexistingloansother WHERE id=:id AND appno=:appno", param);
					if (row != null) {
						row.setAppno(d.getAppno());
						row.setBank(d.getBank());
						row.setLoantype(d.getLoantype());
						row.setOutstandingbal(d.getOutstandingbal());
						row.setAmortizationamt(d.getAmortizationamt());
						if (dbServiceCOOP.saveOrUpdate(row)) {
							flag = "success";
						}
					}
				} else {
					// add
					Tblstexistingloansother n = new Tblstexistingloansother();
					n.setAppno(d.getAppno());
					n.setBank(d.getBank());
					n.setLoantype(d.getLoantype());
					n.setOutstandingbal(d.getOutstandingbal());
					n.setAmortizationamt(d.getAmortizationamt());
					if (dbServiceCOOP.save(n)) {
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
	public List<Tblstexistingloansother> listTblstexistingloansother(String appno) {
		List<Tblstexistingloansother> list = new ArrayList<Tblstexistingloansother>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (appno != null) {
				param.put("appno", appno);
				list = (List<Tblstexistingloansother>) dbServiceCOOP
						.executeListHQLQuery("FROM Tblstexistingloansother WHERE appno=:appno", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteExistingLoan(Integer id) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (id != null) {
				param.put("id", id);
				Tblstexistingloansother row = (Tblstexistingloansother) dbServiceCOOP
						.executeUniqueHQLQuery("FROM Tblstexistingloansother WHERE id=:id", param);
				if (dbServiceCOOP.delete(row)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateBankAccount(Tblstbankaccounts d) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (d.getAppno() != null) {
				if (d.getId() != null) {
					// update
					param.put("id", d.getId());
					param.put("appno", d.getAppno());
					Tblstbankaccounts row = (Tblstbankaccounts) dbServiceCOOP
							.executeUniqueHQLQuery("FROM Tblstbankaccounts WHERE id=:id AND appno=:appno", param);
					if (row != null) {
						row.setAppno(d.getAppno());
						row.setBank(d.getBank());
						row.setBankaccttype(d.getBankaccttype());
						row.setAccountrefno(d.getAccountrefno());
						row.setOutstandingbal(d.getOutstandingbal());
						if (dbServiceCOOP.saveOrUpdate(row)) {
							flag = "success";
						}
					}
				} else {
					// add
					Tblstbankaccounts n = new Tblstbankaccounts();
					n.setAppno(d.getAppno());
					n.setBank(d.getBank());
					n.setBankaccttype(d.getBankaccttype());
					n.setAccountrefno(d.getAccountrefno());
					n.setOutstandingbal(d.getOutstandingbal());
					if (dbServiceCOOP.save(n)) {
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
	public List<Tblstbankaccounts> listTblstbankaccounts(String appno) {
		List<Tblstbankaccounts> list = new ArrayList<Tblstbankaccounts>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (appno != null) {
				param.put("appno", appno);
				list = (List<Tblstbankaccounts>) dbServiceCOOP
						.executeListHQLQuery("FROM Tblstbankaccounts WHERE appno=:appno", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteBankAccount(Integer id) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (id != null) {
				param.put("id", id);
				Tblstbankaccounts row = (Tblstbankaccounts) dbServiceCOOP
						.executeUniqueHQLQuery("FROM Tblstbankaccounts WHERE id=:id", param);
				if (dbServiceCOOP.delete(row)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateCreditCard(Tblstcreditcardinfo d) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (d.getAppno() != null) {
				if (d.getId() != null) {
					// update
					param.put("id", d.getId());
					param.put("appno", d.getAppno());
					Tblstcreditcardinfo row = (Tblstcreditcardinfo) dbServiceCOOP
							.executeUniqueHQLQuery("FROM Tblstcreditcardinfo WHERE id=:id AND appno=:appno", param);
					if (row != null) {
						row.setBank(d.getBank());
						row.setAppno(d.getAppno());
						row.setCardno(d.getCardno());
						row.setCreditlimit(d.getCreditlimit());
						if (dbServiceCOOP.saveOrUpdate(row)) {
							flag = "success";
						}
					}
				} else {
					// add
					Tblstcreditcardinfo n = new Tblstcreditcardinfo();
					n.setAppno(d.getAppno());
					n.setBank(d.getBank());
					n.setCardno(d.getCardno());
					n.setCreditlimit(d.getCreditlimit());
					if (dbServiceCOOP.save(n)) {
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
	public List<Tblstcreditcardinfo> listTblstcreditcardinfo(String appno) {
		List<Tblstcreditcardinfo> list = new ArrayList<Tblstcreditcardinfo>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (appno != null) {
				param.put("appno", appno);
				list = (List<Tblstcreditcardinfo>) dbServiceCOOP
						.executeListHQLQuery("FROM Tblstcreditcardinfo WHERE appno=:appno", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteCreditCard(Integer id) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (id != null) {
				param.put("id", id);
				Tblstcreditcardinfo row = (Tblstcreditcardinfo) dbServiceCOOP
						.executeUniqueHQLQuery("FROM Tblstcreditcardinfo WHERE id=:id", param);
				if (dbServiceCOOP.delete(row)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateBusiness(Tblstappbusiness d) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (d.getAppno() != null) {
				if (d.getBusinessid() != null) {
					// update
					param.put("id", d.getBusinessid());
					param.put("appno", d.getAppno());
					Tblstappbusiness row = (Tblstappbusiness) dbServiceCOOP.executeUniqueHQLQuery(
							"FROM Tblstappbusiness WHERE businessid=:id AND appno=:appno", param);
					if (row != null) {
						row.setAppno(d.getAppno());
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
						if (dbServiceCOOP.saveOrUpdate(row)) {
							flag = "success";
						}
					}
				} else {
					// add
					Tblstappbusiness n = new Tblstappbusiness();
					n.setAppno(d.getAppno());
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
					if (dbServiceCOOP.save(n)) {
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
	public List<Tblstappbusiness> listTblstappbusiness(String appno) {
		List<Tblstappbusiness> list = new ArrayList<Tblstappbusiness>();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (appno != null) {
				param.put("appno", appno);
				list = (List<Tblstappbusiness>) dbServiceCOOP
						.executeListHQLQuery("FROM Tblstappbusiness WHERE appno=:appno", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteBusiness(Integer id) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (id != null) {
				param.put("id", id);
				Tblstappbusiness row = (Tblstappbusiness) dbServiceCOOP
						.executeUniqueHQLQuery("FROM Tblstappbusiness WHERE businessid=:id", param);
				if (dbServiceCOOP.delete(row)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveOrUpdateHeader(Tblstapp d) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (d.getAppno() != null) {
				param.put("appno", d.getAppno());
				Tblstapp row = (Tblstapp) dbServiceCOOP.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno",
						param);
				if (row != null) {
					row.setApplicationdate(d.getApplicationdate());
					if (dbServiceCOOP.saveOrUpdate(row)) {
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
	public String createCIFRecordForNonExistingClients(String appno) {
		// TODO Auto-generated method stub
		String flag = "failed";
		String cifno = "";
		Tbcifcorporate cifcorp = new Tbcifcorporate();
		Tbcifindividual cifindiv = new Tbcifindividual();
		Tbcifmain cifmain = new Tbcifmain();
		DBService dbServiceCIF = new DBServiceImplCIF();
		try {
			if (appno != null) {
				param.put("appno", appno);
				Tblstapp lstapp = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp where appno=:appno", param);
				Tbaccountinfo accinfo = (Tbaccountinfo) dbService
						.executeUniqueHQLQuery("FROM Tbaccountinfo where applno=:appno", param);
				if (lstapp != null) {
					if (!lstapp.getIsexisting()) {
						if (lstapp.getCustomertype().equals("1")) {
							cifno = CIFNoGenerator.generateCIFNo("INDIVIDUAL");
							Tblstappindividual losindiv = (Tblstappindividual) dbService
									.executeUniqueHQLQuery("FROM Tblstappindividual where appno=:appno", param);
							if (losindiv != null) {
								// Basic Details
								cifindiv.setCifno(cifno);
								cifindiv.setTitle(losindiv.getTitle());
								cifindiv.setFirstname(losindiv.getFirstname());
								cifindiv.setLastname(losindiv.getLastname());
								cifindiv.setMiddlename(losindiv.getMiddlename());
								cifindiv.setSuffix(losindiv.getSuffix());
								cifindiv.setShortname(losindiv.getShortname());
								cifindiv.setGender(losindiv.getGender());
								cifindiv.setCivilstatus(losindiv.getCivilstatus());
								cifindiv.setDateofbirth(losindiv.getDateofbirth());
								cifindiv.setAge(losindiv.getAge());
								cifindiv.setCountryofbirth(losindiv.getCountryofbirth());
								cifindiv.setCityofbirth(losindiv.getCityofbirth());
								cifindiv.setNationality(losindiv.getNationality());
								cifindiv.setDualcitizen(losindiv.getDualcitizen());
								cifindiv.setOthernationality(losindiv.getOthernationality());
								cifindiv.setResident(losindiv.getResident());
								cifindiv.setAcrnumber(losindiv.getAcrnumber());
								cifindiv.setTin(losindiv.getTin());
								cifindiv.setSss(losindiv.getSss());
								cifindiv.setGsis(losindiv.getGsis());
								// Contact Details
								cifindiv.setMobilenumber(losindiv.getMobilenumber());
								cifindiv.setHomephoneno(losindiv.getHomephoneno());
								cifindiv.setFaxnumber(losindiv.getFaxnumber());
								cifindiv.setEmailaddress(losindiv.getEmailaddress());
								cifindiv.setContacttype1(losindiv.getContacttype1());
								cifindiv.setContacttype2(losindiv.getContacttype1());
								cifindiv.setContactvalue1(losindiv.getContactvalue1());
								cifindiv.setContactvalue2(losindiv.getContactvalue2());
								// Address Information
								cifindiv.setStreetno1(losindiv.getStreetno1());
								cifindiv.setSubdivision1(losindiv.getSubdivision1());
								cifindiv.setCountry1(losindiv.getCountry1());
								cifindiv.setProvince1(losindiv.getProvince1());
								cifindiv.setCity1(losindiv.getCity1());
								cifindiv.setBarangay1(losindiv.getBarangay1());
								cifindiv.setPostalcode1(losindiv.getPostalcode1());
								cifindiv.setHomeownership1(losindiv.getHomeownership1());
								cifindiv.setOccupiedsince1(losindiv.getOccupiedsince1());
								cifindiv.setIssameaddress1(losindiv.getIssameaddress1());
								cifindiv.setFulladdress1(losindiv.getFulladdress1());

								cifindiv.setStreetno2(losindiv.getStreetno2());
								cifindiv.setSubdivision2(losindiv.getSubdivision2());
								cifindiv.setCountry2(losindiv.getCountry2());
								cifindiv.setProvince2(losindiv.getProvince2());
								cifindiv.setCity2(losindiv.getCity2());
								cifindiv.setBarangay2(losindiv.getBarangay2());
								cifindiv.setPostalcode2(losindiv.getPostalcode2());
								cifindiv.setHomeownership2(losindiv.getHomeownership2());
								cifindiv.setOccupiedsince2(losindiv.getOccupiedsince2());
								cifindiv.setIssameaddress2(losindiv.getIssameaddress2());
								cifindiv.setFulladdress2(losindiv.getFulladdress2());
								// Spouse Parent Information
								cifindiv.setSpousecifno(losindiv.getSpousecifno());
								cifindiv.setSpousetitle(losindiv.getSpousetitle());
								cifindiv.setSpouselastname(losindiv.getSpouselastname());
								cifindiv.setSpousefirstname(losindiv.getSpousefirstname());
								cifindiv.setSpousemiddlename(losindiv.getSpousemiddlename());
								cifindiv.setSpousesuffix(losindiv.getSpousesuffix());
								cifindiv.setFathercifno(losindiv.getFathercifno());
								cifindiv.setFathertitle(losindiv.getFathertitle());
								cifindiv.setFatherlastname(losindiv.getFatherlastname());
								cifindiv.setFatherfirstname(losindiv.getFatherfirstname());
								cifindiv.setFathermiddlename(losindiv.getFathermiddlename());
								cifindiv.setFathersuffix(losindiv.getFathersuffix());
								cifindiv.setMothercifno(losindiv.getMothercifno());
								cifindiv.setMothertitle(losindiv.getMothertitle());
								cifindiv.setMotherlastname(losindiv.getMotherlastname());
								cifindiv.setMothermiddlename(losindiv.getMothermiddlename());
								cifindiv.setMotherfirstname(losindiv.getMotherfirstname());
								cifindiv.setMothersuffix(losindiv.getMothersuffix());
								cifindiv.setBeneficiarytitle(losindiv.getBeneficiarytitle());
								cifindiv.setBeneficiarylastname(losindiv.getBeneficiarylastname());
								cifindiv.setBeneficiarymiddlename(losindiv.getBeneficiarymiddlename());
								cifindiv.setBeneficiaryfirstname(losindiv.getBeneficiaryfirstname());
								cifindiv.setBeneficiarysuffix(losindiv.getBeneficiarysuffix());
								cifindiv.setBeneficiarydateofbirth(losindiv.getBeneficiarydateofbirth());
								cifindiv.setBeneficiarycifno(losindiv.getCifno());
								cifindiv.setRelationtoborrower(losindiv.getRelationtoborrower());
								cifindiv.setAppno(appno);

								cifmain.setCifno(cifno);
								cifmain.setFullname(lstapp.getCifname());
								cifmain.setCustomertype(lstapp.getCustomertype());
								cifmain.setEncodeddate(new Date());
								cifmain.setEncodedby(secservice.getUserName());
								cifmain.setCifapproveddate(new Date());
								cifmain.setCifstatus("1");
								cifmain.setIsencoding(false);
								cifmain.setCiftype("0");
								cifmain.setCifstatusdate(new Date());
								cifmain.setCifapprovedby(secservice.getUserName());
								cifmain.setDateofbirth(losindiv.getDateofbirth());
								cifmain.setAssignedto(secservice.getUserName());
								cifmain.setDateupdated(new Date());
								param.put("user", secservice.getUserName());
								Tbuser user = (Tbuser) dbService
										.executeUniqueHQLQuery("FROM Tbuser where username=:user", param);
								if (user != null) {
									cifmain.setOriginatingbranch(user.getBranchcode());
								}
								cifmain.setFulladdress1(fullAddress(losindiv.getStreetno1(), losindiv.getSubdivision1(),
										losindiv.getBarangay1(), losindiv.getCity1(), losindiv.getProvince1(),
										losindiv.getCountry1(), losindiv.getPostalcode1()));

								cifmain.setFulladdress2(fullAddress(losindiv.getStreetno2(), losindiv.getSubdivision2(),
										losindiv.getBarangay2(), losindiv.getCity2(), losindiv.getProvince2(),
										losindiv.getCountry2(), losindiv.getPostalcode2()));
							}
							// Dependents
							List<Tblstappdependents> losdep = (List<Tblstappdependents>) dbService
									.executeListHQLQuery("FROM Tblstappdependents where appno=:appno", param);
							if (losdep != null) {
								for (Tblstappdependents a : losdep) {
									Tbcifdependents cifdep = new Tbcifdependents();
									cifdep.setCifno(cifno);
									cifdep.setFullname(a.getFullname());
									cifdep.setGender(a.getGender());
									cifdep.setDateofbirth(a.getDateofbirth());
									cifdep.setAddress(a.getAddress());
									cifdep.setIssameasfulladdress1(a.getIssameasfulladdress1());
									cifdep.setIssameasfulladdress2(a.getIssameasfulladdress2());
									cifdep.setRelationship(a.getRelationship());
									cifdep.setOtherrelationship(a.getOtherrelationship());
									dbServiceCIF.saveOrUpdate(cifdep);
								}
							}

							// Employment
							List<Tblstappemployment> losemp = (List<Tblstappemployment>) dbService
									.executeListHQLQuery("FROM Tblstappemployment where appno=:appno", param);
							if (losemp != null) {
								for (Tblstappemployment a : losemp) {
									Tbcifemployment cifemp = new Tbcifemployment();
									cifemp.setCifno(cifno);
									cifemp.setEmployername(a.getEmployername());
									cifemp.setEmprecordtype(a.getEmprecordtype());
									cifemp.setEmployeeno(a.getEmployeeno());
									cifemp.setEmployeraddress(a.getEmployeraddress());
									cifemp.setEmployerphoneno(a.getEmployerphoneno());
									cifemp.setBusinesstype(a.getBusinesstype());
									cifemp.setPsiccode(a.getPsiccode());
									cifemp.setPsoccode(a.getPsoccode());
									cifemp.setPosition(a.getPosition());
									cifemp.setPositioncategory(a.getPositioncategory());
									cifemp.setEmpstatus(a.getEmpstatus());
									cifemp.setDatehiredfrom(a.getDatehiredfrom());
									cifemp.setDatehiredto(a.getDatehiredto());
									cifemp.setIspresent(a.getIspresent());
									cifemp.setYearsofservice(a.getYearsofservice());
									cifemp.setGrossincome(a.getGrossincome());
									dbServiceCIF.saveOrUpdate(cifemp);
								}
							}
							// Business
							List<Tblstappbusiness> losbus = (List<Tblstappbusiness>) dbService
									.executeListHQLQuery("FROM Tblstappbusiness where appno=:appno", param);
							if (losbus != null) {
								for (Tblstappbusiness a : losbus) {
									Tbcifbusiness cifbus = new Tbcifbusiness();
									cifbus.setCifno(cifno);
									cifbus.setBusinessname(a.getBusinessname());
									cifbus.setFulladdress1(a.getFulladdress1());
									cifbus.setBusinessclass(a.getBusinessclass());
									cifbus.setNatureofbusiness(a.getNatureofbusiness());
									cifbus.setBusinessphoneno(a.getBusinessphoneno());
									cifbus.setEmailaddress(a.getEmailaddress());
									cifbus.setGrossincome(a.getGrossincome());
									dbServiceCIF.saveOrUpdate(cifbus);

								}
							}
							// Personal Reference
							List<Tblstapppersonalreference> lospref = (List<Tblstapppersonalreference>) dbService
									.executeListHQLQuery("FROM Tblstapppersonalreference where appno=:appno", param);
							if (lospref != null) {
								for (Tblstapppersonalreference a : lospref) {
									Tbpersonalreference cifpref = new Tbpersonalreference();
									cifpref.setCifno(cifno);
									cifpref.setPersonalrefname(a.getPersonalrefname());
									cifpref.setEmployername(a.getEmployername());
									cifpref.setEmployeraddress(a.getEmployeraddress());
									cifpref.setContactno(a.getContactno());
									cifpref.setRelationship(a.getRelationship());
									dbServiceCIF.saveOrUpdate(cifpref);

								}
							}
//							// Other Loans
//							List<Tblstexistingloansother> losloans = (List<Tblstexistingloansother>) dbService
//									.executeListHQLQuery("FROM Tblstexistingloansother where appno=:appno", param);
//							if (losloans != null) {
//								for (Tblstexistingloansother a : losloans) {
//									Tbexistingloansother cifloans = new Tbexistingloansother();
//									cifloans.setCifno(cifno);
//									cifloans.setBank(a.getBank());
//									cifloans.setLoantype(a.getLoantype());
//									cifloans.setOutstandingbal(a.getOutstandingbal());
//									cifloans.setAmortizationamt(a.getAmortizationamt());
//									dbServiceCIF.saveOrUpdate(cifloans);
//
//								}
//							}
//							// Bank Accounts
//							List<Tblstbankaccounts> losbanks = (List<Tblstbankaccounts>) dbService
//									.executeListHQLQuery("FROM Tblstbankaccounts where appno=:appno", param);
//							if (losbanks != null) {
//								for (Tblstbankaccounts a : losbanks) {
//									Tbbankaccounts cifbanks = new Tbbankaccounts();
//									TbbankaccountsId id = new TbbankaccountsId();
//									id.setCifno(cifno);
//									id.setAccountrefno(a.getAccountrefno());
//									cifbanks.setId(id);
//									cifbanks.setBankname(a.getBank());
//									cifbanks.setAccounttype(a.getBankaccttype());
//									cifbanks.setOutstandingbal(a.getOutstandingbal());
//									dbServiceCIF.saveOrUpdate(cifbanks);
//
//								}
//							}
//							// Bank Accounts
//							List<Tblstcreditcardinfo> loscred = (List<Tblstcreditcardinfo>) dbService
//									.executeListHQLQuery("FROM Tblstcreditcardinfo where appno=:appno", param);
//							if (loscred != null) {
//								for (Tblstcreditcardinfo a : loscred) {
//									Tbcreditcardinfo cifcred = new Tbcreditcardinfo();
//									cifcred.setBank(a.getBank());
//									cifcred.setCardno(a.getCardno());
//									cifcred.setCreditlimit(a.getCreditlimit());
//									cifcred.setCifno(cifno);
//									dbServiceCIF.saveOrUpdate(cifcred);
//								}
//							}
							// Other Bank Accounts
							List<Tbotheraccounts> losotheraccounts = (List<Tbotheraccounts>) dbService
									.executeListHQLQuery("FROM Tbotheraccounts where appno=:appno", param);
							if (losotheraccounts != null) {
								for (int x = 0; x < losotheraccounts.size(); x++) {
									losotheraccounts.get(x).setCifno(cifno);
								}
							}
							if (dbServiceCIF.saveOrUpdate(cifindiv)) {
								lstapp.setCifno(cifno);
								accinfo.setClientid(cifno);
								dbServiceCIF.saveOrUpdate(cifmain);
								dbService.saveOrUpdate(accinfo);
								if (dbService.saveOrUpdate(lstapp)) {
									flag = "success";
								}
							}
						}

						else if (lstapp.getCustomertype().equals("2")) {
							cifno = CIFNoGenerator.generateCIFNo("CORPORATE");
							Tblstappcorporate loscorp = (Tblstappcorporate) dbService
									.executeUniqueHQLQuery("FROM Tblstappcorporate where appno=:appno", param);
							if (loscorp != null) {
								// Basic Details
								cifcorp.setCifno(cifno);
								cifcorp.setCorporatename(loscorp.getCorporatename());
								cifcorp.setCiftype(loscorp.getCiftype());
								cifcorp.setTradename(loscorp.getTradename());
								cifcorp.setDateofincorporation(loscorp.getDateofincorporation());
								cifcorp.setFirmsize(loscorp.getFirmsize());
								cifcorp.setNationality(loscorp.getNationality());
								cifcorp.setResidenceclassification(loscorp.getResidenceclassification());
								cifcorp.setNoofemployees(loscorp.getNoofemployees());
								cifcorp.setRegistrationtype(loscorp.getRegistrationtype());
								cifcorp.setRegistrationtype2(loscorp.getRegistrationtype2());
								cifcorp.setRegistrationno(loscorp.getRegistrationno());
								cifcorp.setRegistrationno(loscorp.getRegistrationno2());
								cifcorp.setExpirydate(loscorp.getExpirydate());
								cifcorp.setExpirydate1(loscorp.getExpirydate1());
								cifcorp.setExpirydate2(loscorp.getExpirydate2());
								cifcorp.setTin(loscorp.getTin());
								cifcorp.setSss(loscorp.getSss());
								cifcorp.setTermofexistence(loscorp.getTermofexistence());
								cifcorp.setPsic(loscorp.getPsic());
								cifcorp.setPsiccode(loscorp.getPsiccode());
								cifcorp.setGrossincome(loscorp.getGrossincome());
								cifcorp.setNettaxableincome(loscorp.getNettaxableincome());
								cifcorp.setMonthlyexpenses(loscorp.getMonthlyincomesale());
								cifcorp.setMonthlyincomesale(loscorp.getMonthlyincomesale());
								cifcorp.setBusinesstype(loscorp.getBusinesstype());
								cifcorp.setPaidupcapital(loscorp.getPaidupcapital());

								// Address Information
								cifcorp.setStreetno1(loscorp.getStreetno1());
								cifcorp.setSubdivision1(loscorp.getSubdivision1());
								cifcorp.setCountry1(loscorp.getCountry1());
								cifcorp.setProvince1(loscorp.getProvince1());
								cifcorp.setCity1(loscorp.getCity1());
								cifcorp.setBarangay1(loscorp.getBarangay1());
								cifcorp.setPostalcode1(loscorp.getPostalcode1());
								cifcorp.setHomeownership1(loscorp.getHomeownership1());
								cifcorp.setOccupiedsince1(loscorp.getOccupiedsince1());
								cifcorp.setIssameaddress1(loscorp.getIssameaddress1());
								cifcorp.setFulladdress1(loscorp.getFulladdress1());

								cifcorp.setStreetno2(loscorp.getStreetno2());
								cifcorp.setSubdivision2(loscorp.getSubdivision2());
								cifcorp.setCountry2(loscorp.getCountry2());
								cifcorp.setProvince2(loscorp.getProvince2());
								cifcorp.setCity2(loscorp.getCity2());
								cifcorp.setBarangay2(loscorp.getBarangay2());
								cifcorp.setPostalcode2(loscorp.getPostalcode2());
								cifcorp.setHomeownership2(loscorp.getHomeownership2());
								cifcorp.setOccupiedsince2(loscorp.getOccupiedsince2());
								cifcorp.setFulladdress2(loscorp.getFulladdress2());

								// Contact Details
								cifcorp.setMobilenumber(loscorp.getMobilenumber());
								cifcorp.setHomephoneno(loscorp.getHomephoneno());
								cifcorp.setFaxnumber(loscorp.getFaxnumber());
								cifcorp.setEmailaddress(loscorp.getEmailaddress());
								cifcorp.setWebsite(loscorp.getWebsite());
								cifcorp.setMaincontact1(loscorp.getMaincontact1());
								cifcorp.setContacttype1(loscorp.getContacttype1());
								cifcorp.setContacttype2(loscorp.getContacttype1());
								cifcorp.setContactvalue1(loscorp.getContactvalue1());
								cifcorp.setContactvalue2(loscorp.getContactvalue2());

								cifmain.setCifno(cifno);
								cifmain.setFullname(lstapp.getCifname());
								cifmain.setCustomertype(lstapp.getCustomertype());
								cifmain.setEncodeddate(new Date());
								cifmain.setEncodedby(secservice.getUserName());
								cifmain.setCifapproveddate(new Date());
								cifmain.setCifstatus("1");
								cifmain.setIsencoding(false);
								cifmain.setCiftype("0");
								cifmain.setCifstatusdate(new Date());
								cifmain.setCifapprovedby(secservice.getUserName());
								cifmain.setAssignedto(secservice.getUserName());
								cifmain.setDateupdated(new Date());
								param.put("user", secservice.getUserName());
								Tbuser user = (Tbuser) dbService
										.executeUniqueHQLQuery("FROM Tbuser where username=:user", param);
								if (user != null) {
									cifmain.setOriginatingbranch(user.getBranchcode());
								}
								cifmain.setFulladdress1(fullAddress(loscorp.getStreetno1(), loscorp.getSubdivision1(),
										loscorp.getBarangay1(), loscorp.getCity1(), loscorp.getProvince1(),
										loscorp.getCountry1(), loscorp.getPostalcode1()));

								cifmain.setFulladdress2(fullAddress(loscorp.getStreetno2(), loscorp.getSubdivision2(),
										loscorp.getBarangay2(), loscorp.getCity2(), loscorp.getProvince2(),
										loscorp.getCountry2(), loscorp.getPostalcode2()));
							}

							// Management Details
							// Trade Reference
							/*
							 * List<Tblstapppersonalreference> lospref = (List<Tblstapppersonalreference>)
							 * dbService
							 * .executeListHQLQuery("FROM Tblstapppersonalreference where appno=:appno",
							 * param); if (lospref != null) { for (Tblstapppersonalreference a : lospref) {
							 * Tbpersonalreference cifpref = new Tbpersonalreference();
							 * cifpref.setCifno(cifno); cifpref.setPersonalrefname(a.getPersonalrefname());
							 * cifpref.setEmployername(a.getEmployername());
							 * cifpref.setEmployeraddress(a.getEmployeraddress());
							 * cifpref.setContactno(a.getContactno());
							 * cifpref.setRelationship(a.getRelationship());
							 * dbServiceCIF.saveOrUpdate(cifpref);
							 * 
							 * } }
							 */
							// Other Loans
							List<Tblstexistingloansother> losloans = (List<Tblstexistingloansother>) dbService
									.executeListHQLQuery("FROM Tblstexistingloansother where appno=:appno", param);
							if (losloans != null) {
								for (Tblstexistingloansother a : losloans) {
									Tbexistingloansother cifloans = new Tbexistingloansother();
									cifloans.setCifno(cifno);
									cifloans.setBank(a.getBank());
									cifloans.setLoantype(a.getLoantype());
									cifloans.setOutstandingbal(a.getOutstandingbal());
									cifloans.setAmortizationamt(a.getAmortizationamt());
									dbServiceCIF.saveOrUpdate(cifloans);

								}
							}
							// Bank Accounts
							List<Tblstbankaccounts> losbanks = (List<Tblstbankaccounts>) dbService
									.executeListHQLQuery("FROM Tblstbankaccounts where appno=:appno", param);
							if (losbanks != null) {
								for (Tblstbankaccounts a : losbanks) {
									Tbbankaccounts cifbanks = new Tbbankaccounts();
									TbbankaccountsId id = new TbbankaccountsId();
									id.setCifno(cifno);
									id.setAccountrefno(a.getAccountrefno());
									cifbanks.setId(id);
									cifbanks.setBankname(a.getBank());
									cifbanks.setAccounttype(a.getBankaccttype());
									cifbanks.setOutstandingbal(a.getOutstandingbal());
									dbServiceCIF.saveOrUpdate(cifbanks);
								}
							}
							// Bank Accounts
							List<Tblstcreditcardinfo> loscred = (List<Tblstcreditcardinfo>) dbService
									.executeListHQLQuery("FROM Tblstcreditcardinfo where appno=:appno", param);
							if (loscred != null) {
								for (Tblstcreditcardinfo a : loscred) {
									Tbcreditcardinfo cifcred = new Tbcreditcardinfo();
									cifcred.setBank(a.getBank());
									cifcred.setCardno(a.getCardno());
									cifcred.setCreditlimit(a.getCreditlimit());
									cifcred.setCifno(cifno);
									dbServiceCIF.saveOrUpdate(cifcred);
								}
							}
							if (dbServiceCIF.saveOrUpdate(cifcorp)) {
								lstapp.setCifno(cifno);
								accinfo.setClientid(cifno);
								dbServiceCIF.saveOrUpdate(cifmain);
								dbService.saveOrUpdate(accinfo);
								if (dbService.saveOrUpdate(lstapp)) {
									flag = "success";
								}
							}
						}

					}
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SearchCIFForm> searchCIF(String branch, String lname, String fname, String businessname, Integer page,
			Integer maxresult, String customertype) {
		List<SearchCIFForm> list = new ArrayList<SearchCIFForm>();
		DBService dbServiceCIF = new DBServiceImplCIF();
		params.put("lname", lname == null ? "%" : "%" + lname + "%");
		params.put("fname", fname == null ? "%" : "%" + fname + "%");
		params.put("businessname", businessname == null ? "%" : "%" + businessname + "%");
		params.put("page", page);
		params.put("maxresult", maxresult);
		params.put("customertype", customertype);
		params.put("branch", branch);
		try {
			String q = "EXEC sp_SearchCIF @page=" + page + ", @maxresult=" + maxresult
					+ ", @branch=:branch, @lname=:lname, @fname=:fname, @businessname=:businessname, @ispagingon='true', @customertype = "
					+ customertype + " ";
			list = (List<SearchCIFForm>) dbServiceCIF.execSQLQueryTransformer(q, params, SearchCIFForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int searchCIFCount(String branch,String lname, String fname, String businessname, String customertype) {
		Integer count = 0;
		DBService dbServiceCIF = new DBServiceImplCIF();
		params.put("lname", lname == null ? "%" : "%" + lname + "%");
		params.put("fname", fname == null ? "%" : "%" + fname + "%");
		params.put("businessname", businessname == null ? "%" : "%" + businessname + "%");
		params.put("customertype", customertype);
		params.put("branch", branch);
		try {
			String q = "EXEC sp_SearchCIF @page=NULL, @maxresult=NULL, @branch=:branch, @lname=:lname, @fname=:fname, @businessname=:businessname, @ispagingon='false', @customertype = "
					+ customertype + " ";
			count = (Integer) dbServiceCIF.execSQLQueryTransformer(q, params, null, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SearchLOSForm> searchLOS(String branch,String lname, String fname, String businessname, Integer page,
			Integer maxresult, String customertype) {
		List<SearchLOSForm> list = new ArrayList<SearchLOSForm>();
		DBService dbServiceCOOP = new DBServiceImpl();
		params.put("lname", lname == null ? "%" : "%" + lname + "%");
		params.put("fname", fname == null ? "%" : "%" + fname + "%");
		params.put("businessname", businessname == null ? "%" : "%" + businessname + "%");
		params.put("page", page);
		params.put("maxresult", maxresult);
		params.put("customertype", customertype);
		params.put("branch", branch);
		
		try {
			String q = "EXEC sp_SearchLOS @page=" + page + ", @maxresult=" + maxresult
					+ ", @branch=:branch, @lname=:lname, @fname=:fname, @businessname=:businessname, @ispagingon='true', @customertype = "
					+ customertype + " ";
			list = (List<SearchLOSForm>) dbServiceCOOP.execSQLQueryTransformer(q, params, SearchLOSForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int searchLOSCount(String branch,String lname, String fname, String businessname, String customertype) {
		Integer count = 0;
		DBService dbServiceCOOP = new DBServiceImpl();
		params.put("lname", lname == null ? "%" : "%" + lname + "%");
		params.put("fname", fname == null ? "%" : "%" + fname + "%");
		params.put("businessname", businessname == null ? "%" : "%" + businessname + "%");
		params.put("customertype", customertype);
		params.put("branch", branch);
		try {
			String q = "EXEC sp_SearchLOS @page=NULL, @maxresult=NULL, @branch=:branch, @lname=:lname, @fname=:fname, @businessname=:businessname, @ispagingon='false', @customertype = "
					+ customertype + " ";
			count = (Integer) dbServiceCOOP.execSQLQueryTransformer(q, params, null, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public String saveOrUpdateApprovalDetails(Tbevaldetails d) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (d.getAppno() != null) {
				param.put("appno", d.getAppno());

				Tbevaldetails row = (Tbevaldetails) dbServiceCOOP
						.executeUniqueHQLQuery("FROM Tbevaldetails WHERE appno=:appno", param);

				if (row == null) {
					Tbevaldetails n = new Tbevaldetails();
					n.setAppno(d.getAppno());
					n.setCifno(d.getCifno());

					n.setCollateralassement(d.getCollateralassement());
					n.setCapacityevaluation(d.getCapacityevaluation());
					n.setRepaymentevaluation(d.getRepaymentevaluation());
					n.setOthernotablefindings(d.getOthernotablefindings());

					n.setCollateralassementremarks(d.getCollateralassementremarks());
					n.setCapacityevaluationremarks(d.getCapacityevaluationremarks());
					n.setRepaymentevaluationremarks(d.getRepaymentevaluationremarks());
					n.setOthernotablefindingsremarks(d.getOthernotablefindingsremarks());

					n.setBapnfisresult(d.getBapnfisresult());
					n.setBapnfisremarks(d.getBapnfisremarks());

					n.setCmapresult(d.getCmapresult());
					n.setCmapremarks(d.getCmapremarks());

					n.setCicresult(d.getCicresult());
					n.setCicremarks(d.getCicremarks());

					n.setBlacklistinternalresult(d.getBlacklistinternalresult());
					n.setBlacklistinternarlremarks(d.getBlacklistinternarlremarks());

					n.setBlacklistexternalresult(d.getBlacklistexternalresult());
					n.setBlacklistexternalremarks(d.getBlacklistexternalremarks());

					n.setAmlaresult(d.getAmlaresult());
					n.setAmlaremarks(d.getAmlaremarks());

					n.setPdrnresult(d.getPdrnresult());
					n.setPdrnremarks(d.getPdrnremarks());

					n.setEvrresult(d.getEvrresult());
					n.setEvrremarks(d.getEvrremarks());

					n.setBvrresult(d.getBvrresult());
					n.setBvrremarks(d.getBvrremarks());

					n.setBankcheckresult(d.getBankcheckresult());
					n.setBankcheckremarks(d.getBankcheckremarks());

					n.setCreditcheckresult(d.getCreditcheckresult());
					n.setCreditcheckremarks(d.getCreditcheckremarks());

					n.setTradecheckresult(d.getTradecheckresult());
					n.setTradecheckremarks(d.getTradecheckremarks());
					
					n.setApproverdecision(d.getApproverdecision());
					n.setApproverremarks(d.getApproverremarks());

					if (dbServiceCOOP.save(n)) {
						flag = "success";
					}
				} else {
					// update

					row.setCollateralassement(d.getCollateralassement());
					row.setCapacityevaluation(d.getCapacityevaluation());
					row.setRepaymentevaluation(d.getRepaymentevaluation());
					row.setOthernotablefindings(d.getOthernotablefindings());

					row.setCollateralassementremarks(d.getCollateralassementremarks());
					row.setCapacityevaluationremarks(d.getCapacityevaluationremarks());
					row.setRepaymentevaluationremarks(d.getRepaymentevaluationremarks());
					row.setOthernotablefindingsremarks(d.getOthernotablefindingsremarks());

					row.setBapnfisresult(d.getBapnfisresult());
					row.setBapnfisremarks(d.getBapnfisremarks());

					row.setCmapresult(d.getCmapresult());
					row.setCmapremarks(d.getCmapremarks());

					row.setCicresult(d.getCicresult());
					row.setCicremarks(d.getCicremarks());

					row.setBlacklistinternalresult(d.getBlacklistinternalresult());
					row.setBlacklistinternarlremarks(d.getBlacklistinternarlremarks());

					row.setBlacklistexternalresult(d.getBlacklistexternalresult());
					row.setBlacklistexternalremarks(d.getBlacklistexternalremarks());

					row.setAmlaresult(d.getAmlaresult());
					row.setAmlaremarks(d.getAmlaremarks());

					row.setPdrnresult(d.getPdrnresult());
					row.setPdrnremarks(d.getPdrnremarks());

					row.setEvrresult(d.getEvrresult());
					row.setEvrremarks(d.getEvrremarks());

					row.setBvrresult(d.getBvrresult());
					row.setBvrremarks(d.getBvrremarks());

					row.setBankcheckresult(d.getBankcheckresult());
					row.setBankcheckremarks(d.getBankcheckremarks());

					row.setCreditcheckresult(d.getCreditcheckresult());
					row.setCreditcheckremarks(d.getCreditcheckremarks());

					row.setTradecheckresult(d.getTradecheckresult());
					row.setTradecheckremarks(d.getTradecheckremarks());
					
					row.setApproverdecision(d.getApproverdecision());
					row.setApproverremarks(d.getApproverremarks());
					
					if (dbServiceCOOP.saveOrUpdate(row)) {
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
	public Tbevaldetails getTbevaldetails(String appno) {
		Tbevaldetails row = new Tbevaldetails();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (appno != null) {
				param.put("appno", appno);
				row = (Tbevaldetails) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbevaldetails WHERE appno=:appno",
						param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return row;
	}

	@SuppressWarnings({ "unlikely-arg-type", "unlikely-arg-type" })
	@Override
	public FormValidation submitLoanApp(String appno) {
		DBService dbServiceCOOP = new DBServiceImpl();
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("appno", appno);
		FormValidation result = new FormValidation();
		result.setErrorMessage("Connection Timed Out.");
		result.setFlag("error");
		Integer appStatus = null;
		try {
			Tblstapp app = (Tblstapp) dbServiceCOOP.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", param);
			if (app == null) {
				result.setErrorMessage("Application Number not found.");
			} else {
				// ENCODING
				if (app.getApplicationstatus().equals(1)) {
					FullDataEntryService fdesrvc = new FullDataEntryServiceImpl();
					// VALIDATE CIF DATA
					result = fdesrvc.validateDataEntry(
							app.getCifno() == null || app.getCifname().equals("") ? appno : app.getCifno());
					if (result != null) {
						if (!result.getFlag().equals("success")) {
							return result;
						}
					}
					// VALIDATE LOAN WORKSHEET
					if ((Tbaccountinfo) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno=:appno",
							param) == null) {
						result.setFlag("error");
						result.setErrorMessage("<br/><b>Missing required field(s):</b> Loan Worksheet tab");
						return result;
					}
					// VALIDATE MAIN SOURCE OF INCOME
					if (app.getCustomertype().equals("2")) {
						Integer empcount = 0;
						Integer businesscount = 0;
						if (app.getCifno() == null || app.getCifname().equals("")) {
							empcount = (Integer) dbServiceCOOP.executeUniqueSQLQuery(
									"select count(*) from Tblstappemployment WHERE appno =:appno", param);
							businesscount = (Integer) dbServiceCOOP.executeUniqueSQLQuery(
									"select count(*) from Tblstappbusiness WHERE appno =:appno", param);
						} else {
							param.put("cifno", app.getCifno());
							empcount = (Integer) dbServiceCIF.executeUniqueSQLQuery(
									"select count(*) from Tbcifemployment WHERE cifno=:cifno", param);
							businesscount = (Integer) dbServiceCIF.executeUniqueSQLQuery(
									"select count(*) from Tbcifbusiness WHERE cifno =:cifno", param);
						}
						if (empcount == 0 && businesscount == 0) {
							result.setFlag("error");
							result.setErrorMessage(
									"<br/><b>Missing required field(s):</b> Employment / Business Information tab");
							return result;
						}
					} else {

					}

					// VALIDATE COLLATERAL IF REQUIRED
					param.put("productcode", app.getLoanproduct());
					Tbloanproduct loanproduct = (Tbloanproduct) dbServiceCOOP
							.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode=:productcode", param);
					if (loanproduct.getIscollateralrequired() != null && loanproduct.getIscollateralrequired()) {
						Integer collateralcount = (Integer) dbServiceCOOP.executeUniqueSQLQuery(
								"select count(*) from Tbloancollateral WHERE appno =:appno", param);
						if (collateralcount == 0) {
							result.setFlag("error");
							result.setErrorMessage("<br/><b>Missing required field(s):</b> Collateral & Securities");
							return result;
						}
					}
					// VALIDATE COMAKER IF REQUIRED
					if (loanproduct.getIscomakerrequired() != null && loanproduct.getIscomakerrequired()) {
						Integer comakercount = (Integer) dbServiceCOOP
								.executeUniqueSQLQuery("select count(*) from Tbcomaker WHERE appno =:appno", param);
						if (comakercount < loanproduct.getNoofcomakerrequired()) {
							result.setFlag("error");
							result.setErrorMessage("<br/><b>Missing required field(s):</b> Collateral & Securities");
							return result;
						}
					}
					// CREATE CIF DATA IF NOT EXISTING
					if (!app.getIsexisting()) {
						createCIFRecordForNonExistingClients(appno);
						app = (Tblstapp) dbServiceCOOP.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", param);
					}
					appStatus = 2;
					
				} else if (app.getApplicationstatus().equals(2)) {
					appStatus = 3;
					
				}else if (app.getApplicationstatus().equals(3)) {
					appStatus = 4;
					
					EmailService emailSrvc = new EmailServiceImpl();
					emailSrvc.sendEmailFoLoanApplicationApprover(EmailCode.LOAN_APPLICATION_APPROVER, appno);
					
				}else if (app.getApplicationstatus().equals(4)) {
					appStatus = 5;
					
				}else if (app.getApplicationstatus().equals(5)) {
					appStatus = 6;
					
				}else if (app.getApplicationstatus().equals(6)) {
					appStatus = 7;
					
				}

				param.put("appstat", appStatus);
				Tbworkflowprocess process = (Tbworkflowprocess) dbServiceCOOP.executeUniqueHQLQuery(
						"FROM Tbworkflowprocess where workflowid =3 and sequenceno =:appstat ",
						param);				
				app.setApplicationstatus(appStatus);
				app.setIsexisting(true);
				app.setStatusdatetime(new Date());
				if (dbServiceCOOP.saveOrUpdate(app)) {
					result.setErrorMessage("Loan Application submitted to: <b><br> " + process.getProcessname());
					result.setFlag("success");
				}
				
				
//				if (app.getApplicationstatus() == 1) {
//					FullDataEntryService fdesrvc = new FullDataEntryServiceImpl();
//					// VALIDATE CIF DATA
//					result = fdesrvc.validateDataEntry(
//							app.getCifno() == null || app.getCifname().equals("") ? appno : app.getCifno());
//					if (result != null) {
//						if (!result.getFlag().equals("success")) {
//							return result;
//						}
//					}
//					// VALIDATE LOAN WORKSHEET
//					if ((Tbaccountinfo) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno=:appno",
//							param) == null) {
//						result.setFlag("error");
//						result.setErrorMessage("<br/><b>Missing required field(s):</b> Loan Worksheet tab");
//						return result;
//					}
//					// VALIDATE MAIN SOURCE OF INCOME
//					if (app.getCustomertype().equals("2")) {
//						Integer empcount = 0;
//						Integer businesscount = 0;
//						if (app.getCifno() == null || app.getCifname().equals("")) {
//							empcount = (Integer) dbServiceCOOP.executeUniqueSQLQuery(
//									"select count(*) from Tblstappemployment WHERE appno =:appno", param);
//							businesscount = (Integer) dbServiceCOOP.executeUniqueSQLQuery(
//									"select count(*) from Tblstappbusiness WHERE appno =:appno", param);
//						} else {
//							param.put("cifno", app.getCifno());
//							empcount = (Integer) dbServiceCIF.executeUniqueSQLQuery(
//									"select count(*) from Tbcifemployment WHERE cifno=:cifno", param);
//							businesscount = (Integer) dbServiceCIF.executeUniqueSQLQuery(
//									"select count(*) from Tbcifbusiness WHERE cifno =:cifno", param);
//						}
//						if (empcount == 0 && businesscount == 0) {
//							result.setFlag("error");
//							result.setErrorMessage(
//									"<br/><b>Missing required field(s):</b> Employment / Business Information tab");
//							return result;
//						}
//					} else {
//
//					}
//
//					// VALIDATE COLLATERAL IF REQUIRED
//					param.put("productcode", app.getLoanproduct());
//					Tbloanproduct loanproduct = (Tbloanproduct) dbServiceCOOP
//							.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode=:productcode", param);
//					if (loanproduct.getIscollateralrequired() != null && loanproduct.getIscollateralrequired()) {
//						Integer collateralcount = (Integer) dbServiceCOOP.executeUniqueSQLQuery(
//								"select count(*) from Tbloancollateral WHERE appno =:appno", param);
//						if (collateralcount == 0) {
//							result.setFlag("error");
//							result.setErrorMessage("<br/><b>Missing required field(s):</b> Collateral & Securities");
//							return result;
//						}
//					}
//					// VALIDATE COMAKER IF REQUIRED
//					if (loanproduct.getIscomakerrequired() != null && loanproduct.getIscomakerrequired()) {
//						Integer comakercount = (Integer) dbServiceCOOP
//								.executeUniqueSQLQuery("select count(*) from Tbcomaker WHERE appno =:appno", param);
//						if (comakercount < loanproduct.getNoofcomakerrequired()) {
//							result.setFlag("error");
//							result.setErrorMessage("<br/><b>Missing required field(s):</b> Collateral & Securities");
//							return result;
//						}
//					}
//					// CREATE CIF DATA IF NOT EXISTING
//					if (!app.getIsexisting()) {
//						createCIFRecordForNonExistingClients(appno);
//						app = (Tblstapp) dbServiceCOOP.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", param);
//					}
//				} else if (app.getApplicationstatus() == 3) {
//					Integer bicompleted = (Integer) dbServiceCOOP.executeUniqueSQLQuery(
//							"select count(*) from tbinvestigationresults where appno=:appno and reporttype='BI' and updatedby is null",
//							param);
//					Integer cicompleted = (Integer) dbServiceCOOP.executeUniqueSQLQuery(
//							"select count(*) from tbinvestigationresults where appno=:appno and reporttype='CI' and updatedby is null",
//							param);
//					Integer cacompleted = (Integer) dbServiceCOOP.executeUniqueSQLQuery(
//							"select count(*) from tbinvestigationresults where appno=:appno and reporttype='APPR' and updatedby is null",
//							param);
//					String errormsg = "";
//					if (bicompleted > 0) {
//						errormsg += "<b>Bureau Investigation Report</b> not yet completed<br/>";
//					}
//					if (cicompleted > 0) {
//						errormsg += "<b>Credit Investigation Report</b> not yet completed<br/>";
//					}
//					if (cacompleted > 0) {
//						errormsg += "<b>Collateral Appraisal Report</b> not yet completed<br/>";
//					}
//					if (bicompleted > 0 || cicompleted > 0 || cacompleted > 0) {
//						result.setErrorMessage(errormsg);
//						return result;
//					}
//				}
//
//				param.put("appstat", app.getApplicationstatus());
//				Tbworkflowprocess process = (Tbworkflowprocess) dbServiceCOOP.executeUniqueHQLQuery(
//						"FROM Tbworkflowprocess p "
//								+ "WHERE p.id.workflowid=3 AND p.id.sequenceno = (select submitoption1 FROM Tbworkflowprocess pp WHERE pp.id.sequenceno=:appstat AND pp.id.workflowid=3)",
//						param);
//				app.setApplicationstatus(process.getId().getSequenceno());
//				app.setIsexisting(true);
//				app.setStatusdatetime(new Date());
//				if (dbServiceCOOP.saveOrUpdate(app)) {
//					result.setErrorMessage("Loan Application submitted to: <b><br> " + process.getProcessname());
//					result.setFlag("success");
//				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public FormValidation waiveBICI(String appno) {
		DBService dbServiceCOOP = new DBServiceImpl();
		//DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("appno", appno);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		param.put("username", secservice.getUserName());
		FormValidation result = new FormValidation();
		result.setErrorMessage("Connection Timed Out.");
		result.setFlag("error");
		try {
			Tblstapp app = (Tblstapp) dbServiceCOOP.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", param);
			if (app == null) {
				result.setErrorMessage("Application Number not found.");
			} else {
				if (app.getApplicationstatus() == 3) {
					
					// generate instructions
					String res = (String) dbServiceCOOP.execSQLQueryTransformer(
							"EXEC sp_GenerateInvestigationRpt @appno=:appno, @username=:username", param, null, 0);
					if (res.equals("success")) {
						
						// set waive result
						Integer res2 = (Integer) dbServiceCOOP.executeUpdate("UPDATE tbinvestigationresults"
								+ " SET bibapnfis = '2', bicmap = '2', bicic = '2', biblacklistinternal = '2', biblacklistexternal = '2', biamla = '2',"
								+ " cipdrn = '2', cievr = '2', cibvr = '2', cibankcheck = '2', cicreditcheck = '2', citradecheck = '2'"
								+ " WHERE appno=:appno", param);
						
						if (res2 != null && res2 > 0) {
							// update status
							param.put("appstat", app.getApplicationstatus());
							Tbworkflowprocess process = (Tbworkflowprocess) dbServiceCOOP.executeUniqueHQLQuery(
									"FROM Tbworkflowprocess p "
											+ "WHERE p.id.workflowid=3 AND p.id.sequenceno = (select submitoption1 FROM Tbworkflowprocess pp WHERE pp.id.sequenceno=:appstat AND pp.id.workflowid=3)",
									param);
							app.setApplicationstatus(process.getId().getSequenceno());
							app.setIsexisting(true);
							app.setStatusdatetime(new Date());
							if (dbServiceCOOP.saveOrUpdate(app)) {
								result.setErrorMessage("Loan Application submitted to : <b><br> " + process.getProcessname());
								result.setFlag("success");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
