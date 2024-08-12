
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbevalmonthlyincome
 *  08/10/2024 21:24:57
 * 
 */
public class Tbevalmonthlyincome {

    private Integer id;
    private Integer evalreportid;
    private String appno;
    private String persontype;
    private String incomesource;
    private BigDecimal grossincome;
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

    public String getPersontype() {
        return persontype;
    }

    public void setPersontype(String persontype) {
        this.persontype = persontype;
    }

    public String getIncomesource() {
        return incomesource;
    }

    public void setIncomesource(String incomesource) {
        this.incomesource = incomesource;
    }

    public BigDecimal getGrossincome() {
        return grossincome;
    }

    public void setGrossincome(BigDecimal grossincome) {
        this.grossincome = grossincome;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
