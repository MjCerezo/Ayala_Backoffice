package com.isls.document;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.cifsdb.data.Tbdocdetails;
import com.coopdb.data.Tbdocchecklist;
import com.coopdb.data.Tbdocpertransactiontype;
import com.coopdb.data.Tbdocsperapplication;
import com.coopdb.data.Tbgeneraldocs;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.common.service.DBServiceImplCIF;
import com.etel.utils.HQLUtil;
import com.etel.utils.ImageUtils;
import com.isls.document.forms.DocFields;
import com.isls.document.forms.DocumentForm;
import com.wavemaker.common.util.IOUtils;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.server.FileUploadResponse;

public class DocumentManagementServiceImpl implements DocumentManagementService {
	DBService dbService = new DBServiceImpl();
	DBService dbServiceCIF = new DBServiceImplCIF();
	private Map<String, Object> param = HQLUtil.getMap();

	@Override
	public FileUploadResponse uploadFile(MultipartFile file) {

		FileUploadResponse ret = new FileUploadResponse();

		try {
			String filename = file.getOriginalFilename(); /* .replaceAll("[^a-zA-Z0-9 ._-]",""); */
			boolean hasExtension = filename.indexOf(".") != -1;
			String name = (hasExtension) ? filename.substring(0, filename.lastIndexOf(".")) : filename;
			String ext = (hasExtension) ? filename.substring(filename.lastIndexOf(".")) : "";
			System.out.println("File ext : " + ext);
			System.out.println("FEEED" + RuntimeAccess.getInstance().getSession().getServletContext());
			if (ext.equalsIgnoreCase(".pdf")) {
				File dir = new File(
						RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/docdir/"));
				if (!dir.exists())
					dir.mkdirs();

				/*
				 * Create a file object that does not point to an existing file. Loop through
				 * names until we find a filename not already in use
				 */
				File outputFile = new File(dir, filename);

				deleteFileOlderThanXdays(1, dir.toString());

				// System.out.println("FEEEEED"+frimage);
				/* Write the file to the filesystem */
				FileOutputStream fos = new FileOutputStream(outputFile);
				IOUtils.copy(file.getInputStream(), fos);
				file.getInputStream().close();
				fos.close();
				// System.out.println(membershipappid);
//				docu_files.setDocubase(ImageUtils.pdfToBase64(outputFile.toString()));
//				docu_files.setDocuname(filename);
//				docu_files.setMembershipappid(membershipappid);
//				dbService.save(docu_files);
				// System.out.println(ImageUtils.pdfToBase64(outputFile.toString()));

				ret.setPath(outputFile.getPath());
				ret.setError("");
			} else {
				ret.setError("Invalid File Format");
			}
		} catch (Exception e) {
			System.out.println("ERROR11:" + e.getMessage() + " | " + e.toString());

			ret.setError(e.getMessage());
		}
		return ret;
	}

	@Override
	public String exportFiles(int docid, String fullname) {
		String flag = "";
		param.put("id", docid);
		// System.out.println("FEEEED"+docid);

		List<DocFields> list = new ArrayList<DocFields>();
		Tbdocchecklist doc = new Tbdocchecklist();

		String path = System.getProperty("user.home") + "\\Desktop";
		System.out.println(path);
		list = (List<DocFields>) dbService.execSQLQueryTransformer(
				"select A.id,B.membername,A.doccategory,A.documentname,A.remarks,A.datereqsubmission,A.datesubmitted,A.docbasecode,A.filename,A.membershipid from TBDOCCHECKLIST as A INNER JOIN TBMEMBER AS B ON A.membershipid = B.membershipid where A.id=:id",
				param, DocFields.class, 1);
		doc = (Tbdocchecklist) dbService.executeUniqueHQLQuery("FROM Tbdocchecklist WHERE id=:id", param);

		if (!list.isEmpty()) {
			for (int x = 0; x < list.size(); x++) {
				try {
					String doccat = list.get(x).getDoccategory();
					File file = new File(path + "\\" + list.get(x).getMembershipid() + "-" + fullname);
					if (!file.exists()) {
						if (file.mkdir()) {
							System.out.println("Directory is created!");
						} else {
							System.out.println("Failed to create directory!");
						}
					}
//					with subfolders	
					File folderinside = new File(file + "\\" + doccat);
					if (!folderinside.exists()) {
						if (folderinside.mkdir()) {
							System.out.println("Directory is created!");
						} else {
							System.out.println("Failed to create directory!");
						}
					}
					String filepath = folderinside.toString() + "\\";
//					String filepath = file.toString() + "\\";
					ImageUtils.base64ToPDF(doc.getDocbasecode(), filepath, list.get(x).getFilename());
					flag = "success";
					// System.out.println(flag);

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			// System.out.println(" " + list);
			return flag;
		}

		else {

			System.out.println("FAILED");
			flag = "failed";
			return flag;
		}
	}

	@Override
	public String addDocuments(String filepath, Tbdocchecklist docchecklist) {
		String flag = "";
		File file = null;
		if (filepath != null) {
			file = new File(filepath);
			// System.out.println(filepath);
		}
		// System.out.println(status);
		try {
			if (docchecklist != null) {
				if (docchecklist.getDocstatus().equals("1")) {
					docchecklist.setDocbasecode(ImageUtils.pdfToBase64(filepath));
					docchecklist.setFilename(file.getName());
					docchecklist.setDateuploaded(new Date());
					docchecklist.setIssubmitted(true);
					docchecklist.setIsuploaded(true);
				}
				dbService.saveOrUpdate(docchecklist);
				flag = "success";
				return flag;
			}

			// System.out.println(ImageUtils.pdfToBase64(filepath));
			// System.out.println(file.getName());
		} catch (Exception e) {
			// TODO: handle exception
		}
		flag = "failed";
		return flag;
	}

	/** Delete file older than x days */
	public void deleteFileOlderThanXdays(long days, String dirPath) {
		File folder = new File(dirPath);
		if (folder.exists()) {
			File[] listFiles = folder.listFiles();
			long eligibleForDeletion = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000);
			for (File listFile : listFiles) {
				if (listFile.lastModified() < eligibleForDeletion) {
					if (!listFile.delete()) {
						System.out.println("Unable to Delete Files..");
					}
				}
			}
		}
	}

	@Override
	public String checkDocStatus(String docid) {
		Tbdocchecklist docs = (Tbdocchecklist) dbService.executeUniqueHQLQuery("FROM Tbdocchecklist WHERE id=:docid",
				param);
		String flag = "";
		if (docs.getDatesubmitted() != null) {
			flag = "success";
			return flag;
		} else {
			flag = "failed";
			return flag;
		}
	}

	@Override
	public String updateDocs(Date dateSubmitted, int docid) {
		param.put("id", docid);
		Tbdocchecklist docs = (Tbdocchecklist) dbService.executeUniqueHQLQuery("FROM Tbdocchecklist WHERE id=:id",
				param);
		if (docs != null) {

			docs.setDatesubmitted(dateSubmitted);
			docs.setIssubmitted(true);
			dbService.saveOrUpdate(docs);
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocFields> searchMember(String membershipid, String name, String status, String doccat) {
		List<DocFields> list = new ArrayList<DocFields>();
		param.put("memid", membershipid);
		param.put("name", name + "%%");
		param.put("status", status);
		param.put("doccat", doccat);

		if (membershipid != null && name == null) {
			try {
				if (doccat != null) {
					list = (List<DocFields>) dbService.execSQLQueryTransformer(
							"select A.id,B.membername,A.doccategory,A.documentname,A.remarks,A.datereqsubmission,A.datesubmitted,A.docbasecode,A.filename,A.membershipid from TBDOCCHECKLIST as A INNER JOIN TBMEMBER AS B ON A.membershipid = B.membershipid where A.membershipid=:memid and A.docstatus =:status and A.doccategory=:doccat",
							param, DocFields.class, 1);
				} else {
					list = (List<DocFields>) dbService.execSQLQueryTransformer(
							"select A.id,B.membername,A.doccategory,A.documentname,A.remarks,A.datereqsubmission,A.datesubmitted,A.docbasecode,A.filename,A.membershipid from TBDOCCHECKLIST as A INNER JOIN TBMEMBER AS B ON A.membershipid = B.membershipid where A.membershipid=:memid and A.docstatus =:status",
							param, DocFields.class, 1);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (membershipid == null && name != null) {
			try {
				if (doccat != null) {
					list = (List<DocFields>) dbService.execSQLQueryTransformer(
							"select A.id,B.membername,A.doccategory,A.documentname,A.remarks,A.datereqsubmission,A.datesubmitted,A.docbasecode,A.filename,A.membershipid from TBDOCCHECKLIST as A INNER JOIN TBMEMBER AS B ON A.membershipid = B.membershipid where B.membername like:name and A.docstatus =:status and A.doccategory=:doccat",
							param, DocFields.class, 1);
				} else {
					list = (List<DocFields>) dbService.execSQLQueryTransformer(
							"select A.id,B.membername,A.doccategory,A.documentname,A.remarks,A.datereqsubmission,A.datesubmitted,A.docbasecode,A.filename,A.membershipid from TBDOCCHECKLIST as A INNER JOIN TBMEMBER AS B ON A.membershipid = B.membershipid where B.membername like:name and A.docstatus =:status",
							param, DocFields.class, 1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				if (doccat != null) {
					list = (List<DocFields>) dbService.execSQLQueryTransformer(
							"select A.id,B.membername,A.doccategory,A.documentname,A.remarks,A.datereqsubmission,A.datesubmitted,A.docbasecode,A.filename,A.membershipid from TBDOCCHECKLIST as A INNER JOIN TBMEMBER AS B ON A.membershipid = B.membershipid where A.membershipid=:memid and B.membername like:name and A.docstatus =:status and A.doccategory=:doccat",
							param, DocFields.class, 1);
				} else {
					list = (List<DocFields>) dbService.execSQLQueryTransformer(
							"select A.id,B.membername,A.doccategory,A.documentname,A.remarks,A.datereqsubmission,A.datesubmitted,A.docbasecode,A.filename,A.membershipid from TBDOCCHECKLIST as A INNER JOIN TBMEMBER AS B ON A.membershipid = B.membershipid where A.membershipid=:memid and B.membername like:name and A.docstatus =:status",
							param, DocFields.class, 1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println(list);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocFields> getAllRecords() {
		List<DocFields> list = new ArrayList<DocFields>();
		list = (List<DocFields>) dbService.execSQLQueryTransformer(
				"select A.id,B.membername,A.doccategory,A.documentname,A.remarks,A.datereqsubmission,A.datesubmitted,A.docbasecode,A.filename,A.membershipid from TBDOCCHECKLIST as A INNER JOIN TBMEMBER AS B ON A.membershipid = B.membershipid",
				param, DocFields.class, 1);

		return list;
	}

	@Override
	public String previewPDF(int docid) {
		param.put("id", docid);
		String flag = "";
		DocFields docfields = new DocFields();
		docfields = (DocFields) dbService.execSQLQueryTransformer(
				"select A.id,B.membername,A.doccategory,A.documentname,A.remarks,A.datereqsubmission,A.datesubmitted,A.docbasecode,A.filename,A.membershipid from TBDOCCHECKLIST as A INNER JOIN TBMEMBER AS B ON A.membershipid = B.membershipid where A.id=:id",
				param, DocFields.class, 0);
		Tbdocchecklist doc = new Tbdocchecklist();
		doc = (Tbdocchecklist) dbService.executeUniqueHQLQuery("FROM Tbdocchecklist WHERE id=:id", param);

		File dir = new File(
				RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/docdir/"));
		if (!dir.exists())
			dir.mkdirs();
		try {
			if (docfields != null) {
				String filepath = dir.toString() + "\\";
				ImageUtils.base64ToPDF(doc.getDocbasecode().toString(), filepath, docfields.getFilename());
				System.out.println(docfields.getDocbasecode().toString());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (Desktop.isDesktopSupported()) {
			try {
				if (docfields != null) {
					File file = new File(dir.toString() + "\\" + docfields.getFilename());
					Desktop.getDesktop().open(file);
					flag = "success";
					return flag;
				}
			} catch (IOException ex) {
				// no application registered for PDFs
			}
		}
		flag = "failed";
		return flag;
	}

	@Override
	public String updateExpiration(Date dateExpiration, int docid) {
		param.put("id", docid);
		Tbdocchecklist docs = (Tbdocchecklist) dbService.executeUniqueHQLQuery("FROM Tbdocchecklist WHERE id=:id",
				param);
		if (docs != null) {
//			System.out.println("PASSED");
//			System.out.println(dateExpiration);
			docs.setDateexpiration(dateExpiration);

			dbService.saveOrUpdate(docs);
		}
		return null;
	}

	@Override
	public String checkIsExpiring(int docid, int txcode) {
		String flag = "";
		String tcode = txcode + "";
		param.put("id", docid);
		param.put("txcode", tcode);
		Tbdocchecklist docs = (Tbdocchecklist) dbService.executeUniqueHQLQuery("FROM Tbdocchecklist WHERE id=:id",
				param);
//		System.out.println(docs);
//		System.out.println(docs.getDocumentcode());
		param.put("doccode", docs.getDocumentcode());
		Tbdocpertransactiontype docpertrans = (Tbdocpertransactiontype) dbService
				.executeUniqueHQLQuery("FROM Tbdocpertransactiontype WHERE documentcode=:doccode", param);
//		System.out.println(tcode);
//		System.out.println(docpertrans);
		if (docpertrans.getIsexpiring()) {
			flag = "true";

		} else {
			flag = "false";
		}
//		System.out.println(flag);
		return flag;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbgeneraldocs> ifFieldisActivated(String doctype, String doccategory) {
		param.put("doctype", doctype);
		param.put("doccategory", doccategory);
		List<Tbgeneraldocs> list = new ArrayList<Tbgeneraldocs>();
		try {
			list = (List<Tbgeneraldocs>) dbService.executeListHQLQuery(
					"FROM Tbgeneraldocs where doctype=:doctype and doccategory=:doccategory", param);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentForm> listAllMemberDocuments(DocumentForm docparameters) {
		// TODO Auto-generated method stub
		try {
			param.put("membershipid", docparameters.getMembershipid() != null ? docparameters.getMembershipid() : "");
			param.put("membershipappid",
					docparameters.getMembershipappid() != null ? docparameters.getMembershipappid() : "");
			if (docparameters.getMembershipid() != null || docparameters.getMembershipappid() != null) {
				// TBDOCCHECKLIST - MEMBER(APPROVED APPLICANT)
				String dcchcklst = "SELECT mem.membershipclass, mem.companycode, mem.employeeid, mem.firstname, mem.lastname, mem.middlename, doc.id, doc.doccategory, doc.documentcode, doc.documentname, doc.membershipid as referenceno,doc.appno, doc.membershipid as cifno, doc.docstatus, doc.filename, doc.dmsid, doc.issubmitted, doc.datesubmitted, doc.datereqsubmission, doc.remarks FROM TBDOCCHECKLIST doc LEFT JOIN TBMEMBER mem ON mem.membershipid=doc.membershipid WHERE doc.membershipid =:membershipid";
				// TBDOCSPERAPPLICATION (LOAN APPLICATION)
				String dcsprapp = "SELECT mem.membershipclass, mem.companycode, mem.employeeid, mem.firstname, mem.lastname, mem.middlename, lo.docid as id, lo.doccategory, lo.documentcode, lo.documentname, lo.appno as referenceno,lo.appno, lo.cifno, lo.docstatus, lo.filename, lo.dmsid, lo.issubmitted, lo.datesubmitted, lo.datereqsubmission, lo.remarks FROM TBDOCSPERAPPLICATION lo LEFT JOIN TBMEMBER mem ON mem.membershipid=lo.cifno WHERE lo.cifno =:membershipid";
				// TBDOCCHECKLIST - APPLICANT
				String dcchcklst2 = "SELECT mem.membershipclass, mem.companycode, mem.employeeid, mem.firstname, mem.lastname, mem.middlename, doc.id, doc.doccategory, doc.documentcode, doc.documentname, doc.membershipappid as referenceno,doc.appno, doc.membershipid as cifno, doc.docstatus, doc.filename, doc.dmsid, doc.issubmitted, doc.datesubmitted, doc.datereqsubmission, doc.remarks FROM TBDOCCHECKLIST doc LEFT JOIN TBMEMBERSHIPAPP mem ON mem.membershipappid=doc.membershipappid WHERE mem.membershipappid =:membershipappid AND mem.membershipappid NOT IN (SELECT m.membershipappid FROM TBMEMBER m)";
				if (docparameters.getDoccategory() != null) {
					param.put("doccategory", docparameters.getDoccategory());
					dcchcklst += " AND doc.doccategory=:doccategory ";
					dcsprapp += " AND lo.doccategory=:doccategory ";
					dcchcklst2 += " AND doc.doccategory=:doccategory ";
				}
				if (docparameters.getDocstatus() != null) {
					param.put("docstatus", docparameters.getDocstatus());
					dcchcklst += " AND doc.docstatus=:docstatus ";
					dcsprapp += " AND lo.docstatus=:docstatus ";
					dcchcklst2 += " AND doc.docstatus=:docstatus ";
				}
				String query = dcchcklst + " UNION ALL " + dcsprapp + " UNION ALL " + dcchcklst2;
//				if (docparameters.getMembershipappid() != null) {
//					query += " UNION ALL " + dcchcklst2;
//				}

//				System.out.println(query);
				return (List<DocumentForm>) dbService.execSQLQueryTransformer(query, param, DocumentForm.class, 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public String saveOrUpdateMemberDoucment(DocumentForm docparameters, Tbdocchecklist doc) {
		// TODO Auto-generated method stub
		try {
			if (docparameters != null) {
				param.put("id", docparameters.getId());
				if (docparameters.getDoccategory().equals("MEMBERSHIP")) {
					Tbdocchecklist d = (Tbdocchecklist) dbService
							.executeUniqueHQLQuery("FROM Tbdocchecklist WHERE id=:id", param);
					if (d != null) {
						if (docparameters.getDocstatus().equals("1")) {
							d.setDatesubmitted(new Date());
						}
						if (docparameters.getDocstatus().equals("2")) {
							d.setDatereqsubmission(null);// on-going
						}
						d.setDocstatus(docparameters.getDocstatus());
						d.setRemarks(docparameters.getRemarks());
						d.setIdtype(doc.getIdtype());
						d.setDatesubmitted(doc.getDatesubmitted());
						if (dbService.saveOrUpdate(d)) {
							return "success";
						}
					}
				} else {
					Tbdocsperapplication d = (Tbdocsperapplication) dbService
							.executeUniqueHQLQuery("FROM Tbdocsperapplication WHERE docid=:id", param);
					if (d != null) {
						if (docparameters.getDocstatus().equals("1")) {
							d.setDatesubmitted(new Date());
						}
						if (docparameters.getDocstatus().equals("2")) {
							d.setDatereqsubmission(null);// on-going
						}
						d.setDocstatus(docparameters.getDocstatus());
						d.setRemarks(docparameters.getRemarks());
						if (dbService.saveOrUpdate(d)) {
							return "success";
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "failed";
	}

	@Override
	public String viewMemberDocument(DocumentForm docparameters) {
		// TODO Auto-generated method stub
		try {
			param.put("id", docparameters.getId());
			if (docparameters != null && docparameters.getDoccategory() != null) {
				if (docparameters.getDoccategory().equals("MEMBERSHIP")) {
					Tbdocchecklist d = (Tbdocchecklist) dbService
							.executeUniqueHQLQuery("FROM Tbdocchecklist WHERE id=:id", param);
					if (d.getDocbasecode() != null && d.getFilename() != null) {

						// directory
						File dir = new File(RuntimeAccess.getInstance().getSession().getServletContext()
								.getRealPath("resources/docdir/"));
						if (!dir.exists())
							dir.mkdirs();

						// conversion
						String filepath = dir.toString() + "\\";
						ImageUtils.base64ToPDF(d.getDocbasecode().toString(), filepath, d.getFilename());

						// try opening to desktop
//						if (Desktop.isDesktopSupported()) {
//							try {
//								File file = new File(dir.toString() + "\\" + d.getFilename());
//								Desktop.getDesktop().open(file);
//								return "success";
//							} catch (IOException ex) {
//								ex.printStackTrace();
//								// no application registered for PDFs
//							}
//						}
						return "resources/docdir/" + d.getFilename();
					}
				} else {
					Tbdocsperapplication d = (Tbdocsperapplication) dbService
							.executeUniqueHQLQuery("FROM Tbdocsperapplication WHERE docid=:id", param);
					if (d.getDocbasecode() != null && d.getFilename() != null) {

						// directory
						File dir = new File(RuntimeAccess.getInstance().getSession().getServletContext()
								.getRealPath("resources/docdir/"));
						if (!dir.exists())
							dir.mkdirs();

						// conversion
						String filepath = dir.toString() + "\\";
						ImageUtils.base64ToPDF(d.getDocbasecode().toString(), filepath, d.getFilename());

						// try opening to desktop
//						if (Desktop.isDesktopSupported()) {
//							try {
//								File file = new File(dir.toString() + "\\" + d.getFilename());
//								Desktop.getDesktop().open(file);
//								return "success";
//							} catch (IOException ex) {
//								ex.printStackTrace();
								// no application registered for PDFs
//							}
//						}
						return "resources/docdir/" + d.getFilename();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return "failed";
	}

	@Override
	public String deleteUploadedFile(int docid) {
		String flag = "failed";
		try {
			if(docid != 0) {
				param.put("id", docid);
				
				Tbdocdetails docs = (Tbdocdetails) dbServiceCIF.executeUniqueHQLQuery("FROM Tbdocdetails WHERE id=:id",
						param);
				if (docs != null) {
					docs.setDocfilename(null);
					docs.setDocbasecode(null);
					
					if(dbServiceCIF.saveOrUpdate(docs)) {
						flag = "success";
						
					}
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		return flag;
	}
}
