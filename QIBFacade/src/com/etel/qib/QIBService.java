package com.etel.qib;

import java.util.List;

import com.cifsdb.data.Tbqibhistory;
import com.cifsdb.data.Tbqibinfo;
import com.etel.codetable.forms.CodetableForm;
import com.etel.forms.FormValidation;

public interface QIBService {
	FormValidation saveOrUpdateQIB(Tbqibinfo info, String cifno);
	Tbqibinfo getQIBInfo(String cifno);
	String saveQIBHistory(String cifno, String status);
	List<Tbqibhistory> qibHistory(String cifno);
	FormValidation validateQIBInfo(String cifno);
	String deleteQIB(String cifno);
	List<CodetableForm> getListofCodesPerCodename(String codename);
	List<CodetableForm> getListofCodesPerCodename2(String codename);
}
