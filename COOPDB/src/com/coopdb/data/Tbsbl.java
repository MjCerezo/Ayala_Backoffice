
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbsbl
 *  08/10/2024 21:24:57
 * 
 */
public class Tbsbl {

    private Integer id;
    private BigDecimal currentratesbl;
    private Integer netunimpairedcapital;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getCurrentratesbl() {
        return currentratesbl;
    }

    public void setCurrentratesbl(BigDecimal currentratesbl) {
        this.currentratesbl = currentratesbl;
    }

    public Integer getNetunimpairedcapital() {
        return netunimpairedcapital;
    }

    public void setNetunimpairedcapital(Integer netunimpairedcapital) {
        this.netunimpairedcapital = netunimpairedcapital;
    }

}
