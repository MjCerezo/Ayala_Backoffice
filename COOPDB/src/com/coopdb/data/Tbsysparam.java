
package com.coopdb.data;



/**
 *  COOPDB.Tbsysparam
 *  08/10/2024 21:24:56
 * 
 */
public class Tbsysparam {

    private Integer id;
    private Integer txseq;
    private String seqyy;
    private String businessdt;
    private String nextbusinessdt;
    private Integer casaeod;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTxseq() {
        return txseq;
    }

    public void setTxseq(Integer txseq) {
        this.txseq = txseq;
    }

    public String getSeqyy() {
        return seqyy;
    }

    public void setSeqyy(String seqyy) {
        this.seqyy = seqyy;
    }

    public String getBusinessdt() {
        return businessdt;
    }

    public void setBusinessdt(String businessdt) {
        this.businessdt = businessdt;
    }

    public String getNextbusinessdt() {
        return nextbusinessdt;
    }

    public void setNextbusinessdt(String nextbusinessdt) {
        this.nextbusinessdt = nextbusinessdt;
    }

    public Integer getCasaeod() {
        return casaeod;
    }

    public void setCasaeod(Integer casaeod) {
        this.casaeod = casaeod;
    }

}
