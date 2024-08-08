
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbproductperbosId
 *  08/04/2024 12:54:44
 * 
 */
public class TbproductperbosId
    implements Serializable
{

    private String boscode;
    private String productcode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbproductperbosId)) {
            return false;
        }
        TbproductperbosId other = ((TbproductperbosId) o);
        if (this.boscode == null) {
            if (other.boscode!= null) {
                return false;
            }
        } else {
            if (!this.boscode.equals(other.boscode)) {
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
        if (this.boscode!= null) {
            rtn = (rtn + this.boscode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.productcode!= null) {
            rtn = (rtn + this.productcode.hashCode());
        }
        return rtn;
    }

    public String getBoscode() {
        return boscode;
    }

    public void setBoscode(String boscode) {
        this.boscode = boscode;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

}
