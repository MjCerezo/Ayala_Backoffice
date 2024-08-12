
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbeventsId
 *  08/10/2024 21:24:57
 * 
 */
public class TbeventsId
    implements Serializable
{

    private Integer eventcode;
    private String coopcode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbeventsId)) {
            return false;
        }
        TbeventsId other = ((TbeventsId) o);
        if (this.eventcode == null) {
            if (other.eventcode!= null) {
                return false;
            }
        } else {
            if (!this.eventcode.equals(other.eventcode)) {
                return false;
            }
        }
        if (this.coopcode == null) {
            if (other.coopcode!= null) {
                return false;
            }
        } else {
            if (!this.coopcode.equals(other.coopcode)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.eventcode!= null) {
            rtn = (rtn + this.eventcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.coopcode!= null) {
            rtn = (rtn + this.coopcode.hashCode());
        }
        return rtn;
    }

    public Integer getEventcode() {
        return eventcode;
    }

    public void setEventcode(Integer eventcode) {
        this.eventcode = eventcode;
    }

    public String getCoopcode() {
        return coopcode;
    }

    public void setCoopcode(String coopcode) {
        this.coopcode = coopcode;
    }

}
