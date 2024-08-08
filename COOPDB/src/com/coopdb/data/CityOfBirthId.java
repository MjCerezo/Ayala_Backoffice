
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.CityOfBirthId
 *  02/23/2023 13:04:33
 * 
 */
public class CityOfBirthId
    implements Serializable
{

    private String originatingBranch;
    private String cifNo;
    private String presentAddressCountry;
    private String presentAddressProvince;
    private String presentAddressCity;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CityOfBirthId)) {
            return false;
        }
        CityOfBirthId other = ((CityOfBirthId) o);
        if (this.originatingBranch == null) {
            if (other.originatingBranch!= null) {
                return false;
            }
        } else {
            if (!this.originatingBranch.equals(other.originatingBranch)) {
                return false;
            }
        }
        if (this.cifNo == null) {
            if (other.cifNo!= null) {
                return false;
            }
        } else {
            if (!this.cifNo.equals(other.cifNo)) {
                return false;
            }
        }
        if (this.presentAddressCountry == null) {
            if (other.presentAddressCountry!= null) {
                return false;
            }
        } else {
            if (!this.presentAddressCountry.equals(other.presentAddressCountry)) {
                return false;
            }
        }
        if (this.presentAddressProvince == null) {
            if (other.presentAddressProvince!= null) {
                return false;
            }
        } else {
            if (!this.presentAddressProvince.equals(other.presentAddressProvince)) {
                return false;
            }
        }
        if (this.presentAddressCity == null) {
            if (other.presentAddressCity!= null) {
                return false;
            }
        } else {
            if (!this.presentAddressCity.equals(other.presentAddressCity)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.originatingBranch!= null) {
            rtn = (rtn + this.originatingBranch.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cifNo!= null) {
            rtn = (rtn + this.cifNo.hashCode());
        }
        rtn = (rtn* 37);
        if (this.presentAddressCountry!= null) {
            rtn = (rtn + this.presentAddressCountry.hashCode());
        }
        rtn = (rtn* 37);
        if (this.presentAddressProvince!= null) {
            rtn = (rtn + this.presentAddressProvince.hashCode());
        }
        rtn = (rtn* 37);
        if (this.presentAddressCity!= null) {
            rtn = (rtn + this.presentAddressCity.hashCode());
        }
        return rtn;
    }

    public String getOriginatingBranch() {
        return originatingBranch;
    }

    public void setOriginatingBranch(String originatingBranch) {
        this.originatingBranch = originatingBranch;
    }

    public String getCifNo() {
        return cifNo;
    }

    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    public String getPresentAddressCountry() {
        return presentAddressCountry;
    }

    public void setPresentAddressCountry(String presentAddressCountry) {
        this.presentAddressCountry = presentAddressCountry;
    }

    public String getPresentAddressProvince() {
        return presentAddressProvince;
    }

    public void setPresentAddressProvince(String presentAddressProvince) {
        this.presentAddressProvince = presentAddressProvince;
    }

    public String getPresentAddressCity() {
        return presentAddressCity;
    }

    public void setPresentAddressCity(String presentAddressCity) {
        this.presentAddressCity = presentAddressCity;
    }

}
