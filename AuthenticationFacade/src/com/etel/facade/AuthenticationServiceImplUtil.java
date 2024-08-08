package com.etel.facade;

import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.OperationNotSupportedException;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import com.etel.utils.SystemUtil;

public class AuthenticationServiceImplUtil {
	
	static DirContext ldapContext;
	
	//Error Codes
	private static final int USERNAME_NOT_FOUND = 0x525;
	private static final int INVALID_PASSWORD = 0x52e;
	private static final int NOT_PERMITTED = 0x530;
	private static final int PASSWORD_EXPIRED = 0x532;
	private static final int ACCOUNT_DISABLED = 0x533;
	private static final int ACCOUNT_EXPIRED = 0x701;
	private static final int PASSWORD_NEEDS_RESET = 0x773;
	private static final int ACCOUNT_LOCKED = 0x775; 
	
	private static final Pattern ERROR_MESSAGE_PATTERN = Pattern.compile(".*\\s([0-9a-f]{3}).*");

	/**
	 * Login User using Active Directory
	 * 
	 * Legend 
	 * return 0 if successful 
	 * return 1 if not successful 
	 * return 2 if user is suspended 
	 * return 3 if user is locked
	 * 
	 * @author Kenny Marlo Cid
	 * @param username
	 * @param password
	 * 
	 */
	public int loginUserUsingAD(String username, String password) {
//		DBService dbService = new DBServiceImplCIF();
//		Tbsecurityparams secparam = (Tbsecurityparams) dbService.executeUniqueHQLQuery(querySecurityParams, null);
		int errorCode = 0;
		String result = null;
		NamingEnumeration<SearchResult> answer = null;
		
		try {
//			String ldapUrl = secparam.getLdapurl() == null ? "" : secparam.getLdapurl();
//			String adDomain = secparam.getAddomain() == null ? "" : secparam.getAddomain();
			String ldapUrl = SystemUtil.getPropertyValue("activeDirectoryConfig.properties", "ldapUrl");
			String adDomain = SystemUtil.getPropertyValue("activeDirectoryConfig.properties", "adDomain");
			

			String principalName = username + "@" + adDomain;
			
			Hashtable<String, String> ldapEnv = new Hashtable<String, String>();
			ldapEnv.put(Context.INITIAL_CONTEXT_FACTORY,
					"com.sun.jndi.ldap.LdapCtxFactory");
			ldapEnv.put(Context.PROVIDER_URL, ldapUrl);
			// ldapEnv.put(Context.PROVIDER_URL, "ldap://10.88.50.251:389");
			ldapEnv.put(Context.SECURITY_AUTHENTICATION, "simple");
			// ldapEnv.put(Context.SECURITY_PRINCIPAL,
			// "cn=kenny marlo b. cid, cn=users, dc=eteligent, dc=com");
			// ldapEnv.put(Context.SECURITY_CREDENTIALS, "DbAdmink?.08");
			ldapEnv.put(Context.SECURITY_PRINCIPAL, principalName);
			ldapEnv.put(Context.SECURITY_CREDENTIALS, password);

			ldapContext = new InitialDirContext(ldapEnv);
			
			// Create the search controls
			SearchControls searchCtrls = new SearchControls();

			// Specify the attributes to return
			String[] returnedAttrs = { "sn", "givenName", "samAccountName",
					"samAccountType", "userPrincipalName" };
			searchCtrls.setReturningAttributes(returnedAttrs);

			// Specify the LDAP search scope
			searchCtrls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// Specify the LDAP Search Filter
			String searchFilter = "(&(userPrincipalName=" + principalName
					+ ")(objectClass=user))";

			// Specify the base for the search
			// String searchBase = "dc=eteligent, dc=com";
			
			String searchBase = "dc=" + adDomain.replace(".", ",dc=");

			// Initial counter to total the results
			int totalResults = 0;

			// Search for objects using the filter
			answer = ldapContext.search(
					searchBase, searchFilter, searchCtrls);
			
			if (!answer.hasMore()) {
				System.out.println("Cannot locate user information for "
						+ username);
				errorCode = USERNAME_NOT_FOUND;
				
				return errorCode;

			}
			String mAccountName = null;
			// Loop through the search results
			while (answer.hasMoreElements()) {
				SearchResult sr = (SearchResult) answer.next();

				totalResults++;
				System.out.println(">>>>>>>>>>> " + sr.getName());
				Attributes attrs = sr.getAttributes();
				mAccountName = sr.getName().toString();
				System.out
						.println(">>>>>>>>>>> " + attrs.get("samAccountName"));
				System.out
						.println(">>>>>>>>>>> " + attrs.get("samAccountType"));
			}
			System.out.println("<<<<<<<<<<<<<<<<< Total Results: "
					+ totalResults);

			ldapContext.close();

		} catch (NamingException e) {
			if( (e instanceof AuthenticationException) || (e instanceof OperationNotSupportedException) ){
				// Parse the error message and return the error code
				return handleNamingException(e);
				
				
			}else{
				
			}
		}finally {
			if(answer != null){
				try{
					answer.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			if(ldapContext != null){
				try{
					ldapContext.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return errorCode;
		
	}


	private int handleNamingException(NamingException e) {
		int errorCode = parseSubErrorCode(e.getMessage());
		// Log the error message
		System.out.println(subCodeToLogMessage(errorCode));
		return errorCode;
	}


	private String subCodeToLogMessage(int errorCode) {
		switch(errorCode){
			case USERNAME_NOT_FOUND:
	            return "User was not found in directory";
	        case INVALID_PASSWORD:
	            return "Supplied password was invalid";
	        case NOT_PERMITTED:
	            return "User not permitted to logon at this time";
	        case PASSWORD_EXPIRED:
	            return "Password has expired";
	        case ACCOUNT_DISABLED:
	            return "Account is disabled";
	        case ACCOUNT_EXPIRED:
	            return "Account expired";
	        case PASSWORD_NEEDS_RESET:
	            return "User must reset password";
	        case ACCOUNT_LOCKED:
	            return "Account locked";
		}
		
		return "Unknown (error code " + Integer.toHexString(errorCode) +")";
	}


	/**
	   * This method parse the error code from the exception message and return the error code
	   * For Ex: Authentication Exception is of the format - 
	   * "[LDAP: error code 49 - 80090308: LdapErr: DSID-0C090334, comment: AcceptSecurityContext error, data 773, vece"
	   * 773 is the Hex Error Code that need be captured
	   * Refer the java Matcher and Pattern classes for capturing the groups in the matched string
	   * @param message
	   * @return
	   */
	private static int parseSubErrorCode(String message) {
		Matcher m = ERROR_MESSAGE_PATTERN.matcher(message);

	      if (m.matches()) {
	          //Error code is in hex, so parse it for base 16
	          return Integer.parseInt(m.group(1), 16);
	      }

	      return -1;
	}
}
