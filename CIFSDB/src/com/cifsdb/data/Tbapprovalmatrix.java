
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tbapprovalmatrix
 *  09/26/2023 10:13:06
 * 
 */
public class Tbapprovalmatrix {

    private Integer id;
    private String transactiontype;
    private String loanproduct;
    private String level1approver;
    private String level2approver;
    private String level3approver;
    private Integer level1requiredapproval;
    private Integer level2requiredapproval;
    private Integer level3requiredapproval;
    private Integer level1requiredrejected;
    private Integer level2requiredrejected;
    private Integer level3requiredrejected;
    private String level1rule;
    private String level2rule;
    private String level3rule;
    private BigDecimal level1limit;
    private BigDecimal level2limit;
    private BigDecimal level3limit;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;
    private String level4approver;
    private Integer level4requiredapproval;
    private Integer level4requiredrejected;
    private String level4rule;
    private BigDecimal level4limit;

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

    public Integer getLevel1requiredrejected() {
        return level1requiredrejected;
    }

    public void setLevel1requiredrejected(Integer level1requiredrejected) {
        this.level1requiredrejected = level1requiredrejected;
    }

    public Integer getLevel2requiredrejected() {
        return level2requiredrejected;
    }

    public void setLevel2requiredrejected(Integer level2requiredrejected) {
        this.level2requiredrejected = level2requiredrejected;
    }

    public Integer getLevel3requiredrejected() {
        return level3requiredrejected;
    }

    public void setLevel3requiredrejected(Integer level3requiredrejected) {
        this.level3requiredrejected = level3requiredrejected;
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

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getLastupdated() {
        return lastupdated;
    }

    public void setLastupdated(Date lastupdated) {
        this.lastupdated = lastupdated;
    }

    public String getLevel4approver() {
        return level4approver;
    }

    public void setLevel4approver(String level4approver) {
        this.level4approver = level4approver;
    }

    public Integer getLevel4requiredapproval() {
        return level4requiredapproval;
    }

    public void setLevel4requiredapproval(Integer level4requiredapproval) {
        this.level4requiredapproval = level4requiredapproval;
    }

    public Integer getLevel4requiredrejected() {
        return level4requiredrejected;
    }

    public void setLevel4requiredrejected(Integer level4requiredrejected) {
        this.level4requiredrejected = level4requiredrejected;
    }

    public String getLevel4rule() {
        return level4rule;
    }

    public void setLevel4rule(String level4rule) {
        this.level4rule = level4rule;
    }

    public BigDecimal getLevel4limit() {
        return level4limit;
    }

    public void setLevel4limit(BigDecimal level4limit) {
        this.level4limit = level4limit;
    }

}
