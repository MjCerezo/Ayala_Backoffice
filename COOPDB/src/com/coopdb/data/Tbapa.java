
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbapa
 *  08/04/2024 12:54:43
 * 
 */
public class Tbapa {

    private Integer id;
    private BigDecimal rr4savingsdeposit;
    private BigDecimal rr4termdeposit;
    private BigDecimal rr4checkingdeposit;
    private BigDecimal transferpoolrate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getRr4savingsdeposit() {
        return rr4savingsdeposit;
    }

    public void setRr4savingsdeposit(BigDecimal rr4savingsdeposit) {
        this.rr4savingsdeposit = rr4savingsdeposit;
    }

    public BigDecimal getRr4termdeposit() {
        return rr4termdeposit;
    }

    public void setRr4termdeposit(BigDecimal rr4termdeposit) {
        this.rr4termdeposit = rr4termdeposit;
    }

    public BigDecimal getRr4checkingdeposit() {
        return rr4checkingdeposit;
    }

    public void setRr4checkingdeposit(BigDecimal rr4checkingdeposit) {
        this.rr4checkingdeposit = rr4checkingdeposit;
    }

    public BigDecimal getTransferpoolrate() {
        return transferpoolrate;
    }

    public void setTransferpoolrate(BigDecimal transferpoolrate) {
        this.transferpoolrate = transferpoolrate;
    }

}
