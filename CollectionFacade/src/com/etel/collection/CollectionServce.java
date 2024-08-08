/**
 * 
 */
package com.etel.collection;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.etel.collection.form.CollectionForm;
import com.wavemaker.runtime.server.FileUploadResponse;

/**
 * @author ETEL-LAPTOP07
 *
 */
public interface CollectionServce {

	FileUploadResponse uploadCollectionFile(MultipartFile file) throws IOException;

	List<CollectionForm> readCollectionFile(String filepath);

}
