
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.JointaccountsId
 *  12/05/2022 03:13:09
 * 
 */
public class JointaccountsId
    implements Serializable
{

    private String oldAccountNumber;
    private String oldCifnumber;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof JointaccountsId)) {
            return false;
        }
        JointaccountsId other = ((JointaccountsId) o);
        if (this.oldAccountNumber == null) {
            if (other.oldAccountNumber!= null) {
                return false;
            }
        } else {
            if (!this.oldAccountNumber.equals(other.oldAccountNumber)) {
                return false;
            }
        }
        if (this.oldCifnumber == null) {
            if (other.oldCifnumber!= null) {
                return false;
            }
        } else {
            if (!this.oldCifnumber.equals(other.oldCifnumber)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.oldAccountNumber!= null) {
            rtn = (rtn + this.oldAccountNumber.hashCode());
        }
        rtn = (rtn* 37);
        if (this.oldCifnumber!= null) {
            rtn = (rtn + this.oldCifnumber.hashCode());
        }
        return rtn;
    }

    public String getOldAccountNumber() {
        return oldAccountNumber;
    }

    public void setOldAccountNumber(String oldAccountNumber) {
        this.oldAccountNumber = oldAccountNumber;
    }

    public String getOldCifnumber() {
        return oldCifnumber;
    }

    public void setOldCifnumber(String oldCifnumber) {
        this.oldCifnumber = oldCifnumber;
    }

}
