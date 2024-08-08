package com.etel.notes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.history.HistoryService;
import com.etel.history.HistoryServiceImpl;
import com.etel.notes.forms.NotesSaveForm;
import com.etel.notes.forms.NotesSearchForm;
import com.etel.utils.HQLUtil;
import com.coopdb.data.Tbnotes;
import com.coopdb.data.Tbuser;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class NotesServiceImpl implements NotesService {
	SecurityService secservice =  (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbuser> getListofUsers() {
		// Get List of Users
		DBService dbsrvc = new DBServiceImpl();
		List<Tbuser> userlist = new ArrayList<Tbuser>();
		
		try{
			userlist = (List<Tbuser>) dbsrvc.executeListHQLQuery("FROM Tbuser", null);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return userlist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbnotes> searchNotes(NotesSearchForm form) {
		// TODO Search notes
				DBService dbService = new DBServiceImpl();
				StringBuilder query = new StringBuilder();
				List<Tbnotes> list = new ArrayList<Tbnotes>();
				SimpleDateFormat  formatter = new SimpleDateFormat("yyyy-MM-dd");
				String noteddate = null;
				if(form.getNoteddate()!=null){
					noteddate = formatter.format(form.getNoteddate());
				}				
				try{
					if(form.getNotetype() == null &&  form.getNoteddate() == null && form.getNotedby() == null && form.getCifno() == null){
						list = null;
						System.out.println(">>>>>>>> ALL FIELDS : NULL <<<<<<<<<");
					}
					else{
						query.append("FROM Tbnotes WHERE ");
						//ADD NOTE TYPE TO QUERY
						if(form.getNotetype() != null){
							query.append("notetype ="+"'" +form.getNotetype()+ "'"+" AND ");
						}
						
						if(form.getNoteddate() != null){
							query.append("noteddate BETWEEN '" + noteddate + " 00:00:00' AND '" + noteddate + " 23:59:00')" +" AND ");
						}
										
						if(form.getNotedby() != null){
							query.append("notedby =" +form.getNotedby()+" AND ");
						}
						
						if(form.getCifno() != null){
							query.append("cifno ="+"'"+ form.getCifno()+"'     ");
						}
						query.append("12345");
						String listquery = query.substring(0, query.length() - 10);
						System.out.println(listquery);
						list =(List<Tbnotes>) dbService.executeListHQLQuery(listquery, null);
					}
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return list;
			}

	@Override
	public String saveNewNotes(NotesSaveForm form) {
		// Save Notes
		
		DBService dbService = new DBServiceImpl();
		Tbnotes newnote = new Tbnotes();
		String flag = "Failed";
		
		try{
			newnote.setCifno(form.getCifno());
			newnote.setNotedbyuser(secservice.getUserName());
			newnote.setNoteddate(new Date());
			newnote.setNotes(form.getNotes());
			newnote.setNotetype(form.getNotetype());
			newnote.setNotedby(form.getNotedby());
			newnote.setPurposeofcall(form.getPurposeofcall());
			newnote.setPhoneno(form.getPhoneno());
			newnote.setDatetimeofcall(form.getDatetimeofcall());
			if(dbService.save(newnote)){
				flag = "success";

				HistoryService h = new HistoryServiceImpl();
				h.addHistory(form.getCifno(), "Added new notes", null);
				}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public String getUserFullName(String username) {
		// TODO Auto-generated method stub
		String fullname = null;
		DBService dbService = new DBServiceImpl();
		Map<String, Object> param = HQLUtil.getMap();
		Tbuser user = new Tbuser();
		param.put("username", secservice.getUserName());
		
		try{
			user = (Tbuser) dbService.executeUniqueHQLQuery("FROM Tbuser WHERE username =:username", param);
			fullname = user.getLastname() + ", " + user.getFirstname() +" " + user.getMiddlename();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return fullname;
	}

	@Override
	public Tbnotes getNotesRecord(Integer noteid) {
		// TODO Get Notes record
				Tbnotes notes = new Tbnotes();
				DBService dbService = new DBServiceImpl();
				Map<String, Object> param = HQLUtil.getMap();
				param.put("noteid", noteid);
				
				try{
					notes = (Tbnotes) dbService.executeUniqueHQLQueryMaxResultOne("FROM Tbnotes WHERE noteid=:noteid", param);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return notes;
			}

}
