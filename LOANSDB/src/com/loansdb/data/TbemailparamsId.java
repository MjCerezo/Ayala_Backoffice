
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbemailparamsId
 *  10/13/2020 10:21:35
 * 
 */
public class TbemailparamsId
    implements Serializable
{

    private String emailtype;
    private String emailcode;
    private String username;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbemailparamsId)) {
            return false;
        }
        TbemailparamsId other = ((TbemailparamsId) o);
        if (this.emailtype == null) {
            if (other.emailtype!= null) {
                return false;
            }
        } else {
            if (!this.emailtype.equals(other.emailtype)) {
                return false;
            }
        }
        if (this.emailcode == null) {
            if (other.emailcode!= null) {
                return false;
            }
        } else {
            if (!this.emailcode.equals(other.emailcode)) {
                return false;
            }
        }
        if (this.username == null) {
            if (other.username!= null) {
                return false;
            }
        } else {
            if (!this.username.equals(other.username)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.emailtype!= null) {
            rtn = (rtn + this.emailtype.hashCode());
        }
        rtn = (rtn* 37);
        if (this.emailcode!= null) {
            rtn = (rtn + this.emailcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.username!= null) {
            rtn = (rtn + this.username.hashCode());
        }
        return rtn;
    }

    public String getEmailtype() {
        return emailtype;
    }

    public void setEmailtype(String emailtype) {
        this.emailtype = emailtype;
    }

    public String getEmailcode() {
        return emailcode;
    }

    public void setEmailcode(String emailcode) {
        this.emailcode = emailcode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
