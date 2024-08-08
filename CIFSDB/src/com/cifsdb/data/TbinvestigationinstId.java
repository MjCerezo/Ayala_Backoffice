
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.TbinvestigationinstId
 *  09/26/2023 10:13:06
 * 
 */
public class TbinvestigationinstId
    implements Serializable
{

    private String appno;
    private String cifno;
    private String investigationtype;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbinvestigationinstId)) {
            return false;
        }
        TbinvestigationinstId other = ((TbinvestigationinstId) o);
        if (this.appno == null) {
            if (other.appno!= null) {
                return false;
            }
        } else {
            if (!this.appno.equals(other.appno)) {
                return false;
            }
        }
        if (this.cifno == null) {
            if (other.cifno!= null) {
                return false;
            }
        } else {
            if (!this.cifno.equals(other.cifno)) {
                return false;
            }
        }
        if (this.investigationtype == null) {
            if (other.investigationtype!= null) {
                return false;
            }
        } else {
            if (!this.investigationtype.equals(other.investigationtype)) {
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
        if (this.cifno!= null) {
            rtn = (rtn + this.cifno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.investigationtype!= null) {
            rtn = (rtn + this.investigationtype.hashCode());
        }
        return rtn;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getInvestigationtype() {
        return investigationtype;
    }

    public void setInvestigationtype(String investigationtype) {
        this.investigationtype = investigationtype;
    }

}
