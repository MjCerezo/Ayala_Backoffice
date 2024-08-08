
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.CifmembershiptypeId
 *  02/23/2023 13:04:33
 * 
 */
public class CifmembershiptypeId
    implements Serializable
{

    private String originatingBranch;
    private String cifNo;
    private String membershipType;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CifmembershiptypeId)) {
            return false;
        }
        CifmembershiptypeId other = ((CifmembershiptypeId) o);
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
        if (this.membershipType == null) {
            if (other.membershipType!= null) {
                return false;
            }
        } else {
            if (!this.membershipType.equals(other.membershipType)) {
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
        if (this.membershipType!= null) {
            rtn = (rtn + this.membershipType.hashCode());
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

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

}
