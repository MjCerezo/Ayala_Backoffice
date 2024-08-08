package com.etel.dataentryutil;

import java.util.Map;

import com.coopdb.data.Tbcooperative;
import com.coopdb.data.Tbmembershipapp;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.forms.FormValidation;
import com.etel.utils.EmailUtil;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class sendEmail {
	private static DBService dbService = new DBServiceImpl();
	private static Map<String, Object> param = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	public static void sendEmailNotification(Tbmembershipapp app, String membershipid, String coopcode){
		try {
			param.put("encoder", app.getEncodedby());
			param.put("coopcode", app.getCoopcode());
			Tbcooperative coop = (Tbcooperative) dbService.executeUniqueHQLQuery("FROM Tbcooperative WHERE coopcode=:coopcode",
					param);
			String emailSubj = coop.getId().getCoopname()+" - Membership Application - "+app.getMembershipappid();
			String bodyMessage = EmailUtil.readHtml("memberApprovalNotification.html");
			if(bodyMessage.contains("P[cooperative]")){
				bodyMessage = bodyMessage.replace("P[cooperative]", coop.getId().getCoopname());
	    	}
			if(bodyMessage.contains("P[membershipid]")){
				bodyMessage = bodyMessage.replace("P[membershipid]", membershipid);
	    	}
			if(bodyMessage.contains("P[headcommremarks]")){
				bodyMessage = bodyMessage.replace("P[headcommremarks]", app.getEdcomapproverremarks());
	    	}
			FormValidation f = EmailUtil.sendEmail(app.getEmailaddress(), null, null, emailSubj, bodyMessage);
			System.out.println(f.getErrorMessage());
			System.out.println(f.getFlag());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
