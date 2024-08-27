
package com.coopdb.data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *  COOPDB.PenaltyId
 *  08/27/2024 14:22:58
 * 
 */
public class PenaltyId
    implements Serializable
{

    private String acctno;
    private BigDecimal penalty;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof PenaltyId)) {
            return false;
        }
        PenaltyId other = ((PenaltyId) o);
        if (this.acctno == null) {
            if (other.acctno!= null) {
                return false;
            }
        } else {
            if (!this.acctno.equals(other.acctno)) {
                return false;
            }
        }
        if (this.penalty == null) {
            if (other.penalty!= null) {
                return false;
            }
        } else {
            if (!this.penalty.equals(other.penalty)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.acctno!= null) {
            rtn = (rtn + this.acctno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.penalty!= null) {
            rtn = (rtn + this.penalty.hashCode());
        }
        return rtn;
    }

    public String getAcctno() {
        return acctno;
    }

    public void setAcctno(String acctno) {
        this.acctno = acctno;
    }

    public BigDecimal getPenalty() {
        return penalty;
    }

    public void setPenalty(BigDecimal penalty) {
        this.penalty = penalty;
    }

}
