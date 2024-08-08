
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbteamsId
 *  05/15/2018 12:03:54
 * 
 */
public class TbteamsId
    implements Serializable
{

    private String teamcode;
    private String teamname;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbteamsId)) {
            return false;
        }
        TbteamsId other = ((TbteamsId) o);
        if (this.teamcode == null) {
            if (other.teamcode!= null) {
                return false;
            }
        } else {
            if (!this.teamcode.equals(other.teamcode)) {
                return false;
            }
        }
        if (this.teamname == null) {
            if (other.teamname!= null) {
                return false;
            }
        } else {
            if (!this.teamname.equals(other.teamname)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.teamcode!= null) {
            rtn = (rtn + this.teamcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.teamname!= null) {
            rtn = (rtn + this.teamname.hashCode());
        }
        return rtn;
    }

    public String getTeamcode() {
        return teamcode;
    }

    public void setTeamcode(String teamcode) {
        this.teamcode = teamcode;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

}
