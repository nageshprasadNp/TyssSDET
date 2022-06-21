package com.crm.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ToGetDateTest {

	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		Date date=new Date();
		String value= date.toString();
		System.out.println(value);
		
		
		Date date1 = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String format = formatter. format(date1);
		System. out. println(format);
		
		
		String yyyy=value.split(" ")[5];
		//String mm=value.split(" ")[1];
		String dd=value.split(" ")[2];
		int mm=date.getMonth()+1;
		
		String findformat=yyyy+" " +mm+" "+dd;
		System.out.println(findformat);
}

}
