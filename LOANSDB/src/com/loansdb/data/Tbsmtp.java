
package com.loansdb.data;

import java.util.Date;


/**
 *  LOANSDB.Tbsmtp
 *  10/13/2020 10:21:35
 * 
 */
public class Tbsmtp {

    private Integer id;
    private String cifno;
    private String subject;
    private String body;
    private String recipient;
    private String sender;
    private String cc;
    private String bcc;
    private Integer flag;
    private Date datesent;
    private Integer applicationstatus;
    private Date dateadded;
    private String addedby;
    private String emailcode;
    private String appno;
    private String applicationstatusdesc;

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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Date getDatesent() {
        return datesent;
    }

    public void setDatesent(Date datesent) {
        this.datesent = datesent;
    }

    public Integer getApplicationstatus() {
        return applicationstatus;
    }

    public void setApplicationstatus(Integer applicationstatus) {
        this.applicationstatus = applicationstatus;
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public String getEmailcode() {
        return emailcode;
    }

    public void setEmailcode(String emailcode) {
        this.emailcode = emailcode;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getApplicationstatusdesc() {
        return applicationstatusdesc;
    }

    public void setApplicationstatusdesc(String applicationstatusdesc) {
        this.applicationstatusdesc = applicationstatusdesc;
    }

}
