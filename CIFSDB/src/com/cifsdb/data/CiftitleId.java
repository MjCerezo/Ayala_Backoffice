
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.CiftitleId
 *  08/06/2024 19:26:36
 * 
 */
public class CiftitleId
    implements Serializable
{

    private String originatingBranch;
    private String cifNo;
    private String title;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof CiftitleId)) {
            return false;
        }
        CiftitleId other = ((CiftitleId) o);
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
        if (this.title == null) {
            if (other.title!= null) {
                return false;
            }
        } else {
            if (!this.title.equals(other.title)) {
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
        if (this.title!= null) {
            rtn = (rtn + this.title.hashCode());
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
