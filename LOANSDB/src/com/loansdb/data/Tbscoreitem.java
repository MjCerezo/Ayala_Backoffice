
package com.loansdb.data;



/**
 *  LOANSDB.Tbscoreitem
 *  10/13/2020 10:19:39
 * 
 */
public class Tbscoreitem {

    private String itemid;
    private String subcriteriaid;
    private String itemname;
    private Integer score;

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getSubcriteriaid() {
        return subcriteriaid;
    }

    public void setSubcriteriaid(String subcriteriaid) {
        this.subcriteriaid = subcriteriaid;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
