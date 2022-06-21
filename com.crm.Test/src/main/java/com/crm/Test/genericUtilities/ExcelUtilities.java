package com.crm.Test.genericUtilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtilities {
	
	/**
	 * it is used to read the data from the excel sheet
	 * @param sheet
	 * @param row
	 * @param cell
	 * @return
	 * @throws Throwable 
	 */
	public String readExcelData(String sheet, int row,int cell) throws Throwable
	{
		FileInputStream fstream=new FileInputStream("./src/test/resources/InputData.xlsx");
		Workbook wb=WorkbookFactory.create(fstream);
		return wb.getSheet(sheet).getRow(row).getCell(cell).toString();

	}
	
	
	/**
	 * its used to write the data to excel sheet
	 * @param sheet
	 * @param row
	 * @param cell
	 * @param data
	 * @throws Throwable
	 */
	public void writeDataIntoExcelFile(String sheet, int row,int cell,String data) throws Throwable
	{
		FileInputStream fstream=new FileInputStream("./src/test/resources/InputData.xlsx");
		Workbook wb=WorkbookFactory.create(fstream);
		wb.getSheet(sheet).getRow(row).createCell(cell).setCellValue(data);
		
		FileOutputStream fos=new FileOutputStream("./src/test/resources/InputData.xlsx");
		wb.write(fos);
		
		
	}
	

}
