
package com.cifsdb.data;



/**
 *  CIFSDB.Tbpersonalreference
 *  08/27/2024 14:22:04
 * 
 */
public class Tbpersonalreference {

    private Integer id;
    private String cifno;
    private String personalrefname;
    private String employername;
    private String employeraddress;
    private String contactno;
    private String relationship;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getPersonalrefname() {
        return personalrefname;
    }

    public void setPersonalrefname(String personalrefname) {
        this.personalrefname = personalrefname;
    }

    public String getEmployername() {
        return employername;
    }

    public void setEmployername(String employername) {
        this.employername = employername;
    }

    public String getEmployeraddress() {
        return employeraddress;
    }

    public void setEmployeraddress(String employeraddress) {
        this.employeraddress = employeraddress;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

}
