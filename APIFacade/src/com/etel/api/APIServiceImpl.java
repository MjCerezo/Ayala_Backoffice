package com.etel.api;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbglentries;
import com.coopdb.data.Tbloanreleaseinst;
import com.coopdb.data.Tblstapp;
import com.etel.api.forms.AddJournalForm;
import com.etel.api.forms.AddLoanDisbursementForm;
import com.etel.api.forms.AddReceiptForm;
import com.etel.api.forms.ApiPropertiesForm;
import com.etel.api.forms.GLEntries;
import com.etel.api.forms.PDC;
import com.etel.api.forms.PostResponseForm;
import com.etel.api.forms.TransactionDetails;
import com.etel.api.util.APIUtil;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.forms.ReturnForm;
import com.etel.utils.HQLUtil;
import com.etel.utils.LoggerUtil;
import com.google.gson.GsonBuilder;

public class APIServiceImpl implements APIService {
	
	private DBService dbService = new DBServiceImpl();
	private Map<String, Object> params = HQLUtil.getMap();
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * --Add Loan Payment to Helix (API)--
	 * @author Kevin 03.26.2019
	 * @return List<{@link ReturnForm}>
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public ReturnForm addLoanDisbursement(String appno) {
		// TODO Auto-generated method stub
		ReturnForm form = new ReturnForm();
		form.setFlag("failed");
		form.setFlag("Loan disbursement interface failed !");
		DataOutputStream infWebSvcReqWriter = null;
		try {
			params.put("appno", appno);
			
			//Get API Properties
			ApiPropertiesForm apiProp = APIUtil.getApiProperties();
			
			//Check Helix connection
			if(!APIUtil.isURLReachable(apiProp.getApiurl())){
				form.setFlag("nocon");
				form.setMessage("GL API CONNECTION TIMED OUT !");
				return form;
			}
			
			//BYPASS SSL
			APIUtil.byPassSSL();
			
			String url = apiProp.getApiurl() + "/api.php?action=loan-disbursement";
			URL urlForInfWebSvc = new URL(url);
			URLConnection UrlConnInfWebSvc = urlForInfWebSvc.openConnection();
			HttpURLConnection httpUrlConnInfWebSvc = (HttpURLConnection) UrlConnInfWebSvc;
			httpUrlConnInfWebSvc.setDoOutput(true);
			httpUrlConnInfWebSvc.setRequestProperty("Content-Type", "application/json");
//			httpUrlConnInfWebSvc.setRequestProperty("token", apiProp.getApitoken());
			httpUrlConnInfWebSvc.setRequestMethod("POST");
			infWebSvcReqWriter = new DataOutputStream(httpUrlConnInfWebSvc.getOutputStream());
			
			List<AddLoanDisbursementForm> list = new ArrayList<AddLoanDisbursementForm>();
			AddLoanDisbursementForm mainform = new AddLoanDisbursementForm();
			List<TransactionDetails> txlist = new ArrayList<TransactionDetails>();
			List<PDC> pdclist = new ArrayList<PDC>();
			List<GLEntries> glEntrylist = new ArrayList<GLEntries>();
			
			Tblstapp lstapp = (Tblstapp) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tblstapp WHERE appno=:appno", params);
			List<Tbloanreleaseinst> inst = (List<Tbloanreleaseinst>) dbService.executeListHQLQuery("FROM Tbloanreleaseinst WHERE applno=:appno", params);
			Tbaccountinfo acctinfo = (Tbaccountinfo) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbaccountinfo WHERE applno=:appno", params);
			
			if(inst == null){
				form.setFlag("failed");
				form.setMessage("Please complete loan release instruction form !");
				return form;
			}
			if(acctinfo == null){
				form.setFlag("failed");
				form.setMessage("Loan account info has no data !");
				return form;
			} 
			
			mainform.setRefType("CV");
			
			if(acctinfo.getPnno() == null){
				form.setFlag("failed");
				form.setMessage("Pnno cannot be empty !");
				return form;
			}else{
				mainform.setRefNbr(acctinfo.getPnno());
			}
			if(acctinfo.getDtbook() == null){
				form.setFlag("failed");
				form.setMessage("Booking date cannot be empty !");
				return form;
			}else{
				mainform.setRefDate(sdf.format(acctinfo.getDtbook()));
			}
			if(acctinfo.getClientid() == null){
				form.setFlag("failed");
				form.setMessage("Client id cannot be empty !");
				return form;
			}else{
				mainform.setVendorID(acctinfo.getClientid());
			}
			
			if(lstapp.getCifname() == null){
				form.setFlag("failed");
				form.setMessage("CIF Name cannot be empty !");
				return form;
			}else{
				mainform.setVendorName(lstapp.getCifname());
			}
			mainform.setTotalTaxableAmt("0.00");
			mainform.setTotalVATAmt("0.00");
			mainform.setTotalWTaxAmt("0.00");
			mainform.setTotalVATWHAmt("0.00");
			mainform.setTotalAmt(acctinfo.getNetprcds() == null? "0.00" :String.valueOf(acctinfo.getNetprcds().setScale(2, RoundingMode.HALF_UP)));
			mainform.setOrigCompanyID(lstapp.getCompanycode());
			
			for(Tbloanreleaseinst i :inst){
				TransactionDetails txform = new TransactionDetails();
				if(i.getPayeename() == null){
					txform.setPayee("");
				} else {
					txform.setPayee(i.getPayeename());
				}
				txform.setDetails("LOAN RELEASE");
				
				if(i.getCheckbrstn() == null){
	//				form.setFlag("failed");
	//				form.setMessage("Loan release -  bank code cannot be empty !");
	//				return form;
					txform.setBankID("");
				}else{
					txform.setBankID(i.getCheckbrstn());
				}
				if(i.getDisposition() == null){
	//				form.setFlag("failed");
	//				form.setMessage("Loan release - Loan disposition cannot be empty !");
	//				return form;
					txform.setPaymentType("");
				} else {
					txform.setPaymentType(i.getDisposition().equals("1") ? "CASH"
							: i.getDisposition().equals("2") ? "CHECQUE" : "");
				}
				if(i.getCheckacctno() == null){
					txform.setChequeNo("");
				} else {
					txform.setChequeNo(i.getCheckacctno());
				}
				if(i.getCheckdate() == null){
					txform.setChequeDate("");
				} else {
					txform.setChequeDate(sdf.format(i.getCheckdate()));
				}
				if(i.getAmount() == null){
	//				form.setFlag("failed");
	//				form.setMessage("Loan release - Check amount cannot be empty !");
	//				return form;
					txform.setGrossAmt("0.00");
				} else {
					txform.setGrossAmt(String.valueOf(i.getAmount().setScale(2, RoundingMode.HALF_UP)));
				}
					
				txform.setRefNbr(acctinfo.getPnno());
				txform.setAccount("");
				txform.setSubAccount("");
				txform.setAPVoucherNo("");
				txform.setUnitCost(String.valueOf(i.getAmount().setScale(2, RoundingMode.HALF_UP)));
				txform.setTaxableAmt("0.00");
				txform.setVATID("");
				txform.setVATAmt("");
				txform.setWTaxID("0");
				txform.setWTaxAmt("0");
				txform.setVATWHID("0");
				txform.setVATWHAmt("0");
				txform.setTotalAmt(i.getAmount() == null? "0.00" : String.valueOf(i.getAmount().setScale(2, RoundingMode.HALF_UP)));
				txlist.add(txform);
			}
			
//			Integer receivableMaxId  = (Integer) dbService.executeUniqueSQLQuery("SELECT MAX(cireceivableid) FROM TBCIRECEIVABLESMAIN WHERE appno=:appno", params);
//			if(receivableMaxId == null){
////				form.setFlag("failed");
////				form.setMessage("Receivables cannot be empty !");
////				return form;
//				PDC pdcform = new PDC();
//				pdcform.setDraweeBank("");
//				pdcform.setCheckNo("");
//				pdcform.setAmount("");
//				pdcform.setCheckType("");
//				pdcform.setCheckDate("");
//				pdclist.add(pdcform);
//			}else{
//				//PDC
//				params.put("receivableMaxId", receivableMaxId);
//				List<Tbcireceivables> receivables = (List<Tbcireceivables>)dbService.executeListHQLQuery("FROM Tbcireceivables WHERE appno=:appno AND cireceivableid=:receivableMaxId AND receivabletype='1'", params);
//				if(receivables == null || receivables.isEmpty()){
////					form.setFlag("failed");
////					form.setMessage("Receivables cannot be empty !");
////					return form;
//					PDC pdcform = new PDC();
//					pdcform.setDraweeBank("");
//					pdcform.setCheckNo("");
//					pdcform.setAmount("");
//					pdcform.setCheckType("");
//					pdcform.setCheckDate("");
//					pdclist.add(pdcform);
//				} else {
//					for(Tbcireceivables r : receivables){
//						PDC pdcform = new PDC();
//						pdcform.setDraweeBank(r.getBankname());
//						pdcform.setCheckNo(r.getCheckno());
//						pdcform.setAmount(r.getAmount() == null? "0" : String.valueOf(r.getAmount().setScale(2, RoundingMode.HALF_UP)));
//						pdcform.setCheckType("PDC");
//						pdcform.setCheckDate(sdf.format(r.getCheckdate()));
//						pdclist.add(pdcform);
//						
//					}
//				}
//			}
			
			//GL Entries - Added 06.27.2019 - Kev
			params.put("pnno", acctinfo.getPnno());
			List<Tbglentries> glEntr = (List<Tbglentries>)dbService.executeListHQLQuery("FROM Tbglentries WHERE accountno=:pnno AND txcode='10'", params);
			if(glEntr != null && !glEntr.isEmpty()){
				for(Tbglentries g : glEntr){
					GLEntries glform = new GLEntries();
					glform.setAccount(g.getGlsl());
					glform.setSubAccount("");
					glform.setDebit(g.getDebit() == null? "" : String.valueOf(g.getDebit().setScale(2, RoundingMode.HALF_UP)));
					glform.setCredit(g.getCredit() == null? "" : String.valueOf(g.getCredit().setScale(2, RoundingMode.HALF_UP)));
					glEntrylist.add(glform);
					
				}
			}
			mainform.setTransactionDetails(txlist);
			mainform.setPDC(pdclist);
			mainform.setGLEntries(glEntrylist);
			
			list.add(mainform);
			String infWebSvcRequestString = new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(list);
			
			//  Copy Content "JSON" into
			infWebSvcReqWriter.write(infWebSvcRequestString.toString().getBytes());

			infWebSvcReqWriter.flush();
			infWebSvcReqWriter.close();
			Integer responseCode = httpUrlConnInfWebSvc.getResponseCode();
			LoggerUtil.info("\n>>>>>Application No.: "+appno+"\n>>>>>Sending 'POST' request to URL : " + url + "\n>>>>>Response Code : " + responseCode, this.getClass());

			BufferedReader infWebSvcReplyReader = new BufferedReader(
					new InputStreamReader(httpUrlConnInfWebSvc.getInputStream()));

			String line;
			String infWebSvcReplyString = "";

			while ((line = infWebSvcReplyReader.readLine()) != null) {
				infWebSvcReplyString = infWebSvcReplyString.concat(line);
			}
			
			infWebSvcReplyReader.close();
			httpUrlConnInfWebSvc.disconnect();
			
			// Save API Logs (for Windows OS Only)
			if (System.getProperty("os.name").contains("Windows")) {
				APIUtil.saveApiLogsLocal(appno, "loandisbursement", infWebSvcRequestString.toString(),
						infWebSvcReplyString.toString());
			}
			
			PostResponseForm res = new ObjectMapper().readValue(infWebSvcReplyString.toString(), PostResponseForm.class);
			if(res != null){
				if(res.getToken() != null){
					LoggerUtil.info(">>>>>GL NEW API TOKEN : "+res.getToken(), this.getClass());
					APIUtil.saveApiToken(res.getToken());
				}
				if(res.getError()!= null && res.getError().equals("true")){
					form.setFlag("failed");
					form.setMessage(res.getMessage());
					LoggerUtil.info(form.getMessage(), this.getClass());
					
					//Save API Logs to database (TBAPILOGS)
					APIUtil.addApiLogs("loandisbursement", appno, false, infWebSvcRequestString.toString(), infWebSvcReplyString.toString());

					return form;
				}
				if(res.getSuccess()!= null && res.getSuccess().equals("false")){
					form.setFlag("failed");
					form.setMessage(res.getMessage());
					LoggerUtil.info(form.getMessage(), this.getClass());
					
					//Save API Logs to database (TBAPILOGS)
					APIUtil.addApiLogs("loandisbursement", appno, false, infWebSvcRequestString.toString(), infWebSvcReplyString.toString());

					return form;
				}
				if(res.getSuccess()!= null && res.getSuccess().equals("true")){
					form.setFlag("success");
					form.setMessage("GL API - Loan disbursement successfully created.");
					LoggerUtil.info(form.getMessage(), this.getClass());
					
					//Save API Logs to database (TBAPILOGS)
					APIUtil.addApiLogs("loandisbursement", appno, true, infWebSvcRequestString.toString(), infWebSvcReplyString.toString());
					
					return form;
				}
			}
			
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, this.getClass());
			e.printStackTrace();
		} finally {
			if (infWebSvcReqWriter != null) {
				try {
					infWebSvcReqWriter.flush();
					infWebSvcReqWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return form;
	}

	/**
	 * --Add Receipt to GL (API)--
	 * @author Kevin 07.17.2019
	 * @return List<{@link ReturnForm}>
	 * */
	@Override
	public ReturnForm addReceipt(String appno, AddReceiptForm receipt) {
		// TODO Auto-generated method stub
		ReturnForm form = new ReturnForm();
		form.setFlag("failed");
		form.setFlag("Add Receipt interface failed !");
		DataOutputStream infWebSvcReqWriter = null;
		try {
			params.put("appno", appno);
			//Get API Properties
			ApiPropertiesForm apiProp = APIUtil.getApiProperties();
			
			//Check Helix connection
			if(!APIUtil.isURLReachable(apiProp.getApiurl())){
				form.setFlag("nocon");
				form.setMessage("GL API CONNECTION TIMED OUT !");
				return form;
			}
			
			//BYPASS SSL
			APIUtil.byPassSSL();
			
			String url = apiProp.getApiurl() + "/receipt?action=add_record";
			URL urlForInfWebSvc = new URL(url);
			URLConnection UrlConnInfWebSvc = urlForInfWebSvc.openConnection();
			HttpURLConnection httpUrlConnInfWebSvc = (HttpURLConnection) UrlConnInfWebSvc;
			httpUrlConnInfWebSvc.setDoOutput(true);
			httpUrlConnInfWebSvc.setRequestProperty("Content-Type", "application/json");
//			httpUrlConnInfWebSvc.setRequestProperty("token", apiProp.getApitoken());
			httpUrlConnInfWebSvc.setRequestMethod("POST");
			infWebSvcReqWriter = new DataOutputStream(httpUrlConnInfWebSvc.getOutputStream());
			String infWebSvcRequestString = new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(receipt);
			
			//  Copy Content "JSON" into
			infWebSvcReqWriter.write(infWebSvcRequestString.toString().getBytes());

			infWebSvcReqWriter.flush();
			infWebSvcReqWriter.close();
			Integer responseCode = httpUrlConnInfWebSvc.getResponseCode();
			LoggerUtil.info("\n>>>>>Application No.: "+appno+"\n>>>>>Sending 'POST' request to URL : " + url + "\n>>>>>Response Code : " + responseCode, this.getClass());

			BufferedReader infWebSvcReplyReader = new BufferedReader(
					new InputStreamReader(httpUrlConnInfWebSvc.getInputStream()));

			String line;
			String infWebSvcReplyString = "";

			while ((line = infWebSvcReplyReader.readLine()) != null) {
				infWebSvcReplyString = infWebSvcReplyString.concat(line);
			}
			
			infWebSvcReplyReader.close();
			httpUrlConnInfWebSvc.disconnect();
			
			// Save API Logs (for Windows OS Only)
			if (System.getProperty("os.name").contains("Windows")) {
				APIUtil.saveApiLogsLocal(appno, "receipt", infWebSvcRequestString.toString(),
						infWebSvcReplyString.toString());
			}
			
			PostResponseForm res = new ObjectMapper().readValue(infWebSvcReplyString.toString(), PostResponseForm.class);
			if(res != null){
				if(res.getToken() != null){
					LoggerUtil.info(">>>>>GL NEW API TOKEN : "+res.getToken(), this.getClass());
					APIUtil.saveApiToken(res.getToken());
				}
				if(res.getError()!= null && res.getError().equals("true")){
					form.setFlag("failed");
					form.setMessage(res.getMessage());
					LoggerUtil.info(form.getMessage(), this.getClass());
					
					//Save API Logs to database (TBAPILOGS)
					APIUtil.addApiLogs("receipt", appno, false, infWebSvcRequestString.toString(), infWebSvcReplyString.toString());

					return form;
				}
				if(res.getSuccess()!= null && res.getSuccess().equals("false")){
					form.setFlag("failed");
					form.setMessage(res.getMessage());
					LoggerUtil.info(form.getMessage(), this.getClass());
					
					//Save API Logs to database (TBAPILOGS)
					APIUtil.addApiLogs("receipt", appno, false, infWebSvcRequestString.toString(), infWebSvcReplyString.toString());

					return form;
				}
				if(res.getSuccess()!= null && res.getSuccess().equals("true")){
					form.setFlag("success");
//					form.setMessage("Helix API - Receipt successfully created.");
					form.setMessage(res.getMessage().substring(res.getMessage().lastIndexOf(' ') + 1));
					LoggerUtil.info("Helix API - Receipt successfully created. : " + form.getMessage(), this.getClass());
					
					//Save API Logs to database (TBAPILOGS)
					APIUtil.addApiLogs("receipt", appno, true, infWebSvcRequestString.toString(), infWebSvcReplyString.toString());
					
					return form;
				}
			}
			
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, this.getClass());
			e.printStackTrace();
		} finally {
			if (infWebSvcReqWriter != null) {
				try {
					infWebSvcReqWriter.flush();
					infWebSvcReqWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return form;
	}
	/**
	 * --Add Journal Entry to GL (API)--
	 * @author Kevin 07.17.2019
	 * @return List<{@link ReturnForm}>
	 * */
	@Override
	public ReturnForm addJournal(String appno, AddJournalForm journal) {
		// TODO Auto-generated method stub
		ReturnForm form = new ReturnForm();
		form.setFlag("failed");
		form.setFlag("Add Journal Entry interface failed !");
		DataOutputStream infWebSvcReqWriter = null;
		try {
			params.put("appno", appno);
			//Get API Properties
			ApiPropertiesForm apiProp = APIUtil.getApiProperties();
			
			//Check Helix connection
			if(!APIUtil.isURLReachable(apiProp.getApiurl())){
				form.setFlag("nocon");
				form.setMessage("GL API CONNECTION TIMED OUT !");
				return form;
			}
			
			//BYPASS SSL
			APIUtil.byPassSSL();
			
			String url = apiProp.getApiurl() + "/api.php?action=journal-entry";
			URL urlForInfWebSvc = new URL(url);
			URLConnection UrlConnInfWebSvc = urlForInfWebSvc.openConnection();
			HttpURLConnection httpUrlConnInfWebSvc = (HttpURLConnection) UrlConnInfWebSvc;
			httpUrlConnInfWebSvc.setDoOutput(true);
			httpUrlConnInfWebSvc.setRequestProperty("Content-Type", "application/json");
//			httpUrlConnInfWebSvc.setRequestProperty("token", apiProp.getApitoken());
			httpUrlConnInfWebSvc.setRequestMethod("POST");
			infWebSvcReqWriter = new DataOutputStream(httpUrlConnInfWebSvc.getOutputStream());
			String infWebSvcRequestString = new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(journal);
			
			//  Copy Content "JSON" into
			infWebSvcReqWriter.write(infWebSvcRequestString.toString().getBytes());

			infWebSvcReqWriter.flush();
			infWebSvcReqWriter.close();
			Integer responseCode = httpUrlConnInfWebSvc.getResponseCode();
			LoggerUtil.info("\n>>>>>Application No.: "+appno+"\n>>>>>Sending 'POST' request to URL : " + url + "\n>>>>>Response Code : " + responseCode, this.getClass());

			BufferedReader infWebSvcReplyReader = new BufferedReader(
					new InputStreamReader(httpUrlConnInfWebSvc.getInputStream()));

			String line;
			String infWebSvcReplyString = "";

			while ((line = infWebSvcReplyReader.readLine()) != null) {
				infWebSvcReplyString = infWebSvcReplyString.concat(line);
			}
			
			infWebSvcReplyReader.close();
			httpUrlConnInfWebSvc.disconnect();
			
			// Save API Logs (for Windows OS Only)
			if (System.getProperty("os.name").contains("Windows")) {
				APIUtil.saveApiLogsLocal(appno, "journal", infWebSvcRequestString.toString(),
						infWebSvcReplyString.toString());
			}
			
			PostResponseForm res = new ObjectMapper().readValue(infWebSvcReplyString.toString(), PostResponseForm.class);
			if(res != null){
				if(res.getToken() != null){
					LoggerUtil.info(">>>>>GL NEW API TOKEN : "+res.getToken(), this.getClass());
					APIUtil.saveApiToken(res.getToken());
				}
				if(res.getError()!= null && res.getError().equals("true")){
					form.setFlag("failed");
					form.setMessage(res.getMessage());
					LoggerUtil.info(form.getMessage(), this.getClass());
					
					//Save API Logs to database (TBAPILOGS)
					APIUtil.addApiLogs("journal", appno, false, infWebSvcRequestString.toString(), infWebSvcReplyString.toString());

					return form;
				}
				if(res.getSuccess()!= null && res.getSuccess().equals("false")){
					form.setFlag("failed");
					form.setMessage(res.getMessage());
					LoggerUtil.info(form.getMessage(), this.getClass());
					
					//Save API Logs to database (TBAPILOGS)
					APIUtil.addApiLogs("journal", appno, false, infWebSvcRequestString.toString(), infWebSvcReplyString.toString());

					return form;
				}
				if(res.getSuccess()!= null && res.getSuccess().equals("true")){
					form.setFlag("success");
					form.setMessage("GL API - " + form.getMessage());
					LoggerUtil.info(form.getMessage(), this.getClass());
					
					//Save API Logs to database (TBAPILOGS)
					APIUtil.addApiLogs("journal", appno, true, infWebSvcRequestString.toString(), infWebSvcReplyString.toString());
					
					return form;
				}
			}
			
		} catch (Exception e) {
			LoggerUtil.exceptionError(e, this.getClass());
			e.printStackTrace();
		} finally {
			if (infWebSvcReqWriter != null) {
				try {
					infWebSvcReqWriter.flush();
					infWebSvcReqWriter.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return form;
	}
}
