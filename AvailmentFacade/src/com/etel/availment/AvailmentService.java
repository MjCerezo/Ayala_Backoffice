package com.etel.availment;

import java.util.List;

import com.etel.availmentforms.ProductForm;
import com.etel.collateralforms.TbCollateralMainForm;
import com.etel.collateralforms.TbCollateralMainFormGroup;
//import com.coopdb.data.Tbcfcoobligor;
//import com.coopdb.data.Tbcfdetails;
import com.coopdb.data.Tbdocsperapplication;
import com.coopdb.data.Tblstcomakers;

public interface AvailmentService {

	/**
	 * --List Main Line by cifno--
	 * @author Wel (Sep.10.2018)
	 * @return form = {@link Tbcfdetails}
	 * */
//	List<Tbcfdetails> listMainLine(String cifno);
	/**
	 * --List Coobligagor--
	 * @author Wel (Sep.10.2018)
	 * @return form = {@link Tbcfcoobligor}
	 * */
//	List<Tbcfcoobligor> listCoobligagor(String cfrefno, String cfrefnoconcat, Boolean isChangeSetup);
	/**
	 * --List Sub-Facility--
	 * @author Wel (Sep.17.2018)
	 * @return form = {@link Tbcfcoobligor}
	 * */	
//	List<Tbcfdetails> listSubFacility(String cfrefno, String cfrefnoconcat, Boolean isChangeSetup);		
	/**
	 * --List Main Line (maxresult)--
	 * @author Wel (Sep.11.2018)
	 * @return form = {@link Tbcfdetails}
	 * */
//	List<Tbcfdetails> getMainLine(String cifno, String cfrefno, String cfrefnoconcat);
	/**
	 * --Save or Update Availment Application--
	 * @author Wel (Sep.12.2018)
	 * @return String = success, otherwise failed
	 * */	
	String saveOrupdateAvailment(String appno, String cfrefno, String cfcode, String cfrefnoconcat);
	/**
	 * --Get Main Line Of Existing Or On-Process Availment Application--
	 * @author Wel (Sep.12.2018)
	 * @return form = {@link Tbcfdetails}
	 * */
//	Tbcfdetails getMainLineOfAvailmentApp(String refno, String cifno, String cfrefnoconcat);
	/**
	 * --List Surety of Main Line and Availment Application--
	 * @author Wel (Sep.13.2018)
	 * @return form = {@link Tblstcomakers}
	 * */
	List<Tblstcomakers> getSuretyList(String liAppno, String avAppno);
	/**
	 * --List Single Collateral Per Line And Availment Application--
	 * @author Wel (Sep.13.2018)
	 * @return List<{@link TbCollateralMainForm}> 
	 * */	
	List<TbCollateralMainForm> listCollateralSingle(String liAppno, String avAppno, String collateraltype);
	/**
	 * --List Group Collateral Per Line And Availment Application--
	 * @author Wel (Sep.13.2018)
	 * @return List<{@link TbCollateralMainFormGroup}> 
	 * */	
	List<TbCollateralMainFormGroup> listCollateralGroup(String liAppno, String avAppno, String collateraltype);
	/**
	 * --Change Availment Details--
	 * @author Wel (Sep.13.2018)
	 * @return String = success, otherwise failed
	 * */
	String changeAvailmentDetails(String appno, String cfrefno, String cifno, String cifname, String cfcode, String cfrefnoconcat);
	/** 
	 * Get List of LoanProduct
	 * @author Wel (Sep.14.2018)
	 * @return List<{@link ProductForm}>
	 * */
	List<ProductForm> getListOfLoanProduct(String facilitycode);
	/**
	 * --Refresh Document Checklist Per Application--
	 * @author Wel (Sep.18.2018)
	 * @return String = success otherwise failed
	 * */
	String refreshDocChecklistPerApp(String appno);
	/**
	 * -- Save or Update Doc Checklist per Application--
	 * @author Wel (Sep.18.2018)
	 * @return String = success otherwise failed
	 * */
	String saveOrUpdateDocsPerApp(Tbdocsperapplication docsperapp);



}
