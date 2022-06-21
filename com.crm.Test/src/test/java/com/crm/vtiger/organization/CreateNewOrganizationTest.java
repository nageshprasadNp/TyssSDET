package com.crm.vtiger.organization;

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
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateNewOrganizationTest {

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
		String LoginTitle=p.getProperty("LoginTitle");
		
		
		//Read the data from excel sheet
		FileInputStream fstream=new FileInputStream("./src/test/resources/InputData.xlsx");
		Workbook wb=WorkbookFactory.create(fstream);
		String organization_Name = wb.getSheet("sheet1").getRow(4).getCell(2).getStringCellValue();
		String website=wb.getSheet("sheet1").getRow(4).getCell(3).getStringCellValue();
		
		//create random num 
		Random randNum=new Random();
		int randNumber = randNum.nextInt(100);
		
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
		driver.findElement(By.name("accountname")).sendKeys(organization_Name+randNumber);
		
		//inspect web site
		driver.findElement(By.xpath("//input[@class='detailedViewTextBox' and @name='website']")).sendKeys(website);
		
		//inspect industry drop down button
		WebElement option = driver.findElement(By.name("industry"));
		Select s=new Select(option);

		
		
		
		s.selectByVisibleText("Healthcare");
		
		//inspect type drop down
		WebElement dropdown1 = driver.findElement(By.xpath("//select[@name='accounttype']"));
		Select s1=new Select(dropdown1);
		List<WebElement> opt1 = s1.getOptions();
		System.out.println("type drop down");
		for(WebElement v:opt1)
		{
			String opti = v.getText();
			
			System.out.println(opti);
		}
		s1.selectByVisibleText("Customer");
		
		
		//inspect save button
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		//verifying organization is added or not
		
		//WebElement text = driver.findElement(By.xpath("//td[@class='dvtCellLabel']/following-sibling::td[1]"));
		WebElement text = driver.findElement(By.xpath("//span[@class='dvHeaderText']"));
		System.out.println(text);
		String actualResult=text.getText();
		System.out.println(actualResult);
		
		String expectedResult=organization_Name+randNumber;
		
		if(actualResult.contains(expectedResult))
		{
			System.out.println("organization is craeted successfully");
		}
		else
		{
			System.out.println("organization is not created ");
		}
		
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally {
			driver.close();
		}
	}
	}