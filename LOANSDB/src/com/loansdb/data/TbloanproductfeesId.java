
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbloanproductfeesId
 *  10/13/2020 10:21:35
 * 
 */
public class TbloanproductfeesId
    implements Serializable
{

    private String prodcode;
    private String loanfeecode;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbloanproductfeesId)) {
            return false;
        }
        TbloanproductfeesId other = ((TbloanproductfeesId) o);
        if (this.prodcode == null) {
            if (other.prodcode!= null) {
                return false;
            }
        } else {
            if (!this.prodcode.equals(other.prodcode)) {
                return false;
            }
        }
        if (this.loanfeecode == null) {
            if (other.loanfeecode!= null) {
                return false;
            }
        } else {
            if (!this.loanfeecode.equals(other.loanfeecode)) {
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
        if (this.loanfeecode!= null) {
            rtn = (rtn + this.loanfeecode.hashCode());
        }
        return rtn;
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode;
    }

    public String getLoanfeecode() {
        return loanfeecode;
    }

    public void setLoanfeecode(String loanfeecode) {
        this.loanfeecode = loanfeecode;
    }

}
