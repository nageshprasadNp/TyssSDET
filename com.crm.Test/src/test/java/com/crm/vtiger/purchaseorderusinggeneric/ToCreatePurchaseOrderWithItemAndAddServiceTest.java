package com.crm.vtiger.purchaseorderusinggeneric;

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
		// creating object for using generic libraries
		FileUtilities flib=new FileUtilities();
		ExcelUtilities elib=new ExcelUtilities();
		JavaUtility jlib=new JavaUtility();
		WebDriverCommonLibraries wlib=new WebDriverCommonLibraries();

		//Initializing web driver
		WebDriver driver=null;
		
		//fetching common data form generic lib
		String url = flib.getPropertyKeyValue("url");
		//to fetch the username from property file
		String username = flib.getPropertyKeyValue("username");
		//to fetch the password from property file
		String password = flib.getPropertyKeyValue("password");
		//to fetch the browser value from property file
		String browser = flib.getPropertyKeyValue("browser");
		//to fetch the LoginTile from property
		String LoginTitle=flib.getPropertyKeyValue("LoginTitle");
		
		//fetching purchase order data from excel utility
		//to get subject name from excel sheet
		String subject = elib.readExcelData("sheet1", 16, 2);
		//to fetch billing address data from excel
		String billing_address=elib.readExcelData("sheet1", 16, 3);
		//to fetch qty data from excel
		String qty=elib.readExcelData("sheet1", 16, 4);
		//to fetch the service name from excel
		String service_name=elib.readExcelData("sheet1",19, 2);
		//to fetch the product name from excel
		String product_name=elib.readExcelData("sheet1", 1, 2);
		
		//to generate random number
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
		//to enter the test url
		driver.get(url);
		//to give implicit wit
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		//to inspect username text box
		driver.findElement(By.name("user_name")).sendKeys(username);
		//to inspecting password text field
		driver.findElement(By.name("user_password")).sendKeys(password);
		//to inspecting Login button
		driver.findElement(By.id("submitButton")).click();

		
		//to inspect more tab
		WebElement ele1 = driver.findElement(By.linkText("More"));
		
		//to perform mouse over action
		wlib.mouseOverAnElement(driver, ele1);
		
		//to inspect purchase order
		driver.findElement(By.linkText("Purchase Order")).click();
		
		//to add purchase order
		driver.findElement(By.xpath("//img[@title='Create Purchase Order...']")).click();
		
		
		//in new purchase order page inspect subject
		driver.findElement(By.name("subject")).sendKeys(subject);
		
		//inspect add vendor icon symbol	
	     driver.findElement(By.xpath("//img[@src='themes/softed/images/select.gif']")).click();
	 	
	 	//switching to vendor child browser window
		wlib.switchToWindow(driver,"Vendor&action");
		
		//inspecting search text filed in vendor page
		driver.findElement(By.id("search_txt")).sendKeys("HP");
		driver.findElement(By.name("search")).click();
		driver.findElement(By.id("1")).click();
		System.out.println("vendor is added");
		
		//switch back to product page
		wlib.switchToWindow(driver, "PurchaseOrder&action");
		
		//to inspect status drop down
		WebElement statusDrp = driver.findElement(By.name("postatus"));
		
		wlib.selectDropDownOption("Delivered", statusDrp);
		
		//inspect assigned radio button
		WebElement resEle = driver.findElement(By.xpath("//input[@value='T']"));
		//verifying
		
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
		
		
		//inspecting billing text field and enter the data
		driver.findElement(By.xpath("(//textarea[@class='detailedViewTextBox'])[1]")).sendKeys(billing_address);
				
		
		
		//inspect radio button
		WebElement resele1 = driver.findElement(By.xpath("(//input[@name='cpy']|//b[text()='Copy Billing address'])[2]"));
		
		//verifying check box is selecting or not
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
		
		//to perform scroll bar
		wlib.scrollBarAction(driver);
		
		//switching product page
		WebElement allIdPro1 = driver.findElement(By.id("searchIcon1"));
		allIdPro1.click();
		
		wlib.switchToWindow(driver, "Product&action");

		//to search  an product in product page
		driver.findElement(By.id("search_txt")).sendKeys(product_name);
		driver.findElement(By.name("search")).click();
		driver.findElement(By.id("popup_product_106")).click();
		System.out.println("product is added");
		
		//driver.switchTo().defaultContent();
		wlib.switchToWindow(driver, "PurchaseOrder&action");		
	
		//to click and enter value to  qyt text field
		driver.findElement(By.id("qty1")).sendKeys(qty);
			
		//to inspect add service button
		driver.findElement(By.xpath("//input[@value='Add Service']")).click();
		
		//to inspect and click add service icon
		driver.findElement(By.xpath("//img[@src='themes/images/services.gif']")).click();
		//switch to product page window
		wlib.switchToWindow(driver, "Product&action");
				
	
		//to inspect add service icon symbol
		driver.findElement(By.linkText("SoftwareUpdating")).click();
		System.out.println("service is added");
	
		//switch back to purchase order page 
		wlib.switchToWindow(driver, "PurchaseOrder&action");
	
		//to enter qyt
		driver.findElement(By.id("qty2")).sendKeys("2");
	
		//to perform scroll bar operation
		wlib.scrollBarAction(driver);

	
				//inspect save button
				driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
				
				//to scroll up action
				wlib.scrollBarUp(driver);
			
				//to inspect and click on log out 
				driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();
				
				//to click on sign out link
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
				
				//to close the browser
				driver.close();

	}

}
