
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbaccountingentriesId
 *  08/01/2019 14:43:51
 * 
 */
public class TbaccountingentriesId
    implements Serializable
{

    private String txcode;
    private String gllineno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbaccountingentriesId)) {
            return false;
        }
        TbaccountingentriesId other = ((TbaccountingentriesId) o);
        if (this.txcode == null) {
            if (other.txcode!= null) {
                return false;
            }
        } else {
            if (!this.txcode.equals(other.txcode)) {
                return false;
            }
        }
        if (this.gllineno == null) {
            if (other.gllineno!= null) {
                return false;
            }
        } else {
            if (!this.gllineno.equals(other.gllineno)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.txcode!= null) {
            rtn = (rtn + this.txcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.gllineno!= null) {
            rtn = (rtn + this.gllineno.hashCode());
        }
        return rtn;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public String getGllineno() {
        return gllineno;
    }

    public void setGllineno(String gllineno) {
        this.gllineno = gllineno;
    }

}
