
package com.cifsdb.data;

import java.math.BigDecimal;


/**
 *  CIFSDB.Tbapprelmarketsurvey
 *  09/26/2023 10:13:05
 * 
 */
public class Tbapprelmarketsurvey {

    private Integer id;
    private String appraisalreportid;
    private String appno;
    private String institution;
    private String informant;
    private BigDecimal amountfrom;
    private BigDecimal amountto;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppraisalreportid() {
        return appraisalreportid;
    }

    public void setAppraisalreportid(String appraisalreportid) {
        this.appraisalreportid = appraisalreportid;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getInformant() {
        return informant;
    }

    public void setInformant(String informant) {
        this.informant = informant;
    }

    public BigDecimal getAmountfrom() {
        return amountfrom;
    }

    public void setAmountfrom(BigDecimal amountfrom) {
        this.amountfrom = amountfrom;
    }

    public BigDecimal getAmountto() {
        return amountto;
    }

    public void setAmountto(BigDecimal amountto) {
        this.amountto = amountto;
    }

}
