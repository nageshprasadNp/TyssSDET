package com.crm.countdropDownOptions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import com.crm.Test.genericUtilities.ExcelUtilities;
import com.crm.Test.genericUtilities.FileUtilities;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CountOptionsInDropDown {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		
		//intiliazing driver
		WebDriver driver=null;
		
		//fetching data from propertyfile
		FileUtilities fu=new FileUtilities();
		String username = fu.getPropertyKeyValue("username");
		String password=fu.getPropertyKeyValue("password");
		String url=fu.getPropertyKeyValue("url");
		String browser=fu.getPropertyKeyValue("browser");
		
		//fetching from generic utilities
		
		ExcelUtilities eu=new ExcelUtilities();
		String orgName = eu.readExcelData("sheet1", 4, 2);
		
		
		//verifying type the browser and launcing
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
				
				//enter the url and 

				driver.manage().window().maximize();
				driver.get(url);
				driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
				
				//inspecting username, password and login button in login page
				
				driver.findElement(By.name("user_name")).sendKeys(username);
				driver.findElement(By.name("user_password")).sendKeys(password);
				driver.findElement(By.id("submitButton")).click();

				//inspecting organization in home page
				driver.findElement(By.linkText("Organizations")).click();

				//inspecting new organization icon 
				driver.findElement(By.xpath("//img[@title='Create Organization...']")).click();
				
				//inspect organization name text box
				driver.findElement(By.name("accountname")).sendKeys(orgName);
				
				
				
				//select
				WebElement dropele = driver.findElement(By.name("industry"));
				Select s=new Select(dropele);
				List<WebElement> options = s.getOptions();
				int count = options.size();
				
				ArrayList al=new ArrayList();
				//to get the count of oprtions present in drop down
				System.out.println(count);
				for(WebElement op:options)
				{
					String allOption = op.getText();
					al.add(allOption);
			
				}
			//to sort the option in ascending order	
				Collections.sort(al);
				System.out.println("ascending order");
				for(Object a:al)
				{
					
					System.out.println(a);
				}
			//to sort the drop down options in decending order	
				System.out.println("Decending order");
				Collections.reverse(al);
				for(Object a:al)
				{
					System.out.println(a);
				}
				
				//to verify specific option is contains in the drop down or not
				if(al.contains("Electronics"))
				{
					System.out.println("option is present");
				}
				else
				{
					System.out.println("option is not present");
				}
				
				driver.close();
	}

}
