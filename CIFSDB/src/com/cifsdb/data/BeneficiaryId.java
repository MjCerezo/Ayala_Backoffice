
package com.cifsdb.data;

import java.io.Serializable;


/**
 *  CIFSDB.BeneficiaryId
 *  09/26/2023 10:13:06
 * 
 */
public class BeneficiaryId
    implements Serializable
{

    private String cifNo;
    private String dxbankCif;
    private String firstnameCorporateName;
    private String middlename;
    private String lastname;
    private Byte beneficiaryTitle;
    private String beneficiaryFirstname;
    private String beneficiaryMiddlename;
    private String beneficiaryLastname;
    private String beneficiarySuffix;
    private String beneficiaryDateOfBirth;
    private String beneficiaryRelationshipToCustomer;
    private String membershipDate;
    private String membershipType;
    private String column15;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof BeneficiaryId)) {
            return false;
        }
        BeneficiaryId other = ((BeneficiaryId) o);
        if (this.cifNo == null) {
            if (other.cifNo!= null) {
                return false;
            }
        } else {
            if (!this.cifNo.equals(other.cifNo)) {
                return false;
            }
        }
        if (this.dxbankCif == null) {
            if (other.dxbankCif!= null) {
                return false;
            }
        } else {
            if (!this.dxbankCif.equals(other.dxbankCif)) {
                return false;
            }
        }
        if (this.firstnameCorporateName == null) {
            if (other.firstnameCorporateName!= null) {
                return false;
            }
        } else {
            if (!this.firstnameCorporateName.equals(other.firstnameCorporateName)) {
                return false;
            }
        }
        if (this.middlename == null) {
            if (other.middlename!= null) {
                return false;
            }
        } else {
            if (!this.middlename.equals(other.middlename)) {
                return false;
            }
        }
        if (this.lastname == null) {
            if (other.lastname!= null) {
                return false;
            }
        } else {
            if (!this.lastname.equals(other.lastname)) {
                return false;
            }
        }
        if (this.beneficiaryTitle == null) {
            if (other.beneficiaryTitle!= null) {
                return false;
            }
        } else {
            if (!this.beneficiaryTitle.equals(other.beneficiaryTitle)) {
                return false;
            }
        }
        if (this.beneficiaryFirstname == null) {
            if (other.beneficiaryFirstname!= null) {
                return false;
            }
        } else {
            if (!this.beneficiaryFirstname.equals(other.beneficiaryFirstname)) {
                return false;
            }
        }
        if (this.beneficiaryMiddlename == null) {
            if (other.beneficiaryMiddlename!= null) {
                return false;
            }
        } else {
            if (!this.beneficiaryMiddlename.equals(other.beneficiaryMiddlename)) {
                return false;
            }
        }
        if (this.beneficiaryLastname == null) {
            if (other.beneficiaryLastname!= null) {
                return false;
            }
        } else {
            if (!this.beneficiaryLastname.equals(other.beneficiaryLastname)) {
                return false;
            }
        }
        if (this.beneficiarySuffix == null) {
            if (other.beneficiarySuffix!= null) {
                return false;
            }
        } else {
            if (!this.beneficiarySuffix.equals(other.beneficiarySuffix)) {
                return false;
            }
        }
        if (this.beneficiaryDateOfBirth == null) {
            if (other.beneficiaryDateOfBirth!= null) {
                return false;
            }
        } else {
            if (!this.beneficiaryDateOfBirth.equals(other.beneficiaryDateOfBirth)) {
                return false;
            }
        }
        if (this.beneficiaryRelationshipToCustomer == null) {
            if (other.beneficiaryRelationshipToCustomer!= null) {
                return false;
            }
        } else {
            if (!this.beneficiaryRelationshipToCustomer.equals(other.beneficiaryRelationshipToCustomer)) {
                return false;
            }
        }
        if (this.membershipDate == null) {
            if (other.membershipDate!= null) {
                return false;
            }
        } else {
            if (!this.membershipDate.equals(other.membershipDate)) {
                return false;
            }
        }
        if (this.membershipType == null) {
            if (other.membershipType!= null) {
                return false;
            }
        } else {
            if (!this.membershipType.equals(other.membershipType)) {
                return false;
            }
        }
        if (this.column15 == null) {
            if (other.column15 != null) {
                return false;
            }
        } else {
            if (!this.column15 .equals(other.column15)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.cifNo!= null) {
            rtn = (rtn + this.cifNo.hashCode());
        }
        rtn = (rtn* 37);
        if (this.dxbankCif!= null) {
            rtn = (rtn + this.dxbankCif.hashCode());
        }
        rtn = (rtn* 37);
        if (this.firstnameCorporateName!= null) {
            rtn = (rtn + this.firstnameCorporateName.hashCode());
        }
        rtn = (rtn* 37);
        if (this.middlename!= null) {
            rtn = (rtn + this.middlename.hashCode());
        }
        rtn = (rtn* 37);
        if (this.lastname!= null) {
            rtn = (rtn + this.lastname.hashCode());
        }
        rtn = (rtn* 37);
        if (this.beneficiaryTitle!= null) {
            rtn = (rtn + this.beneficiaryTitle.hashCode());
        }
        rtn = (rtn* 37);
        if (this.beneficiaryFirstname!= null) {
            rtn = (rtn + this.beneficiaryFirstname.hashCode());
        }
        rtn = (rtn* 37);
        if (this.beneficiaryMiddlename!= null) {
            rtn = (rtn + this.beneficiaryMiddlename.hashCode());
        }
        rtn = (rtn* 37);
        if (this.beneficiaryLastname!= null) {
            rtn = (rtn + this.beneficiaryLastname.hashCode());
        }
        rtn = (rtn* 37);
        if (this.beneficiarySuffix!= null) {
            rtn = (rtn + this.beneficiarySuffix.hashCode());
        }
        rtn = (rtn* 37);
        if (this.beneficiaryDateOfBirth!= null) {
            rtn = (rtn + this.beneficiaryDateOfBirth.hashCode());
        }
        rtn = (rtn* 37);
        if (this.beneficiaryRelationshipToCustomer!= null) {
            rtn = (rtn + this.beneficiaryRelationshipToCustomer.hashCode());
        }
        rtn = (rtn* 37);
        if (this.membershipDate!= null) {
            rtn = (rtn + this.membershipDate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.membershipType!= null) {
            rtn = (rtn + this.membershipType.hashCode());
        }
        rtn = (rtn* 37);
        if (this.column15 != null) {
            rtn = (rtn + this.column15 .hashCode());
        }
        return rtn;
    }

    public String getCifNo() {
        return cifNo;
    }

    public void setCifNo(String cifNo) {
        this.cifNo = cifNo;
    }

    public String getDxbankCif() {
        return dxbankCif;
    }

    public void setDxbankCif(String dxbankCif) {
        this.dxbankCif = dxbankCif;
    }

    public String getFirstnameCorporateName() {
        return firstnameCorporateName;
    }

    public void setFirstnameCorporateName(String firstnameCorporateName) {
        this.firstnameCorporateName = firstnameCorporateName;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Byte getBeneficiaryTitle() {
        return beneficiaryTitle;
    }

    public void setBeneficiaryTitle(Byte beneficiaryTitle) {
        this.beneficiaryTitle = beneficiaryTitle;
    }

    public String getBeneficiaryFirstname() {
        return beneficiaryFirstname;
    }

    public void setBeneficiaryFirstname(String beneficiaryFirstname) {
        this.beneficiaryFirstname = beneficiaryFirstname;
    }

    public String getBeneficiaryMiddlename() {
        return beneficiaryMiddlename;
    }

    public void setBeneficiaryMiddlename(String beneficiaryMiddlename) {
        this.beneficiaryMiddlename = beneficiaryMiddlename;
    }

    public String getBeneficiaryLastname() {
        return beneficiaryLastname;
    }

    public void setBeneficiaryLastname(String beneficiaryLastname) {
        this.beneficiaryLastname = beneficiaryLastname;
    }

    public String getBeneficiarySuffix() {
        return beneficiarySuffix;
    }

    public void setBeneficiarySuffix(String beneficiarySuffix) {
        this.beneficiarySuffix = beneficiarySuffix;
    }

    public String getBeneficiaryDateOfBirth() {
        return beneficiaryDateOfBirth;
    }

    public void setBeneficiaryDateOfBirth(String beneficiaryDateOfBirth) {
        this.beneficiaryDateOfBirth = beneficiaryDateOfBirth;
    }

    public String getBeneficiaryRelationshipToCustomer() {
        return beneficiaryRelationshipToCustomer;
    }

    public void setBeneficiaryRelationshipToCustomer(String beneficiaryRelationshipToCustomer) {
        this.beneficiaryRelationshipToCustomer = beneficiaryRelationshipToCustomer;
    }

    public String getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(String membershipDate) {
        this.membershipDate = membershipDate;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public String getColumn15() {
        return column15;
    }

    public void setColumn15(String column15) {
        this.column15 = column15;
    }

}
