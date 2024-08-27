
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.TbbankaccountsId
 *  08/27/2024 14:22:04
 * 
 */
public class TbbankaccountsId
    implements Serializable
{

    private String cifno;
    private String accountrefno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbbankaccountsId)) {
            return false;
        }
        TbbankaccountsId other = ((TbbankaccountsId) o);
        if (this.cifno == null) {
            if (other.cifno!= null) {
                return false;
            }
        } else {
            if (!this.cifno.equals(other.cifno)) {
                return false;
            }
        }
        if (this.accountrefno == null) {
            if (other.accountrefno!= null) {
                return false;
            }
        } else {
            if (!this.accountrefno.equals(other.accountrefno)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.cifno!= null) {
            rtn = (rtn + this.cifno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.accountrefno!= null) {
            rtn = (rtn + this.accountrefno.hashCode());
        }
        return rtn;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getAccountrefno() {
        return accountrefno;
    }

    public void setAccountrefno(String accountrefno) {
        this.accountrefno = accountrefno;
    }

}
