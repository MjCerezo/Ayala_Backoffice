package com.etel.changecifdetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbchangecifdetailsrequest;
import com.cifsdb.data.Tbcifcorporate;
import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifindividualrequest;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbcodetable;
import com.cifsdb.data.Tbcorporaterequest;
import com.cifsdb.data.Tbfatca;
import com.cifsdb.data.Tbothercontactrequest;
import com.cifsdb.data.Tbothercontacts;
import com.cifsdb.data.Tbsequence;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdepositcif;
import com.coopdb.data.Tbgeneraldocs;
import com.etel.changecifdetails.form.ChangeCIFDetailsForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.email.EmailCode;
import com.etel.email.EmailFacade;
import com.etel.email.forms.EmailForm;
import com.etel.forms.FormValidation;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

/**
 * @author Kevin
 */
public class ChangeCIFDetailsServiceImpl implements ChangeCIFDetailsService {

	private Map<String, Object> param = HQLUtil.getMap();
	DBService dbServiceCOOP = new DBServiceImpl();
	private Map<String, Object> params = new HashMap<String, Object>();

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbchangecifdetailsrequest> getListofChangeCIFDetails(String lastname, String firstname,
			String middlename, String corporatename, String customertype, String requestid, String cifno, String status,
			String changetype, Integer page, Integer maxResult) {
		List<Tbchangecifdetailsrequest> changecif = new ArrayList<Tbchangecifdetailsrequest>();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			StringBuilder hql = new StringBuilder();
			if (customertype != null && (customertype.equals("1") || customertype == "1")) {
				hql.append(
						"SELECT a.requestid,a.cifno,a.fullname,a.changetype ,a.requestedby ,a.daterequested ,a.requeststatus,a.requesttype ,a.customertype ,a.remarks ,a.approvedby,");
				hql.append(
						"a.dateapprove,a.approverremarks,a.teamcode FROM Tbchangecifdetailsrequest a, Tbcifindividualrequest b WHERE a.requestid=b.requestid AND a.cifno=b.cifno AND a.customertype = '"
								+ customertype + "' AND ");
				if (lastname != null) {
					hql.append("b.lastname like '%" + lastname + "%' AND ");
				}
				if (firstname != null) {
					hql.append("b.firstname like '%" + firstname + "%' AND ");
				}
				if (middlename != null) {
					hql.append("b.middlename like '%" + middlename + "%' AND ");
				}
				if (cifno != null) {
					hql.append("a.cifno like '%" + cifno + "%' AND ");
				}
				if (requestid != null) {
					hql.append("a.requestid like '%" + requestid + "%' AND ");
				}
				if (status != null) {
					hql.append("a.requeststatus = '" + status + "' AND ");
				}
				if (changetype != null) {
					hql.append("a.changetype = '" + changetype + "' AND ");
				}
				hql.append("-----");
				String indivcorpquery = hql.substring(0, hql.length() - 10);
				changecif = (List<Tbchangecifdetailsrequest>) dbService.execSQLQueryTransformerListPagination(
						indivcorpquery, null, Tbchangecifdetailsrequest.class, page, maxResult);
			} else if (customertype != null && (customertype.equals("2") || customertype == "2")) {
				hql.append("FROM Tbchangecifdetailsrequest WHERE customertype = '" + customertype + "' AND ");
				if (corporatename != null) {
					hql.append("fullname like '%" + corporatename + "%' AND ");
				}
				if (cifno != null) {
					hql.append("cifno like '%" + cifno + "%' AND ");
				}
				if (requestid != null) {
					hql.append("requestid like '%" + requestid + "%' AND ");
				}
				if (status != null) {
					hql.append("requeststatus = '" + status + "' AND ");
				}
				if (changetype != null) {
					hql.append("changetype = '" + changetype + "' AND ");
				}
				hql.append("-----");
				String indivcorpquery = hql.substring(0, hql.length() - 10);
				changecif = (List<Tbchangecifdetailsrequest>) dbService
						.executeListHQLQueryWithFirstAndMaxResults(indivcorpquery, null, page, maxResult);
			} else {
				System.out.println(">>>CUSTOMERTYPE FIELD NULL : Search Change CIF Details<<<");
			}
			if (changecif != null && !changecif.isEmpty()) {
				for (Tbchangecifdetailsrequest r : changecif) {
					if (r.getRequeststatus() != null) {
						params.put("reqStatus", r.getRequeststatus());
						Tbcodetable reqStatus = (Tbcodetable) dbService.executeUniqueHQLQuery(
								"FROM Tbcodetable a WHERE a.id.codename ='REQUESTSTATUS' AND a.id.codevalue =:reqStatus",
								params);
						if (reqStatus != null) {
							r.setRequeststatus(reqStatus.getDesc1());
						}
					}
					if (r.getRequestedby() != null) {
						r.setRequestedby(UserUtil.getUserFullname(r.getRequestedby()));
					}
					if (r.getApprovedby() != null) {
						r.setApprovedby(UserUtil.getUserFullname(r.getApprovedby()));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return changecif;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getChangeCifDetailsTotal(String lastname, String firstname, String middlename, String corporatename,
			String customertype, String requestid, String cifno, String status, String changetype) {
		List<Tbchangecifdetailsrequest> changecif = new ArrayList<Tbchangecifdetailsrequest>();
		DBService dbService = new DBServiceImplCIF();
		try {
			StringBuilder hql = new StringBuilder();
			if (customertype != null && (customertype.equals("1") || customertype == "1")) {
				hql.append("FROM Tbchangecifdetailsrequest WHERE customertype = '" + customertype + "' AND ");
				if (lastname != null) {
					hql.append("fullname like '%" + lastname + "%' AND ");
				}
				if (firstname != null) {
					hql.append("fullname like '%" + firstname + "%' AND ");
				}
				if (middlename != null) {
					hql.append("fullname like '%" + middlename + "%' AND ");
				}
				if (cifno != null) {
					hql.append("cifno like '%" + cifno + "%' AND ");
				}
				if (requestid != null) {
					hql.append("requestid like '%" + requestid + "%' AND ");
				}
				if (status != null) {
					hql.append("requeststatus = '" + status + "' AND ");
				}
				if (changetype != null) {
					hql.append("changetype = '" + changetype + "' AND ");
				}
				hql.append("-----");
				String indivcorpquery = hql.substring(0, hql.length() - 10);
				changecif = (List<Tbchangecifdetailsrequest>) dbService.executeListHQLQuery(indivcorpquery, null);
			} else if (customertype != null && (customertype.equals("2") || customertype == "2")) {
				hql.append("FROM Tbchangecifdetailsrequest WHERE customertype = '" + customertype + "' AND ");
				if (corporatename != null) {
					hql.append("fullname like '%" + corporatename + "%' AND ");
				}
				if (cifno != null) {
					hql.append("cifno like '%" + cifno + "%' AND ");
				}
				if (requestid != null) {
					hql.append("requestid like '%" + requestid + "%' AND ");
				}
				if (status != null) {
					hql.append("requeststatus = '" + status + "' AND ");
				}
				if (changetype != null) {
					hql.append("changetype = '" + changetype + "' AND ");
				}
				hql.append("-----");
				String indivcorpquery = hql.substring(0, hql.length() - 10);
				changecif = (List<Tbchangecifdetailsrequest>) dbService.executeListHQLQuery(indivcorpquery, null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return changecif.size();
	}

	/**
	 * Type = Request
	 * 
	 * @param changetype
	 * @return String = RCYYSSSSSSS = R ,changetype , year, 7 seq nos
	 */
	@Override
	public String generateRequestID(String changetype) {
		String idNo = "";
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		String yearCheck = DateTimeUtil.yearCheck();
		param.put("year", Integer.valueOf(yearCheck));
		param.put("type", "CHANGECIF");
		try {
			Tbsequence sequence = (Tbsequence) dbService
					.executeUniqueHQLQuery("FROM Tbsequence WHERE year=:year and product=:type", param);
			if (sequence != null) {
				int one = sequence.getSequence(); // value
				String zero = String.format("%07d", one); // 0000000
				idNo = "R" + changetype + yearCheck + zero; // generated
				sequence.setSequence(sequence.getSequence() + 1); // + 1 to be use for the next sequence
				dbService.saveOrUpdate(sequence);
			} else {
				Tbsequence sequenceNew = new Tbsequence();
				sequenceNew.setYear(Integer.valueOf(yearCheck));
				sequenceNew.setProduct("CHANGECIF");
				sequenceNew.setSequence(1);
				if (dbService.saveOrUpdate(sequenceNew)) {
					int one = sequenceNew.getSequence();
					String zero = String.format("%07d", one);
					idNo = "R" + changetype + yearCheck + zero; // generated
					sequenceNew.setSequence(sequenceNew.getSequence() + 1);
					dbService.saveOrUpdate(sequenceNew);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return idNo;
	}

	@Override
	public Tbcifindividualrequest getIndivRequest(String cifno, String requestid) {
		DBService dbservice = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		Tbcifindividualrequest indivReq = new Tbcifindividualrequest();
		try {
			if (cifno != null && requestid != null) {
				params.put("cifno", cifno);
				params.put("requestid", requestid);
				indivReq = (Tbcifindividualrequest) dbservice.executeUniqueHQLQuery(
						"FROM Tbcifindividualrequest WHERE requestid=:requestid AND cifno=:cifno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return indivReq;
	}

	@Override
	public Tbcorporaterequest getCorpRequest(String cifno, String requestid) {
		DBService dbservice = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		Tbcorporaterequest corpReq = new Tbcorporaterequest();
		try {
			if (cifno != null && requestid != null) {
				params.put("cifno", cifno);
				params.put("requestid", requestid);
				corpReq = (Tbcorporaterequest) dbservice.executeUniqueHQLQuery(
						"FROM Tbcorporaterequest WHERE requestid=:requestid AND cifno=:cifno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return corpReq;
	}

	@Override
	public FormValidation saveCustomerName(String requeststatus, String requestid, String cifno, String title,
			String lastname, String firstname, String middlename, String suffix, String shortname, String prevlastname,
			String corporatename, String tradename, String customertype) {
		FormValidation form = new FormValidation();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		form.setFlag("failed");
		try {
			if (cifno != null) {
				params.put("requestid", requestid == null ? "" : requestid);
				params.put("cifno", cifno);
				if (customertype != null && customertype.equals("1")) {
					if (title == null || lastname == null || firstname == null) {
						form.setErrorMessage("<b>Failed ! </b>Missing required field(s) !");
						return form;
					}
					Tbchangecifdetailsrequest mainReq = (Tbchangecifdetailsrequest) dbService
							.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbchangecifdetailsrequest WHERE requestid=:requestid AND cifno=:cifno AND changetype='1'",
									params);
					if (mainReq == null) {
						mainReq = new Tbchangecifdetailsrequest();
						String reqId = generateRequestID("1");
						mainReq.setRequestid(reqId);
						mainReq.setCifno(cifno);
						mainReq.setCustomertype("1");

						String lname = lastname == null ? "" : lastname;
						String fname = firstname == null ? "" : firstname;
						String mname = middlename == null ? "" : middlename;
						String suf = suffix == null ? "" : suffix;
						mainReq.setFullname(lname + ", " + fname + " " + suf + " " + mname);

						mainReq.setChangetype("1");
						mainReq.setRequestedby(UserUtil.securityService.getUserName());
						mainReq.setDaterequested(new Date());
						mainReq.setRequeststatus(requeststatus);

						params.put("username", UserUtil.securityService.getUserName());
						String teamcode = (String) dbService
								.executeUniqueSQLQuery("SELECT teamcode FROM Tbuser WHERE username=:username", params);
						mainReq.setTeamcode(teamcode);
						if (dbService.save(mainReq)) {
							// Save Email to TBSMTP Oct.19,2017 - Kev
							EmailFacade email = new EmailFacade();
							EmailForm emailform = new EmailForm();
							//emailform.setCifno(cifno);
							//emailform.setRequestid(reqId);
							//emailform.setEmailcode(EmailCode.CHANGE_CIF_DETAILS_REQUEST);
							//email.saveEmailSMTP(emailform);
						}
					} else {
						mainReq.setCifno(cifno);
						mainReq.setCustomertype("1");

						String lname = lastname == null ? "" : lastname;
						String fname = firstname == null ? "" : firstname;
						String mname = middlename == null ? "" : middlename;
						String suf = suffix == null ? "" : suffix;
						mainReq.setFullname(lname + ", " + fname + " " + suf + " " + mname);

						mainReq.setChangetype("1");
						mainReq.setRequestedby(UserUtil.securityService.getUserName());
						mainReq.setDaterequested(new Date());
						mainReq.setRequeststatus(requeststatus);

						params.put("username", UserUtil.securityService.getUserName());
						String teamcode = (String) dbService
								.executeUniqueSQLQuery("SELECT teamcode FROM Tbuser WHERE username=:username", params);
						mainReq.setTeamcode(teamcode);
						dbService.saveOrUpdate(mainReq);
					}
					Tbcifindividualrequest indivReq = (Tbcifindividualrequest) dbService
							.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbcifindividualrequest WHERE requestid=:requestid AND cifno=:cifno", params);
					if (indivReq == null) {
						indivReq = new Tbcifindividualrequest();
						indivReq.setRequestid(mainReq.getRequestid());
						indivReq.setCifno(cifno);
						indivReq.setTitle(title);
						indivReq.setLastname(lastname);
						indivReq.setFirstname(firstname);
						indivReq.setMiddlename(middlename);
						indivReq.setSuffix(suffix);
						indivReq.setShortname(shortname);
						indivReq.setPreviouslastname(prevlastname);
						if (dbService.save(indivReq)) {
							form.setErrorMessage(mainReq.getRequestid());
							form.setFlag("success");
						}
					} else {
						indivReq.setCifno(cifno);
						indivReq.setTitle(title);
						indivReq.setLastname(lastname);
						indivReq.setFirstname(firstname);
						indivReq.setMiddlename(middlename);
						indivReq.setSuffix(suffix);
						indivReq.setShortname(shortname);
						indivReq.setPreviouslastname(prevlastname);
						if (dbService.saveOrUpdate(indivReq)) {
							form.setErrorMessage(mainReq.getRequestid());
							form.setFlag("success");
						}
					}
				} else if (customertype != null && customertype.equals("2")) {
					if (corporatename == null || tradename == null) {
						form.setErrorMessage("<b>Failed ! </b>Missing required field(s) !");
						return form;
					}

					Tbchangecifdetailsrequest mainReq = (Tbchangecifdetailsrequest) dbService
							.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbchangecifdetailsrequest WHERE requestid=:requestid AND cifno=:cifno AND changetype='1'",
									params);
					if (mainReq == null) {
						mainReq = new Tbchangecifdetailsrequest();
						String reqId = generateRequestID("1");
						mainReq.setRequestid(reqId);
						mainReq.setCifno(cifno);
						mainReq.setCustomertype("2");
						mainReq.setFullname(corporatename);
						mainReq.setChangetype("1");
						mainReq.setRequestedby(UserUtil.securityService.getUserName());
						mainReq.setDaterequested(new Date());
						mainReq.setRequeststatus(requeststatus);

						params.put("username", UserUtil.securityService.getUserName());
						String teamcode = (String) dbService
								.executeUniqueSQLQuery("SELECT teamcode FROM Tbuser WHERE username=:username", params);
						mainReq.setTeamcode(teamcode);
						if (dbService.save(mainReq)) {
							// Save Email to TBSMTP Oct.19,2017 - Kev
							EmailFacade email = new EmailFacade();
							EmailForm emailform = new EmailForm();
							//emailform.setCifno(cifno);
							//emailform.setRequestid(reqId);
							//emailform.setEmailcode(EmailCode.CHANGE_CIF_DETAILS_REQUEST);
							//email.saveEmailSMTP(emailform);
						}
					} else {
						mainReq.setCifno(cifno);
						mainReq.setCustomertype("2");
						mainReq.setFullname(corporatename);
						mainReq.setChangetype("1");
						mainReq.setRequestedby(UserUtil.securityService.getUserName());
						mainReq.setDaterequested(new Date());
						mainReq.setRequeststatus(requeststatus);
						params.put("username", UserUtil.securityService.getUserName());
						String teamcode = (String) dbService
								.executeUniqueSQLQuery("SELECT teamcode FROM Tbuser WHERE username=:username", params);
						mainReq.setTeamcode(teamcode);
						dbService.saveOrUpdate(mainReq);
					}

					Tbcorporaterequest corpReq = (Tbcorporaterequest) dbService.executeUniqueHQLQueryMaxResultOne(
							"FROM Tbcorporaterequest WHERE requestid=:requestid AND cifno=:cifno", params);
					if (corpReq == null) {
						corpReq = new Tbcorporaterequest();
						corpReq.setRequestid(mainReq.getRequestid());
						corpReq.setCifno(cifno);
						corpReq.setCorporatename(corporatename);
						corpReq.setTradename(tradename);
						if (dbService.save(corpReq)) {
							form.setErrorMessage(mainReq.getRequestid());
							form.setFlag("success");
						}
					} else {
						corpReq.setCifno(cifno);
						corpReq.setCorporatename(corporatename);
						corpReq.setTradename(tradename);
						if (dbService.saveOrUpdate(corpReq)) {
							form.setErrorMessage(mainReq.getRequestid());
							form.setFlag("success");
						}
					}

				}
			} else {
				System.out.println(">>>> Change CIF Details : Request ID or CIF No. is NULL!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public Tbchangecifdetailsrequest getChangeCIFMain(String requestid, String cifno) {
		DBService dbservice = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		Tbchangecifdetailsrequest main = new Tbchangecifdetailsrequest();
		try {
			if (cifno != null && requestid != null) {
				params.put("requestid", requestid);
				params.put("cifno", cifno);
				main = (Tbchangecifdetailsrequest) dbservice.executeUniqueHQLQuery(
						"FROM Tbchangecifdetailsrequest WHERE requestid=:requestid AND cifno=:cifno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return main;
	}

	@Override
	public FormValidation saveIndivAddress(String requeststatus, String requestid, String cifno,
			Tbcifindividualrequest indivReq) {
		FormValidation form = new FormValidation();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		form.setFlag("failed");
		try {
			if (cifno != null) {
				params.put("requestid", requestid == null ? "" : requestid);
				params.put("cifno", cifno);
				boolean address = false;
				// Address1 Address2
				if (indivReq.getCountry1() != null) {
					if (indivReq.getCountry1().equals("PH")) {
						if (indivReq.getProvince1() == null || indivReq.getProvince1().equals("")
								|| indivReq.getCity1() == null || indivReq.getCity1().equals("")) {
							address = true;
						}
					}
				}

				if (indivReq.getCountry2() != null) {
					if (indivReq.getCountry1().equals("PH")) {
						if (indivReq.getProvince2() == null || indivReq.getProvince2().equals("")
								|| indivReq.getCity2() == null || indivReq.getCity2().equals("")) {
							address = true;
						}
					}
				}
				// Address details
				if (indivReq.getStreetno1() == null || indivReq.getStreetno1().equals("")
						|| indivReq.getCountry1() == null || indivReq.getCountry1().equals("")
						|| indivReq.getHomeownership1() == null || indivReq.getStreetno2() == null
						|| indivReq.getStreetno2().equals("") || indivReq.getCountry2() == null
						|| indivReq.getCountry2().equals("") || indivReq.getHomeownership2() == null || address) {
					form.setErrorMessage("<b>Failed ! </b>Missing required field(s)!");
					return form;
				}

				String lname = "";
				String fname = "";
				String mname = "";
				String suf = "";
				Tbcifindividual indiv = (Tbcifindividual) dbService
						.executeUniqueHQLQueryMaxResultOne("FROM Tbcifindividual WHERE cifno=:cifno", params);
				if (indiv != null) {
					lname = indiv.getLastname() == null ? "" : indiv.getLastname();
					fname = indiv.getFirstname() == null ? "" : indiv.getFirstname();
					mname = indiv.getMiddlename() == null ? "" : indiv.getMiddlename();
					suf = indiv.getSuffix() == null ? "" : indiv.getSuffix();

					Tbchangecifdetailsrequest mainReq = (Tbchangecifdetailsrequest) dbService
							.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbchangecifdetailsrequest WHERE requestid=:requestid AND cifno=:cifno AND changetype='2'",
									params);
					if (mainReq == null) {
						mainReq = new Tbchangecifdetailsrequest();
						String reqId = generateRequestID("2");
						mainReq.setRequestid(reqId);
						mainReq.setCifno(cifno);
						mainReq.setCustomertype("1");

						mainReq.setFullname(lname + ", " + fname + " " + suf + " " + mname);

						mainReq.setChangetype("2");
						mainReq.setRequestedby(UserUtil.securityService.getUserName());
						mainReq.setDaterequested(new Date());
						mainReq.setRequeststatus(requeststatus);

						params.put("username", UserUtil.securityService.getUserName());
						String teamcode = (String) dbService
								.executeUniqueSQLQuery("SELECT teamcode FROM Tbuser WHERE username=:username", params);
						mainReq.setTeamcode(teamcode);
						if (dbService.save(mainReq)) {
							// Save Email to TBSMTP Oct.19,2017 - Kev
							EmailFacade email = new EmailFacade();
							EmailForm emailform = new EmailForm();
							//emailform.setCifno(cifno);
							//emailform.setRequestid(reqId);
							//emailform.setEmailcode(EmailCode.CHANGE_CIF_DETAILS_REQUEST);
							//email.saveEmailSMTP(emailform);
						}
					} else {
						mainReq.setCifno(cifno);
						mainReq.setCustomertype("1");
						if (mainReq.getFullname() == null) {
							mainReq.setFullname(lname + ", " + fname + " " + suf + " " + mname);
						}
						mainReq.setChangetype("2");
						mainReq.setRequeststatus(requeststatus);

						params.put("username", UserUtil.securityService.getUserName());
						String teamcode = (String) dbService
								.executeUniqueSQLQuery("SELECT teamcode FROM Tbuser WHERE username=:username", params);
						mainReq.setTeamcode(teamcode);
						dbService.saveOrUpdate(mainReq);
					}

					Tbcifindividualrequest indvReq = (Tbcifindividualrequest) dbService
							.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbcifindividualrequest WHERE requestid=:requestid AND cifno=:cifno", params);
					if (indvReq == null) {
						indvReq = new Tbcifindividualrequest();
						indvReq.setRequestid(mainReq.getRequestid());
						indvReq.setCifno(cifno);
						indvReq.setLastname(indiv.getLastname());
						indvReq.setFirstname(indiv.getFirstname());
						indvReq.setMiddlename(indiv.getMiddlename());
						indvReq.setStreetno1(indivReq.getStreetno1());
						indvReq.setSubdivision1(indivReq.getSubdivision1());
						indvReq.setCountry1(indivReq.getCountry1());
						indvReq.setProvince1(indivReq.getProvince1());
						indvReq.setCity1(indivReq.getCity1());
						indvReq.setBarangay1(indivReq.getBarangay1());
						indvReq.setPostalcode1(indivReq.getPostalcode1());
						indvReq.setOtherhomeownership1(indivReq.getOtherhomeownership1());
						indvReq.setHomeownership1(indivReq.getHomeownership1());
						indvReq.setOccupiedsince1(indivReq.getOccupiedsince1());
						indvReq.setPostalcodename1(indivReq.getPostalcodename1());
						indvReq.setFulladdress1(indivReq.getFulladdress1());

						indvReq.setIssameaddress1(indivReq.getIssameaddress1());
						indvReq.setIsmailingaddress(indivReq.getIsmailingaddress());

						indvReq.setStreetno2(indivReq.getStreetno2());
						indvReq.setSubdivision2(indivReq.getSubdivision2());
						indvReq.setCountry2(indivReq.getCountry2());
						indvReq.setProvince2(indivReq.getProvince2());
						indvReq.setCity2(indivReq.getCity2());
						indvReq.setBarangay2(indivReq.getBarangay2());
						indvReq.setPostalcode2(indivReq.getPostalcode2());
						indvReq.setOtherhomeownership2(indivReq.getOtherhomeownership2());
						indvReq.setHomeownership2(indivReq.getHomeownership2());
						indvReq.setOccupiedsince2(indivReq.getOccupiedsince2());
						indvReq.setPostalcodename2(indivReq.getPostalcodename2());
						indvReq.setFulladdress2(indivReq.getFulladdress2());

						if (dbService.save(indvReq)) {
							form.setErrorMessage(mainReq.getRequestid());
							form.setFlag("success");
						}
					} else {
						indvReq.setStreetno1(indivReq.getStreetno1());
						indvReq.setSubdivision1(indivReq.getSubdivision1());
						indvReq.setCountry1(indivReq.getCountry1());
						indvReq.setProvince1(indivReq.getProvince1());
						indvReq.setCity1(indivReq.getCity1());
						indvReq.setBarangay1(indivReq.getBarangay1());
						indvReq.setPostalcode1(indivReq.getPostalcode1());
						indvReq.setOtherhomeownership1(indivReq.getOtherhomeownership1());
						indvReq.setHomeownership1(indivReq.getHomeownership1());
						indvReq.setOccupiedsince1(indivReq.getOccupiedsince1());
						indvReq.setPostalcodename1(indivReq.getPostalcodename1());
						indvReq.setFulladdress1(indivReq.getFulladdress1());

						indvReq.setIssameaddress1(indivReq.getIssameaddress1());
						indvReq.setIsmailingaddress(indivReq.getIsmailingaddress());

						indvReq.setStreetno2(indivReq.getStreetno2());
						indvReq.setSubdivision2(indivReq.getSubdivision2());
						indvReq.setCountry2(indivReq.getCountry2());
						indvReq.setProvince2(indivReq.getProvince2());
						indvReq.setCity2(indivReq.getCity2());
						indvReq.setBarangay2(indivReq.getBarangay2());
						indvReq.setPostalcode2(indivReq.getPostalcode2());
						indvReq.setOtherhomeownership2(indivReq.getOtherhomeownership2());
						indvReq.setHomeownership2(indivReq.getHomeownership2());
						indvReq.setOccupiedsince2(indivReq.getOccupiedsince2());
						indvReq.setPostalcodename2(indivReq.getPostalcodename2());
						indvReq.setFulladdress2(indivReq.getFulladdress2());

						if (dbService.saveOrUpdate(indvReq)) {
							form.setErrorMessage(mainReq.getRequestid());
							form.setFlag("success");
						}
					}
				}
			} else {
				System.out.println(">>>> Change CIF Details : Request ID or CIF No. is NULL!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public FormValidation saveCorpAddress(String requeststatus, String requestid, String cifno,
			Tbcorporaterequest corpReq) {
		FormValidation form = new FormValidation();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		form.setFlag("failed");
		try {
			if (cifno != null) {
				params.put("requestid", requestid == null ? "" : requestid);
				params.put("cifno", cifno);
				boolean address = false;
				// Address1 Address2
				if (corpReq.getCountry1() != null) {
					if (corpReq.getCountry1().equals("PH")) {
						if (corpReq.getProvince1() == null || corpReq.getProvince1().equals("")
								|| corpReq.getCity1() == null || corpReq.getCity1().equals("")) {
							address = true;
						}
					}
				}

				if (corpReq.getCountry2() != null) {
					if (corpReq.getCountry1().equals("PH")) {
						if (corpReq.getProvince2() == null || corpReq.getProvince2().equals("")
								|| corpReq.getCity2() == null || corpReq.getCity2().equals("")) {
							address = true;
						}
					}
				}
				// Address details
				if (corpReq.getStreetno1() == null || corpReq.getStreetno1().equals("") || corpReq.getCountry1() == null
						|| corpReq.getCountry1().equals("") || corpReq.getHomeownership1() == null
						|| corpReq.getStreetno2() == null || corpReq.getStreetno2().equals("")
						|| corpReq.getCountry2() == null || corpReq.getCountry2().equals("")
						|| corpReq.getHomeownership2() == null || address) {
					form.setErrorMessage("<b>Failed ! </b>Missing required field(s)!");
					return form;
				}

				Tbcifcorporate corp = (Tbcifcorporate) dbService
						.executeUniqueHQLQueryMaxResultOne("FROM Tbcifcorporate WHERE cifno=:cifno", params);
				if (corp != null) {
					Tbchangecifdetailsrequest mainReq = (Tbchangecifdetailsrequest) dbService
							.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbchangecifdetailsrequest WHERE requestid=:requestid AND cifno=:cifno AND changetype='2'",
									params);
					if (mainReq == null) {
						mainReq = new Tbchangecifdetailsrequest();
						String reqId = generateRequestID("2");
						mainReq.setRequestid(reqId);
						mainReq.setCifno(cifno);
						mainReq.setCustomertype("2");

						mainReq.setFullname(corp.getCorporatename());

						mainReq.setChangetype("2");
						mainReq.setRequestedby(UserUtil.securityService.getUserName());
						mainReq.setDaterequested(new Date());
						mainReq.setRequeststatus(requeststatus);

						params.put("username", UserUtil.securityService.getUserName());
						String teamcode = (String) dbService
								.executeUniqueSQLQuery("SELECT teamcode FROM Tbuser WHERE username=:username", params);
						mainReq.setTeamcode(teamcode);
						if (dbService.save(mainReq)) {
							// Save Email to TBSMTP Oct.19,2017 - Kev
							EmailFacade email = new EmailFacade();
							EmailForm emailform = new EmailForm();
							//emailform.setCifno(cifno);
							//emailform.setRequestid(reqId);
							//emailform.setEmailcode(EmailCode.CHANGE_CIF_DETAILS_REQUEST);
							//email.saveEmailSMTP(emailform);
						}
					} else {
						mainReq.setCifno(cifno);
						mainReq.setCustomertype("2");
						if (mainReq.getFullname() == null) {
							mainReq.setFullname(corp.getCorporatename());
						}
						mainReq.setChangetype("2");
						mainReq.setRequeststatus(requeststatus);

						params.put("username", UserUtil.securityService.getUserName());
						String teamcode = (String) dbService
								.executeUniqueSQLQuery("SELECT teamcode FROM Tbuser WHERE username=:username", params);
						mainReq.setTeamcode(teamcode);
						dbService.saveOrUpdate(mainReq);
					}

					Tbcorporaterequest corporateReq = (Tbcorporaterequest) dbService.executeUniqueHQLQueryMaxResultOne(
							"FROM Tbcorporaterequest WHERE requestid=:requestid AND cifno=:cifno", params);
					if (corporateReq == null) {
						corporateReq = new Tbcorporaterequest();
						corporateReq.setRequestid(mainReq.getRequestid());
						corporateReq.setCifno(cifno);
						corporateReq.setCorporatename(corp.getCorporatename());
						corporateReq.setStreetno1(corpReq.getStreetno1());
						corporateReq.setSubdivision1(corpReq.getSubdivision1());
						corporateReq.setCountry1(corpReq.getCountry1());
						corporateReq.setProvince1(corpReq.getProvince1());
						corporateReq.setCity1(corpReq.getCity1());
						corporateReq.setBarangay1(corpReq.getBarangay1());
						corporateReq.setPostalcode1(corpReq.getPostalcode1());
						corporateReq.setOtherhomeownership1(corpReq.getOtherhomeownership1());
						corporateReq.setHomeownership1(corpReq.getHomeownership1());
						corporateReq.setOccupiedsince1(corpReq.getOccupiedsince1());
						corporateReq.setPostalcodename1(corpReq.getPostalcodename1());
						corporateReq.setFulladdress1(corpReq.getFulladdress1());

						corporateReq.setIssameaddress1(corpReq.getIssameaddress1());
						corporateReq.setIsmailingaddress(corpReq.getIsmailingaddress());

						corporateReq.setStreetno2(corpReq.getStreetno2());
						corporateReq.setSubdivision2(corpReq.getSubdivision2());
						corporateReq.setCountry2(corpReq.getCountry2());
						corporateReq.setProvince2(corpReq.getProvince2());
						corporateReq.setCity2(corpReq.getCity2());
						corporateReq.setBarangay2(corpReq.getBarangay2());
						corporateReq.setPostalcode2(corpReq.getPostalcode2());
						corporateReq.setOtherhomeownership2(corpReq.getOtherhomeownership2());
						corporateReq.setHomeownership2(corpReq.getHomeownership2());
						corporateReq.setOccupiedsince2(corpReq.getOccupiedsince2());
						corporateReq.setPostalcodename2(corpReq.getPostalcodename2());
						corporateReq.setFulladdress2(corpReq.getFulladdress2());

						if (dbService.save(corporateReq)) {
							form.setErrorMessage(mainReq.getRequestid());
							form.setFlag("success");
						}
					} else {
						corporateReq.setStreetno1(corpReq.getStreetno1());
						corporateReq.setSubdivision1(corpReq.getSubdivision1());
						corporateReq.setCountry1(corpReq.getCountry1());
						corporateReq.setProvince1(corpReq.getProvince1());
						corporateReq.setCity1(corpReq.getCity1());
						corporateReq.setBarangay1(corpReq.getBarangay1());
						corporateReq.setPostalcode1(corpReq.getPostalcode1());
						corporateReq.setOtherhomeownership1(corpReq.getOtherhomeownership1());
						corporateReq.setHomeownership1(corpReq.getHomeownership1());
						corporateReq.setOccupiedsince1(corpReq.getOccupiedsince1());
						corporateReq.setPostalcodename1(corpReq.getPostalcodename1());
						corporateReq.setFulladdress1(corpReq.getFulladdress1());

						corporateReq.setIssameaddress1(corpReq.getIssameaddress1());
						corporateReq.setIsmailingaddress(corpReq.getIsmailingaddress());

						corporateReq.setStreetno2(corpReq.getStreetno2());
						corporateReq.setSubdivision2(corpReq.getSubdivision2());
						corporateReq.setCountry2(corpReq.getCountry2());
						corporateReq.setProvince2(corpReq.getProvince2());
						corporateReq.setCity2(corpReq.getCity2());
						corporateReq.setBarangay2(corpReq.getBarangay2());
						corporateReq.setPostalcode2(corpReq.getPostalcode2());
						corporateReq.setOtherhomeownership2(corpReq.getOtherhomeownership2());
						corporateReq.setHomeownership2(corpReq.getHomeownership2());
						corporateReq.setOccupiedsince2(corpReq.getOccupiedsince2());
						corporateReq.setPostalcodename2(corpReq.getPostalcodename2());
						corporateReq.setFulladdress2(corpReq.getFulladdress2());

						if (dbService.saveOrUpdate(corporateReq)) {
							form.setErrorMessage(mainReq.getRequestid());
							form.setFlag("success");
						}
					}
				}
			} else {
				System.out.println(">>>> Change CIF Details : Request ID or CIF No. is NULL!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public FormValidation saveIndivContactDetails(String requeststatus, String requestid, String cifno,
			Tbcifindividualrequest indivReq) {
		FormValidation form = new FormValidation();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		form.setFlag("failed");
		try {
			if (cifno != null) {
				params.put("requestid", requestid == null ? "" : requestid);
				params.put("cifno", cifno);
				if (indivReq.getMobilecountrycode() == null || indivReq.getMobileareacode() == null
						|| indivReq.getMobilenumber() == null || indivReq.getHomecountrycode() == null
						|| indivReq.getHomeareacode() == null || indivReq.getHomenumber() == null
						|| indivReq.getFaxcountrycode() == null || indivReq.getFaxareacode() == null
						|| indivReq.getFaxnumber() == null) {
					form.setErrorMessage("<b>Failed ! </b>Missing required field(s) !");
					return form;
				}
				if (indivReq.getContacttype1() != null) {
					if (indivReq.getContactvalue1() == null) {
						form.setErrorMessage("<b>Failed ! </b>Missing required field(s) !");
						return form;
					}
				}

				if (indivReq.getContacttype2() != null) {
					if (indivReq.getContactvalue2() == null) {
						form.setErrorMessage("<b>Failed ! </b>Missing required field(s) !");
						return form;
					}
				}

				String lname = "";
				String fname = "";
				String mname = "";
				String suf = "";
				Tbcifindividual indiv = (Tbcifindividual) dbService
						.executeUniqueHQLQueryMaxResultOne("FROM Tbcifindividual WHERE cifno=:cifno", params);
				if (indiv != null) {
					lname = indiv.getLastname() == null ? "" : indiv.getLastname();
					fname = indiv.getFirstname() == null ? "" : indiv.getFirstname();
					mname = indiv.getMiddlename() == null ? "" : indiv.getMiddlename();
					suf = indiv.getSuffix() == null ? "" : indiv.getSuffix();

					Tbchangecifdetailsrequest mainReq = (Tbchangecifdetailsrequest) dbService
							.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbchangecifdetailsrequest WHERE requestid=:requestid AND cifno=:cifno AND changetype='3'",
									params);
					if (mainReq == null) {
						mainReq = new Tbchangecifdetailsrequest();
						String reqId = generateRequestID("3");
						mainReq.setRequestid(reqId);
						mainReq.setCifno(cifno);
						mainReq.setCustomertype("1");

						mainReq.setFullname(lname + ", " + fname + " " + suf + " " + mname);

						mainReq.setChangetype("3");
						mainReq.setRequestedby(UserUtil.securityService.getUserName());
						mainReq.setDaterequested(new Date());
						mainReq.setRequeststatus(requeststatus);

						params.put("username", UserUtil.securityService.getUserName());
						String teamcode = (String) dbService
								.executeUniqueSQLQuery("SELECT teamcode FROM Tbuser WHERE username=:username", params);
						mainReq.setTeamcode(teamcode);
						if (dbService.save(mainReq)) {
							// Save Email to TBSMTP Oct.19,2017 - Kev
							EmailFacade email = new EmailFacade();
							EmailForm emailform = new EmailForm();
							//emailform.setCifno(cifno);
							//emailform.setRequestid(reqId);
							//emailform.setEmailcode(EmailCode.CHANGE_CIF_DETAILS_REQUEST);
							//email.saveEmailSMTP(emailform);
						}
					} else {
						mainReq.setCifno(cifno);
						mainReq.setCustomertype("1");
						if (mainReq.getFullname() == null) {
							mainReq.setFullname(lname + ", " + fname + " " + suf + " " + mname);
						}
						mainReq.setChangetype("3");
						mainReq.setRequeststatus(requeststatus);

						params.put("username", UserUtil.securityService.getUserName());
						String teamcode = (String) dbService
								.executeUniqueSQLQuery("SELECT teamcode FROM Tbuser WHERE username=:username", params);
						mainReq.setTeamcode(teamcode);
						dbService.saveOrUpdate(mainReq);
					}

					Tbcifindividualrequest indvReq = (Tbcifindividualrequest) dbService
							.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbcifindividualrequest WHERE requestid=:requestid AND cifno=:cifno", params);
					if (indvReq == null) {
						indvReq = new Tbcifindividualrequest();
						indvReq.setRequestid(mainReq.getRequestid());
						indvReq.setCifno(cifno);
						indvReq.setLastname(indiv.getLastname());
						indvReq.setFirstname(indiv.getFirstname());
						indvReq.setMiddlename(indiv.getMiddlename());

						indvReq.setMobilecountrycode(indivReq.getMobilecountrycode());
						indvReq.setMobileareacode(indivReq.getMobileareacode());
						indvReq.setMobilenumber(indivReq.getMobilenumber());
						indvReq.setHomecountrycode(indivReq.getHomecountrycode());
						indvReq.setHomeareacode(indivReq.getHomeareacode());
						indvReq.setHomenumber(indivReq.getHomenumber());
						indvReq.setFaxnumber(indivReq.getFaxnumber());
						indvReq.setFaxcountrycode(indivReq.getFaxcountrycode());
						indvReq.setFaxareacode(indivReq.getFaxareacode());
						indvReq.setFaxnumber(indivReq.getFaxnumber());
						indvReq.setEmailaddress(indivReq.getEmailaddress());
						indvReq.setContacttype1(indivReq.getContacttype1());
						indvReq.setContactvalue1(indivReq.getContactvalue1());
						indvReq.setContacttype2(indivReq.getContacttype2());
						indvReq.setContactvalue2(indivReq.getContactvalue2());
						if (dbService.save(indvReq)) {
							form.setErrorMessage(mainReq.getRequestid());
							form.setFlag("success");
						}
					} else {
						indvReq.setMobilecountrycode(indivReq.getMobilecountrycode());
						indvReq.setMobileareacode(indivReq.getMobileareacode());
						indvReq.setMobilenumber(indivReq.getMobilenumber());
						indvReq.setHomecountrycode(indivReq.getHomecountrycode());
						indvReq.setHomeareacode(indivReq.getHomeareacode());
						indvReq.setHomenumber(indivReq.getHomenumber());
						indvReq.setFaxnumber(indivReq.getFaxnumber());
						indvReq.setFaxcountrycode(indivReq.getFaxcountrycode());
						indvReq.setFaxareacode(indivReq.getFaxareacode());
						indvReq.setFaxnumber(indivReq.getFaxnumber());
						indvReq.setEmailaddress(indivReq.getEmailaddress());
						indvReq.setContacttype1(indivReq.getContacttype1());
						indvReq.setContactvalue1(indivReq.getContactvalue1());
						indvReq.setContacttype2(indivReq.getContacttype2());
						indvReq.setContactvalue2(indivReq.getContactvalue2());
						if (dbService.saveOrUpdate(indvReq)) {
							form.setErrorMessage(mainReq.getRequestid());
							form.setFlag("success");
						}
					}
				}
			} else {
				System.out.println(">>>> Change CIF Details : Request ID or CIF No. is NULL!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public FormValidation saveCivilStatusSpouseInfo(String requeststatus, String requestid, String maincifno,
			String civilstatus, String spsCifno, String spsTitle, String spsLastname, String spsFirstname,
			String spsMiddlename, String spsSuffix, Date spsDateofbirth) {
		FormValidation form = new FormValidation();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		form.setFlag("failed");
		try {
			if (maincifno != null) {
				params.put("requestid", requestid == null ? "" : requestid);
				params.put("cifno", maincifno);

				if (civilstatus == null) {
					form.setErrorMessage("<b>Failed ! </b>Missing required field(s) !");
					return form;
				} else {
					if (civilstatus.equals("2")) {
						if (spsTitle == null || spsLastname == null || spsFirstname == null) {
							form.setErrorMessage("<b>Failed ! </b>Missing required field(s) !");
							return form;
						}
					}
				}
				String lname = "";
				String fname = "";
				String mname = "";
				String suf = "";
				Tbcifindividual indiv = (Tbcifindividual) dbService
						.executeUniqueHQLQueryMaxResultOne("FROM Tbcifindividual WHERE cifno=:cifno", params);
				if (indiv != null) {
					lname = indiv.getLastname() == null ? "" : indiv.getLastname();
					fname = indiv.getFirstname() == null ? "" : indiv.getFirstname();
					mname = indiv.getMiddlename() == null ? "" : indiv.getMiddlename();
					suf = indiv.getSuffix() == null ? "" : indiv.getSuffix();

					Tbchangecifdetailsrequest mainReq = (Tbchangecifdetailsrequest) dbService
							.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbchangecifdetailsrequest WHERE requestid=:requestid AND cifno=:cifno AND changetype='4'",
									params);
					if (mainReq == null) {
						mainReq = new Tbchangecifdetailsrequest();
						String reqId = generateRequestID("4");
						mainReq.setRequestid(reqId);
						mainReq.setCifno(maincifno);
						mainReq.setCustomertype("1");

						mainReq.setFullname(lname + ", " + fname + " " + suf + " " + mname);

						mainReq.setChangetype("4");
						mainReq.setRequestedby(UserUtil.securityService.getUserName());
						mainReq.setDaterequested(new Date());
						mainReq.setRequeststatus(requeststatus);

						params.put("username", UserUtil.securityService.getUserName());
						String teamcode = (String) dbService
								.executeUniqueSQLQuery("SELECT teamcode FROM Tbuser WHERE username=:username", params);
						mainReq.setTeamcode(teamcode);
						if (dbService.save(mainReq)) {
							// Save Email to TBSMTP Oct.19,2017 - Kev
							EmailFacade email = new EmailFacade();
							EmailForm emailform = new EmailForm();
							//emailform.setCifno(maincifno);
							//emailform.setRequestid(reqId);
							//emailform.setEmailcode(EmailCode.CHANGE_CIF_DETAILS_REQUEST);
							//email.saveEmailSMTP(emailform);
						}
					} else {
						mainReq.setCifno(maincifno);
						mainReq.setCustomertype("1");
						if (mainReq.getFullname() == null) {
							mainReq.setFullname(lname + ", " + fname + " " + suf + " " + mname);
						}
						mainReq.setChangetype("4");
						mainReq.setRequeststatus(requeststatus);

						params.put("username", UserUtil.securityService.getUserName());
						String teamcode = (String) dbService
								.executeUniqueSQLQuery("SELECT teamcode FROM Tbuser WHERE username=:username", params);
						mainReq.setTeamcode(teamcode);
						dbService.saveOrUpdate(mainReq);
					}

					Tbcifindividualrequest indvReq = (Tbcifindividualrequest) dbService
							.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbcifindividualrequest WHERE requestid=:requestid AND cifno=:cifno", params);
					if (indvReq == null) {
						indvReq = new Tbcifindividualrequest();
						indvReq.setRequestid(mainReq.getRequestid());
						indvReq.setCifno(maincifno);
						indvReq.setLastname(indiv.getLastname());
						indvReq.setFirstname(indiv.getFirstname());
						indvReq.setMiddlename(indiv.getMiddlename());
						indvReq.setCivilstatus(civilstatus);
						if (civilstatus.equals("2")) {
							indvReq.setSpousetitle(spsTitle);
							indvReq.setSpousecifno(spsCifno);
							indvReq.setSpouselastname(spsLastname);
							indvReq.setSpousefirstname(spsFirstname);
							indvReq.setSpousemiddlename(spsMiddlename);
							indvReq.setSpousesuffix(spsSuffix);
							indvReq.setSpousedateofbirth(spsDateofbirth);
						} else {
							indvReq.setSpousetitle(null);
							indvReq.setSpousecifno(null);
							indvReq.setSpouselastname(null);
							indvReq.setSpousefirstname(null);
							indvReq.setSpousemiddlename(null);
							indvReq.setSpousesuffix(null);
							indvReq.setSpousedateofbirth(null);
						}
						if (dbService.save(indvReq)) {
							form.setErrorMessage(mainReq.getRequestid());
							form.setFlag("success");
						}
					} else {
						indvReq.setCivilstatus(civilstatus);
						if (civilstatus.equals("2")) {
							indvReq.setSpousetitle(spsTitle);
							indvReq.setSpousecifno(spsCifno);
							indvReq.setSpouselastname(spsLastname);
							indvReq.setSpousefirstname(spsFirstname);
							indvReq.setSpousemiddlename(spsMiddlename);
							indvReq.setSpousesuffix(spsSuffix);
							indvReq.setSpousedateofbirth(spsDateofbirth);
						} else {
							indvReq.setSpousetitle(null);
							indvReq.setSpousecifno(null);
							indvReq.setSpouselastname(null);
							indvReq.setSpousefirstname(null);
							indvReq.setSpousemiddlename(null);
							indvReq.setSpousesuffix(null);
							indvReq.setSpousedateofbirth(null);
						}
						if (dbService.saveOrUpdate(indvReq)) {
							form.setErrorMessage(mainReq.getRequestid());
							form.setFlag("success");
						}
					}
				}
			} else {
				System.out.println(">>>> Change CIF Details : Request ID or CIF No. is NULL!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbothercontactrequest> listOtherContactsReq(String requestid, String cifno) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbothercontactrequest> list = new ArrayList<Tbothercontactrequest>();
		try {
			if (cifno != null) {
				params.put("requestid", requestid == null ? "" : requestid);
				params.put("cifno", cifno);
				list = (List<Tbothercontactrequest>) dbService.executeListHQLQuery(
						"FROM Tbothercontactrequest WHERE requestid=:requestid AND cifno=:cifno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveOtherContactReq(Tbothercontactrequest contacts) {
		String flag = "failed";
		DBService dbservice = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			if (contacts.getId() != null) {
				param.put("id", contacts.getId());
				Tbothercontactrequest c = (Tbothercontactrequest) dbservice
						.executeUniqueHQLQuery("FROM Tbothercontactrequest WHERE id=:id", param);
				if (c != null) {
					c.setContactperson(contacts.getContactperson());
					c.setDirectnumber(contacts.getDirectnumber());
					c.setEmailaddress(contacts.getEmailaddress());
					c.setMobilenumber(contacts.getMobilenumber());
					c.setOtherdetails(contacts.getOtherdetails());
					c.setPosition(contacts.getPosition());
				}
				if (dbservice.saveOrUpdate(c)) {
					flag = "success";
				}
			} else {
				if (dbservice.save(contacts)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteOtherContactReq(Integer id) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> param = HQLUtil.getMap();
		String flag = "failed";
		try {
			param.put("id", id);
			Tbothercontactrequest c = (Tbothercontactrequest) dbService
					.executeUniqueHQLQuery("FROM Tbothercontactrequest WHERE id=:id", param);
			if (c != null) {
				if (dbService.delete(c)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public FormValidation saveCorpContactDetails(String requeststatus, String requestid, String cifno,
			Tbcorporaterequest corpReq) {
		FormValidation form = new FormValidation();
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		form.setFlag("failed");
		try {
			if (cifno != null) {
				params.put("requestid", requestid == null ? "" : requestid);
				params.put("cifno", cifno);
				if (corpReq.getMobilecountrycode() == null || corpReq.getMobileareacode() == null
						|| corpReq.getMobilenumber() == null || corpReq.getOfficecountrycode() == null
						|| corpReq.getOfficeareacode() == null || corpReq.getOfficenumber() == null
						|| corpReq.getFaxcountrycode() == null || corpReq.getFaxareacode() == null
						|| corpReq.getFaxnumber() == null || corpReq.getMaincontact1() == null) {
					form.setErrorMessage("<b>Failed ! </b>Missing required field(s) !");
					return form;
				}
				Tbcifcorporate corp = (Tbcifcorporate) dbService
						.executeUniqueHQLQueryMaxResultOne("FROM Tbcifcorporate WHERE cifno=:cifno", params);
				if (corp != null) {
					Tbchangecifdetailsrequest mainReq = (Tbchangecifdetailsrequest) dbService
							.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbchangecifdetailsrequest WHERE requestid=:requestid AND cifno=:cifno AND changetype='3'",
									params);
					if (mainReq == null) {
						mainReq = new Tbchangecifdetailsrequest();
						String reqId = generateRequestID("3");
						mainReq.setRequestid(reqId);
						mainReq.setCifno(cifno);
						mainReq.setCustomertype("2");

						mainReq.setFullname(corp.getCorporatename());

						mainReq.setChangetype("3");
						mainReq.setRequestedby(UserUtil.securityService.getUserName());
						mainReq.setDaterequested(new Date());
						mainReq.setRequeststatus(requeststatus);

						params.put("username", UserUtil.securityService.getUserName());
						String teamcode = (String) dbService
								.executeUniqueSQLQuery("SELECT teamcode FROM Tbuser WHERE username=:username", params);
						mainReq.setTeamcode(teamcode);
						if (dbService.save(mainReq)) {
							// Save Email to TBSMTP Oct.19,2017 - Kev
							EmailFacade email = new EmailFacade();
							EmailForm emailform = new EmailForm();
							//emailform.setCifno(cifno);
							//emailform.setRequestid(reqId);
							//emailform.setEmailcode(EmailCode.CHANGE_CIF_DETAILS_REQUEST);
							//email.saveEmailSMTP(emailform);
						}
					} else {
						mainReq.setCifno(cifno);
						mainReq.setCustomertype("2");
						if (mainReq.getFullname() == null) {
							mainReq.setFullname(corp.getCorporatename());
						}
						mainReq.setChangetype("3");
						mainReq.setRequeststatus(requeststatus);

						params.put("username", UserUtil.securityService.getUserName());
						String teamcode = (String) dbService
								.executeUniqueSQLQuery("SELECT teamcode FROM Tbuser WHERE username=:username", params);
						mainReq.setTeamcode(teamcode);
						dbService.saveOrUpdate(mainReq);
					}

					Tbcorporaterequest corporateReq = (Tbcorporaterequest) dbService.executeUniqueHQLQueryMaxResultOne(
							"FROM Tbcorporaterequest WHERE requestid=:requestid AND cifno=:cifno", params);
					if (corporateReq == null) {
						corporateReq = new Tbcorporaterequest();
						corporateReq.setRequestid(mainReq.getRequestid());
						corporateReq.setCifno(cifno);
						corporateReq.setCorporatename(corp.getCorporatename());
						corporateReq.setMobilecountrycode(corpReq.getMobilecountrycode());
						corporateReq.setMobileareacode(corpReq.getMobileareacode());
						corporateReq.setMobilenumber(corpReq.getMobilenumber());
						corporateReq.setOfficecountrycode(corpReq.getOfficecountrycode());
						corporateReq.setOfficeareacode(corpReq.getOfficeareacode());
						corporateReq.setOfficenumber(corpReq.getOfficenumber());
						corporateReq.setFaxnumber(corpReq.getFaxnumber());
						corporateReq.setFaxcountrycode(corpReq.getFaxcountrycode());
						corporateReq.setFaxareacode(corpReq.getFaxareacode());
						corporateReq.setFaxnumber(corpReq.getFaxnumber());
						corporateReq.setEmailaddress(corpReq.getEmailaddress());
						corporateReq.setMaincontact1(corpReq.getMaincontact1());
						corporateReq.setWebsite(corpReq.getWebsite());
						if (dbService.save(corporateReq)) {
							form.setErrorMessage(mainReq.getRequestid());
							form.setFlag("success");
						}
					} else {
						corporateReq.setMobilecountrycode(corpReq.getMobilecountrycode());
						corporateReq.setMobileareacode(corpReq.getMobileareacode());
						corporateReq.setMobilenumber(corpReq.getMobilenumber());
						corporateReq.setOfficecountrycode(corpReq.getOfficecountrycode());
						corporateReq.setOfficeareacode(corpReq.getOfficeareacode());
						corporateReq.setOfficenumber(corpReq.getOfficenumber());
						corporateReq.setFaxnumber(corpReq.getFaxnumber());
						corporateReq.setFaxcountrycode(corpReq.getFaxcountrycode());
						corporateReq.setFaxareacode(corpReq.getFaxareacode());
						corporateReq.setFaxnumber(corpReq.getFaxnumber());
						corporateReq.setEmailaddress(corpReq.getEmailaddress());
						corporateReq.setMaincontact1(corpReq.getMaincontact1());
						corporateReq.setWebsite(corpReq.getWebsite());
						if (dbService.saveOrUpdate(corporateReq)) {
							form.setErrorMessage(mainReq.getRequestid());
							form.setFlag("success");
						}
					}
				}
			} else {
				System.out.println(">>>> Change CIF Details : Request ID or CIF No. is NULL!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public String saveRequestStatus(String requeststatus, String requestid, String cifno, String changetype,
			String approverremarks) {
		String flag = "failed";
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (cifno != null && requestid != null && changetype != null) {
				params.put("cifno", cifno);
				params.put("requestid", requestid);
				params.put("changetype", changetype);
				Tbchangecifdetailsrequest req = (Tbchangecifdetailsrequest) dbService.executeUniqueHQLQueryMaxResultOne(
						"FROM Tbchangecifdetailsrequest WHERE requestid=:requestid AND cifno=:cifno AND changetype=:changetype",
						params);
				if (req != null) {
					req.setRequeststatus(requeststatus);
					req.setApproverremarks(approverremarks);
					if (requeststatus.equals("0")) {
						req.setRequestedby(UserUtil.securityService.getUserName());
						req.setDaterequested(new Date());
					}
					if (dbService.saveOrUpdate(req)) {
						flag = "success";
					}
				}
			} else {
				System.out.println(">>>> Change CIF Details : Request ID / CIF No. / Change Type is NULL!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String approveRequest(String requestid, String cifno, String changetype, String customertype,
			String approverremarks) {
		String flag = "failed";
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		Tbcifindividualrequest indivReq = null;
		Tbcorporaterequest corpReq = null;
		Tbcifindividual indiv = null;
		Tbcifcorporate corp = null;
		String requeststatus = "2";// Approved
		try {
			if (cifno != null && requestid != null) {
				params.put("requestid", requestid);
				params.put("cifno", cifno);
				params.put("changetype", changetype);
				Tbcifmain main = (Tbcifmain) dbService
						.executeUniqueHQLQueryMaxResultOne("FROM Tbcifmain WHERE cifno=:cifno", params);
				Tbchangecifdetailsrequest mainReq = (Tbchangecifdetailsrequest) dbService
						.executeUniqueHQLQueryMaxResultOne(
								"FROM Tbchangecifdetailsrequest WHERE requestid=:requestid AND cifno=:cifno AND changetype=:changetype",
								params);
				if (customertype != null && customertype.equals("1")) {
					indivReq = (Tbcifindividualrequest) dbService.executeUniqueHQLQueryMaxResultOne(
							"FROM Tbcifindividualrequest WHERE requestid=:requestid AND cifno=:cifno", params);
				} else if (customertype != null && customertype.equals("2")) {
					corpReq = (Tbcorporaterequest) dbService.executeUniqueHQLQueryMaxResultOne(
							"FROM Tbcorporaterequest WHERE requestid=:requestid AND cifno=:cifno", params);
				}
				if (mainReq != null) {
					/*********************** Customer Name ***********************/
					if (changetype.equals("1")) {
						// Individual
						if (mainReq.getCustomertype().equals("1")) {
							indiv = (Tbcifindividual) dbService.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbcifindividual WHERE cifno=:cifno", params);
							if (indiv != null) {
								if (indivReq != null) {
									indiv.setTitle(indivReq.getTitle());
									indiv.setLastname(indivReq.getLastname());
									indiv.setFirstname(indivReq.getFirstname());
									indiv.setMiddlename(indivReq.getMiddlename());
									indiv.setSuffix(indivReq.getSuffix());
									indiv.setShortname(indivReq.getShortname());
									indiv.setPreviouslastname(indivReq.getPreviouslastname());
									main.setFullname(mainReq.getFullname());
									if (dbService.saveOrUpdate(indiv)) {
										flag = "success";
										dbService.saveOrUpdate(main);
										mainReq.setRequeststatus(requeststatus);
										mainReq.setApproverremarks(approverremarks);
										mainReq.setApprovedby(UserUtil.securityService.getUserName());
										mainReq.setDateapprove(new Date());
										dbService.saveOrUpdate(mainReq);
									}
								}
							}
						}
						// Corporate
						if (mainReq.getCustomertype().equals("2") || mainReq.getCustomertype().equals("3")) {
							corp = (Tbcifcorporate) dbService.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbcifcorporate WHERE cifno=:cifno", params);
							if (corp != null) {
								if (corpReq != null) {
									corp.setCorporatename(corpReq.getCorporatename());
									corp.setTradename(corpReq.getCorporatename());
									main.setFullname(mainReq.getFullname());
									if (dbService.saveOrUpdate(corp)) {
										flag = "success";
										dbService.saveOrUpdate(main);
										mainReq.setRequeststatus(requeststatus);
										mainReq.setApproverremarks(approverremarks);
										mainReq.setApprovedby(UserUtil.securityService.getUserName());
										mainReq.setDateapprove(new Date());
										dbService.saveOrUpdate(mainReq);
									}
								}
							}
						}
					}

					/*********************** Address ***********************/
					if (changetype.equals("2")) {
						// Individual
						if (mainReq.getCustomertype().equals("1")) {
							indiv = (Tbcifindividual) dbService.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbcifindividual WHERE cifno=:cifno", params);
							if (indiv != null) {
								if (indivReq != null) {
									indiv.setStreetno1(indivReq.getStreetno1());
									indiv.setSubdivision1(indivReq.getSubdivision1());
									indiv.setCountry1(indivReq.getCountry1());
									indiv.setProvince1(indivReq.getProvince1());
									indiv.setCity1(indivReq.getCity1());
									indiv.setBarangay1(indivReq.getBarangay1());
									indiv.setPostalcode1(indivReq.getPostalcode1());
									indiv.setOtherhomeownership1(indivReq.getOtherhomeownership1());
									indiv.setHomeownership1(indivReq.getHomeownership1());
									indiv.setOccupiedsince1(indivReq.getOccupiedsince1());
									indiv.setPostalcodename1(indivReq.getPostalcodename1());
									indiv.setFulladdress1(indivReq.getFulladdress1());

									indiv.setIssameaddress1(indivReq.getIssameaddress1());
									indiv.setIsmailingaddress(indivReq.getIsmailingaddress());

									indiv.setStreetno2(indivReq.getStreetno2());
									indiv.setSubdivision2(indivReq.getSubdivision2());
									indiv.setCountry2(indivReq.getCountry2());
									indiv.setProvince2(indivReq.getProvince2());
									indiv.setCity2(indivReq.getCity2());
									indiv.setBarangay2(indivReq.getBarangay2());
									indiv.setPostalcode2(indivReq.getPostalcode2());
									indiv.setOtherhomeownership2(indivReq.getOtherhomeownership2());
									indiv.setHomeownership2(indivReq.getHomeownership2());
									indiv.setOccupiedsince2(indivReq.getOccupiedsince2());
									indiv.setPostalcodename2(indivReq.getPostalcodename2());
									indiv.setFulladdress2(indivReq.getFulladdress2());
									if (dbService.saveOrUpdate(indiv)) {
										flag = "success";
										main.setFulladdress1(indivReq.getFulladdress1());
										main.setFulladdress2(indivReq.getFulladdress2());
										dbService.saveOrUpdate(main);
										mainReq.setRequeststatus(requeststatus);
										mainReq.setApproverremarks(approverremarks);
										mainReq.setApprovedby(UserUtil.securityService.getUserName());
										mainReq.setDateapprove(new Date());
										dbService.saveOrUpdate(mainReq);
									}
								}
							}
						}

						// Corporate
						if (mainReq.getCustomertype().equals("2") || mainReq.getCustomertype().equals("3")) {
							corp = (Tbcifcorporate) dbService.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbcifcorporate WHERE cifno=:cifno", params);
							if (corp != null) {
								if (corpReq != null) {
									corp.setStreetno1(corpReq.getStreetno1());
									corp.setSubdivision1(corpReq.getSubdivision1());
									corp.setCountry1(corpReq.getCountry1());
									corp.setProvince1(corpReq.getProvince1());
									corp.setCity1(corpReq.getCity1());
									corp.setBarangay1(corpReq.getBarangay1());
									corp.setPostalcode1(corpReq.getPostalcode1());
									corp.setOtherhomeownership1(corpReq.getOtherhomeownership1());
									corp.setHomeownership1(corpReq.getHomeownership1());
									corp.setOccupiedsince1(corpReq.getOccupiedsince1());
									corp.setPostalcodename1(corpReq.getPostalcodename1());
									corp.setFulladdress1(corpReq.getFulladdress1());

									corp.setIssameaddress1(corpReq.getIssameaddress1());
									corp.setIsmailingaddress(corpReq.getIsmailingaddress());

									corp.setStreetno2(corpReq.getStreetno2());
									corp.setSubdivision2(corpReq.getSubdivision2());
									corp.setCountry2(corpReq.getCountry2());
									corp.setProvince2(corpReq.getProvince2());
									corp.setCity2(corpReq.getCity2());
									corp.setBarangay2(corpReq.getBarangay2());
									corp.setPostalcode2(corpReq.getPostalcode2());
									corp.setOtherhomeownership2(corpReq.getOtherhomeownership2());
									corp.setHomeownership2(corpReq.getHomeownership2());
									corp.setOccupiedsince2(corpReq.getOccupiedsince2());
									corp.setPostalcodename2(corpReq.getPostalcodename2());
									corp.setFulladdress2(corpReq.getFulladdress2());
									if (dbService.saveOrUpdate(corp)) {
										flag = "success";
										main.setFulladdress1(corpReq.getFulladdress1());
										main.setFulladdress2(corpReq.getFulladdress2());
										dbService.saveOrUpdate(main);
										mainReq.setRequeststatus(requeststatus);
										mainReq.setApproverremarks(approverremarks);
										mainReq.setApprovedby(UserUtil.securityService.getUserName());
										mainReq.setDateapprove(new Date());
										dbService.saveOrUpdate(mainReq);
									}
								}
							}
						} // end of corp

					}
				}

				/*********************** Civil Status And Spouse Info ***********************/
				if (changetype.equals("4")) {
					// Individual
					if (mainReq.getCustomertype().equals("1")) {
						indiv = (Tbcifindividual) dbService
								.executeUniqueHQLQueryMaxResultOne("FROM Tbcifindividual WHERE cifno=:cifno", params);
						if (indiv != null) {
							if (indivReq != null) {
								indiv.setCivilstatus(indivReq.getCivilstatus());
								if (indiv.getCivilstatus().equals("2")) {
									indiv.setSpousetitle(indivReq.getSpousetitle());
									indiv.setSpousecifno(indivReq.getSpousecifno());
									indiv.setSpouselastname(indivReq.getSpouselastname());
									indiv.setSpousefirstname(indivReq.getSpousefirstname());
									indiv.setSpousemiddlename(indivReq.getSpousemiddlename());
									indiv.setSpousesuffix(indivReq.getSpousesuffix());
									indiv.setSpousedateofbirth(indivReq.getSpousedateofbirth());
								} else {
									indiv.setSpousetitle(null);
									indiv.setSpousecifno(null);
									indiv.setSpouselastname(null);
									indiv.setSpousefirstname(null);
									indiv.setSpousemiddlename(null);
									indiv.setSpousesuffix(null);
									indiv.setSpousedateofbirth(null);
								}
								if (dbService.saveOrUpdate(indiv)) {
									flag = "success";
									dbService.saveOrUpdate(main);
									mainReq.setRequeststatus(requeststatus);
									mainReq.setApproverremarks(approverremarks);
									mainReq.setApprovedby(UserUtil.securityService.getUserName());
									mainReq.setDateapprove(new Date());
									dbService.saveOrUpdate(mainReq);
								}
							}
						}
					}
				}

				/*********************** Contact Details ***********************/
				if (changetype.equals("3")) {
					// Individual
					if (mainReq.getCustomertype().equals("1")) {
						indiv = (Tbcifindividual) dbService
								.executeUniqueHQLQueryMaxResultOne("FROM Tbcifindividual WHERE cifno=:cifno", params);
						if (indiv != null) {
							if (indivReq != null) {
								indiv.setCountrycodemobile(indivReq.getMobilecountrycode());
								indiv.setAreacodemobile(indivReq.getMobileareacode());
								indiv.setMobilenumber(indivReq.getMobilenumber());
								indiv.setCountrycodephone(indivReq.getHomecountrycode());
								indiv.setAreacodephone(indivReq.getHomeareacode());
								indiv.setHomephoneno(indivReq.getHomenumber());
								indiv.setFaxnumber(indivReq.getFaxnumber());
								indiv.setCountrycodefax(indivReq.getFaxcountrycode());
								indiv.setAreacodefax(indivReq.getFaxareacode());
								indiv.setFaxnumber(indivReq.getFaxnumber());
								indiv.setEmailaddress(indivReq.getEmailaddress());
								indiv.setContacttype1(indivReq.getContacttype1());
								indiv.setContactvalue1(indivReq.getContactvalue1());
								indiv.setContacttype2(indivReq.getContacttype2());
								indiv.setContactvalue2(indivReq.getContactvalue2());
								if (dbService.saveOrUpdate(indiv)) {
									flag = "success";
									mainReq.setRequeststatus(requeststatus);
									mainReq.setApproverremarks(approverremarks);
									mainReq.setApprovedby(UserUtil.securityService.getUserName());
									mainReq.setDateapprove(new Date());
									dbService.saveOrUpdate(mainReq);
								}
							}
						}
					}
					// Corporate
					if (mainReq.getCustomertype().equals("2") || mainReq.getCustomertype().equals("3")) {
						corp = (Tbcifcorporate) dbService
								.executeUniqueHQLQueryMaxResultOne("FROM Tbcifcorporate WHERE cifno=:cifno", params);
						corpReq = (Tbcorporaterequest) dbService.executeUniqueHQLQueryMaxResultOne(
								"FROM Tbcorporaterequest WHERE requestid=:requestid AND cifno=:cifno", params);
						if (corp != null) {
							if (corpReq != null) {
								corp.setCountrycodemobile(corpReq.getMobilecountrycode());
								corp.setAreacodemobile(corpReq.getMobileareacode());
								corp.setMobilenumber(corpReq.getMobilenumber());
								corp.setCountrycodephone(corpReq.getOfficecountrycode());
								corp.setAreacodephone(corpReq.getOfficeareacode());
								corp.setHomephoneno(corpReq.getOfficenumber());
								corp.setFaxnumber(corpReq.getFaxnumber());
								corp.setCountrycodefax(corpReq.getFaxcountrycode());
								corp.setAreacodefax(corpReq.getFaxareacode());
								corp.setFaxnumber(corpReq.getFaxnumber());
								corp.setEmailaddress(corpReq.getEmailaddress());
								corp.setMaincontact1(corpReq.getMaincontact1());
								corp.setWebsite(corpReq.getWebsite());
								if (dbService.saveOrUpdate(corp)) {
									flag = "success";
									mainReq.setRequeststatus(requeststatus);
									mainReq.setApproverremarks(approverremarks);
									mainReq.setApprovedby(UserUtil.securityService.getUserName());
									mainReq.setDateapprove(new Date());
									dbService.saveOrUpdate(mainReq);
								}
							}
						}

						// Other Contacts
						List<Tbothercontacts> otherCon = (List<Tbothercontacts>) dbService
								.executeListHQLQuery("FROM Tbothercontacts WHERE cifno=:cifno", params);
						if (otherCon != null) {
							for (Tbothercontacts a : otherCon) {
								dbService.delete(a);
							}
						}
						List<Tbothercontactrequest> otherConReq = (List<Tbothercontactrequest>) dbService
								.executeListHQLQuery(
										"FROM Tbothercontactrequest WHERE requestid=:requestid AND cifno=:cifno",
										params);
						if (otherConReq != null) {
							for (Tbothercontactrequest req : otherConReq) {
								Tbothercontacts c = new Tbothercontacts();
								c.setCifno(req.getCifno());
								c.setContactperson(req.getContactperson());
								c.setOtherdetails(req.getOtherdetails());
								c.setDirectnumber(req.getDirectnumber());
								c.setMobilenumber(req.getMobilenumber());
								c.setEmailaddress(req.getEmailaddress());
								c.setPosition(req.getPosition());
								dbService.save(c);
							}
							flag = "success";
						}

					} // end of corp
				}
			} // end of checking cifno / requestid
			else {
				System.out.println(">>>> Change CIF Details : Request ID or CIF No. is NULL!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return flag;
		}
		return flag;
	}

	@Override
	public String saveOrUpdateChangeDetails(Tbchangecifdetailsrequest ref) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		Tbchangecifdetailsrequest d = new Tbchangecifdetailsrequest();
		if (ref.getRequestid() != null) {
			params.put("requestid", ref.getRequestid());
			d = (Tbchangecifdetailsrequest) dbService
					.executeUniqueHQLQuery("FROM Tbchangecifdetailsrequest WHERE requestid=:requestid", params);
			d.setCifno(ref.getCifno());
			d.setFullname(ref.getFullname());
			d.setChangetype(ref.getChangetype());
			d.setRequestedby(UserUtil.securityService.getUserName());
			d.setDaterequested(new Date());
			d.setRequeststatus("1");
			d.setRemarks(ref.getRemarks());

			if (dbService.saveOrUpdate(d)) {
				System.out.println("UPDATE");
				return "update";
			}
		} else {
			ref.setRequestid(generateRequestID(ref.getChangetype()));
			ref.setRequestedby(UserUtil.securityService.getUserName());
			ref.setDaterequested(new Date());
			ref.setRequeststatus("1");
			if (dbService.saveOrUpdate(ref)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}

	@Override
	public String updateCIFIndividual(Tbcifindividual ref, Tbcifmain refmain, String changetype, String remarks) {
		System.out.println(">>> running updateCIFIndividual<<<");
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		Tbcifindividual d = new Tbcifindividual();
		Tbcifmain main = new Tbcifmain();
		Tbchangecifdetailsrequest req = new Tbchangecifdetailsrequest();
		String flag = "failed";
		System.out.println(">>> changetype - " + changetype);
		if (ref.getCifno() != null) {
			params.put("cifno", ref.getCifno());
			d = (Tbcifindividual) dbService.executeUniqueHQLQuery("FROM Tbcifindividual WHERE cifno=:cifno", params);
			main = (Tbcifmain) dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", params);
			if (changetype.equals("0")) {
				req.setChangeto(ref.getGender());
				req.setChangefrom(d.getGender());
				d.setGender(ref.getGender());
			} else if (changetype.equals("1")) {
				req.setChangeto(ref.getCivilstatus());
				req.setChangefrom(d.getCivilstatus());
				d.setCivilstatus(ref.getCivilstatus());
			} else if (changetype.equals("2")) {
				req.setChangeto(ref.getDateofbirth().toString());
				req.setChangefrom(d.getDateofbirth().toString());
				d.setDateofbirth(ref.getDateofbirth());
			} else if (changetype.equals("3")) {
				req.setChangeto(ref.getCountryofbirth() + " , " + ref.getStateofbirth() + " , " + ref.getCityofbirth());
				req.setChangefrom(d.getCountryofbirth() + " , " + d.getStateofbirth() + " , " + d.getCityofbirth());
				d.setCountryofbirth(ref.getCountryofbirth());
				d.setStateofbirth(ref.getStateofbirth());
				d.setCityofbirth(ref.getCityofbirth());
			} else if (changetype.equals("5")) {
				req.setChangeto(ref.getNationality());
				req.setChangefrom(d.getNationality());
				d.setNationality(ref.getNationality());
			} else if (changetype.equals("7")) {
				req.setChangeto(ref.getAcrnumber());
				req.setChangefrom(d.getAcrnumber());
				d.setAcrnumber(ref.getAcrnumber());
			} else if (changetype.equals("8")) {
				if (ref.getTin() != null) {
					params.put("tin", ref.getTin());
					Tbcifindividual tinIfExisting = (Tbcifindividual) dbService
							.executeUniqueHQLQuery("FROM Tbcifindividual where" + " tin=:tin", params);
					if (tinIfExisting != null) {
						flag = "existing tin";
						return flag;
					} else {
						req.setChangeto(ref.getTin());
						req.setChangefrom(d.getTin());
						d.setTin(ref.getTin());
					}
				}
			} else if (changetype.equals("9")) {
				req.setChangeto(ref.getSss());
				req.setChangefrom(d.getSss());
				d.setSss(ref.getSss());
			} else if (changetype.equals("10")) {
				req.setChangeto(ref.getGsis());
				req.setChangefrom(d.getGsis());
				d.setGsis(ref.getGsis());
			} else if (changetype.equals("11")) {
				req.setChangeto(
						ref.getCountrycodemobile() + " - " + ref.getAreacodemobile() + " - " + ref.getMobilenumber());
				req.setChangefrom(
						d.getCountrycodemobile() + " - " + d.getAreacodemobile() + " - " + d.getMobilenumber());
				d.setMobilenumber(ref.getMobilenumber());
				d.setCountrycodemobile(ref.getCountrycodemobile());
				d.setAreacodemobile(ref.getAreacodemobile());
			} else if (changetype.equals("12")) {
				req.setChangeto(
						ref.getCountrycodephone() + " - " + ref.getAreacodephone() + " - " + ref.getHomephoneno());
				req.setChangefrom(d.getCountrycodephone() + " - " + d.getAreacodephone() + " - " + d.getHomephoneno());
				d.setCountrycodephone(ref.getCountrycodephone());
				d.setAreacodephone(ref.getAreacodephone());
				d.setHomephoneno(ref.getHomephoneno());
			} else if (changetype.equals("13")) {
				req.setChangeto(ref.getCountrycodefax() + " - " + ref.getAreacodefax() + " - " + ref.getFaxnumber());
				req.setChangefrom(d.getCountrycodefax() + " - " + d.getAreacodefax() + " - " + d.getFaxnumber());
				d.setCountrycodefax(ref.getCountrycodefax());
				d.setAreacodefax(ref.getAreacodefax());
				d.setFaxnumber(ref.getFaxnumber());
			} else if (changetype.equals("14")) {
				req.setChangeto(ref.getEmailaddress());
				req.setChangefrom(d.getEmailaddress());
				d.setEmailaddress(ref.getEmailaddress());
			} else if (changetype.equals("15")) {
				req.setChangeto(ref.getContacttype1());
				req.setChangefrom(d.getContacttype1());
				d.setContacttype1(ref.getContacttype1());
			} else if (changetype.equals("16")) {
				req.setChangeto(ref.getContactvalue1());
				req.setChangefrom(d.getContactvalue1());
				d.setContactvalue1(ref.getContactvalue1());
			} else if (changetype.equals("17")) {
				req.setChangeto(ref.getContacttype2());
				req.setChangefrom(d.getContacttype2());
				d.setContacttype2(ref.getContacttype2());
			} else if (changetype.equals("18")) {
				req.setChangeto(ref.getContactvalue2());
				req.setChangefrom(d.getContactvalue2());
				d.setContactvalue2(ref.getContactvalue2());
			} else if (changetype.equals("19")) {
				req.setChangeto(fullAddress(ref.getStreetno1(), ref.getSubdivision1(), ref.getBarangay1(),
						ref.getCity1(), ref.getProvince1(), ref.getCountry1(), ref.getPostalcode1()));
				req.setChangefrom(fullAddress(d.getStreetno1(), d.getSubdivision1(), d.getBarangay1(), d.getCity1(),
						d.getProvince1(), d.getCountry1(), d.getPostalcode1()));
				d.setStreetno1(ref.getStreetno1());
				d.setSubdivision1(ref.getSubdivision1());
				d.setCountry1(ref.getCountry1());
				d.setProvince1(ref.getProvince1());
				d.setCity1(ref.getCity1());
				d.setBarangay1(ref.getBarangay1());
				d.setPostalcode1(ref.getPostalcode1());
				d.setHomeownership1(ref.getHomeownership1());
				d.setOccupiedsince1(ref.getOccupiedsince1());
			} else if (changetype.equals("20")) {
				req.setChangeto(fullAddress(ref.getStreetno2(), ref.getSubdivision2(), ref.getBarangay2(),
						ref.getCity2(), ref.getProvince2(), ref.getCountry2(), ref.getPostalcode2()));
				req.setChangefrom(fullAddress(d.getStreetno2(), d.getSubdivision2(), d.getBarangay2(), d.getCity2(),
						d.getProvince2(), d.getCountry2(), d.getPostalcode2()));
				d.setStreetno2(ref.getStreetno2());
				d.setSubdivision2(ref.getSubdivision2());
				d.setCountry2(ref.getCountry2());
				d.setProvince2(ref.getProvince2());
				d.setCity2(ref.getCity2());
				d.setBarangay2(ref.getBarangay2());
				d.setPostalcode2(ref.getPostalcode2());
				d.setHomeownership2(ref.getHomeownership2());
				d.setOccupiedsince2(ref.getOccupiedsince2());
			} else if (changetype.equals("21")) {
				req.setChangeto(ref.getMailingaddress());
				req.setChangefrom(d.getMailingaddress());
				d.setMailingaddress(ref.getMailingaddress());
			} else if (changetype.equals("22")) {
				req.setChangeto(ref.getSpouselastname() + ", " + ref.getSpousefirstname() + " "
						+ ref.getSpousemiddlename() + " " + ref.getSpousesuffix());
				req.setChangefrom(d.getSpouselastname() + ", " + d.getSpousefirstname() + " " + d.getSpousemiddlename()
						+ " " + d.getSpousesuffix());
				d.setSpousefirstname(ref.getSpousefirstname());
				d.setSpousemiddlename(ref.getSpousemiddlename());
				d.setSpouselastname(ref.getSpouselastname());
				d.setSpousesuffix(ref.getSpousesuffix());
			} else if (changetype.equals("23")) {
				req.setChangeto(ref.getFatherlastname() + ", " + ref.getFatherfirstname() + " "
						+ ref.getFathermiddlename() + " " + ref.getFathersuffix());
				req.setChangefrom(d.getFatherlastname() + ", " + d.getFatherfirstname() + " " + d.getFathermiddlename()
						+ " " + d.getFathersuffix());
				d.setFatherfirstname(ref.getFatherfirstname());
				d.setFathermiddlename(ref.getFathermiddlename());
				d.setFatherlastname(ref.getFatherlastname());
				d.setFathersuffix(ref.getFathersuffix());
			} else if (changetype.equals("24")) {
				req.setChangeto(ref.getMothermaidenmname());
				req.setChangefrom(d.getMothermaidenmname());
				d.setMothermaidenmname(ref.getMothermaidenmname());
			} else if (changetype.equals("28")) {
				req.setChangeto(ref.getDualcitizen().toString());
				req.setChangefrom(d.getDualcitizen().toString());
				d.setDualcitizen(ref.getDualcitizen());
			} else if (changetype.equals("32")) {
				req.setChangeto(ref.getLastname());
				req.setChangefrom(d.getLastname());
				d.setLastname(ref.getLastname());
			
				Tbcifmain m = (Tbcifmain) dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", params);
				/*m.setFullname(d.getLastname() + ", " + d.getFirstname()
						+ (d.getMiddlename().trim().length() > 0 ? " " + d.getMiddlename() : ""));*/
				
				// suffix
				String suf = d.getSuffix();
				String middlename = "";
				// middle name
				if (ref.getMiddlename() != null && !ref.getMiddlename().equals("")) {
					middlename = ref.getMiddlename();
				}
				m.setFullname(ref.getLastname() + ", " + ref.getFirstname() + " " + suf + " " + middlename);
				
				// 02.02.2023
				m.setFullname(m.getFullname().trim().replaceAll(" +", " "));
				
				dbService.saveOrUpdate(m);
				//System.out.println(">>> m.fullname - " + m.getFullname());
				/*System.out.println(">>> d.getLastname() - " + d.getLastname());
				System.out.println(">>> ref.getLastname() - " + ref.getLastname());*/
				//System.out.println(">>> 32");
				
				/*String fullname = d.getLastname() + ", " + d.getFirstname()
				+ (d.getMiddlename().trim().length() > 0 ? " " + d.getMiddlename() : "");*/
				String fullname = m.getFullname();
				
				//System.out.println(">>> fullname - " + fullname);
				//DEPOSIT ACCOUNTS
				dbServiceCOOP.executeUpdate("update TBDEPOSITCIF set cifname = "+"'"+fullname+"'"+" where cifno=:cifno", params);
				dbServiceCOOP.executeUpdate("update a set a.AccountName ="+"'"+fullname+"'"+" " + 
						" from  TBDEPOSIT a " + 
						" left join TBDEPOSITCIF b ON a.AccountNo = b.accountno" + 
						" where b.cifno=:cifno", params);
				//LOANS
				dbServiceCOOP.executeUpdate("update TBLOANS set fullname = "+"'"+fullname+"'"+" where principalno=:cifno", params);
				dbServiceCOOP.executeUpdate("update TBACCOUNTINFO set name = "+"'"+fullname+"'"+" where clientid=:cifno", params);
				dbServiceCOOP.executeUpdate("update TBLSTAPP set cifname = "+"'"+fullname+"'"+" where cifno=:cifno", params);
				dbServiceCOOP.executeUpdate("update TBLSTAPP set lastname = "+"'"+ref.getLastname()+"'"+" where cifno=:cifno", params);
				
				
				
			}
			else if (changetype.equals("33")) {
				req.setChangeto(ref.getMiddlename());
				req.setChangefrom(d.getMiddlename());
				d.setMiddlename(ref.getMiddlename());
				Tbcifmain m = (Tbcifmain) dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", params);
				/*m.setFullname(d.getLastname() + ", " + d.getFirstname()
						+ (d.getMiddlename().trim().length() > 0 ? " " + d.getMiddlename() : ""));*/
				
				// suffix
				String suf = d.getSuffix();
				String middlename = "";
				// middle name
				if (ref.getMiddlename() != null && !ref.getMiddlename().equals("")) {
					middlename = ref.getMiddlename();
				}
				m.setFullname(ref.getLastname() + ", " + ref.getFirstname() + " " + suf + " " + middlename);

				// 02.02.2023
				m.setFullname(m.getFullname().trim().replaceAll(" +", " "));
				
				dbService.saveOrUpdate(m);
				
				String fullname = m.getFullname();
				//UPDATE DEPOSIT ACCOUNTS NAME
				dbServiceCOOP.executeUpdate("update TBDEPOSITCIF set cifname = "+"'"+fullname+"'"+" where cifno=:cifno", params);
				dbServiceCOOP.executeUpdate("update a set a.AccountName ="+"'"+fullname+"'"+" " + 
						" from  TBDEPOSIT a " + 
						" left join TBDEPOSITCIF b ON a.AccountNo = b.accountno" + 
						" where b.cifno=:cifno", params);
				//UPDATE LOANS ACCOUNTS NAME
				dbServiceCOOP.executeUpdate("update TBLOANS set fullname = "+"'"+fullname+"'"+" where principalno=:cifno", params);
				dbServiceCOOP.executeUpdate("update TBACCOUNTINFO set name = "+"'"+fullname+"'"+" where clientid=:cifno", params);
				dbServiceCOOP.executeUpdate("update TBLSTAPP set cifname = "+"'"+fullname+"'"+" where cifno=:cifno", params);
			}
			else if (changetype.equals("34")) {
				if(refmain.getCifstatus().equals("4")) {
					Integer loansDeposit = (Integer) dbServiceCOOP.executeUniqueSQLQuery("select count(*) from TBDEPOSITCIF where cifno=:cifno",params);
					Integer loansCount = (Integer) dbServiceCOOP.executeUniqueSQLQuery("select count(*) from TBLSTAPP where cifno=:cifno",params);
					Integer loansLMS = (Integer) dbServiceCOOP.executeUniqueSQLQuery("select count(*) from TBLOANS where slaidno=:cifno ",params);
					if(loansDeposit.equals(0) || loansCount.equals(0) || loansLMS.equals(0)) {
						req.setChangeto(refmain.getCifstatus());
						req.setChangefrom(main.getCifstatus());
						Tbcifmain m = (Tbcifmain) dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", params);
						m.setCifstatus(refmain.getCifstatus());
						dbService.saveOrUpdate(m);
					}else {
						flag = "cancel";
						return flag;
					}
				}else {
					req.setChangeto(refmain.getCifstatus());
					req.setChangefrom(main.getCifstatus());
					Tbcifmain m = (Tbcifmain) dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", params);
					m.setCifstatus(refmain.getCifstatus());
					dbService.saveOrUpdate(m);
				}
			}else if (changetype.equals("35")) {
				req.setChangeto(ref.getFirstname());
				req.setChangefrom(d.getFirstname());
				d.setFirstname(ref.getFirstname());
				Tbcifmain m = (Tbcifmain) dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", params);
				/*m.setFullname(d.getLastname() + ", " + d.getFirstname()
						+ (d.getMiddlename().trim().length() > 0 ? " " + d.getMiddlename() : ""));*/
				
				// suffix
				String suf = d.getSuffix();
				String middlename = "";
				// middle name
				if (ref.getMiddlename() != null && !ref.getMiddlename().equals("")) {
					middlename = ref.getMiddlename();
				}
				m.setFullname(ref.getLastname() + ", " + ref.getFirstname() + " " + suf + " " + middlename);
				// 02.02.2023
				m.setFullname(m.getFullname().trim().replaceAll(" +", " "));
				
				dbService.saveOrUpdate(m);
				
				/*String fullname = d.getLastname() + ", " + d.getFirstname()
				+ (d.getMiddlename().trim().length() > 0 ? " " + d.getMiddlename() : "");*/
				String fullname = m.getFullname();
				//DEPOSIT ACCOUNTS
				dbServiceCOOP.executeUpdate("update TBDEPOSITCIF set cifname = "+"'"+fullname+"'"+" where cifno=:cifno", params);
				dbServiceCOOP.executeUpdate("update a set a.AccountName ="+"'"+fullname+"'"+" " + 
						" from  TBDEPOSIT a " + 
						" left join TBDEPOSITCIF b ON a.AccountNo = b.accountno" + 
						" where b.cifno=:cifno", params);
				//LOANS
				dbServiceCOOP.executeUpdate("update TBLOANS set fullname = "+"'"+fullname+"'"+" where principalno=:cifno", params);
				dbServiceCOOP.executeUpdate("update TBACCOUNTINFO set name = "+"'"+fullname+"'"+" where clientid=:cifno", params);
				dbServiceCOOP.executeUpdate("update TBLSTAPP set cifname = "+"'"+fullname+"'"+" where cifno=:cifno", params);
				dbServiceCOOP.executeUpdate("update TBLSTAPP set firstname = "+"'"+ref.getFirstname()+"'"+" where cifno=:cifno", params);
				
			}else if (changetype.equals("36")) {
				req.setChangeto(ref.getMembershiptype());
				req.setChangefrom(d.getMembershiptype());
				d.setMembershiptype(ref.getMembershiptype());
			}
			else if (changetype.equals("37")) {
				req.setChangeto(ref.getDatemembership().toString());
				req.setChangefrom(d.getDatemembership().toString());
				d.setDatemembership(ref.getDatemembership());
			}
			else if (changetype.equals("40")) {
				/*req.setChangeto(ref.getDatemembership().toString());
				req.setChangefrom(d.getDatemembership().toString());*/
				d.setBeneficiarytitle(ref.getBeneficiarytitle());
				d.setBeneficiarylastname(ref.getBeneficiarylastname());
				d.setBeneficiaryfirstname(ref.getBeneficiaryfirstname());
				d.setBeneficiarysuffix(ref.getBeneficiarysuffix());
				d.setBeneficiarymiddlename(ref.getBeneficiarymiddlename());
				d.setBeneficiarydateofbirth(ref.getBeneficiarydateofbirth());
				d.setRelationtoborrower(ref.getRelationtoborrower());
			}
			else if (changetype.equals("41")) {
				System.out.println(">>> uwian na<<<");
				req.setChangeto(ref.getSuffix());
				req.setChangefrom(d.getSuffix());
				d.setSuffix(ref.getSuffix());
				Tbcifmain m = (Tbcifmain) dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", params);
				/*m.setFullname(d.getLastname() + ", " + d.getFirstname()
						+ (d.getMiddlename().trim().length() > 0 ? " " + d.getMiddlename() : ""));*/
				
				// suffix
				String suf = ref.getSuffix(); // changes ref
				String middlename = "";
				// middle name
				if (ref.getMiddlename() != null && !ref.getMiddlename().equals("")) {
					middlename = ref.getMiddlename();
				}
				m.setFullname(ref.getLastname() + ", " + ref.getFirstname() + " " + suf + " " + middlename);

				// 02.02.2023
				m.setFullname(m.getFullname().trim().replaceAll(" +", " "));
				
				dbService.saveOrUpdate(m);
				
				String fullname = m.getFullname();
				
				//UPDATE DEPOSIT ACCOUNTS NAME
				dbServiceCOOP.executeUpdate("update TBDEPOSITCIF set cifname = "+"'"+fullname+"'"+" where cifno=:cifno", params);
				dbServiceCOOP.executeUpdate("update a set a.AccountName ="+"'"+fullname+"'"+" " + 
						" from  TBDEPOSIT a " + 
						" left join TBDEPOSITCIF b ON a.AccountNo = b.accountno" + 
						" where b.cifno=:cifno", params);
				//UPDATE LOANS ACCOUNTS NAME
				dbServiceCOOP.executeUpdate("update TBLOANS set fullname = "+"'"+fullname+"'"+" where principalno=:cifno", params);
				dbServiceCOOP.executeUpdate("update TBACCOUNTINFO set name = "+"'"+fullname+"'"+" where clientid=:cifno", params);
				dbServiceCOOP.executeUpdate("update TBLSTAPP set cifname = "+"'"+fullname+"'"+" where cifno=:cifno", params);
			}
			
			if (dbService.saveOrUpdate(d)) {
				System.out.println("UPDATE");
				req.setRequestid(generateRequestID(changetype));
				req.setRequestedby(UserUtil.securityService.getUserName());
				req.setDaterequested(new Date());
				req.setRequeststatus("1");
				req.setCifno(ref.getCifno());
				req.setChangetype(changetype);
				req.setRemarks(remarks);
				req.setFullname(
						d.getLastname() + ", " + d.getFirstname() + " " + d.getMiddlename() + " " + d.getSuffix());
				if (dbService.saveOrUpdate(req)) {
					flag = "update";
					return flag;
				}
			}
		} else {

			if (changetype.equals("8")) {
				if (ref.getTin() != null) {
					params.put("tin", ref.getTin());
					Tbcifindividual tinIfExisting = (Tbcifindividual) dbService
							.executeUniqueHQLQuery("FROM Tbcifindividual where" + " tin=:tin", params);
					if (tinIfExisting != null) {
						flag = "existing tin";
						return flag;
					} else {
						d.setTin(ref.getTin());
					}
				}
			}
			if (dbService.saveOrUpdate(ref)) {
				System.out.println("SAVE");
				flag = "success";
				return flag;
			}
		}
		return flag;
	}

	@Override
	public String updateCIFFatca(Tbfatca ref, String changetype, String remarks) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		Tbfatca d = new Tbfatca();
		Tbchangecifdetailsrequest req = new Tbchangecifdetailsrequest();
		if (ref.getCifno() != null) {
			params.put("cifno", ref.getCifno());
			d = (Tbfatca) dbService.executeUniqueHQLQuery("FROM Tbfatca WHERE cifno=:cifno", params);
			if (changetype.equals("25")) {
				req.setChangeto(ref.getQ1().toString());
				req.setChangefrom(d.getQ1().toString());
				d.setQ1(ref.getQ1());
			} else if (changetype.equals("26")) {
				req.setChangeto(ref.getQ2().toString());
				req.setChangefrom(d.getQ2().toString());
				d.setQ2(ref.getQ2());
			} else if (changetype.equals("27")) {
				req.setChangeto(ref.getQ3().toString());
				req.setChangefrom(d.getQ3().toString());
				d.setQ3(ref.getQ3());
			}

			if (dbService.saveOrUpdate(d)) {
				req.setRequestid(generateRequestID(changetype));
				req.setRequestedby(UserUtil.securityService.getUserName());
				req.setDaterequested(new Date());
				req.setRequeststatus("1");
				req.setCifno(ref.getCifno());
				req.setChangetype(changetype);
				req.setRemarks(remarks);
				System.out.println("UPDATE");
				if (dbService.saveOrUpdate(req)) {
					return "update";
				}

			}
		} else {

			if (dbService.saveOrUpdate(ref)) {
				System.out.println("SAVE");
				return "success";
			}
		}
		return "failed";
	}

	public static String fullAddress(String streetNumber, String subdivision, String barangay, String city,
			String province, String country, String postalCode) {
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
		if (postalCode != null && !postalCode.equals("")) {
			b.append(postalCode);
		}
		return b.toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbchangecifdetailsrequest> listOfChangeDetailsHistory(String cifno, String cifname) {
		DBService dbService = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbchangecifdetailsrequest> list = new ArrayList<Tbchangecifdetailsrequest>();
		try {
			params.put("cifno", cifno);
			params.put("cifname", cifname);
			if (cifno != null && cifname != null) {
				list = (List<Tbchangecifdetailsrequest>) dbService.executeListHQLQuery(
						"FROM Tbchangecifdetailsrequest where cifno=:cifno and fullname=:cifname", params);
			} else if (cifno != null) {
				list = (List<Tbchangecifdetailsrequest>) dbService
						.executeListHQLQuery("FROM Tbchangecifdetailsrequest where cifno=:cifno", params);
			} else if (cifname != null) {
				list = (List<Tbchangecifdetailsrequest>) dbService
						.executeListHQLQuery("FROM Tbchangecifdetailsrequest where fullname=:cifname", params);
			} else {
				list = (List<Tbchangecifdetailsrequest>) dbService.executeListHQLQuery("FROM Tbchangecifdetailsrequest",
						params);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ChangeCIFDetailsForm> listOfCIFChangeHistory(String cifno, String losLink, String cifLink) {
		DBService dbService = new DBServiceImplCIF();
		List<ChangeCIFDetailsForm> list = new ArrayList<ChangeCIFDetailsForm>();
		try {
			param.put("cifno", cifno);
			param.put("losLink", losLink);
			param.put("cifLink", cifLink);
			String myQuery = "SELECT a.requestid, a.cifno," + " (SELECT desc1 FROM " + losLink
					+ ".dbo.TBCODETABLE WHERE codename = 'CIFCHANGETYPE' AND codevalue = a.changetype) AS changetype,"
					+ " CASE" + " WHEN a.changetype = '0'" + "	THEN (SELECT desc1 FROM " + losLink
					+ ".dbo.TBCODETABLE WHERE codename = 'GENDER' AND codevalue = a.changefrom)"
					+ " WHEN a.changetype = '1'" + "	THEN (SELECT desc1 FROM " + losLink
					+ ".dbo.TBCODETABLE WHERE codename = 'CIVILSTATUS' AND codevalue = a.changefrom)"
					+ " WHEN a.changetype = '34' THEN (SELECT processname FROM " + losLink + ".dbo.TBWORKFLOWPROCESS WHERE processid = a.changefrom)"
					+ "	ELSE a.changefrom" + " END AS changefrom," + " CASE" + " WHEN a.changetype = '0'"
					+ "	THEN (SELECT desc1 FROM " + losLink
					+ ".dbo.TBCODETABLE WHERE codename = 'GENDER' AND codevalue = a.changeto)"
					+ " WHEN a.changetype = '1'" + "	THEN (SELECT desc1 FROM " + losLink
					+ ".dbo.TBCODETABLE WHERE codename = 'CIVILSTATUS' AND codevalue = a.changeto)"
					+ " WHEN a.changetype = '34' THEN (SELECT processname FROM " + losLink + ".dbo.TBWORKFLOWPROCESS WHERE processid = a.changeto)" 
					+ " ELSE a.changeto"
					+ " END AS changeto," + " a.requestedby, a.daterequested, a.remarks" + " FROM " + cifLink
					+ ".dbo.Tbchangecifdetailsrequest a WHERE a.cifno = :cifno";
			list = (List<ChangeCIFDetailsForm>) dbService.execSQLQueryTransformer(myQuery, param,
					ChangeCIFDetailsForm.class, 1);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return list;
	}
}
