
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.TbmembershiptypeperbosId
 *  09/26/2023 10:13:05
 * 
 */
public class TbmembershiptypeperbosId
    implements Serializable
{

    private String boscode;
    private String membertypecode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbmembershiptypeperbosId)) {
            return false;
        }
        TbmembershiptypeperbosId other = ((TbmembershiptypeperbosId) o);
        if (this.boscode == null) {
            if (other.boscode!= null) {
                return false;
            }
        } else {
            if (!this.boscode.equals(other.boscode)) {
                return false;
            }
        }
        if (this.membertypecode == null) {
            if (other.membertypecode!= null) {
                return false;
            }
        } else {
            if (!this.membertypecode.equals(other.membertypecode)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.boscode!= null) {
            rtn = (rtn + this.boscode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.membertypecode!= null) {
            rtn = (rtn + this.membertypecode.hashCode());
        }
        return rtn;
    }

    public String getBoscode() {
        return boscode;
    }

    public void setBoscode(String boscode) {
        this.boscode = boscode;
    }

    public String getMembertypecode() {
        return membertypecode;
    }

    public void setMembertypecode(String membertypecode) {
        this.membertypecode = membertypecode;
    }

}
