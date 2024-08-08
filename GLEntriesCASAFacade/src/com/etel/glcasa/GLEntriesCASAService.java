/**
 * 
 */
package com.etel.glcasa;

import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbaccountingentriescasa;
import com.coopdb.data.Tbcoa;
import com.coopdb.data.Tbglentries;
import com.coopdb.data.Tbglentriesperproductcasa;
import com.coopdb.data.Tbglmatrixcasa;
import com.coopdb.data.Tbglmatrixperprodcasa;
import com.etel.glentries.forms.GLEntriesForm;
import com.etel.glentries.forms.GLEntriesPerProductForm;

/**
 * @author ETEL-LAPTOP07
 *
 */
public interface GLEntriesCASAService {

	List<GLEntriesPerProductForm> glentriesperprod(String prodcode);

	List<Tbcoa> getGLAccounts();

	String saveGLperprod(Tbglentriesperproductcasa glentry);

	String deleteGL(Tbglentriesperproductcasa glentry);

	List<Tbglentries> generateGLEntries(String pnno);

	List<GLEntriesForm> getGLEntriesByPnnoAndTxCode(String pnno, String txcode, String appno, Boolean islmstransaction);

	List<GLEntriesForm> getGLEntries(String pnno, String txcode);

	String saveOrDeleteAccountingEntries(Tbaccountingentriescasa acctentries, String action);

	List<Tbaccountingentriescasa> getListOfAccountingEntries(String txcode);

	String saveOrDeleteGLMatrixPerProd(Tbglmatrixperprodcasa glmatrixperprod, String action);

	List<Tbglmatrixperprodcasa> getListOfGLMatrixPerProd(String prodcode);

	String fileHandOff(Date businessdate, Date enddate);

	List<Tbglmatrixcasa> getGLMatrix();

}
