package com.etel.assignment.forms;

import java.util.List;

import com.cifsdb.data.Tbcifmain;

public class CIFAssignmentForm {

	
	private List<Tbcifmain> main;
	private Integer result = 0;
	
	public List<Tbcifmain> getMain() {
		return main;
	}
	public void setMain(List<Tbcifmain> main) {
		this.main = main;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
}
