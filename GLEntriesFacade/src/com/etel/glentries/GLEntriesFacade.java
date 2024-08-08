package com.etel.glentries;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etel.glentries.forms.GLEntriesForm;
import com.etel.glentries.forms.GLEntriesPerProductForm;
import com.etel.glentries.forms.JournalEntriesForm;
import com.etel.reports.ReportsFacadeImpl;
import com.etel.reports.ReportsFacadeService;
import com.coopdb.data.Tbaccountingentries;
import com.coopdb.data.Tbcoa;
import com.coopdb.data.Tbglentries;
import com.coopdb.data.Tbglentriesperproduct;
import com.coopdb.data.Tbglmatrix;
import com.coopdb.data.Tbglmatrixperprod;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.security.SecurityService;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class. All public methods will be exposed to
 * the client. Their return values and parameters will be passed to the client
 * or taken from the client, respectively. This will be a singleton instance,
 * shared between all requests.
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL,
 * String, Exception). LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to
 * modify your log level. For info on these levels, look for tomcat/log4j
 * documentation
 */
@ExposeToClient
public class GLEntriesFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public GLEntriesFacade() {
		// super(INFO);
	}

	private GLEntriesService srvc = new GLEntriesServiceImpl();

	public List<GLEntriesPerProductForm> glentriesperprod(String prodcode) {
		List<GLEntriesPerProductForm> form = new ArrayList<GLEntriesPerProductForm>();
		form = srvc.glentriesperprod(prodcode);
		return form;
	}

	public List<Tbcoa> getGLAccounts() {
		List<Tbcoa> glacctlist = new ArrayList<Tbcoa>();
		glacctlist = srvc.getGLAccounts();
		return glacctlist;
	}

	public String saveGLperprod(Tbglentriesperproduct glentry) {
		GLEntriesService srvc = new GLEntriesServiceImpl();
		String result = srvc.saveGLperprod(glentry);
		return result;
	}

	public String deleteGL(Tbglentriesperproduct glentry) {
		String result = srvc.deleteGL(glentry);
		return result;
	}

	public List<GLEntriesForm> getGLEntriesByPnnoAndTxCode(String pnno, String txcode, String appno,
			Boolean islmstransaction, Boolean save) {
		List<GLEntriesForm> glentries = new ArrayList<GLEntriesForm>();
		glentries = srvc.getGLEntriesByPnnoAndTxCode(pnno, txcode, appno, islmstransaction, save);
		return glentries;
	}

	public List<Tbglentries> generateGLEntries(String pnno) {
		List<Tbglentries> glentries = new ArrayList<Tbglentries>();
		return glentries;
	}

	public List<GLEntriesForm> GLEntriesForm(List<GLEntriesForm> r) {
		return r;
	}

	public List<GLEntriesForm> getGLEntries(String pnno, String txcode) {
		return srvc.getGLEntries(pnno, txcode);
	}

	/*
	 * ADDED 08.01.2019 - Kevin
	 */
	public String saveOrDeleteAccountingEntries(Tbaccountingentries acctentries, String action) {
		return srvc.saveOrDeleteAccountingEntries(acctentries, action);
	}

	public List<Tbaccountingentries> getListOfAccountingEntries(String txcode) {
		return srvc.getListOfAccountingEntries(txcode);
	}

	public String saveOrDeleteGLMatrixPerProd(Tbglmatrixperprod glmatrixperprod, String action) {
		return srvc.saveOrDeleteGLMatrixPerProd(glmatrixperprod, action);
	}

	public List<Tbglmatrixperprod> getGLMatrixPerProd(String prodcode) {
		return srvc.getListOfGLMatrixPerProd(prodcode);
	}

	public String fileHandOff(Date businessdate, Date enddate) {
		return srvc.fileHandOff(businessdate, enddate);
	}

	public List<Tbglmatrix> getGLMatrix() {
		return srvc.getGLMatrix();
	}

	public List<JournalEntriesForm> listJournalEntries(String branch, Date from, Date to, String accounttype) {
		return srvc.listJournalEntries(branch, from, to, accounttype);
	}

	public String generateJournalEntriesReport(String filetype, String branch, Date from, Date to, String accounttype,
			String company, String imgsrc) {
		ReportsFacadeService service = new ReportsFacadeImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		String filepath = null;
		imgsrc = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath(imgsrc);
		param.put("branch", branch);
		param.put("from", from);
		param.put("to", to);
		param.put("accounttype", accounttype);
		param.put("companyname", company);
		param.put("imgsrc", imgsrc);
		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		String username = secservice.getUserName();
		param.put("generatedby", username);
		System.out.println(param);
		try {
			if (filetype.equals("PDF")) {
				filepath = service.executeJasperPDF("GL_JournalEntriesPerAccount", param);
			} else {
				filepath = service.executeJasperXLSX("GL_JournalEntriesPerAccount", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return filepath;
	}
}
