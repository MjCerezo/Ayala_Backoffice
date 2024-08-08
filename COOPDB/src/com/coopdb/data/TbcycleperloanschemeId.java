
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbcycleperloanschemeId
 *  08/04/2024 12:54:41
 * 
 */
public class TbcycleperloanschemeId
    implements Serializable
{

    private Integer schemecode;
    private String cyclecode;
    private String pi;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbcycleperloanschemeId)) {
            return false;
        }
        TbcycleperloanschemeId other = ((TbcycleperloanschemeId) o);
        if (this.schemecode == null) {
            if (other.schemecode!= null) {
                return false;
            }
        } else {
            if (!this.schemecode.equals(other.schemecode)) {
                return false;
            }
        }
        if (this.cyclecode == null) {
            if (other.cyclecode!= null) {
                return false;
            }
        } else {
            if (!this.cyclecode.equals(other.cyclecode)) {
                return false;
            }
        }
        if (this.pi == null) {
            if (other.pi!= null) {
                return false;
            }
        } else {
            if (!this.pi.equals(other.pi)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.schemecode!= null) {
            rtn = (rtn + this.schemecode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cyclecode!= null) {
            rtn = (rtn + this.cyclecode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.pi!= null) {
            rtn = (rtn + this.pi.hashCode());
        }
        return rtn;
    }

    public Integer getSchemecode() {
        return schemecode;
    }

    public void setSchemecode(Integer schemecode) {
        this.schemecode = schemecode;
    }

    public String getCyclecode() {
        return cyclecode;
    }

    public void setCyclecode(String cyclecode) {
        this.cyclecode = cyclecode;
    }

    public String getPi() {
        return pi;
    }

    public void setPi(String pi) {
        this.pi = pi;
    }

}
