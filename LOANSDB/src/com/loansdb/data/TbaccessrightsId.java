
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbaccessrightsId
 *  10/13/2020 10:21:35
 * 
 */
public class TbaccessrightsId
    implements Serializable
{

    private String accessname;
    private String modulename;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbaccessrightsId)) {
            return false;
        }
        TbaccessrightsId other = ((TbaccessrightsId) o);
        if (this.accessname == null) {
            if (other.accessname!= null) {
                return false;
            }
        } else {
            if (!this.accessname.equals(other.accessname)) {
                return false;
            }
        }
        if (this.modulename == null) {
            if (other.modulename!= null) {
                return false;
            }
        } else {
            if (!this.modulename.equals(other.modulename)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.accessname!= null) {
            rtn = (rtn + this.accessname.hashCode());
        }
        rtn = (rtn* 37);
        if (this.modulename!= null) {
            rtn = (rtn + this.modulename.hashCode());
        }
        return rtn;
    }

    public String getAccessname() {
        return accessname;
    }

    public void setAccessname(String accessname) {
        this.accessname = accessname;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

}
