package com.crm.vtiger.purchaseOrder;

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

import io.github.bonigarcia.wdm.WebDriverManager;

public class ToCreatePurchaseOrderWithOrderAndDeleteTest {

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
		String LoginTitle=prop.getProperty("LoginTitle");
		
		
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
			//System.out.println(title);
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
			//System.out.println(title);
			if(title.contains("Product&action"))
			{
				break;
			}
		}
		
		
		
		//to return back to parent browser
		driver.switchTo().defaultContent();
		
		//to inspect status drop down
		WebElement statusDrp = driver.findElement(By.name("postatus"));
		Select s=new Select(statusDrp);
		s.selectByVisibleText("Delivered");
		
		//inspect assigned radio button
		WebElement resEle = driver.findElement(By.xpath("//input[@value='T']"));
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
				driver.findElement(By.xpath("(//textarea[@class='detailedViewTextBox'])[1]")).sendKeys(billing_address);
				
		
		
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
		
		
		
		
		//inspect copy billing address radio button 
	//	driver.findElement(By.name("cpy")).click();
		
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
		
		//inspect product icon on item panel
		WebElement allIdPro = driver.findElement(By.id("searchIcon1"));
		allIdPro.click();
		Set<String> allIdPro1 = driver.getWindowHandles();
		for(String w:allIdPro1)
		{
		
			driver.switchTo().window(w);
			String title = driver.getTitle();
			//System.out.println(title);
			if(title.contains("Product&action"))
			{
				break;
			}
		}
		
		//to search
		driver.findElement(By.id("search_txt")).sendKeys("OppoMobile");
		driver.findElement(By.name("search")).click();
		driver.findElement(By.id("popup_product_106")).click();
		
		Set<String> allIdPro2 = driver.getWindowHandles();
		for(String w:allIdPro2)
		{
		
			driver.switchTo().window(w);
			String title = driver.getTitle();
			//System.out.println(title);
			if(title.contains("PurchaseOrder&action"))
			{
				break;
			}
		}
		
		//switch back to parent
		driver.switchTo().defaultContent();
		
		//inspect qty text box
		driver.findElement(By.id("qty1")).sendKeys(qty);
		
		js.executeScript("window.scrollBy(0,500)");
		
		
		//inspect add product
		driver.findElement(By.xpath("//input[@value='Add Product']")).click();
		//inspect upward arrow icon 
		//driver.findElement(By.xpath("//img[@src='themes/images/delete.gif']")).click();
		WebElement uparrow = driver.findElement(By.xpath("//img[@src='themes/images/up_layout.gif']"));
		uparrow.click();
		System.out.println("is clicking");
		
		//for deleted product
		WebElement deleteicon = driver.findElement(By.xpath("//img[@src='themes/images/delete.gif']"));
		
		deleteicon.click();
		boolean res2 = deleteicon.isDisplayed();
		if(res2==false)
		{
			System.out.println("deleted add product is successfully ");
		}
		else
		{
			System.out.println("not deleted");
		}
		
		
		
		Set<String> allIdPro3 = driver.getWindowHandles();
		for(String w:allIdPro3)
		{
		
			driver.switchTo().window(w);
			String title = driver.getTitle();
			//System.out.println(title);
			if(title.contains("PurchaseOrder&action"))
			{
				break;
			}
		}
		
		//switch back to parent
		driver.switchTo().defaultContent();
			
		js.executeScript("window.scrollBy(0,-100)");
		//inspect sign out button
				driver.findElement(By.linkText("Sign Out")).click();
				String acttitle=driver.getTitle();
				if(acttitle.equals(LoginTitle))
				{
					System.out.println("successfully sign out and sign up page is displayed");
				}
				else
				{
					System.out.println("not sign out");
				}
	
		
		
	}

}
