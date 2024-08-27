
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbamortizedattachment
 *  08/27/2024 14:22:57
 * 
 */
public class Tbamortizedattachment {

    private Integer id;
    private String appno;
    private String attachmenttype;
    private String attachmentdetail;
    private BigDecimal amount;
    private Date datecreated;
    private String createdby;
    private String username;

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

    public String getAttachmenttype() {
        return attachmenttype;
    }

    public void setAttachmenttype(String attachmenttype) {
        this.attachmenttype = attachmenttype;
    }

    public String getAttachmentdetail() {
        return attachmentdetail;
    }

    public void setAttachmentdetail(String attachmentdetail) {
        this.attachmentdetail = attachmentdetail;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
