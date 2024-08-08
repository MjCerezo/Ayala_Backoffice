package com.etel.codetablecasa;

import java.util.List;

import com.etel.codetable.forms.CodetableForm;
import com.coopdb.data.Tbcodetable;
import com.coopdb.data.Tbcodetablecasa;

/**
 * @author Kevin
 */

public interface CodetableCASAService {

	List<CodetableForm> getListofCodesPerCodename(String codename);
	List<CodetableForm> getListofCodesPerCodenameAndDesc2(String codename, String desc2);
	String addCodetable(CodetableForm form);
	String updateCodetable(CodetableForm form);
	List<Tbcodetablecasa> getCodevalueList(String codename);
	List<String> getAllCodenameList();
	String deleteCodename(CodetableForm form);
	String addCodeName(String codename, String remarks, Boolean iseditable);
	List<CodetableForm> searchCodetable(String search);
	List<CodetableForm> searchCodename(String codename, String search);
	List<CodetableForm> getListofCodesPerCodenameCIF(String codename);
	

}
