
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbintratebyterm
 *  08/27/2024 14:22:56
 * 
 */
public class Tbintratebyterm {

    private TbintratebytermId id;
    private BigDecimal rate;

    public TbintratebytermId getId() {
        return id;
    }

    public void setId(TbintratebytermId id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

}
