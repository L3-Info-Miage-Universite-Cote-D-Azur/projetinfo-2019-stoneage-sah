package game;
import client.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Settings {
	/* STATIC FINAL FIELDS */
	public static final int START_FIGURINE = 5;
	public static final int MAX_FIGURINE = 10;
	public static final int MAX_CHOICES = 1;
	public static final int NB_ZONES = 4;
	public static final int MAX_PLAYERS = 2;
	public static final int MAX_FIGURINE_IN_ZONE = 7;
	public static final int MAX_DICE = 6;

	public static String[] ZONE_NAMES = {"Forêt", "Argile", "Pierre", "Or"};
	public static String[] RESSOURCES_NAMES = {"bois", "argile", "pierre", "or"};

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
		int index = rand.nextInt(Settings.PLAYER_NAME.size());
		String ret = Settings.PLAYER_NAME.get(index);
		Settings.PLAYER_NAME.remove(index);
		return ret;
	}
	
	/**
	 * Renvoie une IA (qui suit une strategie) au hasard parmis une liste de strategie d'IA.
	 * @return IA, avec sa strategie.
	 */
	public static IA getRandomIA()
	{
		Random rand = new Random();
		return IA_list[rand.nextInt(IA_list.length)];
	}
}
