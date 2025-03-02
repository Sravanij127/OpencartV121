package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.Homepage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import utilities.DataProviders;

/*Data is valid  - login sucess - test pass -   logout
 * Data is valid  - login failed - test fail 
 * 
 * Data is invalid  - login sucess - test fail -   logout
 * Data is invalid  - login failed - test pass 
 */


public class TC003_LoginDDT extends BaseClass {
	
	@Test(dataProvider="LoginData", dataProviderClass=DataProviders.class,groups="Datadriven") //getting data provider from different class
	public void verify_LoginDDT(String email, String password, String exp)
	{
		
			logger.info(" ***  Starting TC003_LoginDDT *** ");
		       
			try
			{
			//Homepage
				Homepage hp = new Homepage(driver);
				hp.clickMyAccount();
				hp.clickLogin();
				
				//LoginPage
				LoginPage lp=new LoginPage(driver);
				lp.setEmail(email);
				lp.setPassword(password);
				lp.clickLogin();
				
				//MyAccount
				MyAccountPage macc = new MyAccountPage(driver);
				boolean targetPage=macc.isMyAccountPageExists();
				
				
				/*
				 * Data is valid  - login sucess - test pass -   logout
				  					login failed - test fail 
				  
				 * Data is invalid  - login sucess - test fail -   logout
				  						login failed - test pass 
				 */
				if(exp.equalsIgnoreCase("Valid"))
				{
					if(targetPage==true)
					{
						macc.clickLogout();
						Assert.assertTrue(true);
					}
					else
					{
						Assert.assertTrue(false);
					}
				}
				
				if(exp.equalsIgnoreCase("Invalid"))
				{
					if(targetPage==true)
					{
						macc.clickLogout();
						Assert.assertTrue(false);
					}
					
					else
					{
						Assert.assertTrue(true);

					}
				}	
			}
			
			catch(Exception e)
			{
				Assert.fail();
			}
				logger.info(" ***  Finished TC003_LoginDDT *** ");
			
	} 

}
