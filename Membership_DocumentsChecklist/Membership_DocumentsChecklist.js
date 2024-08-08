dojo.declare("Membership_DocumentsChecklist", wm.Page, {
	start: function() {
		this.svListNewMemAppDocs.update();
        this.svStatus.update();
	},
	"preferredDevice": "desktop",

	gridDocListClick: function(inSender, evt, selectedItem, rowId, fieldId, rowNode, cellNode) {
//        var issubmitted = selectedItem.data.issubmitted;
//		if(fieldId == "issubmitted" && issubmitted){
//            this.dlgDate.show();    
//        }
         this.dlgDocuments.show();
        this.txtDocumentCategory.displayValue("MEMBER");
      

	},
	gridDocListCellEdited: function(inSender, inValue, rowId, fieldId, isInvalid) {
        console.log(inSender);
        if(fieldId == "issubmitted"){
        if(inValue == false){
            this.svUpdateDocs.input.setValue("doc.issubmitted", false);
            this.svUpdateDocs.input.setValue("doc.datesubmitted", null);
            this.svUpdateDocs.update();
        } else if (inValue == true){
            this.dlgDate.show();
            }
        }
	},
	calSubmittedValueSelected: function(inSender, inDate) {
        //this.svUpdateDocs.input.setValue("field", "submit");
        //this.svUpdateDocs.input.setValue("doc.issubmitted", true);
        this.svUpdateDocs.input.setValue("datesubmitted", this.calSubmitted.getDateValue());
		this.svUpdateDocs.update();
	},
    svUpdateDocsResult2: function(inSender, inDeprecated) {
	    this.svListNewMemAppDocs.update();
	    this.dlgDate.hide();
	    this.calSubmitted.setDate(null);
	},
	selectStatusChange: function(inSender, inDisplayValue, inDataValue, inSetByCode) {
		if(inDisplayValue == "To-Follow")
        {
            this.dateSubmission.setShowing(true);
            this.label1.setShowing(false);
            this.dojoFileUpload1.setShowing(false);
            this.label1.setCaption("");
        }
        else if(inDisplayValue == "Waived" || inDisplayValue == "Not Applicable" )
        {
            this.label1.setShowing(false);
            this.dojoFileUpload1.setShowing(false);
            this.dateSubmission.setShowing(false);
            this.label1.setCaption("");
        }
       
        else{
            this.dateSubmission.setShowing(false);
            this.label1.setShowing(true);
            this.dojoFileUpload1.setShowing(true);
            
            }
	},
	svAddDocumentsResult: function(inSender, inDeprecated) {
		this.svListNewMemAppDocs.update();
        this.dlgDocuments.hide();
        var flag = inSender.getData().dataValue;

        if (flag == "success") {
            app.toastInfo("Successfully addded the document.", 3000);
            if(this.selectStatus.displayValue == "On-Hand")
            {
                this.dlgDate.show();
            }
        
            
            this.selectStatus.clear();
            this.txtRemarks.clear();
            this.dojoFileUpload1.clearData();
            this.dateSubmission.clear();
            this.label1.setCaption("");
            
        }
        else if(flag == "failed"){
            app.toastInfo("Failed to add the document.", 3000);
            }
	},
	gridDocListDocstatusFormat: function( inValue, rowId, cellId, cellField, cellObj, rowObj) {
		var length = this.svStatus.getCount();
        for(var i = 0; i < length; i++)
        {
            var item = rowObj.docstatus;
            
             if (this.svStatus.getItem(i).getValue("codevalue") == item) {
                return this.svStatus.getItem(i).getValue("desc1");

            }
        }
	},
	_end: 0
});