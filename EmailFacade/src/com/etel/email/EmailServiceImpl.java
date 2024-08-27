package com.etel.email;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.email.forms.EmailForm;
import com.etel.email.forms.TBEmailParamsForm;
import com.etel.forms.FormValidation;
import com.etel.utils.EmailUtil;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.cifsdb.data.Tbcifcorporate;
import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbcompany;
import com.coopdb.data.Tbemailformats;
import com.coopdb.data.Tbemailparams;
import com.coopdb.data.TbemailparamsId;
import com.coopdb.data.Tbgroup;
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbuser;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author Kevin
 * */
public class EmailServiceImpl implements EmailService {
	private DBService dbService = new DBServiceImpl();
	private DBService dbServiceCIF = new DBServiceImplCIF();
	//EMAIL CODE - "EM" + int
	private static final int EXISTING_SESSION = 1;
	private static final int PASSWORD_RESET_REQUEST = 2;
	private static final int ACTIVATION_OF_USER_ACCOUNT = 3;
	private static final int PASSWORD_RESET = 4;
	private static final int USER_ACCOUNT_CREATION = 5;
	private static final int MEMBERSHIP_APPLICATION = 6;
	private static final int COMPANY_APPLICATION = 7;
	private static final int LOAN_APPLICATION_APPROVER = 8;
	
	
	SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean( "securityService" );
	
	/**Send Email (SMTP)
	 * @author Kevin Uriarte
	 * @param emailCode - Required parameter. e.g. "<b><font color='#303des'>EM1</font></b>"
	 * @param userName - Required if(emailCode = "<b><font color='#303des'>EM1"</font>-Existing Session</b> ; 
	 * <br>"<b><font color='#303des'>EM2</font>"-Password Reset Request</b> ; <br>"<b><font color='#303des'>EM3</font>"-Activation of User Account</b> ; 
	 * <br>"<b><font color='#303des'>EM4</font>"-Password Reset</b> ; <br>"<b><font color='#303des'>EM5</font>"-User Account Creation</b>), otherwise parameter can be null or empty string.
	 * @param changePassword - Required if (emailCode = "<b><font color='#303des'>EM4</font>"-Password Reset</b> ; "<b><font color='#303des'>EM5</font>"-User Account Creation</b>), otherwise parameter can be null or empty string.
	 * @return <b>{@link FormValidation}</b> ( flag = success / failed ; errorMessage = Exception )
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public FormValidation sendEmail(String emailCode, String userName, String changePassword) {
		FormValidation retForm = new FormValidation();
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbuser> tbUserList = new ArrayList<Tbuser>();
		Tbuser tbUser = new Tbuser();
		Tbemailformats emailFormat = new Tbemailformats();
		List<Tbemailparams> emailParams = new ArrayList<Tbemailparams>();
		String username = (userName==null?"":userName).toUpperCase();
		params.put("emailCode", emailCode);
		params.put("username", username);
		String userFirstName = "";
		String userTerminatedIp = "";
		String userLastLogonDate = "";
		try {
			tbUserList = (List<Tbuser>) dbService.executeListHQLQuery("FROM Tbuser", null);
			tbUser = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username AND isdisabled<>'true'", params);
			if(tbUser !=null){
				userFirstName = tbUser.getFirstname() == null ? "" : tbUser.getFirstname();
				userTerminatedIp = tbUser.getTerminatedsessionip() == null ? "" : tbUser.getTerminatedsessionip();
				userLastLogonDate = tbUser.getLastlogondate() == null ? "" : new SimpleDateFormat("M/d/yyyy hh:mm a").format(tbUser.getLastlogondate());
			}
			emailFormat = (Tbemailformats) dbService.executeUniqueHQLQuery("FROM Tbemailformats WHERE emailcode=:emailCode", params);
			String emlcode = emailCode.substring(2);
			
			StringBuilder recipient = new StringBuilder();
			StringBuilder CC = new StringBuilder();
			StringBuilder BCC = new StringBuilder();
			
			//-----RECIPIENT----
			if (emailFormat.getRecipient()!= null && emailFormat.getRecipient().equalsIgnoreCase("customer")) {
				recipient.append("");
			} else if (emailFormat.getRecipient()!= null && emailFormat.getRecipient().equalsIgnoreCase("user")) {
				if(tbUser !=null){
					recipient.append(tbUser.getEmailadd());
				}
			} else if (emailFormat.getRecipient()!= null && emailFormat.getRecipient().equalsIgnoreCase("secadmin")) {
				params.put("groupCode", emailFormat.getGroupcode());
				tbUserList = (List<Tbuser>) dbService.executeListHQLQuery("FROM Tbuser WHERE groupcode=:groupCode AND isdisabled<>'true'", params);
				if(tbUserList != null){
					for (Tbuser usr : tbUserList) {
						recipient.append(usr.getEmailadd()+",");
					}
				}
			} else if (emailFormat.getRecipient()!= null && emailFormat.getRecipient().equalsIgnoreCase("sysadmin")) {
				params.put("groupCode", emailFormat.getGroupcode());
				tbUserList = (List<Tbuser>) dbService.executeListHQLQuery("FROM Tbuser WHERE groupcode=:groupCode AND isdisabled<>'true'", params);
				if(tbUserList != null){
					for (Tbuser usr : tbUserList) {
						recipient.append(usr.getEmailadd()+",");
					}
				}
			} else if (emailFormat.getRecipient()!= null && emailFormat.getRecipient().equalsIgnoreCase("group")) {
				params.put("groupCode", emailFormat.getGroupcode());
				tbUserList = (List<Tbuser>) dbService.executeListHQLQuery("FROM Tbuser WHERE groupcode=:groupCode AND isdisabled<>'true'", params);
				if(tbUserList != null){
					for (Tbuser usr : tbUserList) {
						recipient.append(usr.getEmailadd()+",");
					}
				}
			} else if (emailFormat.getRecipient()!= null && emailFormat.getRecipient().equalsIgnoreCase("team")) {
				recipient.append(emailFormat.getTeamemail());
			}
			
			switch (Integer.valueOf(emlcode)) {
			case EXISTING_SESSION: 
				if(emailFormat != null){
					//----SUBJECT----
					String subject = emailFormat.getSubject() == null ? "" : emailFormat.getSubject().replace("P[username]", username);
					
					//----BODY----
					String bodyMessage = EmailUtil.readHtml("existingSession.html");
					if(bodyMessage.contains("P[userAccountFirstName]")){
						bodyMessage = bodyMessage.replace("P[userAccountFirstName]", userFirstName);
			    	}
			    	if(bodyMessage.contains("P[lastLoginIP]")){
			    		bodyMessage = bodyMessage.replace("P[lastLoginIP]", userTerminatedIp);
			    	}
			    	if(bodyMessage.contains("P[lastLoginDateTime]")){
			    		bodyMessage = bodyMessage.replace("P[lastLoginDateTime]", userLastLogonDate);
			    	}
			    	if(bodyMessage.contains("P[newLoginIP]")){
			    		bodyMessage = bodyMessage.replace("P[newLoginIP]", UserUtil.getUserIp());
			    	}
			    	if(bodyMessage.contains("P[newLoginDateTime]")){
			    		bodyMessage = bodyMessage.replace("P[newLoginDateTime]", new SimpleDateFormat("M/d/yyyy hh:mm a").format(new Date()));
			    	}
			    	
			    	//----CC----
			    	params.put("emailCode", EmailCode.EXISTING_SESSION);
			    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='CC' AND a.id.emailcode=:emailCode", params);
			    	if(emailParams!=null){
			    		for(Tbemailparams em : emailParams){
			    			if(em.getEmailadd()!=null){
			    				CC.append(em.getEmailadd()+",");
			    			}
			    		}
			    	}
			    	
			    	if(emailCode!=null) {
				    	//----BCC----
				    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='BCC' AND a.id.emailcode=:emailCode", params);
				    	if(emailParams!=null){
				    		for(Tbemailparams em : emailParams){
				    			if(em.getEmailadd()!=null){
				    				BCC.append(em.getEmailadd()+",");
				    			}
				    		}
				    	}
			    	}
		
			    	//----Send Email---
					retForm = EmailUtil.sendEmail(recipient.toString(), CC.toString(), BCC.toString(), subject, bodyMessage);
				}
				break;
			case PASSWORD_RESET_REQUEST:
				//copy from PASSWORD_RESET method -Mar
				if(emailFormat != null){
					//----SUBJECT----
					String subject = emailFormat.getSubject() == null ? "" : emailFormat.getSubject().replace("P[username]", username);

					//----BODY----
					String bodyMessage = EmailUtil.readHtml("passwordReset.html");
					if(bodyMessage.contains("P[genPassword]")){
						bodyMessage = bodyMessage.replace("P[genPassword]", changePassword);
			    	}
					
					//----CC----
			    	params.put("emailCode", EmailCode.PASSWORD_RESET_REQUEST);
			    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='CC' AND a.id.emailcode=:emailCode", params);
			    	if(emailParams!=null){
			    		for(Tbemailparams em : emailParams){
			    			if(em.getEmailadd()!=null){
			    				CC.append(em.getEmailadd()+",");
			    			}
			    		}
			    	}
			    	//----BCC----
			    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='BCC' AND a.id.emailcode=:emailCode", params);
			    	if(emailParams!=null){
			    		for(Tbemailparams em : emailParams){
			    			if(em.getEmailadd()!=null){
			    				BCC.append(em.getEmailadd()+",");
			    			}
			    		}
			    	}
			    	//----Send Email---
					retForm = EmailUtil.sendEmail(recipient.toString(), CC.toString(), BCC.toString(), subject, bodyMessage);
				}
				
				/*//orignal
				 * if(emailFormat != null){ //----SUBJECT---- String subject =
				 * emailFormat.getSubject() == null ? "" :
				 * emailFormat.getSubject().replace("P[username]", username);
				 * 
				 * //----BODY---- String bodyMessage =
				 * EmailUtil.readHtml("passwordResetRequest.html");
				 * if(bodyMessage.contains("P[username]")){ bodyMessage =
				 * bodyMessage.replace("P[username]", username); }
				 * 
				 * //----CC---- params.put("emailCode", EmailCode.PASSWORD_RESET_REQUEST);
				 * emailParams = (List<Tbemailparams>) dbService.
				 * executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='CC' AND a.id.emailcode=:emailCode"
				 * , params); if(emailParams!=null){ for(Tbemailparams em : emailParams){
				 * if(em.getEmailadd()!=null){ CC.append(em.getEmailadd()+","); } } }
				 * //----BCC---- emailParams = (List<Tbemailparams>) dbService.
				 * executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='BCC' AND a.id.emailcode=:emailCode"
				 * , params); if(emailParams!=null){ for(Tbemailparams em : emailParams){
				 * if(em.getEmailadd()!=null){ BCC.append(em.getEmailadd()+","); } } }
				 * //----Send Email--- retForm = EmailUtil.sendEmail(recipient.toString(),
				 * CC.toString(), BCC.toString(), subject, bodyMessage); }
				 */
				break;
			case ACTIVATION_OF_USER_ACCOUNT:
				if(emailFormat != null){
					//----SUBJECT----
					String subject = emailFormat.getSubject() == null ? "" : emailFormat.getSubject().replace("P[username]", username);
					
					//----BODY----
					String bodyMessage = EmailUtil.readHtml("activationUserAccount.html");
					
					//----CC----
			    	params.put("emailCode", EmailCode.ACTIVATION_OF_USER_ACCOUNT);
			    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='CC' AND a.id.emailcode=:emailCode", params);
			    	if(emailParams!=null){
			    		for(Tbemailparams em : emailParams){
			    			if(em.getEmailadd()!=null){
			    				CC.append(em.getEmailadd()+",");
			    			}
			    		}
			    	}
			    	//----BCC----
			    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='BCC' AND a.id.emailcode=:emailCode", params);
			    	if(emailParams!=null){
			    		for(Tbemailparams em : emailParams){
			    			if(em.getEmailadd()!=null){
			    				BCC.append(em.getEmailadd()+",");
			    			}
			    		}
			    	}
			    	//----Send Email---
					retForm = EmailUtil.sendEmail(recipient.toString(), CC.toString(), BCC.toString(), subject, bodyMessage);
				}
				break;
			case PASSWORD_RESET:
				//Password Reset
				if(emailFormat != null){
					//----SUBJECT----
					String subject = emailFormat.getSubject() == null ? "" : emailFormat.getSubject().replace("P[username]", username);

					//----BODY----
					String bodyMessage = EmailUtil.readHtml("passwordReset.html");
					if(bodyMessage.contains("P[genPassword]")){
						bodyMessage = bodyMessage.replace("P[genPassword]", changePassword);
			    	}
					
					//----CC----
			    	params.put("emailCode", EmailCode.PASSWORD_RESET);
			    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='CC' AND a.id.emailcode=:emailCode", params);
			    	if(emailParams!=null){
			    		for(Tbemailparams em : emailParams){
			    			if(em.getEmailadd()!=null){
			    				CC.append(em.getEmailadd()+",");
			    			}
			    		}
			    	}
			    	//----BCC----
			    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='BCC' AND a.id.emailcode=:emailCode", params);
			    	if(emailParams!=null){
			    		for(Tbemailparams em : emailParams){
			    			if(em.getEmailadd()!=null){
			    				BCC.append(em.getEmailadd()+",");
			    			}
			    		}
			    	}
			    	//----Send Email---
					retForm = EmailUtil.sendEmail(recipient.toString(), CC.toString(), BCC.toString(), subject, bodyMessage);
				}
				break;
			case USER_ACCOUNT_CREATION:
				//Password Reset
				if(emailFormat != null){
					//----SUBJECT----
					String subject = emailFormat.getSubject() == null ? "" : emailFormat.getSubject().replace("P[username]", username);
					
					//----BODY----
					String bodyMessage = EmailUtil.readHtml("userAccountCreation.html");
					if(bodyMessage.contains("P[username]")){
						bodyMessage = bodyMessage.replace("P[username]", username);
			    	}
					if(bodyMessage.contains("P[defaultPassword]")){
						bodyMessage = bodyMessage.replace("P[defaultPassword]", changePassword);
			    	}
					//----CC----
			    	params.put("emailCode", EmailCode.USER_ACCOUNT_CREATION);
			    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='CC' AND a.id.emailcode=:emailCode", params);
			    	if(emailParams!=null){
			    		for(Tbemailparams em : emailParams){
			    			if(em.getEmailadd()!=null){
			    				CC.append(em.getEmailadd()+",");
			    			}
			    		}
			    	}
			    	//----BCC----
			    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='BCC' AND a.id.emailcode=:emailCode", params);
			    	if(emailParams!=null){
			    		for(Tbemailparams em : emailParams){
			    			if(em.getEmailadd()!=null){
			    				BCC.append(em.getEmailadd()+",");
			    			}
			    		}
			    	}
			    	//----Send Email---
					retForm = EmailUtil.sendEmail(recipient.toString(), CC.toString(), BCC.toString(), subject, bodyMessage);
				}
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retForm;
	}
	/**Save Email to TBSMTP, to be used for email scheduler */
	@Override
	public String saveEmailSMTP(EmailForm emailform) {
		String flag = "failed";
//		DBService dbService = new DBServiceImplLOS();
//		Tbcifmain main = new Tbcifmain();
//		Tbsmtp smtp = new Tbsmtp();
//		Tbteams team = new Tbteams();
//		Tbuser user = new Tbuser();
//		List<Tbemailparams> emailParams = new ArrayList<Tbemailparams>();
//		Map<String, Object> params = HQLUtil.getMap();
//		params.put("emailCode", emailform.getEmailcode());
//		String emlcode = emailform.getEmailcode().substring(2);
//		String bodyMessage = "";
//		String subject = "";
//		String recipient = "";
//		String officer="";
//		String encoder="";
//		String teamname="";
//		String cc="";
//		StringBuilder CC = new StringBuilder();
//		StringBuilder BCC = new StringBuilder();
//		
//		try {
//			Tbemailformats emailFormat = (Tbemailformats) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbemailformats WHERE emailcode=:emailCode", params);
//			if(emailform.getCifno() != null && emailform.getCifno() != ""){
//				params.put("cifno", emailform.getCifno());
//				main = (Tbcifmain) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbcifmain WHERE cifno=:cifno", params);
//				if(main != null && main.getAssignedto() != null){
//					encoder = main.getAssignedto();
//				}
//			}
//			
//			switch (Integer.valueOf(emlcode)) {
//			case CIF_ASSIGNMENT: 
//				// ----RECIPIENT----
//				params.put("teamcode", main.getOriginatingteam());
////				team = (Tbteams) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbteams WHERE id.teamcode=:teamcode", params);
////				if(team != null){
////					teamname = team.getId().getTeamname();
////					if(team.getIsofficeravailable() != null && team.getIsofficeravailable() == false){
////						if(team.getBackupofficer() != null){
////							officer = team.getBackupofficer();
////						}
////					}else{
////						if(team.getOfficer() != null){
////							officer = team.getOfficer();
////						}
////					}
////					System.out.println("officer");
////					params.put("username", officer);
////					Tbuser user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE id.username=:username", params);
////					if(user != null){
////						recipient= user.getEmailadd()+",";
////					}
////				}
//				params.put("username", main.getAssignedto());
//				user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE id.username=:username", params);
//				if(user != null){
//					recipient= user.getEmailadd()+",";
//				}
//				
//				// ----BODY----
//				bodyMessage = EmailUtil.readHtml("cifAssignment.html");
//				if(bodyMessage.contains("P[userAccountFirstName]")){
//					bodyMessage = bodyMessage.replace("P[userAccountFirstName]", user.getFirstname());
//		    	}
//				if(bodyMessage.contains("P[cifno]")){
//					bodyMessage = bodyMessage.replace("P[cifno]", main.getCifno());
//		    	}
//				
//				// ----SUBJECT----
//				subject = emailFormat.getSubject() == null ? "": emailFormat.getSubject().replace("P[cifdetails]",main.getCifno() + " - " + main.getFullname());
//				
//				//----CC----
//		    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='CC' AND a.id.emailcode=:emailCode", params);
//		    	if(emailParams!=null){
//		    		for(Tbemailparams em : emailParams){
//		    			if(em.getEmailadd()!=null){
//		    				CC.append(em.getEmailadd()+",");
//		    			}
//		    		}
//		    	}
//		    	//----BCC----
//		    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='BCC' AND a.id.emailcode=:emailCode", params);
//		    	if(emailParams!=null){
//		    		for(Tbemailparams em : emailParams){
//		    			if(em.getEmailadd()!=null){
//		    				BCC.append(em.getEmailadd()+",");
//		    			}
//		    		}
//		    	}
//		    	smtp.setCifno(emailform.getCifno());
//		    	smtp.setFlag(0);
//		    	smtp.setRecipient(recipient);
//		    	smtp.setBody(bodyMessage);
//				smtp.setSubject(subject);
//				smtp.setDateadded(new Date());
//				smtp.setAddedby(service.getUserName());
//				smtp.setCc(CC.toString());
//				smtp.setBcc(BCC.toString());
//				smtp.setEmailcode(emailform.getEmailcode());
//				if(dbService.saveOrUpdate(smtp)){
//					flag="success";
//				}
//				break;
//			case OFFICER_OF_THE_DAY: 
//				//----RECIPIENT----
//				params.put("teamcode", emailform.getTeamcode());
//				team = (Tbteams) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbteams WHERE id.teamcode=:teamcode", params);
//				officer = "";
//				if(team != null){
//					teamname = team.getId().getTeamname();
//					if(team.getIsofficeravailable() != null && team.getIsofficeravailable() == false){
//						if(team.getBackupofficer() != null){
//							officer = team.getBackupofficer();
//						}
//					}else{
//						if(team.getOfficer() != null){
//							officer = team.getOfficer();
//						}
//					}
//					
//					params.put("username", officer);
//					user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE id.username=:username", params);
//					if(user != null){
//						recipient= user.getEmailadd()+",";
//					}
//				}
//				
//				// ----SUBJECT----
//				subject = emailFormat.getSubject() == null ? "": emailFormat.getSubject().replace("P[date]","("+new SimpleDateFormat("M/d/yyyy").format(new Date())+" - Team : "+teamname+")");
//				
//				// ----BODY----
//				bodyMessage = EmailUtil.readHtml("officerOfTheDay.html");
//				if(bodyMessage.contains("P[userAccountFirstName]")){
//					bodyMessage = bodyMessage.replace("P[userAccountFirstName]", user.getFirstname());
//		    	}
//				
//				//----CC----
//		    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='CC' AND a.id.emailcode=:emailCode", params);
//		    	if(emailParams!=null){
//		    		for(Tbemailparams em : emailParams){
//		    			if(em.getEmailadd()!=null){
//		    				CC.append(em.getEmailadd()+",");
//		    			}
//		    		}
//		    	}
//		    	//----BCC----
//		    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='BCC' AND a.id.emailcode=:emailCode", params);
//		    	if(emailParams!=null){
//		    		for(Tbemailparams em : emailParams){
//		    			if(em.getEmailadd()!=null){
//		    				BCC.append(em.getEmailadd()+",");
//		    			}
//		    		}
//		    	}
//		    	smtp.setCifno(emailform.getCifno());
//		    	smtp.setFlag(0);
//		    	smtp.setRecipient(recipient);
//		    	smtp.setBody(bodyMessage);
//				smtp.setSubject(subject);
//				smtp.setDateadded(new Date());
//				smtp.setAddedby(service.getUserName());
//				smtp.setCc(CC.toString());
//				smtp.setBcc(BCC.toString());
//				smtp.setEmailcode(emailform.getEmailcode());
//				if(dbService.saveOrUpdate(smtp)){
//					flag="success";
//				}
//				break;
//			case SUBMIT_FOR_APPROVAL: 
//				// ----SUBJECT----
//				subject = emailFormat.getSubject() == null ? "": emailFormat.getSubject().replace("P[cifdetails]",main.getCifno() + " - " + main.getFullname());
//				
//				// ----BODY----
//				bodyMessage = EmailUtil.readHtml("submitForApproval.html");
//				if(bodyMessage.contains("P[cifno]")){
//					bodyMessage = bodyMessage.replace("P[cifno]", main.getCifno());
//		    	}
//				//----RECIPIENT----
//				params.put("teamcode", main.getOriginatingteam());
//				team = (Tbteams) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbteams WHERE id.teamcode=:teamcode", params);
//				if(team != null){
//					teamname = team.getId().getTeamname();
//					if(team.getIsofficeravailable() != null && team.getIsofficeravailable() == false){
//						if(team.getBackupofficer() != null){
//							officer = team.getBackupofficer();
//						}
//					}else{
//						if(team.getOfficer() != null){
//							officer = team.getOfficer();
//						}
//					}
//					
//					params.put("username", officer);
//					user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE id.username=:username", params);
//					if(user != null){
//						recipient= user.getEmailadd()+",";
//					}
//				}
//				//----CC----
//		    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='CC' AND a.id.emailcode=:emailCode", params);
//		    	if(emailParams!=null){
//		    		for(Tbemailparams em : emailParams){
//		    			if(em.getEmailadd()!=null){
//		    				CC.append(em.getEmailadd()+",");
//		    			}
//		    		}
//		    	}
//		    	//----BCC----
//		    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='BCC' AND a.id.emailcode=:emailCode", params);
//		    	if(emailParams!=null){
//		    		for(Tbemailparams em : emailParams){
//		    			if(em.getEmailadd()!=null){
//		    				BCC.append(em.getEmailadd()+",");
//		    			}
//		    		}
//		    	}
//		    	smtp.setCifno(emailform.getCifno());
//		    	smtp.setFlag(0);
//		    	smtp.setRecipient(recipient);
//		    	smtp.setBody(bodyMessage);
//				smtp.setSubject(subject);
//				smtp.setDateadded(new Date());
//				smtp.setAddedby(service.getUserName());
//				smtp.setCc(CC.toString());
//				smtp.setBcc(BCC.toString());
//				smtp.setEmailcode(emailform.getEmailcode());
//				if(dbService.saveOrUpdate(smtp)){
//					flag="success";
//				}
//				break;
//			
//			case REQUEST_TO_EDIT: 
//				// ----SUBJECT----
//				subject = emailFormat.getSubject() == null ? "": emailFormat.getSubject().replace("P[cifdetails]",main.getCifno() + " - " + main.getFullname());
//				
//				//----BODY----
//				bodyMessage = EmailUtil.readHtml("requestToEdit.html");
//				if(bodyMessage.contains("P[cifno]")){
//					bodyMessage = bodyMessage.replace("P[cifno]", main.getCifno());
//		    	}
//				
//				//----RECIPIENT----
//				params.put("teamcode", main.getOriginatingteam());
//				team = (Tbteams) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbteams WHERE id.teamcode=:teamcode", params);
//				if(team != null){
//					teamname = team.getId().getTeamname();
//					if(team.getIsofficeravailable() != null && team.getIsofficeravailable() == false){
//						if(team.getBackupofficer() != null){
//							officer = team.getBackupofficer();
//						}
//					}else{
//						if(team.getOfficer() != null){
//							officer = team.getOfficer();
//						}
//					}
//				
//					//officer emailadd	
//					params.put("username", officer);
//					Tbuser offUser = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE id.username=:username", params);
//						if(offUser != null){
//							recipient= offUser.getEmailadd()+",";
//						}
//					//encoder emailadd	
//					params.put("encodername", encoder);
//					Tbuser enUser = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE id.username=:encodername", params);
//					if(enUser != null){
//						cc = enUser.getEmailadd()+",";
//					}
//				}
//				//----CC----
//		    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='CC' AND a.id.emailcode=:emailCode", params);
//		    	if(emailParams!=null){
//		    		for(Tbemailparams em : emailParams){
//		    			if(em.getEmailadd()!=null){
//		    				CC.append(em.getEmailadd()+",");
//		    			}
//		    		}
//		    	}
//		    	//----BCC----
//		    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='BCC' AND a.id.emailcode=:emailCode", params);
//		    	if(emailParams!=null){
//		    		for(Tbemailparams em : emailParams){
//		    			if(em.getEmailadd()!=null){
//		    				BCC.append(em.getEmailadd()+",");
//		    			}
//		    		}
//		    	}
//		    	smtp.setCifno(emailform.getCifno());
//		    	smtp.setFlag(0);
//		    	smtp.setRecipient(recipient);
//		    	smtp.setBody(bodyMessage);
//				smtp.setSubject(subject);
//				smtp.setDateadded(new Date());
//				smtp.setAddedby(service.getUserName());
//				smtp.setCc(cc + CC.toString());
//				smtp.setBcc(BCC.toString());
//				smtp.setEmailcode(emailform.getEmailcode());
//				if(dbService.saveOrUpdate(smtp)){
//					flag="success";
//				}
//				break;
//			
//			case RETURNED_CIF_RECORD: 
//				// ----SUBJECT----
//				subject = emailFormat.getSubject() == null ? "": emailFormat.getSubject().replace("P[cifdetails]",main.getCifno() + " - " + main.getFullname());
//				
//				//----BODY----
//				bodyMessage = EmailUtil.readHtml("returnedCifRecord.html");
//				if(bodyMessage.contains("P[cifno]")){
//					bodyMessage = bodyMessage.replace("P[cifno]", main.getCifno());
//		    	}
//				
//				//----RECIPIENT----
//				params.put("teamcode", main.getOriginatingteam());
//				team = (Tbteams) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbteams WHERE id.teamcode=:teamcode", params);
//				if(team != null){
//					teamname = team.getId().getTeamname();
//					if(team.getIsofficeravailable() != null && team.getIsofficeravailable() == false){
//						if(team.getBackupofficer() != null){
//							officer = team.getBackupofficer();
//						}
//					}else{
//						if(team.getOfficer() != null){
//							officer = team.getOfficer();
//						}
//					}
//				
//					//encoder emailadd	
//					params.put("encodername", encoder);
//					Tbuser enUser = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE id.username=:encodername", params);
//					if(enUser != null){
//						recipient = enUser.getEmailadd()+",";
//					}
//				}
//				//----CC----
//		    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='CC' AND a.id.emailcode=:emailCode", params);
//		    	if(emailParams!=null){
//		    		for(Tbemailparams em : emailParams){
//		    			if(em.getEmailadd()!=null){
//		    				CC.append(em.getEmailadd()+",");
//		    			}
//		    		}
//		    	}
//		    	//----BCC----
//		    	emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailtype='BCC' AND a.id.emailcode=:emailCode", params);
//		    	if(emailParams!=null){
//		    		for(Tbemailparams em : emailParams){
//		    			if(em.getEmailadd()!=null){
//		    				BCC.append(em.getEmailadd()+",");
//		    			}
//		    		}
//		    	}
//		    	smtp.setCifno(emailform.getCifno());
//		    	smtp.setFlag(0);
//		    	smtp.setRecipient(recipient);
//		    	smtp.setBody(bodyMessage);
//				smtp.setSubject(subject);
//				smtp.setDateadded(new Date());
//				smtp.setAddedby(service.getUserName());
//				smtp.setCc(CC.toString());
//				smtp.setBcc(BCC.toString());
//				smtp.setEmailcode(emailform.getEmailcode());
//				if(dbService.saveOrUpdate(smtp)){
//					flag="success";
//				}
//				break;	
//			}
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return flag;
	}
	
	
	/**Get Email Format List*/
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbemailformats> getEmailFormatList() {
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbemailformats> list = new ArrayList<Tbemailformats>();
		try {
			list = (List<Tbemailformats>) dbService.executeListHQLQuery("FROM Tbemailformats ORDER BY CAST(SUBSTRING(emailcode, 3, 5) AS int)", null);
			if(list != null && !list.isEmpty()){
				for(Tbemailformats e : list){
					if(e.getRecipient() != null){
						params.put("recipient",e.getRecipient());
						Tbcodetable rec = (Tbcodetable) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbcodetable WHERE id.codename='RECIPIENT' AND id.codevalue=:recipient", params);
						if(rec != null){
							e.setRecipient(rec.getDesc1());
						}
					}
					if(e.getGroupcode() != null){
						params.put("grpcode",e.getGroupcode());
						Tbgroup grpCode = (Tbgroup) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbgroup WHERE id.groupcode=:grpcode", params);
						if(grpCode != null){
							e.setGroupcode(grpCode.getGroupname());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**Save Email Format*/
	@Override
	public String saveEmailFormat(Tbemailformats format) {
		String flag ="failed";
		Map<String, Object> params = HQLUtil.getMap();
		params.put("emailCode", format.getEmailcode());
		try {
			Tbemailformats emFormat = (Tbemailformats) dbService.executeUniqueHQLQuery("FROM Tbemailformats WHERE emailcode=:emailCode", params);
			if(emFormat == null){
				Tbemailformats newEmFormat = new Tbemailformats();
				newEmFormat.setEmailcode(format.getEmailcode());
				newEmFormat.setFormatname(format.getFormatname());
				newEmFormat.setRecipient(format.getRecipient());
				newEmFormat.setSender(format.getSender());
				if(format.getRecipient().equalsIgnoreCase("secadmin") || format.getRecipient().equalsIgnoreCase("sysadmin")){
					newEmFormat.setGroupcode(format.getGroupcode());
				}else{
					newEmFormat.setGroupcode(null);
				}
				if(format.getRecipient().equalsIgnoreCase("team")){
					newEmFormat.setTeamemail(format.getTeamemail());
				}else{
					newEmFormat.setTeamemail(null);
				}
				newEmFormat.setSubject(format.getSubject());
				newEmFormat.setCreateddate(new Date());
				newEmFormat.setCreatedby(service.getUserName());
				if(dbService.save(newEmFormat)){
					flag = "success";
				}
			}else{
				emFormat.setEmailcode(format.getEmailcode());
				emFormat.setFormatname(format.getFormatname());
				emFormat.setRecipient(format.getRecipient());
				emFormat.setSender(format.getSender());
				emFormat.setSubject(format.getSubject());
				if(format.getRecipient().equalsIgnoreCase("secadmin") || format.getRecipient().equalsIgnoreCase("sysadmin")){
					emFormat.setGroupcode(format.getGroupcode());
				}else{
					emFormat.setGroupcode(null);
				}
				if(format.getRecipient().equalsIgnoreCase("team")){
					emFormat.setTeamemail(format.getTeamemail());
				}else{
					emFormat.setTeamemail(null);
				}
				
				if(dbService.saveOrUpdate(emFormat)){
					flag = "success";
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String deleteEmailFormat(String emailcode) {
		String flag ="failed";
		Map<String, Object> params = HQLUtil.getMap();
		params.put("emailCode", emailcode);
		try {
			if(emailcode!=null) {
				Tbemailformats emFormat = (Tbemailformats) dbService.executeUniqueHQLQuery("FROM Tbemailformats WHERE emailcode=:emailCode", params);
				if(emFormat != null){
					if(dbService.delete(emFormat)){
						List<Tbemailparams> emailParams = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams a WHERE a.id.emailcode=:emailCode", params);
						if(emailParams != null){
							for(Tbemailparams e :emailParams){
								dbService.delete(e);
							}
						}
						flag = "success";
					}
				}else{
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**Get Email Params List*/
	@SuppressWarnings("unchecked")
	@Override
	public List<TBEmailParamsForm> getEmailParamsList() {
		List<TBEmailParamsForm> form = new ArrayList<TBEmailParamsForm>();
		try {
			List<Tbemailparams> list = (List<Tbemailparams>) dbService.executeListHQLQuery("FROM Tbemailparams", null);
			if(list != null){
				for(Tbemailparams emParams: list){
					TBEmailParamsForm emParamsForm = new TBEmailParamsForm();
					emParamsForm.setEmailtype(emParams.getId().getEmailtype());
					emParamsForm.setEmailcode(emParams.getId().getEmailcode());
					emParamsForm.setUsername(emParams.getId().getUsername());
					emParamsForm.setEmailadd(emParams.getEmailadd());
					emParamsForm.setCreateddate(emParams.getCreateddate());
					emParamsForm.setCreatedby(emParams.getCreatedby());
					form.add(emParamsForm);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	/**Save Email Params*/
	@Override
	public String saveEmailParams(TBEmailParamsForm addEmParams, TBEmailParamsForm updateEmParams, String newOrEdited) {
		String flag ="failed";
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(newOrEdited != null && newOrEdited.equalsIgnoreCase("new")){
				Tbemailparams emParam = new Tbemailparams();
				TbemailparamsId emParamId = new TbemailparamsId();
				emParamId.setEmailcode(addEmParams.getEmailcode());
				emParamId.setEmailtype(addEmParams.getEmailtype());
				emParamId.setUsername(addEmParams.getUsername());
				emParam.setId(emParamId);
				emParam.setEmailadd(addEmParams.getEmailadd());
				emParam.setCreateddate(new Date());
				emParam.setCreatedby(UserUtil.securityService.getUserName());
				if(dbService.save(emParam)){
					flag = "success";
				}
			}else{
				params.put("emailType", updateEmParams.getEmailtype());
				params.put("emailCode", updateEmParams.getEmailcode());
				params.put("username", updateEmParams.getUsername());
				Tbemailparams emailParams = (Tbemailparams) dbService.executeUniqueHQLQuery("FROM Tbemailparams a WHERE a.id.emailcode=:emailCode AND a.id.emailtype=:emailType AND a.id.username=:username", params);
				if(emailParams!=null){
//					emailParams.getId().setEmailtype(addEmParams.getEmailtype());
//					emailParams.getId().setEmailcode(addEmParams.getEmailcode());
					emailParams.getId().setUsername(addEmParams.getUsername());;
					emailParams.setEmailadd(addEmParams.getEmailadd());
					if(dbService.saveOrUpdate(emailParams)){
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
	public String deleteEmailParams(String emailtype, String emailcode, String username) {
		String flag ="failed";
		Map<String, Object> params = HQLUtil.getMap();
		params.put("emailType", emailtype);
		params.put("emailCode", emailcode);
		params.put("username", username);
		try {
			Tbemailparams emailParams = (Tbemailparams) dbService.executeUniqueHQLQuery("FROM Tbemailparams a WHERE a.id.emailcode=:emailCode AND a.id.emailtype=:emailType AND a.id.username=:username", params);
			if(emailParams != null){
				if(dbService.delete(emailParams)){
					flag = "success";
				}
			}else{
				flag="success";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public FormValidation validateEmailFormat(Tbemailformats format, String newOrEdited) {
		FormValidation values = new FormValidation();
		StringBuilder sb = new StringBuilder();
		boolean hasErrors = false;
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (format != null) {
				if (format != null && newOrEdited.equalsIgnoreCase("new")) {
					params.put("emailCode", format.getEmailcode() == null ? "" : format.getEmailcode());
					Tbemailformats emFormat = (Tbemailformats) dbService.executeUniqueHQLQuery("FROM Tbemailformats WHERE emailcode=:emailCode", params);
					if (emFormat != null) {
						hasErrors = true;
						sb.append("<b>FAILED! </b><b>Email Code</b> already exists. Please provide a different email code.");
					} else {
						if (format.getEmailcode() == null) {
							hasErrors = true;
							sb.append("<b>FAILED! </b><b>Email Code</b> cannot be empty.");
						}
						if (format.getFormatname() == null) {
							hasErrors = true;
							sb.append("<br>");
							sb.append("<b>FAILED! </b><b>Format Name</b> cannot be empty.");
						}

						if (format.getRecipient() == null) {
							hasErrors = true;
							sb.append("<br>");
							sb.append("<b>FAILED! </b><b>Recipient</b> cannot be empty.");
						}

						if (format.getSubject() == null) {
							hasErrors = true;
							sb.append("<br>");
							sb.append("<b>FAILED! </b><b>Subject</b> cannot be empty.");
						}
						if (format.getRecipient()!=null && (format.getRecipient().equalsIgnoreCase("sysadmin")|| format.getRecipient().equalsIgnoreCase("secadmin"))) {
							if (format.getGroupcode() == null) {
								hasErrors = true;
								sb.append("<br>");
								sb.append("<b>FAILED! </b><b>Group</b> cannot be empty.");
							}
						}

						if (format.getRecipient()!=null && format.getRecipient().equalsIgnoreCase("team")) {
							if (format.getTeamemail() == null) {
								hasErrors = true;
								sb.append("<br>");
								sb.append("<b>FAILED! </b><b>Team Email</b> cannot be empty.");
							}
						}
					}
				} else {
					if (format.getEmailcode() == null) {
						hasErrors = true;
						sb.append("<b>FAILED! </b><b>Email Code</b> cannot be empty.");
					}
					if (format.getFormatname() == null) {
						hasErrors = true;
						sb.append("<br>");
						sb.append("<b>FAILED! </b><b>Format Name</b> cannot be empty.");
					}

					if (format.getRecipient() == null) {
						hasErrors = true;
						sb.append("<br>");
						sb.append("<b>FAILED! </b><b>Recipient</b> cannot be empty.");
					}

					if (format.getSubject() == null) {
						hasErrors = true;
						sb.append("<br>");
						sb.append("<b>FAILED! </b><b>Subject</b> cannot be empty.");
					}
					if (format.getRecipient().equalsIgnoreCase("sysadmin")
							|| format.getRecipient().equalsIgnoreCase("secadmin")) {
						if (format.getGroupcode() == null) {
							hasErrors = true;
							sb.append("<br>");
							sb.append("<b>FAILED! </b><b>Group</b> cannot be empty.");
						}
					}

					if (format.getRecipient().equalsIgnoreCase("team")) {
						if (format.getTeamemail() == null) {
							hasErrors = true;
							sb.append("<br>");
							sb.append("<b>FAILED! </b><b>Team Email</b> cannot be empty.");
						}
					}
				}
			} else {
				hasErrors = true;
				sb.append("<i><b>FAILED! </b> Cannot create an empty data.</i>");
			}
			if (hasErrors) {
				values.setFlag("failed");
				values.setErrorMessage(sb.toString());
			} else {
				values.setFlag("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbemailformats> getEmailCodeList() {
		DBService dbService = new DBServiceImpl();
		List<Tbemailformats> emailCodeList = new ArrayList<Tbemailformats>();
		try {
			emailCodeList = (List<Tbemailformats>) dbService.executeListHQLQuery("FROM Tbemailformats", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emailCodeList;
	}

	@Override
	public FormValidation validateEmailParams(TBEmailParamsForm emParams) {
		FormValidation values = new FormValidation();
		StringBuilder sb = new StringBuilder();
		boolean hasErrors = false;
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (emParams != null) {
				params.put("emailType", emParams.getEmailtype());
				params.put("emailCode", emParams.getEmailcode());
				params.put("username", emParams.getUsername());
				Tbemailparams emailParams = (Tbemailparams) dbService.executeUniqueHQLQuery(
						"FROM Tbemailparams a WHERE a.id.emailcode=:emailCode AND a.id.emailtype=:emailType AND a.id.username=:username",
						params);
				if (emailParams != null) {
					hasErrors = true;
					sb.append("<b>FAILED! </b><b>Parameter</b> already exists!");
				} else {
					if (emParams.getEmailtype() == null) {
						hasErrors = true;
						sb.append("<b>FAILED! </b><b>Email Type</b> cannot be empty.");
					}
					if (emParams.getEmailcode() == null) {
						hasErrors = true;
						sb.append("<br>");
						sb.append("<b>FAILED! </b><b>Email Code</b> cannot be empty.");
					}

					if (emParams.getUsername() == null) {
						hasErrors = true;
						sb.append("<br>");
						sb.append("<b>FAILED! </b><b>Username</b> cannot be empty.");
					}

					if (emParams.getEmailadd() == null) {
						hasErrors = true;
						sb.append("<br>");
						sb.append("<b>FAILED! </b><b>Email Address</b> cannot be empty.");
					}
				}
			} else {
				hasErrors = true;
				sb.append("<i><b>FAILED! </b> Cannot create an empty data.</i>");
			}
			if (hasErrors) {
				values.setFlag("failed");
				values.setErrorMessage(sb.toString());
			} else {
				values.setFlag("success");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return values;
	}
	@Override
	public String sendEmailForMemberAndCompanyApplication(String emailCode, String cifno, String userName, String password) {
		Map<String, Object> params = HQLUtil.getMap();
		Tbemailformats emailFormat = new Tbemailformats();
		Tbcifmain cifMain = new Tbcifmain();
		Tbcifindividual individual = new Tbcifindividual();
		Tbcifcorporate corporate = new Tbcifcorporate();
		String emailAddress = null;
		
		params.put("emailCode", emailCode);
		params.put("cifno", cifno);
		emailFormat = (Tbemailformats) dbService.executeUniqueHQLQuery("FROM Tbemailformats WHERE emailcode=:emailCode", params);
		cifMain = (Tbcifmain) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", params);
		
		String result = "failed";
		String emailcode = emailCode.substring(2);

		switch (Integer.valueOf(emailcode)) {
		case COMPANY_APPLICATION:
			if(cifMain.getCustomertype().equals("1")) {
				corporate = (Tbcifcorporate) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifcorporate WHERE cifno=:cifno", params);
				
				if(corporate.getContacttype1().equals("5")) {
					emailAddress = corporate.getContactvalue1();
				}
				
				if(corporate.getContacttype2().equals("5")) {
					emailAddress = corporate.getContactvalue2();
				}
				
				System.out.print("getContacttype1 : " + corporate.getContacttype1());
				System.out.print("getContactvalue1 : " + corporate.getContactvalue1());
				System.out.print("getContacttype2 : " + corporate.getContacttype2());
				System.out.print("getContactvalue2 : " + corporate.getContactvalue2());
				System.out.print("emailAddress : " + emailAddress);
				
				if(emailAddress != null ) {
					//----SUBJECT----
					String subject = emailFormat.getSubject() == null ? "" : emailFormat.getSubject();
					
					//----BODY----
					
					String bodyMessage = EmailUtil.readHtml("CompanyApproved.html");
					if(bodyMessage.contains("P[name]")){
						bodyMessage = bodyMessage.replace("P[name]", cifMain.getFullname());
			    	}
					
					if(bodyMessage.contains("P[companyName]")){
						bodyMessage = bodyMessage.replace("P[companyName]", userName);
			    	}
			    	
					if(bodyMessage.contains("P[userName]")){
						bodyMessage = bodyMessage.replace("P[userName]", password);
			    	}
					
					if(bodyMessage.contains("P[password]")){
						bodyMessage = bodyMessage.replace("P[password]", password);
			    	}
					result = EmailUtil.sendEmail(emailAddress, null, null, subject, bodyMessage).getFlag();	
			    	if (result.equals("success")) {
			    		return "success";
			    	}else {
			    		return "failed";
			    	}
				}				
			}
			
			break;
			
		case MEMBERSHIP_APPLICATION:
			if(cifMain.getCustomertype().equals("2")) {
				
				individual = (Tbcifindividual) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifindividual WHERE cifno=:cifno", params);
				if(individual.getEmailaddress() != null ) {
					//----SUBJECT----
					String subject = emailFormat.getSubject() == null ? "" : emailFormat.getSubject();
					
					//----BODY----
					
					String bodyMessage = EmailUtil.readHtml("MembershipApproved.html");
					if(bodyMessage.contains("P[name]")){
						bodyMessage = bodyMessage.replace("P[name]", cifMain.getFullname());
			    	}
					
					if(bodyMessage.contains("P[companyName]")){
						bodyMessage = bodyMessage.replace("P[companyName]", "Test Company Name");
			    	}
			    	
					if(bodyMessage.contains("P[userName]")){
						bodyMessage = bodyMessage.replace("P[userName]", userName);
			    	}
					
					if(bodyMessage.contains("P[password]")){
						bodyMessage = bodyMessage.replace("P[password]", password);
			    	}
					result = EmailUtil.sendEmail(individual.getEmailaddress(), null, null, subject, bodyMessage).getFlag();	
			    	if (result.equals("success")) {
			    		return "success";
			    	}else {
			    		return "failed";
			    	}
				}				
			}
			
			break;
	    	
		}
		return result;
	}
	@Override
	public String sendEmailFoLoanApplicationApprover(String emailCode, String appno) {
		Map<String, Object> params = HQLUtil.getMap();
		Tbemailformats emailFormat = new Tbemailformats();
		Tblstapp app = new Tblstapp();
		params.put("emailCode", emailCode);
		params.put("appno", appno);
		
		emailFormat = (Tbemailformats) dbService.executeUniqueHQLQuery("FROM Tbemailformats WHERE emailcode=:emailCode", params);
		app = (Tblstapp) dbService.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", params);
		
		params.put("loanproduct", app.getLoanproduct());
		Tbloanproduct product = (Tbloanproduct) dbService.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode=:loanproduct", params);
		
		String result = "failed";
		String emailcode = emailCode.substring(2);

		switch (Integer.valueOf(emailcode)) {
		case LOAN_APPLICATION_APPROVER:
			params.put("username", UserUtil.securityService.getUserName());
			Tbuser user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
			Tbaccountinfo info = (Tbaccountinfo) dbService.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno=:appno", params);
			DecimalFormat df = new DecimalFormat("#,###,###.00");
			
			if(app!=null && user.getEmailadd()!=null) {
				//----SUBJECT----
				String subject = emailFormat.getSubject() == null ? "" : emailFormat.getSubject();
				
				//----BODY----
				
				String bodyMessage = EmailUtil.readHtml("LoanApplicationForApproval.html");
				if(bodyMessage.contains("P[name]")){
					bodyMessage = bodyMessage.replace("P[name]", user.getFullname());
		    	}
				
				if(bodyMessage.contains("P[appno]")){
					bodyMessage = bodyMessage.replace("P[appno]", app.getAppno());
		    	}
				
				if(bodyMessage.contains("P[loantype]")){
					bodyMessage = bodyMessage.replace("P[loantype]", product.getProductname());
		    	}
		    	
				if(bodyMessage.contains("P[loanAmount]")){
					bodyMessage = bodyMessage.replace("P[loanAmount]", df.format(info.getFaceamt().setScale(2, BigDecimal.ROUND_HALF_UP)));
		    	}
				
				if(bodyMessage.contains("P[term]")){
					bodyMessage = bodyMessage.replace("P[term]", info.getTerm().toString());
		    	}
				
				if(bodyMessage.contains("P[monthlyAmort]")){
					bodyMessage = bodyMessage.replace("P[monthlyAmort]", df.format(info.getAmortfee().setScale(2, BigDecimal.ROUND_HALF_UP)));
		    	}
				
				result = EmailUtil.sendEmail(user.getEmailadd(), null, null, subject, bodyMessage).getFlag();	
		    	if (result.equals("success")) {
		    		return "success";
		    	}else {
		    		return "failed";
		    	}
			}
			
			break;
	    	
		}
		return result;
	}
}
