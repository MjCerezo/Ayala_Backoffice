
package com.coopdb.data;

import java.util.Date;


/**
 *  COOPDB.Tbemailformats
 *  08/10/2024 21:24:56
 * 
 */
public class Tbemailformats {

    private String emailcode;
    private String formatname;
    private String recipient;
    private String sender;
    private String groupcode;
    private String teamemail;
    private String subject;
    private Date createddate;
    private String createdby;

    public String getEmailcode() {
        return emailcode;
    }

    public void setEmailcode(String emailcode) {
        this.emailcode = emailcode;
    }

    public String getFormatname() {
        return formatname;
    }

    public void setFormatname(String formatname) {
        this.formatname = formatname;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getTeamemail() {
        return teamemail;
    }

    public void setTeamemail(String teamemail) {
        this.teamemail = teamemail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

}
