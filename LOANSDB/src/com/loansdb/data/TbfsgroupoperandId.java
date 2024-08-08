
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbfsgroupoperandId
 *  10/13/2020 10:21:35
 * 
 */
public class TbfsgroupoperandId
    implements Serializable
{

    private String fsoprgroupcode;
    private String fsident;
    private String fsoptname;
    private String fssection;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbfsgroupoperandId)) {
            return false;
        }
        TbfsgroupoperandId other = ((TbfsgroupoperandId) o);
        if (this.fsoprgroupcode == null) {
            if (other.fsoprgroupcode!= null) {
                return false;
            }
        } else {
            if (!this.fsoprgroupcode.equals(other.fsoprgroupcode)) {
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
        if (this.fsoptname == null) {
            if (other.fsoptname!= null) {
                return false;
            }
        } else {
            if (!this.fsoptname.equals(other.fsoptname)) {
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
        if (this.fsoprgroupcode!= null) {
            rtn = (rtn + this.fsoprgroupcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fsident!= null) {
            rtn = (rtn + this.fsident.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fsoptname!= null) {
            rtn = (rtn + this.fsoptname.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fssection!= null) {
            rtn = (rtn + this.fssection.hashCode());
        }
        return rtn;
    }

    public String getFsoprgroupcode() {
        return fsoprgroupcode;
    }

    public void setFsoprgroupcode(String fsoprgroupcode) {
        this.fsoprgroupcode = fsoprgroupcode;
    }

    public String getFsident() {
        return fsident;
    }

    public void setFsident(String fsident) {
        this.fsident = fsident;
    }

    public String getFsoptname() {
        return fsoptname;
    }

    public void setFsoptname(String fsoptname) {
        this.fsoptname = fsoptname;
    }

    public String getFssection() {
        return fssection;
    }

    public void setFssection(String fssection) {
        this.fssection = fssection;
    }

}
