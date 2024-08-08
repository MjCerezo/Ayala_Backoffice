
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.TbglentriesperproductId
 *  09/26/2023 10:13:06
 * 
 */
public class TbglentriesperproductId
    implements Serializable
{

    private String prodcode;
    private String txcode;
    private String glacctno;
    private String glline;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbglentriesperproductId)) {
            return false;
        }
        TbglentriesperproductId other = ((TbglentriesperproductId) o);
        if (this.prodcode == null) {
            if (other.prodcode!= null) {
                return false;
            }
        } else {
            if (!this.prodcode.equals(other.prodcode)) {
                return false;
            }
        }
        if (this.txcode == null) {
            if (other.txcode!= null) {
                return false;
            }
        } else {
            if (!this.txcode.equals(other.txcode)) {
                return false;
            }
        }
        if (this.glacctno == null) {
            if (other.glacctno!= null) {
                return false;
            }
        } else {
            if (!this.glacctno.equals(other.glacctno)) {
                return false;
            }
        }
        if (this.glline == null) {
            if (other.glline!= null) {
                return false;
            }
        } else {
            if (!this.glline.equals(other.glline)) {
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
        if (this.txcode!= null) {
            rtn = (rtn + this.txcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.glacctno!= null) {
            rtn = (rtn + this.glacctno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.glline!= null) {
            rtn = (rtn + this.glline.hashCode());
        }
        return rtn;
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public String getGlacctno() {
        return glacctno;
    }

    public void setGlacctno(String glacctno) {
        this.glacctno = glacctno;
    }

    public String getGlline() {
        return glline;
    }

    public void setGlline(String glline) {
        this.glline = glline;
    }

}
