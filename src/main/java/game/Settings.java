package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import client.IA;
import client.RandomIA;

/**
 * La classe represente les parametres immutables du jeu.  
 */

public class Settings
{
	/* STATIC FINAL FIELDS */
	// Nombre de figurine au depart pour chaque joueur
	public static final int START_FIGURINE = 5;
	// Nombre de figurine maximale pour chaque joueur
	public static final int MAX_FIGURINE = 10;
	// Nombre de joueur dans la partie
	public static final int MAX_PLAYER = 2;
	// Nombre de place dans les zones de ressources. 
	public static final int MAX_ZONERESSOURCE_SPACE = 7;
	// Nombre de zones au total
	public static final int NB_ZONES = 7;
    
	// Liste des noms de joueurs. 
	public static ArrayList<String> PLAYER_NAME = new ArrayList<String>(Arrays.asList("Robert","Sardoche","Doritos","JeanCharles","Mclito&Carfly","Helico42"));
    
	public static IA[] IA_list = new IA[]{new RandomIA()};
    
	/* STATIC METHODS */
	
	/**
	 * Renvoie un nom au hasard parmis une liste de nom.
	 * @return String ret, le nom de la personnage pris au hasard. 
	 */
	public static String getRandomName ()
	{
		Random rand = new Random();
		int index = rand.nextInt(SETTING.PLAYER_NAME.size());
		
		String ret = SETTING.PLAYER_NAME.get(index);
		SETTING.PLAYER_NAME.remove(index);
		
		return ret;
	}
	
	/**
	 * Renvoie une IA parmi celles présentes aléatoirement
	 * @return Renvoie une instance implémentant l'interface IA
	 */
	public static IA getRandomIA () 
	{
		Random rand = new Random();
		return IA_list[rand.nextInt(IA_list.length)];
	}
    
}
