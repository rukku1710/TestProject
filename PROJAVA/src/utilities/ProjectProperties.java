package utilities;

public class ProjectProperties 

{
	
	static String sProjectPath = System.getProperty("user.dir");
	
	//Generic Properties
	public static final String sBrowser = "chrome";
	public static final String sAppURL = "https://prod.nextgen.cloud.opentext.com/otcs/llisapi.dll?otdsauth=no-sso";
	public static final int iHardSleep = 3000;
	public static final String sCSAdminURL = "https://prod.nextgen.cloud.opentext.com/otcs/llisapi.dll?func=admin.index"; 
	public static final String ScreenshotsFolder = sProjectPath + "\\test-output\\Screenshots\\";
	public static final String PDFFilesFolder = sProjectPath + "\\test-output";
	public static final int iLongTimeOut = 60;
	public static final int iTimeOut = 10;
	public static final String sTestOutputFolder = System.getProperty("user.dir") + "\\test-output\\";
	public static final String sTestInputFolder = System.getProperty("user.dir") + "\\test-inputs\\";
	public static final String sTestDataFile = "Project_Test_Data";
	public static final String sTestDataFile1 = "Project_Test_Data1";
	public static final String sPropertiesFileName = "ProjectData.properties";
	
	
}
