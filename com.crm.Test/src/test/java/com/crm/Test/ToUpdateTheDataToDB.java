package com.crm.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.jdbc.Driver;

public class ToUpdateTheDataToDB {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		
		
		//to register database
		
Driver driverReference = new Driver();
		
		//to register database
		DriverManager.registerDriver(driverReference);
		
		//connect to database
		
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/projects","root","root");
		
		//create a statement
		
		Statement statement = connection.createStatement();
		
		//write the query
		
		String query = "insert into project values('Ty_proj_0012','','','','',)";
		
		//execute query
		
		ResultSet result = statement.executeQuery(query);
		
	}

}
