package com.crm.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class AddNewProduct {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		
		System.setProperty("webdriver.chrome.driver","./src/main/resources/chromedriver.exe");
		
		WebDriver driver=new ChromeDriver();
		//ChromeOptions options=new ChromeOptions();
		//options.addArguments("--disable notifications---");
		driver.manage().window().maximize();
		driver.get("http://localhost:8888/index.php");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//to inspect username and password text box
		
		driver.findElement(By.name("user_name")).sendKeys("admin");
		driver.findElement(By.name("user_password")).sendKeys("admin");
		driver.findElement(By.id("submitButton")).click();
		
		
		//to inspect add product button
		
		WebElement proele = driver.findElement(By.xpath("//a[.='Products']"));
		proele.click();
		//to inspect add product icon button
		
		driver.findElement(By.xpath("//img[@title='Create Product...']")).click();
		
		//in  add new product page
		
		driver.findElement(By.name("productname")).sendKeys("cripton2");
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
		
		//selecting date
		
	// driver.findElement(By.id("jscal_trigger_start_date")).click();
	 //inspect calendar date 
	
		//driver.findElement(By.name("start_date")).sendKeys("16-3-2022");
		
		
		//handle popup 
	//Alert al = driver.switchTo().alert();
	//	al.accept();
		
		
		WebElement upload = driver.findElement(By.id("my_file_element"));
		upload.sendKeys("C:\\Users\\Prasad\\Pictures\\iPhone_Air_Concept_hashslush0.jpg");
		
		driver.findElement(By.xpath("(//input[@value='  Save  '])[2]")).click();
		Thread.sleep(2000);
		
		
		WebElement add = driver.findElement(By.xpath("//td[.='Product Name']/following-sibling::td"));
		String actual = add.getText();
		System.out.println(actual);
		String expected="  cripton2";
		if(actual.equalsIgnoreCase(expected))
		{
			System.out.println("product is added successfully");
		}
		else
		{
			System.out.println("product is not added successful");
		}

	//	driver.quit();
	}

}
