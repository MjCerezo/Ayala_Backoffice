
package com.coopdb.data;



/**
 *  COOPDB.Tbsigcard
 *  08/27/2024 14:22:57
 * 
 */
public class Tbsigcard {

    private Integer id;
    private String accountno;
    private String sigbasecode;
    private String sigfilename;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
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

}
