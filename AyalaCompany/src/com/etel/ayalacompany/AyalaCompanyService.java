package com.etel.ayalacompany;

import java.util.List;

import com.coopdb.data.AyalaCompany;

public interface AyalaCompanyService {
	List<AyalaCompany> listCompany();
	
	String deleteCompany(Integer id);
	
	String saveOrUpdateCompany(AyalaCompany com);
}
