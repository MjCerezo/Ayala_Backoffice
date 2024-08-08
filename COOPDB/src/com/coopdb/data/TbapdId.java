
package com.coopdb.data;

import java.io.Serializable;
import java.util.Date;


/**
 *  COOPDB.TbapdId
 *  08/04/2024 12:54:43
 * 
 */
public class TbapdId
    implements Serializable
{

    private String memberid;
    private Date payslipperiod;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbapdId)) {
            return false;
        }
        TbapdId other = ((TbapdId) o);
        if (this.memberid == null) {
            if (other.memberid!= null) {
                return false;
            }
        } else {
            if (!this.memberid.equals(other.memberid)) {
                return false;
            }
        }
        if (this.payslipperiod == null) {
            if (other.payslipperiod!= null) {
                return false;
            }
        } else {
            if (!this.payslipperiod.equals(other.payslipperiod)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.memberid!= null) {
            rtn = (rtn + this.memberid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.payslipperiod!= null) {
            rtn = (rtn + this.payslipperiod.hashCode());
        }
        return rtn;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public Date getPayslipperiod() {
        return payslipperiod;
    }

    public void setPayslipperiod(Date payslipperiod) {
        this.payslipperiod = payslipperiod;
    }

}
