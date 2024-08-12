
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbloanprodmembertypeId
 *  08/10/2024 21:24:57
 * 
 */
public class TbloanprodmembertypeId
    implements Serializable
{

    private String loanproduct;
    private String membertype;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbloanprodmembertypeId)) {
            return false;
        }
        TbloanprodmembertypeId other = ((TbloanprodmembertypeId) o);
        if (this.loanproduct == null) {
            if (other.loanproduct!= null) {
                return false;
            }
        } else {
            if (!this.loanproduct.equals(other.loanproduct)) {
                return false;
            }
        }
        if (this.membertype == null) {
            if (other.membertype!= null) {
                return false;
            }
        } else {
            if (!this.membertype.equals(other.membertype)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.loanproduct!= null) {
            rtn = (rtn + this.loanproduct.hashCode());
        }
        rtn = (rtn* 37);
        if (this.membertype!= null) {
            rtn = (rtn + this.membertype.hashCode());
        }
        return rtn;
    }

    public String getLoanproduct() {
        return loanproduct;
    }

    public void setLoanproduct(String loanproduct) {
        this.loanproduct = loanproduct;
    }

    public String getMembertype() {
        return membertype;
    }

    public void setMembertype(String membertype) {
        this.membertype = membertype;
    }

}
