package test.java;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.perfecto.reportium.client.ReportiumClient;
import com.perfecto.reportium.client.ReportiumClientFactory;
import com.perfecto.reportium.exception.ReportiumException;
import com.perfecto.reportium.model.PerfectoExecutionContext;
import com.perfecto.reportium.model.Project;
import com.perfecto.reportium.test.TestContext;
import com.perfecto.reportium.test.result.TestResultFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestHomeDeliverySignup {

	public RemoteWebDriver driver;
	public boolean device;
	public boolean fast;
	ReportiumClient reportiumClient;
	String OS;
	int retry = 1; //number of times to retry
	int retryInterval = 5000; //retry in MS
	Exception ex = null;

	public RemoteWebDriver createDriver(String targetEnvironment) throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		//capabilities.setCapability("openDeviceTimeout", 5);

		switch (targetEnvironment) {
		case "Galaxy S6":
			device = true;
			fast = false;
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("deviceName", "05157DF532C1D40F");
			capabilities.setCapability("browserName", "mobileChrome");
			break;

		case "iPhone 6":
			device = true;
			fast = false;
			capabilities.setCapability("platformName", "iOS");
			capabilities.setCapability("deviceName", "030FB4BA028AFB24D4800B3715516A680E022C5D");
			capabilities.setCapability("browserName", "mobileSafari");
			break;
		
		case "Chrome 56":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "10");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "56");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			break;
			
		case "Chrome 62":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "10");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "62");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			break;	

		case "Internet Explorer 11":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "8.1");
			capabilities.setCapability("browserName", "Internet Explorer");
			capabilities.setCapability("browserVersion", "11");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
			break;

		case "Firefox 54":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "8.1");
			capabilities.setCapability("browserName", "Firefox");
			capabilities.setCapability("browserVersion", "54");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
			break;

		case "Firefox 53":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "7");
			capabilities.setCapability("browserName", "Firefox");
			capabilities.setCapability("browserVersion", "53");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			break;

		case "Chrome 48":
			device = false;
			fast = false;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "XP");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "48");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
			break;
		
		case "Chrome Beta":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "7");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "beta");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			break;
		case "Safari 10 Sierra":
			device = false;
			fast = false;
			capabilities.setCapability("platformName", "Mac");
			capabilities.setCapability("platformVersion", "macOS Sierra");
			capabilities.setCapability("browserName", "Safari");
			capabilities.setCapability("browserVersion", "10");
			capabilities.setCapability("resolution", "1440x900");
			capabilities.setCapability("location", "NA-US-BOS");
			break;	
			
		case "Safari 9 Yosemite":
			device = false;
			fast = false;
			capabilities.setCapability("platformName", "Mac");
			capabilities.setCapability("platformVersion", "OS X Yosemite");
			capabilities.setCapability("browserName", "Safari");
			capabilities.setCapability("browserVersion", "9");
			capabilities.setCapability("resolution", "1440x900");
			capabilities.setCapability("location", "NA-US-BOS");
			break;
		
		case "Safari 8 Yosemite":
			device = false;
			fast = false;
			capabilities.setCapability("platformName", "Mac");
			capabilities.setCapability("platformVersion", "OS X Yosemite");
			capabilities.setCapability("browserName", "Safari");
			capabilities.setCapability("browserVersion", "8");
			capabilities.setCapability("resolution", "1440x900");
			capabilities.setCapability("location", "NA-US-BOS");
			break;
		
		case "Safari 9 Capitan":
			device = false;
			fast = false;
			capabilities.setCapability("platformName", "Mac");
			capabilities.setCapability("platformVersion", "OS X El Capitan");
			capabilities.setCapability("browserName", "Safari");
			capabilities.setCapability("browserVersion", "9");
			capabilities.setCapability("resolution", "1440x900");
			capabilities.setCapability("location", "NA-US-BOS");
			break;
		
		case "Firefox Beta":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "10");
			capabilities.setCapability("browserName", "Firefox");
			capabilities.setCapability("browserVersion", "beta");
			capabilities.setCapability("resolution", "1280x1024");
			capabilities.setCapability("location", "US East");
			break;
		case "Edge 16":
			device = false;
			fast = true;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "10");
			capabilities.setCapability("browserName", "Edge");
			capabilities.setCapability("browserVersion", "16");
			capabilities.setCapability("resolution", "1600x1200");
			capabilities.setCapability("location", "US East");
			break;
			
		}

		capabilities.setCapability("user", System.getProperty("PerfectoUsername"));
		capabilities.setCapability("password", System.getProperty("PerfectoPassword"));		
		if(fast) { capabilities.setCapability("securityToken", System.getProperty("PerfectoToken"));}
		//capabilities.setCapability("securityToken", "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJmZGNiNDRjMS1hN2VhLTQwM2MtYmNhOS1jNmYyOWVlNjg4OTkiLCJleHAiOjAsIm5iZiI6MCwiaWF0IjoxNTAwOTIzNjI0LCJpc3MiOiJodHRwczovL2F1dGgucGVyZmVjdG9tb2JpbGUuY29tL2F1dGgvcmVhbG1zL2RlbW8tcGVyZmVjdG9tb2JpbGUtY29tIiwiYXVkIjoib2ZmbGluZS10b2tlbi1nZW5lcmF0b3IiLCJzdWIiOiJlM2I3ODM1ZS03M2Y1LTQwYzAtYWE4YS00ZWVmYzg5NjU4NTUiLCJ0eXAiOiJPZmZsaW5lIiwiYXpwIjoib2ZmbGluZS10b2tlbi1nZW5lcmF0b3IiLCJzZXNzaW9uX3N0YXRlIjoiNTAyMGZjNGEtMzcxNi00ZDI3LTgxZTktYjcyN2U0MjJmYTY2IiwiY2xpZW50X3Nlc3Npb24iOiJjOGQxNGNlMi1iZTA1LTRmMDYtOTQyOS03NTNlMWNkMzYwNzMiLCJyZWFsbV9hY2Nlc3MiOnsicm9sZXMiOlsib2ZmbGluZV9hY2Nlc3MiXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50Iiwidmlldy1wcm9maWxlIl19fX0.nt3aR7ZD1M21h32OWJUgI0Wc3cCZ9otJBRT24yiTTUpVNJlbgt9JxW-laCXVZYbaf3H4zTy8WdNAYIt8j26fBNH5UXPQRnIGqcSM_1cRPK_KYHRLJ_ELyZcX5B753MjOILRZeo44vkh5aOZl0nO_Afyij74sectzejnUFvf1vCpRzM_FEnWIo7TL8JsTO-1YfQ4R4VmPpU_tZUhT6sDlPTrwJ0v9b021cNIjpHtGeCXUT4Z-As0mfET1o6ITyWSIOQchWpJNDoKj-AAe9OyHSUfCNoM39dhSb4BEtWanj3ViYCkIgMXl0A73I1QmBC7FsboTf4yebkBWMaZkoe3SNw");        
		
		capabilities.setCapability("newCommandTimeout", "30");
		if (device) { capabilities.setCapability("windTunnelPersona", "Georgia"); }
		//	if (device) { capabilities.setCapability("windTunnelPersona", "Ross"); }
		
		capabilities.setCapability("scriptName", "Boston Globe - " + targetEnvironment);
		capabilities.setCapability("outputVideo", true);
		capabilities.setCapability("outputReport", true);
		String tunnelId;
		tunnelId = System.getProperty("tunnelId");
		if(tunnelId != null){
			capabilities.setCapability("tunnelId",	tunnelId);
		}
		
		long startTime; 
		while(retry > 0 && driver == null) {
			startTime = System.nanoTime();
			try {
				System.out.println("Trying to aquire session: " + targetEnvironment);
				if(fast) {
					
					driver = new RemoteWebDriver(new URL("https://demo.perfectomobile.com/nexperience/perfectomobile/wd/hub/fast"),
						capabilities);
					System.out.println(targetEnvironment + ": " + (System.nanoTime() - startTime) / 1000000);
				} else {
					driver = new RemoteWebDriver(new URL("https://demo.perfectomobile.com/nexperience/perfectomobile/wd/hub"),
						capabilities);
					System.out.println(targetEnvironment + ": " + (System.nanoTime() - startTime) / 1000000);
				}
				
			} catch (Exception e) {
				retry--;
				
				System.out.println("Failed to aquire browser session: " + targetEnvironment + ". Retrying...");
				sleep(retryInterval);
				ex = e;
	            StringWriter sw = new StringWriter();
	            e.printStackTrace(new PrintWriter(sw));
	            String exceptionAsString = sw.toString();
	            System.out.println(exceptionAsString);
	            throw e; 
			}		
		}
		
		OS = capabilities.getCapability("platformName").toString();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		if(device) {startLogging();}
	
		System.out.println(targetEnvironment + " - " + driver.manage().window().getSize());
		
		
		
		return driver;
	}

	@Attachment
	public byte[] takeScreenshot() {
		//System.out.println("Taking screenshot");
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	@Test
	public void BostonGlobeTest() {
	/*
		driver.get("http://192.168.1.101:5000");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		takeScreenshot();
		driver.findElementByXPath("//*[@id='login_username']").sendKeys("patrick");
		driver.findElementByXPath("//*[@id='login_passwd']").sendKeys("test");
		driver.findElementByXPath("//*[@id='ext-gen40']").click();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		takeScreenshot();
		
		takeScreenshot();
		*/
		try{
			openHomepage();
			enterZipCode();
			selectLength();
			enterDetails();			
		} catch (Exception e) {
			ex = e;
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String exceptionAsString = sw.toString();
            System.out.println(exceptionAsString);
            throw e; 
		}



		
	}

	public void openHomepage() {
		reportiumClient.stepStart("Open Homepage");
		//System.out.println("### Opening homepage ###");
		driver.get(
				"http://subscribe.bostonglobe.com/B0004/?rc=WW011964&globe_rc=WW011964&p1=BGHeader_HomeDeliverySubscription");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='txtZip']")));
		takeScreenshot();
		reportiumClient.stepEnd("Open Homepage");


	}

	public void enterZipCode() {
		reportiumClient.stepStart("Enter Zip Code");
		
		//System.out.println("### Entering zipcode ###");
		driver.findElement(By.xpath("//input[@name='txtZip']")).clear();
		driver.findElement(By.xpath("//input[@name='txtZip']")).sendKeys("02116");
		//driver.findElement(By.xpath("//input[@name='txtZip']")).sendKeys("secured.eW1U4AHF/7fA0km7X2ty2w==");
		
		
		driver.findElement(By.xpath("//input[@id='cmdSubmit']")).click();
		takeScreenshot();
		reportiumClient.stepEnd("Enter Zip Code");
	}

	public void selectLength() {
		reportiumClient.stepStart("Select Subscription Length");
		//System.out.println("### Selecting subscription length ###");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//label[1]/strong[1])[1]")));

		//JavascriptExecutor js = (JavascriptExecutor) driver;
		//js.executeScript("$('input:radio[name=rdSubscription][value=4]').trigger('click');");
		
		driver.findElement(By.xpath("//ul[@id='available_offers_list']/li[1]")).click();
		driver.findElement(By.xpath("//input[@id='continue_btn']")).click();
		takeScreenshot();

		reportiumClient.stepEnd("Select Subscription Length");
		
		
	}

	public void enterDetails() {
		reportiumClient.stepStart("Enter Subscription Details");
		sleep(1000);
		//System.out.println("### Entering subscription details ###");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='txtDeliveryFirstName']")));
		driver.findElement(By.xpath("//input[@id='txtDeliveryFirstName']")).sendKeys("Patrick");
		driver.findElement(By.xpath("//input[@id='txtDeliveryLastName']")).sendKeys("McCartney");
		driver.findElement(By.xpath("//input[@id='txtDeliveryAddress1']")).sendKeys("28 Main St");
		driver.findElement(By.xpath("//input[@id='txtDeliveryAddress2']")).sendKeys("Apt. 2");
		driver.findElement(By.xpath("//input[@id='txtDeliveryAreaCode']")).sendKeys("781");
		driver.findElement(By.xpath("//input[@id='txtDeliveryPhone3']")).sendKeys("847");
		driver.findElement(By.xpath("//input[@id='txtDeliveryPhone4']")).sendKeys("4433");
		driver.findElement(By.xpath("//input[@id='txtDeliveryEMail']")).sendKeys("patrickm@perfectomobile.com");
		takeScreenshot();
		reportiumClient.stepEnd("Enter Subscription Details");
	}

	@BeforeClass(alwaysRun = true)
	public void baseBeforeClass(ITestContext context) throws MalformedURLException {
		Map<String, String> params = context.getCurrentXmlTest().getAllParameters();

		driver = createDriver(params.get("targetEnvironment"));
		reportiumClient = getReportiumClient(driver);
	}

	@AfterClass(alwaysRun = true)
	public void baseAfterClass() {
		try{ System.out.println("Report url = " + reportiumClient.getReportUrl()); }
		catch (Exception e) {}
		
		if (driver != null) {
			//if(device){endLogging();}
			driver.quit();
		}
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTest(Method method) {
		String testName = method.getDeclaringClass().getSimpleName() + "." + method.getName();
		reportiumClient.testStart(testName, new TestContext());
	}

	@AfterMethod(alwaysRun = true)
	public void afterTest(ITestResult testResult) {
		if(ex != null){
			testResult.setStatus(ITestResult.FAILURE);
			testResult.setThrowable(ex);
		}
		
		int status = testResult.getStatus();
		
		switch (status) {
		case ITestResult.FAILURE:
			reportiumClient.testStop(TestResultFactory.createFailure("An error occurred", testResult.getThrowable()));
			break;
		case ITestResult.SUCCESS_PERCENTAGE_FAILURE:
		case ITestResult.SUCCESS:
			reportiumClient.testStop(TestResultFactory.createSuccess());
			break;
		case ITestResult.SKIP:
			// Ignore
			break;
		default:
			throw new ReportiumException("Unexpected status " + status);
		}
		
		if (driver != null) {
			if(device){endLogging();}
			driver.close();
		}
	}

	protected static ReportiumClient getReportiumClient(RemoteWebDriver driver) {
		
		
		
		PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
				.withProject(new Project("Boston Globe", "1.0")) // Optional
				.withContextTags("Build " + System.getProperty("BuildNumber"), "Software Version: 1.6", "Responsive Build Validation", "patrickm", System.getProperty("tunnelId")) // Optional
				.withWebDriver(driver).build();

		return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {}
	}
	
	private void startLogging() {
		Map<String, Object> params = new HashMap<>();
		driver.executeScript("mobile:logs:start", params);
		
		params.clear();
		List<String> vitals3 = new ArrayList<>();
		vitals3.add("all");
		params.put("vitals", vitals3);
		params.put("interval", "1");
		driver.executeScript("mobile:monitor:start", params);
		
		
		Map<String, Object> params1 = new HashMap<>();
		params1.put("profile", "4g_lte_average");
		Object result1 = driver.executeScript("mobile:vnetwork:start", params1);
	}
	
	private void endLogging() {
		Map<String, Object> params2 = new HashMap<>();
		Object result2 = driver.executeScript("mobile:logs:stop", params2);
		
		Map<String, Object> params3 = new HashMap<>();
		Object result3 = driver.executeScript("mobile:vnetwork:stop", params3);
		
		
	}

}
