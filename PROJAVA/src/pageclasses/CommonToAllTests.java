package pageclasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.vimalselvam.testng.listener.ExtentTestNgFormatter;

import utilities.ProjectProperties;
import utilities.webdriverMethods;
import utilities.FileMethods;
import utilities.GenericMethods;

public class CommonToAllTests 
{

	public static WebDriver driver=null;
	static String sProjectPath = System.getProperty("user.dir");
	static String sCurrentDate = GenericMethods.currentDateAddition("dd-MMM-yyyy", 0);
	static String sPDFFilePath = ProjectProperties.sTestOutputFolder;
	static String sHTMLReportPath = ProjectProperties.sTestOutputFolder + "report.html";
	static String sDSFolder = ProjectProperties.ScreenshotsFolder + sCurrentDate;
	static String filePath = ProjectProperties.sTestInputFolder + ProjectProperties.sTestDataFile + ".xlsx";
	ExtentTestNgFormatter TestFormatter;

	static Properties props = new Properties();

	//static LinkedHashMap<String,String> suiteResults = new LinkedHashMap<String,String>();
	static ArrayList<String> sTestResults = new ArrayList<String>();

	String sResult;
	String sStartTime;
	String sEndTime;
	String sTestCaseName;
	String sSuiteRunTime;
	String sSuiteName;

	//Read Global Test Data
	static String sGlobalData[] = new String[4];
	static String sGlobalEnv = "";
	static String sGlobalURL = "";
	static String sGlobalBrowser = "";
	static int iGlobalTimeout;

	@BeforeSuite()
	public void A_Setup_Screenshots_Directory() throws IOException
	{
		InputStream projectTestData = null;
		try 
		{
			String PropertiesFilePath = ProjectProperties.sTestInputFolder + ProjectProperties.sPropertiesFileName;
			projectTestData = new FileInputStream (new File (PropertiesFilePath));
			try 
			{
				props.load(projectTestData);
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException fe)
		{
			System.out.println("File is not present in the location");
			fe.getMessage();			
		}

		//suiteResults.put("TEST DESCRIPTION", "START TIME&&END TIME&&STATUS");
		sSuiteRunTime = GenericMethods.getCurrentDateAndTimeFormat("YYYY-MM-dd hh:mm:ss");
		//System.out.println("Suite Start Time is : " + sSuiteStartTime);
		sTestResults.add("SUITE_NAME&&SUITE_RUN_TIME&&TEST_CASE_NAME&&TEST_CASE_RESULT&&TEST_CASE_START_TIME&&TEST_CASE_END_TIME");
		String sSFolder = System.getProperty("user.dir") + "\\test-output\\Screenshots\\";
		String sFolderName = GenericMethods.currentDateAddition("dd-MMM-yyyy",0);
		File theDir = new File(ProjectProperties.ScreenshotsFolder + sFolderName);
		if (!theDir.exists()) 
		{
			System.out.println("Creating directory: " + theDir.getName());
			boolean result = false;
			try{
				theDir.mkdir();
				result = true;
			} 
			catch(SecurityException se){
				//handle it
			}        
			if(result) {    
				System.out.println("DIR created");  
			}
		}
		else
		{
			FileUtils.cleanDirectory(theDir);
			//System.out.println("Directory is already available. Cleaned the directory.");
		}

		//Read Global Test Data (Browser, Application URL, TimeOut, etc)
		sGlobalData = FileMethods.read_Global_Data_XML();
		sGlobalEnv = sGlobalData[0];
		sGlobalBrowser = sGlobalData[1];
		sGlobalURL = sGlobalData[2];
		iGlobalTimeout = Integer.parseInt(sGlobalData[3]);
		System.out.println("Environment: " + sGlobalEnv + ": Application URL --> " + sGlobalURL + ": Browser --> " + sGlobalBrowser + ": TImeout --> " + iGlobalTimeout);

	}

	@BeforeSuite
	public void B_Create_New_Results_Sheet() throws Exception
	{
		FileMethods.checkFileExists("Test_Results.xlsx");
		FileMethods.DeleteAndCreatesheet("Test_Results.xlsx","Results");
		//System.out.println("Created new results sheet.");
	}

	@BeforeSuite
	public void C_Get_Project_Suite_Details(ITestContext ctx) {
		sSuiteName = ctx.getCurrentXmlTest().getSuite().getName();
		System.out.println("Suite Name is : " + sSuiteName);
	}

	@BeforeTest()
	public static WebDriver launch_browser() throws InterruptedException
	{
		driver = webdriverMethods.launch_browser(sGlobalBrowser, sGlobalURL);
		AppPage.sEmaitext.append("Please find the <font><b>" + props.getProperty("EmailSubject") + "</b></font> results below. Refer the attached files for more details.<br>" + "\n");
		AppPage.sEmaitext.append("<br>");
		return driver;
	}

	@BeforeMethod
	public void write_MethodNameBefore(ITestResult result)
	{
		sStartTime = GenericMethods.getCurrentTime("YYYY-MM-dd hh:mm:ss");
		//System.out.println(result.getMethod().getMethodName() + " :: Start time is : " + sStartTime);
	}

	@AfterMethod
	public void write_MethodNameAfter(ITestResult result)
	{
		sEndTime = GenericMethods.getCurrentTime("YYYY-MM-dd hh:mm:ss");
		sTestCaseName = result.getMethod().getMethodName();
		//System.out.println(result.getMethod().getMethodName() + " :: End time is : " + sEndTime);
		//System.out.println("Test Results is : " + result.getStatus());
		if(result.getStatus()==1)
		{
			sResult = "PASS";
		}
		else if(result.getStatus()==2)
		{
			sResult = "FAIL";
		}
		else
		{
			sResult = "SKIP";
		}
		//suiteResults.put(sTestCaseName, sStartTime + "&&" + sEndTime + "&&" + sResult);
		sTestResults.add(sSuiteName + "&&" +sSuiteRunTime + "&&" + sTestCaseName + "&&" + sResult + "&&"+ sStartTime + "&&" + sEndTime);
	}

	@AfterSuite
	public void B_Send_Email() throws MalformedURLException, DocumentException, IOException
	{
		//System.out.println("Suite Results data is : " + suiteResults);
		System.out.println("Suite Results data in Array List is : " + sTestResults);
		FileMethods.update_test_results_ArrayList("Test_Report", "Test_Results", sTestResults);
		GenericMethods.Generate_PDF();
		String sSendEmailFlag = props.getProperty("SendEmailFlag");
		
		//New Content
		AppPage.sEmaitext.append("<table style=\"width:50%;border:1px solid black;border-collapse:collapse;\">");
		AppPage.sEmaitext.append("<tr>");
		AppPage.sEmaitext.append("<th style=\"border:1px solid black;border-collapse:collapse;padding: 5px;background-color:Orange;\">Firstname</th>");
		AppPage.sEmaitext.append("<th style=\"border:1px solid black;border-collapse:collapse;padding: 5px;background-color:Orange;\">Lastname</th>");
		AppPage.sEmaitext.append("<th style=\"border:1px solid black;border-collapse:collapse;padding: 5px;background-color:Orange;\">Age</th>");
		AppPage.sEmaitext.append("</tr>");
		AppPage.sEmaitext.append("<tr>");
		AppPage.sEmaitext.append("<td style=\"border:1px solid black;border-collapse:collapse;padding: 5px;\">Jill</td>");
		AppPage.sEmaitext.append("<td style=\"border:1px solid black;border-collapse:collapse;padding: 5px;\">Smith</td>");
		AppPage.sEmaitext.append("<td style=\"border:1px solid black;border-collapse:collapse;padding: 5px;\">50</td>");
		AppPage.sEmaitext.append("</tr>");
		AppPage.sEmaitext.append("</table>");
		AppPage.sEmaitext.append("<br>");
		AppPage.sEmaitext.append("End of the email.");
		

		System.out.println("Send Email flag is : " + sSendEmailFlag);
		if(sSendEmailFlag.equalsIgnoreCase("YES"))
		{
			String sFrom = (String) props.getProperty("FromEmailId");
			String sTo = props.getProperty("ToEmailIDs");
			String sCC = props.getProperty("CCEmailIDs");
			String sBCC = props.getProperty("BCCEmailIDs");
			GenericMethods.SendEmailUsingSMTP(props,sTo,sCC,sBCC,AppPage.sEmaitext,"Test_Report.xlsx","","Automation_Results.pdf");
		}
	}

	@AfterSuite
	public void C_Update_Results_To_DB() throws MalformedURLException, DocumentException, IOException
	{
		String sUpdateDBResultsFlag = props.getProperty("UpdateDBResultsFlag");
		System.out.println("Update Results to DB flag is : " + sUpdateDBResultsFlag);
		if(sUpdateDBResultsFlag.equalsIgnoreCase("YES"))
		{
			String ssqlMachineName = (String) props.getProperty("sqlMachineName");
			String ssqlPortNumber = props.getProperty("sqlPortNumber");
			String ssqlUsername = props.getProperty("sqlUsername");
			String ssqlPassword = props.getProperty("sqlPassword");
			String ssqlDBName = props.getProperty("sqlDBName");
			String ssqlTable = props.getProperty("sqlTable");
			String sUpdateResultsFlag = props.getProperty("UpdateDBResultsFlag");
			FileMethods.SQL_Update_Results_To_DB(ssqlMachineName,ssqlPortNumber,ssqlUsername,ssqlPassword,ssqlDBName,ssqlTable,sTestResults);
		}
	}

}
