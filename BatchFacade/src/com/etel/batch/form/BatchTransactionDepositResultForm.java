/**
 * 
 */
package com.etel.batch.form;

import java.util.List;

/**
 * @author ETEL-LAPTOP07
 *
 */
public class BatchTransactionDepositResultForm {
	private List<BatchTransactionDepositForm> list;
	private String result;
	private String message;

	public List<BatchTransactionDepositForm> getList() {
		return list;
	}

	public void setList(List<BatchTransactionDepositForm> list) {
		this.list = list;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
