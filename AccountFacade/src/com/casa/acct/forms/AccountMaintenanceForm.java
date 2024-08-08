package com.casa.acct.forms;

import java.util.Date;

public class AccountMaintenanceForm {

	private String accountno;
	private String name;
	private int	acctsts;
	private Date dtopened;
	private String shortname;
	private String jointaccttype;
	private String posttx;
	private Integer alertflag;
	private Integer alertlevel;
	private String alertmessage;
	private String watchlist;
	private String prodtype;
	private String solofficer;
	private String refofficer;
	private String channel;
	private boolean ataind;
	
	public String getAccountno() {
		return accountno;
	}
	public String getName() {
		return name;
	}
	public int getAcctsts() {
		return acctsts;
	}
	public Date getDtopened() {
		return dtopened;
	}
	public String getShortname() {
		return shortname;
	}
	public String getJointaccttype() {
		return jointaccttype;
	}
	public String getPosttx() {
		return posttx;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAcctsts(int acctsts) {
		this.acctsts = acctsts;
	}
	public void setDtopened(Date dtopened) {
		this.dtopened = dtopened;
	}
	public void setShortname(String shortname) {
		this.shortname = shortname;
	}
	public void setJointaccttype(String jointaccttype) {
		this.jointaccttype = jointaccttype;
	}
	public void setPosttx(String posttx) {
		this.posttx = posttx;
	}
	public Integer getAlertflag() {
		return alertflag;
	}
	public void setAlertflag(Integer alertflag) {
		this.alertflag = alertflag;
	}
	public Integer getAlertlevel() {
		return alertlevel;
	}
	public void setAlertlevel(Integer alertlevel) {
		this.alertlevel = alertlevel;
	}
	public String getAlertmessage() {
		return alertmessage;
	}
	public void setAlertmessage(String alertmessage) {
		this.alertmessage = alertmessage;
	}
	public String getWatchlist() {
		return watchlist;
	}
	public void setWatchlist(String watchlist) {
		this.watchlist = watchlist;
	}
	public String getProdtype() {
		return prodtype;
	}
	public void setProdtype(String prodtype) {
		this.prodtype = prodtype;
	}
	public String getSolofficer() {
		return solofficer;
	}
	public String getRefofficer() {
		return refofficer;
	}
	public String getChannel() {
		return channel;
	}
	public void setSolofficer(String solofficer) {
		this.solofficer = solofficer;
	}
	public void setRefofficer(String refofficer) {
		this.refofficer = refofficer;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public boolean isAtaind() {
		return ataind;
	}
	public void setAtaind(boolean ataind) {
		this.ataind = ataind;
	}
	
	
	
}
