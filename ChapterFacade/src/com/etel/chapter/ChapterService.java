package com.etel.chapter;

import java.util.List;

import com.coopdb.data.Tbchapter;
import com.coopdb.data.Tbmembershipapp;
import com.etel.chapter.forms.ChapterForm;

public interface ChapterService {

	List<Tbchapter> getAllChapter();
	
	String saveReview(Tbmembershipapp membership);

	String saveOrUpdateChapter(ChapterForm form);

	ChapterForm getChapter(String chaptercode, String chaptername);

	List<Tbchapter> getChaptersPerCooperative(String coopcode);

}
