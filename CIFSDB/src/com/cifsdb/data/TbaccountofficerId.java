
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.TbaccountofficerId
 *  09/26/2023 10:13:06
 * 
 */
public class TbaccountofficerId
    implements Serializable
{

    private String companycode;
    private String aocode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbaccountofficerId)) {
            return false;
        }
        TbaccountofficerId other = ((TbaccountofficerId) o);
        if (this.companycode == null) {
            if (other.companycode!= null) {
                return false;
            }
        } else {
            if (!this.companycode.equals(other.companycode)) {
                return false;
            }
        }
        if (this.aocode == null) {
            if (other.aocode!= null) {
                return false;
            }
        } else {
            if (!this.aocode.equals(other.aocode)) {
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
        if (this.aocode!= null) {
            rtn = (rtn + this.aocode.hashCode());
        }
        return rtn;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getAocode() {
        return aocode;
    }

    public void setAocode(String aocode) {
        this.aocode = aocode;
    }

}
