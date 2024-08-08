
package com.loansdb.data;

import java.io.Serializable;
import java.util.Date;


/**
 *  LOANSDB.TbcustomercllId
 *  08/31/2018 16:39:51
 * 
 */
public class TbcustomercllId
    implements Serializable
{

    private String cifid;
    private byte[] cifname;
    private String refid;
    private String type;
    private String relationshipgroup;
    private String relationshipcode;
    private String cllrelationshiptype;
    private String createdby;
    private Date datecreated;
    private String updatedby;
    private Date dateupdated;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbcustomercllId)) {
            return false;
        }
        TbcustomercllId other = ((TbcustomercllId) o);
        if (this.cifid == null) {
            if (other.cifid!= null) {
                return false;
            }
        } else {
            if (!this.cifid.equals(other.cifid)) {
                return false;
            }
        }
        if (this.cifname == null) {
            if (other.cifname!= null) {
                return false;
            }
        } else {
            if (!this.cifname.equals(other.cifname)) {
                return false;
            }
        }
        if (this.refid == null) {
            if (other.refid!= null) {
                return false;
            }
        } else {
            if (!this.refid.equals(other.refid)) {
                return false;
            }
        }
        if (this.type == null) {
            if (other.type!= null) {
                return false;
            }
        } else {
            if (!this.type.equals(other.type)) {
                return false;
            }
        }
        if (this.relationshipgroup == null) {
            if (other.relationshipgroup!= null) {
                return false;
            }
        } else {
            if (!this.relationshipgroup.equals(other.relationshipgroup)) {
                return false;
            }
        }
        if (this.relationshipcode == null) {
            if (other.relationshipcode!= null) {
                return false;
            }
        } else {
            if (!this.relationshipcode.equals(other.relationshipcode)) {
                return false;
            }
        }
        if (this.cllrelationshiptype == null) {
            if (other.cllrelationshiptype!= null) {
                return false;
            }
        } else {
            if (!this.cllrelationshiptype.equals(other.cllrelationshiptype)) {
                return false;
            }
        }
        if (this.createdby == null) {
            if (other.createdby!= null) {
                return false;
            }
        } else {
            if (!this.createdby.equals(other.createdby)) {
                return false;
            }
        }
        if (this.datecreated == null) {
            if (other.datecreated!= null) {
                return false;
            }
        } else {
            if (!this.datecreated.equals(other.datecreated)) {
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
        if (this.dateupdated == null) {
            if (other.dateupdated!= null) {
                return false;
            }
        } else {
            if (!this.dateupdated.equals(other.dateupdated)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.cifid!= null) {
            rtn = (rtn + this.cifid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cifname!= null) {
            rtn = (rtn + this.cifname.hashCode());
        }
        rtn = (rtn* 37);
        if (this.refid!= null) {
            rtn = (rtn + this.refid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.type!= null) {
            rtn = (rtn + this.type.hashCode());
        }
        rtn = (rtn* 37);
        if (this.relationshipgroup!= null) {
            rtn = (rtn + this.relationshipgroup.hashCode());
        }
        rtn = (rtn* 37);
        if (this.relationshipcode!= null) {
            rtn = (rtn + this.relationshipcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cllrelationshiptype!= null) {
            rtn = (rtn + this.cllrelationshiptype.hashCode());
        }
        rtn = (rtn* 37);
        if (this.createdby!= null) {
            rtn = (rtn + this.createdby.hashCode());
        }
        rtn = (rtn* 37);
        if (this.datecreated!= null) {
            rtn = (rtn + this.datecreated.hashCode());
        }
        rtn = (rtn* 37);
        if (this.updatedby!= null) {
            rtn = (rtn + this.updatedby.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dateupdated!= null) {
            rtn = (rtn + this.dateupdated.hashCode());
        }
        return rtn;
    }

    public String getCifid() {
        return cifid;
    }

    public void setCifid(String cifid) {
        this.cifid = cifid;
    }

    public byte[] getCifname() {
        return cifname;
    }

    public void setCifname(byte[] cifname) {
        this.cifname = cifname;
    }

    public String getRefid() {
        return refid;
    }

    public void setRefid(String refid) {
        this.refid = refid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelationshipgroup() {
        return relationshipgroup;
    }

    public void setRelationshipgroup(String relationshipgroup) {
        this.relationshipgroup = relationshipgroup;
    }

    public String getRelationshipcode() {
        return relationshipcode;
    }

    public void setRelationshipcode(String relationshipcode) {
        this.relationshipcode = relationshipcode;
    }

    public String getCllrelationshiptype() {
        return cllrelationshiptype;
    }

    public void setCllrelationshiptype(String cllrelationshiptype) {
        this.cllrelationshiptype = cllrelationshiptype;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public Date getDateupdated() {
        return dateupdated;
    }

    public void setDateupdated(Date dateupdated) {
        this.dateupdated = dateupdated;
    }

}
