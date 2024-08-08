package com.ete.RelatedAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.etel.cifreport.form.CIFSchedulesReportForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.relatedaccount.form.AccountProfitabilityForm;
import com.etel.relatedaccount.form.DepositAccountForm;
import com.etel.relatedaccount.form.LoanAccountForm;
import com.etel.relatedaccount.form.RelatedAccountParameterForm;
import com.etel.reports.ReportsFacadeImpl;
import com.etel.reports.ReportsFacadeService;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class RelatedAccountServiceImpl implements RelatedAccountService {

	private ReportsFacadeService rptservice = new ReportsFacadeImpl();
	private Map<String, Object> params = new HashMap<String, Object>();
	SecurityService serviceS = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private Map<String, Object> param = HQLUtil.getMap();
	DBService dbService = new DBServiceImpl();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DepositAccountForm> getDepositAccount(RelatedAccountParameterForm forms) {
		List<DepositAccountForm> list = new ArrayList<DepositAccountForm>();
		try {
			param.put("cifNo", forms.getCifno());
			String myQuery = " SELECT b.AccountNo AS accountNo," 
					+ " (SELECT desc1 FROM TBCODETABLECASA where codename = 'PRODUCTGROUP'AND b.ProductCode = codevalue)AS prodName,"
					+ " (SELECT prodname FROM TBPRODMATRIX where prodcode = b.SubProductCode)AS subProdName,"
					+ " (SELECT desc1 FROM TBCODETABLECASA where codename = 'OWNERSHIPTYPE'AND codevalue = b.OwnershipType) AS acctType,"
					+ " b.BookDate AS bookDate, b.IntRate AS intRate, (SELECT branchname FROM TBBRANCH where branchcode = b.unit)AS branch, b.solicitingofficer,"
					+ " (SELECT desc1 FROM TBCODETABLECASA where codename = 'CASA-ACCTSTS' AND codevalue = b.AccountStatus) AS accountStatus,"
					+ " eomadbbookbal AS mADB, ytdaddbookbal AS yTD,"
					+ " (b.AccountBalance - (FloatAmount - garnishedbal) + garnishedbal + PlaceholdAmt - earmarkbal ) AS availBal,"
					+ " b.AccountBalance AS bookBal"
					+ " FROM TBDEPOSITCIF a"
					+ " LEFT JOIN TBDEPOSIT b ON b.AccountNo = a.accountno"
					+ " WHERE a.cifno = :cifNo " ;
			list = (List<DepositAccountForm>) dbService.execSQLQueryTransformer(myQuery, param,DepositAccountForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanAccountForm> getLoanAccount(RelatedAccountParameterForm forms) {
		List<LoanAccountForm> list = new ArrayList<LoanAccountForm>();
		try {
			param.put("cifNo", forms.getCifno());
			String myQuery = "SELECT a.pnno, a.pnno AS loanAccountNo," 
					+ " (SELECT productname FROM TBLOANPRODUCT WHERE productcode = a.prodcode) AS loanProduct,"
					+ " '' AS subProdType, a.dtbook AS dateBook, a.matdt AS matDate, a.pnintrate AS intRate,"
					+ " (SELECT branchname FROM TBBRANCH where branchcode = a.legbranch)AS branch, '' AS solicitingOffice,"
					+ " (SELECT desc1 FROM TBCODETABLE WHERE codename= 'ACCTSTS' AND codevalue = a.acctsts) AS acctsts,"
					+ " a.pnamt AS origAmount, a.prinbal AS prinBal,  a.loanbal AS loanBal"
					+ " FROM TBLOANS a"
					+ " WHERE a.slaidno = :cifNo AND a.pnno IS NOT NULL" ;
			list = (List<LoanAccountForm>) dbService.execSQLQueryTransformer(myQuery, param,LoanAccountForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountProfitabilityForm> getAccountProfitability(RelatedAccountParameterForm forms) {
		List<AccountProfitabilityForm> list = new ArrayList<AccountProfitabilityForm>();
		try {
			param.put("cifNo", forms.getCifno());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT" + 
					" SUM(c.ytdaddbookbal)AS yTDTotal," + 
					" SUM(c.AverageDailyBalance)AS aDBTotal," + 
					" SUM(d.pnamt)AS totalOrigAmount," + 
					" a.fullname, a.cifno," + 
					" (SELECT rr4savingsdeposit FROM "+ forms.getLosDbLink() + ".dbo.TBAPA) AS rr4savingsdeposit," + 
					" (SELECT rr4termdeposit FROM "+ forms.getLosDbLink() + ".dbo.TBAPA) AS rr4termdeposit," + 
					" (SELECT rr4checkingdeposit FROM "+ forms.getLosDbLink() + ".dbo.TBAPA) AS rr4checkingdeposit," + 
					" (SELECT transferpoolrate FROM "+ forms.getLosDbLink() + ".dbo.TBAPA) AS transferpoolrate," + 
					" (SELECT currentratesbl FROM "+ forms.getLosDbLink() + ".dbo.TBSBL) AS currentratesbl," + 
					" (SELECT netunimpairedcapital FROM "+ forms.getLosDbLink() + ".dbo.TBSBL) AS netunimpairedcapital" + 
					" FROM "+ forms.getCifDbLink() + ".dbo.TBCIFMAIN a" + 
					" LEFT JOIN "+ forms.getLosDbLink() + ".dbo.TBDEPOSITCIF b ON b.cifno = a.cifno" + 
					" LEFT JOIN "+ forms.getLosDbLink() + ".dbo.TBDEPOSIT c ON c.AccountNo = b.accountno" + 
					" LEFT JOIN "+ forms.getLosDbLink() + ".dbo.TBLOANS d ON d.slaidno = a.cifno" + 
					" WHERE a.cifno = :cifNo" +
					" GROUP BY a.fullname , a.cifno " ;
			list = (List<AccountProfitabilityForm>) dbService.execSQLQueryTransformer(myQuery, param,AccountProfitabilityForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanAccountForm> getLoanPayment(RelatedAccountParameterForm forms) {
		List<LoanAccountForm> list = new ArrayList<LoanAccountForm>();
		try {
			param.put("cifNo", forms.getCifno());
			String myQuery = "SELECT " 
					+ " SUM(CASE WHEN b.txcode = 20 THEN b.txamt ELSE 0 END) totalDebit,"
					+ " SUM(CASE WHEN b.txcode = 40 THEN b.txamt ELSE 0 END) totalPayment,"
					+ " SUM(CASE WHEN b.txcode = 50 THEN b.txamt ELSE 0 END) totalCredit,"
					+ " a.slaidno AS cifno, a.fullname"
					+ " from TBLOANS a"
					+ " LEFT JOIN TBLNTXJRNL b ON a.accountno = b.accountno"
					+ " where a.slaidno = :cifNo "
					+ " Group BY a.slaidno , a.fullname ";
			list = (List<LoanAccountForm>) dbService.execSQLQueryTransformer(myQuery, param,LoanAccountForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}
	
}
