/**
 * 
 */
package com.etel.bos;

import java.util.List;

import com.etel.bos.form.ProductPerBOSForm;

/**
 * @author ETEL-LAPTOP19
 *
 */
public interface BOSService {

	List<ProductPerBOSForm> listProductPerBOS(String boscode, String membershiptype, String servicestatus);

	String addProduct(ProductPerBOSForm form);

	String editProduct(ProductPerBOSForm form);

	String deleteProduct(ProductPerBOSForm form);

}
