
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbpayschedId
 *  10/13/2020 10:21:35
 * 
 */
public class TbpayschedId
    implements Serializable
{

    private Integer amortid;
    private String applno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbpayschedId)) {
            return false;
        }
        TbpayschedId other = ((TbpayschedId) o);
        if (this.amortid == null) {
            if (other.amortid!= null) {
                return false;
            }
        } else {
            if (!this.amortid.equals(other.amortid)) {
                return false;
            }
        }
        if (this.applno == null) {
            if (other.applno!= null) {
                return false;
            }
        } else {
            if (!this.applno.equals(other.applno)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.amortid!= null) {
            rtn = (rtn + this.amortid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.applno!= null) {
            rtn = (rtn + this.applno.hashCode());
        }
        return rtn;
    }

    public Integer getAmortid() {
        return amortid;
    }

    public void setAmortid(Integer amortid) {
        this.amortid = amortid;
    }

    public String getApplno() {
        return applno;
    }

    public void setApplno(String applno) {
        this.applno = applno;
    }

}
