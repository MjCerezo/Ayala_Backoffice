
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.TbloanfeesperappId
 *  09/26/2023 10:13:06
 * 
 */
public class TbloanfeesperappId
    implements Serializable
{

    private String appno;
    private String loanfeecode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbloanfeesperappId)) {
            return false;
        }
        TbloanfeesperappId other = ((TbloanfeesperappId) o);
        if (this.appno == null) {
            if (other.appno!= null) {
                return false;
            }
        } else {
            if (!this.appno.equals(other.appno)) {
                return false;
            }
        }
        if (this.loanfeecode == null) {
            if (other.loanfeecode!= null) {
                return false;
            }
        } else {
            if (!this.loanfeecode.equals(other.loanfeecode)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.appno!= null) {
            rtn = (rtn + this.appno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.loanfeecode!= null) {
            rtn = (rtn + this.loanfeecode.hashCode());
        }
        return rtn;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getLoanfeecode() {
        return loanfeecode;
    }

    public void setLoanfeecode(String loanfeecode) {
        this.loanfeecode = loanfeecode;
    }

}
