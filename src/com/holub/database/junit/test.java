package com.holub.database.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.sql.*;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Test;
import com.holub.database.*;
import com.holub.tools.ArrayIterator;
 
public class test{
  @Test
  public void testjoinstarquery( ) throws Exception {
	  String check = "addrIdstreetcitystatezipfirstlastaddrId\n"
	  		+ "--------- --------- --------- --------- --------- --------- --------- --------- \n"
	  		+ "012 MyStreetBerkeleyCA99998AllenHolub0\n"
	  		+ "134 Quarry Ln.BedrockAZ00000FredFlintstone1\n"
	  		+ "134 Quarry Ln.BedrockAZ00000WilmaFlintstone1\n";
	  Class.forName( "com.holub.database.jdbc.JDBCDriver" );
	  Connection connection = null;
	  Statement  statement  = null;
	  connection = DriverManager.getConnection(			//{=JDBCTest.getConnection}
				"file:/C:/DP2020Project/Dbase",
				"harpo", "swordfish" );
	  
	  statement = connection.createStatement();
	  
	  ResultSet result = statement.executeQuery( "select * from address, name where address.addrId = name.addrId" );
	  ResultSetMetaData metadata = result.getMetaData();
	  StringBuffer b = new StringBuffer();
	  int columns = metadata.getColumnCount();
	  for( int i = 1; i <= columns; ++i )
		  b.append(metadata.getColumnName(i));
	  b.append("\n");
	  for( int i = 1; i <= columns; ++i )
		  b.append("--------- ");
	  b.append("\n");
	  while( result.next() ){
		  for( int i = 1; i <= columns; ++i )
			  b.append(result.getString(metadata.getColumnName(i)));
		  b.append("\n");
		}
	  String res = b.toString();
	  assertEquals(check, res);
	  if(statement != null) statement.close();
	  if(connection!= null) connection.close();
  }



  
}