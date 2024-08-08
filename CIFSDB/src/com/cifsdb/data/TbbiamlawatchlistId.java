
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.TbbiamlawatchlistId
 *  09/26/2023 10:13:05
 * 
 */
public class TbbiamlawatchlistId
    implements Serializable
{

    private String bireportid;
    private String amlaid;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbbiamlawatchlistId)) {
            return false;
        }
        TbbiamlawatchlistId other = ((TbbiamlawatchlistId) o);
        if (this.bireportid == null) {
            if (other.bireportid!= null) {
                return false;
            }
        } else {
            if (!this.bireportid.equals(other.bireportid)) {
                return false;
            }
        }
        if (this.amlaid == null) {
            if (other.amlaid!= null) {
                return false;
            }
        } else {
            if (!this.amlaid.equals(other.amlaid)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.bireportid!= null) {
            rtn = (rtn + this.bireportid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.amlaid!= null) {
            rtn = (rtn + this.amlaid.hashCode());
        }
        return rtn;
    }

    public String getBireportid() {
        return bireportid;
    }

    public void setBireportid(String bireportid) {
        this.bireportid = bireportid;
    }

    public String getAmlaid() {
        return amlaid;
    }

    public void setAmlaid(String amlaid) {
        this.amlaid = amlaid;
    }

}
