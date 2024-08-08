
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbbiamlawatchlistId
 *  08/04/2024 12:54:44
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
