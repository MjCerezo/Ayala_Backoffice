
package com.coopdb.data;

import java.io.Serializable;
import java.util.Date;


/**
 *  COOPDB.TbappemploymentId
 *  07/23/2018 11:52:20
 * 
 */
public class TbappemploymentId
    implements Serializable
{

    private Integer id;
    private String memberid;
    private String employmentstatus;
    private String employeeid;
    private String companyid;
    private String position;
    private String joblevel;
    private Date datehiredto;
    private Date datehiredfrom;
    private String yearsofemployment;
    private Date dateresign;
    private String businesstype;
    private String companyname;
    private String streetnoname;
    private String subdivision;
    private String barangay;
    private String stateprovince;
    private String city;
    private String region;
    private String country;
    private String postalcode;
    private String psiccode;
    private String psiclevel1;
    private String psiclevel2;
    private String psiclevel3;
    private String psoccode;
    private String psoclevel1;
    private String psoclevel2;
    private String psoclevel3;
    private String psoclevel4;
    private String sector;
    private String subsector;
    private String registrationnumber;
    private Date registrationdate;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbappemploymentId)) {
            return false;
        }
        TbappemploymentId other = ((TbappemploymentId) o);
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
        if (this.employmentstatus == null) {
            if (other.employmentstatus!= null) {
                return false;
            }
        } else {
            if (!this.employmentstatus.equals(other.employmentstatus)) {
                return false;
            }
        }
        if (this.employeeid == null) {
            if (other.employeeid!= null) {
                return false;
            }
        } else {
            if (!this.employeeid.equals(other.employeeid)) {
                return false;
            }
        }
        if (this.companyid == null) {
            if (other.companyid!= null) {
                return false;
            }
        } else {
            if (!this.companyid.equals(other.companyid)) {
                return false;
            }
        }
        if (this.position == null) {
            if (other.position!= null) {
                return false;
            }
        } else {
            if (!this.position.equals(other.position)) {
                return false;
            }
        }
        if (this.joblevel == null) {
            if (other.joblevel!= null) {
                return false;
            }
        } else {
            if (!this.joblevel.equals(other.joblevel)) {
                return false;
            }
        }
        if (this.datehiredto == null) {
            if (other.datehiredto!= null) {
                return false;
            }
        } else {
            if (!this.datehiredto.equals(other.datehiredto)) {
                return false;
            }
        }
        if (this.datehiredfrom == null) {
            if (other.datehiredfrom!= null) {
                return false;
            }
        } else {
            if (!this.datehiredfrom.equals(other.datehiredfrom)) {
                return false;
            }
        }
        if (this.yearsofemployment == null) {
            if (other.yearsofemployment!= null) {
                return false;
            }
        } else {
            if (!this.yearsofemployment.equals(other.yearsofemployment)) {
                return false;
            }
        }
        if (this.dateresign == null) {
            if (other.dateresign!= null) {
                return false;
            }
        } else {
            if (!this.dateresign.equals(other.dateresign)) {
                return false;
            }
        }
        if (this.businesstype == null) {
            if (other.businesstype!= null) {
                return false;
            }
        } else {
            if (!this.businesstype.equals(other.businesstype)) {
                return false;
            }
        }
        if (this.companyname == null) {
            if (other.companyname!= null) {
                return false;
            }
        } else {
            if (!this.companyname.equals(other.companyname)) {
                return false;
            }
        }
        if (this.streetnoname == null) {
            if (other.streetnoname!= null) {
                return false;
            }
        } else {
            if (!this.streetnoname.equals(other.streetnoname)) {
                return false;
            }
        }
        if (this.subdivision == null) {
            if (other.subdivision!= null) {
                return false;
            }
        } else {
            if (!this.subdivision.equals(other.subdivision)) {
                return false;
            }
        }
        if (this.barangay == null) {
            if (other.barangay!= null) {
                return false;
            }
        } else {
            if (!this.barangay.equals(other.barangay)) {
                return false;
            }
        }
        if (this.stateprovince == null) {
            if (other.stateprovince!= null) {
                return false;
            }
        } else {
            if (!this.stateprovince.equals(other.stateprovince)) {
                return false;
            }
        }
        if (this.city == null) {
            if (other.city!= null) {
                return false;
            }
        } else {
            if (!this.city.equals(other.city)) {
                return false;
            }
        }
        if (this.region == null) {
            if (other.region!= null) {
                return false;
            }
        } else {
            if (!this.region.equals(other.region)) {
                return false;
            }
        }
        if (this.country == null) {
            if (other.country!= null) {
                return false;
            }
        } else {
            if (!this.country.equals(other.country)) {
                return false;
            }
        }
        if (this.postalcode == null) {
            if (other.postalcode!= null) {
                return false;
            }
        } else {
            if (!this.postalcode.equals(other.postalcode)) {
                return false;
            }
        }
        if (this.psiccode == null) {
            if (other.psiccode!= null) {
                return false;
            }
        } else {
            if (!this.psiccode.equals(other.psiccode)) {
                return false;
            }
        }
        if (this.psiclevel1 == null) {
            if (other.psiclevel1 != null) {
                return false;
            }
        } else {
            if (!this.psiclevel1 .equals(other.psiclevel1)) {
                return false;
            }
        }
        if (this.psiclevel2 == null) {
            if (other.psiclevel2 != null) {
                return false;
            }
        } else {
            if (!this.psiclevel2 .equals(other.psiclevel2)) {
                return false;
            }
        }
        if (this.psiclevel3 == null) {
            if (other.psiclevel3 != null) {
                return false;
            }
        } else {
            if (!this.psiclevel3 .equals(other.psiclevel3)) {
                return false;
            }
        }
        if (this.psoccode == null) {
            if (other.psoccode!= null) {
                return false;
            }
        } else {
            if (!this.psoccode.equals(other.psoccode)) {
                return false;
            }
        }
        if (this.psoclevel1 == null) {
            if (other.psoclevel1 != null) {
                return false;
            }
        } else {
            if (!this.psoclevel1 .equals(other.psoclevel1)) {
                return false;
            }
        }
        if (this.psoclevel2 == null) {
            if (other.psoclevel2 != null) {
                return false;
            }
        } else {
            if (!this.psoclevel2 .equals(other.psoclevel2)) {
                return false;
            }
        }
        if (this.psoclevel3 == null) {
            if (other.psoclevel3 != null) {
                return false;
            }
        } else {
            if (!this.psoclevel3 .equals(other.psoclevel3)) {
                return false;
            }
        }
        if (this.psoclevel4 == null) {
            if (other.psoclevel4 != null) {
                return false;
            }
        } else {
            if (!this.psoclevel4 .equals(other.psoclevel4)) {
                return false;
            }
        }
        if (this.sector == null) {
            if (other.sector!= null) {
                return false;
            }
        } else {
            if (!this.sector.equals(other.sector)) {
                return false;
            }
        }
        if (this.subsector == null) {
            if (other.subsector!= null) {
                return false;
            }
        } else {
            if (!this.subsector.equals(other.subsector)) {
                return false;
            }
        }
        if (this.registrationnumber == null) {
            if (other.registrationnumber!= null) {
                return false;
            }
        } else {
            if (!this.registrationnumber.equals(other.registrationnumber)) {
                return false;
            }
        }
        if (this.registrationdate == null) {
            if (other.registrationdate!= null) {
                return false;
            }
        } else {
            if (!this.registrationdate.equals(other.registrationdate)) {
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
        if (this.employmentstatus!= null) {
            rtn = (rtn + this.employmentstatus.hashCode());
        }
        rtn = (rtn* 37);
        if (this.employeeid!= null) {
            rtn = (rtn + this.employeeid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.companyid!= null) {
            rtn = (rtn + this.companyid.hashCode());
        }
        rtn = (rtn* 37);
        if (this.position!= null) {
            rtn = (rtn + this.position.hashCode());
        }
        rtn = (rtn* 37);
        if (this.joblevel!= null) {
            rtn = (rtn + this.joblevel.hashCode());
        }
        rtn = (rtn* 37);
        if (this.datehiredto!= null) {
            rtn = (rtn + this.datehiredto.hashCode());
        }
        rtn = (rtn* 37);
        if (this.datehiredfrom!= null) {
            rtn = (rtn + this.datehiredfrom.hashCode());
        }
        rtn = (rtn* 37);
        if (this.yearsofemployment!= null) {
            rtn = (rtn + this.yearsofemployment.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dateresign!= null) {
            rtn = (rtn + this.dateresign.hashCode());
        }
        rtn = (rtn* 37);
        if (this.businesstype!= null) {
            rtn = (rtn + this.businesstype.hashCode());
        }
        rtn = (rtn* 37);
        if (this.companyname!= null) {
            rtn = (rtn + this.companyname.hashCode());
        }
        rtn = (rtn* 37);
        if (this.streetnoname!= null) {
            rtn = (rtn + this.streetnoname.hashCode());
        }
        rtn = (rtn* 37);
        if (this.subdivision!= null) {
            rtn = (rtn + this.subdivision.hashCode());
        }
        rtn = (rtn* 37);
        if (this.barangay!= null) {
            rtn = (rtn + this.barangay.hashCode());
        }
        rtn = (rtn* 37);
        if (this.stateprovince!= null) {
            rtn = (rtn + this.stateprovince.hashCode());
        }
        rtn = (rtn* 37);
        if (this.city!= null) {
            rtn = (rtn + this.city.hashCode());
        }
        rtn = (rtn* 37);
        if (this.region!= null) {
            rtn = (rtn + this.region.hashCode());
        }
        rtn = (rtn* 37);
        if (this.country!= null) {
            rtn = (rtn + this.country.hashCode());
        }
        rtn = (rtn* 37);
        if (this.postalcode!= null) {
            rtn = (rtn + this.postalcode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.psiccode!= null) {
            rtn = (rtn + this.psiccode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.psiclevel1 != null) {
            rtn = (rtn + this.psiclevel1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.psiclevel2 != null) {
            rtn = (rtn + this.psiclevel2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.psiclevel3 != null) {
            rtn = (rtn + this.psiclevel3 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.psoccode!= null) {
            rtn = (rtn + this.psoccode.hashCode());
        }
        rtn = (rtn* 37);
        if (this.psoclevel1 != null) {
            rtn = (rtn + this.psoclevel1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.psoclevel2 != null) {
            rtn = (rtn + this.psoclevel2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.psoclevel3 != null) {
            rtn = (rtn + this.psoclevel3 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.psoclevel4 != null) {
            rtn = (rtn + this.psoclevel4 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.sector!= null) {
            rtn = (rtn + this.sector.hashCode());
        }
        rtn = (rtn* 37);
        if (this.subsector!= null) {
            rtn = (rtn + this.subsector.hashCode());
        }
        rtn = (rtn* 37);
        if (this.registrationnumber!= null) {
            rtn = (rtn + this.registrationnumber.hashCode());
        }
        rtn = (rtn* 37);
        if (this.registrationdate!= null) {
            rtn = (rtn + this.registrationdate.hashCode());
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

    public String getEmploymentstatus() {
        return employmentstatus;
    }

    public void setEmploymentstatus(String employmentstatus) {
        this.employmentstatus = employmentstatus;
    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJoblevel() {
        return joblevel;
    }

    public void setJoblevel(String joblevel) {
        this.joblevel = joblevel;
    }

    public Date getDatehiredto() {
        return datehiredto;
    }

    public void setDatehiredto(Date datehiredto) {
        this.datehiredto = datehiredto;
    }

    public Date getDatehiredfrom() {
        return datehiredfrom;
    }

    public void setDatehiredfrom(Date datehiredfrom) {
        this.datehiredfrom = datehiredfrom;
    }

    public String getYearsofemployment() {
        return yearsofemployment;
    }

    public void setYearsofemployment(String yearsofemployment) {
        this.yearsofemployment = yearsofemployment;
    }

    public Date getDateresign() {
        return dateresign;
    }

    public void setDateresign(Date dateresign) {
        this.dateresign = dateresign;
    }

    public String getBusinesstype() {
        return businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getStreetnoname() {
        return streetnoname;
    }

    public void setStreetnoname(String streetnoname) {
        this.streetnoname = streetnoname;
    }

    public String getSubdivision() {
        return subdivision;
    }

    public void setSubdivision(String subdivision) {
        this.subdivision = subdivision;
    }

    public String getBarangay() {
        return barangay;
    }

    public void setBarangay(String barangay) {
        this.barangay = barangay;
    }

    public String getStateprovince() {
        return stateprovince;
    }

    public void setStateprovince(String stateprovince) {
        this.stateprovince = stateprovince;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getPsiccode() {
        return psiccode;
    }

    public void setPsiccode(String psiccode) {
        this.psiccode = psiccode;
    }

    public String getPsiclevel1() {
        return psiclevel1;
    }

    public void setPsiclevel1(String psiclevel1) {
        this.psiclevel1 = psiclevel1;
    }

    public String getPsiclevel2() {
        return psiclevel2;
    }

    public void setPsiclevel2(String psiclevel2) {
        this.psiclevel2 = psiclevel2;
    }

    public String getPsiclevel3() {
        return psiclevel3;
    }

    public void setPsiclevel3(String psiclevel3) {
        this.psiclevel3 = psiclevel3;
    }

    public String getPsoccode() {
        return psoccode;
    }

    public void setPsoccode(String psoccode) {
        this.psoccode = psoccode;
    }

    public String getPsoclevel1() {
        return psoclevel1;
    }

    public void setPsoclevel1(String psoclevel1) {
        this.psoclevel1 = psoclevel1;
    }

    public String getPsoclevel2() {
        return psoclevel2;
    }

    public void setPsoclevel2(String psoclevel2) {
        this.psoclevel2 = psoclevel2;
    }

    public String getPsoclevel3() {
        return psoclevel3;
    }

    public void setPsoclevel3(String psoclevel3) {
        this.psoclevel3 = psoclevel3;
    }

    public String getPsoclevel4() {
        return psoclevel4;
    }

    public void setPsoclevel4(String psoclevel4) {
        this.psoclevel4 = psoclevel4;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSubsector() {
        return subsector;
    }

    public void setSubsector(String subsector) {
        this.subsector = subsector;
    }

    public String getRegistrationnumber() {
        return registrationnumber;
    }

    public void setRegistrationnumber(String registrationnumber) {
        this.registrationnumber = registrationnumber;
    }

    public Date getRegistrationdate() {
        return registrationdate;
    }

    public void setRegistrationdate(Date registrationdate) {
        this.registrationdate = registrationdate;
    }

}
