package com.crm.vtiger.purchaseorderusinggeneric;

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
import com.crm.Test.genericUtilities.WebDriverCommonLibraries;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ToCreatePurchaseOrderWithOrderAndDeleteTest {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub

WebDriver driver=null;


	//to create respective objects for generic classes
	FileUtilities flib=new FileUtilities();
	ExcelUtilities elib=new ExcelUtilities();
	JavaUtility jlib=new JavaUtility();
	WebDriverCommonLibraries wlib=new WebDriverCommonLibraries();
	//fetching form generic lib
	//to fetching username from property file
	String url = flib.getPropertyKeyValue("url");
	//to fetching the data from property file
	String username = flib.getPropertyKeyValue("username");
	//fetching the password from property file
	String password = flib.getPropertyKeyValue("password");
	//fetching the browser from property file
	String browser = flib.getPropertyKeyValue("browser");
	//fetching the Login title from property file
	String LoginTitle=flib.getPropertyKeyValue("LoginTitle");

	//fetching data from generic utilities
	//fetching the subject data from excel file
	String subject = elib.readExcelData("sheet1", 16, 2);
	//fetching the billing address data from excel file
	String billing_address=elib.readExcelData("sheet1", 16, 3);
	//fetching the qty data from excel file
	String qty=elib.readExcelData("sheet1", 16, 4);
	//fetching the product name data from excel file
	String product_name=elib.readExcelData("sheet1", 1, 2);
	//fetching the vendor name data from excel file
	String vendor_name=elib.readExcelData("sheet1", 22, 2);		
		
	//to verify type of browser we use
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
		//to maximize the browser
		driver.manage().window().maximize();
		//to enter the test url
		driver.get(url);
		
		//to specify the implicit wait
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//to inspect username and password text box
		driver.findElement(By.name("user_name")).sendKeys(username);
		driver.findElement(By.name("user_password")).sendKeys(password);
		driver.findElement(By.id("submitButton")).click();

		
		//to inspect more link
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
		WebElement ele2 = driver.findElement(By.xpath("//img[@title='Select']"));
		ele2.click();
		
		//switching to vendor and action page
		wlib.switchToWindow(driver, "Vendor&action");
			
		//inspect search text field and search button
		driver.findElement(By.id("search_txt")).sendKeys(vendor_name);
		driver.findElement(By.name("search")).click();
		driver.findElement(By.id("1")).click();
		
		
		//switch to product and action page
		wlib.switchToWindow(driver, "Product&action");
			
		//to return back to parent browser
		driver.switchTo().defaultContent();
		
		//to inspect status drop down
		WebElement statusDrp = driver.findElement(By.name("postatus"));
		//to selct drop down option
		wlib.selectDropDownOption("Delivered", statusDrp);
				
		//inspect assigned radio button
		WebElement resEle = driver.findElement(By.xpath("//input[@value='T']"));
		boolean res = resEle.isSelected();
		//to verify radio button is checkrd or not
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
		driver.findElement(By.xpath("(//textarea[@class='detailedViewTextBox'])[1]")).sendKeys(billing_address);
			
		//inspect radio button
		WebElement resele1 = driver.findElement(By.xpath("(//input[@name='cpy']|//b[text()='Copy Billing address'])[2]"));
		boolean res1 = resele1.isSelected();
		//to verify radio button is checked or not
		if(res1==true)
		{
			System.out.println("assigned radio button already selected");
		}
		else
		{
			resele1.click();
			System.out.println("radio button is select now");
		}
		// To scroll bar action
		wlib.scrollBarAction(driver);
				
		//inspect product icon on item panel
		WebElement allIdPro = driver.findElement(By.id("searchIcon1"));
		allIdPro.click();
		
		//to switching to product and action page
		wlib.switchToWindow(driver, "Product&action");
	
		//to inspect search text field and search button
		driver.findElement(By.id("search_txt")).sendKeys(product_name);
		driver.findElement(By.name("search")).click();
		driver.findElement(By.id("popup_product_106")).click();
		
		//to switch purchase order and action page
		wlib.switchToWindow(driver, "PurchaseOrder&action");
		
		//switch back to parent
		driver.switchTo().defaultContent();
		
		//inspect qty text box
		driver.findElement(By.id("qty1")).sendKeys(qty);
		
		
		//to perform scroll bar action
		wlib.scrollBarAction(driver);
		
		//to inspect add product icon in add product page
		driver.findElement(By.xpath("//input[@value='Add Product']")).click();
		//inspect upward arrow icon 
		WebElement uparrow = driver.findElement(By.xpath("//img[@src='themes/images/up_layout.gif']"));
		uparrow.click();
		System.out.println("is clicking");
		
		//inspect for deleted product
		WebElement deleteicon = driver.findElement(By.xpath("//img[@src='themes/images/delete.gif']"));
		deleteicon.click();
		boolean res2 = deleteicon.isDisplayed();
		//to verification
		if(res2==false)
		{
			System.out.println("deleted add product is successfully ");
		}
		else
		{
			System.out.println("not deleted");
		}
		
		//switching back to purchase order and action page
		wlib.switchToWindow(driver, "PurchaseOrder&action");
			
		//switch back to parent
		driver.switchTo().defaultContent();
			
		//to perform scroll bar action
		wlib.scrollBarAction(driver);
		
		//inspect sign out button
				driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();
				driver.findElement(By.linkText("Sign Out")).click();
				String acttitle=driver.getTitle();
			
				//to verification for successful logout
				if(acttitle.equals(LoginTitle))
				{
					System.out.println("successfully sign out and sign up page is displayed");
				}
				else
				{
					System.out.println("not sign out");
				}
	
		//to close the browser
		driver.close();
	}

}
