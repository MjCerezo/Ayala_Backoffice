
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbdeskcidetailsId
 *  01/11/2018 15:14:19
 * 
 */
public class TbdeskcidetailsId
    implements Serializable
{

    private String cireportid;
    private String category;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbdeskcidetailsId)) {
            return false;
        }
        TbdeskcidetailsId other = ((TbdeskcidetailsId) o);
        if (this.cireportid == null) {
            if (other.cireportid!= null) {
                return false;
            }
        } else {
            if (!this.cireportid.equals(other.cireportid)) {
                return false;
            }
        }
        if (this.category == null) {
            if (other.category!= null) {
                return false;
            }
        } else {
            if (!this.category.equals(other.category)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.cireportid!= null) {
            rtn = (rtn + this.cireportid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.category!= null) {
            rtn = (rtn + this.category.hashCode());
        }
        return rtn;
    }

    public String getCireportid() {
        return cireportid;
    }

    public void setCireportid(String cireportid) {
        this.cireportid = cireportid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

}
