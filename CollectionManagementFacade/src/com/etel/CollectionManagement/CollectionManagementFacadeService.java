package com.etel.CollectionManagement;

import java.util.List;

import com.coopdb.data.Tbcollectionmanagement;

public interface CollectionManagementFacadeService {

	Tbcollectionmanagement getCollectionDetails(String accountno);

	String saveCollectionDetails(Tbcollectionmanagement collection);

}
