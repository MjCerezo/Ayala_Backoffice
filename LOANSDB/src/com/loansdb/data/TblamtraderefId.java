
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TblamtraderefId
 *  10/13/2020 10:21:35
 * 
 */
public class TblamtraderefId
    implements Serializable
{

    private String appno;
    private String maincifno;
    private String tradecifno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TblamtraderefId)) {
            return false;
        }
        TblamtraderefId other = ((TblamtraderefId) o);
        if (this.appno == null) {
            if (other.appno!= null) {
                return false;
            }
        } else {
            if (!this.appno.equals(other.appno)) {
                return false;
            }
        }
        if (this.maincifno == null) {
            if (other.maincifno!= null) {
                return false;
            }
        } else {
            if (!this.maincifno.equals(other.maincifno)) {
                return false;
            }
        }
        if (this.tradecifno == null) {
            if (other.tradecifno!= null) {
                return false;
            }
        } else {
            if (!this.tradecifno.equals(other.tradecifno)) {
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
        if (this.maincifno!= null) {
            rtn = (rtn + this.maincifno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.tradecifno!= null) {
            rtn = (rtn + this.tradecifno.hashCode());
        }
        return rtn;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getMaincifno() {
        return maincifno;
    }

    public void setMaincifno(String maincifno) {
        this.maincifno = maincifno;
    }

    public String getTradecifno() {
        return tradecifno;
    }

    public void setTradecifno(String tradecifno) {
        this.tradecifno = tradecifno;
    }

}
