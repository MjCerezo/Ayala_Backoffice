
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbbiactivityId
 *  08/04/2024 12:54:42
 * 
 */
public class TbbiactivityId
    implements Serializable
{

    private String bireportid;
    private String biactivity;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbbiactivityId)) {
            return false;
        }
        TbbiactivityId other = ((TbbiactivityId) o);
        if (this.bireportid == null) {
            if (other.bireportid!= null) {
                return false;
            }
        } else {
            if (!this.bireportid.equals(other.bireportid)) {
                return false;
            }
        }
        if (this.biactivity == null) {
            if (other.biactivity!= null) {
                return false;
            }
        } else {
            if (!this.biactivity.equals(other.biactivity)) {
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
        if (this.biactivity!= null) {
            rtn = (rtn + this.biactivity.hashCode());
        }
        return rtn;
    }

    public String getBireportid() {
        return bireportid;
    }

    public void setBireportid(String bireportid) {
        this.bireportid = bireportid;
    }

    public String getBiactivity() {
        return biactivity;
    }

    public void setBiactivity(String biactivity) {
        this.biactivity = biactivity;
    }

}
