
package com.coopdb.data;

import java.io.Serializable;
import java.util.Date;


/**
 *  COOPDB.TbdocchecklistId
 *  03/07/2018 15:09:44
 * 
 */
public class TbdocchecklistId
    implements Serializable
{

    private Integer id;
    private String documentcode;
    private String dmsid;
    private String membershipappid;
    private String membershipid;
    private String documentname;
    private String txcode;
    private String appno;
    private String cifno;
    private String docstatus;
    private Date dateuploaded;
    private String uploadedby;
    private Boolean issubmitted;
    private Date datesubmitted;
    private Boolean isuploaded;
    private String remarks;
    private Boolean isrequired;
    private Boolean isrequestwaiver;
    private Date dateapproved;
    private Boolean ispoa;
    private String reqtype;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbdocchecklistId)) {
            return false;
        }
        TbdocchecklistId other = ((TbdocchecklistId) o);
        if (this.id == null) {
            if (other.id!= null) {
                return false;
            }
        } else {
            if (!this.id.equals(other.id)) {
                return false;
            }
        }
        if (this.documentcode == null) {
            if (other.documentcode!= null) {
                return false;
            }
        } else {
            if (!this.documentcode.equals(other.documentcode)) {
                return false;
            }
        }
        if (this.dmsid == null) {
            if (other.dmsid!= null) {
                return false;
            }
        } else {
            if (!this.dmsid.equals(other.dmsid)) {
                return false;
            }
        }
        if (this.membershipappid == null) {
            if (other.membershipappid!= null) {
                return false;
            }
        } else {
            if (!this.membershipappid.equals(other.membershipappid)) {
                return false;
            }
        }
        if (this.membershipid == null) {
            if (other.membershipid!= null) {
                return false;
            }
        } else {
            if (!this.membershipid.equals(other.membershipid)) {
                return false;
            }
        }
        if (this.documentname == null) {
            if (other.documentname!= null) {
                return false;
            }
        } else {
            if (!this.documentname.equals(other.documentname)) {
                return false;
            }
        }
        if (this.txcode == null) {
            if (other.txcode!= null) {
                return false;
            }
        } else {
            if (!this.txcode.equals(other.txcode)) {
                return false;
            }
        }
        if (this.appno == null) {
            if (other.appno!= null) {
                return false;
            }
        } else {
            if (!this.appno.equals(other.appno)) {
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
        if (this.docstatus == null) {
            if (other.docstatus!= null) {
                return false;
            }
        } else {
            if (!this.docstatus.equals(other.docstatus)) {
                return false;
            }
        }
        if (this.dateuploaded == null) {
            if (other.dateuploaded!= null) {
                return false;
            }
        } else {
            if (!this.dateuploaded.equals(other.dateuploaded)) {
                return false;
            }
        }
        if (this.uploadedby == null) {
            if (other.uploadedby!= null) {
                return false;
            }
        } else {
            if (!this.uploadedby.equals(other.uploadedby)) {
                return false;
            }
        }
        if (this.issubmitted == null) {
            if (other.issubmitted!= null) {
                return false;
            }
        } else {
            if (!this.issubmitted.equals(other.issubmitted)) {
                return false;
            }
        }
        if (this.datesubmitted == null) {
            if (other.datesubmitted!= null) {
                return false;
            }
        } else {
            if (!this.datesubmitted.equals(other.datesubmitted)) {
                return false;
            }
        }
        if (this.isuploaded == null) {
            if (other.isuploaded!= null) {
                return false;
            }
        } else {
            if (!this.isuploaded.equals(other.isuploaded)) {
                return false;
            }
        }
        if (this.remarks == null) {
            if (other.remarks!= null) {
                return false;
            }
        } else {
            if (!this.remarks.equals(other.remarks)) {
                return false;
            }
        }
        if (this.isrequired == null) {
            if (other.isrequired!= null) {
                return false;
            }
        } else {
            if (!this.isrequired.equals(other.isrequired)) {
                return false;
            }
        }
        if (this.isrequestwaiver == null) {
            if (other.isrequestwaiver!= null) {
                return false;
            }
        } else {
            if (!this.isrequestwaiver.equals(other.isrequestwaiver)) {
                return false;
            }
        }
        if (this.dateapproved == null) {
            if (other.dateapproved!= null) {
                return false;
            }
        } else {
            if (!this.dateapproved.equals(other.dateapproved)) {
                return false;
            }
        }
        if (this.ispoa == null) {
            if (other.ispoa!= null) {
                return false;
            }
        } else {
            if (!this.ispoa.equals(other.ispoa)) {
                return false;
            }
        }
        if (this.reqtype == null) {
            if (other.reqtype!= null) {
                return false;
            }
        } else {
            if (!this.reqtype.equals(other.reqtype)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.id!= null) {
            rtn = (rtn + this.id.hashCode());
        }
        rtn = (rtn* 37);
        if (this.documentcode!= null) {
            rtn = (rtn + this.documentcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dmsid!= null) {
            rtn = (rtn + this.dmsid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.membershipappid!= null) {
            rtn = (rtn + this.membershipappid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.membershipid!= null) {
            rtn = (rtn + this.membershipid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.documentname!= null) {
            rtn = (rtn + this.documentname.hashCode());
        }
        rtn = (rtn* 37);
        if (this.txcode!= null) {
            rtn = (rtn + this.txcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appno!= null) {
            rtn = (rtn + this.appno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cifno!= null) {
            rtn = (rtn + this.cifno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.docstatus!= null) {
            rtn = (rtn + this.docstatus.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dateuploaded!= null) {
            rtn = (rtn + this.dateuploaded.hashCode());
        }
        rtn = (rtn* 37);
        if (this.uploadedby!= null) {
            rtn = (rtn + this.uploadedby.hashCode());
        }
        rtn = (rtn* 37);
        if (this.issubmitted!= null) {
            rtn = (rtn + this.issubmitted.hashCode());
        }
        rtn = (rtn* 37);
        if (this.datesubmitted!= null) {
            rtn = (rtn + this.datesubmitted.hashCode());
        }
        rtn = (rtn* 37);
        if (this.isuploaded!= null) {
            rtn = (rtn + this.isuploaded.hashCode());
        }
        rtn = (rtn* 37);
        if (this.remarks!= null) {
            rtn = (rtn + this.remarks.hashCode());
        }
        rtn = (rtn* 37);
        if (this.isrequired!= null) {
            rtn = (rtn + this.isrequired.hashCode());
        }
        rtn = (rtn* 37);
        if (this.isrequestwaiver!= null) {
            rtn = (rtn + this.isrequestwaiver.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dateapproved!= null) {
            rtn = (rtn + this.dateapproved.hashCode());
        }
        rtn = (rtn* 37);
        if (this.ispoa!= null) {
            rtn = (rtn + this.ispoa.hashCode());
        }
        rtn = (rtn* 37);
        if (this.reqtype!= null) {
            rtn = (rtn + this.reqtype.hashCode());
        }
        return rtn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDocumentcode() {
        return documentcode;
    }

    public void setDocumentcode(String documentcode) {
        this.documentcode = documentcode;
    }

    public String getDmsid() {
        return dmsid;
    }

    public void setDmsid(String dmsid) {
        this.dmsid = dmsid;
    }

    public String getMembershipappid() {
        return membershipappid;
    }

    public void setMembershipappid(String membershipappid) {
        this.membershipappid = membershipappid;
    }

    public String getMembershipid() {
        return membershipid;
    }

    public void setMembershipid(String membershipid) {
        this.membershipid = membershipid;
    }

    public String getDocumentname() {
        return documentname;
    }

    public void setDocumentname(String documentname) {
        this.documentname = documentname;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getDocstatus() {
        return docstatus;
    }

    public void setDocstatus(String docstatus) {
        this.docstatus = docstatus;
    }

    public Date getDateuploaded() {
        return dateuploaded;
    }

    public void setDateuploaded(Date dateuploaded) {
        this.dateuploaded = dateuploaded;
    }

    public String getUploadedby() {
        return uploadedby;
    }

    public void setUploadedby(String uploadedby) {
        this.uploadedby = uploadedby;
    }

    public Boolean getIssubmitted() {
        return issubmitted;
    }

    public void setIssubmitted(Boolean issubmitted) {
        this.issubmitted = issubmitted;
    }

    public Date getDatesubmitted() {
        return datesubmitted;
    }

    public void setDatesubmitted(Date datesubmitted) {
        this.datesubmitted = datesubmitted;
    }

    public Boolean getIsuploaded() {
        return isuploaded;
    }

    public void setIsuploaded(Boolean isuploaded) {
        this.isuploaded = isuploaded;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Boolean getIsrequired() {
        return isrequired;
    }

    public void setIsrequired(Boolean isrequired) {
        this.isrequired = isrequired;
    }

    public Boolean getIsrequestwaiver() {
        return isrequestwaiver;
    }

    public void setIsrequestwaiver(Boolean isrequestwaiver) {
        this.isrequestwaiver = isrequestwaiver;
    }

    public Date getDateapproved() {
        return dateapproved;
    }

    public void setDateapproved(Date dateapproved) {
        this.dateapproved = dateapproved;
    }

    public Boolean getIspoa() {
        return ispoa;
    }

    public void setIspoa(Boolean ispoa) {
        this.ispoa = ispoa;
    }

    public String getReqtype() {
        return reqtype;
    }

    public void setReqtype(String reqtype) {
        this.reqtype = reqtype;
    }

}
