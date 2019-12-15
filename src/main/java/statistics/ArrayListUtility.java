package statistics;

import java.util.ArrayList;

public class ArrayListUtility 
{
	/**
	 * Calcul la taille la plus grande d'un tableau d'ArrayList
	 * @param arr le tableau d'ArrayList
	 * @return la taille maximale
	 */
	public static int getMaxLength (ArrayList<Integer>[] arr)
	{
		int max = 0;
		for (int i = 0; i < arr.length; i++)
			if (arr[i].size() > max)
				max = arr[i].size();
		return max;
	}
	
	/**
	 * Calcul la derniere valeur d'un ArrayList
	 * @param arr un ArrayList(Integer)
	 * @return la derniere valeur de l'ArrayList
	 */
	public static int getLastValueOf (ArrayList<Integer> arr)
	{
		return arr.get(arr.size() - 1);
	}
	
	/**
	 * Recupere la valeur de chaque tableau d'ArrayList
	 * @param arr le tableau d'ArrayList
	 * @return un tableau d'int contenant la derniere valeur de chaque ArrayList
	 */
	public static int[] getLastValuesOf (ArrayList<Integer>[] arr)
	{
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++)
			res[i] = ArrayListUtility.getLastValueOf(arr[i]);
		return res;
	}
	
	/**
	 * Permet d'additionner une valeur sur une plage de donnees
	 * @param arr un ArrayList
	 * @param from le debut
	 * @param to la fin
	 * @param value le nombre a additionner
	 */
	public static void addFromTo (ArrayList<Integer> arr, int from, int to, int value)
	{
		if (from < 0 || to > arr.size())
			return;
		for (int i = from; i < to; i++)
			arr.set(i, arr.get(i) + value);
	}
	
	/**
	 * Remplis un ArrayList jusqu'a n avec une certaine valeur
	 * @param arr un ArrayList
	 * @param n la taille voulue
	 * @param value la valeur voulue
	 */
	public static void fillUpTo (ArrayList<Integer> arr, int n, int value)
	{
		if (arr.size() >= n)
			return;
		for (int i = arr.size(); i < n; i++)
			arr.add(value);
	}
	
	/**
	 * Permet de remplir un tableau d'ArrayList jusqu'a n avec une certaine valeur
	 * @param arr un tableau d'ArrayList
	 * @param n la taille voulue
	 * @param value la valeur voulue
	 */
	public static void fillArraysUpTo (ArrayList<Integer>[] arr, int n, int[] value)
	{
		for (int i = 0; i < arr.length; i++) 
		{
			//System.out.println("ARRAYSTART: " + arr[i].toString());
			ArrayListUtility.fillUpTo(arr[i], n, value[i]);
			//System.out.println("ARRAYEND: " + arr[i].toString());
		}
	}
}
