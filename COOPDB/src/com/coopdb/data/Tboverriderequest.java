
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tboverriderequest
 *  08/04/2024 12:54:41
 * 
 */
public class Tboverriderequest {

    private Integer id;
    private String txrefno;
    private String txcode;
    private String accountno;
    private Date requestdate;
    private String requestedby;
    private String overrideby;
    private String status;
    private Date statusdate;
    private String overriderule;
    private Integer overrideruleid;
    private String requirementtype;
    private String requirementgroup;
    private String overridemessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxrefno() {
        return txrefno;
    }

    public void setTxrefno(String txrefno) {
        this.txrefno = txrefno;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public Date getRequestdate() {
        return requestdate;
    }

    public void setRequestdate(Date requestdate) {
        this.requestdate = requestdate;
    }

    public String getRequestedby() {
        return requestedby;
    }

    public void setRequestedby(String requestedby) {
        this.requestedby = requestedby;
    }

    public String getOverrideby() {
        return overrideby;
    }

    public void setOverrideby(String overrideby) {
        this.overrideby = overrideby;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getStatusdate() {
        return statusdate;
    }

    public void setStatusdate(Date statusdate) {
        this.statusdate = statusdate;
    }

    public String getOverriderule() {
        return overriderule;
    }

    public void setOverriderule(String overriderule) {
        this.overriderule = overriderule;
    }

    public Integer getOverrideruleid() {
        return overrideruleid;
    }

    public void setOverrideruleid(Integer overrideruleid) {
        this.overrideruleid = overrideruleid;
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

    public String getOverridemessage() {
        return overridemessage;
    }

    public void setOverridemessage(String overridemessage) {
        this.overridemessage = overridemessage;
    }

}
