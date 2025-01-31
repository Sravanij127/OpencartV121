package testCases;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;

//import org.apache.logging.log4j.core.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.Logger; //log4j

public class BaseClass {
	
public static WebDriver driver;
public Logger logger;     //log4j
public Properties p;
	

@BeforeClass(groups= {"Sanity","Regression","Master"})

@Parameters({"os","browser"})
	
	public void setup(String os, String br) throws IOException
	{
		//Loadingconfig.properties file
		FileReader file= new FileReader(".//src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		
		
		logger=LogManager.getLogger(this.getClass()); //log4j2
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			//os
			if(os.equalsIgnoreCase("windows"))
			{
				capabilities.setPlatform(Platform.WIN10);

			}
			else if(os.equalsIgnoreCase("mac"))
			{
				capabilities.setPlatform(Platform.WIN10);

			}
			else
			{
				System.out.println("Nomatching os");
				return;
			}
			
			//browser
			
			switch(br.toLowerCase())
			{
			case "chrome": capabilities.setBrowserName("chrome"); break;
			case "edge": capabilities.setBrowserName("MicrosoftEdge"); break;
			default: System.out.println("No matching browser");
			return;

			}
			
			driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		}
		
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
			switch(br.toLowerCase())
			{
			case "chrome" :	driver=new ChromeDriver(); break;
			case "edge" :	driver=new EdgeDriver(); break;
			case "firefox" :	driver=new FirefoxDriver(); break;
			default : System.out.println("Invalid browser name.."); 
			return;
			}
		}
		
	
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.get(p.getProperty("appURL2"));  //reading url from properties file
		driver.manage().window().maximize();
	}
	
	@AfterClass
	void tearDown()
	{
		driver.quit();
	}
	
	public String randomeString()
	 {
		 
		@SuppressWarnings("deprecation")
		String generatedstring=RandomStringUtils.randomAlphabetic(5);
		 return generatedstring;
	 }
	 
	 public String randomeNumber()
	 {
		
		@SuppressWarnings("deprecation")
		String generatednumber=RandomStringUtils.randomNumeric(10);
		 return generatednumber;
	 }
	 
	@SuppressWarnings("deprecation")
	public String randomeAlphanumeric()
	 {
		 String generatedstring=RandomStringUtils.randomAlphabetic(3);
		 String generatednumber=RandomStringUtils.randomNumeric(3);
		 return (generatedstring+"@"+generatednumber);
	 }

	public String captureScreen1(String tname) throws IOException{
	String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()); 
	
    TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
    File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
    
	String targetFilePath = System.getProperty("user.dir") + "/screenshots/" + tname + ".png";
	File targetFile=new File(targetFilePath);
	sourceFile.renameTo(targetFile);
	
	return targetFilePath;
	}

	
	

}
