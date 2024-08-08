
package com.coopdb.data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *  COOPDB.SavingsId
 *  08/24/2022 23:00:29
 * 
 */
public class SavingsId
    implements Serializable
{

    private String accountNo;
    private String name;
    private String asOfDate;
    private BigDecimal asOfBalance;
    private String asOfStatus;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SavingsId)) {
            return false;
        }
        SavingsId other = ((SavingsId) o);
        if (this.accountNo == null) {
            if (other.accountNo!= null) {
                return false;
            }
        } else {
            if (!this.accountNo.equals(other.accountNo)) {
                return false;
            }
        }
        if (this.name == null) {
            if (other.name!= null) {
                return false;
            }
        } else {
            if (!this.name.equals(other.name)) {
                return false;
            }
        }
        if (this.asOfDate == null) {
            if (other.asOfDate!= null) {
                return false;
            }
        } else {
            if (!this.asOfDate.equals(other.asOfDate)) {
                return false;
            }
        }
        if (this.asOfBalance == null) {
            if (other.asOfBalance!= null) {
                return false;
            }
        } else {
            if (!this.asOfBalance.equals(other.asOfBalance)) {
                return false;
            }
        }
        if (this.asOfStatus == null) {
            if (other.asOfStatus!= null) {
                return false;
            }
        } else {
            if (!this.asOfStatus.equals(other.asOfStatus)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.accountNo!= null) {
            rtn = (rtn + this.accountNo.hashCode());
        }
        rtn = (rtn* 37);
        if (this.name!= null) {
            rtn = (rtn + this.name.hashCode());
        }
        rtn = (rtn* 37);
        if (this.asOfDate!= null) {
            rtn = (rtn + this.asOfDate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.asOfBalance!= null) {
            rtn = (rtn + this.asOfBalance.hashCode());
        }
        rtn = (rtn* 37);
        if (this.asOfStatus!= null) {
            rtn = (rtn + this.asOfStatus.hashCode());
        }
        return rtn;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(String asOfDate) {
        this.asOfDate = asOfDate;
    }

    public BigDecimal getAsOfBalance() {
        return asOfBalance;
    }

    public void setAsOfBalance(BigDecimal asOfBalance) {
        this.asOfBalance = asOfBalance;
    }

    public String getAsOfStatus() {
        return asOfStatus;
    }

    public void setAsOfStatus(String asOfStatus) {
        this.asOfStatus = asOfStatus;
    }

}
