
package com.loansdb.data;

import java.math.BigDecimal;


/**
 *  LOANSDB.Tbcisecincorporators
 *  10/13/2020 10:21:35
 * 
 */
public class Tbcisecincorporators {

    private Integer id;
    private String cifno;
    private String cireportid;
    private String appno;
    private String name;
    private String nationality;
    private String residenceaddress;
    private String noofshares;
    private BigDecimal paidup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getCireportid() {
        return cireportid;
    }

    public void setCireportid(String cireportid) {
        this.cireportid = cireportid;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getResidenceaddress() {
        return residenceaddress;
    }

    public void setResidenceaddress(String residenceaddress) {
        this.residenceaddress = residenceaddress;
    }

    public String getNoofshares() {
        return noofshares;
    }

    public void setNoofshares(String noofshares) {
        this.noofshares = noofshares;
    }

    public BigDecimal getPaidup() {
        return paidup;
    }

    public void setPaidup(BigDecimal paidup) {
        this.paidup = paidup;
    }

}
