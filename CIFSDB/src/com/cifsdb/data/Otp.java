
package com.cifsdb.data;

import java.util.Date;


/**
 *  CIFSDB.Otp
 *  08/27/2024 14:22:04
 * 
 */
public class Otp {

    private Integer id;
    private String cifno;
    private String otp;
    private Date dateTimeCreated;
    private Date dateTimeExpired;
    private Byte isValidated;
    private Byte isExpired;
    private String mobileNumber;

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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Date getDateTimeCreated() {
        return dateTimeCreated;
    }

    public void setDateTimeCreated(Date dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }

    public Date getDateTimeExpired() {
        return dateTimeExpired;
    }

    public void setDateTimeExpired(Date dateTimeExpired) {
        this.dateTimeExpired = dateTimeExpired;
    }

    public Byte getIsValidated() {
        return isValidated;
    }

    public void setIsValidated(Byte isValidated) {
        this.isValidated = isValidated;
    }

    public Byte getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Byte isExpired) {
        this.isExpired = isExpired;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

}
