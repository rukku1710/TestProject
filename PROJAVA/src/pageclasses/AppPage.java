package pageclasses;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.ProjectProperties;
import utilities.webdriverMethods;
import utilities.FileMethods;
import utilities.GenericMethods;
import utilities.webdriverfunctions.WebDriverMethods;

public class AppPage 
{

	static WebDriver driver;
	static WebDriverWait wait;
	JavascriptExecutor jsExecutor;
	Actions action;

	static String sProjectInputFolder = System.getProperty("user.dir") + "\\TestInputs\\";

	public static StringBuilder sEmaitext = new StringBuilder();

	public AppPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver,this);
		wait = new WebDriverWait(driver,ProjectProperties.iTimeOut);
		jsExecutor = (JavascriptExecutor)driver;
		action = new Actions(driver);	
	}

	public boolean Launch_URL(String Website,String sURL,String sExpectedTitle) throws IOException
	{
		try
		{
			driver.get(sURL);
			Thread.sleep(10000);
			String sTitle = driver.getTitle().trim();
			System.out.println("Page Title for " + Website+ " is : " + sTitle);
			if(sExpectedTitle.equalsIgnoreCase(sTitle))
			{
				System.out.println("Launched URL for " + Website + ".");
				webdriverMethods.capture_screenshot(driver, "SUCCESS_Launched_"+Website+"_URL_");
				return true;
			}
			System.out.println("FAIL::Failed to launch URL for --> " + Website);
			webdriverMethods.capture_screenshot(driver, "FAILURE_Failed_to_launch_"+Website+"_URL_");
			Assert.fail();
			return false;
		}
		catch (Exception e)
		{
			System.out.println("Exception::Exception faced while launching URL for --> " + Website);
			webdriverMethods.capture_screenshot(driver, "EXCEPTION_Failed_to_launch_"+Website+"_URL_");
			Assert.fail();
			return false;
		}
	}



}