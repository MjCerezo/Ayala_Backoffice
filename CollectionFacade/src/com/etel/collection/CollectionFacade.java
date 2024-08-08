package com.etel.collection;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.etel.collection.form.CollectionForm;
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
public class CollectionFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public CollectionFacade() {
		super(INFO);
	}

	CollectionServce srvc = new CollectionServiceImpl();

	public FileUploadResponse uploadCollectionFile(MultipartFile file) throws IOException {
		return srvc.uploadCollectionFile(file);
	}

	public List<CollectionForm> readCollectionFile(String filepath) {
		return srvc.readCollectionFile(filepath);
	}
}
