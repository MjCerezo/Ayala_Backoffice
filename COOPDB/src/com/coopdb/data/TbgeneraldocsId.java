
package com.coopdb.data;

import java.io.Serializable;


/**
 *  COOPDB.TbgeneraldocsId
 *  08/04/2024 12:54:44
 * 
 */
public class TbgeneraldocsId
    implements Serializable
{

    private String doccategory;
    private String doctype;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbgeneraldocsId)) {
            return false;
        }
        TbgeneraldocsId other = ((TbgeneraldocsId) o);
        if (this.doccategory == null) {
            if (other.doccategory!= null) {
                return false;
            }
        } else {
            if (!this.doccategory.equals(other.doccategory)) {
                return false;
            }
        }
        if (this.doctype == null) {
            if (other.doctype!= null) {
                return false;
            }
        } else {
            if (!this.doctype.equals(other.doctype)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.doccategory!= null) {
            rtn = (rtn + this.doccategory.hashCode());
        }
        rtn = (rtn* 37);
        if (this.doctype!= null) {
            rtn = (rtn + this.doctype.hashCode());
        }
        return rtn;
    }

    public String getDoccategory() {
        return doccategory;
    }

    public void setDoccategory(String doccategory) {
        this.doccategory = doccategory;
    }

    public String getDoctype() {
        return doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

}
