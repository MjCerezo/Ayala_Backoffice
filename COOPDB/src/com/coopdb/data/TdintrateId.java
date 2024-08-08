
package com.coopdb.data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *  COOPDB.TdintrateId
 *  12/05/2022 03:13:09
 * 
 */
public class TdintrateId
    implements Serializable
{

    private String oldacctno;
    private BigDecimal intrate;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TdintrateId)) {
            return false;
        }
        TdintrateId other = ((TdintrateId) o);
        if (this.oldacctno == null) {
            if (other.oldacctno!= null) {
                return false;
            }
        } else {
            if (!this.oldacctno.equals(other.oldacctno)) {
                return false;
            }
        }
        if (this.intrate == null) {
            if (other.intrate!= null) {
                return false;
            }
        } else {
            if (!this.intrate.equals(other.intrate)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.oldacctno!= null) {
            rtn = (rtn + this.oldacctno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.intrate!= null) {
            rtn = (rtn + this.intrate.hashCode());
        }
        return rtn;
    }

    public String getOldacctno() {
        return oldacctno;
    }

    public void setOldacctno(String oldacctno) {
        this.oldacctno = oldacctno;
    }

    public BigDecimal getIntrate() {
        return intrate;
    }

    public void setIntrate(BigDecimal intrate) {
        this.intrate = intrate;
    }

}
