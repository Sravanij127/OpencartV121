package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.ImageHtmlEmail;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testCases.BaseClass;

public class ExtentReportManager implements ITestListener{

	public ExtentSparkReporter sparkReporter;
	public  ExtentReports extent;
	public  ExtentTest test;
	
	String repName;
	
	public void onStart(ITestContext testContext) {
		
		// Generate timestamp for unique report name
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timestamp + ".html";
        sparkReporter = new ExtentSparkReporter(".\\reports\\" + repName);
        
        sparkReporter.config().setDocumentTitle("Opnecart Automation Report");
        sparkReporter.config().setReportName("Opnecart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "Opencart");
        extent.setSystemInfo("Module", "Admin");
        extent.setSystemInfo("Sub Module", "Customers");
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
        
        String os = testContext.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("Operating System", os);
        
        String browser = testContext.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);
        
        List<String> includeGroups = testContext.getCurrentXmlTest().getIncludedGroups();
        if(!includeGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includeGroups.toString());

        }
        	}
	
	public void onTestSuccess(ITestResult result) {
		test=extent.createTest(result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS,result.getMethod()+" got successfully executed");
        //test.log(Status.PASS, "Test passed: " + result.getMethod().getMethodName());

	}
	
	public void onTestFailure(ITestResult result) {
		test=extent.createTest(result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
        test.log(Status.FAIL, result.getMethod()+" got failed");
        test.log(Status.INFO, result.getThrowable().getMessage());
        
        try {
        	String imgPath = new BaseClass().captureScreen1(result.getName());
        	test.addScreenCaptureFromPath(imgPath);
        	
        } catch (IOException e) {
			 e.printStackTrace();
		 }

	}

	public void onTestSkipped(ITestResult result) {
		test=extent.createTest(result.getClass().getName());
		test.assignCategory(result.getMethod().getGroups());
		
        test.log(Status.SKIP, result.getMethod()+" got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }
	
	 public void onFinish(ITestContext testContext) {
		 extent.flush();
		 
		 
			 String pathOfExtentReport = System.getProperty("user.dir")+"\\reports\\"+repName;
			 File extentReport = new File(pathOfExtentReport);
		 
			 try {
				 Desktop.getDesktop().browse(extentReport.toURI());
			 } catch (IOException e) {
				 e.printStackTrace();
			 }
			 
			  /*try {
            // Get the report file URL
            URL url = new URL("file:///" + System.getProperty("user.dir") + "\\reports\\" + reportName);

            // Create email object
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.googlemail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("jeripothulasravani4@gmail.com", "Test@123")); // Use App Password for Gmail
            email.setSSLOnConnect(true);

            // Sender and subject
            email.setFrom("jeripothulasravani4@gmail.com", "Automation Report");
            email.setSubject("Test Results");
            // Email body
            email.setHtmlMsg("<html><h2>Please find the attached Extent Report.</h2><p>Check the report for test execution results.</p></html>");

            // Attach the report file
            //EmailAttachment attachment = new EmailAttachment();
            //attachment.setURL(url);
            //attachment.setDisposition(EmailAttachment.ATTACHMENT);
            //attachment.setDescription("Extent Report");
            //attachment.setName(reportName);
            //email.attach(attachment);
			
            // Add recipient
            email.addTo("jeripothulasravani4@gmail.com");
            email.attach(url, "extent report", "please check report...");

            // Send email
            email.send();
            
        } catch (IOException e) {
			 e.printStackTrace();
		 }
    } */
			  
	    }
	
}
