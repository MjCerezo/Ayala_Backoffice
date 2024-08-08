package com.etel.otherinfo.fatca;

import java.util.List;

import com.cifsdb.data.Tbfatca;
import com.etel.otherinfo.fatca.forms.FATCAViewForm;

public interface FatcaService {
	Tbfatca getFatcaInfo(String cifno);
	String saveOrUpdateFatcaInfo(Tbfatca info, String businesstype);
	List<FATCAViewForm> listFATCA(String cifno);
	String saveFATCAStatus(String cifno);
}
