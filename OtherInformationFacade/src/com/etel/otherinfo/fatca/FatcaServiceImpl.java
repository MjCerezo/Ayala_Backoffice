package com.etel.otherinfo.fatca;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbcodetable;
import com.cifsdb.data.Tbfatca;
import com.cifsdb.data.Tbmanagement;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.dataentry.FullDataEntryService;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.otherinfo.fatca.forms.FATCAViewForm;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

//import sun.org.mozilla.javascript.internal.regexp.SubString;

public class FatcaServiceImpl implements FatcaService {

	@Override
	public String saveOrUpdateFatcaInfo(Tbfatca info, String businesstype) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		String flag = "failed";
		Tbfatca fat = new Tbfatca();
		FullDataEntryService srvc = new FullDataEntryServiceImpl();
		try {
			if (info.getCifno() != null) {
				Tbcifmain main = srvc.getDetails(info.getCifno());
				params.put("cifno", info.getCifno());
				fat = (Tbfatca) dbService.executeUniqueHQLQuery("FROM Tbfatca WHERE cifno=:cifno", params);
				if (main != null) {
					if (main.getCustomertype().equals("1")) {
						// individual
						if (fat != null) {
							fat.setCity(info.getCity());
							fat.setDateupdated(new Date());
							fat.setOthernationality(info.getOthernationality());
							fat.setQ1(info.getQ1());
							if (info.getQ1()) {
								fat.setQ1since(info.getQ1since());
							} else {
								fat.setQ1since(null);
							}
							fat.setQ2(info.getQ2());
							fat.setQ3(info.getQ3());
							fat.setQ4(info.getQ4());
							fat.setQ5(info.getQ5());
							fat.setQ6(info.getQ6());
							fat.setState(info.getState());
							fat.setUpdatedby(UserUtil.securityService.getUserName());
							fat.setUsaddress1(info.getUsaddress1());
							fat.setUsaddress2(info.getUsaddress2());
							fat.setUstelno(info.getUstelno());
							fat.setUstin(info.getUstin());
							fat.setZipcode(info.getZipcode());
							fat.setAreadesc(info.getAreadesc());
							fat.setFatcastatus(info.getFatcastatus());
							if (dbService.saveOrUpdate(fat)) {
								flag = "success";
							}
						} else {
							info.setCreatedby(UserUtil.securityService.getUserName());
							info.setCreateddate(new Date());
							dbService.save(info);
						}
					} else if (main.getCustomertype().equals("2") || main.getCustomertype().equals("3")) {
						// corporate
						Tbcodetable btype = new Tbcodetable();
						if (businesstype != null) {
							params.put("businesstype", businesstype);
							btype = (Tbcodetable) dbService.executeUniqueHQLQuery(
									"FROM Tbcodetable WHERE id.codename='BUSINESSTYPE' AND id.codevalue=:businesstype",
									params);
						}
						fat = (Tbfatca) dbService.executeUniqueHQLQuery("FROM Tbfatca WHERE cifno=:cifno", params);
						if (fat != null) {
							// For Sole Proprietorship / Partnership
							if (btype.getDesc2().equals("Sole Proprietorship")
									|| btype.getDesc2().equals("Partnership")) {
								List<FATCAViewForm> list = listFATCA(info.getCifno());
								if (list != null && !list.isEmpty()) {
									fat.setFatcastatus("1");
								} else {
									fat.setFatcastatus("2");
								}
								fat.setDateupdated(new Date());
								fat.setIsfi(null);
								fat.setFficlass1(null);
								fat.setFficlass2(null);
								fat.setGiin(null);
								fat.setSecexchange(null);
								dbService.saveOrUpdate(fat);
							} else {
								// For Corporation
								fat.setDateupdated(new Date());
								fat.setIsfi(info.getIsfi());
								if (info.getFficlass1() != null && !info.getFficlass1().equals("")) {
									if (info.getFficlass1().equals("0") || info.getFficlass1().equals("1")
											|| info.getFficlass1().equals("2")) {
										fat.setFatcastatus("1");
									} else {
										fat.setFatcastatus("2");
									}
									fat.setFficlass1(info.getFficlass1());
								} else if (info.getFficlass1() == null) { // 11.28.18
									fat.setFatcastatus("2");
								}
								fat.setFficlass2(info.getFficlass2());
								fat.setGiin(info.getGiin());
								fat.setSecexchange(info.getSecexchange());
								dbService.saveOrUpdate(fat);
							}
						} else {
							Tbfatca fat2 = new Tbfatca();
							fat2.setCifno(info.getCifno());
							// For Sole Proprietorship / Partnership
							if (btype.getDesc2().equals("Sole Proprietorship")
									|| btype.getDesc2().equals("Partnership")) {
								List<FATCAViewForm> list = listFATCA(info.getCifno());
								if (list != null && !list.isEmpty()) {
									fat2.setFatcastatus("1");
								} else {
									fat2.setFatcastatus("2");
								}
								fat2.setDateupdated(new Date());
								fat2.setIsfi(null);
								fat2.setFficlass1(null);
								fat2.setFficlass2(null);
								fat2.setGiin(null);
								fat2.setSecexchange(null);
								dbService.save(fat2);
							} else {
								// For Corporation
								fat2.setDateupdated(new Date());
								fat2.setIsfi(info.getIsfi());
								if (info.getFficlass1() != null && !info.getFficlass1().equals("")) {
									if (info.getFficlass1().equals("0") || info.getFficlass1().equals("1")
											|| info.getFficlass1().equals("2")) {
										fat2.setFatcastatus("1");
									} else {
										fat2.setFatcastatus("2");
									}
									fat2.setFficlass1(info.getFficlass1());
								} else if (info.getFficlass1() == null) { // 11.28.18
									fat2.setFatcastatus("2");
								}
								fat2.setFficlass2(info.getFficlass2());
								fat2.setGiin(info.getGiin());
								fat2.setSecexchange(info.getSecexchange());
								dbService.save(fat2);
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

	@Override
	public Tbfatca getFatcaInfo(String cifno) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (cifno != null) {
				params.put("cifno", cifno);
				Tbfatca fat = (Tbfatca) dbService.executeUniqueHQLQuery("FROM Tbfatca WHERE cifno=:cifno", params);
				if (fat != null) {
					if (cifno.substring(0, 1).equals("1")) {
						FullDataEntryService f = new FullDataEntryServiceImpl();
						Tbcifindividual i = f.getIndividual(cifno);
						if (i != null) {
							if ((i.getDualcitizen() && i.getOthernationality() != null
									&& i.getOthernationality().equals("US"))
									|| (i.getNationality() != null && i.getNationality().equals("US"))
									|| (i.getCountryofbirth() != null && i.getCountryofbirth().equals("US"))) {
								fat.setFatcastatus("1");
							}
							dbService.saveOrUpdate(fat);
						}
					}
					return fat;
				} else {
					Tbfatca fat2 = new Tbfatca();
					fat2.setCifno(cifno);

					if (cifno.substring(0, 1).equals("1")) {
						FullDataEntryService f = new FullDataEntryServiceImpl();
						Tbcifindividual i = f.getIndividual(cifno);
						if (i != null) {
//CED CIF 09242020
							if ((i.getDualcitizen() != null && i.getDualcitizen() && i.getOthernationality() != null
									&& i.getOthernationality().equals("US"))
									|| (i.getNationality() != null && i.getNationality().equals("US"))
									|| (i.getCountryofbirth() != null && i.getCountryofbirth().equals("US"))) {
								fat2.setFatcastatus("1");
							}
						}
					}

					if (dbService.save(fat2)) {
						return fat2;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FATCAViewForm> listFATCA(String cifno) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbmanagement> m = new ArrayList<Tbmanagement>();
		List<FATCAViewForm> list = new ArrayList<FATCAViewForm>();
		Tbfatca fat = new Tbfatca();
		try {
			if (cifno != null) {
				params.put("cifno", cifno);
				m = (List<Tbmanagement>) dbService.executeListHQLQuery(
						"FROM Tbmanagement WHERE cifno=:cifno AND (relationshipcode IN ('OWN', 'P','SIG'))", params);
				if (m != null) {
					for (Tbmanagement a : m) {
						FATCAViewForm form = new FATCAViewForm();
						form.setCifno(a.getRelatedcifno());
						if (a.getCustomertype().equals("1")) {
							form.setName(a.getLastname() + ", " + a.getFirstname() + " " + a.getSuffix() + " "
									+ a.getMiddlename());
						} else {
							form.setName(a.getCorporatename());
						}
						fat = getFatcaInfo(a.getRelatedcifno());
						if (fat != null) {
							form.setQ1(fat.getQ1());
							form.setQ2(fat.getQ2());
							form.setQ3(fat.getQ3());
							form.setQ4(fat.getQ4());
							form.setQ5(fat.getQ5());
							form.setQ6(fat.getQ6());
							if (a.getRelationshipcode() != null) {
								params.put("relCode", a.getRelationshipcode());
								Tbcodetable c = (Tbcodetable) dbService.executeUniqueHQLQuery(
										"FROM Tbcodetable WHERE id.codename='RELATIONSHIPCODE' AND id.codevalue=:relCode",
										params);
								form.setRelationship(c.getDesc1());
							}
							if (fat.getFatcastatus() != null) {
								params.put("fatcastatus", fat.getFatcastatus());
								Tbcodetable c = (Tbcodetable) dbService.executeUniqueHQLQuery(
										"FROM Tbcodetable WHERE id.codename='FATCARESULT' AND id.codevalue=:fatcastatus",
										params);
								form.setFatcastatus(c.getDesc1());
							}
						}
						list.add(form);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/****
	 * validation already in front-end binding. This saving of FATCA Status is for
	 * individual only.
	 **/
	@Deprecated
	@Override
	public String saveFATCAStatus(String cifno) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		String flag = "failed";
		Tbfatca fat = new Tbfatca();
		FullDataEntryService srvc = new FullDataEntryServiceImpl();
		try {
			if (cifno != null) {
				Tbcifindividual indiv = srvc.getIndividual(cifno);
				params.put("cifno", cifno);
				fat = (Tbfatca) dbService.executeUniqueHQLQuery("FROM Tbfatca WHERE cifno=:cifno", params);
				if (fat != null) {
					if (indiv.getNationality() != null && indiv.getNationality().equals("US")) {
						// Any question answered by “Y” shall be categorized
						// as “FATCA Covered Person”
						if (fat.getQ1() || fat.getQ2() || fat.getQ3() || fat.getQ4() || fat.getQ5()) {
							fat.setFatcastatus("1");
						} else {
							fat.setFatcastatus("2");
						}
						if (dbService.saveOrUpdate(fat)) {
							flag = "success";
						}
					}
				} else {
					Tbfatca info = new Tbfatca();
					if (indiv.getNationality() != null && indiv.getNationality().equals("US")) {
						info.setFatcastatus("1");
						info.setQ1(true);
						if (indiv.getDualcitizen() != null && indiv.getDualcitizen()) {
							info.setQ4(true);
						} else {
							info.setQ4(false);
						}
						if (indiv.getResident() != null && indiv.getResident()) {
							info.setQ2(true);
						} else {
							info.setQ2(false);
						}
						if (indiv.getCountryofbirth() != null && indiv.getCountryofbirth().equals("US")) {
							info.setQ6(true);
						} else {
							info.setQ6(false);
						}
						if (indiv.getCountryofbirth().equals("US")) {
							if (indiv.getCityofbirth() != null) {
								info.setUsplaceofbirth(indiv.getCityofbirth());
							}
						}
						info.setQ3(false);
						info.setQ5(false);
						info.setCifno(cifno);
						info.setCreatedby(UserUtil.securityService.getUserName());
						info.setCreateddate(new Date());
						if (dbService.save(info)) {
							flag = "success";
						}
					} else {
						info.setQ3(false);
						info.setQ5(false);
						info.setCifno(cifno);
						info.setCreatedby(UserUtil.securityService.getUserName());
						info.setCreateddate(new Date());
						info.setFatcastatus("2");
						dbService.save(info);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

}
