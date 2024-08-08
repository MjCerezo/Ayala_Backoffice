
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbdatabaseparamsId
 *  10/13/2020 10:21:35
 * 
 */
public class TbdatabaseparamsId
    implements Serializable
{

    private String systemname;
    private String databasename;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbdatabaseparamsId)) {
            return false;
        }
        TbdatabaseparamsId other = ((TbdatabaseparamsId) o);
        if (this.systemname == null) {
            if (other.systemname!= null) {
                return false;
            }
        } else {
            if (!this.systemname.equals(other.systemname)) {
                return false;
            }
        }
        if (this.databasename == null) {
            if (other.databasename!= null) {
                return false;
            }
        } else {
            if (!this.databasename.equals(other.databasename)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.systemname!= null) {
            rtn = (rtn + this.systemname.hashCode());
        }
        rtn = (rtn* 37);
        if (this.databasename!= null) {
            rtn = (rtn + this.databasename.hashCode());
        }
        return rtn;
    }

    public String getSystemname() {
        return systemname;
    }

    public void setSystemname(String systemname) {
        this.systemname = systemname;
    }

    public String getDatabasename() {
        return databasename;
    }

    public void setDatabasename(String databasename) {
        this.databasename = databasename;
    }

}
