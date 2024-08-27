
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbloanoffsetId
 *  08/27/2024 14:22:57
 * 
 */
public class TbloanoffsetId
    implements Serializable
{

    private String appno;
    private String accountno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbloanoffsetId)) {
            return false;
        }
        TbloanoffsetId other = ((TbloanoffsetId) o);
        if (this.appno == null) {
            if (other.appno!= null) {
                return false;
            }
        } else {
            if (!this.appno.equals(other.appno)) {
                return false;
            }
        }
        if (this.accountno == null) {
            if (other.accountno!= null) {
                return false;
            }
        } else {
            if (!this.accountno.equals(other.accountno)) {
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
        if (this.accountno!= null) {
            rtn = (rtn + this.accountno.hashCode());
        }
        return rtn;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

}
