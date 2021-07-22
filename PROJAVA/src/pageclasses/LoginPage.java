package pageclasses;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import utilities.ProjectProperties;
import utilities.webdriverMethods;

public class LoginPage 
{

	static WebDriver driver;
	static WebDriverWait wait;
	JavascriptExecutor jsExecutor;
	Actions action;

	@FindBy(xpath = AppElements.eCSLPBtnLogin)
	private static WebElement eCSLPBtnLogin;

	@FindBy(xpath = AppElements.eCSLPUsername)
	private static WebElement eCSLPUsername;

	@FindBy(xpath = AppElements.eCSLPPassword)
	private static WebElement eCSLPPassword;

	public LoginPage(WebDriver driver)
	{
		this.driver = driver;
		PageFactory.initElements(driver,this);
		wait = new WebDriverWait(driver,ProjectProperties.iTimeOut);
		jsExecutor = (JavascriptExecutor)driver;
		action = new Actions(driver);	
	}

	//Script to check if Login page is displayed
	public boolean Is_Login_Page_Displayed() throws IOException
	{
		try
		{
			wait.until(ExpectedConditions.visibilityOf(eCSLPBtnLogin));
			wait.until(ExpectedConditions.elementToBeClickable(eCSLPBtnLogin));
			if(eCSLPBtnLogin.isDisplayed())
			{
				System.out.println("Application Login page is displayed successfully.");
				//webdriverMethods.capture_screenshot(driver, "PASS_Login_Page_displayed_successfully_");
				return true;
			}
			System.out.println("Failed to display Application Login page.");
			//webdriverMethods.capture_screenshot(driver, "FAIL_Login_Page_is_not_displayed_");
			return false;
		}
		catch (Exception e)
		{
			System.out.println("Failed to display Application Login page.");
			webdriverMethods.capture_screenshot(driver, "EXCEPTION_Login_Page_is_not_displayed_");
			Assert.fail();
			return false;
		}
	}

	//Script to login to Application
	public boolean Login_To_Application(String sUsername,String sPassword)
	{
		try
		{
			wait.until(ExpectedConditions.visibilityOf(eCSLPBtnLogin));
			wait.until(ExpectedConditions.elementToBeClickable(eCSLPBtnLogin));
			webdriverMethods.enter_text_Alternate_Send_keys(driver, eCSLPUsername, "Username", sUsername);
			webdriverMethods.enter_text_Alternate_Send_keys(driver, eCSLPPassword, "Password", sPassword);
			webdriverMethods.javascript_click_Element(driver, eCSLPBtnLogin, "Login Button");
			return true;
		}
		catch (Exception e)
		{
			System.out.println("Exception Message is : " + e.getMessage());
			System.out.println("Failed to login to Application.");
			Assert.fail();
			return false;
		}
	}

}
