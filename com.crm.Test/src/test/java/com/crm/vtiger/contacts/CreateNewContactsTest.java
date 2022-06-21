package com.crm.vtiger.contacts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.crm.Test.genericUtilities.ExcelUtilities;
import com.crm.Test.genericUtilities.FileUtilities;
import com.crm.Test.genericUtilities.JavaUtility;
import com.crm.Test.genericUtilities.WebDriverCommonLibraries;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateNewContactsTest {

	public static void main(String[] args) throws Throwable {
		
				//initialization for webDriver
				WebDriver driver=null;
				
				FileUtilities flib=new FileUtilities();
				ExcelUtilities elib=new ExcelUtilities();
				JavaUtility jlib=new JavaUtility();
				WebDriverCommonLibraries wlib=new  WebDriverCommonLibraries();
				
				// Read the data from properties file
				//to fetching url from property file
				String url=flib.getPropertyKeyValue("url");
				
				
				FileInputStream fis=new FileInputStream("./src/test/resources/data.properties");
				Properties p=new Properties();
				p.load(fis);
			//	String url = p.getProperty("url");
				String username = p.getProperty("username");
				String password=p.getProperty("password");
				String browser=p.getProperty("browser");
				int randNum;
				String LoginTitle=flib.getPropertyKeyValue("LoginTitle");
								
				//fetching from excel sheet using generic utilities
					
				randNum=jlib.getrandomNumber();
				String first_name = elib.readExcelData("contacts", 1, 2);
				String last_name=elib.readExcelData("contacts", 1, 3)+randNum;
				String mobile=elib.readExcelData("contacts",1, 4);
				String dept_name=elib.readExcelData("contacts", 1, 5)+randNum;
				String email=elib.readExcelData("contacts", 1, 6);
				
									
				//verifying type the browser and launching
				if(browser.equalsIgnoreCase("chrome"))
				{
					WebDriverManager.chromedriver().setup();
					driver=new ChromeDriver();
				}
				else if(browser.equalsIgnoreCase("firefox"))
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
				//define the implicit wait 
				wlib.waitForPageGetLoad(driver);
							
				//inspecting username, password and login button in login page
				
				driver.findElement(By.name("user_name")).sendKeys(username);
				driver.findElement(By.name("user_password")).sendKeys(password);
				driver.findElement(By.id("submitButton")).click();
				
				//inspect contact sub menu
				driver.findElement(By.linkText("Contacts")).click();
				
				//inspect create new contact icon
				driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();
				
				//inspect drop down
				 WebElement firstDrpdown = driver.findElement(By.name("salutationtype"));
				 
				 Select s=new Select(firstDrpdown);
				 List<WebElement> options = s.getOptions();
				 for(WebElement value:options) {
					String opt1 = value.getText();
					System.out.println(opt1);
				 }
				 
				 
				 //selecting drop down value 
				 wlib.selectDropDownOption(firstDrpdown,1);
		
				//inspect first name text field
				driver.findElement(By.name("firstname")).sendKeys(first_name);
				
				//inspect last name text filed
				driver.findElement(By.name("lastname")).sendKeys(last_name);
				
				//inspect department text field
				driver.findElement(By.name("mobile")).sendKeys(mobile);
				
				//inspect lead drop down
				WebElement leadDropdown = driver.findElement(By.name("leadsource"));
				Select s3=new  Select(leadDropdown);
				List<WebElement> options4 = s3.getOptions();
				for(WebElement value:options4)
				{
					String value1 = value.getText();
					System.out.println(value1);
				}
				
				
				
				
				
				
				
				//inspect department text field
				driver.findElement(By.name("department")).sendKeys(dept_name);
				
				//inspect email text field
				driver.findElement(By.id("email")).sendKeys(email);
				
				//to inspect upload file button
				driver.findElement(By.name("imagename")).sendKeys("C:\\Users\\Prasad\\Pictures\\abstract-4a.jpg");
				
				//inspect save button
				driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
				
				//verifying contact is created or not
				WebElement text = driver.findElement(By.xpath("//span[@class='dvHeaderText']"));
				String actualResult=text.getText();
				System.out.println(actualResult);
				String expectedResult=last_name;
				
				//verify contact is created or not
				if(actualResult.contains(expectedResult))
				{
					System.out.println("contact is craeted successfully");
				}
				else
				{
					System.out.println("contact is not created ");
				}
			
				
				//to sign out from the application
				//to inspect admin icon
				WebElement adminIcon = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
			
				//to perform mouse over action
				wlib.mouseOverAnElement(driver, adminIcon);
				
				//to inspect sign out link
				driver.findElement(By.linkText("Sign Out")).click();
				
				//to verifying successfully sign out or not
				String actaultitle = driver.getTitle();
				String expectedTitle=LoginTitle;
				if(actaultitle.equals(expectedTitle))
				{
					System.out.println("successfully logout from the application");
				}
				else
				{
					System.out.println("not signed out");
				}
				
				
				
				
					driver.close();			
				
				
	}

}
