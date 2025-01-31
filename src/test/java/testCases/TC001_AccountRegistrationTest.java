package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.Homepage;

public class TC001_AccountRegistrationTest extends BaseClass{
	
	//private static final String RandomeStringUtils = "5";
	
	
	@Test(groups={"Regression","Master"})
	public void verify_account_registartion()
	{
		
		logger.info("*** Starting TC001_AccountRegistrationTest ***" );
		
		try 
		{
		Homepage hp=new Homepage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on MyAccount link" );

		hp.clickRegister();
		logger.info("Clicked on Register link" );

		
		AccountRegistrationPage regpage=new AccountRegistrationPage(driver);
		
		logger.info("Providing customer details..." );

		regpage.setFirstName(randomeString().toUpperCase()); //john
		regpage.setLasttName(randomeString().toUpperCase()); //david
		regpage.setEmail(randomeString()+"@gmail.com");
		regpage.setTelephone(randomeNumber()); //"9876543210"
		
		//String password=randomAlphanumeric();
		
		String password=randomeAlphanumeric();
		
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		
		regpage.setPrivacyPolicy();
		regpage.clickContinue();
		
		
		logger.info("Validationg expected message..." );
		String confmsg=regpage.getConfirmationMsg();
		if(confmsg.equals("Your Account Has Been Created!"))
		{
			Assert.assertTrue(true);
		}
		else
		{
			logger.error("Test FAILED..." );
			logger.debug("Debug logs..." );
			Assert.assertTrue(false);

		}
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		}
		
		catch(Exception e) 
		{
			//logger.error("Test FAILED..." );
			//logger.debug("Debug logs..." );
			Assert.fail();


		}
		
		logger.info("*** Finished TC001_AccountRegistrationTest ***" );

	}
	 
   
}
	
	
	
	 
	
	


