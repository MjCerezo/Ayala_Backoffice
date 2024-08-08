
package com.coopdb.data;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 *  COOPDB.BreakdownfixId
 *  08/04/2024 12:54:42
 * 
 */
public class BreakdownfixId
    implements Serializable
{

    private String txnDate;
    private String memberId;
    private String memberName;
    private String dxbankAcctNo;
    private String loanAcctNo;
    private String refNo;
    private String loanProduct;
    private BigDecimal principalPaid;
    private BigDecimal interestPaid;
    private BigDecimal penaltyPaid;
    private String column11;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BreakdownfixId)) {
            return false;
        }
        BreakdownfixId other = ((BreakdownfixId) o);
        if (this.txnDate == null) {
            if (other.txnDate!= null) {
                return false;
            }
        } else {
            if (!this.txnDate.equals(other.txnDate)) {
                return false;
            }
        }
        if (this.memberId == null) {
            if (other.memberId!= null) {
                return false;
            }
        } else {
            if (!this.memberId.equals(other.memberId)) {
                return false;
            }
        }
        if (this.memberName == null) {
            if (other.memberName!= null) {
                return false;
            }
        } else {
            if (!this.memberName.equals(other.memberName)) {
                return false;
            }
        }
        if (this.dxbankAcctNo == null) {
            if (other.dxbankAcctNo!= null) {
                return false;
            }
        } else {
            if (!this.dxbankAcctNo.equals(other.dxbankAcctNo)) {
                return false;
            }
        }
        if (this.loanAcctNo == null) {
            if (other.loanAcctNo!= null) {
                return false;
            }
        } else {
            if (!this.loanAcctNo.equals(other.loanAcctNo)) {
                return false;
            }
        }
        if (this.refNo == null) {
            if (other.refNo!= null) {
                return false;
            }
        } else {
            if (!this.refNo.equals(other.refNo)) {
                return false;
            }
        }
        if (this.loanProduct == null) {
            if (other.loanProduct!= null) {
                return false;
            }
        } else {
            if (!this.loanProduct.equals(other.loanProduct)) {
                return false;
            }
        }
        if (this.principalPaid == null) {
            if (other.principalPaid!= null) {
                return false;
            }
        } else {
            if (!this.principalPaid.equals(other.principalPaid)) {
                return false;
            }
        }
        if (this.interestPaid == null) {
            if (other.interestPaid!= null) {
                return false;
            }
        } else {
            if (!this.interestPaid.equals(other.interestPaid)) {
                return false;
            }
        }
        if (this.penaltyPaid == null) {
            if (other.penaltyPaid!= null) {
                return false;
            }
        } else {
            if (!this.penaltyPaid.equals(other.penaltyPaid)) {
                return false;
            }
        }
        if (this.column11 == null) {
            if (other.column11 != null) {
                return false;
            }
        } else {
            if (!this.column11 .equals(other.column11)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.txnDate!= null) {
            rtn = (rtn + this.txnDate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.memberId!= null) {
            rtn = (rtn + this.memberId.hashCode());
        }
        rtn = (rtn* 37);
        if (this.memberName!= null) {
            rtn = (rtn + this.memberName.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dxbankAcctNo!= null) {
            rtn = (rtn + this.dxbankAcctNo.hashCode());
        }
        rtn = (rtn* 37);
        if (this.loanAcctNo!= null) {
            rtn = (rtn + this.loanAcctNo.hashCode());
        }
        rtn = (rtn* 37);
        if (this.refNo!= null) {
            rtn = (rtn + this.refNo.hashCode());
        }
        rtn = (rtn* 37);
        if (this.loanProduct!= null) {
            rtn = (rtn + this.loanProduct.hashCode());
        }
        rtn = (rtn* 37);
        if (this.principalPaid!= null) {
            rtn = (rtn + this.principalPaid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.interestPaid!= null) {
            rtn = (rtn + this.interestPaid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.penaltyPaid!= null) {
            rtn = (rtn + this.penaltyPaid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.column11 != null) {
            rtn = (rtn + this.column11 .hashCode());
        }
        return rtn;
    }

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getDxbankAcctNo() {
        return dxbankAcctNo;
    }

    public void setDxbankAcctNo(String dxbankAcctNo) {
        this.dxbankAcctNo = dxbankAcctNo;
    }

    public String getLoanAcctNo() {
        return loanAcctNo;
    }

    public void setLoanAcctNo(String loanAcctNo) {
        this.loanAcctNo = loanAcctNo;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getLoanProduct() {
        return loanProduct;
    }

    public void setLoanProduct(String loanProduct) {
        this.loanProduct = loanProduct;
    }

    public BigDecimal getPrincipalPaid() {
        return principalPaid;
    }

    public void setPrincipalPaid(BigDecimal principalPaid) {
        this.principalPaid = principalPaid;
    }

    public BigDecimal getInterestPaid() {
        return interestPaid;
    }

    public void setInterestPaid(BigDecimal interestPaid) {
        this.interestPaid = interestPaid;
    }

    public BigDecimal getPenaltyPaid() {
        return penaltyPaid;
    }

    public void setPenaltyPaid(BigDecimal penaltyPaid) {
        this.penaltyPaid = penaltyPaid;
    }

    public String getColumn11() {
        return column11;
    }

    public void setColumn11(String column11) {
        this.column11 = column11;
    }

}
