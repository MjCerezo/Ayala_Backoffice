
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbdocaffiant
 *  10/13/2020 10:21:35
 * 
 */
public class Tbdocaffiant {

    private Integer id;
    private String appno;
    private String documentcode;
    private String affiant;
    private String govid;
    private String idno;
    private String placeissued;
    private Date dateissued;
    private String position;
    private String signatorycode;

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

    public String getDocumentcode() {
        return documentcode;
    }

    public void setDocumentcode(String documentcode) {
        this.documentcode = documentcode;
    }

    public String getAffiant() {
        return affiant;
    }

    public void setAffiant(String affiant) {
        this.affiant = affiant;
    }

    public String getGovid() {
        return govid;
    }

    public void setGovid(String govid) {
        this.govid = govid;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getPlaceissued() {
        return placeissued;
    }

    public void setPlaceissued(String placeissued) {
        this.placeissued = placeissued;
    }

    public Date getDateissued() {
        return dateissued;
    }

    public void setDateissued(Date dateissued) {
        this.dateissued = dateissued;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSignatorycode() {
        return signatorycode;
    }

    public void setSignatorycode(String signatorycode) {
        this.signatorycode = signatorycode;
    }

}
