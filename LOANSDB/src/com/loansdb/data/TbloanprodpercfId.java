
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbloanprodpercfId
 *  10/13/2020 10:21:35
 * 
 */
public class TbloanprodpercfId
    implements Serializable
{

    private String prodcode;
    private String facilitycode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbloanprodpercfId)) {
            return false;
        }
        TbloanprodpercfId other = ((TbloanprodpercfId) o);
        if (this.prodcode == null) {
            if (other.prodcode!= null) {
                return false;
            }
        } else {
            if (!this.prodcode.equals(other.prodcode)) {
                return false;
            }
        }
        if (this.facilitycode == null) {
            if (other.facilitycode!= null) {
                return false;
            }
        } else {
            if (!this.facilitycode.equals(other.facilitycode)) {
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
        if (this.facilitycode!= null) {
            rtn = (rtn + this.facilitycode.hashCode());
        }
        return rtn;
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode;
    }

    public String getFacilitycode() {
        return facilitycode;
    }

    public void setFacilitycode(String facilitycode) {
        this.facilitycode = facilitycode;
    }

}
