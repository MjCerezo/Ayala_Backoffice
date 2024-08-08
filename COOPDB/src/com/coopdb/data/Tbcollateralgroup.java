
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbcollateralgroup
 *  02/13/2018 09:39:38
 * 
 */
public class Tbcollateralgroup {

    private Integer colgroupid;
    private String colgroupname;
    private String colgrouptype;
    private String colgroupstatus;
    private Date dateencoded;
    private String encodedby;
    private String groupappraisalstatus;
    private Date dateoflastappraisal;
    private BigDecimal totalappraisedvalue;
    private BigDecimal totalfairmarket;
    private Date dateupdated;
    private String updatedby;

    public Integer getColgroupid() {
        return colgroupid;
    }

    public void setColgroupid(Integer colgroupid) {
        this.colgroupid = colgroupid;
    }

    public String getColgroupname() {
        return colgroupname;
    }

    public void setColgroupname(String colgroupname) {
        this.colgroupname = colgroupname;
    }

    public String getColgrouptype() {
        return colgrouptype;
    }

    public void setColgrouptype(String colgrouptype) {
        this.colgrouptype = colgrouptype;
    }

    public String getColgroupstatus() {
        return colgroupstatus;
    }

    public void setColgroupstatus(String colgroupstatus) {
        this.colgroupstatus = colgroupstatus;
    }

    public Date getDateencoded() {
        return dateencoded;
    }

    public void setDateencoded(Date dateencoded) {
        this.dateencoded = dateencoded;
    }

    public String getEncodedby() {
        return encodedby;
    }

    public void setEncodedby(String encodedby) {
        this.encodedby = encodedby;
    }

    public String getGroupappraisalstatus() {
        return groupappraisalstatus;
    }

    public void setGroupappraisalstatus(String groupappraisalstatus) {
        this.groupappraisalstatus = groupappraisalstatus;
    }

    public Date getDateoflastappraisal() {
        return dateoflastappraisal;
    }

    public void setDateoflastappraisal(Date dateoflastappraisal) {
        this.dateoflastappraisal = dateoflastappraisal;
    }

    public BigDecimal getTotalappraisedvalue() {
        return totalappraisedvalue;
    }

    public void setTotalappraisedvalue(BigDecimal totalappraisedvalue) {
        this.totalappraisedvalue = totalappraisedvalue;
    }

    public BigDecimal getTotalfairmarket() {
        return totalfairmarket;
    }

    public void setTotalfairmarket(BigDecimal totalfairmarket) {
        this.totalfairmarket = totalfairmarket;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

}
