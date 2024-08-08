
package com.cifsdb.data;



/**
 *  CIFSDB.Tbproperties
 *  08/06/2024 19:26:35
 * 
 */
public class Tbproperties {

    private Integer id;
    private Boolean smtpIsdisabled;
    private Integer smtpTimeinterval;
    private String smtpEmailaddress;
    private String smtpPassword;
    private String smtpEmailaddalias;
    private String smtpHost;
    private String smtpPort;
    private String rptUrl;
    private String rptUsername;
    private String rptPassword;
    private String dmsUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getSmtpIsdisabled() {
        return smtpIsdisabled;
    }

    public void setSmtpIsdisabled(Boolean smtpIsdisabled) {
        this.smtpIsdisabled = smtpIsdisabled;
    }

    public Integer getSmtpTimeinterval() {
        return smtpTimeinterval;
    }

    public void setSmtpTimeinterval(Integer smtpTimeinterval) {
        this.smtpTimeinterval = smtpTimeinterval;
    }

    public String getSmtpEmailaddress() {
        return smtpEmailaddress;
    }

    public void setSmtpEmailaddress(String smtpEmailaddress) {
        this.smtpEmailaddress = smtpEmailaddress;
    }

    public String getSmtpPassword() {
        return smtpPassword;
    }

    public void setSmtpPassword(String smtpPassword) {
        this.smtpPassword = smtpPassword;
    }

    public String getSmtpEmailaddalias() {
        return smtpEmailaddalias;
    }

    public void setSmtpEmailaddalias(String smtpEmailaddalias) {
        this.smtpEmailaddalias = smtpEmailaddalias;
    }

    public String getSmtpHost() {
        return smtpHost;
    }

    public void setSmtpHost(String smtpHost) {
        this.smtpHost = smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }

    public void setSmtpPort(String smtpPort) {
        this.smtpPort = smtpPort;
    }

    public String getRptUrl() {
        return rptUrl;
    }

    public void setRptUrl(String rptUrl) {
        this.rptUrl = rptUrl;
    }

    public String getRptUsername() {
        return rptUsername;
    }

    public void setRptUsername(String rptUsername) {
        this.rptUsername = rptUsername;
    }

    public String getRptPassword() {
        return rptPassword;
    }

    public void setRptPassword(String rptPassword) {
        this.rptPassword = rptPassword;
    }

    public String getDmsUrl() {
        return dmsUrl;
    }

    public void setDmsUrl(String dmsUrl) {
        this.dmsUrl = dmsUrl;
    }

}
