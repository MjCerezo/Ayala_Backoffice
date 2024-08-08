package com.casa.util;

import java.util.Date;
import java.util.List;

import com.casa.util.forms.BranchInfoForm;
import com.casa.util.forms.DescIdForm;
import com.casa.util.forms.ProductMatrixForm;
import com.coopdb.data.Tbbanks;
import com.coopdb.data.Tbbranch;
import com.coopdb.data.Tbcodetablecasa;
import com.coopdb.data.Tbdocsperproduct;
import com.coopdb.data.Tbdocuments;
import com.coopdb.data.Tbholiday;
import com.coopdb.data.Tbmerchant;
import com.coopdb.data.Tboverridematrix;
import com.coopdb.data.Tbprodmatrix;
import com.coopdb.data.Tbrates;
import com.coopdb.data.Tbterminal;
import com.coopdb.data.Tbtransactioncode;
import com.coopdb.data.Tbunit;

public interface UtilService {
	
	List<DescIdForm> getCurrency();
	List<DescIdForm> getUserListCico();
	List<DescIdForm> genCodetable(String codename);
	List<DescIdForm> getProdtype(String prodgroup);
	Date getBusinessdt();
	String validateUser(String username, String password);
	BranchInfoForm getBrInfo();
	String updateBr(String brstat);

	String aeTerminal(Tbterminal data);
	List<Tbterminal> terminalList(String unitid, int isUnused, String userid);
	String deleteTerminal(int id);
	List<ProductMatrixForm> productList();
	String aeProduct(Tbprodmatrix input);
	Tbprodmatrix getProductDetail(int id);
	int terminalNo(String userid);
	Integer printDocSlip(String txrefno);
	Integer printPassbook(String accountno);
	Tbrates getRates(String curr,String buysell);
	List<Tbholiday> listHolidays(Date minDate);
	Integer printCTD(String accountno);
	ProductMatrixForm checkProduct(String prodcode, String prodgroup);
	List<Tbdocuments> getListDocument();
	List<Tbdocsperproduct> getDocsperProd();
	String addOrupdateDoc(Tbdocuments doc);
	String addOrupdateDocsperProd(Tbdocsperproduct docs);
	List<Tbtransactioncode> getListTxcode(String search);
	String addOrUpdateTxcode(Tbtransactioncode code);
	List<Tbbranch> getBranchList();
	String addEditOverride(Tboverridematrix override);
	List<Tboverridematrix> overrideList();
	String deleteOverride(Tboverridematrix override);
	List<Tbtransactioncode> txList();
	List<Tbcodetablecasa> codetableList(String codename);
	String deleteCodetable(Tbcodetablecasa codetable);
	String addEditCodetable(Tbcodetablecasa codetable);
	Integer passbookIssuance(String accountno, String serialno);
	String addOrupdateMerchant(Tbmerchant merchant);
	List<Tbmerchant> getListMerchant();
	Boolean hasRole(String roleid);
	List<Tbbanks> getBankList();
	String addOrupdateHoliday(Tbholiday hol, String type);
	List<Tbholiday> getListHoliday(String search);
	BranchInfoForm checkBranchStat();
	Tbprodmatrix getProductDetailPerProductCodeORAccountno(String productcode, String accountno);
	String checkMaxWithdraw(String acctno);
	Tbprodmatrix getProductDetailByProdCode(String subProdCode, String prodCode);
	
	
}
