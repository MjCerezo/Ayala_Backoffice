/**
 * 
 */
package com.etel.override.form;

import com.coopdb.data.Tboverridematrix;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class OverrideResponseForm {

	Tboverridematrix matrix;
	private String overridemessage;
	public Tboverridematrix getMatrix() {
		return matrix;
	}
	public void setMatrix(Tboverridematrix matrix) {
		this.matrix = matrix;
	}
	public String getOverridemessage() {
		return overridemessage;
	}
	public void setOverridemessage(String overridemessage) {
		this.overridemessage = overridemessage;
	}
	public OverrideResponseForm(Tboverridematrix matrix, String overridemessage) {
		super();
		this.matrix = matrix;
		this.overridemessage = overridemessage;
	}
}
