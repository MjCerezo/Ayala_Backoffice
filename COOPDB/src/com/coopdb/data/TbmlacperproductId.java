
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbmlacperproductId
 *  08/10/2024 21:24:56
 * 
 */
public class TbmlacperproductId
    implements Serializable
{

    private String productcode;
    private String conditioncode;
    private String particulars;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbmlacperproductId)) {
            return false;
        }
        TbmlacperproductId other = ((TbmlacperproductId) o);
        if (this.productcode == null) {
            if (other.productcode!= null) {
                return false;
            }
        } else {
            if (!this.productcode.equals(other.productcode)) {
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
        if (this.productcode!= null) {
            rtn = (rtn + this.productcode.hashCode());
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

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
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
