
package com.loansdb.data;

import java.io.Serializable;
import java.util.Date;


/**
 *  LOANSDB.LsttxhistId
 *  10/13/2020 10:21:35
 * 
 */
public class LsttxhistId
    implements Serializable
{

    private String loanno;
    private Date txdate;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LsttxhistId)) {
            return false;
        }
        LsttxhistId other = ((LsttxhistId) o);
        if (this.loanno == null) {
            if (other.loanno!= null) {
                return false;
            }
        } else {
            if (!this.loanno.equals(other.loanno)) {
                return false;
            }
        }
        if (this.txdate == null) {
            if (other.txdate!= null) {
                return false;
            }
        } else {
            if (!this.txdate.equals(other.txdate)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.loanno!= null) {
            rtn = (rtn + this.loanno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.txdate!= null) {
            rtn = (rtn + this.txdate.hashCode());
        }
        return rtn;
    }

    public String getLoanno() {
        return loanno;
    }

    public void setLoanno(String loanno) {
        this.loanno = loanno;
    }

    public Date getTxdate() {
        return txdate;
    }

    public void setTxdate(Date txdate) {
        this.txdate = txdate;
    }

}
