
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbmlacperloanapp
 *  08/04/2024 12:54:40
 * 
 */
public class Tbmlacperloanapp {

    private TbmlacperloanappId id;
    private BigDecimal amount;
    private Integer sequence;

    public TbmlacperloanappId getId() {
        return id;
    }

    public void setId(TbmlacperloanappId id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

}
