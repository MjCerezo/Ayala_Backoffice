
package com.loansdb.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.TbincomeexpenseId
 *  01/03/2019 15:48:39
 * 
 */
public class TbincomeexpenseId
    implements Serializable
{

    private Integer id;
    private String appno;
    private Integer evalreportid;
    private String cifno;
    private String type;
    private String particulars;
    private BigDecimal amount;
    private Date datefrom;
    private Date dateto;
    private Date datecreated;
    private String createdby;
    private Date dateupdated;
    private String updatedby;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbincomeexpenseId)) {
            return false;
        }
        TbincomeexpenseId other = ((TbincomeexpenseId) o);
        if (this.id == null) {
            if (other.id!= null) {
                return false;
            }
        } else {
            if (!this.id.equals(other.id)) {
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
        if (this.evalreportid == null) {
            if (other.evalreportid!= null) {
                return false;
            }
        } else {
            if (!this.evalreportid.equals(other.evalreportid)) {
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
        if (this.type == null) {
            if (other.type!= null) {
                return false;
            }
        } else {
            if (!this.type.equals(other.type)) {
                return false;
            }
        }
        if (this.particulars == null) {
            if (other.particulars!= null) {
                return false;
            }
        } else {
            if (!this.particulars.equals(other.particulars)) {
                return false;
            }
        }
        if (this.amount == null) {
            if (other.amount!= null) {
                return false;
            }
        } else {
            if (!this.amount.equals(other.amount)) {
                return false;
            }
        }
        if (this.datefrom == null) {
            if (other.datefrom!= null) {
                return false;
            }
        } else {
            if (!this.datefrom.equals(other.datefrom)) {
                return false;
            }
        }
        if (this.dateto == null) {
            if (other.dateto!= null) {
                return false;
            }
        } else {
            if (!this.dateto.equals(other.dateto)) {
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
        if (this.createdby == null) {
            if (other.createdby!= null) {
                return false;
            }
        } else {
            if (!this.createdby.equals(other.createdby)) {
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
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.id!= null) {
            rtn = (rtn + this.id.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appno!= null) {
            rtn = (rtn + this.appno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.evalreportid!= null) {
            rtn = (rtn + this.evalreportid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cifno!= null) {
            rtn = (rtn + this.cifno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.type!= null) {
            rtn = (rtn + this.type.hashCode());
        }
        rtn = (rtn* 37);
        if (this.particulars!= null) {
            rtn = (rtn + this.particulars.hashCode());
        }
        rtn = (rtn* 37);
        if (this.amount!= null) {
            rtn = (rtn + this.amount.hashCode());
        }
        rtn = (rtn* 37);
        if (this.datefrom!= null) {
            rtn = (rtn + this.datefrom.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dateto!= null) {
            rtn = (rtn + this.dateto.hashCode());
        }
        rtn = (rtn* 37);
        if (this.datecreated!= null) {
            rtn = (rtn + this.datecreated.hashCode());
        }
        rtn = (rtn* 37);
        if (this.createdby!= null) {
            rtn = (rtn + this.createdby.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dateupdated!= null) {
            rtn = (rtn + this.dateupdated.hashCode());
        }
        rtn = (rtn* 37);
        if (this.updatedby!= null) {
            rtn = (rtn + this.updatedby.hashCode());
        }
        return rtn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public Integer getEvalreportid() {
        return evalreportid;
    }

    public void setEvalreportid(Integer evalreportid) {
        this.evalreportid = evalreportid;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDatefrom() {
        return datefrom;
    }

    public void setDatefrom(Date datefrom) {
        this.datefrom = datefrom;
    }

    public Date getDateto() {
        return dateto;
    }

    public void setDateto(Date dateto) {
        this.dateto = dateto;
    }

    public Date getDatecreated() {
        return datecreated;
    }

    public void setDatecreated(Date datecreated) {
        this.datecreated = datecreated;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
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

}
