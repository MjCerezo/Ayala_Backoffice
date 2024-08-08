package com.etel.country.forms;

import java.util.List;

import com.coopdb.data.Tbcountry;

public class AddressForm {
	private List<Tbcountry> listOfCountry;
	private List<Tbcountry> listOfStateByCountry;
	private List<Tbcountry> listOfCityByState;
	private List<Tbcountry> listOfPostalCode;
	
	public List<Tbcountry> getListOfCountry() {
		return listOfCountry;
	}
	public void setListOfCountry(List<Tbcountry> listOfCountry) {
		this.listOfCountry = listOfCountry;
	}
	public List<Tbcountry> getListOfStateByCountry() {
		return listOfStateByCountry;
	}
	public void setListOfStateByCountry(List<Tbcountry> listOfStateByCountry) {
		this.listOfStateByCountry = listOfStateByCountry;
	}
	public List<Tbcountry> getListOfCityByState() {
		return listOfCityByState;
	}
	public void setListOfCityByState(List<Tbcountry> listOfCityByState) {
		this.listOfCityByState = listOfCityByState;
	}
	public List<Tbcountry> getListOfPostalCode() {
		return listOfPostalCode;
	}
	public void setListOfPostalCode(List<Tbcountry> listOfPostalCode) {
		this.listOfPostalCode = listOfPostalCode;
	}
	
}
