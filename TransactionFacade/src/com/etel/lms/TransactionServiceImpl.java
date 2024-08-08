/**
 * 
 */
package com.etel.lms;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cifsdb.data.Tbcifemployment;
import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.Tbbillspayment;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbcoa;
import com.coopdb.data.Tbglentries;
import com.coopdb.data.Tbgrouppayment;
import com.coopdb.data.Tblntxjrnl;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tbnetamt;
import com.etel.api.forms.AddJournalForm;
import com.etel.api.forms.TransactionDetailsJournal;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.glentries.GLEntriesService;
import com.etel.glentries.GLEntriesServiceImpl;
import com.etel.glentries.forms.GLEntriesForm;
import com.etel.lms.forms.GroupPaymentAccountForm;
import com.etel.lms.forms.PaymentTransactionForm;
import com.etel.util.DateTimeUtil;
import com.etel.util.INITUtil;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-COMP05
 *
 */
public class TransactionServiceImpl implements TransactionService {
	DBService dbSrvc = new DBServiceImpl();
	DBService dbSrvcCIF = new DBServiceImplCIF();
	Map<String, Object> params = HQLUtil.getMap();
	SecurityService secsrvc = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public String addEntry(Tbloanfin fin, Tbchecksforclearing check) {
		try {
			fin.setTxdate(new Date());
			fin.setTxstatusdate(new Date());
			if (fin.getTxrefno() == null) {
				String sequence = HQLUtil.generateTransactionRefNoDate();
				fin.setTxrefno(sequence.substring(0, 2) + fin.getTxcode() + sequence.substring(4, sequence.length()));
			}
			if (fin.getTxstatus().equals("4")) {
				fin.setCreationdate(new Date());
				fin.setCreatedby(fin.getCreatedby() == null ? secsrvc.getUserName() : fin.getCreatedby());
			} else {
				PaymentTransactionForm existing = getTransaction(fin.getTxrefno());
				fin.setCreatedby(existing.getTransaction().getCreatedby());
				fin.setCreationdate(existing.getTransaction().getCreationdate());
				fin.setApprovaldate(new Date());
				fin.setApprovedby(secsrvc.getUserName());
			}
			fin.setBranchcode(UserUtil.getUserByUsername(secsrvc.getUserName()).getBranchcode());
			fin.setAddtnlint(fin.getAddtnlint() == null ? BigDecimal.ZERO : fin.getAddtnlint());
			fin.setAddtnllpc(fin.getAddtnllpc() == null ? BigDecimal.ZERO : fin.getAddtnllpc());
			fin.setTxmisc(fin.getTxmisc() == null ? BigDecimal.ZERO : fin.getTxmisc());
			fin.setTxlpc(fin.getTxlpc() == null ? BigDecimal.ZERO : fin.getTxlpc());
			if (dbSrvc.saveOrUpdate(fin)) {
//				if(fin.getTxmode().equals("2") && fin.getTxoper()==2) {
//					Calendar cal = Calendar.getInstance();
//					cal.setTime(fin.getTxvaldt());
//					cal.add(Calendar.DAY_OF_MONTH, 5);
//					check.setCheckdate(fin.getTxvaldt());
//					check.setClearingdate(cal.getTime());
//					check.setClearingdays(5);
//					check.setStatus("1");
//					if(dbSrvc.save(check)) {
//						return "Success";
//					}else {
//						fin.setTxstatus("8");
//						fin.setReason("Unable to save check details.");
//					}
//				}
				return fin.getTxrefno();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed";
		}
		return "Failed";
	}

	@Override
	public List<Tbloanfin> findTXByAccountnoAndTXCode(String accountno, String txcode) {
		List<Tbloanfin> payList = new ArrayList<Tbloanfin>();
		params.put("acctno", accountno);
		params.put("txcode", txcode);
		try {
			payList = (List<Tbloanfin>) dbSrvc.execStoredProc(
					"SELECT (SELECT desc1 FROM Tbcodetable WHERE codename ='TXSTAT' AND codevalue = txStatus ) as txStatus"
							+ ",(SELECT desc1 FROM Tbcodetable WHERE codename ='PAYMODE' AND codevalue = txmode ) as txmode,* FROM Tbloanfin WHERE accountno =:acctno AND txcode =:txcode",
					params, Tbloanfin.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payList;
	}

	@Override
	public List<Tbloanfin> findTXByAccountnoANDTXCodeANDPaymodeANDTxstatus(String accountno, String txcode,
			String paymode, String txstatus) {
		List<Tbloanfin> payList = new ArrayList<Tbloanfin>();
		params.put("acctno", accountno);
		params.put("txcode", txcode);
		params.put("paymode", paymode);
		params.put("txstatus", txstatus);
		try {
			payList = (List<Tbloanfin>) dbSrvc.execStoredProc(
					"SELECT (SELECT desc1 FROM Tbcodetable WHERE codename ='TXSTAT' AND codevalue = txStatus ) as txStatus"
							+ ",(SELECT desc1 FROM Tbcodetable WHERE codename ='PAYMODE' AND codevalue = txmode ) as txmode,* FROM Tbloanfin WHERE accountno =:acctno AND txcode =:txcode AND txmode =:paymode AND txstatus=:txstatus",
					params, Tbloanfin.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payList;
	}

	@Override
	public String errorCorrect(String txrefno) {
		Clob clob;
		try {
//			Tbloanfin fin = new Tbloanfin();
//			Tbchecksforclearing check = new Tbchecksforclearing();
//			fin.
//			addEntry(fin, check);
			clob = (Clob) dbSrvc.execStoredProc(
					"DECLARE @result varchar(MAX) EXEC sp_errorcorrect :txrefno, @result OUTPUT SELECT @result", params,
					null, 0, null);
			return clob.getSubString(1, (int) clob.length());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public List<Tbcifmain> getListofCIFs(String name) {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PaymentTransactionForm getTransaction(String txrefno) {
		params.put("txrefno", txrefno);
		Tbloanfin transaction = (Tbloanfin) dbSrvc.executeUniqueHQLQuery("FROM Tbloanfin Where txrefno=:txrefno",
				params);
		List<Tbglentries> glentries = (List<Tbglentries>) dbSrvc
				.executeListHQLQuery("FROM Tbglentries where txseqno=:txrefno", params);
		// List<GLEntries> gl = new ArrayList<GLEntries>();
		List<GLEntriesForm> gllist = new ArrayList<GLEntriesForm>();
		PaymentTransactionForm form = new PaymentTransactionForm();
		if (glentries != null && glentries.size() > 0) {
			for (Tbglentries glsl : glentries) {

				GLEntriesForm glentry = new GLEntriesForm();
				params.put("glcode", glsl.getGlsl());
				Tbcoa coa = (Tbcoa) dbSrvc.executeUniqueHQLQuery("FROM Tbcoa WHERE accountno=:glcode", params);
				if (coa != null) {
					glentry.setGldesc(coa.getAcctdesc());
				}
				glentry.setCredit(glsl.getCredit());
				glentry.setDebit(glsl.getDebit());
				glentry.setGlaccountno(glsl.getGlsl());
				gllist.add(glentry);
			}
		}

		form.setTransaction(transaction);
		form.setGlentries(gllist);

		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcifmain> searchClient(String cifname) {
		List<Tbcifmain> ciflist = new ArrayList<Tbcifmain>();
		params.put("cifname", cifname);
		System.out.println(cifname);
		ciflist = (List<Tbcifmain>) dbSrvcCIF
				.executeListHQLQuery("FROM Tbcifmain where fullname like '%' + :cifname + '%'", params);
		System.out.println(ciflist.isEmpty());
		return ciflist;
	}

	@Override
	public String addGroupPayment(Tbgrouppayment payment) {
		try {
			payment.setTxdate(new Date());
			if (payment.getTxrefno() == null)
				payment.setTxrefno(HQLUtil.generateGroupTransactionRefNoDate());
			payment.setEncodedby(secsrvc.getUserName());
			if (dbSrvc.saveOrUpdate(payment)) {
				params.put("grouprefno", payment.getTxrefno());
				@SuppressWarnings("unchecked")
				List<Tbloanfin> relatedaccts = (List<Tbloanfin>) dbSrvc
						.executeListHQLQuery("FROM Tbloanfin where grouprefno=:grouprefno", params);
				if (relatedaccts != null) {
					for (Tbloanfin tx : relatedaccts) {
						tx.setTxstatus(payment.getTxstat());
						dbSrvc.saveOrUpdate(tx);
					}
				}
				return payment.getTxrefno();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
		return "failed";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbloans> getAccountListforGroupPayment(String cifno, String prodcode, String companycode) {
		params.put("cifno", cifno);
		List<Tbcifemployment> emplist = (List<Tbcifemployment>) dbSrvcCIF
				.executeListHQLQuery("FROM Tbcifemployment where empcifno=:cifno", params);

		List<Tbloans> acctlist = new ArrayList<Tbloans>();
		if (emplist != null) {
			for (Tbcifemployment emp : emplist) {
				List<Tbloans> accts = new ArrayList<Tbloans>();
				params.put("cif", emp.getCifno());
				params.put("prodcode", prodcode);
				params.put("company", companycode);
				accts = (List<Tbloans>) dbSrvc.executeListHQLQuery(
						"FROM Tbloans Where principalNo=:cif AND prodcode=:prodcode AND legveh=:company and acctsts<>'9'",
						params);
				System.out.println(emp.getCifno());
				System.out.println(prodcode);
				System.out.println(companycode);
				if (accts != null) {

					for (Tbloans acct : accts) {
						acctlist.add(acct);
						System.out.println(acct.getPnno());
					}
				}
			}
		}
		System.out.println(acctlist.isEmpty());
		return acctlist;
	}

	@Override
	public String addGroupAccountPayment(GroupPaymentAccountForm acct, String companycode, String prodcode,
			String txstat, String txmode) {
		params.put("grouprefno", acct.getGrouptxrefno());
		Tbgrouppayment group = (Tbgrouppayment) dbSrvc
				.executeUniqueHQLQuery("FROM Tbgrouppayment where txrefno=:grouprefno", params);
		try {
			Tbloanfin trans = new Tbloanfin();
			trans.setTxrefno(HQLUtil.generateTransactionRefNoDate());
			trans.setAccountno(acct.getPnno());
			trans.setCifno(acct.getCifno());
			trans.setClientname(acct.getCifname());
			trans.setCreatedby(secsrvc.getUserName());
			trans.setCreationdate(new Date());
			trans.setGrouprefno(acct.getGrouptxrefno());
			trans.setEmployeeno(acct.getCifno());
			trans.setLegveh(companycode);
			trans.setParticulars("GROUP PAYMENT");
			trans.setPnno(acct.getPnno());
			trans.setSlaidno(acct.getCifno());
			trans.setTxamount(acct.getTxamt());
			trans.setTxamtbal(acct.getTxamt());
			trans.setTxbrstn(group.getTxbrstn());
			trans.setTxchkacctname(group.getCifname());
			trans.setTxchkamount(group.getTxamt());
			trans.setTxchkdate(group.getTxchkdt());
			trans.setTxchkno(group.getTxchkno());
			trans.setTxcode("40");
			trans.setTxdate(new Date());
			trans.setTxint(BigDecimal.ZERO);
			trans.setTxlpc(BigDecimal.ZERO);
			trans.setTxmisc(BigDecimal.ZERO);
			trans.setTxmode(txmode);
			trans.setTxoper(2);
			trans.setTxor(group.getTxor());
			trans.setTxordate(group.getTxvaldt());
			trans.setTxprin(BigDecimal.ZERO);
			trans.setTxsrce("LMS");
			trans.setTxstatus(txstat);
			trans.setTxstatusdate(new Date());
			trans.setTxvaldt(group.getTxvaldt());
			dbSrvc.save(trans);

		} catch (Exception e) {
			e.printStackTrace();
			return "failed";
		}
		return "success";
	}

	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal getTotalGroupPayments(String grouptxrefno) {
		params.put("grouptxrefno", grouptxrefno);
		List<Tbloanfin> translist = (List<Tbloanfin>) dbSrvc
				.executeListHQLQuery("FROM Tbloanfin where grouprefno=:grouptxrefno", params);
		BigDecimal totalamt = BigDecimal.ZERO;
		for (Tbloanfin trans : translist) {
			totalamt.add(trans.getTxamount());
		}
		return totalamt;
	}

	@Override
	public List<Tbloanfin> getAccountListPerGroup(String grouptxrefno) {
		params.put("grouptxrefno", grouptxrefno);
		@SuppressWarnings("unchecked")
		List<Tbloanfin> translist = (List<Tbloanfin>) dbSrvc
				.executeListHQLQuery("FROM Tbloanfin where grouprefno=:grouptxrefno", params);

		return translist;
	}

	@Override
	public String checkOR(String orno) {
		params.put("orno", orno);

		Tbloanfin trans = (Tbloanfin) dbSrvc.executeUniqueHQLQuery("FROM Tbloanfin WHERE txor=:orno", params);
		if (trans.equals(null)) {
			return "Non-Existing";
		} else
			return "Existing";
	}

	@SuppressWarnings("unchecked")
	@Override
	public String cancelTransaction(String txrefno, String txcode, String reason) {
		String result = "Failed";
		Map<String, Object> params = HQLUtil.getMap();
		params.put("txrefno", txrefno);
		params.put("txcode", txcode);
		try {
			if (txcode.equals("41")) {
				Tbgrouppayment grptx = (Tbgrouppayment) dbSrvc
						.executeUniqueHQLQuery("FROM Tbgrouppayment where txrefno=:txrefno", params);
				if (grptx != null) {
					grptx.setTxstat("C");
					grptx.setUpdatedby(secsrvc.getUserName());
					grptx.setDateupdated(new Date());
					dbSrvc.saveOrUpdate(grptx);
					List<Tbloanfin> relatedaccts = (List<Tbloanfin>) dbSrvc
							.executeListHQLQuery("FROM Tbloanfin where grouprefno=:txrefno", params);
					if (relatedaccts != null) {
						for (Tbloanfin tx1 : relatedaccts) {
							tx1.setTxstatus("C");
							tx1.setTxstatusdate(new Date());
							tx1.setParticulars(secsrvc.getUserName() + " (Group Payment): " + reason);
							dbSrvc.saveOrUpdate(tx1);
						}
					}
					result = "Success";
				}
			} else {
				Tbloanfin loantx = (Tbloanfin) dbSrvc.executeUniqueHQLQuery("FROM Tbloanfin where txrefno=:txrefno",
						params);
				if (loantx != null) {
					loantx.setTxstatus("C");
					loantx.setTxstatusdate(new Date());
					loantx.setParticulars(secsrvc.getUserName() + ": " + reason);
					dbSrvc.saveOrUpdate(loantx);
					result = "Success";
					params.put("lptxrefno", loantx.getRemarks());
					Tbbillspayment loanpayment = (Tbbillspayment) dbSrvc
							.executeUniqueHQLQuery("FROM Tbbillspayment WHERE txrefno=:lptxrefno", params);
					if (loanpayment != null) {
						params.put("userid", loanpayment.getTxby());
						params.put("currency", "PHP");
						loanpayment.setTxstatus("0");
						dbSrvc.saveOrUpdate(loanpayment);
						if (loanpayment.getPaymmode().equals("1")) {
							Tbnetamt netamt = (Tbnetamt) dbSrvc.executeUniqueHQLQuery(
									"FROM Tbnetamt WHERE userid =:userid and currency =:currency and transfertype=1",
									params);
							netamt.setUserbalance(netamt.getUserbalance().subtract(loanpayment.getAmount()));
							dbSrvc.saveOrUpdate(netamt);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "Failed";
		}
		return result;
	}

	@Override
	public Tbgrouppayment getGroupPayment(String txrefno) {
		params.put("txrefno", txrefno);
		Tbgrouppayment grouppayment = (Tbgrouppayment) dbSrvc
				.executeUniqueHQLQuery("FROM Tbgrouppayment Where txrefno=:txrefno", params);

		return grouppayment;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GLEntriesForm> getGLEntries(String txrefno) {
		params.put("txrefno", txrefno);
		List<Tbglentries> gl = (List<Tbglentries>) dbSrvc.executeListHQLQuery("FROM Tbglentries where txseqno=:txrefno",
				params);
		List<GLEntriesForm> glform = new ArrayList<GLEntriesForm>();

		for (Tbglentries g : gl) {
			GLEntriesForm gform = new GLEntriesForm();
			gform.setCredit(g.getCredit());
			gform.setDebit(g.getDebit());
			gform.setGlaccountno(g.getGlsl());
			params.put("code", g.getGlsl());
			Tbcoa coa = (Tbcoa) dbSrvc.executeUniqueHQLQuery("FROM Tbcoa where accountno=:code ", params);
			gform.setGldesc(coa.getAcctdesc());
			glform.add(gform);
		}
		return glform;
	}

	@Override
	public String postSinglePayment(String txrefno) {
		String postflag = "Failed";
		String results = "Failed";
		try {
			params.put("txrefno", txrefno);
			params.put("branchcode", UserUtil.getUserByUsername(secsrvc.getUserName()).getBranchcode());
			Tbbranch branch = (Tbbranch) dbSrvc.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode",
					params);
			params.put("businessdate", branch.getCurrentbusinessdate());
			dbSrvc.executeUpdate("exec sp_LMSSingleTxPosting :txrefno,:businessdate,''", params);

			Tblntxjrnl jrnl = (Tblntxjrnl) dbSrvc.executeUniqueHQLQuery("FROM Tblntxjrnl where txrefno=:txrefno",
					params);
			params.put("txcode", jrnl.getTxcode());
			postflag = "Success";
			GLEntriesService glSrvc = new GLEntriesServiceImpl();
			glSrvc.getGLEntriesByPnnoAndTxCode(jrnl.getAccountno(), jrnl.getTxcode(), jrnl.getTxrefno(), true, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (postflag == "Success") {
			results = "Successfully Posted the Transaction.";
		}
		return results /* + " API Status: " + apiflag */;
	}

	@Override
	public String returnTransaction(String txrefno, String txcode, String reason) {
		String result = "Failed";
		params.put("txrefno", txrefno);
		params.put("txcode", txcode);
		try {
			if (txcode.equals("41")) {
				Tbgrouppayment grptx = (Tbgrouppayment) dbSrvc
						.executeUniqueHQLQuery("FROM Tbgrouppayment where txrefno=:txrefno", params);
				if (grptx != null) {
					grptx.setTxstat("4");
					grptx.setUpdatedby(secsrvc.getUserName());
					grptx.setDateupdated(new Date());
					dbSrvc.saveOrUpdate(grptx);
					List<Tbloanfin> relatedaccts = (List<Tbloanfin>) dbSrvc
							.executeListHQLQuery("FROM Tbloanfin where grouprefno=:txrefno", params);
					if (relatedaccts != null) {
						for (Tbloanfin tx1 : relatedaccts) {
							tx1.setTxstatus("4");
							tx1.setTxstatusdate(new Date());
							tx1.setParticulars(secsrvc.getUserName() + " (Group Payment): " + reason);
							dbSrvc.saveOrUpdate(tx1);
						}
					}
					result = "Success";
				}
			} else {
				Tbloanfin loantx = (Tbloanfin) dbSrvc.executeUniqueHQLQuery("FROM Tbloanfin where txrefno=:txrefno",
						params);
				if (loantx != null) {
					loantx.setTxstatus("4");
					loantx.setTxstatusdate(new Date());
					loantx.setParticulars(secsrvc.getUserName() + ": " + reason);
					dbSrvc.saveOrUpdate(loantx);
					result = "Success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = "Failed";
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String postGroupPayment(String txrefno) {
		String postflag = "Failed";
		String entriesflag = "Failed";
		String apiflag = "Failed";
		String results = "Failed";
		try {
//			flag = (String) 
			params.put("txrefno", txrefno);
			params.put("branchcode", UserUtil.getUserByUsername(secsrvc.getUserName()).getBranchcode());
			Tbbranch branch = (Tbbranch) dbSrvc.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode",
					params);
			params.put("businessdate", branch.getCurrentbusinessdate());
			Tbgrouppayment grppayment = (Tbgrouppayment) dbSrvc
					.executeUniqueHQLQuery("FROM Tbgrouppayment where txrefno=:txrefno", params);

			@SuppressWarnings("unchecked")

			List<Tbloanfin> payments = (List<Tbloanfin>) dbSrvc
					.executeListHQLQuery("FROM Tbloanfin where grouprefno=:txrefno", params);
			if (payments != null) {
				for (Tbloanfin tx : payments) {

					params.put("txno", tx.getTxrefno());

					String logs = (String) dbSrvc.execStoredProc(
							"DECLARE @result varchar(3000) EXEC sp_LMSSingleTxPosting :txno,:businessdate,"
									+ "@result OUTPUT SELECT @result",
							params, null, 0, null);

					Tblntxjrnl jrnl = (Tblntxjrnl) dbSrvc.executeUniqueHQLQuery("FROM Tblntxjrnl where txrefno=:txno",
							params);
					params.put("txcode", jrnl.getTxcode());
					params.put("txno1", jrnl.getTxrefno());
					System.out.println(jrnl.getTxrefno());
					postflag = "Success";
					if (jrnl != null && jrnl.getTxcode() == "40") {
						String gllogs = (String) dbSrvc
								.execStoredProc("DECLARE @result varchar(3000) EXEC sp_LMS_GenerateGLEntries :txno1,"
										+ "@result OUTPUT SELECT @result", params, null, 0, null);
						entriesflag = "Success";
					}

					params.put("pnno", jrnl.getPnno());
					Tbloans loanacct = (Tbloans) dbSrvc.executeUniqueHQLQuery("FROM Tbloans where pnno=:pnno", params);
					List<Tbglentries> jrnlentries = (List<Tbglentries>) dbSrvc
							.executeListHQLQuery("FROM Tbglentries WHERE txseqno=:txno1 and txcode=:txcode", params);
					if (jrnlentries != null) {
//						APIService api = new APIServiceImpl();
						AddJournalForm jrnlform = new AddJournalForm();
						jrnlform.setBranchID(jrnl.getTxbr());
						jrnlform.setCurrency("PHP");
						jrnlform.setDetails(tx.getTxor());
						jrnlform.setDocumentStatus("APPROVED");
						jrnlform.setExchangeRate("1");
						// jrnlform.setOrigCompanyID(jrnl.getTxlegveh());
						jrnlform.setRefDate(sdf.format(jrnl.getTxvaldt()));
						jrnlform.setRefNbr(tx.getTxrefno());
						List<TransactionDetailsJournal> txjrnl = new ArrayList<TransactionDetailsJournal>();
						for (Tbglentries entry : jrnlentries) {
							TransactionDetailsJournal item = new TransactionDetailsJournal();
							item.setAccountCode(entry.getGlsl());
							item.setSubAccountCode(loanacct.getPrincipalNo());
							item.setLineDetails(tx.getTxor());
							item.setCredit(entry.getCredit() == null ? ""
									: String.valueOf(entry.getCredit().setScale(2, RoundingMode.HALF_UP)));
							item.setDebit(entry.getDebit() == null ? ""
									: String.valueOf(entry.getDebit().setScale(2, RoundingMode.HALF_UP)));
							txjrnl.add(item);
						}
						jrnlform.setTransactionDetails(txjrnl);
//						ReturnForm form = api.addJournal(tx.getTxrefno(), jrnlform);
//						apiflag = form.getMessage();
					}

				}
				grppayment.setTxstat("P");
				grppayment.setApprovedby(secsrvc.getUserName());
				grppayment.setApproveddate(new Date());
				dbSrvc.saveOrUpdate(grppayment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (postflag == "Success") {
			results = "Successfully Posted the Transaction.";
		}

		return results /* + " API Status: " + apiflag */;
	}

	@Override
	public String reclass(String txrefno, String acctno) {
		DBService dbSrvc = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		params.put("txrefno", txrefno);
		params.put("acctno", acctno);
		params.put("username", secsrvc.getUserName());
		try {
			dbSrvc.executeUpdate("exec sp_LMSManualReclass :txrefno,:acctno,:username", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "success";
	}
}
