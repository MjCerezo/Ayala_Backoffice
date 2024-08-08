package com.etel.deposit.form;

import java.math.BigDecimal;
import java.util.Date;

public class DepositAccountForm {
	
	String acctno;
	String productcode;
	String productname;
	String subprodcode;
	String subprodname;
	String accountname;
	BigDecimal acctbalance;
	BigDecimal pledgeamt;
	BigDecimal floatamt;
	String acctsts;
	Date dateavailed;
	
	public String getAcctno() {
		return acctno;
	}
	public void setAcctno(String acctno) {
		this.acctno = acctno;
	}
	public String getProductcode() {
		return productcode;
	}
	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getSubprodcode() {
		return subprodcode;
	}
	public void setSubprodcode(String subprodcode) {
		this.subprodcode = subprodcode;
	}
	public String getSubprodname() {
		return subprodname;
	}
	public void setSubprodname(String subprodname) {
		this.subprodname = subprodname;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}
	public BigDecimal getAcctbalance() {
		return acctbalance;
	}
	public void setAcctbalance(BigDecimal acctbalance) {
		this.acctbalance = acctbalance;
	}
	public BigDecimal getPledgeamt() {
		return pledgeamt;
	}
	public void setPledgeamt(BigDecimal pledgeamt) {
		this.pledgeamt = pledgeamt;
	}
	public BigDecimal getFloatamt() {
		return floatamt;
	}
	public void setFloatamt(BigDecimal floatamt) {
		this.floatamt = floatamt;
	}
	public String getAcctsts() {
		return acctsts;
	}
	public void setAcctsts(String acctsts) {
		this.acctsts = acctsts;
	}
	public Date getDateavailed() {
		return dateavailed;
	}
	public void setDateavailed(Date dateavailed) {
		this.dateavailed = dateavailed;
	}
}
