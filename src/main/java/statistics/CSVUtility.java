package statistics;

import java.io.*; 
import printer.Printer;

public class CSVUtility
{
	/* METHODS */
	public void addToRow (File f, String strings) throws IOException
	{
		System.out.println(String.join("", strings));
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true), "UTF-8")))
		{
			StringBuilder sb = new StringBuilder();
			String prefix = new String();

			sb.append(prefix);
			prefix = ",";
			sb.append(strings);

			sb.append(System.getProperty("line.separator"));
			System.out.println(sb.toString());
			writer.write(sb.toString());
			writer.close();
		} catch (FileNotFoundException e) {
			Printer.getPrinter().println(e.getMessage());
		}
	}
	
	// s contient \n
	public void addSomething (File f, String s) throws IOException
	{
		try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, true), "UTF-8")))
		{
			writer.write(new StringBuilder().append(s).toString());
			writer.close();
		} catch (FileNotFoundException e) {
			Printer.getPrinter().println(e.getMessage());
		}
	}
	
	public void addElementToRow (File f, String elt) throws IOException
	{
		for (int i = 0; i < elt.length(); i++)
		{
			System.out.printf("%x ", (int)elt.charAt(i));
		}
		
		String line = null;
		char last = ',';
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f.getAbsoluteFile()));
			while ((line = reader.readLine()) != null && line.length() > 0)
			{
			    last = line.charAt(line.length() - 1);
			}
			reader.close();
		} catch (FileNotFoundException e) {
			Printer.getPrinter().println(e.getMessage());
		}
		
		if (last != ',')
		{
			// AJOUT DE ','
			this.addNothing(f);
		}
		this.addSomething(f, elt);
	}

	public void addNothing (File f) throws IOException
	{
		this.addSomething(f, ",");
	}

	public void endRow (File f) throws IOException
	{
		this.addSomething(f, "\n");
	}
}