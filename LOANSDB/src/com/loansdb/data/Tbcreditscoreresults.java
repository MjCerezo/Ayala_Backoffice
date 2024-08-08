
package com.loansdb.data;

import java.math.BigDecimal;


/**
 *  LOANSDB.Tbcreditscoreresults
 *  10/13/2020 10:21:35
 * 
 */
public class Tbcreditscoreresults {

    private String appno;
    private String scoreresults;
    private BigDecimal scorevalue;

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getScoreresults() {
        return scoreresults;
    }

    public void setScoreresults(String scoreresults) {
        this.scoreresults = scoreresults;
    }

    public BigDecimal getScorevalue() {
        return scorevalue;
    }

    public void setScorevalue(BigDecimal scorevalue) {
        this.scorevalue = scorevalue;
    }

}
