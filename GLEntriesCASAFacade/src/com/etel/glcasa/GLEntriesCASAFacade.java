package com.etel.glcasa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbaccountingentries;
import com.coopdb.data.Tbaccountingentriescasa;
import com.coopdb.data.Tbcoa;
import com.coopdb.data.Tbglentries;
import com.coopdb.data.Tbglentriesperproduct;
import com.coopdb.data.Tbglmatrixcasa;
import com.coopdb.data.Tbglmatrixperprodcasa;
import com.etel.glentries.GLEntriesService;
import com.etel.glentries.GLEntriesServiceImpl;
import com.etel.glentries.forms.GLEntriesForm;
import com.etel.glentries.forms.GLEntriesPerProductForm;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
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
public class GLEntriesCASAFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public GLEntriesCASAFacade() {
		super(INFO);
	}

	private GLEntriesCASAService srvc = new GLEntriesServiceCASAImpl();

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

	public List<GLEntriesForm> getGLEntriesByPnnoAndTxCode(String pnno, String txcode, String appno,
			Boolean islmstransaction) {
		List<GLEntriesForm> glentries = new ArrayList<GLEntriesForm>();
		glentries = srvc.getGLEntriesByPnnoAndTxCode(pnno, txcode, appno, islmstransaction);
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

	public String saveOrDeleteGLMatrixPerProd(Tbglmatrixperprodcasa glmatrixperprod, String action) {
		return srvc.saveOrDeleteGLMatrixPerProd(glmatrixperprod, action);
	}

	public List<Tbglmatrixperprodcasa> getGLMatrixPerProd(String prodcode) {
		return srvc.getListOfGLMatrixPerProd(prodcode);
	}

	public String fileHandOff(Date businessdate, Date enddate) {
		return srvc.fileHandOff(businessdate, enddate);
	}

	public String saveOrDeleteAccountingEntries(Tbaccountingentriescasa acctentries, String action) {
		return srvc.saveOrDeleteAccountingEntries(acctentries, action);
	}

	public List<Tbglmatrixcasa> getGLMatrix() {
		return srvc.getGLMatrix();
	}

	public List<Tbaccountingentriescasa> getListOfAccountingEntries(String txcode) {
		return srvc.getListOfAccountingEntries(txcode);
	}
}
