
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.CiftinId
 *  02/23/2023 13:04:33
 * 
 */
public class CiftinId
    implements Serializable
{

    private String originatingBranch;
    private String cifNo;
    private String tin;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CiftinId)) {
            return false;
        }
        CiftinId other = ((CiftinId) o);
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
        if (this.tin == null) {
            if (other.tin!= null) {
                return false;
            }
        } else {
            if (!this.tin.equals(other.tin)) {
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
        if (this.tin!= null) {
            rtn = (rtn + this.tin.hashCode());
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

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

}
