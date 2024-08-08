
package com.cifsdb.data;

import java.math.BigDecimal;


/**
 *  CIFSDB.Tbloanbuyout
 *  09/26/2023 10:13:06
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
