package com.crm.vtiger.purchaseOrder;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
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

import io.github.bonigarcia.wdm.WebDriverManager;

public class ToCreatePurchaseorderWithItemAndProductTest {

	public static void main(String[] args) throws Throwable {
				
		//to create respective objects for generic classes
				FileUtilities flib=new FileUtilities();
				ExcelUtilities elib=new ExcelUtilities();
				JavaUtility jlib=new JavaUtility();

				//initialzing webdriver
				WebDriver driver=null;
				
				//fetching form generic lib
				String url = flib.getPropertyKeyValue("url");
				String username = flib.getPropertyKeyValue("username");
				String password = flib.getPropertyKeyValue("password");
				String browser = flib.getPropertyKeyValue("browser");
				String LoginTitle=flib.getPropertyKeyValue("LoginTitle");
		
			//fetching data from generic utilities
				String subject = elib.readExcelData("sheet1", 16, 2);
				String billing_address=elib.readExcelData("sheet1", 16, 3);
				String qty=elib.readExcelData("sheet1", 16, 4);
				String product_name=elib.readExcelData("sheet1", 1, 2);
				String vendor_name=elib.readExcelData("sheet1", 22, 2);		
		
		
		Random random=new Random();
		int randnum = random.nextInt(100);
		
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
		
		driver.manage().window().maximize();
		driver.get(url);
		
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//to inspect username and password text box
		
		driver.findElement(By.name("user_name")).sendKeys(username);
		driver.findElement(By.name("user_password")).sendKeys(password);
		driver.findElement(By.id("submitButton")).click();

		
		//to inspect more
		WebElement ele1 = driver.findElement(By.linkText("More"));
		
		Actions ac=new Actions(driver);
		ac.moveToElement(ele1).perform();
		
		//to inspect purchase order
		
		driver.findElement(By.linkText("Purchase Order")).click();
		
		//to add purchase order
		driver.findElement(By.xpath("//img[@title='Create Purchase Order...']")).click();
		
		
		//in new purchase order page inspect subject
		
		driver.findElement(By.name("subject")).sendKeys(subject);
		
		//inspect add vendor icon symbol	
		
		WebElement ele2 = driver.findElement(By.xpath("//img[@title='Select']"));
		ele2.click();
		Set<String> Alladd = driver.getWindowHandles();
		for(String w:Alladd)
		{
		
			driver.switchTo().window(w);
			String title = driver.getTitle();
			//System.out.println(title);
			if(title.contains("Vendor&action"))
			{
				break;
			}
		}
				
		driver.findElement(By.id("search_txt")).sendKeys(vendor_name);
		driver.findElement(By.name("search")).click();
		driver.findElement(By.id("1")).click();
		
		Set<String> Alladd1 = driver.getWindowHandles();
		for(String w:Alladd1)
		{
		
			driver.switchTo().window(w);
			String title = driver.getTitle();
			//System.out.println(title);
			if(title.contains("Product&action"))
			{
				break;
			}
		}
		
		
		
		//to return back to parent browser
		driver.switchTo().defaultContent();
		
		//to inspect status drop down
		WebElement statusDrp = driver.findElement(By.name("postatus"));
		Select s=new Select(statusDrp);
		s.selectByVisibleText("Delivered");
		
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
			System.out.println("Assigned radio button is select now");
		}
		
		
		//inspecting billing text field
				driver.findElement(By.xpath("(//textarea[@class='detailedViewTextBox'])[1]")).sendKeys(billing_address);
				
		
		
		//inspect radio button
		WebElement resele1 = driver.findElement(By.xpath("(//input[@name='cpy']|//b[text()='Copy Billing address'])[2]"));
		boolean res1 = resele1.isSelected();
		if(res1==true)
		{
			System.out.println("assigned radio button already selected");
		}
		else
		{
			resele1.click();
			System.out.println("radio button is select now");
		}
		
		//for scroll bar
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
		
		//inspect product icon on item panel
		WebElement allIdPro = driver.findElement(By.id("searchIcon1"));
		allIdPro.click();
		Set<String> allIdPro1 = driver.getWindowHandles();
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
		
		//to search
		driver.findElement(By.id("search_txt")).sendKeys(product_name);
		driver.findElement(By.name("search")).click();
		driver.findElement(By.id("popup_product_106")).click();
		
		Set<String> allIdPro2 = driver.getWindowHandles();
		for(String w:allIdPro2)
		{
		
			driver.switchTo().window(w);
			String title = driver.getTitle();
			System.out.println(title);
			if(title.contains("PurchaseOrder&action"))
			{
				break;
			}
		}
		
		//switch back to parent
		driver.switchTo().defaultContent();
		
		//inspect qty text box
		driver.findElement(By.id("qty1")).sendKeys(qty);
		
		js.executeScript("window.scrollBy(0,500)");
		
		
		
		//inspect save
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();

		//verification
	//	WebElement add = driver.findElement(By.xpath("//td[.='Subject']/following-sibling::td"));
		WebElement eleAdd = driver.findElement(By.xpath("//span[@class='lvtHeaderText']"));
		
		String actual = eleAdd.getText();
		System.out.println(actual);
		String expected=subject;
		if(actual.contains(expected))
		{
			System.out.println("purchase order is added successfully");
		}
		else
		{
			System.out.println("purchase order  is not added successful");
		}


		js.executeScript("window.scrollBy(0,-1000)");
		//inspect sign out button
				driver.findElement(By.linkText("Sign Out")).click();
				String acttitle=driver.getTitle();
				if(acttitle.equals(LoginTitle))
				{
					System.out.println("successfully sign out and sign up page is displayed");
				}
				else
				{
					System.out.println("not sign out");
				}
	
		
driver.close();

	}

}
