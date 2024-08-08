package com.etel.sigcard;

import java.util.List;

import com.coopdb.data.Tbbatchfile;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdepositcif;
import com.etel.documents.DocumentService;
import com.etel.documents.DocumentServiceImpl;
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
public class SigcardFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public SigcardFacade() {
       super(INFO);
    }

	public String saveSigcard(String filename, String acctno) {
		SigcardService service = new SigcardServiceImpl();	
		return service.saveSigcard(filename, acctno);
	}

	public String viewSigcard(String acctno) {
		SigcardService service = new SigcardServiceImpl();
		String str = service.viewSigcard(acctno);
		System.out.println("DD " +str);
		return str;
	}
	
	public void print(String data, Integer type) {
//		PrinterUtil.printerUtil(data, type);
	}
	public String saveBulkFile(Tbbatchfile data) {
		SigcardService service = new SigcardServiceImpl();	
		return service.saveBulkFile(data);
	}
	public List<Tbbatchfile> getBatchList(Integer batchstatus){
		SigcardService service = new SigcardServiceImpl();	
		return service.getBatchList(batchstatus);
	}
	public String readBulk(Integer id) {
		SigcardService service = new SigcardServiceImpl();	
		return service.readBulk(id);
	}
	public List<Tbdepositcif> bulkAccountCreation(String filename){
		SigcardService service = new SigcardServiceImpl();	
		return service.bulkAccountCreation(filename);
	}
	//view document
	public String viewSigByAcctNo(String acctNo)
	{
		SigcardService service = new SigcardServiceImpl();		
		return service.viewSigByAcctNo(acctNo);
	}
	public String saveDepositSigcard(Tbdeposit dep) {
		SigcardService service = new SigcardServiceImpl();		
		return service.saveDepositSigcard(dep);
	}
}
