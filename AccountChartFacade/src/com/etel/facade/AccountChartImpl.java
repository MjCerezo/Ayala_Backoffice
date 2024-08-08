package com.etel.facade;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.coopdb.data.Tbcoa;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.HQLUtil;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;

public class AccountChartImpl implements AccountChartService {
	private static final Logger logger = Logger.getLogger(AccountChartImpl.class);

	private DBService dbService = new DBServiceImpl();
	String username = UserUtil.securityService.getUserName();
	Map<String, Object> params = HQLUtil.getMap();

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcoa> listCoa() {
		List<Tbcoa> list = new ArrayList<Tbcoa>();
		try {
			list = (List<Tbcoa>) dbService.executeListHQLQuery("FROM Tbcoa", null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public String saveOrUpdateCoa(Tbcoa d, String oldaccountno) {
		String flag = "failed";
		try {
			if (oldaccountno != null) {
				// update
				params.put("accountno", oldaccountno);
				// row to update
				Tbcoa row = (Tbcoa) dbService.executeUniqueHQLQuery("FROM Tbcoa WHERE accountno=:accountno", params);
				if (row != null) {

					if (oldaccountno.equals(d.getAccountno())) {
						// do nothing
						// allow to update
						// row.setAccountno(d.getAccountno());
						row.setAcctdesc(d.getAcctdesc());
						row.setActive(d.getActive());
						row.setUpdatedby(username);
						row.setDateupdated(new Date());

						if (dbService.saveOrUpdate(row)) {
							flag = "success";
						}
					} else {
						// account no changed
						params.put("accountno2", d.getAccountno()); // new
						Tbcoa row2 = (Tbcoa) dbService.executeUniqueHQLQuery("FROM Tbcoa WHERE accountno=:accountno2",
								params);
						if (row2 != null) {
							flag = "existing";
						} else {
							row.setAccountno(d.getAccountno());
							row.setAcctdesc(d.getAcctdesc());
							row.setActive(d.getActive());
							row.setUpdatedby(username);
							row.setDateupdated(new Date());

							if (dbService.saveOrUpdate(row)) {
								flag = "success";
							}
						}
					}
				}
			} else {
				// add
				if (d.getAccountno() != null) {
					params.put("accountno", d.getAccountno());
					Tbcoa row = (Tbcoa) dbService.executeUniqueHQLQuery("FROM Tbcoa WHERE accountno=:accountno",
							params);
					if (row != null) {
						flag = "existing";
					} else {
						Tbcoa n = new Tbcoa();
						n.setAccountno(d.getAccountno());
						n.setAcctdesc(d.getAcctdesc());
						n.setActive(d.getActive());
						n.setCreatedby(username);
						n.setDatecreated(new Date());

						if (dbService.save(n)) {
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

	public String deleteItem(String accountno) {
		String flag = "failed";
		try {
			if (accountno != null) {

				Integer res = dbService.executeUpdate("DELETE FROM TBCOA WHERE accountno ='" + accountno + "'", null);
				if (res != null && res == 1) {
					flag = "success";
				}
				/*
				 * params.put(accountno, accountno); Tbcoa row = (Tbcoa)dbService.
				 * executeUniqueHQLQuery("FROM Tbcoa WHERE accountno=:accountno", params);
				 * if(row!=null) { if(dbService.delete(row)) { flag = "success"; } }
				 */
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcoa> displayAccountDescription(String accountDescript) {
		List<Tbcoa> descript = new ArrayList<Tbcoa>();

		try {
			if ((accountDescript == null || accountDescript.equals(""))) {
				descript = (List<Tbcoa>) dbService.executeListHQLQuery("FROM Tbcoa", null);
			} else {

				params.put("acctdesc", "%" + accountDescript + "%");
				descript = (List<Tbcoa>) dbService.executeListHQLQuery("FROM Tbcoa where acctdesc LIKE :acctdesc",
						params);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ERROR" + e.toString());
		}
		return descript;
	}

	public String uploadCOA(String filename) throws IOException {
		String uploadDir = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("resources/data/upload");
		File file = new File(uploadDir + "/" + filename);
		String ext = filename.substring(filename.lastIndexOf("."), filename.length());
		dbService.executeUpdate("TRUNCATE TABLE TBCOA", null);
		if (ext.equals(".csv")) {
			BufferedReader br = new BufferedReader(new FileReader(file));
			br.readLine();
			try {
				String str;
				while ((str = br.readLine()) != null) {
					String result[] = str.split("\\|");
					Tbcoa coa = new Tbcoa();
					coa.setAccountno(result[0]);
					coa.setAcctdesc(result[1]);
					coa.setActive(true);
					coa.setCreatedby(username);
					coa.setDatecreated(new Date());
					dbService.save(coa);
				}
				br.close();
				return "success";
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				br.close();
			}
		} else if (ext.equals(".xlsx") || ext.equals(".xls")) {
			try {
				XSSFWorkbook workbook = new XSSFWorkbook(file);
				XSSFSheet sheet = workbook.getSheetAt(0);
				Iterator<Row> rowIterator = sheet.iterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					Tbcoa coa = new Tbcoa();
					coa.setAccountno(String.valueOf(row.getCell(0)));
					coa.setAcctdesc(String.valueOf(row.getCell(1)));
					coa.setActive(true);
					coa.setCreatedby(username);
					coa.setDatecreated(new Date());
					dbService.save(coa);
				}
				workbook.close();
				return "success";
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			return "Invalid file extension";
		}
		return "error";
	}
}
