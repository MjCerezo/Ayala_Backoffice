
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbcicreditcheckId
 *  01/10/2018 10:44:28
 * 
 */
public class TbcicreditcheckId
    implements Serializable
{

    private Integer id;
    private Integer cireportid;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbcicreditcheckId)) {
            return false;
        }
        TbcicreditcheckId other = ((TbcicreditcheckId) o);
        if (this.id == null) {
            if (other.id!= null) {
                return false;
            }
        } else {
            if (!this.id.equals(other.id)) {
                return false;
            }
        }
        if (this.cireportid == null) {
            if (other.cireportid!= null) {
                return false;
            }
        } else {
            if (!this.cireportid.equals(other.cireportid)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.id!= null) {
            rtn = (rtn + this.id.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cireportid!= null) {
            rtn = (rtn + this.cireportid.hashCode());
        }
        return rtn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCireportid() {
        return cireportid;
    }

    public void setCireportid(Integer cireportid) {
        this.cireportid = cireportid;
    }

}
