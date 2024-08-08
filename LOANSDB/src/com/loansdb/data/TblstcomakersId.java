
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TblstcomakersId
 *  10/13/2020 10:21:35
 * 
 */
public class TblstcomakersId
    implements Serializable
{

    private String appno;
    private String cifno;
    private String participationcode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TblstcomakersId)) {
            return false;
        }
        TblstcomakersId other = ((TblstcomakersId) o);
        if (this.appno == null) {
            if (other.appno!= null) {
                return false;
            }
        } else {
            if (!this.appno.equals(other.appno)) {
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
        if (this.participationcode == null) {
            if (other.participationcode!= null) {
                return false;
            }
        } else {
            if (!this.participationcode.equals(other.participationcode)) {
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
        if (this.cifno!= null) {
            rtn = (rtn + this.cifno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.participationcode!= null) {
            rtn = (rtn + this.participationcode.hashCode());
        }
        return rtn;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getParticipationcode() {
        return participationcode;
    }

    public void setParticipationcode(String participationcode) {
        this.participationcode = participationcode;
    }

}
