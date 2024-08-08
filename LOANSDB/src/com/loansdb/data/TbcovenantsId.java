
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbcovenantsId
 *  10/13/2020 10:21:35
 * 
 */
public class TbcovenantsId
    implements Serializable
{

    private Integer id;
    private String facilitycode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbcovenantsId)) {
            return false;
        }
        TbcovenantsId other = ((TbcovenantsId) o);
        if (this.id == null) {
            if (other.id!= null) {
                return false;
            }
        } else {
            if (!this.id.equals(other.id)) {
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
        if (this.id!= null) {
            rtn = (rtn + this.id.hashCode());
        }
        rtn = (rtn* 37);
        if (this.facilitycode!= null) {
            rtn = (rtn + this.facilitycode.hashCode());
        }
        return rtn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFacilitycode() {
        return facilitycode;
    }

    public void setFacilitycode(String facilitycode) {
        this.facilitycode = facilitycode;
    }

}
