package com.events.form;

import java.util.Date;

public class EventsForm {
	private static int eventcode;
	private static String governancetype;
	private static String eventtype;
	private static String eventname;
	private static Date eventdate;
	private static String eventdesc;
	private static String eventvenue;
	private static Boolean isrequired;
	private static Date datecreated;
	private static String createdby;
	
	public static int getEventcode() {
		return eventcode;
	}
	public static void setEventcode(int eventcode) {
		EventsForm.eventcode = eventcode;
	}
	public static String getGovernancetype() {
		return governancetype;
	}
	public static void setGovernancetype(String governancetype) {
		EventsForm.governancetype = governancetype;
	}
	public static String getEventtype() {
		return eventtype;
	}
	public static void setEventtype(String eventtype) {
		EventsForm.eventtype = eventtype;
	}
	public static String getEventname() {
		return eventname;
	}
	public static void setEventname(String eventname) {
		EventsForm.eventname = eventname;
	}
	public static Date getEventdate() {
		return eventdate;
	}
	public static void setEventdate(Date eventdate) {
		EventsForm.eventdate = eventdate;
	}
	public static String getEventdesc() {
		return eventdesc;
	}
	public static void setEventdesc(String eventdesc) {
		EventsForm.eventdesc = eventdesc;
	}
	public static String getEventvenue() {
		return eventvenue;
	}
	public static void setEventvenue(String eventvenue) {
		EventsForm.eventvenue = eventvenue;
	}
	public static Boolean getIsrequired() {
		return isrequired;
	}
	public static void setIsrequired(Boolean isrequired) {
		EventsForm.isrequired = isrequired;
	}
	public static Date getDatecreated() {
		return datecreated;
	}
	public static void setDatecreated(Date datecreated) {
		EventsForm.datecreated = datecreated;
	}
	public static String getCreatedby() {
		return createdby;
	}
	public static void setCreatedby(String createdby) {
		EventsForm.createdby = createdby;
	}
	
}
