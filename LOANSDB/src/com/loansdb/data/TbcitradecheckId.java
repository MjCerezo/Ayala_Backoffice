
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbcitradecheckId
 *  01/10/2018 10:44:28
 * 
 */
public class TbcitradecheckId
    implements Serializable
{

    private Integer id;
    private String cireportid;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbcitradecheckId)) {
            return false;
        }
        TbcitradecheckId other = ((TbcitradecheckId) o);
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

    public String getCireportid() {
        return cireportid;
    }

    public void setCireportid(String cireportid) {
        this.cireportid = cireportid;
    }

}
