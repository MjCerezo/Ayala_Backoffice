/**
 * 
 */
package com.etel.depositprint;

import java.util.ArrayList;
import java.util.List;

import com.etel.depositprint.form.ValidationSlipForm;
import com.etel.printer.PrinterUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

/**
 * @author ETEL-LAPTOP19
 *
 */
public class DepositPrintServiceImpl implements DepositPrintService {

	SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@Override
	public Integer printDocSlip(ValidationSlipForm form) {
		// TODO Auto-generated method stub
		try {
			List<String> datatoprint = new ArrayList<String>();
			datatoprint.add(form.getTxname().toUpperCase() + " " + form.getCurrency() + " "
					+ PrinterUtil.formatData(form.getTxamount().toString(), 2));
			datatoprint.add(form.getAccountno());
			datatoprint.add(form.getAccountname());
			datatoprint.add(form.getTxrefno());
			return PrinterUtil.printerUtil(datatoprint, 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

}
