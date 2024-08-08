package com.etel.sigcard;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

import javax.imageio.ImageIO;

import org.codehaus.jackson.map.ObjectMapper;

import com.casa.acct.AccountService;
import com.casa.acct.AccountServiceImpl;
import com.coopdb.data.Tbbatchfile;
import com.coopdb.data.Tbdeposit;
import com.coopdb.data.Tbdepositcif;
import com.coopdb.data.Tbfilehistory;
import com.coopdb.data.Tbsigcard;
import com.etel.common.service.DBService;
import com.etel.common.service.DBServiceImpl;
import com.etel.sigcard.form.BatchFileListHandler;
import com.etel.sigcard.form.BulkForm;
import com.etel.sigcard.form.SigcardUploadForm;
import com.etel.util.ConfigPropertyUtil;
import com.etel.utils.HQLUtil;
import com.etel.utils.ImageUtils;
import com.etel.utils.UserUtil;
import com.wavemaker.runtime.RuntimeAccess;
import com.wavemaker.runtime.security.SecurityService;

public class SigcardServiceImpl implements SigcardService {

	SecurityService service = (SecurityService) RuntimeAccess.getInstance().getServiceBean("securityService");
	private String wsurl = ConfigPropertyUtil.getPropertyValue("ws_url");
	private Map<String, Object> param = HQLUtil.getMap();
	private DBService dbService = new DBServiceImpl();

	@Override
	public String saveSigcard(String filename, String acctno) {
		// TODO Auto-generated method stub
		String result = "0";
		try {
			File dir = new File(
					RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/tempdir/"));
			if (!dir.exists())
				dir.mkdirs();
			String input = RuntimeAccess.getInstance().getSession().getServletContext()
					.getRealPath("resources/tempdir/" + filename);
			File file = new File(input);
			BufferedImage image = ImageIO.read(file);
			BufferedImage resized = resize(image, 500, 500);
			String url = wsurl + "/csr/sigcard/upload";
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(resized, "png", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			ByteArrayOutputStream compressbaos = new ByteArrayOutputStream(imageInByte.length);
			GZIPOutputStream gzipOS = new GZIPOutputStream(compressbaos);
			gzipOS.write(imageInByte);
			gzipOS.close();
			SigcardUploadForm form = new SigcardUploadForm();
			form.setSigcard(compressbaos.toByteArray());
			form.setAccoutno(acctno);
			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(form);
			OutputStream os = con.getOutputStream();
			os.write(jsonData.getBytes());
			os.flush();
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				result = "1";
			}
			con.disconnect();
			file.delete();
			// BufferedImage image = ImageIO.read(file);
			// BufferedImage resized = resize(image, 2100, 1300);
			// String sigDir = RuntimeAccess.getInstance().getSession().getServletContext()
			// .getRealPath("resources/sigcard_dir/");
			// File dir = new File(sigDir);
			// if (!dir.exists())
			// dir.mkdirs();
			// File output = new File(sigDir + "/" + cifno + ".png");
			// File delexist = new File(sigDir + "/" + cifno + ".png");
			// if (output.exists()) {
			// delexist.delete();
			// }
			// ImageIO.write(resized, "png", output);
			// file.delete();
			// result = "1";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static BufferedImage resize(BufferedImage img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	@Override
	public String viewSigcard(String acctno) {
		// TODO Auto-generated method stub
		String result = "0";
		try {
			String url = wsurl + "/csr/sigcard/view/" + acctno;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			System.out.println("Response Code : " + con.getResponseCode());
			if (con.getResponseCode() == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				ObjectMapper mapper = new ObjectMapper();
				SigcardUploadForm form = mapper.readValue(in.readLine(), SigcardUploadForm.class);
				String filename = "/resources/sigcard_dir/"
						+ UserUtil.getUserByUsername(service.getUserName()).getUserid();
//				System.out.println("ez" + form);
				String sigDir = RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("");
				File dir = new File(sigDir + filename);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				InputStream ins = new ByteArrayInputStream(form.getSigcard());
				BufferedImage image = ImageIO.read(ins);
				File output = new File(dir + "/" + acctno + ".png");
				ImageIO.write(image, "png", output);
				System.out.println(output.getAbsolutePath());
				String imageurl = "resources/sigcard_dir/"
						+ UserUtil.getUserByUsername(service.getUserName()).getUserid() + "/" + acctno + ".png";
				System.out.println("image url :" + imageurl);
				return imageurl;
			}
			con.disconnect();
			// String filename = "/resources/sigcard_dir/"+cifno+".png";
			// String sigDir =
			// RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("");
			// File dir = new File(sigDir + filename);
			// System.out.println("DIRECTORY "+dir);
			// if (dir.exists()) {
			// result = filename.substring(1, filename.length());
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public String saveBulkFile(Tbbatchfile data) {
		// TODO Auto-generated method stub
		String result = "0";
		try {
			String input = RuntimeAccess.getInstance().getSession().getServletContext()
					.getRealPath("resources/tempdir/" + data.getBatchfilename());
			File file = new File(input);
			String url = wsurl + "/util/bulk/upload";
			URL obj = new URL(url);

			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			BulkForm form = new BulkForm();
			form.setFileData(Files.readAllBytes(Paths.get(input)));
			form.setData(data);

			ObjectMapper mapper = new ObjectMapper();
			String jsonData = mapper.writeValueAsString(form);
			OutputStream os = con.getOutputStream();
			os.write(jsonData.getBytes());
			os.flush();
			if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
				result = "1";
			}
			con.disconnect();
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<Tbbatchfile> getBatchList(Integer batchstatus) {
		// TODO Auto-generated method stub
		/*
		 * batchstatus 9 = all, 0 = pending, 1 = processed
		 */
		try {
			String url = wsurl + "/util/bulk/get/" + batchstatus.toString();
			URL obj = new URL(url);
			System.out.println(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			System.out.println("Response Code : " + con.getResponseCode());
			if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				ObjectMapper mapper = new ObjectMapper();
				BatchFileListHandler form = mapper.readValue(in.readLine(), BatchFileListHandler.class);
				return form.getBatchlist();
			}

			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String readBulk(Integer id) {
		// TODO Auto-generated method stub
		String result = "0";
		try {
			ObjectMapper mapper = new ObjectMapper();
			URL url = new URL(wsurl + "/util/bulk/read");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			String jsonData = mapper.writeValueAsString(id);
			OutputStream os = con.getOutputStream();
			os.write(jsonData.getBytes());
			os.flush();
			if (con.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED) {
				result = "1";
			}
			con.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tbdepositcif> bulkAccountCreation(String filename) {
		// TODO Auto-generated method stub
		String input = RuntimeAccess.getInstance().getSession().getServletContext()
				.getRealPath("resources/tempdir/" + filename);
		List<Tbdepositcif> list = new ArrayList<Tbdepositcif>();
		AccountService acctService = new AccountServiceImpl();
		try {

			try {
				StringBuilder strCifList = new StringBuilder();
				BufferedReader br = new BufferedReader(new FileReader(input));
				String sCurrentLine;
				while ((sCurrentLine = br.readLine()) != null) {
					String[] strData = sCurrentLine.split("\\|");
					strCifList.append(strCifList.length() == 0 ? "'" + strData[0] + "'" : "," + "'" + strData[0] + "'");

					Tbdeposit inDep = new Tbdeposit();
					inDep.setUnit(service.getUserId().substring(0, 4));
					inDep.setAccountStatus(1);
					inDep.setAccountName(strData[1]);
					inDep.setProductCode("20");
					inDep.setSubProductCode("44");
					inDep.setOwnershipType(0);
					inDep.setJointAcctType("0");
					inDep.setPosttx("00");
					inDep.setAccountBalance(BigDecimal.ZERO);
					inDep.setFloatAmount(BigDecimal.ZERO);
					inDep.setPlaceholdAmt(BigDecimal.ZERO);
					inDep.setPlacementAmt(BigDecimal.ZERO);
					inDep.setAlertflag(0);
					inDep.setBookDate(new Date());
					inDep.setSlaidNo("");

					List<Tbdepositcif> listIn = new ArrayList<Tbdepositcif>();
					Tbdepositcif cifIn = new Tbdepositcif();
					cifIn.setCifno(strData[0]);
					cifIn.setCifname(strData[1]);
					listIn.add(cifIn);

					acctService.createAccount(inDep, listIn);

				}
				br.close();
				System.out.println(strCifList.toString());
				param.put("ciflist", strCifList.toString());
				list = (List<Tbdepositcif>) dbService.execStoredProc(
						"SELECT * FROM TBDEPOSITCIF WHERE cifno IN ( :ciflist" + ")", param, Tbdepositcif.class, 1,
						null);
				System.out.println(">> LIST " + list.size());
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// CED 12202020
	@Override
	public String viewSigByAcctNo(String acctNo) {
		String filepath = null;
		String ext = null;
		DBService dbService = new DBServiceImpl();
		Tbsigcard doc = new Tbsigcard();
		File dir = new File(
				RuntimeAccess.getInstance().getSession().getServletContext().getRealPath("resources/docdir"));
		Map<String, Object> params = HQLUtil.getMap();
		params.put("acctNo", acctNo);
		doc = (Tbsigcard) dbService.executeUniqueHQLQuery("FROM Tbsigcard WHERE accountno=:acctNo", params);
		if (doc != null) {
			try {

				if (doc.getSigbasecode() != null) {
//					ImageUtils.base64ToPDF(doc.getSigbasecode(), dir.toString() + "\\", doc.getSigfilename());
//					ext = doc.getSigfilename().substring(doc.getSigfilename().lastIndexOf("."));
//					if (ext.equals(".pdf")) {
//						filepath = "resources/docdir/" + doc.getSigfilename();
//					} else {
//						filepath = ImageUtils.ImageToPDF("resources/docdir/", doc.getSigfilename());
//					}
					if (ImageUtils
							.base64ToImage(doc.getSigbasecode(), dir.toString() + "\\" + doc.getSigfilename() + ".png")
							.equals("success"))
						return "resources/docdir/" + doc.getSigfilename() + ".png";
				} else {
					return "failed";
				}
				// System.out.println(filepath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			return "failed";
		}
		return filepath;
	}

	@Override
	public String saveDepositSigcard(Tbdeposit dep) {
		DBService dbsrvc = new DBServiceImpl();
		param.put("acctno", dep.getAccountNo());
		try {
			Tbsigcard deposit = (Tbsigcard) dbsrvc.executeUniqueHQLQuery("FROM Tbsigcard WHERE accountno=:acctno",
					param);
			if (deposit == null) {
				deposit = new Tbsigcard();
				deposit.setAccountno(dep.getAccountNo());
			}
			String path = RuntimeAccess.getInstance().getSession().getServletContext()
					.getRealPath("resources/tempdir/" + dep.getSigfilename());
			String code = ImageUtils.imageToBase64(path);
			deposit.setSigbasecode(code.toString());// CED SIGCARD
			String filename = dep.getSigfilename() != null ? dep.getSigfilename().replace(" ", "") : "";
			deposit.setSigfilename(filename.substring(0, filename.lastIndexOf(".")));

			if (dbsrvc.saveOrUpdate(deposit)) {
				Tbfilehistory hist = new Tbfilehistory();
				hist.setFilecode(dep.getSigbasecode());
				hist.setFilename(dep.getSigfilename());
				hist.setAccountnumber(dep.getAccountNo());
				hist.setCreatedby(UserUtil.getUserByUsername(service.getUserName()).getUserid());
				hist.setDatecreated(new Date());
				hist.setBranchcode(UserUtil.getUserByUsername(service.getUserName()).getBranchcode());
				dbsrvc.save(hist);
				return "success";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "failed";
	}
}
