package com.crm.vtiger.purchaseorderusinggeneric;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import com.crm.Test.genericUtilities.ExcelUtilities;
import com.crm.Test.genericUtilities.FileUtilities;
import com.crm.Test.genericUtilities.JavaUtility;
import com.crm.Test.genericUtilities.WebDriverCommonLibraries;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ToCreatePurchaseOrderWithItemAndClickUpwardSymbTest {

	public static void main(String[] args) throws Throwable {
	
		//to create respective objects for generic classes
		FileUtilities flib=new FileUtilities();
		ExcelUtilities elib=new ExcelUtilities();
		JavaUtility jlib=new JavaUtility();
		WebDriverCommonLibraries wlib=new WebDriverCommonLibraries();
		//Initializing webdriver
		WebDriver driver=null;
		
		//fetching form generic lib
		String url = flib.getPropertyKeyValue("url");
		//to fetch the username  data from the property file
		String username = flib.getPropertyKeyValue("username");
		//to fetch the password data from the property file
		String password = flib.getPropertyKeyValue("password");
		//to fetch the browser  data from the property file
		String browser = flib.getPropertyKeyValue("browser");
		//to fetch the LoginTitle  data from the property file
		String LoginTitle=flib.getPropertyKeyValue("LoginTitle");
				
		//Fetching purchase order data from excel utility class
		String subject = elib.readExcelData("sheet1", 16, 2);
		String billing_address=elib.readExcelData("sheet1", 16, 3);
		String qty=elib.readExcelData("sheet1", 16, 4);
		String product_name=elib.readExcelData("sheet1", 1, 2);
		String vendor_name=elib.readExcelData("sheet1", 22, 2);
		
		URL url1=new URL("http://192.168.214.157:7777/wd/hub");
		DesiredCapabilities cap=new DesiredCapabilities();
		cap.setBrowserName("chrome");
		cap.setPlatform(Platform.WINDOWS);
		RemoteWebDriver
		driver1=new RemoteWebDriver(url1, cap);
		driver1.get(url);
		
		
	/*	if(browser.equalsIgnoreCase("chrome"))
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
		//to maximize the window
		driver1.manage().window().maximize();
		//to enter the test url
		driver1.get(url);
		
		//to specify the implicit wait 
		driver1.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//to inspect username and password text box
		driver1.findElement(By.name("user_name")).sendKeys(username);
		driver1.findElement(By.name("user_password")).sendKeys(password);
		driver1.findElement(By.id("submitButton")).click();
		
		//to inspect more
		WebElement ele1 = driver1.findElement(By.linkText("More"));
		
		//to perform the mouse over action
		wlib.mouseOverAnElement(driver1, ele1);
		
		//to inspect purchase order
		driver1.findElement(By.linkText("Purchase Order")).click();
		
		//to add purchase order
		driver1.findElement(By.xpath("//img[@title='Create Purchase Order...']")).click();
		
		
		//in new purchase order page inspect subject
		driver1.findElement(By.name("subject")).sendKeys(subject);
		
		//inspect add vendor icon symbol	
		WebElement ele2 = driver1.findElement(By.xpath("//img[@title='Select']"));
		ele2.click();
		
		//switch to vendor and action page
		wlib.switchToWindow(driver1, "Vendor&action");
		
		//to inspect search text field and search button
		driver1.findElement(By.id("search_txt")).sendKeys(vendor_name);
		driver1.findElement(By.name("search")).click();
		driver1.findElement(By.id("1")).click();
		
		//swith to product and action page
		wlib.switchToWindow(driver1, "Product&action");
		
		//to return back to parent browser
		driver1.switchTo().defaultContent();
		
		//to inspect status drop down
		WebElement statusDrp = driver1.findElement(By.name("postatus"));
	
		//to selecting drop down option
		wlib.selectDropDownOption("Delivered", statusDrp);
				
		//inspect assigned radio button
		WebElement resEle = driver1.findElement(By.xpath("//input[@value='T']"));
		
		//to verifying radio button is selecting or not
		boolean res = resEle.isSelected();
		if(res==true)
		{
			System.out.println("assigned radio button already selected");
		}
		else
		{
			resEle.click();
			System.out.println("radio button is select now");
		}
		
		
		//inspecting billing text field
		driver1.findElement(By.xpath("(//textarea[@class='detailedViewTextBox'])[1]")).sendKeys(billing_address);
						
		//inspect radio button
		WebElement resele1 = driver1.findElement(By.xpath("(//input[@name='cpy']|//b[text()='Copy Billing address'])[2]"));
		
		//verifying radio button is selecting or not
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
		//to perform scroll bar action
		wlib.scrollBarAction(driver1);
		
		//inspect product icon on item panel
		WebElement allIdPro = driver1.findElement(By.id("searchIcon1"));
		allIdPro.click();
		
		//to switching to product and action page
		wlib.switchToWindow(driver1, "Product&action");
		
		//to search text field and search button in product page
		driver1.findElement(By.id("search_txt")).sendKeys(product_name);
		driver1.findElement(By.name("search")).click();
		driver1.findElement(By.id("popup_product_106")).click();
		
		//switch to purchase order page
		wlib.switchToWindow(driver1, "PurchaseOrder&action");
	
		//switch back to parent
		driver1.switchTo().defaultContent();
		//to switch back to purchase order page
		wlib.switchToWindow(driver1, "PurchaseOrder&action");
		//inspect qty text box
		driver1.findElement(By.id("qty1")).sendKeys(qty);
		
		//to perform scroll bar action
		wlib.scrollBarAction(driver1);
				
		//inspect add product
		driver1.findElement(By.xpath("//input[@value='Add Product']")).click();
		
		//inspect upward arrow icon 
		WebElement uparrow = driver1.findElement(By.xpath("//img[@src='themes/images/up_layout.gif']"));
		uparrow.click();
		System.out.println("upward arrow is clicking");
		
		driver1.findElement(By.xpath("//img[@src='themes/images/up_layout.gif']")).click();
		
		//to delete add service icon
		
		driver1.findElement(By.xpath("//img[@src='themes/images/delete.gif']")).click();
		
		//to save
		driver1.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		//to perform scroll bar operation
		wlib.scrollBarUp(driver1);	
		
		//inspect sign out button
		driver1.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();
				driver1.findElement(By.linkText("Sign Out")).click();
			
				//verification
				String acttitle=driver1.getTitle();
				if(acttitle.equals(LoginTitle))
				{
					System.out.println("successfully sign out and sign up page is displayed");
				}
				else
				{
					System.out.println("not sign out");
				}
				
				//to close the browser
				driver1.close();

	}

}
