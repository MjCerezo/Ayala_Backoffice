
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbappdependents
 *  08/27/2024 14:22:56
 * 
 */
public class Tbappdependents {

    private Integer id;
    private String membershipappid;
    private String fullname;
    private Date dateofbirth;
    private Integer age;
    private String title;
    private String gender;
    private String relationship;
    private Boolean ismember;
    private String contactno;
    private String dependentmemberid;

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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public Boolean getIsmember() {
        return ismember;
    }

    public void setIsmember(Boolean ismember) {
        this.ismember = ismember;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getDependentmemberid() {
        return dependentmemberid;
    }

    public void setDependentmemberid(String dependentmemberid) {
        this.dependentmemberid = dependentmemberid;
    }

}
