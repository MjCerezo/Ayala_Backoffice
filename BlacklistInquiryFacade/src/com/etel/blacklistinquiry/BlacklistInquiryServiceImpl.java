package com.etel.blacklistinquiry;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.coopdb.data.Tbblacklistindividual;
import com.coopdb.data.Tbblacklistmain;
import com.coopdb.data.Tbblacklistrequest;
import com.coopdb.data.Tbcountry;
import com.coopdb.data.Tbmember;
import com.etel.blacklist.forms.BlacklistApprovalForm;
import com.etel.blacklist.forms.BlacklistForm;
import com.etel.blacklist.forms.BlacklistInquiryForm;
import com.etel.utils.HQLUtil;
import com.etel.utils.TransactionNoGenerator;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class BlacklistInquiryServiceImpl implements BlacklistInquiryService {
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbblacklistmain> searchBlacklist(BlacklistInquiryForm form) {
		// TODO SEARCH FROM TBBLACKLISTMAIN
		DBService dbService = new DBServiceImpl();
		StringBuilder query = new StringBuilder();
		List<Tbblacklistmain> list = new ArrayList<Tbblacklistmain>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dob = null;
		String doi = null;
		// if (form.getDateofbirth() != null) {
		// dob = formatter.format(form.getDateofbirth());
		// }
		// if (form.getDateofincorporation() != null) {
		// doi = formatter.format(form.getDateofincorporation());
		// }
		try {
			query.append("FROM Tbblacklistmain WHERE ");
			if (form.getCustomertype() != null) {
				if (form.getCustomertype().equals("1")) {
					query.append("customertype = '1' AND ");
				}
				if (form.getCustomertype().equals("2")) {
					query.append("customertype = '2' AND ");
				}
			}
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
				query.append("status =" + form.getStatus() + " AND ");
			}
			query.append("12345");
			String listquery = query.substring(0, query.length() - 10);
			System.out.println(listquery);
			list = (List<Tbblacklistmain>) dbService.executeListHQLQuery(listquery, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
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

	@Override
	public String addBlacklistRequest(BlacklistForm form) {
		// TODO Create Add New Blacklist request
		DBService dbService = new DBServiceImpl();
		Tbblacklistrequest newrequest = new Tbblacklistrequest();
		String flag = "failed";
		try {
			if (form.getRequesttype().equals("0")) {// new
				if (form.getCustomertype().equals("1")) {// indiv
					if (form.getRequeststatus().equals("0")) {// draft
						if (form.getLastname() == null || form.getFirstname() == null
								|| form.getDateofbirth() == null) {
							return "validate";
						}
					}
					if (form.getRequeststatus().equals("1")) {// approval
						if (form.getIsopenended().equals("true")) {
							if (form.getLastname() == null || form.getFirstname() == null
									|| form.getBlacklistsource() == null || form.getBranch() == null
									|| form.getCountry() == null || form.getCourtcaseno() == null
									|| form.getDateofbirth() == null || form.getDescription() == null
									|| form.getRemarks() == null || form.getStartdate() == null) {
								return "validate";
							}
						} else {
							if (form.getLastname() == null || form.getFirstname() == null
									|| form.getBlacklistsource() == null || form.getBranch() == null
									|| form.getCountry() == null || form.getCourtcaseno() == null
									|| form.getDateofbirth() == null || form.getDescription() == null
									|| form.getRemarks() == null || form.getStartdate() == null
									|| form.getEnddate() == null) {
								return "validate";
							}
						}
					}
				} else {// corp
					if (form.getRequeststatus().equals("0")) {// draft
						if (form.getCorporatename() == null || form.getDateofincorporation() == null) {
							return "validate";
						}
					}
					if (form.getRequeststatus().equals("1")) {// approval
						if (form.getIsopenended().equals("true")) {
							if (form.getCorporatename() == null || form.getDateofincorporation() == null
									|| form.getBlacklistsource() == null || form.getBranch() == null
									|| form.getCountry() == null || form.getCourtcaseno() == null
									|| form.getDescription() == null || form.getRemarks() == null
									|| form.getStartdate() == null) {
								return "validate";
							}
						} else {
							if (form.getCorporatename() == null || form.getDateofincorporation() == null
									|| form.getBlacklistsource() == null || form.getBranch() == null
									|| form.getCountry() == null || form.getCourtcaseno() == null
									|| form.getDescription() == null || form.getRemarks() == null
									|| form.getStartdate() == null || form.getEnddate() == null) {
								return "validate";
							}
						}
					}
				}
			}
			if (form.getRequesttype().equals("0")) {
				if (form.getCustomertype().equals("1")) {
					if (form.getMiddlename() != null) {
						form.setFullname(form.getLastname() + ", " + form.getFirstname() + " " + form.getMiddlename());
					} else {
						form.setFullname(form.getLastname() + ", " + form.getFirstname());
					}
				} else {
					form.setFullname(form.getCorporatename());
				}
			}
			if (form.getRequesttype().equals("3")) {

			}
			newrequest.setFullname(form.getFullname());
			newrequest.setBlacklistid(form.getBlacklistid());
			newrequest.setCifno(form.getCifno());
			newrequest.setCustomertype(form.getCustomertype());
			newrequest.setDescription(form.getDescription());
			newrequest.setLastname(form.getLastname());
			newrequest.setFirstname(form.getFirstname());
			newrequest.setMiddlename(form.getMiddlename());
			newrequest.setDateofbirth(form.getDateofbirth());
			newrequest.setSuffix(form.getSuffix());
			newrequest.setIsopenended(form.getIsopenended());
			newrequest.setStartdate(form.getStartdate());
			newrequest.setEnddate(form.getEnddate());
			newrequest.setBlacklistsource(form.getBlacklistsource());
			newrequest.setRemarks(form.getRemarks());
			newrequest.setRequestdate(new Date());
			newrequest.setRequeststatus(form.getRequeststatus());
			newrequest.setRequesttype(form.getRequesttype());
			newrequest.setCorporatename(form.getCorporatename());
			newrequest.setDateofincorporation(form.getDateofincorporation());
			newrequest.setRequestedby(secservice.getUserName());
			newrequest.setCountry(form.getCountry());
			newrequest.setCourtcaseno(form.getCourtcaseno());
			newrequest.setCourtbranchcode(form.getBranch());
			newrequest.setBlackliststatus(form.getBlackliststatus());
			newrequest.setDatecreated(form.getDatecreated());
			newrequest.setCreatedby(form.getCreatedby());
			if (dbService.save(newrequest)) {
				if (newrequest.getRequesttype().equals("3")) {
					flag = newrequest.getRequestid().toString();
				} else {
					flag = newrequest.getRequestid().toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	// BLACKLIST APPROVAL
	/*
	 * Daniel 10182018 ****Fixed Errors
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Tbblacklistrequest> searchRequestByStatus(BlacklistApprovalForm form, String requestid) {
		// TODO SEARCH BLACKLIST REQUEST BY REQUEST STATUS
		Map<String, Object> param = HQLUtil.getMap();
		DBService dbService = new DBServiceImpl();
		StringBuilder query = new StringBuilder();
		List<Tbblacklistrequest> list = new ArrayList<Tbblacklistrequest>();
		// gustoNiMaricarNaLumalabasAgadLahatNungRequestSaGridSaUnangViewPaLangKahitWalangParametersKayMaricarKayoMagReklamoPagMayReklamoKayo!
		list = (List<Tbblacklistrequest>) dbService.executeListHQLQuery("FROM Tbblacklistrequest", null);
		// Nagna-NullPointerYanTo.10182018
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		String du = null;
		// if(form.getDateuploaded()!=null){
		// du = formatter.format(form.getDateuploaded());
		// }
		try {
			System.out.println(requestid);
			if (requestid != null) {
				param.put("requestid", Integer.parseInt(requestid));
				Tbblacklistrequest u = (Tbblacklistrequest) dbService
						.executeUniqueHQLQuery("FROM Tbblacklistrequest WHERE requestid=:requestid", param);
				if (u != null) {
					List<Tbblacklistrequest> listn = new ArrayList<Tbblacklistrequest>();
					listn.add(u);
					return listn;
				}
			} else {
//				if (form.getCustomertype() == null && form.getCustomername() == null && form.getRequesttype() == null
//						&& form.getRequeststatus() == null && form.getSource() == null
//						&& form.getDateuploaded() == null) {
//					list = (List<Tbblacklistrequest>) dbService.executeListHQLQuery("FROM Tbblacklistrequest", null);
//					System.out.println(">>>>>>>> ALL FIELDS : NULL <<<<<<<<<");
//					return list;
//				} else {
				query.append("FROM Tbblacklistrequest WHERE ");

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
					query.append("blacklistsource =" + form.getSource() + " AND ");
				}
				if (form.getDateuploaded() != null) {
					String du = formatter.format(form.getDateuploaded());
					query.append("(uploadeddate BETWEEN '" + du + " 00:00:00' AND '" + du + " 23:59:00') AND ");
				}

				query.append("12345");
				String listquery = query.substring(0, query.length() - 10);
				System.out.println(listquery);
				list = (List<Tbblacklistrequest>) dbService.executeListHQLQuery(listquery, null);
			}
//			}
		} catch (NullPointerException e) {
			// NullPointer.AllFIELDSNULL.10182018
			System.out.println("EZ NULL");
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Tbblacklistrequest getRequestRecord(Integer requestid) {
		// TODO Get Request record
		Tbblacklistrequest request = new Tbblacklistrequest();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("requestid", requestid);

		try {
			request = (Tbblacklistrequest) dbService
					.executeUniqueHQLQueryMaxResultOne("FROM Tbblacklistrequest WHERE requestid=:requestid", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}

	@Override
	public String updateBlacklistRequestStatus(Integer requestid, String requeststatus) {
		// TODO Update Blacklist Request Status
		// 2 for Approved; 3 for Cancelled ; 4 for Rejected
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Tbblacklistrequest request = new Tbblacklistrequest();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("requestid", requestid);
		try {
			request = (Tbblacklistrequest) dbService
					.executeUniqueHQLQuery("FROM Tbblacklistrequest WHERE requestid=:requestid", param);
			if (request != null) {
				request.setRequeststatus(requeststatus);
				if (dbService.saveOrUpdate(request)) {
					if (request.getRequesttype().equals("0") && request.getRequeststatus().equals("2")) {// approvednewrequest
//						NoGenerator blacklist = new NoGenerator();
						String BLACKLIST_ID = TransactionNoGenerator.generateIdNo("BLACKLIST");
//						String id = blacklist.generateBlacklistID();
						if (request.getCustomertype().equals("1")) {
							BlacklistForm indiv = new BlacklistForm();
							indiv.setBlacklistsource(request.getBlacklistsource());
							indiv.setBlackliststatus("1");// Active
							indiv.setBranch(request.getCourtbranchcode());
							indiv.setCifno(request.getCifno());
							indiv.setCountry(request.getCountry());
							indiv.setCourtcaseno(request.getCourtcaseno());
							indiv.setCreatedby(request.getCreatedby());
							indiv.setCustomertype(request.getCustomertype());
							indiv.setDatecreated(new Date());
							indiv.setDateofbirth(request.getDateofbirth());
							indiv.setDescription(request.getDescription());
							indiv.setEnddate(request.getEnddate());
							indiv.setFullname(request.getFullname());
							indiv.setIsopenended(request.getIsopenended());
							indiv.setRemarks(request.getRemarks());
							indiv.setRequestdate(request.getRequestdate());
							indiv.setRequestedby(request.getRequestedby());
							indiv.setStartdate(request.getStartdate());
							indiv.setSuffix(request.getSuffix());
							indiv.setFirstname(request.getFirstname());
							indiv.setMiddlename(request.getMiddlename());
							indiv.setLastname(request.getLastname());
							indiv.setBlacklistid(BLACKLIST_ID);
							return this.addBlacklistIndiv(indiv);
						} else {
							BlacklistForm corp = new BlacklistForm();
							corp.setBlacklistsource(request.getBlacklistsource());
							corp.setBlackliststatus("1");// Active
							corp.setBranch(request.getCourtbranchcode());
							corp.setCifno(request.getCifno());
							corp.setCountry(request.getCountry());
							corp.setCourtcaseno(request.getCourtcaseno());
							corp.setCreatedby(request.getCreatedby());
							corp.setCustomertype(request.getCustomertype());
							corp.setDatecreated(new Date());
							// corp.setDateofbirth(request.getDateofbirth());
							corp.setDescription(request.getDescription());
							corp.setEnddate(request.getEnddate());
							corp.setFullname(request.getFullname());
							corp.setIsopenended(request.getIsopenended());
							corp.setRemarks(request.getRemarks());
							corp.setRequestdate(request.getRequestdate());
							corp.setRequestedby(request.getRequestedby());
							corp.setStartdate(request.getStartdate());
							corp.setCorporatename(request.getCorporatename());
							corp.setDateofincorporation(request.getDateofincorporation());
							corp.setBlacklistid(BLACKLIST_ID);
//							return this.addBlacklistCorp(corp);
						}
					}
					if (request.getRequesttype().equals("3") && request.getRequeststatus().equals("2")) {// approvedforlift
						return this.liftBlacklist(requestid.toString(), request.getCustomertype(),
								request.getBlacklistid());
					}
					flag = "success";
					if (request.getRequeststatus().equals("4")) {
						flag = request.getRequestid().toString();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String saveNewBlacklistMain(BlacklistForm form) {
		// TODO Save NEw Blacklist to TBBLACKLISTMAIN
		DBService dbService = new DBServiceImpl();
		Tbblacklistmain newindiv = new Tbblacklistmain();
		String flag = "Failed";
		try {
			newindiv.setDateofincorporation(form.getDateofincorporation());
			newindiv.setBlacklistid(form.getBlacklistid());
			newindiv.setCifno(form.getCifno());
			newindiv.setFullname(form.getFullname());
			newindiv.setDateofbirth(form.getDateofbirth());
			newindiv.setStartdate(form.getStartdate());
			newindiv.setEnddate(form.getEnddate());
			newindiv.setStatus(form.getBlackliststatus());
			newindiv.setCustomertype(form.getCustomertype());
			newindiv.setCreateddate(new Date());
			newindiv.setBlacklistsource(form.getBlacklistsource());
			newindiv.setIsopenended(form.getIsopenended());
			if (dbService.save(newindiv)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String addBlacklistIndiv(BlacklistForm form) {
		// TODO ADD NEW BLACKLIST SAVE TO TB BLACKLIST INDIVIDUAL

		DBService dbService = new DBServiceImpl();
		Tbblacklistindividual newindiv = new Tbblacklistindividual();
		String flag = "failed";
		try {
			if (form.getMiddlename() == null) {
				form.setMiddlename("");
			}
			newindiv.setBlacklistid(form.getBlacklistid());
			newindiv.setCifno(form.getCifno());
			newindiv.setCountry(form.getCountry());
			newindiv.setDescription(form.getDescription());
			newindiv.setLastname(form.getLastname());
			newindiv.setFirstname(form.getFirstname());
			newindiv.setMiddlename(form.getMiddlename());
			newindiv.setSuffix(form.getSuffix());
			newindiv.setIsopenended(form.getIsopenended());
			newindiv.setDateofbirth(form.getDateofbirth());
			newindiv.setStartdate(form.getStartdate());
			newindiv.setEnddate(form.getEnddate());
			newindiv.setRemarks(form.getRemarks());
			newindiv.setDatecreated(new Date());
			newindiv.setBlacklistsource(form.getBlacklistsource());
			newindiv.setCreatedby(secservice.getUserName());
			newindiv.setBlackliststatus(form.getBlackliststatus());
			newindiv.setCourtcaseno(form.getCourtcaseno());
			newindiv.setCourtbranchcode(form.getBranch());
			if (dbService.save(newindiv)) {
				Tbblacklistmain main = new Tbblacklistmain();
				main.setBlacklistid(newindiv.getBlacklistid());
				main.setCifno(newindiv.getCifno());
				main.setDateofbirth(newindiv.getDateofbirth());
				main.setFullname(
						newindiv.getLastname() + " " + newindiv.getFirstname() + " " + newindiv.getMiddlename());
				main.setStartdate(newindiv.getStartdate());
				main.setEnddate(newindiv.getEnddate());
				main.setIsopenended(newindiv.getIsopenended());
				main.setBlacklistsource(newindiv.getBlacklistsource());
				main.setStatus("1");
				main.setCreateddate(new Date());
				main.setCustomertype(form.getCustomertype());
				if (dbService.save(main)) {
					return "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String updateNewBlacklistMain(BlacklistForm form) {
		// TODO Save NEw Blacklist to TBBLACKLISTMAIN
		DBService dbService = new DBServiceImpl();
		Tbblacklistmain newindiv = new Tbblacklistmain();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("blacklistid", form.getBlacklistid());
		String flag = "Failed";
		try {
			newindiv = (Tbblacklistmain) dbService
					.executeUniqueHQLQuery("FROM Tbblacklistmain WHERE blacklistid =:blacklistid ", param);
			if (newindiv != null) {
				newindiv.setDateofincorporation(form.getDateofincorporation());
				newindiv.setCifno(form.getCifno());
				newindiv.setFullname(form.getFullname());
				newindiv.setDateofbirth(form.getDateofbirth());
				newindiv.setIsopenended(form.getIsopenended());
				newindiv.setStartdate(form.getStartdate());
				newindiv.setEnddate(form.getEnddate());
				newindiv.setStatus(form.getBlackliststatus());
				newindiv.setCustomertype(form.getCustomertype());
				newindiv.setBlacklistsource(form.getBlacklistsource());
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
	public String updateBlacklistIndiv(BlacklistForm form) {
		// TODO UPDATE Blacklist to TBBLAKLISTINDIVIDUAL
		DBService dbService = new DBServiceImpl();
		Tbblacklistindividual newindiv = new Tbblacklistindividual();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("blacklistid", form.getBlacklistid());
		String flag = "Failed";
		try {
			newindiv = (Tbblacklistindividual) dbService
					.executeUniqueHQLQuery("FROM Tbblacklistindividual WHERE blacklistid =:blacklistid ", param);
			if (newindiv != null) {
				newindiv.setCifno(form.getCifno());
				newindiv.setCountry(form.getCountry());
				newindiv.setDescription(form.getDescription());
				newindiv.setLastname(form.getLastname());
				newindiv.setFirstname(form.getFirstname());
				newindiv.setMiddlename(form.getMiddlename());
				newindiv.setSuffix(form.getSuffix());
				newindiv.setDateofbirth(form.getDateofbirth());
				newindiv.setIsopenended(form.getIsopenended());
				newindiv.setStartdate(form.getStartdate());
				newindiv.setEnddate(form.getEnddate());
				newindiv.setRemarks(form.getRemarks());
				newindiv.setBlacklistsource(form.getBlacklistsource());
				newindiv.setBlackliststatus(form.getBlackliststatus());
				newindiv.setCourtcaseno(form.getCourtcaseno());
				newindiv.setCourtbranchcode(form.getBranch());
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
	public String updateBlacklistCorp(BlacklistForm form) {
		// TODO Auto-generated method stub
//		DBService dbService = new DBServiceImpl();
////		Tbblacklistcorporate newcorporate = new Tbblacklistcorporate();
//		Map<String, Object> param = HQLUtil.getMap();
//		param.put("blacklistid", form.getBlacklistid());
//		String flag = "Failed";
//		try {
//			newcorporate = (Tbblacklistcorporate) dbService
//					.executeUniqueHQLQuery("FROM Tbblacklistcorporate WHERE blacklistid =:blacklistid ", param);
//			if (newcorporate != null) {
//				newcorporate.setBlacklistid(form.getBlacklistid());
//				newcorporate.setCifno(form.getCifno());
//				newcorporate.setCountry(form.getCountry());
//				newcorporate.setDescription(form.getDescription());
//				newcorporate.setDateofincorporation(form.getDateofincorporation());
//				newcorporate.setIsopenended(form.getIsopenended());
//				newcorporate.setStartdate(form.getStartdate());
//				newcorporate.setEnddate(form.getEnddate());
//				newcorporate.setRemarks(form.getRemarks());
//				newcorporate.setDatecreated(new Date());
//				newcorporate.setBlackliststatus(form.getBlackliststatus());
//				newcorporate.setCorporatename(form.getCorporatename());
//				newcorporate.setCreatedby(secservice.getUserName());
//				newcorporate.setBlacklistsource(form.getBlacklistsource());
//				newcorporate.setCourtcaseno(form.getCourtcaseno());
//				newcorporate.setCourtbranchcode(form.getBranch());
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

	public Tbblacklistindividual getIndividualRecord(String blacklistid) {
		// TODO Auto-generated method stub
		Tbblacklistindividual indivrecord = new Tbblacklistindividual();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("blacklistid", blacklistid);

		try {
			indivrecord = (Tbblacklistindividual) dbService.executeUniqueHQLQueryMaxResultOne(
					"FROM Tbblacklistindividual WHERE blacklistid=:blacklistid", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indivrecord;
	}

//	@Override
//	public Tbblacklistcorporate getCorporateRecord(String blacklistid) {
//		// TODO GET CORPORATE RECORD
//		Tbblacklistcorporate corprecord = new Tbblacklistcorporate();
//		DBService dbService = new DBServiceImplCIF();
//		Map<String, Object> param = HQLUtil.getMap();
//		param.put("blacklistid", blacklistid);
//
//		try {
//			corprecord = (Tbblacklistcorporate) dbService.executeUniqueHQLQueryMaxResultOne(
//					"FROM Tbblacklistcorporate WHERE blacklistid=:blacklistid", param);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return corprecord;
//	}

	@Override
	public String addBlacklistCorp(BlacklistForm form) {
		// TODO Auto-generated method stub
//		DBService dbService = new DBServiceImplCIF();
//		Tbblacklistcorporate newcorporate = new Tbblacklistcorporate();
//		String flag = "failed";
//		try {
//			newcorporate.setBlacklistid(form.getBlacklistid());
//			newcorporate.setCifno(form.getCifno());
//			newcorporate.setCountry(form.getCountry());
//			newcorporate.setDescription(form.getDescription());
//			newcorporate.setDateofincorporation(form.getDateofincorporation());
//			newcorporate.setIsopenended(form.getIsopenended());
//			newcorporate.setStartdate(form.getStartdate());
//			newcorporate.setEnddate(form.getEnddate());
//			newcorporate.setRemarks(form.getRemarks());
//			newcorporate.setDatecreated(new Date());
//			newcorporate.setBlackliststatus(form.getBlackliststatus());
//			newcorporate.setCorporatename(form.getCorporatename());
//			newcorporate.setCreatedby(secservice.getUserName());
//			newcorporate.setBlacklistsource(form.getBlacklistsource());
//			newcorporate.setCourtcaseno(form.getCourtcaseno());
//			newcorporate.setCourtbranchcode(form.getBranch());
//			if (dbService.save(newcorporate)) {
//				Tbblacklistmain main = new Tbblacklistmain();
//				main.setBlacklistid(newcorporate.getBlacklistid());
//				main.setCifno(newcorporate.getCifno());
//				main.setDateofincorporation(newcorporate.getDateofincorporation());
//				main.setFullname(newcorporate.getCorporatename());
//				main.setStartdate(newcorporate.getStartdate());
//				main.setEnddate(newcorporate.getEnddate());
//				main.setIsopenended(newcorporate.getIsopenended());
//				main.setBlacklistsource(newcorporate.getBlacklistsource());
//				main.setStatus("1");
//				main.setCreateddate(new Date());
//				main.setCustomertype(form.getCustomertype());
//				if (dbService.save(main)) {
//					return "success";
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
		return null;
	}

	public String searchCIF(String cifno) {
		// TODO CHECK IF CIF EXISTS
		String flag = null;
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tbmember cifmain = new Tbmember();
		param.put("cifno", cifno);
		try {
			cifmain = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid =:cifno", param);
			if (cifmain != null) {
				flag = "Success";
			} else {
				flag = "Failed";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String checkCIF(String cifno, String customertype) {
		// TODO CHECK IF CIF EXISTS
		String flag = null;
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tbmember cifmain = new Tbmember();
		param.put("cifno", cifno);
		param.put("customertype", customertype);
		try {
			cifmain = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid =:cifno", param);
			if (cifmain != null) {
				flag = "Success";
			} else {
				flag = "Failed";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String seachIndivCIF(String cifno) {
		// TODO Auto-generated method stub
		String flag = null;
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tbmember cifmain = new Tbmember();
		param.put("cifno", cifno);
		try {
			cifmain = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid =:cifno", param);
			if (cifmain != null) {
				flag = "Success";
			} else {
				flag = "Failed";
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String seachCorpCIF(String cifno) {
		// TODO Auto-generated method stub
//		String flag = null;
//		DBService dbService = new DBServiceImplCIF();
//		Map<String, Object> param = HQLUtil.getMap();
//		Tbcifcorporate cifmain = new Tbcifcorporate();
//		param.put("cifno", cifno);
//		try {
//			cifmain = (Tbcifcorporate) dbService.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno =:cifno",
//					param);
//			if (cifmain != null) {
//				flag = "Success";
//			} else {
//				flag = "Failed";
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return flag;
		return null;
	}

	@Override
	public Tbmember getCIFIndivRecord(String cifno) {
		// TODO Auto-generated method stub
		Tbmember request = new Tbmember();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("cifno", cifno);

		try {
			request = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE membershipid=:cifno", param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}

//	@Override
//	public Tbcifcorporate getCIFCorpRecord(String cifno) {
//		// TODO Auto-generated method stub
//		Tbcifcorporate request = new Tbcifcorporate();
//		DBService dbService = new DBServiceImplCIF();
//		Map<String, Object> param = HQLUtil.getMap();
//		param.put("cifno", cifno);
//
//		try {
//			request = (Tbcifcorporate) dbService.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno=:cifno", param);
//			System.out.println("Corpprate Name>>>  " + request.getCorporatename());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return request;
//	}

	@Override
	public String getUser() {
		// TODO Auto-generated method stub
		String user = null;
		user = secservice.getUserName();
		return user;
	}

	@Override
	public String updateBlacklistRequestDetails(BlacklistForm form, String requestid) {
		// TODO Auto-generated method stub
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Tbblacklistrequest request = new Tbblacklistrequest();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("requestid", Integer.parseInt(requestid));
		try {
			// System.out.println(requestid);
			request = (Tbblacklistrequest) dbService
					.executeUniqueHQLQuery("FROM Tbblacklistrequest WHERE requestid=:requestid", param);
			if (request != null) {
				// request.setFullname(form.getFullname());
				// request.setBlacklistid(form.getBlacklistid());
				// request.setCifno(form.getCifno());
				// request.setCustomertype(form.getCustomertype());
				request.setDescription(form.getDescription());
				// request.setLastname(form.getLastname());
				// request.setFirstname(form.getFirstname());
				request.setMiddlename(form.getMiddlename());
				// request.setDateofbirth(form.getDateofbirth());
				request.setSuffix(form.getSuffix());
				request.setIsopenended(form.getIsopenended());
				request.setStartdate(form.getStartdate());
				request.setEnddate(form.getEnddate());
				request.setBlacklistsource(form.getBlacklistsource());
				request.setRemarks(form.getRemarks());
				// request.setRequestdate(new Date());
				// request.setCorporatename(form.getCorporatename());
				// request.setDateofincorporation(form.getDateofincorporation());
				// request.setRequestedby(secservice.getUserName());
				request.setCountry(form.getCountry());
				request.setCourtcaseno(form.getCourtcaseno());
				request.setCourtbranchcode(form.getBranch());
				request.setBlackliststatus(form.getBlackliststatus());
				request.setRequeststatus(form.getRequeststatus());
				if (request.getRequesttype().equals("0")) {
					if (form.getRequeststatus().equals("1")) {
						if (request.getCustomertype().equals("1")) {
							if (form.getIsopenended()) {
								if (request.getLastname() == null || request.getFirstname() == null
										|| request.getCountry() == null || request.getStartdate() == null
										|| request.getBlacklistsource() == null || request.getDescription() == null) {
									return "validate";
								}
							} else {
								if (request.getLastname() == null || request.getFirstname() == null
										|| request.getCountry() == null || request.getStartdate() == null
										|| request.getBlacklistsource() == null || request.getDescription() == null
										|| request.getEnddate() == null) {
									return "validate";
								}
							}
						} else {
							if (form.getIsopenended()) {
								if (request.getCorporatename() == null || request.getCountry() == null
										|| request.getStartdate() == null || request.getBlacklistsource() == null
										|| request.getDescription() == null
										|| request.getDateofincorporation() == null) {
									return "validate";
								}
							} else {
								if (request.getCorporatename() == null || request.getCountry() == null
										|| request.getStartdate() == null || request.getBlacklistsource() == null
										|| request.getDescription() == null || request.getDateofincorporation() == null
										|| request.getEnddate() == null) {
									return "validate";
								}
							}
						}
					}
				}
				if (dbService.update(request)) {
					flag = request.getRequestid().toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	public String liftBlacklist(String requestid, String customertype, String blacklistid) {
		DBService dbService = new DBServiceImpl();
		// Map<String, Object> param = HQLUtil.getMap();
		try {
			if (customertype.equals("1")) {
				Integer i = dbService.executeUpdate(
						"UPDATE Tbblacklistindividual SET blackliststatus='3' WHERE blacklistid='" + blacklistid + "'",
						null);
				if (i > 0 && i != null) {
					Integer m = dbService.executeUpdate(
							"UPDATE Tbblacklistmain SET status='3' WHERE blacklistid='" + blacklistid + "'", null);
					if (m != null || m > 0) {
						return requestid;
					}
				}
			} else {
				Integer c = dbService.executeUpdate(
						"UPDATE Tbblacklistcorporate SET blackliststatus='3' WHERE blacklistid='" + blacklistid + "'",
						null);
				if (c > 0 && c != null) {
					Integer m = dbService.executeUpdate(
							"UPDATE Tbblacklistmain SET status='3' WHERE blacklistid='" + blacklistid + "'", null);
					if (m != null || m > 0) {
						return requestid;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}
}
