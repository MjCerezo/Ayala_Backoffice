package com.etel.glentries;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.coopdb.data.Tbaccountingentries;
import com.coopdb.data.Tbcoa;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbglentries;
import com.coopdb.data.Tbglentriesperproduct;
import com.coopdb.data.Tbglmatrix;
import com.coopdb.data.Tbglmatrixperprod;
import com.coopdb.data.Tbloanfin;
import com.coopdb.data.Tblogs;
import com.coopdb.data.Tblstapp;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.glentries.forms.GLEntriesForm;
import com.etel.glentries.forms.GLEntriesPerProductForm;
import com.etel.glentries.forms.JournalEntriesForm;
import com.etel.util.DateTimeUtil;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;

public class GLEntriesServiceImpl implements GLEntriesService {

	private DBService DBServiceImpl = new DBServiceImpl();
	private Map<String, Object> param = HQLUtil.getMap();

	@SuppressWarnings("unchecked")
	@Override
	public List<GLEntriesPerProductForm> glentriesperprod(String prodcode) {
		// TODO Auto-generated method stub
		List<GLEntriesPerProductForm> form = new ArrayList<GLEntriesPerProductForm>();
		List<Tbglentriesperproduct> glentrieslist = new ArrayList<Tbglentriesperproduct>();
		param.put("prodcode", prodcode);
		try {
			glentrieslist = (List<Tbglentriesperproduct>) DBServiceImpl.executeListHQLQuery(
					"FROM Tbglentriesperproduct where prodcode=:prodcode ORDER BY txoper DESC", param);
			if (glentrieslist != null) {
				for (Tbglentriesperproduct glentry : glentrieslist) {
					GLEntriesPerProductForm glform = new GLEntriesPerProductForm();

					glform.setProdcode(prodcode);
					glform.setTxcode(glentry.getId().getTxcode());

					param.put("txtype", glentry.getId().getTxcode());
					Tbcodetable transtype = (Tbcodetable) DBServiceImpl.executeUniqueHQLQuery(
							"FROM Tbcodetable WHERE codename='TXCODE' AND codevalue=:txtype", param);
					glform.setTxtype(transtype.getDesc1());
					glform.setTxmode(glentry.getTxmode());
					param.put("txmode", glentry.getTxmode());
					Tbcodetable transmode = (Tbcodetable) DBServiceImpl.executeUniqueHQLQuery(
							"FROM Tbcodetable WHERE codename='LOANDISPOSITION' AND codevalue=:txmode", param);
					if (transmode != null)
						glform.setTxmodedesc(transmode.getDesc1());
					glform.setGlacctno(glentry.getId().getGlacctno());
					glform.setGlline2(glentry.getGlline2());
					glform.setGlline1(glentry.getGlline1());
					glform.setGlline(glentry.getId().getGlline());
					Tbcoa coa = new Tbcoa();
					param.put("accountno", glentry.getId().getGlacctno());
					coa = (Tbcoa) DBServiceImpl.executeUniqueHQLQuery("FROM Tbcoa WHERE accountno=:accountno", param);
					if (glentry.getTxoper().equals("D")) {
						glform.setDebit(coa.getAcctdesc());
					} else {
						glform.setCredit(coa.getAcctdesc());
					}
					form.add(glform);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return form;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcoa> getGLAccounts() {
		// TODO Auto-generated method stub
		List<Tbcoa> glacctlist = new ArrayList<Tbcoa>();

		try {
			glacctlist = (List<Tbcoa>) DBServiceImpl.executeListHQLQuery("FROM Tbcoa", param);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return glacctlist;
	}

	@Override
	public String saveGLperprod(Tbglentriesperproduct glentry) {
		// TODO Auto-generated method stub
		String result = "Failed";
		try {
			DBServiceImpl.saveOrUpdate(glentry);
			result = "Success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String deleteGL(Tbglentriesperproduct glentry) {
		// TODO Auto-generated method stub
		String result = "Failed";
		try {
			DBServiceImpl.delete(glentry);
			result = "Success";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbglentries> generateGLEntries(String pnno) {
		// TODO Auto-generated method stub
		List<Tbglentries> entries = new ArrayList<Tbglentries>();
		param.put("pnno", pnno);
		try {
//			entries = (List<Tbglentries>) DBServiceImpl.execStoredProc("exec SP_GL_ENTRIES_LMS GETDATE(),:pnno"
//					, param, Tbglentries.class, 1, null);
			entries = (List<Tbglentries>) DBServiceImpl.execStoredProc("EXEC sp_GenerateGLEntries GETDATE(),:pnno",
					param, Tbglentries.class, 1, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return entries;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<GLEntriesForm> getGLEntriesByPnnoAndTxCode(String pnno, String txcode, String appno,
			Boolean islmstransaction, Boolean save) {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		List<GLEntriesForm> glentries = new ArrayList<GLEntriesForm>();
		param.put("pnno", pnno);
		param.put("txcode", txcode);
		param.put("appno", appno);
		param.put("islmstransaction", islmstransaction == null ? false : islmstransaction);
		save = save == null ? false : true;
		param.put("save", save);
		List<Tbglentries> entries = new ArrayList<Tbglentries>();
		try {
			// added line 07.03.19 wel
			// BOOKING TRANSACTION
			if (txcode.equals("10")) {
				Tblstapp lstapp = (Tblstapp) dbsrvc.executeUniqueHQLQuery("FROM Tblstapp WHERE appno=:appno", param);
				//MAR
				if (lstapp.getApplicationstatus() == 6) {
					dbsrvc.executeUpdate("DELETE FROM Tbglentries WHERE accountno=:pnno AND txseqno=:appno", param);
//				entries = (List<Tbglentries>) DBServiceImpl.execStoredProc("exec SP_GL_ENTRIES_LMS '06-18-2019',:pnno", param, Tbglentries.class, 1, null);
//				entries = (List<Tbglentries>) DBServiceImpl.execStoredProc("exec sp_GenerateGLEntries '',:pnno,0,:txcode", param, Tbglentries.class, 1, null);

					entries = (List<Tbglentries>) dbsrvc.execStoredProc(
							"EXEC sp_GenerateGLEntries @rundate = '', @pnno =:pnno ,@save = :save, @txcode =:txcode, @txrefno=:appno, @islmstransaction=:islmstransaction",
							param, Tbglentries.class, 1, null);
				} else {
					entries = (List<Tbglentries>) dbsrvc.executeListHQLQuery("FROM Tbglentries WHERE txseqno =:appno",
							param);
				}
			} else {
				// LMS TRANSACTION
				Tbloanfin loanfin = (Tbloanfin) dbsrvc.executeUniqueHQLQuery("FROM Tbloanfin WHERE txrefno=:appno",
						param);
				if (loanfin.getTxstatus().equals("4") || loanfin.getTxstatus().equals("9")
						|| loanfin.getTxstatus().equals("10") || loanfin.getTxstatus().equals("P")) {
					dbsrvc.executeUpdate("DELETE FROM Tbglentries WHERE accountno=:pnno AND txseqno=:appno", param);
					entries = (List<Tbglentries>) dbsrvc.execStoredProc(
							"EXEC sp_GenerateGLEntries @rundate = '', @pnno =:pnno ,@save = :save, @txcode =:txcode, @txrefno=:appno, @islmstransaction=:islmstransaction",
							param, Tbglentries.class, 1, null);
				} else {
					entries = (List<Tbglentries>) dbsrvc.executeListHQLQuery("FROM Tbglentries WHERE txseqno =:appno",
							param);

				}
			}

			if (entries != null) {
				for (Tbglentries gl : entries) {
					GLEntriesForm form = new GLEntriesForm();
					form.setGlaccountno(gl.getGlsl());
					if (gl.getGlsl() != null) {
						param.put("glcode", gl.getGlsl());
						Tbcoa coa = (Tbcoa) dbsrvc.executeUniqueHQLQuery("FROM Tbcoa WHERE accountno=:glcode", param);
						if (coa != null) {
							form.setGldesc(coa.getAcctdesc());
						}
					}
					form.setDebit(gl.getDebit());
					form.setCredit(gl.getCredit());
					glentries.add(form);
//					if (save)
//						DBServiceImpl.saveOrUpdate(gl);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return glentries;
	}

//	@SuppressWarnings({ "unchecked" })
//	@Override
//	public List<GLEntriesForm> getGLEntriesByPnnoAndTxCode(String pnno, String txcode, String appno,
//			Boolean islmstransaction, Boolean save) {
//		// TODO Auto-generated method stub
//		List<GLEntriesForm> glentries = new ArrayList<GLEntriesForm>();
//		param.put("pnno", pnno);
//		param.put("txcode", txcode);
//		param.put("appno", appno);
//		param.put("islmstransaction", islmstransaction == null ? false : islmstransaction);
//		save = save == null ? false : true;
//		param.put("save", save);
//		try {
//			// added line 07.03.19 wel
//
//			DBServiceImpl.executeUpdate("DELETE FROM Tbglentries WHERE accountno=:pnno AND txseqno=:appno", param);
//			List<Tbglentries> entries = new ArrayList<Tbglentries>();
////			entries = (List<Tbglentries>) DBServiceImpl.execStoredProc("exec SP_GL_ENTRIES_LMS '06-18-2019',:pnno", param, Tbglentries.class, 1, null);
////			entries = (List<Tbglentries>) DBServiceImpl.execStoredProc("exec sp_GenerateGLEntries '',:pnno,0,:txcode", param, Tbglentries.class, 1, null);
//
//			entries = (List<Tbglentries>) DBServiceImpl.execStoredProc(
//					"EXEC sp_GenerateGLEntries @rundate = '', @pnno =:pnno ,@save = :save, @txcode =:txcode, @txrefno=:appno, @islmstransaction=:islmstransaction",
//					param, Tbglentries.class, 1, null);
//			if (entries != null) {
//				for (Tbglentries gl : entries) {
//					GLEntriesForm form = new GLEntriesForm();
//					form.setGlaccountno(gl.getGlsl());
//					if (gl.getGlsl() != null) {
//						param.put("glcode", gl.getGlsl());
//						Tbcoa coa = (Tbcoa) DBServiceImpl.executeUniqueHQLQuery("FROM Tbcoa WHERE accountno=:glcode",
//								param);
//						if (coa != null) {
//							form.setGldesc(coa.getAcctdesc());
//						}
//					}
//					form.setDebit(gl.getDebit());
//					form.setCredit(gl.getCredit());
//					glentries.add(form);
////					if (save)
////						DBServiceImpl.saveOrUpdate(gl);
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return glentries;
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GLEntriesForm> getGLEntries(String pnno, String txcode) {
		// TODO Auto-generated method stub
		List<GLEntriesForm> glentries = new ArrayList<GLEntriesForm>();
		try {
			if (pnno == null) {
				System.out.println(">>>>Get GLEntries PN No. parameter is required!");
			} else {
				param.put("pnno", pnno);
				String strQuery = "FROM Tbglentries WHERE accountno=:pnno";

				if (txcode != null) {
					param.put("txcode", txcode);
					strQuery += " AND txcode=:txcode";
				}

				List<Tbglentries> entries = (List<Tbglentries>) DBServiceImpl.executeListHQLQuery(strQuery, param);
				if (entries != null && !entries.isEmpty()) {
					for (Tbglentries gl : entries) {
						GLEntriesForm form = new GLEntriesForm();
						form.setGlaccountno(gl.getGlsl());
						if (gl.getGlsl() != null) {
							param.put("glcode", gl.getGlsl());
							Tbcoa coa = (Tbcoa) DBServiceImpl
									.executeUniqueHQLQuery("FROM Tbcoa WHERE accountno=:glcode", param);
							if (coa != null) {
								form.setGldesc(coa.getAcctdesc());
							}
						}
						form.setDebit(gl.getDebit());
						form.setCredit(gl.getCredit());
						glentries.add(form);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return glentries;
	}

	@Override
	public String saveOrDeleteAccountingEntries(Tbaccountingentries acctentries, String action) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			if (acctentries != null) {
				if (acctentries.getTxcode() != null && acctentries.getGllineno() != null) {

					if (action.equals("delete")) {
						if (DBServiceImpl.delete(acctentries)) {
							flag = "success";
						}
						return flag;
					}
					if (acctentries.getId() != null) {
						param.put("id", acctentries.getId());
						Tbaccountingentries ae = (Tbaccountingentries) DBServiceImpl
								.executeUniqueHQLQuery("FROM Tbaccountingentries WHERE id=:id", param);
						if (ae == null) {
							acctentries.setCreatedby(UserUtil.securityService.getUserName());
							acctentries.setDatecreated(new Date());
							if (DBServiceImpl.save(acctentries)) {
								flag = "success";
							}
						} else {
							acctentries.setCreatedby(ae.getCreatedby());
							acctentries.setDatecreated(ae.getDatecreated());
							acctentries.setUpdatedby(UserUtil.securityService.getUserName());
							acctentries.setDateupdated(new Date());
							if (DBServiceImpl.saveOrUpdate(acctentries)) {
								flag = "success";
							}
						}
					} else {
						acctentries.setCreatedby(UserUtil.securityService.getUserName());
						acctentries.setDatecreated(new Date());
						if (DBServiceImpl.save(acctentries)) {
							flag = "success";
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbaccountingentries> getListOfAccountingEntries(String txcode) {
		// TODO Auto-generated method stub
		List<Tbaccountingentries> list = new ArrayList<Tbaccountingentries>();
		try {
			if (txcode != null) {
				param.put("txcode", txcode);
				list = (List<Tbaccountingentries>) DBServiceImpl
						.executeListHQLQuery("FROM Tbaccountingentries WHERE txcode=:txcode order by id", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveOrDeleteGLMatrixPerProd(Tbglmatrixperprod glmatrixperprod, String action) {
		// TODO Auto-generated method stub
		String flag = "failed";
		try {
			String username = UserUtil.securityService.getUserName();
			if (glmatrixperprod != null && glmatrixperprod.getId() != null) {
				if (glmatrixperprod.getId().getProductcode() != null) {
					param.put("prodcode", glmatrixperprod.getId().getProductcode());

					// Delete
					if (action.equals("delete")) {
						if (DBServiceImpl.delete(glmatrixperprod)) {
							flag = "success";
						}
						return flag;
					}

					// Re-generate
					if (action.equals("regenerate")) {
						// Delete GL Matrix per prod
						DBServiceImpl.executeUpdate(
								"DELETE FROM TBGLMATRIXPERPROD WHERE productcode=:prodcode AND gllineno NOT IN (SELECT gllineno FROM TBGLMATRIX)",
								param);

						// Insert Default GL Matrix per prod
						DBServiceImpl.executeUpdate(
								"INSERT INTO TBGLMATRIXPERPROD(glline1, gllinedesc1, glline2, gllinedesc2, gllineno, productcode, createdby, datecreated)"
										+ "SELECT glline1, gllinedesc1, glline2, gllinedesc2, gllineno,'"
										+ glmatrixperprod.getId().getProductcode() + "','" + username + "', GETDATE() "
										+ "FROM TBGLMATRIX WHERE gllineno NOT IN (SELECT gllineno FROM TBGLMATRIXPERPROD WHERE productcode='"
										+ glmatrixperprod.getId().getProductcode() + "')",
								param);

						return "success";
					}

					if (glmatrixperprod.getId().getGllineno() != null) {
						param.put("gllineno", glmatrixperprod.getId().getGllineno());
						Tbglmatrixperprod gmp = (Tbglmatrixperprod) DBServiceImpl.executeUniqueHQLQuery(
								"FROM Tbglmatrixperprod WHERE id.productcode=:prodcode AND id.gllineno=:gllineno",
								param);
						if (gmp == null) {
							glmatrixperprod.setCreatedby(username);
							glmatrixperprod.setDatecreated(new Date());
							if (DBServiceImpl.save(glmatrixperprod)) {
								flag = "success";
							}
						} else {
							glmatrixperprod.setCreatedby(gmp.getCreatedby());
							glmatrixperprod.setDatecreated(gmp.getDatecreated());
							glmatrixperprod.setUpdatedby(username);
							glmatrixperprod.setDateupdated(new Date());
							if (DBServiceImpl.saveOrUpdate(glmatrixperprod)) {
								flag = "success";
							}
						}
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbglmatrixperprod> getListOfGLMatrixPerProd(String prodcode) {
		// TODO Auto-generated method stub
		List<Tbglmatrixperprod> list = new ArrayList<Tbglmatrixperprod>();
		try {
			if (prodcode != null) {
				param.put("prodcode", prodcode);
				list = (List<Tbglmatrixperprod>) DBServiceImpl
						.executeListHQLQuery("FROM Tbglmatrixperprod WHERE id.productcode=:prodcode", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// Ced 08192021
	@SuppressWarnings("unchecked")
	@Override
	public String fileHandOff(Date businessdate, Date enddate) {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		businessdate = DateTimeUtil.dtRunDateEndDateD(businessdate);
		param.put("businessdate", businessdate);
		param.put("enddate", enddate);
		List<Tbglentries> vouchers = new ArrayList<Tbglentries>();
		List<Tbglentries> entries = new ArrayList<Tbglentries>();
		String filename = "/JV_" + DateTimeUtil.convertDateToString(businessdate, DateTimeUtil.DATE_FORMAT_YYYYMMDD)
				+ "_ " + DateTimeUtil.convertDateToString(new Date(), DateTimeUtil.DATE_FORMAT_HHMM) + ".xlsx";
		Tblogs log = new Tblogs();
		log.setCurrentdate(businessdate);
		log.setDescription("Start of GL File Generation");
		log.setErrordescription("");
		log.setEventdate(new Date());
		log.setEventname("START");
		log.setModulename("GL FILE GENERATION");
		log.setNextdate(enddate);
		log.setUniquekey(filename.substring(1, filename.length()));
		dbsrvc.save(log);
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			CreationHelper createHelper = workbook.getCreationHelper();

			// Create Sheet for vouchers
			XSSFSheet sheetVoucher = workbook.createSheet("Journal Vouchers");

			// Create and Apply Cell Style for formatting Date
			CellStyle headerCellStyle = workbook.createCellStyle();
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
			headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);

			// Create Cell Style for formatting Date
			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("MM-dd-yyyy"));
			dateCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);

			// Create Cell Style for formatting Amount
			CellStyle amountCellStyle = workbook.createCellStyle();
			amountCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("#,##0.00"));
			amountCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);

			// Create Cell Style for Right Align
			CellStyle rightAlignCellStyle = workbook.createCellStyle();
			rightAlignCellStyle.setAlignment(CellStyle.ALIGN_RIGHT);

			// Set up header for first row
			String[] columnVoucher = { "Document No.", "Branch", "Document Series", "Posting Date", "Due Date",
					"Reference 1", "Reference 2", "Remarks" };
			Row headerVoucher = sheetVoucher.createRow(0);
			for (int i = 0; i < columnVoucher.length; i++) {
				Cell cell = headerVoucher.createCell(i);
				cell.setCellValue(columnVoucher[i]);
				cell.setCellStyle(headerCellStyle);
			}

			// Get List of Vouchers by Date
			vouchers = (List<Tbglentries>) dbsrvc.execStoredProc(
					"SELECT case when glbranch is null or glbranch = '' then c.txbr else glbranch end as glbranch,"
							+ "a.txseqno,b.desc1 as txcode,a.txdate,a.accountno,MAX(a.txamt) as txamt, d.fullname as txglbr "
							+ "FROM Tbglentries a left join Tbcodetable b on a.txcode = b.codevalue "
							+ "left join TBLNTXJRNL c on a.txseqno = c.txrefno "
							+ "left join Tbloans d on a.accountno = d.accountno "
							+ "WHERE b.codename='TXCODE' AND CAST(a.txdate as date) >= CAST(:businessdate as date) "
							+ "AND CAST(a.txdate as date) < CAST(:enddate as date) and c.txrefno is not null "
							+ "GROUP BY a.glbranch,a.txseqno,a.txcode,a.Txdate,a.accountno,b.desc1,c.txbr,d.fullname ORDER BY txseqno",
					param, Tbglentries.class, 1, null);
			// Get List of Vouchers by Date CASA
			vouchers.addAll((List<Tbglentries>) dbsrvc.execStoredProc(
					"exec sp_GenerateGLEntriesCASA :businessdate, :enddate, '', '','',3,1", param, Tbglentries.class, 1,
					null));

			// Insert the list of vouchers into excel
			for (int i = 0; i < vouchers.size(); i++) {
				Row vRow = sheetVoucher.createRow(i + 1);
				vRow.createCell(0).setCellValue(vouchers.get(i).getTxseqno());
				vRow.createCell(1).setCellValue(vouchers.get(i).getGlbranch());
				vRow.createCell(2).setCellValue("Manual");
				vRow.createCell(3)
						.setCellValue(DateTimeUtil.convertDateToString(vouchers.get(i).getTxdate(), "MM/dd/yyyy"));
				vRow.createCell(4)
						.setCellValue(DateTimeUtil.convertDateToString(vouchers.get(i).getTxdate(), "MM/dd/yyyy"));
				vRow.createCell(5).setCellValue(vouchers.get(i).getAccountno());
				vRow.createCell(6).setCellValue(vouchers.get(i).getTxglbr());
				vRow.createCell(7).setCellValue(vouchers.get(i).getTxcode());
				// Apply Styles
				vRow.getCell(1).setCellStyle(rightAlignCellStyle);
//				vRow.getCell(3).setCellStyle(rightAlignCellStyle);
			}

			// Resize all columns to fit the content size
			for (int i = 0; i < columnVoucher.length; i++) {
				sheetVoucher.autoSizeColumn(i);
			}

			// Create Sheet for entries
			XSSFSheet sheetEntries = workbook.createSheet("Journal Voucher - Items");

			// Set up header for first row
			String[] columnEntries = { "Document No.", "Branch", "Item No.", "Item Name", "Currency", "Debit", "Credit",
					"Is Bank Account", "Country", "Bank", "Bank Branch", "Bank Account No." };
			Row headerEntries = sheetEntries.createRow(0);
			for (int i = 0; i < columnEntries.length; i++) {
				Cell cell = headerEntries.createCell(i);
				cell.setCellValue(columnEntries[i]);
				cell.setCellStyle(headerCellStyle);
			}

			// Get List of Entries by Date
			entries = (List<Tbglentries>) dbsrvc
					.execStoredProc("SELECT a.txseqno,glsl,c.acctdesc as txbr,a.accountno,debit,credit, "
							+ "case when glbranch is null or glbranch = '' then d.txbr else glbranch end as glbranch "
							+ "FROM Tbglentries a left join Tbcodetable b on a.txcode = b.codevalue "
							+ "left join TBCOA c on a.glsl = c.accountno "
							+ "left join TBLNTXJRNL d on a.txseqno = d.txrefno WHERE b.codename='TXCODE' "
							+ "AND CAST(a.txdate as date) >= CAST(:businessdate as date) "
							+ "AND CAST(a.txdate as date) < CAST(:enddate as date) "
							+ "and d.txrefno is not null ORDER BY a.txseqno", param, Tbglentries.class, 1, null);

			// Get List of Entries by Date CASA
			entries.addAll((List<Tbglentries>) dbsrvc.execStoredProc(
					"exec sp_GenerateGLEntriesCASA :businessdate, :enddate, '', '','',0,1", param, Tbglentries.class, 1,
					null));

			// Insert the list of vouchers into excel
			for (int i = 0; i < entries.size(); i++) {
				Row eRow = sheetEntries.createRow(i + 1);
				eRow.createCell(0).setCellValue(entries.get(i).getTxseqno());
				eRow.createCell(1).setCellValue(entries.get(i).getGlbranch());
				eRow.createCell(2).setCellValue(entries.get(i).getGlsl());
				// Used txbr as substitute for gl account name
				eRow.createCell(3).setCellValue(entries.get(i).getTxbr());
				eRow.createCell(4).setCellValue("PHP");
				eRow.createCell(5).setCellValue(entries.get(i).getDebit().toString());
				eRow.createCell(6).setCellValue(entries.get(i).getCredit().toString());
				eRow.createCell(7).setCellValue("No");
				eRow.createCell(8).setCellValue("");
				eRow.createCell(9).setCellValue("");
				eRow.createCell(10).setCellValue("");
				eRow.createCell(11).setCellValue("");

				// Apply Cell Style
				eRow.getCell(1).setCellStyle(rightAlignCellStyle);
				eRow.getCell(5).setCellStyle(rightAlignCellStyle);
				eRow.getCell(6).setCellStyle(rightAlignCellStyle);
			}

			// Resize all columns to fit the content size
			for (int i = 0; i < columnEntries.length; i++) {
				sheetEntries.autoSizeColumn(i);
			}

			// File Directory
			String uploadDir = RuntimeAccess.getInstance().getSession().getServletContext()
					.getRealPath("resources/data/gl");
			File dir = new File(uploadDir);
			if (!dir.exists())
				dir.mkdirs();
			// Write the output to a file
			FileOutputStream fileOut = new FileOutputStream(uploadDir + filename);
			workbook.write(fileOut);
			fileOut.close();

			// Closing the workbook
			workbook.close();
			log.setDescription("Total No. Journal Vouchers : " + vouchers.size());
			log.setEventdate(new Date());
			log.setEventname("INFO");
			dbsrvc.save(log);
			log.setDescription("Total No. of Voucher Items : " + entries.size());
			log.setEventdate(new Date());
			log.setEventname("INFO");
			dbsrvc.save(log);
			log.setDescription("End of GL File Generation");
			log.setEventdate(new Date());
			log.setEventname("END");
			dbsrvc.save(log);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "resources/data/gl" + filename;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbglmatrix> getGLMatrix() {
		List<Tbglmatrix> gl = new ArrayList<Tbglmatrix>();
		DBService dbsrvc = new DBServiceImpl();
		try {
			gl = (List<Tbglmatrix>) dbsrvc.execStoredProc(
					"SELECT glline1, gllinedesc1 FROM Tbglmatrix GROUP BY glline1, gllinedesc1", param,
					Tbglmatrix.class, 1, null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return gl;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<JournalEntriesForm> listJournalEntries(String branch, Date from, Date to, String accounttype) {
		List<JournalEntriesForm> list = new ArrayList<JournalEntriesForm>();
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		param.put("branch", branch);
		param.put("from", from);
		param.put("to", to);
		param.put("accounttype", accounttype);
		try {
			list = (List<JournalEntriesForm>) dbsrvc.execStoredProc(
					"EXEC sp_LMS_journalEntriesReport  :from, :to, :accounttype, :branch, ''", param,
					JournalEntriesForm.class, 1, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
