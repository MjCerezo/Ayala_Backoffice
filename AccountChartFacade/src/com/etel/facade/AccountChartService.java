package com.etel.facade;

import java.io.IOException;
import java.util.List;

import com.coopdb.data.Tbcoa;



public interface AccountChartService {

	List<Tbcoa> listCoa();


	String saveOrUpdateCoa(Tbcoa d, String oldaccountno);


	String deleteItem(String accountno);


	List<Tbcoa> displayAccountDescription(String accountDescript);


	String uploadCOA(String filename) throws IOException;

}
