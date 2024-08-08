/**
 * 
 */
package com.etel.lms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.lms.forms.LMSDashboardForm;
import com.etel.lms.forms.LoanAccountForm;
import com.etel.lms.forms.LoanTransactionForm;
import com.etel.utils.HQLUtil;


/**
 * @author Mheanne
 *
 */
public class LMSDashboardServiceImpl implements LMSDashboardService {

	/* (non-Javadoc)
	 * @see com.etel.lms.LMSDashboardService#getDashBoard()
	 */
	
	DBService dbServiceLOS = new DBServiceImpl();
	Map<String, Object> params = HQLUtil.getMap();
	
	@SuppressWarnings("unchecked")
	public List<LMSDashboardForm> getDashBoard(String filter, String month, String year) {
		// TODO Auto-generated method stub
		List<LMSDashboardForm> form = new ArrayList<LMSDashboardForm>();
		params.put("month", month);
		params.put("year", year);
		System.out.println(month);
		System.out.println(year);
		try {
			if(filter.equals("All")) {
				form = (List<LMSDashboardForm>) dbServiceLOS.execStoredProc(
						"SELECT 'New Loan Bookings' as txType,(SELECT COUNT(*) FROM Tbaccountinfo  WHERE pnno IS NOT NULL AND txstat ='0') as newtx" + ", (SELECT COUNT(*) FROM Tbaccountinfo  WHERE pnno IS NOT NULL AND txstat ='9') as pending"
						+ " ,(SELECT COUNT(*) FROM Tbaccountinfo  WHERE pnno IS NOT NULL AND txstat ='10') as posted " + " ,(SELECT COUNT(*) FROM Tbaccountinfo  WHERE pnno IS NOT NULL AND txstat ='C') as cancelled " + "UNION ALL " + 
						"SELECT 'Payment Transactions' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='40' AND grouprefno is null) as newtx," + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='40' AND grouprefno is null) as pending,"
						+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='40' AND grouprefno is null) as posted, " + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='40' AND grouprefno is null) as cancelled " + "UNION ALL " + 
//						"SELECT 'Group Payments' as txType,(SELECT COUNT(*) FROM Tbgrouppayment WHERE txstat ='4') as newtx," + " (SELECT COUNT(*) FROM Tbgrouppayment WHERE txstat ='9') as pending,"
//						+ " (SELECT COUNT(*) FROM Tbgrouppayment WHERE txstat ='P') as posted, " + " (SELECT COUNT(*) FROM Tbgrouppayment WHERE txstat ='C') as cancelled " + "UNION ALL " + 
						"SELECT 'Credit Adjustments' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='50') as newtx," + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='50') as pending,"
						+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='50') as posted, " + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='50') as cancelled "+ "UNION ALL " + 
						"SELECT 'Debit Adjustments' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='20') as newtx," + "(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='20') as pending,"
						+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='20') as posted, " + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='20') as cancelled "+ "UNION ALL " + 
						"SELECT 'Return Checks' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='30') as newtx," + "(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='30') as pending,"
						+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='30') as posted, " + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='30') as cancelled "+ "UNION ALL " + 
						"SELECT 'Account Cancellation' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='90') as newtx," + "(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='90') as pending,"
						+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='90') as posted," + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='90') as cancelled "+ "UNION ALL " +                                                                                                                                                                 
						"SELECT 'Manual Reclassification' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='70') as newtx," + "(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='70') as pending,"
						+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='70') as posted," + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='70') as cancelled"
						, null, LMSDashboardForm.class, 1, null);
				} else if (filter.equals("Month & Year")) {
					form = (List<LMSDashboardForm>) dbServiceLOS.execStoredProc(
							"SELECT 'New Loan Bookings' as txType,(SELECT COUNT(*) FROM Tbaccountinfo  WHERE pnno IS NOT NULL AND txstat ='0' AND dtbook>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND dtbook<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as newtx" + ", (SELECT COUNT(*) FROM Tbaccountinfo  WHERE pnno IS NOT NULL AND txstat ='9' AND dtbook>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND dtbook<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as pending"
							+ " ,(SELECT COUNT(*) FROM Tbaccountinfo  WHERE pnno IS NOT NULL AND txstat ='P' AND dtbook>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND dtbook<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as posted " + " ,(SELECT COUNT(*) FROM Tbaccountinfo  WHERE pnno IS NOT NULL AND txstat ='C' AND dtbook>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND dtbook<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as cancelled " + "UNION ALL " + 
							"SELECT 'Payment Transactions' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='40' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as newtx," + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='40' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as pending,"
							+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='40' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as posted, " + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='40' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as cancelled " + "UNION ALL " + 
							"SELECT 'Credit Adjustments' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='50' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as newtx," + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='50' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as pending,"
							+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='50' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as posted, " + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='50' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as cancelled "+ "UNION ALL " + 
							"SELECT 'Debit Adjustments' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='20' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as newtx," + "(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='20' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as pending,"
							+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='20' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as posted, " + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='20' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as cancelled "+ "UNION ALL " + 
							"SELECT 'Return Checks' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='30' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as newtx," + "(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='30' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as pending,"
							+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='30' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as posted, " + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='30' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as cancelled "+ "UNION ALL " + 
							"SELECT 'Account Cancellation' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='90' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as newtx," + "(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='90' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as pending,"
							+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='90' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as posted," + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='90' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as cancelled "+ "UNION ALL " +                                                                                                                                                                 
							"SELECT 'Manual Reclassification' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='70' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as newtx," + "(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='70' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as pending,"
							+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='70' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as posted," + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='70' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-',:month,'-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-',:month,'-','1'),0)) as cancelled"
							, null, LMSDashboardForm.class, 1, null);
				} else if (filter.equals("Year Only")) {
					form = (List<LMSDashboardForm>) dbServiceLOS.execStoredProc(
							"SELECT 'New Loan Bookings' as txType,(SELECT COUNT(*) FROM Tbaccountinfo  WHERE pnno IS NOT NULL AND txstat ='0' AND dtbook>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND dtbook<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as newtx" + ", (SELECT COUNT(*) FROM Tbaccountinfo  WHERE pnno IS NOT NULL AND txstat ='9' AND dtbook>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND dtbook<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as pending"
							+ " ,(SELECT COUNT(*) FROM Tbaccountinfo  WHERE pnno IS NOT NULL AND txstat ='P' AND dtbook>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND dtbook<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as posted " + " ,(SELECT COUNT(*) FROM Tbaccountinfo  WHERE pnno IS NOT NULL AND txstat ='C' AND dtbook>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND dtbook<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as cancelled " + "UNION ALL " + 
							"SELECT 'Payment Transactions' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='40' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as newtx," + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='40' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as pending,"
							+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='40' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as posted, " + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='40' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as cancelled " + "UNION ALL " + 
							"SELECT 'Credit Adjustments' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='50' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as newtx," + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='50' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as pending,"
							+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='50' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as posted, " + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='50' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as cancelled "+ "UNION ALL " + 
							"SELECT 'Debit Adjustments' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='20' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as newtx," + "(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='20' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as pending,"
							+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='20' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as posted, " + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='20' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as cancelled "+ "UNION ALL " + 
							"SELECT 'Return Checks' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='30' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as newtx," + "(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='30' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as pending,"
							+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='30' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as posted, " + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='30' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as cancelled "+ "UNION ALL " + 
							"SELECT 'Account Cancellation' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='90' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as newtx," + "(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='90' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as pending,"
							+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='90' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as posted," + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='90' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as cancelled "+ "UNION ALL " +                                                                                                                                                                 
							"SELECT 'Manual Reclassification' as txType,(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='4' AND txcode='70' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as newtx," + "(SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='9' AND txcode='70' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as pending,"
							+ " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='P' AND txcode='70' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as posted," + " (SELECT COUNT(*) FROM Tbloanfin WHERE TxStatus ='C' AND txcode='70' AND txvaldt>=DATEADD(DAY, 1, EOMONTH(CONCAT(:year,'-','January','-','1'),-1)) AND txvaldt<=EOMONTH(CONCAT(:year,'-','December','-','1'),0)) as cancelled"
							, null, LMSDashboardForm.class, 1, null);

				}
			} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanAccountForm> getLoanReleases(String txstat) {
		// TODO Auto-generated method stub
		List<LoanAccountForm> listOfLoans = new ArrayList<LoanAccountForm>();
		params.put("stat", txstat);
		try {
			listOfLoans = (List<LoanAccountForm>) dbServiceLOS.execStoredProc(
					"SELECT applno, pnno, dtbook as txdate, clientid, name as fullname"
					+ ", (SELECT productname FROM Tbloanproduct WHERE productcode = product) as product, pnamt as loanamount"
					+ ", fduedt as fduedate, matdt as matdate, nominal as interestrate, intcycdesc as intperiod, ppynum as ppynum, term, termcycdesc as termperiod, amortfee as amortization, loanno as accountno"
					+ "  FROM Tbaccountinfo WHERE pnno IS NOT NULL AND txstat =:stat", params, LoanAccountForm.class, 1, null);
//			for(LoanAccountForm row: listOfLoans) {
//				//System.out.println(row.getProduct());
//				//System.out.println(row.getApplno());
//			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listOfLoans;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanTransactionForm> getLoanTransactionbyStatus(String txstat) {
		// TODO Auto-generated method stub
				List<LoanTransactionForm> listOfTrans = new ArrayList<LoanTransactionForm>();
				params.put("stat", txstat);
				try {
					listOfTrans = (List<LoanTransactionForm>) dbServiceLOS.execStoredProc(
							"SELECT accountno, pnno, slaidNo as clientid, clientname as fullname, txrefno, (SELECT desc1 FROM TBCODETABLE WHERE codevalue = txcode AND codename='TXCODE') as txcode, (SELECT desc1 FROM TBCODETABLE WHERE codevalue = txmode AND codename='PAYMENTMODE') as txmode, txdate"
							+ ", txvaldt, txamount, txmisc, txlpc, txint,txprin, (SELECT desc1 FROM TBCODETABLE WHERE codevalue=txstatus AND codename='TXSTAT') as txstat, creationdate as datecreated, txposteddate as dateposted, txchkno as checkno"
							+ ", txbrstn as checkbrstn, txchkbankname as checkbankname, txchkdate as checkdate, txamount as checkamount, txchkacctno as checkaccountno, txchkacctname as checkaccountname, txchecktype as checktype, txchkpayeename as checkpayee"
							+ ", txchkbankbr as checkbankbr, txdebitacctno as debitacctno, txdebitaccttype as debitaccttype, approvaldate FROM Tbloanfin WHERE txstatus =:stat", params, LoanTransactionForm.class, 1, null);
//					for(LoanTransactionForm row: listOfTrans) {
//						//System.out.println(row.getTxrefno());
//					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				return listOfTrans;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<LoanTransactionForm> getLoanTransactionbyStatusAndTxcode(String txstat, String txcode) {
		// TODO Auto-generated method stub
		List<LoanTransactionForm> listOfTrans = new ArrayList<LoanTransactionForm>();
		params.put("stat", txstat);
		params.put("txcode", txcode);
		try {
			if (txcode.equals("41")) {
				listOfTrans = (List<LoanTransactionForm>) dbServiceLOS.execStoredProc(
						"SELECT null as accountno, null as pnno, cifno as clientid, cifname as fullname, txrefno, 'Group Payment' as txcode, (SELECT desc1 FROM TBCODETABLE WHERE codevalue = txmode AND codename='PAYMENTMODE') as txmode, txdate"
								+ ", txvaldt, txamt as txamount, 0.00 as txmisc, 0.00 as txlpc, 0.00 as txint ,txamt as txprin, (SELECT desc1 FROM TBCODETABLE WHERE codevalue=txstat AND codename='TXSTAT') as txstat, txdate as datecreated, dateupdated as dateposted, txchkno as checkno"
								+ ", txbrstn as checkbrstn, txchkbankname as checkbankname, txchkdt as checkdate, txamt as checkamount, null as checkaccountno, null as checkaccountname, null as checktype, null as checkpayee"
								+ ", txchkbankbr as checkbankbr, null as debitacctno, null as debitaccttype, approveddate as approvaldate, txor FROM Tbgrouppayment WHERE txstat =:stat",
						params, LoanTransactionForm.class, 1, null);

			} else {
				listOfTrans = (List<LoanTransactionForm>) dbServiceLOS.execStoredProc(
						"SELECT accountno, pnno, slaidNo as clientid, clientname as fullname, txrefno, (SELECT desc1 FROM TBCODETABLE WHERE codevalue = txcode AND codename='TXCODE') as txcode, (SELECT desc1 FROM TBCODETABLE WHERE codevalue = txmode AND codename='PAYMENTMODE') as txmode, txdate"
								+ ", txvaldt, txamount, txmisc, txlpc, txint,txprin, (SELECT desc1 FROM TBCODETABLE WHERE codevalue=txstatus AND codename='TXSTAT') as txstat, creationdate as datecreated, txposteddate as dateposted, txchkno as checkno"
								+ ", txbrstn as checkbrstn, txchkbankname as checkbankname, txchkdate as checkdate, txamount as checkamount, txchkacctno as checkaccountno, txchkacctname as checkaccountname, txchecktype as checktype, txchkpayeename as checkpayee"
								+ ", txchkbankbr as checkbankbr, txdebitacctno as debitacctno, txdebitaccttype as debitaccttype, approvaldate, txor FROM Tbloanfin WHERE txstatus =:stat AND txcode=:txcode AND grouprefno is null",
						params, LoanTransactionForm.class, 1, null);
			}
			if (listOfTrans != null) {
//				for (LoanTransactionForm row : listOfTrans) {
//					// System.out.println(row.getTxrefno());
//				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listOfTrans;
	}

	
}
