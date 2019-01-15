package org.BBAssignment.frameworkUtils;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.BBAssignment.ExcelLib.ExcelService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

public class WebDriverBase {
	public WebDriver driver;
	
	//Objects for Pages
	
	@BeforeMethod
	public void setUp() {
		
		driver.get("http://computer-database.herokuapp.com/computers");
		
	}
	
	
	@AfterSuite
	public void close() {
		driver.quit();
	}
	

	
	@BeforeSuite
	public void initializePages() {
		System.setProperty("webdriver.chrome.driver", "./Drivers/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@DataProvider(name = "SampleAppTestData")
	public Iterator<Object[]> dataprovider2(Method method) {
		
		
		return new ExcelService().readTestDataFromExcel("TestData.xlsx","Data",method.getName()
				);

	}

}
