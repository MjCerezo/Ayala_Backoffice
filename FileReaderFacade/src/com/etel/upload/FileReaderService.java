package com.etel.upload;

import java.util.List;

import com.etel.filereaderforms.CollateralRealEstateForm;
import com.etel.filereaderforms.CollateralVehicleForm;
import com.etel.filereaderforms.FormValidation;
import com.etel.uploadforms.EmployeeForm;
import com.etel.uploadforms.FormValidation2;

public interface FileReaderService {

	FormValidation2 uploadEmployee(String filename);

	String saveOrUpdateUploadedEmployee(List<EmployeeForm> form);


	String saveRel(List<CollateralRealEstateForm> form);
	String saveAuto(List<CollateralVehicleForm> form);
	List<CollateralRealEstateForm> removeAllExistingRel(List<CollateralRealEstateForm> form);
	List<CollateralVehicleForm> removeAllExistingAuto(List<CollateralVehicleForm> form);
	FormValidation uploadCollateral(String filename, String type);

}
