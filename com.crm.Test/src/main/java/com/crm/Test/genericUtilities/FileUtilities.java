package com.crm.Test.genericUtilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 
 * @author Prasad
 *
 */
public class FileUtilities {
	/**
	 * it is used to get the common data from property file based on key which you specified as a argument
	 * @param key
	 * @return
	 * @throws Throwable
	 */
	public String getPropertyKeyValue(String key) throws Throwable
	{
		FileInputStream fis=new FileInputStream("./src/test/resources/data.properties");
		Properties prop=new Properties();
		prop.load(fis);
		String value=prop.getProperty(key);
		return value;
	}
	/**
	 * 
	 * @param sheet
	 * @param row
	 * @param cell
	 * @return
	 * @throws Throwable 
	 */
	public String readExcelData( String sheet, int row, int cell) throws Throwable{
		FileInputStream fis=new FileInputStream("./src/test/resources/InputData.xlsx");
		Workbook wb = WorkbookFactory.create(fis);
		return wb.getSheet(sheet).getRow(row).getCell(cell).getStringCellValue();
	
}
}
