package testscripts;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.vimalselvam.testng.listener.ExtentTestNgFormatter;

import pageclasses.AppPage;
import pageclasses.CommonToAllTests;
import pageclasses.LoginPage;
import utilities.ProjectProperties;
import utilities.FileMethods;
import utilities.GenericMethods;
import utilities.webdriverMethods;

public class Sanity_Check extends CommonToAllTests
{
	static String sProjectPath = System.getProperty("user.dir");
	static String filePath = ProjectProperties.sTestInputFolder + ProjectProperties.sTestDataFile + ".xlsx";
	
	static FileMethods eProjectData = new FileMethods(filePath,"ProjectData");
	static HashMap<String,String> hmProjectData = eProjectData.getTestDataHashMap("YES");
	static HashMap<String,Integer> GgutidataBefore=new HashMap<String,Integer>();
	
	String hmUsername = hmProjectData.get("username");
	String hmPassword = hmProjectData.get("password");

	@Test(priority=0)
	public void Application_Login_Check() throws Exception
	{
		AppPage oAppPage = new AppPage(driver);
		oAppPage.Launch_URL("Google Maps", "https://www.google.co.in/maps","Google Maps");
		System.out.println("***********************************************************");
	}
	
	@Test(priority=1)
	public void Launch_Youtube() throws Exception
	{
		AppPage oAppPage = new AppPage(driver);
		oAppPage.Launch_URL("Youtube", "https://www.youtube.com/","YouTube");
		System.out.println("***********************************************************");
	}
	
}