
package com.loansdb.data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 *  LOANSDB.TbcireceivablesId
 *  03/05/2019 13:52:49
 * 
 */
public class TbcireceivablesId
    implements Serializable
{

    private Integer id;
    private String checkno;
    private String referenceno;
    private String appno;
    private String cifno;
    private String tradecifno;
    private String tradetype;
    private String receivabletype;
    private String clientsuppliername;
    private String clientsupplieraddress;
    private String contactperson;
    private String contactposition;
    private String emailaddress;
    private String mobile1;
    private String mobile2;
    private String landline1;
    private String landline2;
    private String accreditationstatus;
    private String bankname;
    private String branch;
    private Date checkdate;
    private BigDecimal amount;
    private String remarks;
    private String vehicletype;
    private String make;
    private String model;
    private String chassisno;
    private String engineno;
    private String color;
    private String fuel;
    private String location;
    private String plateno;
    private String conductionstickerno;
    private String checkstatus;
    private String informant1;
    private String informant1position;
    private String informant1contactno;
    private String informant2;
    private String informant2position;
    private String informant2contactno;
    private String invremarks;
    private String paymentterms;
    private Date paymentdate;
    private String postatus;
    private String deliverysched;
    private String paymentoption;
    private String mileage;
    private BigDecimal retailprice;
    private BigDecimal marketresistance;
    private BigDecimal lessmarketresistance;
    private BigDecimal total;
    private BigDecimal loanablevaluepercent;
    private BigDecimal loanablevaluephp;

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof TbcireceivablesId)) {
            return false;
        }
        TbcireceivablesId other = ((TbcireceivablesId) o);
        if (this.id == null) {
            if (other.id!= null) {
                return false;
            }
        } else {
            if (!this.id.equals(other.id)) {
                return false;
            }
        }
        if (this.checkno == null) {
            if (other.checkno!= null) {
                return false;
            }
        } else {
            if (!this.checkno.equals(other.checkno)) {
                return false;
            }
        }
        if (this.referenceno == null) {
            if (other.referenceno!= null) {
                return false;
            }
        } else {
            if (!this.referenceno.equals(other.referenceno)) {
                return false;
            }
        }
        if (this.appno == null) {
            if (other.appno!= null) {
                return false;
            }
        } else {
            if (!this.appno.equals(other.appno)) {
                return false;
            }
        }
        if (this.cifno == null) {
            if (other.cifno!= null) {
                return false;
            }
        } else {
            if (!this.cifno.equals(other.cifno)) {
                return false;
            }
        }
        if (this.tradecifno == null) {
            if (other.tradecifno!= null) {
                return false;
            }
        } else {
            if (!this.tradecifno.equals(other.tradecifno)) {
                return false;
            }
        }
        if (this.tradetype == null) {
            if (other.tradetype!= null) {
                return false;
            }
        } else {
            if (!this.tradetype.equals(other.tradetype)) {
                return false;
            }
        }
        if (this.receivabletype == null) {
            if (other.receivabletype!= null) {
                return false;
            }
        } else {
            if (!this.receivabletype.equals(other.receivabletype)) {
                return false;
            }
        }
        if (this.clientsuppliername == null) {
            if (other.clientsuppliername!= null) {
                return false;
            }
        } else {
            if (!this.clientsuppliername.equals(other.clientsuppliername)) {
                return false;
            }
        }
        if (this.clientsupplieraddress == null) {
            if (other.clientsupplieraddress!= null) {
                return false;
            }
        } else {
            if (!this.clientsupplieraddress.equals(other.clientsupplieraddress)) {
                return false;
            }
        }
        if (this.contactperson == null) {
            if (other.contactperson!= null) {
                return false;
            }
        } else {
            if (!this.contactperson.equals(other.contactperson)) {
                return false;
            }
        }
        if (this.contactposition == null) {
            if (other.contactposition!= null) {
                return false;
            }
        } else {
            if (!this.contactposition.equals(other.contactposition)) {
                return false;
            }
        }
        if (this.emailaddress == null) {
            if (other.emailaddress!= null) {
                return false;
            }
        } else {
            if (!this.emailaddress.equals(other.emailaddress)) {
                return false;
            }
        }
        if (this.mobile1 == null) {
            if (other.mobile1 != null) {
                return false;
            }
        } else {
            if (!this.mobile1 .equals(other.mobile1)) {
                return false;
            }
        }
        if (this.mobile2 == null) {
            if (other.mobile2 != null) {
                return false;
            }
        } else {
            if (!this.mobile2 .equals(other.mobile2)) {
                return false;
            }
        }
        if (this.landline1 == null) {
            if (other.landline1 != null) {
                return false;
            }
        } else {
            if (!this.landline1 .equals(other.landline1)) {
                return false;
            }
        }
        if (this.landline2 == null) {
            if (other.landline2 != null) {
                return false;
            }
        } else {
            if (!this.landline2 .equals(other.landline2)) {
                return false;
            }
        }
        if (this.accreditationstatus == null) {
            if (other.accreditationstatus!= null) {
                return false;
            }
        } else {
            if (!this.accreditationstatus.equals(other.accreditationstatus)) {
                return false;
            }
        }
        if (this.bankname == null) {
            if (other.bankname!= null) {
                return false;
            }
        } else {
            if (!this.bankname.equals(other.bankname)) {
                return false;
            }
        }
        if (this.branch == null) {
            if (other.branch!= null) {
                return false;
            }
        } else {
            if (!this.branch.equals(other.branch)) {
                return false;
            }
        }
        if (this.checkdate == null) {
            if (other.checkdate!= null) {
                return false;
            }
        } else {
            if (!this.checkdate.equals(other.checkdate)) {
                return false;
            }
        }
        if (this.amount == null) {
            if (other.amount!= null) {
                return false;
            }
        } else {
            if (!this.amount.equals(other.amount)) {
                return false;
            }
        }
        if (this.remarks == null) {
            if (other.remarks!= null) {
                return false;
            }
        } else {
            if (!this.remarks.equals(other.remarks)) {
                return false;
            }
        }
        if (this.vehicletype == null) {
            if (other.vehicletype!= null) {
                return false;
            }
        } else {
            if (!this.vehicletype.equals(other.vehicletype)) {
                return false;
            }
        }
        if (this.make == null) {
            if (other.make!= null) {
                return false;
            }
        } else {
            if (!this.make.equals(other.make)) {
                return false;
            }
        }
        if (this.model == null) {
            if (other.model!= null) {
                return false;
            }
        } else {
            if (!this.model.equals(other.model)) {
                return false;
            }
        }
        if (this.chassisno == null) {
            if (other.chassisno!= null) {
                return false;
            }
        } else {
            if (!this.chassisno.equals(other.chassisno)) {
                return false;
            }
        }
        if (this.engineno == null) {
            if (other.engineno!= null) {
                return false;
            }
        } else {
            if (!this.engineno.equals(other.engineno)) {
                return false;
            }
        }
        if (this.color == null) {
            if (other.color!= null) {
                return false;
            }
        } else {
            if (!this.color.equals(other.color)) {
                return false;
            }
        }
        if (this.fuel == null) {
            if (other.fuel!= null) {
                return false;
            }
        } else {
            if (!this.fuel.equals(other.fuel)) {
                return false;
            }
        }
        if (this.location == null) {
            if (other.location!= null) {
                return false;
            }
        } else {
            if (!this.location.equals(other.location)) {
                return false;
            }
        }
        if (this.plateno == null) {
            if (other.plateno!= null) {
                return false;
            }
        } else {
            if (!this.plateno.equals(other.plateno)) {
                return false;
            }
        }
        if (this.conductionstickerno == null) {
            if (other.conductionstickerno!= null) {
                return false;
            }
        } else {
            if (!this.conductionstickerno.equals(other.conductionstickerno)) {
                return false;
            }
        }
        if (this.checkstatus == null) {
            if (other.checkstatus!= null) {
                return false;
            }
        } else {
            if (!this.checkstatus.equals(other.checkstatus)) {
                return false;
            }
        }
        if (this.informant1 == null) {
            if (other.informant1 != null) {
                return false;
            }
        } else {
            if (!this.informant1 .equals(other.informant1)) {
                return false;
            }
        }
        if (this.informant1position == null) {
            if (other.informant1position!= null) {
                return false;
            }
        } else {
            if (!this.informant1position.equals(other.informant1position)) {
                return false;
            }
        }
        if (this.informant1contactno == null) {
            if (other.informant1contactno!= null) {
                return false;
            }
        } else {
            if (!this.informant1contactno.equals(other.informant1contactno)) {
                return false;
            }
        }
        if (this.informant2 == null) {
            if (other.informant2 != null) {
                return false;
            }
        } else {
            if (!this.informant2 .equals(other.informant2)) {
                return false;
            }
        }
        if (this.informant2position == null) {
            if (other.informant2position!= null) {
                return false;
            }
        } else {
            if (!this.informant2position.equals(other.informant2position)) {
                return false;
            }
        }
        if (this.informant2contactno == null) {
            if (other.informant2contactno!= null) {
                return false;
            }
        } else {
            if (!this.informant2contactno.equals(other.informant2contactno)) {
                return false;
            }
        }
        if (this.invremarks == null) {
            if (other.invremarks!= null) {
                return false;
            }
        } else {
            if (!this.invremarks.equals(other.invremarks)) {
                return false;
            }
        }
        if (this.paymentterms == null) {
            if (other.paymentterms!= null) {
                return false;
            }
        } else {
            if (!this.paymentterms.equals(other.paymentterms)) {
                return false;
            }
        }
        if (this.paymentdate == null) {
            if (other.paymentdate!= null) {
                return false;
            }
        } else {
            if (!this.paymentdate.equals(other.paymentdate)) {
                return false;
            }
        }
        if (this.postatus == null) {
            if (other.postatus!= null) {
                return false;
            }
        } else {
            if (!this.postatus.equals(other.postatus)) {
                return false;
            }
        }
        if (this.deliverysched == null) {
            if (other.deliverysched!= null) {
                return false;
            }
        } else {
            if (!this.deliverysched.equals(other.deliverysched)) {
                return false;
            }
        }
        if (this.paymentoption == null) {
            if (other.paymentoption!= null) {
                return false;
            }
        } else {
            if (!this.paymentoption.equals(other.paymentoption)) {
                return false;
            }
        }
        if (this.mileage == null) {
            if (other.mileage!= null) {
                return false;
            }
        } else {
            if (!this.mileage.equals(other.mileage)) {
                return false;
            }
        }
        if (this.retailprice == null) {
            if (other.retailprice!= null) {
                return false;
            }
        } else {
            if (!this.retailprice.equals(other.retailprice)) {
                return false;
            }
        }
        if (this.marketresistance == null) {
            if (other.marketresistance!= null) {
                return false;
            }
        } else {
            if (!this.marketresistance.equals(other.marketresistance)) {
                return false;
            }
        }
        if (this.lessmarketresistance == null) {
            if (other.lessmarketresistance!= null) {
                return false;
            }
        } else {
            if (!this.lessmarketresistance.equals(other.lessmarketresistance)) {
                return false;
            }
        }
        if (this.total == null) {
            if (other.total!= null) {
                return false;
            }
        } else {
            if (!this.total.equals(other.total)) {
                return false;
            }
        }
        if (this.loanablevaluepercent == null) {
            if (other.loanablevaluepercent!= null) {
                return false;
            }
        } else {
            if (!this.loanablevaluepercent.equals(other.loanablevaluepercent)) {
                return false;
            }
        }
        if (this.loanablevaluephp == null) {
            if (other.loanablevaluephp!= null) {
                return false;
            }
        } else {
            if (!this.loanablevaluephp.equals(other.loanablevaluephp)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int rtn = 17;
        rtn = (rtn* 37);
        if (this.id!= null) {
            rtn = (rtn + this.id.hashCode());
        }
        rtn = (rtn* 37);
        if (this.checkno!= null) {
            rtn = (rtn + this.checkno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.referenceno!= null) {
            rtn = (rtn + this.referenceno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.appno!= null) {
            rtn = (rtn + this.appno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.cifno!= null) {
            rtn = (rtn + this.cifno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.tradecifno!= null) {
            rtn = (rtn + this.tradecifno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.tradetype!= null) {
            rtn = (rtn + this.tradetype.hashCode());
        }
        rtn = (rtn* 37);
        if (this.receivabletype!= null) {
            rtn = (rtn + this.receivabletype.hashCode());
        }
        rtn = (rtn* 37);
        if (this.clientsuppliername!= null) {
            rtn = (rtn + this.clientsuppliername.hashCode());
        }
        rtn = (rtn* 37);
        if (this.clientsupplieraddress!= null) {
            rtn = (rtn + this.clientsupplieraddress.hashCode());
        }
        rtn = (rtn* 37);
        if (this.contactperson!= null) {
            rtn = (rtn + this.contactperson.hashCode());
        }
        rtn = (rtn* 37);
        if (this.contactposition!= null) {
            rtn = (rtn + this.contactposition.hashCode());
        }
        rtn = (rtn* 37);
        if (this.emailaddress!= null) {
            rtn = (rtn + this.emailaddress.hashCode());
        }
        rtn = (rtn* 37);
        if (this.mobile1 != null) {
            rtn = (rtn + this.mobile1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.mobile2 != null) {
            rtn = (rtn + this.mobile2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.landline1 != null) {
            rtn = (rtn + this.landline1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.landline2 != null) {
            rtn = (rtn + this.landline2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.accreditationstatus!= null) {
            rtn = (rtn + this.accreditationstatus.hashCode());
        }
        rtn = (rtn* 37);
        if (this.bankname!= null) {
            rtn = (rtn + this.bankname.hashCode());
        }
        rtn = (rtn* 37);
        if (this.branch!= null) {
            rtn = (rtn + this.branch.hashCode());
        }
        rtn = (rtn* 37);
        if (this.checkdate!= null) {
            rtn = (rtn + this.checkdate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.amount!= null) {
            rtn = (rtn + this.amount.hashCode());
        }
        rtn = (rtn* 37);
        if (this.remarks!= null) {
            rtn = (rtn + this.remarks.hashCode());
        }
        rtn = (rtn* 37);
        if (this.vehicletype!= null) {
            rtn = (rtn + this.vehicletype.hashCode());
        }
        rtn = (rtn* 37);
        if (this.make!= null) {
            rtn = (rtn + this.make.hashCode());
        }
        rtn = (rtn* 37);
        if (this.model!= null) {
            rtn = (rtn + this.model.hashCode());
        }
        rtn = (rtn* 37);
        if (this.chassisno!= null) {
            rtn = (rtn + this.chassisno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.engineno!= null) {
            rtn = (rtn + this.engineno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.color!= null) {
            rtn = (rtn + this.color.hashCode());
        }
        rtn = (rtn* 37);
        if (this.fuel!= null) {
            rtn = (rtn + this.fuel.hashCode());
        }
        rtn = (rtn* 37);
        if (this.location!= null) {
            rtn = (rtn + this.location.hashCode());
        }
        rtn = (rtn* 37);
        if (this.plateno!= null) {
            rtn = (rtn + this.plateno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.conductionstickerno!= null) {
            rtn = (rtn + this.conductionstickerno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.checkstatus!= null) {
            rtn = (rtn + this.checkstatus.hashCode());
        }
        rtn = (rtn* 37);
        if (this.informant1 != null) {
            rtn = (rtn + this.informant1 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.informant1position!= null) {
            rtn = (rtn + this.informant1position.hashCode());
        }
        rtn = (rtn* 37);
        if (this.informant1contactno!= null) {
            rtn = (rtn + this.informant1contactno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.informant2 != null) {
            rtn = (rtn + this.informant2 .hashCode());
        }
        rtn = (rtn* 37);
        if (this.informant2position!= null) {
            rtn = (rtn + this.informant2position.hashCode());
        }
        rtn = (rtn* 37);
        if (this.informant2contactno!= null) {
            rtn = (rtn + this.informant2contactno.hashCode());
        }
        rtn = (rtn* 37);
        if (this.invremarks!= null) {
            rtn = (rtn + this.invremarks.hashCode());
        }
        rtn = (rtn* 37);
        if (this.paymentterms!= null) {
            rtn = (rtn + this.paymentterms.hashCode());
        }
        rtn = (rtn* 37);
        if (this.paymentdate!= null) {
            rtn = (rtn + this.paymentdate.hashCode());
        }
        rtn = (rtn* 37);
        if (this.postatus!= null) {
            rtn = (rtn + this.postatus.hashCode());
        }
        rtn = (rtn* 37);
        if (this.deliverysched!= null) {
            rtn = (rtn + this.deliverysched.hashCode());
        }
        rtn = (rtn* 37);
        if (this.paymentoption!= null) {
            rtn = (rtn + this.paymentoption.hashCode());
        }
        rtn = (rtn* 37);
        if (this.mileage!= null) {
            rtn = (rtn + this.mileage.hashCode());
        }
        rtn = (rtn* 37);
        if (this.retailprice!= null) {
            rtn = (rtn + this.retailprice.hashCode());
        }
        rtn = (rtn* 37);
        if (this.marketresistance!= null) {
            rtn = (rtn + this.marketresistance.hashCode());
        }
        rtn = (rtn* 37);
        if (this.lessmarketresistance!= null) {
            rtn = (rtn + this.lessmarketresistance.hashCode());
        }
        rtn = (rtn* 37);
        if (this.total!= null) {
            rtn = (rtn + this.total.hashCode());
        }
        rtn = (rtn* 37);
        if (this.loanablevaluepercent!= null) {
            rtn = (rtn + this.loanablevaluepercent.hashCode());
        }
        rtn = (rtn* 37);
        if (this.loanablevaluephp!= null) {
            rtn = (rtn + this.loanablevaluephp.hashCode());
        }
        return rtn;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCheckno() {
        return checkno;
    }

    public void setCheckno(String checkno) {
        this.checkno = checkno;
    }

    public String getReferenceno() {
        return referenceno;
    }

    public void setReferenceno(String referenceno) {
        this.referenceno = referenceno;
    }

    public String getAppno() {
        return appno;
    }

    public void setAppno(String appno) {
        this.appno = appno;
    }

    public String getCifno() {
        return cifno;
    }

    public void setCifno(String cifno) {
        this.cifno = cifno;
    }

    public String getTradecifno() {
        return tradecifno;
    }

    public void setTradecifno(String tradecifno) {
        this.tradecifno = tradecifno;
    }

    public String getTradetype() {
        return tradetype;
    }

    public void setTradetype(String tradetype) {
        this.tradetype = tradetype;
    }

    public String getReceivabletype() {
        return receivabletype;
    }

    public void setReceivabletype(String receivabletype) {
        this.receivabletype = receivabletype;
    }

    public String getClientsuppliername() {
        return clientsuppliername;
    }

    public void setClientsuppliername(String clientsuppliername) {
        this.clientsuppliername = clientsuppliername;
    }

    public String getClientsupplieraddress() {
        return clientsupplieraddress;
    }

    public void setClientsupplieraddress(String clientsupplieraddress) {
        this.clientsupplieraddress = clientsupplieraddress;
    }

    public String getContactperson() {
        return contactperson;
    }

    public void setContactperson(String contactperson) {
        this.contactperson = contactperson;
    }

    public String getContactposition() {
        return contactposition;
    }

    public void setContactposition(String contactposition) {
        this.contactposition = contactposition;
    }

    public String getEmailaddress() {
        return emailaddress;
    }

    public void setEmailaddress(String emailaddress) {
        this.emailaddress = emailaddress;
    }

    public String getMobile1() {
        return mobile1;
    }

    public void setMobile1(String mobile1) {
        this.mobile1 = mobile1;
    }

    public String getMobile2() {
        return mobile2;
    }

    public void setMobile2(String mobile2) {
        this.mobile2 = mobile2;
    }

    public String getLandline1() {
        return landline1;
    }

    public void setLandline1(String landline1) {
        this.landline1 = landline1;
    }

    public String getLandline2() {
        return landline2;
    }

    public void setLandline2(String landline2) {
        this.landline2 = landline2;
    }

    public String getAccreditationstatus() {
        return accreditationstatus;
    }

    public void setAccreditationstatus(String accreditationstatus) {
        this.accreditationstatus = accreditationstatus;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Date getCheckdate() {
        return checkdate;
    }

    public void setCheckdate(Date checkdate) {
        this.checkdate = checkdate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getChassisno() {
        return chassisno;
    }

    public void setChassisno(String chassisno) {
        this.chassisno = chassisno;
    }

    public String getEngineno() {
        return engineno;
    }

    public void setEngineno(String engineno) {
        this.engineno = engineno;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPlateno() {
        return plateno;
    }

    public void setPlateno(String plateno) {
        this.plateno = plateno;
    }

    public String getConductionstickerno() {
        return conductionstickerno;
    }

    public void setConductionstickerno(String conductionstickerno) {
        this.conductionstickerno = conductionstickerno;
    }

    public String getCheckstatus() {
        return checkstatus;
    }

    public void setCheckstatus(String checkstatus) {
        this.checkstatus = checkstatus;
    }

    public String getInformant1() {
        return informant1;
    }

    public void setInformant1(String informant1) {
        this.informant1 = informant1;
    }

    public String getInformant1position() {
        return informant1position;
    }

    public void setInformant1position(String informant1position) {
        this.informant1position = informant1position;
    }

    public String getInformant1contactno() {
        return informant1contactno;
    }

    public void setInformant1contactno(String informant1contactno) {
        this.informant1contactno = informant1contactno;
    }

    public String getInformant2() {
        return informant2;
    }

    public void setInformant2(String informant2) {
        this.informant2 = informant2;
    }

    public String getInformant2position() {
        return informant2position;
    }

    public void setInformant2position(String informant2position) {
        this.informant2position = informant2position;
    }

    public String getInformant2contactno() {
        return informant2contactno;
    }

    public void setInformant2contactno(String informant2contactno) {
        this.informant2contactno = informant2contactno;
    }

    public String getInvremarks() {
        return invremarks;
    }

    public void setInvremarks(String invremarks) {
        this.invremarks = invremarks;
    }

    public String getPaymentterms() {
        return paymentterms;
    }

    public void setPaymentterms(String paymentterms) {
        this.paymentterms = paymentterms;
    }

    public Date getPaymentdate() {
        return paymentdate;
    }

    public void setPaymentdate(Date paymentdate) {
        this.paymentdate = paymentdate;
    }

    public String getPostatus() {
        return postatus;
    }

    public void setPostatus(String postatus) {
        this.postatus = postatus;
    }

    public String getDeliverysched() {
        return deliverysched;
    }

    public void setDeliverysched(String deliverysched) {
        this.deliverysched = deliverysched;
    }

    public String getPaymentoption() {
        return paymentoption;
    }

    public void setPaymentoption(String paymentoption) {
        this.paymentoption = paymentoption;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public BigDecimal getRetailprice() {
        return retailprice;
    }

    public void setRetailprice(BigDecimal retailprice) {
        this.retailprice = retailprice;
    }

    public BigDecimal getMarketresistance() {
        return marketresistance;
    }

    public void setMarketresistance(BigDecimal marketresistance) {
        this.marketresistance = marketresistance;
    }

    public BigDecimal getLessmarketresistance() {
        return lessmarketresistance;
    }

    public void setLessmarketresistance(BigDecimal lessmarketresistance) {
        this.lessmarketresistance = lessmarketresistance;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getLoanablevaluepercent() {
        return loanablevaluepercent;
    }

    public void setLoanablevaluepercent(BigDecimal loanablevaluepercent) {
        this.loanablevaluepercent = loanablevaluepercent;
    }

    public BigDecimal getLoanablevaluephp() {
        return loanablevaluephp;
    }

    public void setLoanablevaluephp(BigDecimal loanablevaluephp) {
        this.loanablevaluephp = loanablevaluephp;
    }

}
