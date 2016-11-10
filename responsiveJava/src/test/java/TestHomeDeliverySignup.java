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
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestHomeDeliverySignup {

	public RemoteWebDriver driver;
	public boolean device;
	ReportiumClient reportiumClient;
	String OS;
	int retry = 5; //number of times to retry
	int retryInterval = 5000; //retry in MS

	public RemoteWebDriver createDriver(String targetEnvironment) throws MalformedURLException {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		//capabilities.setCapability("openDeviceTimeout", 5);

		switch (targetEnvironment) {
		case "Galaxy S6":
			device = true;
			capabilities.setCapability("platformName", "Android");
			capabilities.setCapability("deviceName", "05157DF532C1D40F");
			capabilities.setCapability("browserName", "mobileChrome");
			break;

		case "iPhone 6":
			device = true;
			capabilities.setCapability("platformName", "iOS");
			capabilities.setCapability("deviceName", "030FB4BA028AFB24D4800B3715516A680E022C5D");
			capabilities.setCapability("browserName", "mobileSafari");
			break;

		case "Internet Explorer 11":
			device = false;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "8.1");
			capabilities.setCapability("browserName", "Internet Explorer");
			capabilities.setCapability("browserVersion", "11");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
			capabilities.setCapability("deviceType", "WEB");
			break;

		case "Firefox 43":
			device = false;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "8.1");
			capabilities.setCapability("browserName", "Firefox");
			capabilities.setCapability("browserVersion", "43");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
			capabilities.setCapability("deviceType", "WEB");
			break;

		case "Firefox 46":
			device = false;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "7");
			capabilities.setCapability("browserName", "Firefox");
			capabilities.setCapability("browserVersion", "46");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
			capabilities.setCapability("deviceType", "WEB");
			break;

		case "Chrome 48":
			device = false;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "XP");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "48");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
			capabilities.setCapability("deviceType", "WEB");
			break;
		
		case "Chrome Beta":
			device = false;
			capabilities.setCapability("platformName", "Windows");
			capabilities.setCapability("platformVersion", "7");
			capabilities.setCapability("browserName", "Chrome");
			capabilities.setCapability("browserVersion", "beta");
			capabilities.setCapability("resolution", "1366x768");
			capabilities.setCapability("location", "US East");
			capabilities.setCapability("deviceType", "WEB");
			break;
		}


		capabilities.setCapability("user", System.getProperty("PerfectoUsername"));
		capabilities.setCapability("password", System.getProperty("PerfectoPassword"));


		
		capabilities.setCapability("newCommandTimeout", "30");
		if (device) { capabilities.setCapability("windTunnelPersona", "Georgia"); }
		capabilities.setCapability("scriptName", "Boston Globe");

		while(retry > 0 && driver == null) {
			try {
				driver = new RemoteWebDriver(new URL("https://demo.perfectomobile.com/nexperience/perfectomobile/wd/hub"),
						capabilities);
			} catch (Exception e) {
				retry--;
				System.out.println("Failed to aquire browser session: " + targetEnvironment + ". Retrying...");
				sleep(retryInterval);
			}		
		}
		
		OS = capabilities.getCapability("platformName").toString();
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.manage().window().maximize();

		return driver;
	}

	@Attachment
	public byte[] takeScreenshot() {
		System.out.println("Taking screenshot");
		return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
	}

	@Test
	public void BostonGlobeTest() {
		openHomepage();
		enterZipCode();
		selectLength();
		enterDetails();
	}

	public void openHomepage() {
		reportiumClient.testStep("Open Homepage");
		System.out.println("### Opening homepage ###");
		driver.get(
				"http://subscribe.bostonglobe.com/B0004/?rc=WW011964&globe_rc=WW011964&p1=BGHeader_HomeDeliverySubscription");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='txtZip']")));
		takeScreenshot();

	}

	public void enterZipCode() {
		reportiumClient.testStep("Enter Zip Code");
		System.out.println("### Entering zipcode ###");
		driver.findElement(By.xpath("//input[@name='txtZip']")).clear();
		driver.findElement(By.xpath("//input[@name='txtZip']")).sendKeys("02116");
		driver.findElement(By.xpath("//input[@id='cmdSubmit']")).click();
		takeScreenshot();
	}

	public void selectLength() {
		reportiumClient.testStep("Select Subscription Length");
		System.out.println("### Selecting subscription length ###");
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//label[1]/strong[1])[1]")));

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("$('input:radio[name=rdSubscription][value=4]').trigger('click');");
		driver.findElement(By.xpath("//input[@id='continue_btn']")).click();
		takeScreenshot();

		
		
		
	}

	public void enterDetails() {
		reportiumClient.testStep("Enter Subscription Details");
		sleep(1000);
		System.out.println("### Entering subscription details ###");
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
		
	}

	@BeforeClass(alwaysRun = true)
	public void baseBeforeClass(ITestContext context) throws MalformedURLException {
		Map<String, String> params = context.getCurrentXmlTest().getAllParameters();

		driver = createDriver(params.get("targetEnvironment"));
		reportiumClient = getReportiumClient(driver);
	}

	@AfterClass(alwaysRun = true)
	public void baseAfterClass() {
		System.out.println("Report url = " + reportiumClient.getReportUrl());
		if (driver != null) {
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
			driver.close();
		}
	}

	protected static ReportiumClient getReportiumClient(RemoteWebDriver driver) {
		PerfectoExecutionContext perfectoExecutionContext = new PerfectoExecutionContext.PerfectoExecutionContextBuilder()
				.withProject(new Project("Boston Globe", "1.0")) // Optional
				.withContextTags("Build " + System.getProperty("BuildNumber"), "Software Version: 1.59.3") // Optional
				.withWebDriver(driver).build();

		return new ReportiumClientFactory().createPerfectoReportiumClient(perfectoExecutionContext);
	}

	private static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {}
	}

}
