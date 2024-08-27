
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbuseraccessId
 *  08/27/2024 14:22:57
 * 
 */
public class TbuseraccessId
    implements Serializable
{

    private String username;
    private String roleid;
    private String accessname;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbuseraccessId)) {
            return false;
        }
        TbuseraccessId other = ((TbuseraccessId) o);
        if (this.username == null) {
            if (other.username!= null) {
                return false;
            }
        } else {
            if (!this.username.equals(other.username)) {
                return false;
            }
        }
        if (this.roleid == null) {
            if (other.roleid!= null) {
                return false;
            }
        } else {
            if (!this.roleid.equals(other.roleid)) {
                return false;
            }
        }
        if (this.accessname == null) {
            if (other.accessname!= null) {
                return false;
            }
        } else {
            if (!this.accessname.equals(other.accessname)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.username!= null) {
            rtn = (rtn + this.username.hashCode());
        }
        rtn = (rtn* 37);
        if (this.roleid!= null) {
            rtn = (rtn + this.roleid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.accessname!= null) {
            rtn = (rtn + this.accessname.hashCode());
        }
        return rtn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getAccessname() {
        return accessname;
    }

    public void setAccessname(String accessname) {
        this.accessname = accessname;
    }

}
