package com.etel.notes;

import java.util.ArrayList;
import java.util.List;

import com.etel.notes.forms.NotesSaveForm;
import com.etel.notes.forms.NotesSearchForm;
import com.coopdb.data.Tbnotes;
import com.coopdb.data.Tbuser;
import com.wavemaker.runtime.javaservice.JavaServiceSuperClass;
import com.wavemaker.runtime.service.annotations.ExposeToClient;

/**
 * This is a client-facing service class.  All
 * public methods will be exposed to the client.  Their return
 * values and parameters will be passed to the client or taken
 * from the client, respectively.  This will be a singleton
 * instance, shared between all requests. 
 * 
 * To log, call the superclass method log(LOG_LEVEL, String) or log(LOG_LEVEL, String, Exception).
 * LOG_LEVEL is one of FATAL, ERROR, WARN, INFO and DEBUG to modify your log level.
 * For info on these levels, look for tomcat/log4j documentation
 */
@ExposeToClient
public class NotesFacade extends JavaServiceSuperClass {
    /* Pass in one of FATAL, ERROR, WARN,  INFO and DEBUG to modify your log level;
     *  recommend changing this to FATAL or ERROR before deploying.  For info on these levels, look for tomcat/log4j documentation
     */
    public NotesFacade() {
       super(INFO);
    }

    //Get List of Users FUllname
   	public List<Tbuser> getListofUsers() {
       	List<Tbuser> userlist = new ArrayList<Tbuser>();
       	NotesService secsrvc = new NotesServiceImpl();
       	userlist = secsrvc.getListofUsers();
       	return userlist;
       }
     
   	public List <Tbnotes> searchNotes(NotesSearchForm form){
    	List<Tbnotes> list = new ArrayList<Tbnotes>();
    	NotesService service = new NotesServiceImpl();
		list = service.searchNotes(form);
		return list;
	}

   	public String saveNewNotes(NotesSaveForm form){
   		NotesService service = new NotesServiceImpl();
		String flag = service.saveNewNotes(form);
		return flag;
    	
    }
   	
   	public String getUserFullName(String username){
   		NotesService service = new NotesServiceImpl();
		String flag = service.getUserFullName(username);
		return flag;
   	}
   	
   	public Tbnotes getNotesRecord(Integer noteid){
   		Tbnotes notes = new Tbnotes() ;
   		NotesService service = new NotesServiceImpl();
   		notes = service.getNotesRecord(noteid);
		return notes;
   	}
   	
}
