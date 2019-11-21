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
	// Nombre de ressource present dans le jeu:
	public static final int NB_RESSOURCES = 6;
	// Aleatoire globale
	public static Random RAND = new Random();
	// Nombre de ressources au maximum dans BuildingRessourceChoosed
	public static int NB_MAX_RESSOURCE_BUILDING = 7;
	// Nombre d'espace au fabricant d'outils:
	public static final int MAX_ZONETOOL_SPACE = 1;

	
	// Toutes les cartes tuiles batiment du jeu
	public static ArrayList<Building> BUILDINGS = new ArrayList<Building>(Arrays.asList(
			new BuildingRessourceImposed(10, Ressource.WOOD, Ressource.WOOD, Ressource.CLAY),
			new BuildingRessourceImposed(11, Ressource.WOOD, Ressource.WOOD, Ressource.STONE),
			new BuildingRessourceImposed(12, Ressource.WOOD, Ressource.WOOD, Ressource.GOLD),
			new BuildingRessourceImposed(11, Ressource.WOOD, Ressource.CLAY, Ressource.CLAY),
			new BuildingRessourceImposed(12, Ressource.WOOD, Ressource.CLAY, Ressource.STONE),
			new BuildingRessourceImposed(12, Ressource.WOOD, Ressource.CLAY, Ressource.STONE),
			new BuildingRessourceImposed(13, Ressource.CLAY, Ressource.CLAY, Ressource.STONE),
			new BuildingRessourceImposed(14, Ressource.WOOD, Ressource.STONE, Ressource.GOLD),
			new BuildingRessourceImposed(14, Ressource.WOOD, Ressource.STONE, Ressource.GOLD),
			new BuildingRessourceImposed(13, Ressource.WOOD, Ressource.STONE, Ressource.STONE),
			new BuildingRessourceImposed(13, Ressource.WOOD, Ressource.CLAY, Ressource.GOLD),
			new BuildingRessourceImposed(13, Ressource.WOOD, Ressource.CLAY, Ressource.GOLD),
			new BuildingRessourceImposed(16, Ressource.STONE, Ressource.STONE, Ressource.GOLD),
			new BuildingRessourceImposed(15, Ressource.CLAY, Ressource.STONE, Ressource.GOLD),
			new BuildingRessourceImposed(15, Ressource.CLAY, Ressource.STONE, Ressource.GOLD),
			new BuildingRessourceImposed(14, Ressource.CLAY, Ressource.STONE, Ressource.STONE),
			new BuildingRessourceImposed(14, Ressource.CLAY, Ressource.CLAY, Ressource.GOLD),
			new BuildingRessourceChoosed(),
			new BuildingRessourceChoosed(),
			new BuildingRessourceChoosed(),
			new BuildingRessourceNotImposed(2, 5),
			new BuildingRessourceNotImposed(1, 5),
			new BuildingRessourceNotImposed(4, 4),
			new BuildingRessourceNotImposed(3, 4),
			new BuildingRessourceNotImposed(2, 4),
			new BuildingRessourceNotImposed(1, 4),
			new BuildingRessourceNotImposed(4, 5),
			new BuildingRessourceNotImposed(3, 5)
			));
	
	// Nombre de pile de cartes tuiles batiments
	public static int NB_BUILDING_DECK = 4;
	// Nombre de cartes dans un deck
	public static int NB_BUILDING_CARD_IN_DECK = (int)(Settings.BUILDINGS.size() / Settings.NB_BUILDING_DECK);
		
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
	 * Renvoie une IA parmi celles presentes alÃ©atoirement
	 * @return Renvoie une instance implementant l'interface IA
	 */
	public static IA getRandomIA () 
	{
		return IA_list[Settings.RAND.nextInt(IA_list.length)];
	}
	
	/**
	 * Renvoie un deck de cartes de tuiles batiments
	 * @return un deck de cartes de tuiles batiments
	 */
	public static ArrayList<Building> getRandomDeck()
	{
		ArrayList<Building> buildings = new ArrayList<Building>(Settings.NB_BUILDING_CARD_IN_DECK);
		for (int i = 0; i < Settings.NB_BUILDING_CARD_IN_DECK; i++)
		{
			buildings.add(Settings.BUILDINGS.get(0));
			Settings.BUILDINGS.remove(0);
			if (Settings.BUILDINGS.isEmpty())
				return buildings;
		}
		return buildings;
	}
    
}
