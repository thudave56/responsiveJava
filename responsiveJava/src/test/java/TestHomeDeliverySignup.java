package test.java;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
//import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

public class TestHomeDeliverySignup {

	
	
	public  String SELENIUM_HUB_URL;
	public  String TARGET_SERVER_URL;

	private static String getConfigurationProperty(String envKey,
			String sysKey, String defValue) {
		String retValue = defValue;
		String envValue = System.getenv(envKey);
		String sysValue = System.getProperty(sysKey);
		// system property prevails over environment variable
		if (sysValue != null) {
			retValue = sysValue;
		} else if (envValue != null) {
			retValue = envValue;
		}
		return retValue;
	}



	public void signUp(DesiredCapabilities browser)
			throws MalformedURLException, IOException, InterruptedException {
		WebDriver driver = null;
		try {
			driver = new RemoteWebDriver(new URL(SELENIUM_HUB_URL), browser);

			//  test starts in Codes entity list page
			driver.get(TARGET_SERVER_URL);
			
			System.out.println(System.getProperty("user.dir"));

//			DesiredCapabilities reportCap;
//			reportCap = (DesiredCapabilities) driver;
//			String reportkey = (String) browser.getCapability("reportKey");
//			String executionId = (String) browser.getCapability("executionId");		
//
//			System.out.println(reportkey);
//			System.out.println(executionId);

			//driver.get("http://www.bostonglobe.com/");
			System.out.println(SELENIUM_HUB_URL + " " + browser.getPlatform());
			
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);



			//driver.findElement(By.xpath("//a[text()='Home Delivery']")).click();


			driver.findElement(By.xpath("//input[@name='txtZip']")).sendKeys("02116");

			driver.findElement(By.xpath("//div[@name='cmdSubmit']")).click();

			Thread.sleep(2000);

			driver.findElement(By.xpath("//label[@for='rdSubscription1']")).click();

			driver.findElement(By.xpath("//div[@id='continue_btn']")).click();

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			driver.findElement(By.xpath("//input[@id='txtDeliveryFirstName']")).sendKeys("Patrick");

			driver.findElement(By.xpath("//input[@id='txtDeliveryLastName']")).sendKeys("McCartney");

			driver.findElement(By.xpath("//input[@id='txtDeliveryAddress1']")).sendKeys("28 Main St");

			driver.findElement(By.xpath("//input[@id='txtDeliveryAddress2']")).sendKeys("Apt. 2");

			driver.findElement(By.xpath("//input[@id='txtDeliveryAreaCode']")).sendKeys("781");

			driver.findElement(By.xpath("//input[@id='txtDeliveryPhone3']")).sendKeys("847");

			driver.findElement(By.xpath("//input[@id='txtDeliveryPhone4']")).sendKeys("4433");		

			driver.findElement(By.xpath("//input[@id='txtDeliveryEMail']")).sendKeys("patrickm@perfectomobile.com");			



		} finally {
			if (driver != null) {
				driver.close();
				
//				Capabilities capabilities = driver.getCapabilities();
//				String executionId = (String) capabilities.getCapability("executionId");
//				String reportKey = (String) capabilities.getCapability("reportKey");
				
				
				driver.quit();
			}
		}

	}

	
//	@DataProvider(parallel = true)
//	  public Object[][] dp() {
//	    return new Object[][] {
//	      new Object[] { "Android", "Patrick", "Chrome" },
//	      new Object[] { "iOS", "Patrick", "Safari" },
//	    };
//	  }
//	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@BeforeMethod
	public void setUp() throws Exception {
	}

	@AfterMethod
	public void tearDown() throws Exception {
	}

	@Parameters({ "targetEnvironment" })
	@Test
	public void test(String targetEnvironment) throws MalformedURLException,
	IOException, InterruptedException {

		boolean device = false;
		DesiredCapabilities capabilities = new DesiredCapabilities();


		switch (targetEnvironment) {
		case "Galaxy S5":
			device = true;
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("description", "Patrick");
			capabilities.setCapability("browser", "mobileOS");
			break;

		case "iPhone 6":
			device = true;
			capabilities.setCapability("platformName", "iOS");
			capabilities.setCapability("description", "Patrick");
			capabilities.setCapability("browser", "mobileSafari");
			break;

		case "Internet Explorer 11":
			device = false;
			DesiredCapabilities.internetExplorer();
			capabilities.setCapability("platform", Platform.ANY);
			capabilities.setCapability("browserName", "internet explorer");
			capabilities.setCapability("version", "11");
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true); 
			break;

		case "Internet Explorer 10":
			device = false;
			capabilities.setCapability("platform", Platform.ANY);
			capabilities.setCapability("browserName", "internet explorer");
			capabilities.setCapability("version", "10");
			break;	

		case "Firefox 34":
			device = false;
			capabilities.setCapability("platform", Platform.ANY);
			capabilities.setCapability("browserName", "firefox");
			capabilities.setCapability("version", "34.0");
			break;

		case "Firefox 35":
			device = false;
			capabilities.setCapability("platform", Platform.ANY);
			capabilities.setCapability("browserName", "firefox");
			capabilities.setCapability("version", "35.0");
			break;

		case "Chrome":
			device = false;
			capabilities.setCapability("platform", Platform.ANY);
			capabilities.setCapability("browserName", "chrome");
			capabilities.setCapability("version", "");
			break;
		}

		TARGET_SERVER_URL = getConfigurationProperty("TARGET_SERVER_URL",
				"test.target.server.url", "http://homedelivery.bostonglobe.com/");

		if (device) {

			System.out.println(targetEnvironment + ": device");
			String host = "demo.perfectomobile.com";
			String user = URLEncoder.encode("patrickm@perfectomobile.com",
					"UTF-8");
			String password = URLEncoder.encode("perfecto", "UTF-8");
			URL gridURL = new URL("https://" + user + ':' + password + '@'
					+ host + "/nexperience/wd/hub");

			SELENIUM_HUB_URL = getConfigurationProperty("SELENIUM_HUB_URL",
					"test.selenium.hub.url", gridURL.toString());

		} else {
			System.out.println(targetEnvironment + ": desktop");;
			SELENIUM_HUB_URL = getConfigurationProperty("SELENIUM_HUB_URL",
					"test.selenium.hub.url",
					"http://seleniumgrid.perfectomobilelab.net:4444/wd/hub"); 
			
		}

		signUp(capabilities);

	}

}
