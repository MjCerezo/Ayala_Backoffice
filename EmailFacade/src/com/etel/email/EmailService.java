package com.etel.email;

import java.util.List;

import com.etel.email.forms.EmailForm;
import com.etel.email.forms.TBEmailParamsForm;
import com.etel.forms.FormValidation;
import com.coopdb.data.Tbemailformats;

public interface EmailService {

	FormValidation sendEmail(String emailCode, String userName, String changePassword);

	String saveEmailSMTP(EmailForm emailform);

	List<Tbemailformats> getEmailFormatList();

	String saveEmailFormat(Tbemailformats format);

	String deleteEmailFormat(String emailcode);

	List<TBEmailParamsForm> getEmailParamsList();

	String saveEmailParams(TBEmailParamsForm addEmParams, TBEmailParamsForm updateEmParams, String newOrEdited);

	String deleteEmailParams(String emailtype, String emailcode, String username);

	FormValidation validateEmailFormat(Tbemailformats format, String newOrEdited);

	List<Tbemailformats> getEmailCodeList();

	FormValidation validateEmailParams(TBEmailParamsForm emParams);
	
	String sendEmailForMemberAndCompanyApplication(String emailCode, String cifno, String userName, String password);

	String sendEmailFoLoanApplicationApprover(String emailCode, String appno);
}
