package testscenarios;

public class TC03_Reverse_Characters_in_String 
{
	public static void main(String[] args)
	{
		String sReversedData1 = Reverse_Using_Stringbuffer("technology");
		System.out.println("Reversed string using String Buffer : " + sReversedData1);

		String sReversedData2 = Reverse_Iteration("technology");
		System.out.println("Reversed string using Iteration : " + sReversedData2);
	}

	public static String Reverse_Using_Stringbuffer(String sBuffer)
	{
		StringBuilder sb=new StringBuilder(sBuffer);  
		sb.reverse();  
		String sReversedData = sb.toString(); 
		return sReversedData;
	}

	public static String Reverse_Iteration(String sData)
	{
		char ch[]=sData.toCharArray();  
		String rev="";  
		for(int i=ch.length-1;i>=0;i--)
		{  
			rev+=ch[i];  
		}  
		return rev;  
	}
}
