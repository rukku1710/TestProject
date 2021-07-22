package testscenarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TC01_SQL_Test {

	public static void main(String[] args) throws SQLException
	{
		String sqlMachineName="RYARRAGPF13G3GG";
		String sqlPortNumber="1433";
		String sqlUsername="sqlryarrago";
		String sqlPassword="Rukkusql@2021";
		String sqlDBName="COEAutomation";
		String sqlTable="PROJAVA";
		ArrayList<String> sArrayList = new ArrayList<String>();
		sArrayList.add("AAAAA&&BBBBB&&CCCCC&&DDDDD&&EEEEE&&FFFFF");
		sArrayList.add("Project_Java&&2021-01-28 03:02:15&&Launch_Gmail&&PASS&&2021-01-28 03:02:22&&2021-01-28 03:02:34");
		sArrayList.add("Project_Java&&2021-01-28 03:02:15&&Launch_Youtube&&PASS&&2021-01-28 03:02:34&&2021-01-28 03:02:48");
		sArrayList.add("Project_Java&&2021-01-28 03:02:15&&Launch_Google_Maps&&PASS&&2021-01-28 03:02:48&&2021-01-28 03:03:00");
		SQL_Update_Results_To_DB(sqlMachineName,sqlPortNumber,sqlUsername,sqlPassword,sqlDBName,sqlTable,sArrayList);

	}

	public static void SQL_Update_Results_To_DB(String sqlMachineName,String sqlPortNumber,String sqlUsername,String sqlPassword,String sqlDBName,String sqlTable,ArrayList<String> sArrayList)
	{
		try
		{
			//Connection conn = DriverManager.getConnection("jdbc:sqlserver://RYARRAGPF13G3GG:1433;user=sqlryarrago;password=Rukkusql@2021;databaseName=COEAutomation");
			Connection conn = DriverManager.getConnection("jdbc:sqlserver://"+sqlMachineName+":"+sqlPortNumber+";user="+sqlUsername+";password="+sqlPassword+";databaseName="+sqlDBName);
			System.out.println("Connected to MS SQL Successfully.");
			Statement statemnt = conn.createStatement();

			String Sql = "select * from PROJAVA";

			int sALSize = sArrayList.size();
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
				System.out.println("Inserted record #" + i + " into the data base : " + sSQLText);
			}
		}
		catch(Exception e)
		{
			System.out.println("Facing exception while writing results to DB: " + e.getMessage());
		}
	}

}
