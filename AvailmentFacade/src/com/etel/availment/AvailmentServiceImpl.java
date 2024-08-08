package com.etel.availment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.etel.availmentforms.ProductForm;
import com.etel.collateralforms.TbCollateralMainForm;
import com.etel.collateralforms.TbCollateralMainFormGroup;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
//import com.coopdb.data.Tbcfcoobligor;
//import com.coopdb.data.Tbcfdetails;
import com.coopdb.data.Tbdocsperapplication;
import com.coopdb.data.Tbdocsperproduct;
import com.coopdb.data.Tblstcomakers;

public class AvailmentServiceImpl implements AvailmentService {

	private DBService dbServiceLOS = new DBServiceImpl();
	//private DBService dbServiceCIF = new DBServiceImplCIF();
	private Map<String, Object> params = HQLUtil.getMap();
	String username = UserUtil.securityService.getUserName();
	
	/**
	 * --List Main Line by cifno--
	 * @author Wel (Sep.10.2018)
	 * @return form = {@link Tbcfdetails}
	 * */	
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Tbcfdetails> listMainLine(String cifno) {
//		List<Tbcfdetails> list = new ArrayList<Tbcfdetails>();
//		try {
//			if(cifno!=null){
//				params.put("cifno", cifno);
//				
//				// Check if Coobligagor
//				Tbcfcoobligor coob = (Tbcfcoobligor) dbServiceLOS.executeUniqueHQLQueryMaxResultOne
//						("FROM Tbcfcoobligor WHERE cfcifno=:cifno", params);
//				if(coob!=null){
//					// Get the Main Line
//					if(coob.getCfrefno()!=null){
//						params.put("cfrefno", coob.getCfrefno());
//						list = (List<Tbcfdetails>) dbServiceLOS.executeListHQLQuery						     // Main Level        Approved
//								("FROM Tbcfdetails a WHERE ((a.id.cfrefno=:cfrefno) OR (a.cifno=:cifno)) AND a.id.cflevel='0' AND a.cfstatus='1'", params);
//					}
//				}else{
//				// If not, the Main Line
//					list = (List<Tbcfdetails>) dbServiceLOS.executeListHQLQuery
//							("FROM Tbcfdetails a WHERE a.cifno=:cifno AND a.id.cflevel='0' AND a.cfstatus='1'", params);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}

	/**
	 * --List Coobligagor--
	 * @author Wel (Sep.10.2018)
	 * @return form = {@link Tbcfcoobligor}
	 * */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Tbcfcoobligor> listCoobligagor(String cfrefno, String cfrefnoconcat, Boolean isChangeSetup) {
//		List<Tbcfcoobligor> list = new ArrayList<Tbcfcoobligor>();
//		StringBuilder sb = new StringBuilder();
//		try {
//			if(cfrefno!=null){
//				params.put("cfrefno", cfrefno);							 // Approved	
//				sb.append("FROM Tbcfcoobligor WHERE cfrefno=:cfrefno AND cfstatus='1'"); // List All
//				if(cfrefnoconcat!=null){
//					if(isChangeSetup){
//						sb.append(" AND cfrefnoconcat NOT IN ('"+cfrefnoconcat+"')"); // List All except Main Line
//					}else{
//						params.put("cfrefnoconcat", cfrefnoconcat);
//						sb.append(" AND cfrefnoconcat=:cfrefnoconcat"); // Get Main Line
//					}
//				}
//				list = (List<Tbcfcoobligor>) dbServiceLOS.executeListHQLQuery(sb.toString(), params);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
	
	/**
	 * --List Sub-Facility--
	 * @author Wel (Sep.17.2018)
	 * @return form = {@link Tbcfcoobligor}
	 * */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Tbcfdetails> listSubFacility(String cfrefno, String cfrefnoconcat, Boolean isChangeSetup) {
//		List<Tbcfdetails> list = new ArrayList<Tbcfdetails>();
//		StringBuilder sb = new StringBuilder();
//		try {
//			if(cfrefno!=null){
//				params.put("cfrefno", cfrefno);								  // Sub-Facility	   Approved				
//				sb.append("FROM Tbcfdetails a WHERE a.id.cfrefno=:cfrefno AND a.id.cflevel='1' AND a.cfstatus='1'"); // List All
//				
//				if(cfrefnoconcat!=null){
//					if(isChangeSetup){
//						sb.append(" AND cfrefnoconcat NOT IN ('"+cfrefnoconcat+"')"); // List All except Main Line
//					}else{
//						params.put("cfrefnoconcat", cfrefnoconcat);
//						sb.append(" AND cfrefnoconcat=:cfrefnoconcat"); // Get Main Line
//					}
//				}
//				list = (List<Tbcfdetails>) dbServiceLOS.executeListHQLQuery(sb.toString(), params);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
	
	/**
	 * --List Main Line (maxresult)--
	 * @author Wel (Sep.11.2018)
	 * @return form = {@link Tbcfdetails}
	 * */
//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Tbcfdetails> getMainLine(String cifno, String cfrefno, String cfrefnoconcat) {
//		List<Tbcfdetails> list = new ArrayList<Tbcfdetails>();
//		try {
//			if(cifno!=null && cfrefno!=null && cfrefnoconcat!=null){
//				params.put("cifno", cifno);
//				params.put("cfrefno", cfrefno);
//				params.put("cfrefnoconcat", cfrefnoconcat);
//				list = (List<Tbcfdetails>) dbServiceLOS.executeListHQLQueryWitMaxResults        // Approved
//						("FROM Tbcfdetails a WHERE a.cifno=:cifno AND a.id.cfrefno=:cfrefno AND a.id.cflevel='0' AND a.cfrefnoconcat=:cfrefnoconcat", 1, params);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return list;
//	}

	/**
	 * --Save or Update Availment Application--
	 * @author Wel (Sep.12.2018)
	 * @return String = success, otherwise failed
	 * */	
	@Override
	public String saveOrupdateAvailment(String appno, String cfrefno, String cfcode, String cfrefnoconcat) {
		String flag = "failed";
		try {
			if(appno!=null && cfrefno!=null && cfcode!=null && cfrefnoconcat!=null){
				params.put("appno", appno);
				String q = "UPDATE Tblstapp SET cfrefno ='"+cfrefno+"',"
						+ "typefacility ='"+cfcode+"',"
						+ "cfrefnoconcat ='"+cfrefnoconcat+"'"
						+ " WHERE appno=:appno";
				Integer res = dbServiceLOS.executeUpdate(q, params);
				if(res!=null && res ==1){
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * --Get Main Line Of Existing Or On-Process Availment Application--
	 * @author Wel (Sep.12.2018)
	 * @return form = {@link Tbcfdetails}
	 * */
//	@Override
//	public Tbcfdetails getMainLineOfAvailmentApp(String refno, String cifno, String cfrefnoconcat) {
//		System.out.println("------------ running getMainLineOfAvailmentApp");
//		Tbcfdetails row = new Tbcfdetails();
//		try {
//			if(refno!=null && cifno!=null && cfrefnoconcat!=null){
//				params.put("refno", refno);
//				params.put("cifno", cifno);
//				params.put("cfrefnoconcat", cfrefnoconcat);
//				
//				// CIF Number Applied in Availment Application is the Main Line 
//				row = (Tbcfdetails) dbServiceLOS.executeUniqueHQLQueryMaxResultOne
//						("FROM Tbcfdetails a WHERE a.id.cfrefno=:refno AND a.id.cflevel='0' AND a.cifno=:cifno AND a.cfstatus='1' AND a.cfrefnoconcat=:cfrefnoconcat", params);
//				if(row!=null){
//					System.out.println("------------ Main Line");
//					return row; 
//				}
//				
//				// CIF Number Applied in Availment Application is the Sub-Facility
//				Tbcfdetails sub = (Tbcfdetails) dbServiceLOS.executeUniqueHQLQueryMaxResultOne
//						("FROM Tbcfdetails a WHERE a.id.cfrefno=:refno AND a.id.cflevel='1' AND a.cifno=:cifno AND a.cfrefnoconcat=:cfrefnoconcat", params);
//				if(sub!=null){
//					params.put("cfrefno", sub.getId().getCfrefno());
//					row = (Tbcfdetails) dbServiceLOS.executeUniqueHQLQueryMaxResultOne
//							("FROM Tbcfdetails a WHERE a.id.cfrefno=:cfrefno AND a.id.cflevel='0' AND a.cfstatus='1'", params);
//					if(row!=null){
//						System.out.println("------------ Sub-Facility");
//						return row; 
//					}
//				}
//				
//				// CIF Number Applied in Availment Application is the Coobligagor
//				Tbcfcoobligor coob = (Tbcfcoobligor) dbServiceLOS.executeUniqueHQLQueryMaxResultOne
//						("FROM Tbcfcoobligor WHERE cfrefno=:refno AND cfcifno=:cifno AND cfrefnoconcat=:cfrefnoconcat", params);
//				if(coob!=null){
//					params.put("cfrefno", coob.getCfrefno());
//					row = (Tbcfdetails) dbServiceLOS.executeUniqueHQLQueryMaxResultOne
//							("FROM Tbcfdetails a WHERE a.id.cfrefno=:cfrefno AND a.id.cflevel='0' AND a.cfstatus='1'", params);
//					if(row!=null){
//						System.out.println("------------ Coobligagor");
//						return row; 
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return row;
//	}
	
	/**
	 * --List Surety of Main Line and Availment Application--
	 * @author Wel (Sep.13.2018)
	 * @return form = {@link Tblstcomakers}
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<Tblstcomakers> getSuretyList(String liAppno, String avAppno) {
		List<Tblstcomakers> list = new ArrayList<Tblstcomakers>();
		try {
			if (liAppno != null && avAppno != null) {
				list = (List<Tblstcomakers>) dbServiceLOS.executeListHQLQuery
						("FROM Tblstcomakers c WHERE c.id.appno IN ('"+liAppno+"', '"+avAppno+"') AND c.id.participationcode='SUR'", null);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * --List Single Collateral Per Line And Availment Application--
	 * @author Wel (Sep.13.2018)
	 * @return List<{@link TbCollateralMainForm}> 
	 * */	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbCollateralMainForm> listCollateralSingle(String liAppno, String avAppno, String collateraltype) {
		List<TbCollateralMainForm> list = new ArrayList<TbCollateralMainForm>();
		StringBuilder sb = new StringBuilder();
		try {
			if(liAppno!=null && avAppno!=null){
				sb.append("SELECT a.collateralid,"
						+ " a.referenceno,"
						+ " (SELECT desc1 FROM TBCODETABLE WHERE codename='COLLATERALTYPE' AND codevalue=a.collateraltype) as collateraltype,"
						+ " a.dateoflastappraisal, "
						+ " (SELECT desc1 FROM TBCODETABLE WHERE codename='APPRAISALSTATUS' AND codevalue=a.appraisalstatus) as appraisalstatus,"
						+ " (SELECT desc1 FROM TBCODETABLE WHERE codename='COLLATERALSTATUS' AND codevalue=a.collateralstatus) as collateralstatus"
						+ " FROM TBCOLLATERALMAIN a"
						+ " LEFT JOIN TBLOANCOLLATERAL b ON a.referenceno = b.collateralreferenceno"
						+ " WHERE b.appno IN ('"+liAppno+"', '"+avAppno+"')");
						if(collateraltype!=null){
							sb.append(" AND a.collateraltype ='"+collateraltype+"'");
						}
						sb.append(" ORDER BY a.collateraltype DESC");
				list = (List<TbCollateralMainForm>) dbServiceLOS.execSQLQueryTransformer(sb.toString(), null, TbCollateralMainForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * --List Group Collateral Per Line And Availment Application--
	 * @author Wel (Sep.13.2018)
	 * @return List<{@link TbCollateralMainFormGroup}> 
	 * */	
	@SuppressWarnings("unchecked")
	@Override
	public List<TbCollateralMainFormGroup> listCollateralGroup(String liAppno, String avAppno, String collateraltype){
		List<TbCollateralMainFormGroup> list = new ArrayList<TbCollateralMainFormGroup>();
		StringBuilder sb = new StringBuilder();
		try {
			if(liAppno!=null && avAppno!=null){
				sb.append("SELECT a.groupname, a.groupid,"
						+ " (SELECT desc1 FROM TBCODETABLE WHERE codename='GROUPTYPE' AND codevalue=a.grouptype) as grouptype,"
						+ " a.datecreated, (SELECT desc1 FROM TBCODETABLE WHERE codename='COLLATERALSTATUS' AND codevalue=a.groupstatus) as collateralstatus"
						+ " FROM TBCOLLATERALGROUPMAIN a"
						+ " LEFT JOIN TBLOANCOLLATERALGROUP b ON a.groupid = b.groupid");
						sb.append(" WHERE b.appno IN ('"+liAppno+"', '"+avAppno+"')");
						if(collateraltype!=null){
							sb.append(" AND a.collateraltype ='"+collateraltype+"'");
						}
						list = (List<TbCollateralMainFormGroup>) dbServiceLOS.execSQLQueryTransformer(sb.toString(), null, TbCollateralMainFormGroup.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * --Change or Update Availment Details--
	 * @author Wel (Sep.13.2018)
	 * @return String = success, otherwise failed
	 * */
	@Override
	public String changeAvailmentDetails(String appno, String cfrefno, String cifno, String cifname, String cfcode, String cfrefnoconcat) {
		String flag = "failed";
		try {
			if(appno!=null && cfrefno!=null && cifno!=null && cifname!=null && cfrefnoconcat!=null){
				params.put("appno", appno);
				String q = "UPDATE Tblstapp SET cfrefno ='"+cfrefno+"',"
						+ " cifno ='"+cifno+"',"
						+ " cifname ='"+cifname+"',"
						+ " cfrefnoconcat ='"+cfrefnoconcat+"',"
						+ " typefacility ='"+cfcode+"'"
						+ " WHERE appno=:appno";
				Integer res = dbServiceLOS.executeUpdate(q, params);
				if(res!=null && res ==1){
					flag = "success";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/** 
	 * Get List of LoanProduct
	 * @author Wel (Sep.14.2018)
	 * @return List<{@link ProductForm}>
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductForm> getListOfLoanProduct(String facilitycode) {
		List<ProductForm> form = new ArrayList<ProductForm>();
		try {
			if(facilitycode != null){
				params.put("facilitycode", facilitycode);
					form = (List<ProductForm>) dbServiceLOS.execSQLQueryTransformer
					("SELECT DISTINCT prodcode, facilitycode, productname FROM Tbloanprodpercf  WHERE facilitycode=:facilitycode", params, ProductForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}
	/**
	 * --Refresh Document Checklist Per Application--
	 * @author Wel (Sep.18.2018)
	 * @return String = success otherwise failed
	 * */
	@SuppressWarnings("unchecked")
	@Override
	public String refreshDocChecklistPerApp(String appno) {
		String flag = "failed";
		try {
			if(appno!=null){
				params.put("appno", appno);
				String loanproduct = (String) dbServiceLOS.execSQLQueryTransformer
						("SELECT loanproduct FROM Tblstapp WHERE appno=:appno", params, null, 0);
				String cifno = (String) dbServiceLOS.execSQLQueryTransformer
						("SELECT cifno FROM Tblstapp WHERE appno=:appno", params, null, 0);
				if(loanproduct!=null){
					params.put("loanproduct", loanproduct);
					List<Tbdocsperproduct> list  = (List<Tbdocsperproduct>) dbServiceLOS.executeListHQLQuery
							("FROM Tbdocsperproduct WHERE productcode=:loanproduct", params);
					if(list != null && !list.isEmpty()){
						for(Tbdocsperproduct d : list){
							params.put("id", d.getId());
							Tbdocsperapplication docSubmitted = (Tbdocsperapplication) dbServiceLOS
									.executeUniqueHQLQueryMaxResultOne(
											"FROM Tbdocsperapplication WHERE appno=:appno AND docchecklistid=:id",
											params);
							if (docSubmitted != null) {
								// Update existing record
								docSubmitted.setDocumentcode(d.getDocumentcode());
								docSubmitted.setDocumentname(d.getDocumentname());
								docSubmitted.setUploadedby(null);
								docSubmitted.setDateuploaded(new Date());
								dbServiceLOS.saveOrUpdate(docSubmitted);
							}else{
								// Create new record
								Tbdocsperapplication docS = new Tbdocsperapplication();
								docS.setAppno(appno);
								docS.setCifno(cifno);
								docS.setDocchecklistid(d.getId());
								docS.setDocumentcode(d.getDocumentcode());
								docS.setDocumentname(d.getDocumentname());
								docS.setUploadedby(username);
								dbServiceLOS.save(docS);
							}
						}
					}
					//Delete from Tbdocsperapplication if not in default Tbdocsperproduct
					dbServiceLOS.executeUpdate("DELETE FROM Tbdocsperapplication WHERE docchecklistid NOT IN(SELECT id FROM Tbdocsperproduct WHERE productcode='"+loanproduct+"')", params);
				}else{
					System.out.println("----------- No Loan Product");
				}
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * -- Save or Update Doc Checklist per Application--
	 * @author Wel (Sep.18.2018)
	 * @return String = success otherwise failed
	 * */
	@Override
	public String saveOrUpdateDocsPerApp(Tbdocsperapplication docsperapp) {
		String flag = "failed";
		try {
			
			if (dbServiceLOS.saveOrUpdate(docsperapp)) {
				flag = "success";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	




}
