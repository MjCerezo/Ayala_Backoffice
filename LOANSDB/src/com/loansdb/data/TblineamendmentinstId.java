
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TblineamendmentinstId
 *  10/13/2020 10:21:35
 * 
 */
public class TblineamendmentinstId
    implements Serializable
{

    private String cfappno;
    private String cfrefno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TblineamendmentinstId)) {
            return false;
        }
        TblineamendmentinstId other = ((TblineamendmentinstId) o);
        if (this.cfappno == null) {
            if (other.cfappno!= null) {
                return false;
            }
        } else {
            if (!this.cfappno.equals(other.cfappno)) {
                return false;
            }
        }
        if (this.cfrefno == null) {
            if (other.cfrefno!= null) {
                return false;
            }
        } else {
            if (!this.cfrefno.equals(other.cfrefno)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.cfappno!= null) {
            rtn = (rtn + this.cfappno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cfrefno!= null) {
            rtn = (rtn + this.cfrefno.hashCode());
        }
        return rtn;
    }

    public String getCfappno() {
        return cfappno;
    }

    public void setCfappno(String cfappno) {
        this.cfappno = cfappno;
    }

    public String getCfrefno() {
        return cfrefno;
    }

    public void setCfrefno(String cfrefno) {
        this.cfrefno = cfrefno;
    }

}
