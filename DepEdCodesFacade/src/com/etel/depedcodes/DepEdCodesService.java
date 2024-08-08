package com.etel.depedcodes;

import java.util.List;

import com.cifsdb.data.Tbcodetable;
import com.cifsdb.data.Tbdepedcodes;
import com.etel.codetable.forms.CodetableForm;
import com.etel.depedcodes.form.DepEdCodes;


public interface DepEdCodesService {
	List<DepEdCodes> listDepEdCodes();
	List<DepEdCodes> listDepEdCodesByRegion(String region);
	List<DepEdCodes> listDepEdCodesByDivision(String division);
	List<Tbdepedcodes> searchDepEdCodes(String region, String division, String station);
	String saveOrupdateDepedCode(Tbdepedcodes data, String saveOrupdate);
	
    /************************************************************************************* 
     *************************************************************************************START OF DEPED CODES*******/
	String saveOrupdateRegion(Tbcodetable data, String addOrupdate);
	List<Tbcodetable> searchRegion(String regionname);
	String saveOrupdateDivision(Tbcodetable data, String addOrupdate);
	List<CodetableForm> searchDivisionByRegionCode(String regioncode);
	List<Tbcodetable> searchDivision(String regioncode, String divisioncode);
}
