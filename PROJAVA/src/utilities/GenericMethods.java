package utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

//import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.SlowLoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class GenericMethods 

{

	static WebDriver driver = null;
	static WebDriverWait wait;
	static JavascriptExecutor jsExecutor;
	String sApplicationURL;
	String sBrowser;
	static Properties props = new Properties();
	static String sdriversPath = System.getProperty("user.dir") + "\\drivers\\";

	public static boolean SendEmailUsingSMTP(Properties propsfile,String sToEmailIDs,String sCCEmailIDs,String sBCCEmailIDs,StringBuilder sEmailText,String sFileName1,String sFileName2,String sFileName3) 
	{
		try
		{

			Session sess = Session.getInstance(props);
			//System.out.println("SMTP Auth is : " + propsfile.getProperty("SMTPAuth") + " || " + "TLS Enable is : " + propsfile.getProperty("TSLEnable") + " || " + "SSL Enable is : " + propsfile.getProperty("SSLEnable"));
			props.put("mail.smtp.host",propsfile.getProperty("FromEmailSMTPHost"));
			//System.out.println("SMTP Host: " + propsfile.getProperty("FromEmailSMTPHost"));
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
			}

			MimeBodyPart messageBodyPart4 = new MimeBodyPart();  
			messageBodyPart4.setContent(sEmailText.toString(), "text/html");
			multipart.addBodyPart(messageBodyPart4); 
			message.setContent(multipart);

			System.out.println("Email content is built successfully...");
			Transport.send(message);
			//Transport.send(message, propsfile.getProperty("FromEmailId"),"");
			System.out.println("Email is sent successfully...");
			Thread.sleep(5000);
			return true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}

	//Method to get current Date and Time
	public static String getCurrentDateAndTime()
	{
		try
		{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");  
			LocalDateTime now = LocalDateTime.now();
			String currenttime = dtf.format(now);
			return currenttime;
		}
		catch(Exception e)
		{
			System.out.println("Exception occured: " + e);
			System.out.println("Unable to get Current Date and Time.");
			Assert.fail();	
		}
		return null;
	}

	public static String getCurrentDateAndTime(String sFormat)
	{
		try
		{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(sFormat);  
			LocalDateTime now = LocalDateTime.now();
			String currenttime = dtf.format(now);
			return currenttime;
		}
		catch(Exception e)
		{
			System.out.println("Exception occured: " + e);
			System.out.println("Unable to get Current Time.");
			Assert.fail();	
		}
		return null;
	}

	public static String currentDateAddition(String sDateFormat,int i)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(sDateFormat);
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_MONTH, i);  
			String newDate = sdf.format(cal.getTime());  
			return newDate;
		}
		catch(Exception e)
		{
			System.out.println("Exception occured: " + e);
			System.out.println("Unable to get Current Date and Time.");
		}
		return null;
	}

	//Method to get current Date and Time
	public static String getCurrentTime(String sFormat)
	{
		try
		{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(sFormat);  
			LocalDateTime now = LocalDateTime.now();
			String currenttime = dtf.format(now);
			return currenttime;
		}
		catch(Exception e)
		{
			System.out.println("Exception occured: " + e);
			System.out.println("Unable to get Current Time.");
			Assert.fail();	
		}
		return null;
	}

	//Method to get current Date and Time
	public static String getCurrentDateAndTimeStamp()
	{
		try
		{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm:ss");  
			LocalDateTime now = LocalDateTime.now();
			String currenttime = dtf.format(now).toString();
			String sTime = currenttime.replace("/","").replace(":","").replace(" ","");
			return sTime;
		}
		catch(Exception e)
		{
			System.out.println("Exception occured: " + e);
			System.out.println("Failed to get Current Time.");
			Assert.fail();	
		}
		return null;
	}

	public static String getCurrentDateAndTimeFormat(String sDateFormat)
	{
		try
		{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(sDateFormat);  
			LocalDateTime now = LocalDateTime.now();
			String currenttime = dtf.format(now).toString();
			return currenttime;
		}
		catch(Exception e)
		{
			System.out.println("Exception occured: " + e);
			System.out.println("Failed to get Current Time.");
			Assert.fail();	
		}
		return null;
	}

	public static String getCurrentTime_with_underscore()
	{
		try
		{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd_MMM_yyyy_hh_mm_a");  
			LocalDateTime now = LocalDateTime.now();
			String currenttime = dtf.format(now);
			return currenttime;
		}
		catch(Exception e)
		{
			System.out.println("Exception occured: " + e);
			System.out.println("Unable to get Current Time.");
			Assert.fail();	
		}
		return null;
	}

	//Method to get current Date
	public static String getCurrentDate()
	{
		try
		{
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MMM-yyyy");  
			LocalDateTime now = LocalDateTime.now();
			String currenttime = dtf.format(now);
			return currenttime;
		}
		catch(Exception e)
		{
			System.out.println("Exception occured: " + e);
			System.out.println("Unable to get Current Date and Time.");
			Assert.fail();	
		}
		return null;
	}

	public static void Generate_PDF() throws DocumentException, MalformedURLException, IOException
	{
		//System.out.println("Inside Generate PDF Method");
		String sCurrentDate = GenericMethods.currentDateAddition("dd-MMM-yyyy", 0);
		String sCurrentDateTime = GenericMethods.currentDateAddition("dd-MMM-yyyy-HH-mm", 0);
		FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir") + "\\test-output\\" + "Automation_Results.pdf");
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document,fos);
		document.open();
		File folderLocation = new File(System.getProperty("user.dir") + "//test-output//Screenshots//"+sCurrentDate);
		File[] files = folderLocation.listFiles();
		for(File f: files)
		{
			String sFileName = f.getName();
			//System.out.println("File Name is --> " + sFileName);
			if(sFileName.contains("FAILURE"))
			{
				String sFilePath = ProjectProperties.ScreenshotsFolder + "\\"+sCurrentDate+"\\"+sFileName;
				//System.out.println("File path is : " + sFilePath);
				Image image1 = Image.getInstance(sFilePath);
				image1.scaleToFit(500,500);
				sFileName=sFileName.replace("_.png","");
				sFileName=sFileName.replace("_"," ");
				sFileName = sFileName.replace("FAILURE","");
				document.add(new Paragraph(sFileName + " --> test-case failed."));
				document.add(image1);
				//System.out.println("Added the image --> " + sFileName);
				//document.add(new Paragraph(" "));
				document.newPage();
			}
			else if(sFileName.contains("SUCCESS"))
			{
				String sFilePath = ProjectProperties.ScreenshotsFolder + "\\"+sCurrentDate+"\\"+sFileName;
				Image image1 = Image.getInstance(sFilePath);
				image1.scaleToFit(500,500);
				sFileName = sFileName.replace("_.png","");
				sFileName = sFileName.replace("SUCCESS","").replace("SUCCESS","");
				sFileName = sFileName.replace("_"," ");
				document.add(new Paragraph(sFileName + " --> test-case is successful."));	
				document.add(image1);
				//System.out.println("Added the image --> " + sFileName);
				//document.add(new Paragraph(" "));
				document.newPage();
			}
			else if(sFileName.contains("INFO"))
			{
				String sFilePath = ProjectProperties.ScreenshotsFolder + "\\"+sCurrentDate+"\\"+sFileName;
				Image image1 = Image.getInstance(sFilePath);
				image1.scaleToFit(500,500);
				sFileName = sFileName.replace("_.png","");
				sFileName = sFileName.replace("INFO","").replace("INFO","");
				sFileName = sFileName.replace("_"," ");
				document.add(new Paragraph(sFileName + " --> test-case information."));
				document.add(image1);
				//System.out.println("Added the image --> " + sFileName);
				document.newPage();
			}
			else
			{
				String sFilePath = ProjectProperties.ScreenshotsFolder + "\\"+sCurrentDate+"\\"+sFileName;
				Image image1 = Image.getInstance(sFilePath);
				image1.scaleToFit(500,500);
				sFileName = sFileName.replace("_.png","");
				sFileName = sFileName.replace("EXCEPTION","").replace("EXCEPTION","");
				sFileName = sFileName.replace("_"," ");
				document.add(new Paragraph(sFileName + " --> test-case count not be executed."));
				document.add(image1);
				//System.out.println("Added the image --> " + sFileName);
				document.newPage();
			}
		}
		document.close();
		writer.close();
		System.out.println("PDF File is created successfully...");
	}

}