package com.etel.facade;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.coopdb.data.Tbpasswordbank;
import com.coopdb.data.Tbsecurityparams;
import com.coopdb.data.Tbuser;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class SecurityServiceImplUtil {
	
	private static int PASSWORD_LIMIT = 8;

	public SecurityServiceImplUtil(){
		DBService dbService = new DBServiceImpl();
		Tbsecurityparams param = (Tbsecurityparams) dbService.executeUniqueHQLQuery("FROM Tbsecurityparams", null);
		setPASSWORD_LIMIT(param.getPasswordhistorycount());
	}
	
	public static int getPASSWORD_LIMIT() {
		return PASSWORD_LIMIT;
	}

	public static void setPASSWORD_LIMIT(int pASSWORD_LIMIT) {
		PASSWORD_LIMIT = pASSWORD_LIMIT;
	}

	@SuppressWarnings("unchecked")
	public static int validatePassword(String username, String password){
		
		int status = 0;
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("username", username.trim());
		List<Tbpasswordbank> passwordList = null;
		
		
		try{
			passwordList = (List<Tbpasswordbank>) dbService.executeListHQLQuery("FROM Tbpasswordbank where username = :username order by datecreated asc", params);
			
			System.out.println("Password List Size: " + passwordList.size());
			
			if(passwordList.size() == 0){
				Tbpasswordbank newPass = new Tbpasswordbank();
				newPass.setUsername(username);
				newPass.setPassword(password);
				newPass.setDatecreated(new Date());
				if(dbService.saveOrUpdate(newPass)){
					System.out.println("Initial Entry of Password for user " + username);
				}
			}
			
			if(!passwordList.isEmpty() && passwordList.size() > 0){
				boolean isMatch = false;
				for(Tbpasswordbank p : passwordList){
					if(p.getPassword().equalsIgnoreCase(password)){
						isMatch = true;
					}
				}
				
				if(isMatch){
					status = 1;
				}else{
					if(passwordList.size() == PASSWORD_LIMIT){
						Tbpasswordbank p = passwordList.get(0);
						dbService.delete(p);
						
						Tbpasswordbank newPass = new Tbpasswordbank();
						newPass.setUsername(username);
						newPass.setPassword(password);
						newPass.setDatecreated(new Date());
						if(dbService.saveOrUpdate(newPass)){
							System.out.println("Deleted Last Password Entry and Saving New Password..");
						}
					}else{
						Tbpasswordbank newPass = new Tbpasswordbank();
						newPass.setUsername(username);
						newPass.setPassword(password);
						newPass.setDatecreated(new Date());
						if(dbService.saveOrUpdate(newPass)){
							System.out.println("Saving New Password...");
						}
					}
				}
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return status;
	}
	
	public static String resetUserRecord(){
		String status = "failed";
		SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean( "securityService" );
		
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("username", service.getUserName().trim());
		
		// End Invalidate Session
				
		try{
			Tbuser user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser where username = :username", params);
			if(user != null){
				user.setIslocked(false);
				user.setIsloggedon(false);
				user.setIssuspended(false);
				user.setInvalidattemptscount(0);
				user.setInvalidattempip("");
				if(dbService.saveOrUpdate(user)){
					System.out.println("Reset Users Session on Force Logout");
					
					// Invalidate Session 
					HttpSession session = RuntimeAccess.getInstance().getRequest().getSession(false);
					// HttpSession session = RuntimeAccess.getInstance().getSession();
					if(session != null){
						session.invalidate();
						status = "success";
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return status;
	}
}
