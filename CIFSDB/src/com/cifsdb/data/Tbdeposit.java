
package com.cifsdb.data;

import java.math.BigDecimal;
import java.util.Date;


/**
 *  CIFSDB.Tbdeposit
 *  09/26/2023 10:13:05
 * 
 */
public class Tbdeposit {

    private Integer id;
    private String productCode;
    private String subProductCode;
    private String accountNo;
    private String accountnoata;
    private String accountName;
    private String slaidNo;
    private String employeeNo;
    private BigDecimal accountBalance;
    private BigDecimal floatAmount;
    private Integer accountStatus;
    private Date statusDate;
    private BigDecimal pledgeAmount;
    private String pledgeSchedule;
    private BigDecimal accumulatedBalance;
    private Integer daysElapsed;
    private BigDecimal averageDailyBalance;
    private BigDecimal mtdint;
    private BigDecimal btdint;
    private BigDecimal mtdcredits;
    private BigDecimal ytdcredits;
    private BigDecimal btdcredits;
    private BigDecimal mtddebits;
    private BigDecimal ytddebits;
    private BigDecimal btddebits;
    private Integer totalNoCredits;
    private Integer totalNoDebits;
    private Date bookDate;
    private BigDecimal intRate;
    private BigDecimal wtaxrate;
    private Integer term;
    private Date maturityDate;
    private String compoundingFreq;
    private BigDecimal maintainingBalance;
    private BigDecimal toEarnInterestBalance;
    private String posttx;
    private Integer oldstatus;
    private Date oldStatusDate;
    private String jointAcctType;
    private Integer ownershipType;
    private BigDecimal placementAmt;
    private BigDecimal lessWtaxAmt;
    private BigDecimal matAmt;
    private BigDecimal placeholdAmt;
    private String createdBy;
    private String watchlistCode;
    private String unit;
    private String instcode;
    private Integer alertflag;
    private String alertmessage;
    private Integer alertlevel;
    private String dispoflag;
    private String dispofreetext;
    private String tdcno;
    private Integer tdcreleaseind;
    private Integer jrnlno;
    private Integer xbelowmin;
    private Integer xreturnchk;
    private BigDecimal int4credit;
    private BigDecimal prevmonthadb;
    private BigDecimal prevmtdint;
    private BigDecimal prevint4credit;
    private Date lasttxdate;
    private String lasttxcode;
    private Integer minbalctr;
    private Integer chk4clr;
    private BigDecimal adbavailbal;
    private BigDecimal adbbookbal;
    private BigDecimal eomadbavailbal;
    private BigDecimal eomadbbookbal;
    private BigDecimal eoyadbavailbal;
    private BigDecimal eoyadbbookbal;
    private BigDecimal passbookbal;
    private BigDecimal ytdadbavailbal;
    private BigDecimal ytdaddbookbal;
    private BigDecimal ytdaccumcredit;
    private BigDecimal ytdaccumdebit;
    private BigDecimal ytdintearned;
    private BigDecimal mtdintearned;
    private BigDecimal earmarkbal;
    private Integer freezeind;
    private Date freezeexpdt;
    private Integer freezeid;
    private String regioncode;
    private String areacode;
    private BigDecimal bpapproved;
    private Date bpexpirydt;
    private String bpstatus;
    private BigDecimal bpavailed;
    private BigDecimal garnishedbal;
    private BigDecimal accumbook;
    private BigDecimal accumavail;
    private String soadispo;
    private String solicitingofficer;
    private String referralofficer;
    private String campaign;
    private String deliverydispo;
    private String addressdispo;
    private Integer withdrawcount;
    private Boolean maxwithdrawind;
    private String maxwithdrawfreq;
    private Date maxwithdrawdate;
    private BigDecimal addonrate;
    private Boolean initialdeposit;
    private String sigbasecode;
    private String sigfilename;
    private BigDecimal interestbalance;
    private BigDecimal interestpaid;
    private BigDecimal interestearned;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSubProductCode() {
        return subProductCode;
    }

    public void setSubProductCode(String subProductCode) {
        this.subProductCode = subProductCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountnoata() {
        return accountnoata;
    }

    public void setAccountnoata(String accountnoata) {
        this.accountnoata = accountnoata;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getSlaidNo() {
        return slaidNo;
    }

    public void setSlaidNo(String slaidNo) {
        this.slaidNo = slaidNo;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public BigDecimal getFloatAmount() {
        return floatAmount;
    }

    public void setFloatAmount(BigDecimal floatAmount) {
        this.floatAmount = floatAmount;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Date getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Date statusDate) {
        this.statusDate = statusDate;
    }

    public BigDecimal getPledgeAmount() {
        return pledgeAmount;
    }

    public void setPledgeAmount(BigDecimal pledgeAmount) {
        this.pledgeAmount = pledgeAmount;
    }

    public String getPledgeSchedule() {
        return pledgeSchedule;
    }

    public void setPledgeSchedule(String pledgeSchedule) {
        this.pledgeSchedule = pledgeSchedule;
    }

    public BigDecimal getAccumulatedBalance() {
        return accumulatedBalance;
    }

    public void setAccumulatedBalance(BigDecimal accumulatedBalance) {
        this.accumulatedBalance = accumulatedBalance;
    }

    public Integer getDaysElapsed() {
        return daysElapsed;
    }

    public void setDaysElapsed(Integer daysElapsed) {
        this.daysElapsed = daysElapsed;
    }

    public BigDecimal getAverageDailyBalance() {
        return averageDailyBalance;
    }

    public void setAverageDailyBalance(BigDecimal averageDailyBalance) {
        this.averageDailyBalance = averageDailyBalance;
    }

    public BigDecimal getMtdint() {
        return mtdint;
    }

    public void setMtdint(BigDecimal mtdint) {
        this.mtdint = mtdint;
    }

    public BigDecimal getBtdint() {
        return btdint;
    }

    public void setBtdint(BigDecimal btdint) {
        this.btdint = btdint;
    }

    public BigDecimal getMtdcredits() {
        return mtdcredits;
    }

    public void setMtdcredits(BigDecimal mtdcredits) {
        this.mtdcredits = mtdcredits;
    }

    public BigDecimal getYtdcredits() {
        return ytdcredits;
    }

    public void setYtdcredits(BigDecimal ytdcredits) {
        this.ytdcredits = ytdcredits;
    }

    public BigDecimal getBtdcredits() {
        return btdcredits;
    }

    public void setBtdcredits(BigDecimal btdcredits) {
        this.btdcredits = btdcredits;
    }

    public BigDecimal getMtddebits() {
        return mtddebits;
    }

    public void setMtddebits(BigDecimal mtddebits) {
        this.mtddebits = mtddebits;
    }

    public BigDecimal getYtddebits() {
        return ytddebits;
    }

    public void setYtddebits(BigDecimal ytddebits) {
        this.ytddebits = ytddebits;
    }

    public BigDecimal getBtddebits() {
        return btddebits;
    }

    public void setBtddebits(BigDecimal btddebits) {
        this.btddebits = btddebits;
    }

    public Integer getTotalNoCredits() {
        return totalNoCredits;
    }

    public void setTotalNoCredits(Integer totalNoCredits) {
        this.totalNoCredits = totalNoCredits;
    }

    public Integer getTotalNoDebits() {
        return totalNoDebits;
    }

    public void setTotalNoDebits(Integer totalNoDebits) {
        this.totalNoDebits = totalNoDebits;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public BigDecimal getIntRate() {
        return intRate;
    }

    public void setIntRate(BigDecimal intRate) {
        this.intRate = intRate;
    }

    public BigDecimal getWtaxrate() {
        return wtaxrate;
    }

    public void setWtaxrate(BigDecimal wtaxrate) {
        this.wtaxrate = wtaxrate;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getCompoundingFreq() {
        return compoundingFreq;
    }

    public void setCompoundingFreq(String compoundingFreq) {
        this.compoundingFreq = compoundingFreq;
    }

    public BigDecimal getMaintainingBalance() {
        return maintainingBalance;
    }

    public void setMaintainingBalance(BigDecimal maintainingBalance) {
        this.maintainingBalance = maintainingBalance;
    }

    public BigDecimal getToEarnInterestBalance() {
        return toEarnInterestBalance;
    }

    public void setToEarnInterestBalance(BigDecimal toEarnInterestBalance) {
        this.toEarnInterestBalance = toEarnInterestBalance;
    }

    public String getPosttx() {
        return posttx;
    }

    public void setPosttx(String posttx) {
        this.posttx = posttx;
    }

    public Integer getOldstatus() {
        return oldstatus;
    }

    public void setOldstatus(Integer oldstatus) {
        this.oldstatus = oldstatus;
    }

    public Date getOldStatusDate() {
        return oldStatusDate;
    }

    public void setOldStatusDate(Date oldStatusDate) {
        this.oldStatusDate = oldStatusDate;
    }

    public String getJointAcctType() {
        return jointAcctType;
    }

    public void setJointAcctType(String jointAcctType) {
        this.jointAcctType = jointAcctType;
    }

    public Integer getOwnershipType() {
        return ownershipType;
    }

    public void setOwnershipType(Integer ownershipType) {
        this.ownershipType = ownershipType;
    }

    public BigDecimal getPlacementAmt() {
        return placementAmt;
    }

    public void setPlacementAmt(BigDecimal placementAmt) {
        this.placementAmt = placementAmt;
    }

    public BigDecimal getLessWtaxAmt() {
        return lessWtaxAmt;
    }

    public void setLessWtaxAmt(BigDecimal lessWtaxAmt) {
        this.lessWtaxAmt = lessWtaxAmt;
    }

    public BigDecimal getMatAmt() {
        return matAmt;
    }

    public void setMatAmt(BigDecimal matAmt) {
        this.matAmt = matAmt;
    }

    public BigDecimal getPlaceholdAmt() {
        return placeholdAmt;
    }

    public void setPlaceholdAmt(BigDecimal placeholdAmt) {
        this.placeholdAmt = placeholdAmt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getWatchlistCode() {
        return watchlistCode;
    }

    public void setWatchlistCode(String watchlistCode) {
        this.watchlistCode = watchlistCode;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getInstcode() {
        return instcode;
    }

    public void setInstcode(String instcode) {
        this.instcode = instcode;
    }

    public Integer getAlertflag() {
        return alertflag;
    }

    public void setAlertflag(Integer alertflag) {
        this.alertflag = alertflag;
    }

    public String getAlertmessage() {
        return alertmessage;
    }

    public void setAlertmessage(String alertmessage) {
        this.alertmessage = alertmessage;
    }

    public Integer getAlertlevel() {
        return alertlevel;
    }

    public void setAlertlevel(Integer alertlevel) {
        this.alertlevel = alertlevel;
    }

    public String getDispoflag() {
        return dispoflag;
    }

    public void setDispoflag(String dispoflag) {
        this.dispoflag = dispoflag;
    }

    public String getDispofreetext() {
        return dispofreetext;
    }

    public void setDispofreetext(String dispofreetext) {
        this.dispofreetext = dispofreetext;
    }

    public String getTdcno() {
        return tdcno;
    }

    public void setTdcno(String tdcno) {
        this.tdcno = tdcno;
    }

    public Integer getTdcreleaseind() {
        return tdcreleaseind;
    }

    public void setTdcreleaseind(Integer tdcreleaseind) {
        this.tdcreleaseind = tdcreleaseind;
    }

    public Integer getJrnlno() {
        return jrnlno;
    }

    public void setJrnlno(Integer jrnlno) {
        this.jrnlno = jrnlno;
    }

    public Integer getXbelowmin() {
        return xbelowmin;
    }

    public void setXbelowmin(Integer xbelowmin) {
        this.xbelowmin = xbelowmin;
    }

    public Integer getXreturnchk() {
        return xreturnchk;
    }

    public void setXreturnchk(Integer xreturnchk) {
        this.xreturnchk = xreturnchk;
    }

    public BigDecimal getInt4credit() {
        return int4credit;
    }

    public void setInt4credit(BigDecimal int4credit) {
        this.int4credit = int4credit;
    }

    public BigDecimal getPrevmonthadb() {
        return prevmonthadb;
    }

    public void setPrevmonthadb(BigDecimal prevmonthadb) {
        this.prevmonthadb = prevmonthadb;
    }

    public BigDecimal getPrevmtdint() {
        return prevmtdint;
    }

    public void setPrevmtdint(BigDecimal prevmtdint) {
        this.prevmtdint = prevmtdint;
    }

    public BigDecimal getPrevint4credit() {
        return prevint4credit;
    }

    public void setPrevint4credit(BigDecimal prevint4credit) {
        this.prevint4credit = prevint4credit;
    }

    public Date getLasttxdate() {
        return lasttxdate;
    }

    public void setLasttxdate(Date lasttxdate) {
        this.lasttxdate = lasttxdate;
    }

    public String getLasttxcode() {
        return lasttxcode;
    }

    public void setLasttxcode(String lasttxcode) {
        this.lasttxcode = lasttxcode;
    }

    public Integer getMinbalctr() {
        return minbalctr;
    }

    public void setMinbalctr(Integer minbalctr) {
        this.minbalctr = minbalctr;
    }

    public Integer getChk4clr() {
        return chk4clr;
    }

    public void setChk4clr(Integer chk4clr) {
        this.chk4clr = chk4clr;
    }

    public BigDecimal getAdbavailbal() {
        return adbavailbal;
    }

    public void setAdbavailbal(BigDecimal adbavailbal) {
        this.adbavailbal = adbavailbal;
    }

    public BigDecimal getAdbbookbal() {
        return adbbookbal;
    }

    public void setAdbbookbal(BigDecimal adbbookbal) {
        this.adbbookbal = adbbookbal;
    }

    public BigDecimal getEomadbavailbal() {
        return eomadbavailbal;
    }

    public void setEomadbavailbal(BigDecimal eomadbavailbal) {
        this.eomadbavailbal = eomadbavailbal;
    }

    public BigDecimal getEomadbbookbal() {
        return eomadbbookbal;
    }

    public void setEomadbbookbal(BigDecimal eomadbbookbal) {
        this.eomadbbookbal = eomadbbookbal;
    }

    public BigDecimal getEoyadbavailbal() {
        return eoyadbavailbal;
    }

    public void setEoyadbavailbal(BigDecimal eoyadbavailbal) {
        this.eoyadbavailbal = eoyadbavailbal;
    }

    public BigDecimal getEoyadbbookbal() {
        return eoyadbbookbal;
    }

    public void setEoyadbbookbal(BigDecimal eoyadbbookbal) {
        this.eoyadbbookbal = eoyadbbookbal;
    }

    public BigDecimal getPassbookbal() {
        return passbookbal;
    }

    public void setPassbookbal(BigDecimal passbookbal) {
        this.passbookbal = passbookbal;
    }

    public BigDecimal getYtdadbavailbal() {
        return ytdadbavailbal;
    }

    public void setYtdadbavailbal(BigDecimal ytdadbavailbal) {
        this.ytdadbavailbal = ytdadbavailbal;
    }

    public BigDecimal getYtdaddbookbal() {
        return ytdaddbookbal;
    }

    public void setYtdaddbookbal(BigDecimal ytdaddbookbal) {
        this.ytdaddbookbal = ytdaddbookbal;
    }

    public BigDecimal getYtdaccumcredit() {
        return ytdaccumcredit;
    }

    public void setYtdaccumcredit(BigDecimal ytdaccumcredit) {
        this.ytdaccumcredit = ytdaccumcredit;
    }

    public BigDecimal getYtdaccumdebit() {
        return ytdaccumdebit;
    }

    public void setYtdaccumdebit(BigDecimal ytdaccumdebit) {
        this.ytdaccumdebit = ytdaccumdebit;
    }

    public BigDecimal getYtdintearned() {
        return ytdintearned;
    }

    public void setYtdintearned(BigDecimal ytdintearned) {
        this.ytdintearned = ytdintearned;
    }

    public BigDecimal getMtdintearned() {
        return mtdintearned;
    }

    public void setMtdintearned(BigDecimal mtdintearned) {
        this.mtdintearned = mtdintearned;
    }

    public BigDecimal getEarmarkbal() {
        return earmarkbal;
    }

    public void setEarmarkbal(BigDecimal earmarkbal) {
        this.earmarkbal = earmarkbal;
    }

    public Integer getFreezeind() {
        return freezeind;
    }

    public void setFreezeind(Integer freezeind) {
        this.freezeind = freezeind;
    }

    public Date getFreezeexpdt() {
        return freezeexpdt;
    }

    public void setFreezeexpdt(Date freezeexpdt) {
        this.freezeexpdt = freezeexpdt;
    }

    public Integer getFreezeid() {
        return freezeid;
    }

    public void setFreezeid(Integer freezeid) {
        this.freezeid = freezeid;
    }

    public String getRegioncode() {
        return regioncode;
    }

    public void setRegioncode(String regioncode) {
        this.regioncode = regioncode;
    }

    public String getAreacode() {
        return areacode;
    }

    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    public BigDecimal getBpapproved() {
        return bpapproved;
    }

    public void setBpapproved(BigDecimal bpapproved) {
        this.bpapproved = bpapproved;
    }

    public Date getBpexpirydt() {
        return bpexpirydt;
    }

    public void setBpexpirydt(Date bpexpirydt) {
        this.bpexpirydt = bpexpirydt;
    }

    public String getBpstatus() {
        return bpstatus;
    }

    public void setBpstatus(String bpstatus) {
        this.bpstatus = bpstatus;
    }

    public BigDecimal getBpavailed() {
        return bpavailed;
    }

    public void setBpavailed(BigDecimal bpavailed) {
        this.bpavailed = bpavailed;
    }

    public BigDecimal getGarnishedbal() {
        return garnishedbal;
    }

    public void setGarnishedbal(BigDecimal garnishedbal) {
        this.garnishedbal = garnishedbal;
    }

    public BigDecimal getAccumbook() {
        return accumbook;
    }

    public void setAccumbook(BigDecimal accumbook) {
        this.accumbook = accumbook;
    }

    public BigDecimal getAccumavail() {
        return accumavail;
    }

    public void setAccumavail(BigDecimal accumavail) {
        this.accumavail = accumavail;
    }

    public String getSoadispo() {
        return soadispo;
    }

    public void setSoadispo(String soadispo) {
        this.soadispo = soadispo;
    }

    public String getSolicitingofficer() {
        return solicitingofficer;
    }

    public void setSolicitingofficer(String solicitingofficer) {
        this.solicitingofficer = solicitingofficer;
    }

    public String getReferralofficer() {
        return referralofficer;
    }

    public void setReferralofficer(String referralofficer) {
        this.referralofficer = referralofficer;
    }

    public String getCampaign() {
        return campaign;
    }

    public void setCampaign(String campaign) {
        this.campaign = campaign;
    }

    public String getDeliverydispo() {
        return deliverydispo;
    }

    public void setDeliverydispo(String deliverydispo) {
        this.deliverydispo = deliverydispo;
    }

    public String getAddressdispo() {
        return addressdispo;
    }

    public void setAddressdispo(String addressdispo) {
        this.addressdispo = addressdispo;
    }

    public Integer getWithdrawcount() {
        return withdrawcount;
    }

    public void setWithdrawcount(Integer withdrawcount) {
        this.withdrawcount = withdrawcount;
    }

    public Boolean getMaxwithdrawind() {
        return maxwithdrawind;
    }

    public void setMaxwithdrawind(Boolean maxwithdrawind) {
        this.maxwithdrawind = maxwithdrawind;
    }

    public String getMaxwithdrawfreq() {
        return maxwithdrawfreq;
    }

    public void setMaxwithdrawfreq(String maxwithdrawfreq) {
        this.maxwithdrawfreq = maxwithdrawfreq;
    }

    public Date getMaxwithdrawdate() {
        return maxwithdrawdate;
    }

    public void setMaxwithdrawdate(Date maxwithdrawdate) {
        this.maxwithdrawdate = maxwithdrawdate;
    }

    public BigDecimal getAddonrate() {
        return addonrate;
    }

    public void setAddonrate(BigDecimal addonrate) {
        this.addonrate = addonrate;
    }

    public Boolean getInitialdeposit() {
        return initialdeposit;
    }

    public void setInitialdeposit(Boolean initialdeposit) {
        this.initialdeposit = initialdeposit;
    }

    public String getSigbasecode() {
        return sigbasecode;
    }

    public void setSigbasecode(String sigbasecode) {
        this.sigbasecode = sigbasecode;
    }

    public String getSigfilename() {
        return sigfilename;
    }

    public void setSigfilename(String sigfilename) {
        this.sigfilename = sigfilename;
    }

    public BigDecimal getInterestbalance() {
        return interestbalance;
    }

    public void setInterestbalance(BigDecimal interestbalance) {
        this.interestbalance = interestbalance;
    }

    public BigDecimal getInterestpaid() {
        return interestpaid;
    }

    public void setInterestpaid(BigDecimal interestpaid) {
        this.interestpaid = interestpaid;
    }

    public BigDecimal getInterestearned() {
        return interestearned;
    }

    public void setInterestearned(BigDecimal interestearned) {
        this.interestearned = interestearned;
    }

}
