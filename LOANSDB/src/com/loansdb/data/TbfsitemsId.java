
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbfsitemsId
 *  10/13/2020 10:21:35
 * 
 */
public class TbfsitemsId
    implements Serializable
{

    private String fssection;
    private String fssubsection;
    private String fsitemtype;
    private String fsitemname;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbfsitemsId)) {
            return false;
        }
        TbfsitemsId other = ((TbfsitemsId) o);
        if (this.fssection == null) {
            if (other.fssection!= null) {
                return false;
            }
        } else {
            if (!this.fssection.equals(other.fssection)) {
                return false;
            }
        }
        if (this.fssubsection == null) {
            if (other.fssubsection!= null) {
                return false;
            }
        } else {
            if (!this.fssubsection.equals(other.fssubsection)) {
                return false;
            }
        }
        if (this.fsitemtype == null) {
            if (other.fsitemtype!= null) {
                return false;
            }
        } else {
            if (!this.fsitemtype.equals(other.fsitemtype)) {
                return false;
            }
        }
        if (this.fsitemname == null) {
            if (other.fsitemname!= null) {
                return false;
            }
        } else {
            if (!this.fsitemname.equals(other.fsitemname)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.fssection!= null) {
            rtn = (rtn + this.fssection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fssubsection!= null) {
            rtn = (rtn + this.fssubsection.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fsitemtype!= null) {
            rtn = (rtn + this.fsitemtype.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fsitemname!= null) {
            rtn = (rtn + this.fsitemname.hashCode());
        }
        return rtn;
    }

    public String getFssection() {
        return fssection;
    }

    public void setFssection(String fssection) {
        this.fssection = fssection;
    }

    public String getFssubsection() {
        return fssubsection;
    }

    public void setFssubsection(String fssubsection) {
        this.fssubsection = fssubsection;
    }

    public String getFsitemtype() {
        return fsitemtype;
    }

    public void setFsitemtype(String fsitemtype) {
        this.fsitemtype = fsitemtype;
    }

    public String getFsitemname() {
        return fsitemname;
    }

    public void setFsitemname(String fsitemname) {
        this.fsitemname = fsitemname;
    }

}
