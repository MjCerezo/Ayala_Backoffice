package com.etel.CICReport;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import com.etel.cicreports.form.CICDataForm;
import com.etel.cicreports.form.CICParametersForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class CICReportServiceImpl implements CICReportService {

	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private Map<String, Object> param = HQLUtil.getMap();
	private DBService dbsrvc = new DBServiceImpl();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CICDataForm> getCoveredTransaction(CICParametersForm forms) {
		List<CICDataForm> list = new ArrayList<CICDataForm>();
		try {
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			
			String myQuery= " SELECT b.AccountNo AS accountNo, a.fullname AS accountName, " 
				+" (SELECT txname FROM "+ forms.getLosDbLink() + ".dbo.TBTRANSACTIONCODE WHERE txcode = d.Txcode) AS transType," 
				+" d.Txdate AS transDate, d.TxAmount AS txAmount," 
				+" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'CUSTOMERTYPE' AND codevalue = a.customertype )AS custType," 
				+" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'OWNERSHIPTYPE' AND codevalue = c.OwnershipType )AS accountType, a.fulladdress1 AS address" 
				+" FROM "+ forms.getCifDbLink() + ".dbo.TBCIFMAIN a" 
				+" LEFT JOIN "+ forms.getLosDbLink() + ".dbo.TBDEPOSITCIF b ON b.cifno = a.cifno" 
				+" LEFT JOIN "+ forms.getLosDbLink() + ".dbo.TBDEPOSIT c ON c.AccountNo = b.Accountno" 
				+" LEFT JOIN "+ forms.getLosDbLink() + ".dbo.TBDEPTXJRNL d ON d.AccountNo = c.Accountno" 
				+" WHERE d.TxAmount >= 500000 AND (d.Txcode = '110111' OR d.Txcode = '111212')" ;
			
			if (forms.getFrom() != null && forms.getTo() != null) {
				myQuery += " AND CAST(d.Txdate AS DATE) BETWEEN CAST(:from AS DATE) AND CAST(:to AS DATE)";
			}
			myQuery +=" UNION"
				+" SELECT b.AccountNo AS accountNo, a.fullname AS accountName, " 
				+" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'TXCODE' AND codevalue = c.txcode) AS transType," 
				+" c.Txdate AS transDate, c.txamt AS txAmount," 
				+" (SELECT desc1 FROM "+ forms.getLosDbLink() + ".dbo.TBCODETABLE WHERE codename = 'CUSTOMERTYPE' AND codevalue = a.customertype )AS custType," 
				+" (' ')AS accountType, a.fulladdress1 AS address" 
				+" FROM "+ forms.getCifDbLink() + ".dbo.TBCIFMAIN a" 
				+" LEFT JOIN "+ forms.getLosDbLink() + ".dbo.TBLOANS b ON b.slaidno = a.cifno" 
				+" LEFT JOIN "+ forms.getLosDbLink() + ".dbo.TBLNTXJRNL c ON c.accountno = b.accountno" 
				+" WHERE c.txamt >= 500000";
			
			if (forms.getFrom() != null && forms.getTo() != null) {
				myQuery += " AND CAST(c.Txdate AS DATE) BETWEEN CAST(:from AS DATE) AND CAST(:to AS DATE)";
			}
			list = (List<CICDataForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,CICDataForm.class, 1);
		}catch(Exception e)
			{
				e.printStackTrace();
			}
		return list;
	}
	
	
}
