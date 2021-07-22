package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import utilities.GenericMethods;

public class FileMethods 
{

	static String projectPath = ProjectProperties.sProjectPath;
	static String sExcelPath = projectPath + "\\test-inputs\\" + ProjectProperties.sTestDataFile + ".xlsx";
	static XSSFWorkbook workbook;
	static XSSFSheet sheet;
	static DataFormatter dataformat;

	public FileMethods(String ExcelPath,String sheetName) 
	{
		try
		{
			//System.out.println("Excel path inside FileMethods constructor is : " + ExcelPath);
			workbook = new XSSFWorkbook(ExcelPath);
			sheet = workbook.getSheet(sheetName);
		}
		catch(Exception e)
		{
			System.out.println("Exception message is : " + e.getMessage());
		}
	}

	//To get test data and store it in a Hash Map
	public static HashMap<String,String> getTestDataHashMap(String expectedRowData) 
	{
		HashMap<String , String> sTestData = new HashMap();
		try
		{
			int rowCount = sheet.getPhysicalNumberOfRows();
			//System.out.println("Number of Rows : " + rowCount);
			for(int i=1;i<rowCount;i++)
			{
				String rowText = sheet.getRow(i).getCell(0).getStringCellValue();
				//System.out.println("Row text at row : " + i + " is --> " + rowText);
				if(rowText.equalsIgnoreCase(expectedRowData))
				{
					//System.out.println("Test Data Setup :: Matching data is found at row --> " + i);
					int colCount = sheet.getRow(i).getLastCellNum();
					for(int j=1;j<colCount;j++)
					{
						String cellKey = sheet.getRow(0).getCell(j).toString();
						String cellValue = sheet.getRow(i).getCell(j).toString();
						sTestData.put(cellKey,cellValue);
					}
				}
			}
			return sTestData;
		}
		catch(Exception e)
		{
			System.out.println("Could not find a Row with the expected data.Exception Message is : " + e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	//Update the test results from HashMap to an Excel file
	public static void update_test_results_hashmap(String sExcelName,String sSheetName,LinkedHashMap<String,String> sLinkedHashMap)
	{

		System.out.println("HashMap is : " + sLinkedHashMap);
		int sHMapSize = sLinkedHashMap.size();
		System.out.println("HashMap length is : " + sHMapSize);
		String sOutputFilePath = ProjectProperties.sTestOutputFolder + sExcelName + ".xlsx";
		try
		{
			//FileInputStream fo = new FileInputStream(new File(sOutputFilePath));
			XSSFWorkbook fowb = new XSSFWorkbook();
			XSSFSheet foSheet = fowb.createSheet(sSheetName);
			Font headerFont = fowb.createFont();
			headerFont.setColor(IndexedColors.BLACK.index);
			CellStyle headerCellStyle = foSheet.getWorkbook().createCellStyle();
			headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerCellStyle.setFont(headerFont);
			int row = 0;
			for(String skey : sLinkedHashMap.keySet())
			{
				Row iRow = foSheet.createRow(row);
				String values = sLinkedHashMap.get(skey);
				iRow.createCell(0).setCellValue(skey);
				int sValuesLength = values.split("&&").length;
				for(int j=0;j<sValuesLength;j++)
				{
					int l=j+1;
					String iValue = values.split("&&")[j]; 
					Cell sCell2 = iRow.createCell(j+1);
					sCell2.setCellType(CellType.STRING);
					sCell2.setCellValue(iValue);
				}
				row++;
			}
			FileOutputStream out = new FileOutputStream(new File(sOutputFilePath));
			fowb.write(out);
			out.close();
			//System.out.println("Excel written successfully..");
		}
		catch(Exception e)
		{
			System.out.println("Could not update the HashMap data to Excel.Exception Message is : " + e.getMessage());
			e.printStackTrace();
		}
	}

	//Script to get number of rows
	public static int getRowCount()
	{
		int rowCount=0;
		try 
		{
			rowCount = sheet.getPhysicalNumberOfRows();
			//System.out.println("Number of rows : " + rowCount);
		}
		catch(Exception e)
		{
			System.out.println("Exception Message is : " + e.getMessage());
			System.out.println("Exception Cause is : " + e.getCause());
			System.out.println("Exception Trace is : " + e.getStackTrace());
		}
		return rowCount;
	}

	//Script to get number of rows
	public static int getColumnCount()
	{
		int columnCount=0;
		try 
		{
			columnCount = sheet.getRow(0).getPhysicalNumberOfCells();
			//System.out.println("Number of columns : " + columnCount);
		}
		catch(Exception e)
		{
			System.out.println("Exception Message is : " + e.getMessage());
		}
		return columnCount;
	}

	//Script to get Cell data as a String
	public static String getCellDataAsString(int rowNum,int colNum)
	{
		String StringCellValue = null;
		try 
		{
			StringCellValue = sheet.getRow(rowNum).getCell(colNum).toString();
			//System.out.println("Data in the cell is : " + StringCellValue);
		}
		catch(Exception e)
		{
			System.out.println("Exception Message is : " + e.getMessage());
		}
		return StringCellValue;
	}

	//Script to get String data from a Cell
	public static String getStringCellData(int rowNum,int colNum)
	{
		try 
		{
			String StringCellValue = sheet.getRow(rowNum).getCell(colNum).getStringCellValue();
			//System.out.println("Data in the cell is : " + StringCellValue);
			return StringCellValue;
		}
		catch(Exception e)
		{
			System.out.println("Exception Message is : " + e.getMessage());
			System.out.println("Exception Cause is : " + e.getCause());
			System.out.println("Exception Trace is : " + e.getStackTrace());
			return null;
		}
	}

	//Script to get Numeric data from a Cell
	public static float getNumericCellData(int rowNum,int colNum)
	{
		float NumericValue=0;
		try 
		{
			NumericValue = (float) sheet.getRow(rowNum).getCell(colNum).getNumericCellValue();
			//System.out.println("Data in the cell is : " + NumericValue);
			return NumericValue;	
		}
		catch(Exception e)
		{
			System.out.println("Exception Message is : " + e.getMessage());
		}
		return NumericValue;

	}

	//Script to check if a given File exists in test output directory
	public static void checkFileExists(String sFileName)
	{
		try
		{
			String sCurrentDate = GenericMethods.getCurrentDateAndTime("dd_MMM_yyyy");
			//System.out.println("Current date is : " + sCurrentDate);
			String sExcelPath = ProjectProperties.sTestOutputFolder + sFileName;
			//System.out.println("Excel path is : " + sExcelPath);
			File file = new File(sExcelPath);

			if(file.exists())
			{
				//System.out.println("File exists in the given directory.");
			}
			else
			{
				System.out.println("File doesn't exist in the given directory.");
				FileOutputStream fos = new FileOutputStream(new File(sExcelPath));
				XSSFWorkbook fowb = new XSSFWorkbook();
				XSSFSheet foSheet = fowb.createSheet("Results");
				fowb.write(fos);
				try {
					if (fowb != null) {
						fowb.close();
					}
					if (fos != null) {
						fos.close();
					}
				} catch (IOException e) {
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("Exception while creating excel file is : " + e.getMessage());
		}
	}

	//Script to delete and create a sheet in an excel file
	public static void DeleteAndCreatesheet(String sFileName,String sSheetName) throws Exception 
	{
		String sFPath = ProjectProperties.sTestOutputFolder + sFileName;
		//System.out.println("Output file path in DeleteAndCreateSheet method is :" + sFPath);
		FileInputStream fis = new FileInputStream(sFPath);
		Workbook wb = WorkbookFactory.create(fis);
		//index = workbook.getSheetIndex("Report");
		try{
			wb.removeSheetAt(wb.getSheetIndex(sSheetName));
			//System.out.println("Removed sheet at : " + sSheetName);
		}
		catch(Exception e) 
		{
			System.out.println("Exception in DeleteAndCreatesheet method is : " + e.getMessage());
		}
		wb.createSheet(sSheetName);
		//workbook.removeSheetAt(WorkbookFactory.getSheetIndex("Report"));
		FileOutputStream output = new FileOutputStream(sFPath);
		wb.write(output);
		output.close();
	}

	//Update the test results from HashMap to an Excel file
	public static void update_test_results_ArrayList(String sExcelName,String sSheetName,ArrayList<String> sArrayList)
	{

		//System.out.println("Array List is : " + sArrayList);
		int sALSize = sArrayList.size();
		//System.out.println("HashMap length is : " + sALSize);
		String sOutputFilePath = ProjectProperties.sTestOutputFolder + sExcelName + ".xlsx";
		try
		{
			//FileInputStream fo = new FileInputStream(new File(sOutputFilePath));
			XSSFWorkbook fowb = new XSSFWorkbook();
			XSSFSheet foSheet = fowb.createSheet(sSheetName);
			//Header Font
			Font headerFont = fowb.createFont();
			headerFont.setColor(IndexedColors.BLACK.index);
			headerFont.setFontName("Calibri Light");
			headerFont.setBold(true);
			//Data Font
			Font dataFont = fowb.createFont();
			dataFont.setColor(IndexedColors.BLACK.index);
			dataFont.setFontName("Calibri Light");
			//Success Data Font
			Font successDataFont = fowb.createFont();
			successDataFont.setColor(IndexedColors.GREEN.index);
			successDataFont.setBold(true);
			successDataFont.setFontName("Calibri Light");
			//Data Font
			Font failureDataFont = fowb.createFont();
			failureDataFont.setColor(IndexedColors.RED.index);
			failureDataFont.setBold(true);
			failureDataFont.setFontName("Calibri Light");
			//Header Style
			CellStyle headerCellStyle = foSheet.getWorkbook().createCellStyle();
			headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.index);
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setBorderBottom(BorderStyle.THIN);
			headerCellStyle.setBorderTop(BorderStyle.THIN);
			headerCellStyle.setBorderRight(BorderStyle.THIN);
			headerCellStyle.setBorderLeft(BorderStyle.THIN);
			//Default Style
			CellStyle defaultCellStyle = foSheet.getWorkbook().createCellStyle();
			defaultCellStyle.setFont(dataFont);
			defaultCellStyle.setBorderBottom(BorderStyle.THIN);
			defaultCellStyle.setBorderTop(BorderStyle.THIN);
			defaultCellStyle.setBorderRight(BorderStyle.THIN);
			defaultCellStyle.setBorderLeft(BorderStyle.THIN);
			//Success Style
			CellStyle successCellStyle = foSheet.getWorkbook().createCellStyle();
			//successCellStyle.setFillForegroundColor(IndexedColors.GREEN.index);
			//successCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			successCellStyle.setBorderBottom(BorderStyle.THIN);
			successCellStyle.setBorderTop(BorderStyle.THIN);
			successCellStyle.setBorderRight(BorderStyle.THIN);
			successCellStyle.setBorderLeft(BorderStyle.THIN);
			successCellStyle.setFont(successDataFont);
			//Failure Style
			CellStyle failureCellStyle = foSheet.getWorkbook().createCellStyle();
			//failureCellStyle.setFillForegroundColor(IndexedColors.RED.index);
			//failureCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			failureCellStyle.setBorderBottom(BorderStyle.THIN);
			failureCellStyle.setBorderTop(BorderStyle.THIN);
			failureCellStyle.setBorderRight(BorderStyle.THIN);
			failureCellStyle.setBorderLeft(BorderStyle.THIN);
			failureCellStyle.setFont(failureDataFont);
			int row = 0;
			for(int i=0;i<sALSize;i++)
			{
				String sArrayData = sArrayList.get(i);
				//System.out.println("Array Data at index : " + i + " is --> " + sArrayData);
				int iArrayDataCount = sArrayData.split("&&").length;
				Row iRow = foSheet.createRow(row);
				for(int j=0;j<iArrayDataCount;j++)
				{
					Cell sCell = iRow.createCell(j);
					String svalue = sArrayData.split("&&")[j];
					if(row==0)
					{
						iRow.getCell(j).setCellStyle(headerCellStyle);
					}
					else if(!(row==0))
					{
						if(svalue.equalsIgnoreCase("PASS"))
						{
							iRow.getCell(j).setCellStyle(successCellStyle);
						}
						else if(svalue.equalsIgnoreCase("FAIL"))
						{
							iRow.getCell(j).setCellStyle(failureCellStyle);
						}
						else
						{
							iRow.getCell(j).setCellStyle(defaultCellStyle);
						}
					}
					sCell.setCellType(CellType.STRING);
					sCell.setCellValue(svalue);
				}
				row++;
			}
			FileOutputStream out = new FileOutputStream(new File(sOutputFilePath));
			fowb.write(out);
			out.close();
			//System.out.println("Excel written successfully..");
		}
		catch(Exception e)
		{
			System.out.println("Could not update the HashMap data to Excel.Exception Message is : " + e.getMessage());
			e.printStackTrace();
		}
	}

	//Updating test results to excel file
	public static void update_data_to_test_results_file(ArrayList<String> sArrayList,String sFileName,String sSuccess,String sFailure)
	{
		//System.out.println("Array List is : " + sArrayList);
		int sALSize = sArrayList.size();
		//System.out.println("Array List length is : " + sALSize);
		try
		{
			checkFileExists(sFileName);
			String sCurrentDate = GenericMethods.getCurrentDateAndTime("dd_MMM_yyyy");
			//System.out.println("Current date is : " + sCurrentDate);
			String sExcelPath = ProjectProperties.sTestOutputFolder + sFileName + ".xlsx";

			FileInputStream fips= new FileInputStream(sExcelPath);
			XSSFWorkbook fowb = new XSSFWorkbook(fips);;
			XSSFSheet foSheet = fowb.getSheetAt(0);

			//Header Font
			Font headerFont = fowb.createFont();
			headerFont.setColor(IndexedColors.BLACK.index);
			headerFont.setFontName("Calibri Light");
			headerFont.setBold(true);

			//Data Font
			Font dataFont = fowb.createFont();
			dataFont.setColor(IndexedColors.BLACK.index);
			dataFont.setFontName("Calibri Light");

			//Success Data Font
			Font successDataFont = fowb.createFont();
			successDataFont.setColor(IndexedColors.GREEN.index);
			successDataFont.setBold(true);
			successDataFont.setFontName("Calibri Light");

			//Data Font
			Font failureDataFont = fowb.createFont();
			failureDataFont.setColor(IndexedColors.RED.index);
			failureDataFont.setBold(true);
			failureDataFont.setFontName("Calibri Light");

			//Header Style
			CellStyle headerCellStyle = ((XSSFSheet) foSheet).getWorkbook().createCellStyle();
			headerCellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.index);
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setBorderBottom(BorderStyle.THIN);
			headerCellStyle.setBorderTop(BorderStyle.THIN);
			headerCellStyle.setBorderRight(BorderStyle.THIN);
			headerCellStyle.setBorderLeft(BorderStyle.THIN);

			//Default Style
			CellStyle defaultCellStyle = ((XSSFSheet) foSheet).getWorkbook().createCellStyle();
			defaultCellStyle.setFont(dataFont);
			defaultCellStyle.setBorderBottom(BorderStyle.THIN);
			defaultCellStyle.setBorderTop(BorderStyle.THIN);
			defaultCellStyle.setBorderRight(BorderStyle.THIN);
			defaultCellStyle.setBorderLeft(BorderStyle.THIN);

			//Success Style
			CellStyle successCellStyle = ((XSSFSheet) foSheet).getWorkbook().createCellStyle();
			//successCellStyle.setFillForegroundColor(IndexedColors.GREEN.index);
			//successCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			successCellStyle.setBorderBottom(BorderStyle.THIN);
			successCellStyle.setBorderTop(BorderStyle.THIN);
			successCellStyle.setBorderRight(BorderStyle.THIN);
			successCellStyle.setBorderLeft(BorderStyle.THIN);
			successCellStyle.setFont(successDataFont);
			//Failure Style
			CellStyle failureCellStyle = ((XSSFSheet) foSheet).getWorkbook().createCellStyle();
			//failureCellStyle.setFillForegroundColor(IndexedColors.RED.index);
			//failureCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			failureCellStyle.setBorderBottom(BorderStyle.THIN);
			failureCellStyle.setBorderTop(BorderStyle.THIN);
			failureCellStyle.setBorderRight(BorderStyle.THIN);
			failureCellStyle.setBorderLeft(BorderStyle.THIN);
			failureCellStyle.setFont(failureDataFont);

			int rowCount = foSheet.getPhysicalNumberOfRows();
			//System.out.println("Number of rows in sheet --> " + rowCount);
			int Row = rowCount+1;

			for(int i=0;i<sALSize;i++)
			{
				String sArrayData = sArrayList.get(i);
				//System.out.println("Array Data at index : " + i + " is --> " + sArrayData);
				int iArrayDataCount = sArrayData.split("&&").length;
				Row iRow = ((XSSFSheet) foSheet).createRow(rowCount+i);
				for(int j=0;j<iArrayDataCount;j++)
				{
					Cell sCell = iRow.createCell(j);
					String svalue = sArrayData.split("&&")[j];
					if(Row==rowCount+1)
					{
						iRow.getCell(j).setCellStyle(headerCellStyle);
					}
					else if(!(Row==rowCount))
					{
						if(svalue.equalsIgnoreCase(sSuccess))
						{
							iRow.getCell(j).setCellStyle(successCellStyle);
						}
						else if(svalue.equalsIgnoreCase(sFailure))
						{
							iRow.getCell(j).setCellStyle(failureCellStyle);
						}
						else
						{
							iRow.getCell(j).setCellStyle(defaultCellStyle);
						}
					}
					sCell.setCellType(CellType.STRING);
					sCell.setCellValue(svalue);
				}
				Row++;
			}
			FileOutputStream out = new FileOutputStream(new File(sExcelPath));
			fowb.write(out);
			out.close();
			//System.out.println("Excel written successfully..");
		}
		catch(Exception e)
		{
			System.out.println("Exception while updating results to excel is : " + e.getMessage());
		}
	}

	//Updating test results to excel file
	public static void update_header_to_test_results_file(String sFileName,String sHeader)
	{
		//System.out.println("Array List is : " + sArrayList);
		//System.out.println("Array List length is : " + sALSize);
		try
		{
			checkFileExists(sFileName);
			String sCurrentDate = GenericMethods.getCurrentDateAndTime("dd_MMM_yyyy");
			//System.out.println("Current date is : " + sCurrentDate);
			String sExcelPath = ProjectProperties.sTestOutputFolder + sFileName + ".xlsx";

			FileInputStream fips= new FileInputStream(sExcelPath);
			XSSFWorkbook fowb = new XSSFWorkbook(fips);;
			XSSFSheet foSheet = fowb.getSheetAt(0);

			//Header Font
			Font headerFont = fowb.createFont();
			headerFont.setColor(IndexedColors.BLACK.index);
			headerFont.setFontName("Calibri Light");
			headerFont.setBold(true);

			//Header Style
			CellStyle headerCellStyle = ((XSSFSheet) foSheet).getWorkbook().createCellStyle();
			headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_ORANGE.index);
			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerCellStyle.setFont(headerFont);
			headerCellStyle.setBorderBottom(BorderStyle.THIN);
			headerCellStyle.setBorderTop(BorderStyle.THIN);
			headerCellStyle.setBorderRight(BorderStyle.THIN);
			headerCellStyle.setBorderLeft(BorderStyle.THIN);

			int rowCount = foSheet.getPhysicalNumberOfRows();
			//System.out.println("Number of rows in sheet --> " + rowCount);
			int Row = rowCount+1;
			Row iRow = ((XSSFSheet) foSheet).createRow(rowCount);
			Cell sCell = iRow.createCell(0);
			sCell.setCellStyle(headerCellStyle);
			sCell.setCellType(CellType.STRING);
			sCell.setCellValue(sHeader);
			FileOutputStream out = new FileOutputStream(new File(sExcelPath));
			fowb.write(out);
			out.close();
			//System.out.println("Excel written successfully..");
		}
		catch(Exception e)
		{
			System.out.println("Exception while updating results to excel is : " + e.getMessage());
		}
	}

	public static void SQL_Update_Results_To_DB(String sqlMachineName,String sqlPortNumber,String sqlUsername,String sqlPassword,String sqlDBName,String sqlTable,ArrayList<String> sArrayList)
	{
		try
		{
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://"+sqlMachineName+":"+sqlPortNumber+";user="+sqlUsername+";password="+sqlPassword+";databaseName="+sqlDBName);
			System.out.println("Connected to MS SQL database " +  sqlDBName + " successfully...");
			Statement statemnt = conn.createStatement();

			String Sql = "select * from " + sqlTable;

			int sALSize = sArrayList.size();
			int sActualResultsSize = sALSize-1;
			System.out.println("Number of results in Results Array List: " + sALSize + ". Results exluding Header data: " + sActualResultsSize + ".");
			for(int i=1;i<sALSize;i++)
			{
				String sSQLTotal;
				String sSQLText = "INSERT INTO "+sqlTable+" VALUES (";
				String sArrayData = sArrayList.get(i);
				int iArrayDataCount = sArrayData.split("&&").length;
				String sSQLDynamic = "";
				for(int j=0;j<iArrayDataCount;j++)
				{
					String svalue = sArrayData.split("&&")[j];
					//System.out.println("String value is : " + svalue);
					sSQLDynamic = sSQLDynamic + "\'" + svalue + "\'";
					if(j!=iArrayDataCount-1)
					{
						sSQLDynamic=sSQLDynamic+",";
					}
				}
				//System.out.println("Total text is : " + sSQLDynamic);
				sSQLText = sSQLText + sSQLDynamic + ")";
				//System.out.println("SQL Statement Text is : " + sSQLText);
				statemnt.executeUpdate(sSQLText);
				//System.out.println("Inserted record #" + i + " into the data base : " + sSQLText);
			}
			System.out.println("All results i.e. " + sActualResultsSize  + " are successfully updated to table: " +  sqlTable + ".");
		}
		catch(Exception e)
		{
			System.out.println("Facing exception while writing results to DB: " + e.getMessage());
		}
	}

	public static String[] read_Global_Data_XML()
	{
		String[] sSetupData = new String[4];
		String sEnvironment="";
		String sAppURL="";
		String sBrowser="";
		String sTimeOut="";
		try
		{
			String sXMLFilePath = System.getProperty("user.dir") + "\\test-inputs\\" + "Global_Test_Data.xml";	
			//System.out.println("File path : " + sXMLFilePath);
			File fXmlFile = new File(sXMLFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			//System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("environment");
			//System.out.println("Node List length is : " + nList.getLength());
			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				//System.out.println("\nCurrent Element :" + nNode.getNodeName());
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String sIsExecute = eElement.getAttribute("execute");
					sEnvironment = eElement.getAttribute("envtype");
					sAppURL = eElement.getElementsByTagName("URL").item(0).getTextContent();
					sBrowser = eElement.getElementsByTagName("Browser").item(0).getTextContent();
					sTimeOut = eElement.getElementsByTagName("Timeout").item(0).getTextContent();
					if(sIsExecute.equalsIgnoreCase("YES"))
					{
						sSetupData[0] = sEnvironment;
						sSetupData[1] = sBrowser;
						sSetupData[2] = sAppURL;
						sSetupData[3] = sTimeOut;
						//System.out.println("Environment: " + sEnvironment + ": Application URL --> " + sAppURL + ": Browser --> " + sBrowser + ": TImeout --> " + sTimeOut);
						return sSetupData;
					}
				}
			}
			return null;
		}
		catch(Exception e)
		{
			System.out.println("Exception while reading Global Test Data : " + e.getMessage());
			System.out.println("Exception while reading Global Test Data : " + e.getStackTrace());
		}
		return null;
	}

	//Update the test results from HashMap to an Excel file
	public static StringBuilder Capture_Results_As_Email(ArrayList<String> sArrayList,String sHeaderBGColur)
	{
		StringBuilder sTableText = new StringBuilder();
		sTableText.append("<table style=\"width:50%;border:1px solid black;border-collapse:collapse;\">");
		System.out.println("Array List is : " + sArrayList);
		int sALSize = sArrayList.size();
		System.out.println("HashMap length is : " + sALSize);
		try
		{
			for(int i=0;i<sALSize;i++)
			{
				String sArrayData = sArrayList.get(i);
				//System.out.println("Array Data at index : " + i + " is --> " + sArrayData);
				int iArrayDataCount = sArrayData.split("&&").length;
				sTableText.append("<tr>");
				for(int j=0;j<iArrayDataCount;j++)
				{
					String sValue = sArrayData.split("&&")[j];
					if(i==0)
					{
						sTableText.append("<th style=\"border:1px solid black;border-collapse:collapse;padding: 5px;background-color:" + sHeaderBGColur + ";\">" + sValue + "</th>");
					}
					sTableText.append("<td style=\"border:1px solid black;border-collapse:collapse;padding: 5px;background-color:Orange;\">" + sValue + "</th>");
				}
				sTableText.append("</tr>");
			}
			sTableText.append("</table>");
			return sTableText;
		}
		catch(Exception e)
		{
			System.out.println("Could not update the HashMap data to Excel.Exception Message is : " + e.getMessage());
			e.printStackTrace();
		}
		return sTableText;
	}
}
