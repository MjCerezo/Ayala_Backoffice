
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TblstcomakersId
 *  08/27/2024 14:22:57
 * 
 */
public class TblstcomakersId
    implements Serializable
{

    private String appno;
    private String membershipid;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TblstcomakersId)) {
            return false;
        }
        TblstcomakersId other = ((TblstcomakersId) o);
        if (this.appno == null) {
            if (other.appno!= null) {
                return false;
            }
        } else {
            if (!this.appno.equals(other.appno)) {
                return false;
            }
        }
        if (this.membershipid == null) {
            if (other.membershipid!= null) {
                return false;
            }
        } else {
            if (!this.membershipid.equals(other.membershipid)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.appno!= null) {
            rtn = (rtn + this.appno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.membershipid!= null) {
            rtn = (rtn + this.membershipid.hashCode());
        }
        return rtn;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getMembershipid() {
        return membershipid;
    }

    public void setMembershipid(String membershipid) {
        this.membershipid = membershipid;
    }

}
