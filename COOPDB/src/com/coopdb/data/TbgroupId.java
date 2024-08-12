
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbgroupId
 *  08/10/2024 21:24:57
 * 
 */
public class TbgroupId
    implements Serializable
{

    private String companycode;
    private String branchcode;
    private String groupcode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbgroupId)) {
            return false;
        }
        TbgroupId other = ((TbgroupId) o);
        if (this.companycode == null) {
            if (other.companycode!= null) {
                return false;
            }
        } else {
            if (!this.companycode.equals(other.companycode)) {
                return false;
            }
        }
        if (this.branchcode == null) {
            if (other.branchcode!= null) {
                return false;
            }
        } else {
            if (!this.branchcode.equals(other.branchcode)) {
                return false;
            }
        }
        if (this.groupcode == null) {
            if (other.groupcode!= null) {
                return false;
            }
        } else {
            if (!this.groupcode.equals(other.groupcode)) {
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
        if (this.branchcode!= null) {
            rtn = (rtn + this.branchcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.groupcode!= null) {
            rtn = (rtn + this.groupcode.hashCode());
        }
        return rtn;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

}
