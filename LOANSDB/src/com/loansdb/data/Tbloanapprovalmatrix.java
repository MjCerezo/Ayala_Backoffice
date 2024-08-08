
package com.loansdb.data;

import java.math.BigDecimal;


/**
 *  LOANSDB.Tbloanapprovalmatrix
 *  10/13/2020 10:21:35
 * 
 */
public class Tbloanapprovalmatrix {

    private Integer id;
    private String transactiontype;
    private String loanproduct;
    private String level1approver;
    private String level2approver;
    private String level3approver;
    private Integer level1requiredapproval;
    private Integer level2requiredapproval;
    private Integer level3requiredapproval;
    private String level1rule;
    private String level2rule;
    private String level3rule;
    private BigDecimal level1limit;
    private BigDecimal level2limit;
    private BigDecimal level3limit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransactiontype() {
        return transactiontype;
    }

    public void setTransactiontype(String transactiontype) {
        this.transactiontype = transactiontype;
    }

    public String getLoanproduct() {
        return loanproduct;
    }

    public void setLoanproduct(String loanproduct) {
        this.loanproduct = loanproduct;
    }

    public String getLevel1approver() {
        return level1approver;
    }

    public void setLevel1approver(String level1approver) {
        this.level1approver = level1approver;
    }

    public String getLevel2approver() {
        return level2approver;
    }

    public void setLevel2approver(String level2approver) {
        this.level2approver = level2approver;
    }

    public String getLevel3approver() {
        return level3approver;
    }

    public void setLevel3approver(String level3approver) {
        this.level3approver = level3approver;
    }

    public Integer getLevel1requiredapproval() {
        return level1requiredapproval;
    }

    public void setLevel1requiredapproval(Integer level1requiredapproval) {
        this.level1requiredapproval = level1requiredapproval;
    }

    public Integer getLevel2requiredapproval() {
        return level2requiredapproval;
    }

    public void setLevel2requiredapproval(Integer level2requiredapproval) {
        this.level2requiredapproval = level2requiredapproval;
    }

    public Integer getLevel3requiredapproval() {
        return level3requiredapproval;
    }

    public void setLevel3requiredapproval(Integer level3requiredapproval) {
        this.level3requiredapproval = level3requiredapproval;
    }

    public String getLevel1rule() {
        return level1rule;
    }

    public void setLevel1rule(String level1rule) {
        this.level1rule = level1rule;
    }

    public String getLevel2rule() {
        return level2rule;
    }

    public void setLevel2rule(String level2rule) {
        this.level2rule = level2rule;
    }

    public String getLevel3rule() {
        return level3rule;
    }

    public void setLevel3rule(String level3rule) {
        this.level3rule = level3rule;
    }

    public BigDecimal getLevel1limit() {
        return level1limit;
    }

    public void setLevel1limit(BigDecimal level1limit) {
        this.level1limit = level1limit;
    }

    public BigDecimal getLevel2limit() {
        return level2limit;
    }

    public void setLevel2limit(BigDecimal level2limit) {
        this.level2limit = level2limit;
    }

    public BigDecimal getLevel3limit() {
        return level3limit;
    }

    public void setLevel3limit(BigDecimal level3limit) {
        this.level3limit = level3limit;
    }

}
