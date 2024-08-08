package com.etel.email;

import java.util.List;

import com.etel.email.forms.EmailForm;
import com.etel.email.forms.TBEmailParamsForm;
import com.etel.forms.FormValidation;
import com.etel.utils.UserUtil;
import com.coopdb.data.Tbemailformats;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class.  All
 * public methods will be exposed to the client.  Their return
 * values and parameters will be passed to the client or taken
 * from the client, respectively.  This will be a singleton
 * instance, shared between all requests. 
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL, String, Exception).
 * LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level.
 * For info on these levels, look for tomcat/log4j documentation
 */
@ExposeToClient
public class EmailFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
	EmailService emailSrvc = new EmailServiceImpl();
	
    public EmailFacade() {
       super(INFO);
    }
    /**Send Email (SMTP)
	 * @author Kevin Uriarte
	 * @param emailCode - Required parameter. e.g. "<b><font color='#303des'>EM1</font></b>"
	 * @param userName - Required if(emailCode = "<b><font color='#303des'>EM1"</font>-Existing Session</b> ; 
	 * <br>"<b><font color='#303des'>EM2</font>"-Password Reset Request</b> ; <br>"<b><font color='#303des'>EM3</font>"-Activation of User Account</b> ; 
	 * <br>"<b><font color='#303des'>EM4</font>"-Password Reset</b> ; <br>"<b><font color='#303des'>EM5</font>"-User Account Creation</b>), otherwise parameter can be null or empty string.
	 * @param changePassword - Required if (emailCode = "<b><font color='#303des'>EM4</font>"-Password Reset</b> ; "<b><font color='#303des'>EM5</font>"-User Account Creation</b>), otherwise parameter can be null or empty string.
	 * @return <b>{@link FormValidation}</b> ( flag = success / failed ; errorMessage = Exception )
	 * */
    public FormValidation sendEmail(String emailCode, String userName, String changePassword) {
       return emailSrvc.sendEmail(emailCode, userName, changePassword);
    }
    /**
     * Save Email to TBSMTP
     * @param {@link EmailForm}
     *@return String - success otherwise failed
     * */
	public String saveEmailSMTP(EmailForm emailform) {
		return emailSrvc.saveEmailSMTP(emailform);
	}

    public List<Tbemailformats> getEmailFormatList(){
        return emailSrvc.getEmailFormatList();
    }
    
    public String saveEmailFormat(Tbemailformats format){
        return emailSrvc.saveEmailFormat(format);
    }
    
    public String deleteEmailFormat(String emailcode){
        return emailSrvc.deleteEmailFormat(emailcode);
    }
    
    public List<TBEmailParamsForm> getEmailParamsList(){
        return emailSrvc.getEmailParamsList();
    }
    
    public String saveEmailParams(TBEmailParamsForm addEmParams, TBEmailParamsForm updateEmParams, String newOrEdited){
        return emailSrvc.saveEmailParams(addEmParams,updateEmParams,newOrEdited);
    }
    
    public String deleteEmailParams(String emailtype, String emailcode, String username){
        return emailSrvc.deleteEmailParams(emailtype,emailcode,username);
    }
    
    public FormValidation validateEmailFormat(Tbemailformats format,String newOrEdited){
        return emailSrvc.validateEmailFormat(format,newOrEdited);
    }
    
    public List<String> getUsernameList(){
        return UserUtil.getUsernameList();
    }
    public List<Tbemailformats> getEmailCodeList(){
        return emailSrvc.getEmailCodeList();
    }
    
    public FormValidation validateEmailParams(TBEmailParamsForm emParams){
        return emailSrvc.validateEmailParams(emParams);
    }
    
    public String sendEmailForMemberAndCompanyApplication(String emailCode, String cifno, String userName, String password){
        return emailSrvc.sendEmailForMemberAndCompanyApplication(emailCode, cifno, userName, password);
    }
    
    public String sendEmailFoLoanApplicationApprover(String emailCode, String appno){
        return emailSrvc.sendEmailFoLoanApplicationApprover(emailCode, appno);
    }  
}
