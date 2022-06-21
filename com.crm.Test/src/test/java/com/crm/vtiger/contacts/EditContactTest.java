package com.crm.vtiger.contacts;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class EditContactTest {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub

		WebDriver driver=null;
		Connection connection=null;
		
		// Read the data from properties file
		
		FileInputStream fis=new FileInputStream("./src/test/resources/data.properties");
		Properties p=new Properties();
		p.load(fis);
		String url = p.getProperty("url");
		String username = p.getProperty("username");
		String password=p.getProperty("password");
		String browser=p.getProperty("browser");
		String LoginPage=p.getProperty("LoginTitle");
		
		
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
		
		//to select contact
		driver.findElement(By.xpath("//a[text()='prasad']")).click();
		Thread.sleep(200);
		//mouseover
		WebElement move = driver.findElement(By.xpath("//td[@class='dvtCellLabel']/following-sibling::td"));
		Actions ac=new  Actions(driver);
		ac.moveToElement(move).perform();
		
		//to inspect edit link
		WebElement edit = driver.findElement(By.linkText("Edit"));
		edit.click();
		
		//to send data into firstname filed
		WebElement fname = driver.findElement(By.id("txtbox_First Name"));
		fname.clear();
		fname.sendKeys("sri chaitanya");
		
		
		//to inspect save button
		driver.findElement(By.name("button_First Name")).click();
		
		//to verify
		WebElement move1 = driver.findElement(By.xpath("//td[@class='dvtCellLabel']/following-sibling::td"));
		String actualText = move1.getText();
		String expectedText="srivasthav12";
		if(actualText.contains(expectedText))
		{
			System.out.println("update first name successfull");
		}
		else
		{
			System.out.println("update is not happened");
		}
		
		
		//to log out
		driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']")).click();
		WebElement adminIcon = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		ac.moveToElement(adminIcon).perform();
		
		
		
		//to click on sign out
		driver.findElement(By.linkText("Sign Out")).click();
		
		//verification
		String signUpPage = driver.getTitle();
		if(signUpPage.equals(LoginPage))
		{
			System.out.println("Sign up page is displayed and signed out successfully");
		}
		else
		{
			System.out.println("sign up pages is not displayed and not signed out");
		}
		
	}

}
