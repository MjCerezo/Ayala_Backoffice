
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.TbproductpercompanyId
 *  09/26/2023 10:13:05
 * 
 */
public class TbproductpercompanyId
    implements Serializable
{

    private String companycode;
    private String productcode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbproductpercompanyId)) {
            return false;
        }
        TbproductpercompanyId other = ((TbproductpercompanyId) o);
        if (this.companycode == null) {
            if (other.companycode!= null) {
                return false;
            }
        } else {
            if (!this.companycode.equals(other.companycode)) {
                return false;
            }
        }
        if (this.productcode == null) {
            if (other.productcode!= null) {
                return false;
            }
        } else {
            if (!this.productcode.equals(other.productcode)) {
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
        if (this.productcode!= null) {
            rtn = (rtn + this.productcode.hashCode());
        }
        return rtn;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

}
