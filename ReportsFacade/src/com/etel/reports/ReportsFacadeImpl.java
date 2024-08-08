package com.etel.reports;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.coopdb.data.Tbproperties;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.reports.forms.AccountOfficersForm;
import com.etel.utils.EncryptDecryptUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportsFacadeImpl implements ReportsFacadeService {

	// Report properties
	public Tbproperties reportProperties() {
		DBService dbService = new DBServiceImpl();
		Tbproperties p = new Tbproperties();
		try {
			p = (Tbproperties) dbService.executeUniqueHQLQuery("FROM Tbproperties", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	private static final Logger logger = Logger.getLogger(ReportsFacade.class);

	SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	public static Connection connectDB(String databaseName, String userName, String password) {

		Connection jdbcConnection = null;

		try {

			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			jdbcConnection = DriverManager.getConnection(databaseName, userName, password);
		} catch (Exception ex) {
			String connectMsg = "Could not connect to the database: " + ex.getMessage() + " "
					+ ex.getLocalizedMessage();
			System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>MAR "+ ex.getMessage());
			System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>MAR "+ databaseName);
			System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>MAR "+ userName);
			System.out.print(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>MAR "+ password);
			logger.debug(connectMsg);
		}

		return jdbcConnection;
	}

	public String executeJasperPDF(String fileName, Map<String, Object> parameters) {

		// get file url
		URL reportFile = null;

		try {
			reportFile = RuntimeAccess.getInstance().getSession().getServletContext()
					.getResource("/WEB-INF/jasper/" + fileName + ".jasper");
		} catch (MalformedURLException e) {
			logger.debug("{}", e);
		}

		// initialize jasper report
		JasperReport report = null;

		// generate report
		JasperPrint jasperPrint = null;

		try {

//			String dir = RuntimeAccess.getInstance().getSession().getServletContext()
//					.getRealPath("/resources/properties");
//			File file = new File(dir);
//			if (!file.exists()) {
//				file.mkdirs();
//			}
//
//			File config = new File(file, "config.properties");
//			if (config.exists()) {
//				System.out.println(config.getAbsolutePath());
//			}
//			Properties prop = new Properties();
//			InputStream input = new FileInputStream(config);
//
//			// Load Properties
//			prop.load(input);

//			System.out.println("DB : "+reportProperties().getRptUrl());
//			System.out.println("u : "+reportProperties().getRptUsername());
//			System.out.println("p : "+reportProperties().getRptPassword());

			report = (JasperReport) JRLoader.loadObject(reportFile);

			Connection jdbcConnection = connectDB(reportProperties().getRptUrl(), reportProperties().getRptUsername(),
					EncryptDecryptUtil.decrypt(reportProperties().getRptPassword()));

			jasperPrint = JasperFillManager.fillReport(report, parameters, jdbcConnection);

		} catch (JRException e) {
			logger.debug("{}", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// URL path =
		// RuntimeAccess.getInstance().getSession().getServletContext().getResource(
		// "/WEB-INF/jasper/pdf/" );

		String pathWR = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("/resources/printtemp/");

		File pdfDirectory = new File(pathWR);

		if (!pdfDirectory.exists()) {
			pdfDirectory.mkdir();
		}

		String tempFileName = new String();

		File tempFile = null;
		try {
			tempFile = File.createTempFile(fileName, ".pdf", pdfDirectory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug("{}", e);
		}
		tempFile.deleteOnExit();
		tempFileName = tempFile.getName();

		// JRCsvExporter exporter = new JRCsvExporter();
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pdfDirectory + "/" + tempFileName);

		try {
			exporter.exportReport();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			logger.debug("{}", e);
		}

		return "resources/printtemp/" + tempFileName;

	}

	// EXCEL
	public String executeJasperXLSX(String fileName, Map<String, Object> parameters) {

		// get file url
		URL reportFile = null;

		try {
			reportFile = RuntimeAccess.getInstance().getSession().getServletContext()
					.getResource("/WEB-INF/jasper/" + fileName + ".jasper");
		} catch (MalformedURLException e) {
			logger.debug("{}", e);
		}

		// initialize jasper report
		JasperReport report = null;

		// generate report
		JasperPrint jasperPrint = null;

		try {

//			String dir = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("/resources/properties");
//	    	File file = new File(dir);
//	    	if(!file.exists()){
//	    		file.mkdirs();
//	    	}
//	    	
//	    	File config = new File(file, "config.properties");
//	    	if(config.exists()){
////	    		System.out.println(config.getAbsolutePath());
//	    	}
//	    	Properties prop = new Properties();
//	    	InputStream input = new FileInputStream(config);
//	    	
//	    	// Load Properties
//	    	prop.load(input);

			report = (JasperReport) JRLoader.loadObject(reportFile);

			Connection jdbcConnection = connectDB(reportProperties().getRptUrl(), reportProperties().getRptUsername(),
					EncryptDecryptUtil.decrypt(reportProperties().getRptPassword()));

			jasperPrint = JasperFillManager.fillReport(report, parameters, jdbcConnection);

		} catch (JRException e) {
			logger.debug("{}", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// URL path =
		// RuntimeAccess.getInstance().getSession().getServletContext().getResource(
		// "/WEB-INF/jasper/pdf/" );

		String pathWR = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("/resources/printtemp/");

		File pdfDirectory = new File(pathWR);

		if (!pdfDirectory.exists()) {
			pdfDirectory.mkdir();
		}

		String tempFileName1 = new String();

		File tempFile1 = null;
		try {
			tempFile1 = File.createTempFile(fileName, ".xlsx", pdfDirectory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug("{}", e);
		}
		tempFile1.deleteOnExit();
		tempFileName1 = tempFile1.getName();

		// JRPdfExporter exporter= new JRPdfExporter();
		// JRXlsExporter exporter= new JRXlsExporter();
		JRXlsxExporter exporter1 = new JRXlsxExporter();
		exporter1.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter1.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pdfDirectory + "/" + tempFileName1);
		try {
			exporter1.exportReport();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			logger.debug("{}", e);
		}

		return "resources/printtemp/" + tempFileName1;
	}

	// for CSV File generation
	@SuppressWarnings("unused")
	public String executeJasperCSV(String fileName, Map<String, Object> parameters) {

//		System.out.println("<<<<<<<<<<<<<<<<<<<CSV Reports");
		// get file url
		URL reportFile = null;

		try {
			reportFile = RuntimeAccess.getInstance().getSession().getServletContext()
					.getResource("/WEB-INF/jasper/" + fileName + ".jasper");
		} catch (MalformedURLException e) {
			logger.debug("{}", e);
		}

		// initialize jasper report
		JasperReport report = null;

		// generate report
		JasperPrint jasperPrint = null;

		try {

//			String dir = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("/resources/properties");
//	    	File file = new File(dir);
//	    	if(!file.exists()){
//	    		file.mkdirs();
//	    	}
//	    	
//	    	File config = new File(file, "config.properties");
//	    	if(config.exists()){
//	    		System.out.println(config.getAbsolutePath());
//	    	}
//	    	Properties prop = new Properties();
//	    	InputStream input = new FileInputStream(config);
//	    	
//	    	// Load Properties
//	    	prop.load(input);
//	    	
//			report = (JasperReport) JRLoader.loadObject(reportFile);

			Connection jdbcConnection = connectDB(reportProperties().getRptUrl(), reportProperties().getRptUsername(),
					reportProperties().getRptPassword());

			jasperPrint = JasperFillManager.fillReport(report, parameters, jdbcConnection);

		} catch (JRException e) {
			logger.debug("{}", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// URL path =
		// RuntimeAccess.getInstance().getSession().getServletContext().getResource(
		// "/WEB-INF/jasper/pdf/" );

		String pathWR = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("/resources/printtemp/");

		File pdfDirectory = new File(pathWR);

		if (!pdfDirectory.exists()) {
			pdfDirectory.mkdir();
		}

		String tempFileName = new String();

		File tempFile = null;
		try {
			tempFile = File.createTempFile(fileName, ".csv", pdfDirectory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug("{}", e);
		}
		tempFile.deleteOnExit();
		tempFileName = tempFile.getName();

		JRCsvExporter exporter = new JRCsvExporter();
		// JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pdfDirectory + "/" + tempFileName);

		try {
			exporter.exportReport();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			logger.debug("{}", e);
		}

		return "resources/printtemp/" + tempFileName;
	}

	public String executeJasperDOCX(String fileName, Map<String, Object> parameters) {

		// get file url
		URL reportFile = null;

		try {
			reportFile = RuntimeAccess.getInstance().getSession().getServletContext()
					.getResource("/WEB-INF/jasper/" + fileName + ".jasper");
		} catch (MalformedURLException e) {
			logger.debug("{}", e);
		}

		// initialize jasper report
		JasperReport report = null;

		// generate report
		JasperPrint jasperPrint = null;

		try {

//			String dir = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("/resources/properties");
//	    	File file = new File(dir);
//	    	if(!file.exists()){
//	    		file.mkdirs();
//	    	}
//	    	
//	    	File config = new File(file, "config.properties");
//	    	if(config.exists()){
//	    		System.out.println(config.getAbsolutePath());
//	    	}
//	    	Properties prop = new Properties();
//	    	InputStream input = new FileInputStream(config);
//	    	
//	    	// Load Properties
//	    	prop.load(input);

			report = (JasperReport) JRLoader.loadObject(reportFile);

			Connection jdbcConnection = connectDB(reportProperties().getRptUrl(), reportProperties().getRptUsername(),
					reportProperties().getRptPassword());
			jasperPrint = JasperFillManager.fillReport(report, parameters, jdbcConnection);

		} catch (JRException e) {
			logger.debug("{}", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// URL path =
		// RuntimeAccess.getInstance().getSession().getServletContext().getResource(
		// "/WEB-INF/jasper/pdf/" );

		String pathWR = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("/resources/printtemp/");

		File pdfDirectory = new File(pathWR);

		if (!pdfDirectory.exists()) {
			pdfDirectory.mkdir();
		}

		String tempFileName1 = new String();

		File tempFile1 = null;
		try {
			tempFile1 = File.createTempFile(fileName, ".docx", pdfDirectory);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.debug("{}", e);
		}
		tempFile1.deleteOnExit();
		tempFileName1 = tempFile1.getName();

		// JRPdfExporter exporter= new JRPdfExporter();
		// JRXlsExporter exporter= new JRXlsExporter();
		JRDocxExporter exporter1 = new JRDocxExporter();
		exporter1.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter1.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pdfDirectory + "/" + tempFileName1);
		try {
			exporter1.exportReport();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			logger.debug("{}", e);
		}

		return "resources/printtemp/" + tempFileName1;
	}

	@Override
	public List<AccountOfficersForm> getListofOfficerPerAocode() {

		List<AccountOfficersForm> acct = new ArrayList<AccountOfficersForm>();

		Map<String, Object> params = new HashMap<String, Object>();

		DBService dbsrvc = new DBServiceImpl();

		try {

			acct = (List<AccountOfficersForm>) dbsrvc.execSQLQueryTransformer(
					"select lastname+', '+firstname+' '+concat(middlename,(case when suffix=null then '' else suffix end)) as accountofficers,aocode from TBACCOUNTOFFICER",
					params, AccountOfficersForm.class, 1);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return acct;
	}

	// EXCEL
	@Override
	public String executeJasperXLSXwithOutputFileName(String fileName, Map<String, Object> parameters,
			String outputfilePath, String outputfileName) {
		// get file url
		URL reportFile = null;
		try {
			reportFile = RuntimeAccess.getInstance().getSession().getServletContext()
					.getResource("/WEB-INF/jasper/" + fileName + ".jasper");
		} catch (MalformedURLException e) {
			logger.debug("{}", e);
		}
		// initialize jasper report
		JasperReport report = null;
		// generate report
		JasperPrint jasperPrint = null;
		try {
			report = (JasperReport) JRLoader.loadObject(reportFile);
			Connection jdbcConnection = connectDB(reportProperties().getRptUrl(), reportProperties().getRptUsername(),
					EncryptDecryptUtil.decrypt(reportProperties().getRptPassword()));

			jasperPrint = JasperFillManager.fillReport(report, parameters, jdbcConnection);

		} catch (JRException e) {
			logger.debug("{}", e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String pathWR = outputfilePath;

		File pdfDirectory = new File(pathWR);

		if (!pdfDirectory.exists()) {
			pdfDirectory.mkdir();
		}

		String tempFileName1 = new String();

		File tempFile1 = null;
		tempFile1 = new File(pdfDirectory, outputfileName + ".xlsx");
		tempFileName1 = tempFile1.getName();
		JRXlsxExporter exporter1 = new JRXlsxExporter();
		exporter1.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter1.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, pdfDirectory + "/" + tempFileName1);
		try {
			exporter1.exportReport();
		} catch (JRException e) {
			// TODO Auto-generated catch block
			logger.debug("{}", e);
		}

		return "resources/docdir/" + tempFileName1;
	}

	@Override
	public String ordinal(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String capitalize(String text) {
		// TODO Auto-generated method stub
		return null;
	}

	public  String intToRoman(int num) {
	    StringBuilder sb = new StringBuilder();
	    int times = 0;
	    String[] romans = new String[] { "i", "iv", "v", "ix", "x", "xl", "l",
	            "xc", "c", "cd", "d", "cm", "m" };
	    int[] ints = new int[] { 1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500,
	            900, 1000 };
	    for (int i = ints.length - 1; i >= 0; i--) {
	        times = num / ints[i];
	        num %= ints[i];
	        while (times > 0) {
	            sb.append(romans[i]);
	            times--;
	        }
	    }
	    return sb.toString();
	}

}
