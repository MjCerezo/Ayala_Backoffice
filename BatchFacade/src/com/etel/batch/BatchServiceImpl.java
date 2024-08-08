/**
 * 
 */
package com.etel.batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbchecksforclearing;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tbloans;
import com.coopdb.data.Tblogs;
import com.coopdb.data.Tbtransactioncode;
import com.coopdb.data.Tbuser;
import com.etel.batch.form.BatchTransactionDepositForm;
import com.etel.batch.form.BatchTransactionLoanForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.deposittransaction.DepositTransactionService;
import com.etel.deposittransaction.DepositTransactionServiceImpl;
import com.etel.deposittransaction.form.DepositTransactionForm;
import com.etel.glentries.GLEntriesService;
import com.etel.glentries.GLEntriesServiceImpl;
import com.etel.lms.TransactionService;
import com.etel.lms.TransactionServiceImpl;
import com.etel.migration.form.MigrationResultForm;
import com.etel.util.DateTimeUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.server.FileUploadResponse;

/**
 * @author ETEL-LAPTOP07
 *
 */
public class BatchServiceImpl implements BatchService {

	Tbuser user = UserUtil.getUserByUsername(UserUtil.securityService.getUserName());

	public FileUploadResponse uploadBatchFile(MultipartFile file) throws IOException {

		String uploadDir = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("resources/data/batch");

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
					"SELECT COUNT(*) FROM Tblogs WHERE modulename = 'BATCH UPLOAD' " + "AND description LIKE :filename",
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
	public List<BatchTransactionDepositForm> readBatchTransactionDepositFile(String filepath, String txcode,
			Date valuedate, String reason) {
		List<BatchTransactionDepositForm> list = new ArrayList<BatchTransactionDepositForm>();
		try {
			DBService dbsrvc = new DBServiceImpl();
			Map<String, Object> param = new HashMap<String, Object>();
			FileInputStream file = new FileInputStream(new File(filepath));
			Workbook workbook = new XSSFWorkbook(file);
			DataFormatter formatter = new DataFormatter();
			Calendar rowDate = Calendar.getInstance();
			Calendar businessDate = Calendar.getInstance();
			param.put("branchcode", user.getBranchcode());
			Tbbranch branch = (Tbbranch) dbsrvc.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode",
					param);
			businessDate.setTime(branch.getCurrentbusinessdate());
			for (Sheet sheet : workbook) {
				if (sheet.getSheetName().contains("BATCH - DEPOSIT")) {
					for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
						Row row = sheet.getRow(i);
						BatchTransactionDepositForm form = new BatchTransactionDepositForm();
						form.setAccountno(formatter.formatCellValue(row.getCell(0)));
						form.setTxcode(formatter.formatCellValue(row.getCell(1)));
						form.setValuedate(DateTimeUtil.convertToDate(formatter.formatCellValue(row.getCell(2)),
								DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
						form.setAmount(BigDecimal.valueOf(row.getCell(3).getNumericCellValue()).setScale(2,
								RoundingMode.HALF_UP));
						form.setReason(formatter.formatCellValue(row.getCell(4)));
						form.setRemarks(formatter.formatCellValue(row.getCell(5)));
						if (form.getTxcode().equals("OBCD")) {
							form.setChecknumber(formatter.formatCellValue(row.getCell(6)));
							form.setBrstn(formatter.formatCellValue(row.getCell(7)));
							form.setClearingdays((int) row.getCell(8).getNumericCellValue());
							form.setClearingdate(DateTimeUtil.convertToDate(formatter.formatCellValue(row.getCell(9)),
									DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
						}
						list.add(form);
						rowDate.setTime(form.getValuedate());
						if(!businessDate.equals(rowDate)) {
							form.setRemarks("Invalid Date");
						}
					}
				}
			}
			workbook.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String postBatchTransactionDepositFile(List<BatchTransactionDepositForm> list, String txcode, Date valuedate,
			String reason, String filename) {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			if (list == null || list.size() <= 0) {
				return "0";
			}
			param.put("branchcode", user.getBranchcode());
			Tbbranch branch = (Tbbranch) dbsrvc.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode",
					param);
			Tblogs log = new Tblogs();
			log.setCurrentdate(branch.getCurrentbusinessdate());
			log.setDescription("BATCH DEPOSIT UPLOAD - " + filename);
			log.setErrordescription("");
			log.setEventdate(new Date());
			log.setEventname("BATCH DEPOSIT UPLOAD");
			log.setModulename("BATCH UPLOAD");
			log.setNextdate(branch.getNextbusinessdate());
			log.setUniquekey((String) dbsrvc.executeUniqueSQLQuery(
					"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE_PROD 'BTX', @txrefno OUTPUT", param));
			dbsrvc.save(log);
			for (BatchTransactionDepositForm form : list) {
				param.put("txcode", form.getTxcode());
				param.put("reason", form.getReason());
				Tbtransactioncode tx = (Tbtransactioncode) dbsrvc
						.executeUniqueHQLQuery("FROM Tbtransactioncode WHERE mnemonic=:txcode", param);
				DepositTransactionForm txForm = new DepositTransactionForm();
				// CED 05-04-2023
//				txForm.setValuedate(branch.getCurrentbusinessdate());
				txForm.setValuedate(form.getValuedate());
				txForm.setErrorcorrect(false);
				// CED 05-04-2023
				if (tx.getTxoper() == 1) {
					// DEBIT
					txForm.setReason((String) dbsrvc.executeUniqueHQLQuery(
							"SELECT codevalue FROM Tbcodetablecasa WHERE codename='REASONDR' and desc1=:reason",
							param));
				} else {
					// CREDIT
					txForm.setReason((String) dbsrvc.executeUniqueHQLQuery(
							"SELECT codevalue FROM Tbcodetablecasa WHERE codename='REASONCR' and desc1=:reason",
							param));

				}
				txForm.setRemarks(form.getRemarks());
				txForm.setOverridestatus("0");
				txForm.setTxbranch(branch.getBranchcode());
				txForm.setAccountnoto("");
				txForm.setTxcode(tx.getTxcode());
				DepositTransactionService depSrvc = new DepositTransactionServiceImpl();
				txForm.setTxamount(form.getAmount());
				txForm.setTxcode(tx.getTxcode());
				txForm.setAccountno(form.getAccountno());
				txForm.setBatchcode(log.getUniquekey());
				if (tx.getWcheck()) {
					List<Tbchecksforclearing> checks = new ArrayList<Tbchecksforclearing>();
					Tbchecksforclearing check = new Tbchecksforclearing();
					check.setAccountnumber(form.getAccountno());
					check.setChecknumber(form.getChecknumber());
					check.setCheckamount(txForm.getTxamount());
					check.setCheckdate(txForm.getValuedate());
					check.setClearingdate(form.getClearingdate());
					check.setClearingdays(form.getClearingdays());
					check.setIslateclearing(false);
					checks.add(check);
					txForm.setChecks(checks);
				}
				depSrvc.casaTransaction(txForm, tx, null);
			}
			return log.getUniquekey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-1";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BatchTransactionDepositForm> getBatchTransactionDepositResult(String batchtxrefno) {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchtxrefno", batchtxrefno);
		try {
			return (List<BatchTransactionDepositForm>) dbsrvc.execSQLQueryTransformer(
					"exec sp_batchtransactiondeposit :batchtxrefno", param, BatchTransactionDepositForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<BatchTransactionLoanForm> readBatchTransactionLoanFile(String filepath, String txcode, Date valuedate,
			String reason) {
		List<BatchTransactionLoanForm> list = new ArrayList<BatchTransactionLoanForm>();
		try {
			FileInputStream file = new FileInputStream(new File(filepath));
			Workbook workbook = new XSSFWorkbook(file);
			DataFormatter formatter = new DataFormatter();
			for (Sheet sheet : workbook) {
				if (sheet.getSheetName().contains("BATCH - LOAN")) {
					for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
						Row row = sheet.getRow(i);
						BatchTransactionLoanForm form = new BatchTransactionLoanForm();
						form.setAccountno(formatter.formatCellValue(row.getCell(0)));
						form.setTxcode(formatter.formatCellValue(row.getCell(1)));
//						form.setValuedate(DateTimeUtil.convertToDate(formatter.formatCellValue(row.getCell(2)),
//								DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
						form.setAmount(BigDecimal.valueOf(row.getCell(3).getNumericCellValue()).setScale(2,
								RoundingMode.HALF_UP));
						form.setLpc(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()).setScale(2,
								RoundingMode.HALF_UP));
						form.setAr(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()).setScale(2,
								RoundingMode.HALF_UP));
						form.setInterest(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()).setScale(2,
								RoundingMode.HALF_UP));
						form.setPrincipal(BigDecimal.valueOf(row.getCell(7).getNumericCellValue()).setScale(2,
								RoundingMode.HALF_UP));
						form.setReason(formatter.formatCellValue(row.getCell(8)));
						form.setRemarks(formatter.formatCellValue(row.getCell(9)));
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

	@Override
	public String postBatchTransactionLoanFile(List<BatchTransactionLoanForm> list, String txcode, Date valuedate,
			String reason, String filename) {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			if (list == null || list.size() <= 0) {
				return "0";
			}
			param.put("branchcode", user.getBranchcode());
			Tbbranch branch = (Tbbranch) dbsrvc.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode",
					param);
			Tblogs log = new Tblogs();
			log.setCurrentdate(branch.getCurrentbusinessdate());
			log.setDescription("BATCH LOAN UPLOAD - " + filename);
			log.setErrordescription("");
			log.setEventdate(new Date());
			log.setEventname("BATCH LOAN UPLOAD");
			log.setModulename("BATCH UPLOAD");
			log.setNextdate(branch.getNextbusinessdate());
			log.setUniquekey((String) dbsrvc.executeUniqueSQLQuery(
					"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE_PROD 'BTX', @txrefno OUTPUT", param));
			dbsrvc.save(log);
			for (BatchTransactionLoanForm form : list) {
				param.put("reason", form.getReason());
				Tbloanfin fin = new Tbloanfin();
				fin.setAccountno(form.getAccountno());
				fin.setPnno(form.getAccountno());
				fin.setCreatedby(user.getUsername());
				fin.setCreationdate(new Date());
				fin.setParticulars(form.getRemarks());
				fin.setReason((String) dbsrvc.executeUniqueSQLQuery(
						"SELECT codevalue FROM Tbcodetable WHERE codename='ADJUSTMENTREASON' and desc1=:reason",
						param));
				fin.setRemarks(fin.getParticulars());
				fin.setTxamount(form.getAmount());
				fin.setTxlpc(form.getLpc());
				fin.setTxmisc(form.getAr());
				fin.setTxint(form.getInterest());
				fin.setTxprin(form.getPrincipal());
				fin.setTxamtbal(fin.getTxamount());
				fin.setTxdate(new Date());
				fin.setTxmode("3");
				fin.setTxoper(form.getTxcode().equals("CR") ? 2 : 1);
				fin.setTxor("");
				fin.setTxstatus("9");
				fin.setTxstatusdate(new Date());
				// CED 05-04-2023
				fin.setTxvaldt(branch.getCurrentbusinessdate());
				fin.setTxcode(form.getTxcode().equals("CR") ? "50" : "20");
				fin.setTxbatch(log.getUniquekey());
				TransactionService txsrvc = new TransactionServiceImpl();
				String txrefno = txsrvc.addEntry(fin, null);
				fin = txsrvc.getTransaction(txrefno).getTransaction();
				param.put("acctno", form.getAccountno());
				Tbloans loan = (Tbloans) dbsrvc.executeUniqueHQLQuery("FROM Tbloans WHERE accountno=:acctno", param);
				if (loan == null) {
					fin.setTxstatus("C");
					fin.setParticulars("Account number invalid.");
					txsrvc.addEntry(fin, null);
					continue;
				}
				fin.setCifno(loan.getPrincipalNo());
				fin.setClientname(loan.getFullname());
				fin.setEmployeeno(loan.getPrincipalNo());
				fin.setSlaidno(loan.getPrincipalNo());
				txsrvc.postSinglePayment(txrefno);
			}
			return log.getUniquekey();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "-1";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BatchTransactionLoanForm> getBatchTransactionLoanResult(String batchtxrefno) {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("batchtxrefno", batchtxrefno);
		try {
			return (List<BatchTransactionLoanForm>) dbsrvc.execSQLQueryTransformer(
					"exec sp_batchtransactionloan:batchtxrefno", param, BatchTransactionLoanForm.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<MigrationResultForm> postDepositBatchFile(String filepath) {
		List<MigrationResultForm> results = new ArrayList<MigrationResultForm>();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			FileInputStream file = new FileInputStream(new File(filepath));
			Workbook workbook = new XSSFWorkbook(file);
			DataFormatter formatter = new DataFormatter();
			Sheet sheetBatchDeposit = null;
			for (Sheet sheet : workbook) {
				if (sheet.getSheetName().equals("BATCH - DEPOSIT")) {
					sheetBatchDeposit = sheet;
				}
			}
			param.put("branchcode", user.getBranchcode());
			Tbbranch branch = (Tbbranch) dbsrvc.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode",
					param);
			// BATCH DEPOSIT
			if (sheetBatchDeposit != null) {
				Tblogs log = new Tblogs();
				log.setCurrentdate(branch.getCurrentbusinessdate());
				log.setDescription("BATCH DEPOSIT UPLOAD - "
						+ filepath.substring(filepath.lastIndexOf("\\") + 1, filepath.length()));
				log.setErrordescription("");
				log.setEventdate(new Date());
				log.setEventname("BATCH DEPOSIT UPLOAD");
				log.setModulename("BATCH UPLOAD");
				log.setNextdate(branch.getNextbusinessdate());
				log.setUniquekey((String) dbsrvc.executeUniqueSQLQuery(
						"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE_PROD 'BTX', @txrefno OUTPUT", param));
				dbsrvc.save(log);
				for (int i = 1; i < sheetBatchDeposit.getPhysicalNumberOfRows(); i++) {
					Row row = sheetBatchDeposit.getRow(i);
					if (formatter.formatCellValue(row.getCell(0)) == null) {
						System.out.println("MISSING ACCOUNT NUMBER, ROW NUMBER : " + i + 1);
						continue;
					}
					param.put("acctno", formatter.formatCellValue(row.getCell(0)));
					String accountno = (String) dbsrvc.executeUniqueSQLQuery(
							"SELECT AccountNo FROM Tbdeposit WHERE accountno=:acctno AND accountstatus < 5", param);
					if (accountno == null) {
						System.out.println("ACCOUNT NUMBER INVALID : " + formatter.formatCellValue(row.getCell(0)));
						param.put("oldcifno", formatter.formatCellValue(row.getCell(1)));
						param.put("prodcode", formatter.formatCellValue(row.getCell(5)));
						param.put("subprodcode", formatter.formatCellValue(row.getCell(6)));
						accountno = (String) dbsrvc.executeUniqueSQLQuery(
								"select TOP 1 c.accountno as accountno from CIFSDBDXBANK.dbo.Tbcifmain a "
										+ "left join Tbdepositcif b on a.cifno = b.cifno COLLATE DATABASE_DEFAULT "
										+ "left join Tbdeposit c on b.accountno = c.AccountNo "
										+ "where oldcifno=:oldcifno and c.ProductCode =:prodcode and c.SubProductCode =:subprodcode",
								param);
					}
					if (accountno == null) {
						System.out.println("OLD CIF NUMBER INVALID : " + formatter.formatCellValue(row.getCell(1)));
						continue;
					}

					if (BigDecimal.valueOf(row.getCell(7).getNumericCellValue()).setScale(2, RoundingMode.HALF_UP)
							.compareTo(BigDecimal.ZERO) == 1) {
						param.put("txcode", formatter.formatCellValue(row.getCell(4)));
						Tbtransactioncode tx = (Tbtransactioncode) dbsrvc
								.executeUniqueHQLQuery("FROM Tbtransactioncode WHERE txcode =:txcode", param);
						DepositTransactionForm txForm = new DepositTransactionForm();
						txForm.setValuedate(DateTimeUtil.convertToDate(formatter.formatCellValue(row.getCell(3)),
								DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
						txForm.setErrorcorrect(false);
						txForm.setReason("9");
						txForm.setRemarks(formatter.formatCellValue(row.getCell(8)));
						txForm.setOverridestatus("0");
						txForm.setTxbranch(branch.getBranchcode());
						txForm.setAccountnoto("");
						txForm.setTxcode(tx.getTxcode());
						DepositTransactionService depSrvc = new DepositTransactionServiceImpl();
						txForm.setTxamount(BigDecimal.valueOf(row.getCell(7).getNumericCellValue()).setScale(2,
								RoundingMode.HALF_UP));
						txForm.setTxcode(tx.getTxcode());
						txForm.setAccountno(accountno);
						txForm.setBatchcode(log.getUniquekey());
						depSrvc.casaTransaction(txForm, tx, null);
					}
				}
			}
			workbook.close();
			return results;
		} catch (

		Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return results;
	}

	@Override
	public List<MigrationResultForm> postLoansBatchFile(String filepath) {
		List<MigrationResultForm> results = new ArrayList<MigrationResultForm>();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		try {
			FileInputStream file = new FileInputStream(new File(filepath));
			Workbook workbook = new XSSFWorkbook(file);
			DataFormatter formatter = new DataFormatter();
			Sheet sheetBatchDeposit = null;
			for (Sheet sheet : workbook) {
				if (sheet.getSheetName().equals("BATCH - LOANS")) {
					sheetBatchDeposit = sheet;
				}
			}
			param.put("branchcode", user.getBranchcode());
			Tbbranch branch = (Tbbranch) dbsrvc.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode",
					param);
			// BATCH DEPOSIT
			if (sheetBatchDeposit != null) {
				Tblogs log = new Tblogs();
				log.setCurrentdate(branch.getCurrentbusinessdate());
				log.setDescription("BATCH LOANS UPLOAD - "
						+ filepath.substring(filepath.lastIndexOf("\\") + 1, filepath.length()));
				log.setErrordescription("");
				log.setEventdate(new Date());
				log.setEventname("BATCH LOANS UPLOAD");
				log.setModulename("BATCH UPLOAD");
				log.setNextdate(branch.getNextbusinessdate());
				log.setUniquekey((String) dbsrvc.executeUniqueSQLQuery(
						"DECLARE @txrefno VARCHAR(20) EXEC SEQGENERATE_PROD 'BTX', @txrefno OUTPUT", param));
				dbsrvc.save(log);
				for (int i = 1; i < sheetBatchDeposit.getPhysicalNumberOfRows(); i++) {
					Row row = sheetBatchDeposit.getRow(i);
					if (formatter.formatCellValue(row.getCell(0)) == null) {
						System.out.println("MISSING ACCOUNT NUMBER, ROW NUMBER : " + i + 1);
						continue;
					}
					param.put("acctno", formatter.formatCellValue(row.getCell(0)));
					Tbloans loan = (Tbloans) dbsrvc.executeUniqueHQLQuery("FROM Tbloans WHERE accountno=:acctno",
							param);
					if (loan == null) {
						System.out.println("LOAN NUMBER INVALID : " + formatter.formatCellValue(row.getCell(0)));
						continue;
					}

					Tbloanfin fin = new Tbloanfin();
					fin.setAccountno(formatter.formatCellValue(row.getCell(0)));
					fin.setCifno(loan.getPrincipalNo());
					fin.setClientname(loan.getFullname());
					fin.setCreatedby(user.getUsername());
					fin.setCreationdate(new Date());
					fin.setEmployeeno(loan.getPrincipalNo());
					fin.setParticulars(formatter.formatCellValue(row.getCell(6)));
					fin.setPnno(loan.getPnno());
					fin.setReason("9");
					fin.setRemarks(fin.getParticulars());
					fin.setSlaidno(loan.getPrincipalNo());
					fin.setTxprin(
							BigDecimal.valueOf(row.getCell(1).getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
					fin.setTxint(
							BigDecimal.valueOf(row.getCell(2).getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
					fin.setTxlpc(
							BigDecimal.valueOf(row.getCell(3).getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
					fin.setTxmisc(
							BigDecimal.valueOf(row.getCell(4).getNumericCellValue()).setScale(2, RoundingMode.HALF_UP));
					fin.setTxamount(fin.getTxprin().add(fin.getTxint()).add(fin.getTxlpc()).add(fin.getTxmisc()));
					fin.setTxamtbal(fin.getTxamount());
					fin.setTxdate(new Date());
					fin.setTxmode("9");
					fin.setTxoper(2);
					fin.setTxor(formatter.formatCellValue(row.getCell(7)));
					fin.setTxstatus("9");
					fin.setTxstatusdate(new Date());
					fin.setTxvaldt(DateTimeUtil.convertToDate(formatter.formatCellValue(row.getCell(5)),
							DateTimeUtil.DATE_FORMAT_MM_DD_YYYY));
					fin.setTxcode(formatter.formatCellValue(row.getCell(8)));
					fin.setReason(formatter.formatCellValue(row.getCell(9)));
					fin.setTxbatch(log.getUniquekey());
					TransactionService txsrvc = new TransactionServiceImpl();
					String txrefno = txsrvc.addEntry(fin, null);
					if (txrefno.equals("Failed")) {
						System.out.println("Unsaved >>> " + fin.getAccountno() + " " + i);
					}
					if (fin.getTxcode().equals("40")) {
						GLEntriesService glSrvc = new GLEntriesServiceImpl();
						glSrvc.getGLEntriesByPnnoAndTxCode(loan.getAccountno(), "100", txrefno, true, false);
					}
					txsrvc.postSinglePayment(txrefno);
				}
			}
			workbook.close();
			return results;
		} catch (

		Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return results;
	}
}
