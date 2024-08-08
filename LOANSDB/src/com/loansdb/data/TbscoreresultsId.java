
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbscoreresultsId
 *  10/13/2020 10:21:35
 * 
 */
public class TbscoreresultsId
    implements Serializable
{

    private String apprefno;
    private Integer evalreportid;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbscoreresultsId)) {
            return false;
        }
        TbscoreresultsId other = ((TbscoreresultsId) o);
        if (this.apprefno == null) {
            if (other.apprefno!= null) {
                return false;
            }
        } else {
            if (!this.apprefno.equals(other.apprefno)) {
                return false;
            }
        }
        if (this.evalreportid == null) {
            if (other.evalreportid!= null) {
                return false;
            }
        } else {
            if (!this.evalreportid.equals(other.evalreportid)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.apprefno!= null) {
            rtn = (rtn + this.apprefno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.evalreportid!= null) {
            rtn = (rtn + this.evalreportid.hashCode());
        }
        return rtn;
    }

    public String getApprefno() {
        return apprefno;
    }

    public void setApprefno(String apprefno) {
        this.apprefno = apprefno;
    }

    public Integer getEvalreportid() {
        return evalreportid;
    }

    public void setEvalreportid(Integer evalreportid) {
        this.evalreportid = evalreportid;
    }

}
