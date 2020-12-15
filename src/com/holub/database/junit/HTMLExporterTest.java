package com.holub.database.junit;

import java.io.FileWriter;
import java.io.Writer;
import java.util.Iterator;
import java.util.LinkedList;

import org.junit.Test;

import com.holub.database.HTMLExporter;
import com.holub.tools.ArrayIterator;

public class HTMLExporterTest {
	  @Test
	  public void testHTMLExporter( ) throws Exception {
		  Writer out = new FileWriter("C:/dp2020/HtmlExportTest.html");
		  HTMLExporter TestHtml = new HTMLExporter(out);
		  TestHtml.startTable();
		  TestHtml.storeMetadata("HtmlExportTest", 3, 3, new ArrayIterator(new String[] { "last", "first", "addrId" }));
		  LinkedList<String[]> rowSet = new LinkedList<String[]>();
		  rowSet.add(new String[] { "Fred", "Flintstone", "1" });
		  rowSet.add(new String[] { "Wilma", "Flintstone", "1" });
		  rowSet.add(new String[] { "Allen", "Holub", "0" });
		  for (Iterator<String[]> i = rowSet.iterator(); i.hasNext();)
			  TestHtml.storeRow(new ArrayIterator((Object[]) i.next()));
		  TestHtml.endTable();
		  out.close();
	  }
}
