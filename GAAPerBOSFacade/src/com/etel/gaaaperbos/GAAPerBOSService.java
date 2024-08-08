/**
 * 
 */
package com.etel.gaaaperbos;

import java.util.List;

import com.coopdb.data.Tbgaaperbos;

/**
 * @author ETEL-LAPTOP19
 *
 */
public interface GAAPerBOSService {

	String saveGAA(Tbgaaperbos gaaperbos);

	List<Tbgaaperbos> listGAAPerBOS(String boscode);

	String deleteGAA(int id);

}
