package pageclasses;

public class AppElements 

{

	//Elements in Portal Page
	public static final String eCSLPBtnLogin = "//input[@value='Sign in']";
	public static final String eCSLPUsername = "//input[@id='otds_username']";
	public static final String eCSLPPassword = "//input[@id='otds_password']";
	
	public static final String eAppPageLogo = "//h1//a[contains(text(),'Open Text Content Server')]";
	
	
	
	
	public static final String eContentServerLPTitle = "//h1[@class='admin-location-page-title']";
	public static final String eContentServerLPBtnLogin = "//input[@value='Log-in']";
	public static final String eContentServerLPInputPassword = "//input[@name='Password']";
	public static final String eAddDocument = "//span[text()='Add Document']";
	public static final String eAddFolder = "//span[text()='Add Folder']";
	public static final String eCSAdminPageTitle = "//h1[@id='pageTitle']";
	public static final String eCSPageTitle = "//div[@class='pageTitleText']//h1";
	
	public static final String eCSCRTDashboardLink = "//div[@id='psceAppearanceLinks']//a//b[text()='CRT Dashboard']";
	
	//Add Document page
	
	public static final String eAddDocumentPageFileInputPath = "//input[@name='versionFile']";
	public static final String eAddDocumentPageBtnAdd = "//input[@value='Add']";
	public static final String eAddDocumentPageBtnReset = "//input[@value='Reset']";
	public static final String eAddDocumentPageAddedFileName = "//label[text()='Name:']//parent::td//following-sibling::td//div[@class='nameContainer']/input";
	
	//CS Home Page Fields
	public static final String eCSGlobalSearchInput= "//div[@id='searchBarActionRow']//input[@name='where1']";
	public static final String eCSGlobalSearchBtn= "//div[@id='searchBarActionRow']//input[@name='submitButton']";
	public static final String eCSLocalSearchInput= "//table[@id='pageFilterTable']//input[@placeholder='Filter by name']";
	public static final String eCSLocalSearchBtn= "//table[@id='pageFilterTable']//span[@id='browse_search_but']";
	public static final String eCSLocalSearchResults= "//table[@id='BrowsePageRowPagingSectionCellTable']//tr[2]/td";
	
	//CS Administration page
	public static final String eCSAShowAllLinks = "//a[text()='Show All Sections']";
	public static final String eCSAdminHomeLink = "//a[text()='Admin Home']";
	
	//MFT Page
	public static final String eMFTBtnSignIn = "//div[@id='signin-submit']//input[@value='Sign in']";
	public static final String eMFTUsername = "//div[@id='signin-input']//input[@name='otds_username']";
	public static final String eMFTPassword = "//div[@id='signin-input']//input[@name='otds_password']";
	public static final String eMFTBtnSignOut = "//p//a[contains(text(),'Sign out')]";
	public static final String eMFTLoggedInUsername = "//td[@class='inputs']//b";
	
	//MDL Workflow
	public static final String eMDLBtnAddMDL = "//span[contains(text(),'Add MDL')]";
	public static final String eMDLTxtMDLName = "//div[contains(text(),'MDL Name')]/input[@type='text']";
	public static final String eMDLTxtDocNum = "//div[contains(text(),'Document Number')]/select";
	
	public static final String eActiveTab = "//ul[@class='cs-tabrow']//li[@class='selected']";
	
}
