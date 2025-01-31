package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {
	
	//Dataprovider 1
	
	@DataProvider(name="LoginData")
	
	public String [][] getData() throws IOException  
	{
		String path=".\\testData\\OpecartLoginData.xlsx";  //taking xl file from testData
		
		ExcelUtility xlutil=new ExcelUtility(path);  //creating an  object for xlutility
		
		int totalrows=xlutil.getRowCount("Sheet1");
		int totalcols=xlutil.getCellCount("Sheet1",1);
		
		String logindata[][]=new String[totalrows][totalcols];  //created for two dimension arrey  which  can  stored
		
		for(int i=1; i<=totalrows; i++)  //1  //read the data from xl storing in two dimesnional array
		{
			for(int j=0; j<totalcols; j++)  //0 1 is rows j is col
			{
				logindata[i-1][j]= xlutil.getCellData("Sheet1", i, j);   //1,0
			}

		}
		return logindata;  //returning two dimentions array
		
	}
	
	//Dataprovider 2
	//Dataprovider 3
	//Dataprovider 4

	

}
