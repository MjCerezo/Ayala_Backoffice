
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.TbmlacperloanappId
 *  09/26/2023 10:13:06
 * 
 */
public class TbmlacperloanappId
    implements Serializable
{

    private String appno;
    private String conditioncode;
    private String particulars;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbmlacperloanappId)) {
            return false;
        }
        TbmlacperloanappId other = ((TbmlacperloanappId) o);
        if (this.appno == null) {
            if (other.appno!= null) {
                return false;
            }
        } else {
            if (!this.appno.equals(other.appno)) {
                return false;
            }
        }
        if (this.conditioncode == null) {
            if (other.conditioncode!= null) {
                return false;
            }
        } else {
            if (!this.conditioncode.equals(other.conditioncode)) {
                return false;
            }
        }
        if (this.particulars == null) {
            if (other.particulars!= null) {
                return false;
            }
        } else {
            if (!this.particulars.equals(other.particulars)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.appno!= null) {
            rtn = (rtn + this.appno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.conditioncode!= null) {
            rtn = (rtn + this.conditioncode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.particulars!= null) {
            rtn = (rtn + this.particulars.hashCode());
        }
        return rtn;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getConditioncode() {
        return conditioncode;
    }

    public void setConditioncode(String conditioncode) {
        this.conditioncode = conditioncode;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

}
