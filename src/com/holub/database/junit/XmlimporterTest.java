package com.holub.database.junit;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileReader;
import java.io.Reader;

import org.junit.Test;

import com.holub.database.ConcreteTable;
import com.holub.database.Table;
import com.holub.database.XMLImporter;

public class XmlimporterTest {
	  @Test
	  public void testXMLImporter( ) throws Exception {
		  String check = "XmlExportTest\n"
		  		+ "last	first	addrId	\n"
		  		+ "----------------------------------------\n"
		  		+ "Fred	Flintstone	1	\n"
		  		+ "Wilma	Flintstone	1	\n"
		  		+ "Allen	Holub	0	\n";
		  Reader in = new FileReader("C:/dp2020/XmlExportTest.xml");
		  Table TestXmlImporter = new ConcreteTable(new XMLImporter(in));
		  assertEquals(check, TestXmlImporter.toString());
		  in.close();
	  }
}
