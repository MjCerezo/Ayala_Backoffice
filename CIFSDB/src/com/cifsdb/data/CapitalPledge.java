
package com.cifsdb.data;

import java.math.BigDecimal;


/**
 *  CIFSDB.CapitalPledge
 *  08/27/2024 14:22:04
 * 
 */
public class CapitalPledge {

    private String cifno;
    private Boolean isFixed;
    private BigDecimal fixedAmount;
    private Boolean isEntered;
    private BigDecimal enteredAmount;

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public Boolean getIsFixed() {
        return isFixed;
    }

    public void setIsFixed(Boolean isFixed) {
        this.isFixed = isFixed;
    }

    public BigDecimal getFixedAmount() {
        return fixedAmount;
    }

    public void setFixedAmount(BigDecimal fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    public Boolean getIsEntered() {
        return isEntered;
    }

    public void setIsEntered(Boolean isEntered) {
        this.isEntered = isEntered;
    }

    public BigDecimal getEnteredAmount() {
        return enteredAmount;
    }

    public void setEnteredAmount(BigDecimal enteredAmount) {
        this.enteredAmount = enteredAmount;
    }

}
