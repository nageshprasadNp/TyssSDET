package com.crm.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import com.mysql.jdbc.Driver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CreateNewPojectTest {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub

		//to launch the rmgyantra 
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https//:localhost:8084/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		//to inspect username, password and login button
		
		driver.findElement(By.id("Username")).sendKeys("rmgyantra");
		driver.findElement(By.id("inputPassword")).sendKeys("rmgy@9999");
		driver.findElement(By.xpath("//button[.='Sign in']")).click();
		
		//to inspect project submenu
		
		driver.findElement(By.xpath("//a[.='Projects']")).click();
		
		//to click on create project button
		 driver.findElement(By.xpath("//button[.='Create Project']")).click();
		 //to inspect project name
		 
		 driver.findElement(By.name("projectName")).sendKeys("cheeta");
		 driver.findElement(By.name("createdBy")).sendKeys("Rajeev");
		 
		 WebElement opt = driver.findElement(By.xpath(""));
		 Select s=new  Select(opt);
		 s.selectByVisibleText("on Going");
		 
		 driver.findElement(By.xpath("//input[@type='submit']")).submit();
		 
		 //verify in project is add in application
		 
		 
		 
		 //verify project is added into database
		 
		 Driver driverReference=new Driver();
		 //to register
		 DriverManager.registerDriver(driverReference);
		 //to connect
		 Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects","root","root");
		 //to create statment
		 Statement statement = connection.createStatement();
		 //write a query
		 String query="select * from project";
		 //execute query
		 ResultSet result = statement.executeQuery(query);
		 while(result.next())
		 {
			 String value = result.getString(4);
						System.out.println(value);
			 if(value.contains("cheeta"))
			 {
				 System.out.println("project is present");
			 }
			 else
			 {
				 System.out.println("record is not present");
			 }
			 
			 
		 }
		 
		 
		 connection.close();
		 
		 
		 driver.quit();
		 
		 
		 
		 
		 
		 
		 
	}

}
