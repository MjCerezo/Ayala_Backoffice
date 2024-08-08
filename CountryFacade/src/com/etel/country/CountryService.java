package com.etel.country;

import java.util.List;

import com.etel.country.forms.AddressForm;
import com.coopdb.data.Tbcountry;

public interface CountryService {
	
	List<Tbcountry> getListOfCountry();
	
	List<Tbcountry> getListOfStateByCountry(String code);
	
	List<Tbcountry> getListOfCityByState(String stateprovince);
	
	List<Tbcountry> getListOfPostalCode(String stateprovince, String city);
	
	AddressForm getAddress(String code, String stateprovince, String city);
	
	List<Tbcountry> getListOfStatebyRegion(String code, String areacode);
	
	List<Tbcountry> getListofRegion(String code);

	List<Tbcountry> getListByFieldParameter(String country, String region, String stateprovince, String city,
			String zipcode, String field);
}
