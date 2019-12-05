package statistics;

import java.io.*; 
import printer.Printer;

public class CSVUtility
{
	/* METHODS */
	/**
	 * Permet d'ajouter un element a la ligne
	 * @param f le fichier
	 * @param strings la valeur
	 * @throws IOException IO exception
	 */
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
	
	/**
	 * Permet d'ajouter un element
	 * @param f le fichier
	 * @param s la valeur
	 * @throws IOException IO exception
	 */
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
	
	/**
	 * Permet d'ajouter un element a la ligne avec une virgule au debut
	 * @param f le fichier
	 * @param elt la valeur
	 * @throws IOException IO exception
	 */
	public void addElementToRow (File f, String elt) throws IOException
	{
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

	/**
	 * Ajoute une virgule
	 * @param f le fichier
	 * @throws IOException IO exception
	 */
	public void addNothing (File f) throws IOException
	{
		this.addSomething(f, ",");
	}

	/**
	 * Ajoute un retour a la ligne
	 * @param f le fichier
	 * @throws IOException IO exception
	 */
	public void endRow (File f) throws IOException
	{
		this.addSomething(f, "\n");
	}
}
