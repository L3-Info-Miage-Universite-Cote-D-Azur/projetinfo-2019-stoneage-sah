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
	// Nombre d'espace dans la cabane
	public static final int MAX_ZONEHUT_SPACE = 2;
	// Nombre d'espace dans le champ
	public static final int MAX_ZONEFIELD_SPACE = 1;
	// Aleatoire globale
	public static Random RAND = new Random();
		
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
		int index = Settings.RAND.nextInt(Settings.PLAYER_NAME.size());
		
		String ret = Settings.PLAYER_NAME.get(index);
		Settings.PLAYER_NAME.remove(index);
		
		return ret;
	}
	
	/**
	 * Renvoie une IA parmi celles presentes aléatoirement
	 * @return Renvoie une instance implementant l'interface IA
	 */
	public static IA getRandomIA () 
	{
		return IA_list[Settings.RAND.nextInt(IA_list.length)];
	}
    
}
