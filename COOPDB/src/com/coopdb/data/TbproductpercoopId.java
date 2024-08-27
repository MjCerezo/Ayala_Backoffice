
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbproductpercoopId
 *  08/27/2024 14:22:56
 * 
 */
public class TbproductpercoopId
    implements Serializable
{

    private String coopcode;
    private String productcode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbproductpercoopId)) {
            return false;
        }
        TbproductpercoopId other = ((TbproductpercoopId) o);
        if (this.coopcode == null) {
            if (other.coopcode!= null) {
                return false;
            }
        } else {
            if (!this.coopcode.equals(other.coopcode)) {
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
        if (this.coopcode!= null) {
            rtn = (rtn + this.coopcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.productcode!= null) {
            rtn = (rtn + this.productcode.hashCode());
        }
        return rtn;
    }

    public String getCoopcode() {
        return coopcode;
    }

    public void setCoopcode(String coopcode) {
        this.coopcode = coopcode;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

}
