package com.casa.util.forms;

import java.math.BigDecimal;

public class ProductMatrixForm {

	private Integer id;
	private String productgroup;
	private String prodcode;
	private String prodname;
	private String prodsname;
	private String currency;
	private String username;
	private BigDecimal reqinitialdepamt;
	private BigDecimal rbmminmainbal;
	
	
	public String getProductgroup() {
		return productgroup;
	}
	public void setProductgroup(String productgroup) {
		this.productgroup = productgroup;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getReqinitialdepamt() {
		return reqinitialdepamt;
	}
	public BigDecimal getRbmminmainbal() {
		return rbmminmainbal;
	}
	public void setReqinitialdepamt(BigDecimal reqinitialdepamt) {
		this.reqinitialdepamt = reqinitialdepamt;
	}
	public void setRbmminmainbal(BigDecimal rbmminmainbal) {
		this.rbmminmainbal = rbmminmainbal;
	}
	
	
}
