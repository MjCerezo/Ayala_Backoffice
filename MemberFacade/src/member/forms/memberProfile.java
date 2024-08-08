package member.forms;

import java.util.List;

import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tblstapp;
import com.coopdb.data.Tblstcomakers;
import com.coopdb.data.Tbmember;
import member.forms.CapConDeposits;

public class memberProfile {
	private Tbmember memberpersonalinformation;
	private List<CapConDeposits> memberdeposits;
	private List<CapConDeposits> capitalcontibutions;
	private List<Tbloans> loanaccountsborrower;
	private List<Tbloans> loanaccountscomaker;
	
	public Tbmember getMemberpersonalinformation() {
		return memberpersonalinformation;
	}
	public void setMemberpersonalinformation(Tbmember memberpersonalinformation) {
		this.memberpersonalinformation = memberpersonalinformation;
	}
	public List<CapConDeposits> getMemberdeposits() {
		return memberdeposits;
	}
	public void setMemberdeposits(List<CapConDeposits> memberdeposits) {
		this.memberdeposits = memberdeposits;
	}
	public List<Tbloans> getLoanaccountsborrower() {
		return loanaccountsborrower;
	}
	public void setLoanaccountsborrower(List<Tbloans> loanaccountsborrower) {
		this.loanaccountsborrower = loanaccountsborrower;
	}
	public List<Tbloans> getLoanaccountscomaker() {
		return loanaccountscomaker;
	}
	public void setLoanaccountscomaker(List<Tbloans> loanaccountscomaker) {
		this.loanaccountscomaker = loanaccountscomaker;
	}
	public List<CapConDeposits> getCapitalcontibutions() {
		return capitalcontibutions;
	}
	public void setCapitalcontibutions(List<CapConDeposits> capitalcontibutions) {
		this.capitalcontibutions = capitalcontibutions;
	}
}
