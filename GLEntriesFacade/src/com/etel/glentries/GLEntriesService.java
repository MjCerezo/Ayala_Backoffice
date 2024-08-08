package com.etel.glentries;

import java.util.Date;
import java.util.List;

import com.etel.glentries.forms.GLEntriesForm;
import com.etel.glentries.forms.GLEntriesPerProductForm;
import com.etel.glentries.forms.JournalEntriesForm;
import com.coopdb.data.Tbaccountingentries;
import com.coopdb.data.Tbcoa;
import com.coopdb.data.Tbglentries;
import com.coopdb.data.Tbglentriesperproduct;
import com.coopdb.data.Tbglmatrix;
import com.coopdb.data.Tbglmatrixperprod;

public interface GLEntriesService {

	List<GLEntriesPerProductForm> glentriesperprod(String prodcode);

	List<Tbcoa> getGLAccounts();

	String saveGLperprod(Tbglentriesperproduct glentry);

	String deleteGL(Tbglentriesperproduct glentry);

	List<Tbglentries> generateGLEntries(String pnno);

	List<GLEntriesForm> getGLEntriesByPnnoAndTxCode(String pnno, String txcode, String appno, Boolean islmstransaction,
			Boolean save);

	List<GLEntriesForm> getGLEntries(String pnno, String txcode);

	String saveOrDeleteAccountingEntries(Tbaccountingentries acctentries, String action);

	List<Tbaccountingentries> getListOfAccountingEntries(String txcode);

	String saveOrDeleteGLMatrixPerProd(Tbglmatrixperprod glmatrixperprod, String action);

	List<Tbglmatrixperprod> getListOfGLMatrixPerProd(String prodcode);

	String fileHandOff(Date businessdate, Date enddate);

	List<Tbglmatrix> getGLMatrix();

	List<JournalEntriesForm> listJournalEntries(String branch, Date from, Date to, String accounttype);
}
