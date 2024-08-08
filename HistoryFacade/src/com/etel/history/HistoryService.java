package com.etel.history;

import java.util.List;

import com.coopdb.data.Tbhistory;


public interface HistoryService {
	String addHistory(String cifno, String description, String remarks);
	List<Tbhistory> listHistory(String cifno);
	
	//MAR 10-13-2020
	String saveHistory(String appno, Integer eventid, String eventdescription);
	
}
