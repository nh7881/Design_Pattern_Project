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

import java.io.*;
import java.util.*;

/***
 *	Pass this exporter to a {@link Table#export} implementation to
 *	create a comma-sparated-value version of a {@link Table}.
 *	For example:
 *	<PRE>
 *	Table people  = TableFactory.create( ... );
 *	//...
 *	Writer out = new FileWriter( "people.csv" );
 *	people.export( new CSVExporter(out) );
 *	out.close();
 *	</PRE>
 *	The output file for a table called "name" with
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
 * @see Table
 * @see Table.Exporter
 * @see CSVImporter
 */

public class XMLExporter implements Table.Exporter
{	private final Writer out;
	private 	  int	 width;
	private String[] columnNames;
	public XMLExporter( Writer out )
	{	this.out = out;
	}

	public void startTable() throws IOException {
		out.write("<root>\n");
		out.write("\t<table>\n");
		out.write("\t\t<name>");
	}
	
	public void storeMetadata( String tableName,
							   int width,
							   int height,
							   Iterator columnNames ) throws IOException

	{	
		this.width = width;
		this.columnNames = new String[this.width];
		out.write(tableName == null ? "<anonymous>" : tableName );
		out.write("</name>\n");
		out.write("\t\t<columns>\n");
		int i=0;
		while( columnNames.hasNext() )
		{	Object datum = columnNames.next();

			if( datum != null )	
				out.write("\t\t\t<column>");
				out.write( datum.toString() );
				this.columnNames[i] = datum.toString();
				i++;
				out.write("</column>\n");
		}
		out.write("\t\t</columns>\n");
		out.write("\t\t<rows>\n");
	}

	public void storeRow( Iterator data ) throws IOException
	{	
		int i=0;
		out.write("\t\t\t<row>\n");
		while( data.hasNext() )
		{	Object datum = data.next();
			if( datum != null )	
				out.write("\t\t\t\t<" + this.columnNames[i] + ">");
				out.write( datum.toString() );
				out.write("</" + this.columnNames[i] + ">\n");
				i++;
		}
		out.write("\t\t\t</row>\n");
	}

	public void endTable()   throws IOException {
		out.write("\t\t</rows>\n");
		out.write("\t</table>\n");
		out.write("</root>\n");
	}
}
