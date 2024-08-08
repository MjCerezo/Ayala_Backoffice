package com.etel.otherinfo.pep;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbcifcorporate;
import com.cifsdb.data.Tbcifemployment;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbcifpepinfo;
import com.cifsdb.data.Tbcodetable;
import com.cifsdb.data.Tbmanagement;
import com.cifsdb.data.Tbpepinfo;
import com.cifsdb.data.Tbpepq3;
import com.cifsdb.data.Tbriskprofile;
import com.etel.codetable.forms.CodetableForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.dataentry.FullDataEntryService;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.otherinfo.OtherInformationFacade;
import com.etel.otherinfo.pep.forms.PEPViewForm;
import com.etel.otherinfo.pep.forms.PresentPreviousGovEmp;
import com.etel.utils.HQLUtil;
import com.etel.utils.LoggerUtil;
import com.etel.utils.UserUtil;

public class PepServiceImpl implements PepService {
	@Override
	public String saveOrUpdatePepInfo(Tbpepinfo info, String cifno, Tbriskprofile risk, String empstatus) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		Tbpepinfo pep = new Tbpepinfo();
		String flag = "failed";
		FullDataEntryService serv = new FullDataEntryServiceImpl();
		Tbcifmain main = new Tbcifmain();
		try {
			if (cifno != null) {
				params.put("cifno", cifno);
				main = (Tbcifmain) dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", params);

				if (main.getCustomertype().equals("1")) {
					List<Tbcifemployment> emp = serv.getListEmployment(cifno);
					pep = (Tbpepinfo) dbService.executeUniqueHQLQuery("FROM Tbpepinfo WHERE cifno=:cifno", params);
					Boolean q1 = false;
					Boolean q2 = false;
					if (pep != null) {
						if (emp != null && !emp.isEmpty()) {
							for (Tbcifemployment i : emp) {
								if (i.getEmprecordtype() != null) {
									if (i.getEmprecordtype().equals("0")) {
										List<PresentPreviousGovEmp> listq1 = listQ1(cifno, null);
										if(!listq1.isEmpty()){
											q1 = true;
										}
									}
									if (i.getEmprecordtype().equals("1")) {
										List<PresentPreviousGovEmp> listq2 = listQ2(cifno, null);
										if(!listq2.isEmpty()){
											q2 = true;
										}
									}
								}
							}
						} 
						pep.setQ1(q1 == null ? false : q1);
						pep.setQ2(q2 == null ? false : q2);
						pep.setGovposition1(info.getGovposition1());
						pep.setGovoffice1(info.getGovoffice1());
						pep.setGovtype1(info.getGovtype1());
						pep.setGovposition2(info.getGovposition2());
						pep.setGovtype2(info.getGovtype2());
						pep.setQ3(info.getQ3() == null ? false : info.getQ3());
						pep.setFamname1(info.getFamname1());
						pep.setRelationship1(info.getRelationship1());
						pep.setFamgovposition1(info.getFamgovposition1());
						pep.setFamgovoffice1(info.getFamgovoffice1());
						pep.setFamgovtype1(info.getFamgovtype1());
						pep.setFamname2(info.getFamname2());
						pep.setRelationship2(info.getRelationship2());
						pep.setFamgovposition2(info.getFamgovposition2());
						pep.setFamgovoffice2(info.getFamgovoffice2());
						pep.setFamgovtype2(info.getFamgovtype2());
						pep.setDateupdated(new Date());
						pep.setUpdatedby(UserUtil.securityService.getUserName());
						
						OtherInformationFacade p = new OtherInformationFacade();
						String status =	p.riskStatus("1", "I", "PEP", q1, q2, info.getQ3(), false, false);
						pep.setPepstatus(status);
						
						if (dbService.saveOrUpdate(pep)) {
							params.put("cifno", cifno);
							@SuppressWarnings("unchecked")
							List<Tbpepq3> peplist = (List<Tbpepq3>) dbService.executeListHQLQuery("FROM Tbpepq3 WHERE cifno=:cifno", params);
							Tbpepinfo i = (Tbpepinfo) dbService.executeUniqueHQLQuery("FROM Tbpepinfo WHERE cifno=:cifno", params);
							if (info != null && !i.getQ3()) {
								if (peplist != null) {
										dbService.delete(peplist);
								}
							}
							flag = "success";
						}
					} else {
						/////////--------if pep is null-----------/////////
						Tbpepinfo p = new Tbpepinfo();
						if (emp != null && !emp.isEmpty()) {
							for (Tbcifemployment i : emp) {
								if (i.getEmprecordtype() != null) {
									if (i.getEmprecordtype().equals("0")) {
										List<PresentPreviousGovEmp> listq1 = listQ1(cifno, null);
										if(!listq1.isEmpty()){
											q1 = true;
										}
									}
									if (i.getEmprecordtype().equals("1")) {
										List<PresentPreviousGovEmp> listq2 = listQ2(cifno, null);
										if(!listq2.isEmpty()){
											q2 = true;
										}
									}
								}
							}
						}
						p.setCifno(cifno);
						p.setCreatedby(UserUtil.securityService.getUserName());
						p.setCreateddate(new Date());
						OtherInformationFacade ps = new OtherInformationFacade();
						String status =	ps.riskStatus("1", "I", "PEP", q1, q2, info.getQ3(), false, false);
						p.setPepstatus(status);
						p.setQ1(q1 == null ? false : q1);
						p.setQ2(q2 == null ? false : q2);
						p.setQ3(info.getQ3() == null ? false : info.getQ3());
						if (dbService.save(p)) {
							flag = "success";
						}
					}
				} else {
					// corporate
					Tbpepinfo pepc = (Tbpepinfo) dbService.executeUniqueHQLQuery("FROM Tbpepinfo WHERE cifno=:cifno",
							params);
					List<PEPViewForm> list = listPEP(cifno);
					Boolean q1 = false;
					Boolean q2 = false;
					if (!list.isEmpty() && list != null) {
						for (PEPViewForm i : list) {
							if (i.isQ1()) {
								q1 = true;
							}
							if (i.isQ2()) {
								q2 = true;
							}
						}
					}
					if (pepc != null) {
						pepc.setQ1(q1 == null ? false : q1);
						pepc.setQ2(q2 == null ? false : q2);
						pepc.setDateupdated(new Date());
						pepc.setUpdatedby(UserUtil.securityService.getUserName());
						
						
						OtherInformationFacade a = new OtherInformationFacade();
						
						String pepstat = a.riskStatus("1", "C", "PEP", q1, q2, false, false, false);
						pepc.setPepstatus(pepstat);
						
						if (dbService.saveOrUpdate(pepc)) {
							flag = "success";
						}
					} else {
						Tbpepinfo pepcorp = new Tbpepinfo();
						pepcorp.setQ1(q1 == null ? false : q1);
						pepcorp.setQ2(q2 == null ? false : q2);
						pepcorp.setCreatedby(UserUtil.securityService.getUserName());
						pepcorp.setCreateddate(new Date());
						pepcorp.setCifno(cifno);
						
						OtherInformationFacade a = new OtherInformationFacade();
						
						String pepstat = a.riskStatus("1", "C", "PEP", q1, q2, false, false, false);
						
						pepcorp.setPepstatus(pepstat);
						if (dbService.save(pepcorp)) {
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

	@Override
	public Tbpepinfo getPepInfo(String cifno) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		Tbpepinfo pep = null;
		try {
			if (cifno != null) {
				params.put("cifno", cifno);
				pep = (Tbpepinfo) dbService.executeUniqueHQLQuery("FROM Tbpepinfo WHERE cifno=:cifno", params);
				String custtype = cifno.substring(0, 1);
				if (custtype.equals("1")) {
					// individual
					if (pep != null) {
						// re-check q1 and q2 by record type.
						FullDataEntryService serv = new FullDataEntryServiceImpl();
						List<Tbcifemployment> emp = serv.getListEmployment(cifno);
						Boolean q1 = false;
						Boolean q2 = false;

						if (emp != null && !emp.isEmpty()) {
							for (Tbcifemployment i : emp) {
								if (i.getEmprecordtype() != null) {
									if (i.getEmprecordtype().equals("0")) {
										List<PresentPreviousGovEmp> listq1 = listQ1(cifno, null);
										if (!listq1.isEmpty()) {
											q1 = true;
										}
									}
									if (i.getEmprecordtype().equals("1")) {
										List<PresentPreviousGovEmp> listq2 = listQ2(cifno, null);
										if (!listq2.isEmpty()) {
											q2 = true;
										}
									}
								}
							}
						}
						
						pep.setQ1(q1 == null ? false : q1);
						pep.setQ2(q2 == null ? false : q2);
						OtherInformationFacade p = new OtherInformationFacade();
						String status =	p.riskStatus("1", "I", "PEP", q1, q2, false, false, false);
						pep.setPepstatus(status);
						if (dbService.saveOrUpdate(pep)) {
							return pep;
						}
					} else {

						FullDataEntryService serv = new FullDataEntryServiceImpl();
						List<Tbcifemployment> emp = serv.getListEmployment(cifno);
						Boolean q1 = false;
						Boolean q2 = false;
						///////// --------if pep is null-----------/////////
						Tbpepinfo p = new Tbpepinfo();
						if (emp != null && !emp.isEmpty()) {
							for (Tbcifemployment i : emp) {
								if (i.getEmprecordtype() != null) {
									if (i.getEmprecordtype().equals("0")) {
										List<PresentPreviousGovEmp> listq1 = listQ1(cifno, null);
										if (!listq1.isEmpty()) {
											q1 = true;
										}
									}
									if (i.getEmprecordtype().equals("1")) {
										List<PresentPreviousGovEmp> listq2 = listQ2(cifno, null);
										if (!listq2.isEmpty()) {
											q2 = true;
										}
									}
								}
							}
						} 
						
						p.setCifno(cifno);
						p.setCreatedby(UserUtil.securityService.getUserName());
						p.setCreateddate(new Date());
						OtherInformationFacade ps = new OtherInformationFacade();
						String status = ps.riskStatus("1", "I", "PEP", q1, q2, false, false, false);
						p.setPepstatus(status);
						p.setQ1(q1 == null ? false : q1);
						p.setQ2(q2 == null ? false : q2);
						p.setQ3(false);
						if (dbService.save(p)) {
							return p;
						}
					}
				} else {
					// corporate
					Tbpepinfo pepc = (Tbpepinfo) dbService.executeUniqueHQLQuery("FROM Tbpepinfo WHERE cifno=:cifno",
							params);
					List<PEPViewForm> list = listPEP(cifno);
					Boolean q1 = false;
					Boolean q2 = false;
					if (!list.isEmpty() && list != null) {
						for (PEPViewForm i : list) {
							if (i.isQ1()) {
								q1 = true;
							}
							if (i.isQ2()) {
								q2 = true;
							}
						}
					}
					if (pepc != null) {
						pepc.setQ1(q1 == null ? false : q1);
						pepc.setQ2(q2 == null ? false : q2);
						pepc.setDateupdated(new Date());
						pepc.setUpdatedby(UserUtil.securityService.getUserName());
						
						
						OtherInformationFacade a = new OtherInformationFacade();
						String pepstat = a.riskStatus("1", "C", "PEP", q1, q2, pepc.getQ3(), false, false);
						pepc.setPepstatus(pepstat);
						
						if (dbService.saveOrUpdate(pepc)) {
							return pepc;
						}
					} else {
						Tbpepinfo pepcorp = new Tbpepinfo();
						pepcorp.setQ1(q1 == null ? false : q1);
						pepcorp.setQ2(q2 == null ? false : q2);
						pepcorp.setCreatedby(UserUtil.securityService.getUserName());
						pepcorp.setCreateddate(new Date());
						pepcorp.setCifno(cifno);
						
						OtherInformationFacade a = new OtherInformationFacade();
						
						String pepstat = a.riskStatus("1", "C", "PEP", q1, q2, false, false, false);
						
						pepcorp.setPepstatus(pepstat);
						if (dbService.save(pepcorp)) {
							return pepcorp;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.exceptionError(e, this.getClass());
		}
		return pep;
	}

	/***
	 * PEP VIEW for Corporate List relation where Relationship is OWNER. System
	 * shall display the list of owner/s or partners and their corresponding PEP
	 * Information.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<PEPViewForm> listPEP(String cifno) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbmanagement> rel = new ArrayList<Tbmanagement>();
		
		List<PEPViewForm> list = new ArrayList<PEPViewForm>();
		try {
			if (cifno != null) {
				params.put("cifno", cifno);

				rel = (List<Tbmanagement>) dbService
						.executeListHQLQuery("FROM Tbmanagement WHERE cifno=:cifno", params);
				if (rel != null) {
					for (Tbmanagement a : rel) {
						PEPViewForm pepForm = new PEPViewForm();
						pepForm.setCifno(a.getRelatedcifno());
						String relcifno = a.getRelatedcifno();
						params.put("relcifno", relcifno);
						Tbpepinfo pep = new Tbpepinfo();
						pep = getPepInfo(relcifno);
						if (pep != null) {
							pepForm.setQ1(pep.getQ1() == null ? false : pep.getQ1());
							pepForm.setQ2(pep.getQ2() == null ? false : pep.getQ2());
							pepForm.setQ3(pep.getQ3() == null ? false : pep.getQ3());
							pepForm.setPepstatus(pep.getPepstatus());
						}else{
							pepForm.setQ1(false);
							pepForm.setQ2(false);
							pepForm.setQ3(false);
							pepForm.setPepstatus("L");			
						}
						if (a.getCustomertype().equals("1")) {
							pepForm.setName(a.getLastname() + ", " + a.getFirstname() + " " + a.getSuffix() + " "
									+ a.getMiddlename());
						} else {
							pepForm.setName(a.getCorporatename());
						}
						params.put("relcode", a.getRelationshipcode());
						Tbcodetable r = (Tbcodetable) dbService.executeUniqueHQLQuery(
								"FROM Tbcodetable WHERE codename='RELATIONSHIPCODE' AND codevalue=:relcode",params);						
						pepForm.setRelationship(r.getDesc1());
						list.add(pepForm);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<PresentPreviousGovEmp> listQ1(String cifno, String empstatus) {
		List<PresentPreviousGovEmp> q1 = new ArrayList<PresentPreviousGovEmp>();
		FullDataEntryService serv = new FullDataEntryServiceImpl();
		List<Tbcifemployment> emp = serv.getListEmployment(cifno);
		Tbcodetable c = new Tbcodetable();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(!emp.isEmpty()){
				for (Tbcifemployment i : emp) {
					if (i.getEmprecordtype() != null) {
						//0 = Present
//						Position in the Gov't = Position
//						Gov't Office (Agency/GOCC) = Employer Name
//						Gov't Type = business type of the employer
						if (i.getEmprecordtype().equals("0")) {
							
							params.put("cifno", i.getEmpcifno());
							Tbcifcorporate corp = (Tbcifcorporate)dbService.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno=:cifno",params);
							
							params.put("businesstype", corp.getBusinesstype());
							Tbcodetable btype = (Tbcodetable) dbService.executeUniqueHQLQuery(
									"FROM Tbcodetable WHERE id.codename='BUSINESSTYPE' AND id.codevalue=:businesstype",params);
							
							if (corp != null && (corp.getBusinesstype().equals("21") || 
									corp.getBusinesstype().equals("22")
									||corp.getBusinesstype().equals("23")
									||corp.getBusinesstype().equals("24")
									||corp.getBusinesstype().equals("27"))) {
								PresentPreviousGovEmp ppg = new PresentPreviousGovEmp();
								params.put("pos", i.getPosition());
								c = (Tbcodetable) dbService.executeUniqueHQLQuery(
										"FROM Tbcodetable WHERE id.codename='POSITION' AND id.codevalue=:pos",
										params);
								ppg.setGovType(btype.getDesc1());
								ppg.setPositionInGov(c.getDesc1());
								ppg.setOfficeAgencyGOCC(i.getEmployername());
								q1.add(ppg);
							}
						}
					}
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return q1;
	}

	@Override
	public List<PresentPreviousGovEmp> listQ2(String cifno, String empstatus) {
		List<PresentPreviousGovEmp> q2 = new ArrayList<PresentPreviousGovEmp>();
		FullDataEntryService serv = new FullDataEntryServiceImpl();
		List<Tbcifemployment> emp = serv.getListEmployment(cifno);
		Tbcodetable c = new Tbcodetable();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(!emp.isEmpty()){
				for (Tbcifemployment i : emp) {
					if (i.getEmprecordtype() != null) {
						//0 = Present
//						Position in the Gov't = Position
//						Gov't Office (Agency/GOCC) = Employer Name
//						Gov't Type = business type of the employer
						if (i.getEmprecordtype().equals("1")) {
							params.put("cifno", i.getEmpcifno());
							Tbcifcorporate corp = (Tbcifcorporate)dbService.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno=:cifno",params);
							
							params.put("businesstype", corp.getBusinesstype());
							Tbcodetable btype = (Tbcodetable) dbService.executeUniqueHQLQuery(
									"FROM Tbcodetable WHERE id.codename='BUSINESSTYPE' AND id.codevalue=:businesstype",params);
							
							if (corp != null && (corp.getBusinesstype().equals("21") || 
									corp.getBusinesstype().equals("22")
									||corp.getBusinesstype().equals("23")
									||corp.getBusinesstype().equals("24")
									||corp.getBusinesstype().equals("27"))) {
								PresentPreviousGovEmp ppg = new PresentPreviousGovEmp();
								params.put("pos", i.getPosition());
								c = (Tbcodetable) dbService.executeUniqueHQLQuery(
										"FROM Tbcodetable WHERE id.codename='POSITION' AND id.codevalue=:pos",
										params);
								ppg.setGovType(btype.getDesc1());
								ppg.setPositionInGov(c.getDesc1());
								ppg.setOfficeAgencyGOCC(i.getEmployername());
								q2.add(ppg);
							}
						}
					}
				}				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return q2;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbpepq3> listQ3(String cifno) {
		DBService dbService = new DBServiceImplCIF();
		List<Tbpepq3> list = new ArrayList<Tbpepq3>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(cifno != null){
				params.put("cifno", cifno);
				list = (List<Tbpepq3>)dbService.executeListHQLQuery("FROM Tbpepq3 WHERE cifno=:cifno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveQ3(Tbpepq3 q3) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(q3.getId() != null){
				params.put("id", q3.getId());
				Tbpepq3 a = (Tbpepq3)dbService.executeUniqueHQLQuery("FROM Tbpepq3 WHERE id=:id", params);
				if(a != null){
					a.setFamilymembername(q3.getFamilymembername());
					a.setGovagencygocc(q3.getGovagencygocc());
					a.setGovposition(q3.getGovposition());
					a.setGovtype(q3.getGovtype());
					a.setRelationship(q3.getRelationship());
					a.setOtherrelationship(q3.getOtherrelationship());
					dbService.saveOrUpdate(a);					
				}
			}else{
				Tbpepq3 b = new Tbpepq3();
				b.setCifno(q3.getCifno());
				b.setFamilymembername(q3.getFamilymembername());
				b.setGovagencygocc(q3.getGovagencygocc());
				b.setGovposition(q3.getGovposition());
				b.setGovtype(q3.getGovtype());
				b.setRelationship(q3.getRelationship());
				b.setOtherrelationship(q3.getOtherrelationship());
				dbService.save(b);	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteQ3(Integer id) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(id != null){
				params.put("id", id);
				Tbpepq3 q3 = (Tbpepq3)dbService.executeUniqueHQLQuery("FROM Tbpepq3 WHERE id=:id", params);
				if(q3 != null){
					dbService.delete(q3);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CodetableForm> listGovernmentType() {
		List<CodetableForm> codelist = new ArrayList<CodetableForm>();
		DBService dbService = new DBServiceImplCIF();
		try {
			List<Tbcodetable> codetable = (List<Tbcodetable>) dbService
					.executeListHQLQuery("FROM Tbcodetable WHERE desc1 like '%government%' AND codename='BUSINESSTYPE' ORDER BY desc1 ASC", null);
			if (codetable != null) {
				for (Tbcodetable ct : codetable) {
					CodetableForm form = new CodetableForm();
					form.setCodename(ct.getId().getCodename());
					form.setCodevalue(ct.getId().getCodevalue());
					form.setDesc1(ct.getDesc1());
					form.setDesc2(ct.getDesc2());
					form.setRemarks(ct.getRemarks());
					codelist.add(form);
				}
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codelist;
	}

	//Renz
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcifpepinfo> listPEPQ1(String cifno) {
		List<Tbcifpepinfo> list = new ArrayList<Tbcifpepinfo>();
		Map<String, Object> params = HQLUtil.getMap();
		DBService dbService = new DBServiceImplCIF();
		params.put("cifno", cifno);
		try {	
			list = (List<Tbcifpepinfo>) dbService.executeListHQLQuery("FROM Tbcifpepinfo WHERE cifno =:cifno AND type = 'Q1'",params);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcifpepinfo> listPEPQ2(String cifno) {
		List<Tbcifpepinfo> list = new ArrayList<Tbcifpepinfo>();
		Map<String, Object> params = HQLUtil.getMap();
		DBService dbService = new DBServiceImplCIF();
		params.put("cifno", cifno);
		try {	
			list = (List<Tbcifpepinfo>) dbService.executeListHQLQuery("FROM Tbcifpepinfo WHERE cifno =:cifno AND type = 'Q2'",params);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deletePEP(Integer id) {
		Map<String, Object> params = HQLUtil.getMap();
		DBService dbService = new DBServiceImplCIF();
		String flag = "failed";
		params.put("id", id);
		try {
			if (id != null) {

				Integer res = dbService.executeUpdate("DELETE FROM TBCIFPEPINFO WHERE id =:id", params);
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
	public String saveOrUpdateQ1(Tbcifpepinfo ref) {
		Map<String, Object> params = HQLUtil.getMap();
		DBService dbService = new DBServiceImplCIF();
		Tbcifpepinfo d = new Tbcifpepinfo();
		if(ref.getId() != null)
		{
			params.put("id", ref.getId());
			d = (Tbcifpepinfo) dbService.executeUniqueHQLQuery("FROM Tbcifpepinfo WHERE id=:id", params);
			d.setCifno(ref.getCifno());
			d.setGovtype(ref.getGovtype());
			d.setGovname(ref.getGovname());
			d.setPosition(ref.getPosition());
			d.setType("Q1");
			
			if(dbService.saveOrUpdate(d)) {
				System.out.println("UPDATE");
				return "update";
			}
		}
		else {
			ref.setType("Q1");
			if(dbService.saveOrUpdate(ref)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}
	
	@Override
	public String saveOrUpdateQ2(Tbcifpepinfo ref) {
		Map<String, Object> params = HQLUtil.getMap();
		DBService dbService = new DBServiceImplCIF();
		Tbcifpepinfo d = new Tbcifpepinfo();
		if(ref.getId() != null)
		{
			params.put("id", ref.getId());
			d = (Tbcifpepinfo) dbService.executeUniqueHQLQuery("FROM Tbcifpepinfo WHERE id=:id", params);
			d.setCifno(ref.getCifno());
			d.setGovtype(ref.getGovtype());
			d.setGovname(ref.getGovname());
			d.setPosition(ref.getPosition());
			d.setType("Q2");
			
			if(dbService.saveOrUpdate(d)) {
				System.out.println("UPDATE");
				return "update";
			}
		}
		else {
			ref.setType("Q2");
			if(dbService.saveOrUpdate(ref)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}
}
