package com.casa.user.forms;

import java.util.Date;

public class UserInfoForm {

	private int result;
	private String name;
	private String cifno;
	private Date dateofbirth;

	public int getResult() {
		return result;
	}

	public String getName() {
		return name;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCifno() {
		return cifno;
	}

	public void setCifno(String cifno) {
		this.cifno = cifno;
	}

	public Date getDateofbirth() {
		return dateofbirth;
	}

	public void setDateofbirth(Date dateofbirth) {
		this.dateofbirth = dateofbirth;
	}

	@Override
	public String toString() {
		return "UserInfoForm [result=" + result + ", name=" + name + ", cifno=" + cifno + ", dateofbirth=" + dateofbirth
				+ "]";
	}

}
