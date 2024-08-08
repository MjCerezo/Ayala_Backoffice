/**
 * 
 */
package com.etel.glcasa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.coopdb.data.Tbaccountingentriescasa;
import com.coopdb.data.Tbcoa;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbglentries;
import com.coopdb.data.Tbglentriesperproductcasa;
import com.coopdb.data.Tbglmatrixcasa;
import com.coopdb.data.Tbglmatrixperprodcasa;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.glentries.forms.GLEntriesForm;
import com.etel.glentries.forms.GLEntriesPerProductForm;
import com.etel.util.DateTimeUtil;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;

/**
 * @author ETEL-LAPTOP07
 *
 */
public class GLEntriesServiceCASAImpl implements GLEntriesCASAService {

	private DBService DBServiceImpl = new DBServiceImpl();
	private Map<String, Object> param = HQLUtil.getMap();

	@SuppressWarnings("unchecked")
	@Override
	public List<GLEntriesPerProductForm> glentriesperprod(String prodcode) {
		// TODO Auto-generated method stub
		List<GLEntriesPerProductForm> form = new ArrayList<GLEntriesPerProductForm>();
		List<Tbglentriesperproductcasa> glentrieslist = new ArrayList<Tbglentriesperproductcasa>();
		param.put("prodcode", prodcode);
		try {
			glentrieslist = (List<Tbglentriesperproductcasa>) DBServiceImpl.executeListHQLQuery(
					"FROM Tbglentriesperproductcasa where prodcode=:prodcode ORDER BY txoper DESC", param);
			if (glentrieslist != null) {
				for (Tbglentriesperproductcasa glentry : glentrieslist) {
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
	public String saveGLperprod(Tbglentriesperproductcasa glentry) {
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
	public String deleteGL(Tbglentriesperproductcasa glentry) {
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
			Boolean islmstransaction) {
		// TODO Auto-generated method stub
		List<GLEntriesForm> glentries = new ArrayList<GLEntriesForm>();
		param.put("pnno", pnno);
		param.put("txcode", txcode);
		param.put("appno", appno);
		param.put("islmstransaction", islmstransaction == null ? false : islmstransaction);
		try {
			// added line 07.03.19 wel

			DBServiceImpl.executeUpdate("DELETE FROM Tbglentries WHERE accountno=:pnno AND txseqno=:appno", param);
			List<Tbglentries> entries = new ArrayList<Tbglentries>();
//			entries = (List<Tbglentries>) DBServiceImpl.execStoredProc("exec SP_GL_ENTRIES_LMS '06-18-2019',:pnno", param, Tbglentries.class, 1, null);
//			entries = (List<Tbglentries>) DBServiceImpl.execStoredProc("exec sp_GenerateGLEntries '',:pnno,0,:txcode", param, Tbglentries.class, 1, null);

			entries = (List<Tbglentries>) DBServiceImpl.execStoredProc(
					"EXEC sp_GenerateGLEntries @rundate = '', @pnno =:pnno ,@save = 0, @txcode =:txcode, @txrefno=:appno, @islmstransaction=:islmstransaction",
					param, Tbglentries.class, 1, null);
			if (entries != null) {
				for (Tbglentries gl : entries) {
					GLEntriesForm form = new GLEntriesForm();
					form.setGlaccountno(gl.getGlsl());
					if (gl.getGlsl() != null) {
						param.put("glcode", gl.getGlsl());
						Tbcoa coa = (Tbcoa) DBServiceImpl.executeUniqueHQLQuery("FROM Tbcoa WHERE accountno=:glcode",
								param);
						if (coa != null) {
							form.setGldesc(coa.getAcctdesc());
						}
					}
					form.setDebit(gl.getDebit());
					form.setCredit(gl.getCredit());
					glentries.add(form);
					DBServiceImpl.saveOrUpdate(gl);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return glentries;
	}

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
	public String saveOrDeleteAccountingEntries(Tbaccountingentriescasa acctentries, String action) {
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
						Tbaccountingentriescasa ae = (Tbaccountingentriescasa) DBServiceImpl
								.executeUniqueHQLQuery("FROM Tbaccountingentriescasa WHERE id=:id", param);
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
	public List<Tbaccountingentriescasa> getListOfAccountingEntries(String txcode) {
		// TODO Auto-generated method stub
		List<Tbaccountingentriescasa> list = new ArrayList<Tbaccountingentriescasa>();
		try {
			if (txcode != null) {
				param.put("txcode", txcode);
				list = (List<Tbaccountingentriescasa>) DBServiceImpl.executeListHQLQuery(
						"FROM Tbaccountingentriescasa WHERE txcode=:txcode ORDER BY txoper desc,gllineno", param);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public String saveOrDeleteGLMatrixPerProd(Tbglmatrixperprodcasa glmatrixperprod, String action) {
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
//						DBServiceImpl.executeUpdate(
//								"DELETE FROM Tbglmatrixperprodcasa WHERE productcode=:prodcode AND gllineno NOT IN (SELECT gllineno FROM TBGLMATRIXCASA)",
//								param);

						// Insert Default GL Matrix per prod
//						DBServiceImpl.executeUpdate(
//						"INSERT INTO Tbglmatrixperprodcasa(glline1, gllinedesc1, glline2, gllinedesc2, gllineno, productcode, createdby, datecreated)"
//								+ "SELECT glline1, gllinedesc1, glline2, gllinedesc2, gllineno,'"
//								+ glmatrixperprod.getId().getProductcode() + "','" + username + "', GETDATE() "
//								+ "FROM TBGLMATRIXCASA WHERE gllineno NOT IN (SELECT gllineno FROM Tbglmatrixperprodcasa WHERE productcode='"
//								+ glmatrixperprod.getId().getProductcode() + "')",
//						param);
						DBServiceImpl.executeUpdate("EXEC sp_GenerateGLTemplateCASA :prodcode", param);

						return "success";
					}

					if (glmatrixperprod.getId().getGllineno() != null) {
						param.put("gllineno", glmatrixperprod.getId().getGllineno());
						Tbglmatrixperprodcasa gmp = (Tbglmatrixperprodcasa) DBServiceImpl.executeUniqueHQLQuery(
								"FROM Tbglmatrixperprodcasa WHERE id.productcode=:prodcode AND id.gllineno=:gllineno",
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
	public List<Tbglmatrixperprodcasa> getListOfGLMatrixPerProd(String prodcode) {
		// TODO Auto-generated method stub
		List<Tbglmatrixperprodcasa> list = new ArrayList<Tbglmatrixperprodcasa>();
		try {
			if (prodcode != null) {
				param.put("prodcode", prodcode);
				list = (List<Tbglmatrixperprodcasa>) DBServiceImpl
						.executeListHQLQuery("FROM Tbglmatrixperprodcasa WHERE id.productcode=:prodcode", param);
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
		param.put("businessdate", businessdate);
		param.put("enddate", enddate);
		List<Tbglentries> vouchers = new ArrayList<Tbglentries>();
		List<Tbglentries> entries = new ArrayList<Tbglentries>();
		String filename = "/JV_" + DateTimeUtil.convertDateToString(businessdate, DateTimeUtil.DATE_FORMAT_MM_DD_YYYY)
				+ "_001" + ".xlsx";
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
			String[] columnVoucher = { "Document No.", "Document Series", "Posting Date", "Due Date", "Reference 1",
					"Reference 2", "Remarks" };
			Row headerVoucher = sheetVoucher.createRow(0);
			for (int i = 0; i < columnVoucher.length; i++) {
				Cell cell = headerVoucher.createCell(i);
				cell.setCellValue(columnVoucher[i]);
				cell.setCellStyle(headerCellStyle);
			}

			// Get List of Vouchers by Date
			vouchers = (List<Tbglentries>) dbsrvc.execStoredProc(
					"SELECT ISNULL(glbranch,'') as glbranch,txseqno,b.desc1 as txcode,txdate,accountno,MAX(txamt) as txamt "
							+ "FROM Tbglentries a left join Tbcodetable b on a.txcode = b.codevalue "
							+ "WHERE b.codename='TXCODE' AND CAST(txdate as date) BETWEEN CAST(:businessdate as date) AND CAST(:enddate as date) "
							+ "GROUP BY glbranch,txseqno,txcode,Txdate,accountno,b.desc1 ORDER  BY txseqno",
					param, Tbglentries.class, 1, null);

			// Insert the list of vouchers into excel
			for (int i = 0; i < vouchers.size(); i++) {
				Row vRow = sheetVoucher.createRow(i + 1);
				vRow.createCell(0).setCellValue(vouchers.get(i).getTxseqno());
				vRow.createCell(1).setCellValue("Manual");
				vRow.createCell(2)
						.setCellValue(DateTimeUtil.convertDateToString(vouchers.get(i).getTxdate(), "MM/dd/yyyy"));
				vRow.createCell(3)
						.setCellValue(DateTimeUtil.convertDateToString(vouchers.get(i).getTxdate(), "MM/dd/yyyy"));
				vRow.createCell(4).setCellValue(vouchers.get(i).getAccountno());
				vRow.createCell(5).setCellValue(vouchers.get(i).getAccountno());
				vRow.createCell(6).setCellValue(vouchers.get(i).getTxcode());
				// Apply Styles
				vRow.getCell(2).setCellStyle(rightAlignCellStyle);
				vRow.getCell(3).setCellStyle(rightAlignCellStyle);
			}

			// Resize all columns to fit the content size
			for (int i = 0; i < columnVoucher.length; i++) {
				sheetVoucher.autoSizeColumn(i);
			}

			// Create Sheet for entries
			XSSFSheet sheetEntries = workbook.createSheet("Journal Voucher - Items");

			// Set up header for first row
			String[] columnEntries = { "Document No.", "Item No.", "Item Name", "Currency", "Debit", "Credit",
					"Is Bank Account", "Country", "Bank", "Bank Branch", "Bank Account No." };
			Row headerEntries = sheetEntries.createRow(0);
			for (int i = 0; i < columnEntries.length; i++) {
				Cell cell = headerEntries.createCell(i);
				cell.setCellValue(columnEntries[i]);
				cell.setCellStyle(headerCellStyle);
			}

			// Get List of Entries by Date
			entries = (List<Tbglentries>) dbsrvc
					.execStoredProc("SELECT txseqno,glsl,c.acctdesc as txbr,a.accountno,debit,credit,txbr "
							+ "FROM Tbglentries a left join Tbcodetable b on a.txcode = b.codevalue "
							+ "left join TBCOA c on a.glsl = c.accountno "
							+ "WHERE b.codename='TXCODE' AND CAST(txdate as date) BETWEEN CAST(:businessdate as date) AND CAST(:enddate as date) "
							+ "ORDER BY txseqno", param, Tbglentries.class, 1, null);

			// Insert the list of vouchers into excel
			for (int i = 0; i < entries.size(); i++) {
				Row eRow = sheetEntries.createRow(i + 1);
				eRow.createCell(0).setCellValue(entries.get(i).getTxseqno());
				eRow.createCell(1).setCellValue(entries.get(i).getGlsl());
				// Used txbr as substitute for gl account name
				eRow.createCell(2).setCellValue(entries.get(i).getTxbr());
				eRow.createCell(3).setCellValue("PHP");
				eRow.createCell(4).setCellValue(entries.get(i).getDebit().toString());
				eRow.createCell(5).setCellValue(entries.get(i).getCredit().toString());
				eRow.createCell(6).setCellValue("No");
				eRow.createCell(7).setCellValue("");
				eRow.createCell(8).setCellValue("");
				eRow.createCell(9).setCellValue("");
				eRow.createCell(10).setCellValue("");

				// Apply Cell Style
				eRow.getCell(4).setCellStyle(rightAlignCellStyle);
				eRow.getCell(5).setCellStyle(rightAlignCellStyle);
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
			System.out.println("success");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "resources/data/gl" + filename;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbglmatrixcasa> getGLMatrix() {
		DBService dbsrvc = new DBServiceImpl();
		try {
			return (List<Tbglmatrixcasa>) dbsrvc.executeListHQLQuery("FROM Tbglmatrixcasa", param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public String generateGLEntriesCASA(Date businessdate, Date enddate) {
		DBService dbsrvc = new DBServiceImpl();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("businessdate", businessdate);
		param.put("enddate", enddate);
		try {
			dbsrvc.executeUpdate("EXEC sp_GenerateGLEntriesCASA :businessdate,:enddate,'',1,0", param);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "success";
	}
}
