package com.etel.otherinfo.fatca.forms;

public class FATCAViewForm {
	private String cifno;
	private String name;
	private String relationship;
	private boolean q1;
	private boolean q2;
	private boolean q3;
	private boolean q4;
	private boolean q5;
	private boolean q6;
	private String fatcastatus;
	
	public boolean isQ6() {
		return q6;
	}
	public void setQ6(boolean q6) {
		this.q6 = q6;
	}
	public String getCifno() {
		return cifno;
	}
	public void setCifno(String cifno) {
		this.cifno = cifno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public boolean isQ1() {
		return q1;
	}
	public void setQ1(boolean q1) {
		this.q1 = q1;
	}
	public boolean isQ2() {
		return q2;
	}
	public void setQ2(boolean q2) {
		this.q2 = q2;
	}
	public boolean isQ3() {
		return q3;
	}
	public void setQ3(boolean q3) {
		this.q3 = q3;
	}
	public boolean isQ4() {
		return q4;
	}
	public void setQ4(boolean q4) {
		this.q4 = q4;
	}
	public boolean isQ5() {
		return q5;
	}
	public void setQ5(boolean q5) {
		this.q5 = q5;
	}
	public String getFatcastatus() {
		return fatcastatus;
	}
	public void setFatcastatus(String fatcastatus) {
		this.fatcastatus = fatcastatus;
	}
	
	
}
