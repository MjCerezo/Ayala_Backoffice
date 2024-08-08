
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbfsoperationId
 *  10/13/2020 10:21:35
 * 
 */
public class TbfsoperationId
    implements Serializable
{

    private String fsopttype;
    private String fsoptname;
    private String fsident;
    private String fsoptapplyto;
    private String fssection;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbfsoperationId)) {
            return false;
        }
        TbfsoperationId other = ((TbfsoperationId) o);
        if (this.fsopttype == null) {
            if (other.fsopttype!= null) {
                return false;
            }
        } else {
            if (!this.fsopttype.equals(other.fsopttype)) {
                return false;
            }
        }
        if (this.fsoptname == null) {
            if (other.fsoptname!= null) {
                return false;
            }
        } else {
            if (!this.fsoptname.equals(other.fsoptname)) {
                return false;
            }
        }
        if (this.fsident == null) {
            if (other.fsident!= null) {
                return false;
            }
        } else {
            if (!this.fsident.equals(other.fsident)) {
                return false;
            }
        }
        if (this.fsoptapplyto == null) {
            if (other.fsoptapplyto!= null) {
                return false;
            }
        } else {
            if (!this.fsoptapplyto.equals(other.fsoptapplyto)) {
                return false;
            }
        }
        if (this.fssection == null) {
            if (other.fssection!= null) {
                return false;
            }
        } else {
            if (!this.fssection.equals(other.fssection)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.fsopttype!= null) {
            rtn = (rtn + this.fsopttype.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fsoptname!= null) {
            rtn = (rtn + this.fsoptname.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fsident!= null) {
            rtn = (rtn + this.fsident.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fsoptapplyto!= null) {
            rtn = (rtn + this.fsoptapplyto.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fssection!= null) {
            rtn = (rtn + this.fssection.hashCode());
        }
        return rtn;
    }

    public String getFsopttype() {
        return fsopttype;
    }

    public void setFsopttype(String fsopttype) {
        this.fsopttype = fsopttype;
    }

    public String getFsoptname() {
        return fsoptname;
    }

    public void setFsoptname(String fsoptname) {
        this.fsoptname = fsoptname;
    }

    public String getFsident() {
        return fsident;
    }

    public void setFsident(String fsident) {
        this.fsident = fsident;
    }

    public String getFsoptapplyto() {
        return fsoptapplyto;
    }

    public void setFsoptapplyto(String fsoptapplyto) {
        this.fsoptapplyto = fsoptapplyto;
    }

    public String getFssection() {
        return fssection;
    }

    public void setFssection(String fssection) {
        this.fssection = fssection;
    }

}
