
package com.cifsdb.data;

import java.math.BigDecimal;


/**
 *  CIFSDB.Tbsbl
 *  09/26/2023 10:13:06
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
