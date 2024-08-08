
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbscoredatabaseparams
 *  10/13/2020 10:21:35
 * 
 */
public class Tbscoredatabaseparams {

    private String dbcode;
    private String dbname;
    private String dbnametodisplay;
    private String codestablename;
    private String codenamecolumnname;
    private String codevaluecolumnname;
    private String codedesc1columnname;
    private Integer lasttableseqno;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;

    public String getDbcode() {
        return dbcode;
    }

    public void setDbcode(String dbcode) {
        this.dbcode = dbcode;
    }

    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public String getDbnametodisplay() {
        return dbnametodisplay;
    }

    public void setDbnametodisplay(String dbnametodisplay) {
        this.dbnametodisplay = dbnametodisplay;
    }

    public String getCodestablename() {
        return codestablename;
    }

    public void setCodestablename(String codestablename) {
        this.codestablename = codestablename;
    }

    public String getCodenamecolumnname() {
        return codenamecolumnname;
    }

    public void setCodenamecolumnname(String codenamecolumnname) {
        this.codenamecolumnname = codenamecolumnname;
    }

    public String getCodevaluecolumnname() {
        return codevaluecolumnname;
    }

    public void setCodevaluecolumnname(String codevaluecolumnname) {
        this.codevaluecolumnname = codevaluecolumnname;
    }

    public String getCodedesc1columnname() {
        return codedesc1columnname;
    }

    public void setCodedesc1columnname(String codedesc1columnname) {
        this.codedesc1columnname = codedesc1columnname;
    }

    public Integer getLasttableseqno() {
        return lasttableseqno;
    }

    public void setLasttableseqno(Integer lasttableseqno) {
        this.lasttableseqno = lasttableseqno;
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

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

}
