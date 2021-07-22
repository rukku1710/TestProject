package testscenarios;

public class StringSplitScenarios 
{
	public static void main(String[] args)
	{
		String sData = "gridcolumn-1877-textInnerEl";
		String sDataNew = sData.split("-")[0] + "-" + sData.split("-")[1];
		System.out.println(sDataNew);
		
	}

}
