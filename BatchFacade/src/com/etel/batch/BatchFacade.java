package com.etel.batch;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.etel.batch.form.BatchTransactionDepositForm;
import com.etel.batch.form.BatchTransactionLoanForm;
import com.etel.migration.form.MigrationResultForm;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.server.FileUploadResponse;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class. All public methods will be exposed to
 * the client. Their return values and parameters will be passed to the client
 * or taken from the client, respectively. This will be a singleton instance,
 * shared between all requests.
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL,
 * String, Exception). LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to
 * modify your log level. For info on these levels, look for tomcat/log4j
 * documentation
 */
@ExposeToClient
public class BatchFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public BatchFacade() {
		super(INFO);
	}

	BatchService srvc = new BatchServiceImpl();

	public FileUploadResponse uploadBatchFile(MultipartFile file) throws IOException {
		return srvc.uploadBatchFile(file);
	}

	public List<BatchTransactionDepositForm> readBatchTransactionDepositFile(String filepath, String txcode,
			Date valuedate, String reason) {
		return srvc.readBatchTransactionDepositFile(filepath, txcode, valuedate, reason);
	}

	public String postBatchTransactionDepositFile(List<BatchTransactionDepositForm> list, String txcode, Date valuedate,
			String reason, String filename) {
		return srvc.postBatchTransactionDepositFile(list, txcode, valuedate, reason, filename);
	}

	public List<BatchTransactionDepositForm> getBatchTransactionDepositResult(String batchtxrefno) {
		return srvc.getBatchTransactionDepositResult(batchtxrefno);
	}

	public List<BatchTransactionLoanForm> readBatchTransactionLoanFile(String filepath, String txcode, Date valuedate,
			String reason) {
		return srvc.readBatchTransactionLoanFile(filepath, txcode, valuedate, reason);
	}

	public String postBatchTransactionLoanFile(List<BatchTransactionLoanForm> list, String txcode, Date valuedate,
			String reason, String filename) {
		return srvc.postBatchTransactionLoanFile(list, txcode, valuedate, reason, filename);
	}

	public List<BatchTransactionLoanForm> getBatchTransactionLoanResult(String batchtxrefno) {
		return srvc.getBatchTransactionLoanResult(batchtxrefno);
	}

	public List<MigrationResultForm> postDepositBatchFile(String filepath) {
		return srvc.postDepositBatchFile(filepath);
	}

	public List<MigrationResultForm> postLoansBatchFile(String filepath) {
		return srvc.postLoansBatchFile(filepath);
	}

}
