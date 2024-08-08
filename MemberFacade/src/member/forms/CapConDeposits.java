package member.forms;

import java.math.BigDecimal;

public class CapConDeposits {
	 
	private String accountnocapcon;
	private BigDecimal accountbalancecapcpon;
	private String accounttypedeposits;
	private String accountnodeposits;
	private BigDecimal accountbalancedeposits;
	private BigDecimal sumdeposits;
	private BigDecimal sumcapcon;
	
	public BigDecimal getSumdeposits() {
		return sumdeposits;
	}
	public void setSumdeposits(BigDecimal sumdeposits) {
		this.sumdeposits = sumdeposits;
	}
	public BigDecimal getSumcapcon() {
		return sumcapcon;
	}
	public void setSumcapcon(BigDecimal sumcapcon) {
		this.sumcapcon = sumcapcon;
	}
	public String getAccountnocapcon() {
		return accountnocapcon;
	}
	public void setAccountnocapcon(String accountnocapcon) {
		this.accountnocapcon = accountnocapcon;
	}
	public BigDecimal getAccountbalancecapcpon() {
		return accountbalancecapcpon;
	}
	public void setAccountbalancecapcpon(BigDecimal accountbalancecapcpon) {
		this.accountbalancecapcpon = accountbalancecapcpon;
	}
	public String getAccounttypedeposits() {
		return accounttypedeposits;
	}
	public void setAccounttypedeposits(String accounttypedeposits) {
		this.accounttypedeposits = accounttypedeposits;
	}
	public String getAccountnodeposits() {
		return accountnodeposits;
	}
	public void setAccountnodeposits(String accountnodeposits) {
		this.accountnodeposits = accountnodeposits;
	}
	public BigDecimal getAccountbalancedeposits() {
		return accountbalancedeposits;
	}
	public void setAccountbalancedeposits(BigDecimal accountbalancedeposits) {
		this.accountbalancedeposits = accountbalancedeposits;
	}
}
