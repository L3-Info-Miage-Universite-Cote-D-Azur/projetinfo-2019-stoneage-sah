package statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

public class CSVUtilityTest 
{
	private CSVUtility csv;
	
	@Test
	public void testAddToRow () throws IOException
	{
		File f = new File("file.test");
		String s = "TEST";
		String expected = "TEST";
		this.csv = new CSVUtility();
		this.csv.clearFile(f);
		
		this.csv.addToRow(f, s);
		
		@SuppressWarnings("resource")
		String ret = new BufferedReader(new InputStreamReader(new FileInputStream(f))).readLine();
		assertNotNull(ret);
		assertTrue(ret.equals(expected));
	}
	
	@Test
	public void testAddSomething () throws IOException
	{
		File f = new File("file.test1");
		String s = "TEST";
		String expected = "TEST";
		this.csv = new CSVUtility();
		this.csv.clearFile(f);
		
		this.csv.addSomething(f, s);
		
		@SuppressWarnings("resource")
		String ret = new BufferedReader(new InputStreamReader(new FileInputStream(f))).readLine();
		assertNotNull(ret);
		assertTrue(ret.equals(expected));
	}
	
	@Test
	public void testAddElementToRow () throws IOException
	{
		// Test avec une virgule a la fin
		File f = new File("file.test2");
		String s = "TEST,TEST,";
		String in = "test";
		String expected = "TEST,TEST,test";
		this.csv = new CSVUtility();
		this.csv.clearFile(f);
		this.csv.addSomething(f, s);
		
		this.csv.addElementToRow(f, in);
		
		@SuppressWarnings("resource")
		String ret = new BufferedReader(new InputStreamReader(new FileInputStream(f))).readLine();
		assertNotNull(ret);
		assertTrue(ret.equals(expected));
		this.csv.clearFile(f);
		
		// Test sans virgule a la fin
		f = new File("file.test3");
		s = "TEST,TEST";
		in = "test";
		expected = "TEST,TEST,test";
		this.csv = new CSVUtility();
		this.csv.clearFile(f);
		this.csv.addSomething(f, s);
		
		this.csv.addElementToRow(f, in);
		
		@SuppressWarnings("resource")
		String ret1 = new BufferedReader(new InputStreamReader(new FileInputStream(f))).readLine();
		assertNotNull(ret1);
		assertTrue(ret1.equals(expected));
	}
	
	@Test
	public void testAddNothing () throws IOException
	{
		File f = new File("file.test4");
		this.csv = new CSVUtility();
		this.csv.clearFile(f);
		
		this.csv.addNothing(f);
		
		@SuppressWarnings("resource")
		String ret = new BufferedReader(new InputStreamReader(new FileInputStream(f))).readLine();
		assertNotNull(ret);
		assertTrue(ret.equals(","));
	}
	
	@Test
	public void testEndRow () throws IOException
	{
		File f = new File("file.test5");
		String s = "TEST";
		this.csv = new CSVUtility();
		this.csv.clearFile(f);
		
		this.csv.addSomething(f, "TEST");
		this.csv.endRow(f);
		this.csv.addSomething(f, "TEST");
		
		@SuppressWarnings("resource")
		BufferedReader ret = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
		String line = ret.readLine();
		assertNotNull(line);
		assertTrue(line.equals("TEST"));
		line = ret.readLine();
		assertNotNull(line);
		assertTrue(line.equals("TEST"));
	}
	
	@Test
	public void testClearFile () throws IOException
	{
		File f = new File("file.test6");
		this.csv = new CSVUtility();
		this.csv.clearFile(f);
		assertNotNull(f);
		this.csv.addSomething(f, "AAAA");
		assertTrue(f.length() > 0);
		this.csv.clearFile(f);
		assertTrue(f.length() == 0);
	}
}
