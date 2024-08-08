
package com.loansdb.data;



/**
 *  LOANSDB.Tbscoreresults
 *  10/13/2020 10:21:35
 * 
 */
public class Tbscoreresults {

    private TbscoreresultsId id;
    private String itemid;
    private Integer score;

    public TbscoreresultsId getId() {
        return id;
    }

    public void setId(TbscoreresultsId id) {
        this.id = id;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
