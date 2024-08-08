package com.etel.security.adnonad;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.email.EmailCode;
import com.etel.email.EmailFacade;
import com.etel.forms.ADUser;
import com.etel.forms.UserForm;
import com.etel.generator.NoGenerator;
import com.etel.security.adnonad.forms.CreateUserForm;
import com.etel.security.forms.TBRoleForm;
import com.etel.util.SequenceGenerator;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.etel.utils.LoggerUtil;
import com.etel.utils.PasswordGeneratorUtil;
import com.etel.utils.SystemUtil;
import com.etel.utils.UserUtil;
import com.cifsdb.data.Tbcifbusiness;
import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.Tbemailparams;
import com.coopdb.data.Tbnetamt;
import com.coopdb.data.Tbpasswordbank;
import com.coopdb.data.Tbrole;
import com.coopdb.data.Tbroleaccess;
import com.coopdb.data.Tbuser;
import com.coopdb.data.Tbuseraccess;
import com.coopdb.data.TbuseraccessId;
import com.coopdb.data.Tbuserroles;
import com.coopdb.data.TbuserrolesId;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class ADInquiryServiceImpl implements ADInquiryService {

	static DirContext ldapContext;
	SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	/*
	 * Module Last Modified 05-27-2017 - Kevin
	 */
	@Override
	public Tbuser searchADUser(String username) throws NamingException {
//		DBService dbService = new DBServiceImplLOS();
//		Tbsecurityparams secparam = (Tbsecurityparams) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbsecurityparams", null);
		try {
			System.out.println("Testing Active Directory");

//			String ldapUrl = secparam.getLdapurl() == null ? "" : secparam.getLdapurl();
//			String adDomain = secparam.getAddomain() == null ? "" : secparam.getAddomain();
//			String adUserName = secparam.getAdusername() == null ? "" : secparam.getAdusername();
//			String adPass = secparam.getAdpassword() == null ? "" : secparam.getAdpassword();

			String ldapUrl = SystemUtil.getPropertyValue("activeDirectoryConfig.properties", "ldapUrl");
			String adDomain = SystemUtil.getPropertyValue("activeDirectoryConfig.properties", "adDomain");
			String adUserName = SystemUtil.getPropertyValue("activeDirectoryConfig.properties", "adUserName");
			String adPass = SystemUtil.getPropertyValue("activeDirectoryConfig.properties", "adPassword");

			Hashtable<String, String> ldapEnv = new Hashtable<String, String>(11);
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			// ldapEnv.put(Context.PROVIDER_URL, "ldap://122.3.101.105:389");
			ldapEnv.put(Context.PROVIDER_URL, ldapUrl);
			// ldapEnv.put(Context.PROVIDER_URL, "ldap://10.88.50.251:389");
			ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			// ldapEnv.put(Context.SECURITY_PRINCIPAL, "cn=administrator,
			// cn=users, dc=eteligent, dc=com");
			// ldapEnv.put(Context.SECURITY_PRINCIPAL, "cn=kenny marlo b. cid,
			// cn=users, dc=eteligent, dc=com");
			ldapEnv.put(Context.SECURITY_PRINCIPAL, adUserName + "@" + adDomain);
			// ldapEnv.put(Context.SECURITY_CREDENTIALS, "DbAdmink?.08");
			ldapEnv.put(Context.SECURITY_CREDENTIALS, adPass);
			// ldapEnv.put(Context.SECURITY_PROTOCOL, "ssl");
			// ldapEnv.put(Context.SECURITY_PROTOCOL, "simple");

			ldapContext = new InitialDirContext(ldapEnv);

			// Create the search controls
			SearchControls searchCtrls = new SearchControls();

			// Specify the attributes to return
			String[] returnedAttr = { "sn", "givenName", "samAccountName", "samAccountType", "userPrincipalName",
					"mail" };
			searchCtrls.setReturningAttributes(returnedAttr);

			// Specify the LDAP search scope
			searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			String principalName = username + "@" + adDomain;
			System.out.println("<<<<<<<<<<<<<<<< Principal Name: " + principalName);
			// Specify the LDAP search filter
			// String searchFilter =
			// "(&(objectCategory=person)(objectClass=user)(sAMAccountName=Abe))";
			String searchFilter = "(&(userPrincipalName=" + principalName + ")(objectClass=user))";

			// Specify the base for the search
			// String searchBase = "dc=eteligent, dc=com";

			String searchBase = "dc=" + adDomain.replace(".", ",dc=");

			// Initialize couter to total the results
			int totalResults = 0;

			// Search for objects using the filter
			NamingEnumeration<SearchResult> answer = ldapContext.search(searchBase, searchFilter, searchCtrls);

			Tbuser adUser = new Tbuser();

			// Loop through the search results
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();

				totalResults++;
				System.out.println(">>>" + sr.getName());
				Attributes attrs = sr.getAttributes();
				if (attrs.get("givenName") != null) {
					System.out.println(">>>>>" + attrs.get("givenName"));
					String str1[] = attrs.get("givenName").toString().split(":");
					adUser.setFirstname(str1[1].trim());
				}

				if (attrs.get("sn") != null) {
					System.out.println(">>>>>" + attrs.get("sn"));
					String str2[] = attrs.get("sn").toString().split(":");
					adUser.setLastname(str2[1].trim());
				}

				if (attrs.get("mail") != null) {
					System.out.println(">>>>>" + attrs.get("mail"));
					String str3[] = attrs.get("mail").toString().split(":");
					adUser.setEmailadd(str3[1].trim());
				}

				if (attrs.get("samAccountName") != null) {
					System.out.println(">>>>>" + attrs.get("samAccountName"));
					String str4[] = attrs.get("samAccountName").toString().split(":");
					adUser.setUsername(str4[1].trim());
				}

				System.out.println();
			}

			System.out.println("Total Results: " + totalResults);
			if (totalResults != 0)
				return adUser;

			ldapContext.close();
		} catch (Exception e) {
			System.out.println("Search Error: " + e);
			LoggerUtil.exceptionError(e, ADInquiryServiceImpl.class);
			e.printStackTrace();
			return null;
		}

		return null;
	}

	@Override
	public boolean addToLosUser(ADUser adUser) {

		DBService dbService = new DBServiceImpl();
		Tbuser user = new Tbuser();

		SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

		Map<String, Object> params = HQLUtil.getMap();
		params.put("user", adUser.getsAMAccountName());

		Tbuser sUser = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:user", params);
		if (sUser == null) {
			// Insert Data from ADUser Bean
			user.setFirstname(adUser.getGivenName().toUpperCase());
			user.setLastname(adUser.getSurname().toUpperCase());
			user.setUsername(adUser.getsAMAccountName());
			user.setPassword(UserUtil.sha1("P@ssword01"));
			user.setEmailadd(adUser.getPrincipalName());
			user.setIsactivedirectorymember(true);
			user.setLastIp("");
			user.setIsactive(true);
			user.setIsloggedon(false);
			user.setIslocked(false);
			user.setIssuspended(false);
			user.setInvalidattemptscount(0);
			user.setInvalidattempip("");
			user.setCreatedby(service.getUserName());
			user.setUpdatedby(service.getUserName());
			user.setIschangepwrequired(false);
			user.setIsdisabled(false);

			dbService.saveOrUpdate(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public UserForm searchDBUser(String username) {
		DBService dbService = new DBServiceImpl();
		Tbuser user = null;
		UserForm userForm = new UserForm();

		Map<String, Object> params = HQLUtil.getMap();
		params.put("user", username);

		try {
			user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:user", params);
			if (user != null) {

				if (user.getIsactivedirectorymember() == true) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.VIEW_USER_ACCOUNT_AD),
							"View Active Directory User " + user.getUsername() + ".",
							RuntimeAccess.getInstance().getRequest().getUserPrincipal().getName(), new Date(),
							AuditLogEvents.getEventModule(AuditLogEvents.VIEW_USER_ACCOUNT_AD));
				} else if (user.getIsactivedirectorymember() == false) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.VIEW_USER_ACCOUNT_NON_AD),
							"View Non - Active Directory User " + user.getUsername() + ".",
							RuntimeAccess.getInstance().getRequest().getUserPrincipal().getName(), new Date(),
							AuditLogEvents.getEventModule(AuditLogEvents.VIEW_USER_ACCOUNT_NON_AD));
				}

				userForm.setUseraccount(user);

				@SuppressWarnings("unchecked")
				List<Tbuserroles> userroles = (List<Tbuserroles>) dbService
						.executeListHQLQuery("FROM Tbuserroles a where a.id.username = :user", params);

				@SuppressWarnings("unchecked")
				List<Tbrole> roles = (List<Tbrole>) dbService.executeListHQLQuery("FROM Tbrole", null);

				List<TBRoleForm> selectedRoles = new ArrayList<TBRoleForm>();

				if (!userroles.isEmpty()) {
					for (Tbuserroles r : userroles) {
						System.out.println("Username: " + r.getId().getUsername() + " User ID: " + r.getId().getRoleid()
								+ " Role Code: " + r.getId().getRoleid());
						for (Tbrole role : roles) {
							if (role.getId().getRoleid().equals(r.getId().getRoleid())) {
								TBRoleForm tbRoleForm = new TBRoleForm();
								tbRoleForm.setRoleid(role.getId().getRoleid());
								tbRoleForm.setRolename(role.getId().getRolename());
								tbRoleForm.setCreateddate(role.getCreateddate());
								tbRoleForm.setCreatedby(role.getCreatedby());
								tbRoleForm.setDateupdated(role.getDateupdated());
								tbRoleForm.setUpdatedby(role.getUpdatedby());
								selectedRoles.add(tbRoleForm);
							}
						}
					}

					userForm.setRoles(selectedRoles);
				}
			}
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, ADInquiryServiceImpl.class);
			e.printStackTrace();
			throw new UsernameNotFoundException(username + " not found in the database");
		}

		return userForm;

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean saveEditUserAndRoles(UserForm userForm, String tag) {
		boolean status = false;
		DBService dbService = new DBServiceImpl();
		UserForm mUserForm = userForm;
		if (userForm == null) {
			return status = false;
		}
		Map<String, Object> mParams = HQLUtil.getMap();
		mParams.put("user", mUserForm.getUseraccount().getUsername());
		try {
			Tbuser user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:user", mParams);

			// Audit Logs Events
			boolean mIsActive = user.getIsactive();
			if (mIsActive != mUserForm.getUseraccount().getIsactive()) {
				if (mUserForm.getUseraccount().getIsactive() == false) {
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.DEACTIVATE_USER),
							"Deactivate user " + user.getUsername() + ".",
							RuntimeAccess.getInstance().getRequest().getUserPrincipal().getName(), new Date(),
							AuditLogEvents.getEventModule(AuditLogEvents.DEACTIVATE_USER));
				} else {

					// Modified 05-31-2017 - Kevin >> Added Routine - Send Email if user account
					// reactivated.
					EmailFacade email = new EmailFacade();
					String result = "";
					result = email.sendEmail(EmailCode.ACTIVATION_OF_USER_ACCOUNT, user.getUsername(), null).getFlag();
					if (result == "sucess") {
						System.out.println(">>>>Activation of User: " + user.getUsername() + ", Email has been sent!");
					} else {
						System.out.println(">>>>Activation of User: " + user.getUsername() + ", Email Sending failed!");
					}
					AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.REACTIVATE_USER),
							"Reactivate user " + user.getUsername() + ".",
							RuntimeAccess.getInstance().getRequest().getUserPrincipal().getName(), new Date(),
							AuditLogEvents.getEventModule(AuditLogEvents.REACTIVATE_USER));
				}
			}

			// Last Modified - 05-28-2017 - Kevin
			user.setFirstname(mUserForm.getUseraccount().getFirstname());
			user.setMiddlename(mUserForm.getUseraccount().getMiddlename());
			user.setLastname(mUserForm.getUseraccount().getLastname());
			user.setCompanycode(mUserForm.getUseraccount().getCompanycode());// Last Modified - 07-15-2019 - Ced
			if (!user.getBranchcode().equals(mUserForm.getUseraccount().getBranchcode())) {
				mParams.put("olduserid", user.getUserid());
//				user.setUserid(SequenceGenerator.generateUserSequence(mUserForm.getUseraccount().getBranchcode(),
//						user.getCoopcode()));
				mParams.put("newuserid", user.getUserid());
				dbService.executeUpdate("UPDATE Tbnetamt SET userid =:newuserid WHERE userid=:olduserid", mParams);
				AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.U_CHANGE_BRANCH),
						"Change user branch " + user.getUsername() + " from " + user.getBranchcode() + " to "
								+ mUserForm.getUseraccount().getBranchcode(),
						RuntimeAccess.getInstance().getRequest().getUserPrincipal().getName(), new Date(),
						AuditLogEvents.getEventModule(AuditLogEvents.U_CHANGE_BRANCH));
			}
			user.setBranchcode(mUserForm.getUseraccount().getBranchcode());
			user.setGroupcode(mUserForm.getUseraccount().getGroupcode());
			user.setCoopcode(mUserForm.getUseraccount().getCoopcode());
//			if (mUserForm.getUseraccount().getGroupcode().contains("MKTG")) { Ced 01082020
//				user.setTeamcode(mUserForm.getUseraccount().getTeamcode());
//			} else {
			user.setTeamcode(null);
//			}
			user.setEmailadd(mUserForm.getUseraccount().getEmailadd());
//			user.setUpdatedby(RuntimeAccess.getInstance().getRequest().getUserPrincipal().getName());
			user.setUpdatedby(service.getUserName());
			user.setDateupdated(new Date());
			user.setIsloggedon(mUserForm.getUseraccount().getIsloggedon());
			user.setIslocked(mUserForm.getUseraccount().getIslocked());
			if (mUserForm.getUseraccount().getIslocked() == null || mUserForm.getUseraccount().getIslocked() == false) {
				user.setInvalidattemptscount(0);
			}
			user.setIsactive(mUserForm.getUseraccount().getIsactive());
			user.setIssuspended(mUserForm.getUseraccount().getIssuspended());
			user.setIsactivedirectorymember(mUserForm.getUseraccount().getIsactivedirectorymember());
			user.setIschangepwrequired(mUserForm.getUseraccount().getIschangepwrequired());
			user.setIsdisabled(false);

			user.setValiditydatefrom(mUserForm.getUseraccount().getValiditydatefrom());
			user.setValiditydateto(mUserForm.getUseraccount().getValiditydateto());
			
			// 01.09.2023
			user.setIsopen(mUserForm.getUseraccount().getIsopen());

			// Modified Nov.10,2017 - Added Fullname - Kevin
			String fullname = "";
			String mInitial = " ";
			if (mUserForm.getUseraccount().getMiddlename() != null) {
				if (mUserForm.getUseraccount().getMiddlename().length() > 0) {
					mInitial = " " + mUserForm.getUseraccount().getMiddlename().substring(0, 1).toUpperCase() + ". ";
				}
			}
			fullname = user.getFirstname() + mInitial + user.getLastname();
			user.setFullname(fullname);

			if (tag.equalsIgnoreCase("dbuser")) {
				user.setIspwneverexpire(mUserForm.getUseraccount().getIspwneverexpire());
				user.setPwexpirydate(mUserForm.getUseraccount().getPwexpirydate());

				AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.EDIT_USER_ACCOUNT_NON_AD),
						"Successfully edited " + user.getUsername() + " account.",
						RuntimeAccess.getInstance().getRequest().getUserPrincipal().getName(), new Date(),
						AuditLogEvents.getEventModule(AuditLogEvents.EDIT_USER_ACCOUNT_NON_AD));
			} else if (tag.equalsIgnoreCase("aduser")) {

				AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.EDIT_USER_ACCOUNT_AD),
						"Successfully edited " + user.getUsername() + " account.",
						RuntimeAccess.getInstance().getRequest().getUserPrincipal().getName(), new Date(),
						AuditLogEvents.getEventModule(AuditLogEvents.EDIT_USER_ACCOUNT_AD));
			}
			List<TBRoleForm> roles = mUserForm.getRoles();

			for (TBRoleForm r : roles) {
				System.out.println("Role ID: " + r.getRoleid() + " Role Name: " + r.getRolename());
			}

			// Modified - June 3, 2017 - Kevin
			if (dbService.saveOrUpdate(user)) {
				Map<String, Object> params = HQLUtil.getMap();
				params.put("user", user.getUsername());
				List<Tbuserroles> userroles = new ArrayList<Tbuserroles>();
				userroles = (List<Tbuserroles>) dbService
						.executeListHQLQuery("FROM Tbuserroles a where a.id.username = :user", params);
				if (userroles == null) {
					for (TBRoleForm r : roles) {
						Tbuserroles ur = new Tbuserroles();
						TbuserrolesId id = new TbuserrolesId();
						id.setRoleid(r.getRoleid());
						id.setUsername(user.getUsername());
						ur.setId(id);
						ur.setRolename(r.getRolename());
						ur.setAssigneddate(new Date());
						ur.setAssignedby(service.getUserName());
						if (dbService.saveOrUpdate(ur)) {
							System.out.println("Saved ROLES for " + user.getUsername() + " " + r.getRoleid());
						}

						// Added Routine - Insert User Access - Kevin - June 11, 2017
						params.put("roleid", r.getRoleid());
						List<Tbroleaccess> roleAccess = (List<Tbroleaccess>) dbService
								.executeListHQLQuery("FROM Tbroleaccess a WHERE a.id.roleid=:roleid", params);
						if (roleAccess != null) {
							for (Tbroleaccess roleAcc : roleAccess) {
								Tbuseraccess userAccess = new Tbuseraccess();
								TbuseraccessId userAccessId = new TbuseraccessId();
								userAccessId.setUsername(user.getUsername());
								userAccessId.setRoleid(roleAcc.getId().getRoleid());
								userAccessId.setAccessname(roleAcc.getId().getAccessname());
								userAccess.setModulename(roleAcc.getModulename());
								userAccess.setId(userAccessId);
								userAccess.setAssigneddate(new Date());
								dbService.save(userAccess);
							}
						}
					}
				} else {
					StringBuilder hql = new StringBuilder();
					for (TBRoleForm r : roles) {
						hql.append(",'" + r.getRoleid() + "'");
						params.put("roleId", r.getRoleid());
						params.put("userName", user.getUsername());
						Tbuserroles userRole = (Tbuserroles) dbService.executeUniqueHQLQuery(
								"FROM Tbuserroles a where a.id.username = :user AND a.id.roleid=:roleId", params);
						if (userRole != null) {
							// Do nothing if there's an existing data.
						} else {
							Tbuserroles ur = new Tbuserroles();
							TbuserrolesId id = new TbuserrolesId();
							id.setRoleid(r.getRoleid());
							id.setUsername(user.getUsername());
							ur.setId(id);
							ur.setRolename(r.getRolename());
							ur.setAssigneddate(new Date());
							ur.setAssignedby(service.getUserName());
							if (dbService.saveOrUpdate(ur)) {
								System.out.println("Saved ROLES for " + user.getUsername() + " " + r.getRoleid());
							}
						}

						// Added Routine - User Access - Kevin - June 11, 2017
						params.put("roleid", r.getRoleid());
						List<Tbroleaccess> roleAccess = (List<Tbroleaccess>) dbService
								.executeListHQLQuery("FROM Tbroleaccess a WHERE a.id.roleid=:roleid", params);
						if (roleAccess != null) {
							for (Tbroleaccess roleAcc : roleAccess) {
								params.put("accRoleId", roleAcc.getId().getRoleid());
								params.put("accAccessname", roleAcc.getId().getAccessname());
								Tbuseraccess tbUserAccess = (Tbuseraccess) dbService.executeUniqueHQLQuery(
										"FROM Tbuseraccess a WHERE a.id.username=:userName AND a.id.roleid=:accRoleId AND a.id.accessname=:accAccessname",
										params);
								if (tbUserAccess != null) {
									// Do nothing if there's an existing data.
								} else {
									Tbuseraccess userAccess = new Tbuseraccess();
									TbuseraccessId userAccessId = new TbuseraccessId();
									userAccessId.setUsername(user.getUsername());
									userAccessId.setRoleid(roleAcc.getId().getRoleid());
									userAccessId.setAccessname(roleAcc.getId().getAccessname());
									userAccess.setModulename(roleAcc.getModulename());
									userAccess.setId(userAccessId);
									userAccess.setAssigneddate(new Date());
									dbService.save(userAccess);
								}
							}
						}
					}
					// Delete Userroles
					String deleteUserRolesQuery = "FROM Tbuserroles a where a.id.username = :user AND a.id.roleid NOT IN(' '"
							+ hql.toString() + ")";
					userroles = (List<Tbuserroles>) dbService.executeListHQLQuery(deleteUserRolesQuery, params);
					if (userroles != null) {
						for (Tbuserroles u : userroles) {
							dbService.delete(u);
						}
					}

					// Delete User Access
					String deleteUserAccessQuery = "FROM Tbuseraccess a where a.id.username = :user AND a.id.roleid NOT IN(' '"
							+ hql.toString() + ")";
					List<Tbuseraccess> listUserAccess = (List<Tbuseraccess>) dbService
							.executeListHQLQuery(deleteUserAccessQuery, params);
					if (listUserAccess != null) {
						for (Tbuseraccess u : listUserAccess) {
							dbService.delete(u);
						}
					}
				}

				// Added Routine >> Update EmailAddress on Tbemailparams - Kevin 06-02-2017
				List<Tbemailparams> emailParam = (List<Tbemailparams>) dbService
						.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.username=:user", params);
				if (emailParam != null) {
					for (Tbemailparams emParam : emailParam) {
						emParam.setEmailadd(user.getEmailadd());
						dbService.saveOrUpdate(emParam);
					}
				}
				status = true;

			}
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, ADInquiryServiceImpl.class);
			e.printStackTrace();
		}

		return status;
	}

	@Override
	public boolean deactivateUser(String username) {
		boolean status = false;
		DBService dbService = new DBServiceImpl();

		Map<String, Object> mParams = HQLUtil.getMap();
		mParams.put("user", username);

		try {
			Tbuser user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:user", mParams);
			user.setIsactive(false);
			user.setIsdisabled(true);

			if (dbService.saveOrUpdate(user)) {
				System.out.println("Successfully disabled user.");
			}
//			@SuppressWarnings("unchecked")
//			List<Tbuserroles> roles = (List<Tbuserroles>) dbService.executeListHQLQuery("FROM Tbuserroles a where a.id.username = :user", mParams);
//			if(roles != null && roles.isEmpty() == false){
//				for(Tbuserroles r : roles){
//					if(dbService.delete(r)){
//						System.out.println("Removing Access Role " + r.getId().getRoleid() + " to " + r.getId().getUsername());
//					}
//				}
//			}
			status = true;
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, ADInquiryServiceImpl.class);
			e.printStackTrace();
		}
		return status;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CreateUserForm saveDbUser(UserForm userForm) {
		CreateUserForm res = new CreateUserForm();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		// Tbsecurityparams secparam = (Tbsecurityparams)
		// dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbsecurityparams", null);
		UserForm mUserForm = userForm;
		if (userForm == null) {
			res.setFlag("failed");
		}
		try {
			String randomPassword = PasswordGeneratorUtil.generatePassword();
			Tbuser user = mUserForm.getUseraccount();
			user.setEmailadd(mUserForm.getUseraccount().getEmailadd());
			user.setCompanycode(mUserForm.getUseraccount().getCompanycode());
			user.setBranchcode(mUserForm.getUseraccount().getBranchcode());
			user.setGroupcode(mUserForm.getUseraccount().getGroupcode());
			user.setUpdatedby(service.getUserName());
			user.setIsloggedon(false);
			user.setIslocked(false);
			user.setIsdisabled(false);
			user.setIsactive(true);
			user.setIssuspended(false);
			user.setIsactivedirectorymember(false);
			user.setIschangepwrequired(mUserForm.getUseraccount().getIschangepwrequired());
			user.setDatecreated(new Date());
			user.setDateupdated(new Date());
			user.setPassword(UserUtil.sha1(randomPassword));// random password
			user.setValiditydatefrom(mUserForm.getUseraccount().getValiditydatefrom());
			user.setValiditydateto(mUserForm.getUseraccount().getValiditydateto());
			user.setCoopcode(mUserForm.getUseraccount().getCoopcode());
			// Modified Nov.10,2017 - Added Fullname - Kevin
			String fullname = "";
			String mInitial = " ";
			if (mUserForm.getUseraccount().getMiddlename() != null) {
				if (mUserForm.getUseraccount().getMiddlename().length() > 0) {
					mInitial = " " + mUserForm.getUseraccount().getMiddlename().substring(0, 1).toUpperCase() + ". ";
				}
			}
			fullname = user.getFirstname() + mInitial + user.getLastname();
			user.setFullname(fullname);
			// Modified Oct.23,2018 - Added CASA - Ced>>
			user.setUserid(SequenceGenerator.generateUserSequence(user.getBranchcode(), user.getCoopcode()));
			if (dbService.saveOrUpdate(user)) {
				List<String> currlist = (List<String>) dbService.execStoredProc(
						"SELECT codevalue FROM TBCODETABLECASA WHERE codename='CURR' ", null, null, 1, null);
//				System.out.println("CURRENCY COUNT "+currlist.size());
				for (String currency : currlist) {
					Tbnetamt record = new Tbnetamt();
					record.setUserbalance(BigDecimal.ZERO);
					record.setCurrency(currency);
					record.setUserid(user.getUserid());
					record.setBusinessdate(user.getDatecreated());
					record.setTransfertype("1");
					dbService.execStoredProc(null, null, null, 3, record);
					record = new Tbnetamt();
					record.setUserbalance(BigDecimal.ZERO);
					record.setCurrency(currency);
					record.setUserid(user.getUserid());
					record.setBusinessdate(user.getDatecreated());
					record.setTransfertype("2");
					dbService.execStoredProc(null, null, null, 3, record);
				} // Modified Oct.23,2018 - Added CASA - Ced <<

				// Modified 05-28-2017 - Kevin >> Added Checking if password never expire.
				if (user.getIspwneverexpire() == null
						|| (user.getIspwneverexpire() != null && user.getIspwneverexpire() == false)) {
				} else {
					// Calendar cal = new GregorianCalendar(Locale.getDefault());
					// cal.setTime(new Date());
					// cal.add(Calendar.DATE, secparam.getMaxpasswordage());
					// user.setPwexpirydate(cal.getTime());

					user.setPwexpirydate(mUserForm.getUseraccount().getPwexpirydate());
				}

				AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.ADD_OR_CREATE_USER_ACCOUNT_NON_AD),
						"Add or Create User " + user.getUsername() + ".",
						RuntimeAccess.getInstance().getRequest().getUserPrincipal().getName(), new Date(),
						AuditLogEvents.getEventModule(AuditLogEvents.ADD_OR_CREATE_USER_ACCOUNT_NON_AD));

				// Save User's Password in Password Bank
				Tbpasswordbank pbank = new Tbpasswordbank();
				pbank.setUsername(user.getUsername());
				pbank.setPassword(user.getPassword());
				pbank.setDatecreated(new Date());
				dbService.saveOrUpdate(pbank);

				List<TBRoleForm> roles = mUserForm.getRoles();
				for (TBRoleForm r : roles) {
					Tbuserroles ur = new Tbuserroles();
					TbuserrolesId id = new TbuserrolesId();
					if (r.getRoleid() != null && r.getRolename() != null) {
						id.setRoleid(r.getRoleid());
						id.setUsername(user.getUsername());
						ur.setId(id);
						ur.setRolename(r.getRolename());
						ur.setAssignedby(service.getUserName());
						ur.setAssigneddate(new Date());
						if (dbService.save(ur)) {
							System.out.println("Saved ROLES for " + user.getUsername() + " " + r.getRoleid());
						}

						// Added Routine - Insert User Access - Kevin - June 11, 2017
						params.put("roleid", r.getRoleid());
						List<Tbroleaccess> roleAccess = (List<Tbroleaccess>) dbService
								.executeListHQLQuery("FROM Tbroleaccess a WHERE a.id.roleid=:roleid", params);
						if (roleAccess != null) {
							for (Tbroleaccess roleAcc : roleAccess) {
								Tbuseraccess userAccess = new Tbuseraccess();
								TbuseraccessId userAccessId = new TbuseraccessId();
								userAccessId.setUsername(user.getUsername());
								userAccessId.setRoleid(roleAcc.getId().getRoleid());
								userAccessId.setAccessname(roleAcc.getId().getAccessname());
								userAccess.setId(userAccessId);
								userAccess.setModulename(roleAcc.getModulename());
								userAccess.setAssigneddate(new Date());
								dbService.save(userAccess);
							}
						}
					}
				}

//				//Modified 05-30-2017 - Kevin >> Added Email Notification Routine User Account Creation.
//				if(secparam != null){
//					if(secparam.getDefaultpassword() != null){
//						String defaultPassword = secparam.getDefaultpassword();
//						EmailFacade email = new EmailFacade();
//						email.sendEmail(EmailCode.USER_ACCOUNT_CREATION, user.getUsername(), defaultPassword);
//					}
//				}

				res.setFlag("success");
				res.setReturnMessage(randomPassword);
				// Modified Aug 21, 2017 - Kevin -> change to random password.
				String emailFlag = "success";
				try {
					EmailFacade email = new EmailFacade();
					String flag = email.sendEmail(EmailCode.USER_ACCOUNT_CREATION, user.getUsername(), randomPassword)
							.getFlag();
					if (flag != null && flag.equals("success")) {
						res.setSmtpFlag("success");
					} else {
						emailFlag = "failed";
					}
					
					System.out.print("aaaaaaaaaaaaaaaaaaaaa : " + flag);
				} catch (Exception e) {
					emailFlag = "failed";
				}

				/*--------Error Handling------*/
				if (emailFlag.equals("failed")) {
					String dir = RuntimeAccess.getInstance().getSession().getServletContext()
							.getRealPath("/resources/properties");
					File request = new File(dir + "/EmailSecurityErrorLogs.txt");
					request.getParentFile().mkdirs();
					try {
						if (!request.exists()) {
							request.getParentFile().mkdirs();
						}
						// Get length of file in bytes
						long fileSizeInBytes = request.length();
						// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
						long fileSizeInKB = fileSizeInBytes / 1024;
						// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
						long fileSizeInMB = fileSizeInKB / 1024;
						FileWriter fw = new FileWriter(request, true);

						if (fileSizeInMB < 5) {
							fw.write(">>>>Email Sending Failed: "
									+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
							fw.write(System.getProperty("line.separator"));
							fw.write("  User Account Creation -->  " + "Username: " + user.getUsername()
									+ " , Temporary Password: " + randomPassword);
							fw.write(System.getProperty("line.separator"));
							fw.write(System.getProperty("line.separator"));
							fw.write(System.getProperty("line.separator"));
							fw.close();
						} else {
							PrintWriter writer = new PrintWriter(request);
							writer.print("");
							writer.close();
						}
						fw.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				return res;
			}
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, ADInquiryServiceImpl.class);
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public CreateUserForm saveMemberCredentials(String cifNo, String role) {
		CreateUserForm res = new CreateUserForm();
		DBService dbService = new DBServiceImpl();
		DBService dbServiceCIF = new DBServiceImplCIF();
		Map<String, Object> params = HQLUtil.getMap();
		Tbcifindividual indiv = new Tbcifindividual();
		Tbcifmain cifMain = new Tbcifmain();
		NoGenerator generator = new NoGenerator();
		params.put("cifNo", cifNo);
		
		System.out.println("IM IN ");
		try {
			cifMain = (Tbcifmain) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifNo", params);
			indiv = (Tbcifindividual) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifindividual WHERE cifno=:cifNo", params);
			String fullname = indiv.getFirstname() + " " + indiv.getMiddlename() + " " + indiv.getLastname();
			
			String userName = generator.generateMemberUserName(cifNo);
			System.out.println("userName : " + userName);
			
			String randomPassword = PasswordGeneratorUtil.generatePassword();
			Tbuser user = new Tbuser();
			user.setUsername(userName);
			user.setEmailadd(indiv.getEmailaddress());
			user.setCompanycode(cifMain.getCompanyCode());
			user.setUpdatedby(service.getUserName());
			user.setIsactive(true);
			user.setIschangepwrequired(true);
			user.setIslocked(false);
			user.setIsloggedon(false);
			user.setIsdisabled(false);
			user.setIssuspended(false);
			user.setDatecreated(new Date());
			user.setDateupdated(new Date());
			user.setPassword(UserUtil.sha1(randomPassword));// random password
			user.setIspwneverexpire(true);
			user.setFullname(fullname);
			user.setFirstname(indiv.getFirstname());
			user.setMiddlename(indiv.getMiddlename());
			user.setLastname(indiv.getLastname());
			user.setUserType("3");// MEMBER
			user.setIsactivedirectorymember(false);
			if (dbService.saveOrUpdate(user)) {
			// Save User's Password in Password Bank
			Tbpasswordbank pbank = new Tbpasswordbank();
			pbank.setUsername(user.getUsername());
			pbank.setPassword(user.getPassword());
			pbank.setDatecreated(new Date());
			dbService.saveOrUpdate(pbank);

//			List<TBRoleForm> roles = mUserForm.getRoles();
//			for (TBRoleForm r : roles) {
//				Tbuserroles ur = new Tbuserroles();
//				TbuserrolesId id = new TbuserrolesId();
//				if (r.getRoleid() != null && r.getRolename() != null) {
//					id.setRoleid(r.getRoleid());
//					id.setUsername(user.getUsername());
//					ur.setId(id);
//					ur.setRolename(r.getRolename());
//					ur.setAssignedby(service.getUserName());
//					ur.setAssigneddate(new Date());
//					if (dbService.save(ur)) {
//						System.out.println("Saved ROLES for " + user.getUsername() + " " + r.getRoleid());
//					}
//
//					// Added Routine - Insert User Access - Kevin - June 11, 2017
//					params.put("roleid", r.getRoleid());
//					@SuppressWarnings("unchecked")
//					List<Tbroleaccess> roleAccess = (List<Tbroleaccess>) dbService
//							.executeListHQLQuery("FROM Tbroleaccess a WHERE a.id.roleid=:roleid", params);
//					if (roleAccess != null) {
//						for (Tbroleaccess roleAcc : roleAccess) {
//							Tbuseraccess userAccess = new Tbuseraccess();
//							TbuseraccessId userAccessId = new TbuseraccessId();
//							userAccessId.setUsername(user.getUsername());
//							userAccessId.setRoleid(roleAcc.getId().getRoleid());
//							userAccessId.setAccessname(roleAcc.getId().getAccessname());
//							userAccess.setId(userAccessId);
//							userAccess.setModulename(roleAcc.getModulename());
//							userAccess.setAssigneddate(new Date());
//							dbService.save(userAccess);
//						}
//					}
//				}
//			}
			
			res.setFlag("success");
			res.setReturnMessage(randomPassword);
			String emailFlag = "success";
			try {
				EmailFacade email = new EmailFacade();
				String flag = email.sendEmailForMemberAndCompanyApplication(EmailCode.MEMBERSHIP_APPLICATION, cifNo, userName, randomPassword);
				if (flag != null && flag.equals("success")) {
					res.setSmtpFlag("success");
				} else {
					emailFlag = "failed";
				}
			} catch (Exception e) {
				emailFlag = "failed";
			}

			/*--------Error Handling------*/
//			if (emailFlag.equals("failed")) {
//				String dir = RuntimeAccess.getInstance().getSession().getServletContext()
//						.getRealPath("/resources/properties");
//				File request = new File(dir + "/EmailSecurityErrorLogs.txt");
//				request.getParentFile().mkdirs();
//				try {
//					if (!request.exists()) {
//						request.getParentFile().mkdirs();
//					}
//					// Get length of file in bytes
//					long fileSizeInBytes = request.length();
//					// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
//					long fileSizeInKB = fileSizeInBytes / 1024;
//					// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
//					long fileSizeInMB = fileSizeInKB / 1024;
//					FileWriter fw = new FileWriter(request, true);
//
//					if (fileSizeInMB < 5) {
//						fw.write(">>>>Email Sending Failed: "
//								+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
//						fw.write(System.getProperty("line.separator"));
//						fw.write("  User Account Creation -->  " + "Username: " + user.getUsername()
//								+ " , Temporary Password: " + randomPassword);
//						fw.write(System.getProperty("line.separator"));
//						fw.write(System.getProperty("line.separator"));
//						fw.write(System.getProperty("line.separator"));
//						fw.close();
//					} else {
//						PrintWriter writer = new PrintWriter(request);
//						writer.print("");
//						writer.close();
//					}
//					fw.close();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
			return res;
		}
	} catch (Exception e) {
		LoggerUtil.exceptionError(e, ADInquiryServiceImpl.class);
		e.printStackTrace();
	}
	return res;
	}
}
