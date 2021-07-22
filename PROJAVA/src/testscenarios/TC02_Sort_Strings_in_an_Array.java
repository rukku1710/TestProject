package testscenarios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class TC02_Sort_Strings_in_an_Array 
{
	public static void main(String[] args)
	{
		String[] sArrayData = {"Ball","Zebra","Pen","Horse","Ink","Apple"};
		Sort_Array(sArrayData);
		
		ArrayList<String> ArrayListss = new ArrayList<String>();
		ArrayListss.add("Ball");
		ArrayListss.add("Pen");
		ArrayListss.add("Horse");
		ArrayListss.add("Zebra");
		ArrayListss.add("Apple");
		ArrayListss.add("Ink");
		
		Sort_using_Collections_Ascending_Order(ArrayListss);
		Sort_using_Collections_Descending_Order(ArrayListss);
	}

	public static void Sort_Array(String[] sArrayData)
	{
		//String[] sArrayData = {"Ball","Zebra","Pen","Horse","Ink","Apple"};
		Arrays.sort(sArrayData);
		System.out.println("Printing the array values in ascending order.");
		for(String sArrayValue: sArrayData)
		{
			System.out.println("Array value is : " + sArrayValue);
		}
	}
	
	public static void Sort_using_Collections_Ascending_Order(ArrayList<String> sArrayList)
	{
		Collections.sort(sArrayList);
		System.out.println(sArrayList.toString());
	}
	
	public static void Sort_using_Collections_Descending_Order(ArrayList<String> sArrayList)
	{
		Collections.sort(sArrayList,Collections.reverseOrder());
		System.out.println(sArrayList.toString());
	}

}
