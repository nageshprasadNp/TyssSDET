package com.crm.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.swing.Action;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateProductByUsingVendorNameTest {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub

		WebDriver driver=null;
		FileInputStream fis=new FileInputStream("./src/test/resources/data.properties");
		Properties prop=new Properties();
		prop.load(fis);
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		String browser = prop.getProperty("browser");
		
		
		
		FileInputStream fstream=new FileInputStream("./src/test/resources/InputData.xlsx");
		Workbook wb=WorkbookFactory.create(fstream);
		String product_name= wb.getSheet("sheet1").getRow(1).getCell(3).getStringCellValue();
		
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
		
		//to inspect vendor
		
		driver.findElement(By.linkText("Vendors")).click();
		
		//to add vendor
		driver.findElement(By.xpath("//img[@title='Create Vendor...']")).click();
		
		
		//in new vendor page inspect vendor name
		
		driver.findElement(By.name("vendorname")).sendKeys("HP");
				
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
		//to inspect save button
		driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		WebElement eleText = driver.findElement(By.xpath("//td[@class='dvtCellLabel']/following-sibling::td"));
		String text=eleText.getText();
		if(text.contains("HP"))
		{
			System.out.println("vendor is added");
		}
		else
		
		{
			System.out.println("vendor is not added");
		}
	
		
		
		//in  product page inspect vendor name
		
		WebElement proele = driver.findElement(By.xpath("//a[.='Products']"));
		proele.click();
		//to inspect add product icon button
		
		driver.findElement(By.xpath("//img[@title='Create Product...']")).click();
		
		//in  add new product page
		
		driver.findElement(By.name("productname")).sendKeys("cripton212");
		driver.findElement(By.xpath("//img[@title='Select']")).click();
		
		
		
		
		WebElement ele2 = driver.findElement(By.xpath("//img[@title='Select']"));
		Set<String> Alladd = driver.getWindowHandles();
		for(String w:Alladd)
		{
		
			driver.switchTo().window(w);
			String title = driver.getTitle();
			System.out.println(title);
			if(title.contains("Vendor&action"))
			{
				break;
			}
		}
				
		driver.findElement(By.id("search_txt")).sendKeys("HP");
		driver.findElement(By.name("search")).click();
		driver.findElement(By.id("1")).click();
		
		Set<String> Alladd1 = driver.getWindowHandles();
		for(String w:Alladd1)
		{
		
			driver.switchTo().window(w);
			String title = driver.getTitle();
			System.out.println(title);
			if(title.contains("Product&action"))
			{
				break;
			}
		}
		
		
		
		//to click save
		driver.switchTo().defaultContent();
		js.executeScript("window.scrollBy(0,500)");
		driver.findElement(By.xpath("(//input[@value='  Save  '])[2]")).click();
		
		WebElement add = driver.findElement(By.xpath("//td[.='Product Name']/following-sibling::td"));
		String actual = add.getText();
		System.out.println(actual);
		String expected="  cripton212";
		if(actual.equalsIgnoreCase(expected))
		{
			System.out.println("product is added successfully");
		}
		else
		{
			System.out.println("product is not added successful");
		}

	
		
driver.close();
	}

}
