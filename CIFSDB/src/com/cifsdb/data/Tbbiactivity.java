
package com.cifsdb.data;



/**
 *  CIFSDB.Tbbiactivity
 *  09/26/2023 10:13:05
 * 
 */
public class Tbbiactivity {

    private TbbiactivityId id;
    private String cifno;
    private String overallresults;
    private String overallremarks;

    public TbbiactivityId getId() {
        return id;
    }

    public void setId(TbbiactivityId id) {
        this.id = id;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getOverallresults() {
        return overallresults;
    }

    public void setOverallresults(String overallresults) {
        this.overallresults = overallresults;
    }

    public String getOverallremarks() {
        return overallremarks;
    }

    public void setOverallremarks(String overallremarks) {
        this.overallremarks = overallremarks;
    }

}
