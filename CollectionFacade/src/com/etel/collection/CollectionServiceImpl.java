/**
 * 
 */
package com.etel.collection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.etel.collection.form.CollectionForm;
import com.etel.collection.form.CollectionResultForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.util.DateTimeUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.server.FileUploadResponse;

/**
 * @author ETEL-LAPTOP07
 *
 */
public class CollectionServiceImpl implements CollectionServce {

	@Override
	public FileUploadResponse uploadCollectionFile(MultipartFile file) throws IOException {

		String uploadDir = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("resources/data/collection");

		// Create our return object
		FileUploadResponse ret = new FileUploadResponse();
		ret.setError("Error");
		ret.setWidth("");
		ret.setHeight("");
		try {
			/* Find our upload directory, make sure it exists */
			File dir = new File(uploadDir);
			if (!dir.exists())
				dir.mkdirs();

			/*
			 * Create a file object that does not point to an existing file. Loop through
			 * names until we find a filename not already in use
			 */
			String filename = file.getOriginalFilename(); /* .replaceAll("[^a-zA-Z0-9 ._-]",""); */
			boolean hasExtension = filename.indexOf(".") != -1;
			String name = (hasExtension) ? filename.substring(0, filename.lastIndexOf(".")) : filename;
			String ext = (hasExtension) ? filename.substring(filename.lastIndexOf(".")) : "";
			if (!ext.equals(".xlsx")) {
				ret.setError("Please select an xlsx file.");
				return ret;
			}

			File outputFile = new File(dir, filename);
			DBService dbsrvc = new DBServiceImpl();
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("filename", "%" + filename + "%");
			int count = dbsrvc.getSQLMaxId(
					"SELECT COUNT(*) FROM Tblogs WHERE modulename = 'COLLECTION' AND description LIKE :filename",
					param);

			if (count > 0) {
				ret.setError("Duplicate File.");
				return ret;
			}

			for (int i = 0; i < 10000 && outputFile.exists(); i++) {
				outputFile = new File(dir, name + i + ext);
			}
			FileUtils.cleanDirectory(dir);
			/* Write the file to the filesystem */
			FileOutputStream fos = new FileOutputStream(outputFile);
			IOUtils.copy(file.getInputStream(), fos);
			file.getInputStream().close();
			fos.close();

			/* Setup the return object */
			ret.setPath(outputFile.getPath());
			ret.setWidth("");
			ret.setHeight("");
			ret.setError("");
		} catch (Exception e) {
			System.out.println("ERROR:" + e.getMessage() + " | " + e.toString());
			ret.setError(e.getMessage());
			file.getInputStream().close();
		}
		return ret;
	}

	@Override
	public List<CollectionForm> readCollectionFile(String filepath) {
		List<CollectionForm> list = new ArrayList<CollectionForm>();
		try {
			FileInputStream file = new FileInputStream(new File(filepath));
			Workbook workbook = new XSSFWorkbook(file);
			DataFormatter formatter = new DataFormatter();
			for (Sheet sheet : workbook) {
				if (sheet.getSheetName().contains("COLLECTION")) {
					for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
						Row row = sheet.getRow(i);
						CollectionForm form = new CollectionForm();
						form.setAccountNumber(formatter.formatCellValue(row.getCell(0)));
						form.setAccountName(formatter.formatCellValue(row.getCell(1)));
						form.setProductName(formatter.formatCellValue(row.getCell(2)));
						form.setValuedate(DateTimeUtil.convertToDate(formatter.formatCellValue(row.getCell(3)),
								DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
						form.setAmount(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()).setScale(2,
								RoundingMode.HALF_UP));
						form.setRemarks(formatter.formatCellValue(row.getCell(5)));
						form.setCollector(formatter.formatCellValue(row.getCell(6)));
						list.add(form);
					}
				}
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<CollectionResultForm> postCollectionFile(List<CollectionForm> list){
		try {
			List<CollectionResultForm> result = new ArrayList<CollectionResultForm>();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
