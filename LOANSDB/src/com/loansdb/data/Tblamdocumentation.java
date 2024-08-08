
package com.loansdb.data;



/**
 *  LOANSDB.Tblamdocumentation
 *  10/13/2020 10:21:35
 * 
 */
public class Tblamdocumentation {

    private Integer id;
    private Integer evalreportid;
    private String appno;
    private String remarks;
    private String remarksdocspercfapp;

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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRemarksdocspercfapp() {
        return remarksdocspercfapp;
    }

    public void setRemarksdocspercfapp(String remarksdocspercfapp) {
        this.remarksdocspercfapp = remarksdocspercfapp;
    }

}
