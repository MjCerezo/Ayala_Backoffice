package com.etel.lmsinquiry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbcoa;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tblntxjrnl;
import com.coopdb.data.Tbloancollateral;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tblstcomakers;
import com.coopdb.data.Tbpaymentsched;
import com.coopdb.data.Tbpaysched;
import com.coopdb.data.Tbuser;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.lms.forms.LoanAccountForm;
import com.etel.lmsinquiry.forms.CustomerInfoForm;
import com.etel.lmsinquiry.forms.LoanAccountInquiryForm;
import com.etel.lmsinquiry.forms.LoanTransactionHistory;
import com.etel.lmsinquiry.forms.PaymentBreakdownForm;
import com.etel.lmsinquiry.forms.PaymentScheduleForm;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class LMSInquiryServiceImpl implements LMSInquiryService {

	DBService dbServiceLOS = new DBServiceImpl();
	DBService dbServiceCIF = new DBServiceImplCIF();

	Map<String, Object> params = HQLUtil.getMap();
	SecurityService secsrvc = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@SuppressWarnings("unchecked")
	@Override
	public LoanAccountInquiryForm accountform(String accountno) {
		// TODO Auto-generated method stub

		// System.out.println(">>> accountno <<< " + accountno);
		LoanAccountInquiryForm acctform = new LoanAccountInquiryForm();

		if (accountno != null) {
			// System.out.println(">>> 1 <<< ");
			params.put("branchcode", UserUtil.getUserByUsername(secsrvc.getUserName()).getBranchcode());
			Tbbranch branch = (Tbbranch) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode", params);

			params.put("valuedate", branch.getCurrentbusinessdate());
			params.put("acctno", accountno);
			Tbaccountinfo acctinfo = (Tbaccountinfo) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE loanno=:acctno", params);
			List<Tbpaysched> paysched = (List<Tbpaysched>) dbServiceLOS
					.executeListHQLQuery("FROM Tbpaysched WHERE accountno=:acctno ORDER BY ilno ASC", params);

			Tbloans loanacct = (Tbloans) dbServiceLOS.executeUniqueHQLQuery("FROM Tbloans WHERE accountno=:acctno",
					params);
			if (loanacct != null) {
				// System.out.println(">>> 2 <<< ");
				loanacct.setMtdint(dbServiceLOS.getSQLAmount(
						"SELECT dbo.getMTDInt(:acctno, null) FROM Tbloans WHERE accountno=:acctno", params));
//				loanacct.setNxtdueilint(dbServiceLOS.getSQLAmount("select interest from getDueAmount(:acctno, :valuedate)", params));
//				loanacct.setNxtdueilprin(dbServiceLOS.getSQLAmount("select principal from getDueAmount(:acctno, null)", params));

				params.put("acctsts", loanacct.getAcctsts().toString());
				System.out.println(loanacct.getAcctsts());
				Tbcodetable acctstatus = (Tbcodetable) dbServiceLOS.executeUniqueHQLQuery(
						"FROM Tbcodetable WHERE id.codename='ACCTSTS' AND id.codevalue=:acctsts", params);

				List<Tbpaymentsched> paymentsched = (List<Tbpaymentsched>) dbServiceLOS
						.executeListHQLQuery("FROM Tbpaymentsched WHERE accountno=:acctno ORDER BY ilno ASC", params);
				List<Tblntxjrnl> txjrnl = (List<Tblntxjrnl>) dbServiceLOS
						.executeListHQLQuery("FROM Tblntxjrnl WHERE accountno=:acctno", params);

				// lntxjrnl.setTxmkr(UserUtil.getUserByUsername(UserUtil.securityService.getUserName()).getUserid());
				// added 11.07.22
				if (txjrnl != null) {
					for (Tblntxjrnl j : txjrnl) {
						params.put("userid", j.getTxmkr());
						Tbuser u = (Tbuser) dbServiceLOS.executeUniqueHQLQuery("FROM Tbuser WHERE userid=:userid",
								params);
						if (u != null) {
							j.setTxmkr(u.getUsername());
						} else {
							if (j.getTxmkr() == "0" || j.getTxmkr().equals("0")) {
								j.setTxmkr(acctinfo.getTxmkr());
							}
						}
					}
				}
				// System.out.println(">>> 3 <<< ");
				List<Tbloancollateral> collaterals = (List<Tbloancollateral>) dbServiceLOS
						.executeListHQLQuery("FROM Tbloancollateral WHERE loanno=:acctno", params);

				String productcode = loanacct.getProdcode();
				params.put("prodcode", productcode);
				Tbloanproduct product = (Tbloanproduct) dbServiceLOS
						.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode=:prodcode", params);
//			Tbapprovedcf cfdetails2 = new Tbapprovedcf();
//			Tbapprovedcf cfdetails1 = new Tbapprovedcf();

//			if (loanacct.getCfrefno1() != null) {
//				params.put("cfrefno1", loanacct.getCfrefno1());
//				cfdetails1 = (Tbapprovedcf) dbServiceLOS
//						.executeUniqueHQLQuery("FROM Tbapprovedcf where cfrefnoconcat=:cfrefno1", params);
//				if (loanacct.getCfrefno2() != null) {
				//
//					params.put("cfrefno2", loanacct.getCfrefno2());
				//
//					cfdetails2 = (Tbapprovedcf) dbServiceLOS
//							.executeUniqueHQLQuery("FROM Tbapprovedcf where cfrefnoconcat=:cfrefno2", params);
//				}
//			}

				params.put("applno", acctinfo.getApplno());
				List<Tblstcomakers> comakers = (List<Tblstcomakers>) dbServiceLOS
						.executeListHQLQuery("FROM Tblstcomakers WHERE id.appno=:applno", params);

				// cif
//			params.put("cifno", acctinfo.getClientid());
//			Tbcifmain cifdetails = (Tbcifmain) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno",
//					params);
				acctform.setAccount(loanacct);
				acctform.setApplication(acctinfo);
				acctform.setApppaysched(paysched);
				acctform.setJournal(txjrnl);
				// acctform.setMaincfdetails(cfdetails1);
				acctform.setPaysched(paymentsched);
				acctform.setProduct(product);
				// acctform.setSubcfdetails(cfdetails2);
				acctform.setComakers(comakers);
				acctform.setAccountstatus(acctstatus.getDesc1());
				acctform.setCollaterals(collaterals);
				// acctform.setCifdetails(cifdetails);
				params.put("acctsts", loanacct.getAcctsts());
				Tbcodetable accstatuscode = (Tbcodetable) dbServiceLOS.executeUniqueHQLQuery(
						"FROM Tbcodetable WHERE codename='ACCTSTS' AND codevalue=:acctsts", params);
				acctform.setAccountstatus(accstatuscode.getDesc1());
				// System.out.println(">>> 4 <<< ");
			}
		}

		return acctform;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanAccountForm> getLoanAccounts(String acctno, String clientname, String cifno) {
		// TODO Auto-generated method stub

		SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
		List<LoanAccountForm> listOfLoans = new ArrayList<LoanAccountForm>();

		try {

			params.put("acctno", acctno);
			params.put("clientname", clientname == null ? "" : clientname);
			params.put("cifno", cifno);
			params.put("branchcode", UserUtil.getUserByUsername(secservice.getUserName()).getBranchcode());

			if (acctno != null) {
				listOfLoans = (List<LoanAccountForm>) dbServiceLOS.execStoredProc(
						"SELECT a.applno, a.pnno, a.dtbook as txdate, a.clientid, a.name as fullname,(SELECT desc1 FROM TBCODETABLE WHERE codename='ACCTSTS' AND codevalue = b.acctsts) AS acctsts"
								+ ", (SELECT productname FROM TBLOANPRODUCT WHERE productcode = a.product) as product, a.pnamt as loanamount"
								+ ", a.fduedt as fduedate, a.matdt as matdate, Convert(decimal(20,4),a.nominal) as interestrate, a.intcycdesc as intperiod, a.ppynum as ppynum, a.term, a.termcycdesc as termperiod, a.amortfee as amortization, a.loanno as accountno"
								+ "  FROM Tbaccountinfo a LEFT JOIN tbloans b ON b.accountno = a.loanno WHERE a.pnno IS NOT NULL AND txstat = '10' AND (a.loanno=:acctno OR a.pnno=:acctno) AND a.txbranch=:branchcode",
						params, LoanAccountForm.class, 1, null);

			} else if (cifno != null) {
				listOfLoans = (List<LoanAccountForm>) dbServiceLOS.execStoredProc(
						"SELECT a.applno, a.pnno, a.dtbook as txdate, a.clientid, a.name as fullname, (SELECT desc1 FROM TBCODETABLE WHERE codename='ACCTSTS' AND codevalue = b.acctsts) AS acctsts"
								+ ", (SELECT productname FROM TBLOANPRODUCT WHERE productcode = a.product) as product, a.pnamt as loanamount"
								+ ", a.fduedt as fduedate, a.matdt as matdate, Convert(decimal(20,4),a.nominal) as interestrate, a.intcycdesc as intperiod, a.ppynum as ppynum, a.term, a.termcycdesc as termperiod, a.amortfee as amortization, a.loanno as accountno"
								+ "  FROM Tbaccountinfo a LEFT JOIN tbloans b ON b.accountno = a.loanno WHERE a.pnno IS NOT NULL AND a.txstat = '10' AND a.clientid=:cifno AND a.txbranch=:branchcode",
						params, LoanAccountForm.class, 1, null);
			} else {

				listOfLoans = (List<LoanAccountForm>) dbServiceLOS.execStoredProc(
						"SELECT a.applno, a.pnno, a.dtbook as txdate, a.clientid, a.name as fullname, (SELECT desc1 FROM TBCODETABLE WHERE codename='ACCTSTS' AND codevalue = b.acctsts) AS acctsts"
								+ ", (SELECT productname FROM TBLOANPRODUCT WHERE productcode = a.product) as product, a.pnamt as loanamount"
								+ ", a.fduedt as fduedate, a.matdt as matdate, Convert(decimal(20,4),a.nominal) as interestrate, a.intcycdesc as intperiod, a.ppynum as ppynum, a.term, a.termcycdesc as termperiod, a.amortfee as amortization, a.loanno as accountno"
								+ "  FROM Tbaccountinfo a LEFT JOIN tbloans b ON b.accountno = a.loanno WHERE a.pnno IS NOT NULL AND a.txstat = '10' AND a.name like '%' + :clientname + '%' AND a.txbranch=:branchcode",
						params, LoanAccountForm.class, 1, null);
			}
//			for (LoanAccountForm row : listOfLoans) {
//				System.out.println(row.getProduct());
//				System.out.println(row.getApplno());
//			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return listOfLoans;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LoanTransactionHistory> getTransactionHistory(String acctno) {
		// TODO Auto-generated method stub

		params.put("txacctno", acctno);
		List<LoanTransactionHistory> translist = new ArrayList<LoanTransactionHistory>();

		List<Tblntxjrnl> txjrnl = (List<Tblntxjrnl>) dbServiceLOS
				.executeListHQLQuery("FROM Tblntxjrnl WHERE accountno=:txacctno", params);

		for (Tblntxjrnl jrnl : txjrnl) {

			LoanTransactionHistory trans = new LoanTransactionHistory();

			trans.setAccountno(acctno);
			params.put("acctsts", jrnl.getTxacctsts());
			Tbcodetable accstatuscode = (Tbcodetable) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbcodetable WHERE codename='ACCTSTS' AND codevalue=:acctsts", params);
			trans.setAcctstatus(accstatuscode.getDesc1());
			trans.setDuedtpd(jrnl.getDuedtpd());
			trans.setIlnopd(jrnl.getIlnopd().toString());
			trans.setLoanbal(jrnl.getTxloanbal());
			trans.setPnno(jrnl.getPnno());
			trans.setPrinbal(jrnl.getTxprinbal());
			trans.setTxamt(jrnl.getTxamt());
			trans.setTxar(jrnl.getTxar1());
			params.put("txcode", jrnl.getTxcode());
			Tbcodetable txcode = (Tbcodetable) dbServiceLOS
					.executeUniqueHQLQuery("FROM Tbcodetable WHERE codename='TXCODE' AND codevalue=:txcode", params);
			trans.setTxcode(txcode.getDesc1());
			trans.setTxdate(jrnl.getTxdate());
			trans.setTxexcessbal(jrnl.getTxexcessbal());
			trans.setTxexcess(jrnl.getTxexcess());
			trans.setTxint(jrnl.getTxint());
			trans.setTxlpc(jrnl.getTxlpcprin());
			params.put("txmode", jrnl.getTxmode());
			Tbcodetable txmode = (Tbcodetable) dbServiceLOS.executeUniqueHQLQuery(
					"FROM Tbcodetable WHERE codename='PAYMENTMODE' AND codevalue=:txmode", params);
			if (txmode != null)
				trans.setTxmode(txmode.getDesc1());
			trans.setTxprin(jrnl.getTxprin());
			trans.setTxrefno(jrnl.getTxrefno());
			trans.setTxvldt(jrnl.getTxvaldt());

			translist.add(trans);

		}

		return translist;
	}

	@Override
	public CustomerInfoForm getCustomerInformation(String cifno) {
		// TODO Auto-generated method stub
		CustomerInfoForm cifform = new CustomerInfoForm();
		Tbcifmain indiv = new Tbcifmain();
//		Tbcifcorporate corp = new Tbcifcorporate();
//		Tbcifsoleprop sole = new Tbcifsoleprop();
//		
		params.put("cifno", cifno);
		if (cifno != null) {
			System.out.println(">>> Search CIF No. : " + cifno);
			Tbcifmain cif = (Tbcifmain) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifmain WHERE cifno=:cifno", params);
			// if(cif.getCustomertype() == "1") {
			// indiv = (Tbmember) dbServiceCIF.executeUniqueHQLQuery("FROM Tbmember WHERE
			// cifno=:cifno", params);
			// } else if(cif.getCustomertype() == "2") {
			// corp = (Tbcifcorporate) dbServiceCIF.executeUniqueHQLQuery("FROM
			// Tbcifcorporate WHERE cifno=:cifno", params);
			// } else {
			// sole = (Tbcifsoleprop) dbServiceCIF.executeUniqueHQLQuery("FROM Tbcifsoleprop
			// WHERE cifno=:cifno", params);
			// }

			// cifform.setCorp(corp);
			cifform.setMaincif(cif);
			// cifform.setIndiv(indiv);
			// cifform.setSole(sole);

		}

		return cifform;
	}

	@Override
	public PaymentScheduleForm getPaymentSchedPerAcct(String pnno) {
		// TODO Auto-generated method stub

		List<Tbpaymentsched> pysched = new ArrayList<Tbpaymentsched>();

		params.put("txacctno", pnno);

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbloanfin> getPaymentTransactionInquiry(String clientname, String pnno, String transno, String orno,
			String status) {
		List<Tbloanfin> payInquiry = new ArrayList<Tbloanfin>();
		params.put("clientname", clientname);
		params.put("pnno", pnno);
		params.put("transno", transno);
		params.put("orno", orno);
		params.put("status", status);
		String query = "FROM Tbloanfin where ";
		if (clientname != null) {
			query = query + "clientname like '%' + :clientname + '%' AND ";
			if (pnno != null) {
				query = query + "pnno=:pnno AND ";
				if (transno != null) {
					query = query + "txrefno=:transno AND ";
					if (orno != null) {
						query = query + "txor=:orno AND ";
						if (status != null) {
							query = query + "txstatus=:status AND ";
						}
					} else if (status != null) {
						query = query + "txstatus=:status AND ";
					}
				} else {
					if (orno != null) {
						query = query + "txor=:orno AND ";
						if (status != null) {
							query = query + "txstatus=:status AND ";
						}
					} else if (status != null) {
						query = query + "txstatus=:status AND ";
					}
				}
			} else {
				if (transno != null) {
					query = query + "txrefno=:transno AND ";
					if (orno != null) {
						query = query + "txor=:orno AND ";
						if (status != null) {
							query = query + "txstatus=:status AND ";
						}
					} else if (status != null) {
						query = query + "txstatus=:status AND ";
					}
				} else {
					if (orno != null) {
						query = query + "txor=:orno AND ";
						if (status != null) {
							query = query + "txstatus=:status AND ";
						}
					} else if (status != null) {
						query = query + "txstatus=:status AND ";
					}
				}
			}
		} else if (pnno != null) {
			query = query + "pnno=:pnno AND ";
			if (transno != null) {
				query = query + "txrefno=:transno AND ";
				if (orno != null) {
					query = query + "txor=:orno AND ";
					if (status != null) {
						query = query + "txstatus=:status AND ";
					}

				} else {
					if (status != null) {

						query = query + "txstatus=:status AND ";
					}
				}

			} else {
				if (orno != null) {
					query = query + "txor=:orno AND ";
					if (status != null) {
						query = query + "txstatus=:status AND ";
					}

				} else {
					if (status != null) {

						query = query + "txstatus=:status AND ";
					}
				}
			}
		} else if (transno != null) {
			query = query + "txrefno=:transno AND ";
			if (orno != null) {
				query = query + "txor=:orno AND ";
				if (status != null) {
					query = query + "txstatus=:status AND ";

				}

			} else {
				if (status != null) {
					query = query + "txstatus=:status AND ";

				}

			}
		} else if (orno != null) {
			query = query + "txor=:orno AND ";
			query = query + "txrefno=:transno AND ";
			if (status != null) {
				query = query + "txstatus=:status AND ";

			}

		} else if (status != null) {
			query = query + "txstatus=:status AND ";
		}

		else {
			payInquiry = (List<Tbloanfin>) dbServiceLOS.executeListHQLQuery("FROM Tbloanfin where txcode='40'", null);
			return payInquiry;
		}
		query = query + "txcode = '40'";
		System.out.println(query);
		payInquiry = (List<Tbloanfin>) dbServiceLOS.executeListHQLQuery(query, params);

		return payInquiry;
	}

	@Override
	public PaymentBreakdownForm getPaymentBreakdown(String accountno, Date txvaldt, BigDecimal txamount,
			BigDecimal txinterest, BigDecimal txlpc, BigDecimal txar) {
		PaymentBreakdownForm form = new PaymentBreakdownForm();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("acctno", accountno);
		params.put("txvaldt", txvaldt);
		params.put("txamount", txamount);
		params.put("txinterest", txinterest == null ? -1 : txinterest);
		params.put("txlpc", txlpc == null ? -1 : txlpc);
		params.put("txar", txar == null ? -1 : txar);
		try {
			form = (PaymentBreakdownForm) dbService.execStoredProc(
					"select * from getPaymentBreakdown(:acctno,cast(:txvaldt as date),:txamount,:txinterest,:txlpc,:txar) ",
					params, PaymentBreakdownForm.class, 0, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcoa> getAndListTbcoa(String acctno) {
		List<Tbcoa> list = new ArrayList<Tbcoa>();
		DBService dbServiceCoop = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (acctno != null) {
				params.put("acctno", acctno);
				list = (List<Tbcoa>) dbServiceCoop.executeListHQLQuery("FROM Tbcoa WHERE accountno=:acctno", params);
			} else {
				list = (List<Tbcoa>) dbServiceCoop.executeListHQLQuery("FROM Tbcoa", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public Tbloanfin getLastTransaction(String acctno) {
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (acctno != null) {
				params.put("acctno", acctno);
				@SuppressWarnings("unchecked")
				List<Tbloanfin> list = (List<Tbloanfin>) dbService.executeListHQLQueryWitMaxResults(
						"FROM Tbloanfin WHERE accountno=:acctno AND txstatus ='P' ORDER BY txstatusdate desc", 1,
						params);
				if (list != null && !list.isEmpty()) {
					return list.get(0);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
