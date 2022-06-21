package com.crm.vtiger.productAndCampaign;

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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateProductAndCampaignTest {

	public static void main(String[] args) throws Throwable {
		// create product and campaign
		
		//intialization of driver
		
		WebDriver driver=null;
		try
		{
		//fetching data from property file
		FileInputStream fis=new  FileInputStream("./src/test/resources/data.properties");
		Properties p=new Properties();
		p.load(fis);
		String url = p.getProperty("url");
		String username = p.getProperty("username");
		String password = p.getProperty("password");
		String browser = p.getProperty("browser");
		String LoginTitle=p.getProperty("LoginTitle");
		
		
		//fetching product details from excel sheet
		FileInputStream fsteam=new FileInputStream("./src/test/resources/InputData.xlsx");
		Workbook wb=WorkbookFactory.create(fsteam);
		String product_Name = wb.getSheet("sheet1").getRow(1).getCell(2).getStringCellValue();
		
		//fetching campaign details
		
		String campaign_Name = wb.getSheet("sheet1").getRow(10).getCell(2).getStringCellValue();
		
		
		
		//genrate random number
		Random rand=new Random();
		int randNum = rand.nextInt();
		
		//launch the browser
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
		
	//to inspect add product button
		
		WebElement proele = driver.findElement(By.xpath("//a[.='Products']"));
		proele.click();
		//to inspect add product icon button
		
		driver.findElement(By.xpath("//img[@title='Create Product...']")).click();
		
		//in  add new product page
		
		driver.findElement(By.name("productname")).sendKeys(product_Name+randNum);
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
		
		//verifying product is created or not
		
		WebElement text = driver.findElement(By.xpath("//span[@class='lvtHeaderText']"));
		System.out.println(text);
		String actualResult=text.getText();
		System.out.println(actualResult);
		
		String expectedResult=product_Name;
		
		if(actualResult.contains(expectedResult))
		{
			System.out.println("product is craeted successfully");
		}
		else
		{
			System.out.println("product is not created ");
		}
	
		//move to cursor to more
		WebElement ele3 = driver.findElement(By.linkText("More"));
		Actions ac=new Actions(driver);
		ac.moveToElement(ele3).perform();
		
		//inspect campaign and click
		driver.findElement(By.linkText("Campaigns")).click();
		
		
		//inspect camapaign icon
		driver.findElement(By.xpath("//img[@title='Create Campaign...']")).click();
		
		//enter campaign name
		driver.findElement(By.name("campaignname")).sendKeys(campaign_Name+randNum);
		
		//inspect radio button
		WebElement radio = driver.findElement(By.xpath("//input[@value='T']"));
		boolean res1 = radio.isSelected();
		if(res1==true)
		{
			System.out.println("Radio button is selected already");
		}
		else
		{
			radio.click();
			System.out.println("selecting now");
		}
		//inspect drop down
		WebElement ele4 = driver.findElement(By.xpath("//select[@name='assigned_group_id']"));
		Select s2=new Select(ele4);
		s2.selectByVisibleText("Support Group");
		
		
		//inspect campaign status drop down
		WebElement statusDrp = driver.findElement(By.xpath("//select[@name='campaignstatus']"));
		Select statusdrp=new Select(statusDrp);
		statusdrp.selectByVisibleText("Active");
		
		//inspect product icon 
		WebElement win = driver.findElement(By.xpath("//img[@title='Select']"));
		win.click();
		Set<String> allIds = driver.getWindowHandles();
		for(String w:allIds)
		{
			driver.switchTo().window(w);
			String title = driver.getTitle();
			System.out.println(title);
		}
	
	
	//inspect  search text field
	driver.findElement(By.id("search_txt")).sendKeys(product_Name);
	driver.findElement(By.name("search")).click();
	driver.findElement(By.id("2")).click();
	
	//
	Set<String> allIds1 = driver.getWindowHandles();
	for(String w:allIds1)
	{
		driver.switchTo().window(w);
		String title = driver.getTitle();
		System.out.println(title);
	}
	driver.switchTo().defaultContent();
		
	//inspect campaign type drop down
	WebElement campDrp = driver.findElement(By.xpath("//select[@name='campaigntype']"));
	Select scamp=new Select(campDrp);
	scamp.selectByVisibleText("Advertisement");
	
	//to save
	driver.findElement(By.xpath("//input[@title='Save [Alt+S]']")).click();
		
		
	
	//verification
	
	WebElement text2 = driver.findElement(By.xpath("//span[@class='dvHeaderText']"));
	System.out.println(text2);
	String actualResult1=text2.getText();
	System.out.println(actualResult1);
	
	String expectedResult2=campaign_Name;
	
	if(actualResult1.contains(expectedResult2))
	{
		System.out.println("product and campaign is craeted  successfully");
	}
	else
	{
		System.out.println("product and campaign is not created ");
	}
	
	//inspect administered icon
	WebElement logout = driver.findElement(By.xpath("//td[@class='small']//img"));
	ac.moveToElement(logout).perform();
	
	//inspect sign out button
	driver.findElement(By.linkText("Sign Out")).click();
	String acttitle=driver.getTitle();
	if(acttitle.equals(LoginTitle))
	{
		System.out.println("successfully sign out");
	}
	else
	{
		System.out.println("not sign out");
	}
	
	
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	/*	finally {
			driver.close();
		}*/
	
	
	}
	
		
	}

