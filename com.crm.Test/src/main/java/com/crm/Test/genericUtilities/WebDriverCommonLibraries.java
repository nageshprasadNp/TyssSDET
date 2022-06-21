package com.crm.Test.genericUtilities;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.http.util.Asserts;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

/**
 * 
 * @author Prasad
 *
 */
public class WebDriverCommonLibraries {

	/**
	 * it is wait for 10  seconds till the page gets load                                            
	 * @param driver
	 */
	public void waitForPageGetLoad(WebDriver driver)
	{
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
		
	}
	
	/**
	 * it will check for the element in the GUI for the polling time of 500ms
	 * @param driver
	 * @param element
	 */
	
	public void waitForElementToBeClickable(WebDriver driver,WebElement element)
	{
		WebDriverWait wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	/**
	 * it will check for the title in GUI for polling time of 500ms
	 * @param driver
	 * @param title
	 */
	public void  waitForTitleContains(WebDriver driver,String title)
	{
		WebDriverWait wait=new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.titleContains(title));
		
	}
	/**
	 * here we can change the polling time from default 500ms to any polling time 
	 * @param driver
	 * @param pollingtime
	 * @param element
	 */
	public void waitForElementToCustomWait(WebDriver driver,int pollingtime,WebElement element)
	{
		FluentWait wait=new FluentWait(driver);
		wait.pollingEvery(pollingtime, TimeUnit.SECONDS);
		wait.ignoring(Exception.class);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	/**
	 * Here we giving the custom time out
	 * @param element
	 * @throws InterruptedException
	 */
	public void waitAndClick(WebElement element) throws InterruptedException
	{
		int count=0;
		while(count<10)
		{
			try {
				element.click();
				
			} catch (Exception e) {
			Thread.sleep(1000);
			count++;
			}
		}
	}
	 
	/**
	 * it is used to switching from one window to another window
	 * @param driver
	 * @param partialWindowTitle
	 */
	
	public void switchToWindow(WebDriver driver,String partialWinTitle)
	{
		
		Set<String> allAddr = driver.getWindowHandles();
		Iterator<String> it = allAddr.iterator();
		while(it.hasNext())
		{
			String wTitle = it.next();
			driver.switchTo().window(wTitle);
			String currentWinTitle = driver.getTitle();
			if(currentWinTitle.contains(partialWinTitle))
			{
				
				break;
				
			}
			
		}
		
	}


	
	
	
	
	
	
	
	
	
	/**
	 * switching from one frame to another frame using index
	 * @param driver
	 * @param index
	 */
	
	public void switchToFrame(WebDriver driver,int index)
	{
	
		driver.switchTo().frame(index);
	}
	/**
	 * switch frome one frame to another frame by using id_attibute
	 * @param driver
	 * @param id_attribute
	 */
	public void switchToFrame(WebDriver driver,String id_attribute)
	{
		driver.switchTo().frame(id_attribute);
	}
	
	/**
	 * switching from one frame to another frame by using xpath
	 * @param driver
	 * @param element
	 */
	public void switchToFrame(WebDriver driver,WebElement element)
	{
		driver.switchTo().frame(element);
	}
	
	/**
	 * it is used to switch from one frame to parent frame
	 * @param driver
	 */
	public void switchBackToParentPage(WebDriver  driver)
	{
		driver.switchTo().defaultContent();
	}
	
	/**
	 * it is used to switch to alert pop up and accept
	 * @param driver
	 */
	public void switchToAlertPopUpAndAccept(WebDriver driver)
	{
		driver.switchTo().alert().accept();
	}
	
	/**
	 * it is used to switch to alert popup and dismiss
	 * @param driver
	 */
	public void switchToAlertPopupAndDismiss(WebDriver driver)
	{
		driver.switchTo().alert().dismiss();
	}
	
	/**
	 * it is used to select the drop down value by index
	 * @param element
	 * @param index
	 */
	public void selectDropDownOption(WebElement element,int index)
	{
		Select select=new Select(element);
		select.selectByIndex(index);
	}
	
	/**
	 * it is used to select the drop down option by using value
	 * @param element
	 * @param value
	 */
	public void selectDropDownOption(WebElement element,String value)
	{
		Select select=new Select(element);
		select.selectByValue(value);
	}
	
	/**
	 * it is used to select the drop down option by using visible text
	 * @param value
	 * @param element
	 */
	public void selectDropDownOption(String value,WebElement element)
	{
		Select select=new Select(element);
		select.selectByVisibleText(value);
	}
	
	/**
	 * it is used to mouse over an element
	 * @param driver
	 * @param element
	 */
	public void mouseOverAnElement(WebDriver driver,WebElement element)
	{
		Actions action=new Actions(driver);
		action.moveToElement(element).perform();;
	}
	
	/**
	 * it is used to right click on element
	 * @param driver
	 * @param element
	 */
	public void rightClickOnElement(WebDriver driver,WebElement  element)
	{
		Actions action=new Actions(driver);
		action.contextClick(element).perform();
		
	}
	
	/**
	 * it is used to click on enter button using keyboard action
	 * @param driver
	 */
	public void clickOnEnterButton(WebDriver driver)
	{
		Actions action=new Actions(driver);
		action.sendKeys(Keys.ENTER);
	}
	
	/**
	 * it is used to take screen shot
	 * @param driver
	 * @param screenShotName
	 * @throws Throwable
	 */
	public void takeScreenShot(WebDriver  driver,String screenShotName) throws Throwable
	{
		TakesScreenshot ts=(TakesScreenshot) driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File("./screenShots/"+screenShotName+".png");
		Files.copy(src, dest);
		
	}
	
	/**
	 * it is used to perform the scroll bar action
	 * @param driver
	 */
	public void scrollBarAction(WebDriver driver)
	{
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,500)");
	}
	
	public void scrollBarUp(WebDriver driver)
	{
		JavascriptExecutor js=(JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-1000)");
	}
	
	public void verify(String actaul,String expected)
	{
		if(actaul.equals(expected))
		{
			System.out.println("test case is passed");
		}
		else
		{
			System.out.println("test case is failed");
		}
	}
}
