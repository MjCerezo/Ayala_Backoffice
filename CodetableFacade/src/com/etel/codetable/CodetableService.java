package com.etel.codetable;

import java.util.List;

import com.etel.codetable.forms.AOForm;
import com.etel.codetable.forms.CodetableForm;
import com.coopdb.data.Tbcodetable;

/**
 * @author Kevin
 */

public interface CodetableService {

	List<CodetableForm> getListofCodesPerCodename(String codename);
	List<CodetableForm> getListofCodesPerCodenameAndDesc2(String codename, String desc2);
	String addCodetable(CodetableForm form);
	String updateCodetable(CodetableForm form);
	List<Tbcodetable> getCodevalueList(String codename);
	List<String> getAllCodenameList();
	String deleteCodename(CodetableForm form);
	String addCodeName(String codename, String remarks, Boolean iseditable);
	List<CodetableForm> searchCodetable(String search);
	List<CodetableForm> searchCodename(String codename, String search);
	List<CodetableForm> getListofCodesPerCodenameCIF(String codename);
	List<AOForm> getListofAO(String aocode);
	

}
