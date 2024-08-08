
package com.loansdb.data;

import java.io.Serializable;
import java.util.Date;


/**
 *  LOANSDB.LstaccountinfoId
 *  05/18/2018 11:10:13
 * 
 */
public class LstaccountinfoId
    implements Serializable
{

    private String applno;
    private String clientid;
    private Date txdate;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof LstaccountinfoId)) {
            return false;
        }
        LstaccountinfoId other = ((LstaccountinfoId) o);
        if (this.applno == null) {
            if (other.applno!= null) {
                return false;
            }
        } else {
            if (!this.applno.equals(other.applno)) {
                return false;
            }
        }
        if (this.clientid == null) {
            if (other.clientid!= null) {
                return false;
            }
        } else {
            if (!this.clientid.equals(other.clientid)) {
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
        if (this.applno!= null) {
            rtn = (rtn + this.applno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.clientid!= null) {
            rtn = (rtn + this.clientid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.txdate!= null) {
            rtn = (rtn + this.txdate.hashCode());
        }
        return rtn;
    }

    public String getApplno() {
        return applno;
    }

    public void setApplno(String applno) {
        this.applno = applno;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public Date getTxdate() {
        return txdate;
    }

    public void setTxdate(Date txdate) {
        this.txdate = txdate;
    }

}
