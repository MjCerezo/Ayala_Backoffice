package com.etel.otherinfo.pep;


import java.util.List;

import com.cifsdb.data.Tbcifpepinfo;
import com.cifsdb.data.Tbpepinfo;
import com.cifsdb.data.Tbpepq3;
import com.cifsdb.data.Tbriskprofile;
import com.etel.codetable.forms.CodetableForm;
import com.etel.otherinfo.pep.forms.PEPViewForm;
import com.etel.otherinfo.pep.forms.PresentPreviousGovEmp;

public interface PepService {
	String saveOrUpdatePepInfo(Tbpepinfo info, String cifno, Tbriskprofile risk, String empstatus);
	Tbpepinfo getPepInfo(String cifno);
	List<PEPViewForm> listPEP(String cifno);
	List<PresentPreviousGovEmp> listQ1(String cifno, String empstatus);
	List<PresentPreviousGovEmp> listQ2(String cifno, String empstatus);
	List<Tbpepq3> listQ3(String cifno);
	String saveQ3(Tbpepq3 q3);
	void deleteQ3(Integer id);
	List<CodetableForm> listGovernmentType();
	//Renz
	List<Tbcifpepinfo> listPEPQ1(String cifno);
	List<Tbcifpepinfo> listPEPQ2(String cifno);
	String deletePEP(Integer id);
	String saveOrUpdateQ1(Tbcifpepinfo ref);
	String saveOrUpdateQ2(Tbcifpepinfo ref);
 }
