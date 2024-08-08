
package com.cifsdb.data;

import java.math.BigDecimal;


/**
 *  CIFSDB.Tbpayslipadjustment
 *  09/26/2023 10:13:05
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
