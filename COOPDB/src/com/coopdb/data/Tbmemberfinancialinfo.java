
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbmemberfinancialinfo
 *  08/27/2024 14:22:56
 * 
 */
public class Tbmemberfinancialinfo {

    private Integer id;
    private String membershipappid;
    private String membershipid;
    private String financialtype;
    private String bankname;
    private String accounttype;
    private String assettype;
    private String assetdesc;
    private String accountstatus;
    private BigDecimal outstandingbalance;
    private String branch;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMembershipappid() {
        return membershipappid;
    }

    public void setMembershipappid(String membershipappid) {
        this.membershipappid = membershipappid;
    }

    public String getMembershipid() {
        return membershipid;
    }

    public void setMembershipid(String membershipid) {
        this.membershipid = membershipid;
    }

    public String getFinancialtype() {
        return financialtype;
    }

    public void setFinancialtype(String financialtype) {
        this.financialtype = financialtype;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public String getAssettype() {
        return assettype;
    }

    public void setAssettype(String assettype) {
        this.assettype = assettype;
    }

    public String getAssetdesc() {
        return assetdesc;
    }

    public void setAssetdesc(String assetdesc) {
        this.assetdesc = assetdesc;
    }

    public String getAccountstatus() {
        return accountstatus;
    }

    public void setAccountstatus(String accountstatus) {
        this.accountstatus = accountstatus;
    }

    public BigDecimal getOutstandingbalance() {
        return outstandingbalance;
    }

    public void setOutstandingbalance(BigDecimal outstandingbalance) {
        this.outstandingbalance = outstandingbalance;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

}
