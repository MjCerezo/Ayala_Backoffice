
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbloanapprovaldetailsId
 *  08/10/2024 21:24:57
 * 
 */
public class TbloanapprovaldetailsId
    implements Serializable
{

    private Integer evalreportid;
    private String appno;
    private String username;
    private Integer approvallevel;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbloanapprovaldetailsId)) {
            return false;
        }
        TbloanapprovaldetailsId other = ((TbloanapprovaldetailsId) o);
        if (this.evalreportid == null) {
            if (other.evalreportid!= null) {
                return false;
            }
        } else {
            if (!this.evalreportid.equals(other.evalreportid)) {
                return false;
            }
        }
        if (this.appno == null) {
            if (other.appno!= null) {
                return false;
            }
        } else {
            if (!this.appno.equals(other.appno)) {
                return false;
            }
        }
        if (this.username == null) {
            if (other.username!= null) {
                return false;
            }
        } else {
            if (!this.username.equals(other.username)) {
                return false;
            }
        }
        if (this.approvallevel == null) {
            if (other.approvallevel!= null) {
                return false;
            }
        } else {
            if (!this.approvallevel.equals(other.approvallevel)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.evalreportid!= null) {
            rtn = (rtn + this.evalreportid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appno!= null) {
            rtn = (rtn + this.appno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.username!= null) {
            rtn = (rtn + this.username.hashCode());
        }
        rtn = (rtn* 37);
        if (this.approvallevel!= null) {
            rtn = (rtn + this.approvallevel.hashCode());
        }
        return rtn;
    }

    public Integer getEvalreportid() {
        return evalreportid;
    }

    public void setEvalreportid(Integer evalreportid) {
        this.evalreportid = evalreportid;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getApprovallevel() {
        return approvallevel;
    }

    public void setApprovallevel(Integer approvallevel) {
        this.approvallevel = approvallevel;
    }

}
