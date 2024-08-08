
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbfssubsectionId
 *  10/13/2020 10:21:35
 * 
 */
public class TbfssubsectionId
    implements Serializable
{

    private String fssection;
    private String fssubsection;
    private String fssubsectiondesc;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbfssubsectionId)) {
            return false;
        }
        TbfssubsectionId other = ((TbfssubsectionId) o);
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
        if (this.fssubsectiondesc == null) {
            if (other.fssubsectiondesc!= null) {
                return false;
            }
        } else {
            if (!this.fssubsectiondesc.equals(other.fssubsectiondesc)) {
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
        if (this.fssubsectiondesc!= null) {
            rtn = (rtn + this.fssubsectiondesc.hashCode());
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

    public String getFssubsectiondesc() {
        return fssubsectiondesc;
    }

    public void setFssubsectiondesc(String fssubsectiondesc) {
        this.fssubsectiondesc = fssubsectiondesc;
    }

}
