
package com.loansdb.data;

import java.io.Serializable;
import java.util.Date;


/**
 *  LOANSDB.TbfsmainId
 *  10/13/2020 10:21:35
 * 
 */
public class TbfsmainId
    implements Serializable
{

    private String cifno;
    private String fstype;
    private Date fsdate;
    private Boolean industryflag;
    private String industryname;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbfsmainId)) {
            return false;
        }
        TbfsmainId other = ((TbfsmainId) o);
        if (this.cifno == null) {
            if (other.cifno!= null) {
                return false;
            }
        } else {
            if (!this.cifno.equals(other.cifno)) {
                return false;
            }
        }
        if (this.fstype == null) {
            if (other.fstype!= null) {
                return false;
            }
        } else {
            if (!this.fstype.equals(other.fstype)) {
                return false;
            }
        }
        if (this.fsdate == null) {
            if (other.fsdate!= null) {
                return false;
            }
        } else {
            if (!this.fsdate.equals(other.fsdate)) {
                return false;
            }
        }
        if (this.industryflag == null) {
            if (other.industryflag!= null) {
                return false;
            }
        } else {
            if (!this.industryflag.equals(other.industryflag)) {
                return false;
            }
        }
        if (this.industryname == null) {
            if (other.industryname!= null) {
                return false;
            }
        } else {
            if (!this.industryname.equals(other.industryname)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.cifno!= null) {
            rtn = (rtn + this.cifno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fstype!= null) {
            rtn = (rtn + this.fstype.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fsdate!= null) {
            rtn = (rtn + this.fsdate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.industryflag!= null) {
            rtn = (rtn + this.industryflag.hashCode());
        }
        rtn = (rtn* 37);
        if (this.industryname!= null) {
            rtn = (rtn + this.industryname.hashCode());
        }
        return rtn;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getFstype() {
        return fstype;
    }

    public void setFstype(String fstype) {
        this.fstype = fstype;
    }

    public Date getFsdate() {
        return fsdate;
    }

    public void setFsdate(Date fsdate) {
        this.fsdate = fsdate;
    }

    public Boolean getIndustryflag() {
        return industryflag;
    }

    public void setIndustryflag(Boolean industryflag) {
        this.industryflag = industryflag;
    }

    public String getIndustryname() {
        return industryname;
    }

    public void setIndustryname(String industryname) {
        this.industryname = industryname;
    }

}
