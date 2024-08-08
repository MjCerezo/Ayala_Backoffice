
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbbiblacklistId
 *  08/04/2024 12:54:43
 * 
 */
public class TbbiblacklistId
    implements Serializable
{

    private String bireportid;
    private String blacklistid;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbbiblacklistId)) {
            return false;
        }
        TbbiblacklistId other = ((TbbiblacklistId) o);
        if (this.bireportid == null) {
            if (other.bireportid!= null) {
                return false;
            }
        } else {
            if (!this.bireportid.equals(other.bireportid)) {
                return false;
            }
        }
        if (this.blacklistid == null) {
            if (other.blacklistid!= null) {
                return false;
            }
        } else {
            if (!this.blacklistid.equals(other.blacklistid)) {
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
        if (this.blacklistid!= null) {
            rtn = (rtn + this.blacklistid.hashCode());
        }
        return rtn;
    }

    public String getBireportid() {
        return bireportid;
    }

    public void setBireportid(String bireportid) {
        this.bireportid = bireportid;
    }

    public String getBlacklistid() {
        return blacklistid;
    }

    public void setBlacklistid(String blacklistid) {
        this.blacklistid = blacklistid;
    }

}
