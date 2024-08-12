
package com.coopdb.data;

import java.math.BigDecimal;


/**
 *  COOPDB.Tbamountrange
 *  08/10/2024 21:24:57
 * 
 */
public class Tbamountrange {

    private Integer id;
    private String name;
    private BigDecimal minvalue;
    private BigDecimal maxvalue;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(BigDecimal minvalue) {
        this.minvalue = minvalue;
    }

    public BigDecimal getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(BigDecimal maxvalue) {
        this.maxvalue = maxvalue;
    }

}
