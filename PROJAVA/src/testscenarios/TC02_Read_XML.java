package testscenarios;

import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TC02_Read_XML 

{

	public static void main(String[] args) throws SQLException, ParserConfigurationException, SAXException, IOException
	{
		String sGlobalData[] = new String[4];
		sGlobalData =  read_Global_Data();
		String sEnv = sGlobalData[0];
		String sURL = sGlobalData[1];
		String sBrowser = sGlobalData[2];
		String sTimeout = sGlobalData[3];
	}
	
	public static String[] read_Global_Data()
	{
		String[] sSetupData = new String[4];
		String sEnvironment="";
		String sAppURL="";
		String sBrowser="";
		String sTimeOut="";
		try
		{
			String sXMLFilePath = System.getProperty("user.dir") + "\\test-inputs\\" + "Global_Test_Data.xml";	
			System.out.println("File path : " + sXMLFilePath);
			File fXmlFile = new File(sXMLFilePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("environment");
			System.out.println("Node List length is : " + nList.getLength());
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
						sSetupData[1] = sAppURL;
						sSetupData[2] = sBrowser;
						sSetupData[3] = sTimeOut;
						System.out.println("Environment --> " + sEnvironment + " || Application URL --> " + sAppURL + " || Browser --> " + sBrowser + " || TImeout --> " + sTimeOut);
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
}

