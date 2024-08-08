
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.TbmembercompanyId
 *  09/26/2023 10:13:05
 * 
 */
public class TbmembercompanyId
    implements Serializable
{

    private String companycode;
    private String companyname;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbmembercompanyId)) {
            return false;
        }
        TbmembercompanyId other = ((TbmembercompanyId) o);
        if (this.companycode == null) {
            if (other.companycode!= null) {
                return false;
            }
        } else {
            if (!this.companycode.equals(other.companycode)) {
                return false;
            }
        }
        if (this.companyname == null) {
            if (other.companyname!= null) {
                return false;
            }
        } else {
            if (!this.companyname.equals(other.companyname)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.companycode!= null) {
            rtn = (rtn + this.companycode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.companyname!= null) {
            rtn = (rtn + this.companyname.hashCode());
        }
        return rtn;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

}
