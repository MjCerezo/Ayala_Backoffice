
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbcollateralperprodId
 *  08/27/2024 14:22:57
 * 
 */
public class TbcollateralperprodId
    implements Serializable
{

    private String prodcode;
    private String collateraltype;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbcollateralperprodId)) {
            return false;
        }
        TbcollateralperprodId other = ((TbcollateralperprodId) o);
        if (this.prodcode == null) {
            if (other.prodcode!= null) {
                return false;
            }
        } else {
            if (!this.prodcode.equals(other.prodcode)) {
                return false;
            }
        }
        if (this.collateraltype == null) {
            if (other.collateraltype!= null) {
                return false;
            }
        } else {
            if (!this.collateraltype.equals(other.collateraltype)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.prodcode!= null) {
            rtn = (rtn + this.prodcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.collateraltype!= null) {
            rtn = (rtn + this.collateraltype.hashCode());
        }
        return rtn;
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode;
    }

    public String getCollateraltype() {
        return collateraltype;
    }

    public void setCollateraltype(String collateraltype) {
        this.collateraltype = collateraltype;
    }

}
