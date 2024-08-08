package com.etel.RateManagement;

import java.util.List;

import com.coopdb.data.Tbrates;

public interface RateManagementService {

	List<Tbrates> getListRates();

	String addOrupdateRates(Tbrates rate);

	String deleteRates(int id);

}
