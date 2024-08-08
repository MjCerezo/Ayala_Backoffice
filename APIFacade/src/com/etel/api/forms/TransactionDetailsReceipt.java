package com.etel.api.forms;

public class TransactionDetailsReceipt {
	private String LineDetails;
	private String GLType;
	private String InvoiceNbr;
	private String InvoiceAmt;
	private String PaymentAmt;
	public String getLineDetails() {
		return LineDetails;
	}
	public void setLineDetails(String lineDetails) {
		LineDetails = lineDetails;
	}
	public String getGLType() {
		return GLType;
	}
	public void setGLType(String gLType) {
		GLType = gLType;
	}
	public String getInvoiceNbr() {
		return InvoiceNbr;
	}
	public void setInvoiceNbr(String invoiceNbr) {
		InvoiceNbr = invoiceNbr;
	}
	public String getInvoiceAmt() {
		return InvoiceAmt;
	}
	public void setInvoiceAmt(String invoiceAmt) {
		InvoiceAmt = invoiceAmt;
	}
	public String getPaymentAmt() {
		return PaymentAmt;
	}
	public void setPaymentAmt(String paymentAmt) {
		PaymentAmt = paymentAmt;
	}
}