
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbevalbiId
 *  08/27/2024 14:22:57
 * 
 */
public class TbevalbiId
    implements Serializable
{

    private Integer evalreportid;
    private String cifno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbevalbiId)) {
            return false;
        }
        TbevalbiId other = ((TbevalbiId) o);
        if (this.evalreportid == null) {
            if (other.evalreportid!= null) {
                return false;
            }
        } else {
            if (!this.evalreportid.equals(other.evalreportid)) {
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
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.evalreportid!= null) {
            rtn = (rtn + this.evalreportid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cifno!= null) {
            rtn = (rtn + this.cifno.hashCode());
        }
        return rtn;
    }

    public Integer getEvalreportid() {
        return evalreportid;
    }

    public void setEvalreportid(Integer evalreportid) {
        this.evalreportid = evalreportid;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

}
