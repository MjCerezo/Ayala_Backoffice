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
import com.coopdb.data.Tbloanproduct;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tbpaysched;
import com.coopdb.data.TbpayschedId;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.loancalc.forms.LoanCalculatorForm;
import com.etel.loancalc.forms.PaymentScheduleForm;
import com.etel.utils.UserUtil;

/**
 * @author ETEL-LAPTOP07
 *
 */
@SuppressWarnings("unchecked")
public class LoanCalculatorServiceImplNew implements LoanCalculatorServiceNew {

	@Override
	public LoanCalculatorForm computeLoan(LoanCalculatorForm loancalc) {
		// TODO Auto-generated method stub
		List<Tbpaysched> paysched = new ArrayList<Tbpaysched>();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("totalattachment", loancalc.getTotalattachment());
		param.put("productcode", loancalc.getProdcode()); // Temporary
		Tbloanproduct product = (Tbloanproduct) dbsrvc
				.executeUniqueHQLQuery("FROM Tbloanproduct WHERE productcode=:productcode", param);
		param.put("loanappno", loancalc.getLoanappno());
		param.put("amountfinanced", loancalc.getAmountfinanced());
		param.put("noofinstallments", loancalc.getPpynum());
		param.put("intrate",
				loancalc.getRate().compareTo(BigDecimal.ZERO) == 1
						? loancalc.getRate().divide(new BigDecimal(100), 10, RoundingMode.HALF_UP)
						: BigDecimal.ZERO);
		param.put("intratecycle", loancalc.getIntcycle());
		param.put("paymentcycle", loancalc.getCycle());
		param.put("amortization", 0);
		param.put("bookingdate", loancalc.getBookingdate());
		param.put("firstduedate", loancalc.getFirstbookingdate());
		param.put("duedaterule", loancalc.getDuedaterule());
		param.put("intcomptype", loancalc.getInttype());
		param.put("intpaytype", loancalc.getIntpaytype());
		param.put("prinpaytype", loancalc.getIntpaytype()); // Temporary
		param.put("intpaycycle", loancalc.getIntpaycycle());
		param.put("prinpaycycle", loancalc.getPrinpaycycle());
		param.put("noofskippedint", 0);
		param.put("noofskippedprin", 0);
		param.put("ignoreweekend", 2); // Temporary
		param.put("ignoreholiday", 0); // Temporary
		param.put("adjustdays", 1); // Temporary
		param.put("totalcharges", loancalc.getTotalcharges());
		param.put("roundingmethod", product.getRoundingscheme());
		param.put("baseyear", product.getBaseyear());

		// Fixed Amortization Diminishing
		if (loancalc.getRepaytype().equals("1")) {
			param.put("intpaytype", 0); // In Arrears
			param.put("prinpaytype", 0); // In Arrears
		}

		// Irregular Payment Check Discounting
		if (loancalc.getRepaytype().equals("2")) {
			param.put("intpaytype", 0); // In Arrears
			param.put("prinpaytype", 0); // In Arrears
		}
		// Irregular Payment
		if (loancalc.getRepaytype().equals("3")) {
			param.put("intpaytype", 0); // In Arrears
			param.put("prinpaytype", 0); // In Arrears
		}

		// Fixed Amortization Straight Line
		if (loancalc.getRepaytype().equals("4")) {
			param.put("incomptype", 1); // Diminishing
			param.put("intpaytype", 2); // Straight Line / Flat Rate
			param.put("prinpaytype", 2); // Straight Line / Flat Rate
		}

		// Fixed Amortization with Balloon Payment
		if (loancalc.getRepaytype().equals("5")) {
			param.put("amortization", loancalc.getAmortfee());
			param.put("intpaytype", 2); // Days Difference
			param.put("prinpaytype", 0); // In Arrears
		}

		// Fixed Principal
		if (loancalc.getRepaytype().equals("6")) {
			param.put("intpaytype", 3); // Days Difference
			param.put("prinpaytype", 2); // Straight Line / Flat Rate
			param.put("ignoreweekend", 2); // Straight Line / Flat Rate
		}

		// Balloon Payment Periodic Principal / Fixed Principal Balloon Payment
		if (loancalc.getRepaytype().equals("7")) {
			param.put("noofskippedint", loancalc.getPpynum()); // At Maturity
			param.put("prinpaytype", 2); // Straight Line / Flat Rate
		}

		// Balloon Payment Periodic Interest
		if (loancalc.getRepaytype().equals("8")) {
			param.put("noofskippedprin", loancalc.getPpynum()); // At Maturity
			param.put("intpaytype", 2); // Straight Line / Flat Rate
			param.put("paymentcycle", loancalc.getIntpaycycle());
			param.put("prinpaycycle", loancalc.getIntpaycycle());
		}

		// Balloon
		if (loancalc.getRepaytype().equals("9")) {
			param.put("paymentcycle", loancalc.getTermcycle());
		}
		if (product.getIntpaycomp() != null && product.getIntpaycomp().equals("I")
				&& loancalc.getRepaytype().equals("1")) {
			param.put("intpaytype", "3");
		}
		System.out.println(param);

		BigDecimal totalamort = dbsrvc
				.getSQLAmount("SELECT SUM(ISNULL(amount,0)) FROM TBAMORTIZEDATTACHMENT WHERE appno=:loanappno", param);
		loancalc.setTotalattachment(totalamort);
		param.put("totalattachment", loancalc.getTotalattachment());

		String query = "EXEC computeLoan :amountfinanced, :noofinstallments, :intrate, :intratecycle, "
				+ ":paymentcycle, :amortization, :bookingdate, :firstduedate, :duedaterule, :intcomptype, "
				+ ":intpaytype, :prinpaytype, :prinpaycycle, :intpaycycle, :noofskippedint, :noofskippedprin, "
				+ ":ignoreweekend, :ignoreholiday, :adjustdays, :totalcharges, :roundingmethod, :baseyear, :totalattachment";
//		System.out.println("EXEC computeLoan " + param.get("amountfinanced") + "," + param.get("noofinstallments")
//				+ ", " + param.get("intrate") + ", " + param.get("intratecycle") + ", " + param.get("paymentcycle")
//				+ ", " + param.get("amortization") + ", " + param.get("bookingdate") + ", " + param.get("firstduedate")
//				+ "," + param.get("duedaterule") + "," + param.get("intcomptype") + ", " + param.get("intpaytype") + ","
//				+ param.get("prinpaytype") + "," + param.get("prinpaycycle") + "," + param.get("intpaycycle") + ","
//				+ param.get("noofskippedint") + "," + param.get("noofskippedprin") + "," + param.get("ignoreweekend")
//				+ "," + param.get("ignoreholiday") + "," + param.get("adjustdays") + "," + param.get("totalcharges")
//				+ "," + param.get("roundingmethod"));
		try {
			paysched = (List<Tbpaysched>) dbsrvc.execStoredProc(query, param, Tbpaysched.class, 1, null);
			if (paysched == null || paysched.isEmpty()) {
				return loancalc;
			}

			loancalc.setFirstbookingdate(paysched.get(1).getIlduedt());
			loancalc.setLastamort(paysched.get(loancalc.getPpynum()).getIlamt());
			loancalc.setAmortfee(paysched.get(1).getIlamt());
			loancalc.setMir(paysched.get(0).getIltaxrate());
			loancalc.setEir(paysched.get(0).getIlintrate());
			loancalc.setEyrate(loancalc.getMir().multiply(BigDecimal.valueOf(12)));
			loancalc.setEyrate(loancalc.getMir().add(BigDecimal.ONE).pow(Integer.parseInt(loancalc.getIntpaycycle()))
					.subtract(BigDecimal.valueOf(1)).setScale(4, RoundingMode.HALF_UP));
			loancalc.setBkintey(paysched.get(0).getIlint());
			loancalc.setInterestdue(paysched.get(0).getIlint());
			loancalc.setMaturityvalue(paysched.get(0).getIlamt());
			loancalc.setLoanamount(paysched.get(0).getIlprin());
//			System.out.println(loancalc.toString());
			loancalc.setAdvanceinterest(
					loancalc.getAdvanceinterest() == null ? BigDecimal.ZERO : loancalc.getAdvanceinterest());
			loancalc.setTotalcharges(loancalc.getTotalcharges() == null ? BigDecimal.ZERO : loancalc.getTotalcharges());
			loancalc.setIntchargeamt(loancalc.getIntchargeamt() == null ? BigDecimal.ZERO : loancalc.getIntchargeamt());
			loancalc.setNetproceeds(loancalc.getAmountfinanced().subtract(loancalc.getAdvanceinterest())
					.subtract(loancalc.getTotalcharges()).subtract(loancalc.getIntchargeamt()));
			Date matdt = paysched.get(paysched.size() - 1).getIlduedt();
			if (loancalc.getMaturitydate() == null) {
				loancalc.setMaturitydate(matdt);
			} else {
				paysched.get(paysched.size() - 1).setIlduedt(loancalc.getMaturitydate());
			}
			List<PaymentScheduleForm> forms = new ArrayList<PaymentScheduleForm>();
			for (Tbpaysched row : paysched) {
				matdt = row.getIlduedt();
				PaymentScheduleForm form = new PaymentScheduleForm();
				form.setTransDate(row.getIlduedt());
				form.setIntEy(row.getIlint());
				form.setDaysdiff(row.getDaysdiff().doubleValue());
				form.setLoanbal(row.getLoanbal());
				form.setPrinbal(row.getPrinbal());
				form.setPrincipal(row.getIlprin());
				form.setTransAmount(row.getIlamt());
				form.setTransType(row.getIlno() == 0 ? "Booking"
						: row.getIlno() == 9999 ? "Advance Payment" : row.getIlno().toString());
				form.setUidey(row.getUidbal());
				form.setOthercharge(row.getOthers()); // 02-21-23
				forms.add(form);

			}

			loancalc.setPaymentsched(forms);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return loancalc;
	}

	@Override
	public String saveLoanDetails(LoanCalculatorForm loancalc) {
		// TODO Auto-generated method stub
		String result = "Failed";

		DBService dbsrvc = new DBServiceImpl();
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("applno", loancalc.getLoanappno());
//		System.out.println(">>>>>>>>>> SAVING OF LOAN DETAILS <<<<<<<<<<<<<");
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
					accountinfo.setLpcrate(loanprod.getLpcrate());
					accountinfo.setAccrtype(loanprod.getAccrtype());
					accountinfo.setIncomerecognition(loanprod.getIncomerecognition());
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
			if (accountinfo.getIncomerecognition().equals("A")) {
				accountinfo.setOuid(loancalc.getInterestdue());
			}
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
//			if (accountinfo.getIncomerecognition().equals("C")) {// Cash Basis
//				accountinfo.setAir(BigDecimal.ZERO);
//				accountinfo.setUidBal(BigDecimal.ZERO);
//				accountinfo.setOuid(BigDecimal.ZERO);
//			} else {
//				accountinfo.setAir(loancalc.getInterestdue());
//				accountinfo.setUidBal(loancalc.getInterestdue().subtract(loancalc.getAdvanceinterest()));
//				accountinfo.setOuid(loancalc.getInterestdue().subtract(loancalc.getAdvanceinterest()));
//			}
			// Added 03.30.2023 Ced
//			if (accountinfo.getIntcompmethod().equals("2")) {// Diminishing
//				accountinfo.setAir(BigDecimal.ZERO);
//				accountinfo.setUidBal(BigDecimal.ZERO);
//				accountinfo.setOuid(BigDecimal.ZERO);
//			} else {
//				accountinfo.setAir(loancalc.getInterestdue());
//				accountinfo.setUidBal(loancalc.getInterestdue().subtract(loancalc.getAdvanceinterest()));
//				accountinfo.setOuid(loancalc.getInterestdue().subtract(loancalc.getAdvanceinterest()));
//			}
			accountinfo.setAir(loancalc.getPaymentsched().get(0).getIntEy());
			accountinfo.setUidBal(loancalc.getPaymentsched().get(0).getUidey());
			accountinfo.setOuid(loancalc.getPaymentsched().get(0).getUidey());

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
				int amortid = 0;
				for (PaymentScheduleForm sched : loancalc.getPaymentsched()) {
					if (sched.getTransType().equals("Booking") || sched.getTransType().equals("Advance Payment")) {
						sched.setTransType("0");
					}
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
					pay.setTxmkr(UserUtil.securityService.getUserName());
					pay.setUidbal(sched.getUidey());
					dbsrvc.save(pay);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
