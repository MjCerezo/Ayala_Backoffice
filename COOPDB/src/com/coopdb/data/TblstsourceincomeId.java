
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TblstsourceincomeId
 *  12/06/2018 11:15:11
 * 
 */
public class TblstsourceincomeId
    implements Serializable
{

    private Integer id;
    private String appno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TblstsourceincomeId)) {
            return false;
        }
        TblstsourceincomeId other = ((TblstsourceincomeId) o);
        if (this.id == null) {
            if (other.id!= null) {
                return false;
            }
        } else {
            if (!this.id.equals(other.id)) {
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
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.id!= null) {
            rtn = (rtn + this.id.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appno!= null) {
            rtn = (rtn + this.appno.hashCode());
        }
        return rtn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

}
