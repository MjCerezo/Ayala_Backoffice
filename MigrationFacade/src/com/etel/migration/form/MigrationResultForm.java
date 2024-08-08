/**
 * 
 */
package com.etel.migration.form;

/**
 * @author ETEL-LAPTOP07
 *
 */
public class MigrationResultForm {

	String module;
	String result;

	public MigrationResultForm(String module, String result) {
		super();
		this.module = module;
		this.result = result;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
