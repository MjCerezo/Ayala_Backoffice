package com.etel.cifgroup;

import java.util.List;

import com.cifsdb.data.Tbcifgroup;

public interface CIFGroupService {

	List<Tbcifgroup> displayCIFGroupDetails(String groupcode);
	String addCIFGroup(Tbcifgroup group);
	String deleteCIFGroup(Tbcifgroup groupcode);
	String updateCIFGroup(Tbcifgroup group);
	List<Tbcifgroup> getListCIFGroup();

}
