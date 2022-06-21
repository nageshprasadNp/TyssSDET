package com.crm.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class ToReadDataFromTheDB {

	public static void main(String[] args) throws SQLException {
	
		Driver driverReference = new Driver();
		
		//to register database
		DriverManager.registerDriver(driverReference);
		
		//connect to database
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects","root","root");
		
		//create a statement
		
		Statement statement = connection.createStatement();
		
		//write the query
		
		String query = "select * from Project";
		
		//execute query
		
		ResultSet result = statement.executeQuery(query);
		
		while(result.next())
		{
			System.out.println(result.getString(1)+" "+result.getString(2)+" "+result.getString(3)+" "+result.getString(4));
		}
		
		connection.close();
		
		
		
		
		
		
		
	}

}
