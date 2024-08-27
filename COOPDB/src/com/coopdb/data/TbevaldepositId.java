
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbevaldepositId
 *  08/27/2024 14:22:58
 * 
 */
public class TbevaldepositId
    implements Serializable
{

    private Integer evalreportid;
    private Integer recordid;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbevaldepositId)) {
            return false;
        }
        TbevaldepositId other = ((TbevaldepositId) o);
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
