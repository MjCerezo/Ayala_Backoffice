
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbpayslipadjustment
 *  08/27/2024 14:22:57
 * 
 */
public class Tbpayslipadjustment {

    private TbpayslipadjustmentId id;
    private String particulars;
    private BigDecimal amount;

    public TbpayslipadjustmentId getId() {
        return id;
    }

    public void setId(TbpayslipadjustmentId id) {
        this.id = id;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
