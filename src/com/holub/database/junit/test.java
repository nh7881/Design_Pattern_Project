package com.holub.database.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.*;

import org.junit.Test;
import com.holub.database.*;
 
public class test{
   
  @Test
  public void testjoinstarquery( ) throws Exception {
	  Class.forName( "com.holub.database.jdbc.JDBCDriver" );
	  Connection connection = null;
	  Statement  statement  = null;
	  connection = DriverManager.getConnection(			//{=JDBCTest.getConnection}
				"file:/c:/src/com/holub/database/jdbc/Dbase",
				"harpo", "swordfish" );
	  statement = connection.createStatement();
	  ResultSet result = statement.executeQuery( "select * from testA, testB" );
	  while( result.next()) {
		  System.out.println("row data : " + result.toString());
	  }
	  if(statement != null) statement.close();
	  if(connection!= null) connection.close();
  }
 
}