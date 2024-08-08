/**
 * 
 */
package com.etel.financial;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.coopdb.data.Tbapd;
import com.coopdb.data.Tbmlacperloanapp;
import com.etel.codetable.forms.CodetableForm;
import com.etel.financial.form.MLACForm;
import com.etel.financial.form.MaxLoanAmountForm;
import com.etel.financial.form.SBLForm;

/**
 * @author ETEL-LAPTOP19
 *
 */
public interface FinancialService {

	BigDecimal computeMLA(MLACForm form);

	Tbapd getLatestPayslip(Tbapd apd);

	String addPayslip(Tbapd apd);

	String deletePayslip(Tbapd apd);

	List<MaxLoanAmountForm> listMLA(MLACForm form);

	List<Tbapd> listPayslip(String memberid);

	String recomputeMLA(String appno);

	Tbapd computeAPD(String appno);

	String checkIfMinimumMaintainingBalLessen(String acctno, BigDecimal transamt, String prodcode);

	BigDecimal getAvailBalance(String acctno);





}
