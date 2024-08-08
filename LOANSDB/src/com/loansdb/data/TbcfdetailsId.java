
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbcfdetailsId
 *  10/13/2020 10:21:35
 * 
 */
public class TbcfdetailsId
    implements Serializable
{

    private String cfappno;
    private String cfrefno;
    private Integer cflevel;
    private String cfseqno;
    private String cfsubseqno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbcfdetailsId)) {
            return false;
        }
        TbcfdetailsId other = ((TbcfdetailsId) o);
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
        if (this.cflevel == null) {
            if (other.cflevel!= null) {
                return false;
            }
        } else {
            if (!this.cflevel.equals(other.cflevel)) {
                return false;
            }
        }
        if (this.cfseqno == null) {
            if (other.cfseqno!= null) {
                return false;
            }
        } else {
            if (!this.cfseqno.equals(other.cfseqno)) {
                return false;
            }
        }
        if (this.cfsubseqno == null) {
            if (other.cfsubseqno!= null) {
                return false;
            }
        } else {
            if (!this.cfsubseqno.equals(other.cfsubseqno)) {
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
        rtn = (rtn* 37);
        if (this.cflevel!= null) {
            rtn = (rtn + this.cflevel.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cfseqno!= null) {
            rtn = (rtn + this.cfseqno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cfsubseqno!= null) {
            rtn = (rtn + this.cfsubseqno.hashCode());
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

    public Integer getCflevel() {
        return cflevel;
    }

    public void setCflevel(Integer cflevel) {
        this.cflevel = cflevel;
    }

    public String getCfseqno() {
        return cfseqno;
    }

    public void setCfseqno(String cfseqno) {
        this.cfseqno = cfseqno;
    }

    public String getCfsubseqno() {
        return cfsubseqno;
    }

    public void setCfsubseqno(String cfsubseqno) {
        this.cfsubseqno = cfsubseqno;
    }

}
