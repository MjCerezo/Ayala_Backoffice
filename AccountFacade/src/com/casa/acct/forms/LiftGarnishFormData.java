package com.casa.acct.forms;

import java.math.BigDecimal;
import java.util.Date;

public class LiftGarnishFormData {

		String accountno;
		String accountName;
		Date effectivitydate;
		Date expirydate;
		BigDecimal txamount;
		String lockreason;
		public String getAccountno() {
			return accountno;
		}
		public void setAccountno(String accountno) {
			this.accountno = accountno;
		}
		public String getAccountName() {
			return accountName;
		}
		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}
		public Date getEffectivitydate() {
			return effectivitydate;
		}
		public void setEffectivitydate(Date effectivitydate) {
			this.effectivitydate = effectivitydate;
		}
		public Date getExpirydate() {
			return expirydate;
		}
		public void setExpirydate(Date expirydate) {
			this.expirydate = expirydate;
		}
		public BigDecimal getTxamount() {
			return txamount;
		}
		public void setTxamount(BigDecimal txamount) {
			this.txamount = txamount;
		}
		public String getLockreason() {
			return lockreason;
		}
		public void setLockreason(String lockreason) {
			this.lockreason = lockreason;
		}
		
		
}
