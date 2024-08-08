
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbcifdependents
 *  02/23/2023 13:04:33
 * 
 */
public class Tbcifdependents {

    private Integer dependentid;
    private String cifno;
    private String fullname;
    private Date dateofbirth;
    private Boolean issameasfulladdress1;
    private Boolean issameasfulladdress2;
    private String address;
    private Integer age;
    private String gradeyear;
    private String relationship;
    private String otherrelationship;
    private String gender;
    private String appno;

    public Integer getDependentid() {
        return dependentid;
    }

    public void setDependentid(Integer dependentid) {
        this.dependentid = dependentid;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public Boolean getIssameasfulladdress1() {
        return issameasfulladdress1;
    }

    public void setIssameasfulladdress1(Boolean issameasfulladdress1) {
        this.issameasfulladdress1 = issameasfulladdress1;
    }

    public Boolean getIssameasfulladdress2() {
        return issameasfulladdress2;
    }

    public void setIssameasfulladdress2(Boolean issameasfulladdress2) {
        this.issameasfulladdress2 = issameasfulladdress2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGradeyear() {
        return gradeyear;
    }

    public void setGradeyear(String gradeyear) {
        this.gradeyear = gradeyear;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getOtherrelationship() {
        return otherrelationship;
    }

    public void setOtherrelationship(String otherrelationship) {
        this.otherrelationship = otherrelationship;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

}
