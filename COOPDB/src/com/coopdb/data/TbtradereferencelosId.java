
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbtradereferencelosId
 *  08/27/2024 14:22:56
 * 
 */
public class TbtradereferencelosId
    implements Serializable
{

    private String cifno;
    private String tradecifno;
    private String tradetype;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbtradereferencelosId)) {
            return false;
        }
        TbtradereferencelosId other = ((TbtradereferencelosId) o);
        if (this.cifno == null) {
            if (other.cifno!= null) {
                return false;
            }
        } else {
            if (!this.cifno.equals(other.cifno)) {
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
        if (this.tradetype == null) {
            if (other.tradetype!= null) {
                return false;
            }
        } else {
            if (!this.tradetype.equals(other.tradetype)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.cifno!= null) {
            rtn = (rtn + this.cifno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.tradecifno!= null) {
            rtn = (rtn + this.tradecifno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.tradetype!= null) {
            rtn = (rtn + this.tradetype.hashCode());
        }
        return rtn;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getTradecifno() {
        return tradecifno;
    }

    public void setTradecifno(String tradecifno) {
        this.tradecifno = tradecifno;
    }

    public String getTradetype() {
        return tradetype;
    }

    public void setTradetype(String tradetype) {
        this.tradetype = tradetype;
    }

}
