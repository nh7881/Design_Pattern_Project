/*  (c) 2004 Allen I. Holub. All rights reserved.
 *
 *  This code may be used freely by yourself with the following
 *  restrictions:
 *
 *  o Your splash screen, about box, or equivalent, must include
 *    Allen Holub's name, copyright, and URL. For example:
 *
 *      This program contains Allen Holub's SQL package.<br>
 *      (c) 2005 Allen I. Holub. All Rights Reserved.<br>
 *              http://www.holub.com<br>
 *
 *    If your program does not run interactively, then the foregoing
 *    notice must appear in your documentation.
 *
 *  o You may not redistribute (or mirror) the source code.
 *
 *  o You must report any bugs that you find to me. Use the form at
 *    http://www.holub.com/company/contact.html or send email to
 *    allen@Holub.com.
 *
 *  o The software is supplied <em>as is</em>. Neither Allen Holub nor
 *    Holub Associates are responsible for any bugs (or any problems
 *    caused by bugs, including lost productivity or data)
 *    in any of this code.
 */
package com.holub.database;

import com.holub.tools.ArrayIterator;

import java.io.*;
import java.util.*;

/***
 *	Pass this importer to a {@link Table} constructor (such
 *	as
 *	{link com.holub.database.ConcreteTable#ConcreteTable(Table.Importer)}
 *	to initialize
 *	a <code>Table</code> from
 *	a comma-sparated-value repressentation. For example:
 *	<PRE>
 *	Reader in = new FileReader( "people.csv" );
 *	people = new ConcreteTable( new CSVImporter(in) );
 *	in.close();
 *	</PRE>
 *	The input file for a table called "name" with
 *	columns "first," "last," and "addrId" would look
 *	like this:
 *	<PRE>
 *	name
 *	first,	last,	addrId
 *	Fred,	Flintstone,	1
 *	Wilma,	Flintstone,	1
 *	Allen,	Holub,	0
 *	</PRE>
 *	The first line is the table name, the second line
 *	identifies the columns, and the subsequent lines define
 *	the rows.
 *
 * @include /etc/license.txt
 *
 * @see Table
 * @see Table.Importer
 * @see CSVExporter
 */

public class XMLImporter implements Table.Importer
{	private BufferedReader  in;			// null once end-of-file reached
	private LinkedList<String> columnNames;
	private String tableName;

	public XMLImporter( Reader in )
	{	this.in = in instanceof BufferedReader
						? (BufferedReader)in
                        : new BufferedReader(in)
	                    ;
		this.columnNames = new LinkedList();
	}
	public void startTable()			throws IOException
	{	
		in.readLine();
		in.readLine();
		tableName   = in.readLine().trim().replace("<name>", "").replace("</name>", "");
		in.readLine();
		String temp = in.readLine().trim();
		String endpoint = new String("</columns>");
		while(!temp.equals(endpoint)) {
			temp = temp.replace("<column>", "").replace("</column>", "");
			this.columnNames.add(temp);
			temp = in.readLine().trim();
		}
		in.readLine();
	}
	public String loadTableName()		throws IOException
	{	return tableName;
	}
	public int loadWidth()			    throws IOException
	{	return columnNames.size();
	}
	public Iterator loadColumnNames()	throws IOException
	{	return columnNames.iterator();
	}

	public Iterator loadRow()			throws IOException
	{	
		Iterator row = null;
		if( in != null )
		{	
			String[] tempRow = new String[loadWidth()];
			int i=0;
			in.readLine();
			String line = in.readLine().trim();
			String endpoint = new String("</row>");
			String finalpoint = new String("</table>");
			while(!line.equals(endpoint)) {
				if( line.equals(finalpoint) ) {
					in = null;
					break;
				}
				else {
					tempRow[i] = line.replaceAll("<[0-9A-Za-z]*>", "").replaceAll("</[0-9A-Za-z]*>", "");
					i++;
					line = in.readLine().trim();
				}
			}
			if(i!=0) {
				row = new ArrayIterator(tempRow);
			}
			
		}
		return row;
	}

	public void endTable() throws IOException {}
}
