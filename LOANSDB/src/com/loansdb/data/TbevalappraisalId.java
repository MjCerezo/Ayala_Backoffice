
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbevalappraisalId
 *  10/13/2020 10:21:35
 * 
 */
public class TbevalappraisalId
    implements Serializable
{

    private Integer evalreportid;
    private String referenceno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbevalappraisalId)) {
            return false;
        }
        TbevalappraisalId other = ((TbevalappraisalId) o);
        if (this.evalreportid == null) {
            if (other.evalreportid!= null) {
                return false;
            }
        } else {
            if (!this.evalreportid.equals(other.evalreportid)) {
                return false;
            }
        }
        if (this.referenceno == null) {
            if (other.referenceno!= null) {
                return false;
            }
        } else {
            if (!this.referenceno.equals(other.referenceno)) {
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
        if (this.referenceno!= null) {
            rtn = (rtn + this.referenceno.hashCode());
        }
        return rtn;
    }

    public Integer getEvalreportid() {
        return evalreportid;
    }

    public void setEvalreportid(Integer evalreportid) {
        this.evalreportid = evalreportid;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

}
