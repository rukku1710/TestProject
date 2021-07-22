package utilities;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class webdriverMethods

{
	static WebDriver driver = null;
	static String sProjectPath = System.getProperty("user.dir");

	@BeforeSuite
	//Method to launch Web Browser
	public static WebDriver launch_browser(String sBrowser, String sApplicationURL)
	{
		if(sBrowser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", sProjectPath + "//drivers//" + "chromedriver.exe");
			Map<String, Object> prefs = new HashMap<String, Object>();
			//Put this into prefs map to switch off browser notification
			prefs.put("profile.default_content_setting_values.notifications", 2);
			//Create chrome options to set this prefs
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			driver = new ChromeDriver(options);
			//driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(ProjectProperties.iTimeOut, TimeUnit.SECONDS);
			driver.manage().window().maximize(); 
			driver.get(sApplicationURL);
			System.out.println("Launched the browser --> " + sBrowser + " ; Application URL --> " + sApplicationURL + ".");
			return driver;
		}
		else if(sBrowser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", sProjectPath + "//drivers//" + "geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(ProjectProperties.iTimeOut, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(sApplicationURL);
			System.out.println("Launched the browser --> " + sBrowser + " ; Application URL --> " + sApplicationURL + ".");
			return driver;
		}
		else if(sBrowser.equalsIgnoreCase("ie"))
		{
			System.setProperty("webdriver.ie.driver", sProjectPath + "//drivers//" + "IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.manage().timeouts().implicitlyWait(ProjectProperties.iTimeOut, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(sApplicationURL);
			System.out.println("Launched the browser --> " + sBrowser + " ; Application URL --> " + sApplicationURL + ".");
			return driver;
		}
		else if(sBrowser.equalsIgnoreCase("edge"))
		{
			System.setProperty("webdriver.edge.driver", sProjectPath + "//drivers//" + "msedgedriver.exe");
			driver = new EdgeDriver();
			driver.manage().timeouts().implicitlyWait(ProjectProperties.iTimeOut, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(sApplicationURL);
			//driver.navigate().to(sApplicationURL);
			System.out.println("Launched the browser --> " + sBrowser + " ; Application URL --> " + sApplicationURL + ".");
			return driver;
		}
		return driver;
	}

	public static WebDriver launch_browser_12_Nov(String sBrowser, String sApplicationURL)
	{
		if(sBrowser.equalsIgnoreCase("chrome"))
		{
			System.setProperty("webdriver.chrome.driver", sProjectPath + "//drivers//" + "chromedriver.exe");
			Map<String, Object> prefs = new HashMap<String, Object>();
			//Put this into prefs map to switch off browser notification
			prefs.put("profile.default_content_setting_values.notifications", 2);
			//Create chrome options to set this prefs
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", prefs);
			driver = new ChromeDriver(options);
			//driver = new ChromeDriver();
			driver.manage().timeouts().implicitlyWait(ProjectProperties.iTimeOut, TimeUnit.SECONDS);
			driver.manage().window().maximize(); 
			driver.get(sApplicationURL);
			System.out.println("Launched the browser -> " + sBrowser + " ; Application URL --> " + sApplicationURL + ".");
			return driver;
		}
		else if(sBrowser.equalsIgnoreCase("firefox"))
		{
			System.setProperty("webdriver.gecko.driver", sProjectPath + "//drivers//" + "geckodriver.exe");
			driver = new FirefoxDriver();
			driver.manage().timeouts().implicitlyWait(ProjectProperties.iTimeOut, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(sApplicationURL);
			System.out.println("Launched the browser -> " + sBrowser + " ; Application URL --> " + sApplicationURL + ".");
			return driver;
		}
		else if(sBrowser.equalsIgnoreCase("ie"))
		{
			System.setProperty("webdriver.ie.driver", sProjectPath + "//drivers//" + "IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.manage().timeouts().implicitlyWait(ProjectProperties.iTimeOut, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(sApplicationURL);
			System.out.println("Launched the browser -> " + sBrowser + " ; Application URL --> " + sApplicationURL + ".");
			return driver;
		}
		else if(sBrowser.equalsIgnoreCase("edge"))
		{
			System.setProperty("webdriver.edge.driver", sProjectPath + "//drivers//" + "msedgedriver.exe");
			driver = new EdgeDriver();
			driver.manage().timeouts().implicitlyWait(ProjectProperties.iTimeOut, TimeUnit.SECONDS);
			driver.manage().window().maximize();
			driver.get(sApplicationURL);
			//driver.navigate().to(sApplicationURL);
			System.out.println("Launched the browser -> " + sBrowser + " ; Application URL --> " + sApplicationURL + ".");
			return driver;
		}
		return driver;
	}

	public static String capture_screenshot(WebDriver driver,String sName) throws IOException
	{
		File screenshotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String sFolderPath = ProjectProperties.ScreenshotsFolder;
		String sFolderName = GenericMethods.currentDateAddition("dd-MMM-yyyy", 0);
		File theDir = new File(ProjectProperties.ScreenshotsFolder + sFolderName);
		if (!theDir.exists()) 
		{
			System.out.println("creating directory: " + theDir.getName());
			boolean result = false;
		}
		String sCurrentTime = GenericMethods.currentDateAddition("HH_mm_ss_", 0);
		String sFileName =  sName.replace("/","_");
		//sFileName =sFileName.replace(":","_") + GenericMethods.getCurrentTime("HH_MM_SS");
		sFileName =sFileName.replace(":","_");
		FileUtils.copyFile(screenshotFile, new File(sFolderPath+sFolderName+"/"+sFileName +".png"));
		String sFilePath = sFolderPath+sFolderName+"\\"+sFileName +".png";
		//System.out.println("File Path is : " + sFilePath);
		return sFilePath;
	}

	//Script to click element
	public static boolean clickOnElement(WebDriver driver, WebElement we,String sText)
	{
		WebDriverWait wait = new WebDriverWait(driver, ProjectProperties.iTimeOut);
		try
		{
			wait.until(ExpectedConditions.visibilityOf(we));
			wait.until(ExpectedConditions.elementToBeClickable(we));
			//ScrollToElement(we);
			we.click();
			Reporter.log("Clicked the ***"  + sText +  "*** button/link successfully.");
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Exception message is : " + e.getMessage());
			Reporter.log("Failed to click the ***"  + sText +  "*** button/link.");
			Assert.fail();
			return false;
		}
	}

	public static boolean enter_text_Alternate_Send_keys(WebDriver driver, WebElement we,String SFieldName,String Text) throws IOException
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		try
		{
			wait.until(ExpectedConditions.visibilityOf(we));
			wait.until(ExpectedConditions.elementToBeClickable(we));
			Text.length();
			we.clear();
			for(int i=0;i<Text.length();i++)
			{
				String x = Character.toString(Text.charAt(i));
				we.sendKeys(x);
			}
			System.out.println("Entered the text in the field : ***** " + SFieldName + " ***** as --> ***** " + Text + " *****.");
			Reporter.log("Entered the text in the field : ***** " + SFieldName + " ***** as --> ***** " + Text + " *****.");
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Exception while executing Alternate Send Keys method : " + e.getMessage());
			Reporter.log("Exception while executing Alternate Send Keys method : " + e.getMessage());
			System.out.println("Failed to enter data in the field : ***** " + SFieldName + " ***** as --> ***** " + Text + " *****.");
			Reporter.log("Failed to enter data in the field : ***** " + SFieldName + " ***** as --> ***** " + Text + " *****.");
			capture_screenshot(driver, "FAIL_Alternate_Send_Keys_"+SFieldName+"_");
			Assert.fail();
			return false;
		}
	}

	//Script to scroll to an element
	public static boolean ScrollToElement(WebDriver driver, WebElement j)
	{
		try
		{
			JavascriptExecutor js=(JavascriptExecutor)driver;
			js.executeScript("arguments[0].scrollIntoView(true);",j);
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

	//Method to click a Web Element
	public static boolean javascript_click_Element(WebDriver driver, WebElement we,String sText)
	{
		WebDriverWait wait = new WebDriverWait(driver, ProjectProperties.iTimeOut);
		try
		{
			JavascriptExecutor js=(JavascriptExecutor)driver;
			ScrollToElement(driver, we);
			wait.until(ExpectedConditions.visibilityOf(we));
			wait.until(ExpectedConditions.elementToBeClickable(we));
			js.executeScript("var event = document.createEvent('MouseEvents');" + "event.initEvent('click',true, true);arguments[0].addEventListener('click', function (e) {}, false);" + "arguments[0].dispatchEvent(event);", we);
			System.out.println("Clicked <- "  + sText +  " -> button/link.");
			Reporter.log("Clicked <- "  + sText +  " -> button/link.");
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Exception message is : " + e.getMessage());
			System.out.println("Exception trace is : " + e.getStackTrace());
			Reporter.log("Failed to click <- "  + sText +  " -> button/link.");
			Assert.fail();
			return false;
		}
	}

	//Method to click a Web Element
	public static boolean close_open_windows(WebDriver driver)
	{
		WebDriverWait wait = new WebDriverWait(driver, ProjectProperties.iTimeOut);
		try
		{
			String originalHandle = driver.getWindowHandle();
			System.out.println("Original Window Handle is --> " + originalHandle);
			for(String handle : driver.getWindowHandles()) {
		        if (!handle.equals(originalHandle)) {
		            driver.switchTo().window(handle);
		            driver.close();
		        }
		    }
		    driver.switchTo().window(originalHandle);
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Exception message is : " + e.getMessage());
			System.out.println("Exception trace is : " + e.getStackTrace());
			Assert.fail();
			return false;
		}
	}

	//Script to select Dropdown field
	public static boolean D2_select_Dropdown(WebDriver driver,String sLayout,String DFLabel,String sLOVField) throws IOException
	{
		try
		{
			//WebElement eDropdownArrow = driver.findElement(By.xpath("//div[contains(.,'"+sLayout+"')]//div[contains(.,'"+DFLabel+"') and contains(@class,'x-form-item')]//img[contains(@class,'x-form-trigger')]"));
			WebElement eDropdownArrow = driver.findElement(By.xpath("(//div[contains(.,'"+sLayout+"')]//label[text()='"+DFLabel+"']//parent::div//img[contains(@class,'x-form-trigger')])[last()]"));
			ScrollToElement(driver, eDropdownArrow);
			eDropdownArrow.click();
			Reporter.log("Clicked ***** " + DFLabel + " ***** dropdown.");
			//WebElement eDFInput = driver.findElement(By.xpath("//div[contains(.,'"+sLayout+"')]//div[contains(.,'"+DFLabel+"') and contains(@class,'x-form-item')]//input[contains(@class,'x-form-text')]"));
			WebElement eDFInput = driver.findElement(By.xpath("(//div[contains(.,'"+sLayout+"')]//label[text()='"+DFLabel+"']//parent::div//input)[last()]"));
			enter_text_Alternate_Send_keys(driver,eDFInput,DFLabel + " - Input",sLOVField);
			Thread.sleep(2000);
			try
			{
				WebElement eDocmentsListItem = driver.findElement(By.xpath("//div[@role='listitem' and contains(text(),'"+sLOVField+"')]"));
				eDocmentsListItem.click();
				System.out.println("Selected the LOV in ***** " + DFLabel + " ***** dropdown as " + sLOVField + ".");
				Reporter.log("Selected the LOV in ***** " + DFLabel + " ***** dropdown as " + sLOVField + ".");
				return true;
			}
			catch(Exception ee)
			{
				System.out.println("Exception in dropdown selection is : " + ee.getMessage());
				System.out.println(sLOVField + " is not available as List Option in ***** " + DFLabel + " ***** dropdown.");
				Reporter.log(sLOVField + " is not available as List Option in ***** " + DFLabel + " ***** dropdown.");
				capture_screenshot(driver, "FAIL_"+DFLabel+"_"+sLOVField+"_NA_");
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in dropdown selection is : " + e.getMessage());
			System.out.println("Failed to select *****" + DFLabel + " ***** as " + sLOVField + ".");
			Reporter.log("Failed to select ***** " + DFLabel + " ***** as " + sLOVField + ".");
			capture_screenshot(driver, "FAIL_"+DFLabel+"_"+sLOVField+"_");
			Assert.fail();
			return false;
		}
	}

	//Script to select Dropdown field
	public static boolean D2_PopUp_select_Dropdown(WebDriver driver,String sProperties,String DFLabel,String sLOVField) throws IOException
	{
		try
		{
			WebElement eDropdownArrow = driver.findElement(By.xpath("//div[contains(.,'"+sProperties+"')]//label[text()='"+DFLabel+"']//parent::div//img[contains(@class,'x-form-trigger')]"));
			ScrollToElement(driver, eDropdownArrow);
			eDropdownArrow.click();
			Reporter.log("Clicked ***** " + DFLabel + " ***** dropdown.");
			WebElement eDFInput = driver.findElement(By.xpath("//div[contains(.,'"+sProperties+"')]//label[text()='"+DFLabel+"']//parent::div//input"));
			enter_text_Alternate_Send_keys(driver,eDFInput,DFLabel + " - Input",sLOVField);
			Thread.sleep(2000);
			try
			{
				WebElement eDocmentsListItem = driver.findElement(By.xpath("//div[@role='listitem' and contains(text(),'"+sLOVField+"')]"));
				eDocmentsListItem.click();
				System.out.println("Selected the LOV in ***** " + DFLabel + " ***** dropdown as " + sLOVField + ".");
				Reporter.log("Selected the LOV in ***** " + DFLabel + " ***** dropdown as " + sLOVField + ".");
				return true;
			}
			catch(Exception ee)
			{
				System.out.println("Exception in dropdown selection is : " + ee.getMessage());
				System.out.println(sLOVField + " is not available as List Option in ***** " + DFLabel + " ***** dropdown.");
				Reporter.log(sLOVField + " is not available as List Option in ***** " + DFLabel + " ***** dropdown.");
				capture_screenshot(driver, "FAIL_"+DFLabel+"_"+sLOVField+"_NA_");
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception in dropdown selection is : " + e.getMessage());
			System.out.println("Failed to select *****" + DFLabel + " ***** as " + sLOVField + ".");
			Reporter.log("Failed to select ***** " + DFLabel + " ***** as " + sLOVField + ".");
			capture_screenshot(driver, "FAIL_"+DFLabel+"_"+sLOVField+"_");
			Assert.fail();
			return false;
		}
	}

	//Script to select Dropdown field
	public static boolean D2_enter_Dropdown_Text(WebDriver driver,String sLayout,String DFLabel,String sLOVField) throws IOException
	{
		try
		{
			WebElement eDFInput = driver.findElement(By.xpath("//div[contains(.,'"+sLayout+"')]//div[contains(.,'"+DFLabel+"') and contains(@class,'x-form-item')]//input[contains(@class,'x-form-text')]"));
			enter_text_Alternate_Send_keys(driver,eDFInput,DFLabel + " - Input",sLOVField);
			Thread.sleep(2000);
			System.out.println("Entered the dropdown text field : ***** " + DFLabel + " as " + sLOVField + ".");
			Reporter.log("Entered the dropdown text field : ***** " + DFLabel + " as " + sLOVField + ".");
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Exception in dropdown selection is : " + e.getMessage());
			System.out.println("Failed to enter text in ***** " + DFLabel + " as " + DFLabel + ".");
			Reporter.log("Failed to enter text in ***** " + DFLabel + " as " + DFLabel + ".");
			capture_screenshot(driver, "FAIL_to_enter_"+DFLabel+"_"+sLOVField+"_");
			Assert.fail();
			return false;
		}
	}

	//Script to select Dropdown field
	public static boolean D2_PopUp_enter_Dropdown_Text(WebDriver driver,String sLayout,String DFLabel,String sLOVField) throws IOException
	{
		try
		{
			WebElement eDFInput = driver.findElement(By.xpath("//div[contains(.,'"+sLayout+"')]//div[contains(.,'"+DFLabel+"') and contains(@class,'x-form-item')]//input[contains(@class,'x-form-text')]"));
			enter_text_Alternate_Send_keys(driver,eDFInput,DFLabel + " - Input",sLOVField);
			Thread.sleep(2000);
			System.out.println("Entered the dropdown text field : ***** " + DFLabel + " as " + sLOVField + ".");
			Reporter.log("Entered the dropdown text field : ***** " + DFLabel + " as " + sLOVField + ".");
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Exception in dropdown selection is : " + e.getMessage());
			System.out.println("Failed to enter text in ***** " + DFLabel + " as " + DFLabel + ".");
			Reporter.log("Failed to enter text in ***** " + DFLabel + " as " + DFLabel + ".");
			capture_screenshot(driver, "FAIL_to_enter_"+DFLabel+"_"+sLOVField+"_");
			Assert.fail();
			return false;
		}
	}

	//Script to select values in LIST field
	public static boolean D2_select_List_Options(WebDriver driver,String ListFieldLabel,String sListPopUpHeader,String sListValues) throws IOException
	{
		WebDriverWait wait = new WebDriverWait(driver, ProjectProperties.iTimeOut);
		try
		{
			WebElement eLFPopUpLaunchBtn = driver.findElement(By.xpath("//div[@id='dialog_panel']//div[contains(.,'"+ListFieldLabel+"') and contains(@class,'x-form-item')]//div[contains(@class,'list-assistance')]"));
			ScrollToElement(driver, eLFPopUpLaunchBtn);
			eLFPopUpLaunchBtn.click();
			WebElement eLABtnCancel = driver.findElement(By.xpath("//div[contains(.,'"+sListPopUpHeader+"')]//button[@type='button' and contains(text(),'Cancel')]"));
			WebElement eLABtnOK = driver.findElement(By.xpath("//div[contains(.,'"+sListPopUpHeader+"')]//button[@type='button' and contains(text(),'OK')]"));
			wait.until(ExpectedConditions.visibilityOf(eLABtnCancel));
			wait.until(ExpectedConditions.elementToBeClickable(eLABtnCancel));
			WebElement eLADropdown = driver.findElement(By.xpath("//div[contains(.,'"+sListPopUpHeader+"')]//img[contains(@class,'x-form-trigger')]"));
			eLADropdown.click();
			Thread.sleep(2000);
			int NoOfListValues = sListValues.split("::").length;
			System.out.println("Number of list values --> " + NoOfListValues);
			WebElement eToRightbtn = driver.findElement(By.xpath("//div[contains(.,'List Assistance')]//div[@id='toRight-button']"));
			for(int i=0;i<NoOfListValues;i++)
			{
				String sReqListValue = sListValues.split("::")[i]; 
				WebElement eReqListValue = driver.findElement(By.xpath("(//div[contains(.,'List Assistance')]//div[contains(@class,'x-form-list')])[1]//div[contains(@class,'x-combo-list-item') and contains(text(),'"+sReqListValue+"')]"));
				if(eReqListValue.isDisplayed())
				{
					eReqListValue.click();
					System.out.println("List Value is selected : " + sReqListValue);
					eToRightbtn.click();
				}
				System.out.println("List value is not available : " + sReqListValue);
			}

			List<WebElement> ToListItems = driver.findElements(By.xpath("(//div[contains(.,'List Assistance')]//div[contains(@class,'x-form-list')])[2]//div[contains(@class,'x-combo-list-item')]"));
			int NoOfToListItems = ToListItems.size();
			if(NoOfToListItems==NoOfListValues)
			{
				System.out.println("Successfully added the list items to TO List.");
				eLABtnOK.click();
				return true;
			}
			System.out.println("Failed to add list items to TO list.");
			return false;
		}
		catch(Exception e)
		{
			System.out.println("Exception in dropdown selection is : " + e.getMessage());
			//System.out.println("Failed to enter text in ***** " + DFLabel + " ***** as ***** " + DFLabel + " *****.");
			//Reporter.log("Failed to enter text in ***** " + DFLabel + " ***** as ***** " + DFLabel + " *****.");
			//capture_screenshot(driver, "FAIL_to_enter_"+DFLabel+"_"+sLOVField+"_");
			Assert.fail();
			return false;
		}
	}

	//Script to enter Text Field
	public static boolean D2_enter_Text_Input(WebDriver driver,String sLayout,String DFLabel,String sTextInput) throws IOException
	{
		try
		{
			WebElement eDFInput = driver.findElement(By.xpath("(//div[contains(.,'"+sLayout+"')]//label[text()='"+DFLabel+"']//parent::div//input)[last()]"));
			enter_text_Alternate_Send_keys(driver,eDFInput,DFLabel,sTextInput);
			Thread.sleep(2000);
			//System.out.println("Entered the input in text field : ***** " + DFLabel + " ***** as " + sTextInput + ".");
			//Reporter.log("Entered the input in text field : ***** " + DFLabel + " ***** as " + sTextInput + ".");
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Exception in entering the text in Input field is : " + e.getMessage());
			System.out.println("Failed to enter text in ***** " + DFLabel + " ***** as " + DFLabel + ".");
			Reporter.log("Failed to enter text in ***** " + DFLabel + " ***** as " + DFLabel + ".");
			capture_screenshot(driver, "FAIL_to_enter_"+DFLabel+"_"+sTextInput+"_");
			Assert.fail();
			return false;
		}
	}

	//Script to enter Text Field
	public static boolean D2_PopUp_enter_Text_Input(WebDriver driver,String sPopUpProperties,String DFLabel,String sTextInput) throws IOException
	{
		try
		{
			WebElement eDFInput = driver.findElement(By.xpath("//div[contains(.,'"+sPopUpProperties+"//div[contains(.,'"+DFLabel+"']//parent::div//input"));
			enter_text_Alternate_Send_keys(driver,eDFInput,DFLabel,sTextInput);
			Thread.sleep(2000);
			//System.out.println("Entered the input in text field : ***** " + DFLabel + " ***** as " + sTextInput + ".");
			//Reporter.log("Entered the input in text field : ***** " + DFLabel + " ***** as " + sTextInput + ".");
			return true;
		}
		catch(Exception e)
		{
			System.out.println("Exception in entering the text in Input field is : " + e.getMessage());
			System.out.println("Failed to enter text in ***** " + DFLabel + " ***** as " + DFLabel + ".");
			Reporter.log("Failed to enter text in ***** " + DFLabel + " ***** as " + DFLabel + ".");
			capture_screenshot(driver, "FAIL_to_enter_"+DFLabel+"_"+sTextInput+"_");
			Assert.fail();
			return false;
		}
	}

	//Script to navigate to a Repository folder
	public static boolean D2_Navigate_To_Folder(WebDriver driver,String sFolderPath) throws IOException
	{
		try
		{
			System.out.println("Navigating to folder : " + sFolderPath);
			int NoOfFolders = sFolderPath.split("::").length;
			//System.out.println("Number of folders to navigate is : " + NoOfFolders);
			for(int i=0;i<NoOfFolders;i++) 
			{
				String sReqFolder = sFolderPath.split("::")[i];
				WebElement eReqFolder = driver.findElement(By.xpath("//div[contains(@tag_id,'Repository')]//div[@role='tree' and contains(@style,'display: block')]//div[@aria-label='"+sReqFolder+"']"));
				String sIsExpandedFlag = eReqFolder.getAttribute("aria-expanded");
				//System.out.println("Is expanded flag for folder : " + sReqFolder + " is --> " + sIsExpandedFlag);
				if(sIsExpandedFlag.equalsIgnoreCase("false"))
				{
					WebElement eFolderExpandButton = driver.findElement(By.xpath("//div[contains(@tag_id,'Repository')]//div[@role='tree' and contains(@style,'display: block')]//div[@aria-label='"+sReqFolder+"']//img[contains(@class,'node-joint')]"));
					eFolderExpandButton.click();
					Thread.sleep(2000);
					System.out.println("Clicked Expand folder button for : " + sReqFolder);
					WebElement eFolderBlock = driver.findElement(By.xpath("//div[contains(@tag_id,'Repository')]//div[@role='tree' and contains(@style,'display: block')]//div[@aria-label='"+sReqFolder+"']//following-sibling::div[@role='group' and contains(@style,'display: block')]"));
					if(eFolderBlock.isDisplayed())
					{
						System.out.println(sReqFolder + " is expanded successfully.");
					}
					System.out.println(sReqFolder + " --> failed to expand the folder.");
				}
				else 
				{
					System.out.println(sReqFolder + " --> folder is expanded by default.");
				}

				if(i==NoOfFolders-1)
				{
					String sRootFolder = sFolderPath.split("::")[i];
					WebElement eRootFolder = driver.findElement(By.xpath("//div[contains(@tag_id,'Repository')]//div[@role='treeitem' and @aria-label='"+sReqFolder+"']"));
					eRootFolder.click();
					Thread.sleep(2000);
					String sIsFolderSelectedFlag = eRootFolder.getAttribute("aria-selected");
					if(sIsFolderSelectedFlag.equalsIgnoreCase("true"))
					{
						System.out.println(sRootFolder + " --> Root folder is selected successfully.");
						return true;
					}
					else
					{
						System.out.println(sFolderPath.split("::")[i] + "Failed to select the root folder.");
						return false;
					}
				}
			}
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}

}
