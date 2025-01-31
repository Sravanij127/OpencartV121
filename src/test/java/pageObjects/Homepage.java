package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Homepage extends Basepage{
	
	//WebDriver driver;
	
	public Homepage(WebDriver driver)
	{
		super(driver);
	}

	@FindBy(xpath="//span[normalize-space()='My Account']")
	WebElement lnkMyaccount;
	
	@FindBy(xpath="(//a[normalize-space()='Register'])[1]")
	WebElement lnkRegister;
	  
	@FindBy(linkText = "Login") //Logi linkadded ins teps
	WebElement linkLogin;
	
	
	
	public void clickMyAccount()
	{
		lnkMyaccount.click();
	}
	
	public void clickRegister()
	{
		lnkRegister.click();
	}
	public void clickLogin()
	{
		linkLogin.click();
	}
	
	
}
