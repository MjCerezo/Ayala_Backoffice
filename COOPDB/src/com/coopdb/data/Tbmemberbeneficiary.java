
package com.coopdb.data;



/**
 *  COOPDB.Tbmemberbeneficiary
 *  08/04/2024 12:54:42
 * 
 */
public class Tbmemberbeneficiary {

    private Integer id;
    private String membershipappid;
    private String membershipid;
    private String fullname;
    private String fulladdress;
    private String relationship;
    private String contactno;
    private String beneficiarymemberid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMembershipappid() {
        return membershipappid;
    }

    public void setMembershipappid(String membershipappid) {
        this.membershipappid = membershipappid;
    }

    public String getMembershipid() {
        return membershipid;
    }

    public void setMembershipid(String membershipid) {
        this.membershipid = membershipid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getFulladdress() {
        return fulladdress;
    }

    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getBeneficiarymemberid() {
        return beneficiarymemberid;
    }

    public void setBeneficiarymemberid(String beneficiarymemberid) {
        this.beneficiarymemberid = beneficiarymemberid;
    }

}
