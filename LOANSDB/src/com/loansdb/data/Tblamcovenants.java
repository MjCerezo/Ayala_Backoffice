
package com.loansdb.data;



/**
 *  LOANSDB.Tblamcovenants
 *  10/13/2020 10:21:35
 * 
 */
public class Tblamcovenants {

    private Integer id;
    private Integer evalreportid;
    private String appno;
    private String covenants;
    private String remarkscfcovenants;

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

    public String getCovenants() {
        return covenants;
    }

    public void setCovenants(String covenants) {
        this.covenants = covenants;
    }

    public String getRemarkscfcovenants() {
        return remarkscfcovenants;
    }

    public void setRemarkscfcovenants(String remarkscfcovenants) {
        this.remarkscfcovenants = remarkscfcovenants;
    }

}
