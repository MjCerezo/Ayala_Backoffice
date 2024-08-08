package com.etel.accessrights;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.cifsdb.data.Tbchangecifdetailsrequest;
import com.cifsdb.data.Tbcifmain;
import com.cifsdb.data.Tbteams;
import com.coopdb.data.Tbaccessrights;
import com.coopdb.data.TbaccessrightsId;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbmember;
import com.coopdb.data.Tbuser;
import com.coopdb.data.Tbuserroles;
import com.coopdb.data.Tbworkflowprocess;
import com.etel.accessrights.forms.LAAccessRightsForm;
import com.etel.accessrights.forms.ReadOnlyOrDisable;
import com.etel.accessrights.forms.RequestForm;
import com.etel.accessrights.forms.StatusAndRoles;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.dataentry.FullDataEntryService;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.defaultusers.forms.DefaultUsers;
import com.etel.facade.SecurityServiceImpl;
import com.etel.forms.UserForm;
import com.etel.role.forms.AccessRightsForm;
import com.etel.security.forms.TBRoleForm;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class AccessRightsServiceImpl implements AccessRightsService {

	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private DBService dbService = new DBServiceImpl();

	@Override
	public String addAccessRights(AccessRightsForm form) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			Tbaccessrights accessright = new Tbaccessrights();
			TbaccessrightsId aID = new TbaccessrightsId();
			if (form.getAccesstype() == null) {
				if (form.getModulename().equals("ROLE")) {
					aID.setModulename(form.getModulename());
					aID.setAccessname(form.getModulename() + "_" + form.getSubmodulename());
				}
			} else {
				if (form.getAccesstype().equals("PG")) {
					if (form.getModulename() != null) {
						aID.setModulename(form.getModulename());
						aID.setAccessname(form.getModulename() + "_" + form.getAccesstype());
					}
				} else {
					if (form.getModulename() != null && form.getAccesstype() != null) {
						aID.setModulename(form.getModulename());
						aID.setAccessname(
								form.getModulename() + "_" + form.getSubmodulename() + "_" + form.getAccesstype());
					}
				}
			}
			accessright.setId(aID);
			accessright.setAccesstype(form.getAccesstype());
			accessright.setCreateddate(new Date());
			if (form.getAccesstype() != null && form.getModulename() != null) {
				if (form.getModulename().equals("NAV")) {
					if (form.getAccesstype().equals("PAR")) {
						form.setDescription(form.getDescription().replace(" ", "_"));
						accessright.setNavorder(form.getNavorder());

					}
					if (form.getAccesstype().equals("CH")) {
						form.setDescription(form.getDescription().replace(" ", "_"));
						form.setNavparent(form.getNavparent().replace(" ", "_"));
						form.setSubmodulename(form.getSubmodulename());
						accessright.setNavorder(form.getParentnavorder() + "-" + form.getNavorder());

						// form.setSubmodulename(form.getParentsubmodulename()+"_"+form.getSubmodulename());
					}
				}
			}
			accessright.setSubmodulename(form.getSubmodulename());
			accessright.setDescription(form.getDescription());
			accessright.setCreatedby(secservice.getUserName());
			accessright.setNavparent(form.getNavparent());
			accessright.setParentsubmodulename(form.getParentsubmodulename());
			if (dbService.save(accessright)) {
				flag = "success";
				updateProjectSecurityXML();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String deleteAccessRights(String accessname) {
		// TODO DELETE SELECTED ACCESS RIGHTS
		String result = null;
		Tbaccessrights access = new Tbaccessrights();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("accessname", accessname);
		try {
			access = (Tbaccessrights) dbService
					.executeUniqueHQLQuery("FROM Tbaccessrights a where a.id.accessname =:accessname", params);
			if (access == null) {
				result = "failed";
			} else {
				dbService.delete(access);
				result = "success";
				updateProjectSecurityXML();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccessRightsForm> searchAccess(String parameter) {
		// TODO WILD SEARCH FROM TB ACCESSRIGHTS BY A PARAMETER
		List<AccessRightsForm> accesslist = new ArrayList<AccessRightsForm>();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("parameter", "%" + parameter + "%");
		try {
			List<Tbaccessrights> access = (List<Tbaccessrights>) dbService.executeListHQLQuery(
					"FROM Tbaccessrights a where a.id.accessname LIKE :parameter OR a.id.modulename LIKE :parameter OR a.submodulename LIKE :parameter OR a.accesstype LIKE :parameter OR a.description LIKE :parameter ",
					params);
			for (Tbaccessrights ar : access) {
				AccessRightsForm form = new AccessRightsForm();
				form.setModulename(ar.getId().getModulename());
				form.setAccessname(ar.getId().getAccessname());
				form.setSubmodulename(ar.getSubmodulename());
				form.setAccesstype(ar.getAccesstype());
				form.setDescription(ar.getDescription());
				accesslist.add(form);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accesslist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccessRightsForm> searchAccessWithModulename(String parameter, String modulename) {
		// TODO WILD SEARCH FROM TB ASCCESSRIGHTS BY CODENAME AND APARAMETER
		List<AccessRightsForm> accesslist = new ArrayList<AccessRightsForm>();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("parameter", "%" + parameter + "%");
		params.put("modulename", modulename);

		try {
			List<Tbaccessrights> access = (List<Tbaccessrights>) dbService.executeListHQLQuery(
					"FROM Tbaccessrights a where a.id.modulename =:modulename AND (a.id.accessname LIKE :parameter OR a.submodulename LIKE :parameter OR a.accesstype LIKE :parameter OR a.description LIKE :parameter)",
					params);
			for (Tbaccessrights ar : access) {
				AccessRightsForm form = new AccessRightsForm();
				form.setModulename(ar.getId().getModulename());
				form.setAccessname(ar.getId().getAccessname());
				form.setSubmodulename(ar.getSubmodulename());
				form.setAccesstype(ar.getAccesstype());
				form.setDescription(ar.getDescription());
				accesslist.add(form);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return accesslist;
	}

	@Override
	public String updateAccessRights(AccessRightsForm form) {
		// TODO EDIT ACCESS RIGHT RECORD
		String flag = "failed";
		Map<String, Object> params = HQLUtil.getMap();
		params.put("accessname", form.getAccessname());
		params.put("modulename", form.getModulename());
		try {
			Tbaccessrights access = (Tbaccessrights) dbService.executeUniqueHQLQuery(
					"FROM Tbaccessrights a WHERE a.id.accessname=:accessname AND a.id.modulename=:modulename", params);
			if (access != null) {
				ObjectMapper mapper = new ObjectMapper();
				System.out.println(mapper.writeValueAsString(access));
//				if (form.getAccesstype() == null) {
//					access.getId().setAccessname(form.getModulename() + "_" + form.getSubmodulename());
//				} else if (form.getSubmodulename() == null) {
//					access.getId().setAccessname(form.getModulename() + "_" + form.getAccesstype());
//				} else if (form.getSubmodulename() != null & form.getAccesstype() != null) {
//					access.getId().setAccessname(
//							form.getModulename() + "_" + form.getSubmodulename() + "_" + form.getAccesstype());
//				}
//				access.getId().setModulename(form.getModulename());
//				access.setSubmodulename(form.getSubmodulename());
//				access.setAccesstype(form.getAccesstype());
//				access.setDescription(form.getDescription());
//				access.setUpdatedby(secservice.getUserName());
//				access.setDateupdated(new Date());
//				if (dbService.saveOrUpdate(access)) {
//					flag = "success";
//				}
			} else {
				flag = "fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String updateProjectSecurityXML() {
		List<Tbaccessrights> list = new ArrayList<Tbaccessrights>();
		try {
			list = (List<Tbaccessrights>) dbService
					.executeListHQLQuery("FROM Tbaccessrights ORDER BY id.accessname DESC", null);
			String realpath = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("WEB-INF");
			String filepath = realpath + "\\project-security.xml";
			File fXmlFile = new File(filepath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			NodeList mainList = doc.getElementsByTagName("list");
			Node firstNode = (Node) mainList.item(0);
			Element firstElement = (Element) firstNode;
			// XXX collection of nodes to delete XXX
			Node node = mainList.item(0);
			NodeList childList = node.getChildNodes();
			// Looking through all children nodes
			for (int x = 1; x < childList.getLength(); x++) {
				Node child = childList.item(x);
				firstElement.removeChild(child);
			}
			if (firstElement.getElementsByTagName("value").getLength() != 0) {
				if (list == null) {
				} else {
					for (Tbaccessrights tb : list) {
						Text a = doc.createTextNode(tb.getId().getAccessname());
						Element p = doc.createElement("value");
						p.appendChild(a);
						((NodeList) firstNode).item(0).getParentNode().insertBefore(p, ((NodeList) firstNode).item(0));
						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						transformer.setOutputProperty(OutputKeys.INDENT, "yes");
						StreamResult result = new StreamResult(new StringWriter());
						DOMSource source = new DOMSource(doc);
						transformer.transform(source, result);
						String xmlOutput = result.getWriter().toString();
						// System.out.println(xmlOutput);
						fXmlFile.delete();
						File fnew = new File(filepath);
						FileWriter f2 = new FileWriter(fnew, false);
						f2.write(xmlOutput);
						f2.close();
					}
				}
			} else {
				if (list == null) {
				} else {
					for (Tbaccessrights tb : list) {
						Text a = doc.createTextNode(tb.getId().getAccessname());
						Element p = doc.createElement("value");
						p.appendChild(a);
						((NodeList) firstNode).item(0).getParentNode().insertBefore(p, ((NodeList) firstNode).item(0));
						Transformer transformer = TransformerFactory.newInstance().newTransformer();
						transformer.setOutputProperty(OutputKeys.INDENT, "yes");
						StreamResult result = new StreamResult(new StringWriter());
						DOMSource source = new DOMSource(doc);
						transformer.transform(source, result);
						String xmlOutput = result.getWriter().toString();
						// System.out.println(xmlOutput);
						fXmlFile.delete();
						File fnew = new File(filepath);
						FileWriter f2 = new FileWriter(fnew, false);
						f2.write(xmlOutput);
						f2.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}

	@Override
	public String getOfficer() {
		String flag = "";
		Tbteams team = new Tbteams();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("acctofficer", secservice.getUserName());

		try {
			Tbuser Tbuser = (Tbuser) dbsrvc.executeUniqueHQLQuery("FROM Tbuser WHERE username=:acctofficer", params);
			if (Tbuser.getTeamcode() != null) {
				params.put("teamcode", Tbuser.getTeamcode());
				team = (Tbteams) dbsrvc.executeUniqueHQLQuery(
						"FROM Tbteams WHERE teamcode=:teamcode AND officer=:acctofficer AND isofficeravailable='1'",
						params);
				if (team != null) {

					flag = "OFFICER AVAILABLE";

				} else {
					team = (Tbteams) dbsrvc.executeUniqueHQLQuery(
							"FROM Tbteams WHERE teamcode=:teamcode AND backupofficer=:acctofficer AND isofficeravailable='0'",
							params);
					if (team != null) {
						flag = "BACKUP OFFICER AVAILABLE";
					} else {
						flag = "Error";
					}

				}
				// System.out.println(flag);
				/*
				 * else if(team==null){ team = (Tbteams)dbsrvc.executeUniqueHQLQuery(
				 * "FROM Tbteams WHERE backupofficer=:officer AND isofficeravailable = '0'" ,
				 * params);
				 * 
				 * flag = "BACKUP OFFICER"; } System.out.println(flag+ ">> FLAG	");
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return flag;
	}

	@Override
	public LAAccessRightsForm getLoanApplicationAccess(String appno) {
		LAAccessRightsForm form = new LAAccessRightsForm();
		DefaultUsers d = new DefaultUsers();
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		params.put("username", username);
		try {
			if (appno != null) {
				params.put("appno", appno);
				Tblstapp app = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
				Tbuser user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
//				Tbteams team = new Tbteams();
//				params.put("teamcode", user.getTeamcode());
//				if (user.getTeamcode() != null) {
//					team = (Tbteams) dbService.executeUniqueHQLQuery("FROM Tbteams WHERE teamcode=:teamcode", params);
//				}

				if (app != null) {
					if (app.getCompanycode() != null) {
						d = new DefaultUsers(app.getCoopcode());
					}
					params.put("workflowid", app.getApplicationtype());
					params.put("sequenceno", app.getApplicationstatus());
					Tbworkflowprocess pflow = (Tbworkflowprocess) dbService.executeUniqueHQLQuery(
							"FROM Tbworkflowprocess a WHERE a.id.workflowid=:workflowid AND a.id.sequenceno=:sequenceno",
							params);
					if (pflow != null) {
						form.setForEncoding(pflow.getTabencoding());
						form.setForInvestigationAndAppraisal(pflow.getTabinvestigationandappraisal());
						form.setForEvaluation(pflow.getTabevaluation());
						form.setForRecommendation(pflow.getTabrecommendation());
						form.setForCreditApproval(pflow.getTabcreditapproval());
						form.setForClientAcceptance(pflow.getTabclientacceptance());
						form.setForDocumentInsurance(pflow.getTabdocumentinsurance());
						form.setForReleasingApproval(pflow.getTabreleasingapproval());
						form.setForBookingAndReleasing(pflow.getTabbookandreleasing());
						form.setBooked(pflow.getTabbooked());
						form.setBookedDocPending(pflow.getTabbookeddocpending());
						form.setCancelled(pflow.getTabcancelled());
						form.setRejected(pflow.getTabrejected());
						form.setHistory(pflow.getTabhistory());
						form.setNotes(pflow.getTabnotes());
					}
					Integer status = app.getApplicationstatus();
					// For Encoding
					if (status == 1) {
//						System.out.println("FEED"); Commented by Ced 6-21-2021
						form.setMainBtnPanel(true);
						form.setTablayer("layerEncoding");
						if (app.getCreatedby().equals(username)) {
							form.setDoneEncdngBtn(false);
							form.setSubmitBtn(true);
							form.setSaveBtn(true);
							form.setReadOnly(false);

//							form.setSaveBtn(true);
//							form.setSubmitBtn(false);
//							form.setReadOnly(false);
						}
						// KEV 01-19-2021
						if (UserUtil.isUserHasARoleId("LOAN_APP_ENCODER")) {
							form.setSaveBtn(true);
							form.setSubmitBtn(true);
							form.setReadOnly(false);
						}

						else {
							if (app.getIsdoneencoding() != null) {
								if (app.getIsdoneencoding()) {
									form.setSubmitBtn(UserUtil.hasRole("ACCT_OFFICER"));
									form.setReturnBtn(UserUtil.hasRole("ACCT_OFFICER"));
								}
							}
							// Default - daniel 05.09.2019
							form.setReadOnly(true);
							form.setSaveBtn(false);
						}
//						if (user.getTeamcode() != null) {
//							if (team.getIsofficeravailable() && team.getOfficer().equals(username)
//									|| (team.getBackupofficer() != null && team.getBackupofficer().equals(username))) {
//								form.setSaveBtn(true);
//								form.setSubmitBtn(true);
//								form.setReadOnly(false);
//							}
//						}
					}

					// For Investigation & Appraisal
					if (status == 2) {
						form.setMainBtnPanel(true);
						form.setTablayer("layerInvstAppraisal");
						// Complete BI (Supervisor)
						if (app.getIsbicompleted() == null || app.getIsbicompleted() == false) {
							if (username.equals(d.getBisupervisor())) {
								form.setReturnBtn(true);
								form.setCompleteBiBtn(true);
							}
						}

						// Complete CI
						if (app.getIscicompleted() == null || app.getIscicompleted() == false) {
							if (username.equals(d.getCisupervisor())) {
								form.setReturnBtn(true);
								form.setCompleteCiBtn(true);
							}
						}
						if (app.getIsappraisalcompleted() == null || app.getIsappraisalcompleted() == false) {
							if (username.equals(d.getAppraisalsupervisor())) {
								form.setReturnBtn(true);
								form.setCompleteCaBtn(true);
							}
						}
						form.setReadOnly(true);
					}
					// For Evaluation
					if (status == 3) {
						form.setTablayer("layerEvaluation");
						form.setSubmitBtn(false);
					}
					// FOR APPROVAL
					if (status == 4) {
						form.setTablayer("layerEvaluation");
						form.setSubmitBtn(false);
					}
					// ACCEPTANCE
					if (status == 5) {
						form.setTablayer("layerClientAcceptance");
						form.setSubmitBtn(UserUtil.hasRole("ACCT_OFFICER") || app.getCreatedby().equals(username));
						form.setReadOnly(!UserUtil.hasRole("ACCT_OFFICER") && !app.getCreatedby().equals(username));
					}
					// FOR DOCUMENTATION & INSURANCE
					if (status == 6) {
						form.setTablayer("layerDocuStage");
						form.setMainBtnPanel(false);
						boolean docanalyst = UserUtil.hasRole("DOC_ANALYST");
						form.setSubmitBtn(docanalyst);
						form.setSaveBtn(docanalyst);
						form.setReadOnly(docanalyst ? false : true);
//						if (app.getCreatedby().equals(username)) {
//							form.setReturnBtn(true);
//							form.setSubmitBtn(true);
//							form.setReadOnly(false);
//						}
//						if (user.getTeamcode() != null) {
//							if (team.getIsofficeravailable() && team.getOfficer().equals(username)
//									|| (team.getBackupofficer() != null && team.getBackupofficer().equals(username))) {
//								form.setReturnBtn(true);
//								form.setSubmitBtn(true);
//								form.setReadOnly(false);
//							}
//						}
					}
					// FOR FOR BOOKING AND RELEASING
					if (status == 7) {
						form.setTablayer("layerBooking");
						// Default - daniel 05.10.2019
						form.setSubmitBtn(UserUtil.hasRole("BOOKING_OFFICER"));
						form.setReadOnly(!UserUtil.hasRole("BOOKING_OFFICER"));
					}
					// FOR RELEASING APPROVAL
					if (status == 8) {
//						form.setTablayer("layerBooking");
					}
					// FOR BOOKING AND RELEASING
					if (status == 9) {
//						form.setTablayer("layerBooking");
					}
					// BOOKED
					if (status == 10) {
//						form.setTablayer("layerClientAcceptance");
					}
//					System.out.println("FEED" + form.isMainBtnPanel()); // Commented by Ced
//					System.out.println("FEED" + form.isSaveBtn());
//					System.out.println("FEED" + form.isSubmitBtn());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String completeInvestigationAccess(String appno) {
		String flag = "donothing";
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		try {
			if (appno != null) {
				params.put("username", username);
				params.put("appno", appno);
				Tblstapp app = (Tblstapp) dbService
						.executeUniqueHQLQueryMaxResultOne("FROM Tblstapp WHERE appno=:appno", params);
				Integer count = (Integer) dbService
						.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbuserroles WHERE username=:username", params);
				if (count != null && count == 1) { // 1 role
					flag = "todashboard";
				} else if (count != null && count == 2) { // 2 roles
					// check userroles
					List<Tbuserroles> roles = (List<Tbuserroles>) dbService
							.executeListHQLQuery("FROM Tbuserroles WHERE id.username=:username", params);
					if (roles != null) {
						// check if with or without collateral appraisal
						Integer collateral = (Integer) dbService.executeUniqueSQLQuery(
								"SELECT COUNT(*) FROM Tbcolinvestigationinst WHERE appno=:appno", params);
						if (collateral != null && collateral > 0) {
							// BI or CI, with CA
							Boolean bi = false;
							Boolean ci = false;
							Boolean ca = false;
							for (Tbuserroles r : roles) {
								if (r.getId().getRoleid().equals("BI_SUPERVISOR")) {
									bi = true;
								} else if (r.getId().getRoleid().equals("CI_SUPERVISOR")) {
									ci = true;
								} else if (r.getId().getRoleid().equals("COLLATERAL_APP_SUPERVISOR")) {
									ca = true;
								}
							}
							if (bi || ca) {
								if ((app.getIsbicompleted() != null && app.getIsbicompleted())
										&& (app.getIsappraisalcompleted() != null && app.getIsappraisalcompleted())) {
									flag = "todashboard";
								}
							}
							if (ci || ca) {
								if ((app.getIscicompleted() != null && app.getIscicompleted())
										&& (app.getIsappraisalcompleted() != null && app.getIsappraisalcompleted())) {
									flag = "todashboard";
								}
							}
						} else {
							// BI or CI, no CA
							for (Tbuserroles r : roles) {
								String biorci = "";
								if (r.getId().getRoleid().equals("BI_SUPERVISOR")) {
									biorci = "bi";
								}
								if (r.getId().getRoleid().equals("CI_SUPERVISOR")) {
									biorci = "ci";
								}
								if (r.getId().equals("COLLATERAL_APP_SUPERVISOR")) {
									biorci = "ca";
								}
								if (biorci.equals("bi") || biorci.equals("ca")) {
									if (app.getIsbicompleted() != null && app.getIsbicompleted()) {
										flag = "todashboard";
									}
								}
								if (biorci.equals("ci") || biorci.equals("ca")) {
									if (app.getIscicompleted() != null && app.getIscicompleted()) {
										flag = "todashboard";
									}
								}
							}
						}
					}
				} else if (count != null && count == 3) { // 3 roles
															// BI_SUPERVISOR /
															// CI_SUPERVISOR /
															// COLLATERAL_APP_SUPERVISOR
					// check userroles
					List<Tbuserroles> roles = (List<Tbuserroles>) dbService
							.executeListHQLQuery("FROM Tbuserroles WHERE id.username=:username", params);
					if (roles != null) {
						// check if with or without collateral appraisal
						Integer collateral = (Integer) dbService.executeUniqueSQLQuery(
								"SELECT COUNT(*) FROM Tbcolinvestigationinst WHERE appno=:appno", params);
						if (collateral != null && collateral > 0) {
							// BI, CI, with CA
							if ((app.getIsbicompleted() != null && app.getIsbicompleted())
									&& (app.getIscicompleted() != null && app.getIscicompleted())
									&& (app.getIsappraisalcompleted() != null && app.getIsappraisalcompleted())) {
								flag = "todashboard";
							}
						} else {
							// BI, CI, no CA
							if ((app.getIsbicompleted() != null && app.getIsbicompleted())
									&& (app.getIscicompleted() != null && app.getIscicompleted())) {
								flag = "todashboard";
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
	public Boolean getDocumentInputLoansAccess(String appno) {
		// TODO Auto-generated method stub
		try {
			FullDataEntryService s = new FullDataEntryServiceImpl();
			Tblstapp a = s.getLstapp(appno);
			if (a != null) {
				if (a.getApplicationstatus() == 6) {
					// Documentation Stage
					return UserUtil.hasRole("DOC_ANALYST");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public StatusAndRoles getStatusAndRoles(String cifno) {
		// TODO Auto-generated method stub
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();

		StatusAndRoles sr = new StatusAndRoles();

		try {
			params.put("username", username);
			@SuppressWarnings("unchecked")
			List<Tbuserroles> roles = (List<Tbuserroles>) dbService
					.executeListHQLQuery("FROM Tbuserroles WHERE username=:username", params);
			if (roles != null) {
				for (Tbuserroles r : roles) {
					if (r.getId().getRoleid().equals("AMLA_ APPROVER")) {
						sr.setAmlaApprover(true);
					}
					if (r.getId().getRoleid().equals("AMLA_USER")) {
						sr.setAmlaUser(true);
					}
					if (r.getId().getRoleid().equals("BLACKLIST_ APPROVER")) {
						sr.setBlacklistApprover(true);
					}
					if (r.getId().getRoleid().equals("BLACKLIST_USER")) {
						sr.setBlacklistUser(true);
					}
					if (r.getId().getRoleid().equals("CIF_ENCODER")) {
						sr.setEncoder(true);
					}
					if (r.getId().getRoleid().equals("CIF_OFFICER")) {
						sr.setOfficer(true);
					}
					if (r.getId().getRoleid().equals("CIF_USER")) {
						sr.setUser(true);
					}
					if (r.getId().getRoleid().equals("SECAD")) {
						sr.setSecadmin(true);
					}
					if (r.getId().getRoleid().equals("SYSAD")) {
						sr.setSysadmin(true);
					}
				}
			}
			if (cifno != null) {

				params.put("cifno", cifno);
				Tbmember main = (Tbmember) dbService.executeUniqueHQLQuery("FROM Tbmember WHERE cifno=:cifno", params);

				if (main != null) {
					sr.setStatus(main.getMembershipstatus());
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sr;
	}

	@Override
	public ReadOnlyOrDisable disabledOrReadOnly(String cifno) {
		ReadOnlyOrDisable result = new ReadOnlyOrDisable();
		DBService dbService = new DBServiceImplCIF();
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		try {
			if (cifno != null) {
				params.put("cifno", cifno);
				Tbcifmain main = (Tbcifmain) dbService.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno",
						params);
				params.put("username", username);
				Tbuser user = (Tbuser) dbServiceCOOP.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username",
						params);
				
				com.etel.facade.SecurityService secsrvc = new SecurityServiceImpl();
				UserForm u = secsrvc.getUserAccount(username);

				if (main != null) {
					if (user != null) {
						
						//COMPANY
						if(main.getCustomertype().equals("1")) {
							
							if (main.getCifstatus().equals("1")) {
								//For Encoding
								boolean isEncoder = false;
								//for (TBRoleForm role : u.getRoles()) {
								//	if (role.getRoleid().equals("COMPANY_ENCODER"))
										isEncoder = true;
								//}
										
								if(isEncoder) {
									result.setReadOnly(false);
									result.setDisable(false);
									result.setDisableMainbtns(false);
									result.setShowing(true);
									result.setShowingMainbtns(true);
								}
								
							}else if (main.getCifstatus().equals("2")) {
								//For Approval of Cluster Head
								boolean isApprover = false;
								
								//for (TBRoleForm role : u.getRoles()) {
								//	if (role.getRoleid().equals("COMPANY_CLUSTER_HEAD_APPROVER"))
										isApprover = true;
								//}
								if (isApprover) {
									result.setReadOnly(true);
									result.setDisable(true);
									result.setDisableMainbtns(false);
									result.setShowing(true);
									result.setShowingMainbtns(true);
								}
								
							}else if (main.getCifstatus().equals("3")) {
								//For Approval
								boolean isApprover = false;
								
								//for (TBRoleForm role : u.getRoles()) {
								//	if (role.getRoleid().equals("COMPANY_APPROVER"))
										isApprover = true;
								//}
								if (isApprover) {
									result.setReadOnly(true);
									result.setDisable(true);
									result.setDisableMainbtns(false);
									result.setShowing(true);
									result.setShowingMainbtns(true);
								}
								
							}else if (main.getCifstatus().equals("4")) {
								//Approved
								result.setReadOnly(true);
								result.setDisable(true);
								result.setDisableMainbtns(false);
								result.setShowingMainbtns(true);
								
							}else if (main.getCifstatus().equals("5")) {
								//Cancelled
								result.setReadOnly(true);
								result.setDisable(true);
								result.setDisableMainbtns(false);
								result.setShowingMainbtns(true);
							}
						}
						
						//MEMBERSHIP
						else if (main.getCustomertype().equals("2")) {
							
							if (main.getCifstatus().equals("1")) {
								//For Encoding
								boolean isEncoder = false;
								//for (TBRoleForm role : u.getRoles()) {
								//	if (role.getRoleid().equals("MEMBERSHIP_ENCODER"))
										isEncoder = true;
								//}
										
								if(isEncoder) {
									result.setReadOnly(false);
									result.setDisable(false);
									result.setDisableMainbtns(false);
									result.setShowing(true);
									result.setShowingMainbtns(true);
								}
								
							}else if (main.getCifstatus().equals("2")) {
								//For Document Submission
								boolean isApprover = false;
								
								//for (TBRoleForm role : u.getRoles()) {
								//	if (role.getRoleid().equals("MEMBERSHIP_ENCODER"))
										isApprover = true;
								//}
								if (isApprover) {
									result.setReadOnly(false);
									result.setDisable(false);
									result.setDisableMainbtns(false);
									result.setShowing(true);
									result.setShowingMainbtns(true);
								}
								
							}else if (main.getCifstatus().equals("3")) {
								//For HR Review
								boolean isApprover = false;
								
								//for (TBRoleForm role : u.getRoles()) {
								//	if (role.getRoleid().equals("MEMBERSHIP_HR_APPROVER"))
										isApprover = true;
								//}
								if (isApprover) {
									result.setReadOnly(true);
									result.setDisable(true);
									result.setDisableMainbtns(false);
									result.setShowing(true);
									result.setShowingMainbtns(true);
								}
								
							}else if (main.getCifstatus().equals("4")) {
								//For Approval
								result.setReadOnly(true);
								result.setDisable(true);
								result.setDisableMainbtns(false);
								result.setShowingMainbtns(true);
								
							}else if (main.getCifstatus().equals("5")) {
								//Approved
								result.setReadOnly(true);
								result.setDisable(true);
								result.setDisableMainbtns(true);
								result.setShowingMainbtns(false);
								
							}else if (main.getCifstatus().equals("6")) {
								//Cancelled
								result.setReadOnly(true);
								result.setDisable(true);
								result.setDisableMainbtns(true);
								result.setShowingMainbtns(false);
							}
							
						}
						else {
							// return true
							result.setReadOnly(true);
							result.setDisable(true);
							result.setDisableMainbtns(true);
							result.setShowing(false);
							result.setShowingMainbtns(false);
							result.setEditMainButton(false);
						}
						
//						if (main.getCifstatus().equals("1")) {
//							//MAR - 07022024
//							boolean isEncoder = false;
//							//for (TBRoleForm role : u.getRoles()) {
//							//	if (role.getRoleid().equals("CIF_ENCODER"))
//									isEncoder = true;
//							//}
//							//
//							if (
//							// Ced CIF 09242020
////									main.getOriginatingteam() != null && 
////							main.getAssignedto() != null
//							isEncoder) {
//								// 1 FOR ENCODING
//								// teamcode && assignedto
//								// System.out.println("status "+ main.getCifstatus());
//								// System.out.println("assigned to"+ main.getAssignedto());
////								if (
//								// Ced CIF 09242020
////										main.getOriginatingteam().equals(user.getTeamcode()) && 
////								main.getAssignedto().equals(username)) {
//								result.setReadOnly(false);
//								result.setDisable(false);
//								result.setDisableMainbtns(false);
//								result.setShowing(true);
//								result.setShowingMainbtns(true);
//								// this is showing for edit button on main button for encoder only in approved
//								// status only
//								result.setEditMainButton(false);
////								}
//							}
//						} else if (main.getCifstatus().equals("2")) {
//							// 2 FOR APPROVAL
//							// Ced CIF 09242020
//
//							boolean isApprover = false;
//							for (TBRoleForm role : u.getRoles()) {
//								if (role.getRoleid().equals("CIF_APPROVER"))
//									isApprover = true;
//							}
//							if (isApprover) {
//								result.setReadOnly(true);
//								result.setDisable(true);
//								result.setDisableMainbtns(false);
//								result.setShowingMainbtns(true);
//							}
//////							System.out.println("status "+ main.getCifstatus());
////							System.out.println("officer available " + team.getIsofficeravailable());
////							System.out.println("officer " + team.getOfficer());
//////							System.out.println("backupofficer "+team.getBackupofficer());
////							System.out.println("user teamcode: " + user.getTeamcode());
////							System.out.println("main originatingteam: " + main.getOriginatingteam());
////							if (team.getIsofficeravailable()) {
////								if (user.getTeamcode().equals(main.getOriginatingteam())
////										&& team.getOfficer().equals(username)) {
////									result.setReadOnly(true);
////									result.setDisable(true);
////									result.setDisableMainbtns(false);
////									result.setShowingMainbtns(true);
////
////								}
////							} else {
////								if (team.getBackupofficer().equals(username)) {
////									result.setReadOnly(true);
////									result.setDisable(true);
////									result.setDisableMainbtns(false);
////									result.setShowingMainbtns(true);
////								}
////							}
//						} else if (main.getCifstatus().equals("3")) {
//							// 3 APPROVED
//							// Ced CIF 09242020
////							if (team.getIsofficeravailable()) {
////								if (team.getOfficer().equals(username)) {
////									result.setReadOnly(true);
////									result.setDisable(true);
////									result.setDisableMainbtns(false);
////									result.setShowingMainbtns(true);
////								}
////							} else {
////								if (team.getBackupofficer().equals(username)) {
//							result.setReadOnly(true);
//							result.setDisable(true);
//							result.setDisableMainbtns(false);
//							result.setShowingMainbtns(true);
////								}
////							}
//							// For encoder
//							if (main.getOriginatingteam().equals(user.getTeamcode())
//									&& main.getAssignedto().equals(username)) {
//								result.setReadOnly(true);
//								result.setDisable(true);
//								result.setDisableMainbtns(false);
//								result.setShowingMainbtns(false);
//								// this is showing for edit button on main button for encoder only in approved
//								// status only
//								result.setEditMainButton(true);
//							}
//
//						} else if (main.getCifstatus().equals("4")) {
//							// 4 FOR EDITING if approver
//							if (main.getOriginatingteam().equals(user.getTeamcode())
//									&& main.getAssignedto().equals(username)) {
//								result.setShowing(false);
//								result.setReadOnly(false);
//								result.setDisable(false);
//								result.setDisableMainbtns(false);
//								result.setShowingMainbtns(true);
//							}
//							// 10 - 03 - 17
//							// 4 FOR EDITING if encoder
//							params.put("username", username);
//							@SuppressWarnings("unchecked")
//							List<Tbuserroles> roles = (List<Tbuserroles>) dbServiceCOOP.executeListHQLQuery(
//									"FROM Tbuserroles WHERE username=:username AND roleid='CIF_ENCODER'", params);
//							boolean isEncoder = false;
//							if (roles != null && !roles.isEmpty()) {
//								isEncoder = true;
//							}
//							if (main.getOriginatingteam().equals(user.getTeamcode())
//									&& main.getAssignedto().equals(username) && isEncoder) {
//								result.setShowing(true);
//								result.setReadOnly(false);
//								result.setDisable(false);
//								result.setDisableMainbtns(false);
//								result.setShowingMainbtns(true);
//							}
//						} else if (main.getCifstatus().equals("0")) {
//							// 0 CANCELLED
//							// Ced CIF 09242020
////							if (team.getIsofficeravailable()) {
////								if (team.getOfficer().equals(username)) {
////									result.setReadOnly(true);
////									result.setDisable(true);
////									result.setDisableMainbtns(false);
////									result.setShowingMainbtns(true);
////								}
////							} else {
////								if (team.getBackupofficer().equals(username)) {
//							result.setReadOnly(true);
//							result.setDisable(true);
//							result.setDisableMainbtns(false);
//							result.setShowingMainbtns(true);
////								}
////							}
//						} else {
//							// return true
//							result.setReadOnly(true);
//							result.setDisable(true);
//							result.setDisableMainbtns(true);
//							result.setShowing(false);
//							result.setShowingMainbtns(false);
//							result.setEditMainButton(false);
//						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	// Renz
	@SuppressWarnings("unchecked")
	@Override
	public RequestForm getRequestAccessRight(String cifno, String requestid, String changetype) {
		RequestForm form = new RequestForm();
		DBService dbService = new DBServiceImpl();
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		params.put("username", username);
		boolean isEncoder = false;
		boolean isOfficer = false;
		String teamcodeTbteam = " ";
		try {
			List<Tbuserroles> userroles = (List<Tbuserroles>) dbService
					.executeListHQLQuery("FROM Tbuserroles WHERE id.username=:username", params);
			if (userroles != null) {
				for (Tbuserroles u : userroles) {
					if (u.getId().getRoleid().equalsIgnoreCase("CIF_ENCODER")) {
						isEncoder = true;
					}
					if (u.getId().getRoleid().equalsIgnoreCase("CIF_OFFICER")) {
						isOfficer = true;
					}
				}
			}

			if (isOfficer) {
				Tbteams team = (Tbteams) dbService.executeUniqueHQLQueryMaxResultOne(
						"FROM Tbteams WHERE officer=:username OR backupofficer=:username", params);
				if (team == null) {
					isOfficer = false;
				} else {
					if (team.getIsofficeravailable() != null && team.getIsofficeravailable() != false) {
						if (team.getOfficer() != null && team.getOfficer().equals(username)) {
							teamcodeTbteam = team.getTeamcode();
						} else {
							isOfficer = false;
						}
					} else {
						if (team.getBackupofficer() != null && team.getBackupofficer().equals(username)) {
							teamcodeTbteam = team.getTeamcode();
						} else {
							isOfficer = false;
						}
					}
				}
			}

			if (isEncoder || isOfficer) {
				params.put("tcodeTeam", teamcodeTbteam);
				String hqlRequest = " AND (teamcode=:tcodeTeam OR requestedby=:username)";
				if (isEncoder && isOfficer == false) {
					hqlRequest = " AND requestedby=:username";
				} else if (isEncoder == false && isOfficer) {
					hqlRequest = " AND teamcode=:tcodeTeam";
				}

				if (cifno != null && requestid != null && changetype != null) {
					params.put("cifno", cifno);
					params.put("requestid", requestid);
					params.put("changetype", changetype);
					Tbchangecifdetailsrequest checker = (Tbchangecifdetailsrequest) dbServiceCIF
							.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbchangecifdetailsrequest WHERE requestid=:requestid AND cifno=:cifno AND changetype=:changetype",
									params);
					if (checker == null) {
						if (isEncoder) {
							form.setBtnPanel(true);
							form.setSaveAsDraftBtn(true);
							form.setSubmitBtn(true);
							form.setEditBtn(true);
						}
					}
					Tbchangecifdetailsrequest req = (Tbchangecifdetailsrequest) dbServiceCIF
							.executeUniqueHQLQueryMaxResultOne(
									"FROM Tbchangecifdetailsrequest WHERE requestid=:requestid AND cifno=:cifno AND changetype=:changetype"
											+ hqlRequest,
									params);
					if (req != null) {
						// savedraft, submit, edit
						if (req.getRequeststatus().equals("0") || req.getRequeststatus().equals("5")) {
							if (isEncoder) {
								if (req.getRequestedby() != null && req.getRequestedby().equals(username)) {
									form.setBtnPanel(true);
									form.setSaveAsDraftBtn(true);
									form.setSubmitBtn(true);
									form.setEditBtn(true);
									form.setRejectBtn(true);
									return form;
								}
							}
						}
						// approve
						if (req.getRequeststatus().equals("1")) {
							if (isOfficer) {
								form.setBtnPanel(true);
								form.setApproveBtn(true);
								form.setReturnBtn(true);
								form.setRejectBtn(true);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@Override
	public LAAccessRightsForm getLASAccess(String appno, Integer applicationtype) {
		LAAccessRightsForm form = new LAAccessRightsForm();
//		DefaultUsers d = new DefaultUsers();
		Map<String, Object> params = HQLUtil.getMap();
		String username = UserUtil.securityService.getUserName();
		params.put("username", username);
		try {
			if (appno != null && applicationtype != null) {
				params.put("appno", appno);
				Tblstapp app = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
//				Tbuser user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
//				Tbteams team = new Tbteams();
//				params.put("teamcode", user.getTeamcode());
//				if (user.getTeamcode() != null) {
//					team = (Tbteams) dbService.executeUniqueHQLQuery("FROM Tbteams WHERE teamcode=:teamcode", params);
//				}

				if (app != null) {
//					if (app.getCompanycode() != null) {
//						d = new DefaultUsers(app.getCoopcode());
//					}
					params.put("workflowid", applicationtype);
					params.put("sequenceno", app.getApplicationstatus());
					Tbworkflowprocess pflow = (Tbworkflowprocess) dbService.executeUniqueHQLQuery(
							"FROM Tbworkflowprocess a WHERE a.id.workflowid=:workflowid AND a.id.sequenceno=:sequenceno",
							params);
					if (pflow != null) {
						form.setForEncoding(pflow.getTabencoding());
						form.setForInvestigationAndAppraisal(pflow.getTabinvestigationandappraisal());
						form.setForEvaluation(pflow.getTabevaluation());
						form.setForRecommendation(pflow.getTabrecommendation());
						form.setForCreditApproval(pflow.getTabcreditapproval());
						form.setForClientAcceptance(pflow.getTabclientacceptance());
						form.setForDocumentInsurance(pflow.getTabdocumentinsurance());
						form.setForReleasingApproval(pflow.getTabreleasingapproval());
						form.setForBookingAndReleasing(pflow.getTabbookandreleasing());
						form.setBooked(pflow.getTabbooked());
						form.setBookedDocPending(pflow.getTabbookeddocpending());
						form.setCancelled(pflow.getTabcancelled());
						form.setRejected(pflow.getTabrejected());
						form.setHistory(pflow.getTabhistory());
						form.setNotes(pflow.getTabnotes());
					}
					Integer status = app.getApplicationstatus();

					// For Encoding
					if (status == 1) {
						form.setMainBtnPanel(true);
//						form.setTablayer("layerEncoding");
						form.setMainBtnPanel(true);
						if (UserUtil.hasRole("LOAN_APP_ENCODER") || app.getCreatedby().equals(username)) {
							form.setSaveBtn(true);
							form.setSubmitBtn(true);
							form.setReadOnly(false);
							form.setCancelBtn(true);
						}

					}
					// FOR EVALUATION & APPROVAL
					if (status == 4) {
						// form.setTablayer("layerEvaluation");
						form.setMainBtnPanel(true);
						if (UserUtil.hasRole("LOAN_ACCT_OFFICER")) {
							form.setSubmitBtn(true);
							form.setReadOnly(false);
							form.setCancelBtn(true);
							form.setReturnBtn(true);
						}

					}

//					// ACCEPTANCE
//					if (status == 5) {
//						form.setTablayer("layerClientAcceptance");
//						form.setSubmitBtn(UserUtil.hasRole("ACCT_OFFICER") || app.getCreatedby().equals(username));
//						form.setReadOnly(!UserUtil.hasRole("ACCT_OFFICER") && !app.getCreatedby().equals(username));
//					}
//					// FOR DOCUMENTATION & INSURANCE
//					if (status == 6) {
//						form.setTablayer("layerDocuStage");
//						form.setMainBtnPanel(false);
//						boolean docanalyst = UserUtil.hasRole("DOC_ANALYST");
//						form.setSubmitBtn(docanalyst);
//						form.setSaveBtn(docanalyst);
//						form.setReadOnly(docanalyst ? false : true);
//					}
					// FOR RELEASING AND BOOKING
					if (status == 7) {
//						form.setTablayer("layerBooking");
						form.setMainBtnPanel(true);
						if (UserUtil.hasRole("BOOKING_OFFICER")) {
							form.setSubmitBtn(true);
							form.setReadOnly(false);
							form.setCancelBtn(true);
							form.setReturnBtn(true);
						}
					}

//					// FOR RELEASING APPROVAL
//					if (status == 8) {
////						form.setTablayer("layerBooking");
//					}
//					// FOR BOOKING AND RELEASING
//					if (status == 9) {
////						form.setTablayer("layerBooking");
//					}
//					// BOOKED
//					if (status == 10) {
////						form.setTablayer("layerClientAcceptance");
//					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	// 12-11-2020
	@SuppressWarnings("unchecked")
	@Override
	public List<AccessRightsForm> listAccessRights(String module) {
		// TODO Auto-generated method stub
		List<AccessRightsForm> accessRights = new ArrayList<AccessRightsForm>();
		DBService dbSrvc = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("module", module);
		params.put("username", secservice.getUserName());
		params.put("accesstypepar", "PAR");
		params.put("accesstypech", "CH");
		try {
			accessRights = (List<AccessRightsForm>) dbSrvc.execSQLQueryTransformer(
					"select CAST(ROW_NUMBER() OVER (ORDER BY c.navorder,c.submodulename) as int) accessid, "
							+ "REPLACE(c.description,'_',' ') description,c.navorder,c.submodulename, "
							+ "c.accesstype, REPLACE(c.navparent,'_',' ') navparent from TBUSERROLES a "
							+ "left join TBROLEACCESS b on a.roleid=b.roleid "
							+ "left join TBACCESSRIGHTS c on b.accessname = c.accessname "
							+ "where a.username=:username and c.modulename =:module "
							+ "group by c.navorder,c.submodulename,c.description,c.accesstype,c.navparent "
							+ "order by case when CHARINDEX('.',navorder)>0 and cast(right(navorder, charindex('.', reverse(navorder) + '.') - 1) as int)>9 "
							+ "then substring(navorder,0,len(navorder)-1)+'9'+substring(navorder,len(navorder)-1,len(navorder))"
							+ "else navorder end,submodulename",
					params, AccessRightsForm.class, 1);
//			accessRights = (List<AccessRightsForm>) dbService
//					.execSQLQueryTransformer("select DATALENGTH(c.navorder) as accessid, "
//							+ "c.navorder,c.navparent,c.description,c.submodulename from TBUSERROLES a "
//							+ "left join TBROLEACCESS b on a.roleid=b.roleid "
//							+ "left join TBACCESSRIGHTS c on b.accessname = c.accessname "
//							+ "where a.username=:username and c.modulename =:module "
//							+ "group by c.navorder,c.submodulename,c.description,c.accesstype,c.navparent "
//							+ "order by DATALENGTH(c.navorder),navorder", params, AccessRightsForm.class, 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//        if(accessRights != null && !accessRights.isEmpty()) {
//            String jsonString = new JSONObject().toString();
//            for(AccessRightsForm row: accessRights) {
//                System.out.println(row.toString());
//            }
//        }
		return accessRights;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccessRightsForm> listAccessRightsByAccessType(String module, String accesstype) {
		// TODO Auto-generated method stub
		List<AccessRightsForm> accessRights = new ArrayList<AccessRightsForm>();
		DBService dbSrvc = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("module", module);
		params.put("accesstype", accesstype);
		params.put("username", secservice.getUserName());
		try {
			accessRights = (List<AccessRightsForm>) dbSrvc.execSQLQueryTransformer(
					"select CAST(ROW_NUMBER() OVER (ORDER BY c.navorder,c.submodulename) as int) accessid, "
							+ "IIF(CHARINDEX('_',c.description)>0,SUBSTRING(c.description,1,CHARINDEX('_',c.description)-1),c.description) description,"
							+ "c.navorder,c.submodulename, "
							+ "c.accesstype, REPLACE(c.navparent,'_',' ') navparent from TBUSERROLES a "
							+ "left join TBROLEACCESS b on a.roleid=b.roleid "
							+ "left join TBACCESSRIGHTS c on b.accessname = c.accessname "
							+ "where a.username=:username and c.modulename =:module and c.accesstype =:accesstype "
							+ "group by c.navorder,c.submodulename,c.description,c.accesstype,c.navparent "
							+ "order by navorder,submodulename",
					params, AccessRightsForm.class, 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
//        if(accessRights != null && !accessRights.isEmpty()) {
//            String jsonString = new JSONObject().toString();
//            for(AccessRightsForm row: accessRights) {
//                System.out.println(row.toString());
//            }
//        }
		return accessRights;
	}

	public String saveAccessRights(Tbaccessrights access) {
		try {
			if (access.getId() == null) {
				// NEW
				access.setCreatedby(UserUtil.securityService.getUserName());
				access.setCreateddate(new Date());
			} else {
				access.setDateupdated(new Date());
				access.setUpdatedby(UserUtil.securityService.getUserName());
			}
			dbService.saveOrUpdate(access);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	public List<Tbcodetable> listModules() {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			return (List<Tbcodetable>) dbsrvc.execStoredProc(
					"SELECT modulname as codevalue, modulename as desc1 FROM Tbaccessrights GROUP BY modulename ",
					param, Tbcodetable.class, 1, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
//	@SuppressWarnings("unchecked")
//	public List<Tbcodetable> listSubModules(String modulename) {
//		DBService dbsrvc = new DBServiceImpl();
//		Map<String, Object> param = new HashMap<String, Object>();
//		try {
//			return (List<Tbcodetable>) dbsrvc.execStoredProc(
//					"SELECT modulname as codevalue, modulename as desc1 FROM Tbaccessrights GROUP BY modulename ",
//					param, Tbcodetable.class, 1, null);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return null;
//	}
//	@SuppressWarnings("unchecked")
//	public List<Tbcodetable> listSubModules(String submodulename) {
//		DBService dbsrvc = new DBServiceImpl();
//		Map<String, Object> param = new HashMap<String, Object>();
//		try {
//			return (List<Tbcodetable>) dbsrvc.execStoredProc(
//					"SELECT modulname as codevalue, modulename as desc1 FROM Tbaccessrights GROUP BY modulename ",
//					param, Tbcodetable.class, 1, null);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return null;
//	}
}
