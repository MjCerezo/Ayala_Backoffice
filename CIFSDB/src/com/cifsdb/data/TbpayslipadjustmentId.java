
package com.cifsdb.data;

import java.io.Serializable;
import java.util.Date;


/**
 *  CIFSDB.TbpayslipadjustmentId
 *  09/26/2023 10:13:05
 * 
 */
public class TbpayslipadjustmentId
    implements Serializable
{

    private String memberid;
    private Date paysliperiod;
    private Integer adjustmentno;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbpayslipadjustmentId)) {
            return false;
        }
        TbpayslipadjustmentId other = ((TbpayslipadjustmentId) o);
        if (this.memberid == null) {
            if (other.memberid!= null) {
                return false;
            }
        } else {
            if (!this.memberid.equals(other.memberid)) {
                return false;
            }
        }
        if (this.paysliperiod == null) {
            if (other.paysliperiod!= null) {
                return false;
            }
        } else {
            if (!this.paysliperiod.equals(other.paysliperiod)) {
                return false;
            }
        }
        if (this.adjustmentno == null) {
            if (other.adjustmentno!= null) {
                return false;
            }
        } else {
            if (!this.adjustmentno.equals(other.adjustmentno)) {
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
        if (this.paysliperiod!= null) {
            rtn = (rtn + this.paysliperiod.hashCode());
        }
        rtn = (rtn* 37);
        if (this.adjustmentno!= null) {
            rtn = (rtn + this.adjustmentno.hashCode());
        }
        return rtn;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public Date getPaysliperiod() {
        return paysliperiod;
    }

    public void setPaysliperiod(Date paysliperiod) {
        this.paysliperiod = paysliperiod;
    }

    public Integer getAdjustmentno() {
        return adjustmentno;
    }

    public void setAdjustmentno(Integer adjustmentno) {
        this.adjustmentno = adjustmentno;
    }

}
