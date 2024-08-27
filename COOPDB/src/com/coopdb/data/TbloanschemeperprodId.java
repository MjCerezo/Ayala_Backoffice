
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbloanschemeperprodId
 *  08/27/2024 14:22:57
 * 
 */
public class TbloanschemeperprodId
    implements Serializable
{

    private String prodcode;
    private String schemecode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbloanschemeperprodId)) {
            return false;
        }
        TbloanschemeperprodId other = ((TbloanschemeperprodId) o);
        if (this.prodcode == null) {
            if (other.prodcode!= null) {
                return false;
            }
        } else {
            if (!this.prodcode.equals(other.prodcode)) {
                return false;
            }
        }
        if (this.schemecode == null) {
            if (other.schemecode!= null) {
                return false;
            }
        } else {
            if (!this.schemecode.equals(other.schemecode)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.prodcode!= null) {
            rtn = (rtn + this.prodcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.schemecode!= null) {
            rtn = (rtn + this.schemecode.hashCode());
        }
        return rtn;
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode;
    }

    public String getSchemecode() {
        return schemecode;
    }

    public void setSchemecode(String schemecode) {
        this.schemecode = schemecode;
    }

}
