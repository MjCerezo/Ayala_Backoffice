package com.etel.api.util;

import java.io.File;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.codehaus.jackson.map.ObjectMapper;

import com.coopdb.data.Tbapilogs;
import com.coopdb.data.Tbproperties;
import com.etel.api.forms.ApiPropertiesForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.LoggerUtil;
import com.etel.utils.UserUtil;

public class APIUtil {

	public String apiUrl = "http://116.93.120.29:8080/api";
	public String apiToken = "53e658b3270d935c691efb2493439ff2";
	private static DBService dbService = new DBServiceImpl();
	
	/**
	 * --Check if url is reachable--
	 * @author Kevin 03.26.2019
	 * @return true otherwise false
	 */
	public static Boolean isURLReachable(String strUrl){
		try {
			if(strUrl == null){
				LoggerUtil.error(">>>>>>>>>>> API URL IS NULL ! <<<<<<<<<<<<", APIUtil.class);
			} else {
				URL url = new URL(strUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
	
				String code = connection.getResponseMessage();
	
				if (code.equals("OK")) {
					LoggerUtil.info(">>>>>>>>>>> CONNECTED TO : '" + strUrl + "' <<<<<<<<<<<<", APIUtil.class);
					return true;
				}else{
					LoggerUtil.error(">>>>>>>>>>> '" + strUrl + "' - CONNECTION TIMED OUT ! <<<<<<<<<<<<", APIUtil.class);
					return false;
				}
			}
		} catch (Exception e) {
			LoggerUtil.error(">>>>>>>>>>> '" + strUrl + "' - CONNECTION TIMED OUT ! <<<<<<<<<<<<", APIUtil.class);
			e.printStackTrace();
		 }
		return false;
	}
	/**
	 * --This method will bypass SSL--
	 * @author Kevin 03.26.2019
	 */
	public static void byPassSSL(){
		try {
			/*
			 * BYPASS SSL
			 */
			TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {

				}

				@Override
				public void checkServerTrusted(java.security.cert.X509Certificate[] arg0, String arg1)
						throws CertificateException {

				}
			} };

			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

			// Create all-trusting host name verifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return false;
				}
			};
			// Install the all-trusting host verifier
			HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
			/*
			 * end of the fix
			 */
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, APIUtil.class);
			e.printStackTrace();
		}
	}
	/**
	 * --Save API Logs (for Windows OS Only)--
	 * @author Kevin 03.26.2019
	 */
	public static void saveApiLogsLocal(String refno, String apitype, String requestbody, String responsebody){
		PrintWriter writer = null;
		try {
			//Save Logs to Drive C:
		    SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd hh-mma");
			Date newDate = new Date();
			String date = "";
			date = formatter1.format(newDate);
			File request = new File("c:/ACACIALOS x EBT (JSON Logs)/"+apitype+"/" + refno+ " "+apitype+" " + date + ".txt");

			request.getParentFile().mkdirs();
			for (int i = 1; request.exists(); i++) {
				request = new File(String.format("c:/ACACIALOS x EBT (JSON Logs)/"+apitype+"/" + refno+ " "+apitype+" " + date + "_%d" + ".txt", i));
			}
			
			//File Writer
			writer = new PrintWriter(request);
			if (!request.exists()) {
				request.getParentFile().mkdirs();
			}
			ObjectMapper mapper = new ObjectMapper();
			Object jsonRequest = mapper.readValue(requestbody, Object.class);
			String reqBody = mapper.writerWithDefaultPrettyPrinter()
			                               .writeValueAsString(jsonRequest);
			Object jsonResponse = mapper.readValue(responsebody, Object.class);
			String resBody = mapper.writerWithDefaultPrettyPrinter()
			                               .writeValueAsString(jsonResponse);
			writer.println("REQUEST :");
			writer.println("");
			writer.println("");
			writer.println("");
			writer.println(reqBody);
			writer.println("");
			writer.println("");
			writer.println("");
			writer.println("");
			writer.println("");
			writer.println("RESPONSE :");
			writer.println("");
			writer.println("");
			writer.println("");
			writer.println(resBody);
			writer.close();
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, APIUtil.class);
			e.printStackTrace();
		} finally {
			if(writer != null){
				writer.close();
			}
		}
	}
	/**
	 * --Get API Properties--
	 * @author Kevin 04.05.2019
	 * @return form = {@link ApiPropertiesForm}
	 */
	public static ApiPropertiesForm getApiProperties(){
		ApiPropertiesForm form = new ApiPropertiesForm();
		try {
			Tbproperties c = (Tbproperties) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbproperties", null);
			if(c != null){
				if(c.getApiurl() != null){
					form.setApiurl(c.getApiurl());
				}
				if(c.getApitoken() != null){
					form.setApitoken(c.getApitoken());
				}
			}
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, APIUtil.class);
			e.printStackTrace();
		}
		return form;
	}
	
	/**
	 * --Save new api token from gl--
	 * @author Kevin 04.05.2019
	 * @return String = "success" otherwise "failed"
	 */
	public static String saveApiToken(String token){
		try {
			if (token == null) {
				LoggerUtil.error("Api token parameter is null !", APIUtil.class);
			}else{
				Tbproperties c = (Tbproperties) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbproperties", null);
				if(c != null){
					c.setApitoken(token);
					if(dbService.saveOrUpdate(c)){
						return "success";
					}
				}
			}
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, APIUtil.class);
			e.printStackTrace();;
		}
		return "failed";
	}
	
	/**
	 * --Save API Logs to DB (TBAPILOGS)--
	 * @author Kevin 04.05.2019
	 * @return String = "success" otherwise "failed"
	 */
	public static String addApiLogs(String apitype, String appno, Boolean issuccess, String requestbody, String responsebody){
		try {
			if (apitype != null && appno != null && issuccess != null) {
				Tbapilogs log = new Tbapilogs();
				log.setApitype(apitype);
				log.setAppno(appno);
				log.setIssuccess(issuccess);
				log.setRequestbody(requestbody);
				log.setResponsebody(responsebody);
				log.setDateinvoked(new Date());
				log.setInvokedby(UserUtil.securityService.getUserName());
				if(dbService.saveOrUpdate(log)){
					return "success";
				}
			}else{
				LoggerUtil.error("Missing paramater.", APIUtil.class);
			}
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, APIUtil.class);
			e.printStackTrace();;
		}
		return "failed";
	}
	/**
	 * --Get Latest Api Logs By type and appno--
	 * @author Kevin 04.04.2019
	 * @return List<{@link Tbapilogs}>
	 * */
	public static Tbapilogs getLatestApiLogsByTypeAndAppNo(String appno, String apitype) {
		Tbapilogs log = new Tbapilogs();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(appno != null && apitype != null){
				params.put("appno", appno);
				params.put("apitype", apitype);
				Integer maxId = (Integer) dbService.executeUniqueSQLQuery("SELECT MAX(id) FROM Tbapilogs WHERE appno=:appno", params);
				if(maxId != null){
					params.put("maxId", maxId);
					log = (Tbapilogs) dbService.executeUniqueHQLQuery("FROM Tbapilogs WHERE appno=:appno AND apitype=:apitype AND id=:maxId", params);
				}
			}
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, APIUtil.class);
			e.printStackTrace();
		}
		return log;
	}
}
