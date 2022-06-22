package com.crm.vtiget.purchasemodule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.crm.Test.genericUtilities.ExcelUtilities;
import com.crm.Test.genericUtilities.FileUtilities;
import com.crm.Test.genericUtilities.JavaUtility;
import com.crm.Test.genericUtilities.WebDriverCommonLibraries;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ToCreatePurchaseOrderWithItemAndAddServiceTest {

	public static void main(String[] args) throws Throwable {
		// using generic libraries
		
		FileUtilities flib=new FileUtilities();
		ExcelUtilities elib=new ExcelUtilities();
		JavaUtility jlib=new JavaUtility();
		WebDriverCommonLibraries wlib=new WebDriverCommonLibraries();

		//initialzing webdriver
		WebDriver driver=null;
		
		//fetching common data form generic lib
		String url = flib.getPropertyKeyValue("url");
		String username = flib.getPropertyKeyValue("username");
		String password = flib.getPropertyKeyValue("password");
		String browser = flib.getPropertyKeyValue("browser");
		String LoginTitle=flib.getPropertyKeyValue("LoginTitle");
		
		//fetching purchase order data from excel utility
		String subject = elib.readExcelData("sheet1", 16, 2);
		String billing_address=elib.readExcelData("sheet1", 16, 3);
		String qty=elib.readExcelData("sheet1", 16, 4);
		String service_name=elib.readExcelData("sheet1",19, 2);
		String product_name=elib.readExcelData("sheet1", 1, 2);
		
		//to generat the random number
		int randNum = jlib.getrandomNumber();
	//verifying browser type		
		if(browser.equalsIgnoreCase("chrome"))
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
		//to maximize the window
		driver.manage().window().maximize();
		//to enter url fetching from property file
		driver.get(url);
		
driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//to inspect username and password text box
		
		driver.findElement(By.name("user_name")).sendKeys(username);
		driver.findElement(By.name("user_password")).sendKeys(password);
		driver.findElement(By.id("submitButton")).click();

		
		//to inspect more
		WebElement ele1 = driver.findElement(By.linkText("More"));
		
		wlib.mouseOverAnElement(driver, ele1);
	
		//to inspect purchase order
		
		driver.findElement(By.linkText("Purchase Order")).click();
		
		//to add purchase order
		driver.findElement(By.xpath("//img[@title='Create Purchase Order...']")).click();
		
		
		//in new purchase order page inspect subject
		
		driver.findElement(By.name("subject")).sendKeys(subject);
		
		//inspect add vendor icon symbol	
		
	 WebElement vendorIcon = driver.findElement(By.xpath("//img[@src='themes/softed/images/select.gif']"));
	 vendorIcon.click();
	 
	
	wlib.switchToWindow(driver,"Vendor&action");
	/*	Set<String> Alladd = driver.getWindowHandles();
		for(String w:Alladd)
		{
		
			driver.switchTo().window(w);
			String title = driver.getTitle();
			System.out.println(title);
			if(title.contains("Vendor&action"))
			{
				break;
			}
		}*/
				
		driver.findElement(By.id("search_txt")).sendKeys("HP");
		driver.findElement(By.name("search")).click();
		driver.findElement(By.id("1")).click();
		System.out.println("vendor is added");
		wlib.switchToWindow(driver,"Product&action");
	//	driver.switchTo().defaultContent();
	
	/*	Set<String> Alladd1 = driver.getWindowHandles();
		for(String w:Alladd1)
		{
		
			driver.switchTo().window(w);
			String title = driver.getTitle();
			//System.out.println(title);
			if(title.contains("Product&action"))
			{
				break;
			}
		}*/
		
		//
		//wlib.switchToWindow(driver,"Product&action");
		//to return back to parent browser
		driver.switchTo().defaultContent();
		
		//to inspect status drop down
		WebElement statusDrp = driver.findElement(By.name("postatus"));
		
		wlib.selectDropDownOption("Delivered", statusDrp);
		
	//	Select s=new Select(statusDrp);
	//	s.selectByVisibleText("Delivered");
		
		//inspect assigned radio button
		WebElement resEle = driver.findElement(By.xpath("//input[@value='T']"));
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
				driver.findElement(By.xpath("(//textarea[@class='detailedViewTextBox'])[1]")).sendKeys(billing_address);
				
		
		
		//inspect radio button
		WebElement resele1 = driver.findElement(By.xpath("(//input[@name='cpy']|//b[text()='Copy Billing address'])[2]"));
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
		
		
		wlib.scrollBarAction(driver);
		
		
	//	JavascriptExecutor js=(JavascriptExecutor) driver;
	//	js.executeScript("window.scrollBy(0,500)");
		
		
		WebElement allIdPro = driver.findElement(By.id("searchIcon1"));
		allIdPro.click();
		
		wlib.switchToWindow(driver,"Product&action");
/*		Set<String> allIdPro1 = driver.getWindowHandles();
		for(String w:allIdPro1)
		{
		
			driver.switchTo().window(w);
			String title = driver.getTitle();
			//System.out.println(title);
			if(title.contains("Product&action"))
			{
				break;
			}
		}
		*/
		//to search
		driver.findElement(By.id("search_txt")).sendKeys(product_name);
		driver.findElement(By.name("search")).click();
		driver.findElement(By.id("popup_product_106")).click();
		System.out.println("product is added");
		

		wlib.switchToWindow(driver,"PurchaseOrder&action");
	/*	Set<String> Alladd3 = driver.getWindowHandles();
		for(String w:Alladd3)
		{
		
			driver.switchTo().window(w);
			String title = driver.getTitle();
			//System.out.println(title);
			if(title.contains("Purchase&action"))
			{
				break;
			}
		}*/
		
		driver.switchTo().defaultContent();
		
		//to enter qyt
		driver.findElement(By.id("qty1")).sendKeys(qty);
			
		//to inspect add service button
		driver.findElement(By.xpath("//input[@value='Add Service']")).click();
		
		//to inspect add service icon
		driver.findElement(By.xpath("//img[@src='themes/images/services.gif']")).click();
		wlib.switchToWindow(driver,"Product&action");
				
	/*	Set<String> Alladd2 = driver.getWindowHandles();
		for(String w:Alladd2)
		{
		
			driver.switchTo().window(w);
			String title = driver.getTitle();
			//System.out.println(title);
			if(title.contains("Product&action"))
			{
				break;
			}
		}*/
		//driver.findElement(By.id("search_txt")).sendKeys(service_name);
		//driver.findElement(By.name("search")).click();
		driver.findElement(By.linkText("SoftwareUpdating")).click();
		System.out.println("service is added");
		
		wlib.switchToWindow(driver,"Purchase&action");
	/*	Set<String> Alladd4 = driver.getWindowHandles();
		for(String w:Alladd4)
		{
		
			driver.switchTo().window(w);
			String title = driver.getTitle();
			//System.out.println(title);
			if(title.contains("Purchase&action"))
			{
				break;
			}
		}*/
		//to return back to parent browser
	driver.switchTo().defaultContent();
	
	//to enter qyt
	driver.findElement(By.id("qty2")).sendKeys("2");
	
	wlib.scrollBarAction(driver);
//	js.executeScript("window.scrollBy(0,1000)");
	
		//inspect save
				driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
				
				wlib.scrollBarAction(driver);
				
			//	js.executeScript("window.scrollBy(0,-1000)");
				//to log out
				driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();
				
				//to click on sign out
				driver.findElement(By.linkText("Sign Out")).click();
				
				//verification
				String signUpPage = driver.getTitle();
				if(signUpPage.equals(LoginTitle))
				{
					System.out.println("signed out successfully and Sign up page is displayed");
				}
				else
				{
					System.out.println(" not signed out");
				}
				
				
				driver.close();

	}

}
