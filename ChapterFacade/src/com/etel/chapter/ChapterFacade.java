package com.etel.chapter;

import java.util.List;

import com.coopdb.data.Tbchapter;
import com.coopdb.data.Tbmembershipapp;
import com.etel.chapter.forms.ChapterForm;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class. All public methods will be exposed to
 * the client. Their return values and parameters will be passed to the client
 * or taken from the client, respectively. This will be a singleton instance,
 * shared between all requests.
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL,
 * String, Exception). LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to
 * modify your log level. For info on these levels, look for tomcat/log4j
 * documentation
 */
@ExposeToClient
public class ChapterFacade extends JavaServiceSuperClass {
	/*
	 * Pass in one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log
	 * level; recommend changing this to FATAL or ERROR before deploying. For
	 * info on these levels, look for tomcat/log4j documentation
	 */
	public ChapterFacade() {
		// super(INFO);
	}

	private ChapterService service = new ChapterServiceImpl();

	public List<Tbchapter> getAllChapter() {
		return service.getAllChapter();
	}

	public String saveReview(Tbmembershipapp membership) {
		return service.saveReview(membership);
	}

	public String saveOrUpdateChapter(ChapterForm form) {
		return service.saveOrUpdateChapter(form);
	}

	public ChapterForm getChapter(String chaptercode, String chaptername) {
		return service.getChapter(chaptercode, chaptername);
	}
	
	public List<Tbchapter> getChaptersPerCooperative(String coopcode){
		return service.getChaptersPerCooperative(coopcode);
	}
}
