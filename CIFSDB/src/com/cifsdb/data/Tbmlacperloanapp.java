
package com.cifsdb.data;

import java.math.BigDecimal;


/**
 *  CIFSDB.Tbmlacperloanapp
 *  09/26/2023 10:13:06
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
