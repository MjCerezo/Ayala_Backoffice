package com.etel.financial;

import java.math.BigDecimal;
import java.util.List;

import com.coopdb.data.Tbapd;
import com.etel.financial.form.MLACForm;
import com.etel.financial.form.MaxLoanAmountForm;
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
public class FinancialFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public FinancialFacade() {
       super(INFO);
    }
    FinancialService srvc = new FinancialServiceImpl();
	public BigDecimal computeMLA(MLACForm form) {
       return srvc.computeMLA(form);
    }
//	public SBLForm computeSBL(String appno, int save) {
//		return srvc.computeSBL(appno, save);
//	}
	public Tbapd getLatestPayslip(Tbapd apd) {
		return srvc.getLatestPayslip(apd);
	}
	public String addPayslip(Tbapd apd) {
		return srvc.addPayslip(apd);
	}
	public String deletePayslip(Tbapd apd) {
		return srvc.deletePayslip(apd);
	}
	public List<MaxLoanAmountForm> listMLA(MLACForm form) {
		return srvc.listMLA(form);
	}
	public List<Tbapd> listPayslip(String memberid){
		return srvc.listPayslip(memberid);
	}
	public String recomputeMLA(String appno) {
		return srvc.recomputeMLA(appno);
	}
	public Tbapd computeAPD(String appno) {
		return srvc.computeAPD(appno);
	}
	public String checkIfMinimumMaintainingBalLessen (String acctno, BigDecimal transamt, String prodcode) {
		return srvc.checkIfMinimumMaintainingBalLessen(acctno, transamt, prodcode);
	}
	public BigDecimal getAvailBalance (String acctno) {
		return srvc.getAvailBalance(acctno);
	}
	
	
	
	
	
	
	
	
	
}
