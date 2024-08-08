
package com.loansdb.data;



/**
 *  LOANSDB.Tblamtraderef
 *  10/13/2020 10:21:35
 * 
 */
public class Tblamtraderef {

    private TblamtraderefId id;
    private String traderefname;
    private String accreditationstatus;
    private String cireportid;
    private String evalrecommendation;
    private String evaluatorremarks;
    private String approvalstatus;

    public TblamtraderefId getId() {
        return id;
    }

    public void setId(TblamtraderefId id) {
        this.id = id;
    }

    public String getTraderefname() {
        return traderefname;
    }

    public void setTraderefname(String traderefname) {
        this.traderefname = traderefname;
    }

    public String getAccreditationstatus() {
        return accreditationstatus;
    }

    public void setAccreditationstatus(String accreditationstatus) {
        this.accreditationstatus = accreditationstatus;
    }

    public String getCireportid() {
        return cireportid;
    }

    public void setCireportid(String cireportid) {
        this.cireportid = cireportid;
    }

    public String getEvalrecommendation() {
        return evalrecommendation;
    }

    public void setEvalrecommendation(String evalrecommendation) {
        this.evalrecommendation = evalrecommendation;
    }

    public String getEvaluatorremarks() {
        return evaluatorremarks;
    }

    public void setEvaluatorremarks(String evaluatorremarks) {
        this.evaluatorremarks = evaluatorremarks;
    }

    public String getApprovalstatus() {
        return approvalstatus;
    }

    public void setApprovalstatus(String approvalstatus) {
        this.approvalstatus = approvalstatus;
    }

}
