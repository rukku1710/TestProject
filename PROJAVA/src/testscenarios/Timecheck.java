package testscenarios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Timecheck 
{
	public static void main(String[] args) throws ParseException, InterruptedException
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		String currenttime = dtf.format(now);
		System.out.println("Time is : " + currenttime);

		String sStartDate = dtf.format(now);

		Thread.sleep(10000);
		LocalDateTime now2 = LocalDateTime.now();
		String sEndDate = dtf.format(now2);

		System.out.println("Start Date is --> " + sStartDate + ". End Date is --> " + sEndDate);

		SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

		Date d1 = sdf.parse(sStartDate);
		Date d2 = sdf.parse(sEndDate);

		long difference_In_Time = d2.getTime() - d1.getTime();

		long difference_In_Seconds = (difference_In_Time/ 1000) % 60;

		long difference_In_Minutes = (difference_In_Time/ (1000 * 60))% 60;
		
		String sTimeDifference = difference_In_Minutes + " minutes, " + difference_In_Seconds + " seconds";

		System.out.println(sTimeDifference);
	}

}

