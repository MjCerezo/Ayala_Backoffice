package com.etel.amla;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbamlaindividual;
import com.coopdb.data.Tbamlalistmain;
import com.coopdb.data.Tbamlanoncountries;
import com.coopdb.data.Tbamlarequest;
import com.coopdb.data.Tbcountry;
import com.coopdb.data.Tbmember;
import com.etel.amla.forms.AMLANonCountriesForm;
import com.etel.amla.forms.AMLApprovalForm;
import com.etel.amla.forms.AmlaForm;
import com.etel.amla.forms.AmlaInquiryForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.TransactionNoGenerator;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class AmlaInquiryServiceImpl implements AmlaInquiryService {
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbamlalistmain> searchAmla(AmlaInquiryForm form) {
		// TODO SEARCH AMLA RECORD
		DBService dbService = new DBServiceImpl();
		StringBuilder query = new StringBuilder();
		List<Tbamlalistmain> list = new ArrayList<Tbamlalistmain>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dob = null;
		String doi = null;
		try {
			if (form.getCustomertype() == null && form.getFullname() == null && form.getDateofbirth() == null
					&& form.getStatus() == null && form.getDateofincorporation() == null) {
				list = null;
				System.out.println(">>>>>>>> ALL FIELDS : NULL <<<<<<<<<");
				return null;
			} else {
				query.append("FROM Tbamlalistmain WHERE ");

				if (form.getFullname() != null) {
					query.append("fullname like '%" + form.getFullname() + "%' AND ");
				}

				if (form.getDateofbirth() != null) {
					dob = formatter.format(form.getDateofbirth());
					query.append("dateofbirth BETWEEN '" + dob + " 00:00:00' AND '" + dob + " 23:59:00')" + " AND ");
				}

				if (form.getDateofincorporation() != null) {
					doi = formatter.format(form.getDateofincorporation());
					query.append(
							"dateofincorporation BETWEEN '" + doi + " 00:00:00' AND '" + doi + " 23:59:00')" + " AND ");
				}

				if (form.getStatus() != null) {
					query.append("status ='" + form.getStatus() + "' AND ");
				}

				if (form.getCustomertype() != null) {
					query.append("customertype = '" + form.getCustomertype() + "'     ");
				}
				query.append("12345");
				String listquery = query.substring(0, query.length() - 10);
				System.out.println(listquery);
				list = (List<Tbamlalistmain>) dbService.executeListHQLQuery(listquery, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String addAmlaRequest(AmlaForm form) {
		// TODO Add new Amla request - save to TBamlarequest
		DBService dbService = new DBServiceImpl();
		Tbamlarequest newrequest = new Tbamlarequest();
		String flag = "Failed";
		try {
			if (form.getRequesttype().equals("0") && form.getRequeststatus().equals("1")) {// newforvalidationapproval
				if (form.getOpenendeddate()) {
					if (form.getCustomertype().equals("1")) {
						if (form.getFirstname() == null || form.getLastname() == null || form.getDateofbirth() == null
								|| form.getCountry() == null || form.getStartdate() == null || form.getRemarks() == null
								|| form.getDescription() == null || form.getAmlalistsource() == null) {
							return "validate";
						}
					} else {
						if (form.getCorporatename() == null || form.getDateofincorporation() == null
								|| form.getCountry() == null || form.getStartdate() == null || form.getRemarks() == null
								|| form.getDescription() == null || form.getAmlalistsource() == null) {
							return "validate";
						}
					}
				} else if (!form.getOpenendeddate() || form.getOpenendeddate().equals(null)) {
					if (form.getCustomertype().equals("1")) {
						if (form.getFirstname() == null || form.getLastname() == null || form.getDateofbirth() == null
								|| form.getCountry() == null || form.getStartdate() == null || form.getEnddate() == null
								|| form.getRemarks() == null || form.getDescription() == null
								|| form.getAmlalistsource() == null) {
							return "validate";
						}
					} else {
						if (form.getCorporatename() == null || form.getDateofincorporation() == null
								|| form.getCountry() == null || form.getStartdate() == null || form.getEnddate() == null
								|| form.getRemarks() == null || form.getDescription() == null
								|| form.getAmlalistsource() == null) {
							return "validate";
						}
					}
				}
			}
			if (form.getRequesttype().equals("0") && form.getRequeststatus().equals("0"))// newvalidationdraft
			{
				if (form.getCustomertype().equals("1")) {
					if (form.getLastname() == null || form.getFirstname() == null || form.getDateofbirth() == null) {
						return "validate";
					}
				} else {
					if (form.getCorporatename() == null || form.getDateofincorporation() == null) {
						return "validate";
					}
				}
			}
			if (form.getRequesttype().equals("3")) {// forlift AMLA
				if (form.getAmlalistid() != null) {
					newrequest.setAmlalistid(form.getAmlalistid());
					newrequest.setAmlalistsource(form.getAmlalistsource());
					newrequest.setAmlaliststatus(form.getAmlaliststatus());
					newrequest.setCifno(form.getCifno());
					newrequest.setRequestedby(secservice.getUserName());
					newrequest.setRequestdate(new Date());
					newrequest.setCustomertype(form.getCustomertype());
					if (form.getCustomertype().equals("1")) {
						if (form.getMiddlename() == null) {
							form.setMiddlename(" ");
						}
						newrequest.setFullname(
								form.getLastname() + ", " + form.getFirstname() + " " + form.getMiddlename());
					} else {
						newrequest.setFullname(form.getCorporatename());
					}
					newrequest.setStartdate(form.getStartdate());
					newrequest.setEnddate(form.getEnddate());
					newrequest.setRequesttype(form.getRequesttype());
					newrequest.setRequeststatus(form.getRequeststatus());
					newrequest.setLastname(form.getLastname());
					newrequest.setMiddlename(form.getMiddlename());
					newrequest.setFirstname(form.getFirstname());
					newrequest.setCorporatename(form.getCorporatename());
					if (dbService.save(newrequest)) {
						return "success";// for LIFT request. Service cut
											// here.
					} else {
						return "failed";
					}
				} else {
					return "failed";
				}
			}
			newrequest.setFullname(form.getFullname());
			newrequest.setAmlalistid(form.getAmlalistid());
			newrequest.setCifno(form.getCifno());
			newrequest.setCustomertype(form.getCustomertype());
			newrequest.setDescription(form.getDescription());
			newrequest.setLastname(form.getLastname());
			newrequest.setFirstname(form.getFirstname());
			newrequest.setMiddlename(form.getMiddlename());
			newrequest.setDateofbirth(form.getDateofbirth());
			newrequest.setSuffix(form.getSuffix());
			newrequest.setStartdate(form.getStartdate());
			newrequest.setEnddate(form.getEnddate());
			newrequest.setAmlalistsource(form.getAmlalistsource());
			newrequest.setRemarks(form.getRemarks());
			newrequest.setRequestdate(new Date());
			newrequest.setRequeststatus(form.getRequeststatus());
			newrequest.setRequesttype(form.getRequesttype());
			newrequest.setCorporatename(form.getCorporatename());
			newrequest.setDateofincorporation(form.getDateofincorporation());
			newrequest.setRequestedby(secservice.getUserName());
			newrequest.setCountry(form.getCountry());
			newrequest.setAmlaliststatus(form.getAmlaliststatus());
			if (form.getOpenendeddate().equals(true)) {
				newrequest.setIsopenended(form.getOpenendeddate());
			} else {
				newrequest.setIsopenended(false);
			}
			if (!form.getCustomertype().equals("1")) {
				newrequest.setFullname(form.getCorporatename());
			} else {
				if (form.getMiddlename() == null) {
					form.setMiddlename(" ");
				}
				if (form.getFirstname() == null) {
					form.setFirstname(" ");
				}
				if (form.getLastname() == null) {
					form.setLastname(" ");
				}
				newrequest.setFullname(form.getLastname() + ", " + form.getFirstname() + " " + form.getMiddlename());
			}
			if (dbService.save(newrequest)) {
				flag = newrequest.getRequestid().toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String addAmlaIndiv(AmlaForm form) {
		// TODO SAVE TO Tbamlaindividual
		DBService dbService = new DBServiceImpl();
		Tbamlaindividual newindiv = new Tbamlaindividual();
		String flag = "Failed";
		try {
			newindiv.setIsopenended(form.getOpenendeddate());
			newindiv.setAmlalistid(form.getAmlalistid());
			newindiv.setCifno(form.getCifno());
			newindiv.setCountry(form.getCountry());
			newindiv.setDescription(form.getDescription());
			newindiv.setLastname(form.getLastname());
			newindiv.setFirstname(form.getFirstname());
			newindiv.setMiddlename(form.getMiddlename());
			newindiv.setSuffix(form.getSuffix());
			newindiv.setDateofbirth(form.getDateofbirth());
			newindiv.setStartdate(form.getStartdate());
			newindiv.setEnddate(form.getEnddate());
			newindiv.setRemarks(form.getRemarks());
			newindiv.setDatecreated(new Date());
			newindiv.setAmlalistsource(form.getAmlalistsource());
			newindiv.setCreatedby(secservice.getUserName());
			newindiv.setAmlaliststatus(form.getAmlaliststatus());
			if (dbService.save(newindiv)) {// create amla to amlamain
				Tbamlalistmain main = new Tbamlalistmain();
				main.setAmlalistid(form.getAmlalistid());
				main.setCifno(form.getCifno());
				main.setDateofbirth(form.getDateofbirth());
				main.setStartdate(form.getStartdate());
				main.setEnddate(form.getEnddate());
				main.setCreateddate(new Date());
				main.setStatus("1");
				main.setFullname(form.getFullname());
				main.setAmlalistsource(form.getAmlalistsource());
				main.setCustomertype(form.getCustomertype());
				main.setIsopenended(form.getOpenendeddate());
				if (dbService.save(main)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String addAmlaCorp(AmlaForm form) {
		// TODO Save to Tbamlacorporate
//		DBService dbService = new DBServiceImpl();
//		Tbamlacorporate newcorporate = new Tbamlacorporate();
//		String flag = "Failed";
//		try {
//			newcorporate.setAmlalistid(form.getAmlalistid());
//			newcorporate.setCifno(form.getCifno());
//			newcorporate.setCountry(form.getCountry());
//			newcorporate.setDescription(form.getDescription());
//			newcorporate.setDateofincorporation(form.getDateofincorporation());
//			newcorporate.setStartdate(form.getStartdate());
//			newcorporate.setEnddate(form.getEnddate());
//			newcorporate.setRemarks(form.getRemarks());
//			newcorporate.setDatecreated(new Date());
//			newcorporate.setAmlaliststatus(form.getAmlaliststatus());
//			newcorporate.setCorporatename(form.getCorporatename());
//			newcorporate.setCreatedby(secservice.getUserName());
//			newcorporate.setAmlalistsource(form.getAmlalistsource());
//			newcorporate.setIsopenended(form.getOpenendeddate());
//			if (dbService.save(newcorporate)) {// create amla to amlamain
//				Tbamlalistmain main = new Tbamlalistmain();
//				main.setIsopenended(form.getOpenendeddate());
//				main.setAmlalistid(form.getAmlalistid());
//				main.setCifno(form.getCifno());
//				main.setDateofincorporation(form.getDateofincorporation());
//				main.setStartdate(form.getStartdate());
//				main.setEnddate(form.getEnddate());
//				main.setCreateddate(new Date());
//				main.setStatus("1");
//				main.setFullname(form.getCorporatename());
//				main.setAmlalistsource(form.getAmlalistsource());
//				main.setCustomertype(form.getCustomertype());
//				if (dbService.save(main)) {
//					flag = "success";
//				}
//
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
		return null;
	}

	@Override
	public AmlaForm searchCIF(String cifno) {
		// TODO
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
//		Tbmember cifmain = new Tbmember();
		param.put("cifno", cifno);

		try {
//			cifmain = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE cifno =:cifno", param);
//			if (cifmain != null) {
				AmlaForm amla = new AmlaForm();
//				if (cifmain.getCustomertype().equals("1")) {
				Tbmember indiv = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:cifno", param);
				if (indiv != null) {
//						amla.setCustomertype(cifmain.getCustomertype());
					amla.setFirstname(indiv.getFirstname());
					amla.setLastname(indiv.getLastname());
					amla.setMiddlename(indiv.getMiddlename());
					amla.setCountry(indiv.getCountry1());
					amla.setSuffix(indiv.getSuffix());
					amla.setDateofbirth(indiv.getDateofbirth());
					amla.setCifno(indiv.getMembershipid());
					return amla;
				}
//				} else {
//					Tbcifcorporate corp = (Tbcifcorporate) dbService
//							.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno=:cifno", param);
//					if (corp != null) {
//						amla.setCustomertype(cifmain.getCustomertype());
//						amla.setCorporatename(corp.getCorporatename());
//						amla.setDateofincorporation(corp.getDateofincorporation());
//						amla.setCountry(corp.getCountry1());
//						amla.setCifno(corp.getCifno());
//						return amla;
//					}
//				}
//			} else {
//				return null;
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcountry> getAllCountry() {
		List<Tbcountry> list = new ArrayList<Tbcountry>();
		List<Object> obj = new ArrayList<Object>();
		DBService dbService = new DBServiceImpl();
		try {
			obj = (List<Object>) dbService.executeListSQLQuery("SELECT DISTINCT code, country FROM Tbcountry", null);

			if (obj != null) {
				for (Object obj1 : obj) {
					Object obj2[] = (Object[]) obj1;
					ArrayList<String> data = new ArrayList<String>();
					for (Object obj3 : obj2) {
						data.add(String.valueOf(obj3));
					}
					Tbcountry form = new Tbcountry();
					form.setCode(data.get(0));
					form.setCountry(data.get(1));
					list.add(form);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// AMLA APPROVAL
	/***************
	 * Daniel 10182018 * - Fixed Errors
	 ****************/
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbamlarequest> searchRequestByStatus(AMLApprovalForm form, String requestid) {
		// TODO SEARCH AMLA REQUEST BY REQUEST STATUS
		// gustoNiMaricarNaLumalabasAgadLahatNungRequestSaGridSaUnangViewPaLangKahitWalangParametersKayMaricarKayoMagReklamoPagMayReklamoKayo!
		DBService dbService = new DBServiceImpl();
		StringBuilder query = new StringBuilder();
		List<Tbamlarequest> list = new ArrayList<Tbamlarequest>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> param = HQLUtil.getMap();
		list = (List<Tbamlarequest>) dbService.executeListHQLQuery("FROM Tbamlarequest", null);
		String du = "";
//		if (form.getDateuploaded() != null) {
//			du = formatter.format(form.getDateuploaded());
//		}

		try {
			if (requestid != null) {
				param.put("id", Integer.parseInt(requestid));
				Tbamlarequest u = (Tbamlarequest) dbService
						.executeUniqueHQLQuery("FROM Tbamlarequest WHERE requestid=:id", param);
				if (u != null) {
					List<Tbamlarequest> listn = new ArrayList<Tbamlarequest>();
					listn.add(u);
					return listn;
				}
			}
			if (form.getCustomertype() == null && form.getCustomername() == null && form.getRequesttype() == null
					&& form.getRequeststatus() == null && form.getSource() == null && form.getDateuploaded() == null) {
				return list;
			}
			query.append("FROM Tbamlarequest WHERE ");

			if (form.getCustomername() != null) {
				query.append("fullname like '%" + form.getCustomername() + "%' AND ");
			}
			if (form.getCustomertype() != null) {
				query.append("customertype = '" + form.getCustomertype() + "' AND ");
			}
			if (form.getRequeststatus() != null) {
				query.append("requeststatus =" + form.getRequeststatus() + " AND ");
			}
			if (form.getRequesttype() != null) {
				query.append("requesttype =" + form.getRequesttype() + " AND ");
			}
			if (form.getSource() != null) {
				query.append("amlalistsource =" + form.getSource() + " AND ");
			}
			if (form.getDateuploaded() != null) {
				du = formatter.format(form.getDateuploaded());
				query.append("(uploadeddate BETWEEN '" + du + " 00:00:00' AND '" + du + " 23:59:00') AND ");
			}

			query.append("12345");
			String listquery = query.substring(0, query.length() - 10);
			list = (List<Tbamlarequest>) dbService.executeListHQLQuery(listquery, null);
		} catch (NullPointerException e) {
			e.printStackTrace();
			e.printStackTrace();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

	@Override
	public Tbamlarequest getRequestRecord(Integer requestid) {
		// TODO Get Request record
		Tbamlarequest request = new Tbamlarequest();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("requestid", requestid);
		try {
			request = (Tbamlarequest) dbService
					.executeUniqueHQLQueryMaxResultOne("FROM Tbamlarequest WHERE requestid=:requestid", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}

	@Override
	public String saveNewAmlaMain(AmlaForm form) {
		// TODO Save NEw Blacklist to TBAMLALISTMAIN
		DBService dbService = new DBServiceImpl();
		Tbamlalistmain newindiv = new Tbamlalistmain();
		String flag = "Failed";
		try {
			newindiv.setDateofincorporation(form.getDateofincorporation());
			newindiv.setAmlalistid(form.getAmlalistid());
			newindiv.setCifno(form.getCifno());
			newindiv.setFullname(form.getFullname());
			newindiv.setDateofbirth(form.getDateofbirth());
			newindiv.setStartdate(form.getStartdate());
			newindiv.setEnddate(form.getEnddate());
			newindiv.setStatus(form.getAmlaliststatus());
			newindiv.setCustomertype(form.getCustomertype());
			newindiv.setCreateddate(new Date());
			newindiv.setAmlalistsource(form.getAmlalistsource());
			if (dbService.save(newindiv)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String updateAmlaRequestStatus(Integer requestid, String requeststatus) {
		// TODO Update AMLA Request Status
		// 2 for Approved; 3 for Cancelled ; 4 for Rejected
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Tbamlarequest request = new Tbamlarequest();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("requestid", requestid);
		try {
			request = (Tbamlarequest) dbService.executeUniqueHQLQuery("FROM Tbamlarequest WHERE requestid=:requestid",
					param);
			if (request != null) {
				request.setRequeststatus(requeststatus);
				if (dbService.saveOrUpdate(request)) {
					if (request.getRequesttype().equals("0") && requeststatus.equals("2")) {// newrequest-approved
						AmlaForm form = new AmlaForm();
						form.setAmlalistsource(request.getAmlalistsource());
						form.setApproverremarks(request.getApproverremarks());
						form.setCifno(request.getCifno());
						form.setCorporatename(request.getCorporatename());
						form.setCountry(request.getCountry());
						form.setCreatedby(request.getCreatedby());
						form.setCustomertype(request.getCustomertype());
						form.setDateofbirth(request.getDateofbirth());
						form.setDateofincorporation(request.getDateofincorporation());
						form.setDescription(request.getDescription());
						form.setEnddate(request.getEnddate());
						form.setFirstname(request.getFirstname());
						form.setFullname(request.getFullname());
						form.setLastname(request.getLastname());
						form.setMiddlename(request.getMiddlename());
						form.setRemarks(request.getRemarks());
						form.setRequestdate(request.getRequestdate());
						form.setRequestedby(request.getRequestedby());
						form.setRequeststatus(requeststatus);
						form.setAmlaliststatus("1");
						form.setRequesttype(request.getRequesttype());
						form.setSuffix(request.getSuffix());
						form.setStartdate(request.getStartdate());
						form.setCustomertype(request.getCustomertype());
						form.setOpenendeddate(request.getIsopenended());
						String AMLA_ID = TransactionNoGenerator.generateIdNo("AMLA");
//						NoGenerator amla = new NoGenerator();
						if (request.getCustomertype().equals("1")) {
							form.setFullname(request.getLastname() + " " + request.getFirstname() + " "
									+ request.getMiddlename());
							form.setAmlalistid(AMLA_ID);
							form.setCustomertype("1");
							this.addAmlaIndiv(form);// createToAmlaIndiv
						} else {
							form.setCustomertype(request.getCustomertype());
							form.setAmlalistid(AMLA_ID);
							this.addAmlaCorp(form); // createToAmlaCorp
						}
					}
					if (request.getRequesttype().equals("3") && requeststatus.equals("2")) {// liftrequest-approved
						if (request.getAmlalistid() != null) {
							param.put("id", request.getAmlalistid());
							Tbamlalistmain m = (Tbamlalistmain) dbService
									.executeUniqueHQLQuery("FROM Tbamlalistmain WHERE amlalistid=:id", param);
							if (m != null) {
								m.setStatus("3");// lifted
								if (dbService.saveOrUpdate(m)) {
									return this.liftAMLAPerTable(m.getAmlalistid(), m.getCustomertype());
								}
							}
						}
					}
					if (requeststatus.equals("3")) {// cancel-request
						return "cancelled";
					}
					return "success";
				}

			}
		} catch (

		Exception e)

		{
			e.printStackTrace();
		}
		return flag;

	}

	@Override
	public Tbamlaindividual getIndividualRecord(String amlalistid) {
		// TODO Auto-generated method stub
		Tbamlaindividual indivrecord = new Tbamlaindividual();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("amlalistid", amlalistid);
		try {
			indivrecord = (Tbamlaindividual) dbService
					.executeUniqueHQLQueryMaxResultOne("FROM Tbamlaindividual WHERE amlalistid=:amlalistid", param);
			if (indivrecord != null) {
				return indivrecord;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indivrecord;
	}

//	@Override
//	public Tbamlacorporate getCorporateRecord(String amlalistid) {
//		// TODO GET CORPORATE RECORD
//		Tbamlacorporate corprecord = new Tbamlacorporate();
//		DBService dbService = new DBServiceImpl();
//		Map<String, Object> param = HQLUtil.getMap();
//		param.put("amlalistid", amlalistid);
//
//		try {
//			corprecord = (Tbamlacorporate) dbService
//					.executeUniqueHQLQueryMaxResultOne("FROM Tbamlacorporate WHERE amlalistid=:amlalistid", param);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return corprecord;
//	}

	@Override
	public String updateNewAmlaMain(AmlaForm form) {
		// TODO UPDATE AMLA to TBAMLAMAIN
		DBService dbService = new DBServiceImpl();
		Tbamlalistmain newindiv = new Tbamlalistmain();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("amlalistid", form.getAmlalistid());
		String flag = "Failed";
		try {
			newindiv = (Tbamlalistmain) dbService
					.executeUniqueHQLQuery("FROM Tbamlalistmain WHERE amlalistid =:amlalistid ", param);
			if (newindiv != null) {
				newindiv.setDateofincorporation(form.getDateofincorporation());
				newindiv.setCifno(form.getCifno());
				newindiv.setFullname(form.getFullname());
				newindiv.setDateofbirth(form.getDateofbirth());
				newindiv.setStartdate(form.getStartdate());
				newindiv.setEnddate(form.getEnddate());
				newindiv.setStatus(form.getAmlaliststatus());
				newindiv.setCustomertype(form.getCustomertype());
				newindiv.setAmlalistsource(form.getAmlalistsource());
				if (dbService.update(newindiv)) {
					flag = "success";
				}
			} else {
				flag = "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String updateAmlaIndiv(AmlaForm form) {
		DBService dbService = new DBServiceImpl();
		Tbamlaindividual newindiv = new Tbamlaindividual();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("amlalistid", form.getAmlalistid());
		String flag = "Failed";
		try {
			newindiv = (Tbamlaindividual) dbService
					.executeUniqueHQLQuery("FROM Tbamlaindividual WHERE amlalistid =:amlalistid ", param);
			if (newindiv != null) {
				newindiv.setCifno(form.getCifno());
				newindiv.setCountry(form.getCountry());
				newindiv.setDescription(form.getDescription());
				newindiv.setLastname(form.getLastname());
				newindiv.setFirstname(form.getFirstname());
				newindiv.setMiddlename(form.getMiddlename());
				newindiv.setSuffix(form.getSuffix());
				newindiv.setDateofbirth(form.getDateofbirth());
				newindiv.setStartdate(form.getStartdate());
				newindiv.setEnddate(form.getEnddate());
				newindiv.setRemarks(form.getRemarks());
				newindiv.setAmlalistsource(form.getAmlalistsource());
				newindiv.setAmlaliststatus(form.getAmlaliststatus());
				if (dbService.update(newindiv)) {
					flag = "success";
				}
			} else {
				flag = "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String updateAmlaCorp(AmlaForm form) {
//		DBService dbService = new DBServiceImpl();
//		Tbamlacorporate newcorporate = new Tbamlacorporate();
//		Map<String, Object> param = HQLUtil.getMap();
//		param.put("amlalistid", form.getAmlalistid());
//		String flag = "Failed";
//		try {
//			newcorporate = (Tbamlacorporate) dbService
//					.executeUniqueHQLQuery("FROM Tbamlacorporate WHERE amlalistid =:amlalistid ", param);
//			if (newcorporate != null) {
//				newcorporate.setCifno(form.getCifno());
//				newcorporate.setCountry(form.getCountry());
//				newcorporate.setDescription(form.getDescription());
//				newcorporate.setDateofincorporation(form.getDateofincorporation());
//				newcorporate.setStartdate(form.getStartdate());
//				newcorporate.setEnddate(form.getEnddate());
//				newcorporate.setRemarks(form.getRemarks());
//				newcorporate.setAmlaliststatus(form.getAmlaliststatus());
//				newcorporate.setCorporatename(form.getCorporatename());
//				newcorporate.setAmlalistsource(form.getAmlalistsource());
//				if (dbService.update(newcorporate)) {
//					flag = "success";
//				}
//			} else {
//				flag = "failed";
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbamlanoncountries> getAllNonCoopCountries() {
		// TODO Get ALL list of NOn Cooperative Countries
		DBService dbService = new DBServiceImpl();
		List<Tbamlanoncountries> noncountries = new ArrayList<Tbamlanoncountries>();
		try {
			noncountries = (List<Tbamlanoncountries>) dbService.executeListHQLQuery("FROM Tbamlanoncountries", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noncountries;
	}

	@Override
	public String updateAmalNonCoopCountries(AMLANonCountriesForm form) {
		// TODO Save or Update AMLA non coop countries
		DBService dbService = new DBServiceImpl();
		Tbamlanoncountries noncountries = new Tbamlanoncountries();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("countrycode", form.getCountrycode());
		String flag = "Failed";
		try {
			noncountries = (Tbamlanoncountries) dbService
					.executeUniqueHQLQuery("FROM Tbamlanoncountries WHERE countrycode =:countrycode ", param);
			if (noncountries != null) {
				noncountries.setEnddate(form.getEnddate());
				noncountries.setReference(form.getReference());
				noncountries.setRemarks(form.getRemarks());
				noncountries.setStartdate(form.getStartdate());
				if (dbService.update(noncountries)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = "failed";
		}
		return flag;
	}

	@Override
	public String addAmlaNonCoopCountries(AMLANonCountriesForm form) {
		// TODO Add new Entry
		DBService dbService = new DBServiceImpl();
		Tbamlanoncountries noncountries = new Tbamlanoncountries();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("countrycode", form.getCountrycode());
		String flag = "Failed";
		try {
			noncountries.setCountrycode(form.getCountrycode());
			noncountries.setCountryname(form.getCountryname());
			noncountries.setCreatedby(secservice.getUserName());
			noncountries.setDatecreated(new Date());
			noncountries.setEnddate(form.getEnddate());
			noncountries.setReference(form.getReference());
			noncountries.setRemarks(form.getRemarks());
			noncountries.setStartdate(form.getStartdate());
			noncountries.setUploadbatch(form.getUploadbatch());
			noncountries.setUploadeddate(form.getUploadeddate());
			noncountries.setUploadbatch(form.getUploadbatch());
			noncountries.setUploadrefid(form.getUploadrefid());
			if (dbService.save(noncountries)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = "failed";
		}
		return flag;
	}

	@Override
	public Tbamlanoncountries getAmlaNonCoopRecord(AMLANonCountriesForm form) {
		// TODO getNONCOOP RECORD
		Tbamlanoncountries amla = new Tbamlanoncountries();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("countrycode", form.getCountrycode());

		try {
			amla = (Tbamlanoncountries) dbService
					.executeUniqueHQLQueryMaxResultOne("FROM Tbamlanoncountries WHERE countrycode=:countrycode", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return amla;
	}

	@Override
	public String deleteAmlaNonCoop(AMLANonCountriesForm form) {
		// TODO Auto-generated method stub
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("countrycode", form.getCountrycode());
		Tbamlanoncountries amla = new Tbamlanoncountries();
		try {
			amla = (Tbamlanoncountries) dbService
					.executeUniqueHQLQuery("FROM Tbamlanoncountries WHERE countrycode=:countrycode", params);
			if (dbService.delete(amla)) {
				flag = "successful";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String liftAMLAPerTable(String amlaid, String customertype) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (amlaid != null && customertype != null) {
				params.put("id", amlaid);
				if (customertype.equals("1")) {// individual
					Tbamlaindividual i = (Tbamlaindividual) dbService
							.executeUniqueHQLQuery("FROM Tbamlaindividual WHERE amlalistid=:id", params);
					if (i != null) {
						i.setAmlaliststatus("3");// lifted
						if (dbService.saveOrUpdate(i))
							return "success";
					}
				} else {// corp-sole
//					Tbamlacorporate c = (Tbamlacorporate) dbService
//							.executeUniqueHQLQuery("FROM Tbamlacorporate WHERE amlalistid=:id", params);
//					if (c != null) {
//						c.setAmlaliststatus("3");// lifted
//						if (dbService.saveOrUpdate(c))
//							return "success";
//					}
				}
			} else {
				return "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String updateDraftRequest(String middlename, String suffix, String remarks, Boolean openended, Date enddate,
			Date startdate, String country, String reference, String source, Integer requestid, String requeststatus) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (requeststatus.equals("1")) {// forapprovalvalidation
				if (openended.equals("true")) {
					if (remarks == null || startdate == null || country == null || reference == null
							|| source == null) {
						return "validate";
					}
				} else {
					if (enddate == null || remarks == null || startdate == null || country == null || reference == null
							|| source == null) {
						return "validate";
					}
				}
			}
			params.put("requestid", requestid);
			Tbamlarequest a = (Tbamlarequest) dbService
					.executeUniqueHQLQuery("FROM Tbamlarequest WHERE requestid=:requestid", params);
			if (a != null) {
				if (requeststatus != null) {
					a.setRequeststatus(requeststatus);
				}
				a.setRemarks(remarks);
				a.setDescription(reference);
				a.setStartdate(startdate);
				a.setEnddate(enddate);
				a.setIsopenended(openended);
				a.setMiddlename(middlename);
				a.setCountry(country);
				a.setSuffix(suffix);
				a.setAmlalistsource(source);
				if (dbService.saveOrUpdate(a)) {
					if (requeststatus == "1") {
						return "forapproval";
					}
					return "success";
				}
			} else {
				return "failed";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}
}
