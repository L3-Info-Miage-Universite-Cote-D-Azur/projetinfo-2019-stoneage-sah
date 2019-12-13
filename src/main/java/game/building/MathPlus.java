package game.building;

import java.util.ArrayList;

public class MathPlus 
{
	private MathPlus() {}
	
	/**
	 * Factoriel de n
	 * @param n le nombre
	 * @return n!
	 */
	public static int fact (int n)
	{
		int res = 1;
		if (n >= 0)
		{
			for (int i = n; i > 1; i--)
			{
				res *= i;
			}
			return res;
		}
		throw new IllegalArgumentException("Negative argument");
	}
	
	/**
	 * Calcul p parmis n
	 * @param p combien d'elements
	 * @param n le nombre d'elements total
	 * @return la combinaison de p parmis n
	 */
	public static int combinaison (int p, int n)
	{
		if (p == n) return 1;
		if (p > n / 2) p = n - p;
		return MathPlus.fact(n) / (MathPlus.fact(p) * MathPlus.fact(n - p));
	}
	
	/**
	 * Coupe le tableau tab tel que tab = tab[n:]
	 * @param tab le tableau
	 * @param n l'indice
	 * @return un nouveau tableau tab[n:]
	 */
	public static int[] slice (int[] tab, int n)
	{
		if (tab.length - n < 0 || n < 0)
			return new int[0];
		int[] res = new int[tab.length - n];
		for (int i = n; i < tab.length; i++)
		{
			res[i-n] = tab[i];
		}
		return res;
	}
	
	/**
	 * Ajoute un element a un tableau
	 * @param tab le tableau
	 * @param i l'element
	 * @return un nouveau tableau avec i a la fin
	 */
	public static int[] concat (int[] tab, int i)
	{
		int[] res = new int[tab.length + 1];
		for (int k = 0; k < tab.length; k++)
		{
			res[k] = tab[k];
		}
		res[res.length - 1] = i;
		return res;
	}
	
	/**
	 * Renvoie une liste d'une combinaison possible de taille k
	 * @param n l'indice de combinaison
	 * @param source le tableau source
	 * @param inProgress le tableau qui contient les elements de la combinaison
	 * @param ret un ArrayList<int[]> qui contient les differentes combinaison
	 * @param k la longueur de la combinaison generee
	 */
	public static void combinaisonPartition (int n, int[] source, int[] inProgress, ArrayList<int[]> ret, int k)
	{
		if (n == 0)
		{
			if (inProgress.length == k)
			{
				ret.add(inProgress);
			}
		}
		else
		{
			for (int i = 0; i < source.length; i++)
			{
				MathPlus.combinaisonPartition(n - 1, MathPlus.slice(source, i + 1), MathPlus.concat(inProgress, source[i]), ret, k);
			}
		}
	}
	
	/**
	 * Genere les combinaisons de taille k
	 * @param inv le tableau
	 * @param k la longueur voulue
	 * @return un ArrayList de taille combinaison(k, inv.length) contenant des tableaux de taille k qui sont des combinaisons de inv
	 */
	public static ArrayList<int[]> combinaisons (int[] inv, int k)
	{
		ArrayList<int[]> res = new ArrayList<int[]>();
		for (int i = 0; i < inv.length; i++)
		{
			MathPlus.combinaisonPartition(i, inv, new int[] {}, res, k);
		}
		return res;
	}
}
