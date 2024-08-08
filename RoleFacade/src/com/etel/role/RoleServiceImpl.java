package com.etel.role;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.forms.FormValidation;
import com.etel.role.forms.AccessRightsForm;
import com.etel.role.forms.MenuForm;
import com.etel.role.forms.RoleAccessList;
import com.etel.role.forms.RoleForm;
import com.etel.utils.HQLUtil;
import com.coopdb.data.Tbaccessrights;
import com.coopdb.data.Tbrole;
import com.coopdb.data.TbroleId;
import com.coopdb.data.Tbroleaccess;
import com.coopdb.data.TbroleaccessId;
import com.coopdb.data.Tbuseraccess;
import com.coopdb.data.TbuseraccessId;
import com.coopdb.data.Tbuserroles;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class RoleServiceImpl implements RoleService {
	
	SecurityService secservice =  (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	/**Add roles.*/
	@Override
	public FormValidation addRole(RoleForm form) {
		FormValidation formVal = new FormValidation();
		DBService dbService = new DBServiceImpl();
		Map<String,Object> params = HQLUtil.getMap();
		params.put("roleid", form.getRoleid());
		params.put("rolename", form.getRolename());
		Tbrole role = new Tbrole();
		try {
			role = (Tbrole) dbService.executeUniqueHQLQuery("FROM Tbrole WHERE id.roleid=:roleid AND id.rolename=:rolename", params);
			if(role!=null){
				formVal.setFlag("failed");
				formVal.setErrorMessage("Failed! <b>Existing Role.");
			}else{
				role = new Tbrole();
				TbroleId roleID = new TbroleId();
				roleID.setRoleid(form.getRoleid());
				roleID.setRolename(form.getRolename());
				role.setId(roleID);
				role.setCreatedby(secservice.getUserName());
				role.setCreateddate(new Date());
				if(dbService.save(role)){
					formVal.setFlag("success");
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formVal;
	}

	/**Delete roles.*/
	@SuppressWarnings("unchecked")
	@Override
	public FormValidation deleteRole(String roleid, String rolename) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		FormValidation formVal = new FormValidation();
		params.put("roleid", roleid);
		params.put("rolename", rolename);
		try {
			Tbrole role = (Tbrole) dbService.executeUniqueHQLQuery("FROM Tbrole WHERE id.roleid=:roleid AND id.rolename=:rolename", params);
			if (role != null) {
				if (dbService.delete(role)) {
					formVal.setFlag("success");
					formVal.setErrorMessage("Role deleted.");
					//Delete from Tbroleaccess
					List<Tbroleaccess> roleaccess = (List<Tbroleaccess>) dbService.executeListHQLQuery("FROM Tbroleaccess WHERE id.roleid=:roleid", params);
					if(roleaccess != null){
						for(Tbroleaccess a : roleaccess){
							dbService.delete(a);
						}
					}
					
					//Delete from Tbuserroles
					List<Tbuserroles> userroles = (List<Tbuserroles>) dbService.executeListHQLQuery("FROM Tbuserroles WHERE id.roleid=:roleid", params);
					if(userroles != null){
						for(Tbuserroles a : userroles){
							dbService.delete(a);
						}
					}
					
					//Delete from Tbuseraccess
					List<Tbuseraccess> useraccess = (List<Tbuseraccess>) dbService.executeListHQLQuery("FROM Tbuseraccess WHERE id.roleid=:roleid", params);
					if(useraccess != null){
						for(Tbuseraccess a : useraccess){
							dbService.delete(a);
						}
					}

				}
			} else {
				formVal.setErrorMessage("<b>Deleting role failed");
				formVal.setFlag("failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formVal;
	}
	
	/**Save role access.*/
	@SuppressWarnings("unchecked")
	@Override
	public FormValidation saveRolesAccess(RoleAccessList roleAccess) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		FormValidation formVal = new FormValidation();
		formVal.setFlag("failed");
		List<Tbroleaccess> tbRoleAccess = new ArrayList<Tbroleaccess>();
		try {
			System.out.println("SERVICE STARTS..");
			params.put("roleid", roleAccess.getRoleid());
			params.put("modulename", roleAccess.getModule());
			tbRoleAccess  = (List<Tbroleaccess>) dbService.executeListHQLQuery("FROM Tbroleaccess a WHERE a.id.roleid=:roleid AND modulename=:modulename", params);
			
			//Selected Access Name
			StringBuilder rAccessname = new StringBuilder();
			if(roleAccess.getAccessname() != null && !roleAccess.getAccessname().isEmpty()){
				System.out.println("PARAMETERS NOT NULL.");
				for (TbroleaccessId accessName : roleAccess.getAccessname()) {
					rAccessname.append(",'" + accessName.getAccessname() + "'");
				}
				System.out.println("ACCESS NAME:"+rAccessname);
			} 
			
			//if access name selection is empty.
			if(roleAccess.getAccessname()==null){
				if (tbRoleAccess != null) {
					System.out.println("TBROLEACCESS NOT NULL.");
					for (Tbroleaccess u : tbRoleAccess) {
						if(dbService.delete(u)){
							params.put("accRoleId", u.getId().getRoleid());
							params.put("accAccessname", u.getId().getAccessname());
							
							//Delete also in TBUSERACCESS
							List<Tbuseraccess> tbUserAccess = (List<Tbuseraccess>) dbService.executeListHQLQuery("FROM Tbuseraccess a WHERE a.id.roleid=:accRoleId AND a.id.accessname=:accAccessname", params);
							if(tbUserAccess !=null){
								for(Tbuseraccess userAcc : tbUserAccess){
								dbService.delete(userAcc);
								}
							}
						}
					}
				}
				formVal.setFlag("success");
				return formVal;
			 }else{
				if(tbRoleAccess==null){
					System.out.println("TBROLEACCESS IS NULL");
					for(TbroleaccessId accessName : roleAccess.getAccessname()){
						Tbroleaccess aRole = new Tbroleaccess();
						TbroleaccessId roleAID = new TbroleaccessId();
						roleAID.setRoleid(roleAccess.getRoleid());
						roleAID.setAccessname(accessName.getAccessname());
						aRole.setId(roleAID);
						aRole.setModulename(roleAccess.getModule());
						aRole.setAssigneddate(new Date());
						aRole.setAssignedby(secservice.getUserName());
						if(dbService.save(aRole)){
							formVal.setFlag("success");
							
						}
						//start userroles checking
						params.put("roleid", roleAccess.getRoleid());
						List<Tbuserroles> listUserRoles = (List<Tbuserroles>) dbService.executeListHQLQuery("FROM Tbuserroles a WHERE a.id.roleid=:roleid", params);
						if (listUserRoles != null) {
							System.out.println("LIST OF USERROLES");
							for (Tbuserroles userRole : listUserRoles) {
								//start user access check
								params.put("usRoleId", userRole.getId().getRoleid());
								List<Tbroleaccess> listRoleAccess = (List<Tbroleaccess>) dbService.executeListHQLQuery("FROM Tbroleaccess a WHERE a.id.roleid=:usRoleId",params);
								if (listRoleAccess != null) {
									System.out.println("USER ACCESS");
									for (Tbroleaccess u : listRoleAccess) {
										params.put("username", userRole.getId().getUsername());
										params.put("roleID", u.getId().getRoleid());
										params.put("accessname", u.getId().getAccessname());
										Tbuseraccess tbUserAccess = (Tbuseraccess) dbService.executeUniqueHQLQuery("FROM Tbuseraccess a WHERE a.id.roleid=:roleID AND a.id.username=:username AND a.id.accessname=:accessname", params);
										if(tbUserAccess !=null){
											//Do nothing, there's an existing data.
										}else{
											Tbuseraccess userAccess = new Tbuseraccess();
											TbuseraccessId userAccessId = new TbuseraccessId();
											userAccessId.setUsername(userRole.getId().getUsername());
											userAccessId.setRoleid(u.getId().getRoleid());
											userAccessId.setAccessname(u.getId().getAccessname());
											userAccess.setId(userAccessId);
											userAccess.setModulename(u.getModulename());
											userAccess.setAssigneddate(new Date());
											if(dbService.saveOrUpdate(userAccess)){
												System.out.println("Successfully Saved User Access!");
											}else{
												System.out.println("Error in Saving of User Access!");
											}
										}
									}
									
									// Delete User access in DB if exists and not selected in the parameter.
									params.put("username", userRole.getId().getUsername());
									params.put("roleid", roleAccess.getRoleid());
									params.put("modulename", roleAccess.getModule());
									String deleteUserAccessQuery = "FROM Tbuseraccess a WHERE a.id.username=:username AND a.id.roleid=:roleid AND a.modulename=:modulename AND a.id.accessname NOT IN(' '" + rAccessname.toString() + ")";
									List<Tbuseraccess> listTbUserAccess = (List<Tbuseraccess>) dbService.executeListHQLQuery(deleteUserAccessQuery, params);
									if (listTbUserAccess != null) {
										for (Tbuseraccess userAcc : listTbUserAccess) {
											dbService.delete(userAcc);
										}
									}
								}//end user access check
								
							}
						}//end of userroles checking
					}
				} 
				//if role access not null
				else {
					
					for(TbroleaccessId accessName : roleAccess.getAccessname()){
						rAccessname.append(",'" + accessName.getAccessname() + "'");
						params.put("roleid", roleAccess.getRoleid());
						params.put("accessname", accessName.getAccessname());
						params.put("modulename", roleAccess.getModule());
						Tbroleaccess tbRAccess = (Tbroleaccess) dbService.executeUniqueHQLQuery("FROM Tbroleaccess a WHERE a.id.roleid=:roleid AND a.id.accessname=:accessname AND modulename=:modulename",
								params);
						if (tbRAccess != null) {
							//Do nothing, there's an existing data.
							formVal.setFlag("success");
						} else {
							Tbroleaccess aRole = new Tbroleaccess();
							TbroleaccessId roleAID = new TbroleaccessId();
							roleAID.setRoleid(roleAccess.getRoleid());
							roleAID.setAccessname(accessName.getAccessname());
							aRole.setId(roleAID);
							aRole.setModulename(roleAccess.getModule());
							aRole.setAssigneddate(new Date());
							aRole.setAssignedby(secservice.getUserName());
							if(dbService.save(aRole)){
								formVal.setFlag("success");
							}
						}
						
						//start userroles checking
						params.put("roleid", roleAccess.getRoleid());
						List<Tbuserroles> listUserRoles = (List<Tbuserroles>) dbService.executeListHQLQuery("FROM Tbuserroles a WHERE a.id.roleid=:roleid", params);
						if (listUserRoles != null) {
							for (Tbuserroles userRole : listUserRoles) {
								//start user access check
								params.put("usRoleId", userRole.getId().getRoleid());
								List<Tbroleaccess> listRoleAccess = (List<Tbroleaccess>) dbService.executeListHQLQuery("FROM Tbroleaccess a WHERE a.id.roleid=:usRoleId",params);
								if (listRoleAccess != null) {
									for (Tbroleaccess u : listRoleAccess) {
										params.put("username", userRole.getId().getUsername());
										params.put("roleID", u.getId().getRoleid());
										params.put("accessname", u.getId().getAccessname());
										Tbuseraccess tbUserAccess = (Tbuseraccess) dbService.executeUniqueHQLQuery("FROM Tbuseraccess a WHERE a.id.roleid=:roleID AND a.id.username=:username AND a.id.accessname=:accessname", params);
										if(tbUserAccess !=null){
											//Do nothing, there's an existing data.
										}else{
											Tbuseraccess userAccess = new Tbuseraccess();
											TbuseraccessId userAccessId = new TbuseraccessId();
											userAccessId.setUsername(userRole.getId().getUsername());
											userAccessId.setRoleid(u.getId().getRoleid());
											userAccessId.setAccessname(u.getId().getAccessname());
											userAccess.setId(userAccessId);
											userAccess.setModulename(u.getModulename());
											userAccess.setAssigneddate(new Date());
											if(dbService.saveOrUpdate(userAccess)){
												System.out.println(">>>Successfully Saved User Access!");
											}else{
												System.out.println(">>>Error in Saving of User Access!");
											}
										}
									}
									
									// Delete User access in DB if exists and not selected in the parameter.
									params.put("username", userRole.getId().getUsername());
									params.put("roleid", roleAccess.getRoleid());
									params.put("modulename", roleAccess.getModule());
									String deleteUserAccessQuery = "FROM Tbuseraccess a WHERE a.id.username=:username AND a.id.roleid=:roleid AND a.modulename=:modulename AND a.id.accessname NOT IN(' '" + rAccessname.toString() + ")";
									List<Tbuseraccess> listTbUserAccess = (List<Tbuseraccess>) dbService.executeListHQLQuery(deleteUserAccessQuery, params);
									if (listTbUserAccess != null) {
										for (Tbuseraccess userAcc : listTbUserAccess) {
											dbService.delete(userAcc);
										}
									}
								}//end user access check
								
							}
						}//end of userroles checking
						
					}

					// Delete role access in DB if exists and not selected in the parameter.
					params.put("roleid", roleAccess.getRoleid());
					params.put("modulename", roleAccess.getModule());
					String deleteRoleAccessQuery = "FROM Tbroleaccess a WHERE a.id.roleid=:roleid AND a.modulename=:modulename AND a.id.accessname NOT IN(' '" + rAccessname.toString() + ")";
					tbRoleAccess = (List<Tbroleaccess>) dbService.executeListHQLQuery(deleteRoleAccessQuery, params);
					if (tbRoleAccess != null) {
						for (Tbroleaccess u : tbRoleAccess) {
							dbService.delete(u);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formVal;
	}
	/**List of roles.*/
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbrole> listRoles() {
		DBService dbService = new DBServiceImpl();
		List<Tbrole> list = new ArrayList<Tbrole>();
		try {
			list = (List<Tbrole>) dbService.executeListHQLQuery("FROM Tbrole", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**List access rights sub module.*/
	@SuppressWarnings("unchecked")
	@Override
	public List<AccessRightsForm> listAccessRightsSubModule(String modulename) {
		DBService dbService = new DBServiceImpl();
		List<Tbaccessrights> list = new ArrayList<Tbaccessrights>();
		List<AccessRightsForm> listForm = new ArrayList<AccessRightsForm>();
		Map<String, Object> params =  HQLUtil.getMap();
		params.put("modulename", modulename);
		try {
			list = (List<Tbaccessrights>) dbService.executeListHQLQuery("FROM Tbaccessrights WHERE modulename=:modulename order by navorder asc", params);
			if (list != null) {
				for (Tbaccessrights a : list) {
					AccessRightsForm aform = new AccessRightsForm();
					aform.setAccessname(a.getId().getAccessname());
					aform.setAccesstype(a.getAccesstype());
					aform.setCreatedby(a.getCreatedby());
					aform.setCreateddate(a.getCreateddate());
					aform.setDateupdated(a.getDateupdated());
					aform.setDescription(a.getDescription());
					aform.setModulename(a.getId().getModulename());
//					aform.setSubmodulename(a.getSubmodulename());
					aform.setSubmodulename(getModuleDesc(a.getSubmodulename()));
					aform.setUpdatedby(a.getUpdatedby());
					aform.setNavorder(a.getNavorder());
					aform.setNavparent(a.getNavparent());
					listForm.add(aform);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listForm;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccessRightsForm> getRoleAccess(String roleid, String modulename) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("roleid", roleid);
		params.put("modulename", modulename);
		List<AccessRightsForm> form = new ArrayList<AccessRightsForm>();
		try {
			List<Tbroleaccess> roleaccesslist = (List<Tbroleaccess>) dbService.executeListHQLQuery("FROM Tbroleaccess WHERE roleid=:roleid AND modulename=:modulename", params);
			if (roleaccesslist != null) {
				for (Tbroleaccess racc : roleaccesslist) {
					AccessRightsForm aForm = new AccessRightsForm();
					aForm.setAccessname(racc.getId().getAccessname());
					form.add(aForm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/**Get Menu Access Rights*/
	@SuppressWarnings("unchecked")
	@Override
	public MenuForm getMenuAccessRights() {
		MenuForm form = new MenuForm();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("username", secservice.getUserName());
		try {
			List<Tbuseraccess> list = (List<Tbuseraccess>) dbService.executeListHQLQuery("FROM Tbuseraccess WHERE id.username=:username", params);
			if(list != null){
				for(Tbuseraccess a : list){
					
					/********************************** HOME *************************************/
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_HOME_OPTN")){
						form.setHome(true);
					}
					
					/********************************** INQUIRY *************************************/
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_INQUIRY_OPTN")){
						form.setInquiry(true);
					}
					//Loan Application
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_INQ_LOANAPPLICATION_OPTN")){
						form.setInquiryLoanApplication(true);
						form.setInquiry(true);
					}
					//Line Application
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_INQ_LINEAPPLICATION_OPTN")){
						form.setInquiryLineApplication(true);
						form.setInquiry(true);
					}
					//Line Availment
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_INQ_LINEAVAILMENT_OPTN")){
						form.setInquiryLineAvailment(true);
						form.setInquiry(true);
					}
					//Loan Roll Over
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_INQ_LOANROLLOVER_OPTN")){
						form.setInquiryLoanRollOver(true);
						form.setInquiry(true);
					}
					
					/********************************** CREATE *************************************/
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_CREATEAPP_OPTN")){
						form.setCreateApp(true);
					}
					//Loan Application
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_NEW_LOANAPPLICATION_OPTN")){
						form.setCreateLoanApplication(true);
						form.setCreateApp(true);
					}
					//Line Application
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_NEW_LINEAPPLICATION_OPTN")){
						form.setCreateLineApplication(true);
						form.setCreateApp(true);
					}
					//Line Availment
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_NEW_LINEAVAILMENT_OPTN")){
						form.setCreateLineAvailment(true);
						form.setCreateApp(true);
					}
					//Loan Roll Over
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_NEW_LOANROLLOVER_OPTN")){
						form.setCreateLoanRollOver(true);
						form.setCreateApp(true);
					}
					
					
					
					/********************************** ASSIGNMENTS *************************************/
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_ASSIGNMENT_OPTN")){
						form.setAssignments(true);
					}
					//My assignments
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_MYASSIGNMENT_OPTN")){
						form.setMyAssignments(true);
						form.setAssignments(true);
					}
					//Manual assignments
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_MANUALASSIGNMENT_OPTN")){
						form.setManualAssignments(true);
						form.setAssignments(true);
					}
					
					
					/********************************** OTHER TRANSACTIONS *************************************/
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_OTHERTX_OPTN")){
						form.setOtherTx(true);
					}
					//Bureau Investigation
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_OTHERTX_BI_OPTN")){
						form.setOtherTxBureauInvestigation(true);
						form.setOtherTx(true);
					}
					//Credit Investigation
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_OTHERTX_CI_OPTN")){
						form.setOtherTxCreditInvestigation(true);
						form.setOtherTx(true);
					}
					//Collateral Appraisal
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_OTHERTX_COLAPPRAISAL_OPTN")){
						form.setOtherTxCollateralAppraisal(true);
						form.setOtherTx(true);
					}
					//Collateral Maintenance
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_OTHERTX_COLMAINTENANCE_OPTN")){
						form.setOtherTxCollateralMaintenance(true);
						form.setOtherTx(true);
					}
					
					
					/********************************** REPORTS *************************************/
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_REPORTS_OPTN")){
						form.setReports(true);
					}
					
					
					/********************************** SYSTEM ADMIN *************************************/
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SYSTEMADMINMAIN_OPTN")){
						form.setSysAdmin(true);
					}
					//System Parameters
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SYSPARAMSMAINTENANCE_OPTN")){
						form.setSysParams(true);
						form.setSysAdmin(true);
					}
					//Document Checklist
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SYSPARAMSDOCCHECKLIST_OPTN")){
						form.setDocChecklist(true);
						form.setSysAdmin(true);
					}
					//Company
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SYSPARAMSCOMPANY_OPTN")){
						form.setCompany(true);
						form.setSysAdmin(true);
					}
					//Branch
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SYSPARAMSBRANCH_OPTN")){
						form.setBranch(true);
						form.setSysAdmin(true);
					}
					//Group
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SYSPARAMSGROUP_OPTN")){
						form.setGroup(true);
						form.setSysAdmin(true);
					}
					//Team
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SYSPARAMSTEAM_OPTN")){
						form.setTeam(true);
						form.setSysAdmin(true);
					}
					//Region
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SYSPARAMSREGION_OPTN")){
						form.setRegion(true);
						form.setSysAdmin(true);
					}
					//Loan Product
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SYSPARAMSLOANPROD_OPTN")){
						form.setLoanproduct(true);
						form.setSysAdmin(true);
					}
					//System Workflow
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SYSTEMWORKFLOW_OPTN")){
						form.setSystemWorkflow(true);
						form.setSysAdmin(true);
					}
					//LOS Default User Maintenance
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SYSPARAMS_DEFAULTUSERS_OPTN")){
						form.setDefaultusers(true);
						form.setSysAdmin(true);
					}
					
					
					/********************************** SECURITY *************************************/
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SECURITY_OPTN")){
						form.setSecurity(true);
					}
					//User Account
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SECUSERACCOUNT_OPTN")){
						form.setUserAccount(true);
						form.setSecurity(true);
					}
					//Role & Access Rights
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SECROLEACCESSRIGHTS_OPTN")){
						form.setRoleAndAccessRights(true);
						form.setSecurity(true);
					}
					//Security Parameter
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SECURITYPARAMETERS_OPTN")){
						form.setSecurityParams(true);
						form.setSecurity(true);
					}
					//Email Utility
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SECEMAILUTILITY_OPTN")){
						form.setEmailUtility(true);
						form.setSecurity(true);
					}
					//Audit Trail
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SECAUDITTRAIL_OPTN")){
						form.setAuditTrail(true);
						form.setSecurity(true);
					}
					//Reports
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_SECREPORTS_OPTN")){
						form.setSecurityReports(true);
						form.setSecurity(true);
					}
					/********************************** CIF LINK *************************************/
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_CIFLINK_OPTN")){
						form.setCifLink(true);
					}
					/********************************** LMS LINK *************************************/
					if(a.getId().getAccessname()!=null && a.getId().getAccessname().equalsIgnoreCase("MENU_LMSLINK_OPTN")){
						form.setLmsLink(true);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleForm> getListRoleId() {
		// TODO Auto-generated method stub
		DBService dbService = new DBServiceImpl();
		List<Tbrole> list = new ArrayList<Tbrole>();
		List<RoleForm> form = new ArrayList<RoleForm>();
		try {
			list = (List<Tbrole>) dbService.executeListHQLQuery("FROM Tbrole", null);
			if (list != null) {
				for (Tbrole a : list) {
					RoleForm aform = new RoleForm();
					aform.setRoleid(a.getId().getRoleid());
					aform.setRolename(a.getId().getRolename());
					form.add(aform);
				}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}
	
	public String getModuleDesc(String submodulename) {
		try {
			DBService dbService = new DBServiceImpl();
			Map<String, Object> params = HQLUtil.getMap();
			params.put("sub", submodulename);
			String desc = (String) dbService.executeUniqueSQLQuery(
					"SELECT desc1 FROM Tbcodetable WHERE codevalue=:sub AND codename='SUBMODULENAME'", params);
			if (desc != null) {
				return desc;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}
}
