/**
 * 
 */
package com.etel.casareports;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbglentries;
import com.etel.casareports.form.CASAAllProductReportForm;
import com.etel.casareports.form.CASAAnalyticalReportForm;
import com.etel.casareports.form.CASAComprehensiveListForm;
import com.etel.casareports.form.CASADataForm;
import com.etel.casareports.form.CASAExceptionalReportForm;
import com.etel.casareports.form.CASAGetMasterListAll;
import com.etel.casareports.form.CASAInterestForm;
import com.etel.casareports.form.CASAParametersForm;
import com.etel.casareports.form.CASASavingReportForm;
import com.etel.casareports.form.CASATimeDepositReportForm;
import com.etel.casareports.form.CASATransactionalReportForm;
import com.etel.casareports.form.DormatData;
import com.etel.casareports.form.DormatForm;
import com.etel.casareports.form.ElectronicJournalData;
import com.etel.casareports.form.ElectronicJournalForm;
import com.etel.casareports.form.ElectronicJournalResponse;
import com.etel.casareports.form.TellersBlotterForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
@SuppressWarnings("unchecked")
public class CASAReportsServiceImpl implements CASAReportsService {

	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private Map<String, Object> param = HQLUtil.getMap();
	private DBService dbsrvc = new DBServiceImpl();

	@Override
	public List<CASADataForm> getDepositProductBySub(String prodgroup) {
		List<CASADataForm> list = new ArrayList<CASADataForm>();
		try {
			param.put("prodgroup", prodgroup);
			String myQuery = "SELECT prodgroup,prodcode,prodname,prodsname,currency FROM TBPRODMATRIX WHERE prodgroup = :prodgroup";
			System.out.print("MAR " + myQuery);
			list = (List<CASADataForm>) dbsrvc.execSQLQueryTransformer(myQuery, param, CASADataForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public List<CASADataForm> getDepositProductAll() {
		List<CASADataForm> list = new ArrayList<CASADataForm>();
		try {
			String myQuery = "SELECT prodgroup,prodcode,prodname,prodsname,currency FROM TBPRODMATRIX";
			System.out.print("MAR " + myQuery);
			list = (List<CASADataForm>) dbsrvc.execSQLQueryTransformer(myQuery, param, CASADataForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ElectronicJournalResponse getEJ(ElectronicJournalForm form) {
		ElectronicJournalResponse response = new ElectronicJournalResponse();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("txdate", form.getTxdate());
		param.put("txcode", form.getTxcode());
		param.put("txstatus", form.getTxstatus());
		param.put("errorcorrectind", form.getErrorcorrectind());
		param.put("userid", form.getUserid());
		String qry = "SELECT jrnl.txvaldt as txdate, tx.txcode, tx.txname, jrnl.accountno, jrnl.txamount, "
				+ "jrnl.txrefno, stat.desc1 as txstatus, jrnl.CreatedBy as userid, "
				+ "CAST(IIF(jrnl.overridestatus IS NULL, 0,1) as BIT) as override, "
				+ "ISNULL(jrnl.errorcorrectind,'false') as errorcorrectind, dep.accountname "
				+ "FROM TBDEPTXJRNL jrnl LEFT JOIN TBDEPOSIT dep on dep.accountno = jrnl.accountno "
				+ "LEFT JOIN TBTRANSACTIONCODE tx on jrnl.Txcode = tx.txcode "
				+ "LEFT JOIN TBCODETABLECASA stat on jrnl.TxStatus = stat.codevalue where "
				+ "stat.codename ='TXSTAT' ";
		try {
			response.setBranchcode(form.getBranchcode());
			response.setName(form.getName());
			response.setTxdate(form.getTxdate());
			response.setUserid(form.getUserid());
			if (form.getTxdate() != null) {
				qry += "and cast(txvaldt as date) = CAST(:txdate as date)";
			}
			if (form.getTxcode() != null && !form.getTxcode().isEmpty()) {
				qry += "and jrnl.txcode =:txcode ";
			}
			if (form.getTxstatus() != null && !form.getTxstatus().isEmpty()) {
				qry += "and jrnl.txstatus =:txstatus ";
			}
			if (form.getErrorcorrectind() != null) {
				qry += "and jrnl.errorcorrectind =:errorcorrectind ";
			}
			if (form.getUserid() != null && !form.getUserid().isEmpty()) {
				qry += "and jrnl.createdby =:userid";
			}
			System.out.println(qry);
			System.out.println(param);
			response.setJrnl((List<ElectronicJournalData>) dbsrvc.execStoredProc(qry, param,
					ElectronicJournalResponse.class, 1, null));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return response;
	}

	@Override
	public List<ElectronicJournalData> getEJData(ElectronicJournalForm form) {
		// TODO Auto-generated method stub
		List<ElectronicJournalData> data = new ArrayList<ElectronicJournalData>();

		param.put("txdate", form.getTxdate());
		param.put("txcode", form.getTxcode());
		param.put("txstatus", form.getTxstatus());
		param.put("errorcorrectind", form.getErrorcorrectind());
		param.put("userid", form.getUserid());
		String qry = "SELECT jrnl.txvaldt as txdate, tx.txcode, tx.txname, jrnl.accountno, jrnl.txamount, "
				+ "	jrnl.txrefno, stat.desc1 as txstatus, jrnl.CreatedBy as userid, jrnl.remarks,"
				+ "	CAST(IIF(jrnl.overridestatus IS NULL, 0,1) as BIT) as override, "
				+ "	ISNULL(jrnl.errorcorrectind,'false') as errorcorrectind, dep.accountname "
				+ " FROM TBDEPTXJRNL jrnl " + " LEFT JOIN TBDEPOSIT dep on dep.accountno = jrnl.accountno"
				+ "	LEFT JOIN TBTRANSACTIONCODE tx on jrnl.Txcode = tx.txcode "
				+ "	LEFT JOIN TBCODETABLECASA stat on jrnl.TxStatus = stat.codevalue where "
				+ "	stat.codename ='TXSTAT' ";
		try {
			if (form.getTxdate() != null) {
				qry += "and cast(txvaldt as date) = CAST(:txdate as date)";
			}
			if (form.getTxcode() != null && !form.getTxcode().isEmpty()) {
				qry += "and jrnl.txcode =:txcode ";
			}
			if (form.getTxstatus() != null && !form.getTxstatus().isEmpty()) {
				qry += "and jrnl.txstatus =:txstatus ";
			}
			if (form.getErrorcorrectind() != null) {
				qry += "and jrnl.errorcorrectind =:errorcorrectind ";
			}
			if (form.getUserid() != null && !form.getUserid().isEmpty()) {
				qry += "and jrnl.createdby =:userid";
			}
			System.out.println(qry);
			System.out.println(param);
			data = (List<ElectronicJournalData>) dbsrvc.execStoredProc(qry, param, ElectronicJournalData.class, 1,
					null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public List<DormatData> getDormat(DormatForm form) {
		// TODO Auto-generated method stub
		List<DormatData> list = new ArrayList<DormatData>();
		param.put("txDate", form.getTxDate());
		param.put("txCode", form.getReportCode());
		param.put("userId", form.getUserId());

		try {
			if (form.getReportCode().equals("1") || form.getReportCode().equals("2") || form.getReportCode().equals("3")
					|| form.getReportCode().equals("4")) {
				String myqry = "SELECT AccountNo AS accountNumber, AccountName AS accountName, lasttxdate AS lastTransactionDate, AccountBalance AS accountBalance, AccountBalance AS outstandingBalance,"
						+ "DATEDIFF(DAY,CAST(lasttxdate as date), CAST( GETDATE() AS Date )) AS daysDormat,"
						+ "DATEDIFF(MONTH,CAST(lasttxdate as date), CAST( GETDATE() AS Date )) AS monthsDormat,"
						+ "DATEDIFF(YEAR,CAST(lasttxdate as date), CAST( GETDATE() AS Date )) AS yearsDormat "
						+ "FROM TBDEPOSIT WHERE AccountStatus = '5' ";

				if (form.getReportCode().equals("1")) {
					try {
						myqry += " AND DATEDIFF(YEAR,CAST(lasttxdate as date), CAST( GETDATE() AS Date )) = 2 ";
						list = (List<DormatData>) dbsrvc.execSQLQueryTransformer(myqry, param, DormatData.class, 1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (form.getReportCode().equals("2")) {
					try {
						myqry += " AND DATEDIFF(MONTH,CAST(lasttxdate as date), CAST( GETDATE() AS Date )) = 2 ";
						System.out.println("MAR " + myqry);
						System.out.println("MAR " + param);
						list = (List<DormatData>) dbsrvc.execSQLQueryTransformer(myqry, param, DormatData.class, 1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (form.getReportCode().equals("3")) {
					try {
						myqry += " AND DATEDIFF(YEAR,CAST(lasttxdate as date), CAST( GETDATE() AS Date )) = 5 ";
						System.out.println("MAR " + myqry);
						System.out.println("MAR " + param);
						list = (List<DormatData>) dbsrvc.execSQLQueryTransformer(myqry, param, DormatData.class, 1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if (form.getReportCode().equals("4")) {
					try {
						myqry += " AND DATEDIFF(YEAR,CAST(lasttxdate as date), CAST( GETDATE() AS Date )) = 10 ";
						System.out.println("MAR " + myqry);
						System.out.println("MAR " + param);
						list = (List<DormatData>) dbsrvc.execSQLQueryTransformer(myqry, param, DormatData.class, 1);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} else if (form.getReportCode().equals("5")) {
				try {
					String myqry = "SELECT  A.AccountNo AS accountNumber, AccountName AS accountName, lasttxdate AS lastTransactionDate, AccountBalance AS accountBalance, AccountBalance AS outstandingBalance,"
							+ "	DATEDIFF(DAY,CAST(lasttxdate as date), CAST( GETDATE() AS Date )) AS daysDormat, "
							+ "	DATEDIFF(MONTH,CAST(lasttxdate as date), CAST( GETDATE() AS Date )) AS monthsDormat, "
							+ "	DATEDIFF(YEAR,CAST(lasttxdate as date), CAST( GETDATE() AS Date )) AS yearsDormat "
							+ " FROM ACACIARBDB.dbo.TBDEPOSIT A "
							+ "	LEFT JOIN ACACIARBDB.dbo.TBDEPDETAIL B ON A.AccountNo = B.Accountno "
							+ " WHERE A.AccountBalance < B.minbalamt AND AccountStatus = '5'";
					System.out.println("MAR " + myqry);
					System.out.println("MAR " + param);
					list = (List<DormatData>) dbsrvc.execSQLQueryTransformer(myqry, param, DormatData.class, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (form.getReportCode().equals("6")) {
				try {
					String myqry = "SELECT A.AccountNo AS AccountNumber, A.AccountName AS AccountName, B.TxAmount AS TransactionAmount, A.lasttxdate AS LastTransactionDate, A.lasttxcode AS TransactionCode, A.AccountBalance AS PreviousBalance, A.AccountBalance AS OutstandingBalance "
							+ "FROM ACACIARBDB.dbo.TBDEPOSIT A "
							+ "LEFT JOIN ACACIARBDB.dbo.TBDEPTXJRNL B ON A.AccountNo = B.Accountno ";
					System.out.println("MAR " + myqry);
					System.out.println("MAR " + param);
					list = (List<DormatData>) dbsrvc.execSQLQueryTransformer(myqry, param, DormatData.class, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list;
	}

	// TRANSACTIONAL REPORT
	@Override
	public List<CASATransactionalReportForm> getTellerListofTransactionsfortheDay(CASAParametersForm forms) {
		List<CASATransactionalReportForm> list = new ArrayList<CASATransactionalReportForm>();
		param.put("dateFilter", forms.getDateFilter());
		param.put("businessDate", forms.getBusinessDate());
		param.put("from", forms.getFrom());
		param.put("to", forms.getTo());
		param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
		param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
		param.put("transType", forms.getTransType() == null ? "" : forms.getTransType());
		try {

			if (forms.getTransType().equals("")) {
				list = (List<CASATransactionalReportForm>) dbsrvc.execStoredProc(
						"EXEC sp_CASA_TellerListofTransactionsfortheDay :dateFilter, :businessDate, :from, :to, :branch, :tellerName, :transType",
						param, CASATransactionalReportForm.class, 1, null);
			} 
			
//			else {
//				String myQuery = " IF OBJECT_ID('tempdb..#temp') IS NOT NULL DROP TABLE #temp"
//						+ " Create table #temp(txrefNo varchar(50),branch varchar(50),txdate datetime,accountno varchar(50),accountname varchar(50),"
//						+ " prodName varchar(50),subProdName varchar(50),lastTransDate datetime,transType varchar(50),txamount decimal(16,2),prevBal decimal(16,2),"
//						+ " currentBal decimal(16,2),transStat varchar(50),remarks varchar(500),overrideRequest varchar(50),overrideBy varchar(50),passbookPosted varchar(50))"
//						+ " insert into #temp exec sp_CASA_TellerListofTransactionsfortheDay :dateFilter, :businessDate, :from, :to, :branch, :tellerName, ''"
//						+ " select * from #temp " + " where transType = :transType";
//				System.out.print("MAR " + myQuery);
//				list = (List<CASATransactionalReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
//						CASATransactionalReportForm.class, 1);
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASATransactionalReportForm> getListofNewlyOpenedAccountsfortheDay(CASAParametersForm forms) {
		List<CASATransactionalReportForm> list = new ArrayList<CASATransactionalReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("businessDate", forms.getBusinessDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			param.put("prodType", forms.getProdType() == null ? "" : forms.getProdType());
			list = (List<CASATransactionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ListofNewlyOpenedAccountsfortheDay :dateFilter, :businessDate, :from, :to, :branch, :tellerName, :prodType",
					param, CASATransactionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASATransactionalReportForm> getListofClosedTerminatedPreterminatedAccountsfortheDay(
			CASAParametersForm forms) {
		List<CASATransactionalReportForm> list = new ArrayList<CASATransactionalReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("businessDate", forms.getBusinessDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			param.put("prodType", forms.getProdType() == null ? "" : forms.getProdType());

			list = (List<CASATransactionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ListofClosedTerminatedPreterminatedAccountsfortheDay :dateFilter, :businessDate, :from, :to, :branch, :tellerName, :prodType",
					param, CASATransactionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASATransactionalReportForm> getBranchTransactionListfortheDayFinancialSavingChecking(
			CASAParametersForm forms) {
		List<CASATransactionalReportForm> list = new ArrayList<CASATransactionalReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("businessDate", forms.getBusinessDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			param.put("prodType", forms.getProdType() == null ? "" : forms.getProdType());

			list = (List<CASATransactionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_BranchTransactionListfortheDayFinancialSavingChecking :dateFilter, :businessDate, :from, :to, :branch, :tellerName, :prodType",
					param, CASATransactionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASATransactionalReportForm> getBranchTransactionListfortheDayFinancialTermProducts(
			CASAParametersForm forms) {
		List<CASATransactionalReportForm> list = new ArrayList<CASATransactionalReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("businessDate", forms.getBusinessDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			param.put("prodType", forms.getProdType() == null ? "" : forms.getProdType());

			list = (List<CASATransactionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_BranchTransactionListfortheDayFinancialTermProducts :dateFilter, :businessDate, :from, :to, :branch, :tellerName, :prodType",
					param, CASATransactionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASATransactionalReportForm> getFileMaintenanceUpdateTransactionsfortheDay(CASAParametersForm forms) {
		List<CASATransactionalReportForm> list = new ArrayList<CASATransactionalReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("businessDate", forms.getBusinessDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());

			list = (List<CASATransactionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_FileMaintenanceUpdateTransactionsfortheDay :dateFilter, :businessDate, :from, :to, :branch, :tellerName",
					param, CASATransactionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASATransactionalReportForm> getCashTransferMovementsfortheDay(CASAParametersForm forms) {
		List<CASATransactionalReportForm> list = new ArrayList<CASATransactionalReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("businessDate", forms.getBusinessDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			list = (List<CASATransactionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_CashTransferMovementsfortheDay :dateFilter, :businessDate, :from, :to, :branch, :tellerName",
					param, CASATransactionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASATransactionalReportForm> getListofIssuedCTDPASSBOOKCKBOOKfortheDay(CASAParametersForm forms) {
		List<CASATransactionalReportForm> list = new ArrayList<CASATransactionalReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("businessDate", forms.getBusinessDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());

			list = (List<CASATransactionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ListofIssuedCTDPASSBOOKCKBOOK :dateFilter, :businessDate, :from, :to, :branch, :tellerName",
					param, CASATransactionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASATransactionalReportForm> getListofLateCheckDepositsfortheDay(CASAParametersForm forms) {
		List<CASATransactionalReportForm> list = new ArrayList<CASATransactionalReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("businessDate", forms.getBusinessDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			list = (List<CASATransactionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ListofLateCheckDepositsfortheDay :dateFilter, :businessDate, :from, :to, :branch, :tellerName",
					param, CASATransactionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASATransactionalReportForm> getListofClosedAccountsfortheDayPeriod(CASAParametersForm forms) {
		List<CASATransactionalReportForm> list = new ArrayList<CASATransactionalReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("businessDate", forms.getBusinessDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			list = (List<CASATransactionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ListofClosedAccountsfortheDayPeriod :dateFilter, :businessDate, :from, :to, :branch, :tellerName",
					param, CASATransactionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASATransactionalReportForm> getListofEscheatedAccountsfortheDayPeriod(CASAParametersForm forms) {
		List<CASATransactionalReportForm> list = new ArrayList<CASATransactionalReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("businessDate", forms.getBusinessDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			list = (List<CASATransactionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ListofEscheatedAccountsfortheDayPeriod :dateFilter, :businessDate, :from, :to, :branch, :tellerName",
					param, CASATransactionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASATransactionalReportForm> getListofAutoReneworAutoRollTermDepositPlacementsFortheDay(
			CASAParametersForm forms) {
		List<CASATransactionalReportForm> list = new ArrayList<CASATransactionalReportForm>();
		try {
			param.put("dateFilter", forms.getDateFilter());
			param.put("businessDate", forms.getBusinessDate());
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			param.put("dispositionType", forms.getDispositionType() == null ? "" : forms.getDispositionType());

			list = (List<CASATransactionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ListofAutoReneworAutoRollTermDepositPlacementsFortheDay :dateFilter, :businessDate, :from, :to, :branch, :tellerName, :dispositionType",
					param, CASATransactionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// BALANCES REPORT
	// ALL PRODUCT

	@Override
	public List<CASAAllProductReportForm> getMasterlistofAccountsActive(CASAParametersForm forms) {
		List<CASAAllProductReportForm> list = new ArrayList<CASAAllProductReportForm>();
		param.put("asOf", forms.getAsOf());
		param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
		param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
		param.put("prodType", forms.getProdType() == null ? "" : forms.getProdType());
		param.put("accountStatus", forms.getAccountStatus() == null ? "" : forms.getAccountStatus());
		try {
			list = (List<CASAAllProductReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_GetMasterListAllAccount :asOf, :branch, :tellerName, :prodType, :accountStatus ",
					param, CASAAllProductReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASAAllProductReportForm> getMasterlistofAccountsBelowMinimumBalance(CASAParametersForm forms) {
		List<CASAAllProductReportForm> list = new ArrayList<CASAAllProductReportForm>();
		try {
			param.put("asOf", forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			param.put("prodType", forms.getProdType() == null ? "" : forms.getProdType());
			param.put("accountStatus", forms.getAccountStatus() == null ? "" : forms.getAccountStatus());

			list = (List<CASAAllProductReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_MasterlistofAccountsBelowMinimumBalance :asOf, :branch, :tellerName, :prodType, :accountStatus ",
					param, CASAAllProductReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASAAllProductReportForm> getMasterListofAccountsDormant5And10Years(CASAParametersForm forms) {
		List<CASAAllProductReportForm> list = new ArrayList<CASAAllProductReportForm>();
		try {
			param.put("asOf", forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			list = (List<CASAAllProductReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_MasterListofAccountsDormant5And10Years :asOf, :branch, :tellerName", param,
					CASAAllProductReportForm.class, 1, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASAAllProductReportForm> getScheduleofAccruedInterestPayable(CASAParametersForm forms) {
		List<CASAAllProductReportForm> list = new ArrayList<CASAAllProductReportForm>();
		try {
			param.put("asOf", forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			list = (List<CASAAllProductReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ScheduleofAccruedInterestPayable :asOf, :branch, :tellerName", param,
					CASAAllProductReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASAAllProductReportForm> getCustomerListofDepositAccounts(CASAParametersForm forms) {
		List<CASAAllProductReportForm> list = new ArrayList<CASAAllProductReportForm>();
		try {
			param.put("asOf", forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			param.put("cifNo", forms.getCifNo() == null ? "" : forms.getCifNo());
			param.put("accountName", forms.getAccountName() == null ? "" : forms.getAccountName());
			list = (List<CASAAllProductReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_CustomerListofDepositAccounts :asOf, :branch, :tellerName, :cifNo, :accountName",
					param, CASAAllProductReportForm.class, 1, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASAAllProductReportForm> getScheduleofAccountswithNegativeBalanceTemporaryOverdraft(
			CASAParametersForm forms) {
		List<CASAAllProductReportForm> list = new ArrayList<CASAAllProductReportForm>();
		try {
			param.put("asOf", forms.getAsOf());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());

			list = (List<CASAAllProductReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ScheduleofAccountswithNegativeBalanceTemporaryOverdraft :asOf, :branch, :tellerName",
					param, CASAAllProductReportForm.class, 1, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// ANALYTICAL REPORTS
	@Override
	public List<CASAAnalyticalReportForm> getGrossDepositsandWithdrawalfortheQuarter(CASAParametersForm forms) {
		// TODO Auto-generated method stub
		return null;
	}

	// EXCEPTIONAL REPORTS
	// SPNA
	@Override
	public List<CASAExceptionalReportForm> getListofActivatedDormantAccounts(CASAParametersForm forms) {
		List<CASAExceptionalReportForm> list = new ArrayList<CASAExceptionalReportForm>();
		try {
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());

			list = (List<CASAExceptionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ListofActivatedDormantAccounts :from, :to, :branch, :tellerName", param,
					CASAExceptionalReportForm.class, 1, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASAExceptionalReportForm> getListofAccountswith500tTransactions(CASAParametersForm forms) {
		List<CASAExceptionalReportForm> list = new ArrayList<CASAExceptionalReportForm>();
		try {
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			list = (List<CASAExceptionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ListofAccountswith500tTransactions :from, :to, :branch, :tellerName", param,
					CASAExceptionalReportForm.class, 1, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASAExceptionalReportForm> getListofErrorCorrectedTransactionsforthePeriod(CASAParametersForm forms) {
		List<CASAExceptionalReportForm> list = new ArrayList<CASAExceptionalReportForm>();
		try {
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			list = (List<CASAExceptionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ListofErrorCorrectedTransactionsforthePeriod :from, :to, :branch, :tellerName", param,
					CASAExceptionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASAExceptionalReportForm> getListofRejectedTransactionsforthePeriod(CASAParametersForm forms) {
		List<CASAExceptionalReportForm> list = new ArrayList<CASAExceptionalReportForm>();
		try {
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			list = (List<CASAExceptionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ListofRejectedTransactionsforthePeriod :from, :to, :branch, :tellerName", param,
					CASAExceptionalReportForm.class, 1, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// WALA TO
	@Override
	public List<CASAExceptionalReportForm> getListofTimeoutTransactions(CASAParametersForm forms) {
		List<CASAExceptionalReportForm> list = new ArrayList<CASAExceptionalReportForm>();
		try {
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("tellersName", forms.getTellersName());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "" + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " " + " ";
			if (forms.getFrom() != null) {
				myQuery += " AND CAST(c.statusdate AS DATE) BETWEEN CAST (:from AS DATE) AND CAST (:to AS DATE)";
			}

			if (forms.getTellersName() != null) {
				myQuery += " AND dep.CreatedBy = :tellersName";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASAExceptionalReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASAExceptionalReportForm.class, 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASAExceptionalReportForm> getListofOverrideTransactionsforthePeriod(CASAParametersForm forms) {
		List<CASAExceptionalReportForm> list = new ArrayList<CASAExceptionalReportForm>();
		try {
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
			list = (List<CASAExceptionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ListofOverrideTransactionsforthePeriod :from, :to, :branch, :tellerName", param,
					CASAExceptionalReportForm.class, 1, null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNA
	@Override
	public List<CASAExceptionalReportForm> getListofAccountsClassifiedtoDormantfortheDay(CASAParametersForm forms) {
		List<CASAExceptionalReportForm> list = new ArrayList<CASAExceptionalReportForm>();
		try {
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
			param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());

			list = (List<CASAExceptionalReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ListofAccountsClassifiedtoDormantfortheDay :from, :to, :branch, :tellerName", param,
					CASAExceptionalReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// -----------------------------------------------SA-----------------------------------------------
	@Override
	public List<CASASavingReportForm> getSAMasterList(CASAParametersForm forms) {
		List<CASASavingReportForm> list = new ArrayList<CASASavingReportForm>();
		param.put("asOf", forms.getAsOf());
		param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
		param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
		param.put("subProd", forms.getSubProdSA() == null ? "" : forms.getSubProdSA());
		try {
			list = (List<CASASavingReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_GetMasterlistSA :asOf, :branch, :tellerName, :subProd", param,
					CASASavingReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASASavingReportForm> getSADailyListofAccountsBelowMinimumBalance(CASAParametersForm forms) {
		List<CASASavingReportForm> list = new ArrayList<CASASavingReportForm>();
		try {
			param.put("asOf", forms.getAsOf());
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellersName());
			param.put("subProd", forms.getSubProdSA());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT accountno AS accountNo," + " AccountName AS accountName, lasttxdate,"
					+ " code.txname, interestbalance, intRate," + " AccountBalance AS accountBal" + " FROM "
					+ forms.getLosDbLink() + ".dbo.TBDEPOSIT dep" + " left join " + forms.getLosDbLink()
					+ ".dbo.TBTRANSACTIONCODE code on dep.lasttxcode = code.txcode" + " WHERE xbelowmin > 0";
			if (forms.getBranch() != null) {
				myQuery += " AND dep.unit = :branch";
			}
			if (forms.getTellersName() != null) {
				myQuery += " AND dep.CreatedBy = :tellerName";
			}
			if (forms.getSubProdSA() != null) {
				myQuery += " AND dep.SubProductCode = :subProd";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASASavingReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASASavingReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASASavingReportForm> getSADailyListofActivatedDormantAccounts(CASAParametersForm forms) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CASASavingReportForm> getSADailyListofClosedAccounts(CASAParametersForm forms) {
		List<CASASavingReportForm> list = new ArrayList<CASASavingReportForm>();
		try {
			param.put("asOf", forms.getAsOf());
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellersName());
			param.put("subProd", forms.getSubProdSA());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT AccountName as accountName, AccountNo AS accountNo, code.txname,lasttxdate,StatusDate as effectiveDate,interestbalance,"
					+ " interestpaid,WTAXRate AS wTaxRate,0 as servicecharge,0 as closingbalance" + " FROM "
					+ forms.getLosDbLink() + ".dbo.TBDEPOSIT dep" + " left join " + forms.getLosDbLink()
					+ ".dbo.TBTRANSACTIONCODE code on dep.lasttxcode = code.txcode" + " WHERE accountstatus = '5'";
			if (forms.getBranch() != null) {
				myQuery += " AND dep.unit = :branch";
			}
			if (forms.getTellersName() != null) {
				myQuery += " AND dep.CreatedBy = :tellerName";
			}
			if (forms.getSubProdSA() != null) {
				myQuery += " AND dep.SubProductCode = :subProd";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASASavingReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASASavingReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASASavingReportForm> getSADailyListofDormantAccounts5Years(CASAParametersForm forms) {
		List<CASASavingReportForm> list = new ArrayList<CASASavingReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellersName());
			param.put("subProd", forms.getSubProdSA());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT AccountNo , AccountName, code.txname,"
					+ " lasttxdate,txname,0 as amount,interestbalance," + " AccountBalance as outbal,0 as penalty"
					+ " FROM " + forms.getLosDbLink() + ".dbo.TBDEPOSIT dep" + " LEFT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBTRANSACTIONCODE code on dep.lasttxcode = code.txcode"
					+ " WHERE DATEPART(YEAR,GETDATE()) - DATEPART(YEAR,ISNULL(lasttxdate,StatusDate)) = 5";
			if (forms.getBranch() != null) {
				myQuery += " AND dep.unit = :branch";
			}
			if (forms.getTellersName() != null) {
				myQuery += " AND dep.CreatedBy = :tellerName";
			}
			if (forms.getSubProdSA() != null) {
				myQuery += " AND dep.SubProductCode = :subProd";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASASavingReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASASavingReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASASavingReportForm> getSADailyListofDormantAccounts10Years(CASAParametersForm forms) {
		List<CASASavingReportForm> list = new ArrayList<CASASavingReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellersName());
			param.put("subProd", forms.getSubProdSA());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT AccountNo , AccountName, code.txname,"
					+ " lasttxdate,txname,0 as amount,interestbalance," + " AccountBalance as outbal,0 as penalty"
					+ " FROM " + forms.getLosDbLink() + ".dbo.TBDEPOSIT dep" + " LEFT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBTRANSACTIONCODE code on dep.lasttxcode = code.txcode"
					+ " WHERE DATEPART(YEAR,GETDATE()) - DATEPART(YEAR,ISNULL(lasttxdate,StatusDate)) = 10";
			if (forms.getBranch() != null) {
				myQuery += " AND dep.unit = :branch";
			}
			if (forms.getTellersName() != null) {
				myQuery += " AND dep.CreatedBy = :tellerName";
			}
			if (forms.getSubProdSA() != null) {
				myQuery += " AND dep.SubProductCode = :subProd";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASASavingReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASASavingReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASASavingReportForm> getSADailyListofDormantAccountsfallingBelowMinimumBalance2Months(
			CASAParametersForm forms) {
		List<CASASavingReportForm> list = new ArrayList<CASASavingReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellersName());
			param.put("subProd", forms.getSubProdSA());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT AccountNo , AccountName, code.txname,"
					+ " lasttxdate,txname,0 as amount,interestbalance," + " AccountBalance as outbal,0 as penalty"
					+ " FROM " + forms.getLosDbLink() + ".dbo.TBDEPOSIT dep" + " LEFT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBTRANSACTIONCODE code on dep.lasttxcode = code.txcode"
					+ " WHERE xbelowmin > 0 AND DATEPART(MONTH,GETDATE()) - DATEPART(MONTH,ISNULL(lasttxdate,StatusDate)) = 2";
			if (forms.getBranch() != null) {
				myQuery += " AND dep.unit = :branch";
			}
			if (forms.getTellersName() != null) {
				myQuery += " AND dep.CreatedBy = :tellerName";
			}
			if (forms.getSubProdSA() != null) {
				myQuery += " AND dep.SubProductCode = :subProd";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASASavingReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASASavingReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASASavingReportForm> getSADailyListofDormantAccounts(CASAParametersForm forms) {
		List<CASASavingReportForm> list = new ArrayList<CASASavingReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellersName());
			param.put("subProd", forms.getSubProdSA());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT AccountNo ,AccountName, code.txname, intRate,"
					+ " lasttxdate,txname,0 as amount,interestbalance," + " AccountBalance as outbal,0 as penalty"
					+ " FROM " + forms.getLosDbLink() + ".dbo.TBDEPOSIT dep" + " LEFT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBTRANSACTIONCODE code on dep.lasttxcode = code.txcode" + " WHERE accountstatus = '3'";
			if (forms.getBranch() != null) {
				myQuery += " AND dep.unit = :branch";
			}
			if (forms.getTellersName() != null) {
				myQuery += " AND dep.CreatedBy = :tellerName";
			}
			if (forms.getSubProdSA() != null) {
				myQuery += " AND dep.SubProductCode = :subProd";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASASavingReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASASavingReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASASavingReportForm> getSADailyListofNewAccounts(CASAParametersForm forms) {
		List<CASASavingReportForm> list = new ArrayList<CASASavingReportForm>();
		try {
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellersName());
			param.put("subProd", forms.getSubProdSA());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT AccountNo AS accountNo,AccountName accountName, code.txname,BookDate AS bookDate,"
					+ " statusdate, txname, IntRate AS intRate, interestbalance," + " AccountBalance AS accountBal"
					+ " FROM " + forms.getLosDbLink() + ".dbo.TBDEPOSIT dep" + " LEFT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBTRANSACTIONCODE code on dep.lasttxcode = code.txcode" + " WHERE accountstatus ='1'";
			if (forms.getBranch() != null) {
				myQuery += " AND dep.unit = :branch";
			}
			if (forms.getTellersName() != null) {
				myQuery += " AND dep.CreatedBy = :tellerName";
			}
			if (forms.getSubProdSA() != null) {
				myQuery += " AND dep.SubProductCode = :subProd";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASASavingReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASASavingReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASASavingReportForm> getSASavingsandPremiumSavingsDeposit(CASAParametersForm forms) {
		List<CASASavingReportForm> list = new ArrayList<CASASavingReportForm>();
		try {
			param.put("asOf", forms.getAsOf());
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellersName());
			param.put("subProd", forms.getSubProdSA());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "select a.name," + " SUM(IIF(b.AccountStatus = '1', 1, 0)) as totalcountactive,"
					+ " (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH WHERE branchcode = b.unit) AS branch," + " (SELECT prodname FROM "
					+ forms.getLosDbLink() + ".dbo.TBPRODMATRIX where prodcode = b.SubProductCode)AS subProdName,"
					+ " ISNULL(SUM(IIF(b.AccountStatus = '1',b.AccountBalance,0)),0) as totalamountactive,"
					+ " SUM(IIF(b.AccountStatus = '3', 1, 0)) as totalcountdormant,"
					+ " ISNULL(SUM(IIF(b.AccountStatus = '3',b.AccountBalance,0)),0) as totalamountdormant,"
					+ " COUNT(b.id) as totalcount," + " ISNULL(SUM(b.AccountBalance),0) as totalamount" + " from "
					+ forms.getLosDbLink() + ".dbo.tbamountrange a" + " left join " + forms.getLosDbLink()
					+ ".dbo.tbdeposit b on b.AccountBalance >= a.minvalue and b.AccountBalance <= a.maxvalue and b.AccountStatus in ('1','3')"
					+ " WHERE b.unit  IS NOT NULL";
			if (forms.getAsOf() != null) {
				myQuery += " AND CAST(b.BookDate AS DATE)  <= CAST (:asOf AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND b.unit = :branch";
			}
			if (forms.getTellersName() != null) {
				myQuery += " AND b.CreatedBy = :tellerName";
			}
			if (forms.getSubProdSA() != null) {
				myQuery += " AND b.SubProductCode = :subProd";
			}
			myQuery += " group by a.id, a.name, b.unit, b.SubProductCode order by a.id";
			System.out.print("MAR " + myQuery);
			list = (List<CASASavingReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASASavingReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASASavingReportForm> getSASummaryofDailyTransactionReport(CASAParametersForm forms) {
		List<CASASavingReportForm> list = new ArrayList<CASASavingReportForm>();
		try {
			param.put("asOf", forms.getAsOf());
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellersName());
			param.put("subProd", forms.getSubProdSA());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT" + " (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH WHERE branchcode = jrnl.txbranch) AS branch," + " (SELECT prodname FROM "
					+ forms.getLosDbLink() + ".dbo.TBPRODMATRIX where prodcode = b.SubProductCode)AS subProdName,"
					+ " prod.prodcode as productcode,prodname as productdescription, code.txname,code.txname,"
					+ " SUM(IIF(jrnl.Txoper = 1,0,1)) as noofdebits,"
					+ " SUM(IIF(jrnl.Txoper = 1,0,jrnl.txamount)) as totaldebits,"
					+ " SUM(IIF(jrnl.Txoper = 2,0,1)) as noofcredits,"
					+ " SUM(IIF(jrnl.Txoper = 2,0,jrnl.txamount)) as totalcredits" + " from " + forms.getLosDbLink()
					+ ".dbo.TBDEPTXJRNL jrnl" + " left join " + " " + forms.getLosDbLink()
					+ ".dbo.TBDEPOSIT b on jrnl.Accountno = b.accountno" + " left join " + forms.getLosDbLink()
					+ ".dbo.TBTRANSACTIONCODE code on jrnl.txcode = code.txcode" + " left join " + forms.getLosDbLink()
					+ ".dbo.TBPRODMATRIX prod on SUBSTRING(jrnl.Accountno,4,2) = prod.prodcode"
					+ " WHERE jrnl.txbranch  IS NOT NULL";
			if (forms.getAsOf() != null) {
				myQuery += " AND CAST(b.BookDate AS DATE)  = CAST (:asOf AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND b.unit = :branch";
			}
			if (forms.getTellersName() != null) {
				myQuery += " AND b.CreatedBy = :tellerName";
			}
			if (forms.getSubProdSA() != null) {
				myQuery += " AND b.SubProductCode = :subProd";
			}
			myQuery += " group by prod.prodcode, prodname, jrnl.txcode, code.txname, jrnl.txbranch, b.SubProductCode";
			System.out.print("MAR " + myQuery);
			list = (List<CASASavingReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASASavingReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// -----------------------------------------------TD-----------------------------------------------
	@Override
	public List<CASATimeDepositReportForm> getTDMasterList(CASAParametersForm forms) {
		List<CASATimeDepositReportForm> list = new ArrayList<CASATimeDepositReportForm>();
		param.put("asOf", forms.getAsOf());
		param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
		param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
		param.put("subProd", forms.getSubProdTD() == null ? "" : forms.getSubProdTD());
		try {
			list = (List<CASATimeDepositReportForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_GetMasterlistTD :asOf, :branch, :tellerName, :subProd", param,
					CASATimeDepositReportForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASATimeDepositReportForm> getTDDailyListofAccuredInterestPayable(CASAParametersForm forms) {
		List<CASATimeDepositReportForm> list = new ArrayList<CASATimeDepositReportForm>();
		try {
			param.put("asOf", forms.getAsOf());
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellersName());
			param.put("subProd", forms.getSubProdTD());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT dep.AccountName AS accountName , dep.AccountNo AS accountNo,dep.tdcno, origtd.bookingdate, origtd.maturitydate,"
					+ " origtd.placementamt, origtd.termindays, td.intrate,dep.StatusDate as txvaldt,"
					+ " (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH WHERE branchcode = dep.unit) AS branch," + " (SELECT prodname FROM "
					+ forms.getLosDbLink() + ".dbo.TBPRODMATRIX where prodcode = dep.SubProductCode)AS subProdName,"
					+ " DATEDIFF(DAY,td.dtbook,GETDATE()) as noofdaysaccrued,"
					+ " interestearned, interestpaid, interestbalance" + " FROM " + forms.getLosDbLink()
					+ ".dbo.TBDEPOSIT dep" + " left join " + forms.getLosDbLink()
					+ ".dbo.TBTIMEDEPOSIT td on dep.AccountNo = td.accountno" + " left join " + forms.getLosDbLink()
					+ ".dbo.TBTDC origtd on dep.accountno = origtd.accountno"
					+ " where origtd.bookingdate = dep.BookDate";
			if (forms.getAsOf() != null) {
				myQuery += " AND CAST(dep.BookDate AS DATE)  <= CAST (:asOf AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND dep.unit = :branch";
			}
			if (forms.getTellersName() != null) {
				myQuery += " AND dep.CreatedBy = :tellerName";
			}
			if (forms.getSubProdTD() != null) {
				myQuery += " AND dep.SubProductCode = :subProd";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASATimeDepositReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASATimeDepositReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASATimeDepositReportForm> getTDDailyListofMaturedbutUnwithdrawnAccounts(CASAParametersForm forms) {
		List<CASATimeDepositReportForm> list = new ArrayList<CASATimeDepositReportForm>();
		try {
			param.put("asOf", forms.getAsOf());
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellersName());
			param.put("subProd", forms.getSubProdTD());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT dep.AccountName AS accountName, dep.AccountNo AS accountNo, dep.tdcno, origtd.bookingdate, origtd.maturitydate,"
					+ " origtd.placementamt, origtd.termindays, td.intrate,dep.StatusDate as txvaldt,"
					+ " (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH WHERE branchcode = dep.unit) AS branch," + " (SELECT prodname FROM "
					+ forms.getLosDbLink() + ".dbo.TBPRODMATRIX where prodcode = dep.SubProductCode)AS subProdName,"
					+ " DATEDIFF(DAY,td.dtbook,GETDATE()) as noofdaysaccrued,"
					+ " interestearned, interestpaid, interestbalance, AccountBalance AS accountBal" + " FROM "
					+ forms.getLosDbLink() + ".dbo.TBDEPOSIT dep" + " left join " + forms.getLosDbLink()
					+ ".dbo.TBTIMEDEPOSIT td on dep.AccountNo = td.accountno" + " left join " + forms.getLosDbLink()
					+ ".dbo.TBTDC origtd on dep.accountno = origtd.accountno"
					+ " where origtd.bookingdate = dep.BookDate and accountstatus = '4'";
			if (forms.getAsOf() != null) {
				myQuery += " AND CAST(dep.BookDate AS DATE)  <= CAST (:asOf AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND dep.unit = :branch";
			}
			if (forms.getTellersName() != null) {
				myQuery += " AND dep.CreatedBy = :tellerName";
			}
			if (forms.getSubProdTD() != null) {
				myQuery += " AND dep.SubProductCode = :subProd";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASATimeDepositReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASATimeDepositReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASATimeDepositReportForm> getTDDailyListofNewPlacements(CASAParametersForm forms) {
		List<CASATimeDepositReportForm> list = new ArrayList<CASATimeDepositReportForm>();
		try {
			param.put("asOf", forms.getAsOf());
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellersName());
			param.put("subProd", forms.getSubProdTD());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "select td.AccountNo AS accountNo, td.Accountname AS accountName, origtdc.tdcno AS ctdNumber,"
					+ " origtdc.bookingdate AS placementDate, origtdc.MaturityDate AS maturitydate,dep.StatusDate as txvaldt,"
					+ " (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH WHERE branchcode = dep.unit) AS branch," + " (SELECT prodname FROM "
					+ forms.getLosDbLink() + ".dbo.TBPRODMATRIX where prodcode = dep.SubProductCode)AS subProdName,"
					+ " origtdc.PlacementAmt AS placementamt, td.term, td.IntRate AS intrate,"
					+ " origtdc.placementamt AS totalMatAmount" + " from " + forms.getLosDbLink()
					+ ".dbo.tbtimedeposit td" + " left join " + forms.getLosDbLink()
					+ ".dbo.tbdeposit dep on td.accountno = dep.accountno"
					+ " left join (select top 1 accountno,tdcno,bookingdate,maturitydate,placementamt,termindays,intrate,interestamt,wtaxamt"
					+ " from " + forms.getLosDbLink()
					+ ".dbo.tbtdc group by accountno, tdcno,bookingdate,maturitydate,placementamt,termindays,intrate,interestamt,wtaxamt"
					+ " order by bookingdate) origtdc on dep.accountno= origtdc.accountno"
					+ " left join (select accountno,SUM(wtaxamt) as wtaxamt from " + forms.getLosDbLink()
					+ ".dbo.tbtdc where status ='2' group by accountno ) totals on dep.accountno= totals.accountno";
			if (forms.getAsOf() != null) {
				myQuery += " WHERE CAST(dep.BookDate AS DATE)  <= CAST (:asOf AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND dep.unit = :branch";
			}
			if (forms.getTellersName() != null) {
				myQuery += " AND dep.CreatedBy = :tellerName";
			}
			if (forms.getSubProdTD() != null) {
				myQuery += " AND dep.SubProductCode = :subProd";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASATimeDepositReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASATimeDepositReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASATimeDepositReportForm> getTDDailyListPretermTimeAcctsReport(CASAParametersForm forms) {
		List<CASATimeDepositReportForm> list = new ArrayList<CASATimeDepositReportForm>();
		try {
			param.put("asOf", forms.getAsOf());
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellersName());
			param.put("subProd", forms.getSubProdTD());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT dep.AccountName AS accountName, dep.AccountNo AS accountNo,dep.tdcno, origtd.bookingdate, origtd.maturitydate,"
					+ " origtd.placementamt, origtd.termindays, td.intrate, matvalue,dep.StatusDate as txvaldt,"
					+ " (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH WHERE branchcode = dep.unit) AS branch," + " (SELECT prodname FROM "
					+ forms.getLosDbLink() + ".dbo.TBPRODMATRIX where prodcode = dep.SubProductCode)AS subProdName,"
					+ " DATEDIFF(DAY,td.dtbook,GETDATE()) as noofdaysaccrued,"
					+ " interestearned, null as lastintcreditdate, interestpaid, interestbalance,"
					+ " AccountBalance AS accountBal, wtaxamt, dep.wtaxrate, docstamps" + " FROM "
					+ forms.getLosDbLink() + ".dbo.TBDEPOSIT dep" + " left join " + forms.getLosDbLink()
					+ ".dbo.TBTIMEDEPOSIT td on dep.AccountNo = td.accountno" + " left join " + forms.getLosDbLink()
					+ ".dbo.TBTDC origtd on dep.accountno = origtd.accountno"
					+ " where origtd.bookingdate = dep.BookDate"
					+ " AND accountstatus ='5' AND dep.StatusDate < dep.MaturityDate";
			if (forms.getAsOf() != null) {
				myQuery += " AND CAST(dep.BookDate AS DATE)  <= CAST (:asOf AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND dep.unit = :branch";
			}
			if (forms.getTellersName() != null) {
				myQuery += " AND dep.CreatedBy = :tellerName";
			}
			if (forms.getSubProdTD() != null) {
				myQuery += " AND dep.SubProductCode = :subProd";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASATimeDepositReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASATimeDepositReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASATimeDepositReportForm> getTDDailyListofRollOvers(CASAParametersForm forms) {
		List<CASATimeDepositReportForm> list = new ArrayList<CASATimeDepositReportForm>();
		try {
			param.put("asOf", forms.getAsOf());
			param.put("branch", forms.getBranch());
			param.put("tellerName", forms.getTellersName());
			param.put("subProd", forms.getSubProdTD());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "select" + " (SELECT branchname FROM " + forms.getLosDbLink()
					+ ".dbo.TBBRANCH WHERE branchcode = dep.unit) AS branch," + " (SELECT prodname FROM "
					+ forms.getLosDbLink() + ".dbo.TBPRODMATRIX where prodcode = dep.SubProductCode)AS subProdName,"
					+ " td.AccountNo AS accountNo, td.Accountname AS accountName, origtdc.tdcno AS ctdNumber,"
					+ " origtdc.bookingdate, origtdc.MaturityDate AS maturitydate,dep.StatusDate as txvaldt,"
					+ " origtdc.PlacementAmt AS originalPlacement, origtdc.termindays, origtdc.intrate,"
					+ " interestearned, totals.wtaxamt AS withholdingTax,"
					+ " td.placeamt, td.dtbook AS bookingdate, td.dtmat, td.term, td.IntRate AS intrate" + " from "
					+ forms.getLosDbLink() + ".dbo.tbtimedeposit td" + " left join " + forms.getLosDbLink()
					+ ".dbo.tbdeposit dep on td.accountno = dep.accountno"
					+ " left join (select top 1 accountno,tdcno,bookingdate,maturitydate,placementamt,termindays,intrate,interestamt,wtaxamt"
					+ " from " + forms.getLosDbLink() + ".dbo.tbtdc"
					+ " group by accountno, tdcno,bookingdate,maturitydate,placementamt,termindays,intrate,interestamt,wtaxamt"
					+ " order by bookingdate) origtdc on dep.accountno= origtdc.accountno"
					+ " left join (select accountno,SUM(wtaxamt) as wtaxamt from " + forms.getLosDbLink()
					+ ".dbo.tbtdc where status ='2' group by accountno ) totals on dep.accountno= totals.accountno"
					+ " where td.dispositiontype = 'TD02'";
			if (forms.getAsOf() != null) {
				myQuery += " AND CAST(dep.BookDate AS DATE)  <= CAST (:asOf AS DATE)";
			}
			if (forms.getBranch() != null) {
				myQuery += " AND dep.unit = :branch";
			}
			if (forms.getTellersName() != null) {
				myQuery += " AND dep.CreatedBy = :tellerName";
			}
			if (forms.getSubProdTD() != null) {
				myQuery += " AND dep.SubProductCode = :subProd";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASATimeDepositReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASATimeDepositReportForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

//--------------------------------------------------------------TD END--------------------------------------------------------
	// SPNEED
	@Override
	public List<CASAExceptionalReportForm> getOtherBankReturnCheck(CASAParametersForm forms) {
		List<CASAExceptionalReportForm> list = new ArrayList<CASAExceptionalReportForm>();
		try {
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT "
					+ " (SELECT desc1 FROM TBCODETABLECASA where codename = 'PRODUCTGROUP'AND a.ProductCode = codevalue)AS prodName,"
					+ " (SELECT prodname FROM TBPRODMATRIX where prodcode = a.SubProductCode)AS subProdName,"
					+ " (SELECT desc1 FROM " + forms.getLosDbLink()
					+ ".dbo.TBCODETABLECASA where codename = 'OWNERSHIPTYPE' AND codevalue = a.OwnershipType) AS acctType,"
					+ " a.AccountNo AS accountNo, a.AccountName AS accountName," + " b.checkdate AS businessDate,"
					+ " (SELECT bankname FROM TBBANKS WHERE bankcode = b.brstn) AS bankName,"
					+ " b.brstn, b.checknumber, b.checkamount" + " FROM " + forms.getLosDbLink() + ".dbo.TBDEPOSIT a"
					+ " LEFT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBCHECKSFORCLEARING b ON b.accountnumber = a.AccountNo" + " WHERE b.status = '3'";
			if (forms.getFrom() != null) {
				myQuery += " AND CAST(b.checkdate AS DATE) BETWEEN CAST (:from AS DATE) AND CAST (:to AS DATE)";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASAExceptionalReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASAExceptionalReportForm.class, 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// SPNEED
	@Override
	public List<CASAExceptionalReportForm> getListofForceClearTransactions(CASAParametersForm forms) {
		List<CASAExceptionalReportForm> list = new ArrayList<CASAExceptionalReportForm>();
		try {
			param.put("from", forms.getFrom());
			param.put("to", forms.getTo());
			param.put("cifDbLink", forms.getCifDbLink());
			param.put("losDbLink", forms.getLosDbLink());
			String myQuery = "SELECT " + " b.checkdate AS businessDate,"
					+ " a.AccountNo AS accountNo, a.AccountName AS accountName,"
					+ " b.brstn, b.checknumber, b.checkamount," + " b.clearingdate,"
					+ " (SELECT username FROM TBUSER WHERE userid = b.updatedby) AS createdBy" + " FROM "
					+ forms.getLosDbLink() + ".dbo.TBDEPOSIT a" + " LEFT JOIN " + forms.getLosDbLink()
					+ ".dbo.TBCHECKSFORCLEARING b ON b.accountnumber = a.AccountNo" + " WHERE b.status = '2'";
			if (forms.getFrom() != null) {
				myQuery += " AND CAST(b.checkdate AS DATE) BETWEEN CAST (:from AS DATE) AND CAST (:to AS DATE)";
			}
			System.out.print("MAR " + myQuery);
			list = (List<CASAExceptionalReportForm>) dbsrvc.execSQLQueryTransformer(myQuery, param,
					CASAExceptionalReportForm.class, 1);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Tbglentries> getGLEntriesForTheDay(CASAParametersForm forms) {
		// TODO Auto-generated method stub
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dateFilter", forms.getDateFilter());
		param.put("businessDate", forms.getBusinessDate());
		param.put("from", forms.getFrom());
		param.put("to", forms.getTo());
		param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
		param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
		try {

			if (forms.getDateFilter().equals("1")) {
				return (List<Tbglentries>) dbsrvc.execStoredProc(
						"exec sp_GenerateGLEntriesCASA :businessDate, :businessDate, :branch, :tellerName,'',0,0",
						param, Tbglentries.class, 1, null);
			} else {
				return (List<Tbglentries>) dbsrvc.execStoredProc(
						"exec sp_GenerateGLEntriesCASA :from, :to, :branch, :tellerName,'',0,0", param,
						Tbglentries.class, 1, null);
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<CASAGetMasterListAll> getMasterListAll(CASAParametersForm forms) {
		List<CASAGetMasterListAll> list = new ArrayList<CASAGetMasterListAll>();
		param.put("asOf", forms.getAsOf());
		param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
		param.put("tellerName", forms.getTellersName() == null ? "" : forms.getTellersName());
		try {
			list = (List<CASAGetMasterListAll>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_GetMasterlistAll :asOf, :branch, :tellerName", param, CASAGetMasterListAll.class, 1,
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASAComprehensiveListForm> getCASAComprehensive(CASAParametersForm forms) {
		List<CASAComprehensiveListForm> list = new ArrayList<CASAComprehensiveListForm>();
		param.put("asOf", forms.getAsOf());
		param.put("branch", forms.getBranch() == null ? "" : forms.getBranch());
		param.put("prodCode", forms.getProdType() == null ? "" : forms.getProdType());
		try {
			list = (List<CASAComprehensiveListForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_ComprehensiveList :asOf, :branch, :prodCode", param, CASAComprehensiveListForm.class,
					1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<TellersBlotterForm> listTellersBlotter(String branch, String userid, Date from, Date to) {
		System.out.println(">>> running listTellersBlotter<<<");
		List<TellersBlotterForm> list = new ArrayList<TellersBlotterForm>();
		Map<String, Object> params = HQLUtil.getMap();
		DBService dbsrvcCoop = new DBServiceImpl();

		/*
		 * params.put("imgsrc", ""); params.put("generatedby", "");
		 * params.put("companyname", "");
		 */

		params.put("branch", branch == null ? "" : branch);
		params.put("from", from);
		params.put("to", to);
		params.put("userid", userid == null ? "" : userid);
		try {
			list = (List<TellersBlotterForm>) dbsrvcCoop.execStoredProc("EXEC sp_tellersblotter :from, :to, :userid",
					params, TellersBlotterForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASAInterestForm> listCASAInterestAccrual(String branch, String groupby, Date from, Date to) {
		List<CASAInterestForm> list = new ArrayList<CASAInterestForm>();
		Map<String, Object> param = HQLUtil.getMap();
		DBService dbsrvc = new DBServiceImpl();
		param.put("branch", branch == null ? "" : branch);
		param.put("from", from);
		param.put("to", to);
		param.put("groupby", groupby);
		try {
			list = (List<CASAInterestForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_InterestAccrualReport :from, :to, :branch, :groupby", param, CASAInterestForm.class,
					1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<CASAInterestForm> listCASAInterestCredit(String branch, String groupby, Date from, Date to) {
		List<CASAInterestForm> list = new ArrayList<CASAInterestForm>();
		Map<String, Object> param = HQLUtil.getMap();
		DBService dbsrvc = new DBServiceImpl();
		param.put("branch", branch == null ? "" : branch);
		param.put("from", from);
		param.put("to", to);
		param.put("groupby", groupby);
		try {
			list = (List<CASAInterestForm>) dbsrvc.execStoredProc(
					"EXEC sp_CASA_InterestCreditReport :from, :to, :branch, :groupby", param, CASAInterestForm.class, 1,
					null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
