
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbintratebyterm
 *  08/04/2024 12:54:43
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
