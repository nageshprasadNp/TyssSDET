package com.crm.rmgyanta.project;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class AddProjectIntoRmgYantraAndVerifyInMysqlTest {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		
		WebDriver  driver=null;
		//fetching data from property file
		FileInputStream fis=new  FileInputStream("./src/test/resources/data.properties");
		Properties p=new Properties();
		p.load(fis);
		String url = p.getProperty("url");
		String username = p.getProperty("username");
		String password = p.getProperty("Password");
		String browser = p.getProperty("browser");
		//String LoginTitle=p.getProperty("LoginTitle");
		
		
		//fetching product details from excel sheet
		FileInputStream fsteam=new FileInputStream("./src/test/resources/InputData.xlsx");
		Workbook wb=WorkbookFactory.create(fsteam);
		//fetching project_id from excel
		String project_Id = wb.getSheet("sheet1").getRow(13).getCell(2).getStringCellValue();
		//fetching project name from excel
		String project_Name=wb.getSheet("sheet1").getRow(13).getCell(3).getStringCellValue();
		//fetching no_of_emp from excel
		int no_of_emp=(int) wb.getSheet("sheet1").getRow(13).getCell(4).getNumericCellValue();
		//fetching projct manager name from excel
		String project_manager=wb.getSheet("sheet1").getRow(13).getCell(6).getStringCellValue();
		////fetching status of the project from excel
		String status=wb.getSheet("sheet1").getRow(13).getCell(6).getStringCellValue();
	
		
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
				System.out.println("browser is launched");
				driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
				//inspecting user name and password from loginpage
				driver.findElement(By.name("user_name")).sendKeys(username);
				driver.findElement(By.id("user_password")).sendKeys(password);
				driver.findElement(By.xpath("")).click();
		

	}

}
