
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbevaltradecheckId
 *  10/13/2020 10:21:35
 * 
 */
public class TbevaltradecheckId
    implements Serializable
{

    private Integer evalreportid;
    private Integer recordid;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbevaltradecheckId)) {
            return false;
        }
        TbevaltradecheckId other = ((TbevaltradecheckId) o);
        if (this.evalreportid == null) {
            if (other.evalreportid!= null) {
                return false;
            }
        } else {
            if (!this.evalreportid.equals(other.evalreportid)) {
                return false;
            }
        }
        if (this.recordid == null) {
            if (other.recordid!= null) {
                return false;
            }
        } else {
            if (!this.recordid.equals(other.recordid)) {
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
        if (this.recordid!= null) {
            rtn = (rtn + this.recordid.hashCode());
        }
        return rtn;
    }

    public Integer getEvalreportid() {
        return evalreportid;
    }

    public void setEvalreportid(Integer evalreportid) {
        this.evalreportid = evalreportid;
    }

    public Integer getRecordid() {
        return recordid;
    }

    public void setRecordid(Integer recordid) {
        this.recordid = recordid;
    }

}
