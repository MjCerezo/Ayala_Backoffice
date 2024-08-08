
package com.loansdb.data;

import java.math.BigDecimal;


/**
 *  LOANSDB.Tbintratebyterm
 *  10/13/2020 10:21:35
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
