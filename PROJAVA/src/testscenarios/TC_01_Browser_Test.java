package testscenarios;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TC_01_Browser_Test 

{
	static String sProjectPath = System.getProperty("user.dir");
	static WebDriver driver = null;
	
	public static void main(String[] args)
	{
		System.out.println("Checking Browser test");
		System.setProperty("webdriver.chrome.driver", sProjectPath + "//drivers//" + "chromedriver.exe");
		driver = new ChromeDriver();
		System.out.println("Launched chrome driver.");
		driver.get("https://www.youtube.com/");
	}
}
