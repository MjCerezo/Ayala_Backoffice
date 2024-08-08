
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbintratebytermId
 *  08/04/2024 12:54:43
 * 
 */
public class TbintratebytermId
    implements Serializable
{

    private String productcode;
    private Integer loanterm;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbintratebytermId)) {
            return false;
        }
        TbintratebytermId other = ((TbintratebytermId) o);
        if (this.productcode == null) {
            if (other.productcode!= null) {
                return false;
            }
        } else {
            if (!this.productcode.equals(other.productcode)) {
                return false;
            }
        }
        if (this.loanterm == null) {
            if (other.loanterm!= null) {
                return false;
            }
        } else {
            if (!this.loanterm.equals(other.loanterm)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.productcode!= null) {
            rtn = (rtn + this.productcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.loanterm!= null) {
            rtn = (rtn + this.loanterm.hashCode());
        }
        return rtn;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public Integer getLoanterm() {
        return loanterm;
    }

    public void setLoanterm(Integer loanterm) {
        this.loanterm = loanterm;
    }

}
