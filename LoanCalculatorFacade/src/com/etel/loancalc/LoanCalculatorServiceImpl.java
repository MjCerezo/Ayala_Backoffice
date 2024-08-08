/**
 * 
 */
package com.etel.loancalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.coopdb.data.Tbaccountinfo;
import com.coopdb.data.Tbirregsched;
import com.coopdb.data.Tbloanoffset;
import com.coopdb.data.TbloanoffsetId;
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbpaysched;
import com.coopdb.data.TbpayschedId;
import com.coopdb.data.Tbpdc;
import com.etel.cifreport.form.CIFSchedulesReportForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.loancalc.forms.LoanCalculatorForm;
import com.etel.loancalc.forms.PaymentScheduleForm;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;

/**
 * @author MMM
 *
 */
public class LoanCalculatorServiceImpl implements LoanCalculatorService {

	private String username = UserUtil.securityService.getUserName();

	private DBService dbsrvc = new DBServiceImpl();

	@Override
	public Tbaccountinfo getAccountInfoByAppno(String appno) {
		// TODO Auto-generated method stub
		Tbaccountinfo acct = new Tbaccountinfo();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (appno != null) {
				params.put("applno", appno);
				acct = (Tbaccountinfo) dbsrvc.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno=:applno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return acct;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LoanCalculatorForm computeLoan(LoanCalculatorForm loancalc) {
		// TODO Auto-generated method stub\
		HashMap<String, Object> params = new HashMap<String, Object>();
		List<Tbpaysched> paysched = new ArrayList<Tbpaysched>();
		List<PaymentScheduleForm> forms = new ArrayList<PaymentScheduleForm>();
		// BigDecimal eir = BigDecimal.ZERO;
		params.put("loanamount", loancalc.getLoanamount());
		params.put("amtfinance", loancalc.getAmountfinanced());
		params.put("netproceeds", loancalc.getNetproceeds());
		params.put("intcycle", loancalc.getIntcycle());
		params.put("tcycle", loancalc.getTermcycle());
		params.put("term", loancalc.getTerm());
		params.put("instno", loancalc.getPpynum());
		params.put("intrate", loancalc.getRate().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP));
		params.put("bookingdate", loancalc.getBookingdate());
		params.put("pcycle", loancalc.getCycle());
		params.put("loanappno", loancalc.getLoanappno());
		params.put("fduedate", loancalc.getFirstbookingdate());
		params.put("dueType", 0); // 1 for fixed due date = same as firstduedate, 0 for not fixed due dates = ex.
									// monthly will follow the end of month
		params.put("intpaytype", loancalc.getIntpaytype());
		params.put("amortfee", 0);
		params.put("repaytype", loancalc.getRepaytype());
		params.put("inttype", loancalc.getInttype());
		params.put("intpycycle", loancalc.getIntpaycycle());
		params.put("prinpycycle", loancalc.getPrinpaycycle());
		params.put("retentionamt", loancalc.getRetentionamt());

		params.put("noofintpay", loancalc.getNoofintpay());
		params.put("noofprinpay", loancalc.getNoofprinpay());
		params.put("noofadvint", loancalc.getNoofadvint() == null ? 0 : loancalc.getNoofadvint());
		params.put("noofadvprin", loancalc.getNoofadvprin() == null ? 0 : loancalc.getNoofadvprin());
		params.put("duedaterule", loancalc.getDuedaterule());
		String query = "";
		try {

			/*
			 * 1 - Equal Monthly Amortization (P + I) 4 - Fixed Amortization 5 - Fixed
			 * Amortization with Balloon Payment
			 */
			System.out.println(params);
			if (loancalc.getRepaytype().equals("1") || loancalc.getRepaytype().equals("4")
					|| loancalc.getRepaytype().equals("5") || loancalc.getRepaytype().equals("6")) {

				if (loancalc.getRepaytype().equals("5")) {
					params.put("amortfee", loancalc.getAmortfee());
//					if(loancalc.getInttype().equals("1")) {
					// Addon
					query = "EXEC fixedwithballoon_addon :amtfinance, 0, :instno, :term, :intrate, 0, :pcycle, :amortfee, :bookingdate, :fduedate, 0, 0, :intcycle, :tcycle, :intpaytype, :prinpycycle ";
//					}else {
//						//Diminishing
//						query = "EXEC FixedWithBalloon_Diminishing :amtfinance, 0, :instno, :term, :intrate, 0, :pcycle, :amortfee, :bookingdate, :fduedate, 0, 0, :intcycle, :tcycle, :intpaytype ";
//					}
				} else if (loancalc.getRepaytype().equals("6")) {
					query = "EXEC fixedPrincipalAmort :amtfinance, 0, :instno, :term, :intrate, :pcycle, :amortfee, :bookingdate, 0, :dueType, :fduedate, :intcycle, :tcycle, 1, :prinpycycle ";

				} else {
					if (loancalc.getProdcode() != null && loancalc.getProdcode().equals("SEALO")) {
						query = "EXEC sp_LoanCalc_EMI @principalamount =:amtfinance, @loanamount = 0, @noofinstallments =:instno, @term =:term, @intrate =:intrate, @intamount = 0, @paycycle =:pcycle, @amort =:amortfee, @bookingdate =:bookingdate, @fduedt =:fduedate, "
								+ "@eir = 0, @duedaterule =:duedaterule, @intcycle =:intcycle, @termcycle =:tcycle, @inpytype =:intpaytype, @prinpycycle =:prinpycycle, @noofintpay =:noofintpay, @noofprinpay =:noofprinpay, @noofadvint =:noofadvint, @noofadvprin =:noofadvprin";

						System.out.println("EXEC sp_LoanCalc_EMI @principalamount =" + params.get("amtfinance")
								+ ", @loanamount = 0, @noofinstallments =" + params.get("instno") + ", @term ="
								+ params.get("term") + ", @intrate =" + params.get("intrate")
								+ ", @intamount = 0, @paycycle =" + params.get("pcycle") + ", @amort="
								+ params.get("amortfee") + ", @bookingdate=" + params.get("bookingdate") + ", @fduedt ="
								+ params.get("fduedate") + ", " + "@eir = 0, @intcycle =" + params.get("intcycle")
								+ ", @duedaterule =" + params.get("duedaterule") + ", @termcycle ="
								+ params.get("tcycle") + ", @inpytype =" + params.get("intpaytype") + ", @prinpycycle ="
								+ params.get("prinpycycle") + ", @noofintpay =" + params.get("noofintpay")
								+ ", @noofprinpay =" + params.get("noofprinpay") + ", @noofadvint ="
								+ params.get("noofadvint") + ", @noofadvprin =" + params.get("noofadvprin"));
					} else {
						// Add - on
						if (loancalc.getInttype().equals("1")) {
							query = "EXEC Addon :amtfinance, 0, :instno, :term, :intrate, 0, :pcycle, :amortfee, :bookingdate, :fduedate, 0, :duedaterule, :intcycle, :tcycle, :intpaytype, :prinpycycle, :duedaterule ";
							System.out.println("EXEC Addon " + params.get("amtfinance") + "," + params.get("amtfinance")
									+ " , 0, " + params.get("term") + ", " + params.get("intrate") + ", 0, "
									+ params.get("pcycle") + ", " + params.get("amortfee") + ", "
									+ params.get("bookingdate") + ", " + params.get("fduedate") + ", 0, 0, "
									+ params.get("intcycle") + ", " + params.get("tcycle") + ", "
									+ params.get("intpaytype") + ", " + params.get("prinpycycle") + ", "
									+ params.get("duedaterule"));
						}
						// Diminishing
						if (loancalc.getInttype().equals("2")) {
							query = "EXEC Contractual :amtfinance, 0, :instno, :term, :intrate, :pcycle, :amortfee, :bookingdate, 0, :dueType, :fduedate, :intcycle, :tcycle, :intpaytype, :prinpycycle, :duedaterule ";
							System.out.println("EXEC Contractual " + params.get("amtfinance") + ", 0, "
									+ params.get("instno") + ", " + params.get("term") + ", " + params.get("intrate")
									+ ", " + params.get("pcycle") + ", " + params.get("amortfee") + ", "
									+ params.get("bookingdate") + ", 0, " + params.get("dueType") + ", "
									+ params.get("fduedate") + ", " + params.get("intcycle") + ", "
									+ params.get("tcycle") + ", " + params.get("intpaytype") + ", "
									+ params.get("prinpycycle") + ", " + params.get("duedaterule"));
						}
						// True Discount
						if (loancalc.getInttype().equals("3")) {
							query = "EXEC TrueDiscount :amtfinance, 0, :instno, :term, :intrate, :pcycle, :amortfee, :bookingdate, 0, :dueType, :fduedate, :intcycle, :tcycle, :intpaytype, :prinpycycle, :duedaterule ";
						}
					}
				}
			}

			// Irregular_Check Discounting
			if (loancalc.getRepaytype().equals("2")) {
				query = "EXEC Irregular_Discounted :amtfinance, :retentionamt, :intrate, :loanappno, :bookingdate, :inttype ";
				System.out.println("EXEC Irregular_Diminishing " + params.get("amtfinance") + ","
						+ params.get("retentionamt") + "," + params.get("intrate") + ", " + params.get("loanappno")
						+ ", " + params.get("bookingdate") + ", " + params.get("inttype"));
			}
			// Irregular_Payment
			if (loancalc.getRepaytype().equals("3")) {
//				query = "EXEC Irregular :amtfinance, :retentionamt, :intrate, :loanappno, :bookingdate, :prinpycycle ";
//				System.out.println("EXEC Irregular "+params.get("prinamount")+","+params.get("retentionamt")+","+params.get("intrate")+", "+params.get("appno")+", "+params.get("bookingdate")+", "+params.get("prinpycycle")+" ");

				// Add - on
				if (loancalc.getInttype().equals("1")) {
					query = "EXEC Irregular_Diminishing :amtfinance, :retentionamt, :intrate, :loanappno, :bookingdate ";
					System.out.println("EXEC Irregular_Diminishing " + params.get("amtfinance") + ","
							+ params.get("retentionamt") + "," + params.get("intrate") + ", " + params.get("loanappno")
							+ ", " + params.get("bookingdate"));

				}
				// Diminishing
				if (loancalc.getInttype().equals("2")) {
					query = "EXEC Irregular_Diminishing :amtfinance, :retentionamt, :intrate, :loanappno, :bookingdate ";
					System.out.println("EXEC Irregular_Diminishing " + params.get("amtfinance") + ","
							+ params.get("retentionamt") + "," + params.get("intrate") + ", " + params.get("loanappno")
							+ ", " + params.get("bookingdate"));
				}
				// True Discount
				if (loancalc.getInttype().equals("3")) {
					query = "EXEC Irregular :amtfinance, :retentionamt, :intrate, :loanappno, :bookingdate, :prinpycycle ";
					System.out.println("EXEC Irregular " + params.get("prinamount") + "," + params.get("retentionamt")
							+ "," + params.get("intrate") + ", " + params.get("loanappno") + ", "
							+ params.get("bookingdate") + ", " + params.get("prinpycycle") + " ");
				}

			}

			// Balloon Payment with Periodic Interest
			if (loancalc.getRepaytype().equals("8")) {
				params.put("dueType", loancalc.getCycle());
				query = "EXEC BalloonPeriodicInterest :amtfinance,0,:instno,:intrate,0,12,0,:bookingdate,0,:dueType,:fduedate,:intcycle,:tcycle,:prinpycycle, :prinpycycle ";
			}

			// Balloon Payment
			if (loancalc.getRepaytype().equals("9")) {
				query = "EXEC BalloonRegular :loanamount, :amtfinance, :retentionamt, :term, :intrate, 0, 0, :bookingdate, 0, :dueType, :fduedate, :intcycle, :tcycle, :prinpycycle, :inttype, :intpaytype";
			}

			// Balloon Interest Paid in Advance
			if (loancalc.getRepaytype().equals("10")) {
				query = "EXEC BalloonInterestPaidInAdvance :amtfinance,0,:term,:intrate,0,0,:bookingdate,0,:dueType,:fduedate,:intcycle,:tcycle, :prinpycycle ";
			}

			// else if(loancalc.getRepaytype().equals("5")) {
			// //Balloon Monthly
			// params.put("dueType", loancalc.getCycle());
			// query = "EXEC BalloonMonthly
			// :amtfinance,0,:instno,:intrate,0,12,0,:bookingdate,0,:dueType,:fduedate,:intcycle,:tcycle
			// ";
			// }

			// with retention
			// params.put("retRate", insert retention rate here);
			// query = "DECLARE @tvpRet AS RetentionTable " + query + " EXEC
			// RetentionGenerator @tvpRet, :retRate";
			// System.out.println(params);
//			System.out.println(params);
			paysched = (List<Tbpaysched>) dbsrvc.executeListSQLQueryWithClass(query, params, Tbpaysched.class);

			if (paysched != null && !paysched.isEmpty()) {
				loancalc.setFirstbookingdate(paysched.get(1).getIlduedt());
				loancalc.setLastamort(paysched.get(loancalc.getPpynum()).getIlamt());

				if (loancalc.getRepaytype().equals("2") || loancalc.getRepaytype().equals("3")
						|| loancalc.getRepaytype().equals("9")) {
					loancalc.setAmortfee(BigDecimal.ZERO);
					if (loancalc.getRepaytype().equals("9")) {
						if (loancalc.getIntpaytype().equals("1")) {
							loancalc.setAmortfee(paysched.get(0).getPrinbal());
						} else {
							loancalc.setAmortfee(loancalc.getAmountfinanced());
						}
					}
//					loancalc.setEir(BigDecimal.ZERO);
//					loancalc.setMir(BigDecimal.ZERO);
					params.put("netproceeds",
							loancalc.getRepaytype().equals("7")
									? loancalc.getAmountfinanced()
											.subtract(loancalc.getBkintey().add(loancalc.getTotalcharges()))
									: loancalc.getAmountfinanced().subtract(loancalc.getTotalcharges()));

					params.put("amort", loancalc.getAmortfee());
					loancalc.setEir(loancalc.getRate().divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));
					loancalc.setMir(loancalc.getEir().divide(new BigDecimal(12), 4, RoundingMode.HALF_UP));
					loancalc.setMaturitydate(paysched.get(loancalc.getPpynum()).getIlduedt());
					loancalc.setEyrate(loancalc.getRate().divide(new BigDecimal(100), 4, RoundingMode.HALF_UP));

					if (loancalc.getRepaytype().equals("2")) {
						dbsrvc.executeUpdate("DELETE FROM TBIRREGSCHED WHERE loanappno=:appno", params);
					}
//					if(loancalc.getRepaytype().equals("3")){
//						dbsrvc.executeUpdate("DELETE FROM TBPDC WHERE targetacctno=:appno", params);
//					}

				} else {
					// Delete FROM TBPDC | TBIRREGSCHED
					params.put("appno", loancalc.getLoanappno());
					dbsrvc.executeUpdate("DELETE FROM TBPDC WHERE targetacctno=:appno", params);
					// dbsrvc.executeUpdate("DELETE FROM TBIRREGSCHED WHERE loanappno=:appno",
					// params);

					loancalc.setAmortfee(paysched.get(1).getIlamt());
					loancalc.setMaturitydate(paysched.get(paysched.size() - 1).getIlduedt());

					params.put("amort", loancalc.getAmortfee());
					// params.put("netproceeds", loancalc.getNetproceeds());
					params.put("netproceeds",
							loancalc.getRepaytype().equals("7")
									? loancalc.getAmountfinanced()
											.subtract(loancalc.getBkintey().add(loancalc.getTotalcharges()))
									: loancalc.getAmountfinanced().subtract(loancalc.getTotalcharges()));
					loancalc.setEir(dbsrvc.getSQLAmount(
							"DECLARE @irr decimal(15,8),@oma int = :noofadvint exec irr :netproceeds,0,:instno,:amort,@irr output, @oma SELECT ROUND(@irr*12,5) ",
							params));
					loancalc.setMir(loancalc.getEir().divide(new BigDecimal(12), 4, RoundingMode.HALF_UP));

					loancalc.setEyrate(dbsrvc.getSQLAmount(
							"DECLARE @irr decimal(15,8),@oma int = :noofadvint exec irr :amtfinance,0,:instno,:amort,@irr output, @oma SELECT ROUND(@irr*12,5) ",
							params));

				}

				loancalc.setAmountfinanced(loancalc.getAmountfinanced());
				loancalc.setPrinbal(paysched.get(0).getPrinbal());
				loancalc.setLoanbal(paysched.get(0).getLoanbal());
				loancalc.setBkintey(paysched.get(0).getIlint());
				loancalc.setInterestdue(paysched.get(0).getIlint());
				if (loancalc.getRepaytype().equals("9")) {
					loancalc.setInterestdue(paysched.get(0).getUidbal());
				}

				if ((loancalc.getRepaytype().equals("5") && loancalc.getInttype().equals("1"))
						|| loancalc.getRepaytype().equals("6")) {
//					System.out.println(Math.pow(1+(loancalc.getRate()/12), 12.00) + " bas");
//					loancalc.setEir((Math.pow(1+(loancalc.getRate()/12), 12.00)-1)*100);
//					loancalc.setMir(loancalc.getRate() / 12);
					loancalc.setInterestdue(paysched.get(0).getUidbal());
//					loancalc.setEyrate(dbsrvc.getSQLAmount("DECLARE @irr decimal(15,8),@amo int = IIF(:fduedate=:bookingdate,1,0) exec irr :prinamount,0,:instno,:amort,@irr output, @amo SELECT ROUND(@irr*12,5) ", params).doubleValue());	
				}
				// System.out.println(loancalc.getEir() + " " + loancalc.getEyrate());
				boolean advPayFlag = false;
				boolean firstNegative = false;
				Date matdt = paysched.get(paysched.size() - 1).getIlduedt();
				loancalc.setMaturitydate(matdt);
				loancalc.setPpynum(paysched.get(paysched.size() - 1).getIlno());
				for (Tbpaysched row : paysched) {
					matdt = row.getIlduedt();
					PaymentScheduleForm form = new PaymentScheduleForm();
					form.setTransDate(row.getIlduedt());
					form.setIntEy(row.getIlno() == 0 && loancalc.getRepaytype().equals("10") ? row.getIlint().negate()
							: row.getIlint());
					form.setDaysdiff(row.getDaysdiff().doubleValue());
					form.setLoanbal(row.getLoanbal());
					form.setPrinbal(row.getPrinbal());
					form.setPrincipal(row.getIlprin());
					form.setTransAmount(row.getIlamt());
					form.setTransType(row.getIlno() == 0 ? "Booking"
							: row.getIlno() == 9999 ? "Advance Payment" : row.getIlno().toString());
					form.setUidey(row.getUidbal());
					forms.add(form);

					if (row.getIlno() == 9999) {
						advPayFlag = true;
					}

					// Irregular Payment
					if (loancalc.getRepaytype().equals("3")
							&& row.getLoanbal().setScale(2, RoundingMode.HALF_UP).compareTo(BigDecimal.ZERO) <= 0
							&& firstNegative == false) {
						firstNegative = true;
						loancalc.setMaturitydate(row.getIlduedt());
						loancalc.setPpynum(row.getIlno());
					}
				}
				loancalc.setPaymentsched(forms);
				params.put("applno", loancalc.getLoanappno());

				// ----------ADVANCE PAYMENT FLAG
				if (advPayFlag) {
					loancalc.setAdvprinamt(
							new BigDecimal(loancalc.getNoofadvprin()).multiply(paysched.get(2).getIlprin()));
					loancalc.setAdvintamt(new BigDecimal(loancalc.getNoofadvint()).multiply(loancalc.getInterestdue()
							.divide(new BigDecimal(loancalc.getNoofintpay()), 2, RoundingMode.HALF_UP)));

					loancalc.setAmortfee((loancalc.getPrinbal().add(loancalc.getInterestdue()))
							.divide(new BigDecimal(loancalc.getNoofprinpay()), 2, RoundingMode.HALF_UP));
					loancalc.setMaturitydate(matdt);

					params.put("amort", loancalc.getAmortfee());
					// params.put("netproceeds", loancalc.getNetproceeds());
					params.put("netproceeds",
							loancalc.getRepaytype().equals("7")
									? loancalc.getAmountfinanced()
											.subtract(loancalc.getBkintey().add(loancalc.getTotalcharges()))
									: loancalc.getAmountfinanced().subtract(loancalc.getTotalcharges()));
					loancalc.setEir(dbsrvc.getSQLAmount(
							"DECLARE @irr decimal(15,8),@oma int = :noofadvint exec irr :netproceeds,0,:instno,:amort,@irr output, @oma SELECT ROUND(@irr*12,5) ",
							params));
					System.out.println(">>>>>EIR: DECLARE @irr decimal(15,8),@oma int = '" + params.get("noofadvint")
							+ "' exec irr '" + params.get("netproceeds") + "',0,'" + params.get("instno") + "','"
							+ params.get("amort") + "',@irr output, @oma SELECT ROUND(@irr*12,5) ");
					loancalc.setMir(loancalc.getEir().divide(new BigDecimal(12), 4, RoundingMode.HALF_UP));

					loancalc.setEyrate(dbsrvc.getSQLAmount(
							"DECLARE @irr decimal(15,8),@oma int = :noofadvint exec irr :amtfinance,0,:instno,:amort,@irr output, @oma SELECT ROUND(@irr*12,5) ",
							params));
					System.out.println(">>>>>EY: DECLARE @irr decimal(15,8),@oma int = '" + params.get("noofadvint")
							+ "' exec irr '" + params.get("amtfinance") + "',0,'" + params.get("instno") + "','"
							+ params.get("amort") + "',@irr output, @oma SELECT ROUND(@irr*12,5) ");

				} else {
					loancalc.setAdvprinamt(BigDecimal.ZERO);
					loancalc.setAdvintamt(BigDecimal.ZERO);
				}

				Tblstapp appdetails = (Tblstapp) dbsrvc.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:applno",
						params);
				Tbaccountinfo accountinfo = (Tbaccountinfo) dbsrvc
						.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno=:applno", params);
				if (accountinfo == null) {
					accountinfo = new Tbaccountinfo();
					accountinfo.setAcctsts(0);
					accountinfo.setTxstat("3");
					accountinfo.setTxcode("10");
				}
				accountinfo.setAccref(loancalc.getRate());
				accountinfo.setAcctoff(username);
				accountinfo.setAmortfee(loancalc.getAmortfee());
				accountinfo.setApplno(loancalc.getLoanappno());
				accountinfo.setClientid(appdetails.getCifno());
				accountinfo.setCurrency("PHP");
				accountinfo.setAdvprinamt(loancalc.getAdvprinamt());
				accountinfo.setAdvintamt(loancalc.getAdvintamt());
				// accountinfo.setDocstamp();

				dbsrvc.saveOrUpdate(accountinfo);

				// //save payments
				// List<Tbpaysched> paysched1 = (List<Tbpaysched>)
				// dbsrvc.executeListHQLQuery("FROM Tbpaysched WHERE applno=:applno", params);
				// if(paysched1 != null){
				// for (Tbpaysched pay : paysched1) {
				// dbsrvc.delete(pay);
				// }
				// }

//				List<Tbpaysched> oldPaysched = (List<Tbpaysched>) dbsrvc.executeListHQLQuery("SELECT * FROM Tbpaysched where applno=: applno", params);
//				if(oldPaysched != null) {
//					dbsrvc.delete(oldPaysched);
//				}

//	

				dbsrvc.executeUpdate("DELETE FROM Tbpaysched WHERE applno=:applno", params);

				// Modified by 09.16.2018 - Added amortid
				int amortid = 0;
				for (PaymentScheduleForm sched : forms) {
					if (!sched.getTransType().equals("Booking") && !sched.getTransType().equals("Advance Payment")) {
						amortid++;
						TbpayschedId id = new TbpayschedId();
						id.setAmortid(amortid);
						id.setApplno(loancalc.getLoanappno());

						Tbpaysched pay = new Tbpaysched();
						pay.setId(id);
						pay.setDaysdiff(sched.getDaysdiff().intValue());
						pay.setIlamt(sched.getTransAmount());
						pay.setIlduedt(sched.getTransDate());
						pay.setIlint(sched.getIntEy());
						pay.setIlintrate(BigDecimal.ZERO);
						pay.setIlno(Integer.valueOf(sched.getTransType()));
						pay.setIlprin(sched.getPrincipal());
						pay.setIsPaid(false);
						pay.setLoanbal(sched.getLoanbal());
						pay.setPrinbal(sched.getPrinbal());
						pay.setLoanno(loancalc.getLoanappno());
						pay.setTxmkr(username);
						pay.setUidbal(sched.getUidey());
						dbsrvc.save(pay);
						System.out.println(">>> SAVING OF LOAN PAYCHED <<<");
					} else {
						System.out.println(">>> ELSE OF LOAN PAYCHED <<<");
					}

				}
//				if(loancalc.getRepaytype().equals("2") || loancalc.getRepaytype().equals("3") || loancalc.getRepaytype().equals("9")){
//					loancalc.setEir(dbsrvc.getSQLAmount("DECLARE @irr decimal(15,8) exec IRR_IRREGULAR :applno, @irr output SELECT ROUND(@irr*12,5) ", params));
//					loancalc.setMir(loancalc.getEir().divide(new BigDecimal(12), 4, RoundingMode.HALF_UP));
//					System.out.println(">>>>>>>>>>>>>>>>>>>>>>EIR IRREG: "+ loancalc.getEir());
//				}

				System.out.println(">>> SAVING OF LOAN DETAILS <<");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loancalc;
	}

	@Override
	public String saveLoanDetails(LoanCalculatorForm loancalc) {
		// TODO Auto-generated method stub
		String result = "Failed";
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("applno", loancalc.getLoanappno());
		System.out.println("<<<<< SAVING OF LOAN DETAILS >>>>>");
		try {
			dbsrvc.executeUpdate("DELETE FROM Tbpaysched WHERE applno=:applno", params);
			Tblstapp appdetails = (Tblstapp) dbsrvc.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:applno", params);

			Tbaccountinfo accountinfo = (Tbaccountinfo) dbsrvc
					.executeUniqueHQLQuery("FROM Tbaccountinfo WHERE applno=:applno", params);
			if (accountinfo == null) {
				accountinfo = new Tbaccountinfo();
				accountinfo.setAcctsts(0);
				accountinfo.setTxstat("3");
				accountinfo.setTxcode("10");
			}
//			if (appdetails.getLoanproduct() != null) {
			if (loancalc.getProdcode() != null) {
				params.put("productcode", loancalc.getProdcode());
				Tbloanproduct loanprod = (Tbloanproduct) dbsrvc
						.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode=:productcode", params);
				if (loanprod != null) {
					accountinfo.setProduct(loanprod.getProductcode());
					accountinfo.setProductGroup(loanprod.getProducttype1() == null ? "" : loanprod.getProducttype1());
					accountinfo.setSubprd(loanprod.getProducttype2() == null ? "" : loanprod.getProducttype2());
					accountinfo.setGracePeriod(loanprod.getLpcgraceperiod() == null ? 0 : loanprod.getLpcgraceperiod());
					accountinfo.setLpcrate(loancalc.getLpcrate());
					accountinfo.setAccrtype(loanprod.getIncomerecognition());
					if (loanprod.getIntpaycomp() != null) {
						accountinfo.setIntcompmethod(loanprod.getIntpaycomp() == null ? "" : loanprod.getIntpaycomp());
						params.put("intpaycomp", loanprod.getIntpaycomp());
						String intcompmethoddesc = (String) dbsrvc.executeUniqueSQLQuery(
								"SELECT desc1 FROM Tbcodetable WHERE codename='INTTYPE' AND codevalue=:intpaycomp",
								params);
						accountinfo.setIncompmethoddesc(intcompmethoddesc == null ? "" : intcompmethoddesc);
					}
					appdetails.setLoanproduct(loanprod.getProductcode());
					if (loancalc.getLoanpurpose() != null && !loancalc.getLoanpurpose().equalsIgnoreCase("")) {
						appdetails.setLoanpurpose(loancalc.getLoanpurpose());
					}
					dbsrvc.saveOrUpdate(appdetails);
				}
			}

			accountinfo.setAccref(loancalc.getRate());
			accountinfo.setAccref(loancalc.getRate());
			accountinfo.setAcctoff(UserUtil.securityService.getUserName());
			accountinfo.setAmortfee(loancalc.getAmortfee());
			accountinfo.setApprbr(appdetails.getBranchcode());
			accountinfo.setApplicationtype(appdetails.getApplicationtype());
			accountinfo.setApplno(loancalc.getLoanappno());
			accountinfo.setClientid(appdetails.getCifno());
			accountinfo.setCurrency("PHP");
			accountinfo.setDesiredMonthlyAmort(BigDecimal.ZERO);
			accountinfo.setDocstamp(BigDecimal.ZERO);
			accountinfo.setDocnum(0);
			accountinfo.setDtbook(loancalc.getBookingdate());
			accountinfo.setFaceamt(loancalc.getLoanamount());
			accountinfo.setFduedt(loancalc.getFirstbookingdate());
			accountinfo.setNotarialFee(BigDecimal.ZERO);
			accountinfo.setNotfee(BigDecimal.ZERO);
			accountinfo.setNotfeedocs(0);
			accountinfo.setName(appdetails.getCifname());
			accountinfo.setNetprcds(loancalc.getNetproceeds());
			accountinfo.setNetprcdsorig(loancalc.getNetproceeds());
			accountinfo.setNominal(loancalc.getRate());
			accountinfo.setInsuranceOutright(loancalc.getTaInsurance());
			accountinfo.setInterestPeriod(loancalc.getIntcycle());
			accountinfo.setIntpycyc(loancalc.getIntcycle());
			accountinfo.setIntpyfddt(loancalc.getFirstbookingdate());
			accountinfo.setInttyp(loancalc.getInttype());
			accountinfo.setLastInstallment(loancalc.getLastamort());
			accountinfo.setLoanoff(UserUtil.securityService.getUserName());
			accountinfo.setMatdt(loancalc.getMaturitydate());
			accountinfo.setMir(loancalc.getMir());
			accountinfo.setEir(loancalc.getEir());
			accountinfo.setOrigbr(appdetails.getBranchcode());

			accountinfo.setOthcharges(loancalc.getOthercharges());
			accountinfo.setOuid(loancalc.getInterestdue());
			accountinfo.setPnamt(loancalc.getPrinbal());
			accountinfo.setPpycyc(loancalc.getCycle());

			if (loancalc.getRepaytype().equals("1") || loancalc.getRepaytype().equals("4")) {
				accountinfo.setPpyfddt(loancalc.getOrigbookingdate());
			} else {
				accountinfo.setPpyfddt(loancalc.getFirstbookingdate());
			}
			accountinfo.setPpynum(loancalc.getPpynum());
			accountinfo.setPpytype(loancalc.getRepaytype());
			accountinfo.setPymntPlan(loancalc.getRepaytype());

			accountinfo.setTerm((loancalc.getTerm() == null ? null : loancalc.getTerm().intValue()));
			accountinfo.setTermcyc(loancalc.getTermcycle());
			accountinfo.setTotalNfcdeductToLoanProc(loancalc.getTotalcharges());
			accountinfo.setTxbranch(appdetails.getBranchcode());

			accountinfo.setTxdate(new Date());
			accountinfo.setTxmkr(UserUtil.securityService.getUserName());
			accountinfo.setTxoff(UserUtil.securityService.getUserName());
			accountinfo.setTxvaldt(loancalc.getBookingdate());
			accountinfo.setUidAdv(loancalc.getAdvanceinterest());
//			accountinfo.setUidBal(loancalc.getInterestdue().subtract(loancalc.getAdvanceinterest()));
			accountinfo.setUidEndDate(loancalc.getMaturitydate());
			accountinfo.setEyrate((loancalc.getEyrate() == null ? null : loancalc.getEyrate()));
			accountinfo.setNotfeedocs(loancalc.getNoofdocs());
			accountinfo.setIntchargedays(loancalc.getIntchargeday());
			accountinfo.setIntchargeamt(loancalc.getIntchargeamt());

			accountinfo.setCfrefnoconcat(loancalc.getCfrefno());
			// Added 09.14.2018 - Kev
			accountinfo.setIntpytype(loancalc.getIntpaytype());
			accountinfo.setIntpaycycle(loancalc.getIntpaycycle());
			accountinfo.setPrinpaycycle(loancalc.getPrinpaycycle());
			accountinfo.setMaturityvalue(loancalc.getMaturityvalue());
			accountinfo.setInterestdue(loancalc.getInterestdue());
			accountinfo.setTotalfeescharges(loancalc.getTotalfeescharges());
			accountinfo.setTotalloanoffsetamt(loancalc.getTotalloanoffsetamt());
			accountinfo.setDownpay(loancalc.getDownpayment());
			accountinfo.setDownpaypcnt(loancalc.getDownpaymentpcnt());
			accountinfo.setRetentionamt(loancalc.getRetentionamt());
			accountinfo.setRetentionpcnt(loancalc.getRetentionpcnt());
			accountinfo.setAmtfinance(loancalc.getAmountfinanced());

			// Added 01.07.2019
			accountinfo.setRepaytypedesc(loancalc.getRepaytypedesc());
			accountinfo.setInttypedesc(loancalc.getInttypedesc());
			accountinfo.setIntpaytypedesc(loancalc.getIntpaytypedesc());
			accountinfo.setTermcycdesc(loancalc.getTermcycdesc());
			accountinfo.setIntcycdesc(loancalc.getIntcycdesc());
			accountinfo.setPrinpaycycdesc(loancalc.getPrinpaycycdesc());
			accountinfo.setIntpaycycdesc(loancalc.getIntpaycycdesc());

			// Added 02.26.2019
			if (loancalc.getIntpaytype().equals("0")) {// In-Arrears
				accountinfo.setAir(loancalc.getInterestdue());
				accountinfo.setUidBal(BigDecimal.ZERO);
				accountinfo.setOuid(loancalc.getInterestdue());
			} else {
				accountinfo.setAir(BigDecimal.ZERO);
				accountinfo.setUidBal(loancalc.getInterestdue().subtract(loancalc.getAdvanceinterest()));
				accountinfo.setOuid(loancalc.getInterestdue().subtract(loancalc.getAdvanceinterest()));
			}
			accountinfo.setIsAmortFeeRounded(false);
			accountinfo.setLegveh(appdetails.getCompanycode());
			accountinfo.setNoOfInterestPaymentToAdvance(0);
			accountinfo.setRprceflg("0");
			accountinfo.setStsdate(new Date());
			accountinfo.setOtherAddtnlToAmortFee(BigDecimal.ZERO);
			accountinfo.setOtherNonFinancialCharges(BigDecimal.ZERO);
			accountinfo.setOrigoff(appdetails.getAccountofficer());

			if (accountinfo.getPdcctr() == null) {
				accountinfo.setPdcctr(0);
			}

			// UNUSED FIELDS
			accountinfo.setAccrtype("");
			accountinfo.setAddon(BigDecimal.ZERO);
			accountinfo.setAdjustedTerm(0);
			accountinfo.setAdjustmentAmortization(BigDecimal.ZERO);
			accountinfo.setDocStampDeductType("");
			accountinfo.setFireInsurance(BigDecimal.ZERO);
			accountinfo.setFireInsuranceDeductType("");
			accountinfo.setFundtype("");

//			//Irregular check discounting
//			if(loancalc.getRepaytype().equals("2")||loancalc.getRepaytype().equals("3")){
//				accountinfo.setInttyp("");
//				accountinfo.setInttypedesc("");
//				accountinfo.setIntpytype("1");//In-advance
//				accountinfo.setIntpaytypedesc("In-advance");
//			}

			// Added May 3, 2019 - Kev
			accountinfo.setTotalfeesandcharges(loancalc.getTotalfeeschargesbilled());
			accountinfo.setNoofintpay(loancalc.getNoofintpay());
			accountinfo.setNoofprinpay(loancalc.getNoofprinpay());
			accountinfo.setNoofadvint(loancalc.getNoofadvint());
			accountinfo.setNoofadvprin(loancalc.getNoofadvprin());
			accountinfo.setDuedaterule(loancalc.getDuedaterule());
			accountinfo.setAdvintamt(loancalc.getAdvintamt());
			accountinfo.setAdvprinamt(loancalc.getAdvprinamt());
			if (dbsrvc.saveOrUpdate(accountinfo)) {
				result = "Success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public String addCheckRecord(Tbpdc pdc) {
		try {
			if (dbsrvc.saveOrUpdate(pdc)) {
				return "Success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Failed";

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbpdc> getCheckList(String loanappno) {
		List<Tbpdc> checks = new ArrayList<Tbpdc>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("loanappno", loanappno);
			checks = (List<Tbpdc>) dbsrvc
					.executeListHQLQuery("FROM Tbpdc WHERE targetacctno=:loanappno order by checkdate ASC", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checks;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbirregsched> getIrregSchedList(String loanappno) {
		List<Tbirregsched> sched = new ArrayList<Tbirregsched>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			params.put("loanappno", loanappno);
			sched = (List<Tbirregsched>) dbsrvc
					.executeListHQLQuery("FROM Tbirregsched WHERE loanappno=:loanappno order by transdate ASC", params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sched;
	}

	@Override
	public String addIrregSched(Tbirregsched sched) {
		try {
			if (dbsrvc.saveOrUpdate(sched)) {
				return "Success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Failed";
	}

	@Override
	public BigDecimal getTotalCheckAmount(String loanappno, List<Tbpdc> pdcs) {
		// TODO Auto-generated method stub
		BigDecimal checkstotal = BigDecimal.ZERO;
		try {
			if (pdcs != null && !pdcs.isEmpty()) {
				for (Tbpdc pdc : pdcs) {
					checkstotal = checkstotal.add(pdc.getCheckamount());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return checkstotal;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbpaysched> getPayschedList(String loanappno) {
		// TODO Auto-generated method stub
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbpaysched> payschedlist = null;
		try {
			params.put("loanappno", loanappno);
			payschedlist = (List<Tbpaysched>) dbsrvc.executeListHQLQuery("FROM Tbpaysched WHERE id.applno=:loanappno",
					params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return payschedlist;
	}

	/**
	 * --Get Existing Loans by CIFNo--
	 * 
	 * @author Kevin (09.14.2018)
	 * @return List<{@link Tbloans}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbloans> getExistingLoansByCIFNo(String cifno) {
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbloans> list = null;
		try {
			if (cifno != null) {
				params.put("cifno", cifno);
				list = (List<Tbloans>) dbsrvc.executeListHQLQuery("FROM Tbloans WHERE principalNo=:cifno", params);
				if (list != null && list.size() > 0) {
					for (Tbloans row : list) {
						params.put("acctno", row.getAccountno());
						row.setMtdint(dbsrvc.getSQLAmount(
								"SELECT dbo.getMTDInt(:acctno, null) FROM Tbloans WHERE accountno=:acctno", params));
						row.setNxtdueilint(
								dbsrvc.getSQLAmount("select interest from getDueAmount(:acctno, null)", params));
						row.setNxtdueilprin(
								dbsrvc.getSQLAmount("select principal from getDueAmount(:acctno, null)", params));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * --Add Loan Account for Offset--
	 * 
	 * @author Kevin (09.15.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String addLoanAccountForOffset(List<Tbloans> tbloans, String appno) {
		String flag = "failed";
		try {
			if (tbloans != null && !tbloans.isEmpty()) {
				for (Tbloans l : tbloans) {
					Tbloanoffset lo = new Tbloanoffset();
					TbloanoffsetId id = new TbloanoffsetId();
					id.setAppno(appno);
					id.setAccountno(l.getAccountno());
					lo.setId(id);
					lo.setCifno(l.getPrincipalNo());
					lo.setApplno(l.getApplno());
					lo.setPnno(l.getPnno());
					lo.setProdcode(l.getProdcode());
					lo.setProductgroup(l.getProductGroup());
					lo.setPrinbal(l.getPrinbal() == null ? BigDecimal.ZERO : l.getPrinbal());
					lo.setLoanbal(l.getLoanbal() == null ? BigDecimal.ZERO : l.getLoanbal());
					lo.setUidbal(l.getUidbal());
					lo.setLpc(l.getLpcbal() == null ? BigDecimal.ZERO : l.getLpcbal());
					lo.setOthercharges(l.getAr1() == null ? BigDecimal.ZERO : l.getAr1());
					lo.setRebate(BigDecimal.ZERO);
					lo.setAcctsts(l.getAcctsts());
					lo.setOutstandingbal(lo.getLoanbal().add(lo.getLpc()).add(lo.getOthercharges()));
					// lo.setAppstatus(l.getAppstatus());
					lo.setTxdate(l.getDtbook());
					dbsrvc.saveOrUpdate(lo);
				}
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Delete Loan Account for Offset--
	 * 
	 * @author Kevin (09.15.2018)
	 * @return String = success, otherwise failed
	 */
	@Override
	public String deleteLoanAccountForOffset(Tbloanoffset loanoffset) {
		String flag = "failed";
		try {
			if (loanoffset != null) {
				if (dbsrvc.delete(loanoffset)) {
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * --Get List of Loan Accounts for Offset--
	 * 
	 * @author Kevin (09.15.2018)
	 * @return List<{@link Tbloanoffset}>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tbloanoffset> getListLoanAcctForOffset(String cifno) {
		Map<String, Object> params = HQLUtil.getMap();
		List<Tbloanoffset> list = null;
		try {
			if (cifno != null) {
				params.put("appno", cifno);
				list = (List<Tbloanoffset>) dbsrvc.executeListHQLQuery("FROM Tbloanoffset WHERE appno=:appno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String deleteCheckRecord(Tbpdc pdc) {
		try {
			if (dbsrvc.delete(pdc)) {
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String deleteIrregSched(Tbirregsched sched) {
		try {
			if (dbsrvc.delete(sched)) {
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public BigDecimal getTotalReceivablesByAppNo(String appno) {
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (appno != null) {
				params.put("appno", appno);
//				BigDecimal total = (BigDecimal) dbsrvc.executeUniqueSQLQuery("SELECT ISNULL(SUM(ISNULL(amount, 0)),0) FROM TBRECEIVABLES WHERE appno=:appno", params);
				BigDecimal total = (BigDecimal) dbsrvc.executeUniqueSQLQuery(
						"SELECT ISNULL(SUM(ISNULL(transamount, 0)),0) FROM TBIRREGSCHED WHERE loanappno=:appno",
						params);
				if (total != null) {
					return total;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return BigDecimal.ZERO;
	}

	@Override
	public Tbloans getLoanAccount(String pnno) {
		Map<String, Object> params = HQLUtil.getMap();
		Tbloans loans = null;
		try {
			if (pnno != null) {
				params.put("pnno", pnno);
				loans = (Tbloans) dbsrvc.executeUniqueHQLQueryMaxResultOne("FROM Tbloans WHERE pnno=:pnno", params);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return loans;
	}
}
