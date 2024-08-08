
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbfssectionId
 *  10/13/2020 10:21:35
 * 
 */
public class TbfssectionId
    implements Serializable
{

    private String fssection;
    private String fssectiondesc;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbfssectionId)) {
            return false;
        }
        TbfssectionId other = ((TbfssectionId) o);
        if (this.fssection == null) {
            if (other.fssection!= null) {
                return false;
            }
        } else {
            if (!this.fssection.equals(other.fssection)) {
                return false;
            }
        }
        if (this.fssectiondesc == null) {
            if (other.fssectiondesc!= null) {
                return false;
            }
        } else {
            if (!this.fssectiondesc.equals(other.fssectiondesc)) {
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
        if (this.fssectiondesc!= null) {
            rtn = (rtn + this.fssectiondesc.hashCode());
        }
        return rtn;
    }

    public String getFssection() {
        return fssection;
    }

    public void setFssection(String fssection) {
        this.fssection = fssection;
    }

    public String getFssectiondesc() {
        return fssectiondesc;
    }

    public void setFssectiondesc(String fssectiondesc) {
        this.fssectiondesc = fssectiondesc;
    }

}
