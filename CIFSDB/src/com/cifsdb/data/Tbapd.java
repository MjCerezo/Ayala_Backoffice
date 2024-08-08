
package com.cifsdb.data;

import java.math.BigDecimal;


/**
 *  CIFSDB.Tbapd
 *  09/26/2023 10:13:05
 * 
 */
public class Tbapd {

    private TbapdId id;
    private BigDecimal gmp;
    private BigDecimal nthp;
    private BigDecimal gaa;
    private BigDecimal totaladjustments;
    private BigDecimal netnthpgaa;
    private BigDecimal avp;

    public TbapdId getId() {
        return id;
    }

    public void setId(TbapdId id) {
        this.id = id;
    }

    public BigDecimal getGmp() {
        return gmp;
    }

    public void setGmp(BigDecimal gmp) {
        this.gmp = gmp;
    }

    public BigDecimal getNthp() {
        return nthp;
    }

    public void setNthp(BigDecimal nthp) {
        this.nthp = nthp;
    }

    public BigDecimal getGaa() {
        return gaa;
    }

    public void setGaa(BigDecimal gaa) {
        this.gaa = gaa;
    }

    public BigDecimal getTotaladjustments() {
        return totaladjustments;
    }

    public void setTotaladjustments(BigDecimal totaladjustments) {
        this.totaladjustments = totaladjustments;
    }

    public BigDecimal getNetnthpgaa() {
        return netnthpgaa;
    }

    public void setNetnthpgaa(BigDecimal netnthpgaa) {
        this.netnthpgaa = netnthpgaa;
    }

    public BigDecimal getAvp() {
        return avp;
    }

    public void setAvp(BigDecimal avp) {
        this.avp = avp;
    }

}
