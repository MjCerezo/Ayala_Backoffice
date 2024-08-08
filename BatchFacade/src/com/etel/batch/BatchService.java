/**
 * 
 */
package com.etel.batch;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.etel.batch.form.BatchTransactionDepositForm;
import com.etel.batch.form.BatchTransactionLoanForm;
import com.etel.migration.form.MigrationResultForm;
import com.wavemaker.runtime.server.FileUploadResponse;

/**
 * @author ETEL-LAPTOP07
 *
 */
public interface BatchService {

	FileUploadResponse uploadBatchFile(MultipartFile file) throws IOException;

	String postBatchTransactionDepositFile(List<BatchTransactionDepositForm> list, String txcode, Date valuedate,
			String reason, String filename);

	List<BatchTransactionDepositForm> getBatchTransactionDepositResult(String batchtxrefno);

	List<BatchTransactionDepositForm> readBatchTransactionDepositFile(String filepath, String txcode, Date valuedate,
			String reason);

	List<BatchTransactionLoanForm> readBatchTransactionLoanFile(String filepath, String txcode, Date valuedate,
			String reason);

	String postBatchTransactionLoanFile(List<BatchTransactionLoanForm> list, String txcode, Date valuedate,
			String reason, String filename);

	List<BatchTransactionLoanForm> getBatchTransactionLoanResult(String batchtxrefno);

	List<MigrationResultForm> postDepositBatchFile(String filepath);

	List<MigrationResultForm> postLoansBatchFile(String filepath);

}
