package com.etel.personalreference;

import java.util.List;

import com.cifsdb.data.Tbpersonalreference;
import com.coopdb.data.Tbcomaker;



public interface PersonalReferenceService {

	List<Tbpersonalreference> listPersonalreference(String cifno);
	
	String deletePref(Integer id);
	
	String saveOrUpdatePref(Tbpersonalreference ref);

	List<Tbcomaker> listComaker(String cifno, String appno);
	
	String deleteComaker(Integer id);
	
	String saveOrUpdateComaker(Tbcomaker ref);

	
}
