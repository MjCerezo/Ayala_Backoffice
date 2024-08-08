
package com.loansdb.data;

import java.io.Serializable;


/**
 *  LOANSDB.TbdocprintingpercfId
 *  10/13/2020 10:21:35
 * 
 */
public class TbdocprintingpercfId
    implements Serializable
{

    private String facilitycode;
    private String documentcode;
    private String doccategory;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbdocprintingpercfId)) {
            return false;
        }
        TbdocprintingpercfId other = ((TbdocprintingpercfId) o);
        if (this.facilitycode == null) {
            if (other.facilitycode!= null) {
                return false;
            }
        } else {
            if (!this.facilitycode.equals(other.facilitycode)) {
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
        if (this.doccategory == null) {
            if (other.doccategory!= null) {
                return false;
            }
        } else {
            if (!this.doccategory.equals(other.doccategory)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.facilitycode!= null) {
            rtn = (rtn + this.facilitycode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.documentcode!= null) {
            rtn = (rtn + this.documentcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.doccategory!= null) {
            rtn = (rtn + this.doccategory.hashCode());
        }
        return rtn;
    }

    public String getFacilitycode() {
        return facilitycode;
    }

    public void setFacilitycode(String facilitycode) {
        this.facilitycode = facilitycode;
    }

    public String getDocumentcode() {
        return documentcode;
    }

    public void setDocumentcode(String documentcode) {
        this.documentcode = documentcode;
    }

    public String getDoccategory() {
        return doccategory;
    }

    public void setDoccategory(String doccategory) {
        this.doccategory = doccategory;
    }

}
