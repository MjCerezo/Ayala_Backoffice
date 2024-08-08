
package com.loansdb.data;

import java.math.BigDecimal;


/**
 *  LOANSDB.Tblamsublimits
 *  10/13/2020 10:21:35
 * 
 */
public class Tblamsublimits {

    private Integer id;
    private Integer evalreportid;
    private String appno;
    private String maincfrefno;
    private String cfrefno;
    private Integer cflevel;
    private String cfseqno;
    private String cfsubseqno;
    private String cfrefnoconcat;
    private String cfcode;
    private String facilitytype;
    private BigDecimal approvedloantamount;
    private BigDecimal availedamount;
    private BigDecimal proposedamount;
    private String remarks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEvalreportid() {
        return evalreportid;
    }

    public void setEvalreportid(Integer evalreportid) {
        this.evalreportid = evalreportid;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getMaincfrefno() {
        return maincfrefno;
    }

    public void setMaincfrefno(String maincfrefno) {
        this.maincfrefno = maincfrefno;
    }

    public String getCfrefno() {
        return cfrefno;
    }

    public void setCfrefno(String cfrefno) {
        this.cfrefno = cfrefno;
    }

    public Integer getCflevel() {
        return cflevel;
    }

    public void setCflevel(Integer cflevel) {
        this.cflevel = cflevel;
    }

    public String getCfseqno() {
        return cfseqno;
    }

    public void setCfseqno(String cfseqno) {
        this.cfseqno = cfseqno;
    }

    public String getCfsubseqno() {
        return cfsubseqno;
    }

    public void setCfsubseqno(String cfsubseqno) {
        this.cfsubseqno = cfsubseqno;
    }

    public String getCfrefnoconcat() {
        return cfrefnoconcat;
    }

    public void setCfrefnoconcat(String cfrefnoconcat) {
        this.cfrefnoconcat = cfrefnoconcat;
    }

    public String getCfcode() {
        return cfcode;
    }

    public void setCfcode(String cfcode) {
        this.cfcode = cfcode;
    }

    public String getFacilitytype() {
        return facilitytype;
    }

    public void setFacilitytype(String facilitytype) {
        this.facilitytype = facilitytype;
    }

    public BigDecimal getApprovedloantamount() {
        return approvedloantamount;
    }

    public void setApprovedloantamount(BigDecimal approvedloantamount) {
        this.approvedloantamount = approvedloantamount;
    }

    public BigDecimal getAvailedamount() {
        return availedamount;
    }

    public void setAvailedamount(BigDecimal availedamount) {
        this.availedamount = availedamount;
    }

    public BigDecimal getProposedamount() {
        return proposedamount;
    }

    public void setProposedamount(BigDecimal proposedamount) {
        this.proposedamount = proposedamount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
