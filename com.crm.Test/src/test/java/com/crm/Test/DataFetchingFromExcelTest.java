package com.crm.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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

public class DataFetchingFromExcelTest {

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
		String product_name= wb.getSheet("sheet1").getRow(1).getCell(2).getStringCellValue();
		
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
		
		//add product
		
		WebElement proele = driver.findElement(By.xpath("//a[.='Products']"));
		proele.click();
		//to inspect add product icon button
		
		driver.findElement(By.xpath("//img[@title='Create Product...']")).click();
		
		//in  add new product page
		
		driver.findElement(By.name("productname")).sendKeys(product_name+randnum);
		WebElement ele = driver.findElement(By.name("productcategory"));
		Select s=new Select(ele);
		s.selectByValue("Software");
		
		WebElement ele1 = driver.findElement(By.name("manufacturer"));
		Select s1=new Select(ele1);
		s1.selectByValue("MetBeat Corp");
		
		WebElement ele2 = driver.findElement(By.xpath("//input[@value='T']"));
		boolean res = ele2.isSelected();
		if(res==true)
		{
			ele2.click();
			System.out.println("already radio button selected");
		}
		else
		{
			ele2.click();
			System.out.println("selecting radio button now");
		}
		
		WebElement upload = driver.findElement(By.id("my_file_element"));
		upload.sendKeys("C:\\Users\\Prasad\\Pictures\\iPhone_Air_Concept_hashslush0.jpg");
		
		driver.findElement(By.xpath("(//input[@value='  Save  '])[2]")).click();
		Thread.sleep(2000);
		
		
		WebElement add = driver.findElement(By.xpath("//td[.='Product Name']/following-sibling::td"));
		String actual = add.getText();
		System.out.println(actual);
		String expected=product_name+randnum;
		if(actual.contains(expected))
		{
			System.out.println("product is added successfully");
		}
		else
		{
			System.out.println("product is not added successful");
		}

		
		
		
		
		
		

	}

}
