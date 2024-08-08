
package com.loansdb.data;

import java.math.BigDecimal;


/**
 *  LOANSDB.Tbcreditscoredetailedresults
 *  10/13/2020 10:21:35
 * 
 */
public class Tbcreditscoredetailedresults {

    private Integer id;
    private String appno;
    private String attributes;
    private String value;
    private BigDecimal weights;
    private BigDecimal score;
    private BigDecimal weightedscore;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BigDecimal getWeights() {
        return weights;
    }

    public void setWeights(BigDecimal weights) {
        this.weights = weights;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getWeightedscore() {
        return weightedscore;
    }

    public void setWeightedscore(BigDecimal weightedscore) {
        this.weightedscore = weightedscore;
    }

}
