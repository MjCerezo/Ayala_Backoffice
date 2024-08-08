
package com.loansdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.Tbincomeexpense
 *  10/13/2020 10:21:35
 * 
 */
public class Tbincomeexpense {

    private Integer id;
    private String appno;
    private Integer evalreportid;
    private String cifno;
    private String type;
    private String particulars;
    private String otherparticulars;
    private BigDecimal amount;
    private Date datefrom;
    private Date dateto;
    private Date datecreated;
    private String createdby;
    private Date dateupdated;
    private String updatedby;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public Integer getEvalreportid() {
        return evalreportid;
    }

    public void setEvalreportid(Integer evalreportid) {
        this.evalreportid = evalreportid;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public String getOtherparticulars() {
        return otherparticulars;
    }

    public void setOtherparticulars(String otherparticulars) {
        this.otherparticulars = otherparticulars;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(Date datefrom) {
        this.datefrom = datefrom;
    }

    public Date getDateto() {
        return dateto;
    }

    public void setDateto(Date dateto) {
        this.dateto = dateto;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
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
