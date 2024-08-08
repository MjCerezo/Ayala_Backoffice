/**
 * 
 */
package com.etel.migration;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.etel.migration.form.MigrationResultForm;
import com.wavemaker.runtime.server.FileUploadResponse;

/**
 * @author ETEL-LAPTOP07
 *
 */
public interface MigrationService {

	FileUploadResponse uploadXLSXFile(MultipartFile file) throws IOException;

	List<MigrationResultForm> uploadCIF(String filepath);

	List<MigrationResultForm> uploadDeposit(String filepath);

	List<MigrationResultForm> uploadLoans(String filepath);

}
