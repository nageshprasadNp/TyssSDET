package com.crm.Test.genericUtilities;

import java.util.Date;
import java.util.Random;

/**
 * 
 * @author Prasad
 *
 */
public class JavaUtility {
	/**
	 * its used to generate a random number
	 * @return
	 */
	public int getrandomNumber()
	{
		Random random=new Random();
		int randNum=random.nextInt(1000);
		return randNum;
	}
	
	/**
	 * its used to get systemDateAndTime in IST Format
	 * @return
	 */
	public String getSystemDateAndTimeInISTformat()
	{
		Date date=new Date();
		return date.toString();
	}
	
	/**
	 * its is used to get system date and time in required format
	 * @return
	 */
	public String getSystemDateAndTimeInStandardFormat()
	{
		Date date=new Date();
		String dateAndTime = date.toString();
		String YYYY=dateAndTime.split(" ")[5];
		int MM=date.getMonth()+1;
		String DD=dateAndTime.split(" ")[1];
		
		String finalFormat=YYYY+" "+MM+" "+DD;
		return finalFormat;
		
	}
	
	
}
