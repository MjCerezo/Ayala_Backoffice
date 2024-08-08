package com.user.maintenance.form;

import java.math.BigDecimal;

public class UserListForm {

	private int id;
	private String username;
	private String name;
	private String userid;
	private String currency;
	private BigDecimal unitbalance;
	private String role;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public BigDecimal getUnitbalance() {
		return unitbalance;
	}
	public void setUnitbalance(BigDecimal unitbalance) {
		this.unitbalance = unitbalance;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
