
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.TbcodetableId
 *  08/06/2024 19:26:36
 * 
 */
public class TbcodetableId
    implements Serializable
{

    private String codename;
    private String codevalue;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbcodetableId)) {
            return false;
        }
        TbcodetableId other = ((TbcodetableId) o);
        if (this.codename == null) {
            if (other.codename!= null) {
                return false;
            }
        } else {
            if (!this.codename.equals(other.codename)) {
                return false;
            }
        }
        if (this.codevalue == null) {
            if (other.codevalue!= null) {
                return false;
            }
        } else {
            if (!this.codevalue.equals(other.codevalue)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.codename!= null) {
            rtn = (rtn + this.codename.hashCode());
        }
        rtn = (rtn* 37);
        if (this.codevalue!= null) {
            rtn = (rtn + this.codevalue.hashCode());
        }
        return rtn;
    }

    public String getCodename() {
        return codename;
    }

    public void setCodename(String codename) {
        this.codename = codename;
    }

    public String getCodevalue() {
        return codevalue;
    }

    public void setCodevalue(String codevalue) {
        this.codevalue = codevalue;
    }

}
