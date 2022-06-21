package com.crm.vtiget.purchasemodule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ToCreatePurchaseOrderWithItemTest {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub

		WebDriver driver=null;
		ChromeOptions options=new ChromeOptions();
		options.addArguments("--disable-notifications");
		
		
		FileInputStream fis=new FileInputStream("./src/test/resources/data.properties");
		Properties prop=new Properties();
		prop.load(fis);
		String url = prop.getProperty("url");
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		String browser = prop.getProperty("browser");
		
		
		
		FileInputStream fstream=new FileInputStream("./src/test/resources/InputData.xlsx");
		Workbook wb=WorkbookFactory.create(fstream);
		String subject= wb.getSheet("sheet1").getRow(16).getCell(2).getStringCellValue();
		String billing_address=wb.getSheet("sheet1").getRow(16).getCell(3).getStringCellValue();
		String qty=String.valueOf((int)wb.getSheet("sheet1").getRow(16).getCell(4).getNumericCellValue());
		String product_name=wb.getSheet("sheet1").getRow(1).getCell(2).getStringCellValue();
		
		
		
		
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
		
		//to inspect billing text box
		driver.findElement(By.name("bill_street")).sendKeys("Bangalore");
		
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
		
				
		//to return back to parent browser
		driver.switchTo().defaultContent();
	}

}
