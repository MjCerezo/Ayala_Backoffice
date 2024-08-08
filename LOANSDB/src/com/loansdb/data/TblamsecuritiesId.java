
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TblamsecuritiesId
 *  02/13/2019 19:49:04
 * 
 */
public class TblamsecuritiesId
    implements Serializable
{

    private String evalreportid;
    private String cfrefno;
    private String appno;
    private String collateralid;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TblamsecuritiesId)) {
            return false;
        }
        TblamsecuritiesId other = ((TblamsecuritiesId) o);
        if (this.evalreportid == null) {
            if (other.evalreportid!= null) {
                return false;
            }
        } else {
            if (!this.evalreportid.equals(other.evalreportid)) {
                return false;
            }
        }
        if (this.cfrefno == null) {
            if (other.cfrefno!= null) {
                return false;
            }
        } else {
            if (!this.cfrefno.equals(other.cfrefno)) {
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
        if (this.collateralid == null) {
            if (other.collateralid!= null) {
                return false;
            }
        } else {
            if (!this.collateralid.equals(other.collateralid)) {
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
        if (this.cfrefno!= null) {
            rtn = (rtn + this.cfrefno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appno!= null) {
            rtn = (rtn + this.appno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.collateralid!= null) {
            rtn = (rtn + this.collateralid.hashCode());
        }
        return rtn;
    }

    public String getEvalreportid() {
        return evalreportid;
    }

    public void setEvalreportid(String evalreportid) {
        this.evalreportid = evalreportid;
    }

    public String getCfrefno() {
        return cfrefno;
    }

    public void setCfrefno(String cfrefno) {
        this.cfrefno = cfrefno;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getCollateralid() {
        return collateralid;
    }

    public void setCollateralid(String collateralid) {
        this.collateralid = collateralid;
    }

}
