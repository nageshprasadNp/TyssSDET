package com.crm.vtiger.purchaseOrder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

import com.crm.Test.genericUtilities.ExcelUtilities;
import com.crm.Test.genericUtilities.FileUtilities;
import com.crm.Test.genericUtilities.JavaUtility;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.remote.RemoteWebDriver;

public class ToCreatePurchaseOrderWithItemAndAddServiceTest {

	@Test
	public void cratePurchaseOrderTest() throws EncryptedDocumentException, IOException {
	
		//initialzing webdriver
		WebDriver driver=null;
		
		FileInputStream fis=new FileInputStream("./src/test/resources/data.properties");
		Properties prop=new Properties();
		prop.load(fis);
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		String browser = prop.getProperty("browser");
		String LoginTitle=prop.getProperty("LoginTitle");
		
	
		
		FileInputStream fstream=new FileInputStream("./src/test/resources/InputData.xlsx");
		Workbook wb=WorkbookFactory.create(fstream);
	 	String subject= wb.getSheet("sheet1").getRow(16).getCell(2).getStringCellValue();
		String billing_address=wb.getSheet("sheet1").getRow(16).getCell(3).getStringCellValue();
		String qty=String.valueOf((int)wb.getSheet("sheet1").getRow(16).getCell(4).getNumericCellValue());
		String service_name=wb.getSheet("sheet1").getRow(19).getCell(2).getStringCellValue();
		String product_name=wb.getSheet("sheet1").getRow(1).getCell(2).getStringCellValue();
		
		
		
		Random random=new Random();
		int randnum = random.nextInt(100);
		
		
		URL url1=new URL("http://localhost:5555/wd/hub");
		DesiredCapabilities cap=new DesiredCapabilities();
		cap.setBrowserName("chrome");
		cap.setPlatform(Platform.WINDOWS);
		RemoteWebDriver
		driver1=new RemoteWebDriver(url1, cap);
		driver1.get(url);
		
		
		
		
/*		if(browser.equalsIgnoreCase("chrome"))
		{
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
			
		}else if(browser.equalsIgnoreCase("firefox"))
		{
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
		}
		else
		{
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver();
		}
		*/
		driver1.manage().window().maximize();
		driver1.get(url);
		
driver1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//to inspect username and password text box
		
		driver1.findElement(By.name("user_name")).sendKeys(username);
		driver1.findElement(By.name("user_password")).sendKeys(password);
		driver1.findElement(By.id("submitButton")).click();

		
		//to inspect more
		WebElement ele1 = driver1.findElement(By.linkText("More"));
		
		Actions ac=new Actions(driver1);
		ac.moveToElement(ele1).perform();
		
		//to inspect purchase order
		
		driver1.findElement(By.linkText("Purchase Order")).click();
		
		//to add purchase order
		driver1.findElement(By.xpath("//img[@title='Create Purchase Order...']")).click();
		
		
		//in new purchase order page inspect subject
		
		driver1.findElement(By.name("subject")).sendKeys(subject);
		
		//inspect add vendor icon symbol	
		
		WebElement ele2 = driver1.findElement(By.xpath("//img[@title='Select']"));
		ele2.click();
		Set<String> Alladd = driver1.getWindowHandles();
		for(String w:Alladd)
		{
		
			driver1.switchTo().window(w);
			String title = driver1.getTitle();
			System.out.println(title);
			if(title.contains("Vendor&action"))
			{
				break;
			}
		}
				
		driver1.findElement(By.id("search_txt")).sendKeys("HP");
		driver1.findElement(By.name("search")).click();
		driver1.findElement(By.id("1")).click();
		System.out.println("vendor is added");
		
		Set<String> Alladd1 = driver1.getWindowHandles();
		for(String w:Alladd1)
		{
		
			driver1.switchTo().window(w);
			String title = driver1.getTitle();
			//System.out.println(title);
			if(title.contains("Product&action"))
			{
				break;
			}
		}
		
		//
		
		//to return back to parent browser
		driver1.switchTo().defaultContent();
		
		//to inspect status drop down
		WebElement statusDrp = driver1.findElement(By.name("postatus"));
		Select s=new Select(statusDrp);
		s.selectByVisibleText("Delivered");
		
		//inspect assigned radio button
		WebElement resEle = driver1.findElement(By.xpath("//input[@value='T']"));
		boolean res = resEle.isSelected();
		if(res==true)
		{
			System.out.println("assigned radio button already selected");
		}
		else
		{
			resEle.click();
			System.out.println("assigned to radio button is select now");
		}
		
		
		//inspecting billing text field
				driver1.findElement(By.xpath("(//textarea[@class='detailedViewTextBox'])[1]")).sendKeys(billing_address);
				
		
		
		//inspect radio button
		WebElement resele1 = driver1.findElement(By.xpath("(//input[@name='cpy']|//b[text()='Copy Billing address'])[2]"));
		boolean res1 = resele1.isSelected();
		if(res1==true)
		{
			System.out.println("copy billing  radio button already selected");
		}
		else
		{
			resele1.click();
			System.out.println("copy billing radio button is selecting now");
		}
		
		
		JavascriptExecutor js=(JavascriptExecutor) driver1;
		js.executeScript("window.scrollBy(0,500)");
		
		
		WebElement allIdPro = driver1.findElement(By.id("searchIcon1"));
		allIdPro.click();
		Set<String> allIdPro1 = driver1.getWindowHandles();
		for(String w:allIdPro1)
		{
		
			driver1.switchTo().window(w);
			String title = driver1.getTitle();
			//System.out.println(title);
			if(title.contains("Product&action"))
			{
				break;
			}
		}
		
		//to search
		driver1.findElement(By.id("search_txt")).sendKeys(product_name);
		driver1.findElement(By.name("search")).click();
		driver1.findElement(By.id("popup_product_106")).click();
		System.out.println("product is added");
		

		Set<String> Alladd3 = driver1.getWindowHandles();
		for(String w:Alladd3)
		{
		
			driver1.switchTo().window(w);
			String title = driver1.getTitle();
			//System.out.println(title);
			if(title.contains("Purchase&action"))
			{
				break;
			}
		}
		
		driver1.switchTo().defaultContent();
		
		//to enter qyt
		driver1.findElement(By.id("qty1")).sendKeys("2");
		
		
		
		
		
		
		//to inspect add service button
		driver1.findElement(By.xpath("//input[@value='Add Service']")).click();
		
		//to inspect add service icon
		driver1.findElement(By.xpath("//img[@src='themes/images/services.gif']")).click();
		
				
		Set<String> Alladd2 = driver1.getWindowHandles();
		for(String w:Alladd2)
		{
		
			driver1.switchTo().window(w);
			String title = driver1.getTitle();
			//System.out.println(title);
			if(title.contains("Product&action"))
			{
				break;
			}
		}
		//driver.findElement(By.id("search_txt")).sendKeys(service_name);
		//driver.findElement(By.name("search")).click();
		driver1.findElement(By.linkText("SoftwareUpdating")).click();
		System.out.println("service is added");
		
		
		Set<String> Alladd4 = driver1.getWindowHandles();
		for(String w:Alladd4)
		{
		
			driver1.switchTo().window(w);
			String title = driver1.getTitle();
			//System.out.println(title);
			if(title.contains("Purchase&action"))
			{
				break;
			}
		}
		//to return back to parent browser
	driver1.switchTo().defaultContent();
	
	//to enter qyt
	driver1.findElement(By.id("qty2")).sendKeys("2");
	
	js.executeScript("window.scrollBy(0,1000)");
	
		//inspect save
				driver1.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
				
				
				js.executeScript("window.scrollBy(0,-1000)");
				//to log out
				driver1.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();
				
				//to click on sign out
				driver1.findElement(By.linkText("Sign Out")).click();
				
				//verification
				String signUpPage = driver1.getTitle();
				if(signUpPage.equals(LoginTitle))
				{
					System.out.println("signed out successfully and Sign up page is displayed");
				}
				else
				{
					System.out.println(" not signed out");
				}

	}

}
