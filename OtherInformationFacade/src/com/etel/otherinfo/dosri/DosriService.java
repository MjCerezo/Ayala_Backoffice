package com.etel.otherinfo.dosri;

import java.util.List;

import com.cifsdb.data.Tbdosri;
import com.etel.otherinfo.dosri.forms.AffiliatesOrSubsidiaries;
import com.etel.otherinfo.dosri.forms.CommonDOSRI;

public interface DosriService {
	String saveOrUpdateDosri(String cifno, Tbdosri dosri);
	Tbdosri getDosriInfo(String cifno);
	String saveDosriStatus(String cifno);
	List<AffiliatesOrSubsidiaries> listAffiliates(String cifno);
	List<CommonDOSRI> listCommonDOSRI(String cifno, String relationcode);
}
