
package com.coopdb.data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *  COOPDB.TbsblId
 *  11/12/2021 15:45:44
 * 
 */
public class TbsblId
    implements Serializable
{

    private BigDecimal currentratesbl;
    private Integer netunimpairedcapital;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbsblId)) {
            return false;
        }
        TbsblId other = ((TbsblId) o);
        if (this.currentratesbl == null) {
            if (other.currentratesbl!= null) {
                return false;
            }
        } else {
            if (!this.currentratesbl.equals(other.currentratesbl)) {
                return false;
            }
        }
        if (this.netunimpairedcapital == null) {
            if (other.netunimpairedcapital!= null) {
                return false;
            }
        } else {
            if (!this.netunimpairedcapital.equals(other.netunimpairedcapital)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.currentratesbl!= null) {
            rtn = (rtn + this.currentratesbl.hashCode());
        }
        rtn = (rtn* 37);
        if (this.netunimpairedcapital!= null) {
            rtn = (rtn + this.netunimpairedcapital.hashCode());
        }
        return rtn;
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
