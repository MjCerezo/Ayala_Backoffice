package com.etel.otherinfo.dosri;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbcodetable;
import com.cifsdb.data.Tbdosri;
import com.cifsdb.data.Tbmanagement;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.otherinfo.dosri.forms.AffiliatesOrSubsidiaries;
import com.etel.otherinfo.dosri.forms.CommonDOSRI;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

public class DosriServiceImpl implements DosriService {

	
	@Override
	public String saveOrUpdateDosri(String cifno, Tbdosri info) {
		Map<String, Object> params = HQLUtil.getMap();
		Tbdosri d = new Tbdosri();
		DBService dbService = new DBServiceImplCIF();
		String flag = "failed";
		try {
			if (cifno != null) {
				params.put("cifno", cifno);
				d = (Tbdosri) dbService.executeUniqueHQLQuery("FROM Tbdosri WHERE cifno=:cifno", params);
				if (d != null) {
					//System.out.println("dosri not null");
					d.setDateupdated(new Date());
					d.setNameofdosrirelative(info.getNameofdosrirelative());
					d.setPositionofdosrirelative(info.getPositionofdosrirelative());
					d.setQ1(info.getQ1() == null ? false : info.getQ1());
					d.setQ2(info.getQ2() == null ? false : info.getQ2());
					d.setQ3(info.getQ3() == null ? false : info.getQ3());
					d.setQ4(info.getQ4() == null ? false : info.getQ4());
					d.setQ5(info.getQ5() == null ? false : info.getQ5());
					d.setSubsidiary1(info.getSubsidiary1());
					d.setSubsidiary2(info.getSubsidiary2());
					d.setSubsidiary3(info.getSubsidiary3());
					d.setSubsidiary4(info.getSubsidiary4());
					d.setSubsidiary5(info.getSubsidiary5());
					d.setUpdatedby(UserUtil.securityService.getUserName());
					if (dbService.saveOrUpdate(d)) {
						flag = "success";
						//d = getDosriInfo(cifno);
						if (d.getQ1() || d.getQ2() || d.getQ3() || d.getQ4() || d.getQ5()) {
							d.setDosristatus("1");
						} else {
							d.setDosristatus("2");
						}
						if(dbService.saveOrUpdate(d)){
							//Save or Update CIF Type - Kevin 12/18/2018
							updateCIFTypeDOSRIIndicator(cifno, d.getDosristatus());
						}
					}
				} else {
					info.setCifno(cifno);
					info.setCreateddate(new Date());
					info.setCreatedby(UserUtil.securityService.getUserName());
					info.setCifno(cifno);
					info.setQ1(false);
					info.setQ2(false);
					info.setQ3(false);
					info.setQ4(false);
					info.setQ5(false);
					info.setDosristatus("2");
					if (dbService.save(info)) {
						flag = "success";
						
						//Save or Update CIF Type - Kevin 12/18/2018
						updateCIFTypeDOSRIIndicator(cifno, info.getDosristatus());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Tbdosri getDosriInfo(String cifno) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		 
		try {
			if (cifno != null) {
				params.put("cifno", cifno);
				Tbdosri dosri = (Tbdosri) dbService.executeUniqueHQLQuery("FROM Tbdosri WHERE cifno=:cifno", params);
				if(dosri != null){
					return dosri;
				}else{
					Tbdosri dosri2 = new Tbdosri();
					dosri2.setCifno(cifno);
					dosri2.setCreateddate(new Date());
					dosri2.setCreatedby(UserUtil.securityService.getUserName());
					if(dbService.save(dosri2)){
						return dosri2;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<AffiliatesOrSubsidiaries> listAffiliates(String cifno) {
		List<AffiliatesOrSubsidiaries> list = new ArrayList<AffiliatesOrSubsidiaries>();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("cifno", cifno);
		String query="";
		try {
			// corporate
			if (cifno.substring(0, 1).equals("2") || cifno.substring(0, 1).equals("3")) {
				query = "SELECT DISTINCT m.relatedcifno as cifno, CASE WHEN SUBSTRING(m.relatedcifno, 1, 1)='1' THEN concat(m.lastname,', ',m.firstname,' ', m.middlename) ELSE m.corporatename END as name, r.relatedcifname as company, "
						+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='RELATIONSHIPCODE' AND codevalue = m.relationshipcode) as position "
						+ "FROM TBMANAGEMENT m INNER JOIN TBCUSTOMERRELATIONSHIP r on ((m.cifno = r.maincifno)) AND r.relationshipcode IN ('SHA', 'SUB', 'APE') "
						+ "INNER JOIN TBCOMPANY c on c.companyname IN (r.relatedcifname, r.maincifname) AND m.relationshipcode IN "
						+ "(SELECT relationshipcode FROM TBMANAGEMENT WHERE relationshipcode IN('SHA', 'OFF', 'DR') AND cifno=r.relatedcifno) "
						+ "WHERE (m.cifno=:cifno)";
			} else {
				// individual
				query = "SELECT DISTINCT m.relatedcifno as cifno, CASE WHEN SUBSTRING(m.relatedcifno, 1, 1)='1' THEN concat(m.lastname,', ',m.firstname,' ', m.middlename) ELSE m.corporatename END as name, r.maincifname as company, "
						+ "(SELECT desc1 FROM TBCODETABLE WHERE codename='RELATIONSHIPCODE' AND codevalue = m.relationshipcode) as position "
						+ "FROM TBMANAGEMENT m INNER JOIN TBCUSTOMERRELATIONSHIP r on ((m.cifno = r.relatedcifno)) AND r.relationshipcode IN ('SHA', 'SUB', 'APE') "
						+ "INNER JOIN TBCOMPANY c on c.companyname IN (r.relatedcifname, r.maincifname) AND m.relationshipcode IN "
						+ "(SELECT relationshipcode FROM TBMANAGEMENT WHERE relationshipcode IN('SHA', 'OFF', 'DR') AND cifno=r.maincifno) "
						+ "WHERE (m.relatedcifno=:cifno)";
			}

			list = (List<AffiliatesOrSubsidiaries>)dbService.execSQLQueryTransformer(query, params, AffiliatesOrSubsidiaries.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/***
	 *Save DOSRI status (DOSRI / NON-DOSRI): check from tbmanagement
	 *stockholder or shareholder (SHA),
	 *Director (DR),
	 *Officer (OFF)
	 *check cifno of (SHA,DR,OFF) in tbcompany. if true set Q1,Q2,Q4.
	 ***/
	@SuppressWarnings("unchecked")
	@Override
	public String saveDosriStatus(String cifno) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		Tbdosri dosri = new Tbdosri();
		String flag = "failed";
		try {
			if (cifno != null) {
				params.put("cifno", cifno);
				dosri = getDosriInfo(cifno);
				String main = (String) dbService.executeUniqueSQLQuery("SELECT customertype FROM Tbcifmain WHERE cifno=:cifno",
						params);
				if (main != null) {
					if (main.equals("1")) {
						// individual
						List<Tbmanagement> mngmntList = (List<Tbmanagement>) dbService
								.executeListHQLQuery("FROM Tbmanagement WHERE relatedcifno=:cifno", params);
						if (dosri != null) {
							if (mngmntList != null && mngmntList.size() > 0 ) {
								Boolean q1 = false;
								Boolean q2 = false;
								Boolean q3 = false;
								Boolean q4 = false;
								for (Tbmanagement m : mngmntList) {
									// Q1
									// Check stockholder or shareholder (SHA)
									if (m.getRelationshipcode().equals("SHA")) {
										params.put("cifno", m.getCifno());
										Integer comp = (Integer) dbService
												.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbcompany WHERE cifno=:cifno", params);
										if (comp > 0) {
											q1 = true;
										}
									}
									// Q2
									// Check Director (DR)
									if (m.getRelationshipcode().equals("DR")) {
										params.put("cifno", m.getCifno());
										Integer comp = (Integer) dbService
												.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbcompany WHERE cifno=:cifno", params);
										if (comp > 0) {
											q2 = true;
										}
									}
									
									// Q3
									// check customer is a director, stockholder, officer or
									// related with any subsidiary or affiliate of any of the companies in TBCOMPANY
									if(!listAffiliates(cifno).isEmpty() && listAffiliates(cifno) != null){
										q3 = true;
									}
									// Q4
									// Check Officer (OFF)
									if (m.getRelationshipcode().equals("OFF")) {
										params.put("cifno", m.getCifno());
										Integer comp = (Integer) dbService
												.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbcompany WHERE cifno=:cifno", params);
										if (comp > 0) {
											q4 = true;
										}
									}
								}
								//set questions
								dosri.setQ1(q1 == null ? false : q1);
								dosri.setQ2(q2 == null ? false : q2);
								dosri.setQ3(q3 == null ? false : q3);
								dosri.setQ4(q4 == null ? false : q4);
								if(dbService.saveOrUpdate(dosri)){
									flag = "success";
								}
							}
						} else {
							Tbdosri d = new Tbdosri();
							Boolean q1 = false;
							Boolean q2 = false;
							Boolean q3 = false;
							Boolean q4 = false;
							if (mngmntList != null && !mngmntList.isEmpty()) {
								for (Tbmanagement m : mngmntList) {
									// Q1
									// Check stockholder or shareholder (SHA)
									if (m.getRelationshipcode().equals("SHA")) {
										params.put("cifno", m.getCifno());
										Integer comp = (Integer) dbService
												.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbcompany WHERE cifno=:cifno", params);
										if (comp > 0) {
											q1 = true;
										}
									}
									// Q2
									// Check Director (DR)
									if (m.getRelationshipcode().equals("DR")) {
										params.put("cifno", m.getCifno());
										Integer comp = (Integer) dbService
												.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbcompany WHERE cifno=:cifno", params);
										if (comp > 0) {
											q2 = true;
										}
									}
									
									// Q3
									// check customer is a director, stockholder, officer or
									// related with any subsidiary or affiliate of any of the companies in TBCOMPANY
									if(!listAffiliates(cifno).isEmpty() && listAffiliates(cifno) != null){
										q3 = true;
									}
									// Q4
									// Check Officer (OFF)
									if (m.getRelationshipcode().equals("OFF")) {
										params.put("cifno", m.getCifno());
										Integer comp = (Integer) dbService
												.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbcompany WHERE cifno=:cifno", params);
										if (comp > 0) {
											q4 = true;
										}
									}
								}
							}
							//set questions
							d.setQ1(q1 == null ? false : q1);
							d.setQ2(q2 == null ? false : q2);
							d.setQ3(q3 == null ? false : q3);
							d.setQ4(q4 == null ? false : q4);
							d.setQ5(false);
							d.setCifno(cifno);
							d.setCreatedby(UserUtil.securityService.getUserName());
							d.setCreateddate(new Date());
							if(dbService.save(d)){
								flag = "success";
							}
						}
					} else {
						// corporate
						Boolean q1 = false;
						Boolean q2 = false;
						Boolean q3 = false;
						Boolean q4 = false;
						// Q1
						// Shareholder
						List<CommonDOSRI> sha = listCommonDOSRI(cifno, "SHA");
						if (sha != null && sha.size() > 0) {
							q1 = true;
						}
						// Q2
						// Director
						List<CommonDOSRI> dr = listCommonDOSRI(cifno, "DR");
						if (dr != null && dr.size() > 0) {
							q2 = true;
						}
						
						// Q3
						List<AffiliatesOrSubsidiaries> subAff = listAffiliates(cifno);
						if (subAff != null && subAff.size() > 0) {
							q3 = true;
						}
						// Q4
						// Officers
						List<CommonDOSRI> off = listCommonDOSRI(cifno, "OFF");
						if (off != null && off.size() > 0) {
							q4 = true;
						}
						if (dosri != null) {
							// set questions
							dosri.setQ1(q1 == null ? false : q1);
							dosri.setQ2(q2 == null ? false : q2);
							dosri.setQ3(q3 == null ? false : q3);
							dosri.setQ4(q4 == null ? false : q4);
							if (dbService.saveOrUpdate(dosri)) {
								flag = "success";
							}
						} else {
							Tbdosri d = new Tbdosri();
							// set questions
							d.setQ1(q1 == null ? false : q1);
							d.setQ2(q2 == null ? false : q2);
							d.setQ3(q3 == null ? false : q3);
							d.setQ4(q4 == null ? false : q4);
							d.setQ5(false);
							d.setCifno(cifno);
							d.setCreatedby(UserUtil.securityService.getUserName());
							d.setCreateddate(new Date());
							if (dbService.save(d)) {
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
	/****
	 * List of all common DOSRI for corporate (SHA,DR and OFF) only Q1,Q2 and Q4
	 * **/
	@SuppressWarnings("unchecked")
	@Override
	public List<CommonDOSRI> listCommonDOSRI(String cifno, String relationcode) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		List<CommonDOSRI> list = new ArrayList<CommonDOSRI>();
		params.put("cifno", cifno);
		params.put("rcode", relationcode);
		try {
			list = (ArrayList<CommonDOSRI>) dbService.execSQLQueryTransformer(""
					+ "SELECT DISTINCT CASE WHEN SUBSTRING(m.relatedcifno, 1, 1)='1' THEN concat(m.lastname,', ',m.firstname,' ', m.middlename) ELSE m.corporatename END as name, "
					+ "c.companyname as company, "
					+ "m.relatedcifno as cifno "
					+ "FROM TBMANAGEMENT m INNER JOIN TBCUSTOMERRELATIONSHIP r "
					+ "ON ((m.cifno = r.maincifno)) AND r.relationshipcode =:rcode "
					+ "INNER JOIN TBCOMPANY c on c.companyname "
					+ "IN (r.relatedcifname, r.maincifname) AND m.relationshipcode IN "
					+ "(SELECT relationshipcode FROM TBMANAGEMENT WHERE relationshipcode =:rcode AND cifno=r.maincifno) "
					+ "WHERE m.cifno=:cifno", params, CommonDOSRI.class, 1);
			if(list != null && !list.isEmpty()){
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * CIF Type DOSRI Indicator
	 * @author Kevin (12.18.2018)
	 * @return String = success, otherwise failed
	 * */
	private static String updateCIFTypeDOSRIIndicator(String cifno, String dosristatus){
		String flag = "failed";
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			/*
			 * DOSRI STATUS:
			 * 1 - DOSRI
			 * 2 - NON DOSRI
			 * 
			 * */
			
			/* 
			 * CIFTYPE :
			 * 0 - Prospect
			 * 1 - Applicant
			 * 2 - Customer
			 * 3 - Linked CIF
			 * 4 - Linked DOSRI
			 * 5 - Prospect DOSRI
			 * 6 - Applicant DOSRI
			 * 7 - Customer DOSRI
			 * */
			if (cifno != null && dosristatus != null) {
				params.put("cifno", cifno);
				Tbcifmain main = (Tbcifmain) dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", params);
				if (main != null) {
					String ciftype = main.getCiftype();
					System.out.println(">>>>>>>>>>>>> START CIF TYPE: "+ ciftype);
					if(ciftype != null){
						params.put("ciftype", ciftype);
						Tbcodetable cd = new Tbcodetable();
						//DOSRI
						if (dosristatus.equals("1")) {
							if(ciftype.equals("0") || ciftype.equals("1") || ciftype.equals("2") || ciftype.equals("3")){
								cd = (Tbcodetable) dbService.executeUniqueHQLQuery(
										"FROM Tbcodetable WHERE id.codename='CIFTYPE' AND id.codevalue=:ciftype AND desc2 IN('4','5','6','7')", params);
								
								if (cd != null) {
									ciftype = cd.getDesc2();
								}
							}
						} 
						//NON-DOSRI
						else {
							if(ciftype.equals("4") || ciftype.equals("5") || ciftype.equals("6") || ciftype.equals("7")){
								cd = (Tbcodetable) dbService.executeUniqueHQLQuery(
										"FROM Tbcodetable WHERE id.codename='CIFTYPE' AND desc2=:ciftype  AND codevalue IN('0','1','2','3')", params);
								
								if (cd != null) {
									ciftype = cd.getId().getCodevalue();
								}
							}
						}
						System.out.println(">>>>>>>>>>>>> END CIF TYPE: "+ ciftype);
						main.setCiftype(ciftype);
						if (dbService.saveOrUpdate(main)) {
							flag = "success";
						}
					}//end ciftype checking
					
					
				}//end tbcifmain checking
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
