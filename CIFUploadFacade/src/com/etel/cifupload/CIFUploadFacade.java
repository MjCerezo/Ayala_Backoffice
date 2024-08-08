
package com.etel.cifupload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.casa.acct.AccountService;
import com.casa.acct.AccountServiceImpl;
import com.casa.acct.forms.AccountGenericForm;
import com.casa.acct.util.AccountNumberGenerator;
import com.casa.misc.MiscTxService;
import com.casa.misc.MiscTxServiceImpl;
import com.cifsdb.data.Tbcifindividual;
import com.cifsdb.data.Tbcifmain;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdepositcif;
import com.coopdb.data.Tbpassbookissuance;
import com.coopdb.data.Tbprodmatrix;
import com.coopdb.data.Tbsigcard;
import com.coopdb.data.Tbtdc;
import com.coopdb.data.Tbtimedeposit;
import com.coopdb.data.Tbtransactioncode;
import com.coopdb.data.Tbuser;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.deposittransaction.DepositTransactionService;
import com.etel.deposittransaction.DepositTransactionServiceImpl;
import com.etel.deposittransaction.form.DepositTransactionForm;
import com.etel.generator.NoGenerator;
import com.etel.utils.DateTimeUtil;
import com.etel.utils.HQLUtil;
import com.etel.utils.ImageUtils;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.security.SecurityService;
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
public class CIFUploadFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level;
	 * recommend changing this to FATAL or ERROR before deploying. For info on these
	 * levels, look for tomcat/log4j documentation
	 */
	public CIFUploadFacade() {
		super(INFO);
	}

	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	public FileUploadResponse uploadCIFFile(MultipartFile file) throws IOException {

		String uploadDir = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("resources/data/cif");

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

	public String readXLSXFIle(String path) {
		DBService dbServiceRB = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		Tbuser user = new Tbuser();
		try {
			String username = UserUtil.securityService.getUserName();
			String branchcode = "";
			if (username != null) {
				params.put("username", username);
				user = (Tbuser) dbServiceRB.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
				if (user != null) {
//					if (user.getTeamcode() != null) {
//						main.setOriginatingteam(user.getTeamcode());
//					}
					branchcode = user.getBranchcode();
				}
			}
			FileInputStream file = new FileInputStream(new File(path));
			Workbook workbook = new XSSFWorkbook(file);
//			DataFormatter formatter = new DataFormatter();
			Sheet sheet = workbook.getSheetAt(0);
			for (int i = 1; i <= sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);
				if (row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK) == null)
					break;
				String oldcifno = row.getCell(0) == null ? "" : row.getCell(0).getStringCellValue();
				String fullname = row.getCell(1).getStringCellValue();
				Date dateofbirth = null;
				if (row.getCell(2) != null) {
					dateofbirth = DateTimeUtil.convertToDate(String.valueOf(row.getCell(2)), "dd-MMM-yyyy");
				}
				String address = String.valueOf(row.getCell(3));
				createCIF(branchcode, oldcifno, fullname, dateofbirth, address);
			}
//			}
			workbook.close();
			return "success";
		} catch (

		Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "error";
	}

	public String createCIF(String branchcode, String oldcifno, String fullname, Date dateofbirth, String address) {
		NoGenerator cifNoGenerator = new NoGenerator();
		DBService dbService = new DBServiceImplCIF();
		try {
			Tbcifmain main = new Tbcifmain();
			Tbcifindividual indiv = new Tbcifindividual();
//			Tbcifcorporate corp = new Tbcifcorporate();
			main.setOldcifno(oldcifno);
			main.setEncodedby(secservice.getUserName());
			main.setEncodeddate(new Date());
			main.setAssignedto(secservice.getUserName());
			main.setDateupdated(new Date());
			main.setCifstatus("3");
			main.setCifstatusdate(new Date());
			main.setCifapprovedby(secservice.getUserName());
			main.setCifapproveddate(new Date());
			main.setCiftype("2");
			main.setBorrowerfundertype("1");
			main.setApprovalcode("4");// ced for clarification
			main.setOriginatingbranch(branchcode);
			main.setFulladdress1(address);
			main.setFulladdress2(main.getFulladdress1());
//				if (row.getCell(49).getStringCellValue() == null
//						|| row.getCell(49).getStringCellValue().equals("")
//						|| row.getCell(49).getStringCellValue() == "") {
//					// corp
//					main.setCustomertype("2");
//					main.setCifno(cifNoGenerator.generateCIFNoCorp());
//					main.setDateofincorporation(
//							row.getCell(356) != null ? row.getCell(356).getDateCellValue() : null);
//					corp.setCifno(main.getCifno());
//					corp.setNationality(row.getCell(62).getStringCellValue());
//					corp.setMobilenumber(formatter.formatCellValue(row.getCell(105)));
//					corp.setFulladdress1(row.getCell(64).getStringCellValue());
//					corp.setFulladdress2(main.getFulladdress1());
//					corp.setCorporatename(formatter.formatCellValue(row.getCell(50)).toUpperCase());
//					corp.setCiftype(main.getCiftype());
//					corp.setCifstatus(main.getCifstatus());
//					corp.setAssignedto(main.getAssignedto());
//					corp.setBranchcode(main.getOriginatingbranch());
//					dbService.save(corp);
//				} else {
			// indiv
			main.setCustomertype("1");
			main.setCifno(cifNoGenerator.generateCIFNoIndiv());
			indiv.setCifno(main.getCifno());
//					indiv.setLastname(formatter.formatCellValue(row.getCell(49)).toUpperCase());
//					indiv.setFirstname(formatter.formatCellValue(row.getCell(50)).toUpperCase());
//					indiv.setMiddlename(formatter.formatCellValue(row.getCell(51)).toUpperCase());
//					indiv.setShortname(formatter.formatCellValue(row.getCell(52)).toUpperCase());
//					indiv.setSuffix(formatter.formatCellValue(row.getCell(53)).toUpperCase());
//				main.setFullname(indiv.getLastname() + ", " + indiv.getFirstname() + " " + indiv.getSuffix()
//						+ " " + indiv.getMiddlename());
			main.setFullname(fullname);
//			String d = formatter.formatCellValue(row.getCell(2));
			main.setDateofbirth(dateofbirth);
//					indiv.setCivilstatus(formatter.formatCellValue(row.getCell(59)));
//					indiv.setGender(formatter.formatCellValue(row.getCell(60)));
//					indiv.setProvince1(formatter.formatCellValue(row.getCell(61)));
//					indiv.setNationality(formatter.formatCellValue(row.getCell(62)));
//					indiv.setMobilenumber(formatter.formatCellValue(row.getCell(105)));
//					indiv.setFulladdress1(formatter.formatCellValue(row.getCell(64)));
//					indiv.setFulladdress2(main.getFulladdress1());
//					indiv.setSpousefirstname(formatter.formatCellValue(row.getCell(143)));
//					indiv.setSpouselastname(formatter.formatCellValue(row.getCell(144)));
//					indiv.setMiddlename(formatter.formatCellValue(row.getCell(145)));
//					indiv.setFatherfirstname(formatter.formatCellValue(row.getCell(150)));
//					indiv.setFathermiddlename(formatter.formatCellValue(row.getCell(151)));
//					indiv.setFatherlastname(formatter.formatCellValue(row.getCell(152)));
//					indiv.setMotherfirstname(formatter.formatCellValue(row.getCell(157)));
//					indiv.setMotherlastname(formatter.formatCellValue(row.getCell(158)));
//					indiv.setMothermiddlename(formatter.formatCellValue(row.getCell(159)));
//					indiv.setResident(formatter.formatCellValue(row.getCell(166)).equals("1") ? true : false);
			dbService.save(indiv);
//				}
			dbService.save(main);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "success";
	}

	@SuppressWarnings("unchecked")
	public String uploadSavingsFile(String path) {
		DBService dbService = new DBServiceImplCIF();
		DBService dbServiceRB = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		Tbuser user = new Tbuser();
		try {
			String username = UserUtil.securityService.getUserName();
			String branchcode = "";
			if (username != null) {
				params.put("username", username);
				user = (Tbuser) dbServiceRB.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
				if (user != null) {
					branchcode = user.getBranchcode();
				}
			}
			params.put("branchcode", branchcode);
			Tbbranch branch = (Tbbranch) dbServiceRB.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode",
					params);
			FileInputStream file = new FileInputStream(new File(path));
			Workbook workbook = new XSSFWorkbook(file);
//			DataFormatter formatter = new DataFormatter();
			Sheet sheet = workbook.getSheetAt(0);
			Tbprodmatrix prod = new Tbprodmatrix();
			prod = (Tbprodmatrix) dbServiceRB.executeUniqueHQLQuery("FROM Tbprodmatrix WHERE prodcode = '21'", params);
			Tbtransactioncode tx = (Tbtransactioncode) dbServiceRB
					.executeUniqueHQLQuery("FROM Tbtransactioncode WHERE txcode ='112013'", params);
			Tbtransactioncode txIntAccrual = (Tbtransactioncode) dbServiceRB
					.executeUniqueHQLQuery("FROM Tbtransactioncode WHERE txcode ='911400'", params);
			Tbdeposit dep = new Tbdeposit();
			DepositTransactionForm txForm = new DepositTransactionForm();
			txForm.setValuedate(branch.getCurrentbusinessdate());
			txForm.setErrorcorrect(false);
			txForm.setReason("9");
			txForm.setRemarks("Migration");
			txForm.setOverridestatus("0");
			txForm.setTxbranch(branchcode);
			txForm.setAccountnoto("");
			txForm.setTxcode("112013");
			dep.setAccountBalance(BigDecimal.ZERO);
			dep.setProductCode(prod.getProdgroup());
			dep.setSubProductCode(prod.getProdcode());
			dep.setUnit(branchcode);
			dep.setAccountnoata("");
			dep.setSlaidNo("");
			dep.setEmployeeNo("");
			dep.setAccountStatus(1);
			dep.setStatusDate(new Date());
			dep.setPledgeAmount(BigDecimal.ZERO);
			dep.setPledgeSchedule("");
			dep.setAccumulatedBalance(BigDecimal.ZERO);
			dep.setBookDate(new Date());
			dep.setJointAcctType("0");
			dep.setOwnershipType(0);
			dep.setPlacementAmt(BigDecimal.ZERO);
			dep.setLessWtaxAmt(BigDecimal.ZERO);
			dep.setCreatedBy(username);
			dep.setInstcode(branch.getCoopcode());
			dep.setAlertflag(0);
			dep.setAlertmessage("");
			dep.setAlertlevel(0);
			dep.setDispoflag("");
			dep.setDispofreetext("");
			dep.setTdcno("");
			dep.setLasttxdate(new Date());
			dep.setLasttxcode("");
			int pbNo = (Integer) dbServiceRB.executeUniqueHQLQuery(
					"SELECT ISNULL(MAX(CAST(newpssbksn as int)),0) FROM Tbpassbookissuance", params);
			for (int i = 1; i <= sheet.getPhysicalNumberOfRows(); i++) {
				try {
					Row row = sheet.getRow(i);
					if (row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK) == null)
						break;
					System.out.println(row.getCell(0));
					List<Tbdepositcif> cifList = new ArrayList<Tbdepositcif>();
					params.put("oldcifno", String.valueOf(row.getCell(0)));
					cifList = (List<Tbdepositcif>) dbService.execStoredProc(
							"SELECT cifno, fullname as cifname FROM Tbcifmain WHERE oldcifno=:oldcifno", params,
							Tbdepositcif.class, 1, null);
					if (cifList == null || cifList.size() == 0) {
						System.out.println(params.get("oldcifno") + " << NOT FOUND EZ");
						createCIF(branchcode, String.valueOf(row.getCell(0)), String.valueOf(row.getCell(1)), null,
								String.valueOf(row.getCell(3)));
					}
					cifList = (List<Tbdepositcif>) dbService.execStoredProc(
							"SELECT cifno, fullname as cifname FROM Tbcifmain WHERE oldcifno=:oldcifno", params,
							Tbdepositcif.class, 1, null);
					if (cifList == null || cifList.size() == 0) {
						System.out.println(params.get("oldcifno") + " << NOT FOUND");
					} else {
						dep.setAccountName(String.valueOf(row.getCell(1)));
						dep.setInterestearned(BigDecimal.ZERO);
//						dep.setInterestbalance(BigDecimal.valueOf(row.getCell(5).getNumericCellValue()));
						dep.setAccountStatus((int) row.getCell(5).getNumericCellValue());
						dep.setStatusDate(DateTimeUtil.convertToDate(String.valueOf(row.getCell(7)), "dd-MMM-yyyy"));
						AccountGenericForm acctResult = new AccountGenericForm();
						acctResult = createDepAcct(dep, cifList, params.get("oldcifno").toString());
						if (acctResult.getResult() == 1) {
							MiscTxService miscSrvc = new MiscTxServiceImpl();
							Tbpassbookissuance pb = new Tbpassbookissuance();
							pb.setAccountno(acctResult.getValue());
							pb.setBrid(branchcode);
							pb.setIssuancetype("1");
							pb.setNewpssbksn(String.valueOf(pbNo));
							pb.setOldpassbksn("");
							pb.setTxby(user.getUserid());
							pb.setTxdate(branch.getCurrentbusinessdate());
							String pbResult = "";
							while (!pbResult.equals("Passbook Number Issued.")) {
								pbNo += 1;
								pb.setNewpssbksn(String.valueOf(pbNo));
								pbResult = miscSrvc.passbookIssuance(pb);
							}
//							BALANCE - CREDIT MEMO
							DepositTransactionService depSrvc = new DepositTransactionServiceImpl();
							txForm.setTxamount(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()).setScale(2,
									RoundingMode.DOWN));
							txForm.setTxcode(tx.getTxcode());
							txForm.setAccountno(pb.getAccountno());
							System.out.println(txForm.getTxcode());
							depSrvc.casaTransaction(txForm, tx, null);
							// INTEREST EARNED - INTEREST CREDIT
							if (row.getCell(6) != null && row.getCell(6).getNumericCellValue() > 0) {
								txForm.setTxcode(txIntAccrual.getTxcode());
								txForm.setTxamount(BigDecimal.valueOf(row.getCell(6).getNumericCellValue()).setScale(2,
										RoundingMode.DOWN));
								System.out.println(txForm.getTxcode());
								depSrvc.casaTransaction(txForm, txIntAccrual, null);
							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
//			}
			workbook.close();
			return "success";
		} catch (

		Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "error";
	}

	@SuppressWarnings("unchecked")
	public String uploadTDFile(String path) {
		DBService dbService = new DBServiceImplCIF();
		DBService dbServiceRB = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		Tbuser user = new Tbuser();
		try {
			String username = UserUtil.securityService.getUserName();
			String branchcode = "";
			if (username != null) {
				params.put("username", username);
				user = (Tbuser) dbServiceRB.executeUniqueHQLQuery("FROM Tbuser WHERE username=:username", params);
				if (user != null) {
					branchcode = user.getBranchcode();
				}
			}
			params.put("branchcode", branchcode);
			Tbbranch branch = (Tbbranch) dbServiceRB.executeUniqueHQLQuery("FROM Tbbranch WHERE branchcode=:branchcode",
					params);
			FileInputStream file = new FileInputStream(new File(path));
			Workbook workbook = new XSSFWorkbook(file);
//			DataFormatter formatter = new DataFormatter();
			Sheet sheet = workbook.getSheetAt(0);
			Tbprodmatrix prodTD = new Tbprodmatrix();
			Tbprodmatrix prodTD5 = new Tbprodmatrix();
			if (path.substring(path.lastIndexOf("\\") + 1, path.length()).equals("td.xlsx")) {
				prodTD = (Tbprodmatrix) dbServiceRB.executeUniqueHQLQuery("FROM Tbprodmatrix WHERE prodcode = '42'",
						params);
				prodTD5 = (Tbprodmatrix) dbServiceRB.executeUniqueHQLQuery("FROM Tbprodmatrix WHERE prodcode = '43'",
						params);
			} else {
				prodTD = (Tbprodmatrix) dbServiceRB.executeUniqueHQLQuery("FROM Tbprodmatrix WHERE prodcode = '41'",
						params);
				prodTD5 = (Tbprodmatrix) dbServiceRB.executeUniqueHQLQuery("FROM Tbprodmatrix WHERE prodcode = '44'",
						params);
			}
			Tbtransactioncode tx = (Tbtransactioncode) dbServiceRB
					.executeUniqueHQLQuery("FROM Tbtransactioncode WHERE txcode ='112013'", params);
			Tbtransactioncode txIntAccrual = (Tbtransactioncode) dbServiceRB
					.executeUniqueHQLQuery("FROM Tbtransactioncode WHERE txcode ='911400'", params);
			Tbtransactioncode txIntCredit = (Tbtransactioncode) dbServiceRB
					.executeUniqueHQLQuery("FROM Tbtransactioncode WHERE txcode ='911401'", params);
			Tbtransactioncode txIntWithdraw = (Tbtransactioncode) dbServiceRB
					.executeUniqueHQLQuery("FROM Tbtransactioncode WHERE txcode ='122023'", params);
			Tbtransactioncode txWtax = (Tbtransactioncode) dbServiceRB
					.executeUniqueHQLQuery("FROM Tbtransactioncode WHERE txcode ='912401'", params);
			Tbdeposit dep = new Tbdeposit();
			DepositTransactionForm txForm = new DepositTransactionForm();
			txForm.setValuedate(branch.getCurrentbusinessdate());
			txForm.setErrorcorrect(false);
			txForm.setReason("9");
			txForm.setRemarks("Migration");
			txForm.setOverridestatus("0");
			txForm.setTxbranch(branchcode);
			txForm.setAccountnoto("");
			txForm.setTxcode("112013");
			txForm.setTxmode("3");
			dep.setAccountBalance(BigDecimal.ZERO);
			dep.setUnit(branchcode);
			dep.setAccountnoata("");
			dep.setSlaidNo("");
			dep.setEmployeeNo("");
			dep.setAccountStatus(1);
			dep.setStatusDate(new Date());
			dep.setPledgeAmount(BigDecimal.ZERO);
			dep.setPledgeSchedule("");
			dep.setAccumulatedBalance(BigDecimal.ZERO);
			dep.setBookDate(new Date());
			dep.setJointAcctType("0");
			dep.setOwnershipType(0);
			dep.setPlacementAmt(BigDecimal.ZERO);
			dep.setLessWtaxAmt(BigDecimal.ZERO);
			dep.setCreatedBy(username);
			dep.setInstcode(branch.getCoopcode());
			dep.setAlertflag(0);
			dep.setAlertmessage("");
			dep.setAlertlevel(0);
			dep.setDispoflag("");
			dep.setDispofreetext("");
			dep.setTdcno("");
			dep.setLasttxdate(new Date());
			dep.setLasttxcode("");
			int pbNo = (Integer) dbServiceRB.executeUniqueHQLQuery(
					"SELECT ISNULL(MAX(CAST(newpssbksn as int)),0) FROM Tbpassbookissuance", params);
			int tdcNo = (Integer) dbServiceRB
					.executeUniqueHQLQuery("SELECT ISNULL(MAX(CAST(tdcno as int)),0) FROM Tbtdc", params);
			for (int i = 1; i <= sheet.getPhysicalNumberOfRows(); i++) {
				try {
					Row row = sheet.getRow(i);
					if (row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK) == null)
						break;
					System.out.println(row.getCell(0));
					List<Tbdepositcif> cifList = new ArrayList<Tbdepositcif>();
					params.put("oldcifno", String.valueOf((int) row.getCell(0).getNumericCellValue()));
					cifList = (List<Tbdepositcif>) dbService.execStoredProc(
							"SELECT cifno, fullname as cifname FROM Tbcifmain WHERE oldcifno=:oldcifno", params,
							Tbdepositcif.class, 1, null);
					if (cifList == null || cifList.size() == 0) {
//						System.out.println(params.get("oldcifno") + " << NOT FOUND EZ");
						createCIF(branchcode, String.valueOf((int) row.getCell(0).getNumericCellValue()),
								String.valueOf(row.getCell(1)), null, "");
					}
					cifList = (List<Tbdepositcif>) dbService.execStoredProc(
							"SELECT cifno, fullname as cifname FROM Tbcifmain WHERE oldcifno=:oldcifno", params,
							Tbdepositcif.class, 1, null);
					if (cifList == null || cifList.size() == 0) {
						System.out.println(params.get("oldcifno") + " << NOT FOUND");
					} else {
						Tbprodmatrix prod = new Tbprodmatrix();
						if (row.getCell(15) != null && row.getCell(15).getNumericCellValue() > 0) {
							prod = prodTD;
						} else {
							prod = prodTD5;
						}
						dep.setProductCode(prod.getProdgroup());
						dep.setSubProductCode(prod.getProdcode());
						dep.setAccountName(String.valueOf(row.getCell(1)));
						dep.setBookDate(DateTimeUtil.convertToDate(String.valueOf(row.getCell(5)), "dd-MMM-yyyy"));
						dep.setPlacementAmt(BigDecimal.valueOf(row.getCell(3).getNumericCellValue()));
						dep.setIntRate(BigDecimal.valueOf(row.getCell(4).getNumericCellValue()));
						dep.setMaturityDate(DateTimeUtil.convertToDate(String.valueOf(row.getCell(6)), "dd-MMM-yyyy"));
						dep.setTerm((int) row.getCell(8).getNumericCellValue());
						dep.setWtaxrate(prod.getWtaxrate());
						dep.setLessWtaxAmt(BigDecimal.valueOf(row.getCell(18).getNumericCellValue()).setScale(2,
								RoundingMode.DOWN));
						dep.setMatAmt(BigDecimal.valueOf(row.getCell(19).getNumericCellValue()).setScale(2,
								RoundingMode.DOWN));
						AccountGenericForm acctResult = new AccountGenericForm();
						acctResult = createDepAcct(dep, cifList, params.get("oldcifno").toString());
						if (acctResult.getResult() == 1) {
							AccountService acctSrvc = new AccountServiceImpl();
							Tbtimedeposit td = new Tbtimedeposit();
							td.setAccountno(acctResult.getValue());
							td.setAccountname(dep.getAccountName());
							td.setBranchcode(branchcode);
							td.setCoopcode(branch.getCoopcode());
							td.setDispositiontype("TD03");
							td.setDtbook(dep.getBookDate());
							td.setDtmat(dep.getMaturityDate());
							td.setGrossint(BigDecimal.valueOf(row.getCell(17).getNumericCellValue()).setScale(2,
									RoundingMode.DOWN));
							td.setIntrate(dep.getIntRate());
							td.setLessdocstamp(BigDecimal.valueOf(row.getCell(20).getNumericCellValue()).setScale(2,
									RoundingMode.DOWN));
							td.setLesswtaxamt(dep.getLessWtaxAmt());
							td.setMatvalue(dep.getMatAmt());
							td.setNetint(td.getGrossint().subtract(td.getLesswtaxamt()));
							td.setPlaceamt(dep.getPlacementAmt());
							td.setProducttype(prod.getProdcode());
							td.setTerm(dep.getTerm());
							td.setWtaxrate(prod.getWtaxrate());
							acctSrvc.saveTDDetails(td);
							if (prod.getPassbookind()) {
								MiscTxService miscSrvc = new MiscTxServiceImpl();
								Tbpassbookissuance pb = new Tbpassbookissuance();
								pb.setAccountno(acctResult.getValue());
								pb.setBrid(branchcode);
								pb.setIssuancetype("1");
								pb.setNewpssbksn(String.valueOf(pbNo));
								pb.setOldpassbksn("");
								pb.setTxby(user.getUserid());
								pb.setTxdate(branch.getCurrentbusinessdate());
								String pbResult = "";
								while (!pbResult.equals("Passbook Number Issued.")) {
									pbNo += 1;
									pb.setNewpssbksn(String.valueOf(pbNo));
									pbResult = miscSrvc.passbookIssuance(pb);
								}
							}
							if (prod.getCerttimedepind()) {
								Tbtdc tdc = new Tbtdc();
								tdc.setAccountno(td.getAccountname());
								tdc.setBookingdate(td.getDtbook());
								tdc.setMaturitydate(td.getDtmat());
								tdc.setDocstamps(td.getLessdocstamp());
								tdc.setInterestamt(td.getGrossint());
								tdc.setIssuedate(new Date());
								tdc.setIssuedby(username);
								tdc.setMaturityvalue(td.getMatvalue());
								tdc.setPlacementamt(td.getPlaceamt());
								tdc.setStatus("1");
								tdc.setTermindays(td.getTerm());
								tdc.setWtaxamt(td.getLesswtaxamt());
								tdc.setTdcno(String.valueOf(tdcNo));
								String tdResult = "";
								while (!tdResult.equals("success")) {
									tdcNo += 1;
									tdc.setTdcno(String.valueOf(tdcNo));
									params.put("tdcno", tdcNo);
									if (dbServiceRB.getSQLMaxId("SELECT COUNT(*) FROM Tbtdc WHERE tdcno=:tdcno",
											params) == 0) {
										dbServiceRB.save(tdc);
										tdResult = "success";
									}
								}
							}
							DepositTransactionService depSrvc = new DepositTransactionServiceImpl();
							txForm.setAccountno(dep.getAccountNo());
							txForm.setTxcode(tx.getTxcode());
							// PLACEMENT AMOUNT - CREDIT MEMO
							txForm.setTxamount(BigDecimal.valueOf(row.getCell(3).getNumericCellValue()).setScale(2,
									RoundingMode.DOWN));
							depSrvc.casaTransaction(txForm, tx, null);
							// INTEREST EARNED - INTEREST ACCRUAL
							if (row.getCell(11) != null && row.getCell(11).getNumericCellValue() > 0) {
								txForm.setTxcode(txIntAccrual.getTxcode());
								txForm.setTxamount(BigDecimal.valueOf(row.getCell(11).getNumericCellValue()).setScale(2,
										RoundingMode.DOWN));
								depSrvc.casaTransaction(txForm, txIntAccrual, null);
							}
							// INTEREST EARNED - INTEREST CREDIT
							if (row.getCell(11) != null && row.getCell(11).getNumericCellValue() > 0) {
								txForm.setTxcode(txIntCredit.getTxcode());
								txForm.setTxamount(BigDecimal.valueOf(row.getCell(11).getNumericCellValue()).setScale(2,
										RoundingMode.DOWN));
								depSrvc.casaTransaction(txForm, txIntCredit, null);
							}
							// INTEREST PAID - INTEREST WITHDRAWAL
							if (row.getCell(13) != null && row.getCell(13).getNumericCellValue() > 0) {
								txForm.setTxcode(txIntWithdraw.getTxcode());
								txForm.setTxamount(BigDecimal.valueOf(row.getCell(13).getNumericCellValue()).setScale(2,
										RoundingMode.DOWN));
								depSrvc.casaTransaction(txForm, txIntWithdraw, null);
							}
							// WTAX - WTAX
							if (row.getCell(15) != null && row.getCell(15).getNumericCellValue() > 0) {
								txForm.setTxcode(txWtax.getTxcode());
								txForm.setTxamount(BigDecimal.valueOf(row.getCell(15).getNumericCellValue()).setScale(2,
										RoundingMode.DOWN));
								depSrvc.casaTransaction(txForm, txWtax, null);
							}
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
//			}
			workbook.close();
			return "success";
		} catch (

		Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "error";
	}

	public AccountGenericForm createDepAcct(Tbdeposit dep, List<Tbdepositcif> ciflist, String oldcifno) {
		AccountGenericForm form = new AccountGenericForm();
		form.setResult(0);
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		try {
			dep.setAccountNo(AccountNumberGenerator.genereteAccountNumber(dep.getUnit(), dep.getSubProductCode(),
					dep.getProductCode()));
			dep.setAccountNo(dep.getAccountNo().substring(0, 5)
					+ String.format("%08d", Integer.valueOf(oldcifno.replaceAll("[^0-9]", "")))
					+ dep.getAccountNo().substring(dep.getAccountNo().length() - 1, dep.getAccountNo().length()));
			if (dbService.save(dep)) {
				Tbsigcard sigcard = new Tbsigcard();
				sigcard.setAccountno(dep.getAccountNo());
				sigcard.setSigfilename("blank_sigcard");
				sigcard.setSigbasecode(
						"UklGRgAeAQBXRUJQVlA4IPQdAQDQeQOdASoAAwAEPm0wlEgkIqIhpNfL6IANiWlLfro+2Ygf9XYx/M2n/xeQD/R1lujf9H1D/zT+2/tP7F/8Pl/Iv+bN53ztf+Hv0PQf/7NMK9lbT2P/waYj/vfAk6B0qLmbasPPVYrz/A88XJsZzHruSf9f4INsd6m/6P5OXrXf6P1AbzB/GvUn/gH+H9b++b/tX+o/tn42++36T+4/2z/F/5f/Lf2f0p/Gvl37n/cv8f/mf7F+3X4YfYn9n4e/mP6v/v/7L/OeyH8u+3/5f+5f5b/tf4n50f1v+Y/zX7y/2v0f/Ov0r/E/2//Of8j+0fIL+L/x/++f2T/Mf+P+1/Fj+l/3e8P0//e/8z/bfvR8Avqh80/1397/y//u/zvxT+/f6L/Bf5z/rf4v///+/6L/OP6d/oP79/qP/h/ff///7P0A/jP87/zf92/yv/j/wX/////3v/n/99/q/3g9Kf6p/j/+N/gvzR+wH+R/0j/R/3X/Jf+3/Gf/////jh/Rf9r/M/6z9ufcH+if4r/s/5T/V/t39g38v/r//R/wX+k/bb/////70f/9/0PgZ+4////43/U+Vf9nP/9/v/+OVxFJRx1dHP2wd4q7Pce8IcFkWBML+ZR4s8oLm35LQOc25PvREb2UOPDL0RJ+sw1Q6nBbUtWopgZhkR5qQzjt6K1+lYXXWQc+HGZXCjv53usrKCbZLximu+il798Cu5mHjBNiAWDuirUWd1cNzBLkbnLBBqGKM15P1WpXTjubfgW2r7Rr7VQPTUaYEk4OXyErnphWw81PtyBhz9p3o4X6qYq6Swp6qzfzxvg9hhzNN6/TvqkM2OTwxLTvuhj/cxFHJnfTRKm5cL5+ozkLTGZluU/j77L01k7Rwkdk7griHASnjNq5ozPTEA3kXCkA9Esz6VnyErnqz4ye/Es6bJA7T5WcSXpezp/udGA4XbFFQ/Dx7qRfB+mguYKCcER5Ml+Z71pq+a9HzFfCys4Ag1WgfYwuELCpW+BhE2Pmx/dZiZ5wtmvt0lyTiYdem221nFXnl2VPe7aMfnEu8gs4lczfKl0Qx3bpcCpTMknBy+QbOUjCqBicFI2lwVnpmfDkAqs+WiXB9NuGZmMF9Rtmyi2WxVGlLX5zstOiZxnHTUcx1rVCBm4B3/3Iw6TpRx7yL5pzO8GuVkJP4LxzqOZPZj95/qz5CVzqcmZYDhLS3BQNhaoYQvPpprWJBosIhTMEPJZE57EtPzd9iXUd3YfzLei5Nl4k4HQchlIPaR3ykpkLTMjNw4cyjl8hK56s7gy51RmGUaBCgevylvgMYP34X/1+kLa3JNg3og6PhwmYnRtwB74LrBnFVwe7gq5lf9s5hnhjRs6x37GjNIKWRePyS/shKB9aZvRlW2+VcB4u9HMxnI7jQoH25nJd26PYqDpuCByhKOLuby+NYN3ecM1vlH5Eum80JHfq/KJCbWZ+vrwbsWBeGfPUJvFKqp7ME4OXyErnqxjD8lyo/hJxST1H8BoSec79hK4urCBeqoFGOAwmUBTE7JdSe+nSnaxX6Gg7YWpXE9tPn1RHe8Y2abR9PyumBRo/WZEfZ9sUP3YcMUUbIgTXUi2XeYetF8Kf2ojqfXFPj76CzaG1OE0DYSuerPkJW+PtMnJPjXAmewpVA22rzdJUrblLqFIRCmE77H42eDrlrP4IMboLkee1S19AhvvxojbEDX6Hzx9IsAilHWvAuQxc+gyAHi+KX2Gp6s+Qlc9WeX5pWDNzmmmk8ZovVZhz5BODdkCD468+IrtP8L0CiajMQaKV1QgpgJyHLrDLeCpfXBoLwtWhZwsy19DNL1G5UlvWOhqmPKVpNXjy4YtfbYjdknyUh4sZcPs3Cjz3mH0xMNQZRQDS/00+KiKSGEI/bAsbC7TGx4iqcviN3L5CVz1YS82KUZ77+yN7vVHLEou+Dy1NZnxrJYonP87S8HvNJJjfN3L5CVz1YtnwKzoMDgHNfRGwzV7wRCQm1e4WpTlforK5ghf9nYLpJwYReW4sFcfMrRyfYQqO5vaI+Y+rqG0gfmb09uJb/0g4m3E+/jrbhKThBbcEj9r605+lL18sYOiCBH6li6gSbuEjrZmphbHofh26lbCXiCP0GceYrUpmRh9SRq6tg8Bp2dKi+3pdz88B+pUHqXKD5YXVHzozr8uIafNJ0jyRyRintiOyVkq7enGRSDme/D53N8PtwOMW1l6d64kpJYkHVmpp5Sjjp7MjFKrYwyICOvn+rPkJQTvcu2yailPqtq8VyAjioZWZFb+HI2r6POG8SCZb30Xy+JvH/maOszfddqalmbjuzNx3Zm5MC/CDMyBuWvoZQNRWMOpnTtpcFmhR4CwJ2GCGmVk8xeUZnyfaBJrfzzv1akbInl8OZ5u5fISuenetWtw5byuw8264VMyjcuI0ftHfnlp4GS5pqGo3kTYWQ3ntBIlxgMM1KwVD7kRyNVl6CS8TxctTs8WFVUEneAxIfszHZfUTQq2+5AzFVWKfuZ0pKu4uJBVW0bGBQFY2hcJJODl8hJa1aogou5a0dEoe7E9jGMP1LeTeSNNsdlKc3K9S2FWC9fx1L1oksYyvRq6Mp1W6+1k+HZPDka2d5stdNp4GEF36HkDXG3IgJ6xRkgu/j7Y33RhYNPBjGK8GMWf2OVy17lmREyEbCxyYIH+9+eoJak4rav5NqD7JiuerPkJXOWnPayEIv+s6XMM+KVh0lwuH6lKZJvdJuydQMiaxGMXT3V43LoMOtZ0zmqh7W/x8d1u931sTAIdnOyihKSBzefMwn4yP+PMGuKt+XiqchXuSzp466b9fvRYYsbcB0FouTXrl2ct/3pawe3PeKkIM5MlH647UzJJwcvkFmdRjdZXeaUpIZC68zM9VsUgzDw1dW5yyoqj1DptuOTkm4ONUrcKQXjr286TU5UFYKOilqVdXBJbVeJcDlESap6OIIYq0oSDfT3b5TbeguV/qCHw4hBlaEXZ3hIC/iU0CENwgXSlaTg5fISueneC/Vj4PNDunXXi1jQvnZtj6d4GDXd66wCMqgpc8HhkQFWe2qHZSDbA72a/THgnJKxxbmxjNv/0t+EsWiOwF4bHtYkhqoFuQzBmQLFL16rz2gnxuQUfyzkkaYgxjfDTsNjKmAF0I7tcBy49fYaLtX9Bv9oKgrUm3wvh9YalLwikmBK56s+Qlb05KLg1woYocaJ3ouf2EU6aui2JNZPoZq3uiMj04CVaN/uljshaxS2j/3gVZH3UOWKldo7BImF6aZkhIWiu09bEW5zvyVGcK6NJt0sMQLUXf+w0Umiol0aJ7p4KSSU5gB/ZFfKb0waogTeY+ak4iRsF79H8ny8SMrYuBSnP1HXZLJ/E1f9RWq/8hc5hzqyHSnCN2EXHbfRX71XDaPC1oOYrUpmSTgx83PIhWcDHO6BUlSwkcwmsazpS13K/ksB/hkZLRJQ3nJclKpf1H/xrKFiJyLJKP8Q4gfPirUpmSTg5fEvTZCm5FRQHVQQvdJguiy/p1CrL+kvGufgTt+BAYaSyt8KRMz1JTBKQnD/Om+68HYvIFGoaahcqTBuatYOBHXLwRv1Z8hK56s8rtBKSTg5gwc/0rW5oV6qYk1aQglc6o5UmhI1DxOjISuerFsXXD0Tw3w6cXzMqqvngmEqqdhY6ElO+leF0rwuleF0rwuleF1NR9f4BQ3+atHi39PXc9fPpubmepkJEMwLpTcvKZSD6VLkkcW7XOII+VbSGceYrUpjkqrUNDVI3oPAsQ9J4wnEHxDlScQXRq6ZQkLJcbPoxszN7A1prASOmxpAktF575BKkGs1b6kU6m0dVGDMFDfVHc6lT/VnyErnq0NYbg48xWpn8nKc3Dj7cnMVqUzJJvFCYF4rUpmSTg5hqm+W4d0a0YWiciySJ384pHlWTPW1ERN+8+xZJ8l3F6vCvWff7z6562oiESBf7miO2586uu3NII5wxHOGI5YDCbzLffISuerPkJYBwPFaCS58YPznJKvu+RzQKHH/735URUEJRA6IZPCNWKs4+/q1r2PXh1MlD44kvBp3+BEQ/26CcNBfzrEhlCqbe2ukJiy0LwzawkmFc9WfISuerJRHgvz05gxzs6kXDQfmqVP9WfISuerGUDNxJODl8hK505gVl1+bkOTAgEqGUykurx8wrLpyac0z+ibYT8QXyXWOh0xEadrxinN+KsnWs6CUD/O+8CRMutK4F36y74lDN04q/wskiddTyiNmpZun4OXyErnqzypJ/rfaxU/jfvduK/CXdDuvWd8jt+jaH8yjyb4IEUBplUuhzVKyqq8IBW7oeY15Qrjewu+DWmFV6NG/MwpWmT0ZFCg8w62GglYhm9oik5zwx/5LrwOSrZxmvMwOwdkfX3Cof99Ck2MmAvnQyT2lgWamKR/bMpZ+favZKBO1q/jnirPYcnE0/l1av/bwA5gauk9nHyfGiJNG7mR2uCUDSXjg1ywxdXeKFYTBnHmK1KZkdpxIAtD8NBIvvTmCYsoRQpEmiFKHQQ2fNzEQs6kkJkwm6chkTjqR6tB+noa8ijWGjHVA4bHPUvWqrWfNGHO7HoKn2fzu8sDL4TcZtPecSq1rydvnX1cCGI080weK1KZkk3AtqdmOizmpwQyeMTOZGh3UfTw3G9ykGCOW/JtB1SqeoGcQAjpWDXe9KdkdnGMiXM8DTDMoJv/Rf4rR2HjepdT99nGDPZKHAoBRBxYgVKjQXuVbfDFq9D4hb36lrSzX1gaG4CCTEjTZUp+vMB1dEqr3ELuqskV36FxB6Znrftp/i4JHuwx8nHSXoR2/uarZdbEno6xefoxXPVnyErnpdIBic4sMqDWaurRzIajaz/3cs1RK50z6I+65lcG/xGLlyG20Lno5zr97XFFFDib9yvjmdIpJ7Pq6UR1SK6AZO58Z3c1Fe1ZHtlVWxjuCa2XqkmFFLQdSPwb9p7wqlch61u5kAo+vKtaJyNe7+2uFpQTiX7mbU3ePvFTLjKKO4JhP6NaG4jli6UwuX1DLDh0a+VpD0UxsfkiGxxvZQHcUT1cH25h8lqj2RsNXvI8pRh5s7dZ3Q+VGDhN/9vUAhoT5huL97Sic0Ln76XbslTBDvC8LhB8dkuDl8hK56s91s8RDtoUMyCZb2eeOrzvaTD3K6EWSnhRBtQHD4ibe3v1M6cDQwNfXqp8cMpnPMjD0xEPL5oj6dXrsHFQf2oxllg+LpqZRJGQAib0XMs86Lhc21Qdp/E2ity7INgQbc0KH3qUFOfKcShb567dn/RGxWLvJisEnxd9OKuElg5yKtg1jU+wmMdQmLlLktQYMivdtzf3FYPGX0no3x/7v2R+F6UiptkcNdKKEQMeqZdYEK8L6XtxcGujj2AGdfUDZ9tzK69s0+U02FsLVBYfLhoknBy+REGA2maArl4FLqNrBMzo8E1iSBQqTYbiKuOgCZ66R+HsBCFGI7+Pn8oCh5uROI4e4dylX1ixi+7KQ9kgx4BLn8Na86Ka62/1016Nq/87t9vLcjxhu+hPqF04XfPw5WweFHMSual1jh7uGjtCibK97eXt3ikjrU5RQX680jOP7JyJ7pyvnaZEpW7M45SeUqJVUwjNAqIbQLgGPAtMByhhkIRzg/UpK2dpWPDEoRe2D/fGtl4sBQAU42PPWUNK/khbh0oQcLiQMJP1z1Z8hK56utpbQXzoMPXvZrRJuqB8DdeYRcmI4zY7fsDtXAI5tCHQipkwFTGhbNFPltIf8Vi01BzymGQwZZFO9FQFBy+dkxjS4U8hGXGA78FH0rqGvB/FXUKZfKkgv5tibl7upNmPXfDq0Hjny5Nr/gz+gIgY5DNOtqaFCBGwSv3CYU7f1fInGuTkXVll/Ujn4GO6BqBuHc0z5neov32c4YX5ATYII9y8C7Tyae2iKkr6xHM6SnKjDdpORqa8VlFduFg98nvgG+7hJmGJfcskaoqS0pe1BABfinqzEztfN3L5CVz1Jzwq+/3On0KE8JTQLAVM6XagF2uJFw8LtnbDjIqOt9aFVgTtwOKt0FIXIikTrWgTJU50ms3RZPsuiLVD7KAS6DP33CITXyxZbykCPxpWMvFfIP6n11E+aibMj5gMjHdcDFAFwtqavc8vOEEC2wOl9ClJOPT8v6vLl6PUgZ1ooK2GOywNeS6TYMK069sz92iN9yYJrkcT03gOSdTcZ9yrH3Zg0sUUvRa4fYPa4Oo8z0YSDDDZQk/yZRSh4OdJizLK0s1Dh9ibccrhJJwcvkQ+WbzhoLZap/AovvklexQXvx7dV+RjB5g9SHy0KzN7F/ABciU0jFn/rW80TjoZWhrToLeg2zVAsYJKjonus/YSzTUWwOJgg8G/bM1bvv3eZEAmbNZoQbE86Tb8G2AAUmntP+npmtZFogaeCxrUqa6UE98UHDflncn/qAjSIKlWX96u6AH5rZNDUrexh/DUvGcGbTdqBgbOANlq1EuxReFf8Eg5VAY6mUPJNVx6CGmyrDqXfxhTsHImJYJEorxeD2JV58TPayifrh+HgTMknBy+QlgIwTaTA/MKmFDLCIIszfZom9xGJygH5jBeAyHUNJRPPLglP4LaiYO6T3N975QkjUJsSgADmXYVWPK/x6RH9hS3q36B3ikyeKR62Nx/PiNbyxNruUEkSluj0AS7r61wF5ix2qCuvSXwv4o5Oi3ufPWwQng4ZTC4iwKqWv+D8cE+nFck/5iGJIVxuWNMUjFX13/kHB/4z+Z31B5QX+PqselxLMGJYx+3/r/DaD7wVxHsJP6mjhIEt3rz8hb59zFAAnJXxyqxCW5rg5+KwsfvP9WfISue10TCg0tP95ekbzHb2In81ijcaCFQSeJDCq+ybREqkFSG5wLUp+Tgaz8cDmYVfuCC2oX9CIupm/mkWgllm9YJ0TghSaBuLNAbwxTndaJPB4w/nHRBn82BcaBBja6KIvEz3bIAvWr6D9t3L5CVz1aFoCcCOnqvv2RUY2jwHtMSbgJ5tUCiIb42XPnEJUtIII6+f6s+QlgLoc0m7syP2u3AEfyaL9KkELKtQTGVk8yM34a8xPtwzOAHKp0vzmagopjhFth2R5QQfkKUDpNsth/oU9OeBo/DUnbb27Da3oQW+puqvK1HUwWTqWoqSoC/w8VHMVqUzJJwdLZcm0qWe47pKl/BU12NpL0wJWKa4tzd0cO3HNHEZ4fxvsxXBLyY2a2pHqLFyYhno8ZM2fx+I97dOp7nnQjSQy8RuqDDp3B87zTAU0hwvW+tBL4QhxIhUsnTnWxkKhi9S/JLWYlF5tASLaUzJJwcvkQ8+8I7tEPKvTtsQEcSGfk8+c4Kfx2nFJnNfGQFBymcvbNWyfF34nrgwnGC8N56IcjBKDebjabUpmSTg5fIiISoJz31jmEoCsYMZUoXhQvAvPReei89F56Lz0Xnou35iteZoZhQT9B2MvVnyErnqz5x2K9acxP7uJBRDddG0OSr+NShOkAXqK7n9uzldfP9WfIKlHmpAYVXh1HoADVmo7tHFXvH7g7r3wyErnqz5BsavitSmZJODRHh6a0zINSIHZ/e+7gqTVja+7cpgNsekUv9pnH+OdGrB3Fdbj6q0ZoNHML4xz+N/5TyOOjqeawNFZ0vG9zd//AoWm80o3Po/wvHVEbwifW+UI/HTsRdic+uJkjyv9oGwebpO8X8Sl+5MpvzKGSJ7SsDiiJp/ZOoDZW0UHb6uf+0diTg5fISuerrRRLVLNX8BrOqTtbbB5xpPL7ZzkNNXEE33iILyJ0ncsEEhx4AI/73yk67jKToFThAKxugxrjVNXZAn7uisc6qJECnBxwHCVeZR9bEuH1+xemyiCmbarjYclZ5hy2M+Vewo/fOlEsQaYrUpmSTg5RfxQJKy3jq+C/S6HQki56zGfEHyErnqzyvrDvmYv8N0/8+/iPFopETjZfsR30CD+P3uz+QwB6omI9iJ1MewHHSCcrhJJwcvkJrNKnYAk43LboGpHE+wmTGN01f95aY+JT0McmnEtlpZXGIa1WyhWxj0kPZfahPZHvmCrTPtavXh1A2aJfK5yxXAybOK0fsLLESWsaht6gnrvXBy+Qlc9WhchaQdN/3gS/tzTQYdmSoRiofbYAcvPReePUTaaTugMxDH7XOKo8cEDYi4SScHL5CWCCd1KmeAxvJyDZyTym8ENYzkfD8VNgKz6oi6vRcNEk4OQmbW5kZmi5+aBmmR+WU8VxG1Zf64TGceYrUpiLlw0STg5fITafLOSYA0x5a6pzZozyvDNrNJborY4cuNu67LXuM2b7k77IXc5cMUK1T+hR10wSmSgLY9tv0eIjfw53VIaofT+MBp0sRurNSoTx+i1kFdNtEMU5s5cyj9InsPcHVKQsVxunLG1iZULf2BKxjnz+XAdsNn8LyypNj43JM4lM3cvkJXPVoV/XIQ2rWWw6OsfT8JtFY5LTb14X3vKcl7x4AszvaQJMGaS1MhY6cTZs2Z5Uli+N63VzexOR5V/xg8dI4w8eylHBs1Ixej8Bmfo1H5ZTsWZQR5SKJgJZdhgoOqNl5/+eXz3wDJ7wPkJXPVnyEsBh1ojDjPnDqN8v62ZC78SJ0Syb/DlxBUFB69fwkzQHEUPb8NYtqnBn99xQz+rCN6SZJ2KBCHvcJhfvE0Eo+NsWZPQNFuoU1pLAZX9mzlQJyR4a3VKYFc9WeWKWQlc9WfISwFbDaBbNdFWUuIn1mE944FcXe/LDCeJcc6Jfe/eFPJArRstvCZxTNJIDYpc9/OvKnJ8iVtyglsA3VSRcABqG/xzBe+uDJIzN+cZBE6xH9qwFVfFLdvt5d8n9ku17X9acSTgyNc9WfISuerQsrzhGTos1V1y7p9NOkuHyddT7WQyPBhr4UGmE56jcmI5a8g7J02wncZEZ6aNekCSkyoermGhpyJQBPmjvQyQ9iZKmpEW3PZxK0BYaZq3CefFHWb9fEejoI3QL8BZ31h7+fmVK8Vz1Z8hK57XZTNV9AFBvz0XnoxeEfByLU8cyvLnFDJ+YcAcLINR+c6BWJirD9MQ+jvcntOE/29lzmK1KZkk4Olr2G0MGHbPPnkKoGU/XBPHR2+DH0cVnPEayMnxWCW6SXu4E01mcsRbqhMKsdAavP5EamNdvoCA4lLB8Is4HmcLkO9TCPLuAt/SwN0C0gRVcz26nFdbKFc0odJwv4YZTr4v4rj4zzMYernpNcFqNLvSDKZkk4OXyIh7b1gqHm8FAY2ZdCad1/3ERqRhIwhD8h6z7YnefCIkiN0J5T8QxeX3fFSz5sw/2SScHL48/xpJwcgrANhnHmK1KZk5cdusbDkYXb8kCFsdHUWKfQc0hk7MNwtmb1+tvu47HwpJPOEuYQ8iEXOPCCZZFWaN2qnD8wkzGgKv6Shk314srI+Ors0bNcLWq4aN9Zg08ZmfDOhMxWY5NuiD3IFBJXK1kk4OXyErnbSogkehuerPkJXPVnyErnq0wDR5u8KEErnqz5CVz1Z8hK56s+Qlc9WfISuerPkJXPVnyErnowAD+7BUFw4MlqtugwRaINNDSOEYVgFeHJxX8mO3qC7Q0W6fbXMbNnn4Ma/9/DEu+7EwADL2qy/2NiVntaLD0K8eAlz92FLSXLUAzYc+cqF/ffldc0CSQDT4E5v5k8ihqJITEGOO890Bc8968tJsDWlxD1QLiP+nsqjcJFgMCfbIoR5HZHOwi8JzotBDtAB58eQvVIFmjTCuIeHyC5cOCD9r4lrOhAyyoSBw6ldAgxQbJwnezpD+aPEvxBUvGaSnxHUCUsGamb9ruzi/LhZUBVHOgnHJdd13PweEnhQPP4YD8bJG5r8MVXJwYuAZMzqr/XhZCPIAKCej3GqcWaojWvwXocSmPISaF+63osUuN54mZWsBe4aR4xbfnyUi/zWlws9wfw4P9J4xjm7yPmnnABYL+8rXA856lMlwc5gYDJ6JtALgUaKpc8R77UXQzOLaho77dkBkUXbBffnKVRXB05fA1P4I0u1rR2FNI2LH1N3N5mY2E0fpZbWYR6l8J1AzALkVj8TBMEJm3HK1BOhN1rMJJgzJ0b2XnoDxm3TvjFdU8IX0MeAN52qT4Sk7loHpBsPbv4KnjxC4FIAeG34ST9lLFm0DWX3vS9sqMalj7TZRZDlivrhTg+sCjESnEM7Ut37nXlvjefe8lixH25XAAqdb6hT1dkDa0/dNZO9gcOmSHA15CigOT5kPliRx/u+y3gv00HXYCQ0hiGSvTgFVW6YmCe9c3fUnKarENs0GhHTqwgs3qGUsiVCY4ICVo22nh8lf/jhaEYCml9Aaoos1ruSkLxhOd/sBCTdt8GCGdeave8paVOGnTucRVcMz+9ECscxXAudKbBr2wBOE/TVFHK0EkqsgXiOUuFwA6sUybupets/OmWbjlIMQMLqoISBYzNMULlF1I62g6IjmG7ZbHWBcEbpL7Y6uvt+hL72D5rrLgBrf7KPO262O6D1N9huqHJp3D4hoAE0ynubP4ER990tQliERSi2XQEKoUwSASUrjgW65+q3z43Mxp2UiogF/d4zd1UNqSfhQVdqHfhM5w18nW1i/LzOmr7RkFdU4oTBzVNLIIlmQr1UBNt9SZQVGj66nXBckKJy47CippL1gnxa4ZOe/vECvjb9OVM4ey/HUvvp4y08mC2XNXA8NWM0gFxuwmQOzI0hLSt/fcV7TpKwPpFhCaDZLTEwNfcSQKCseF2YURE29Qo2O/QUW27yIAVdWSN2WKQaYWrd5C2ugygfrRMEbvnJdSECLeCgkYNCPfAqx4uxCYpqLI9Ldv6EArlh3VSloI4U9FMAHiVa7/7qibfAYm7H7Y4nXJyT6dQoISNLqkHsJr5MioVmRshNAFnwhtcA+gLS98bmABtCqyDVGXmtqNatARk6k0Nd4X/l6dB4tv8y9p/L27MTKasPziBecPIRofgYQCB4yuEX9ykCK97yLZa+4bXrv6pee5dNXaz6iR1Xt46hwBrCDDaskovDgbYY93fT5WtbVzB2iUtOl1GQ/AGEmIZgxHIO6e7uCevuble4CNfBxF6eMLLES4Ln8UWaeJebsMcJLVCHjOxnle3DS+0KUdpwJSHpzScgY5uIBtTjJ3GG5+Zh36iiA1Vi3cp00jXCIiWvRH/1SMcAw88fozFFggdCgVQ3FScqppXW9oDrhpDcINjEjYmHdIZhMbMZo7U+T2jjLYxurIhGPMhuVsLoC40GI2L7ECoGwihK4jPbxRBfEO6V1hODHEAnyk5OECya3dWm+632JpDl93/ECB0VpU6JcqmnD18tg9ueDEMtIcMaQlM/1HDyzn6wtc81STuyqiE9v4tNtXUDzYA2j+ONN8yrSIR5JGWOiemocWH6hDVInGkOk29mt+FIRgSI/mA8w9wIxKdXSMzRFzej9YZRWnLHbt7KcTM+6iRyQAzM93cm7s7mJniOaF1qqSiUcbxhpaq7Bs23xHpYChpN7wBA+hUIdBZVCBKYk2tV81s2fzmU+sd0WZ0261uuTgLSK+6ARmjTeDjPaw6CzIx7Zaa+PicKWpKng04YZTHWG2+ol+TAecN9mEzCvHlVpEiuHeFJL8E/PlyCkTyOPyfsQOJaPJlycOBNBpSZpeudX47h6sVAdF6CvKFsSvh0ReyjFIE1aDmZSEVNBzAOG3igpjYNlEy8UxKK3/lEDSAWMr/qsZdwxXdQazNvNbN9muqjpjGO7aiTsNhUNwSJYZLcPjQoebV+6nc151E7iZk+QPW1o8WHf9+MxF4QlAkCo/qwUWND4aN7MgeNE0kcTeK5HQpYJrdg0uO723DuavXokXgbjSj8fNUNvM/O33/oGB8s/WhEbxIYYWm6rTwUwnFSFY6HvLZvYhC+EhU+8DyZi7+gt2Y7Pj5inugtoSL+E+KNJaohMwq6LeNZJkW4WwjDqhW8Ii3bB+hi4KZTPa5DmWR3w2cMxJM2+fXXE4EFgbPLSKeKOz3ukPYtA3rv0n6wySgUutns5XlIohAxkvqprvm3aL/9kvjH0q4nfjsuqUck5YJhgpxZj/5kvxTH1U/utJewaCKwA80dXmQzwsHICJPSk0Me+Y2PdY28Whr/iRfnRYA1lm6CZrjOQxgIcP5a0qdXahyHOGxEZpwgwELFj8fzujB+yJ3//s4+RQwMKPMlHs+eGvuRBpf4VGA0L3zT/OFTqrblx2Y4UHlhQjN1NfKPwpD3za9SkFMjbIOqmAJ4j4+zjNiVCdVuQvPhOZUd5ZZTegPZsz/Ccf0fKU7Q6aNRQvFFwb6Tvay955bhutbf0jhm9Ve+YzUf+IsddOp/bVQaw5ldjNxzZOfudpKBV/nzqJXHZ38HNvEaMdOdWJMS3483L6121QamSTwdfoAKGUOcmYnyHRLE6fs1dyVz362XsvPzQrUqmPfNjhv/gAU6BQD/m71SBEXJr4N24V2Y1xCeKMIMLEu3YU0Tqm+Xnfsb2570IdHZ9QqCX5kSA4qNV3tbLuf2eXXd87CvU4tZkWwCxWW0weq472tZHqDnzZv1wsj9HqkQgWDFBOggA0+uItyXC5UHv0hRGlp3k50nIE8I/NUlSHlWpySO1f1Q5tUgW6vQNWQEBR/oMcaBxGVAlJdoAGBqKqfrZ0/Uqb5kWdU1jrlYazpiw/HfGuKbfYfnpcdiQYAjwSb0qLgGvr423Nnoyxu+8kU0U8tiXppm0zF+m0rm8YRGrDhjjUnY4JAtcxdXOdhUhIABEkdI8cKwYGcEQ1UL4jHVg8jP4nbQYJzD5aSUmkO8f/0m58+Ft40mmrMcbAlC4IdiZz3MMD/Lk230gwkKKe4ruIr0nWn5OkwRdgxr7DJWwVahRjhVis2S9iJ/9FEoq5ty5TJDaVPAa3lR1RjgmIJ4tUgPws9ULfbn9GdNRJkjmZcHZBCYgNftYpK1uLKUaiQ/LWVQnlQHrnhlY000bmT9XAtzO4wz+PIZHEVVsTM6C13CYMJzSYyv60Z2XU8rPNJJ/fGW9cGfy3wZz43NSQeWYOosvBDFqfVOrDFqpP0bxJBnf6rPFfSlxosX2Wa1GNmGBwfHY9gSOSi+1p6wi3d5JwgeZr1OzS3/NMqNem1/Kfo3FdBdhgXeHOIVO3iwxGopMFgyLV3lsKIE7rI9MKkw+i8JO0JeldHFUHzXGOywEisU3IXS/3U9HvcFAvPzp1S+4STI6HMgXsbS4o5gl+AgdXyJZ914XNwmlq/xcGoy1AYwJJlH49jJF8joH00/VAWWxHyfPHgnVsngvo92htuhfW3MsmXi8DGw36jv/tJX+AQOv2+888Z+OERkR/AFcs6M7Jx7BN2tS1TzJXQPZYKzRe9OsUXve7dw5FGlvgar2ep4Nrnmn3wupqYiln4M8puTQm8BYk1gkoVkUtayLTt+BFn2gQom4/QrJloB+GgWvGWBPk/sQEWOhXmz+hEcKWL8XKJPQ5BVw0o3yITWHATBq7HCXcyv0PNEAA9owsQR+Z5YCRaUE45ThSC3rh0RAYPR/h6oFuVywNuF8KdOoK85yW/07fONLwQrsy/UKPoh5UZtj07woiLxa0WGNs4kZziZNNAAegPov/UCRdTDbrUDj+GRSK6C2bAGRgPuY3DMRfscKUy2MvyVuGef3eF4iIWbK2K49kfC20VbunwSGCtJBZYW3PpEsYSVqwsqLjLINrGMnKokG5BnWUzZKpKssGPFZ/kRVgaEU42aWDC1WUkQsaNiB1El9xOEyLG+YFCW9qWfHQAhQa81trou1wlCOT9nhcSgjmdM/IsSV/7VspIdu8xxSI2vvO3YljTi/YqXB1sOYdT/IPUcKt4N/wAVtETQU3j3ncjM0Lv5CtZgoYjDJ5BxIq1c4jBj+QFlOBoo90YWA2NHqmyQh2ww0RFKuYQBrVjcI0m8OkpIiyfTp2FJVVwaThpC/TXy4B6iIM1RF/NCurNrYq8c+b5rGHUu/AqUt1CfrrfTKl7krdAcOhc6sd3/MBD6K9dhgrh0L7xpaqBEmVtuibMwnewtdd14Yo4XDcujBlzqIxqPh1QZayCN/HPUABIl6bmfVj1EmuOizQEQdr9eBXT2Ro9KCMY5KogOKuAuzdACZ5lGUgUovVQf8gg3k3RRdK5vnGxTCYSVZliwY9740/uTLIe1DjRBK5QNy3wPVVBCXM+QQ8/LsBsI2M5H/nBt136o0NKstnqMeIfXNOk8E8klLZaMriIbY7+UNg3eD9JMqS+xKS2WVPtl/oSjdQHfSoR89Q5+XDVTPaOhc5s7oKeCTpBfwz5HrxybGTb5pq2NjUA3OLL2mlu4YA628kodMhL55zSUu/w3fWIXIiL6ur7y4GJzj9lKUDTgULxi+jSAL+o6Xz/5hpXNFkFkZivMH563cn57u8qUp7y6DblCLXJ/SLRSHfYyBr6jh4AzceAr+T5nmCUa1NYMDsn4al8yaxNOVVIrK+ZVQ+rt/KuVOBtkpLjqS9iBVaICLar9DZYcD1FkS3oQ07OY/l/mMaJmLxCEX+HjNZsbRP11vkusnE9/HzLJ9+rfZBzxtxhSlbayjFQQKs1y88YLhbVsKqXtIQtKpbqxuvKBIMiXfk4UWGfhNh2+ejJb1cpzh17sxw7JMwBKbP5+5urHBMtYSpreyiF3oa3u74qwbhI4MYbN6HuDeVEcNs4QOYcK3Oovq8atHcxv8KJvgobEMgi7unJrl1NwdgJBGTIZEQMfZ2MBfDYAJjuecMlpw+Uwtn6yuJBiSOB9qzGY8qFt+TfVtPNY91oNjiHUvWHe3jni5EnugNc6O/NJ0W1p5bK2mue3fiRkBaE1Xrbs/QfCPeVrHGJDaYGPEHBNvxrhEe3l5RpbAjGhOZamYCclItb2kYDvxIH2rPjJzl0TgnrxsW7R1Xi9v1+EJcl2Sy85ANHo7/olrNiAplpx914HnVXm/boD1dLYQkHV0aXBem+r31oknbXmmWbCfARLmeqorE9EPgydNIpmU2bv/4smWEkSrd+FJdhaVw15lgunxXGzSg8HJYfWJe5iELLSntxGcpT8sNK1x6YAzwlBU4ZzI39ISgB8bOAc/KdvLB2oTmed+euoSEpdV7gwjiuEn2PoEnYbr/SXFUJHElco5aELhs7baznhpyJlC3btdxFFkIYhL7gxCjUFDafe7ikAtQeJEX0cyMnbmNiMgcOYAlUpRKR2A10g7l+aD+zdXuB4yFZ1byDn91gK3rS6eVVuLtHLNewqJuVGzNA/E2jNbImQBlXK3wTOQK3T3LxfLw3Zxa0ir9oXxdORC49RQSdpxSYAlWT5yzopvly1fiNtbHBbQOc7C3Av6ftJACicCYMPIrQvlhIdhM1YI/0gUa0H9HfDS3I1javHBRwDW5M9b2GtN6YZVCv7PmTSEHN+eby99fepx2hRWhazJbU12QUZ+B8Hv7YmDSlHP184/Zd9rUHufO04jaFzTYLeibCiK1tr4faM2Di2a6Z50cw41K6KKbNT+jGuPRG8om96ZtqJw38xNWyUcIk6tgj9ri3Qj8x0xafYJ84LPuZF8r6qtmFlHUUd+FyKNS3nJsqvn7ZTfMEaWiS0G0NLjZMp71JOd6gugHgwF3BhKPW9Q/Mx0Fot1+yQtLyZIcUkSz04cejuO8mgzgN2TA3O+MozOLlEk1Zy7rxnaizR+kMxqHK8jMp7i1t6fJAqY0GmyIH/pQmCTM61N8Rp1lISWDVstOtdyaW+SOoAYSojo/VlxniwmsQj2hGoK5mB8eYlCX0SroNWsRoYG7LxAT7zFA4Uk/ebWq1cL8iyc5P+M6Vi7WABtChAIGqkTyxpinUbTr3l68XtzQs/HD/0NihUuObKLYrZDt7s01Gv+eqpgGEU1JUKnOpaJayFT6w62Bw4CieFmKBo1w+iVCQ9Ky8OvksJ4hwX30Dli0CSyqKsg38n/uROMSRG4Z6DUJaOvUtnVWBYGFG56hL5UVftf6gh9HkmcXf7GX4yXjfPs4OL1uw9FRBIahQw1fa1treTsuUX/OA+xTAs9ccNIlD1SyhS7SCkewvAwKFYSJ6/xi8jqnd27kd63YHfQde+vd7w/6E3KYxjaTofKge9clz1RFS7jzTJiFoAWz8Hy4gONdLyQtZs6NIgAefD2xlRGMxZJZLWB6NtUwg3gaNzZ0K+ivGI4FJ2ljVcHQ8OQpQoRqpQ8vfvZJPU0BzW06x7ZQolIqKJHzvD4X3yKisVSuVcw2wKOj9Q7nWubMmLD/7ivPfEiS1HGb2DkCzF1LpSDmycM/nUulOs4/3abrvvIs0owxsXiUmfI+eud/VlPa/XtVJlkYqhCKOf+yOA08ctzlPF0HaJcN0jY9BbBhR+QOFlg8Ik9OdTOHZwtB4nuYcZnch/2y6TRz7I67y7hiNz8GI1XtqX5BxwGQO28fDhyw+Kwq9HOJ/w2VAvSgyOQX/DwKnYcwqmVE4z7YfSZi8THufqbAlhpjvbY1/EtE5cWjbn+QouL615n93JRlKOFJ4uSbkDjbL6ybhEQhhc1qtZ7PRjhz3B7OU3TOdCL0NAIxES9i0t3/R/O7P1tlmDio/pOufX3WXLg1rF6mcfSyRvchP1QLeq3gPXxaMKOBUhm6m+dJ1O2fBk72tlIhb4byNMn8ioP/DikRuBCs4gaq7C4fzbR4rueqVxs5r3zpAbYfISqJssRWStlwSbe1jGVO065fJ+6ZKHy4AWMGKVedXyWQ9JVK3SRVPmZe1FbA5wT0rp0VwUFF8YB+E4KTyRnj5NguxWzY9lLDGcvmzxLnZgtOp9DBE/C9pOlr+2jIpUghNuvHMqb0sOIHApYpyZqDGgU+uuF1ogkXJ2anSm4+oDh37Ii2tdOcxp4oiJXmAuHg5vVxXxe9hkvi5nyEfOTSXf7rfyNzRRsdMfRV7jeKaG+t0+g1pAD9tpLXboAHXt7XiIjKV/NhEHmt69kZ9n5JXC1b5MIIaDEnczs1iqXVyzeZiFRwQQxXOSrUDRnHFp5tuUrHpPcLbKJUA5g2v5R9MWYucQcJihiteQtSjzpAPVoanE03VDnoCl6ntKbb9x9NoRcTtRCU40n0mU6T5QNRK1l3VeD+45GnGrlTx8P5xKhHq16Fz70EF5X6cK5ascp2IHViZnoPwOnFxntE69fGyeiJrerFwTu1QK0e8lCG67y4EgTMN1vZDUJS/2kCUzVxOVpZvQHRhlkGEdAiFX35YKoUB2ZJY2KZBX0IHGX7avjri6uAf/W/HwoAHlk5/oR8rT1F+E/SQA/vssheHMJHTf7A+ui7XggjUamAcSjFEJr5xcMKxlj0cOzJdu4zFthQDK4MQRyW3MGsRw8F7hMqpuOI2LOpI1YzAVS0mIDCOiEgS2ysoKUXgxQCsQqEvCsFUdB2LS/fQR8Ssk6CHzNzXw5wAhDaFBM5okBL3T0Rzj9lSxv+COOqI9wIwE7UR80kT87jvEDrMdDcGtKuu0YZ2gSlpvQrkGBc8zT/skKzyXeeiv2jopTfOrDuW2Gm2/VTjRv9M1sS6fZw3aVUBw91tupIWHbTpP/GfgjZJ7ZbWVa6nUgNdnuxr/JeeEwXorH7+Rs6LEtQ83iWqWCLWB9w5AvwqzgeP3WjE18nKykVz2kqKESdXbCiMfngBgBZ9lBu7jXXYTeo5B2yCv7LOVlWQWwjdECuyOCEd71IQTgYFW60F4vQz+sfmRHrRnCLfKZkksH/qmY15Jv2IkZGap4/ABvKw4tn6PwBsVgtcm10UlExnNalanX514drOgrSyoLBGbbDSKgvkQ7T5VhD3MR9/QsvILTWQA9iuOZhDc1IKLFa57gEYsBGXl7Mhy5gYLI7EPR81OxIpD2m/HZsm7+pssGBN0biVVKrIvQ75dLuY0DFlBUfTUtVJlWI97bDDWrzz4Y/6wbL5smvS6z6aBGFp+99odWzOwMF5aS8bavI4Y1X4rDbzFrZHPBXc21vqhrYP9vqDm8a0BGYYRAUIQ9pGFf5BdFONviCmo0bJjl0Tj9Gxr2p/NgLPIWpBtDpR7n7ytSMKGr5eV3nGUWskbnHsTbBlQZKBGrJkC0xMYrbxkv02bXgmby+HTb0huG49XD07WWJRRfMm5k33qNiAS69Y7/3HKdLNztNp0kIT116SHoK5bECH2jaIadA6jZs6T1bNuEvbpSk+7NXPLgR9vrD5FNgoExJSAemI7YZ/CZqhvFeJ0ATOKsX6c3Wmmp64lIBCdsvoMkXod4/8GZZxu5oofz69CajOmd9BoZjJrjwt/l3sDjFnm9DyN1MYcx3co0Dpi9DU1h0fSICgLQj/j30t/3z64/cPWQXAIAMNUfFWDp0I7DU8PYLSD/ip+AADfMexaaw9E+m0ZvkNtDEGpB4hqUMSv4dQFeXHr3p9StFv90Pw3C4+VQdPq07LF72UJNLGtC9D4gMEpSkJwfs2QABMNnRn78uK+xEYFgzBvcRPGUSjoLsgzsKqGVXT78dIklrKrSPF5W39Rcj0I0rHYogf0pCJmR/BgtoLFv5oHLI+JOC2FxSGVSlbQCfNad0Et0CtncJSwBQIDfSPbUcbYFV7WkfpNggo9/GnsYvS6BFx/hFfz0bcK1jXOMNxXcrB/09jZ5jR1xegBn9WGypm9LXMoVk++2m3ceErd5MPqe+ojFb7JrGrmEdd3n8yxecB8F2APAjSLxlC6BkV0K8Eo/3udM6B99TwWr5i9rw8UFKgYJ2nNa3HR2rCmLTI/rpMsUTUw9HvKXDmPFIY4NM3ZNX4uDeqkUO1BJs1B7EjRhRHlQqmT4HGMtL2hMi4CbcQQznm1r+ZOnkW9DbgLbQ63ICfGEf/I3DoiqyON+X07mMDpborueF39qwj79UphHT7d0+YiMjIsoPXz7lw/RJk0WXX6eJAOD0Ir4gpSzgHHzSm0OOzQ3N4VFFVQkdAsdX2MOeHUNXnQyxDZ6DO62KK2ahMbpS+9b3tNim4PZ/MQYDMSa249AhiFhn7EhcARl5yr8LQ4fnhF2PiYN/reAxsh8MPHLLNKr1yvcuCTv78wjxYZ5E3vTT7L65P8D45O9q+AqgVU8q1edy8DkYI4BDl4KS1wNI1zzEmGf8pwXyt99mASmedhkyuNvtQpGbshYvvqyh5gq9dZJWPTiA83ZPc48CsonYtOiuvqk66erSn+ZVHc2kLmBiNPGxibaZ2EmL9mQ/031O3+0krzt2gXZuKLi+lz74Y8Gqi1mbmuoN69gVkq138y3MpWVO/OPXXGygj50FVqo4uIwUNp0Xs6iQS9UH/m1D0hXZNRLW0hnqD03hCoDDvPjftl0JcPO1LZIwElIsABCr9JMsh6CSozmSGreoiZ0tFeLvGEa2DAfgmov/WfwahRivX5SFO5fZ5rTLJGrT0ucWIkNEmPpMj6V0xpU3SVHWV4OqtHzI/7mfkX6r9Wc2/KRhm1H90MqOIpu98+rOzOC77ugBMjWxTBaqjvLMX0tNV9knpnejGYhn8UKxY+SNPvyYIcgJCsWppO+KklL5A3Yk0WZ9UJnjAY34ow7FCbN42oWuO5ShNgcROYn/t+gcuJ/PXp7yMzQvy0lTHqrlfVuwJCyMPkqT0lb1NyMram86DsfCS0HfmcD9WfLTs4OhQ3mZMQ36z7t9NH34OAicUUWNfAG7/ChuWPn7sVs1cPdXClTQ+kSDeoLqGo1Bj/SBGuY5d0AwFFz/bi7/p7vxDojNeYA1P2W1j5N7cRVWFXl8TB23ZkyPRxMzpFt5I5Vr324kb1To8sWLpqK/f5wGaKrI506UgbC1lAlXuy1g0rn9gmUWTE1cAAGx0h9TcZ+dt/NApryfUD8AaoLLoSkoqK5dZpCs2DBtV64RJBQjvedOqBvhc8ZmBIh07bk5EgRWTxHTLZpn6VYdPk34oNwuNPuHDrCxNAB9AEm/6+dit5ZTs0lc2PshJtCsxa6P4WDHpsm+H7S8+kZmA4yxZWO+0uzg1K3KxdkT9IpFJyTjCALiYxDUPRdCBkAt64tb11FU8fCVsDGlIRacFjSIZ/nNTqWNnzDOQTdruEvyYtjz5yedEMoiHFnp2+VioA/29/OBL0whKtp+Nbq97hM6KL2LiGI7+qbTcROP8fVy0Qpq9cN8w1kpcAney8i0KWwJ8JEGIyThXoaz6JisBbtRzCHgD7bCn7qkBK31qx7ZaoKC0HCcqA5dj34Ql8Bu+8xIBSMgwTfL0588gNnJPO4AVywmg82OfkFDB+bH9SAdCMkYNup6zpMnBisw2v0KN1AmqHey85Tu1q12CQnTSj1QCgB9ZblzHCyTzGw3+9Qtz/EZp6KzmQyRlsmnAByBN1YtRQbmCNbyqPEdytF5qETDirURSJdtl8Ozk1OHceGidfO8Qp5s4af1TjZ5OdY80Zd2AOdxwmEyk+0wb/Sn0+kyNuc84hlFOQR4NLEdUpxyLL/tzJ+Syrds4FrBgkqVM/VtLlj9W3cx/XX19jmofDbXf7RcvOYNmwh52Hss3QanVMOt1WLgijgWag6WedH8ovfmz1bj9NJXNDTmBj0h7Em6lQJA4gGxirpUm568OdatK36x158vpNGoin755HOJstccwxU7oBOhXpFiygeKhDpFt/EinLjOqWPdv4cwjSaKuwufxxOsY1d+S5AxU5Y3P8JPoTN+wopP4RrVqNrMfVILDCThvDHt7uZIsTFH1Zjg0fV+fThcVrD6tv5sLX5Ij4Db4mNwwDGk32ulikZr/pCgi5J+oOKZe3IMiisqjxIU3V4qwz7Zmc5rFnFToWpIcT2ymNVk+1FkQR2xNswRqUqXAKI92MiSJJovW+FFlwzTyHOXnPGvt0hS6+TW/5m8pC5aPjCjoV5ivEmBOVEMM8ehxGY5XuVVUc3Kimm7XC0NOk4MLCG7JZEs13laVBELLie8Aq4RhKWgBkLq+1I0kUOZBluUTOJb+oKD+9lXPaDN3TEtUo1kFvfhJcCvZcgQ8o0/pJFC2aUFiUPjaU/UaQiFU/VTSJVx2CbCjJA3O6cWDEBdPzI8bcaerYJ/93IGkZ8j8JPyHX4RHl2J6/aoR6BuHbbeLGaQ3Vd7ehYUx7c5nfmzB0LA+ns5ivI5y9sHrtJTsLFZKCLQFJvynEcqnF2hYafiRvoNZwq5trbeMZKPrg+MG50kGxojH2fisfL2+AIvYoq5GQoziB0i551ix6tQsOnT/NaAPKwMfc3jfMlRoHUlhMv4DXS28Fq7HbwTM/sSdwelXd8ahy1TaSqEAwqEgVI7LOLle4HhdEoxSEHt1wB/8C2nbkm0xk/Oqw8HTVBbWbVC+g0Ut5dNOxYQrqIvmUS5Z2LER39fuufCzILfUNq1LkD1aMrM1TWB+914cLR77v9ElcJGAX9CC+iWO23HcSSFO2I50D4gJNfi5LE+VWTPliY4rXHxckpBdJsuxfqoqd7mFlRmA/SwBvJvPO349bLXS1Opi7OBh1YZjqbp/gDjOASvxGo8VN/g6PCrBEkP9oCj6P1XYTRn64wIrHMdMMhKBsEhBXVgNNITWpKdualKPzDZsDgfXdjZjkuxyCBctZViU5tsAXowbFvs75Uc8PpVTsk3SQZPzfZ3DI1Sli4R6/AlMsAgY2kmusVFfd5k4auSJYR+eqOmXi8o+5g1b2t7r1zhPLLVken4uo0CYE1NRk8y/WKu5zDPX63/Fwn2ZgmoiT0NDxHr/1gZoYbE1PYRAhwjfyANUexInibjfAYQIQ+AtWcImwx/mx260qMPKBL5OnYTT8aNPatsPfvtvC5qmSeUXIebGJ1euR3ftZ3DWRaavi99FiPhT9Mx8/QtBgT7oL+LGI65p9ymEEP+x4ru1K+/hvtzkczLCJF8FjPOnlV+/x6Ox/EcQAR04/pjvA8TGT6PxDaVb1+EX4UBiYkYAc8IPmbDM3IVr9lvvqjkZsV8OOv7LQv+6/BPmM3cO4S3kdCTgLRgUtynZGPAbtLOLKklTIt/C6WtU/fzJmDjkETsETBztvWE9oSIPnoKyQ7qA+Ti4xtP0wNSeXiLtEwTEUcZvxpOdjihb9Qnmap6vvWGL9cWrYFioNkcggqNokGMm/S4rF1fRIbOUuNahdZow4b2UBVVBLoa72hEtrwYConfZAWcHPAx0+gY9Un+SrE8wClVbkgZ8T98SuRkFmvX8k74NcLpWcwz2vsYTUzctl0M+DZkfqxtGupWESA2AiRPHLjlXC15w0MeRLo2bO97tR85T6ObKcidxKLTS4OEg/26s8BbLzGdBltgB7xpHKp2t5QRcLnN6qWQGwbZLbC7rnuUD88KeClxfGRdJEzD5H7JLtWsTeJ1oHoxOSIHNXGkD/h3RyEx2lImWNGg3bLWJWeWW+w7GtqCAjhgOq6jqZ+q4Jj3uv5O7rk3GB1CGyD0cDzsL2loAV3XogwKYP83VXoCiz+VWwq0vmaE8Y0GJoSi4nCYvapTAYjRHtHEGFoaJhD15cOD/Par9F+hW8phtPJ2EFaDlxS+qHpc6Xg1+hdulpghU1ya5VfaD7yHXi13SbJcGhvaVIb1kQhGrL80gE3qvmNptlxSCBK0mQOFsEba9LfxGZR0KXxKzKtj8Wqk5n4kebi25k5+EM3kooIGQbPaaGQVeHhzvYFPXGboG4RtuqcP0jVbl2okM/IVM2envOEaf6xcviAwEFE+mk3+w37Z5xkH3kn+lnJH1o1bsdU8HiPBti2iRuDC5jfajlEG2Q0Uzi4D8AXSa83uW8/Qe82kEqgRPG15rBt8GdzhVW+0BUBsmI3yYrWaAcvG0ZDDOxN7L8eMe0Wei3Dpgez3n9YKzae3Bu9F+wgA//VqfsfgUJjr6+i6Xzv4mAT/rKvHkeki3o3+PdoCbJcUBbcegAmbXRJf8oEr+d4NoSzKK5SWHvXJMfcEjEihVzhx2n8NL579kvQtDlTPsoaJMdoHf1eQ0q3fNxEQ9oiwEeqKEnf3988nqxser1UggEkqZ5XOukEAgPGfWRG4eglniTmqk7HbiL1PJ2dVThvtSgffNLsmZqvBW8fqpX5mZ7Rksa660RVNgVh53ElvP7rPoQM52EyS04DsA/ONuB2VdCHJyR3j5D/xVFm4bwmC+3873VW1DnpAf6/HvxvegVTyVVPFZ9ZwwHZJXlKeSpwsQbVwXhW60uPYTEsVyPf/wueCpD5bnzu08un4QQBsl7nidNW75uXRQJrnBC1GH/XDZ9GIQ640qpuG6Myu4G2xc0YjHFWFwgLscCXa/afZIkqQWXNU041HIDXR6bX7jKb/XxoG18tV+l+Qb4M4pMQ3zeWGagzbviBdz73EYz94JXeeWCKKJLhpEZ77sCeH8W1aGgzz9iN31WLJp537l3sIuMSQfGmcwrSWin8gGJenunxBBdTw1do2NmNoOqsoX6R3Nrw6TXehfqkJ2ZDtf0fKMrrP2cswqXz0m5rA+ZMjYPYyy2zT6ji7zjPB4ZKO6h6HsrqHFPU9uzQI5xVqdPUzfqwUTIYhz6JJGlG6L8Wu8n0hJ+2LrdPD3bQc3Cz8+zXmun8TNAiqP1Qf8Ga2XBVN+SiaZ4Hvp1If+edjLSdmAnysV4NB+DTitQ25aiZeEHZwWw5DPlwn0TewEhImeFtcp9uCr+fkFZrN6m8kskVtKR/+i6VrBYqmxL+HlK8ViWL1PDhYRIMJUlv9/v5nYgVv5NGLhjGN2B5cYa9/TsWu4FZ2oztM0FvMts1MW0zTpc9b7x4OFFTkQgJbXwRrFdfNboZ/A046fzbiH8P8ASAmsuhMOTKEGc1txvdywDWGDdrKTRPfvPjK/PHDCZ9wRMMbXoD5Rk0E38iY7JFkPyzr+PjjvIrGHKd5fvcWGSVMw1UQlAyER+Bl4i9ofHAKp26vv9/sxGy35ZBcPr30TrzM7vb8t2nIenYrZ7N6v71zCB4uAGcR1n4YmSj6WgQp17xiIpnoFe1dzUGdxy58v4JsJCcHExRll72M0C4J2cUcFHEwyfGIPgLA9VKpbB/jVXG24+shU72Zb1a4pqcH9tfvV8bVXj3YVWDca+AOXAwy/FD/TE5P2I/fvel4qufCFx7racSPBMcNuQXRMbqoWPk8fhyuuLTOmGj3tXSMnSJJDfgV1BG+biOduq8OEbjmZFxrKEsxiWTFRgAZ075YXVN9eTg73qy1GNrtIKYMmFPvIyiSbxhKbQzglmmXqCIaxyeqenB9KG1i1w0ZWmqbn7MGSZg4dzEgjX0XG7ahj3C5qAO8+ABi3FtMnhqXvDLBChL/mSOd3stUPLozKcWM6mMVG6ESiNkfy8bVoTO51RkThm34xq8jzu8f9FgVBkrCeT/Tgn1O047KnlO2SamDQZtRj9se+w0zQrqj033yx+ME4hEIzxL6OMh9rlRJLu9gEzu4S0mtHnz57W0RD8s/NVkcnoBLNNgMEUjbCHOyV83mgDK/0kZfbkGuzpZwzvzC0vlY7toHlhpS+mDaCm3/i82EV8oINqGF0Fu4s4zwdyF4U3r/SoDI6z0KmYwFa/CO2kBq0/OySoRyJ98Weixs7OKVPf4ISdm//KNggaRzauHS9HtMEqTOooSBHHg2mrietlrUB686Z6FUe7HYg4XacpUUXc4nyH/Qk4mfMIXZ+XTWUqiFoB02vpNSnkpRcB7u8dOLAfyHZw2VLdL1uo5Vtzcs2rnXFLY1h6n+TFVITd0p58oDZtK86rqE52PInbT22kqEScUesj6MvQ3XX5YRTvI+gSZOpLKmr51nwWQAAUgpCy8yulisgFLXVNwxuaBZnMmx+wT6SUQMVVW0SQDYtDe0xuKiE2w8JYmdCsBNPgot439u3I6D5l0pvOI8TZCxzup3TVUL6k/xkE8KCJV6k8JkimjfYb6c/Sqpfj3LZrbkY4my2AFfQdGD7mP+KD6PltvRZ2z5Ztpeen6Aj0pSUbnEsSaHLO1rYCRZdkMy1ysCaqcklI+Hisvp7pgDNdx7W3Fo/WGmeC+O0o9sFeeGf+G4iHR9coVd3gmsPUZABGPgXm1yD61uUosNc8WIJk7SqmYFNSrVsXqg5gdO4EecM2hEFEtX78q8KML2yBR2/UYn1pzkrwQV914ZV6KqxiXTFc2F3DY2E2kpmDCsQp5jmYlLSHqytxjF2vAYmj96oFGUzzPfOTLOkS+35y5MUBAkrkCiMZxjr/lQ48leAf7wnw1+nZgLPUu3zW/MANk19pBLZYmsLdgNS5pgVpgytilB+2GTLvo4nQa1wAK3WsrXiiJSHVkjJqWyeOfzHSclmRdbtlryx6+iHciNX6qgNgkY2kYKMQjzMTW9Mzb0QyXfIdRhzJ6TdnYcYKm6+v9ZI2RBEQF9KJantiGUDcuhklapODyaT7A6HRsWEobWAcugb8OZ+ZeNvO3MBmQwz5w/D+ZLW95mN/H5pAcBC2rLCMtOY43Ph+nCnTuRSq1i8V4Wcop1OwO/TBRUzU4NDiNlrMJYpfOLutb3xomePhaTrFzihZqQ7dxnsycu11leaP5stfgxcJtlyzhyItnsx7CzI+VDjJRa8Pd/c8v2NUBPZg0gvSfi9cbu3BWkdn2tl4uhI06xv0XFTBxY/+bgGLTegnJS1Ops96MBHa6vt92RoKe+/qOoKEREhJ0X5LDpYb4AuO8cdQe66Uvu5kRaYMxh206kGVRlmQYx8LPMXsgTDBS/KredzsBxmSbrW2kDyPrzK1LmX5r89lWfU7GZ77m6oQbabDpCfic1kV5euIP70FhooNYTrXCjCpffAxTerjCc1O8u2qbHtoWr5L+m/EBHwKx1Hwgg7U8eVCzZNG8lM/O+Pik7R6rISYdkp5MQleVz2Eati/OOfZTp8sBBOcdwPX0jw6ZAmnZ+eUp8AwUtMjYAXN521l33lOsh/rmIjI3xWtJaa9Y4Zio9acEaqqSLeFACthgtPAe27NEb7ylHHFbAYLB6saI/cwcHrIGl2ZRFIBHwrTKRmrpaE6f8UmPUr2gARLK21IiDPLEElAmBxD3uMo133XTZK4w1Gil/HeWEcwW7H+OFB8DiljE8/Jl6wLCXnHLedvvJF/LlhLZSp0hEfiOZab5vgL/qaYg4kVU6eTsu9CU7sQc+Ju7+S1fVTw4VCRDS62WgtLjp7d1gREpREDCQ0kIURTrqJrQpjUyv7SCjXCwQ7r6Nt5i+1QFrjq2uHz+Iqspf0fQRFzK+FE0oRxfaTvuqfbUMQLt9hH+OvTzMVGewQWcesRevj92HExfOL4tBLnujQkibTTCQwN9CSBeo4PI+fihzGadRWRD1XJ4qD38wiBsNVpGvJM6c8Lpkw+lvryh9M25e8zb1V1l8OBzPZc5BuhjCqJHDkDKqhb04FNqZNxHzXraCXI/xGHJDC/OCN0XMKhdVfX56hZFEjMDauaSHwh7C5+c2+hljwE6UDF6rg+a3IlnMIcJzZi74LiCnPiyzHcLHMBCD2xrJz2CYn7ADGea+uWXCZarhkmbQcGkUCaTL0XoROiOqXxUt6vqSHFeHOaJJ/A4o/y0ylT/LQ8NwGEQV8wmOxGfTWMrI0BWkDOx1FaISzcrj6PtUhht8tDcLR6Sz96F9iG+qaj+JXwlP5pY/Z7Qi/6QO1cd1y+n69wAKnp4L3Q+bgl4DSgDRanGITkKp6jD8EjJbAfJ1QCBf+XF9lS9Vbi/9lNbTxYCthJyNrpbFPP7W6dltg0tKzivEaxgrDQHK160Xb5YJLh5ZUhQgf7xwaRmCK4mvHqA7IZeBKh413pkGtjmEWPK1qqL+v75PwB2Gu/HniSLeICtAJa1DrDYvuSBZYcQeNa6ne3wY0gErCRn/9D+CinmG0rhMq5JDxn0IBIiZWngp4J2KH5XaH5WemTWH7i1BEhuOxiS+5BnqP10scT+mknx8CqKMs3U2I2Hjw6eQbIVhvYh6gBSU+Kt7RKVcfhE1XnEFjSoraZbX5vdI6JhkYciXxIVnYyx2T78ojAd6thuFDjDpx9feoYYO+nVXwOyA8wuP5107G9B9dVeRE6dOD8/QiX28ivyHgqMhTtrNgvzBFXdHsZorlCN/cnhDw2nn3Qc4QNtQQtbN8JuUGloO6tcKh0oUfeciRedIatseDV03yfme5YvNWYYcFFoRu6154e9Pj5h5wW343i/tlk0irdA8M6pUX3ucfwNoGh2LnyX2HSP30sP4vSrDtW1LLQ4GtGIWINp9VICgzMQTjq8zOH7cdkzL/YdRYqf9ppAl+KTGi6U4U7gWQiTuC+hJ748wtse6Zuxd61uuBJCa4w/T++vvbuEghIUWI5KgZ1rXq7dkwmBu5MPM3T+BYlpJk2PFBAjD6qgRjHj57t7ecnXFxe3/EIhy1cwsBarbyLyk0cIBUXujCqxHEC8YuMcB0rSfGzaciMvOJ6b8o0JpQ17o5Y7osJ6QtuXMS2Ep6KisscnDs/GZIkgVtnQjHYvvx10djzYseQvoLlfIDmEVgQ+WV38osqif5NeCQ6Tf6ZHAAkEcimM8BEXC2giIpgFSbCHzdVMIRQhRkmAtruXQ9YzN7/zcChQgCA52RcEnfLz5fHBtNBGP8goKTrqLyWgtDLsrKiXBMPki2SGONkB26rIXCilx2Z+zEUjLOOAz684uYmd6Om4jTEcxJlEWK5PFWpIw86T6Yje+gibwYb0LfYZMqbYcCxWsw9+UQa+YPtwGZaYwbHMtL5Pna56e4BnUn2W7NRS5Z9LoVCTzcCbUCfh4bmDYdw7x50cQlQYk/4ECDo+TS8e0ddFwgDhvWBSMuyMLPAH+SjJu37/vjOL+f6Yui+5HnETNK8ZHRYEBMtmTZjmupIY/6NVe2JEEA77RnUVg1fBD3jIYrjO2HkFj6P8krut7DVdFGEPSYmQxO5dpxn2+9KYNe78XeErh0l7vOzk2lJi8MW8mVThY6o6JY0rr7CVuy3qvhJ2itTWMhq5qK+YZOJZfH6wDs9hPcduf2DIROut7cD499+hFzvPbTOdsXllEYZ5aGmd3TSwCG7Y17k/8pbyts6VAc3Jt9sVaGRZSgoUQj7Ni7C4RJpUfaY8R9LpBg7cPO4UXYzw0XjhSKZ9+COc2fs2d/IB9aZaNkxicROwZTRFEEu0jTyiUU6mtXESuX8McouHHvkDLmGNf2OWbACNkghudnwzq19fheDkEXGHUA8RZdxJWkjImSYamiwwwpoIdiL1ssMJgFYAbHy6IQXcKBIHILsAD0w8RuR1UvItlOAJDEZ9jnqBUxwrfIDVk9SRL6abRtey54lZwbhFCV6/J4d5O4Swkh+XMrSnSwJNS5H02mdi9grkl40Ousy5orb+ebDDWlxJbsZhT79BTC2sN9Uo7L+PDaoZ03kJC5h6jqQyiFK97EqSDFYvQk8gTYTSSuYsta96h40Y1lZobIUOJr6p7GHwXaUvPO6QYov97dHhCD0Mq8gL9fCn9vdGFXS0sI5fCoDEnn1hQX6BfjcnbImW1KtE0j0Uf1KMAyKrfZLvW3D8ekVg+Pj04Z+hZrPJm3YZvxF//det4TMzhCIo2C0M40D04uIl5Y3BP6u6d2MHFCwgEYawjfT3l5dr2mppQIg2CS9jVYL2CV26z+EksMbgS2SaCHwI1ScvpvUVCoNlZPxqY25dr97o3Sg8X9bpv21VvSisLmfQ3hRWUnyolZtmnep6+Alx6pwcO3m9JX+U5VeIqPQ/u0t5mBwmdmafCRZsQrJy6M/jm6GDgVPJ6x4qAtQu9nRShdynGD3aDkqJGUW6emciEK3DKWoTwJJPwQgxcKYuFc/z1WrrbN5U4K09uLynSnT7ubonJJQcPgfewX+zADwE3gNZEGnYnB1iDUuk1SjFmF44DbhOpHIXQErEXB8wg0LYtuXIZ8FJpgTpB8m36GN+5cD1CgOYDbW/r1zwu0XIzUxJygmGXwrdeUsEmSriuq8MUDmKpzbxkzC8BXZxQMJHRR3B48LCJqTgEBX6dtI+vWHX0hemVE5GGXtO6cFhI6I9KFKCvyGUima+YKzyFWie7+4vYohrHt08TJUaZOpajAcWIPg8DLyXOe8+FhUjLn23IWhn/o9XZnxT+P2TTv48bQuFNm3NcvR2C7DjnYESgRdnlFkNWYAAGFmlaD13qxQelTTXUDcCBUEzM+webP/lSsmmyhX/NDsqwcocXgdwLF5oWcdvInfo3aullgGYfWTxym42k9sS4RAdb1dTyUpBCGR1JUDSlzEWKBO5FmQOBuFY349MfS/B7ln8shCFGZOtDC80M23OFoEIGEolM3Le4XCBUCKMPJfwNjcDwo4sp1PRjRlFYScL2FQxfSItNnfaB3bvDKvmZ+lEJlAjs1XmkCiYbsMCaJmjctkyur+oIBKsG5S7vpR9rTEZe3AsPYdIhYg1Mu3+ArvQq6D945pvyTxA4Icjie7D4Z+hYAd5oyKwGxKhKI7l3wA6uJdqdSJH4rituInZOc0tJBLBdyYJ1yBBf7bWwLkjC1eY7kpfTZ1vtigRAY1eTCRM8BeXBn9xy8SYSamsT37FdTtAuID6M5Gdkj5twc/ILfv/jklGQZfxRqapEPA8bP3oMXsVv7Yt0Mud3j8Fozf60EB4PHp8hzIpzwdQBpg6rVHacLuwztkM9wNHCBPpjoq3k43jGTS8Gl0y85LraG5gqzBRQAHhHZy0prvwxb97NaMMAdlwMWI3GP5KFwYodjsnvNnQYwVik44nw8DPvCWqDM+kaFawkY5BWfpxHQee9yepGtpSeu9CF9IPIMTY0pfrTCrhzCd8OJKl1w7iTL0sh8zIw8UqM31pEndIy2sPOWVEBWXjMZUIfsLq0bzNATb8w0Ji7tyBQQtwFxb+5cK3BivcaES4npkBDckOtfsQYYXhSnAnCSzjTdztOW0N/ZsaF5crtE1D5ZbX+JTU5xBRoIj1uoUIaYRahGWdrBodsLWdo+MMHOdjDi1oYe9s8i/lzxxadyLORfME5LC7Si75YOM/Z1T4EURUneHbKn11TQDG/8jpfSqAp0rBK8GhMJUQ0YYnIQCk4Y4e2MmUMk7ayVw7FaAoVQuHsoaSgNhBFqddYD/pfHj/ODUSz/uK6Bej6LEfWyz5OjVQN3xHBUwcyKqtQ58tQXTcw5QjOPxYiChuGOj1diLwhMk8r8n6ASW+UbR7D1CtFKG75iTV/W8vE6lG+UgLSWpNhL4BKvy25rsoc1vUnp8lsWqYVjwrPstqf6R7ZXPAVXCMbu929SPvlFPWDKNXkoFiPmqgSFrJnnJFE8k8XpsAWv+UcVu4D9sO8QLwhXqB2fKMhXcsOpwtU7WHWaZC/lLdHgYYZl9vuiV3/DCp0pUVSSohKtggUGKeMwpJN9lv/sSu2gM0Txs315Ny6jC6kLhZlxAYvnKoz0JDxxSD6WCgl/vsZ++yoo8MaYAiW9Qa45iQwpeWO9b/s3NKfoJHgcvptqHeL/Y3uExAfSj+y7jrRJZqLOKqJ+OYrMOCcBON/mlvc3IvhKYa8XFlpJdl7aykobKQYKykraCOLC+bVIA1YxSaonDiTP60319gWYUSWA/OuWcvDzxhB+GUWMrNFuCS7/iWLn8p8Ml556wbYQex3E1mhGTTy6ZZd+7XizwLo8M8pcD9pBbRyxvgzqbuyGXEL+1U/EjUmSBJYHDEU+19dRCRh+3+AcC3n00C6Ay6NcSMVvEuOM6XyNWwt4jP/vy39AFi7RN9FK+cBEQeS5/m09LcsWMmZtOVp5bHrCnkWuulhxK5zr5xli5GKjwveRqoya5dq4rbiKAXpZ4Ew7pPap58rB2KeGk5kU7aXAgK+WVNYJhUaj01tTv+a5XC7k8qvIw+Tm5lbohcjfxARdm4CSlgAA6CPjO4bIghd4gdPlKc/3gNCzU17bRbWEvE7B739YIEvkD5l6dytjLVfTl80Il+/pADFK2OHTlrq6XsTH0r1rhHTqOGD65XqAnpavb++fe+ETnUxrI3DtK+RBQaSkZbh0rGz200PseOZIrtRiQsd9raVR1fQlaMxH2jNygQULciCUYlv9ErkllWAwXK2SXYbNX87I5jWy1LA+QYptfGu26cKEnNiPuTWmrgYCUswzaRyZdyndmXO2/brU4CHbru0PVxn0qDodd40vuSPsuxFfK+iGTuV386g+s4pICMyoI+wIdAxiN1KtBE+yE2WU3Q6NMSwTFFePB6vn2aB6tmHl5BGhKXnibNwMfWHPLGzY3gjQ6Vl9jNOftmcq05jPMd/SRLr9i2XYDlYh7Pz34nUbkdx55fURcAdkR5JRJPed6N0wbjB/0oUThs8dG3WzANbH1F6XW4RD/2rVoHM3mCO6xGRCOtzTWxwUKmIVJEy8f9qlABJGR2yd/AlwdX6KsWIREGHMTniLn/PgtWuoKiQi09fhWn/Ki6ncDYpKEJxHaptzOpnQNCa2FGZhEQig6+KqUxVqTHazPQrF5K1GIfNXBxbq6UXgn5v3WaykrBVHpV8k9+4SXeW5BcBUD/VR0ILJ7Xx8rhsSw8fOkhBA0Dk1CIO2FOIvxkvVbR2xVdegWCZ29/FP5JRpcEpjMptF8qDALvWx1P+bx9p4uA9nyFJW8RCUL+KEDXVeIHkVi85UekJ9vRMVja4FLK7PPt5gc8Qjnt7Io5TRU8xEiQ9EcDMHjIaoa3zyxskz7wx7MWFjHoexUNx09jDP6dDOjrcEuvED+sXdIqR8qvJHUdnVSAZlwii6PwQjGZjW8bPTeDW8OrbYtpAMKaFkwi95jDV/2MH88fhlZ/m1Uw06bgmYTlrejexmgN22FvdfwiBULFRNRZRYWfkwa8NfSF1DnomqfJUVYzgJW+q3b0HtvpfjbyJDa3vh5w4OBpIIR8AyxLj8t1jUSw6eAUsygFGlZk++VvtD9c78xaKwh/CBcRNjJXeux8MEQ7qGhP6FgfwH+zmB5fXxcyp2W5cwg6odjIA+QhatM5HI1BftXdQ914hqyeLxhhb0Fd4rMNK7nQFPv6R+OORnW2mBHhpDW7n0GOpy1m6GAIAjJ6ODrzu1euBo2v5iUyVHTvWdB/EEhKfqc8cg+CY2KhXM5PbfEJc6nUKa21LLXe46cEZhmkghEv5+ZnhhP6iY8pKKV9ZyRuT+adqdPODqtvJXkOI7U7tzgprNKLSdeFWtDAuti47pFqdsNg6N6fqarTWx0Mj8yDlj2UhaErlJfDZFjVGNH3bDf2imF4D4JFQ31fect1M4j3yj6EcTSOzngozX+VDT3vbyMKe4zkVuQiU/eY+z7hg2wnoSSeasB/Etosm2G1fpB5Drf1bhkxyP8ZlDyDi1TJO04+AH83Ez8W+8Wsdc+sgTa2ohg0ticpw/H6hv9DXlKwPx1G98bKzKa4OdpAF5m6RC7iYHpWTvhZ1LnutKmPKKeQt1PCAdSqbmj3Fht3Faa+bMvVush4pO/9K5WEdWQ2vapjl0qbKfNJVjSq0t9ihGSLS2RJ9yOqSoXw4Z3UU6jyTIKCd88Lhp83RuDwqiJKeBM7/hx5V6eIGryBYnA7HGMEuAsSa7UqewwQaWqs92dz/dm70/dcDtebsZD+coTD4ZMRf+EIQn6i8U5FETZxqfcxfxJLm210SAG7zcYSoyYbC/lSt2+5MFHVcBiKmpCpPY5w8/gL5QRA5aBeopE1UOIDryKCBC+ObLqGpmNhX41g5sG6Q5/Isk2xTrEiIMKCUoUZ0WMCjDYkVbvw32GL21pE9ZC6TIXCLa6rfnKrkr9//IRdpiblJDy0fStrlWJpTSFGNGcHbQ5qVJDAd+54BCE+Nr1auud8dXw4P55qaBP42v6IXkbv218BDGrftXr6AHIdC7+S1FLJMGdYpFXMiKV7rTPw/dGnPPyOOW6guFR2REvTD9vC/n6TGXoqmQYkdGRliwZVPaSgo2QAQD3rs/PzrlHG0FzXQnzazEt1bXG4iB+0QyFcn646sNxx8iPBJ7V0GQVsSTZ5BPmvX7R7oUq59hbdRttuwpjCKsYmcA/gQAA8ODf+eiFBkwv2xvZYcj6/X+svXvaBxP5tEpCkjtGzNW0brc6ayInQszkUHzNvILfJT4SnTHI4EM9m67/mZAuDCmkYG7V88Ipb3B528Vrk40Qm0bJw+wZj7UW+0LkyIwu0BvC+2iy1yMRm+aKzGIcsMY5n4sZsPfkrMJ/8Rr0BwQaxxDb3e97zEGXEpdbPjmdkwQq3UIfpdSpdvPXEF6Dx0VOfXvb4DxZbgS/bSwenY0285MVoZah3tRUOGASXJknjUeJTjpVj3vJrkEmD4aWauTH5KX34SMTmiMibng3RdBHxRqxKAEVNy/kNZzbJe4uJ/bbvmxKBdsiANjQx98hCZTeB3+hAPbAAuGFlP0kyTzSZq4mSxzDl43+IdtBNsZLbRVVg+IjZ9MeIcysOqv/dDhx/1Bn+3nyL3quRYDU3N1uAUE5A/Wdm+q4ZcX7xFQ7ZDaf5MhHBe5XvrSYyGHGUZltS15gBx/pO+MfL5BeMNehV3y1FpR2taT6WFyllSE2Rvh/1SAwanuXpk4MEFn3h8PMSZWO/aceCfo323ZLQeuID/9CAXumJclCmlYfijsP4WIlbw6fs4YPcIcaz0LKCPNdcaLN13417qNXA9rxeWInFAq7dpJiAWZ0UNL124/O9vkWO7QLKtaiZM/nbjLYBr6Z23vxSa0vFMxdYPKMIZ1edIGGiPasGmA7d+YEbSNk/uIzN9YsG2XgMVjRYmxWP7NMv8hCqbLFYPI/AAlG+0zP0k0//7K/BMoEtSsxnbZFFGRF7XG7D5Vqckox37Hd8AMbTMJXtAj42UA1/X/JurfNcl8D4k3RXldUT9egi+sWw6cJ+ayN9JwT1izswz2GFZNhKceMII9GLsBPB2f3IaygnOu2aECzVbrQA1wzwWK+yac0W382QgeKXYF1B3Zk9OxEbJi9fI4HEzu2KxomH6PxbDpbYwaAaHiiA6KJcKyprkfBCejZxMtQuQhyErMwlxMtJzuGB0hivIbW2q696s+9mro2WgSGEYvhLVmnU2nkdSSVAqT3VXIwvL9dHE6GFprrcNflo+gspL3SrVXedp7Ig7L1mUw6MjreLD6zZ2YaqXyzWlsDcKiCIWXy30IY7mW7VXQO1PxmhBSPY2gGnfZS1GgvwxjpEe1Jy4M223LbDH9vB1pssnbO8gcpxbHQI23G5+R90hgZe62zi1UxLuQlG2OvEpswY40NkUXBW+IqfOAbB3+HQ9yE4bpy9Ct5DecM8MRRA2K5XiwgXwuy05UR93I8tuPZejKD9jQMlQq8vf4oh7mtzORpg9DeqRZexafb/ff1VwAFQ8PgGyRkOGKxsfB9v9jQymrykJIF+CfVFkLGdL9fZFpfqHBbTT29eYeFPsvKsZ5ce6zQbyGr7qwwQkmPpR+m170Y42XUfTAUFhGKYzIVIjEeq6UKLCdbvAe90PkbcCo+0e74trhhGIna4rH8KlCOdgPts60IMCrHujYY5VS9zDVCrSb6bS4SHYmUmzL6iwP4Z5EdqjM8Uy4evblIBECvVZA/GdJoJEBNOQlHMZ2TjCW8qtFaK7mWMwRWFTwag9KpF7oWPeixpXNwUY65mFajfmH5rHA1SLhxlHKdAnKXOYRJQGWEA+4JzVAZyBhng+CtjlAE0lh1GhZSs7u3yQrS83bhfWsaBtALf4mVOJsXQPZV9jIj92HY0ZG3OI89C5n8r4EX0aB9C0y8et/WafClQrgReDdPSbaScz3YBG8z7qy8Nro1mM5Mt0/DNi34qFU+9pp+11YhdQ5fYFu0T0nKZoxnlw8Bni83oFDpvBJnWUhP0MyW8pZDutI9oCQJxz7C1f9DSSuu/X8hj+GwW3wSLCoe/U3YRy8cYSPina/t4HcmJj+vgd/59kx7wdLvemnurB1PE/m+eEraeUOC3v3S5VdtKhaETGV27udjbA8c2Jwc9I+gtHLjDttHNAEi101dCTYHB2J9HJtK1ziHsBzIBoGpoEAAE5Sbpo8+cfYBAeiBB9dVQ/Edts7F42lqiVHxCc9NXO/QLiesa2zdG71P9A4oAJKlCfH24lw931GeBCOY30CkPCbkl0L343vReAxaQK4vXj0hiD/nH7B+C7U5d3EPd7YR9psSAj7rd0x43BBKwZfNtVUxTEMugT7F9Ou4scrS47rth+UunhBc3ysY8XqM6ztLsmeRcOKqyh5TsbT12sSSf1CEtKdr2B/01TEzchcorLlWHBLpvqJ9bRE30eIERywnXLHV+Alusaz2+MYNWxVqk6LByrgdcEnelNFL2OEntcDr7QenqOLqUVplgAii59oFF3Q1ThjoPHJgwqmgIQymPk7VMAxrartaGVjo0++D4Qgxf4u03A84PI/ivxiZZQ6aFi4wEp5jNdTBRStIBTqAdGCB7jUF3nFXDfOvdAdjN+b+N10DwVyovt7/i3pcQJkLNFtBvSuRaoyD5IJ/fGcXxwzfVCz/F8dTfZpoR1AfuFVTbXppAp7/2OYKeNW1CtOv3C97MPA9tf/RxHBBCJvxiG6Wn1MECJR8uvIo3sIw4JJ2O0aqsHbeymSRohEcUzi1RX1LZnhDf7okqYadGNKVBd5ngg/vqEymgJpBLi8tJOTf661iDjtqu4+Vz76nXejkGs3lbXh48f5ciaKPmj/NzxxrFasNNOFJvOA1Q6beQhlz/yagtVsYNSlVH5uaXVnAscfwkwueckWz2HQVR9ICoX1UnYBzJ7sr5LyanIO+Sz0mKNHJk+4c/uNzZnyGIeIBnSkqCgZL+8Mpju6carHkTZrcM2K1Ei7QAAlCmpDi/yNAPMk77hLkXEoCbneONYg1oW6/ZXN+MXMBG5HUR5k5EvxLbHWG8NCLU1Mj2dCjPZv1ZMF5q1U6xuFMaIAAwcdFN53VfGHKO4Fio6eIilsI3k3rvBAB8ZN0RIu1YMWVw4D8LgNumJpIGLKKIdVZOWVJPDCXsnmRMJLHIyb4MBdfuJSV/qzD60GL1p2MZHytBU1s2tvB2ogRBm7Mk44Hj3lFW2IgYkdU52O/w8Lwt+IrcUZyKmZGFibrGVqOLIdvSC5ci43Gm01rmzkwtyiu4F6hNHA+wW9hhEvHCx1Xqb+/cXtLjmDIADnYCsVKfkRB2cDAanM3TaimYsQpULAXKuoL29Bh7weX8ZiukIgJyK0Wa0INBQwcQETYEWxxYlg/TCPY24rbFV1LeYENA+oX3WOJZfuvMLMTZswKxhCvTqbM8ryfOfy3xNbw6z5fHIlD+AHpfTurOqda60oGt8w3ypVgjmDIr5+4PJBWTKxJA68KDG4UaxQTfDTtQlwKpAyKCsqOKG/Qu5scBj61d6iTX8uzY+uWVCdb3UZ8pTEZiqOKobdSCnUCqWZTD4TsDvIsrdxak5N7Mh722NvN4vBJkAJmOfuu2PQC1IUjX4Y0h0aVokHg6jamIr6iX2m5GbTSf8XMB/eILLtJgf2oxpt+BNtg+LzgfO5u+nOa9Zlbedyi8GXlwPKzTnkb4y8KMPs6f92c/kH+UQBs3ZDHjHmFKonLgJLA9EQu34wpYE8ML64qoGuzWl+P5NVFZhNolY+B0oUWWBmhx328PrFoidTM2bVbgDqHrK1TNN9Uze9gxdRwaMhUrCC4D6F4uBmcU5JxeLkFs5qQnpr4ouXrD2HWt/Vs2bcAz+RQ4n9LQJqMhuy4TV0UFO5UdRjug6ujkcrTxNz0jbGeqc376KiFyGC55Kdm+cD86Oh07fYmXcofJJVRiTGL21J/ttUqCpLxJ73lAnoDdIdY5jC4PYst0HCVFkcYD1hJfpjLekfV30xVOuB74U+4CfMJgnbQWpBWotwEWSE/ptOD2d/Gx1kSrkkEf29QgUmKpFRrhEz4CTj9zd8M9NJEBlYkYiDePisNMl9Id0UuwXxhZaU+7CGC/DV1QokTWG7LSfLAOh8oUYqXCcW2PUWIr+XngMAWQ/dMkgHFXfsGX8FXHP14vtvn+stVz3XCmhPBEJwLZQGWuQUZUuUFB1WjGXyMr0iAvAblhIOCV3emxV14BjDgn9k7ClPo0BFZOt6nIALAUbLy8xKAOhWOwI7uzlFe7z/BgARplnFu8r+ZiC3gljLqGBFVYQDklD74eNJsNLl4lPliG6Lvr72wgakDUykF/PQOHDoNcyUoW8Wd/ZxA72WqV1H8HaBm+zoVZGA6ngPI7fhJer4GuOLcgWfB1DRJNumftZJONO9pErASCeGisMODLzoGV+ujI/Y+bLOHQ6CiOLx5CystH/iSnxI3eD0zRfbbokHTjF1VqrkvCAS1T5WQZsG2ogkAJ95bfSnRYVv6Rk0aoES7bAIGl/Rf358gnww7ogNmDINMV5HP62HMrNVJ8Kquy1/ad0U6gmF6xcPdsUALJGAk4nsAVIAM95IkP8b3ebI5xFX/hI7eRAIJOGtAlkIvono8UNn4wzSlhg6xIvawTk9MveFpCpngKyvS3SsE+xCAEGZ1AwOINaL2Fl2RiWLn0jqK7f9QKwX6DSLplKvLLcn42AexYen0SSNNVPQcemeZjQ77ofJBcoMbFd8QzCUdQTrD+gmwr0ouyBQrIRTFJmX9Q3blCkd+LJYBGpJYcpOWYEX8qK3KsP3K1auVm9EGm1ZW/ucs6ads3h5KbsTMaCOtlHGlYXUhgKctUrvFMBtCLQi9McXvvH4dR+8r+ke2Jqsfz2YEXENQUd8EnP3jfwZf9gUk5kG5PMvYhQqFzzECteNuY6DU4rvZM4HeQQNKRTUzAtQQCUMFgyHHhQTJPFkfCN1XHuFUfVY70RFmjMXwy14CdZr7irjG1s2hAJroCL2uAoOnF7/+Zlo0r3iZtoyiAqaHaQkpeh9GDnvv3px29IgZCNvjv4Ll1otEoGFi8iZ0DHHvCl+dmCZMUWgsV4CemXCyjfG0dR2OM/UimAQUYSZEq0QSgjmRK5xk35ZmiTn60Fc+npM0WQnJwxX8dEIdNX4IqAKDIPFvsxrwFfZ3c+IiLXA+eKdShuzkwxK43mBMTAdpP1CCHw7/n3gQ0AIGS7iXAr1FJUWGbxS+20DaMhJByMpQMBHYmjqY/DiRM+JN7ND7HAgCGmoNLcIlid712n9jiMit0yf8fUdbI576eRs8nqoLQ4J2HumwwODKwbQQcuBsUPCEOLlf9qxC7TpRgIqNg3cuKErjBjMFrdkdw3LbGysstFsnBx0VACQnQXcZdpbHHqDKZmhw6L2HPCRtcnMCYnM/jsTMzKmH0Dr6uYzSMdGtfKHukg/dl/fd2Buh1PwKXt64tMAAIBTBjRfTx35735lEgf/dDDFEN4LoxIdx4zhsZkT8ACpzlVSgK3KdMbRPFj3VuypUV0oSgfyd+NcTcep9IyYLUXTWxPTVO4fphvyk7B+eN16DebvxCdKwria0fc1n3Y9WhUi6I+u91xNIYMIH6+eiW27ARFi1ZkrWLcXFBgekbkI10MmgSsX8csh76iRlUHcDCCyvuHZ4tcgsfDuIFizZKSgoaOB4Ex3xTUwBcLDhA7+J887jyMMyzDUJwZAQ9uftR9XgutdC9nMPe+dKcepWA1Cl5ZK2OA+A+DSsLxHkwwDBYR0UnM987BEOCD9efuFF+Qbv1r/URT89vB9GB7+919vXerp9nDLHnG1WAEDQ22Z/5btwdEk7cis1CrWMy8ewKdaMuSEQg6J2Pr4AYELoLVAlWJg8cxvCYSWsvArxdNUUT/234vPMGY4d3syapgcH4jyJ56MB64fYsAZNNF/SQljW2lQmjVXHxHyMYHwMGELeKexllsZKGD33UQVcRLMqYVjoY0utcekcmluN1QkYS4XzrqpT8DW4xMeeGND0JtmbDmNB54djQr2dbln5FX7EFZMu3HNoDu+qypm8qINNALQsQ7oVqjZSFVg59FB1TkXVSXUgHzCmNpyIoxCeoQEnym7IDLuP6d8JRapln7ptcfb1HAGUix9eAjYpNf0iqmUz4vyN/TWFJC6jo3NoELv9XW8oEoLimjWfeYNfLdU+5PceEjA7yUgYD4dixMbNGyMWNh5Ic9/jOnbBi79XqtLkI7DzyOCroHSsZSFfcfBu3yDlLKEYizt7zY4irv/iCZBjdtExJQjfSmWR/zdPAhXC/19MJ3uYLX4Mr8HvtYM+j0G5Ck3mry31Bwd5X1XRa59ghWqo+sEWvJGeecMG1DdIO1lp+VPbD6Va9/HflaMVajxK96kCK7vE5EclPO3GsV7uf/rYUtBHyE/d3Y3e9Yb2yr+dfIdfNzL3vCUH/ZppRfijsbzDBUT3kNpEdi3/QfXbr8IAoerLNpNseZa+TiVzkY1cwEBigPGYrXwLHR9ferhytJlWwkUddhnU6TQGPGtsRgHkpCE3qsdv8cjoUp0Wl0ToQchTbhtS1hl7GZSJRcPJsVlhleb1ITJHuUsyebMHnZNj8cb9mq9d3TnbFFSPZjF8eQydykT0SkEKHdJscKJULPt1ixooySRYj/y4Rb5weF1znz2gdMC1uVm8jz3/Ki5hxrc+9XXDMuxXdZfGv5ASGWnvkMqrbgaPczzpom13pkICuyzyG0PP2wfJUdZsGqC4MtaZn4VtsjN20OHSY//lhG+NNI6rfXA1emmm2n2R6b7wwcbESqUK6FgjG1623/m8vApzC7785tiYZ2O5uIGXSEAqalFYWx589D8tsOX1rmvDJFQ0DRdOEJLXA3+J78LRvYl4kJSWeBXuBcjHSHT8MEFj2Uhh/o13tz4EGK14/Owd91Js19R7Q2MEYd9AFBzX+zTIneo3LP1j3eBx75uZK38vQOoP6oraZmPgojZbTiwhhx/rdtRfAuyBOAP0ojTfko2JXqNSTG/CPVCmZZBtiEhy4goPdzStLWbtjAo51Ehu+rkKksvFgQawSmw5DNDZ8xQ2Rk495Qy/0FoYuyrtCTaptgO/wP2pZc57v2kL8mAZdexEloJ3geOTvJWhDHH+MGZrleMjAwWIG7MVNMHjvHOlVN5IAK+jhR1OF+7ZJBXwD672lYVY1CpgKT1RvKFj96NmJ0dBGaW7DNED/O4/r4ulB1yGYP2v9v0xxYOo69gykRK4IOTBXF+9kMK1cksiiIZWQteoCNTvD4OIDxJcEm9WQWBgyMPm8dFJgWbA1OR50KSOlLkfcXDCu4kVP1LxDd5AasuSmB0mrP9z2vvtKq9Jif2a0qfQmDQEwrM8N88fPzbaB6S4bf5GIdIL2mGL1zzL7JdFHThq4rISYAzoXUsFw12ArYKPTW6IwpoRBflVrw61pWZI88LMclT16wqzof9iaHVzMUycgtuXsYLKwCwWMe5HGCM7GZemdAdlebOzdoFp7ciTg+qA2W6jSLPRKTdIPbPYunxglC5ygx8ukzSdL2J2r35JT2PzKXmxw/eFqx4LkG0Z1WW3GMnHaHkdZtnnEVyskmll5SpBXiRnreURo0jfnIICgRcS+HreHyfyPCl0+Igqwp/EeQnmzQAbonl/9bDKx4ybrMdEpT7eZ2qNGxpGp8d5qZfHWEWrwWNgaZFMrMdvCOco4EF6PTrFhEDXN7Cf6Jx+GuMHxN65NMqIBu6Pcmw9nCjvXtOkypc0vsb/zO86gn6+rPp6qJpheR06ptcA4ce9b2RNHu5uceGCezwCta2+MDVhM1hOu+1i6wG0ydxboMOSm1evwcuvVjGkIZQoyep/zPYL8vvH4WE5/5+j/UKQ8/hT0fwDnVmsQHKPplXLeo1RF+vkQp46wLqM26G3dXmx94/A2cewrvU0KjExBuePQ/1MDR/pR2RI8FP0w+9kTBDPBFZUKYvGjV1tv9DrAM4e4o4cKK9rEGlSGTtzt6l9FZ8l3sxj0QGx5+vuOTQhrr8MZt6Fx3mpw9iZrFgBRPqnTlu8AdA/QTX0kcuIqsWcciATmd7ajrF5h9XSDRgHpE7s7cFV9iFrjWefcPvINLw+X/5+R2WtidfNm0lxRBKa7RfrBlZBsWCaGZl5OnplOV4k2z6GBDyqKDBV1igWp9g5UwXXfTsm5wNro63HkU/+eA4p3xoEv9yEavqj9ZzzEl/9wsFDrxB9bmwUF0ngUniBIlVmvEjNb4vueYhdUCDwBASDl0EIzz24JucX4ptp4bG+xT62lCVi05DBmQz0a0IQXH5u78xt0ytu0nw6nbDXVgUCVWbwYR5QhanZRx5qMzUx2YWN3q7BKhuLLG02eXP2pDGMfNywjp2L9d1446EvM1Mt9cf/qFOJbjrheQEB2ULCxIMYJ47qYG49M4746BCbuSfEpe2p0VO1hKe4IuCogD53RtTFUxYdjcEh18gGd6blKocx338WUYqQGiDa7GiPq9fqxXbyO1/SgNVOwS3vqzTtaMM7pIqx+jcHLT03HardPpy4ywlNxO7tRgkx46rX3+QvvmkHbu9dEBKMqaY9Q7wLlyZwQYVYjfUO/CFyXbLRtVSBUQnnZGUzw8wkgsb/DoX80XIm/qo/LCF3odY84RWo6b8H1RB5s43nhgkNnK9GpnZ5M9dZMx+2G6gnAE0bKCDQ+iMo/MoAA1jV5yb/xCJsvlhuP4+MGlwAsJ0Gr474+AocR0DIlb+/bmP27F/ZalsPAluXPuieDOrZCzBFTEk6dJK+ACX35eze5/jicIp0Eys6LPVX7EOV+fELGlB2jNhOsGwagDo9gYPh+p3n3bD3vnraifG652FL1IBCCmu9K4mi6eZn/UTfjR1D+qO24wydeebE9iZtttoVlgti2oTQoozKmi5JwAevzuDG+dpcc1MQpsTVF6V9pK2cZqhc/oTGR0Ni6Yyp72A+0UChTwPOZjqv7eWElG+IjGiwap5hzIFW0ekUax8HpAUncjGeJWPf123VSfMnYbJTTHagarIGwv0VWqzeJB+4kz+AwqQzsFvaACPkBfkNWKgeN7WvLTlAhqHwnriQfytaRwJaXoi23QYhx0heLUWs0PndYcYFilRwNiJ7RHfPt6H1FxDAkx1jVeBzB0gGFW5JSIzRZP5x2jr1SDFB/RCFimthVpjCoPYRYzm08ai3T5BALSQmT319HC54aXJcnVR4XvhGfsv8xv4Hi+iF1jx7N700/e0ww5OptrKuDmP75WQHlPXSr9257ppwpqKZ+oVLz2CiOhaShUdD3rSKFR3tQ/UtuAIhLrOivq0RsTWQXlWvyJsEFDVKJ/R54r7gR8FG8bAZeCNdMF4JmnosHRzsgvXZTycd9ODE3EG9s6cRxbEwSrwD19YHRLy3RFNd/Mgsjqp/+OxRZUgDDd/J5PD2OcZLuY47jW5FLeXn+vKIdBNZsyxSNqi9Y/aFFyYsE6jJ03unVAuWUpxO7B2woy01hfStUjnRnXE0SP02tDTX0PFH1bsV3Cci2gz/XuZIfAIhdxfBQbmJJ+ZHX8SZ+2udJwnhEg/QlziT7QgzKZZpxAk/MSWeA9m3xicuQpAccBZYFCaB/z7NVFK77ofTKXukw6ALUf/Q4Ecq+byRSsWCy66IegEQuja4b4BZLECsQ2yOqWGzpq6QGQ7mCi/bzCDVQJRYAmAHB36G97sk1uOBOW0i2QPiGEao9j55rxGWwhDF4KnbH6eKZbavEwv6/We8r+hZ7wq6Aa6w9lR+L9nFG3783J9zj2bYOr5SH2t7GH4ie5s/FbPTNBlL6W+xMtePmqpuY1KVVpezPufKeLF4ed6jG1CgQYUgksiI5gJ77pYjeeK0yGdf+l+JUupD+4zwhbNMFokjV9ydjWGDEiWpVG6kU4CxQD4xSknjRBnaXDQJ32tellBrGy822znFwUetDqMFAg4AjXwmu4Xr7UU434MKFtpcWtR7yXa7TySy76AP7multyexXRbcoYw77vz+B0mGVztltRIqCvYWPEfHI/qz7K9cF5fENCnMsNXCCEwGhxfC1x0iIQ6t//Y359bcuaabipjAI0JK2EI3SBJwfjTmEgxbfWg4tEo/xN8Q5dr0ygZnKt4J2tHg/pWf4rH4jOIVHSWcMA54FKNq6O8Skhb6WzvzWE2iMtaxjrvhlxcigSZK001rjXyLE1mQdbDy16U6o7FdLq2B5JTMvak1+SS38VFV79ZD5j29zCDQ3SI+jmTQf3JJjCMjCAW6LUEQk0Puolt9ktkwhxN+K1INx9BvawSN5aO72mY6vw2gFhIZ7eiFztX8WRiRAlE1eVTnnB5ohs+uC9jZn6kYgEPLG/6wACqqgPoSYtKkZz+k0mOm6MbSIeABI0y1yyqNtQWrb4s+eHCM0c6pCwrtOPKe1z7Ar1SWrQfZohQl+7qJxVzTdgdWKvnLAbSQGeYjRZTcu4dLxsmbucpDPgtIxAPzNxRy3qRvqyDOW3PU7Ym1wY/WbLwK/0MtVPpabB+W2Lqgf/GLc0N//t0jBroPcLZtep6SVHKmD8zGImqKfbBMaOhySnTYCmtrUoX2OwSqIy1JHZvK3H+aGJID11OwTXtdrjRvcwAGGImR/+pVuahT3+haxQu3Bi48nx9WlxxVxFMskrHYEYCdbHWhAKYftJw6BweBKbFSfPuvPmQnszp6j7AgWydR4ba3iOj2+LWtiETtYOs09FUS6psCN00MM62DYLYV3F6GDR5dvlv3YVtK0Bb+ZKBRkaJuin/EJDw9XJKL1OqJIrUDpRoXNcnGhEuHRsdtG0YAIyqupb70RgM5yRLl92Vmh7OcomkddL709z3qbmYCNMV71wtCetjjHbBRLs/uBdWCcW5HfB0Mym5mGaO45lH936XFCSwhHrJUGo04B+8BQLZQMmJWIzBmJa9cp9FJGqqzPPHnekyNBoisBeW3AFi5wQef+WtX9dacC7KYjJIykNF0AA4MgkIIwt+03wZbuJKIDs+Kmt/WsafpQEah9QScEFK9p4ExbEhfycU0Q79R5zhXZVtwV/FseWxI+s3XkoNKGMUb29BvIkG3dtLG/o9pHcAgwxWbUDwIA3JfGfP5NeRHfwAKZn/npSyUu2mus7ekoz+YuxTKCs45bnG/cy+dnFhlBVEZfdLmYxvwAK+c3shk9Kkec83IlWTBw2F6ocGnHUX/qBjWiSBlE0gI2Yux21UC0AC5+EKb9en5olJJz3VHu2d0LUCL0JMD3XiNEA9ov8XGtW08A92JM+LdRhmwU3H32nNTT2Nxroz/RDrmFCdceNVqwoTIpouH+iM5TmmYwoUvir+MTdGYeGbx00f1Mtvtbo21cdJliuIWgJlzaM3xXbyNLemAMkac6R1C6ez3xg07CgGvkDn5gAYPkvuWenVI5H6PAlwqxhmhQ1jw2tbP206oXafSXL+7n/AH8nQheEyxmaJN4zh4th8G/dtVjeoFfUjz7kmysO+pEmffO7rUEA0agOSFIHV5EkCskx7BRZFT17xKd/bhmm3oH52Vpi/E3sohhaa9onFmH48sY1UgqQ4i+veHi6D94nZ6SqUZKQGou/zh58usEPzZmlNgzickoEEl5C3pOu8MPVU8pR305pDp8ceKTz75Ji59I6T7RVXvAHGvK4w9vl50JFSdgJiWRqmGLwk3gFH9xXx1pKiS9oXaomia2D+VO5ezcBgQu9hF9VNzLkGicXsIIlg1rff6icFD7yNMzgBcAYMwWJBxXQw00IwuEekJ3Ym+VO/uSYZ6kc0g5r2Cu0o1iw8iXlDht7ktC521DWgA/6A3apuVQFGqjSBGGduuFmkTMWduJ6H3tNYDHjyr6dGscCSxE3ZHBt1m06HjkMXh8MpM+t2URebu03r+xnEDU4/fiQxVRPvePzd51MW8tX3oP6iEfINtIXKfndoE22VMLhwH54GQdSp3mRHgM6QntkPwdfmI9OJlUgbhhcuMPohdNvvW/veLDgzmVFB0koftD/l3NBpfKUZJddw/pdL9rPd5MyuM3lqyg5c7nzbOX1TS4icx4vZpE67MOpQQJ/gliRsABri3kjlRtOUhYRcMFKFVCUf7qolt8HPyF2iNRtSWEagEQobPUEKlzDY3J43pYyL5e41F0cDGI8+Sx1qYpBHBtQIMhOSA7/TMCrr7yPtY7lUUgCete5275jubJlgyTUcrOKEzFKfw4bx80hJPphFnjyCUggrzMTwOPAl20CVqptDsT+vYG5wt5DFAhJozicN8Iq3YYVQphOnqQICj4jUV2mQytdCvWf+3kfnqaMLJAvbip/Dj4v5QphWFDyHEWtrbVkVnIx1F8sE0A5+TeBi49mBVm2WR8ALt0nxuwWThWAt2UKVVnbEGnNhS0y+w6hnHquL0iTqIatRfvchafQb+BzfNOj9rB8TBcP8p+2XyEeoRg3frSAMbqdUUCJ70Q0P3XFzmPNhlKSDGWE0yAu2mZSx+ITvdh+b1db9d4ZBLm7Ki3Gc9dyz5lVhYwjThKrjKPBcXyea+xMhJD4OTBokullR9XksCpIm8WLiLsiscT8TzDpuwDTJ4Omr/cpAZCdljPZ8slbYNNle+8NIVqJLGhNAzsEuhFWGDIa8nwcaRdEJhCSxe2F01CZ3LmxAWYgNugGk9b3PHrLqGA5st+jEBDAKaactTPfEOTatXAS4ZzpfbLorxR45qqx9IGda5xXUjw1HFZCzkpdSLnqwGFIa4tlIGY+dfiNRQ99/3YEVDaeABApsUmzkuT+ceXqNMO3HXg1YrODdNtXQ1faHrmSkYiU+auZSH3rYzXv106aBw8MpRLyDjPI+/tWpoWF3Qjs2VfwlPZAdUbBcFni1Mx75cW5MPu2IyW7o85AyPzBwWD6qJkwznY1D+2Mjr8yU8CGRFeuvch+NikW620JOkfTaDsWA1qsUjTIKNXe4pSI/Z9N9Mmg9//DL1CCtLQiZb/m0Ni5Byzi+dJXB/sT26X+cUdiBVJiMZ9tphMFQaJytWNiS6BW7pkGHjm7DU8/DTBKEbG5UDQQNQ9eex6aosFB2GpTG7WbpI92kyUFFDbD3PcACocABmGoglbTfJ3PuRqBbzwEVnEwhvT7UnpW0X4TgeMnlgviTgAJj5wR7DQzpOkZh0U3ciFCSeH41g43mAF/zhbQy/MnE/3GBfq0xgJ1oHoYmnD6d5L50pBucaT8GeRM4783zF5a0ATScw7BQ++mP8V8pZxq5LqnFCqaATP8hy3AFdCIWRcY9tAiYAO1ypAiksHz6lAlKgVXIzR8E5jSP7WdL3ZP12MTrKavQufuS+T4KA1e7aQ+VIYW8B9hVZO7xX8DdFr2+ssUtemWTC4FCvFCI/Hc6a67leY7wvaOJdS79iO3sQhcZiqNBk3mk5cKhluSkTpKwoS3bfurD8yB8ID/QQwYQLGg7prJuksInY9n7pKCBFhhK1xjxy6TxoUZzTNhJZW0PM/MpyNsXpWrvPV97n37KGEiDxeDt6++KmHHdqmh4EJGoBc9r709NQ5OVeciuvr6AapVxj5vFLlERYvCEtbgZeIejM0Tnclyvhugr9ghcNwaK4lfqTkDTlQ5xxSpA52O4OD9xlwt5cPue2mM7FQUc7fUy+B+fp0Cnu0dosPZrG7cAKYGmkC0WJMiyG13O09lpQvI57L9BhKechw4vgKzg+51jyOUljVEvQtmQFxnQBCxhpmMuOCLfs4xl3MafvFD1gwT/Qd2VXev9aYtSLw0NrDKglrif3GfWxuhX2XMxY9INa4fC2AUZ1kNPtc2NYUQGd7xJ8/VKCdYd/By+IJ7jw1AIPVWOP1EMqs0o3QgzVYkV8jGOMNprjxPBkLovAEPqoTZtYbRVsCbR4cBmtD67jouClo2FeH07EDzpuCeM9RU2dwidO3Xf+VJxVUe84PMPPcxmxbEVkTE1UtfG6fr0HqU3fBbivG3cDCD/zn/CLJtpv0Wto4S0/MSwCL0F73Dj6teo24/9goDhizMXMYzOrnJ1nkoZ2JNqEnFp2LInfmHcbNyK7vyuXxVsnEYAbyDCmg00EvbvT1zNhKDqntkT41JsxjBjvjTHgteIjOrbTSGLEZ7YHD7+DiDv15Itq7kx3qJUh0B9DltpWGP30qNHQh6GV4OP4NNfg9j/vnacS2I/FkmrUll0gL6gGoBEps631hOdSBaUfydKCl+Q6JA1p8Va3mGGObKEIYYasHzGF/l7lSzbacR9I8y6hh00ZWyBrgi1uL25u/UMmBKSbjPB3Bm0/Y/eP/F/UUg5L+LaOAkd73sijDKf04UjEScD2Xng/b8KQuBeLa67VrECGyit4S3/hS9g762atwW1XQ6pSBv0pLWaONjSeQqCtK6bok+FJFjat/z0ZIsbQdPhQSMrkL2P1PnLNSstU0H+UC7rc0BDHKi2yDeA6TnzSBSm92I6wVgnYG/Ate0+ShtkQO3k4iipYXtaEWCrvKvzejMEM8ZPsLA+HdnzlJ+neZJ44LxGStCxsrdQNlRA1c1iv4+Lue2S2XX9U5cm5QTnvQmeXWR0+vm3ow+NVFztu30hh8Go3rFypVA0xRBlzJsZM0ZXv1d8Vw1GtLG1kl74H5SjWcGYaoRUxyLWBfvHuHyW4eBEQfoeqZQ0uplIZFj5chQCdDWXuyl/XFi2UNkkpexVOrdhiYqlIHNm0B6MQv5s71hkuGSPm2FRuwbpz5fJ6e6YMG395/AcxxTAfnndzKNNKhnq4ZPZX0e+4jqyeHg/3VAOr/udCqXZKg3ZKV4Z5eZAmkuVaMkruj8lp9+ZpfBfAlyUOwPbwUnvj+RMHyHvbPnJziAm1MCBRNXyE6mKoMWCau9zEbOKI/JhPgQlL+bA5kdEGx5VoFk3oTlCOEXjPY2vWDrZdHDMEMiY+/9wRkIHNCVf91WmYajmIi/WbdR5PyLERwDMf8KaTmRkFj+ErGqttx+PfViWGhN2BcaSKETIV/26vAuAjordO2UI9AMG5GctOiCKGPPxXp44Cw2BZM5Jbf4nmpJme4wGDrD2VEE/nbAqnXpeWQ3R857GnmZJ//UAXLRR1rLKPOJLVme+EBgOJee5H9d+WAhk0l9othLkDFlJYQlMF0/JSBJ78RWe77uHOM6IjnP1bNVyeURo5x9g/UrEcAnep9aRoRbmhUMMI1B03whyFNwCjz87rAcRfjE3zHM6B8CEVSF4LnAT/OOdKBVLIpAe/8wOiq20OKq0+G5LgFoFV87h6uUXS7PyDlgj2eFhGLypotYZANqOohg4R1YYq7orKBOIotVr9OJstMk4swIIEtlaJ+qZ2tzUDiT2F0lcHhj5sqj7Fqgr3omBhbbKsIXzsQ9RobVLYP3Nvq57FoJsKKd8QwM1dXgUigIkA7OKyq4PukLfzsx1FskRzZ8goar7UfXOYepuneqMwHTSiYZ4nU2IUuew+BDske3ilZUjznkosbuzu1D9eUWc39gZfyWeKdRhivH8DaEmBeH68IOqtcFzBf5F+vtNIGHD+6WFTpgXyFEonHdVkyL+N0sxOsW8c3/ualjPa+y7ug2gHhHxYxXEVMB9tRZn9mkoTX3mLAPYtzQiWvdS56R72tYPpzPW0IUJlxjtdnj6w+t2ILrgUSZJVwNazv7P8cYfa137QVNKd2UVvE5VUMuyAytyYvq81XI7qOPACizex/PxOiCM1eR1matVF0skA/YuqMrJpeoIKa0jjh6WR+6YIHG+Bbwhxr1SkRNNHZbQzXUe7V2DSl8i8nZZxyIjyERsUkQVe8bTj5t+2E1Yf9rxNdhalFjgUNxTf/NehQcqzc93ey1dTKqmeLPig3Koghqfkkh7LlIrPO3RNl8TIgapWCtddcqWeivrSXG6MnUa8vOdQ4PTmAHAMgvCvRmBcGOgNO2uwGbW1p2jMXMBanI/nnqk0g+GCTcT3IEcCQlRqG6JMQRW7jEG2YcdgYIrEjFJsiYZnx0fgB5k2g1XxBzCQZzoD6xc41c095wRRuFXoybxqATa+9VfcYzuVq5D7fLVHLNtlZT4NNQ+40zAIRoDEbvYvm3XQEMUe7sy+cipYAsSKNjgh1pf5/c2pbTo9zjqH4YoO1C2Q5smnhiTXGwEVK33MI5xJH2Dc/LwN9lqfAlB6TEjFMduVKmekoa+r4I05/GTITtFdWXAi7Dmcw8CZ4ucwjMo6/w6bLYp30OluehV6/9U03AFbBRAYZX92VRFcpcEVcYfPSDkw0AuOgDZ6u5VYzclJyrXZ4tKuNZEfXX+2k8J9+GYc9ziE8/AnlLwreKES3e5LYb2f+U9wHAmPDTeXLQ+6MXB56fcfzVP21vP2ug73wUhV9aNAyUJ5eUxofqT2sRnsWuGgQ0GIIM/f6ewGrxb5Xc8X2aoADIFlstwVSjxIbsLrjzvbDZ4hcGpYbrGqfWj8Dq3xL/RzStY9U3sEBqvAEidnLG1755a36/g3b/pjS+Ck7Gq2olpnOmmbcgsFltLDDcbVYXZCzQSrGaH8tE1nsIj/jbGg72QUDasjlYgmn5AagAjBmFoVPaQMslW1po6YSyCu3aX+VUc8B8W1H76iSAbz6ccZJSnKOP0gt2Pp/SFuTuiK9wxTkpS09/0f6Tuw/PzdHnyHKE3UiRu0fqu0Vf/hGZ2TXwUH1T/p317ysG81wieWKgp4Ld8R1ysdTL3iOMruhxR+tr+sO4Kpy/wUFK/tKfRgpfoxv/2A2ijc0EfPgnT+YBe3upXq6WByEFMVMX6xGEd238uykRktaHnkceaURBj5Lhn9vMSDwjSSWGjYduoQpdMqSjhJicjVv/xFMQuJxcfejGCoheD38A7wn4xzSPDD3mLmrE9RGGW2cF+pyQnZ4o/zr0VGHXff+3BiotHrf9C45RBGtF06uU9lpc9Hznic/JwxgqotzPafyWh7AH2fQ24WhS4+5frzg7N2e4h/yLmi8q4W0Ytl5clQSCAZ+qH+QZF799Jdee50kcGEVQcL/5JNoqbW6aUk1moJLi8s764/cXg9UcDhStvlhMszjExsuDAS0+XTuL+yDiRLAZ9ggbiBQ8iexS8z3N8kuioNAmMufz3QW52gdTVrvR097fIshmTcJI8AB2830VBGIh66UfZWj7kf9aQuo1fc2URUR8+mG6+Sm9kDBqo2wKYaq1XvW7DXybNKRDb5AY3FesSjpAMN3BXYS0IvHNN3alNwzIH1gUhtJsnAjXWUw71sRHFWtXypixgqPjQAkGMcHMyf9QxHY87aF1iRy4KmtWD9qCGgQd25Fm9MLQJEO88m96kg3wrwKukZdmxd/skXUtIk5kvmVgzU10iIAhKnNp/+XOGu3giiGw+0BmYtBCOl5yKySZdh/GUcP5TUyBWAeXUkIFa3qsSc91P6pzCQE7AyHA38N10UK4LclMensYy6TzoOTzl8GLsAJM6YWh0JXv5kzE4m7ctsxregEtjzN1bRjR7s/74ukWUAEN1WH7VXBo1wCrqz0hgk9KPiTbFEyKf/6I08pvcUzjJY4CHfBxvLZH05/gkrPrdV0a4WycwFIW6/w3xsebiaEqxzVQYL2YjDFFKerE2ONvUT3tlWZEMC2R7/DQ+GqsmOp07HhRsWKc5r90nlVstajjt93V8yUe9ymcB9HaSdU1Yrqra338jTdAZFTbkVDvS4mi+3d/i5GcginjPpCOv+qK2iq3ZsVT91Pfwn00coDDnLEU23tpiTeF/cj6YsKRt+5sWtE7JnMNO4BrAhciexnCMr5SRZI1mqdBhg1/zUJkJ1tUCvBUoxsSLEtPLGEWOXo7Te3f9jaAZL9ZPZyE49uVGJdyX78qRmxgpjg8okcowiOIllk1zth4z0ANH+Y8m6Gfhl0l8Aw3Irhn4HYh8gn28jHl+4/5XD4t3pQ6Yf+kvlLg+wVCnPvoenwb9Q5pmNB7kCB2PFR7GkbBu0dNW3u2vfAiY30YHVRta9WtoNuvtNKGx0/lU2YgEcBBmiz+/cUj1yCnfJlX/JRSwbeWG/sExs6CHIOrW2LD00dS1rjbL9vCFk6jPO3nqPeI/86V1tdJOewe48XNXBUT4ie+cKb0aZhXD64oxC5DJr9PdAbOb0pay3HA1eZQCUCQN7ewXapsPvgEX3mnxSu/HPUHdpQn5bt5yBreWIETuqSSJo991ooHZK0qtEtF5Qg8s00SJd8Wjn6zcCPPloNtF1XlWNqPQViUrKn5TUfVOkEAJBUCAD/hvTpEeQeEfW/GnhIQoy05qwE+5zf37gUKvGyEPju7den/JPdAdqjcH10EX97JFusLPVTDnfdgrT6cl0hiljBd9ObzbHNzXPWjvznoEOlJmn9trdwMogn4Q0Bx6Kln3foFddCl9GGCHGFZ9pATcJEasPvsaZAzavUe5L4OX0Toqo5MaZ4vm6bqGR0uLiBiW/2kFpNbpOQ1zSdDF755WXXWzFfyXb3GJE6H9b4RI/wwbm48oU232NHaLEqkK8nyX+kXnMDCOwa0KQUjWwMJDgQFEmXu5Q5WDa6rgljGDgT27zUjFaGoPNT7EqEUX5kBpnkHMsWiIhlLMW0g3XBT0floWa1xx+lrtdMvc6LtuzgqwaieYuhFfOrekuxZ2dgMRwCmPkAQMyntB41275YZ6A7Wh2LDYdESH7hrBpmb19w/MiawHyj1pcXkLjs5nTnjr29MkY7JJhqU2O9lm4/FJt2E+ZYERJbla1m03NC7vnSq4QpQpLUCHVVmObr5wCkA8frkd9CH8ZkM4+58E7FucNrxZo1Recr2Y0MWoIOENYhd1gx7JjVkBWe9f8D88/bL2OUqBo9R3vWNM9EGUSH6giAOV7JKdxLqabOF98Eo+z7BZZSJQp3Hg74tr2H7tZT8RPzisNoNnW7dRa2WLBEVYvt5vqZ75jhC05hBXY2f7r3h3Jfow2y6YW1EK+1b76aFnSM0E2n2DDHmTXZF12Z/gzGwJAYAmL/hm1HA5zvMLbbBvZhPr8C/ZJhPxpC0JYYTIP69m94vSWOyVxmyd5YPQ9eFowUyQE+uyooUyLUyXaAPWHQSm9SyXABxV8SJnFQNm83eeLBdm2R0pISK3GlJIZox+ECL62sfDtVo/0Ph1Cg0N9vxV6mJ//CpBBmyLjGkjB7zekMPH1cpeiBnfdSyG0hoJHV3HvCDiomAKgcCGG8PMjTFPkcIHeAuNEyKejDTba0X1n2ibyECmo/1gU4tZDwolAD49tbUtmbfuNLscQy6tjo6tdqNLZlg3IwO6RfBZaegK1w1p202VjGlhq34r2WjRB2LD3zagbdLd0OGLwlTnB/59NKVYh07wQopA9OyVBo/pftEYeYJr1yzTuxH5RmGZC0FjnIJUIE924BVjhAtFdBKFKkb9lXYolnboVja1Fs3Qme8/rzKFlPNWXIUkJj7Z9BpQ1npWIfd94erJFssAUAahHOfRzYUnm0GTSZV+nSwCjoiJzas8PbXZ70mMJLYe8wvK0Iob06ciBPuW5t47hPCko9fSeG+ffNmGkz7H6I9ZbHXygYxs/iBNmNlUqcGZRYp+BilvemMPXVtqi00zY+y/mKLnvs/qZhD8uI7tj/X1thESm0E+z5SlF3e+Q27Agl52bHm2lOTMh2w2ppfctOnTahvupbU7MToWmXutox3xG+jHW7bHWvxXjt24SpVO+G+C6KHTMQdR/OH8AO1m5gjycyFHUKU6ISsow0xWRhPHsoru6SMCNh1agDkNZV+Ng8eZ/ZTPrzgNde7NX+KSODREMnGgv/an/27x0emC8M/+LqwmKP5OSlyz/H90d8pEia8N/pt6jlQxiohYn/zaqEVtH3uXhOrFoQr1cubYELq6kdnsyamTDYh4Yrcy4EMI4y68GM8y4/IaC3BlJBPp4uJGV+Plh9d98nmInKVyHh+RJLpYTKiOJeQXdSfx8sX9FF6LtLCzD4yB6IKScudtdn/oWkUNz/iC08aGTYI6yH8vw59LZ3c5M8Ys5qD0zLG1XW77FfBG04CG1x0vBF0i0ToRYLCD0376GPtt+yGhbae98BISEIQrUAFub0HNTcMcCyOSzUXNAxBKS+CG7iXeJlJpeer83gtQ+EtP/k5KVNGqOGNFyuw4oE+XebEW+NWPTyHTc33JTEJmIE6WtxhoamqRf3tzcTCE85pwijRFjVNq4XH5u5guIgG+qb/A2qmXhI4Jf9t+zpRT4AbfEROruf3JPquzbOE90/FwOvoA6shCiFM5R6y6QtaZ0KzVsNHZ/ORM+DNyY8zcqIhJP+G1oJ8drh0FCd/shhYiM3wTaTLGY56E2UdakDfmLIEcps1VuXjQBLXEHvF/py1Qfi/Fy7a6WGWyqdSLe6dICZ6RXc6FSk8NjUGVvdbxY8LTgtxbRGXpaRdshGucqxlBV3vReTNs1yigqGXthXUw7pNdN4Orb3MG9ICAFkmLQ270IQs+OlBVFqxFXw6Yt7mhhdteYz56QK9c2n++1XVNRPvFTnpY83Hfh0VYiz0CLNtFg3zwG6qRW/tJOYxN+1HTsN9mHIycj+5gWMjvK3cQws2v2d8dIOLLgiBUj5mHN6FgHgVP8z2LxUSteTZEkFFOcSP4dXmJGPP/dZuJcMlyZxXHvbJe70Q8YTvoYNBy9sUfjR70ZxwdTSC/A+mStz5Apmpikx9t6jcx8aoIkAZRk61P2RKS2Xr6q1q7mIr6Vau9i1JbBOqzjN1niKWGGYwZ/yaFRx6J9YE5QHNtfky8pnhhWMjEcIZakk16XzphE1p6OZaF4aaNiu5ExS41A1m92BQIPLYnnR7ALnLDWFjQgSEtYQR6WYJf3u3JZeHw8/3zkaiijMwrZMN2q8Zs7SvDPRF+DyHJSyzXQLoOyaOUQQzFz+gn05YdhknM6gD/5EY6AAvKyeE+gupmtI7z6MeWuGv/o3rVFyK3pJOmbZedLt42hH3jJeROpzy4Lt/wMWqt5xHyZ148fzJdnDZPTGe3Ba0rCB5+Lf2eHlK1sky+tcYLZ/953/v/EEmz4KUVgZ78XR2Cs4N/psREspiBnCKyz1HHYAtzYHO5N1q8+mOg90QhPqMnWv2OQoBMdAAAfeSdtHOh6mnq3ttQPlpVgclwDZ3F9OlSz7bBOxdSaPOwIXjom+SIz67A1x0oAZL397sQBVJYs07vl9cwq4NyNGX51sSoaGHFxLstpaqdf8G9oRy4DLxShxVxsIovr5+h6MNvLKLcGdrPJtyWGKNMGSii5pZ7yc7h+pOEfSv3hmm+Q57Mmp9U4CDzuLwbLMZHvMDGjk2Ai4NE7rcnKOjb9Dy9Bj9qzNcmOUHohr9aZpJnpEl9N7R0LVRTZmtg23zqSFatKXgzuXvH6Y6cA5Hnn2bA9UZQfLgZXOQGGIcuLHR0qD3OhdF8oMLyvY79xpilYMw5PgxapTXCMQyhALKmeR1Lt350jjdjP3K2jdg2L+o/c0hbrc/n8lzufWgn+PkvoWwtffjjBq29uErH0HyXFkNP9vwY0thSJ3/roEygh08XWGK21X9HGAovcYBiBskB9cH7NzWPU2fR8PjVm6+Kw2x9C5LcV8PQl0J++A/uvQsXOAFEoPd3KyvbH4j8ZfBPNgv1qOb8rh+ac8NDkG52e5qsufTiiZEkuTwypY9DQcZoa+GEu47x7d3RpMIgDYlbBkkkqEflxlCEe87qC96Yj1mo7TT3MNGKj8HzVZkq5CMfmlxuU4CKKW1arXaCSLfYCPtD3mOn+Cq+18n8PbyopJbRqIHV6cQkQ92NWVhSD1Dl3wx2K+WrLule3H3Cz2bP+11jQSWQspgl+clvzKaoKg66GpMeQ0ErUTe2dHHHtJWJYqkb/igcV+eTaT6U+FfhAVKwrs6oTIcvPUvMuV9LCD83Wv80XYGsgKYYxNJIvOIQedn/O8jRR9dvvgJmx9kWoEwW4lhoimBfKaz+UwmBIXWaGDn53hYMfoo0X4S5MwroiD7CB8DCvJGYz65fFQUQZY4G2IzkjsjbPcaz+oHK5nFxTVbu0lw6feHsyq7G9UPdZ3U1QJnPJUvww2/mlb+QdENp2UY0X9d2cft5JO5lvAaYqIWWYd5z8KTYdsZhq3GEU0Sp8qZ7e3iKC/fJ091LYDDvW+d7UcbcKFKCf0YeAT7KvwpFsFDnAUAWE/2yC55qJIYdsNb27vuh5LlDnWfz5lnB2WHWubC8yYKSUOXpyWfgNxIIuWuE8VB9xftbK8gBibE7Wjz/UExbIEoS8feAUeLmvRM7nFNQJVMZCbjimKu58B1LPuRWupcWP1AGcJkvw/S1f2JW3Lez67f/FIIu9kL86xRY9FizMVqDwHtxLTfOlAkwtUI0S15EU+36MhPaCL0pu3SDqWO2gaO1UXrqJNXvPqEo1X1gIyEBEgPlyhQnu5y/KL2/8Opt4saMS1gXd8xMz2XVhehMVQ4knRBCIUyKtXO5r6DqBSAI421vm4JqfNS/N1j5M0Uvea18uqhYZULtn1m4R2M8sPYQJBy4KLLDiXsTmrjeWaTvAER8g6+U2lH4yg5IRNZJ6b2P0IY7Pn5LhWLPWbfJSGzfz3DB28tw/WtrzbPDF7Sqj9WXr1DMrwc5mgQCBGPV2gLvLDx/1fGMYffpYn8bvBZv01UvVIcw2mF6qgwyx3ifVj2DWNcjCGkXPiohdVkuYOM6S8RBp3UhAxnB7Sgad/QF8n22wYI+mNU0hZkQVlBG+A48iIepMBHTj7R7BCbuwc0inwRXIXhOq5WEb2+Kney5+xM5sXEdtPtJJnpol5JVLUzySQqXiXkE+8SHa0IZINwE8rc/fDww5TMuFD869KpW6yC1fpgD0N3hTN11ehn1+LS5SCMuveuJ2MecxIpU6ge+8ETfjBrrk9DPF0qYKZLkTlk+8rgd9n4T+hYaqnzjHO98jkipC8O7I0hnxlIHn4VBilBjVGtPoV6i5KO9fNyjW3BBew68hRO8/FUS8fqf7nGPz8MTgW8/qzvtViK/QE64u63PJhnF7s/T+fSiRwSa1HBnFf/LLNx8/lW+/053qaAC9Pokh2BLdGmU75b9eJ6/KhrwoIltd5pKMtYCVKp1tOHGvS4Vr65VSLrMRPMEYzZzHBt3OKLJ6WkQS9/HFvGOj37JvVO7X3oav6xm1Ae4bBFy3XACwXlv21JdJIyyeFLZXwyfFwoaIH2wGPT4ptk8twV7jTA9jabUuOM3kLVkd4tSlu5vyxGgxCipVIDpHQpXETlLngh5eWcplCVPyZUrd9S4tjGRxdmxzRnQy7lvPo1Mmc1zckT2d7qll8u4d4LTHTwUMGN54MCzU09Y2IcvyiI8iA7rmLZ578ayLT2ADl+re4IMwIxT0IgjlAFdlMZURIIiOJmlmuozvzrfxQGw1/JpUk4l/1XmZmtfN1CPRxxGXeJqwEH3g2/7eh5izljqn5EdkyuIHNTWPS9w1ZmjCeTtOkiuOWhEg2eSjjoOSPNigT04t6MIH+meCY7LqyKqK3gjTV/uuXfRnUzLEXY31/GR+Lu+/wfFYwXz3pfjMbrVI3imkzIaiiY8QMq96E2FSH29LjleioyzzWTj4iKlguwO2I4z6r/i3a6pn1DArBaWo8VVJgLIBXGf6XrYTRzOpDIwms5z6tmuzg1z3RQMGa0T+F+VY10mJ3n8uGW8Y9NEXTN99fMCOWaju7XI2TvaaP3ioTZJg08e93s04q8kbXNQ5bVGLdiPDvAIis4QNrUXgB8WnSzLQ687488fC8iNwCJD9pvyndk6CYLSNSg9oZEeYs445AqH9n7MnwSKmNFox8Ci8H02Cyaf9I5iwSaq5IXojY8FOLp0bAqEaXl955Zo0l6Z4hHRGlM2tkLGUnPnQ2HjRsunKNc7EBlBQuZ2e49C6Q8B6AQOuJtNta/bMRoAN2PgCaBDBtcCkXTFdxts9sPTZDSxZzGzbVcBMWG6yCPMUEoIwvbR8yRrGOR83199QcmTLSx1HEigoxrgoseSuvXHA53l73FCTTj+qNm/DbEFtXR2jpF5S5Q4ORW5/3xjRoNeQZdgN/rrDuDFXV/+v5mhhhLj62jdnk4DmQ2lIPEl8kt54ezBmh6s7ML/cYwmvgl7iARz94tqF6JhBHm13O2BEMqrrxhjU0+FTv3sYJqyaQ5aWcAdHjCNA58lFlwfQwG58tffb/FLOHf/KQDKjvA34OxAZxbv9Y2D0DDwyJYJD5KZlKOovnE3yJbjUADIabu0u+MESHricyy6JA3GFQq3DJoACNu0xOyDBRW34agq/PzYRLN5Er/qocfhbYMUZW9ArHzk/kPSiG7tiD5RQOuG1LtKvMveT8zh8V5l/3rMDUceUWdjVm9mPLeX0VTjhJYcBd1Jdkta6LlR8aw4FaNEUnR5wFxwbEFkLD/Wd/qVLQgsSevAIJ34VtXkgYOTqwikSJQTywVWN26ZEQOWr294C+KsPbX7kEDuMyxshyTiWKHGFSeFtT+Zd6xTS0gSa9EdCC6x2XQKxMb5LPWGdXuF+d3kqRZkXOxZ6PU5CdByl9yx0KQaMzXb7nRxAUVOVUOL/ATvkg1VdDJCLc/YofH89UHHbHKndNeUWxKj4rB2dsbs1kqWXdMt/Rug9iI2pxWsQIdceAZQsnh5XAO1MFwQSod++hHKnsVvoPR0upXeZKW2IXXQB3r9XyH5d7ftYr6P33QvyMoX4pd8+U0Oa1GE7+7QeH7Wa4IQO11OL9cS31xt6IROPGolNEOU4PXrmRnIIxcFTpyOt+qqBXtmuH5wCfH3qFmqSU8wju7PCf0CzHZYvXVpwjFxdThjTrNwhSkxTGOaBmOboGPMwvZMW1UhDYZaT2kiUwt/KbfKDlOjFRrjmxcs7QokZKWN9iOTcVlb98BAPM9H/6Y1AZThBM+yxgPUcnmAHkzfgmWjps6yDKRhEu2/jEfUPsp3PdczWHpyRhs01/VRYZqlwo8Z2IP1URUkzWEsMvt/+XGH+ICO7rcSpvyxzi9y3ZeWjSU0R3w60T5hYbqtkaBkETQiNlo9l2FMg+HB2ee98w5cuWgOmSklrNffOtxML45wjcROPynUcXXOj/AyEW9Q1CFQg5C5yLIsKQaqOg10pehvkvNUzdf4d1eRnWwkybShGEHT7lLhZKirtYgCd+WdErtaRsuc0SjKnKW3gUmYe+FX5fml34K6IpTTxaPjg0YCR/XIuQwObl5lBpdAidRbnhXOK1d3Ba3SAYpOzfRFJzE5IhV3r3OFKHGsNxFO3QUMXkzxk+8Z7JenWv2V4PetroD3W8yeUXQ2JdQE9V10lmb2/mQgotuwvW64tnDq+CNBXXC/gvkL7PmoWox+Gsrs8bkfmoXfh5uqhcNkgWAFXcemNs99FQ5msDH5QmRcRDLvGGk+CxGh3ywFUb+IeU+39+oeDcick+p7Jcm3djyzw9Xcx22qYwvcS7AWdE3F3uMqWfYUF3NwsAYHVme4z/Iznmf7Sbjxav+vgBxj6Hh0TCXbQnXrlR8QZYn/j/4Ya4wb/RmPLPFh2PFyDe2sBsWWneEWTCl3p1GiEy89t8v4bI39Wcg1kwAnMEAGJkWyGuyrFe6/V0VgdKaYzymluD3LlFZkJCSImWA/kvR0ts30WKdHq21wEocnmv03Rh2tFS9usf75dKyARl2n/hMUfDLiizwzZWBF9Sz6GZgI1odNMnsK86VI6wlNfuznipQtSmi4m2gyIUKA9JkLGzglVa3OK8vHfkvidfzHtCtvrKZZow018KWYkY+T2EE6pTNIIargS7jAZKg0QVBhVaY5Bm51to1sLmD3g37fPO01fF35aEubvIN/XzfliEvUSrQb4YvfBVDMCfUyVpFdmZPtmyx+2dQZZn6cTCLrgAjW7gmPKrUHw0jQb/vNfR5XJhAKPUXMo8zDcBO6YyUHdkFRuIobDWbYgOh9DOf+SyMeaGYLLi11dIPUdXhlZ7l7n1Y4Q5+Nqs1HytlQrSx2aJGE1ye2TbEZAfJyh+Dc1sZf7l4BPUGbxLxQpmCVpSgebVTYhRODuRrcIiwvu7jWg1j5d3bClPGF2ITvWl3ZdxTx801sV7pA18mJqxlO86JN2Gh3IbVzvlriScHAawcb8swV7gscEQvq/5Y2eGPoJAD3a7NFTRDeqBIGwMFOwYMhf7qWY5KpWfkdGdhjW2JVDjK2LXboKkQ+SWtlYFZBGMH+A8EPHbJ79E6tz4PIDBnE/6s4MMWJFeJZ0NTQmccsKVReVKC3iNhHvSrUTcDMbjqxKa+yrtiPCGAugFxTJZtwgL8z1esNqrSc6DBnIMfS34vxVqvMjZ+K4lHsoSUxSSFBfcaA5M7NPvXbzWD8L5aXx0k01dzSwEcIOtZDy6uk79DMc2xc7dOg8+worLBq6m71EhO7aMaoYUdVqYFrvQsV8VNSHKhe7S0jmjXwxh756TVJi7e7WWYcKB5ZZ75ybvnPyywaeg5tXSayu3stuZ0AmoldrCyhz69wBmjearnm3wtACQoXGZ6QdOfCWKegJS1zCxBotQSszDAShofQSg5xHlgaJ5hRitOfpSCzaA38rlPCr9G9AB+wdj7tkfa6zJsnElp3oW0RUYb+OvGQZ1BwLNp97YcmxELT5IFpCPO08Pw3fdYhw9qLlzfuChgw7KK2rIrgv8rUyLD+KZMJpFYL5Q4tBwGFVZrVRVtknS7qOpBn4HA7dHASXMGTq3cyG0jcFTDXklDrOt2scRLJyfANsTM+KVZqNv7FGcEzuh6de3l5hEy4IPUW2e4dOYBU9VKQUEjpIEXDJfeeo7uD7u2VBZu+Xi0miiadhUnp7MJClqKtO/Lu7l3kL66c+2v1BG1NuGCv8n+G5bNF9zPy0F1Kn/HQ8ce7QA/FAhmiXNIJy7Z4L07WoH9LiFGToJG5g5Zg8HgWXH5h48yDQtiTa5mfl6YVikXlyrOzH13MVTJAC03YMPdtgWHre9ZDu268dltiS2aXYoZB0cPGgejCyq5oKqfHVygCAkCreUp3SqshWN2ZJ8FObaZmmc0gqBvl7JWQdjt1hyGmxkn946Ld4ynfcJSkl2Sd8q/n4zSvpUg96UVwb79gKxZK1OXM2bUMntdN1TpZ+p4PvR2ZMb/7Ixwtr9li0V/JOUrKZJjGdvy8nBx461Rj2mLcOKJeUJApEU6G1rj+Yqm6YkVNA+JQrS0GsxGGYfaSLkmtEb18liXVwLL7OPxDe/xbdacuWPr8GpKbG6/IPvjzNavxFClCOOrBNmVmuu1TMwkiT0EU+mK82GkqcdHCCPZjbrFpB47/FX2pTKfQI7StIKR+nNk7g2TRlaST8gpLgedZ/CUUCdmRvhHkDL3qD4jXDBPiOnEwziyTjIEaxttIsotR48AmHhI+FBGNP2OtV6cOvrQw867daZbpnrEluRlZKsa6QzCnBS5nu+PSH/xeYAysyDXGsdh04cSAZIGp57a9Hz8ZsCnt2A7Yd6MiTE79x6EP1wtqWsQd2pjzlzHTqKKTdMh9uQO3QCG74o4HQILdjD9C/GwuGkzvaM6aB9JuVsVJHD+1cZKcntG+xNlQBTP1hj9iVGr1HgkUWt9mkzX1TzWItH35P2GBleDruGy7AHj/Y2jphVgUkHD9ebRNhYoZ6LYmSjq5XWZ5MbmdT9WwlQiDeZQcbw65t33GDhsRraPNoVsF6VUkY9LicBKCn4JVK2E1/7xs+UAxdLWUjJwbvya2ssTvYup76ohDQCq8iecSG0eOySdVBBbw7jZMbhikVG0LGpNVJV7zkm2Ge5xywnrb0tdIc/Afc5SikrP3/W57CWCHCJ1WOQntcGG/mN1lI3/KYTt6xTfpd0Y+FZx1o/LI1WdEnjXiu8QEI7uk+QL3xYp9aKcXim1emUhz2BBN/3VF5cJNSuLmQ3P/ETh9ReBEtF+pMMx88yp60L3cDCAylR/MCcB1FQ0SxZyuv0WCr4WHWCixRMhB2BDnO46d6jvFUvVJrU5tRP2eBWg52lO8eudHnwpXETwMZQF3dS7vWBA5g4dd0jVSUYvV4huJUp29XK9Mh3rOq+dBoXdxNcgzoV8rw/lBC8mvdwZu6FucU9eo+USJ/vvYEkGJUrAiRuWE1+ZFZtZi4mcE6BOo6Cna209/FoX0CmXGPxfF2rIrs3+BTp4eDDGr9l+WMOS3pbU6uGLVUtSv3bYDF+YHFCP17JET4eFwLyK8Czu2OVryFGuaxwLe61D/BF1dI5zULxYFl4W1seekuRysXS4rVLrT2i893qCNQrzOi5RYioIKbwpvcAXiPDb2mIhkNi4RrfL6vA7Hwyg5DcqHLJQg7m0d2nFQxmRbr+6PgUSmHq6TA6wjII8dVhpgv6Tr+Hhm5o8p+o1oE7p9ulH4rWZjIwGLy4Brpw/lrXb210yOJTmGpVoxhbjhmXup4VxQqN3S17KB/gcQcK3651q6kt+BtxGE+0vqbQ9sG+Gh8wlZ6Y+47bCV5zUhrbbVuRAfPfKwG5hzgctALb/8aBfhFDz0VntD5qahQYWefE6mE6fklfwsTCMpbjFRcSL0/o7Yg3V7k5QFA9G1NVGqnMuuSG06ZLFvCv2t4aBA0OEANsXKUcYEHF1CkpV2mylGws168quorfWZaVn4ESiEtIbZXrVvEMVFESknxA/Gkf//wxYjXGTg0J/+S5n5iA5eS6oLBFetZdpmNL5ByuZvHpy7YcZcL+UFI2gHgi49nx5hfWhvcCjSMwLL4wXb1xHl2TfsRnuoYmrx2O0OmNmCzZrcheQxKELYeyha93GMipNW/Dk/uUfupWnuB2AdeVu0xWK9nnqPe1PtoLAupW0OaoKAR3p9aGnXpDZmP/60eBFKgqQlbqkZsc4QmrAc66P1HKZOP76l/BPjBWY1WXl7aqFcCX7doU4g5DcF26Y3FvCGqww0dLQkontfTwImQv30dUKgBivj//YgaPE/hc0Ncl1HN87t798jevX+2XQkFYX4QEyQ6kF36v+SCbRurt8gcDSwQaJdqg0Tcn/lf1qGFtYXGZ+kAjSrqRFfNuSAI4QfDHHHevC/YLlLYv8U/GwDVDTT/Kpz1arwAqKM3fer7dQE4ABCG2MMJGiMhClzO5cGY86KVoOb4gFv7aqCYW8+Ni+V397dZ1v0aDtnJA3DQfN3LpWa2sEN/gFwo8LLGKG432eL9btue9N7KFhX0hc0Rs2+YxPJfdPbO8hdNMss2/eLqr+1JKdvrHQcyTcyfg/m/05Hdz2nDvdBzmI3inmJ0hlbGcZ3XjkS7B9ojzZK2h0YW3T/yhazsW3verN00HH21AYGkNG+PHESeMsSjOoweK87y2TcGbsRVkwNLLBpLzK9bM1Hcf9a6TXImnOwjzeZEqkmaUwI+CRnGNmzenb7V52lprl0+ycFmIOIaqBuW5mK/BIaOih8aSphgbGJMs6aErYV9CeEgcYNSdUr9+3V2dnFNAfZ7OTPk6v4BqXeHuf8AF0GoOqCtTepuRfU+1RSgaoDUCGuXu8S33weq8XctlIO/MbU2XAilO2k/9gD6/Lq9luTAspBIQoBOKQCs/jnFRtfJR46qBV3pHjVWaQQe2XGFDSFF656LsC0kifvUN9GQj4bpBXf8oxIg+ucQYI5gDfkpfiYOu3vqDFB4XmM03kenJ7VrP9Y9izNITISdbclxYcpZ0eCo/7xqy/bj5L+zws5JE//9Ds4nKsvDN2ykVf+E0wCL0haPRJipeiLLcnf1MLiXtJIKbiKlkF6Ij4fD+HTbXt5EuKp1i615hRFp4j5vhqJLl9456TF7GyJCvFlTgmGoVLH6q2vk2leo6rwrTSc1jToYGMTS4+qOpfzwVbBcUm1Q/8Aa5VRgVlRmDpi3650wAKPC/Tei9VKYJTkIc7hf9U1OwheIDM60kIZsjZgiRUjH6ijGrZ5eT3Ly0dTBxmla0b6GZtj2LgOm+Cqu0I+eh7IkusM6lzItl+1LZAONvnxVaav1soDfALHqIGggp1qfaxqH3EQZ1SOemneZx6p7wnde7xXoLuFrd5I3QH8Uczq0qI0ZWK+R4Yuk3fJrqtkQZGrkKWxmCZulWy+y9nPnLYKMVukKfFQbflizEHVxS+iB4YZ3WONPGFDTy7p/ePbyOzKNLJFratWd5zVroi1rpxb4asvxnuiRNAGtwEAz3ykG0+vy5OyRquym9Hf/zQAJUX0t/U7WY29c7czzDHm7zzOaO8jeXHpmO/1f2jPRUEiB486nYuow1HJznlNPbCA+MO9v4MVaE13/Xc4QheKtMJgrJcqlVjmroabOZyIX125Yn/7lSFnms0UUB3QGxm6RRmrcsFDNujvNyVKAtQU+0J0hhEAjpHvMOOUcn2YdyINnJ3Avnf1HznG6oGB2lh7d9ZMZr2C2VueLjVrliFUkj3U2Wge09E0PGSxzxLbEJWvbV9eFFv96Unq8oKjNb7ni0twnPoRS8lbRV4u6A6r4HhA6c8gkj/ZSUuxA9S22KeBNvDFYpyckhMay6uznilDrZDYnAwjDvpUKvATzVcT9Gdj0YRbLMGVNBKfQfdU8J0F/GqNUILvSjgHEjMhwbdXH+Uu8wfMi06hECgotkJbUuqhF2kFCXTyNNLMT54vD+K13WqvxMSIVOLUo3uXidBrTMmEzIwakUJa8RIWyeitF3X7m3CuIfpP1DQ9+PcnqlTh4mf4LOTV0PgfQro5msHtr9lxD/qyr9AUWG5K9BaHLqmljriUqC5/3AZw3OpQiQAEg/cbJVpfx2fBFREZtYJqv3viLoveiuEjaagbj8m9venaoelCxITlWe4Z816htibr8gG97YaUlaf3s41rfiRXvFhunH0TVw7GIQwlY4PGknBSClMUn32ieCGjYkzGoAp/fzHFMKHqHQ9b2bLgy2nHHaRFW5SceZ8y61rhP8cYPzh5MH8S9pIxvpGLGcOfWylYBgrGt88/aIs2cXGU2lv3Np+L7F9fNURQDEs3YUDyhxoPFEHzhyqXszUJlebVlARrU7fsAW0XttbUXz8NnKzIkmHhdpyLPi2F436IK/EmALtJWgRUsT5K3tkOViLExoUMrHo87Sib38P/sT3pEyVRyiddaBoQUQl/Qqzc4QQDoHxFKbru5SauB2Y2MgDs4vlR6E81jwV8P1zLCubvZNzySOx3MOL9rrX2s17mYBt7oiHuVfHj4hsCZL1NyYUtfxiSFEDGdveSbSgQzY+SJexFHKZiw4oP2KrrETHtLkPrIoPWoUJMTSssSsduTeJssBc3DnJldljFDi8Zv63F0LMtiGgsiQ9HAbSyod0foBdHsTFMPGDKodDFVeC3YOJMvZ0bW7jOzzf71XPlQ0eHGGM4Wyue/Xdm42VrSWHbaIMXxAtmI6oPa2PM3V/Dke8+17lqqAkkoiVkDm42FUHK61ZkhLfY6Q91rv/5kj+4QRSeso+U0CrpqAodtGfEkh2/bEqMCG5L7Ywm1sGP1dR/zXPqQ6ijdjnn4ynOgejf/oVSdC8aEX1q7LCVOhuXXllmIam0Ivx7VSO0nBoSJxcIWlEI3ZHE/0GaDY8m/FHT852do5DTuIt+2MrqLuBdtAJeOx/hRaH+LefSmEKiQiQCusE0WcoeQmBC9gzyrDB9B78IpYtg1SCiQsRysO067Lvzs4QNqhJ7jqb24UcSKMI/m0lgwMV/Znh7kS6mC5KV0n436oeFVY0wxggm3hAdH4Uuu3EaJB1CcuoW2Re3d+i9VlDbAzstk+VeMz5mMfo9xcZf+ZKXGu0oh3Im6uhmMe25H1Q6HiEXETuhx0QaYyep6ZDQ67axkRaWYWkPdlJsBfXABDdZwghbkpJd6PgflTsTlmjSTS5oc0eiwzASYGiZ21uAS46CmXxiMIdAUVzsB+TiMobXRC+w6RxCSoZDvQ837ggNhAvcChD+A1W1jvyGlo7EOMXbkBdJXy0TGkwU31fj9LxcntqJiG9HuTGUHT07AkXT6YCGr7k11MVXMnCz6K67lwhdm+j0MTQUHdpW5ASNQT6VceuuWGXr6zEeNIZ+eBXUpF40hQ4s/7/mw7hEt6DkvIVqaLojg8XcmukIS+311P6nwzxOQpOmxQ0ooE25itjYxZLgl7wrO36EVgkOLktfaHZxf8bScDnL4//i9ijvxO1OEM0oERk3zZHGZDehLMYeuViEuUXy22JHLXoNT7gBJVDebF6uFwvBjBAkglPBKLZXOHzLzzX3XGupe4tFKzuU521oXrds14gUBPPlq1QvmvsDuzcfwXayJRh6CYcHhJJQyee8Y24xSIcyrp/1362V2urRMAfRpPZ/gpSdC98etmkiM3gYr877CuklmeBA/h/FpJxStSgjuFsoZr77t31XfgRIJ7a38jkBysmwYkKHjmOaJiNeRYzjTPcFsTRQCq5HI6RrkSL2/sbYSYGcxyLRlljuHwypXKc2ZColmvHYYaiuNUIV3CdEeVVgspqY+7cbbqckzm7/s0eZ3RagwvswE/EXnPleW+UlpVQlxQaxyMvFC7EZtu51Y1iB4N/vpjlZru1q3h/83FghhpERmdiTLdSRHD2wRessFLq5eIzkSuJa31sPVj0/Q3Z4oTIKc5LCvWcjcz7yA6xsqiFOJWX0DgWd40CzYqgKKMCJiM4Jt0hitdY4/eBDcVFOkatq0Uj2jUWxcjzklY23Xob6vu+ok29ZWiSx9ZT3evth2G6fyoeZ7BCkMVG+Whu/BmzfXJSdncJzAfNHhd6gmvtAkDnQKVD/18UjvNXloxesFVqPTvCPwVSDoH+FcZwzbY8bYP8MCirh2AemR3lndWb+Ph0i5qV3KyOx0BOHI0rhT+/aB29x3StaNe0T2ef6Ke57XsFmEcrORrCQZtBvDq4faXiMUc13i7myXr/Wh74C3wMVELGtBsifBplcBkyMbz5JLH94xDxgR4ZmF977i0IANB2AonbbVsOXaHkljEdxlMup1ixYNJWf2yf4TID08UHGmxLKYR7sVD0Z694g2xhtEN37PP0SE9JoIo9Y09ApYl2S0i2+HEfffNNu/3HeVcG/kQtiHQDLt2s56wfIxTFqznlsTACKXE0Ed4XYZiafdLwQumtrtLiy8PCxLl2vNMMTaqTqf3yWxlx3rcM2iY");
				dbService.save(sigcard);
				for (Tbdepositcif cif : ciflist) {
					cif.setAccountno(dep.getAccountNo());
					dbService.save(cif);
				}
			}
			param.put("acctno", dep.getAccountNo());
			form.setResult((Integer) dbService.executeUniqueSQLQuery(
					"declare @result int exec ACCOUNTINIT :acctno,@result output select @result", param));
			form.setValue(dep.getAccountNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}
}
