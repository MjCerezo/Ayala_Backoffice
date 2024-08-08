
package com.coopdb.data;



/**
 *  COOPDB.Tbcifpepinfo
 *  02/23/2023 13:04:33
 * 
 */
public class Tbcifpepinfo {

    private Integer id;
    private String cifno;
    private String type;
    private String govtype;
    private String govname;
    private String position;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGovtype() {
        return govtype;
    }

    public void setGovtype(String govtype) {
        this.govtype = govtype;
    }

    public String getGovname() {
        return govname;
    }

    public void setGovname(String govname) {
        this.govname = govname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
