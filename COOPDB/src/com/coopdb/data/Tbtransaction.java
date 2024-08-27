
package com.coopdb.data;



/**
 *  COOPDB.Tbtransaction
 *  08/27/2024 14:22:57
 * 
 */
public class Tbtransaction {

    private Integer id;
    private String txcode;
    private String txname;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTxcode() {
        return txcode;
    }

    public void setTxcode(String txcode) {
        this.txcode = txcode;
    }

    public String getTxname() {
        return txname;
    }

    public void setTxname(String txname) {
        this.txname = txname;
    }

}
