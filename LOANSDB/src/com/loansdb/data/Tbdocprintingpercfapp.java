
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbdocprintingpercfapp
 *  10/13/2020 10:21:35
 * 
 */
public class Tbdocprintingpercfapp {

    private TbdocprintingpercfappId id;
    private String cfrefnoconcat;
    private String appno;
    private String facilityname;
    private String documentname;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;
    private String remarks;
    private Boolean enableincorporate;
    private Boolean enableinsoleproprietorship;
    private String pagename;

    public TbdocprintingpercfappId getId() {
        return id;
    }

    public void setId(TbdocprintingpercfappId id) {
        this.id = id;
    }

    public String getCfrefnoconcat() {
        return cfrefnoconcat;
    }

    public void setCfrefnoconcat(String cfrefnoconcat) {
        this.cfrefnoconcat = cfrefnoconcat;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getFacilityname() {
        return facilityname;
    }

    public void setFacilityname(String facilityname) {
        this.facilityname = facilityname;
    }

    public String getDocumentname() {
        return documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getEnableincorporate() {
        return enableincorporate;
    }

    public void setEnableincorporate(Boolean enableincorporate) {
        this.enableincorporate = enableincorporate;
    }

    public Boolean getEnableinsoleproprietorship() {
        return enableinsoleproprietorship;
    }

    public void setEnableinsoleproprietorship(Boolean enableinsoleproprietorship) {
        this.enableinsoleproprietorship = enableinsoleproprietorship;
    }

    public String getPagename() {
        return pagename;
    }

    public void setPagename(String pagename) {
        this.pagename = pagename;
    }

}
