/**
 * 
 */
package com.etel.lms.forms;

import java.math.BigDecimal;

/**
 * @author Mheanne Munoz
 *
 */
public class GroupPaymentAccountForm {

	private String grouptxrefno;
	private String pnno;
	private String txrefno;
	private BigDecimal txamt;
	private String cifno;
	private String cifname;
	public String getGrouptxrefno() {
		return grouptxrefno;
	}
	public void setGrouptxrefno(String grouptxrefno) {
		this.grouptxrefno = grouptxrefno;
	}
	public String getPnno() {
		return pnno;
	}
	public void setPnno(String pnno) {
		this.pnno = pnno;
	}
	public String getTxrefno() {
		return txrefno;
	}
	public void setTxrefno(String txrefno) {
		this.txrefno = txrefno;
	}
	public BigDecimal getTxamt() {
		return txamt;
	}
	public void setTxamt(BigDecimal txamt) {
		this.txamt = txamt;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getCifname() {
		return cifname;
	}
	public void setCifname(String cifname) {
		this.cifname = cifname;
	}
	
	
	
}
