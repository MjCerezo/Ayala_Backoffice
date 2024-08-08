
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.LsttxfinId
 *  10/13/2020 10:21:35
 * 
 */
public class LsttxfinId
    implements Serializable
{

    private String loanno;
    private Integer txseqno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LsttxfinId)) {
            return false;
        }
        LsttxfinId other = ((LsttxfinId) o);
        if (this.loanno == null) {
            if (other.loanno!= null) {
                return false;
            }
        } else {
            if (!this.loanno.equals(other.loanno)) {
                return false;
            }
        }
        if (this.txseqno == null) {
            if (other.txseqno!= null) {
                return false;
            }
        } else {
            if (!this.txseqno.equals(other.txseqno)) {
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
        if (this.txseqno!= null) {
            rtn = (rtn + this.txseqno.hashCode());
        }
        return rtn;
    }

    public String getLoanno() {
        return loanno;
    }

    public void setLoanno(String loanno) {
        this.loanno = loanno;
    }

    public Integer getTxseqno() {
        return txseqno;
    }

    public void setTxseqno(Integer txseqno) {
        this.txseqno = txseqno;
    }

}
