package testscenarios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import pageclasses.AppPage;
import utilities.GenericMethods;
import utilities.ProjectProperties;

public class StringBuilderTest 
{
	static Properties props = new Properties();

	public static void main(String[] args) throws IOException
	{	
		StringBuilder sAppText = new StringBuilder();
		ArrayList<String> sTestResults = new ArrayList<String>();
		sTestResults.add("SUITE_NAME&&SUITE_RUN_TIME&&TEST_CASE_NAME&&TEST_CASE_RESULT&&TEST_CASE_START_TIME&&TEST_CASE_END_TIME");
		sTestResults.add("Project_Java&&2021-03-18 02:54:27&&Application_Login_Check&&PASS&&2021-03-18 02:54:39&&2021-03-18 02:54:59");
		sTestResults.add("Project_Java&&2021-03-18 02:54:27&&Launch_Youtube&&FAIL&&2021-03-18 02:54:59&&2021-03-18 02:55:27");
		sTestResults.add("Project_Java&&2021-03-18 02:54:27&&Launch_Google_Maps&&PASS&&2021-03-18 02:54:59&&2021-03-18 02:55:27");
		StringBuilder sTableText = Capture_Results_As_Email(sTestResults,"#00b300","#ff704d");
		sAppText.append(sTableText);
		InputStream projectTestData = null;
		String PropertiesFilePath = ProjectProperties.sTestInputFolder + ProjectProperties.sPropertiesFileName;
		projectTestData = new FileInputStream (new File (PropertiesFilePath));
		System.out.println("Properties values are :: " + props.values().toString());
		props.load(projectTestData);
		String sFrom = (String) props.getProperty("FromEmailId");
		String sTo = props.getProperty("ToEmailIDs");
		String sCC = props.getProperty("CCEmailIDs");
		String sBCC = props.getProperty("BCCEmailIDs");
		SendEmailUsingSMTP(props,sTo,sCC,sBCC,sAppText,"Automation_Results.pdf","","");

	}

	//Update the test results from HashMap to an Excel file
	public static StringBuilder Capture_Results_As_Email(ArrayList<String> sArrayList,String sSuccessRowBG,String sFailureRowBG)
	{
		StringBuilder sTableText = new StringBuilder();
		sTableText.append("<table style=\"width:20%;border:1px solid black;border-collapse:collapse;\">");
		System.out.println("Array List is : " + sArrayList);
		int sALSize = sArrayList.size();
		System.out.println("HashMap length is : " + sALSize);
		try
		{
			for(int i=0;i<sALSize;i++)
			{
				String sArrayData = sArrayList.get(i);
				//System.out.println("Array Data at index : " + i + " is --> " + sArrayData);
				int iArrayDataCount = sArrayData.split("&&").length;
				if(sArrayData.contains("PASS"))
				{
					sTableText.append("<tr style = \"background-color: " + sSuccessRowBG + ";\">");
				}
				else
				{
					sTableText.append("<tr style = \"background-color: " + sFailureRowBG + ";\">");
				}
				for(int j=0;j<iArrayDataCount;j++)
				{
					String sValue = sArrayData.split("&&")[j];
					if(i==0)
					{
						sTableText.append("<th style=\"border:1px solid black;border-collapse:collapse;padding:5px;background-color:Gray;color:white;\">" + sValue + "</th>");
					}
					else
					{
						sTableText.append("<td style=\"border:1px solid black;border-collapse:collapse;padding:5px;\">" + sValue + "</th>");
					}
				}
				sTableText.append("</tr>");
			}
			sTableText.append("</table>");
			sTableText.append("<br>");
			return sTableText;
		}
		catch(Exception e)
		{
			System.out.println("Could not update the HashMap data to Excel.Exception Message is : " + e.getMessage());
			e.printStackTrace();
		}
		return sTableText;
	}

	public static boolean SendEmailUsingSMTP(Properties propsfile,String sToEmailIDs,String sCCEmailIDs,String sBCCEmailIDs,StringBuilder sEmailText,String sFileName1,String sFileName2,String sFileName3) 
	{
		try
		{
			Session sess = Session.getInstance(props);
			System.out.println("SMTP Auth is : " + propsfile.getProperty("SMTPAuth"));
			System.out.println("TLS Enable is : " + propsfile.getProperty("TSLEnable"));
			System.out.println("SSL Enable is : " + propsfile.getProperty("SSLEnable"));
			props.put("mail.smtp.host",propsfile.getProperty("FromEmailSMTPHost"));
			System.out.println("SMTP Host: " + propsfile.getProperty("FromEmailSMTPHost"));
			props.put("mail.smtp.auth", propsfile.getProperty("SMTPAuth")); 
			props.setProperty("mail.smtp.port", propsfile.getProperty("FromEmailPort"));
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.starttls.enable", propsfile.getProperty("TSLEnable"));
			//props.put("mail.smtp.socketFactory.fallback",true);
			//props.put("mail.debug", "false");
			//props.put("mail.smtp.ssl.trust", "*");
			props.put("mail.smtp.ssl.enable", propsfile.getProperty("SSLEnable"));
			Message message = new MimeMessage(sess);
			message.setSentDate(new Date());
			message.setFrom(new InternetAddress(propsfile.getProperty("FromEmailId")));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sToEmailIDs));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(sCCEmailIDs));
			message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(sBCCEmailIDs));
			message.setSubject(propsfile.getProperty("EmailSubject") + " : " + GenericMethods.getCurrentDateAndTime("dd-MMM-yyyy"));
			System.out.println("From Email ID: " + propsfile.getProperty("FromEmailId"));
			System.out.println("From Email Password: " + propsfile.getProperty("FromEmailPassword"));
			
			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(propsfile.getProperty("FromEmailId"),propsfile.getProperty("FromEmailPassword"));
				}
			});
			
			//First attachment
			Multipart multipart = new MimeMultipart(); 
			MimeBodyPart messageBodyPart1 = new MimeBodyPart();
			String filepath = ProjectProperties.sTestOutputFolder + sFileName1;
			FileDataSource source = new FileDataSource(filepath);  
			messageBodyPart1.setDataHandler(new DataHandler(source));  
			messageBodyPart1.setFileName(sFileName1); 
			multipart.addBodyPart(messageBodyPart1);
			System.out.println("Added file 1 to email content.File Path is : " + filepath);

			//Second attachment
			if(sFileName2.length()>1)
			{
				MimeBodyPart messageBodyPart2 = new MimeBodyPart();
				String filename2 = sFileName2;//change accordingly  
				String filepath2 = ProjectProperties.sTestOutputFolder + sFileName2;
				FileDataSource source2 = new FileDataSource(filepath2);  
				messageBodyPart2.setDataHandler(new DataHandler(source2));  
				messageBodyPart2.setFileName(filename2); 
				multipart.addBodyPart(messageBodyPart2);
				System.out.println("Added file 2 to email content. File Path is : " + filepath2);
			}

			//Third attachment
			if(sFileName3.length()>1)
			{
				MimeBodyPart messageBodyPart3 = new MimeBodyPart();
				String filename3 = sFileName3;//change accordingly  
				String filepath3 = ProjectProperties.sTestOutputFolder + sFileName3;
				FileDataSource source3 = new FileDataSource(filepath3);  
				messageBodyPart3.setDataHandler(new DataHandler(source3));  
				messageBodyPart3.setFileName(filename3); 
				multipart.addBodyPart(messageBodyPart3);
				System.out.println("Added file 3 to email content. File Path is : " + filepath3);
			}

			MimeBodyPart messageBodyPart4 = new MimeBodyPart();  
			messageBodyPart4.setContent(sEmailText.toString(), "text/html");
			multipart.addBodyPart(messageBodyPart4); 
			message.setContent(multipart);
			System.out.println("Built the mail content.");
			Transport.send(message);
			//Transport.send(message, propsfile.getProperty("FromEmailId"),propsfile.getProperty("FromEmailPassword"));
			System.out.println("Email sent successfully.");
			Thread.sleep(5000);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
