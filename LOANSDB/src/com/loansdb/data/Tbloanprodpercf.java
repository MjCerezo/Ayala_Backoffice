
package com.loansdb.data;



/**
 *  LOANSDB.Tbloanprodpercf
 *  10/13/2020 10:21:35
 * 
 */
public class Tbloanprodpercf {

    private TbloanprodpercfId id;
    private String repaymentcode;
    private String productname;
    private String facilityname;
    private String repaymenttype;

    public TbloanprodpercfId getId() {
        return id;
    }

    public void setId(TbloanprodpercfId id) {
        this.id = id;
    }

    public String getRepaymentcode() {
        return repaymentcode;
    }

    public void setRepaymentcode(String repaymentcode) {
        this.repaymentcode = repaymentcode;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getFacilityname() {
        return facilityname;
    }

    public void setFacilityname(String facilityname) {
        this.facilityname = facilityname;
    }

    public String getRepaymenttype() {
        return repaymenttype;
    }

    public void setRepaymenttype(String repaymenttype) {
        this.repaymenttype = repaymenttype;
    }

}
