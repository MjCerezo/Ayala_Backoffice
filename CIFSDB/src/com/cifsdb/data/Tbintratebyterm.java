
package com.cifsdb.data;

import java.math.BigDecimal;


/**
 *  CIFSDB.Tbintratebyterm
 *  09/26/2023 10:13:06
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
