package com.etel.migration;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

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
public class MigrationFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public MigrationFacade() {
		super(INFO);
	}

	MigrationService srvc = new MigrationServiceImpl();

	public FileUploadResponse uploadXLSXFile(MultipartFile file) throws IOException {
		return srvc.uploadXLSXFile(file);
	}

	public List<MigrationResultForm> uploadCIF(String filepath) {
		return srvc.uploadCIF(filepath);
	}

	public List<MigrationResultForm> uploadDeposit(String filepath) {
		return srvc.uploadDeposit(filepath);
	}

	public List<MigrationResultForm> uploadLoans(String filepath) {
		return srvc.uploadLoans(filepath);
	}

}
