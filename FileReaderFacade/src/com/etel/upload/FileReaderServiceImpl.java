package com.etel.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.coopdb.data.Tbemployee;
import com.coopdb.data.Tbmember;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplLOS;
import com.etel.filereaderforms.CollateralRealEstateForm;
import com.etel.filereaderforms.CollateralVehicleForm;
import com.etel.filereaderforms.FormValidation;
import com.etel.uploadforms.EmployeeForm;
import com.etel.uploadforms.FormValidation2;
import com.etel.utils.ApplicationNoGenerator;
import com.etel.utils.HQLUtil;
import com.etel.utils.LoggerUtil;
import com.loansdb.data.Tbcollateralauto;
import com.loansdb.data.Tbcollateralmain;
import com.loansdb.data.Tbcollateralrel;

public class FileReaderServiceImpl implements FileReaderService{

	public static boolean isRowEmpty(Row row) {
	    for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	        Cell cell = row.getCell(c);
	        if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
	            return false;
	    }
	    return true;
	}
	
	@SuppressWarnings({ "resource", "unused" })
	@Override
	public FormValidation2 uploadEmployee(String filename) {
		System.out.println(">>>>>>>>>> running uploadEmployee <<<<<<<<<<");
		LoggerUtil.info("---------- FILENAME EMPLOYEE ------------ " + filename,FileReaderFacade.class);
		FormValidation2 form = new FormValidation2();
		form.setFlag("failed");
		form.setErrorMessage("");
		boolean errorCheck = false;
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		List<EmployeeForm> reqListTemp = new ArrayList<EmployeeForm>();
		try {	
			String path = filename;
			FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				System.out.println("------------ File not found in the specified path. ----------");
				e.printStackTrace();
				return form;
			}
			LoggerUtil.info("-------------- PATH - " + path,FileReaderFacade.class);
			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
			DataFormatter fmt = new DataFormatter();
			SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy");		
			
			int rowNumIndiv = 1;
			
			try {
				XSSFSheet sheet = workBook.getSheetAt(0);
				Iterator<Row> rows = sheet.rowIterator();
				Integer value = new Integer(1);
				
				while (rows.hasNext()) {
					EmployeeForm temp = new EmployeeForm(); 
					Row row = rows.next();
					if (row.getRowNum() == 0) {
						continue; // just skip the rows if row number is 0 -> first line
					}
					if(!isRowEmpty(row)){
						rowNumIndiv++;
						value++;
						if(fmt.formatCellValue(row.getCell(0))!=null){ // employeeid
							params.put("empid", fmt.formatCellValue(row.getCell(0)).trim());
							Tbemployee emp = (Tbemployee) dbService.executeUniqueHQLQueryMaxResultOne
									("FROM Tbemployee WHERE employeeid=:empid", params);
							temp.setRow(value);
							if(emp!=null){
								temp.setIsExisting(true);
							}else{
								temp.setIsExisting(false);
							}
							temp.setEmployeeid(fmt.formatCellValue(row.getCell(0)));
							temp.setEmploymentstatus(fmt.formatCellValue(row.getCell(1)));
							temp.setLastname(fmt.formatCellValue(row.getCell(2)));
							temp.setFirstname(fmt.formatCellValue(row.getCell(3)));
							temp.setMiddlename(fmt.formatCellValue(row.getCell(4)));
							temp.setSuffix(fmt.formatCellValue(row.getCell(5)));
							temp.setGender(fmt.formatCellValue(row.getCell(6)));
							temp.setCivilstatus(fmt.formatCellValue(row.getCell(7)));
							temp.setNationality(fmt.formatCellValue(row.getCell(8)));
							temp.setDateofbirth(row.getCell(9)==null?null:sf.parse(String.valueOf(fmt.formatCellValue(row.getCell(9)))));
							temp.setSss(fmt.formatCellValue(row.getCell(10)));
							temp.setTin(fmt.formatCellValue(row.getCell(11)));
							temp.setGsis(fmt.formatCellValue(row.getCell(12)));
							temp.setBranch(fmt.formatCellValue(row.getCell(13)));
							temp.setCompanyname(fmt.formatCellValue(row.getCell(14)));
							temp.setCompanycode(fmt.formatCellValue(row.getCell(15)));
							temp.setPosition(fmt.formatCellValue(row.getCell(16)));
							temp.setRank(fmt.formatCellValue(row.getCell(17)));
							reqListTemp.add(temp);
						}
					}
				} 
				if(!reqListTemp.isEmpty()){
					System.out.println("--------- ADDED TO REQLIST EMPLOYEE---------- !!!");
					form.setEmpForm(reqListTemp);
					form.setFlag("success");
				}
			} catch (Exception e) {
				errorCheck = true;
				System.out.println("----- ERROR ------ "+ e.getMessage() +" Rownum: "+rowNumIndiv);
				e.printStackTrace();
				form.setFlag("failed");
				form.setErrorMessage("<b>Error!</b> - Row Number: <b>"+rowNumIndiv+"</b>");
				return form;
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
			File file = new File(filename);
			if(file.exists()){
				file.delete();
			}
		}
		return form;
	}

	@Override
	public String saveOrUpdateUploadedEmployee(List<EmployeeForm> form) {
		String flag = "failed";
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			Boolean recordSaved = false;
			for(EmployeeForm f : form){
				if(f.getEmployeeid()!=null){
					params.put("empid", f.getEmployeeid());
					Tbemployee emp = (Tbemployee) dbService.executeUniqueHQLQueryMaxResultOne
							("FROM Tbemployee WHERE employeeid=:empid", params);
					if(emp!=null){
						if(f.getEmploymentstatus()!=null && !f.getEmploymentstatus().equals("")){
							String[] b = f.getEmploymentstatus().split("-");
							emp.setEmploymentstatus(b[1].trim());
						}
						emp.setLastname(f.getLastname());
						emp.setFirstname(f.getFirstname());
						emp.setMiddlename(f.getMiddlename());
						emp.setSuffix(f.getSuffix());
						emp.setGender(f.getGender());	
						if(f.getCivilstatus()!=null && !f.getCivilstatus().equals("")){
							String[] b = f.getCivilstatus().split("-");
							emp.setCivilstatus(b[1].trim());
						}
						emp.setNationality(f.getNationality());
						emp.setDateofbirth(f.getDateofbirth());
						emp.setSss(f.getSss());
						emp.setTin(f.getTin());
						emp.setGsis(f.getGsis());
						if(f.getBranch()!=null && !f.getBranch().equals("")){
							String[] b = f.getBranch().split("-");
							emp.setBranch(b[1].trim());
						}
						if(f.getCompanycode()!=null && !f.getCompanycode().equals("")){
							String[] b = f.getCompanycode().split("-");
							emp.setCompanycode(b[1].trim());
						}
						if(f.getPosition()!=null && !f.getPosition().equals("")){
							String[] b = f.getPosition().split("-");
							emp.setPosition(b[1].trim());
						}
						
						emp.setCompanyname(f.getCompanyname());
						if(f.getPosition()!=null && !f.getPosition().equals("")){
							String[] b = f.getPosition().split("-");
							emp.setPosition(b[1].trim());
						}
						if(f.getRank()!=null && !f.getRank().equals("")){
							String[] b = f.getRank().split("-");
							emp.setRank(b[1].trim());
						}
						if(dbService.saveOrUpdate(emp)){
							recordSaved = true;
						}
					}else{
						// new employee
						emp = new Tbemployee();
						emp.setEmployeeid(f.getEmployeeid());
						if(f.getEmploymentstatus()!=null && !f.getEmploymentstatus().equals("")){
							String[] b = f.getEmploymentstatus().split("-");
							emp.setEmploymentstatus(b[1].trim());
						}
						emp.setLastname(f.getLastname());
						emp.setFirstname(f.getFirstname());
						emp.setMiddlename(f.getMiddlename());
						emp.setSuffix(f.getSuffix());
						emp.setGender(f.getGender());	
						if(f.getCivilstatus()!=null && !f.getCivilstatus().equals("")){
							String[] b = f.getCivilstatus().split("-");
							emp.setCivilstatus(b[1].trim());
						}
						emp.setNationality(f.getNationality());
						emp.setDateofbirth(f.getDateofbirth());
						emp.setSss(f.getSss());
						emp.setTin(f.getTin());
						emp.setGsis(f.getGsis());
						if(f.getBranch()!=null && !f.getBranch().equals("")){
							String[] b = f.getBranch().split("-");
							emp.setBranch(b[1].trim());
						}
						if(f.getCompanycode()!=null && !f.getCompanycode().equals("")){
							String[] b = f.getCompanycode().split("-");
							emp.setCompanycode(b[1].trim());
						}
						emp.setCompanyname(f.getCompanyname());
						if(f.getPosition()!=null && !f.getPosition().equals("")){
							String[] b = f.getPosition().split("-");
							emp.setPosition(b[1].trim());
						}
						if(f.getRank()!=null && !f.getRank().equals("")){
							String[] b = f.getRank().split("-");
							emp.setRank(b[1].trim());
						}
						if(dbService.save(emp)){
							recordSaved = true;
						}
					}
				}
			}
			// any record saved
			if(recordSaved){
				return "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	//MAR 11-03-2020

	@SuppressWarnings({ "unused", "resource" })
	@Override
	public FormValidation uploadCollateral(String filename, String type) {
		LoggerUtil.info("---------- FILENAME ------------ " + filename,FileReaderFacade.class);
		FormValidation form = new FormValidation();
		form.setFlag("failed");
		DBService dbServiceCOOP = new DBServiceImpl();
		boolean errorCheck = false;
		List<CollateralVehicleForm> reqListAuto = new ArrayList<CollateralVehicleForm>();
		List<CollateralRealEstateForm> reqListRealEstate = new ArrayList<CollateralRealEstateForm>();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			String path = filename;
			FileInputStream inputStream = null;
			try {
				inputStream = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				System.out.println("------------ File not found in the specified path. ----------");
				e.printStackTrace();
				return form;
			}
			LoggerUtil.info("-------------- PATH - " + path,FileReaderFacade.class);
			POIFSFileSystem fileSystem = null;
			fileSystem = new POIFSFileSystem(inputStream);
			HSSFWorkbook workBook = new HSSFWorkbook(fileSystem);
			DataFormatter fmt = new DataFormatter();
			SimpleDateFormat sf = new SimpleDateFormat("dd-MMM-yyyy");			
			
			if(type !=null && (type.equals("VEHICLE") || type.equals("REAL ESTATE"))){
				if(type.equals("VEHICLE")){
					int rowNumIndiv = 1;
					try {
						HSSFSheet sheet = workBook.getSheetAt(0);
						Iterator<Row> rows = sheet.rowIterator();
						Integer value = new Integer(0);
						while (rows.hasNext()) {
							Row row = rows.next();
							if (row.getRowNum() == 0) {
								continue; // just skip the rows if row number is 0 -> first line
							}
							if(!isRowEmpty(row)){
								rowNumIndiv++;
								value++;
								
								CollateralVehicleForm auto = new CollateralVehicleForm();
								auto.setRow(value);
								auto.setCollateralType("1"); // Vehicle
								auto.setRegisteredowner(fmt.formatCellValue(row.getCell(9)));
								auto.setDateOfLastAppraisal(row.getCell(10)==null?null:sf.parse(row.getCell(10).toString()));
								auto.setLastAppraisedValue(row.getCell(11)==null?null:new BigDecimal(String.valueOf(row.getCell(11))));
								auto.setLastFairMarketValue(row.getCell(12)==null?null:new BigDecimal(String.valueOf(row.getCell(12))));
								auto.setVehicleType(fmt.formatCellValue(row.getCell(15)));
								auto.setNewOrused(fmt.formatCellValue(row.getCell(16)));
								auto.setMake(fmt.formatCellValue(row.getCell(17)));
								auto.setModel(fmt.formatCellValue(row.getCell(18)));
								auto.setModelDetails(fmt.formatCellValue(row.getCell(19)));
								auto.setVehicleValue(row.getCell(20)==null?null:new BigDecimal(String.valueOf(row.getCell(20))));
								auto.setYear(fmt.formatCellValue(row.getCell(21)));
								auto.setSerialChassisNo(fmt.formatCellValue(row.getCell(22)));
								auto.setEngineNo(fmt.formatCellValue(row.getCell(23)));
								auto.setCrNo(fmt.formatCellValue(row.getCell(24)));
								auto.setOrNo(fmt.formatCellValue(row.getCell(25)));
								auto.setMvFileNo(fmt.formatCellValue(row.getCell(26)));
								auto.setPlateNo(fmt.formatCellValue(row.getCell(27)));
								auto.setRetailPrice(row.getCell(28)==null?null:new BigDecimal(String.valueOf(row.getCell(28))));
								auto.setConductionStickerNo(fmt.formatCellValue(row.getCell(29)));
								auto.setFuel(fmt.formatCellValue(row.getCell(30)));
								auto.setNoCylinders(fmt.formatCellValue(row.getCell(31)));
								auto.setLocation(fmt.formatCellValue(row.getCell(32)));
								auto.setLtoOffice(fmt.formatCellValue(row.getCell(33)));
								auto.setDatePurchased(row.getCell(34)==null?null:sf.parse(row.getCell(34).toString()));
								auto.setKmReading(fmt.formatCellValue(row.getCell(35)));
								auto.setColor(fmt.formatCellValue(row.getCell(36)));
								auto.setTireCondition(fmt.formatCellValue(row.getCell(37)));
								auto.setAccessories(fmt.formatCellValue(row.getCell(38)));
								
								if(auto.getSerialChassisNo()!=null && auto.getCollateralType().equals("1")){
									params.put("refno", auto.getSerialChassisNo());
									params.put("type", auto.getCollateralType());
									Integer main = (Integer) dbServiceCOOP.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbcollateralmain WHERE referenceno=:refno AND collateraltype='1'", params);
									if(main!=null && main > 0){
										auto.setIsExisting(true);
									}else{
										auto.setIsExisting(false);
									}
									reqListAuto.add(auto);
								}
							}
						}
						if(!reqListAuto.isEmpty()){
							System.out.println("--------- ADDED TO REQLIST ---------- !!!");
							form.setAutoForm(reqListAuto);
							form.setFlag("success");
						}
					} catch (Exception e) {
						errorCheck = true;
						System.out.println("----- ERROR ------ "+ e.getMessage() +" Rownum: "+rowNumIndiv);
						e.printStackTrace();
						form.setErrorMessage(e.getMessage() +"Auto - Row Number: "+rowNumIndiv);
						return form;
					}
				}else if(type.equals("REAL ESTATE")){
					int rowNumIndiv = 1;
					try {
						HSSFSheet sheet = workBook.getSheetAt(0);
						Iterator<Row> rows = sheet.rowIterator();
						Integer value = new Integer(0);
						while (rows.hasNext()) {
							Row row = rows.next();
							if (row.getRowNum() == 0) {
								continue; // just skip the rows if row number is 0 -> first line
							}
							if(!isRowEmpty(row)){
								rowNumIndiv++;
								value++;
								
								CollateralRealEstateForm rel = new CollateralRealEstateForm();
								rel.setRow(value);
								rel.setCollateralType("2"); // Real Estate
								rel.setRegisteredowner(fmt.formatCellValue(row.getCell(9)));
								rel.setDateOfLastAppraisal(row.getCell(10)==null?null:sf.parse(row.getCell(10).toString()));
								rel.setLastAppraisedValue(row.getCell(11)==null?null:new BigDecimal(String.valueOf(row.getCell(11))));
								rel.setLastFairMarketValue(row.getCell(12)==null?null:new BigDecimal(String.valueOf(row.getCell(12))));
								rel.setTitleno(fmt.formatCellValue(row.getCell(15)));
								rel.setLotno(fmt.formatCellValue(row.getCell(16)));
								rel.setPropertyAddress(fmt.formatCellValue(row.getCell(17)));
								rel.setArea(row.getCell(18)==null?null:new BigDecimal(String.valueOf(row.getCell(18))));
								rel.setPrevTitleNo(fmt.formatCellValue(row.getCell(19)));
								rel.setPrevOwner(fmt.formatCellValue(row.getCell(20)));
								rel.setPropertyValue(row.getCell(21)==null?null:new BigDecimal(String.valueOf(row.getCell(21))));
								rel.setHouseType(fmt.formatCellValue(row.getCell(22)));	
								rel.setBlockNo(fmt.formatCellValue(row.getCell(23)));
								rel.setBldgName(fmt.formatCellValue(row.getCell(24)));
								rel.setUnitNo(fmt.formatCellValue(row.getCell(25)));
								rel.setUnitType(fmt.formatCellValue(row.getCell(26)));
								rel.setRemarks(fmt.formatCellValue(row.getCell(27)));
								rel.setPropertyType(fmt.formatCellValue(row.getCell(28)));
								
								if(rel.getLotno()!=null && rel.getCollateralType().equals("2")){
									params.put("refno", rel.getTitleno());
									params.put("type", rel.getCollateralType());
									Integer main = (Integer) dbServiceCOOP.executeUniqueSQLQuery("SELECT COUNT(*) FROM Tbcollateralmain WHERE referenceno=:refno AND collateraltype='2'", params);
									if(main!=null && main > 0){
										rel.setIsExisting(true);
									}else{
										rel.setIsExisting(false);
									}
									reqListRealEstate.add(rel);
								}
							}
						}
						if(!reqListRealEstate.isEmpty()){
							System.out.println("--------- ADDED TO REQLIST ---------- !!!");
							form.setRelForm(reqListRealEstate);
							form.setFlag("success");
						}
					} catch (Exception e) {
						errorCheck = true;
						System.out.println("----- ERROR ------ "+ e.getMessage() +" Rownum: "+rowNumIndiv);
						e.printStackTrace();
						form.setErrorMessage(e.getMessage() +"Real Estate - Row Number: "+rowNumIndiv);
						return form;
					}				
				}
			}else{
				form.setFlag("NoSelectedCollateralType");
				return form;
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
			File file = new File(filename);
			if(file.exists()){
				file.delete();
			}
		}
		return form;
	}
	@Override
	public List<CollateralVehicleForm> removeAllExistingAuto(List<CollateralVehicleForm> form) {
		List<CollateralVehicleForm> list = new ArrayList<CollateralVehicleForm>();
		try {
			for(CollateralVehicleForm f2 : form){
				if(f2.getIsExisting()){
					// do nothing
				}else{
					list.add(f2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public List<CollateralRealEstateForm> removeAllExistingRel(List<CollateralRealEstateForm> form) {
		List<CollateralRealEstateForm> list = new ArrayList<CollateralRealEstateForm>();
		try {
			for(CollateralRealEstateForm f2 : form){
				if(f2.getIsExisting()){
					// do nothing
				}else{
					list.add(f2);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings("static-access")
	@Override
	public String saveAuto(List<CollateralVehicleForm> form) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(form!=null){
				for(CollateralVehicleForm f2 : form){
					if(f2.getSerialChassisNo()!=null && !f2.getSerialChassisNo().equals("")){
						params.put("refno", f2.getSerialChassisNo());
						Tbcollateralmain row = (Tbcollateralmain) dbServiceCOOP.executeUniqueHQLQueryMaxResultOne
								("FROM Tbcollateralmain WHERE referenceno=:refno", params);
						Tbcollateralauto row2 = (Tbcollateralauto) dbServiceCOOP.executeUniqueHQLQueryMaxResultOne
								("FROM Tbcollateralauto WHERE referenceno=:refno", params);
						if(row!=null && row2!=null){
							// update record
							flag = "success";
						}else if(row==null && row2==null){
							
							ApplicationNoGenerator no = new ApplicationNoGenerator();
							String a = no.generateCollateralNo("COLLATERAL", f2.getCollateralType());
							
							// create new record
							Tbcollateralmain main = new Tbcollateralmain();
							main.setCollateralid(a);
							main.setCollateraltype(f2.getCollateralType());
							main.setReferenceno(f2.getSerialChassisNo());
							main.setCollateralstatus("0"); // In-active
							main.setRegisteredowner(f2.getRegisteredowner());
							main.setDateoflastappraisal(f2.getDateOfLastAppraisal());
							main.setLastappraisedvalue(f2.getLastAppraisedValue());
							main.setLastfairmarketvalue(f2.getLastFairMarketValue());
							if(dbServiceCOOP.save(main)){
								System.out.println("---------- TABLE MAIN SAVED ---------- !!!");
								Tbcollateralauto auto = new Tbcollateralauto();
								auto.setCollateralid(a);
								auto.setReferenceno(f2.getSerialChassisNo());
								auto.setVehicletype(f2.getVehicleType());
								auto.setNeworused(f2.getNewOrused());
								auto.setMake(f2.getMake());
								auto.setModel(f2.getModel());
								auto.setModeldetails(f2.getModelDetails());
								auto.setVehiclevalue(f2.getVehicleValue());
								auto.setYear(f2.getYear());
								auto.setSerialchassisno(f2.getSerialChassisNo());
								auto.setEngineno(f2.getEngineNo());
								auto.setCrnumber(f2.getCrNo());
								auto.setOrnumber(f2.getOrNo());
								auto.setMvfileno(f2.getMvFileNo());
								auto.setPlateno(f2.getPlateNo());
								auto.setRetailprice(f2.getRetailPrice());
								auto.setConductionstickerno(f2.getConductionStickerNo());
								auto.setFuel(f2.getFuel());
								auto.setNoofcylinders(f2.getNoCylinders());
								auto.setLocation(f2.getLocation());
								auto.setRegisteredowner(f2.getRegisteredowner());
								auto.setLtooffice(f2.getLtoOffice());
								auto.setDatepurchased(f2.getDatePurchased());
								auto.setKmreading(f2.getKmReading());
								auto.setColor(f2.getColor());
								auto.setTirecondition(f2.getTireCondition());
								auto.setAccessories(f2.getAccessories());
								if(dbServiceCOOP.save(auto)){
									flag = "success";
									System.out.println("---------- TABLE VEHICLE SAVED ---------- !!!");
								}
							}
						}
					}
				}
			}else{
				System.out.println("---------- VEHICLE FORM IS EMPTY ---------- !!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	@SuppressWarnings("static-access")
	@Override
	public String saveRel(List<CollateralRealEstateForm> form) {
		String flag = "failed";
		DBService dbServiceCOOP = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if(form!=null){
				for(CollateralRealEstateForm f2 : form){
					if(f2.getTitleno()!=null && !f2.getTitleno().equals("")){
						params.put("refno", f2.getTitleno());
						Tbcollateralmain row = (Tbcollateralmain) dbServiceCOOP.executeUniqueHQLQueryMaxResultOne
								("FROM Tbcollateralmain WHERE referenceno=:refno", params);
						Tbcollateralrel row2 = (Tbcollateralrel) dbServiceCOOP.executeUniqueHQLQueryMaxResultOne
								("FROM Tbcollateralrel WHERE referenceno=:refno", params);
						if(row!=null && row2!=null){
							// update record
							flag = "success";
						}else if(row==null && row2==null){
							
							ApplicationNoGenerator no = new ApplicationNoGenerator();
							String a = no.generateCollateralNo("COLLATERAL", f2.getCollateralType());
							
							// create new record
							Tbcollateralmain main = new Tbcollateralmain();
							main.setCollateralid(a);
							main.setCollateraltype(f2.getCollateralType());
							main.setReferenceno(f2.getTitleno());
							main.setCollateralstatus("0"); // In-active
							main.setRegisteredowner(f2.getRegisteredowner());
							main.setDateoflastappraisal(f2.getDateOfLastAppraisal());
							main.setLastappraisedvalue(f2.getLastAppraisedValue());
							main.setLastfairmarketvalue(f2.getLastFairMarketValue());
							if(dbServiceCOOP.save(main)){
								System.out.println("---------- TABLE MAIN SAVED ---------- !!!");
								Tbcollateralrel rel = new Tbcollateralrel();
								rel.setCollateralid(a);
								rel.setReferenceno(f2.getTitleno());
								rel.setRegisteredowner(f2.getRegisteredowner());
								rel.setTitleno(f2.getTitleno());
								rel.setLotno(f2.getLotno());
								rel.setPropertyaddress(f2.getPropertyAddress());
								rel.setArea(f2.getArea());
								rel.setPrevtitleno(f2.getPrevTitleNo());
								rel.setPrevowner(f2.getPrevOwner());
								rel.setPropertyvalue(f2.getPropertyValue());
								rel.setHousetype(f2.getHouseType());
								rel.setBlockno(f2.getBlockNo());
								rel.setBldgname(f2.getBldgName());
								rel.setUnitno(f2.getUnitNo());
								rel.setUnittype(f2.getUnitType());
								rel.setRemarks(f2.getRemarks());
								rel.setPropertytype(f2.getPropertyType());
								if(dbServiceCOOP.save(rel)){
									flag = "success";
									System.out.println("---------- TABLE REAL ESTATE SAVED ---------- !!!");
								}
							}
						}
					}
				}
			}else{
				System.out.println("---------- REAL ESTATE FORM IS EMPTY ---------- !!!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}



}
