
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbcooperativeId
 *  08/27/2024 14:22:56
 * 
 */
public class TbcooperativeId
    implements Serializable
{

    private String coopcode;
    private String coopname;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbcooperativeId)) {
            return false;
        }
        TbcooperativeId other = ((TbcooperativeId) o);
        if (this.coopcode == null) {
            if (other.coopcode!= null) {
                return false;
            }
        } else {
            if (!this.coopcode.equals(other.coopcode)) {
                return false;
            }
        }
        if (this.coopname == null) {
            if (other.coopname!= null) {
                return false;
            }
        } else {
            if (!this.coopname.equals(other.coopname)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.coopcode!= null) {
            rtn = (rtn + this.coopcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.coopname!= null) {
            rtn = (rtn + this.coopname.hashCode());
        }
        return rtn;
    }

    public String getCoopcode() {
        return coopcode;
    }

    public void setCoopcode(String coopcode) {
        this.coopcode = coopcode;
    }

    public String getCoopname() {
        return coopname;
    }

    public void setCoopname(String coopname) {
        this.coopname = coopname;
    }

}
