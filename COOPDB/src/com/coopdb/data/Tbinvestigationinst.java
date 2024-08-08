
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbinvestigationinst
 *  08/04/2024 12:54:42
 * 
 */
public class Tbinvestigationinst {

    private TbinvestigationinstId id;
    private String customername;
    private String instruction;
    private String initiatedby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;
    private String aoremarks;
    private String supervisor;
    private String supervisorremarks;
    private String customertype;
    private String status;
    private Date lastrecorddate;
    private String participationcode;
    private String companycode;
    private String referenceno;
    private String collateraltype;
    private Date dateoflastappraisal;
    private BigDecimal lastappraisedvalue;
    private String appraisalstatus;
    private String groupid;
    private String groupname;
    private String grouptype;
    private BigDecimal totalappraisedvalue;
    private String collateralcategory;

    public TbinvestigationinstId getId() {
        return id;
    }

    public void setId(TbinvestigationinstId id) {
        this.id = id;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getInitiatedby() {
        return initiatedby;
    }

    public void setInitiatedby(String initiatedby) {
        this.initiatedby = initiatedby;
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

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getAoremarks() {
        return aoremarks;
    }

    public void setAoremarks(String aoremarks) {
        this.aoremarks = aoremarks;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getSupervisorremarks() {
        return supervisorremarks;
    }

    public void setSupervisorremarks(String supervisorremarks) {
        this.supervisorremarks = supervisorremarks;
    }

    public String getCustomertype() {
        return customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getLastrecorddate() {
        return lastrecorddate;
    }

    public void setLastrecorddate(Date lastrecorddate) {
        this.lastrecorddate = lastrecorddate;
    }

    public String getParticipationcode() {
        return participationcode;
    }

    public void setParticipationcode(String participationcode) {
        this.participationcode = participationcode;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public String getCollateraltype() {
        return collateraltype;
    }

    public void setCollateraltype(String collateraltype) {
        this.collateraltype = collateraltype;
    }

    public Date getDateoflastappraisal() {
        return dateoflastappraisal;
    }

    public void setDateoflastappraisal(Date dateoflastappraisal) {
        this.dateoflastappraisal = dateoflastappraisal;
    }

    public BigDecimal getLastappraisedvalue() {
        return lastappraisedvalue;
    }

    public void setLastappraisedvalue(BigDecimal lastappraisedvalue) {
        this.lastappraisedvalue = lastappraisedvalue;
    }

    public String getAppraisalstatus() {
        return appraisalstatus;
    }

    public void setAppraisalstatus(String appraisalstatus) {
        this.appraisalstatus = appraisalstatus;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getGroupname() {
        return groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    public String getGrouptype() {
        return grouptype;
    }

    public void setGrouptype(String grouptype) {
        this.grouptype = grouptype;
    }

    public BigDecimal getTotalappraisedvalue() {
        return totalappraisedvalue;
    }

    public void setTotalappraisedvalue(BigDecimal totalappraisedvalue) {
        this.totalappraisedvalue = totalappraisedvalue;
    }

    public String getCollateralcategory() {
        return collateralcategory;
    }

    public void setCollateralcategory(String collateralcategory) {
        this.collateralcategory = collateralcategory;
    }

}
