package com.crm.vtiger.organization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateNewContactWithOrganizationNameTest {

	public static void main(String[] args) throws Throwable {
		
		//initialization for webDriver
		WebDriver driver=null;
		Connection connection=null;
		
		// Read the data from properties file
		try
		{
		FileInputStream fis=new FileInputStream("./src/test/resources/data.properties");
		Properties p=new Properties();
		p.load(fis);
		String url = p.getProperty("url");
		String username = p.getProperty("username");
		String password=p.getProperty("password");
		String browser=p.getProperty("browser");
		
		//Read the data from excel sheet
		FileInputStream fstream=new FileInputStream("./src/test/resources/InputData.xlsx");
		Workbook wb=WorkbookFactory.create(fstream);
		String first_Name = wb.getSheet("sheet1").getRow(7).getCell(2).getStringCellValue();
		String last_Name = wb.getSheet("sheet1").getRow(7).getCell(3).getStringCellValue();
		String mobileNUm = String.valueOf((long)wb.getSheet("sheet1").getRow(7).getCell(4).getNumericCellValue());
		System.out.println(mobileNUm);
		
		
		
		
		
		//wb.getSheet("sheet1").getRow(7).getCell(4).getNumericCellValue();
		//String mobileno=mobileNUm.toString();
		String depart = wb.getSheet("sheet1").getRow(7).getCell(5).getStringCellValue();
		String email=wb.getSheet("sheet1").getRow(7).getCell(6).getStringCellValue();
		
		//fetching organization name
		String organization_Name = wb.getSheet("sheet1").getRow(4).getCell(3).getStringCellValue();
		
						
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
		
		//inspect contact sub menu
		driver.findElement(By.linkText("Contacts")).click();
		
		//inspect create new contact icon
		driver.findElement(By.xpath("//img[@title='Create Contact...']")).click();
		
		//inspect drop down
		 WebElement firstDrpdown = driver.findElement(By.xpath("//select[@name='salutationtype']"));
		Select s=new Select(firstDrpdown);
		s.selectByVisibleText("Mr.");
		
		//inspect first name text field
		driver.findElement(By.name("firstname")).sendKeys(first_Name);
		
		//inspect last name text filed
		driver.findElement(By.name("lastname")).sendKeys(last_Name);
		
		//inspect organization text fiels
		WebElement ele = driver.findElement(By.xpath("//img[@title='Select']"));
		ele.click();
		Set<String> allAdds = driver.getWindowHandles();
		for(String w:allAdds)
		{
			driver.switchTo().window(w);
			String title = driver.getTitle();
			System.out.println(title);
		}
		
		//inspect  search text field
		driver.findElement(By.id("search_txt")).sendKeys(organization_Name);
		driver.findElement(By.name("search")).click();
		driver.findElement(By.id("2")).click();
		
		//
		Set<String> allAdds1 = driver.getWindowHandles();
		for(String w:allAdds1)
		{
			driver.switchTo().window(w);
			String title = driver.getTitle();
			System.out.println(title);
		}
		driver.switchTo().defaultContent();
			
		
		//inspect department text field
	driver.findElement(By.name("mobile")).sendKeys(mobileNUm);
		
		//inspect department text field
		driver.findElement(By.name("department")).sendKeys(depart);
		
		//inspect email text field
		driver.findElement(By.id("email")).sendKeys(email);
		
		//inspect save button
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		//verifying contact is created or not
		
		WebElement text = driver.findElement(By.xpath("//span[@class='dvHeaderText']"));
		System.out.println(text);
		String actualResult=text.getText();
		System.out.println(actualResult);
		
		String expectedResult=last_Name;
		
		if(actualResult.contains(expectedResult))
		{
			System.out.println("contact is craeted with organization successfully");
		}
		else
		{
			System.out.println("contact is not created ");
		}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		finally {
			//driver.close();
		}
		
	}

}
