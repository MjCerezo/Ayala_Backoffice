package com.etel.lms.forms;

public class LMSDashboardForm {
	
	String txType;
	int newtx;
	int pending;
	int posted;
	int cancelled;
	
	
	public int getNewtx() {
		return newtx;
	}
	public void setNewtx(int newtx) {
		this.newtx = newtx;
	}
	public int getCancelled() {
		return cancelled;
	}
	public void setCancelled(int cancelled) {
		this.cancelled = cancelled;
	}
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
