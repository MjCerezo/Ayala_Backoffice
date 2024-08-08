package com.etel.dedupe;

import java.util.List;

import com.coopdb.data.Tbemployee;
import com.coopdb.data.Tbmember;
import com.etel.dedupe.forms.SearchParameters;
import com.etel.dedupe.forms.SearchResult;
import com.etel.dedupe.forms.fromMasterList;
import com.etel.forms.ReturnForm;

public interface DedupeService {

	SearchResult getSearchResult(SearchParameters params);

	List<Tbemployee> getList(SearchParameters params);

	List<Tbmember> getReferror(String name);

	ReturnForm createApplication(SearchParameters form, String apptype);

	fromMasterList getReadonlyFields(String appid);

	List<Tbmember> getSearchPerson(String name);

}
