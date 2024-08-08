package com.etel.chapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.cloudfoundry.org.codehaus.jackson.map.ObjectMapper;

import com.coopdb.data.Tbchapter;
import com.coopdb.data.TbchapterId;
import com.coopdb.data.Tbmembershipapp;
import com.etel.chapter.forms.ChapterForm;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.utils.AuditLog;
import com.etel.utils.AuditLogEvents;
import com.etel.utils.HQLUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class ChapterServiceImpl implements ChapterService {

	private static DBService dbService = new DBServiceImpl();
	private Map<String, Object> param = HQLUtil.getMap();
	SecurityService secservice = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private String username = secservice.getUserName();

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbchapter> getAllChapter() {
		try {
			List<Tbchapter> chapter = new ArrayList<Tbchapter>();
			chapter = (List<Tbchapter>) dbService.executeListHQLQuery("FROM Tbchapter", null);
			if (chapter != null) {
				return chapter;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public String saveReview(Tbmembershipapp membership) {
		try {
			if (membership.getMembershipappid() != null) {
				param.put("memappid", membership.getMembershipappid());
				Tbmembershipapp review = (Tbmembershipapp) dbService
						.executeUniqueHQLQuery("FROM Tbmembershipapp WHERE membershipappid=:memappid", param);
				if (review != null) {
					review.setMembershipclass(membership.getMembershipclass());
					review.setChaptercode(membership.getChaptercode());
					review.setWithmoa(membership.getWithmoa());
					if (dbService.saveOrUpdate(review)) {
						AuditLog.addAuditLog(AuditLogEvents.getAuditLogEvents(AuditLogEvents.M_SAVE_AS_DRAFT_REVIEW),
								"User " + username + " Saved as Draft Membership Application.", username, new Date(),
								AuditLogEvents.getEventModule(AuditLogEvents.M_SAVE_AS_DRAFT_REVIEW));
						return "success";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public String saveOrUpdateChapter(ChapterForm form) {
		try {
			param.put("chaptercode", form.getChaptercode());
			param.put("chaptername", form.getChaptername());
			Tbchapter chapter = (Tbchapter) dbService.executeUniqueHQLQuery(
					"FROM Tbchapter WHERE chaptercode=:chaptercode OR chaptername=:chaptername", param);
			if (chapter != null) {
				if (form.getFlag().equalsIgnoreCase("ADD")) {
					return "existing";
				} else {
					chapter.setChaptername(form.getChaptername());
					chapter.setUpdatedby(secservice.getUserName());
					chapter.setDateupdated(new Date());
					if (dbService.saveOrUpdate(chapter))
						return "success";
				}
			} else {
				if (form.getFlag().equalsIgnoreCase("ADD")) {
					Tbchapter c = new Tbchapter();
					TbchapterId id = new TbchapterId();
					id.setChaptercode(form.getChaptercode());
					id.setBranchcode(form.getBranchcode());
					c.setId(id);
					c.setCoopcode(form.getCoopcode());
//					c.setChaptercode(form.getChaptercode());
					c.setChaptername(form.getChaptername());
					c.setCreatedby(secservice.getUserName());
					c.setDatecreated(new Date());
					if (dbService.save(c))
						return "success";
				} else if (form.getFlag().equalsIgnoreCase("UPDATE")) {
					return "no existing chapter found";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "failed";
	}

	@Override
	public ChapterForm getChapter(String chaptercode, String chaptername) {
		try {
			ChapterForm chap = new ChapterForm();
			ObjectMapper mapper = new ObjectMapper();
			param.put("chaptercode", chaptercode);
			if (chaptercode != null && chaptername == null) {// DANIELFESALBON
				Tbchapter chapter = (Tbchapter) dbService
						.executeUniqueHQLQuery("FROM Tbchapter  WHERE chaptercode=:chaptercode", param);
				if (chapter != null) {
					String value = mapper.writeValueAsString(chapter);
					chap = mapper.readValue(value, ChapterForm.class);
					return chap;// DANIELFESALBON
				}
			}
			if (chaptercode != null && chaptername != null) {
				param.put("chaptername", chaptername);
				ChapterForm chapter = (ChapterForm) dbService.execSQLQueryTransformer(
						"FROM Tbchapter  WHERE chaptercode=:chaptercode AND chaptername=:chaptername", param,
						ChapterForm.class, 0);
				if (chapter != null)
					return chapter;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbchapter> getChaptersPerCooperative(String coopcode) {
		// TODO Auto-generated method stub
		try {
			param.put("coopcode", coopcode);
			List<Tbchapter> chapter = (List<Tbchapter>) dbService
					.executeListHQLQuery("FROM Tbchapter WHERE coopcode=:coopcode", param);
			if (chapter != null) {
				return chapter;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
