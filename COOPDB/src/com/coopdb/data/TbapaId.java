
package com.coopdb.data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *  COOPDB.TbapaId
 *  11/12/2021 15:45:44
 * 
 */
public class TbapaId
    implements Serializable
{

    private BigDecimal rr4savingsdeposit;
    private BigDecimal rr4termdeposit;
    private BigDecimal rr4checkingdeposit;
    private BigDecimal transferpoolrate;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbapaId)) {
            return false;
        }
        TbapaId other = ((TbapaId) o);
        if (this.rr4savingsdeposit == null) {
            if (other.rr4savingsdeposit!= null) {
                return false;
            }
        } else {
            if (!this.rr4savingsdeposit.equals(other.rr4savingsdeposit)) {
                return false;
            }
        }
        if (this.rr4termdeposit == null) {
            if (other.rr4termdeposit!= null) {
                return false;
            }
        } else {
            if (!this.rr4termdeposit.equals(other.rr4termdeposit)) {
                return false;
            }
        }
        if (this.rr4checkingdeposit == null) {
            if (other.rr4checkingdeposit!= null) {
                return false;
            }
        } else {
            if (!this.rr4checkingdeposit.equals(other.rr4checkingdeposit)) {
                return false;
            }
        }
        if (this.transferpoolrate == null) {
            if (other.transferpoolrate!= null) {
                return false;
            }
        } else {
            if (!this.transferpoolrate.equals(other.transferpoolrate)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.rr4savingsdeposit!= null) {
            rtn = (rtn + this.rr4savingsdeposit.hashCode());
        }
        rtn = (rtn* 37);
        if (this.rr4termdeposit!= null) {
            rtn = (rtn + this.rr4termdeposit.hashCode());
        }
        rtn = (rtn* 37);
        if (this.rr4checkingdeposit!= null) {
            rtn = (rtn + this.rr4checkingdeposit.hashCode());
        }
        rtn = (rtn* 37);
        if (this.transferpoolrate!= null) {
            rtn = (rtn + this.transferpoolrate.hashCode());
        }
        return rtn;
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
