package com.etel.management;

import java.util.List;

import com.cifsdb.data.Tbmanagement;
import com.coopdb.data.Tbmanagementlos;
import com.etel.managementforms.ManagementForm;

public interface ManagementService {

	String setupManagement(ManagementForm form);

	List<Tbmanagement> mngmtList(String cifno, String pcode, String shaCustType, Integer id);

	String updateManagement(Tbmanagement mg);

	String deleteManagement(int id);

	Tbmanagement getManagementRecord(String cifno, int id);

	String deleteUnusedBusinessType(String cifno, String oldBtype);

	String updateBusinessType(String cifno, String btype);

	String checkIfExisting(String cifno, String relatedcifno, String relcode, String ofcrposition, String poscategory);

	List<Tbmanagement> mngmtList2(String cifno, String oldBtype);

	Tbmanagement getGenManager(String cifno);
	
	//AMR 10-23-2020
	List<Tbmanagementlos> mngmtList2RB(String cifno, String oldBtype);
	String deleteUnusedBusinessTypeRB(String cifno, String oldBtype);

	List<Tbmanagement> listRegOwner(String cifno);

	List<Tbmanagement> listPartners(String cifno);

	List<Tbmanagement> listDirectorsTrustees(String cifno);

	List<Tbmanagement> listSignatories(String cifno);

	List<Tbmanagement> listOfficers(String cifno);

	List<Tbmanagement> listGenMngr(String cifno);

	List<Tbmanagement> listShareholdersIndiv(String cifno);

	List<Tbmanagement> listShareholdersCorp(String cifno);

	String saveOrUpdateRegOwner(Tbmanagement ref);

	String deleteRegOwner(Integer id);

	String saveOrUpdateDir(Tbmanagement ref);

	String deleteDir(Integer id);

	String saveOrUpdateOfficer(Tbmanagement ref);

	String deleteNew(Integer id);

	String saveOrUpdateSignatory(Tbmanagement ref);

	String saveOrUpdateShareIndiv(Tbmanagement ref);

	String saveOrUpdateShareCorp(Tbmanagement ref);
}
