
package com.coopdb.data;

import java.io.Serializable;
import java.util.Date;


/**
 *  COOPDB.TbprofilechangehistoryId
 *  01/11/2019 10:40:45
 * 
 */
public class TbprofilechangehistoryId
    implements Serializable
{

    private Integer id;
    private String membershipid;
    private String changecategory;
    private Date dateupdated;
    private String updatedby;
    private String source;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbprofilechangehistoryId)) {
            return false;
        }
        TbprofilechangehistoryId other = ((TbprofilechangehistoryId) o);
        if (this.id == null) {
            if (other.id!= null) {
                return false;
            }
        } else {
            if (!this.id.equals(other.id)) {
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
        if (this.changecategory == null) {
            if (other.changecategory!= null) {
                return false;
            }
        } else {
            if (!this.changecategory.equals(other.changecategory)) {
                return false;
            }
        }
        if (this.dateupdated == null) {
            if (other.dateupdated!= null) {
                return false;
            }
        } else {
            if (!this.dateupdated.equals(other.dateupdated)) {
                return false;
            }
        }
        if (this.updatedby == null) {
            if (other.updatedby!= null) {
                return false;
            }
        } else {
            if (!this.updatedby.equals(other.updatedby)) {
                return false;
            }
        }
        if (this.source == null) {
            if (other.source!= null) {
                return false;
            }
        } else {
            if (!this.source.equals(other.source)) {
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
        if (this.membershipid!= null) {
            rtn = (rtn + this.membershipid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.changecategory!= null) {
            rtn = (rtn + this.changecategory.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dateupdated!= null) {
            rtn = (rtn + this.dateupdated.hashCode());
        }
        rtn = (rtn* 37);
        if (this.updatedby!= null) {
            rtn = (rtn + this.updatedby.hashCode());
        }
        rtn = (rtn* 37);
        if (this.source!= null) {
            rtn = (rtn + this.source.hashCode());
        }
        return rtn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMembershipid() {
        return membershipid;
    }

    public void setMembershipid(String membershipid) {
        this.membershipid = membershipid;
    }

    public String getChangecategory() {
        return changecategory;
    }

    public void setChangecategory(String changecategory) {
        this.changecategory = changecategory;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
