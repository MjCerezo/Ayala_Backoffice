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

Membership_DocumentsChecklist.widgets = {
svListNewMemAppDocs: ["wm.ServiceVariable", {"inFlightBehavior":"executeLast","operation":"getNewMemberAppDocChecklist","service":"DocumentsFacade"}, {}, {
input: ["wm.ServiceInput", {"type":"getNewMemberAppDocChecklistInputs"}, {}, {
binding: ["wm.Binding", {}, {}, {
wire: ["wm.Wire", {"expression":undefined,"source":"app.MemberAppID.dataValue","targetProperty":"membershipappid"}, {}]
}]
}]
}],
svUpdateDocs: ["wm.ServiceVariable", {"inFlightBehavior":"executeLast","operation":"updateDocs","service":"DocumentManagementFacade"}, {"onResult2":"svUpdateDocsResult2"}, {
input: ["wm.ServiceInput", {"type":"updateDocsInputs"}, {}, {
binding: ["wm.Binding", {}, {}, {
wire: ["wm.Wire", {"expression":undefined,"source":"gridDocList.selectedItem","targetProperty":"doc"}, {}],
wire1: ["wm.Wire", {"expression":undefined,"source":"calSubmitted.dateValue","targetProperty":"doc.datesubmitted"}, {}],
wire2: ["wm.Wire", {"expression":undefined,"source":"gridDocList.selectedItem.issubmitted","targetProperty":"doc.issubmitted"}, {}],
wire3: ["wm.Wire", {"expression":undefined,"source":"gridDocList.selectedItem.datesubmitted","targetProperty":"dateSubmitted"}, {}],
wire4: ["wm.Wire", {"expression":undefined,"source":"gridDocList.selectedItem.id","targetProperty":"docid"}, {}]
}]
}]
}],
svAddDocuments: ["wm.ServiceVariable", {"inFlightBehavior":"executeLast","operation":"addDocuments","service":"DocumentManagementFacade"}, {"onResult":"svAddDocumentsResult"}, {
input: ["wm.ServiceInput", {"type":"addDocumentsInputs"}, {}, {
binding: ["wm.Binding", {}, {}, {
wire: ["wm.Wire", {"expression":undefined,"source":"gridDocList.selectedItem.id","targetProperty":"docid"}, {}],
wire1: ["wm.Wire", {"expression":undefined,"source":"txtRemarks.dataValue","targetProperty":"remarks"}, {}],
wire2: ["wm.Wire", {"expression":undefined,"source":"dateSubmission.dataValue","targetProperty":"dateSubmission"}, {}],
wire4: ["wm.Wire", {"expression":undefined,"source":"dojoFileUpload1.variable.path","targetProperty":"filepath"}, {}],
wire3: ["wm.Wire", {"expression":undefined,"source":"selectStatus.dataValue","targetProperty":"status"}, {}]
}]
}]
}],
svStatus: ["wm.ServiceVariable", {"inFlightBehavior":"executeLast","operation":"getListofCodesPerCodename","service":"CodetableFacade"}, {}, {
input: ["wm.ServiceInput", {"type":"getListofCodesPerCodenameInputs"}, {}, {
binding: ["wm.Binding", {}, {}, {
wire: ["wm.Wire", {"expression":"\"DOCAPPSTATUS\"","targetProperty":"codename"}, {}]
}]
}]
}],
dlgDate: ["wm.DesignableDialog", {"border":"1","buttonBarId":"buttonBar","containerWidgetId":"containerWidget","desktopHeight":"200px","height":"200px","title":"Date Submitted","width":"250px"}, {}, {
containerWidget: ["wm.Container", {"_classes":{"domNode":["wmdialogcontainer","MainContent"]},"autoScroll":true,"height":"100%","horizontalAlign":"left","padding":"5","verticalAlign":"top","width":"100%"}, {}, {
calSubmitted: ["wm.dijit.Calendar", {"height":"100%","width":"100%"}, {"onValueSelected":"calSubmittedValueSelected"}, {
binding: ["wm.Binding", {}, {}, {
wire: ["wm.Wire", {"expression":"new Date()","targetProperty":"maximum"}, {}]
}]
}]
}],
buttonBar: ["wm.ButtonBarPanel", {"border":"1,0,0,0","borderColor":"#eeeeee","height":"15px"}, {}]
}],
dlgDocuments: ["wm.DesignableDialog", {"border":"1","buttonBarId":"buttonBar1","containerWidgetId":"containerWidget1","desktopHeight":"300px","height":"300px","title":"Add Document","width":"350px"}, {}, {
containerWidget1: ["wm.Container", {"_classes":{"domNode":["wmdialogcontainer","MainContent"]},"height":"100%","horizontalAlign":"left","padding":"5","verticalAlign":"top","width":"100%"}, {}, {
panel1: ["wm.Panel", {"height":"100%","horizontalAlign":"left","verticalAlign":"top","width":"100%"}, {}, {
txtDocumentName: ["wm.Text", {"border":"0","caption":"Document Name","displayValue":"","height":"25px","readonly":true}, {}, {
binding: ["wm.Binding", {}, {}, {
wire: ["wm.Wire", {"expression":undefined,"source":"gridDocList.selectedItem.documentname","targetProperty":"dataValue"}, {}]
}]
}],
txtDocumentCategory: ["wm.Text", {"border":"0","caption":"Document Category","dataValue":"MEMBER","displayValue":"MEMBER","height":"25px","readonly":true}, {}],
selectStatus: ["wm.SelectMenu", {"_classes":{"domNode":["selectMenu"]},"border":"0","caption":"Status","dataField":"codevalue","dataType":"com.etel.codetable.forms.CodetableForm","dataValue":undefined,"displayField":"desc1","displayValue":"","height":"25px"}, {"onchange":"selectStatusChange"}, {
binding: ["wm.Binding", {}, {}, {
wire: ["wm.Wire", {"expression":undefined,"source":"svStatus","targetProperty":"dataSet"}, {}]
}]
}],
dateSubmission: ["wm.Date", {"border":"0","caption":"Date Submission","dataValue":undefined,"displayValue":"","height":"25px","showing":false}, {}, {
binding: ["wm.Binding", {}, {}, {
wire: ["wm.Wire", {"expression":"new Date()","targetProperty":"minimum"}, {}]
}]
}],
txtRemarks: ["wm.LargeTextArea", {"border":"0","caption":"Remarks","captionPosition":"left","dataValue":undefined,"desktopHeight":"44px","displayValue":"","height":"44px"}, {}],
panel2: ["wm.Panel", {"height":"100%","horizontalAlign":"left","layoutKind":"left-to-right","verticalAlign":"top","width":"100%"}, {}, {
label1: ["wm.Label", {"align":"left","height":"37px","padding":"4","singleLine":false,"width":"250px"}, {}, {
binding: ["wm.Binding", {}, {}, {
wire: ["wm.Wire", {"expression":undefined,"source":"dojoFileUpload1.variable.name","targetProperty":"caption"}, {}]
}]
}],
dojoFileUpload1: ["wm.DojoFileUpload", {"buttonCaption":"Upload","height":"35px","service":"DocumentManagementFacade","useList":false,"width":"80px"}, {}, {
input: ["wm.ServiceInput", {"type":"uploadFileInputs"}, {}]
}]
}]
}]
}],
buttonBar1: ["wm.ButtonBarPanel", {"border":"1,0,0,0","borderColor":"#eeeeee","desktopHeight":"36px","height":"36px"}, {}, {
btnSave: ["wm.Button", {"border":"1","caption":"Save","desktopHeight":"25px","height":"25px","styles":{},"width":"80px"}, {"onclick":"svAddDocuments"}]
}]
}],
layoutBox1: ["wm.Layout", {"horizontalAlign":"left","styles":{},"verticalAlign":"top"}, {}, {
gridDocList: ["wm.DojoGrid", {"columns":[
{"show":false,"field":"id","title":"Id","width":"80px","align":"right","formatFunc":"","mobileColumn":false},
{"show":false,"field":"documentcode","title":"Documentcode","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"dmsid","title":"Dmsid","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"membershipappid","title":"Membershipappid","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"membershipid","title":"Membershipid","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":true,"field":"documentname","title":"Document Name","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"txcode","title":"Txcode","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"appno","title":"Appno","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"cifno","title":"Cifno","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":true,"field":"docstatus","title":"Status","width":"150px","align":"left","formatFunc":"gridDocListDocstatusFormat","mobileColumn":false},
{"show":false,"field":"dateuploaded","title":"Dateuploaded","width":"80px","align":"left","formatFunc":"wm_date_formatter","mobileColumn":false},
{"show":false,"field":"uploadedby","title":"Uploadedby","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"issubmitted","title":"Is Submitted","width":"100px","align":"center","formatFunc":"","fieldType":"dojox.grid.cells.Bool","mobileColumn":false},
{"show":false,"field":"datesubmitted","title":"Date Submitted","width":"120px","align":"center","formatFunc":"wm_date_formatter","formatProps":{"useLocalTime":false,"datePattern":"MM-dd-yyyy","formatLength":"short","dateType":"date"},"constraints":null,"mobileColumn":false},
{"show":false,"field":"isuploaded","title":"Isuploaded","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":true,"field":"remarks","title":"Remarks","width":"150px","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"isrequired","title":"Isrequired","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"isrequestwaiver","title":"Isrequestwaiver","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"dateapproved","title":"Dateapproved","width":"80px","align":"left","formatFunc":"wm_date_formatter","mobileColumn":false},
{"show":false,"field":"ispoa","title":"Ispoa","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"reqtype","title":"Reqtype","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"PHONE COLUMN","title":"-","width":"100%","align":"left","expression":"\"<div class='MobileRowTitle'>\" +\n\"Document Name: \" + ${documentname} +\n\"</div>\"\n\n+ \"<div class='MobileRow'>\" +\n\"Status: \" + ${wm.runtimeId}.formatCell(\"docstatus\", ${docstatus}, ${this}, ${wm.rowId})\n + \"</div>\"\n\n+ \"<div class='MobileRow'>\" +\n\"Remarks: \" + ${remarks}\n + \"</div>\"\n\n","mobileColumn":true},
{"show":false,"field":"isreviewed","title":"Isreviewed","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"datereviewed","title":"Datereviewed","width":"80px","align":"left","formatFunc":"wm_date_formatter","mobileColumn":false},
{"show":false,"field":"txrefno","title":"Txrefno","width":"80px","align":"right","formatFunc":"","mobileColumn":false},
{"show":false,"field":"docbasecode","title":"Docbasecode","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"filename","title":"Filename","width":"100%","align":"left","formatFunc":"","mobileColumn":false},
{"show":false,"field":"datereqsubmission","title":"Datereqsubmission","width":"80px","align":"left","formatFunc":"wm_date_formatter","mobileColumn":false}
],"height":"100%","localizationStructure":{},"minDesktopHeight":60,"primaryKeyFields":["id"],"singleClickEdit":true}, {"onCellEdited":"gridDocListCellEdited","onClick":"gridDocListClick"}, {
binding: ["wm.Binding", {}, {}, {
wire: ["wm.Wire", {"expression":undefined,"source":"svListNewMemAppDocs","targetProperty":"dataSet"}, {}]
}]
}]
}]
};

Membership_DocumentsChecklist.prototype._cssText = '';
Membership_DocumentsChecklist.prototype._htmlText = '';