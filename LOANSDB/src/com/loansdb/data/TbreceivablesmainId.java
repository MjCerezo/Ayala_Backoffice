
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbreceivablesmainId
 *  06/24/2019 10:47:39
 * 
 */
public class TbreceivablesmainId
    implements Serializable
{

    private String referenceno;
    private String receivabletype;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbreceivablesmainId)) {
            return false;
        }
        TbreceivablesmainId other = ((TbreceivablesmainId) o);
        if (this.referenceno == null) {
            if (other.referenceno!= null) {
                return false;
            }
        } else {
            if (!this.referenceno.equals(other.referenceno)) {
                return false;
            }
        }
        if (this.receivabletype == null) {
            if (other.receivabletype!= null) {
                return false;
            }
        } else {
            if (!this.receivabletype.equals(other.receivabletype)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.referenceno!= null) {
            rtn = (rtn + this.referenceno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.receivabletype!= null) {
            rtn = (rtn + this.receivabletype.hashCode());
        }
        return rtn;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public String getReceivabletype() {
        return receivabletype;
    }

    public void setReceivabletype(String receivabletype) {
        this.receivabletype = receivabletype;
    }

}
