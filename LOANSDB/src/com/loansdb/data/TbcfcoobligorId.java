
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbcfcoobligorId
 *  08/23/2018 17:32:24
 * 
 */
public class TbcfcoobligorId
    implements Serializable
{

    private String cfrefno;
    private String cfcifno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbcfcoobligorId)) {
            return false;
        }
        TbcfcoobligorId other = ((TbcfcoobligorId) o);
        if (this.cfrefno == null) {
            if (other.cfrefno!= null) {
                return false;
            }
        } else {
            if (!this.cfrefno.equals(other.cfrefno)) {
                return false;
            }
        }
        if (this.cfcifno == null) {
            if (other.cfcifno!= null) {
                return false;
            }
        } else {
            if (!this.cfcifno.equals(other.cfcifno)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.cfrefno!= null) {
            rtn = (rtn + this.cfrefno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cfcifno!= null) {
            rtn = (rtn + this.cfcifno.hashCode());
        }
        return rtn;
    }

    public String getCfrefno() {
        return cfrefno;
    }

    public void setCfrefno(String cfrefno) {
        this.cfrefno = cfrefno;
    }

    public String getCfcifno() {
        return cfcifno;
    }

    public void setCfcifno(String cfcifno) {
        this.cfcifno = cfcifno;
    }

}
