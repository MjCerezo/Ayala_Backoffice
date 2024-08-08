package com.etel.dataentryutil;

import com.coopdb.data.Tbmembershipapp;
import com.etel.dataentry.FullDataEntryService;
import com.etel.dataentry.FullDataEntryServiceImpl;
import com.etel.forms.ReturnForm;

public class ValidateDataEntry {

	FullDataEntryService f = new FullDataEntryServiceImpl();

	public ReturnForm validateMembershipAppEncoding(String membershipappid) {
		ReturnForm form = new ReturnForm();
		form.setMessage("Please comply to the following requirements:<br><b>");
		form.setFlag("success");
		try {
			Tbmembershipapp app = f.getMembershipapp(membershipappid, null);
			if (app != null) {
//				if (app.getShortname() == null || app.getShortname().equals("")) {
//					form.setMessage(form.getMessage() + "Shortname, ");
//					form.setFlag("failed");
//				}
				if (app.getMembershipclass() == null || app.getMembershipclass().equals("")) {
					form.setMessage(form.getMessage() + "Membership Type, ");
					form.setFlag("failed");
				}
				if (app.getTitle() == null || app.getTitle().equals("")) {
					form.setMessage(form.getMessage() + "Title, ");
					form.setFlag("failed");
				}
				if (app.getDateofbirth() == null) {
					form.setMessage(form.getMessage() + "Date of Birth, ");
					form.setFlag("failed");
				}
				if (app.getGender() == null || app.getGender().equals("")) {
					form.setFlag("failed");
					form.setMessage(form.getMessage() + "Gender, ");
				}
				if (app.getPlaceofbirth() == null || app.getPlaceofbirth().equals("")) {
					form.setFlag("failed");
					form.setMessage(form.getMessage() + "Place of Birth, ");
				}
				if (app.getCountryofbirth() == null || app.getCountryofbirth().equals("")) {
					form.setFlag("failed");
					form.setMessage(form.getMessage() + "Country of Birth, ");
				}
				if (app.getNationality() == null || app.getNationality().equals("")) {
					form.setFlag("failed");
					form.setMessage(form.getMessage() + "Nationality, ");
				}
//				if (app.getSocialaffiliation() == null || app.getSocialaffiliation().equals("")) {
//					form.setFlag("failed");
//					form.setMessage(form.getMessage() + "Social Affiliation, ");
//				}
				if (app.getCivilstatus() == null || app.getCivilstatus().equals("")) {
					form.setFlag("failed");
					form.setMessage(form.getMessage() + "Civil Status, ");
				}
//				if (app.getReligion() == null || app.getReligion().equals("")) {
//					form.setFlag("failed");
//					form.setMessage(form.getMessage() + "Religion, ");
//				}
//				if (app.getWeight() == null) {
//					form.setFlag("failed");
//					form.setMessage(form.getMessage() + "Weight, ");
//				}
//				if (app.getHeightfeet() == null && app.getHeightinches() == null) {
//					form.setFlag("failed");
//					form.setMessage(form.getMessage() + "Height, ");
//				}
//				if (app.getBloodtype() == null || app.getBloodtype().equals("")) {
//					form.setFlag("failed");
//					form.setMessage(form.getMessage() + "Blood Type, ");
//				}
				if (app.getBranch() == null || app.getBranch().equals("")) {
					form.setFlag("failed");
					form.setMessage(form.getMessage() + "Branch, ");
				}
				if (app.getCompanycode() == null || app.getCompanycode().equals("")) {
					form.setFlag("failed");
					form.setMessage(form.getMessage() + "Branch of Service, ");
				}
				if(app.getPicture() == null || app.getPicture().equals("")) {
					form.setFlag("failed");
					form.setMessage(form.getMessage() + "Picture, ");					
				}
//				if (app.getGroupcode() == null || app.getGroupcode().equals("")) {
//					form.setFlag("failed");
//					form.setMessage(form.getMessage() + "Department, ");
//				}
			}
			form.setMessage(form.getMessage().substring(0, form.getMessage().length() - 2));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return form;
	}
}
