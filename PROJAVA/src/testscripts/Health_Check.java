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

public class Health_Check extends CommonToAllTests
{
	static String sProjectPath = System.getProperty("user.dir");
	static String filePath = ProjectProperties.sTestInputFolder + ProjectProperties.sTestDataFile + ".xlsx";
	
	static FileMethods eProjectData = new FileMethods(filePath,"ProjectData");
	static HashMap<String,String> hmProjectData = eProjectData.getTestDataHashMap("YES");
	
	String hmUsername = hmProjectData.get("username");
	String hmPassword = hmProjectData.get("password");
	static HashMap<String,Integer> GgutidataBefore=new HashMap<String,Integer>();

	@Test(priority=0)
	public void Launch_Gmail() throws Exception
	{
		System.out.println("Project Data HashMap is : " + hmProjectData);
		AppPage oAppPage = new AppPage(driver);
		oAppPage.Launch_URL("Gmail", "https://accounts.google.com/","Sign in – Google accounts");
		System.out.println("***********************************************************");
	}
	
	@Test(priority=1,dependsOnMethods = {"Launch_Gmail"},enabled=false)
	public void Launch_Youtube() throws Exception
	{
		AppPage oAppPage = new AppPage(driver);
		oAppPage.Launch_URL("Youtube", "https://www.youtube.com/","YouTube");
		System.out.println("***********************************************************");
	}
	
	@Test(priority=2,dependsOnMethods = {"Launch_Gmail"},enabled=false)
	public void Launch_Google_Maps() throws Exception
	{
		AppPage oAppPage = new AppPage(driver);
		oAppPage.Launch_URL("Google Maps", "https://www.google.co.in/maps","Google Maps");
		System.out.println("***********************************************************");
	}
	
}