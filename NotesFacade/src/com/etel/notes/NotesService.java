package com.etel.notes;

import java.util.List;

import com.etel.branch.forms.BranchForm;
import com.etel.notes.forms.NotesSaveForm;
import com.etel.notes.forms.NotesSearchForm;
import com.coopdb.data.Tbnotes;
import com.coopdb.data.Tbuser;

public interface NotesService {

	List<Tbuser> getListofUsers();

	List<Tbnotes> searchNotes(NotesSearchForm form);

	String saveNewNotes(NotesSaveForm form);

	String getUserFullName(String username);

	Tbnotes getNotesRecord(Integer noteid);
	
}
