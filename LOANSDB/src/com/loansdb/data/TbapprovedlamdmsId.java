
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbapprovedlamdmsId
 *  10/13/2020 10:21:35
 * 
 */
public class TbapprovedlamdmsId
    implements Serializable
{

    private String appno;
    private String cfrefno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbapprovedlamdmsId)) {
            return false;
        }
        TbapprovedlamdmsId other = ((TbapprovedlamdmsId) o);
        if (this.appno == null) {
            if (other.appno!= null) {
                return false;
            }
        } else {
            if (!this.appno.equals(other.appno)) {
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
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.appno!= null) {
            rtn = (rtn + this.appno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cfrefno!= null) {
            rtn = (rtn + this.cfrefno.hashCode());
        }
        return rtn;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getCfrefno() {
        return cfrefno;
    }

    public void setCfrefno(String cfrefno) {
        this.cfrefno = cfrefno;
    }

}
