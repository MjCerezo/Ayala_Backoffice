
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbroleId
 *  08/04/2024 12:54:43
 * 
 */
public class TbroleId
    implements Serializable
{

    private String roleid;
    private String rolename;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbroleId)) {
            return false;
        }
        TbroleId other = ((TbroleId) o);
        if (this.roleid == null) {
            if (other.roleid!= null) {
                return false;
            }
        } else {
            if (!this.roleid.equals(other.roleid)) {
                return false;
            }
        }
        if (this.rolename == null) {
            if (other.rolename!= null) {
                return false;
            }
        } else {
            if (!this.rolename.equals(other.rolename)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.roleid!= null) {
            rtn = (rtn + this.roleid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.rolename!= null) {
            rtn = (rtn + this.rolename.hashCode());
        }
        return rtn;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

}
