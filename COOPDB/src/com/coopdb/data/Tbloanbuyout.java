
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbloanbuyout
 *  08/10/2024 21:24:57
 * 
 */
public class Tbloanbuyout {

    private Integer id;
    private String appno;
    private String otherlendinginst;
    private BigDecimal amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getOtherlendinginst() {
        return otherlendinginst;
    }

    public void setOtherlendinginst(String otherlendinginst) {
        this.otherlendinginst = otherlendinginst;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
