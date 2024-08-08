package com.etel.sigcard;

import java.util.List;

import com.coopdb.data.Tbbatchfile;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdepositcif;

public interface SigcardService {
	
	String saveSigcard(String filename, String acctno);
	String viewSigcard(String acctno);
	String saveBulkFile(Tbbatchfile data);
	List<Tbbatchfile> getBatchList(Integer batchstatus);
	String readBulk(Integer id);
	List<Tbdepositcif> bulkAccountCreation(String filename);
	String viewSigByAcctNo(String acctNo);
	String saveDepositSigcard(Tbdeposit dep);
}
