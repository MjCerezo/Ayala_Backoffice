/**
 * 
 */
package com.etel.dashboard.forms;

/**
 * @author ETEL-COMP05
 *
 */
public class DashBoardForm {
	String txType;
	int pending;
	int posted;
	
	public String getTxType() {
		return txType;
	}
	public void setTxType(String txType) {
		this.txType = txType;
	}
	public int getPending() {
		return pending;
	}
	public void setPending(int pending) {
		this.pending = pending;
	}
	public int getPosted() {
		return posted;
	}
	public void setPosted(int posted) {
		this.posted = posted;
	}
}
