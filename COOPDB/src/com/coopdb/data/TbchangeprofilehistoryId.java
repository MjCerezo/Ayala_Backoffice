
package com.coopdb.data;

import java.io.Serializable;
import java.util.Date;


/**
 *  COOPDB.TbchangeprofilehistoryId
 *  10/31/2018 11:23:37
 * 
 */
public class TbchangeprofilehistoryId
    implements Serializable
{

    private Integer id;
    private String memberid;
    private String changecategory;
    private Date dateupdated;
    private String updatedby;
    private String source;
    private Integer updatecount;
    private String remarks;
    private String streetnoname1;
    private String subdivision1;
    private String barangay1;
    private String stateprovince1;
    private String city1;
    private String region1;
    private String country1;
    private String postalcode1;
    private Date occupiedsince1;
    private String ownershiptype1;
    private Boolean sameaspermanentaddress;
    private String streetnoname2;
    private String subdivision2;
    private String barangay2;
    private String stateprovince2;
    private String city2;
    private String region2;
    private String country2;
    private String postalcode2;
    private Date occupiedsince2;
    private String ownershiptype2;
    private String homephoneareacode;
    private String homephoneno;
    private String mobilephoneareacode;
    private String mobilephoneno;
    private String officephoneareacode;
    private String officephone;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbchangeprofilehistoryId)) {
            return false;
        }
        TbchangeprofilehistoryId other = ((TbchangeprofilehistoryId) o);
        if (this.id == null) {
            if (other.id!= null) {
                return false;
            }
        } else {
            if (!this.id.equals(other.id)) {
                return false;
            }
        }
        if (this.memberid == null) {
            if (other.memberid!= null) {
                return false;
            }
        } else {
            if (!this.memberid.equals(other.memberid)) {
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
        if (this.updatecount == null) {
            if (other.updatecount!= null) {
                return false;
            }
        } else {
            if (!this.updatecount.equals(other.updatecount)) {
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
        if (this.streetnoname1 == null) {
            if (other.streetnoname1 != null) {
                return false;
            }
        } else {
            if (!this.streetnoname1 .equals(other.streetnoname1)) {
                return false;
            }
        }
        if (this.subdivision1 == null) {
            if (other.subdivision1 != null) {
                return false;
            }
        } else {
            if (!this.subdivision1 .equals(other.subdivision1)) {
                return false;
            }
        }
        if (this.barangay1 == null) {
            if (other.barangay1 != null) {
                return false;
            }
        } else {
            if (!this.barangay1 .equals(other.barangay1)) {
                return false;
            }
        }
        if (this.stateprovince1 == null) {
            if (other.stateprovince1 != null) {
                return false;
            }
        } else {
            if (!this.stateprovince1 .equals(other.stateprovince1)) {
                return false;
            }
        }
        if (this.city1 == null) {
            if (other.city1 != null) {
                return false;
            }
        } else {
            if (!this.city1 .equals(other.city1)) {
                return false;
            }
        }
        if (this.region1 == null) {
            if (other.region1 != null) {
                return false;
            }
        } else {
            if (!this.region1 .equals(other.region1)) {
                return false;
            }
        }
        if (this.country1 == null) {
            if (other.country1 != null) {
                return false;
            }
        } else {
            if (!this.country1 .equals(other.country1)) {
                return false;
            }
        }
        if (this.postalcode1 == null) {
            if (other.postalcode1 != null) {
                return false;
            }
        } else {
            if (!this.postalcode1 .equals(other.postalcode1)) {
                return false;
            }
        }
        if (this.occupiedsince1 == null) {
            if (other.occupiedsince1 != null) {
                return false;
            }
        } else {
            if (!this.occupiedsince1 .equals(other.occupiedsince1)) {
                return false;
            }
        }
        if (this.ownershiptype1 == null) {
            if (other.ownershiptype1 != null) {
                return false;
            }
        } else {
            if (!this.ownershiptype1 .equals(other.ownershiptype1)) {
                return false;
            }
        }
        if (this.sameaspermanentaddress == null) {
            if (other.sameaspermanentaddress!= null) {
                return false;
            }
        } else {
            if (!this.sameaspermanentaddress.equals(other.sameaspermanentaddress)) {
                return false;
            }
        }
        if (this.streetnoname2 == null) {
            if (other.streetnoname2 != null) {
                return false;
            }
        } else {
            if (!this.streetnoname2 .equals(other.streetnoname2)) {
                return false;
            }
        }
        if (this.subdivision2 == null) {
            if (other.subdivision2 != null) {
                return false;
            }
        } else {
            if (!this.subdivision2 .equals(other.subdivision2)) {
                return false;
            }
        }
        if (this.barangay2 == null) {
            if (other.barangay2 != null) {
                return false;
            }
        } else {
            if (!this.barangay2 .equals(other.barangay2)) {
                return false;
            }
        }
        if (this.stateprovince2 == null) {
            if (other.stateprovince2 != null) {
                return false;
            }
        } else {
            if (!this.stateprovince2 .equals(other.stateprovince2)) {
                return false;
            }
        }
        if (this.city2 == null) {
            if (other.city2 != null) {
                return false;
            }
        } else {
            if (!this.city2 .equals(other.city2)) {
                return false;
            }
        }
        if (this.region2 == null) {
            if (other.region2 != null) {
                return false;
            }
        } else {
            if (!this.region2 .equals(other.region2)) {
                return false;
            }
        }
        if (this.country2 == null) {
            if (other.country2 != null) {
                return false;
            }
        } else {
            if (!this.country2 .equals(other.country2)) {
                return false;
            }
        }
        if (this.postalcode2 == null) {
            if (other.postalcode2 != null) {
                return false;
            }
        } else {
            if (!this.postalcode2 .equals(other.postalcode2)) {
                return false;
            }
        }
        if (this.occupiedsince2 == null) {
            if (other.occupiedsince2 != null) {
                return false;
            }
        } else {
            if (!this.occupiedsince2 .equals(other.occupiedsince2)) {
                return false;
            }
        }
        if (this.ownershiptype2 == null) {
            if (other.ownershiptype2 != null) {
                return false;
            }
        } else {
            if (!this.ownershiptype2 .equals(other.ownershiptype2)) {
                return false;
            }
        }
        if (this.homephoneareacode == null) {
            if (other.homephoneareacode!= null) {
                return false;
            }
        } else {
            if (!this.homephoneareacode.equals(other.homephoneareacode)) {
                return false;
            }
        }
        if (this.homephoneno == null) {
            if (other.homephoneno!= null) {
                return false;
            }
        } else {
            if (!this.homephoneno.equals(other.homephoneno)) {
                return false;
            }
        }
        if (this.mobilephoneareacode == null) {
            if (other.mobilephoneareacode!= null) {
                return false;
            }
        } else {
            if (!this.mobilephoneareacode.equals(other.mobilephoneareacode)) {
                return false;
            }
        }
        if (this.mobilephoneno == null) {
            if (other.mobilephoneno!= null) {
                return false;
            }
        } else {
            if (!this.mobilephoneno.equals(other.mobilephoneno)) {
                return false;
            }
        }
        if (this.officephoneareacode == null) {
            if (other.officephoneareacode!= null) {
                return false;
            }
        } else {
            if (!this.officephoneareacode.equals(other.officephoneareacode)) {
                return false;
            }
        }
        if (this.officephone == null) {
            if (other.officephone!= null) {
                return false;
            }
        } else {
            if (!this.officephone.equals(other.officephone)) {
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
        if (this.memberid!= null) {
            rtn = (rtn + this.memberid.hashCode());
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
        rtn = (rtn* 37);
        if (this.updatecount!= null) {
            rtn = (rtn + this.updatecount.hashCode());
        }
        rtn = (rtn* 37);
        if (this.remarks!= null) {
            rtn = (rtn + this.remarks.hashCode());
        }
        rtn = (rtn* 37);
        if (this.streetnoname1 != null) {
            rtn = (rtn + this.streetnoname1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.subdivision1 != null) {
            rtn = (rtn + this.subdivision1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.barangay1 != null) {
            rtn = (rtn + this.barangay1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.stateprovince1 != null) {
            rtn = (rtn + this.stateprovince1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.city1 != null) {
            rtn = (rtn + this.city1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.region1 != null) {
            rtn = (rtn + this.region1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.country1 != null) {
            rtn = (rtn + this.country1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.postalcode1 != null) {
            rtn = (rtn + this.postalcode1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.occupiedsince1 != null) {
            rtn = (rtn + this.occupiedsince1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.ownershiptype1 != null) {
            rtn = (rtn + this.ownershiptype1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.sameaspermanentaddress!= null) {
            rtn = (rtn + this.sameaspermanentaddress.hashCode());
        }
        rtn = (rtn* 37);
        if (this.streetnoname2 != null) {
            rtn = (rtn + this.streetnoname2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.subdivision2 != null) {
            rtn = (rtn + this.subdivision2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.barangay2 != null) {
            rtn = (rtn + this.barangay2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.stateprovince2 != null) {
            rtn = (rtn + this.stateprovince2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.city2 != null) {
            rtn = (rtn + this.city2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.region2 != null) {
            rtn = (rtn + this.region2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.country2 != null) {
            rtn = (rtn + this.country2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.postalcode2 != null) {
            rtn = (rtn + this.postalcode2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.occupiedsince2 != null) {
            rtn = (rtn + this.occupiedsince2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.ownershiptype2 != null) {
            rtn = (rtn + this.ownershiptype2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.homephoneareacode!= null) {
            rtn = (rtn + this.homephoneareacode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.homephoneno!= null) {
            rtn = (rtn + this.homephoneno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.mobilephoneareacode!= null) {
            rtn = (rtn + this.mobilephoneareacode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.mobilephoneno!= null) {
            rtn = (rtn + this.mobilephoneno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.officephoneareacode!= null) {
            rtn = (rtn + this.officephoneareacode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.officephone!= null) {
            rtn = (rtn + this.officephone.hashCode());
        }
        return rtn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
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

    public Integer getUpdatecount() {
        return updatecount;
    }

    public void setUpdatecount(Integer updatecount) {
        this.updatecount = updatecount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getStreetnoname1() {
        return streetnoname1;
    }

    public void setStreetnoname1(String streetnoname1) {
        this.streetnoname1 = streetnoname1;
    }

    public String getSubdivision1() {
        return subdivision1;
    }

    public void setSubdivision1(String subdivision1) {
        this.subdivision1 = subdivision1;
    }

    public String getBarangay1() {
        return barangay1;
    }

    public void setBarangay1(String barangay1) {
        this.barangay1 = barangay1;
    }

    public String getStateprovince1() {
        return stateprovince1;
    }

    public void setStateprovince1(String stateprovince1) {
        this.stateprovince1 = stateprovince1;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getRegion1() {
        return region1;
    }

    public void setRegion1(String region1) {
        this.region1 = region1;
    }

    public String getCountry1() {
        return country1;
    }

    public void setCountry1(String country1) {
        this.country1 = country1;
    }

    public String getPostalcode1() {
        return postalcode1;
    }

    public void setPostalcode1(String postalcode1) {
        this.postalcode1 = postalcode1;
    }

    public Date getOccupiedsince1() {
        return occupiedsince1;
    }

    public void setOccupiedsince1(Date occupiedsince1) {
        this.occupiedsince1 = occupiedsince1;
    }

    public String getOwnershiptype1() {
        return ownershiptype1;
    }

    public void setOwnershiptype1(String ownershiptype1) {
        this.ownershiptype1 = ownershiptype1;
    }

    public Boolean getSameaspermanentaddress() {
        return sameaspermanentaddress;
    }

    public void setSameaspermanentaddress(Boolean sameaspermanentaddress) {
        this.sameaspermanentaddress = sameaspermanentaddress;
    }

    public String getStreetnoname2() {
        return streetnoname2;
    }

    public void setStreetnoname2(String streetnoname2) {
        this.streetnoname2 = streetnoname2;
    }

    public String getSubdivision2() {
        return subdivision2;
    }

    public void setSubdivision2(String subdivision2) {
        this.subdivision2 = subdivision2;
    }

    public String getBarangay2() {
        return barangay2;
    }

    public void setBarangay2(String barangay2) {
        this.barangay2 = barangay2;
    }

    public String getStateprovince2() {
        return stateprovince2;
    }

    public void setStateprovince2(String stateprovince2) {
        this.stateprovince2 = stateprovince2;
    }

    public String getCity2() {
        return city2;
    }

    public void setCity2(String city2) {
        this.city2 = city2;
    }

    public String getRegion2() {
        return region2;
    }

    public void setRegion2(String region2) {
        this.region2 = region2;
    }

    public String getCountry2() {
        return country2;
    }

    public void setCountry2(String country2) {
        this.country2 = country2;
    }

    public String getPostalcode2() {
        return postalcode2;
    }

    public void setPostalcode2(String postalcode2) {
        this.postalcode2 = postalcode2;
    }

    public Date getOccupiedsince2() {
        return occupiedsince2;
    }

    public void setOccupiedsince2(Date occupiedsince2) {
        this.occupiedsince2 = occupiedsince2;
    }

    public String getOwnershiptype2() {
        return ownershiptype2;
    }

    public void setOwnershiptype2(String ownershiptype2) {
        this.ownershiptype2 = ownershiptype2;
    }

    public String getHomephoneareacode() {
        return homephoneareacode;
    }

    public void setHomephoneareacode(String homephoneareacode) {
        this.homephoneareacode = homephoneareacode;
    }

    public String getHomephoneno() {
        return homephoneno;
    }

    public void setHomephoneno(String homephoneno) {
        this.homephoneno = homephoneno;
    }

    public String getMobilephoneareacode() {
        return mobilephoneareacode;
    }

    public void setMobilephoneareacode(String mobilephoneareacode) {
        this.mobilephoneareacode = mobilephoneareacode;
    }

    public String getMobilephoneno() {
        return mobilephoneno;
    }

    public void setMobilephoneno(String mobilephoneno) {
        this.mobilephoneno = mobilephoneno;
    }

    public String getOfficephoneareacode() {
        return officephoneareacode;
    }

    public void setOfficephoneareacode(String officephoneareacode) {
        this.officephoneareacode = officephoneareacode;
    }

    public String getOfficephone() {
        return officephone;
    }

    public void setOfficephone(String officephone) {
        this.officephone = officephone;
    }

}
