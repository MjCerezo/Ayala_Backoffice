
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.TbbranchId
 *  08/06/2024 19:26:36
 * 
 */
public class TbbranchId
    implements Serializable
{

    private String branchcode;
    private String branchname;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbbranchId)) {
            return false;
        }
        TbbranchId other = ((TbbranchId) o);
        if (this.branchcode == null) {
            if (other.branchcode!= null) {
                return false;
            }
        } else {
            if (!this.branchcode.equals(other.branchcode)) {
                return false;
            }
        }
        if (this.branchname == null) {
            if (other.branchname!= null) {
                return false;
            }
        } else {
            if (!this.branchname.equals(other.branchname)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.branchcode!= null) {
            rtn = (rtn + this.branchcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.branchname!= null) {
            rtn = (rtn + this.branchname.hashCode());
        }
        return rtn;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

}
