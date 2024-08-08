package com.etel.email.scheduler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.AuthenticationFailedException;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.scheduling.annotation.Scheduled;

import com.etel.forms.FormValidation;
import com.etel.utils.EncryptDecryptUtil;
import com.coopdb.COOPDB;
import com.coopdb.data.Tbproperties;
import com.coopdb.data.Tbsmtp;
import com.wavemaker.runtime.service.annotations.HideFromClient;

/**
 * @author Kevin
 */
public class EmailScheduler {
	private static String path;
	private static String imagePath;
	private static final Date systemUptime = new Date();

	private static String smtpUserEmailAdd;
	private static String smtpPassword;
	private static String smtpEmailAddAlias;
	private static String smtpHost;
	private static String smtpPort;

	private static boolean isDisabled;
	private static Integer timer;

	@Autowired
	private COOPDB coopDB = null;

	@SuppressWarnings("static-access")
	public EmailScheduler() {
		try {
			// Path Directory
			URL resource = EmailScheduler.class.getResource("/");
			this.path = String.valueOf(Paths.get(resource.toURI()).toFile()).replace("WEB-INF\\classes", "resources\\properties");
			this.imagePath = String.valueOf(Paths.get(resource.toURI()).toFile()).replace("WEB-INF\\classes", "resources\\images");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@HideFromClient
	@Required
	public void setDbService(COOPDB coopDB) {
		this.coopDB = coopDB;
	}

	/***********************************************************************************************************
	 ******************************************** >>> INVOKER <<< **********************************************
	 ***********************************************************************************************************/
	@SuppressWarnings({ "unchecked", "static-access" })
	@Scheduled(fixedDelay = 5000) // 300000 milliseconds = 5 minutes
	public void emailScheduleInvoker() {
		Session session = coopDB.getDataServiceManager().getSession();
		SessionFactory sFactory = session.getSessionFactory();
		Session dbService = sFactory.openSession();
		try {
			// SMTP Properties
			Query queryConfig = dbService.createQuery("FROM Tbproperties");
			Tbproperties conf = (Tbproperties) queryConfig.setMaxResults(1).uniqueResult();
			if(conf != null){
				EncryptDecryptUtil en = new EncryptDecryptUtil();
				smtpUserEmailAdd = conf.getSmtpEmailaddress();
				smtpPassword = en.decrypt(conf.getSmtpPassword());
				smtpEmailAddAlias = conf.getSmtpEmailaddalias();
				smtpHost = conf.getSmtpHost();
				smtpPort = conf.getSmtpPort();
				isDisabled = conf.getSmtpIsdisabled();
				timer = conf.getSmtpTimeinterval();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (isDisabled==false) {
			try {
				// Timer - default 10 seconds >> (10000 - 5000) = 5000
				long milliseconds = timer==null? 5000: timer !=null && timer < 5000? 5000 : timer - 5000;
				Thread.sleep(milliseconds);

				Query queryGetEmailList = dbService.createQuery("FROM Tbsmtp WHERE flag='0' AND dateadded >= :sysUptime").setParameter("sysUptime", systemUptime);
				List<Tbsmtp> email = queryGetEmailList.list();
				if (email.isEmpty() || email == null) {
				} else {
					for (Tbsmtp e : email) {
						if(e.getRecipient()!= null && e.getRecipient() != ""){
							FormValidation form = new FormValidation();
							if (isEmailServerReachable()) {
								form = sendEmail(e.getRecipient(), e.getCc(), e.getBcc(), e.getSubject(), e.getBody());
							}
							if (form.getFlag() != null && form.getFlag().equalsIgnoreCase("success")) {
								e.setFlag(1);
								e.setSender(smtpUserEmailAdd);
								e.setDatesent(new Date());
								dbService.saveOrUpdate(e);
								dbService.beginTransaction().commit();
							}
						}
					}
				}
			} catch (Exception e) {
				dbService.beginTransaction().rollback();
				e.printStackTrace();

				/*--------Error Handling------*/
				File request = new File(path + "\\EmailSchedulerErrorLogs.txt");
				request.getParentFile().mkdirs();
				try {
					StackTraceElement[] elements = e.getStackTrace();
					for (int iterator = 1; iterator <= elements.length; iterator++) {
						if (!request.exists()) {
							request.getParentFile().mkdirs();
						}
						// Get length of file in bytes
						long fileSizeInBytes = request.length();
						// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
						long fileSizeInKB = fileSizeInBytes / 1024;
						// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
						long fileSizeInMB = fileSizeInKB / 1024;
						FileWriter fw = new FileWriter(request, true);

						if (fileSizeInMB < 5) {
							fw.write(">>>>Email Sending Failed: "
									+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S").format(new Date()));
							fw.write(System.getProperty("line.separator"));
							fw.write("     " + e.toString());
							fw.write(System.getProperty("line.separator"));
							fw.write("     Class Name:" + elements[iterator - 1].getClassName() + ".java Method Name:"
									+ elements[iterator - 1].getMethodName() + " Line Number:"
									+ elements[iterator - 1].getLineNumber());
							fw.write(System.getProperty("line.separator"));
							fw.write(System.getProperty("line.separator"));
							fw.close();
						} else {
							PrintWriter writer = new PrintWriter(request);
							writer.print("");
							writer.close();
						}
						fw.close();
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			} // end first catch
			dbService.close();
		}
	}

	/***************************************************************************************************
	 ****************************************** UTILITIES **********************************************
	 ***************************************************************************************************/

	@Test
	private static boolean isEmailServerReachable() {
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", true); // added this line
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.user", smtpUserEmailAdd);
		props.put("mail.smtp.password", smtpPassword);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", true);
		javax.mail.Session s = javax.mail.Session.getInstance(props, null);
		try {
			Transport transport = s.getTransport("smtp");
			transport.connect(smtpHost, smtpUserEmailAdd, smtpPassword);
			if (transport.isConnected()) {
				return true;
			}
		} catch (AuthenticationFailedException e) {
			System.out.println(">>>SMTP Invalid username or password. " + e.getMessage());
			return false;
		} catch (MessagingException e) {
			System.out.println(">>>Can't connect to smtp server. " + e.getMessage());
			return false;
		}
		return false;
	}

	/** HTML content convert to string */
	public static String readHtml(String htmlFilename) {
		File fileDir = new File(new File(path + "\\emailformat"), htmlFilename);
		StringBuilder contentBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileDir));
			String str;
			while ((str = in.readLine()) != null) {
				contentBuilder.append(str);
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String content = contentBuilder.toString();
		return content;
	}

	@Test
	public static FormValidation sendEmail(String recipient, String CC, String BCC, String subject,
			String bodyMessage) {
		FormValidation form = new FormValidation();
		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable", true);
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.user", smtpUserEmailAdd);
		props.put("mail.smtp.password", smtpPassword);
		props.put("mail.smtp.port", smtpPort);
		props.put("mail.smtp.auth", true);

		javax.mail.Session session = javax.mail.Session.getInstance(props, null);
		MimeMessage message = new MimeMessage(session);
		try {
			InternetAddress from = new InternetAddress(smtpUserEmailAdd, smtpEmailAddAlias);
			message.setSubject(subject);
			message.setFrom(from);

			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			if (CC != null && CC != "") {
				message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(CC));
			}
			if (BCC != null && BCC != "") {
				message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(BCC));
			}
			// This mail has 2 part, the BODY and the embedded image
			MimeMultipart multipart = new MimeMultipart("alternative");

			// >>>>First part (the html)
			BodyPart messageBodyPart = new MimeBodyPart();
			// String htmlText = "<H1>Hello</H1><img src=\"cid:image\">";
			messageBodyPart.setContent(bodyMessage, "text/html");
			// add it
			multipart.addBodyPart(messageBodyPart);

			// >>>>Second part (the image)

			// AcaciaLogo
			messageBodyPart = new MimeBodyPart();
			DataSource acacialogo = new FileDataSource(imagePath + "\\emailheader.png");
			// System.out.println(imagePath + "\\emailheader.png");
			messageBodyPart.setDataHandler(new DataHandler(acacialogo));
			messageBodyPart.setHeader("Content-ID", "<acacialogo>");
			multipart.addBodyPart(messageBodyPart);
			// Eteligent Logo
			messageBodyPart = new MimeBodyPart();
			DataSource etellogo = new FileDataSource(imagePath + "\\etellogo.png");
			// System.out.println(imagePath + "\\etellogo.png");
			messageBodyPart.setDataHandler(new DataHandler(etellogo));
			messageBodyPart.setHeader("Content-ID", "<etellogo>");
			multipart.addBodyPart(messageBodyPart);

			// Add html part to multi part
			multipart.addBodyPart(messageBodyPart);

			// Associate multi-part with message
			message.setContent(multipart);
			if(recipient !=null && recipient !=""){
				// Send message
				Transport transport = session.getTransport("smtp");
				transport.connect(smtpHost, smtpUserEmailAdd, smtpPassword);
				System.out.println(">>>>>>>>>>>> Sending Email... "+ new SimpleDateFormat("EEEE - MMMM dd, yyyy hh:mm a").format(new Date()) + " <<<<<<<<<<<<");
				transport.sendMessage(message, message.getAllRecipients());
				transport.close();
				form.setFlag("success");
				form.setErrorMessage("The message has been sent.");
				System.out.println(">>>>>>>>>>>> The message has been sent!  "+ new SimpleDateFormat("EEEE - MMMM dd, yyyy hh:mm a").format(new Date()));
			}
			return form;
		} catch (AuthenticationFailedException e) {
			System.out.println(">>>>>>>>>>>>SMTP Invalid username or password.");
			form.setFlag("failed");
			form.setErrorMessage("Sending Failed! SMTP Invalid username or password.");
			e.printStackTrace();
		} catch (MessagingException e) {
			System.out.println(">>>>>>>>>>>>Sending Failed! SMTP server is unreachable!");
			form.setFlag("failed");
			form.setErrorMessage("Sending Failed! SMTP server is unreachable! Please try again.");
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			form.setFlag("failed");
			form.setErrorMessage("Sending Failed! Please try again.");
		}
		return form;
	}
}
