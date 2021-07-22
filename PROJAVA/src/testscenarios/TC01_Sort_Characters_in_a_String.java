package testscenarios;

import java.util.Arrays;

public class TC01_Sort_Characters_in_a_String 
{

	public static void main(String[] args)
	{
		//Sorting characters in a
		String sData = "technology";
		char charArray[] = sData.toCharArray();
		Arrays.sort(charArray);
		System.out.println(new String(charArray));
		
	}
}
