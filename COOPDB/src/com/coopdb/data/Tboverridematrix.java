
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tboverridematrix
 *  08/27/2024 14:22:57
 * 
 */
public class Tboverridematrix {

    private Integer id;
    private String txcode;
    private String prodcode;
    private String subprodcode;
    private String overriderule;
    private String overriderole;
    private String overrideusername;
    private BigDecimal minamount;
    private BigDecimal maxamount;
    private String requirementtype;
    private String requirementgroup;
    private String status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode;
    }

    public String getSubprodcode() {
        return subprodcode;
    }

    public void setSubprodcode(String subprodcode) {
        this.subprodcode = subprodcode;
    }

    public String getOverriderule() {
        return overriderule;
    }

    public void setOverriderule(String overriderule) {
        this.overriderule = overriderule;
    }

    public String getOverriderole() {
        return overriderole;
    }

    public void setOverriderole(String overriderole) {
        this.overriderole = overriderole;
    }

    public String getOverrideusername() {
        return overrideusername;
    }

    public void setOverrideusername(String overrideusername) {
        this.overrideusername = overrideusername;
    }

    public BigDecimal getMinamount() {
        return minamount;
    }

    public void setMinamount(BigDecimal minamount) {
        this.minamount = minamount;
    }

    public BigDecimal getMaxamount() {
        return maxamount;
    }

    public void setMaxamount(BigDecimal maxamount) {
        this.maxamount = maxamount;
    }

    public String getRequirementtype() {
        return requirementtype;
    }

    public void setRequirementtype(String requirementtype) {
        this.requirementtype = requirementtype;
    }

    public String getRequirementgroup() {
        return requirementgroup;
    }

    public void setRequirementgroup(String requirementgroup) {
        this.requirementgroup = requirementgroup;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
