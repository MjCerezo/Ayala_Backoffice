
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Tbcidependents
 *  09/26/2023 10:13:05
 * 
 */
public class Tbcidependents {

    private Integer dependentid;
    private String cireportid;
    private String fullname;
    private String gender;
    private Date dateofbirth;
    private Integer age;
    private String fulladdress;
    private String relationship;
    private String otherrelationship;

    public Integer getDependentid() {
        return dependentid;
    }

    public void setDependentid(Integer dependentid) {
        this.dependentid = dependentid;
    }

    public String getCireportid() {
        return cireportid;
    }

    public void setCireportid(String cireportid) {
        this.cireportid = cireportid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
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

    public String getOtherrelationship() {
        return otherrelationship;
    }

    public void setOtherrelationship(String otherrelationship) {
        this.otherrelationship = otherrelationship;
    }

}
