
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbdepedcodesId
 *  06/21/2019 09:57:53
 * 
 */
public class TbdepedcodesId
    implements Serializable
{

    private String regioncode;
    private String divisioncode;
    private String stationcode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbdepedcodesId)) {
            return false;
        }
        TbdepedcodesId other = ((TbdepedcodesId) o);
        if (this.regioncode == null) {
            if (other.regioncode!= null) {
                return false;
            }
        } else {
            if (!this.regioncode.equals(other.regioncode)) {
                return false;
            }
        }
        if (this.divisioncode == null) {
            if (other.divisioncode!= null) {
                return false;
            }
        } else {
            if (!this.divisioncode.equals(other.divisioncode)) {
                return false;
            }
        }
        if (this.stationcode == null) {
            if (other.stationcode!= null) {
                return false;
            }
        } else {
            if (!this.stationcode.equals(other.stationcode)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.regioncode!= null) {
            rtn = (rtn + this.regioncode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.divisioncode!= null) {
            rtn = (rtn + this.divisioncode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.stationcode!= null) {
            rtn = (rtn + this.stationcode.hashCode());
        }
        return rtn;
    }

    public String getRegioncode() {
        return regioncode;
    }

    public void setRegioncode(String regioncode) {
        this.regioncode = regioncode;
    }

    public String getDivisioncode() {
        return divisioncode;
    }

    public void setDivisioncode(String divisioncode) {
        this.divisioncode = divisioncode;
    }

    public String getStationcode() {
        return stationcode;
    }

    public void setStationcode(String stationcode) {
        this.stationcode = stationcode;
    }

}
