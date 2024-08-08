/**
 * 
 */
package com.casa.acct.util;

import java.util.HashMap;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class AccountNumberGenerator {

	public static String genereteAccountNumber(String unit, String prodcode, String prodgroup) {
		String accountNumber = "failed";
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("unit", unit);
		param.put("prodcode", prodcode);
		param.put("prodgroup", prodgroup);
		try {
			accountNumber = (String) dbsrvc.executeUniqueSQLQuery(
					"declare @acctno varchar(25) exec SEQGEN_ACCTNO :unit,:prodcode,:prodgroup,@acctno output select @acctno",
					param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return accountNumber.concat(getCheckDigit(accountNumber, 93812));
	}

	public static String getCheckDigit(String accountno, int divisor) {

		// MODULUS 11
		String checkdigit = "";
		String strDivisor = String.valueOf(divisor);
		String[] div = strDivisor.split("");
		String[] acctno = accountno.split("");

		// System.out.println(acctno.length);

		int chkdgt = 0;
		int sum = 0;
		int n = 1;
		for (int i = 1; i < acctno.length; i++) {

			// System.out.println(div.length-1);

			if (n == (div.length))
				n = 1;

			// System.out.println("ACCOUNT[" + i + "]:" + acctno[i]);
			// System.out.println("DIV[" + n + "]:" + div[n]);
			sum = sum + (Character.getNumericValue(acctno[i].charAt(0)) * Integer.valueOf(div[n]));

			n++;

		}

		// System.out.println("SUM:" + sum);
		chkdgt = sum % 11;
		chkdgt = 11 - chkdgt;

		// rules agreed with Sir Lys 09152016
		if (chkdgt == 11 || chkdgt == 10)
			chkdgt = 0;

		checkdigit = String.valueOf(chkdgt);

		return checkdigit;

	}
}
