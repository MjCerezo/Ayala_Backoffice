
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbapprovedcftermconditions
 *  10/13/2020 10:21:35
 * 
 */
public class Tbapprovedcftermconditions {

    private Integer id;
    private String cfrefno;
    private Integer cflevel;
    private String cfseqno;
    private String cfsubseqno;
    private String cfrefnoconcat;
    private String cfappno;
    private String cftermconditions;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date lastupdated;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getCfappno() {
        return cfappno;
    }

    public void setCfappno(String cfappno) {
        this.cfappno = cfappno;
    }

    public String getCftermconditions() {
        return cftermconditions;
    }

    public void setCftermconditions(String cftermconditions) {
        this.cftermconditions = cftermconditions;
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

}
