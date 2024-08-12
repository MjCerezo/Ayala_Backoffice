
package com.coopdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  COOPDB.Tbprodmatrix
 *  08/10/2024 21:24:57
 * 
 */
public class Tbprodmatrix {

    private Integer id;
    private String prodgroup;
    private String prodcode;
    private String prodname;
    private String prodsname;
    private String currency;
    private Integer minterm;
    private String minperiod;
    private Integer maxterm;
    private String maxperiod;
    private BigDecimal reqinitialdepamt;
    private String reqdepfreq;
    private BigDecimal reqmonthlydepamt;
    private Boolean passbookind;
    private Boolean checkbookind;
    private Boolean certtimedepind;
    private Boolean atmind;
    private Boolean soaind;
    private String freqsoaprint;
    private String posttx;
    private BigDecimal minbalei;
    private String baltype;
    private String balcomp;
    private Integer baseyear;
    private String inttype;
    private BigDecimal intrate;
    private String intplan;
    private String freqintcomp;
    private String freqintpost;
    private String freqintpayout;
    private BigDecimal wtaxrate;
    private Boolean docstampind;
    private String pretermplan;
    private BigDecimal rbmminmainbal;
    private String rbmbaltype;
    private String rbmbalcomp;
    private String rbmfreqcomp;
    private BigDecimal rbmchrgbelminbal;
    private Integer sequence;
    private String instcode;
    private String createdby;
    private Date createddate;
    private Boolean ataind;
    private BigDecimal maxbalance;
    private Integer age;
    private Integer dormancytermno;
    private String dormancytermperiod;
    private Integer escheattermno;
    private String escheattermperiod;
    private Integer inactivetermno;
    private String inactivetermperiod;
    private String customertype;
    private Boolean isenable;
    private Integer maxwithdraw;
    private String maxwithdrawfreq;
    private String freqintaccr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProdgroup() {
        return prodgroup;
    }

    public void setProdgroup(String prodgroup) {
        this.prodgroup = prodgroup;
    }

    public String getProdcode() {
        return prodcode;
    }

    public void setProdcode(String prodcode) {
        this.prodcode = prodcode;
    }

    public String getProdname() {
        return prodname;
    }

    public void setProdname(String prodname) {
        this.prodname = prodname;
    }

    public String getProdsname() {
        return prodsname;
    }

    public void setProdsname(String prodsname) {
        this.prodsname = prodsname;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getMinterm() {
        return minterm;
    }

    public void setMinterm(Integer minterm) {
        this.minterm = minterm;
    }

    public String getMinperiod() {
        return minperiod;
    }

    public void setMinperiod(String minperiod) {
        this.minperiod = minperiod;
    }

    public Integer getMaxterm() {
        return maxterm;
    }

    public void setMaxterm(Integer maxterm) {
        this.maxterm = maxterm;
    }

    public String getMaxperiod() {
        return maxperiod;
    }

    public void setMaxperiod(String maxperiod) {
        this.maxperiod = maxperiod;
    }

    public BigDecimal getReqinitialdepamt() {
        return reqinitialdepamt;
    }

    public void setReqinitialdepamt(BigDecimal reqinitialdepamt) {
        this.reqinitialdepamt = reqinitialdepamt;
    }

    public String getReqdepfreq() {
        return reqdepfreq;
    }

    public void setReqdepfreq(String reqdepfreq) {
        this.reqdepfreq = reqdepfreq;
    }

    public BigDecimal getReqmonthlydepamt() {
        return reqmonthlydepamt;
    }

    public void setReqmonthlydepamt(BigDecimal reqmonthlydepamt) {
        this.reqmonthlydepamt = reqmonthlydepamt;
    }

    public Boolean getPassbookind() {
        return passbookind;
    }

    public void setPassbookind(Boolean passbookind) {
        this.passbookind = passbookind;
    }

    public Boolean getCheckbookind() {
        return checkbookind;
    }

    public void setCheckbookind(Boolean checkbookind) {
        this.checkbookind = checkbookind;
    }

    public Boolean getCerttimedepind() {
        return certtimedepind;
    }

    public void setCerttimedepind(Boolean certtimedepind) {
        this.certtimedepind = certtimedepind;
    }

    public Boolean getAtmind() {
        return atmind;
    }

    public void setAtmind(Boolean atmind) {
        this.atmind = atmind;
    }

    public Boolean getSoaind() {
        return soaind;
    }

    public void setSoaind(Boolean soaind) {
        this.soaind = soaind;
    }

    public String getFreqsoaprint() {
        return freqsoaprint;
    }

    public void setFreqsoaprint(String freqsoaprint) {
        this.freqsoaprint = freqsoaprint;
    }

    public String getPosttx() {
        return posttx;
    }

    public void setPosttx(String posttx) {
        this.posttx = posttx;
    }

    public BigDecimal getMinbalei() {
        return minbalei;
    }

    public void setMinbalei(BigDecimal minbalei) {
        this.minbalei = minbalei;
    }

    public String getBaltype() {
        return baltype;
    }

    public void setBaltype(String baltype) {
        this.baltype = baltype;
    }

    public String getBalcomp() {
        return balcomp;
    }

    public void setBalcomp(String balcomp) {
        this.balcomp = balcomp;
    }

    public Integer getBaseyear() {
        return baseyear;
    }

    public void setBaseyear(Integer baseyear) {
        this.baseyear = baseyear;
    }

    public String getInttype() {
        return inttype;
    }

    public void setInttype(String inttype) {
        this.inttype = inttype;
    }

    public BigDecimal getIntrate() {
        return intrate;
    }

    public void setIntrate(BigDecimal intrate) {
        this.intrate = intrate;
    }

    public String getIntplan() {
        return intplan;
    }

    public void setIntplan(String intplan) {
        this.intplan = intplan;
    }

    public String getFreqintcomp() {
        return freqintcomp;
    }

    public void setFreqintcomp(String freqintcomp) {
        this.freqintcomp = freqintcomp;
    }

    public String getFreqintpost() {
        return freqintpost;
    }

    public void setFreqintpost(String freqintpost) {
        this.freqintpost = freqintpost;
    }

    public String getFreqintpayout() {
        return freqintpayout;
    }

    public void setFreqintpayout(String freqintpayout) {
        this.freqintpayout = freqintpayout;
    }

    public BigDecimal getWtaxrate() {
        return wtaxrate;
    }

    public void setWtaxrate(BigDecimal wtaxrate) {
        this.wtaxrate = wtaxrate;
    }

    public Boolean getDocstampind() {
        return docstampind;
    }

    public void setDocstampind(Boolean docstampind) {
        this.docstampind = docstampind;
    }

    public String getPretermplan() {
        return pretermplan;
    }

    public void setPretermplan(String pretermplan) {
        this.pretermplan = pretermplan;
    }

    public BigDecimal getRbmminmainbal() {
        return rbmminmainbal;
    }

    public void setRbmminmainbal(BigDecimal rbmminmainbal) {
        this.rbmminmainbal = rbmminmainbal;
    }

    public String getRbmbaltype() {
        return rbmbaltype;
    }

    public void setRbmbaltype(String rbmbaltype) {
        this.rbmbaltype = rbmbaltype;
    }

    public String getRbmbalcomp() {
        return rbmbalcomp;
    }

    public void setRbmbalcomp(String rbmbalcomp) {
        this.rbmbalcomp = rbmbalcomp;
    }

    public String getRbmfreqcomp() {
        return rbmfreqcomp;
    }

    public void setRbmfreqcomp(String rbmfreqcomp) {
        this.rbmfreqcomp = rbmfreqcomp;
    }

    public BigDecimal getRbmchrgbelminbal() {
        return rbmchrgbelminbal;
    }

    public void setRbmchrgbelminbal(BigDecimal rbmchrgbelminbal) {
        this.rbmchrgbelminbal = rbmchrgbelminbal;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getInstcode() {
        return instcode;
    }

    public void setInstcode(String instcode) {
        this.instcode = instcode;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public Date getCreateddate() {
        return createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    public Boolean getAtaind() {
        return ataind;
    }

    public void setAtaind(Boolean ataind) {
        this.ataind = ataind;
    }

    public BigDecimal getMaxbalance() {
        return maxbalance;
    }

    public void setMaxbalance(BigDecimal maxbalance) {
        this.maxbalance = maxbalance;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getDormancytermno() {
        return dormancytermno;
    }

    public void setDormancytermno(Integer dormancytermno) {
        this.dormancytermno = dormancytermno;
    }

    public String getDormancytermperiod() {
        return dormancytermperiod;
    }

    public void setDormancytermperiod(String dormancytermperiod) {
        this.dormancytermperiod = dormancytermperiod;
    }

    public Integer getEscheattermno() {
        return escheattermno;
    }

    public void setEscheattermno(Integer escheattermno) {
        this.escheattermno = escheattermno;
    }

    public String getEscheattermperiod() {
        return escheattermperiod;
    }

    public void setEscheattermperiod(String escheattermperiod) {
        this.escheattermperiod = escheattermperiod;
    }

    public Integer getInactivetermno() {
        return inactivetermno;
    }

    public void setInactivetermno(Integer inactivetermno) {
        this.inactivetermno = inactivetermno;
    }

    public String getInactivetermperiod() {
        return inactivetermperiod;
    }

    public void setInactivetermperiod(String inactivetermperiod) {
        this.inactivetermperiod = inactivetermperiod;
    }

    public String getCustomertype() {
        return customertype;
    }

    public void setCustomertype(String customertype) {
        this.customertype = customertype;
    }

    public Boolean getIsenable() {
        return isenable;
    }

    public void setIsenable(Boolean isenable) {
        this.isenable = isenable;
    }

    public Integer getMaxwithdraw() {
        return maxwithdraw;
    }

    public void setMaxwithdraw(Integer maxwithdraw) {
        this.maxwithdraw = maxwithdraw;
    }

    public String getMaxwithdrawfreq() {
        return maxwithdrawfreq;
    }

    public void setMaxwithdrawfreq(String maxwithdrawfreq) {
        this.maxwithdrawfreq = maxwithdrawfreq;
    }

    public String getFreqintaccr() {
        return freqintaccr;
    }

    public void setFreqintaccr(String freqintaccr) {
        this.freqintaccr = freqintaccr;
    }

}
