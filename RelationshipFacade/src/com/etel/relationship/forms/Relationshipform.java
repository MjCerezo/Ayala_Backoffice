package com.etel.relationship.forms;

public class Relationshipform {

	private String relatedcifno;
	
	private String relatedcifname;
	
	private String relationshipcode;
	
	private boolean isautogenerated;

	private Integer relid;
	
	public Integer getRelid() {
		return relid;
	}

	public void setRelid(Integer relid) {
		this.relid = relid;
	}

	public String getRelatedcifno() {
		return relatedcifno;
	}

	public String getRelatedcifname() {
		return relatedcifname;
	}

	public String getRelationshipcode() {
		return relationshipcode;
	}

	public boolean isIsautogenerated() {
		return isautogenerated;
	}

	public void setRelatedcifno(String relatedcifno) {
		this.relatedcifno = relatedcifno;
	}

	public void setRelatedcifname(String relatedcifname) {
		this.relatedcifname = relatedcifname;
	}

	public void setRelationshipcode(String relationshipcode) {
		this.relationshipcode = relationshipcode;
	}

	public void setIsautogenerated(boolean isautogenerated) {
		this.isautogenerated = isautogenerated;
	}


}
