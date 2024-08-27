
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.ChangeMemberStatusId
 *  08/15/2024 19:53:55
 * 
 */
public class ChangeMemberStatusId
    implements Serializable
{

    private String transactionNumber;
    private String cifno;
    private String memberId;
    private String applicationStatus;
    private String currentStatus;
    private String subCurrentStatus;
    private String newStatus;
    private String subNewStatus;
    private String dateCreated;
    private String createdBy;
    private String dateApproved;
    private String approvedBy;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ChangeMemberStatusId)) {
            return false;
        }
        ChangeMemberStatusId other = ((ChangeMemberStatusId) o);
        if (this.transactionNumber == null) {
            if (other.transactionNumber!= null) {
                return false;
            }
        } else {
            if (!this.transactionNumber.equals(other.transactionNumber)) {
                return false;
            }
        }
        if (this.cifno == null) {
            if (other.cifno!= null) {
                return false;
            }
        } else {
            if (!this.cifno.equals(other.cifno)) {
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
        if (this.applicationStatus == null) {
            if (other.applicationStatus!= null) {
                return false;
            }
        } else {
            if (!this.applicationStatus.equals(other.applicationStatus)) {
                return false;
            }
        }
        if (this.currentStatus == null) {
            if (other.currentStatus!= null) {
                return false;
            }
        } else {
            if (!this.currentStatus.equals(other.currentStatus)) {
                return false;
            }
        }
        if (this.subCurrentStatus == null) {
            if (other.subCurrentStatus!= null) {
                return false;
            }
        } else {
            if (!this.subCurrentStatus.equals(other.subCurrentStatus)) {
                return false;
            }
        }
        if (this.newStatus == null) {
            if (other.newStatus!= null) {
                return false;
            }
        } else {
            if (!this.newStatus.equals(other.newStatus)) {
                return false;
            }
        }
        if (this.subNewStatus == null) {
            if (other.subNewStatus!= null) {
                return false;
            }
        } else {
            if (!this.subNewStatus.equals(other.subNewStatus)) {
                return false;
            }
        }
        if (this.dateCreated == null) {
            if (other.dateCreated!= null) {
                return false;
            }
        } else {
            if (!this.dateCreated.equals(other.dateCreated)) {
                return false;
            }
        }
        if (this.createdBy == null) {
            if (other.createdBy!= null) {
                return false;
            }
        } else {
            if (!this.createdBy.equals(other.createdBy)) {
                return false;
            }
        }
        if (this.dateApproved == null) {
            if (other.dateApproved!= null) {
                return false;
            }
        } else {
            if (!this.dateApproved.equals(other.dateApproved)) {
                return false;
            }
        }
        if (this.approvedBy == null) {
            if (other.approvedBy!= null) {
                return false;
            }
        } else {
            if (!this.approvedBy.equals(other.approvedBy)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.transactionNumber!= null) {
            rtn = (rtn + this.transactionNumber.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cifno!= null) {
            rtn = (rtn + this.cifno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.memberId!= null) {
            rtn = (rtn + this.memberId.hashCode());
        }
        rtn = (rtn* 37);
        if (this.applicationStatus!= null) {
            rtn = (rtn + this.applicationStatus.hashCode());
        }
        rtn = (rtn* 37);
        if (this.currentStatus!= null) {
            rtn = (rtn + this.currentStatus.hashCode());
        }
        rtn = (rtn* 37);
        if (this.subCurrentStatus!= null) {
            rtn = (rtn + this.subCurrentStatus.hashCode());
        }
        rtn = (rtn* 37);
        if (this.newStatus!= null) {
            rtn = (rtn + this.newStatus.hashCode());
        }
        rtn = (rtn* 37);
        if (this.subNewStatus!= null) {
            rtn = (rtn + this.subNewStatus.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dateCreated!= null) {
            rtn = (rtn + this.dateCreated.hashCode());
        }
        rtn = (rtn* 37);
        if (this.createdBy!= null) {
            rtn = (rtn + this.createdBy.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dateApproved!= null) {
            rtn = (rtn + this.dateApproved.hashCode());
        }
        rtn = (rtn* 37);
        if (this.approvedBy!= null) {
            rtn = (rtn + this.approvedBy.hashCode());
        }
        return rtn;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public String getSubCurrentStatus() {
        return subCurrentStatus;
    }

    public void setSubCurrentStatus(String subCurrentStatus) {
        this.subCurrentStatus = subCurrentStatus;
    }

    public String getNewStatus() {
        return newStatus;
    }

    public void setNewStatus(String newStatus) {
        this.newStatus = newStatus;
    }

    public String getSubNewStatus() {
        return subNewStatus;
    }

    public void setSubNewStatus(String subNewStatus) {
        this.subNewStatus = subNewStatus;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDateApproved() {
        return dateApproved;
    }

    public void setDateApproved(String dateApproved) {
        this.dateApproved = dateApproved;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

}
