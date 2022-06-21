package com.crm.Test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class AddNewDocument {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
System.setProperty("webdriver.chrome.driver","./src/main/resources/chromedriver.exe");
		
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://localhost:8888/index.php");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//to inspect username and password text box
		
		driver.findElement(By.name("user_name")).sendKeys("admin");
		driver.findElement(By.name("user_password")).sendKeys("admin");
		driver.findElement(By.id("submitButton")).click();
		
		//to inspect document tab
		
		driver.findElement(By.xpath("//a[.='Documents']")).click();
		
		driver.findElement(By.xpath("//img[@title='Create Document...']")).click();
		
		
		driver.findElement(By.name("notes_title")).sendKeys("Installation giud for API testing tool");
		
		
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
		WebElement ele = driver.findElement(By.name("assigned_group_id"));
		Select s=new Select(ele);
		s.selectByVisibleText("Team Selling");
		JavascriptExecutor js=(JavascriptExecutor) driver;

	
		js.executeScript("scrollBy(0,500)");
		WebElement webele = driver.findElement(By.xpath("//iframe[@title='Rich text editor, notecontent, press ALT 0 for help.']"));
		webele.sendKeys("Testing tool procedure guid");
		Thread.sleep(10);
	//	driver.switchTo().frame(1);
	//	webele.sendKeys("Testing tool procedure guid");
		//JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
		WebElement upload = driver.findElement(By.id("filename_I__"));
		upload.sendKeys("C:\\Users\\Prasad\\Pictures\\iPhone_Air_Concept_hashslush0.jpg");
		 WebElement button = driver.findElement(By.name("button"));
		 js.executeScript("arguments[0].scrollIntoView()", button);
		 button.click();
		WebElement ele1 = driver.findElement(By.xpath("//td[text()='Title']/following-sibling::td"));
		String actual=ele1.getText();
		System.out.println(actual);
		String expected="  Installation giud for Automation testing tool";
		
		if(actual.equalsIgnoreCase(expected))
		{
			System.out.println("document added");
		}
		else
		{
			System.out.println("document is not added");
		}
		
		
		//driver.quit();
	}

}
