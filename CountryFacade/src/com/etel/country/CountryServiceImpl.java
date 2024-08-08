package com.etel.country;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.country.forms.AddressForm;
import com.etel.utils.HQLUtil;
import com.coopdb.data.Tbcountry;

public class CountryServiceImpl implements CountryService {

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcountry> getListOfCountry() {
		List<Tbcountry> list = new ArrayList<Tbcountry>();
		DBService dbService = new DBServiceImpl();
		try {
			list = (List<Tbcountry>) dbService.execSQLQueryTransformer(
					"SELECT DISTINCT code, country FROM Tbcountry ORDER BY country ASC", null, Tbcountry.class, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcountry> getListOfStateByCountry(String code) {
		List<Tbcountry> list = new ArrayList<Tbcountry>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (code != null) {
				params.put("code", code);
				list = (List<Tbcountry>) dbService.execSQLQueryTransformer(
						"SELECT DISTINCT code, country, stateprovince, areacode FROM Tbcountry WHERE code=:code", params,
						Tbcountry.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcountry> getListOfCityByState(String stateprovince) {
		List<Tbcountry> list = new ArrayList<Tbcountry>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (stateprovince != null) {
				params.put("stateprovince", stateprovince);
				list = (List<Tbcountry>) dbService.execSQLQueryTransformer(
						"SELECT DISTINCT code, country, stateprovince, city FROM Tbcountry WHERE stateprovince=:stateprovince",
						params, Tbcountry.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcountry> getListOfPostalCode(String stateprovince, String city) {
		List<Tbcountry> list = new ArrayList<Tbcountry>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (city != null && stateprovince != null) {
				params.put("city", city);
				params.put("state", stateprovince);
				list = (List<Tbcountry>) dbService.execSQLQueryTransformer
						("select areadesc,postalcode FROM Tbcountry WHERE stateprovince=:state AND city=:city", params, Tbcountry.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public AddressForm getAddress(String code, String stateprovince, String city) {
		AddressForm form = new AddressForm();
		try {
			List<Tbcountry> listCountry = getListOfCountry();
			if (listCountry != null) {
				// set list of country
				form.setListOfCountry(listCountry);
				if (code != null) {
					List<Tbcountry> listState = getListOfStateByCountry(code);
					if (listState != null) {
						// set list of state
						form.setListOfStateByCountry(listState);
						if (stateprovince != null) {
							List<Tbcountry> listCity = getListOfCityByState(stateprovince);
							if (listCity != null) {
								// set list of city
								form.setListOfCityByState(listCity);
								if (city != null) {
									List<Tbcountry> listPostal = getListOfPostalCode(stateprovince, city);
									if (listPostal != null) {
										form.setListOfPostalCode(listPostal);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return form;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcountry> getListofRegion(String code) {
		List<Tbcountry> list = new ArrayList<Tbcountry>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (code != null) {
				params.put("code", code);
				list = (List<Tbcountry>) dbService.execSQLQueryTransformer(
						"SELECT DISTINCT code, country, areacode FROM Tbcountry WHERE code=:code", params,
						Tbcountry.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcountry> getListOfStatebyRegion(String code, String areacode) {
		List<Tbcountry> list = new ArrayList<Tbcountry>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		try {
			if (code != null && areacode != null) {
				params.put("code", code);
				params.put("areacode", areacode);
				list = (List<Tbcountry>) dbService.execSQLQueryTransformer(
						"SELECT DISTINCT areacode, stateprovince FROM Tbcountry WHERE code=:code and areacode=:areacode", params,
						Tbcountry.class, 1);

			} else if (code != null && areacode == null) {
				params.put("code", code);
				list = (List<Tbcountry>) dbService.execSQLQueryTransformer(
						"SELECT DISTINCT areacode, stateprovince FROM Tbcountry where code=:code", params, Tbcountry.class, 1);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbcountry> getListByFieldParameter(String country, String region, String stateprovince, String city,
			String zipcode, String field) {
		// TODO Auto-generated method stub
		List<Tbcountry> list = new ArrayList<Tbcountry>();
		DBService dbService = new DBServiceImpl();
		Map<String, Object> params = HQLUtil.getMap();
		StringBuilder q = new StringBuilder("SELECT DISTINCT ");
		params.put("code", country);
		params.put("areacode", region);
		params.put("stateprovince", stateprovince);
		params.put("city", city);
		params.put("postalcode", zipcode);
		try {
			if (field != null) {
				if (field.equalsIgnoreCase("COUNTRY")) {
					q.append("code, country FROM Tbcountry");
				}
				if (field.equalsIgnoreCase("REGION")) {
					/* search by country */
					q.append("code, country, areacode FROM Tbcountry WHERE code=:code");
				}
				if (field.equalsIgnoreCase("STATEPROVINCE")) {
					/* Country is default on parameter */
					/* region, city */
					q.append("stateprovince, areacode FROM Tbcountry WHERE code=:code");
					if (region != null) {
						q.append(" AND areacode=:areacode");
					}
					if (city != null) {
						q.append(" AND city=:city");
					}
				}
				if (field.equalsIgnoreCase("CITY")) {
					/* Country is default on parameter */
					/* region, stateprovince */
					q.append("city, stateprovince, areacode FROM Tbcountry WHERE code=:code");
					if (stateprovince != null) {
						q.append(" AND stateprovince=:stateprovince");
					}
					if (region != null) {
						q.append(" AND areacode=:areacode");
					}
				}
				if (field.equalsIgnoreCase("ZIPCODE")) {
					/* Country is default on parameter */
					/* stateprovince, city */
					q.append("postalcode, areadesc FROM Tbcountry WHERE code=:code");
					if (stateprovince != null) {
						q.append(" AND stateprovince=:stateprovince");
					}
					if (city != null) {
						q.append(" AND city=:city");
					}
				}
				list = (List<Tbcountry>) dbService.execSQLQueryTransformer(q.toString(), params, Tbcountry.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
